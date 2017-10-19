/*   1:    */ package com.rwtema.extrautils.tileentity.generators;
/*   2:    */ 
/*   3:    */ import cofh.api.energy.EnergyStorage;
/*   4:    */ import cofh.api.energy.IEnergyHandler;
/*   5:    */ import cofh.api.energy.IEnergyReceiver;
/*   6:    */ import com.rwtema.extrautils.sounds.ISoundTile;
/*   7:    */ import com.rwtema.extrautils.sounds.Sounds;
/*   8:    */ import cpw.mods.fml.relauncher.Side;
/*   9:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  10:    */ import java.util.Random;
/*  11:    */ import net.minecraft.block.Block;
/*  12:    */ import net.minecraft.init.Items;
/*  13:    */ import net.minecraft.item.ItemStack;
/*  14:    */ import net.minecraft.nbt.NBTTagCompound;
/*  15:    */ import net.minecraft.network.NetworkManager;
/*  16:    */ import net.minecraft.network.Packet;
/*  17:    */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*  18:    */ import net.minecraft.tileentity.TileEntity;
/*  19:    */ import net.minecraft.tileentity.TileEntityFurnace;
/*  20:    */ import net.minecraft.util.ResourceLocation;
/*  21:    */ import net.minecraft.world.World;
/*  22:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  23:    */ import net.minecraftforge.fluids.Fluid;
/*  24:    */ import net.minecraftforge.fluids.FluidStack;
/*  25:    */ import net.minecraftforge.fluids.FluidTank;
/*  26:    */ import net.minecraftforge.fluids.FluidTankInfo;
/*  27:    */ 
/*  28:    */ public class TileEntityGenerator
/*  29:    */   extends TileEntity
/*  30:    */   implements IEnergyHandler, ISoundTile
/*  31:    */ {
/*  32:    */   public static final int capacity = 100000;
/*  33: 32 */   public EnergyStorage storage = new EnergyStorage(100000);
/*  34: 34 */   public byte rotation = 0;
/*  35: 35 */   public double coolDown = 0.0D;
/*  36: 37 */   int c = -1;
/*  37: 39 */   public boolean initPower = true;
/*  38:    */   
/*  39:    */   public EnergyStorage getStorage()
/*  40:    */   {
/*  41: 42 */     if ((this.initPower) && (this.worldObj != null) && (!this.worldObj.isClient) && (this.worldObj.blockExists(x(), y(), z())))
/*  42:    */     {
/*  43: 43 */       this.initPower = false;
/*  44: 44 */       this.storage.setCapacity(100000 * getMultiplier());
/*  45: 45 */       this.storage.setMaxTransfer(100000 * getMultiplier());
/*  46:    */     }
/*  47: 48 */     return this.storage;
/*  48:    */   }
/*  49:    */   
/*  50: 51 */   public boolean playSound = false;
/*  51: 53 */   private int multiplier = -1;
/*  52: 54 */   private double divisor = -1.0D;
/*  53:    */   
/*  54:    */   public int getMultiplier()
/*  55:    */   {
/*  56: 57 */     if (this.multiplier == -1)
/*  57:    */     {
/*  58: 58 */       Block b = getBlockType();
/*  59: 59 */       if ((b instanceof BlockGenerator)) {
/*  60: 60 */         this.multiplier = ((BlockGenerator)b).numGenerators;
/*  61:    */       } else {
/*  62: 62 */         this.multiplier = 1;
/*  63:    */       }
/*  64:    */     }
/*  65: 64 */     return this.multiplier;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void invalidate()
/*  69:    */   {
/*  70: 69 */     super.invalidate();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public double getDivisor()
/*  74:    */   {
/*  75: 73 */     if (this.divisor == -1.0D) {
/*  76: 74 */       this.divisor = (1.0D / getMultiplier());
/*  77:    */     }
/*  78: 76 */     return this.divisor;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public static int getFurnaceBurnTime(ItemStack item)
/*  82:    */   {
/*  83: 80 */     if (item == null) {
/*  84: 81 */       return 0;
/*  85:    */     }
/*  86: 84 */     if (item.getItem() == Items.lava_bucket) {
/*  87: 85 */       return 0;
/*  88:    */     }
/*  89: 88 */     return TileEntityFurnace.func_145952_a(item);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public TileEntity getTile()
/*  93:    */   {
/*  94: 93 */     return this;
/*  95:    */   }
/*  96:    */   
/*  97: 96 */   public static ResourceLocation hum = new ResourceLocation("extrautils", "ambient.hum");
/*  98:    */   
/*  99:    */   public ResourceLocation getSound()
/* 100:    */   {
/* 101:100 */     return hum;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean shouldSoundPlay()
/* 105:    */   {
/* 106:105 */     return this.playSound;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public static String getCoolDownString(double time)
/* 110:    */   {
/* 111:109 */     String s = String.format("%.1f", new Object[] { Double.valueOf(time % 60.0D) }) + "s";
/* 112:110 */     int t = (int)time / 60;
/* 113:112 */     if (t == 0) {
/* 114:113 */       return s;
/* 115:    */     }
/* 116:116 */     s = t % 60 + "m " + s;
/* 117:117 */     t /= 60;
/* 118:119 */     if (t == 0) {
/* 119:120 */       return s;
/* 120:    */     }
/* 121:123 */     s = t % 24 + "h " + s;
/* 122:124 */     t /= 24;
/* 123:126 */     if (t == 0) {
/* 124:127 */       return s;
/* 125:    */     }
/* 126:130 */     s = t + "d " + s;
/* 127:131 */     return s;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public int x()
/* 131:    */   {
/* 132:135 */     return this.xCoord;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public int y()
/* 136:    */   {
/* 137:139 */     return this.yCoord;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public int z()
/* 141:    */   {
/* 142:143 */     return this.zCoord;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public boolean isPowered()
/* 146:    */   {
/* 147:147 */     return this.worldObj.isBlockIndirectlyGettingPowered(x(), y(), z());
/* 148:    */   }
/* 149:    */   
/* 150:    */   public String getBlurb(double coolDown, double energy)
/* 151:    */   {
/* 152:151 */     if (coolDown == 0.0D) {
/* 153:152 */       return "";
/* 154:    */     }
/* 155:154 */     return "PowerLevel:\n" + String.format("%.1f", new Object[] { Double.valueOf(energy) }) + "\nTime Remaining:\n" + getCoolDownString(coolDown);
/* 156:    */   }
/* 157:    */   
/* 158:    */   public double stepCoolDown()
/* 159:    */   {
/* 160:160 */     return 1.0D;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public int getCompLevel()
/* 164:    */   {
/* 165:168 */     if (this.c == -1) {
/* 166:169 */       this.c = (getStorage().getEnergyStored() * 15 / getStorage().getMaxEnergyStored());
/* 167:    */     }
/* 168:171 */     return this.c;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void checkCompLevel()
/* 172:    */   {
/* 173:175 */     if (getCompLevel() != getStorage().getEnergyStored() * 15 / getStorage().getMaxEnergyStored())
/* 174:    */     {
/* 175:176 */       this.c = (getStorage().getEnergyStored() * 15 / getStorage().getMaxEnergyStored());
/* 176:177 */       this.worldObj.notifyBlocksOfNeighborChange(x(), y(), z(), getBlockType());
/* 177:    */     }
/* 178:    */   }
/* 179:    */   
/* 180:    */   public boolean shouldProcess()
/* 181:    */   {
/* 182:183 */     return false;
/* 183:    */   }
/* 184:    */   
/* 185:186 */   private boolean shouldInit = true;
/* 186:    */   
/* 187:    */   public void updateEntity()
/* 188:    */   {
/* 189:191 */     if (this.worldObj.isClient)
/* 190:    */     {
/* 191:192 */       if (this.shouldInit)
/* 192:    */       {
/* 193:193 */         this.shouldInit = false;
/* 194:194 */         Sounds.addGenerator(this);
/* 195:    */       }
/* 196:196 */       return;
/* 197:    */     }
/* 198:199 */     if (this.coolDown > 0.0D)
/* 199:    */     {
/* 200:200 */       if (this.coolDown > 1.0D)
/* 201:    */       {
/* 202:201 */         getStorage().receiveEnergy((int)Math.floor(genLevel() * getMultiplier()), false);
/* 203:202 */         this.coolDown -= stepCoolDown();
/* 204:    */       }
/* 205:    */       else
/* 206:    */       {
/* 207:204 */         getStorage().receiveEnergy((int)Math.floor(this.coolDown * genLevel() * getMultiplier()), false);
/* 208:205 */         this.coolDown = 0.0D;
/* 209:    */       }
/* 210:    */     }
/* 211:    */     else {
/* 212:208 */       this.coolDown = 0.0D;
/* 213:    */     }
/* 214:211 */     doSpecial();
/* 215:213 */     if ((shouldProcess()) && ((getStorage().getEnergyStored() == 0) || (getStorage().getEnergyStored() < Math.min(getStorage().getMaxEnergyStored() - 1000, getStorage().getMaxEnergyStored() - getMultiplier() * genLevel())))) {
/* 216:214 */       processInput();
/* 217:    */     }
/* 218:216 */     if (this.coolDown > 0.0D != this.playSound)
/* 219:    */     {
/* 220:217 */       this.worldObj.markBlockForUpdate(x(), y(), z());
/* 221:218 */       this.playSound = (this.coolDown > 0.0D);
/* 222:    */     }
/* 223:222 */     if ((shouldTransmit()) && (getStorage().getEnergyStored() > 0)) {
/* 224:223 */       transmitEnergy();
/* 225:    */     }
/* 226:226 */     checkCompLevel();
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void doSpecial() {}
/* 230:    */   
/* 231:    */   @SideOnly(Side.CLIENT)
/* 232:    */   public void doRandomDisplayTickR(Random random) {}
/* 233:    */   
/* 234:    */   private void transmitEnergy()
/* 235:    */   {
/* 236:239 */     for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS)
/* 237:    */     {
/* 238:240 */       TileEntity tile = this.worldObj.getTileEntity(x() + side.offsetX, y() + side.offsetY, z() + side.offsetZ);
/* 239:242 */       if (!(tile instanceof TileEntityGenerator)) {
/* 240:245 */         if ((tile instanceof IEnergyReceiver)) {
/* 241:246 */           getStorage().extractEnergy(((IEnergyReceiver)tile).receiveEnergy(side.getOpposite(), getStorage().extractEnergy(transferLimit() * getMultiplier(), true), false), false);
/* 242:    */         }
/* 243:    */       }
/* 244:    */     }
/* 245:    */   }
/* 246:    */   
/* 247:    */   public int transferLimit()
/* 248:    */   {
/* 249:253 */     return getStorage().getMaxEnergyStored();
/* 250:    */   }
/* 251:    */   
/* 252:    */   public boolean shouldTransmit()
/* 253:    */   {
/* 254:257 */     return true;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public int getMaxCoolDown()
/* 258:    */   {
/* 259:261 */     return 200;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public double getNerfVisor()
/* 263:    */   {
/* 264:265 */     if (getMultiplier() == 1) {
/* 265:266 */       return 1.0D;
/* 266:    */     }
/* 267:267 */     if (getMultiplier() <= 8) {
/* 268:268 */       return 1.0D;
/* 269:    */     }
/* 270:270 */     return 1.0D;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public final boolean addCoolDown(double coolDown, boolean simulate)
/* 274:    */   {
/* 275:274 */     if (!simulate) {
/* 276:275 */       this.coolDown += coolDown * getDivisor() * getNerfVisor();
/* 277:    */     }
/* 278:278 */     return true;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public boolean processInput()
/* 282:    */   {
/* 283:282 */     return false;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public double genLevel()
/* 287:    */   {
/* 288:286 */     return 0.0D;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public FluidTank[] getTanks()
/* 292:    */   {
/* 293:290 */     return new FluidTank[0];
/* 294:    */   }
/* 295:    */   
/* 296:    */   public InventoryGeneric getInventory()
/* 297:    */   {
/* 298:294 */     return null;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public void readFromNBT(NBTTagCompound nbt)
/* 302:    */   {
/* 303:299 */     super.readFromNBT(nbt);
/* 304:    */     
/* 305:301 */     int energy = nbt.getInteger("Energy");
/* 306:303 */     if (energy > this.storage.getMaxEnergyStored())
/* 307:    */     {
/* 308:304 */       this.storage.setCapacity(energy);
/* 309:305 */       this.initPower = true;
/* 310:    */     }
/* 311:307 */     this.storage.setEnergyStored(energy);
/* 312:309 */     if (getInventory() != null) {
/* 313:310 */       getInventory().readFromNBT(nbt);
/* 314:    */     }
/* 315:313 */     if (getTanks() != null) {
/* 316:314 */       for (int i = 0; i < getTanks().length; i++) {
/* 317:315 */         getTanks()[i].readFromNBT(nbt.getCompoundTag("Tank_" + i));
/* 318:    */       }
/* 319:    */     }
/* 320:319 */     this.rotation = ((byte)nbt.getInteger("rotation"));
/* 321:320 */     this.coolDown = nbt.getDouble("coolDown");
/* 322:321 */     this.playSound = (this.coolDown > 0.0D);
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void writeToNBT(NBTTagCompound nbt)
/* 326:    */   {
/* 327:326 */     getStorage().writeToNBT(nbt);
/* 328:328 */     if (getInventory() != null) {
/* 329:329 */       getInventory().writeToNBT(nbt);
/* 330:    */     }
/* 331:332 */     if (getTanks() != null) {
/* 332:333 */       for (int i = 0; i < getTanks().length; i++)
/* 333:    */       {
/* 334:334 */         NBTTagCompound t = new NBTTagCompound();
/* 335:335 */         getTanks()[i].writeToNBT(t);
/* 336:336 */         nbt.setTag("Tank_" + i, t);
/* 337:    */       }
/* 338:    */     }
/* 339:340 */     nbt.setInteger("rotation", this.rotation);
/* 340:341 */     nbt.setDouble("coolDown", this.coolDown);
/* 341:342 */     super.writeToNBT(nbt);
/* 342:343 */     NBTTagCompound backup = new NBTTagCompound();
/* 343:344 */     super.writeToNBT(backup);
/* 344:    */     
/* 345:346 */     nbt.setTag("backup", backup);
/* 346:    */   }
/* 347:    */   
/* 348:    */   public Packet getDescriptionPacket()
/* 349:    */   {
/* 350:351 */     NBTTagCompound t = new NBTTagCompound();
/* 351:352 */     t.setByte("d", this.rotation);
/* 352:353 */     t.setBoolean("s", this.coolDown > 0.0D);
/* 353:354 */     this.playSound = (this.coolDown > 0.0D);
/* 354:355 */     return new S35PacketUpdateTileEntity(x(), y(), z(), 4, t);
/* 355:    */   }
/* 356:    */   
/* 357:    */   @SideOnly(Side.CLIENT)
/* 358:    */   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
/* 359:    */   {
/* 360:361 */     if (!this.worldObj.isClient) {
/* 361:362 */       return;
/* 362:    */     }
/* 363:365 */     NBTTagCompound tags = pkt.func_148857_g();
/* 364:366 */     if (tags.hasKey("d"))
/* 365:    */     {
/* 366:367 */       if (tags.getByte("d") != this.rotation) {
/* 367:368 */         this.worldObj.markBlockForUpdate(x(), y(), z());
/* 368:    */       }
/* 369:371 */       this.rotation = tags.getByte("d");
/* 370:    */     }
/* 371:374 */     if (tags.hasKey("s"))
/* 372:    */     {
/* 373:375 */       this.playSound = tags.getBoolean("s");
/* 374:376 */       Sounds.refresh();
/* 375:    */     }
/* 376:    */   }
/* 377:    */   
/* 378:    */   public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
/* 379:    */   {
/* 380:384 */     return 0;
/* 381:    */   }
/* 382:    */   
/* 383:    */   public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
/* 384:    */   {
/* 385:389 */     return shouldTransmit() ? getStorage().extractEnergy(Math.min(transferLimit() * getMultiplier(), maxExtract), simulate) : 0;
/* 386:    */   }
/* 387:    */   
/* 388:    */   public boolean canConnectEnergy(ForgeDirection from)
/* 389:    */   {
/* 390:394 */     return true;
/* 391:    */   }
/* 392:    */   
/* 393:    */   public int getEnergyStored(ForgeDirection from)
/* 394:    */   {
/* 395:399 */     return getStorage().getEnergyStored();
/* 396:    */   }
/* 397:    */   
/* 398:    */   public int getMaxEnergyStored(ForgeDirection from)
/* 399:    */   {
/* 400:404 */     return getStorage().getMaxEnergyStored();
/* 401:    */   }
/* 402:    */   
/* 403:    */   public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
/* 404:    */   {
/* 405:408 */     int c = 0;
/* 406:410 */     for (FluidTank tank : getTanks()) {
/* 407:411 */       c += tank.fill(resource, doFill);
/* 408:    */     }
/* 409:414 */     return c;
/* 410:    */   }
/* 411:    */   
/* 412:    */   public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
/* 413:    */   {
/* 414:418 */     return null;
/* 415:    */   }
/* 416:    */   
/* 417:    */   public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
/* 418:    */   {
/* 419:422 */     return null;
/* 420:    */   }
/* 421:    */   
/* 422:    */   public boolean canFill(ForgeDirection from, Fluid fluid)
/* 423:    */   {
/* 424:426 */     return true;
/* 425:    */   }
/* 426:    */   
/* 427:    */   public boolean canDrain(ForgeDirection from, Fluid fluid)
/* 428:    */   {
/* 429:430 */     return false;
/* 430:    */   }
/* 431:    */   
/* 432:    */   public FluidTankInfo[] getTankInfo(ForgeDirection from)
/* 433:    */   {
/* 434:434 */     FluidTankInfo[] info = new FluidTankInfo[getTanks().length];
/* 435:436 */     for (int i = 0; i < getTanks().length; i++) {
/* 436:437 */       info[i] = getTanks()[i].getInfo();
/* 437:    */     }
/* 438:440 */     return info;
/* 439:    */   }
/* 440:    */   
/* 441:    */   public boolean canExtractItem(int i, ItemStack itemstack, int j)
/* 442:    */   {
/* 443:507 */     return true;
/* 444:    */   }
/* 445:    */   
/* 446:    */   public void readInvFromTags(NBTTagCompound tags)
/* 447:    */   {
/* 448:511 */     if (tags.hasKey("Energy")) {
/* 449:512 */       getStorage().readFromNBT(tags);
/* 450:    */     }
/* 451:    */   }
/* 452:    */   
/* 453:    */   public void writeInvToTags(NBTTagCompound tags)
/* 454:    */   {
/* 455:517 */     if (getStorage().getEnergyStored() > 0) {
/* 456:518 */       getStorage().writeToNBT(tags);
/* 457:    */     }
/* 458:    */   }
/* 459:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.generators.TileEntityGenerator
 * JD-Core Version:    0.7.0.1
 */