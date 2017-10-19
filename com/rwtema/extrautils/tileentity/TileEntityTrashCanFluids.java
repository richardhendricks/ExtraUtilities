/*  1:   */ package com.rwtema.extrautils.tileentity;
/*  2:   */ 
/*  3:   */ import net.minecraft.tileentity.TileEntity;
/*  4:   */ import net.minecraftforge.common.util.ForgeDirection;
/*  5:   */ import net.minecraftforge.fluids.Fluid;
/*  6:   */ import net.minecraftforge.fluids.FluidStack;
/*  7:   */ import net.minecraftforge.fluids.FluidTankInfo;
/*  8:   */ import net.minecraftforge.fluids.IFluidHandler;
/*  9:   */ 
/* 10:   */ public class TileEntityTrashCanFluids
/* 11:   */   extends TileEntity
/* 12:   */   implements IFluidHandler
/* 13:   */ {
/* 14:   */   public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
/* 15:   */   {
/* 16:14 */     return resource != null ? resource.amount : 0;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
/* 20:   */   {
/* 21:19 */     return null;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
/* 25:   */   {
/* 26:24 */     return null;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public boolean canFill(ForgeDirection from, Fluid fluid)
/* 30:   */   {
/* 31:29 */     return true;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public boolean canDrain(ForgeDirection from, Fluid fluid)
/* 35:   */   {
/* 36:34 */     return false;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public FluidTankInfo[] getTankInfo(ForgeDirection from)
/* 40:   */   {
/* 41:39 */     return new FluidTankInfo[] { new FluidTankInfo(null, 16777215) };
/* 42:   */   }
/* 43:   */   
/* 44:   */   public boolean canUpdate()
/* 45:   */   {
/* 46:44 */     return false;
/* 47:   */   }
/* 48:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.TileEntityTrashCanFluids
 * JD-Core Version:    0.7.0.1
 */