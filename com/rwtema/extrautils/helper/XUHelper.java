/*   1:    */ package com.rwtema.extrautils.helper;
/*   2:    */ 
/*   3:    */ import com.mojang.authlib.GameProfile;
/*   4:    */ import com.rwtema.extrautils.LogHelper;
/*   5:    */ import com.rwtema.extrautils.core.CastIterator;
/*   6:    */ import com.rwtema.extrautils.tileentity.transfernodes.InvHelper;
/*   7:    */ import gnu.trove.iterator.TIntIterator;
/*   8:    */ import gnu.trove.list.linked.TIntLinkedList;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Collection;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Locale;
/*  13:    */ import java.util.Map;
/*  14:    */ import java.util.Random;
/*  15:    */ import java.util.UUID;
/*  16:    */ import net.minecraft.block.Block;
/*  17:    */ import net.minecraft.enchantment.Enchantment;
/*  18:    */ import net.minecraft.entity.Entity;
/*  19:    */ import net.minecraft.entity.item.EntityItem;
/*  20:    */ import net.minecraft.entity.player.EntityPlayer;
/*  21:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*  22:    */ import net.minecraft.init.Blocks;
/*  23:    */ import net.minecraft.init.Items;
/*  24:    */ import net.minecraft.inventory.IInventory;
/*  25:    */ import net.minecraft.inventory.ISidedInventory;
/*  26:    */ import net.minecraft.inventory.InventoryBasic;
/*  27:    */ import net.minecraft.item.Item;
/*  28:    */ import net.minecraft.item.ItemStack;
/*  29:    */ import net.minecraft.nbt.NBTTagCompound;
/*  30:    */ import net.minecraft.nbt.NBTTagList;
/*  31:    */ import net.minecraft.nbt.NBTTagString;
/*  32:    */ import net.minecraft.network.NetHandlerPlayServer;
/*  33:    */ import net.minecraft.network.NetworkManager;
/*  34:    */ import net.minecraft.server.MinecraftServer;
/*  35:    */ import net.minecraft.server.management.ServerConfigurationManager;
/*  36:    */ import net.minecraft.tileentity.TileEntity;
/*  37:    */ import net.minecraft.world.World;
/*  38:    */ import net.minecraft.world.chunk.Chunk;
/*  39:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  40:    */ import net.minecraftforge.fluids.Fluid;
/*  41:    */ import net.minecraftforge.fluids.FluidRegistry;
/*  42:    */ import net.minecraftforge.fluids.FluidStack;
/*  43:    */ import net.minecraftforge.fluids.IFluidBlock;
/*  44:    */ import net.minecraftforge.oredict.OreDictionary;
/*  45:    */ 
/*  46:    */ public class XUHelper
/*  47:    */ {
/*  48: 40 */   public static boolean deObf = false;
/*  49: 41 */   public static final Random rand = XURandom.getInstance();
/*  50: 42 */   public static final String[] dyes = { "dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite" };
/*  51: 47 */   public static final int[] dyeCols = new int[16];
/*  52:    */   private static long timer;
/*  53:    */   static final ArrayList<Class<?>> wrenchClazzes;
/*  54:    */   
/*  55:    */   static
/*  56:    */   {
/*  57: 48 */     for (int i = 0; i < 16; i++)
/*  58:    */     {
/*  59: 49 */       float[] cols = net.minecraft.entity.passive.EntitySheep.fleeceColorTable[i];
/*  60: 50 */       int r = (int)(cols[0] * 255.0F);
/*  61: 51 */       int g = (int)(cols[1] * 255.0F);
/*  62: 52 */       int b = (int)(cols[2] * 255.0F);
/*  63: 53 */       dyeCols[i] = (r << 16 | g << 8 | b);
/*  64:    */     }
/*  65: 58 */     timer = 0L;
/*  66:    */     
/*  67:    */ 
/*  68:    */ 
/*  69:    */ 
/*  70:    */ 
/*  71: 64 */     String[] wrenchClassNames = { "buildcraft.api.tools.IToolWrench", "cofh.api.item.IToolHammer", "powercrystals.minefactoryreloaded.api.IMFRHammer", "appeng.api.implementations.items.IAEWrench", "crazypants.enderio.api.tool.ITool" };
/*  72:    */     
/*  73:    */ 
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79: 72 */     wrenchClazzes = new ArrayList();
/*  80: 73 */     for (String wrenchClassName : wrenchClassNames) {
/*  81:    */       try
/*  82:    */       {
/*  83: 75 */         wrenchClazzes.add(Class.forName(wrenchClassName));
/*  84: 76 */         LogHelper.fine("Detected wrench class: " + wrenchClassName, new Object[0]);
/*  85:    */       }
/*  86:    */       catch (ClassNotFoundException ignore) {}
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   public static boolean isWrench(ItemStack itemstack)
/*  91:    */   {
/*  92: 84 */     if (itemstack == null) {
/*  93: 84 */       return false;
/*  94:    */     }
/*  95: 85 */     Item item = itemstack.getItem();
/*  96: 86 */     if (item == null) {
/*  97: 86 */       return false;
/*  98:    */     }
/*  99: 87 */     if (item == Items.stick) {
/* 100: 87 */       return true;
/* 101:    */     }
/* 102: 88 */     for (Class<?> wrenchClazz : wrenchClazzes) {
/* 103: 89 */       if (wrenchClazz.isAssignableFrom(item.getClass())) {
/* 104: 90 */         return true;
/* 105:    */       }
/* 106:    */     }
/* 107: 93 */     return false;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public static FluidStack drainBlock(World world, int x, int y, int z, boolean doDrain)
/* 111:    */   {
/* 112: 97 */     Block id = world.getBlock(x, y, z);
/* 113: 99 */     if ((id == Blocks.flowing_lava) || (id == Blocks.lava))
/* 114:    */     {
/* 115:    */       FluidStack liquid;
/* 116:    */       FluidStack liquid;
/* 117:102 */       if (world.getBlockMetadata(x, y, z) == 0) {
/* 118:103 */         liquid = new FluidStack(FluidRegistry.LAVA, 1000);
/* 119:    */       } else {
/* 120:105 */         liquid = new FluidStack(FluidRegistry.LAVA, 0);
/* 121:    */       }
/* 122:108 */       if (doDrain) {
/* 123:109 */         world.setBlockToAir(x, y, z);
/* 124:    */       }
/* 125:112 */       return liquid;
/* 126:    */     }
/* 127:113 */     if ((id == Blocks.flowing_water) || (id == Blocks.water))
/* 128:    */     {
/* 129:    */       FluidStack liquid;
/* 130:    */       FluidStack liquid;
/* 131:116 */       if (world.getBlockMetadata(x, y, z) == 0) {
/* 132:117 */         liquid = new FluidStack(FluidRegistry.WATER, 1000);
/* 133:    */       } else {
/* 134:119 */         liquid = new FluidStack(FluidRegistry.WATER, 0);
/* 135:    */       }
/* 136:122 */       if (doDrain) {
/* 137:123 */         world.setBlockToAir(x, y, z);
/* 138:    */       }
/* 139:126 */       return liquid;
/* 140:    */     }
/* 141:127 */     if ((id instanceof IFluidBlock))
/* 142:    */     {
/* 143:128 */       IFluidBlock fluidBlock = (IFluidBlock)id;
/* 144:130 */       if ((fluidBlock.getFluid() != null) && (fluidBlock.canDrain(world, x, y, z))) {
/* 145:131 */         return fluidBlock.drain(world, x, y, z, doDrain);
/* 146:    */       }
/* 147:    */     }
/* 148:135 */     return null;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public static String getAnvilName(ItemStack item)
/* 152:    */   {
/* 153:139 */     String s = "";
/* 154:141 */     if ((item != null) && (item.getTagCompound() != null) && (item.getTagCompound().hasKey("display")))
/* 155:    */     {
/* 156:142 */       NBTTagCompound nbttagcompound = item.getTagCompound().getCompoundTag("display");
/* 157:144 */       if (nbttagcompound.hasKey("Name")) {
/* 158:145 */         s = nbttagcompound.getString("Name");
/* 159:    */       }
/* 160:    */     }
/* 161:149 */     return s;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public static boolean hasPersistantNBT(Entity entity)
/* 165:    */   {
/* 166:153 */     return entity.getEntityData().func_150297_b("PlayerPersisted", 10);
/* 167:    */   }
/* 168:    */   
/* 169:    */   public static NBTTagCompound getPersistantNBT(Entity entity)
/* 170:    */   {
/* 171:157 */     NBTTagCompound t = entity.getEntityData();
/* 172:158 */     if (!t.func_150297_b("PlayerPersisted", 10))
/* 173:    */     {
/* 174:159 */       NBTTagCompound tag = new NBTTagCompound();
/* 175:160 */       t.setTag("PlayerPersisted", tag);
/* 176:161 */       return tag;
/* 177:    */     }
/* 178:163 */     return t.getCompoundTag("PlayerPersisted");
/* 179:    */   }
/* 180:    */   
/* 181:    */   public static String getPlayerOwner(ItemStack item)
/* 182:    */   {
/* 183:168 */     String s = "";
/* 184:170 */     if ((item != null) && (item.getTagCompound() != null) && (item.getTagCompound().hasKey("XU:owner"))) {
/* 185:171 */       s = item.getTagCompound().getString("XU:owner");
/* 186:    */     }
/* 187:174 */     return s;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public static void setPlayerOwner(ItemStack item, String s)
/* 191:    */   {
/* 192:178 */     if (item != null)
/* 193:    */     {
/* 194:179 */       NBTTagCompound tags = item.getTagCompound();
/* 195:181 */       if (tags == null) {
/* 196:182 */         tags = new NBTTagCompound();
/* 197:    */       }
/* 198:185 */       if ((s == null) || (s.equals("")))
/* 199:    */       {
/* 200:186 */         if (tags.hasKey("XU:owner"))
/* 201:    */         {
/* 202:187 */           tags.removeTag("XU:owner");
/* 203:189 */           if (tags.hasNoTags()) {
/* 204:190 */             item.setTagCompound(null);
/* 205:    */           } else {
/* 206:192 */             item.setTagCompound(tags);
/* 207:    */           }
/* 208:    */         }
/* 209:    */       }
/* 210:    */       else
/* 211:    */       {
/* 212:196 */         tags.setString("XU:owner", s);
/* 213:197 */         item.setTagCompound(tags);
/* 214:    */       }
/* 215:    */     }
/* 216:    */   }
/* 217:    */   
/* 218:    */   public static void resetTimer()
/* 219:    */   {
/* 220:203 */     timer = System.nanoTime();
/* 221:    */   }
/* 222:    */   
/* 223:    */   public static void printTimer(String t)
/* 224:    */   {
/* 225:207 */     LogHelper.debug("time:" + t + " - " + (System.nanoTime() - timer) / 1000000.0D, new Object[0]);
/* 226:208 */     timer = System.nanoTime();
/* 227:    */   }
/* 228:    */   
/* 229:    */   public static int getDyeFromItemStack(ItemStack dye)
/* 230:    */   {
/* 231:212 */     if (dye == null) {
/* 232:213 */       return -1;
/* 233:    */     }
/* 234:216 */     if (dye.getItem() == Items.dye) {
/* 235:217 */       return dye.getItemDamage();
/* 236:    */     }
/* 237:220 */     for (int i = 0; i < 16; i++) {
/* 238:221 */       for (ItemStack target : OreDictionary.getOres(dyes[i])) {
/* 239:222 */         if (OreDictionary.itemMatches(target, dye, false)) {
/* 240:223 */           return i;
/* 241:    */         }
/* 242:    */       }
/* 243:    */     }
/* 244:228 */     return -1;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public static int rndInt(int n)
/* 248:    */   {
/* 249:232 */     if (n <= 0) {
/* 250:233 */       return 0;
/* 251:    */     }
/* 252:235 */     return rand.nextInt(n);
/* 253:    */   }
/* 254:    */   
/* 255:    */   public static String getFluidName(FluidStack fluid)
/* 256:    */   {
/* 257:240 */     if ((fluid == null) || (fluid.getFluid() == null)) {
/* 258:241 */       return "ERROR";
/* 259:    */     }
/* 260:244 */     String s = fluid.getFluid().getLocalizedName(fluid);
/* 261:246 */     if (s == null) {
/* 262:247 */       return "ERROR";
/* 263:    */     }
/* 264:250 */     if (s.equals("")) {
/* 265:251 */       s = "Unnamed liquid";
/* 266:    */     }
/* 267:254 */     if (s.equals(fluid.getFluid().getUnlocalizedName()))
/* 268:    */     {
/* 269:255 */       s = fluid.getFluid().getName();
/* 270:257 */       if (s.equals(s.toLowerCase())) {
/* 271:258 */         s = s.substring(0, 1).toUpperCase() + s.substring(1, s.length());
/* 272:    */       }
/* 273:    */     }
/* 274:262 */     return s;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public static Block safeBlockId(World world, int x, int y, int z)
/* 278:    */   {
/* 279:266 */     return safeBlockId(world, x, y, z, Blocks.air);
/* 280:    */   }
/* 281:    */   
/* 282:    */   public static int[] getSlots(int k)
/* 283:    */   {
/* 284:270 */     int[] slots = new int[k];
/* 285:272 */     for (int i = 0; i < k; i++) {
/* 286:273 */       slots[i] = i;
/* 287:    */     }
/* 288:276 */     return slots;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public static TileEntity safegetTileEntity(World world, int x, int y, int z)
/* 292:    */   {
/* 293:280 */     if (world.blockExists(x, y, z)) {
/* 294:281 */       return world.getTileEntity(x, y, z);
/* 295:    */     }
/* 296:283 */     return null;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public static Block safeBlockId(World world, int x, int y, int z, Block falseReturnValue)
/* 300:    */   {
/* 301:288 */     if (world.blockExists(x, y, z)) {
/* 302:289 */       return world.getBlock(x, y, z);
/* 303:    */     }
/* 304:291 */     return falseReturnValue;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public static boolean canItemsStack(ItemStack a, ItemStack b)
/* 308:    */   {
/* 309:296 */     return canItemsStack(a, b, false, true);
/* 310:    */   }
/* 311:    */   
/* 312:    */   public static ItemStack invInsert(IInventory inv, ItemStack item, int side)
/* 313:    */   {
/* 314:300 */     if ((item != null) && (item.stackSize > 0))
/* 315:    */     {
/* 316:301 */       boolean nonSided = !(inv instanceof ISidedInventory);
/* 317:302 */       int empty = -1;
/* 318:303 */       int filter = -1;
/* 319:304 */       int maxStack = Math.min(item.getMaxStackSize(), inv.getInventoryStackLimit());
/* 320:305 */       filter = maxStack;
/* 321:306 */       boolean flag = false;
/* 322:308 */       for (int i : InvHelper.getSlots(inv, side))
/* 323:    */       {
/* 324:309 */         ItemStack dest = inv.getStackInSlot(i);
/* 325:311 */         if (dest == null)
/* 326:    */         {
/* 327:312 */           if ((empty == -1) && (inv.isItemValidForSlot(i, item)) && ((nonSided) || (((ISidedInventory)inv).canInsertItem(i, item, side)))) {
/* 328:313 */             empty = i;
/* 329:    */           }
/* 330:    */         }
/* 331:316 */         else if ((InvHelper.canStack(item, dest)) && (inv.isItemValidForSlot(i, item)) && ((nonSided) || (((ISidedInventory)inv).canInsertItem(i, item, side))) && 
/* 332:317 */           (maxStack - dest.stackSize > 0) && (filter > 0))
/* 333:    */         {
/* 334:318 */           int l = Math.min(Math.min(item.stackSize, maxStack - dest.stackSize), filter);
/* 335:320 */           if (l > 0)
/* 336:    */           {
/* 337:321 */             dest.stackSize += l;
/* 338:322 */             item.stackSize -= l;
/* 339:323 */             filter -= l;
/* 340:324 */             flag = true;
/* 341:326 */             if (item.stackSize <= 0) {
/* 342:327 */               item = null;
/* 343:    */             } else {
/* 344:331 */               if (filter <= 0) {
/* 345:    */                 break;
/* 346:    */               }
/* 347:    */             }
/* 348:    */           }
/* 349:    */         }
/* 350:    */       }
/* 351:340 */       if ((filter > 0) && (item != null) && (empty != -1) && (inv.isItemValidForSlot(empty, item)) && ((nonSided) || (((ISidedInventory)inv).canInsertItem(empty, item, side))))
/* 352:    */       {
/* 353:341 */         if (filter < item.stackSize)
/* 354:    */         {
/* 355:342 */           inv.setInventorySlotContents(empty, item.splitStack(filter));
/* 356:    */         }
/* 357:    */         else
/* 358:    */         {
/* 359:344 */           inv.setInventorySlotContents(empty, item);
/* 360:345 */           item = null;
/* 361:    */         }
/* 362:348 */         flag = true;
/* 363:    */       }
/* 364:351 */       if (flag) {
/* 365:352 */         inv.onInventoryChanged();
/* 366:    */       }
/* 367:    */     }
/* 368:356 */     return item;
/* 369:    */   }
/* 370:    */   
/* 371:    */   public static ItemStack[] simMassInvInsert(IInventory inv, ItemStack[] items, int side)
/* 372:    */   {
/* 373:360 */     TIntLinkedList resultInd = new TIntLinkedList();
/* 374:361 */     ItemStack[] result = new ItemStack[items.length];
/* 375:362 */     for (int i = 0; i < result.length; i++) {
/* 376:363 */       if ((items[i] != null) && (items[i].stackSize > 0))
/* 377:    */       {
/* 378:364 */         result[i] = items[i].copy();
/* 379:365 */         resultInd.add(i);
/* 380:    */       }
/* 381:    */     }
/* 382:368 */     return simMassInvInsert_do(inv, side, resultInd, result);
/* 383:    */   }
/* 384:    */   
/* 385:    */   public static ItemStack[] simMassInvInsert(IInventory inv, Collection<ItemStack> items, int side)
/* 386:    */   {
/* 387:372 */     TIntLinkedList resultInd = new TIntLinkedList();
/* 388:373 */     ItemStack[] result = new ItemStack[items.size()];
/* 389:374 */     int i = 0;
/* 390:375 */     for (ItemStack item : items)
/* 391:    */     {
/* 392:376 */       if ((item != null) && (item.stackSize > 0))
/* 393:    */       {
/* 394:377 */         result[i] = item.copy();
/* 395:378 */         resultInd.add(i);
/* 396:    */       }
/* 397:380 */       i++;
/* 398:    */     }
/* 399:382 */     return simMassInvInsert_do(inv, side, resultInd, result);
/* 400:    */   }
/* 401:    */   
/* 402:    */   public static ItemStack[] simMassInvInsert_do(IInventory inv, int side, TIntLinkedList resultInd, ItemStack[] result)
/* 403:    */   {
/* 404:386 */     if (resultInd.isEmpty()) {
/* 405:386 */       return null;
/* 406:    */     }
/* 407:388 */     int[] slots = InvHelper.getSlots(inv, side);
/* 408:    */     
/* 409:390 */     ISidedInventory invS = null;
/* 410:391 */     boolean sided = inv instanceof ISidedInventory;
/* 411:392 */     if (sided) {
/* 412:393 */       invS = (ISidedInventory)inv;
/* 413:    */     }
/* 414:396 */     int maxStack = inv.getInventoryStackLimit();
/* 415:    */     
/* 416:398 */     TIntLinkedList emptySlots = new TIntLinkedList();
/* 417:400 */     for (int i : slots)
/* 418:    */     {
/* 419:401 */       ItemStack curStack = inv.getStackInSlot(i);
/* 420:402 */       if (curStack == null)
/* 421:    */       {
/* 422:403 */         emptySlots.add(i);
/* 423:    */       }
/* 424:    */       else
/* 425:    */       {
/* 426:407 */         int m = Math.min(maxStack, curStack.getMaxStackSize()) - curStack.stackSize;
/* 427:409 */         if (m > 0)
/* 428:    */         {
/* 429:411 */           TIntIterator resultIterator = resultInd.iterator();
/* 430:412 */           while (resultIterator.hasNext())
/* 431:    */           {
/* 432:413 */             int j = resultIterator.next();
/* 433:414 */             ItemStack itemStack = result[j];
/* 434:415 */             if ((itemStack != null) && 
/* 435:    */             
/* 436:417 */               (canItemsStack(itemStack, curStack)) && (inv.isItemValidForSlot(i, itemStack)) && ((invS == null) || (invS.canInsertItem(i, itemStack, side))))
/* 437:    */             {
/* 438:423 */               itemStack.stackSize -= m;
/* 439:424 */               if (itemStack.stackSize <= 0)
/* 440:    */               {
/* 441:425 */                 result[j] = null;
/* 442:426 */                 resultIterator.remove();
/* 443:427 */                 if (resultInd.isEmpty()) {
/* 444:427 */                   return null;
/* 445:    */                 }
/* 446:    */               }
/* 447:    */             }
/* 448:    */           }
/* 449:    */         }
/* 450:    */       }
/* 451:    */     }
/* 452:432 */     if (emptySlots.isEmpty()) {
/* 453:432 */       return result;
/* 454:    */     }
/* 455:434 */     TIntIterator slotIterator = emptySlots.iterator();
/* 456:435 */     while (slotIterator.hasNext())
/* 457:    */     {
/* 458:436 */       int i = slotIterator.next();
/* 459:    */       
/* 460:438 */       TIntIterator resultIterator = resultInd.iterator();
/* 461:439 */       while (resultIterator.hasNext())
/* 462:    */       {
/* 463:440 */         int j = resultIterator.next();
/* 464:441 */         ItemStack itemStack = result[j];
/* 465:442 */         if ((itemStack != null) && 
/* 466:    */         
/* 467:444 */           (inv.isItemValidForSlot(i, itemStack)) && ((invS == null) || (invS.canInsertItem(i, itemStack, side))))
/* 468:    */         {
/* 469:448 */           itemStack.stackSize -= maxStack;
/* 470:449 */           if (itemStack.stackSize <= 0)
/* 471:    */           {
/* 472:450 */             result[j] = null;
/* 473:451 */             resultIterator.remove();
/* 474:    */           }
/* 475:    */         }
/* 476:    */       }
/* 477:    */     }
/* 478:456 */     if (resultInd.isEmpty()) {
/* 479:456 */       return null;
/* 480:    */     }
/* 481:458 */     return result;
/* 482:    */   }
/* 483:    */   
/* 484:    */   public static ItemStack simInvInsert(IInventory inv, ItemStack item, int side)
/* 485:    */   {
/* 486:462 */     if ((item == null) || (item.stackSize <= 0)) {
/* 487:463 */       return item;
/* 488:    */     }
/* 489:466 */     item = item.copy();
/* 490:    */     
/* 491:468 */     boolean nonSided = !(inv instanceof ISidedInventory);
/* 492:469 */     int empty = -1;
/* 493:470 */     int filter = -1;
/* 494:471 */     int maxStack = Math.min(item.getMaxStackSize(), inv.getInventoryStackLimit());
/* 495:472 */     filter = maxStack;
/* 496:475 */     for (int i : InvHelper.getSlots(inv, side))
/* 497:    */     {
/* 498:476 */       ItemStack dest = inv.getStackInSlot(i);
/* 499:478 */       if (dest == null)
/* 500:    */       {
/* 501:479 */         if ((empty == -1) && (inv.isItemValidForSlot(i, item)) && ((nonSided) || (((ISidedInventory)inv).canInsertItem(i, item, side)))) {
/* 502:480 */           empty = i;
/* 503:    */         }
/* 504:    */       }
/* 505:483 */       else if ((InvHelper.canStack(item, dest)) && (inv.isItemValidForSlot(i, item)) && ((nonSided) || (((ISidedInventory)inv).canInsertItem(i, item, side))) && 
/* 506:484 */         (maxStack - dest.stackSize > 0) && (filter > 0))
/* 507:    */       {
/* 508:487 */         int l = Math.min(Math.min(item.stackSize, maxStack - dest.stackSize), filter);
/* 509:489 */         if (l > 0)
/* 510:    */         {
/* 511:490 */           item.stackSize -= l;
/* 512:491 */           filter -= l;
/* 513:493 */           if (item.stackSize <= 0) {
/* 514:494 */             item = null;
/* 515:    */           } else {
/* 516:498 */             if (filter <= 0) {
/* 517:    */               break;
/* 518:    */             }
/* 519:    */           }
/* 520:    */         }
/* 521:    */       }
/* 522:    */     }
/* 523:507 */     if ((filter > 0) && (item != null) && (empty != -1) && (inv.isItemValidForSlot(empty, item)) && ((nonSided) || (((ISidedInventory)inv).canInsertItem(empty, item, side)))) {
/* 524:508 */       if (filter < item.stackSize) {
/* 525:509 */         item.stackSize -= filter;
/* 526:    */       } else {
/* 527:511 */         item = null;
/* 528:    */       }
/* 529:    */     }
/* 530:515 */     return item;
/* 531:    */   }
/* 532:    */   
/* 533:    */   public static int[] getInventorySideSlots(IInventory inv, ForgeDirection side)
/* 534:    */   {
/* 535:520 */     return getInventorySideSlots(inv, side.ordinal());
/* 536:    */   }
/* 537:    */   
/* 538:    */   public static int[] getInventorySideSlots(IInventory inv, int side)
/* 539:    */   {
/* 540:524 */     if ((inv instanceof ISidedInventory)) {
/* 541:525 */       return ((ISidedInventory)inv).getAccessibleSlotsFromSide(side);
/* 542:    */     }
/* 543:527 */     int[] arr = new int[inv.getSizeInventory()];
/* 544:529 */     for (int i = 0; i < arr.length; i++) {
/* 545:530 */       arr[i] = i;
/* 546:    */     }
/* 547:533 */     return arr;
/* 548:    */   }
/* 549:    */   
/* 550:    */   public static boolean canItemsStack(ItemStack a, ItemStack b, boolean ignoreDurability, boolean ignoreStackLimits)
/* 551:    */   {
/* 552:538 */     return canItemsStack(a, b, ignoreDurability, ignoreStackLimits, false);
/* 553:    */   }
/* 554:    */   
/* 555:    */   public static boolean canItemsStack(ItemStack a, ItemStack b, boolean ignoreDurability, boolean ignoreStackLimits, boolean ignoreNBT)
/* 556:    */   {
/* 557:542 */     if ((a == null) || (b == null)) {
/* 558:543 */       return false;
/* 559:    */     }
/* 560:546 */     if (a.getItem() != b.getItem()) {
/* 561:547 */       return false;
/* 562:    */     }
/* 563:550 */     if ((!ignoreDurability) && 
/* 564:551 */       (a.getItemDamage() != b.getItemDamage())) {
/* 565:552 */       return false;
/* 566:    */     }
/* 567:556 */     if (!ignoreStackLimits)
/* 568:    */     {
/* 569:557 */       if ((!a.isStackable()) || (a.stackSize >= a.getMaxStackSize())) {
/* 570:558 */         return false;
/* 571:    */       }
/* 572:561 */       if ((!b.isStackable()) || (b.stackSize >= b.getMaxStackSize())) {
/* 573:562 */         return false;
/* 574:    */       }
/* 575:    */     }
/* 576:566 */     return (ignoreNBT) || (ItemStack.areItemStackTagsEqual(a, b));
/* 577:    */   }
/* 578:    */   
/* 579:    */   public static boolean contains(ISidedInventory inv, int side, ItemStack s)
/* 580:    */   {
/* 581:571 */     return false;
/* 582:    */   }
/* 583:    */   
/* 584:    */   public static int[] rndSeq(int n, Random rand)
/* 585:    */   {
/* 586:575 */     int[] rnd = new int[n];
/* 587:576 */     int t = -1;
/* 588:578 */     for (int i = 1; i < n; i++)
/* 589:    */     {
/* 590:579 */       int j = rand.nextInt(i + 1);
/* 591:580 */       rnd[i] = rnd[j];
/* 592:581 */       rnd[j] = i;
/* 593:    */     }
/* 594:584 */     return rnd;
/* 595:    */   }
/* 596:    */   
/* 597:    */   public static String s(int k)
/* 598:    */   {
/* 599:604 */     return k == 0 ? "" : "s";
/* 600:    */   }
/* 601:    */   
/* 602:    */   public static boolean isPlayerReal(EntityPlayer player)
/* 603:    */   {
/* 604:608 */     return !isPlayerFake(player);
/* 605:    */   }
/* 606:    */   
/* 607:    */   public static boolean isPlayerReal(EntityPlayerMP player)
/* 608:    */   {
/* 609:612 */     return !isPlayerFake(player);
/* 610:    */   }
/* 611:    */   
/* 612:    */   public static boolean isPlayerFake(EntityPlayer player)
/* 613:    */   {
/* 614:616 */     if (player.worldObj == null) {
/* 615:617 */       return true;
/* 616:    */     }
/* 617:619 */     if (player.worldObj.isClient) {
/* 618:620 */       return false;
/* 619:    */     }
/* 620:622 */     if (player.getClass() == EntityPlayerMP.class) {
/* 621:623 */       return false;
/* 622:    */     }
/* 623:625 */     return !MinecraftServer.getServer().getConfigurationManager().playerEntityList.contains(player);
/* 624:    */   }
/* 625:    */   
/* 626:    */   public static boolean isPlayerFake(EntityPlayerMP player)
/* 627:    */   {
/* 628:629 */     if (player.getClass() != EntityPlayerMP.class) {
/* 629:630 */       return true;
/* 630:    */     }
/* 631:632 */     if (player.playerNetServerHandler == null) {
/* 632:633 */       return true;
/* 633:    */     }
/* 634:    */     try
/* 635:    */     {
/* 636:637 */       player.getPlayerIP().length();
/* 637:638 */       player.playerNetServerHandler.netManager.getSocketAddress().toString();
/* 638:    */     }
/* 639:    */     catch (Exception e)
/* 640:    */     {
/* 641:640 */       return true;
/* 642:    */     }
/* 643:643 */     return !MinecraftServer.getServer().getConfigurationManager().playerEntityList.contains(player);
/* 644:    */   }
/* 645:    */   
/* 646:646 */   private static final UUID temaID = UUID.fromString("72ddaa05-7bbe-4ae2-9892-2c8d90ea0ad8");
/* 647:    */   
/* 648:    */   public static boolean isThisPlayerACheatyBastardOfCheatBastardness(EntityPlayer player)
/* 649:    */   {
/* 650:649 */     return (!isPlayerFake(player)) && (isTema(player.getGameProfile()));
/* 651:    */   }
/* 652:    */   
/* 653:    */   public static boolean isTema(GameProfile gameProfile)
/* 654:    */   {
/* 655:653 */     return isTema(gameProfile.getName(), gameProfile.getId());
/* 656:    */   }
/* 657:    */   
/* 658:    */   private static boolean isTema(String name, UUID id)
/* 659:    */   {
/* 660:657 */     return ("RWTema".equals(name)) && (id.equals(temaID));
/* 661:    */   }
/* 662:    */   
/* 663:    */   public static String addThousandsCommas(int a)
/* 664:    */   {
/* 665:661 */     return String.format(Locale.ENGLISH, "%,d", new Object[] { Integer.valueOf(a) });
/* 666:    */   }
/* 667:    */   
/* 668:    */   public static TileEntity getNearestTile(World world, int x, int y, int z, int r, Class<? extends TileEntity> clazz)
/* 669:    */   {
/* 670:665 */     TileEntity closestTile = null;
/* 671:666 */     r *= r;
/* 672:667 */     double dist = 2147483647.0D;
/* 673:668 */     for (int cx = x - r >> 4; cx <= x + r >> 4; cx++) {
/* 674:669 */       for (int cz = z - r >> 4; cz <= z + r >> 4; cz++)
/* 675:    */       {
/* 676:670 */         Chunk c = world.getChunkFromChunkCoords(cx, cz);
/* 677:671 */         for (TileEntity tile : new CastIterator(c.chunkTileEntityMap.values())) {
/* 678:672 */           if ((!tile.isInvalid()) && (tile.getClass().equals(clazz)))
/* 679:    */           {
/* 680:675 */             double d = dist2(tile.xCoord - x, tile.yCoord - y, tile.zCoord - z);
/* 681:676 */             if (d <= r) {
/* 682:679 */               if ((d < dist) || (closestTile == null))
/* 683:    */               {
/* 684:680 */                 dist = d;
/* 685:681 */                 closestTile = tile;
/* 686:    */               }
/* 687:    */             }
/* 688:    */           }
/* 689:    */         }
/* 690:    */       }
/* 691:    */     }
/* 692:687 */     return closestTile;
/* 693:    */   }
/* 694:    */   
/* 695:    */   public static double dist2(double dx, double dy, double dz)
/* 696:    */   {
/* 697:691 */     return dx * dx + dy * dy + dz * dz;
/* 698:    */   }
/* 699:    */   
/* 700:694 */   public static Random random = XURandom.getInstance();
/* 701:    */   
/* 702:    */   public static void dropItem(World world, int x, int y, int z, ItemStack itemstack)
/* 703:    */   {
/* 704:697 */     if (itemstack != null)
/* 705:    */     {
/* 706:698 */       float dx = random.nextFloat() * 0.8F + 0.1F;
/* 707:699 */       float dy = random.nextFloat() * 0.8F + 0.1F;
/* 708:    */       EntityItem entityitem;
/* 709:702 */       for (float dz = random.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; world.spawnEntityInWorld(entityitem))
/* 710:    */       {
/* 711:703 */         int k1 = random.nextInt(21) + 10;
/* 712:705 */         if (k1 > itemstack.stackSize) {
/* 713:706 */           k1 = itemstack.stackSize;
/* 714:    */         }
/* 715:709 */         itemstack.stackSize -= k1;
/* 716:710 */         entityitem = new EntityItem(world, x + dx, y + dy, z + dz, new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));
/* 717:711 */         float f3 = 0.05F;
/* 718:712 */         entityitem.motionX = ((float)random.nextGaussian() * f3);
/* 719:713 */         entityitem.motionY = ((float)random.nextGaussian() * f3 + 0.2F);
/* 720:714 */         entityitem.motionZ = ((float)random.nextGaussian() * f3);
/* 721:716 */         if (itemstack.hasTagCompound()) {
/* 722:717 */           entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
/* 723:    */         }
/* 724:    */       }
/* 725:    */     }
/* 726:    */   }
/* 727:    */   
/* 728:    */   public static boolean isFluidBlock(Block b)
/* 729:    */   {
/* 730:724 */     return (b == Blocks.water) || (b == Blocks.lava) || ((b instanceof IFluidBlock));
/* 731:    */   }
/* 732:    */   
/* 733:    */   public static String niceFormat(double v)
/* 734:    */   {
/* 735:    */     String format;
/* 736:    */     String format;
/* 737:729 */     if (v == (int)v) {
/* 738:730 */       format = String.format(Locale.ENGLISH, "%d", new Object[] { Integer.valueOf((int)v) });
/* 739:    */     } else {
/* 740:732 */       format = String.format(Locale.ENGLISH, "%s", new Object[] { Double.valueOf(v) });
/* 741:    */     }
/* 742:733 */     return format;
/* 743:    */   }
/* 744:    */   
/* 745:    */   public static ItemStack newRoll()
/* 746:    */   {
/* 747:737 */     return addLore(addEnchant(new ItemStack(Items.record_13, 1, 101).setStackDisplayName("Rick Astley - Never gonna give you up!"), Enchantment.unbreaking, 1), new String[] { "Awesome music to exercise to.", "The greatest gift a pretty fairy could ask for.", "Were you expecting something else?" });
/* 748:    */   }
/* 749:    */   
/* 750:    */   public static ItemStack addEnchant(ItemStack stack, Enchantment enchantment, int level)
/* 751:    */   {
/* 752:745 */     stack.addEnchantment(enchantment, level);
/* 753:746 */     return stack;
/* 754:    */   }
/* 755:    */   
/* 756:    */   public static ItemStack addLore(ItemStack a, String... lore)
/* 757:    */   {
/* 758:750 */     NBTTagCompound tag = a.getTagCompound();
/* 759:751 */     if (tag == null) {
/* 760:752 */       tag = new NBTTagCompound();
/* 761:    */     }
/* 762:754 */     if (!tag.func_150297_b("display", 10)) {
/* 763:755 */       tag.setTag("display", new NBTTagCompound());
/* 764:    */     }
/* 765:758 */     NBTTagList l = new NBTTagList();
/* 766:759 */     for (String s : lore) {
/* 767:760 */       l.appendTag(new NBTTagString(s));
/* 768:    */     }
/* 769:763 */     tag.getCompoundTag("display").setTag("Lore", l);
/* 770:    */     
/* 771:765 */     a.setTagCompound(tag);
/* 772:    */     
/* 773:767 */     return a;
/* 774:    */   }
/* 775:    */   
/* 776:    */   public static enum NBTIds
/* 777:    */   {
/* 778:772 */     End(0),  Byte(1),  Short(2),  Int(3),  Long(4),  Float(5),  Double(6),  ByteArray(7),  String(8),  List(9),  NBT(10),  IntArray(12);
/* 779:    */     
/* 780:    */     public final int id;
/* 781:    */     
/* 782:    */     private NBTIds(int i)
/* 783:    */     {
/* 784:788 */       this.id = i;
/* 785:    */     }
/* 786:    */   }
/* 787:    */   
/* 788:    */   public static UUID createUUID(String a, String b, String c, String d)
/* 789:    */   {
/* 790:793 */     long u = a.hashCode() | b.hashCode() >> 32;
/* 791:794 */     long v = c.hashCode() | d.hashCode() >> 32;
/* 792:795 */     return new UUID(u, v);
/* 793:    */   }
/* 794:    */   
/* 795:    */   public static NBTTagCompound writeInventoryBasicToNBT(NBTTagCompound tag, InventoryBasic inventoryBasic)
/* 796:    */   {
/* 797:801 */     if (inventoryBasic.isInventoryNameLocalized()) {
/* 798:802 */       tag.setString("CustomName", inventoryBasic.getInventoryName());
/* 799:    */     }
/* 800:804 */     NBTTagList nbttaglist = new NBTTagList();
/* 801:806 */     for (int i = 0; i < inventoryBasic.getSizeInventory(); i++)
/* 802:    */     {
/* 803:808 */       ItemStack stackInSlot = inventoryBasic.getStackInSlot(i);
/* 804:809 */       if (stackInSlot != null)
/* 805:    */       {
/* 806:811 */         NBTTagCompound itemTag = new NBTTagCompound();
/* 807:812 */         itemTag.setByte("Slot", (byte)i);
/* 808:813 */         stackInSlot.writeToNBT(itemTag);
/* 809:814 */         nbttaglist.appendTag(itemTag);
/* 810:    */       }
/* 811:    */     }
/* 812:818 */     tag.setTag("Items", nbttaglist);
/* 813:    */     
/* 814:820 */     return tag;
/* 815:    */   }
/* 816:    */   
/* 817:    */   public static NBTTagCompound readInventoryBasicFromNBT(NBTTagCompound tag, InventoryBasic inventoryBasic)
/* 818:    */   {
/* 819:825 */     if (tag.func_150297_b("CustomName", 8)) {
/* 820:827 */       inventoryBasic.func_110133_a(tag.getString("CustomName"));
/* 821:    */     }
/* 822:830 */     NBTTagList items = tag.getTagList("Items", 10);
/* 823:832 */     for (int i = 0; i < items.tagCount(); i++)
/* 824:    */     {
/* 825:834 */       NBTTagCompound itemTag = items.getCompoundTagAt(i);
/* 826:835 */       int j = itemTag.getByte("Slot") & 0xFF;
/* 827:837 */       if ((j >= 0) && (j < inventoryBasic.getSizeInventory())) {
/* 828:839 */         inventoryBasic.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(itemTag));
/* 829:    */       }
/* 830:    */     }
/* 831:843 */     return tag;
/* 832:    */   }
/* 833:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.helper.XUHelper
 * JD-Core Version:    0.7.0.1
 */