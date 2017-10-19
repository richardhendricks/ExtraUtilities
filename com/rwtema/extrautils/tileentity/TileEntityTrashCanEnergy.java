/*  1:   */ package com.rwtema.extrautils.tileentity;
/*  2:   */ 
/*  3:   */ import cofh.api.energy.IEnergyReceiver;
/*  4:   */ import net.minecraft.tileentity.TileEntity;
/*  5:   */ import net.minecraftforge.common.util.ForgeDirection;
/*  6:   */ 
/*  7:   */ public class TileEntityTrashCanEnergy
/*  8:   */   extends TileEntity
/*  9:   */   implements IEnergyReceiver
/* 10:   */ {
/* 11:   */   public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
/* 12:   */   {
/* 13:10 */     return (int)Math.ceil(maxReceive * 0.9D);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public int getEnergyStored(ForgeDirection from)
/* 17:   */   {
/* 18:15 */     return 0;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public int getMaxEnergyStored(ForgeDirection from)
/* 22:   */   {
/* 23:20 */     return 268435455;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public boolean canConnectEnergy(ForgeDirection from)
/* 27:   */   {
/* 28:25 */     return true;
/* 29:   */   }
/* 30:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.TileEntityTrashCanEnergy
 * JD-Core Version:    0.7.0.1
 */