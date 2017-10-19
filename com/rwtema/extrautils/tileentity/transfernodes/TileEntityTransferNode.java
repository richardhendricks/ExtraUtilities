/*   1:    */ package com.rwtema.extrautils.tileentity.transfernodes;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.helper.XURandom;
/*   5:    */ import com.rwtema.extrautils.network.NetworkHandler;
/*   6:    */ import com.rwtema.extrautils.tileentity.enderquarry.IChunkLoad;
/*   7:    */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INode;
/*   8:    */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeBuffer;
/*   9:    */ import com.rwtema.extrautils.tileentity.transfernodes.pipes.IPipe;
/*  10:    */ import com.rwtema.extrautils.tileentity.transfernodes.pipes.IPipeCosmetic;
/*  11:    */ import com.rwtema.extrautils.tileentity.transfernodes.pipes.StdPipes;
/*  12:    */ import cpw.mods.fml.relauncher.Side;
/*  13:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.HashSet;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Random;
/*  18:    */ import net.minecraft.entity.player.EntityPlayer;
/*  19:    */ import net.minecraft.inventory.IInventory;
/*  20:    */ import net.minecraft.item.ItemStack;
/*  21:    */ import net.minecraft.nbt.NBTTagCompound;
/*  22:    */ import net.minecraft.network.NetworkManager;
/*  23:    */ import net.minecraft.network.Packet;
/*  24:    */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*  25:    */ import net.minecraft.tileentity.TileEntity;
/*  26:    */ import net.minecraft.util.IIcon;
/*  27:    */ import net.minecraft.world.IBlockAccess;
/*  28:    */ import net.minecraft.world.World;
/*  29:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  30:    */ 
/*  31:    */ public abstract class TileEntityTransferNode
/*  32:    */   extends TileEntity
/*  33:    */   implements IPipe, INode, IPipeCosmetic, IChunkLoad
/*  34:    */ {
/*  35: 33 */   public static int baseMaxCoolDown = 20;
/*  36: 36 */   public int pipe_x = 0;
/*  37: 36 */   public int pipe_y = 0;
/*  38: 36 */   public int pipe_z = 0;
/*  39: 36 */   public int pipe_dir = 6;
/*  40: 39 */   public int pipe_type = 0;
/*  41: 41 */   public float pr = 1.0F;
/*  42: 41 */   public float pg = 0.0F;
/*  43: 41 */   public float pb = 0.0F;
/*  44: 43 */   public TileEntityTransferNodeUpgradeInventory upgrades = new TileEntityTransferNodeUpgradeInventory(6, this);
/*  45: 44 */   public boolean isReceiver = false;
/*  46:    */   public String type;
/*  47:    */   public INodeBuffer buffer;
/*  48: 47 */   public boolean powered = false;
/*  49: 48 */   public boolean init = false;
/*  50: 49 */   public SearchType searchType = SearchType.RANDOM_WALK;
/*  51: 50 */   public HashSet<SearchPosition> prevSearch = new HashSet();
/*  52: 51 */   public ArrayList<SearchPosition> toSearch = new ArrayList();
/*  53: 52 */   public Random rand = XURandom.getInstance();
/*  54:    */   protected int coolDown;
/*  55: 54 */   protected int maxCoolDown = 384;
/*  56: 55 */   protected int stepCoolDown = 1;
/*  57: 58 */   protected boolean oldVersion = false;
/*  58:    */   
/*  59:    */   public TileEntityTransferNode(String s, INodeBuffer buffer)
/*  60:    */   {
/*  61: 62 */     this.type = s;
/*  62: 63 */     this.buffer = buffer;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int upgradeNo(int n)
/*  66:    */   {
/*  67: 67 */     if (ExtraUtils.nodeUpgrade == null) {
/*  68: 68 */       return 0;
/*  69:    */     }
/*  70: 71 */     int k = 0;
/*  71: 73 */     for (int i = 0; i < this.upgrades.getSizeInventory(); i++)
/*  72:    */     {
/*  73: 74 */       ItemStack item = this.upgrades.getStackInSlot(i);
/*  74: 75 */       if ((item != null) && (this.upgrades.hasUpgradeNo(item)) && (this.upgrades.getUpgradeNo(item) == n)) {
/*  75: 76 */         k += this.upgrades.getStackInSlot(i).stackSize;
/*  76:    */       }
/*  77:    */     }
/*  78: 80 */     return k;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void calcUpgradeModifiers()
/*  82:    */   {
/*  83: 84 */     if ((this.worldObj == null) || (this.worldObj.isClient)) {
/*  84: 85 */       return;
/*  85:    */     }
/*  86: 87 */     if (this.isReceiver) {
/*  87: 88 */       TransferNodeEnderRegistry.clearTileRegistrations(this.buffer);
/*  88:    */     }
/*  89: 89 */     this.isReceiver = false;
/*  90:    */     
/*  91:    */ 
/*  92: 92 */     this.stepCoolDown = 2;
/*  93:    */     
/*  94: 94 */     SearchType prevSearchType = this.searchType;
/*  95: 95 */     this.searchType = SearchType.RANDOM_WALK;
/*  96:    */     
/*  97: 97 */     int prev_pipe_type = this.pipe_type;
/*  98: 98 */     if (this.upgrades.isValidPipeType(this.pipe_type)) {
/*  99: 99 */       this.pipe_type = 0;
/* 100:    */     }
/* 101:101 */     for (int i = 0; i < this.upgrades.getSizeInventory(); i++) {
/* 102:102 */       if ((this.upgrades.getStackInSlot(i) != null) && (ExtraUtils.nodeUpgrade != null) && (this.upgrades.getStackInSlot(i).getItem() == ExtraUtils.nodeUpgrade))
/* 103:    */       {
/* 104:103 */         if (this.upgrades.getStackInSlot(i).getItemDamage() == 0)
/* 105:    */         {
/* 106:104 */           for (int k = 0; k < this.upgrades.getStackInSlot(i).stackSize; k++)
/* 107:    */           {
/* 108:105 */             if (this.stepCoolDown >= this.maxCoolDown) {
/* 109:    */               break;
/* 110:    */             }
/* 111:106 */             this.stepCoolDown += 1;
/* 112:    */           }
/* 113:    */         }
/* 114:111 */         else if ((this.upgrades.getStackInSlot(i).getItemDamage() == 6) && (this.upgrades.getStackInSlot(i).hasDisplayName()))
/* 115:    */         {
/* 116:112 */           TransferNodeEnderRegistry.registerTile(new Frequency(this.upgrades.getStackInSlot(i)), this.buffer);
/* 117:113 */           this.isReceiver = true;
/* 118:    */         }
/* 119:114 */         else if (this.upgrades.getStackInSlot(i).getItemDamage() == 7)
/* 120:    */         {
/* 121:115 */           this.searchType = SearchType.DEPTH_FIRST;
/* 122:    */         }
/* 123:116 */         else if (this.upgrades.getStackInSlot(i).getItemDamage() == 8)
/* 124:    */         {
/* 125:117 */           this.searchType = SearchType.BREADTH_FIRST;
/* 126:    */         }
/* 127:    */       }
/* 128:121 */       else if (this.upgrades.pipeType(this.upgrades.getStackInSlot(i)) > 0) {
/* 129:122 */         this.pipe_type = this.upgrades.pipeType(this.upgrades.getStackInSlot(i));
/* 130:    */       }
/* 131:    */     }
/* 132:126 */     if (prevSearchType != this.searchType) {
/* 133:127 */       resetSearch();
/* 134:    */     }
/* 135:129 */     if (prev_pipe_type != this.pipe_type) {
/* 136:130 */       this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:133 */   boolean joinedWorld = false;
/* 141:    */   
/* 142:    */   public void updateEntity()
/* 143:    */   {
/* 144:136 */     if ((this.worldObj == null) || (this.worldObj.isClient)) {
/* 145:137 */       return;
/* 146:    */     }
/* 147:139 */     this.catchingDirty = true;
/* 148:140 */     if (!this.joinedWorld)
/* 149:    */     {
/* 150:141 */       this.joinedWorld = true;
/* 151:142 */       onWorldJoin();
/* 152:    */     }
/* 153:145 */     processBuffer();
/* 154:146 */     if (ExtraUtils.nodeUpgrade != null) {
/* 155:147 */       sendEnder();
/* 156:    */     }
/* 157:149 */     this.catchingDirty = false;
/* 158:150 */     if (this.isDirty)
/* 159:    */     {
/* 160:151 */       super.onInventoryChanged();
/* 161:152 */       this.isDirty = false;
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:156 */   public boolean catchingDirty = false;
/* 166:157 */   public boolean isDirty = false;
/* 167:    */   
/* 168:    */   public void onInventoryChanged()
/* 169:    */   {
/* 170:161 */     if (this.catchingDirty) {
/* 171:162 */       this.isDirty = true;
/* 172:    */     } else {
/* 173:164 */       super.onInventoryChanged();
/* 174:    */     }
/* 175:    */   }
/* 176:    */   
/* 177:    */   public abstract void processBuffer();
/* 178:    */   
/* 179:    */   public void sendEnder()
/* 180:    */   {
/* 181:170 */     for (int i = 0; i < this.upgrades.getSizeInventory(); i++)
/* 182:    */     {
/* 183:171 */       ItemStack item = this.upgrades.getStackInSlot(i);
/* 184:172 */       if ((item != null) && (item.getItem() == ExtraUtils.nodeUpgrade) && (item.getItemDamage() == 5)) {
/* 185:173 */         TransferNodeEnderRegistry.doTransfer(this.buffer, new Frequency(item), item.stackSize);
/* 186:    */       }
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void updateRedstone()
/* 191:    */   {
/* 192:179 */     if (this.worldObj == null) {
/* 193:180 */       return;
/* 194:    */     }
/* 195:181 */     TileEntity tile = this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord);
/* 196:182 */     if ((tile instanceof INode)) {
/* 197:183 */       this.powered = ((INode)tile).recalcRedstone();
/* 198:    */     }
/* 199:    */   }
/* 200:    */   
/* 201:    */   public boolean recalcRedstone()
/* 202:    */   {
/* 203:188 */     return this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord);
/* 204:    */   }
/* 205:    */   
/* 206:    */   public boolean isPowered()
/* 207:    */   {
/* 208:192 */     if ((!this.init) && (this.worldObj != null))
/* 209:    */     {
/* 210:193 */       this.powered = this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord);
/* 211:194 */       this.init = true;
/* 212:    */     }
/* 213:197 */     return this.powered == (upgradeNo(-2) == 0);
/* 214:    */   }
/* 215:    */   
/* 216:    */   public boolean checkRedstone()
/* 217:    */   {
/* 218:202 */     if (isPowered())
/* 219:    */     {
/* 220:203 */       resetSearch();
/* 221:204 */       return true;
/* 222:    */     }
/* 223:206 */     return false;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public int getNodeX()
/* 227:    */   {
/* 228:212 */     return this.xCoord;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public int getNodeY()
/* 232:    */   {
/* 233:217 */     return this.yCoord;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public int getNodeZ()
/* 237:    */   {
/* 238:222 */     return this.zCoord;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public ForgeDirection getNodeDir()
/* 242:    */   {
/* 243:227 */     return ForgeDirection.getOrientation(getBlockMetadata() % 6);
/* 244:    */   }
/* 245:    */   
/* 246:    */   public int getPipeX()
/* 247:    */   {
/* 248:232 */     return this.pipe_x;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public int getPipeY()
/* 252:    */   {
/* 253:237 */     return this.pipe_y;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public int getPipeZ()
/* 257:    */   {
/* 258:242 */     return this.pipe_z;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public int getPipeDir()
/* 262:    */   {
/* 263:247 */     return this.pipe_dir;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public List<ItemStack> getUpgrades()
/* 267:    */   {
/* 268:252 */     List<ItemStack> u = new ArrayList();
/* 269:254 */     for (int i = 0; i < this.upgrades.getSizeInventory(); i++) {
/* 270:255 */       if (this.upgrades.getStackInSlot(i) != null) {
/* 271:256 */         u.add(this.upgrades.getStackInSlot(i));
/* 272:    */       }
/* 273:    */     }
/* 274:259 */     return u;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void readFromNBT(NBTTagCompound tag)
/* 278:    */   {
/* 279:267 */     super.readFromNBT(tag);
/* 280:269 */     if (tag.hasKey("pipe_x")) {
/* 281:270 */       this.pipe_x = tag.getInteger("pipe_x");
/* 282:    */     } else {
/* 283:272 */       this.pipe_x = 0;
/* 284:    */     }
/* 285:275 */     if (tag.hasKey("pipe_y")) {
/* 286:276 */       this.pipe_y = tag.getInteger("pipe_y");
/* 287:    */     } else {
/* 288:278 */       this.pipe_y = 0;
/* 289:    */     }
/* 290:281 */     if (tag.hasKey("pipe_z")) {
/* 291:282 */       this.pipe_z = tag.getInteger("pipe_z");
/* 292:    */     } else {
/* 293:284 */       this.pipe_z = 0;
/* 294:    */     }
/* 295:287 */     this.pipe_dir = tag.getInteger("pipe_dir");
/* 296:290 */     if (tag.hasKey("pipe_type")) {
/* 297:291 */       this.pipe_type = tag.getByte("pipe_type");
/* 298:    */     } else {
/* 299:293 */       this.pipe_type = 0;
/* 300:    */     }
/* 301:296 */     for (int i = 0; i < this.upgrades.getSizeInventory(); i++) {
/* 302:297 */       if (tag.hasKey("upgrade_" + i)) {
/* 303:298 */         this.upgrades.setInventorySlotContents(i, ItemStack.loadItemStackFromNBT(tag.getCompoundTag("upgrade_" + i)));
/* 304:    */       } else {
/* 305:300 */         this.upgrades.setInventorySlotContents(i, null);
/* 306:    */       }
/* 307:    */     }
/* 308:304 */     this.buffer.readFromNBT(tag);
/* 309:    */     
/* 310:306 */     NBTTagCompound t = tag.getCompoundTag("prevSearch");
/* 311:307 */     int s = t.getInteger("size");
/* 312:308 */     for (int i = 0; i < s; i++) {
/* 313:309 */       this.prevSearch.add(SearchPosition.loadFromTag(t.getCompoundTag(Integer.toString(i))));
/* 314:    */     }
/* 315:312 */     t = tag.getCompoundTag("toSearch");
/* 316:313 */     s = t.getInteger("size");
/* 317:314 */     for (int i = 0; i < s; i++) {
/* 318:315 */       this.toSearch.add(SearchPosition.loadFromTag(t.getCompoundTag(Integer.toString(i))));
/* 319:    */     }
/* 320:318 */     if (tag.getByte("version") == 0) {
/* 321:319 */       this.oldVersion = true;
/* 322:    */     }
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void writeToNBT(NBTTagCompound par1NBTTagCompound)
/* 326:    */   {
/* 327:327 */     super.writeToNBT(par1NBTTagCompound);
/* 328:329 */     if (this.pipe_x != 0) {
/* 329:330 */       par1NBTTagCompound.setInteger("pipe_x", this.pipe_x);
/* 330:    */     }
/* 331:333 */     if (this.pipe_y != 0) {
/* 332:334 */       par1NBTTagCompound.setInteger("pipe_y", this.pipe_y);
/* 333:    */     }
/* 334:337 */     if (this.pipe_z != 0) {
/* 335:338 */       par1NBTTagCompound.setInteger("pipe_z", this.pipe_z);
/* 336:    */     }
/* 337:341 */     if (this.pipe_dir != 0) {
/* 338:342 */       par1NBTTagCompound.setInteger("pipe_dir", this.pipe_dir);
/* 339:    */     }
/* 340:345 */     if (this.pipe_type != 0) {
/* 341:346 */       par1NBTTagCompound.setByte("pipe_type", (byte)this.pipe_type);
/* 342:    */     }
/* 343:349 */     for (int i = 0; i < this.upgrades.getSizeInventory(); i++) {
/* 344:350 */       if (this.upgrades.getStackInSlot(i) != null)
/* 345:    */       {
/* 346:351 */         NBTTagCompound newItem = new NBTTagCompound();
/* 347:352 */         this.upgrades.getStackInSlot(i).writeToNBT(newItem);
/* 348:353 */         par1NBTTagCompound.setTag("upgrade_" + i, newItem);
/* 349:    */       }
/* 350:    */     }
/* 351:357 */     this.buffer.writeToNBT(par1NBTTagCompound);
/* 352:    */     
/* 353:359 */     NBTTagCompound t2 = new NBTTagCompound();
/* 354:360 */     t2.setInteger("size", this.prevSearch.size());
/* 355:361 */     int i = 0;
/* 356:362 */     for (SearchPosition p : this.prevSearch)
/* 357:    */     {
/* 358:363 */       t2.setTag(Integer.toString(i), p.getTag());
/* 359:364 */       i++;
/* 360:    */     }
/* 361:367 */     NBTTagCompound t3 = new NBTTagCompound();
/* 362:368 */     t3.setInteger("size", this.toSearch.size());
/* 363:370 */     for (i = 0; i < this.toSearch.size(); i++) {
/* 364:371 */       t3.setTag(Integer.toString(i), ((SearchPosition)this.toSearch.get(i)).getTag());
/* 365:    */     }
/* 366:374 */     par1NBTTagCompound.setTag("prevSearch", t2);
/* 367:375 */     par1NBTTagCompound.setTag("toSearch", t3);
/* 368:377 */     if (!this.oldVersion) {
/* 369:378 */       par1NBTTagCompound.setByte("version", (byte)1);
/* 370:    */     }
/* 371:    */   }
/* 372:    */   
/* 373:381 */   int ptype = 0;
/* 374:    */   
/* 375:    */   public int getType()
/* 376:    */   {
/* 377:384 */     if (this.ptype == 0)
/* 378:    */     {
/* 379:385 */       this.ptype = -1;
/* 380:386 */       Class clazz = getClass();
/* 381:387 */       if (clazz.equals(TileEntityTransferNodeInventory.class)) {
/* 382:388 */         this.ptype = 3;
/* 383:389 */       } else if (clazz.equals(TileEntityTransferNodeLiquid.class)) {
/* 384:390 */         this.ptype = 4;
/* 385:391 */       } else if (clazz.equals(TileEntityRetrievalNodeInventory.class)) {
/* 386:392 */         this.ptype = 5;
/* 387:393 */       } else if (clazz.equals(TileEntityRetrievalNodeLiquid.class)) {
/* 388:394 */         this.ptype = 6;
/* 389:395 */       } else if ((clazz.equals(TileEntityTransferNodeEnergy.class)) || (clazz.equals(TileEntityTransferNodeHyperEnergy.class))) {
/* 390:396 */         this.ptype = 7;
/* 391:    */       }
/* 392:    */     }
/* 393:399 */     return this.ptype;
/* 394:    */   }
/* 395:    */   
/* 396:    */   public void sendParticleUpdate()
/* 397:    */   {
/* 398:403 */     if ((ExtraUtils.disableNodeParticles) || (!this.joinedWorld)) {
/* 399:404 */       return;
/* 400:    */     }
/* 401:406 */     if (upgradeNo(-1) == 0) {
/* 402:407 */       return;
/* 403:    */     }
/* 404:409 */     NetworkHandler.sendParticleEvent(this.worldObj, getType(), this.xCoord + this.pipe_x, this.yCoord + this.pipe_y, this.zCoord + this.pipe_z);
/* 405:    */   }
/* 406:    */   
/* 407:    */   public void sendEnderParticleUpdate()
/* 408:    */   {
/* 409:413 */     if ((ExtraUtils.disableNodeParticles) || (!this.joinedWorld)) {
/* 410:414 */       return;
/* 411:    */     }
/* 412:416 */     if (upgradeNo(-1) == 0) {
/* 413:417 */       return;
/* 414:    */     }
/* 415:419 */     NetworkHandler.sendParticleEvent(this.worldObj, 8, this.xCoord + this.pipe_x, this.yCoord + this.pipe_y, this.zCoord + this.pipe_z);
/* 416:    */   }
/* 417:    */   
/* 418:    */   public boolean handleInventories()
/* 419:    */   {
/* 420:423 */     boolean advance = false;
/* 421:424 */     boolean rr = upgradeNo(9) != 0;
/* 422:425 */     boolean ss = this.buffer.shouldSearch();
/* 423:427 */     if (ss)
/* 424:    */     {
/* 425:428 */       IPipe pipe = TNHelper.getPipe(this.worldObj, this.xCoord + this.pipe_x, this.yCoord + this.pipe_y, this.zCoord + this.pipe_z);
/* 426:430 */       if (pipe != null)
/* 427:    */       {
/* 428:431 */         sendParticleUpdate();
/* 429:432 */         advance = pipe.transferItems(this.worldObj, this.xCoord + this.pipe_x, this.yCoord + this.pipe_y, this.zCoord + this.pipe_z, ForgeDirection.getOrientation(this.pipe_dir), this.buffer);
/* 430:    */       }
/* 431:    */       else
/* 432:    */       {
/* 433:434 */         resetSearch();
/* 434:435 */         return false;
/* 435:    */       }
/* 436:    */     }
/* 437:439 */     if (rr) {
/* 438:440 */       return ss;
/* 439:    */     }
/* 440:442 */     if (!this.buffer.shouldSearch())
/* 441:    */     {
/* 442:443 */       resetSearch();
/* 443:444 */       return false;
/* 444:    */     }
/* 445:447 */     return advance;
/* 446:    */   }
/* 447:    */   
/* 448:    */   public boolean advPipeSearch()
/* 449:    */   {
/* 450:453 */     if (this.pipe_dir == 6) {
/* 451:454 */       this.pipe_dir = getNodeDir().getOpposite().ordinal();
/* 452:    */     }
/* 453:457 */     IPipe pipeBlock = TNHelper.getPipe(this.worldObj, this.xCoord + this.pipe_x, this.yCoord + this.pipe_y, this.zCoord + this.pipe_z);
/* 454:460 */     if (pipeBlock != null)
/* 455:    */     {
/* 456:461 */       this.prevSearch.add(new SearchPosition(this.pipe_x, this.pipe_y, this.pipe_z, ForgeDirection.getOrientation(this.pipe_dir)));
/* 457:    */       
/* 458:463 */       ArrayList<ForgeDirection> dirs = pipeBlock.getOutputDirections(this.worldObj, this.xCoord + this.pipe_x, this.yCoord + this.pipe_y, this.zCoord + this.pipe_z, ForgeDirection.getOrientation(this.pipe_dir), this.buffer);
/* 459:465 */       switch (1.$SwitchMap$com$rwtema$extrautils$tileentity$transfernodes$TileEntityTransferNode$SearchType[this.searchType.ordinal()])
/* 460:    */       {
/* 461:    */       case 1: 
/* 462:467 */         if (!dirs.isEmpty()) {
/* 463:468 */           this.toSearch.add(new SearchPosition(this.pipe_x, this.pipe_y, this.pipe_z, (ForgeDirection)dirs.get(0)).adv());
/* 464:    */         }
/* 465:    */         break;
/* 466:    */       case 2: 
/* 467:471 */         for (ForgeDirection d : dirs)
/* 468:    */         {
/* 469:472 */           SearchPosition s = new SearchPosition(this.pipe_x, this.pipe_y, this.pipe_z, d).adv();
/* 470:473 */           if ((!this.prevSearch.contains(s)) && (!this.toSearch.contains(s))) {
/* 471:474 */             if (this.toSearch.isEmpty()) {
/* 472:475 */               this.toSearch.add(new SearchPosition(this.pipe_x, this.pipe_y, this.pipe_z, d).adv());
/* 473:    */             } else {
/* 474:477 */               this.toSearch.add(0, new SearchPosition(this.pipe_x, this.pipe_y, this.pipe_z, d).adv());
/* 475:    */             }
/* 476:    */           }
/* 477:    */         }
/* 478:481 */         break;
/* 479:    */       case 3: 
/* 480:483 */         for (ForgeDirection d : dirs)
/* 481:    */         {
/* 482:484 */           SearchPosition s = new SearchPosition(this.pipe_x, this.pipe_y, this.pipe_z, d).adv();
/* 483:485 */           if ((!this.prevSearch.contains(s)) && (!this.toSearch.contains(s))) {
/* 484:486 */             this.toSearch.add(s);
/* 485:    */           }
/* 486:    */         }
/* 487:    */       }
/* 488:    */     }
/* 489:493 */     if (!loadNextPos())
/* 490:    */     {
/* 491:494 */       this.pipe_dir = getNodeDir().getOpposite().ordinal();
/* 492:495 */       resetSearch();
/* 493:496 */       return false;
/* 494:    */     }
/* 495:499 */     return true;
/* 496:    */   }
/* 497:    */   
/* 498:    */   public void resetSearch()
/* 499:    */   {
/* 500:503 */     if ((this.pipe_x != 0) || (this.pipe_y != 0) || (this.pipe_z != 0) || (!this.prevSearch.isEmpty()))
/* 501:    */     {
/* 502:504 */       this.pipe_x = 0;
/* 503:505 */       this.pipe_y = 0;
/* 504:506 */       this.pipe_z = 0;
/* 505:507 */       this.pipe_dir = -1;
/* 506:508 */       this.toSearch.clear();
/* 507:509 */       this.prevSearch.clear();
/* 508:    */     }
/* 509:    */   }
/* 510:    */   
/* 511:    */   public boolean loadNextPos()
/* 512:    */   {
/* 513:514 */     if (this.toSearch.isEmpty()) {
/* 514:515 */       return false;
/* 515:    */     }
/* 516:518 */     SearchPosition pos = (SearchPosition)this.toSearch.remove(0);
/* 517:520 */     if (this.searchType != SearchType.RANDOM_WALK)
/* 518:    */     {
/* 519:521 */       while ((this.prevSearch.contains(pos)) && (!this.toSearch.isEmpty())) {
/* 520:522 */         pos = (SearchPosition)this.toSearch.remove(0);
/* 521:    */       }
/* 522:524 */       if (this.prevSearch.contains(pos)) {
/* 523:525 */         return false;
/* 524:    */       }
/* 525:527 */       this.prevSearch.add(pos.copy());
/* 526:    */     }
/* 527:530 */     this.pipe_x = pos.x;
/* 528:531 */     this.pipe_y = pos.y;
/* 529:532 */     this.pipe_z = pos.z;
/* 530:533 */     this.pipe_dir = pos.side.ordinal();
/* 531:534 */     return true;
/* 532:    */   }
/* 533:    */   
/* 534:    */   public Packet getDescriptionPacket()
/* 535:    */   {
/* 536:539 */     NBTTagCompound t = new NBTTagCompound();
/* 537:540 */     t.setByte("d", (byte)this.pipe_type);
/* 538:541 */     return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 4, t);
/* 539:    */   }
/* 540:    */   
/* 541:    */   @SideOnly(Side.CLIENT)
/* 542:    */   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
/* 543:    */   {
/* 544:547 */     if (!this.worldObj.isClient) {
/* 545:548 */       return;
/* 546:    */     }
/* 547:551 */     if (pkt.func_148857_g().hasKey("d"))
/* 548:    */     {
/* 549:552 */       if (pkt.func_148857_g().getByte("d") != this.pipe_type) {
/* 550:553 */         this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
/* 551:    */       }
/* 552:556 */       this.pipe_type = pkt.func_148857_g().getByte("d");
/* 553:    */     }
/* 554:    */   }
/* 555:    */   
/* 556:    */   public boolean isUseableByPlayer(EntityPlayer entityplayer)
/* 557:    */   {
/* 558:561 */     return true;
/* 559:    */   }
/* 560:    */   
/* 561:    */   public void invalidate()
/* 562:    */   {
/* 563:567 */     super.invalidate();
/* 564:568 */     onWorldLeave();
/* 565:    */   }
/* 566:    */   
/* 567:    */   public void onChunkLoad() {}
/* 568:    */   
/* 569:    */   public void onWorldJoin()
/* 570:    */   {
/* 571:577 */     this.buffer.setNode(this);
/* 572:578 */     calcUpgradeModifiers();
/* 573:    */   }
/* 574:    */   
/* 575:    */   public void onWorldLeave()
/* 576:    */   {
/* 577:582 */     TransferNodeEnderRegistry.clearTileRegistrations(this.buffer);
/* 578:    */   }
/* 579:    */   
/* 580:    */   public ArrayList<ForgeDirection> getOutputDirections(IBlockAccess world, int x, int y, int z, ForgeDirection dir, INodeBuffer buffer)
/* 581:    */   {
/* 582:587 */     return StdPipes.getPipeType(this.pipe_type).getOutputDirections(world, x, y, z, dir, buffer);
/* 583:    */   }
/* 584:    */   
/* 585:    */   public boolean transferItems(IBlockAccess world, int x, int y, int z, ForgeDirection dir, INodeBuffer buffer)
/* 586:    */   {
/* 587:592 */     return StdPipes.getPipeType(this.pipe_type).transferItems(world, x, y, z, dir, buffer);
/* 588:    */   }
/* 589:    */   
/* 590:    */   public boolean canInput(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
/* 591:    */   {
/* 592:597 */     return (dir != getNodeDir()) && (StdPipes.getPipeType(this.pipe_type).canInput(world, x, y, z, dir));
/* 593:    */   }
/* 594:    */   
/* 595:    */   public boolean canOutput(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
/* 596:    */   {
/* 597:603 */     return (dir != getNodeDir()) && (StdPipes.getPipeType(this.pipe_type).canOutput(world, x, y, z, dir));
/* 598:    */   }
/* 599:    */   
/* 600:    */   public int limitTransfer(TileEntity dest, ForgeDirection side, INodeBuffer buffer)
/* 601:    */   {
/* 602:609 */     return StdPipes.getPipeType(this.pipe_type).limitTransfer(dest, side, buffer);
/* 603:    */   }
/* 604:    */   
/* 605:    */   public IInventory getFilterInventory(IBlockAccess world, int x, int y, int z)
/* 606:    */   {
/* 607:614 */     return null;
/* 608:    */   }
/* 609:    */   
/* 610:    */   public boolean shouldConnectToTile(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
/* 611:    */   {
/* 612:619 */     return (dir != getNodeDir()) && (StdPipes.getPipeType(this.pipe_type).shouldConnectToTile(world, x, y, z, dir));
/* 613:    */   }
/* 614:    */   
/* 615:    */   public IIcon baseTexture()
/* 616:    */   {
/* 617:625 */     return ((IPipeCosmetic)StdPipes.getPipeType(this.pipe_type)).baseTexture();
/* 618:    */   }
/* 619:    */   
/* 620:    */   public IIcon pipeTexture(ForgeDirection dir, boolean blocked)
/* 621:    */   {
/* 622:630 */     return ((IPipeCosmetic)StdPipes.getPipeType(this.pipe_type)).pipeTexture(dir, blocked);
/* 623:    */   }
/* 624:    */   
/* 625:    */   public IIcon invPipeTexture(ForgeDirection dir)
/* 626:    */   {
/* 627:635 */     return ((IPipeCosmetic)StdPipes.getPipeType(this.pipe_type)).invPipeTexture(dir);
/* 628:    */   }
/* 629:    */   
/* 630:    */   public IIcon socketTexture(ForgeDirection dir)
/* 631:    */   {
/* 632:640 */     return ((IPipeCosmetic)StdPipes.getPipeType(this.pipe_type)).socketTexture(dir);
/* 633:    */   }
/* 634:    */   
/* 635:    */   public String getPipeType()
/* 636:    */   {
/* 637:645 */     return StdPipes.getPipeType(this.pipe_type).getPipeType();
/* 638:    */   }
/* 639:    */   
/* 640:    */   public float baseSize()
/* 641:    */   {
/* 642:650 */     return ((IPipeCosmetic)StdPipes.getPipeType(this.pipe_type)).baseSize();
/* 643:    */   }
/* 644:    */   
/* 645:    */   public TileEntityTransferNode getNode()
/* 646:    */   {
/* 647:655 */     return this;
/* 648:    */   }
/* 649:    */   
/* 650:    */   public String getNodeType()
/* 651:    */   {
/* 652:660 */     return this.type;
/* 653:    */   }
/* 654:    */   
/* 655:    */   public void update() {}
/* 656:    */   
/* 657:    */   public boolean initDirection()
/* 658:    */   {
/* 659:668 */     return false;
/* 660:    */   }
/* 661:    */   
/* 662:    */   public static enum SearchType
/* 663:    */   {
/* 664:673 */     RANDOM_WALK,  DEPTH_FIRST,  BREADTH_FIRST;
/* 665:    */     
/* 666:    */     private SearchType() {}
/* 667:    */   }
/* 668:    */   
/* 669:    */   public void bufferChanged()
/* 670:    */   {
/* 671:680 */     onInventoryChanged();
/* 672:    */   }
/* 673:    */   
/* 674:    */   public void onChunkUnload()
/* 675:    */   {
/* 676:685 */     super.onChunkUnload();
/* 677:686 */     onWorldLeave();
/* 678:    */   }
/* 679:    */   
/* 680:    */   public static class SearchPosition
/* 681:    */   {
/* 682:690 */     public ForgeDirection side = ForgeDirection.UNKNOWN;
/* 683:    */     public int x;
/* 684:    */     public int y;
/* 685:    */     public int z;
/* 686:    */     
/* 687:    */     public SearchPosition(int par1, int par2, int par3, ForgeDirection par4)
/* 688:    */     {
/* 689:694 */       this.x = par1;
/* 690:695 */       this.y = par2;
/* 691:696 */       this.z = par3;
/* 692:697 */       this.side = par4;
/* 693:    */     }
/* 694:    */     
/* 695:    */     public static byte getOrd(ForgeDirection e)
/* 696:    */     {
/* 697:701 */       return (byte)e.ordinal();
/* 698:    */     }
/* 699:    */     
/* 700:    */     public static SearchPosition loadFromTag(NBTTagCompound tag)
/* 701:    */     {
/* 702:705 */       return new SearchPosition(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z"), ForgeDirection.getOrientation(tag.getByte("side")));
/* 703:    */     }
/* 704:    */     
/* 705:    */     public SearchPosition copy()
/* 706:    */     {
/* 707:714 */       return new SearchPosition(this.x, this.y, this.z, this.side);
/* 708:    */     }
/* 709:    */     
/* 710:    */     public String toString()
/* 711:    */     {
/* 712:719 */       return "SearchPosition{side=" + this.side + ", x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
/* 713:    */     }
/* 714:    */     
/* 715:    */     public SearchPosition adv()
/* 716:    */     {
/* 717:728 */       this.x += this.side.offsetX;
/* 718:729 */       this.y += this.side.offsetY;
/* 719:730 */       this.z += this.side.offsetZ;
/* 720:731 */       return this;
/* 721:    */     }
/* 722:    */     
/* 723:    */     public boolean equals(Object o)
/* 724:    */     {
/* 725:736 */       if (this == o) {
/* 726:736 */         return true;
/* 727:    */       }
/* 728:737 */       if ((o == null) || (getClass() != o.getClass())) {
/* 729:737 */         return false;
/* 730:    */       }
/* 731:739 */       SearchPosition that = (SearchPosition)o;
/* 732:741 */       if (effectiveX() != that.effectiveX()) {
/* 733:741 */         return false;
/* 734:    */       }
/* 735:742 */       return (effectiveY() == that.effectiveY()) && (effectiveZ() == that.effectiveZ());
/* 736:    */     }
/* 737:    */     
/* 738:    */     public int effectiveX()
/* 739:    */     {
/* 740:746 */       return this.x * 2 - this.side.offsetX;
/* 741:    */     }
/* 742:    */     
/* 743:    */     public int effectiveY()
/* 744:    */     {
/* 745:750 */       return this.y * 2 - this.side.offsetY;
/* 746:    */     }
/* 747:    */     
/* 748:    */     public int effectiveZ()
/* 749:    */     {
/* 750:754 */       return this.z * 2 - this.side.offsetZ;
/* 751:    */     }
/* 752:    */     
/* 753:    */     public int hashCode()
/* 754:    */     {
/* 755:759 */       int result = effectiveX();
/* 756:760 */       result = 31 * result + effectiveY();
/* 757:761 */       result = 31 * result + effectiveZ();
/* 758:762 */       return result;
/* 759:    */     }
/* 760:    */     
/* 761:    */     public NBTTagCompound getTag()
/* 762:    */     {
/* 763:766 */       NBTTagCompound tag = new NBTTagCompound();
/* 764:767 */       tag.setInteger("x", this.x);
/* 765:768 */       tag.setInteger("y", this.y);
/* 766:769 */       tag.setInteger("z", this.z);
/* 767:770 */       tag.setByte("side", (byte)this.side.ordinal());
/* 768:771 */       return tag;
/* 769:    */     }
/* 770:    */   }
/* 771:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNode
 * JD-Core Version:    0.7.0.1
 */