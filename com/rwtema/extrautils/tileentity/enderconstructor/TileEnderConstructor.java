/*   1:    */ package com.rwtema.extrautils.tileentity.enderconstructor;
/*   2:    */ 
/*   3:    */ import cofh.api.energy.EnergyStorage;
/*   4:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   5:    */ import com.rwtema.extrautils.helper.XURandom;
/*   6:    */ import com.rwtema.extrautils.sounds.ISoundTile;
/*   7:    */ import com.rwtema.extrautils.sounds.Sounds;
/*   8:    */ import com.rwtema.extrautils.tileentity.enderquarry.IChunkLoad;
/*   9:    */ import java.util.Random;
/*  10:    */ import net.minecraft.entity.player.EntityPlayer;
/*  11:    */ import net.minecraft.inventory.ISidedInventory;
/*  12:    */ import net.minecraft.item.ItemStack;
/*  13:    */ import net.minecraft.nbt.NBTTagCompound;
/*  14:    */ import net.minecraft.tileentity.TileEntity;
/*  15:    */ import net.minecraft.util.ResourceLocation;
/*  16:    */ import net.minecraft.world.World;
/*  17:    */ 
/*  18:    */ public class TileEnderConstructor
/*  19:    */   extends TileEntity
/*  20:    */   implements IEnderFluxHandler, ISidedInventory, IChunkLoad, ISoundTile
/*  21:    */ {
/*  22: 20 */   static Random rand = ;
/*  23:    */   public EnergyStorage energy;
/*  24:    */   public InventoryKraft inv;
/*  25:    */   public ItemStack outputslot;
/*  26:    */   int coolDown;
/*  27:    */   ResourceLocation location;
/*  28:    */   
/*  29:    */   public void setWorldObj(World p_145834_1_)
/*  30:    */   {
/*  31: 29 */     super.setWorldObj(p_145834_1_);
/*  32: 31 */     if ((p_145834_1_ != null) && (p_145834_1_.isClient)) {
/*  33: 32 */       Sounds.registerSoundTile(this);
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void readFromNBT(NBTTagCompound par1NBTTagCompound)
/*  38:    */   {
/*  39: 38 */     super.readFromNBT(par1NBTTagCompound);
/*  40: 39 */     this.energy.readFromNBT(par1NBTTagCompound);
/*  41: 40 */     this.inv.readFromNBT(par1NBTTagCompound);
/*  42:    */     
/*  43: 42 */     this.outputslot = ItemStack.loadItemStackFromNBT(par1NBTTagCompound.getCompoundTag("output"));
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void onChunkLoad()
/*  47:    */   {
/*  48: 47 */     this.inv.onInventoryChanged();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void onInventoryChanged()
/*  52:    */   {
/*  53: 52 */     if (this.worldObj != null)
/*  54:    */     {
/*  55: 53 */       super.onInventoryChanged();
/*  56: 54 */       this.inv.onInventoryChanged();
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getSizeInventory()
/*  61:    */   {
/*  62: 60 */     return 10;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public ItemStack getStackInSlot(int i)
/*  66:    */   {
/*  67: 65 */     if (i >= 9) {
/*  68: 66 */       return this.outputslot;
/*  69:    */     }
/*  70: 68 */     return this.inv.matrix.getStackInSlot(i);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public ItemStack decrStackSize(int i, int j)
/*  74:    */   {
/*  75: 73 */     if (i != 9) {
/*  76: 74 */       return null;
/*  77:    */     }
/*  78: 76 */     if (this.outputslot == null) {
/*  79: 77 */       return null;
/*  80:    */     }
/*  81:    */     ItemStack itemstack;
/*  82: 81 */     if (this.outputslot.stackSize <= j)
/*  83:    */     {
/*  84: 82 */       ItemStack itemstack = this.outputslot;
/*  85: 83 */       this.outputslot = null;
/*  86: 84 */       onInventoryChanged();
/*  87:    */     }
/*  88:    */     else
/*  89:    */     {
/*  90: 86 */       itemstack = this.outputslot.splitStack(j);
/*  91: 88 */       if (this.outputslot.stackSize == 0) {
/*  92: 89 */         this.outputslot = null;
/*  93:    */       }
/*  94: 92 */       onInventoryChanged();
/*  95:    */     }
/*  96: 94 */     return itemstack;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public ItemStack getStackInSlotOnClosing(int i)
/* 100:    */   {
/* 101: 99 */     return getStackInSlot(i);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setInventorySlotContents(int i, ItemStack itemstack)
/* 105:    */   {
/* 106:104 */     if (i == 9) {
/* 107:105 */       this.outputslot = itemstack;
/* 108:    */     } else {
/* 109:107 */       this.inv.setInventorySlotContents(i, itemstack);
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String getInventoryName()
/* 114:    */   {
/* 115:112 */     return this.inv.getInventoryName();
/* 116:    */   }
/* 117:    */   
/* 118:    */   public boolean isInventoryNameLocalized()
/* 119:    */   {
/* 120:117 */     return false;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public int getInventoryStackLimit()
/* 124:    */   {
/* 125:122 */     return 64;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public boolean isUseableByPlayer(EntityPlayer entityplayer)
/* 129:    */   {
/* 130:127 */     return true;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public boolean isItemValidForSlot(int i, ItemStack itemstack)
/* 134:    */   {
/* 135:142 */     if (i == 9) {
/* 136:143 */       return false;
/* 137:    */     }
/* 138:145 */     if ((this.inv.getStackInSlot(i) == null) || (itemstack == null)) {
/* 139:146 */       return false;
/* 140:    */     }
/* 141:148 */     return XUHelper.canItemsStack(itemstack, this.inv.getStackInSlot(i));
/* 142:    */   }
/* 143:    */   
/* 144:    */   public int[] getAccessibleSlotsFromSide(int var1)
/* 145:    */   {
/* 146:153 */     return new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
/* 147:    */   }
/* 148:    */   
/* 149:    */   public boolean canInsertItem(int i, ItemStack itemstack, int j)
/* 150:    */   {
/* 151:158 */     return false;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public boolean canExtractItem(int i, ItemStack itemstack, int j)
/* 155:    */   {
/* 156:163 */     return i == 9;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void writeToNBT(NBTTagCompound par1NBTTagCompound)
/* 160:    */   {
/* 161:168 */     super.writeToNBT(par1NBTTagCompound);
/* 162:169 */     this.energy.writeToNBT(par1NBTTagCompound);
/* 163:170 */     this.inv.writeToNBT(par1NBTTagCompound);
/* 164:172 */     if (this.outputslot != null)
/* 165:    */     {
/* 166:173 */       NBTTagCompound output = new NBTTagCompound();
/* 167:174 */       this.outputslot.writeToNBT(output);
/* 168:175 */       par1NBTTagCompound.setTag("output", output);
/* 169:    */     }
/* 170:    */   }
/* 171:    */   
/* 172:    */   public int getDisplayProgress()
/* 173:    */   {
/* 174:181 */     return this.energy.getEnergyStored() * 22 / this.energy.getMaxEnergyStored();
/* 175:    */   }
/* 176:    */   
/* 177:    */   public boolean isActive()
/* 178:    */   {
/* 179:186 */     return ((getBlockMetadata() == 1) && (getWorldObj().isClient)) || ((!getWorldObj().isClient) && (canAddMorez()));
/* 180:    */   }
/* 181:    */   
/* 182:    */   public int recieveEnergy(int amount, Transfer simulate)
/* 183:    */   {
/* 184:191 */     return this.energy.receiveEnergy(amount, simulate == Transfer.SIMULATE);
/* 185:    */   }
/* 186:    */   
/* 187:    */   public float getAmountRequested()
/* 188:    */   {
/* 189:196 */     return this.energy.getMaxEnergyStored() - this.energy.getEnergyStored();
/* 190:    */   }
/* 191:    */   
/* 192:    */   public boolean canAddMorez()
/* 193:    */   {
/* 194:200 */     ItemStack item = this.inv.getStackInSlot(9);
/* 195:201 */     if (item == null) {
/* 196:202 */       return false;
/* 197:    */     }
/* 198:204 */     if (this.outputslot == null) {
/* 199:205 */       return true;
/* 200:    */     }
/* 201:207 */     if (!XUHelper.canItemsStack(item, this.outputslot)) {
/* 202:208 */       return false;
/* 203:    */     }
/* 204:210 */     return (this.outputslot.stackSize + item.stackSize <= this.outputslot.getMaxStackSize()) && (this.outputslot.stackSize + item.stackSize <= getInventoryStackLimit());
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void updateEntity()
/* 208:    */   {
/* 209:216 */     if ((this.worldObj.isClient) && (getBlockMetadata() == 1))
/* 210:    */     {
/* 211:217 */       double dx1 = this.xCoord + rand.nextDouble();
/* 212:218 */       double dy1 = this.yCoord + rand.nextDouble();
/* 213:219 */       double dz1 = this.zCoord + rand.nextDouble();
/* 214:220 */       double dx2 = this.xCoord + rand.nextDouble();
/* 215:221 */       double dy2 = this.yCoord + rand.nextDouble();
/* 216:222 */       double dz2 = this.zCoord + rand.nextDouble();
/* 217:223 */       this.worldObj.spawnParticle("portal", dx1, dy1, dz1, dx2 - dx1, dy2 - dy1, dz2 - dz1);
/* 218:    */     }
/* 219:226 */     if (!this.worldObj.isClient)
/* 220:    */     {
/* 221:227 */       int newMeta = -1;
/* 222:229 */       if ((this.energy.getEnergyStored() == this.energy.getMaxEnergyStored()) && (canAddMorez()))
/* 223:    */       {
/* 224:230 */         ItemStack result = this.inv.result.getStackInSlot(0).copy();
/* 225:232 */         for (int i = 0; i < 9; i++) {
/* 226:233 */           this.inv.matrix.decrStackSize(i, 1);
/* 227:    */         }
/* 228:235 */         this.inv.result.markDirty(this.inv.matrix);
/* 229:237 */         if (this.outputslot == null) {
/* 230:238 */           this.outputslot = result;
/* 231:    */         } else {
/* 232:240 */           this.outputslot.stackSize += result.stackSize;
/* 233:    */         }
/* 234:242 */         this.energy.setEnergyStored(0);
/* 235:244 */         if (!canAddMorez()) {
/* 236:245 */           newMeta = 4;
/* 237:    */         }
/* 238:    */       }
/* 239:248 */       if (this.energy.getEnergyStored() > 0) {
/* 240:249 */         if (canAddMorez())
/* 241:    */         {
/* 242:250 */           newMeta = 1;
/* 243:251 */           this.coolDown = 20;
/* 244:    */         }
/* 245:    */         else
/* 246:    */         {
/* 247:253 */           this.energy.extractEnergy(1, false);
/* 248:    */         }
/* 249:    */       }
/* 250:257 */       if (this.coolDown > 0)
/* 251:    */       {
/* 252:258 */         this.coolDown -= 1;
/* 253:260 */         if (this.coolDown == 0) {
/* 254:261 */           newMeta = 0;
/* 255:    */         }
/* 256:    */       }
/* 257:265 */       if ((newMeta != -1) && (newMeta != getBlockMetadata())) {
/* 258:266 */         this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, newMeta, 2);
/* 259:    */       }
/* 260:    */     }
/* 261:    */   }
/* 262:    */   
/* 263:    */   public boolean shouldSoundPlay()
/* 264:    */   {
/* 265:272 */     return getBlockMetadata() == 1;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public TileEnderConstructor()
/* 269:    */   {
/* 270: 21 */     this.energy = new CustomEnergy(20000);
/* 271: 22 */     this.inv = new InventoryKraft(this);
/* 272: 23 */     this.outputslot = null;
/* 273: 24 */     this.coolDown = 0;
/* 274:    */     
/* 275:    */ 
/* 276:    */ 
/* 277:    */ 
/* 278:    */ 
/* 279:    */ 
/* 280:    */ 
/* 281:    */ 
/* 282:    */ 
/* 283:    */ 
/* 284:    */ 
/* 285:    */ 
/* 286:    */ 
/* 287:    */ 
/* 288:    */ 
/* 289:    */ 
/* 290:    */ 
/* 291:    */ 
/* 292:    */ 
/* 293:    */ 
/* 294:    */ 
/* 295:    */ 
/* 296:    */ 
/* 297:    */ 
/* 298:    */ 
/* 299:    */ 
/* 300:    */ 
/* 301:    */ 
/* 302:    */ 
/* 303:    */ 
/* 304:    */ 
/* 305:    */ 
/* 306:    */ 
/* 307:    */ 
/* 308:    */ 
/* 309:    */ 
/* 310:    */ 
/* 311:    */ 
/* 312:    */ 
/* 313:    */ 
/* 314:    */ 
/* 315:    */ 
/* 316:    */ 
/* 317:    */ 
/* 318:    */ 
/* 319:    */ 
/* 320:    */ 
/* 321:    */ 
/* 322:    */ 
/* 323:    */ 
/* 324:    */ 
/* 325:    */ 
/* 326:    */ 
/* 327:    */ 
/* 328:    */ 
/* 329:    */ 
/* 330:    */ 
/* 331:    */ 
/* 332:    */ 
/* 333:    */ 
/* 334:    */ 
/* 335:    */ 
/* 336:    */ 
/* 337:    */ 
/* 338:    */ 
/* 339:    */ 
/* 340:    */ 
/* 341:    */ 
/* 342:    */ 
/* 343:    */ 
/* 344:    */ 
/* 345:    */ 
/* 346:    */ 
/* 347:    */ 
/* 348:    */ 
/* 349:    */ 
/* 350:    */ 
/* 351:    */ 
/* 352:    */ 
/* 353:    */ 
/* 354:    */ 
/* 355:    */ 
/* 356:    */ 
/* 357:    */ 
/* 358:    */ 
/* 359:    */ 
/* 360:    */ 
/* 361:    */ 
/* 362:    */ 
/* 363:    */ 
/* 364:    */ 
/* 365:    */ 
/* 366:    */ 
/* 367:    */ 
/* 368:    */ 
/* 369:    */ 
/* 370:    */ 
/* 371:    */ 
/* 372:    */ 
/* 373:    */ 
/* 374:    */ 
/* 375:    */ 
/* 376:    */ 
/* 377:    */ 
/* 378:    */ 
/* 379:    */ 
/* 380:    */ 
/* 381:    */ 
/* 382:    */ 
/* 383:    */ 
/* 384:    */ 
/* 385:    */ 
/* 386:    */ 
/* 387:    */ 
/* 388:    */ 
/* 389:    */ 
/* 390:    */ 
/* 391:    */ 
/* 392:    */ 
/* 393:    */ 
/* 394:    */ 
/* 395:    */ 
/* 396:    */ 
/* 397:    */ 
/* 398:    */ 
/* 399:    */ 
/* 400:    */ 
/* 401:    */ 
/* 402:    */ 
/* 403:    */ 
/* 404:    */ 
/* 405:    */ 
/* 406:    */ 
/* 407:    */ 
/* 408:    */ 
/* 409:    */ 
/* 410:    */ 
/* 411:    */ 
/* 412:    */ 
/* 413:    */ 
/* 414:    */ 
/* 415:    */ 
/* 416:    */ 
/* 417:    */ 
/* 418:    */ 
/* 419:    */ 
/* 420:    */ 
/* 421:    */ 
/* 422:    */ 
/* 423:    */ 
/* 424:    */ 
/* 425:    */ 
/* 426:    */ 
/* 427:    */ 
/* 428:    */ 
/* 429:    */ 
/* 430:    */ 
/* 431:    */ 
/* 432:    */ 
/* 433:    */ 
/* 434:    */ 
/* 435:    */ 
/* 436:    */ 
/* 437:    */ 
/* 438:    */ 
/* 439:    */ 
/* 440:    */ 
/* 441:    */ 
/* 442:    */ 
/* 443:    */ 
/* 444:    */ 
/* 445:    */ 
/* 446:    */ 
/* 447:    */ 
/* 448:    */ 
/* 449:    */ 
/* 450:    */ 
/* 451:    */ 
/* 452:    */ 
/* 453:    */ 
/* 454:    */ 
/* 455:    */ 
/* 456:    */ 
/* 457:    */ 
/* 458:    */ 
/* 459:    */ 
/* 460:    */ 
/* 461:    */ 
/* 462:    */ 
/* 463:    */ 
/* 464:    */ 
/* 465:    */ 
/* 466:    */ 
/* 467:    */ 
/* 468:    */ 
/* 469:    */ 
/* 470:    */ 
/* 471:    */ 
/* 472:    */ 
/* 473:    */ 
/* 474:    */ 
/* 475:    */ 
/* 476:    */ 
/* 477:    */ 
/* 478:    */ 
/* 479:    */ 
/* 480:    */ 
/* 481:    */ 
/* 482:    */ 
/* 483:    */ 
/* 484:    */ 
/* 485:    */ 
/* 486:    */ 
/* 487:    */ 
/* 488:    */ 
/* 489:    */ 
/* 490:    */ 
/* 491:    */ 
/* 492:    */ 
/* 493:    */ 
/* 494:    */ 
/* 495:    */ 
/* 496:    */ 
/* 497:    */ 
/* 498:    */ 
/* 499:    */ 
/* 500:    */ 
/* 501:    */ 
/* 502:    */ 
/* 503:    */ 
/* 504:    */ 
/* 505:    */ 
/* 506:    */ 
/* 507:    */ 
/* 508:    */ 
/* 509:    */ 
/* 510:    */ 
/* 511:    */ 
/* 512:    */ 
/* 513:    */ 
/* 514:    */ 
/* 515:    */ 
/* 516:    */ 
/* 517:    */ 
/* 518:    */ 
/* 519:    */ 
/* 520:    */ 
/* 521:    */ 
/* 522:    */ 
/* 523:    */ 
/* 524:275 */     this.location = new ResourceLocation("extrautils", "ambient.qed");
/* 525:    */   }
/* 526:    */   
/* 527:    */   public ResourceLocation getSound()
/* 528:    */   {
/* 529:279 */     return this.location;
/* 530:    */   }
/* 531:    */   
/* 532:    */   public TileEntity getTile()
/* 533:    */   {
/* 534:284 */     return this;
/* 535:    */   }
/* 536:    */   
/* 537:    */   public void openInventory() {}
/* 538:    */   
/* 539:    */   public void closeInventory() {}
/* 540:    */   
/* 541:    */   public static class CustomEnergy
/* 542:    */     extends EnergyStorage
/* 543:    */   {
/* 544:    */     public CustomEnergy(int capacity)
/* 545:    */     {
/* 546:289 */       super();
/* 547:    */     }
/* 548:    */     
/* 549:    */     public EnergyStorage readFromNBT(NBTTagCompound nbt)
/* 550:    */     {
/* 551:294 */       return super.readFromNBT(nbt);
/* 552:    */     }
/* 553:    */     
/* 554:    */     public void setEnergyStored(int energy)
/* 555:    */     {
/* 556:299 */       super.setEnergyStored(energy);
/* 557:    */     }
/* 558:    */     
/* 559:    */     public int receiveEnergy(int maxReceive, boolean simulate)
/* 560:    */     {
/* 561:304 */       return super.receiveEnergy(maxReceive, simulate);
/* 562:    */     }
/* 563:    */     
/* 564:    */     public int extractEnergy(int maxExtract, boolean simulate)
/* 565:    */     {
/* 566:309 */       return super.extractEnergy(maxExtract, simulate);
/* 567:    */     }
/* 568:    */     
/* 569:    */     public void setCapacity(int capacity)
/* 570:    */     {
/* 571:314 */       super.setCapacity(capacity);
/* 572:    */     }
/* 573:    */   }
/* 574:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.enderconstructor.TileEnderConstructor
 * JD-Core Version:    0.7.0.1
 */