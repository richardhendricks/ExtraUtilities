/*   1:    */ package com.rwtema.extrautils.tileentity.transfernodes;
/*   2:    */ 
/*   3:    */ import cofh.api.energy.EnergyStorage;
/*   4:    */ import cofh.api.energy.IEnergyContainerItem;
/*   5:    */ import cofh.api.energy.IEnergyHandler;
/*   6:    */ import cofh.api.energy.IEnergyProvider;
/*   7:    */ import cofh.api.energy.IEnergyReceiver;
/*   8:    */ import com.rwtema.extrautils.ChunkPos;
/*   9:    */ import com.rwtema.extrautils.block.Box;
/*  10:    */ import com.rwtema.extrautils.block.BoxModel;
/*  11:    */ import com.rwtema.extrautils.modintegration.IC2EnergyHandler;
/*  12:    */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.EnergyBuffer;
/*  13:    */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INode;
/*  14:    */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeBuffer;
/*  15:    */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeEnergy;
/*  16:    */ import com.rwtema.extrautils.tileentity.transfernodes.pipes.IPipe;
/*  17:    */ import cpw.mods.fml.common.Loader;
/*  18:    */ import java.util.ArrayList;
/*  19:    */ import java.util.Collections;
/*  20:    */ import java.util.List;
/*  21:    */ import net.minecraft.block.Block;
/*  22:    */ import net.minecraft.item.ItemStack;
/*  23:    */ import net.minecraft.nbt.NBTTagCompound;
/*  24:    */ import net.minecraft.tileentity.TileEntity;
/*  25:    */ import net.minecraft.world.World;
/*  26:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  27:    */ 
/*  28:    */ public class TileEntityTransferNodeEnergy
/*  29:    */   extends TileEntityTransferNode
/*  30:    */   implements INodeEnergy, IEnergyHandler
/*  31:    */ {
/*  32:    */   public static final int RF_PER_MJ = 10;
/*  33:    */   public static final int RF_PER_EU = 4;
/*  34:    */   public static final double RFtoBC = 0.1D;
/*  35:    */   public static final double BCtoRF = 10.0D;
/*  36: 33 */   public EnergyStorage powerHandler = new EnergyStorage(10000);
/*  37: 34 */   public IC2EnergyHandler powerHandlerIC2 = null;
/*  38: 35 */   public int powerInserted = 0;
/*  39: 36 */   public boolean lastStep = false;
/*  40: 37 */   public List<ChunkPos> searchLocations = new ArrayList();
/*  41: 38 */   public List<EnergyPosition> clientele = new ArrayList();
/*  42: 39 */   public List<EnergyPosition> clientele_temp = new ArrayList();
/*  43: 40 */   public Object battery = null;
/*  44:    */   private int search_i;
/*  45:    */   
/*  46:    */   public TileEntityTransferNodeEnergy()
/*  47:    */   {
/*  48: 51 */     this("Energy", new EnergyBuffer());
/*  49:    */   }
/*  50:    */   
/*  51:    */   public TileEntityTransferNodeEnergy(String s, INodeBuffer buffer)
/*  52:    */   {
/*  53: 56 */     super(s, buffer);this.pr = 1.0F;this.pg = 0.7F;this.pb = 0.0F;
/*  54: 58 */     if (Loader.isModLoaded("IC2")) {
/*  55: 59 */       this.powerHandlerIC2 = new IC2EnergyHandler(this, 512, 1);
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static double RFtoBC(int RF)
/*  60:    */   {
/*  61: 64 */     return RF * 0.1D;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static int toRF2(double BC)
/*  65:    */   {
/*  66: 68 */     return (int)Math.floor(BC * 10.0D);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public static int toRF3(double BC)
/*  70:    */   {
/*  71: 72 */     return (int)Math.round(BC * 10.0D);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static int BCtoRF(double BC)
/*  75:    */   {
/*  76: 76 */     return (int)Math.floor(BC * 10.0D);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static double toIC2(int k)
/*  80:    */   {
/*  81: 80 */     return k / 4.0D;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static int fromIC2(double t)
/*  85:    */   {
/*  86: 84 */     return (int)Math.floor(t * 4.0D);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int numMachines()
/*  90:    */   {
/*  91: 88 */     return this.clientele.size();
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void readFromNBT(NBTTagCompound tag)
/*  95:    */   {
/*  96: 96 */     super.readFromNBT(tag);
/*  97: 97 */     this.clientele.clear();
/*  98: 98 */     int i = 0;
/*  99:100 */     while (tag.hasKey("cx" + i))
/* 100:    */     {
/* 101:101 */       this.clientele.add(new EnergyPosition(tag.getInteger("cx" + i), tag.getInteger("cy" + i), tag.getInteger("cz" + i), tag.getByte("cs" + i), tag.getBoolean("ce")));
/* 102:    */       
/* 103:103 */       this.clientele_temp.add(new EnergyPosition(tag.getInteger("cx" + i), tag.getInteger("cy" + i), tag.getInteger("cz" + i), tag.getByte("cs" + i), tag.getBoolean("ce")));
/* 104:    */       
/* 105:105 */       i++;
/* 106:    */     }
/* 107:108 */     this.powerHandler.readFromNBT(tag);
/* 108:110 */     if (this.powerHandlerIC2 != null) {
/* 109:111 */       this.powerHandlerIC2.readFromNBT(tag);
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void writeToNBT(NBTTagCompound tag)
/* 114:    */   {
/* 115:119 */     super.writeToNBT(tag);
/* 116:121 */     for (int i = 0; i < this.clientele.size(); i++)
/* 117:    */     {
/* 118:122 */       tag.setInteger("cx" + i, ((EnergyPosition)this.clientele.get(i)).x);
/* 119:123 */       tag.setInteger("cy" + i, ((EnergyPosition)this.clientele.get(i)).y);
/* 120:124 */       tag.setInteger("cz" + i, ((EnergyPosition)this.clientele.get(i)).z);
/* 121:125 */       tag.setByte("cs" + i, ((EnergyPosition)this.clientele.get(i)).side);
/* 122:126 */       tag.setBoolean("ce", ((EnergyPosition)this.clientele.get(i)).extract);
/* 123:    */     }
/* 124:129 */     this.powerHandler.writeToNBT(tag);
/* 125:132 */     if (this.powerHandlerIC2 != null)
/* 126:    */     {
/* 127:133 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 128:    */       
/* 129:135 */       this.powerHandlerIC2.writeToNBT(nbttagcompound1);
/* 130:136 */       tag.setTag("buffer", nbttagcompound1);
/* 131:    */     }
/* 132:    */   }
/* 133:    */   
/* 134:    */   public boolean checkRedstone()
/* 135:    */   {
/* 136:142 */     return false;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void processBuffer()
/* 140:    */   {
/* 141:149 */     if (this.powerHandlerIC2 != null)
/* 142:    */     {
/* 143:150 */       this.powerHandlerIC2.updateEntity();
/* 144:151 */       this.powerHandlerIC2.useEnergy(toIC2(this.powerHandler.receiveEnergy(fromIC2(this.powerHandlerIC2.getEnergyStored()), false)));
/* 145:    */     }
/* 146:154 */     if (this.powerHandler.getEnergyStored() > 0) {
/* 147:155 */       for (int i = 0; i < this.upgrades.getSizeInventory(); i++)
/* 148:    */       {
/* 149:156 */         ItemStack itemstack = this.upgrades.getStackInSlot(i);
/* 150:157 */         if ((itemstack != null) && ((itemstack.getItem() instanceof IEnergyContainerItem)))
/* 151:    */         {
/* 152:158 */           int a = ((IEnergyContainerItem)itemstack.getItem()).receiveEnergy(itemstack, this.powerHandler.extractEnergy(10000, true), false);
/* 153:159 */           this.powerHandler.extractEnergy(a, false);
/* 154:160 */           if (a > 0) {
/* 155:161 */             onInventoryChanged();
/* 156:    */           }
/* 157:    */         }
/* 158:    */       }
/* 159:    */     }
/* 160:165 */     if (upgradeNo(4) > 0) {
/* 161:166 */       this.powerHandler.setEnergyStored(6400000);
/* 162:    */     }
/* 163:169 */     if (this.coolDown > 0) {
/* 164:170 */       this.coolDown -= this.stepCoolDown;
/* 165:    */     }
/* 166:173 */     while (this.coolDown <= 0)
/* 167:    */     {
/* 168:174 */       this.coolDown += baseMaxCoolDown;
/* 169:175 */       findMachines();
/* 170:    */     }
/* 171:178 */     sendEnergy();
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void onChunkUnload()
/* 175:    */   {
/* 176:184 */     if (this.powerHandlerIC2 != null) {
/* 177:185 */       this.powerHandlerIC2.onChunkUnload();
/* 178:    */     }
/* 179:186 */     super.onChunkUnload();
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void invalidate()
/* 183:    */   {
/* 184:191 */     if (this.powerHandlerIC2 != null) {
/* 185:192 */       this.powerHandlerIC2.invalidate();
/* 186:    */     }
/* 187:193 */     super.invalidate();
/* 188:    */   }
/* 189:    */   
/* 190:    */   private void sendEnergy()
/* 191:    */   {
/* 192:197 */     if (this.clientele.size() > 0)
/* 193:    */     {
/* 194:198 */       Collections.shuffle(this.clientele);
/* 195:199 */       List<EnergyPosition> repeat = new ArrayList();
/* 196:200 */       int m1 = this.powerHandler.getEnergyStored() / this.clientele.size();
/* 197:202 */       for (EnergyPosition client : this.clientele)
/* 198:    */       {
/* 199:203 */         int x = this.xCoord + client.x;
/* 200:204 */         int y = this.yCoord + client.y;
/* 201:205 */         int z = this.zCoord + client.z;
/* 202:206 */         TileEntity tile = this.worldObj.getTileEntity(x, y, z);
/* 203:207 */         ForgeDirection from = ForgeDirection.getOrientation(client.side);
/* 204:208 */         if (client.extract)
/* 205:    */         {
/* 206:209 */           if (((tile instanceof IEnergyProvider)) && (((IEnergyProvider)tile).canConnectEnergy(from)))
/* 207:    */           {
/* 208:210 */             int a = this.powerHandler.receiveEnergy(((IEnergyProvider)tile).extractEnergy(from, this.powerHandler.getMaxEnergyStored(), true), true);
/* 209:211 */             if (a > 0) {
/* 210:212 */               this.powerHandler.receiveEnergy(((IEnergyProvider)tile).extractEnergy(from, a, false), false);
/* 211:    */             }
/* 212:    */           }
/* 213:    */         }
/* 214:215 */         else if ((this.powerHandler.getEnergyStored() > 0) && 
/* 215:216 */           ((tile instanceof IEnergyReceiver)) && (((IEnergyReceiver)tile).canConnectEnergy(from)))
/* 216:    */         {
/* 217:217 */           IEnergyReceiver machine = (IEnergyReceiver)tile;
/* 218:218 */           int a = machine.receiveEnergy(from, this.powerHandler.getEnergyStored(), true);
/* 219:220 */           if (a > m1)
/* 220:    */           {
/* 221:221 */             if (m1 >= 1)
/* 222:    */             {
/* 223:222 */               a = machine.receiveEnergy(from, this.powerHandler.extractEnergy(m1, true), false);
/* 224:223 */               this.powerHandler.extractEnergy(a, false);
/* 225:    */             }
/* 226:226 */             repeat.add(client);
/* 227:    */           }
/* 228:227 */           else if (a > 0)
/* 229:    */           {
/* 230:228 */             a = machine.receiveEnergy(from, this.powerHandler.extractEnergy(a, true), false);
/* 231:229 */             this.powerHandler.extractEnergy(a, false);
/* 232:    */           }
/* 233:    */         }
/* 234:    */       }
/* 235:235 */       if (this.powerHandler.getEnergyStored() > 0) {
/* 236:236 */         for (EnergyPosition aRepeat : repeat)
/* 237:    */         {
/* 238:237 */           int x = this.xCoord + aRepeat.x;
/* 239:238 */           int y = this.yCoord + aRepeat.y;
/* 240:239 */           int z = this.zCoord + aRepeat.z;
/* 241:240 */           TileEntity tile = this.worldObj.getTileEntity(x, y, z);
/* 242:    */           
/* 243:    */ 
/* 244:243 */           ForgeDirection from = ForgeDirection.getOrientation(aRepeat.side);
/* 245:244 */           if ((tile instanceof IEnergyReceiver))
/* 246:    */           {
/* 247:245 */             IEnergyReceiver machine = (IEnergyReceiver)tile;
/* 248:246 */             int e = machine.receiveEnergy(from, this.powerHandler.getEnergyStored(), true);
/* 249:248 */             if (e > 0)
/* 250:    */             {
/* 251:249 */               int a = machine.receiveEnergy(from, this.powerHandler.extractEnergy(e, true), false);
/* 252:250 */               this.powerHandler.extractEnergy(a, false);
/* 253:    */             }
/* 254:    */           }
/* 255:    */         }
/* 256:    */       }
/* 257:    */     }
/* 258:    */   }
/* 259:    */   
/* 260:    */   public int hashCode()
/* 261:    */   {
/* 262:261 */     return this.xCoord * 8976890 + this.yCoord * 981131 + this.zCoord;
/* 263:    */   }
/* 264:    */   
/* 265:    */   private void findMachines()
/* 266:    */   {
/* 267:266 */     this.search_i += 1;
/* 268:268 */     if ((this.searchLocations.size() == 0) || (this.search_i >= this.searchLocations.size()))
/* 269:    */     {
/* 270:269 */       this.clientele.clear();
/* 271:270 */       this.clientele.addAll(this.clientele_temp);
/* 272:271 */       this.clientele_temp.clear();
/* 273:272 */       this.search_i = 0;
/* 274:273 */       this.searchLocations.clear();
/* 275:274 */       this.searchLocations.add(new ChunkPos(0, 0, 0));
/* 276:    */     }
/* 277:277 */     this.pipe_x = ((ChunkPos)this.searchLocations.get(this.search_i)).x;
/* 278:278 */     this.pipe_y = ((ChunkPos)this.searchLocations.get(this.search_i)).y;
/* 279:279 */     this.pipe_z = ((ChunkPos)this.searchLocations.get(this.search_i)).z;
/* 280:280 */     int x = this.pipe_x + this.xCoord;
/* 281:281 */     int y = this.pipe_y + this.yCoord;
/* 282:282 */     int z = this.pipe_z + this.zCoord;
/* 283:283 */     sendParticleUpdate();
/* 284:284 */     Block id = this.worldObj.getBlock(x, y, z);
/* 285:285 */     IPipe pipeBlock = TNHelper.getPipe(this.worldObj, this.xCoord + this.pipe_x, this.yCoord + this.pipe_y, this.zCoord + this.pipe_z);
/* 286:287 */     if (pipeBlock != null) {
/* 287:288 */       for (int i = 0; i < 6; i++) {
/* 288:289 */         if ((pipeBlock.shouldConnectToTile(this.worldObj, x, y, z, ForgeDirection.getOrientation(i))) || (((pipeBlock instanceof INode)) && (((INode)pipeBlock).getNodeDir() == ForgeDirection.getOrientation(i))))
/* 289:    */         {
/* 290:291 */           TileEntity tile = this.worldObj.getTileEntity(x + net.minecraft.util.Facing.offsetsXForSide[i], y + net.minecraft.util.Facing.offsetsYForSide[i], z + net.minecraft.util.Facing.offsetsZForSide[i]);
/* 291:293 */           if (TNHelper.isEnergy(tile, ForgeDirection.getOrientation(i).getOpposite()))
/* 292:    */           {
/* 293:296 */             EnergyPosition pos = new EnergyPosition(this.pipe_x + net.minecraft.util.Facing.offsetsXForSide[i], this.pipe_y + net.minecraft.util.Facing.offsetsYForSide[i], this.pipe_z + net.minecraft.util.Facing.offsetsZForSide[i], (byte)net.minecraft.util.Facing.oppositeSide[i], "Energy_Extract".equals(pipeBlock.getPipeType()));
/* 294:299 */             if (!this.clientele_temp.contains(pos)) {
/* 295:300 */               this.clientele_temp.add(pos);
/* 296:    */             }
/* 297:302 */             if (!this.clientele.contains(pos)) {
/* 298:303 */               this.clientele.add(pos);
/* 299:    */             }
/* 300:    */           }
/* 301:    */         }
/* 302:305 */         else if ((TNHelper.doesPipeConnect(this.worldObj, x, y, z, ForgeDirection.getOrientation(i))) && 
/* 303:306 */           (!this.searchLocations.contains(new ChunkPos(this.pipe_x + net.minecraft.util.Facing.offsetsXForSide[i], this.pipe_y + net.minecraft.util.Facing.offsetsYForSide[i], this.pipe_z + net.minecraft.util.Facing.offsetsZForSide[i]))))
/* 304:    */         {
/* 305:307 */           this.searchLocations.add(new ChunkPos(this.pipe_x + net.minecraft.util.Facing.offsetsXForSide[i], this.pipe_y + net.minecraft.util.Facing.offsetsYForSide[i], this.pipe_z + net.minecraft.util.Facing.offsetsZForSide[i]));
/* 306:    */         }
/* 307:    */       }
/* 308:    */     }
/* 309:    */   }
/* 310:    */   
/* 311:    */   public ForgeDirection getNodeDir()
/* 312:    */   {
/* 313:315 */     return ForgeDirection.UNKNOWN;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public void resetSearch()
/* 317:    */   {
/* 318:320 */     this.powerInserted = 0;
/* 319:321 */     super.resetSearch();
/* 320:    */   }
/* 321:    */   
/* 322:    */   public TileEntityTransferNodeEnergy getNode()
/* 323:    */   {
/* 324:326 */     return this;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public BoxModel getModel(ForgeDirection dir)
/* 328:    */   {
/* 329:331 */     BoxModel boxes = new BoxModel();
/* 330:332 */     boxes.add(new Box(0.1875F, 0.3125F, 0.3125F, 0.8125F, 0.6875F, 0.6875F));
/* 331:333 */     boxes.add(new Box(0.3125F, 0.1875F, 0.3125F, 0.6875F, 0.8125F, 0.6875F));
/* 332:334 */     boxes.add(new Box(0.3125F, 0.3125F, 0.1875F, 0.6875F, 0.6875F, 0.8125F));
/* 333:335 */     boxes.add(new Box(0.25F, 0.25F, 0.25F, 0.75F, 0.75F, 0.75F));
/* 334:336 */     return boxes;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
/* 338:    */   {
/* 339:341 */     return this.powerHandler.receiveEnergy(maxReceive, simulate);
/* 340:    */   }
/* 341:    */   
/* 342:    */   public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
/* 343:    */   {
/* 344:346 */     return 0;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public boolean canConnectEnergy(ForgeDirection from)
/* 348:    */   {
/* 349:351 */     return true;
/* 350:    */   }
/* 351:    */   
/* 352:    */   public int getEnergyStored(ForgeDirection from)
/* 353:    */   {
/* 354:356 */     return this.powerHandler.getEnergyStored();
/* 355:    */   }
/* 356:    */   
/* 357:    */   public int getMaxEnergyStored(ForgeDirection from)
/* 358:    */   {
/* 359:361 */     return this.powerHandler.getMaxEnergyStored();
/* 360:    */   }
/* 361:    */   
/* 362:    */   public static class EnergyPosition
/* 363:    */     extends ChunkPos
/* 364:    */   {
/* 365:365 */     public byte side = 7;
/* 366:366 */     public boolean extract = false;
/* 367:    */     
/* 368:    */     public EnergyPosition(int par1, int par2, int par3, byte par4, boolean extract)
/* 369:    */     {
/* 370:369 */       super(par2, par3);
/* 371:370 */       this.side = par4;
/* 372:371 */       this.extract = extract;
/* 373:    */     }
/* 374:    */     
/* 375:    */     public boolean equals(Object o)
/* 376:    */     {
/* 377:376 */       if (this == o) {
/* 378:376 */         return true;
/* 379:    */       }
/* 380:377 */       if ((o == null) || (getClass() != o.getClass())) {
/* 381:377 */         return false;
/* 382:    */       }
/* 383:378 */       if (!super.equals(o)) {
/* 384:378 */         return false;
/* 385:    */       }
/* 386:380 */       EnergyPosition that = (EnergyPosition)o;
/* 387:382 */       if (this.extract != that.extract) {
/* 388:382 */         return false;
/* 389:    */       }
/* 390:383 */       if (this.side != that.side) {
/* 391:383 */         return false;
/* 392:    */       }
/* 393:385 */       return true;
/* 394:    */     }
/* 395:    */     
/* 396:    */     public int hashCode()
/* 397:    */     {
/* 398:390 */       int result = super.hashCode();
/* 399:391 */       result = 31 * result + this.side;
/* 400:392 */       result = 31 * result + (this.extract ? 1 : 0);
/* 401:393 */       return result;
/* 402:    */     }
/* 403:    */   }
/* 404:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNodeEnergy
 * JD-Core Version:    0.7.0.1
 */