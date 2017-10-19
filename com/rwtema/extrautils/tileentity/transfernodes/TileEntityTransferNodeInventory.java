/*   1:    */ package com.rwtema.extrautils.tileentity.transfernodes;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.block.Box;
/*   5:    */ import com.rwtema.extrautils.block.BoxModel;
/*   6:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   7:    */ import com.rwtema.extrautils.inventory.LiquidInventory;
/*   8:    */ import com.rwtema.extrautils.item.ItemNodeUpgrade;
/*   9:    */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeBuffer;
/*  10:    */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeInventory;
/*  11:    */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.ItemBuffer;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Random;
/*  14:    */ import net.minecraft.block.Block;
/*  15:    */ import net.minecraft.block.material.Material;
/*  16:    */ import net.minecraft.entity.item.EntityItem;
/*  17:    */ import net.minecraft.entity.player.EntityPlayer;
/*  18:    */ import net.minecraft.init.Blocks;
/*  19:    */ import net.minecraft.inventory.IInventory;
/*  20:    */ import net.minecraft.inventory.ISidedInventory;
/*  21:    */ import net.minecraft.inventory.InventoryCrafting;
/*  22:    */ import net.minecraft.item.Item;
/*  23:    */ import net.minecraft.item.ItemStack;
/*  24:    */ import net.minecraft.item.crafting.CraftingManager;
/*  25:    */ import net.minecraft.item.crafting.IRecipe;
/*  26:    */ import net.minecraft.nbt.NBTTagCompound;
/*  27:    */ import net.minecraft.tileentity.TileEntity;
/*  28:    */ import net.minecraft.util.AxisAlignedBB;
/*  29:    */ import net.minecraft.world.World;
/*  30:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  31:    */ import net.minecraftforge.fluids.IFluidHandler;
/*  32:    */ 
/*  33:    */ public class TileEntityTransferNodeInventory
/*  34:    */   extends TileEntityTransferNode
/*  35:    */   implements INodeInventory, ISidedInventory
/*  36:    */ {
/*  37: 32 */   private static final int[] contents = { 0 };
/*  38: 33 */   private static final int[] nullcontents = new int[0];
/*  39: 34 */   private static InvCrafting crafting = new InvCrafting(3, 3);
/*  40: 36 */   private static ForgeDirection[] orthY = { ForgeDirection.NORTH, ForgeDirection.NORTH, ForgeDirection.UP, ForgeDirection.UP, ForgeDirection.UP, ForgeDirection.UP, ForgeDirection.UNKNOWN };
/*  41: 45 */   private static ForgeDirection[] orthX = { ForgeDirection.WEST, ForgeDirection.WEST, ForgeDirection.WEST, ForgeDirection.EAST, ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.UNKNOWN };
/*  42: 54 */   private boolean hasCStoneGen = false;
/*  43: 55 */   private int genCStoneCounter = 0;
/*  44: 56 */   private long checkTimer = 0L;
/*  45: 64 */   private IRecipe cachedRecipe = null;
/*  46: 65 */   private int prevStack = 0;
/*  47:    */   
/*  48:    */   public TileEntityTransferNodeInventory()
/*  49:    */   {
/*  50: 68 */     super("Inv", new ItemBuffer());this.pr = 1.0F;this.pg = 0.0F;this.pb = 0.0F;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public TileEntityTransferNodeInventory(String txt, INodeBuffer buffer)
/*  54:    */   {
/*  55: 72 */     super(txt, buffer);this.pr = 1.0F;this.pg = 0.0F;this.pb = 0.0F;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static IRecipe findMatchingRecipe(InventoryCrafting inv, World world)
/*  59:    */   {
/*  60: 77 */     for (int i = 0; i < CraftingManager.getInstance().getRecipeList().size(); i++)
/*  61:    */     {
/*  62: 78 */       IRecipe recipe = (IRecipe)CraftingManager.getInstance().getRecipeList().get(i);
/*  63: 80 */       if (recipe.matches(inv, world)) {
/*  64: 81 */         return recipe;
/*  65:    */       }
/*  66:    */     }
/*  67: 84 */     return null;
/*  68:    */   }
/*  69:    */   
/*  70:    */   private static int getFirstExtractableItemStackSlot(IInventory inv, int side)
/*  71:    */   {
/*  72: 88 */     for (int i : XUHelper.getInventorySideSlots(inv, side))
/*  73:    */     {
/*  74: 89 */       ItemStack item = inv.getStackInSlot(i);
/*  75: 90 */       if ((item != null) && (item.stackSize > 0) && (
/*  76: 91 */         (!(inv instanceof ISidedInventory)) || (((ISidedInventory)inv).canExtractItem(i, item, side)))) {
/*  77: 92 */         if (item.getItem().hasContainerItem(item))
/*  78:    */         {
/*  79: 93 */           ItemStack t = item.getItem().getContainerItem(item);
/*  80: 95 */           for (int j : XUHelper.getInventorySideSlots(inv, side)) {
/*  81: 96 */             if (((j != i) && (inv.getStackInSlot(j) == null)) || ((j == i) && (item.stackSize == 1) && 
/*  82: 97 */               (inv.isItemValidForSlot(j, t)) && (
/*  83: 98 */               (!(inv instanceof ISidedInventory)) || (((ISidedInventory)inv).canInsertItem(i, t, side))))) {
/*  84: 99 */               return i;
/*  85:    */             }
/*  86:    */           }
/*  87:    */         }
/*  88:    */         else
/*  89:    */         {
/*  90:105 */           return i;
/*  91:    */         }
/*  92:    */       }
/*  93:    */     }
/*  94:109 */     return -1;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void readFromNBT(NBTTagCompound tag)
/*  98:    */   {
/*  99:117 */     super.readFromNBT(tag);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void writeToNBT(NBTTagCompound tag)
/* 103:    */   {
/* 104:125 */     super.writeToNBT(tag);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void processBuffer()
/* 108:    */   {
/* 109:136 */     if ((this.worldObj != null) && (!this.worldObj.isClient))
/* 110:    */     {
/* 111:137 */       if (this.coolDown > 0) {
/* 112:138 */         this.coolDown -= this.stepCoolDown;
/* 113:    */       }
/* 114:141 */       if (checkRedstone()) {
/* 115:142 */         return;
/* 116:    */       }
/* 117:145 */       startDelayMarkDirty();
/* 118:147 */       while (this.coolDown <= 0)
/* 119:    */       {
/* 120:148 */         this.coolDown += baseMaxCoolDown;
/* 121:149 */         if (handleInventories()) {
/* 122:150 */           advPipeSearch();
/* 123:    */         }
/* 124:153 */         loadbuffer();
/* 125:    */       }
/* 126:156 */       finishMarkDirty();
/* 127:    */     }
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void loadbuffer()
/* 131:    */   {
/* 132:161 */     if ((this.buffer.getBuffer() != null) && (((ItemStack)this.buffer.getBuffer()).stackSize >= ((ItemStack)this.buffer.getBuffer()).getMaxStackSize())) {
/* 133:162 */       return;
/* 134:    */     }
/* 135:165 */     int dir = getBlockMetadata() % 6;
/* 136:166 */     IInventory inv = TNHelper.getInventory(this.worldObj.getTileEntity(this.xCoord + net.minecraft.util.Facing.offsetsXForSide[dir], this.yCoord + net.minecraft.util.Facing.offsetsYForSide[dir], this.zCoord + net.minecraft.util.Facing.offsetsZForSide[dir]));
/* 137:169 */     if (inv != null)
/* 138:    */     {
/* 139:170 */       if ((inv instanceof ISidedInventory))
/* 140:    */       {
/* 141:171 */         dir = net.minecraft.util.Facing.oppositeSide[dir];
/* 142:172 */         ISidedInventory invs = (ISidedInventory)inv;
/* 143:173 */         int[] aint = invs.getAccessibleSlotsFromSide(dir);
/* 144:175 */         for (int i = 0; (i < aint.length) && ((this.buffer.getBuffer() == null) || (((ItemStack)this.buffer.getBuffer()).stackSize < ((ItemStack)this.buffer.getBuffer()).getMaxStackSize())); i++)
/* 145:    */         {
/* 146:177 */           ItemStack itemstack = invs.getStackInSlot(aint[i]);
/* 147:179 */           if ((itemstack != null) && (itemstack.stackSize > 0) && ((this.buffer.getBuffer() == null) || (XUHelper.canItemsStack((ItemStack)this.buffer.getBuffer(), itemstack, false, true))) && 
/* 148:180 */             (invs.canExtractItem(aint[i], itemstack, dir)))
/* 149:    */           {
/* 150:181 */             ItemStack itemstack1 = itemstack.copy();
/* 151:    */             ItemStack itemstack2;
/* 152:    */             ItemStack itemstack2;
/* 153:184 */             if (upgradeNo(3) == 0) {
/* 154:185 */               itemstack2 = XUHelper.invInsert(this, invs.decrStackSize(aint[i], 1), -1);
/* 155:    */             } else {
/* 156:187 */               itemstack2 = XUHelper.invInsert(this, invs.getStackInSlot(aint[i]), -1);
/* 157:    */             }
/* 158:190 */             if (upgradeNo(3) == 0)
/* 159:    */             {
/* 160:191 */               if (itemstack2 != null) {
/* 161:192 */                 inv.setInventorySlotContents(aint[i], itemstack1);
/* 162:    */               } else {
/* 163:194 */                 inv.onInventoryChanged();
/* 164:    */               }
/* 165:    */             }
/* 166:    */             else {
/* 167:198 */               inv.setInventorySlotContents(aint[i], itemstack2);
/* 168:    */             }
/* 169:201 */             inv.onInventoryChanged();
/* 170:    */           }
/* 171:    */         }
/* 172:    */       }
/* 173:    */       else
/* 174:    */       {
/* 175:206 */         int j = inv.getSizeInventory();
/* 176:208 */         for (int k = 0; (k < j) && ((this.buffer.getBuffer() == null) || (((ItemStack)this.buffer.getBuffer()).stackSize < ((ItemStack)this.buffer.getBuffer()).getMaxStackSize())); k++)
/* 177:    */         {
/* 178:209 */           ItemStack itemstack = inv.getStackInSlot(k);
/* 179:211 */           if ((itemstack != null) && ((this.buffer.getBuffer() == null) || (XUHelper.canItemsStack((ItemStack)this.buffer.getBuffer(), itemstack, false, true))))
/* 180:    */           {
/* 181:212 */             ItemStack itemstack1 = itemstack.copy();
/* 182:    */             ItemStack itemstack2;
/* 183:    */             ItemStack itemstack2;
/* 184:215 */             if (upgradeNo(3) == 0) {
/* 185:216 */               itemstack2 = XUHelper.invInsert(this, inv.decrStackSize(k, 1), -1);
/* 186:    */             } else {
/* 187:218 */               itemstack2 = XUHelper.invInsert(this, inv.getStackInSlot(k), -1);
/* 188:    */             }
/* 189:221 */             if ((itemstack2 != null) && (itemstack2.stackSize == 0)) {
/* 190:222 */               itemstack2 = null;
/* 191:    */             }
/* 192:225 */             if (upgradeNo(3) == 0)
/* 193:    */             {
/* 194:226 */               if (itemstack2 != null) {
/* 195:227 */                 inv.setInventorySlotContents(k, itemstack1);
/* 196:    */               } else {
/* 197:229 */                 inv.onInventoryChanged();
/* 198:    */               }
/* 199:    */             }
/* 200:    */             else {
/* 201:233 */               inv.setInventorySlotContents(k, itemstack2);
/* 202:    */             }
/* 203:236 */             inv.onInventoryChanged();
/* 204:    */           }
/* 205:    */         }
/* 206:    */       }
/* 207:    */     }
/* 208:240 */     else if (upgradeNo(2) > 0)
/* 209:    */     {
/* 210:241 */       if (genCobble()) {
/* 211:242 */         return;
/* 212:    */       }
/* 213:243 */       if (doCraft()) {
/* 214:244 */         return;
/* 215:    */       }
/* 216:245 */       suckItems();
/* 217:    */     }
/* 218:    */   }
/* 219:    */   
/* 220:249 */   private boolean delay = false;
/* 221:250 */   private boolean isDirty = false;
/* 222:    */   
/* 223:    */   public void startDelayMarkDirty()
/* 224:    */   {
/* 225:253 */     if (this.delay) {
/* 226:254 */       throw new RuntimeException("Tile Entity to be marked for delayMarkDirty is already marked as such");
/* 227:    */     }
/* 228:255 */     this.delay = true;
/* 229:256 */     this.isDirty = false;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void finishMarkDirty()
/* 233:    */   {
/* 234:260 */     if (this.isDirty) {
/* 235:261 */       super.onInventoryChanged();
/* 236:    */     }
/* 237:262 */     this.delay = false;
/* 238:263 */     this.isDirty = false;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void onInventoryChanged()
/* 242:    */   {
/* 243:269 */     if (!this.delay)
/* 244:    */     {
/* 245:270 */       this.isDirty = false;
/* 246:271 */       super.onInventoryChanged();
/* 247:    */     }
/* 248:    */     else
/* 249:    */     {
/* 250:273 */       this.isDirty = true;
/* 251:    */     }
/* 252:    */   }
/* 253:    */   
/* 254:    */   private void suckItems()
/* 255:    */   {
/* 256:278 */     if ((this.buffer.getBuffer() == null) || (((ItemStack)this.buffer.getBuffer()).stackSize < ((ItemStack)this.buffer.getBuffer()).getMaxStackSize()))
/* 257:    */     {
/* 258:279 */       ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() % 6);
/* 259:    */       
/* 260:281 */       double r = Math.log(upgradeNo(2)) / Math.log(2.0D);
/* 261:283 */       if (r > 3.5D) {
/* 262:284 */         r = 3.5D;
/* 263:    */       }
/* 264:286 */       for (Object o : this.worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 1, this.zCoord + 1).offset(dir.offsetX * (1.0D + r), dir.offsetY * (1.0D + r), dir.offsetZ * (1.0D + r)).expand(r, r, r)))
/* 265:    */       {
/* 266:287 */         EntityItem item = (EntityItem)o;
/* 267:288 */         ItemStack itemstack = item.getEntityItem();
/* 268:290 */         if ((item.isEntityAlive()) && (itemstack != null) && ((this.buffer.getBuffer() == null) || (XUHelper.canItemsStack((ItemStack)this.buffer.getBuffer(), itemstack, false, true))))
/* 269:    */         {
/* 270:291 */           ItemStack itemstack1 = itemstack.copy();
/* 271:293 */           if (upgradeNo(3) == 0) {
/* 272:294 */             itemstack1.stackSize = 1;
/* 273:    */           }
/* 274:296 */           int n = itemstack1.stackSize;
/* 275:    */           
/* 276:298 */           itemstack1 = XUHelper.invInsert(this, itemstack1, -1);
/* 277:300 */           if (itemstack1 != null) {
/* 278:301 */             n -= itemstack1.stackSize;
/* 279:    */           }
/* 280:304 */           if (n > 0)
/* 281:    */           {
/* 282:305 */             itemstack.stackSize -= n;
/* 283:307 */             if (itemstack.stackSize > 0) {
/* 284:308 */               item.setEntityItemStack(itemstack);
/* 285:    */             } else {
/* 286:310 */               item.setDead();
/* 287:    */             }
/* 288:312 */             if (upgradeNo(3) == 0) {
/* 289:313 */               return;
/* 290:    */             }
/* 291:    */           }
/* 292:    */         }
/* 293:    */       }
/* 294:    */     }
/* 295:    */   }
/* 296:    */   
/* 297:    */   private boolean doCraft()
/* 298:    */   {
/* 299:322 */     if ((this.buffer.getBuffer() == null) || (((ItemStack)this.buffer.getBuffer()).stackSize < ((ItemStack)this.buffer.getBuffer()).getMaxStackSize()))
/* 300:    */     {
/* 301:323 */       ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() % 6);
/* 302:    */       
/* 303:325 */       boolean craft = this.worldObj.getBlock(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ) == Blocks.crafting_table;
/* 304:327 */       if (!craft) {
/* 305:328 */         return false;
/* 306:    */       }
/* 307:330 */       ForgeDirection dirX = orthX[dir.ordinal()];
/* 308:331 */       ForgeDirection dirY = orthY[dir.ordinal()];
/* 309:    */       
/* 310:333 */       int[] slots = new int[9];
/* 311:334 */       IInventory[] inventories = new IInventory[9];
/* 312:    */       
/* 313:336 */       boolean isEmpty = true;
/* 314:338 */       for (int dx = -1; dx <= 1; dx++) {
/* 315:339 */         for (int dy = -1; dy <= 1; dy++)
/* 316:    */         {
/* 317:340 */           TileEntity tile = this.worldObj.getTileEntity(this.xCoord + dir.offsetX * 2 + dirX.offsetX * dx + dirY.offsetX * dy, this.yCoord + dir.offsetY * 2 + dirX.offsetY * dx + dirY.offsetY * dy, this.zCoord + dir.offsetZ * 2 + dirX.offsetZ * dx + dirY.offsetZ * dy);
/* 318:341 */           int j = dx + 1 + 3 * (-dy + 1);
/* 319:342 */           boolean a = tile instanceof IInventory;
/* 320:343 */           boolean b = (a) || ((tile instanceof IFluidHandler));
/* 321:344 */           if (b)
/* 322:    */           {
/* 323:345 */             if (a) {
/* 324:346 */               inventories[j] = ((IInventory)tile);
/* 325:    */             } else {
/* 326:348 */               inventories[j] = new LiquidInventory((IFluidHandler)tile, dir.getOpposite());
/* 327:    */             }
/* 328:350 */             int i = getFirstExtractableItemStackSlot(inventories[j], dir.getOpposite().ordinal());
/* 329:    */             
/* 330:352 */             slots[j] = i;
/* 331:353 */             if (i >= 0)
/* 332:    */             {
/* 333:354 */               ItemStack item = inventories[j].getStackInSlot(i);
/* 334:    */               
/* 335:356 */               crafting.setInventorySlotContents(j, item.copy());
/* 336:357 */               isEmpty = false;
/* 337:    */             }
/* 338:    */             else
/* 339:    */             {
/* 340:359 */               crafting.setInventorySlotContents(j, null);
/* 341:    */             }
/* 342:    */           }
/* 343:    */           else
/* 344:    */           {
/* 345:361 */             inventories[j] = null;
/* 346:362 */             crafting.setInventorySlotContents(j, null);
/* 347:    */           }
/* 348:    */         }
/* 349:    */       }
/* 350:367 */       if (isEmpty) {
/* 351:368 */         return true;
/* 352:    */       }
/* 353:370 */       if ((this.cachedRecipe == null) || (!this.cachedRecipe.matches(crafting, this.worldObj)) || (this.cachedRecipe.getCraftingResult(crafting) == null))
/* 354:    */       {
/* 355:371 */         int p = crafting.hashCode();
/* 356:372 */         if ((p == this.prevStack) && (this.prevStack != 0) && 
/* 357:373 */           (this.rand.nextInt(10) > 0)) {
/* 358:374 */           return true;
/* 359:    */         }
/* 360:377 */         this.prevStack = p;
/* 361:    */         
/* 362:379 */         IRecipe r = findMatchingRecipe(crafting, this.worldObj);
/* 363:380 */         if ((r == null) || (r.getCraftingResult(crafting) == null) || (!isItemValidForSlot(0, r.getCraftingResult(crafting)))) {
/* 364:381 */           return true;
/* 365:    */         }
/* 366:382 */         this.cachedRecipe = r;
/* 367:    */       }
/* 368:385 */       ItemStack stack = this.cachedRecipe.getCraftingResult(crafting);
/* 369:    */       
/* 370:387 */       this.prevStack = 0;
/* 371:389 */       if (this.buffer.getBuffer() != null)
/* 372:    */       {
/* 373:390 */         if (!XUHelper.canItemsStack(stack, (ItemStack)this.buffer.getBuffer(), false, true, false)) {
/* 374:391 */           return true;
/* 375:    */         }
/* 376:393 */         if (stack.stackSize + ((ItemStack)this.buffer.getBuffer()).stackSize > stack.getMaxStackSize()) {
/* 377:394 */           return true;
/* 378:    */         }
/* 379:    */       }
/* 380:397 */       if (!isItemValidForSlot(0, stack)) {
/* 381:398 */         return true;
/* 382:    */       }
/* 383:400 */       ItemStack[] items = new ItemStack[9];
/* 384:402 */       for (int i = 0; i < 9; i++) {
/* 385:403 */         if ((inventories[i] != null) && (slots[i] >= 0))
/* 386:    */         {
/* 387:404 */           ItemStack c = inventories[i].getStackInSlot(slots[i]);
/* 388:    */           
/* 389:406 */           boolean flag = false;
/* 390:408 */           if ((c == null) || (!XUHelper.canItemsStack(crafting.getStackInSlot(i), c))) {
/* 391:409 */             flag = true;
/* 392:    */           }
/* 393:412 */           if (!flag)
/* 394:    */           {
/* 395:413 */             items[i] = inventories[i].decrStackSize(slots[i], 1);
/* 396:414 */             if ((items[i] != null) && (items[i].stackSize != 1)) {
/* 397:415 */               flag = true;
/* 398:    */             }
/* 399:    */           }
/* 400:418 */           if (flag)
/* 401:    */           {
/* 402:419 */             for (int j = 0; j <= i; j++) {
/* 403:420 */               if ((items[j] != null) && (inventories[j] != null))
/* 404:    */               {
/* 405:421 */                 items[j] = XUHelper.invInsert(inventories[j], items[j], dir.getOpposite().ordinal());
/* 406:422 */                 if (items[j] != null) {
/* 407:423 */                   XUHelper.dropItem(getWorldObj(), getNodeX(), getNodeY(), getNodeZ(), items[j]);
/* 408:    */                 }
/* 409:    */               }
/* 410:    */             }
/* 411:426 */             return true;
/* 412:    */           }
/* 413:430 */           if (c.getItem().hasContainerItem(c))
/* 414:    */           {
/* 415:431 */             ItemStack t = c.getItem().getContainerItem(c);
/* 416:433 */             if ((t != null) && ((!t.isItemStackDamageable()) || (t.getItemDamage() <= t.getMaxDamage()))) {
/* 417:434 */               XUHelper.invInsert(inventories[i], t, dir.getOpposite().ordinal());
/* 418:    */             }
/* 419:    */           }
/* 420:    */         }
/* 421:    */       }
/* 422:439 */       XUHelper.invInsert(this, stack, -1);
/* 423:    */     }
/* 424:441 */     return true;
/* 425:    */   }
/* 426:    */   
/* 427:    */   private boolean genCobble()
/* 428:    */   {
/* 429:445 */     if (ExtraUtils.disableCobblegen) {
/* 430:446 */       return false;
/* 431:    */     }
/* 432:448 */     if ((this.buffer.getBuffer() == null) || ((((ItemStack)this.buffer.getBuffer()).getItem() == Item.getItemFromBlock(Blocks.cobblestone)) && (((ItemStack)this.buffer.getBuffer()).stackSize < 64)))
/* 433:    */     {
/* 434:449 */       int dir = getBlockMetadata() % 6;
/* 435:450 */       this.genCStoneCounter = ((this.genCStoneCounter + 1) % (1 + upgradeNo(0)));
/* 436:452 */       if (this.genCStoneCounter != 0) {
/* 437:453 */         return false;
/* 438:    */       }
/* 439:456 */       if (this.worldObj.getTotalWorldTime() - this.checkTimer > 100L)
/* 440:    */       {
/* 441:457 */         this.checkTimer = this.worldObj.getTotalWorldTime();
/* 442:458 */         this.hasCStoneGen = false;
/* 443:460 */         if (this.worldObj.getBlock(this.xCoord + net.minecraft.util.Facing.offsetsXForSide[dir], this.yCoord + net.minecraft.util.Facing.offsetsYForSide[dir], this.zCoord + net.minecraft.util.Facing.offsetsZForSide[dir]) == Blocks.cobblestone)
/* 444:    */         {
/* 445:461 */           boolean hasLava = false;
/* 446:462 */           boolean hasWater = false;
/* 447:464 */           for (int i = 2; ((!hasWater) || (!hasLava)) && (i < 6); i++)
/* 448:    */           {
/* 449:465 */             hasWater |= this.worldObj.getBlock(this.xCoord + net.minecraft.util.Facing.offsetsXForSide[dir] + net.minecraft.util.Facing.offsetsXForSide[i], this.yCoord + net.minecraft.util.Facing.offsetsYForSide[dir], this.zCoord + net.minecraft.util.Facing.offsetsZForSide[dir] + net.minecraft.util.Facing.offsetsZForSide[i]).getMaterial() == Material.water;
/* 450:    */             
/* 451:    */ 
/* 452:468 */             hasLava |= this.worldObj.getBlock(this.xCoord + net.minecraft.util.Facing.offsetsXForSide[dir] + net.minecraft.util.Facing.offsetsXForSide[i], this.yCoord + net.minecraft.util.Facing.offsetsYForSide[dir], this.zCoord + net.minecraft.util.Facing.offsetsZForSide[dir] + net.minecraft.util.Facing.offsetsZForSide[i]).getMaterial() == Material.lava;
/* 453:    */           }
/* 454:473 */           if ((hasWater) && (hasLava)) {
/* 455:474 */             this.hasCStoneGen = true;
/* 456:    */           }
/* 457:    */         }
/* 458:    */       }
/* 459:479 */       if (this.hasCStoneGen)
/* 460:    */       {
/* 461:480 */         if (this.buffer.getBuffer() == null)
/* 462:    */         {
/* 463:481 */           this.buffer.setBuffer(new ItemStack(Blocks.cobblestone, upgradeNo(2)));
/* 464:    */         }
/* 465:    */         else
/* 466:    */         {
/* 467:483 */           ((ItemStack)this.buffer.getBuffer()).stackSize += 1 + upgradeNo(2);
/* 468:485 */           if (((ItemStack)this.buffer.getBuffer()).stackSize > 64) {
/* 469:486 */             ((ItemStack)this.buffer.getBuffer()).stackSize = 64;
/* 470:    */           }
/* 471:    */         }
/* 472:489 */         return true;
/* 473:    */       }
/* 474:    */     }
/* 475:492 */     return false;
/* 476:    */   }
/* 477:    */   
/* 478:    */   public int getSizeInventory()
/* 479:    */   {
/* 480:498 */     return 1;
/* 481:    */   }
/* 482:    */   
/* 483:    */   public ItemStack getStackInSlot(int i)
/* 484:    */   {
/* 485:503 */     if (i == 0) {
/* 486:504 */       return (ItemStack)this.buffer.getBuffer();
/* 487:    */     }
/* 488:507 */     return null;
/* 489:    */   }
/* 490:    */   
/* 491:    */   public ItemStack decrStackSize(int i, int j)
/* 492:    */   {
/* 493:512 */     if (i != 0) {
/* 494:513 */       return null;
/* 495:    */     }
/* 496:516 */     if (this.buffer.getBuffer() != null)
/* 497:    */     {
/* 498:519 */       if (((ItemStack)this.buffer.getBuffer()).stackSize <= j)
/* 499:    */       {
/* 500:520 */         ItemStack itemstack = (ItemStack)this.buffer.getBuffer();
/* 501:521 */         this.buffer.setBuffer(null);
/* 502:522 */         onInventoryChanged();
/* 503:523 */         return itemstack;
/* 504:    */       }
/* 505:525 */       ItemStack itemstack = ((ItemStack)this.buffer.getBuffer()).splitStack(j);
/* 506:527 */       if (((ItemStack)this.buffer.getBuffer()).stackSize == 0) {
/* 507:528 */         this.buffer.setBuffer(null);
/* 508:    */       }
/* 509:531 */       onInventoryChanged();
/* 510:532 */       return itemstack;
/* 511:    */     }
/* 512:535 */     return null;
/* 513:    */   }
/* 514:    */   
/* 515:    */   public ItemStack getStackInSlotOnClosing(int i)
/* 516:    */   {
/* 517:541 */     if (i != 0) {
/* 518:542 */       return null;
/* 519:    */     }
/* 520:545 */     if (this.buffer.getBuffer() != null)
/* 521:    */     {
/* 522:546 */       ItemStack itemstack = (ItemStack)this.buffer.getBuffer();
/* 523:547 */       this.buffer.setBuffer(null);
/* 524:548 */       return itemstack;
/* 525:    */     }
/* 526:550 */     return null;
/* 527:    */   }
/* 528:    */   
/* 529:    */   public void setInventorySlotContents(int i, ItemStack itemstack)
/* 530:    */   {
/* 531:556 */     this.buffer.setBuffer(itemstack);
/* 532:558 */     if ((itemstack != null) && (itemstack.stackSize > getInventoryStackLimit())) {
/* 533:559 */       itemstack.stackSize = getInventoryStackLimit();
/* 534:    */     }
/* 535:562 */     onInventoryChanged();
/* 536:    */   }
/* 537:    */   
/* 538:    */   public String getInventoryName()
/* 539:    */   {
/* 540:567 */     return "gui.transferNode";
/* 541:    */   }
/* 542:    */   
/* 543:    */   public boolean isInventoryNameLocalized()
/* 544:    */   {
/* 545:572 */     return false;
/* 546:    */   }
/* 547:    */   
/* 548:    */   public int getInventoryStackLimit()
/* 549:    */   {
/* 550:577 */     return 64;
/* 551:    */   }
/* 552:    */   
/* 553:    */   public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
/* 554:    */   {
/* 555:582 */     return par1EntityPlayer.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
/* 556:    */   }
/* 557:    */   
/* 558:    */   public void openInventory() {}
/* 559:    */   
/* 560:    */   public void closeInventory() {}
/* 561:    */   
/* 562:    */   public boolean isItemValidForSlot(int i, ItemStack itemstack)
/* 563:    */   {
/* 564:595 */     for (int j = 0; j < this.upgrades.getSizeInventory(); j++)
/* 565:    */     {
/* 566:596 */       ItemStack upgrade = this.upgrades.getStackInSlot(j);
/* 567:597 */       if ((upgrade != null) && (ItemNodeUpgrade.isFilter(upgrade)) && 
/* 568:598 */         (!ItemNodeUpgrade.matchesFilterItem(itemstack, upgrade))) {
/* 569:599 */         return false;
/* 570:    */       }
/* 571:    */     }
/* 572:604 */     return true;
/* 573:    */   }
/* 574:    */   
/* 575:    */   public int[] getAccessibleSlotsFromSide(int j)
/* 576:    */   {
/* 577:609 */     if ((j < 0) || (j >= 6) || (j == getBlockMetadata() % 6)) {
/* 578:610 */       return contents;
/* 579:    */     }
/* 580:612 */     return nullcontents;
/* 581:    */   }
/* 582:    */   
/* 583:    */   public boolean canInsertItem(int i, ItemStack itemstack, int j)
/* 584:    */   {
/* 585:619 */     return ((j < 0) || (j >= 6) || (j == getBlockMetadata() % 6)) && (isItemValidForSlot(i, itemstack));
/* 586:    */   }
/* 587:    */   
/* 588:    */   public boolean canExtractItem(int i, ItemStack itemstack, int j)
/* 589:    */   {
/* 590:625 */     return false;
/* 591:    */   }
/* 592:    */   
/* 593:    */   public TileEntityTransferNodeInventory getNode()
/* 594:    */   {
/* 595:630 */     return this;
/* 596:    */   }
/* 597:    */   
/* 598:    */   public BoxModel getModel(ForgeDirection dir)
/* 599:    */   {
/* 600:635 */     BoxModel boxes = new BoxModel();
/* 601:636 */     boxes.add(new Box(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.0625F, 0.9375F).rotateToSide(dir).setTextureSides(new Object[] { Integer.valueOf(dir.ordinal()), BlockTransferNode.nodeBase }));
/* 602:637 */     boxes.add(new Box(0.1875F, 0.0625F, 0.1875F, 0.8125F, 0.25F, 0.8125F).rotateToSide(dir));
/* 603:638 */     boxes.add(new Box(0.3125F, 0.25F, 0.3125F, 0.6875F, 0.375F, 0.6875F).rotateToSide(dir));
/* 604:639 */     boxes.add(new Box(0.375F, 0.25F, 0.375F, 0.625F, 0.375F, 0.625F).rotateToSide(dir).setTexture(BlockTransferNode.nodeBase).setAllSideInvisible().setSideInvisible(new Object[] { Integer.valueOf(dir.getOpposite().ordinal()), Boolean.valueOf(false) }));
/* 605:    */     
/* 606:641 */     return boxes;
/* 607:    */   }
/* 608:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNodeInventory
 * JD-Core Version:    0.7.0.1
 */