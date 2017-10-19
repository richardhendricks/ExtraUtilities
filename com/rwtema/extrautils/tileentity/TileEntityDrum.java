/*   1:    */ package com.rwtema.extrautils.tileentity;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.texture.LiquidColorRegistry;
/*   4:    */ import cpw.mods.fml.relauncher.Side;
/*   5:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   6:    */ import net.minecraft.nbt.NBTTagCompound;
/*   7:    */ import net.minecraft.network.NetworkManager;
/*   8:    */ import net.minecraft.network.Packet;
/*   9:    */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*  10:    */ import net.minecraft.tileentity.TileEntity;
/*  11:    */ import net.minecraft.world.World;
/*  12:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  13:    */ import net.minecraftforge.fluids.Fluid;
/*  14:    */ import net.minecraftforge.fluids.FluidStack;
/*  15:    */ import net.minecraftforge.fluids.FluidTank;
/*  16:    */ import net.minecraftforge.fluids.FluidTankInfo;
/*  17:    */ import net.minecraftforge.fluids.IFluidHandler;
/*  18:    */ 
/*  19:    */ public class TileEntityDrum
/*  20:    */   extends TileEntity
/*  21:    */   implements IFluidHandler
/*  22:    */ {
/*  23:    */   public static final int numBuckets = 256;
/*  24:    */   public static final int defaultCapacity = 256000;
/*  25: 17 */   private FluidTank tank = new FluidTank(256000);
/*  26: 18 */   public static int numTicksTilDisplayEmpty = 100;
/*  27: 19 */   public boolean recentlyDrained = false;
/*  28: 20 */   public boolean recentlyFilled = false;
/*  29: 22 */   public FluidStack prevFluid = null;
/*  30:    */   
/*  31:    */   public TileEntityDrum() {}
/*  32:    */   
/*  33:    */   public TileEntityDrum(int metadata)
/*  34:    */   {
/*  35: 29 */     this.blockMetadata = metadata;
/*  36: 30 */     setCapacityFromMetadata(metadata);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static int getCapacityFromMetadata(int meta)
/*  40:    */   {
/*  41: 34 */     if (meta == 1) {
/*  42: 35 */       return 65536000;
/*  43:    */     }
/*  44: 36 */     return 256000;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setCapacityFromMetadata(int meta)
/*  48:    */   {
/*  49: 40 */     if (meta == 1) {
/*  50: 41 */       this.tank.setCapacity(getCapacityFromMetadata(meta));
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void loadDrumFromNBT(NBTTagCompound par1NBTTagCompound)
/*  55:    */   {
/*  56: 45 */     this.tank.setFluid(null);
/*  57: 46 */     this.tank.readFromNBT(par1NBTTagCompound.getCompoundTag("tank"));
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void writeDrumToNBT(NBTTagCompound par1NBTTagCompound)
/*  61:    */   {
/*  62: 50 */     NBTTagCompound tag = new NBTTagCompound();
/*  63: 51 */     this.tank.writeToNBT(tag);
/*  64: 52 */     par1NBTTagCompound.setTag("tank", tag);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void readFromNBT(NBTTagCompound tag)
/*  68:    */   {
/*  69: 57 */     super.readFromNBT(tag);
/*  70: 58 */     loadDrumFromNBT(tag);
/*  71:    */     
/*  72: 60 */     this.tank.setCapacity(tag.getCompoundTag("tank").getInteger("capacity"));
/*  73: 62 */     if (this.tank.getFluid() != null) {
/*  74: 63 */       this.prevFluid = this.tank.getFluid().copy();
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void writeToNBT(NBTTagCompound tag)
/*  79:    */   {
/*  80: 69 */     super.writeToNBT(tag);
/*  81: 70 */     writeDrumToNBT(tag);
/*  82: 71 */     NBTTagCompound tag2 = tag.getCompoundTag("tank");
/*  83: 72 */     tag2.setInteger("capacity", this.tank.getCapacity());
/*  84: 73 */     tag.setTag("tank", tag2);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean canUpdate()
/*  88:    */   {
/*  89: 78 */     return false;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public Packet getDescriptionPacket()
/*  93:    */   {
/*  94: 83 */     NBTTagCompound t = new NBTTagCompound();
/*  95: 84 */     writeDrumToNBT(t);
/*  96: 85 */     return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 4, t);
/*  97:    */   }
/*  98:    */   
/*  99:    */   @SideOnly(Side.CLIENT)
/* 100:    */   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
/* 101:    */   {
/* 102: 91 */     if (!this.worldObj.isClient) {
/* 103: 92 */       return;
/* 104:    */     }
/* 105: 95 */     loadDrumFromNBT(pkt.func_148857_g());
/* 106: 96 */     this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void ticked()
/* 110:    */   {
/* 111:100 */     if (this.recentlyDrained)
/* 112:    */     {
/* 113:101 */       this.recentlyDrained = false;
/* 114:103 */       if (this.recentlyFilled)
/* 115:    */       {
/* 116:104 */         this.recentlyFilled = false;
/* 117:105 */         this.worldObj.scheduleBlockUpdate(this.xCoord, this.yCoord, this.zCoord, getBlockType(), numTicksTilDisplayEmpty);
/* 118:    */       }
/* 119:    */       else
/* 120:    */       {
/* 121:107 */         this.prevFluid = null;
/* 122:108 */         onInventoryChanged();
/* 123:109 */         this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
/* 124:    */       }
/* 125:    */     }
/* 126:    */   }
/* 127:    */   
/* 128:115 */   boolean sided = false;
/* 129:    */   
/* 130:    */   public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
/* 131:    */   {
/* 132:119 */     if ((this.sided) && (from.ordinal() > 1)) {
/* 133:120 */       return 0;
/* 134:    */     }
/* 135:123 */     boolean t2 = this.tank.getFluid() == null;
/* 136:124 */     int t = this.tank.fill(resource, doFill);
/* 137:126 */     if (doFill)
/* 138:    */     {
/* 139:127 */       if ((t2) && (this.tank.getFluid() != null) && 
/* 140:128 */         (!this.tank.getFluid().isFluidEqual(this.prevFluid)))
/* 141:    */       {
/* 142:129 */         this.prevFluid = this.tank.getFluid().copy();
/* 143:130 */         this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
/* 144:    */       }
/* 145:133 */       if (t != 0)
/* 146:    */       {
/* 147:134 */         this.recentlyFilled = true;
/* 148:135 */         onInventoryChanged();
/* 149:    */       }
/* 150:    */     }
/* 151:139 */     return t;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
/* 155:    */   {
/* 156:144 */     if (((this.sided) && (from.ordinal() > 1)) || (resource == null) || (!resource.isFluidEqual(this.tank.getFluid()))) {
/* 157:145 */       return null;
/* 158:    */     }
/* 159:148 */     if (doDrain) {
/* 160:148 */       onInventoryChanged();
/* 161:    */     }
/* 162:150 */     return drain(from, resource.amount, doDrain);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
/* 166:    */   {
/* 167:155 */     if (((!this.sided) || (from.ordinal() <= 1)) && (this.tank.getFluidAmount() > 0))
/* 168:    */     {
/* 169:156 */       FluidStack t = this.tank.drain(maxDrain, doDrain);
/* 170:158 */       if ((doDrain) && (t != null))
/* 171:    */       {
/* 172:159 */         if (this.tank.getFluidAmount() == 0)
/* 173:    */         {
/* 174:160 */           this.recentlyFilled = false;
/* 175:161 */           this.recentlyDrained = true;
/* 176:    */           
/* 177:163 */           this.worldObj.scheduleBlockUpdate(this.xCoord, this.yCoord, this.zCoord, getBlockType(), numTicksTilDisplayEmpty);
/* 178:    */         }
/* 179:165 */         onInventoryChanged();
/* 180:    */       }
/* 181:167 */       return t;
/* 182:    */     }
/* 183:169 */     return null;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public boolean canFill(ForgeDirection from, Fluid fluid)
/* 187:    */   {
/* 188:175 */     return (!this.sided) || (from.ordinal() <= 1);
/* 189:    */   }
/* 190:    */   
/* 191:    */   public boolean canDrain(ForgeDirection from, Fluid fluid)
/* 192:    */   {
/* 193:180 */     return true;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public FluidTankInfo[] getTankInfo(ForgeDirection from)
/* 197:    */   {
/* 198:185 */     return new FluidTankInfo[] { this.tank.getInfo() };
/* 199:    */   }
/* 200:    */   
/* 201:    */   @SideOnly(Side.CLIENT)
/* 202:    */   public int getColor()
/* 203:    */   {
/* 204:190 */     return LiquidColorRegistry.getFluidColor(this.tank.getFluid());
/* 205:    */   }
/* 206:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.TileEntityDrum
 * JD-Core Version:    0.7.0.1
 */