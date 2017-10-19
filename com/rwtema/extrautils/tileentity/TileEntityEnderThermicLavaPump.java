/*   1:    */ package com.rwtema.extrautils.tileentity;
/*   2:    */ 
/*   3:    */ import cofh.api.energy.EnergyStorage;
/*   4:    */ import cofh.api.energy.IEnergyHandler;
/*   5:    */ import com.rwtema.extrautils.ExtraUtils;
/*   6:    */ import com.rwtema.extrautils.ExtraUtilsMod;
/*   7:    */ import com.rwtema.extrautils.LogHelper;
/*   8:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   9:    */ import cpw.mods.fml.common.Optional.InterfaceList;
/*  10:    */ import java.util.Random;
/*  11:    */ import net.minecraft.block.Block;
/*  12:    */ import net.minecraft.entity.player.EntityPlayer;
/*  13:    */ import net.minecraft.init.Blocks;
/*  14:    */ import net.minecraft.nbt.NBTTagByte;
/*  15:    */ import net.minecraft.nbt.NBTTagCompound;
/*  16:    */ import net.minecraft.nbt.NBTTagInt;
/*  17:    */ import net.minecraft.network.Packet;
/*  18:    */ import net.minecraft.tileentity.TileEntity;
/*  19:    */ import net.minecraft.util.ChatComponentText;
/*  20:    */ import net.minecraft.world.ChunkCoordIntPair;
/*  21:    */ import net.minecraft.world.World;
/*  22:    */ import net.minecraft.world.WorldProvider;
/*  23:    */ import net.minecraftforge.common.ForgeChunkManager;
/*  24:    */ import net.minecraftforge.common.ForgeChunkManager.Ticket;
/*  25:    */ import net.minecraftforge.common.ForgeChunkManager.Type;
/*  26:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  27:    */ import net.minecraftforge.fluids.FluidStack;
/*  28:    */ import net.minecraftforge.fluids.FluidTank;
/*  29:    */ import net.minecraftforge.fluids.FluidTankInfo;
/*  30:    */ import net.minecraftforge.fluids.IFluidHandler;
/*  31:    */ import net.minecraftforge.fluids.TileFluidHandler;
/*  32:    */ 
/*  33:    */ @Optional.InterfaceList({@cpw.mods.fml.common.Optional.Interface(iface="buildcraft.api.mj.IBatteryProvider", modid="BuildCraftAPI|power")})
/*  34:    */ public class TileEntityEnderThermicLavaPump
/*  35:    */   extends TileFluidHandler
/*  36:    */   implements IFluidHandler, IEnergyHandler
/*  37:    */ {
/*  38: 29 */   public EntityPlayer owner = null;
/*  39: 30 */   public boolean finished = false;
/*  40:    */   private ForgeChunkManager.Ticket chunkTicket;
/*  41:    */   private FluidTank tank;
/*  42: 33 */   private int pump_y = -1;
/*  43: 34 */   private int chunk_x = 0;
/*  44: 35 */   private int chunk_z = 0;
/*  45: 36 */   private int b = 0;
/*  46: 37 */   private boolean find_new_block = false;
/*  47: 38 */   private boolean init = false;
/*  48: 39 */   private int chunk_no = -1;
/*  49: 40 */   private float p = 0.95F;
/*  50: 42 */   private EnergyStorage cofhEnergy = new EnergyStorage(10000);
/*  51:    */   
/*  52:    */   public TileEntityEnderThermicLavaPump()
/*  53:    */   {
/*  54: 45 */     this.tank = new FluidTank(1000);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void updateEntity()
/*  58:    */   {
/*  59: 50 */     if (this.worldObj.isClient) {
/*  60: 51 */       return;
/*  61:    */     }
/*  62: 54 */     if (this.finished)
/*  63:    */     {
/*  64: 55 */       if (this.chunkTicket != null)
/*  65:    */       {
/*  66: 56 */         ForgeChunkManager.releaseTicket(this.chunkTicket);
/*  67: 57 */         this.chunkTicket = null;
/*  68:    */       }
/*  69: 60 */       return;
/*  70:    */     }
/*  71: 63 */     if (this.chunkTicket == null)
/*  72:    */     {
/*  73: 64 */       boolean valid = false;
/*  74: 66 */       if (ExtraUtils.validDimensionsForEnderPump != null)
/*  75:    */       {
/*  76: 67 */         if (ExtraUtils.allNonVanillaDimensionsValidForEnderPump) {
/*  77: 68 */           valid = true;
/*  78:    */         }
/*  79: 71 */         for (int i = 0; i < ExtraUtils.validDimensionsForEnderPump.length; i++) {
/*  80: 72 */           if (ExtraUtils.validDimensionsForEnderPump[i] == this.worldObj.provider.dimensionId)
/*  81:    */           {
/*  82: 73 */             valid = !valid;
/*  83: 74 */             break;
/*  84:    */           }
/*  85:    */         }
/*  86:    */       }
/*  87: 79 */       if (!valid)
/*  88:    */       {
/*  89: 80 */         this.finished = true;
/*  90: 82 */         if (this.owner != null)
/*  91:    */         {
/*  92: 83 */           this.owner.addChatComponentMessage(new ChatComponentText("Pump will not function in this dimension"));
/*  93:    */           
/*  94: 85 */           this.owner = null;
/*  95:    */         }
/*  96: 88 */         onInventoryChanged();
/*  97: 89 */         return;
/*  98:    */       }
/*  99: 92 */       this.chunkTicket = ForgeChunkManager.requestTicket(ExtraUtilsMod.instance, this.worldObj, ForgeChunkManager.Type.NORMAL);
/* 100: 94 */       if (this.chunkTicket == null)
/* 101:    */       {
/* 102: 95 */         this.finished = true;
/* 103: 97 */         if (this.owner != null)
/* 104:    */         {
/* 105: 98 */           this.owner.addChatComponentMessage(new ChatComponentText("Unable to assign Chunkloader, this pump will not work"));
/* 106:    */           
/* 107:100 */           this.owner = null;
/* 108:    */         }
/* 109:103 */         onInventoryChanged();
/* 110:104 */         return;
/* 111:    */       }
/* 112:107 */       this.owner = null;
/* 113:108 */       this.chunkTicket.getModData().setString("id", "pump");
/* 114:109 */       this.chunkTicket.getModData().setInteger("pumpX", this.xCoord);
/* 115:110 */       this.chunkTicket.getModData().setInteger("pumpY", this.yCoord);
/* 116:111 */       this.chunkTicket.getModData().setInteger("pumpZ", this.zCoord);
/* 117:112 */       ForgeChunkManager.forceChunk(this.chunkTicket, new ChunkCoordIntPair(this.xCoord >> 4, this.zCoord >> 4));
/* 118:    */     }
/* 119:115 */     boolean goAgain = true;
/* 120:117 */     for (int t = 0; (t < 16) && (goAgain); t++)
/* 121:    */     {
/* 122:118 */       goAgain = false;
/* 123:119 */       int bx = this.b >> 4;int bz = this.b & 0xF;
/* 124:120 */       int pump_x = (this.chunk_x << 4) + bx;
/* 125:121 */       int pump_z = (this.chunk_z << 4) + bz;
/* 126:122 */       Block id = this.worldObj.getBlock(pump_x, this.pump_y, pump_z);
/* 127:124 */       if ((this.pump_y >= 0) && (XUHelper.drainBlock(this.worldObj, pump_x, this.pump_y, pump_z, false) != null))
/* 128:    */       {
/* 129:125 */         if (((this.tank.getInfo().fluid == null) || (this.tank.getInfo().fluid.amount <= 0)) && 
/* 130:126 */           (this.cofhEnergy.extractEnergy(100, true) == 100) && (this.cofhEnergy.extractEnergy(100, false) > 0))
/* 131:    */         {
/* 132:129 */           FluidStack liquid = XUHelper.drainBlock(this.worldObj, pump_x, this.pump_y, pump_z, true);
/* 133:130 */           this.tank.fill(liquid, true);
/* 134:132 */           if (this.worldObj.isAirBlock(pump_x, this.pump_y, pump_z)) {
/* 135:133 */             if (this.worldObj.rand.nextDouble() < this.p) {
/* 136:134 */               this.worldObj.setBlock(pump_x, this.pump_y, pump_z, Blocks.stone, 0, 2);
/* 137:    */             } else {
/* 138:136 */               this.worldObj.setBlock(pump_x, this.pump_y, pump_z, Blocks.cobblestone, 0, 2);
/* 139:    */             }
/* 140:    */           }
/* 141:139 */           this.pump_y -= 1;
/* 142:140 */           onInventoryChanged();
/* 143:    */         }
/* 144:    */       }
/* 145:    */       else
/* 146:    */       {
/* 147:144 */         goAgain = true;
/* 148:146 */         if (!this.init) {
/* 149:147 */           this.b = 256;
/* 150:    */         }
/* 151:150 */         this.b += 1;
/* 152:152 */         if (this.b >= 256)
/* 153:    */         {
/* 154:153 */           this.b = 0;
/* 155:154 */           goAgain = false;
/* 156:158 */           if ((this.init) && (this.chunk_no > 0)) {
/* 157:159 */             for (int dx = -2; dx <= 2; dx++) {
/* 158:160 */               for (int dz = -2; dz <= 2; dz++) {
/* 159:161 */                 ForgeChunkManager.unforceChunk(this.chunkTicket, new ChunkCoordIntPair(this.chunk_x + dx, this.chunk_z + dz));
/* 160:    */               }
/* 161:    */             }
/* 162:    */           }
/* 163:166 */           this.chunk_no += 1;
/* 164:167 */           setChunk(this.chunk_no);
/* 165:169 */           for (int dx = -2; dx <= 2; dx++) {
/* 166:170 */             for (int dz = -2; dz <= 2; dz++)
/* 167:    */             {
/* 168:171 */               ForgeChunkManager.forceChunk(this.chunkTicket, new ChunkCoordIntPair(this.chunk_x + dx, this.chunk_z + dz));
/* 169:172 */               this.worldObj.getChunkFromChunkCoords(this.chunk_x + dx, this.chunk_z + dz);
/* 170:    */             }
/* 171:    */           }
/* 172:177 */           ForgeChunkManager.forceChunk(this.chunkTicket, new ChunkCoordIntPair(this.xCoord >> 4, this.zCoord >> 4));
/* 173:    */         }
/* 174:180 */         this.pump_y = (this.yCoord - 1);
/* 175:181 */         this.init = true;
/* 176:182 */         onInventoryChanged();
/* 177:    */       }
/* 178:    */     }
/* 179:186 */     FluidStack liquid = this.tank.getInfo().fluid;
/* 180:188 */     if ((liquid != null) && (liquid.amount > 0))
/* 181:    */     {
/* 182:189 */       int[] seq = XUHelper.rndSeq(6, this.worldObj.rand);
/* 183:191 */       for (int i = 0; i < 6; i++)
/* 184:    */       {
/* 185:192 */         TileEntity tile = this.worldObj.getTileEntity(this.xCoord + net.minecraft.util.Facing.offsetsXForSide[seq[i]], this.yCoord + net.minecraft.util.Facing.offsetsYForSide[seq[i]], this.zCoord + net.minecraft.util.Facing.offsetsZForSide[seq[i]]);
/* 186:194 */         if ((tile instanceof IFluidHandler))
/* 187:    */         {
/* 188:195 */           int moved = ((IFluidHandler)tile).fill(ForgeDirection.values()[seq[i]].getOpposite(), liquid, true);
/* 189:196 */           onInventoryChanged();
/* 190:197 */           this.tank.drain(moved, true);
/* 191:198 */           liquid = this.tank.getInfo().fluid;
/* 192:200 */           if ((liquid == null) || (liquid.amount <= 0)) {
/* 193:    */             break;
/* 194:    */           }
/* 195:    */         }
/* 196:    */       }
/* 197:    */     }
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void invalidate()
/* 201:    */   {
/* 202:210 */     ForgeChunkManager.releaseTicket(this.chunkTicket);
/* 203:    */     
/* 204:212 */     super.invalidate();
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void onChunkUnload() {}
/* 208:    */   
/* 209:    */   public void setChunk(int chunk_no)
/* 210:    */   {
/* 211:221 */     int base_chunk_x = this.xCoord >> 4;
/* 212:222 */     int base_chunk_z = this.zCoord >> 4;
/* 213:223 */     int j = chunk_no;
/* 214:225 */     if (j == 0)
/* 215:    */     {
/* 216:226 */       this.chunk_x = base_chunk_x;
/* 217:227 */       this.chunk_z = base_chunk_z;
/* 218:228 */       return;
/* 219:    */     }
/* 220:231 */     j--;
/* 221:233 */     for (int k = 1; k <= 5; k++) {
/* 222:234 */       if (j >= 4 * k)
/* 223:    */       {
/* 224:235 */         j -= 4 * k;
/* 225:    */       }
/* 226:    */       else
/* 227:    */       {
/* 228:237 */         if (j < k)
/* 229:    */         {
/* 230:238 */           this.chunk_x = (base_chunk_x + j);
/* 231:239 */           this.chunk_z = (base_chunk_z + k - j);
/* 232:    */         }
/* 233:240 */         else if (j < 2 * k)
/* 234:    */         {
/* 235:241 */           j -= k;
/* 236:242 */           this.chunk_x = (base_chunk_x + k - j);
/* 237:243 */           this.chunk_z = (base_chunk_z - j);
/* 238:    */         }
/* 239:244 */         else if (j < 3 * k)
/* 240:    */         {
/* 241:245 */           j -= 2 * k;
/* 242:246 */           this.chunk_x = (base_chunk_x - j);
/* 243:247 */           this.chunk_z = (base_chunk_z - (k - j));
/* 244:    */         }
/* 245:    */         else
/* 246:    */         {
/* 247:249 */           j -= 3 * k;
/* 248:250 */           this.chunk_x = (base_chunk_x - (k - j));
/* 249:251 */           this.chunk_z = (base_chunk_z + j);
/* 250:    */         }
/* 251:254 */         return;
/* 252:    */       }
/* 253:    */     }
/* 254:258 */     this.finished = true;
/* 255:259 */     onInventoryChanged();
/* 256:260 */     chunk_no = 255;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void readFromNBT(NBTTagCompound par1NBTTagCompound)
/* 260:    */   {
/* 261:269 */     super.readFromNBT(par1NBTTagCompound);
/* 262:271 */     if ((par1NBTTagCompound.hasKey("block_no")) && ((par1NBTTagCompound.getTag("block_no") instanceof NBTTagInt))) {
/* 263:272 */       this.b = par1NBTTagCompound.getInteger("block_no");
/* 264:    */     } else {
/* 265:274 */       LogHelper.info("Extra Utilities: Problem loading EnderPump TileEntity Tag (block_no)", new Object[0]);
/* 266:    */     }
/* 267:277 */     if ((par1NBTTagCompound.hasKey("chunk_no")) && ((par1NBTTagCompound.getTag("chunk_no") instanceof NBTTagByte))) {
/* 268:278 */       this.chunk_no = par1NBTTagCompound.getByte("chunk_no");
/* 269:    */     } else {
/* 270:280 */       LogHelper.info("Extra Utilities: Problem loading EnderPump TileEntity Tag (chunk_no)", new Object[0]);
/* 271:    */     }
/* 272:283 */     if (this.chunk_no == -128) {
/* 273:284 */       this.finished = true;
/* 274:    */     } else {
/* 275:286 */       setChunk(this.chunk_no);
/* 276:    */     }
/* 277:289 */     this.tank.readFromNBT(par1NBTTagCompound.getCompoundTag("tank"));
/* 278:    */     
/* 279:291 */     this.init = true;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void writeToNBT(NBTTagCompound par1NBTTagCompound)
/* 283:    */   {
/* 284:299 */     super.writeToNBT(par1NBTTagCompound);
/* 285:300 */     par1NBTTagCompound.setInteger("block_no", this.b);
/* 286:302 */     if (this.finished) {
/* 287:303 */       par1NBTTagCompound.setByte("chunk_no", (byte)-128);
/* 288:    */     } else {
/* 289:305 */       par1NBTTagCompound.setByte("chunk_no", (byte)this.chunk_no);
/* 290:    */     }
/* 291:308 */     NBTTagCompound tank_tags = new NBTTagCompound();
/* 292:309 */     this.tank.writeToNBT(tank_tags);
/* 293:310 */     par1NBTTagCompound.setTag("tank", tank_tags);
/* 294:311 */     NBTTagCompound power_tags = new NBTTagCompound();
/* 295:    */     
/* 296:313 */     par1NBTTagCompound.setTag("power", power_tags);
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void forceChunkLoading(ForgeChunkManager.Ticket ticket)
/* 300:    */   {
/* 301:317 */     if (this.chunkTicket == null) {
/* 302:318 */       this.chunkTicket = ticket;
/* 303:    */     }
/* 304:321 */     ForgeChunkManager.forceChunk(this.chunkTicket, new ChunkCoordIntPair(this.xCoord >> 4, this.zCoord >> 4));
/* 305:323 */     for (int dx = -2; dx <= 2; dx++) {
/* 306:324 */       for (int dz = -2; dz <= 2; dz++)
/* 307:    */       {
/* 308:325 */         ForgeChunkManager.forceChunk(this.chunkTicket, new ChunkCoordIntPair(this.chunk_x + dx, this.chunk_z + dz));
/* 309:326 */         this.worldObj.getChunkFromChunkCoords(this.chunk_x + dx, this.chunk_z + dz);
/* 310:    */       }
/* 311:    */     }
/* 312:    */   }
/* 313:    */   
/* 314:    */   public Packet getDescriptionPacket()
/* 315:    */   {
/* 316:332 */     if (this.finished)
/* 317:    */     {
/* 318:333 */       NBTTagCompound t = new NBTTagCompound();
/* 319:334 */       t.setBoolean("finished", true);
/* 320:    */     }
/* 321:337 */     return null;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
/* 325:    */   {
/* 326:342 */     return this.cofhEnergy.receiveEnergy(maxReceive, simulate);
/* 327:    */   }
/* 328:    */   
/* 329:    */   public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
/* 330:    */   {
/* 331:347 */     return this.cofhEnergy.extractEnergy(maxExtract, simulate);
/* 332:    */   }
/* 333:    */   
/* 334:    */   public boolean canConnectEnergy(ForgeDirection from)
/* 335:    */   {
/* 336:352 */     return true;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public int getEnergyStored(ForgeDirection from)
/* 340:    */   {
/* 341:357 */     return this.cofhEnergy.getEnergyStored();
/* 342:    */   }
/* 343:    */   
/* 344:    */   public int getMaxEnergyStored(ForgeDirection from)
/* 345:    */   {
/* 346:362 */     return this.cofhEnergy.getMaxEnergyStored();
/* 347:    */   }
/* 348:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.TileEntityEnderThermicLavaPump
 * JD-Core Version:    0.7.0.1
 */