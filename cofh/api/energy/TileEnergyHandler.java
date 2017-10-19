/*  1:   */ package cofh.api.energy;
/*  2:   */ 
/*  3:   */ import net.minecraft.nbt.NBTTagCompound;
/*  4:   */ import net.minecraft.tileentity.TileEntity;
/*  5:   */ import net.minecraftforge.common.util.ForgeDirection;
/*  6:   */ 
/*  7:   */ public class TileEnergyHandler
/*  8:   */   extends TileEntity
/*  9:   */   implements IEnergyHandler
/* 10:   */ {
/* 11:15 */   protected EnergyStorage storage = new EnergyStorage(32000);
/* 12:   */   
/* 13:   */   public void readFromNBT(NBTTagCompound nbt)
/* 14:   */   {
/* 15:20 */     super.readFromNBT(nbt);
/* 16:21 */     this.storage.readFromNBT(nbt);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void writeToNBT(NBTTagCompound nbt)
/* 20:   */   {
/* 21:27 */     super.writeToNBT(nbt);
/* 22:28 */     this.storage.writeToNBT(nbt);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public boolean canConnectEnergy(ForgeDirection from)
/* 26:   */   {
/* 27:35 */     return true;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
/* 31:   */   {
/* 32:42 */     return this.storage.receiveEnergy(maxReceive, simulate);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
/* 36:   */   {
/* 37:49 */     return this.storage.extractEnergy(maxExtract, simulate);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public int getEnergyStored(ForgeDirection from)
/* 41:   */   {
/* 42:56 */     return this.storage.getEnergyStored();
/* 43:   */   }
/* 44:   */   
/* 45:   */   public int getMaxEnergyStored(ForgeDirection from)
/* 46:   */   {
/* 47:62 */     return this.storage.getMaxEnergyStored();
/* 48:   */   }
/* 49:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     cofh.api.energy.TileEnergyHandler
 * JD-Core Version:    0.7.0.1
 */