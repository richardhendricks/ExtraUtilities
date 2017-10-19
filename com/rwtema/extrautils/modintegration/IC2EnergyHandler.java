/*  1:   */ package com.rwtema.extrautils.modintegration;
/*  2:   */ 
/*  3:   */ import ic2.api.energy.prefab.BasicSink;
/*  4:   */ import net.minecraft.nbt.NBTTagCompound;
/*  5:   */ import net.minecraft.tileentity.TileEntity;
/*  6:   */ 
/*  7:   */ public class IC2EnergyHandler
/*  8:   */ {
/*  9:   */   private final TileEntity ic2EnergySink;
/* 10:   */   
/* 11:   */   public IC2EnergyHandler(TileEntity parent1, int capacity1, int tier1)
/* 12:   */   {
/* 13:54 */     this.ic2EnergySink = new BasicSink(parent1, capacity1, tier1);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void updateEntity()
/* 17:   */   {
/* 18:58 */     this.ic2EnergySink.updateEntity();
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void useEnergy(double e)
/* 22:   */   {
/* 23:62 */     ((BasicSink)this.ic2EnergySink).useEnergy(e);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public double getEnergyStored()
/* 27:   */   {
/* 28:66 */     return ((BasicSink)this.ic2EnergySink).getEnergyStored();
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void onChunkUnload()
/* 32:   */   {
/* 33:70 */     this.ic2EnergySink.onChunkUnload();
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void writeToNBT(NBTTagCompound tagCompound)
/* 37:   */   {
/* 38:   */     try
/* 39:   */     {
/* 40:75 */       this.ic2EnergySink.writeToNBT(tagCompound);
/* 41:   */     }
/* 42:   */     catch (Throwable ignored) {}
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void readFromNBT(NBTTagCompound tagCompound)
/* 46:   */   {
/* 47:   */     try
/* 48:   */     {
/* 49:83 */       this.ic2EnergySink.readFromNBT(tagCompound);
/* 50:   */     }
/* 51:   */     catch (Throwable ignored) {}
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void invalidate()
/* 55:   */   {
/* 56:90 */     this.ic2EnergySink.invalidate();
/* 57:   */   }
/* 58:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.modintegration.IC2EnergyHandler
 * JD-Core Version:    0.7.0.1
 */