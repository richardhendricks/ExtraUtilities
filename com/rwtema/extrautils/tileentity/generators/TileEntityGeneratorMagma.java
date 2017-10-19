/*  1:   */ package com.rwtema.extrautils.tileentity.generators;
/*  2:   */ 
/*  3:   */ import net.minecraftforge.common.util.ForgeDirection;
/*  4:   */ import net.minecraftforge.fluids.Fluid;
/*  5:   */ import net.minecraftforge.fluids.FluidStack;
/*  6:   */ import net.minecraftforge.fluids.FluidTank;
/*  7:   */ import net.minecraftforge.fluids.FluidTankInfo;
/*  8:   */ import net.minecraftforge.fluids.IFluidHandler;
/*  9:   */ 
/* 10:   */ public class TileEntityGeneratorMagma
/* 11:   */   extends TileEntityGenerator
/* 12:   */   implements IFluidHandler
/* 13:   */ {
/* 14: 7 */   public FluidTank[] tanks = { new FluidTankRestricted(4000, new String[] { "lava" }) };
/* 15:   */   
/* 16:   */   public int transferLimit()
/* 17:   */   {
/* 18:11 */     return 160;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public FluidTank[] getTanks()
/* 22:   */   {
/* 23:16 */     return this.tanks;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public int getMaxCoolDown()
/* 27:   */   {
/* 28:21 */     return 0;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public boolean shouldProcess()
/* 32:   */   {
/* 33:27 */     return (this.coolDown == 0.0D) || (this.coolDown < getMaxCoolDown());
/* 34:   */   }
/* 35:   */   
/* 36:   */   public boolean processInput()
/* 37:   */   {
/* 38:33 */     for (int i = 0; i < getTanks().length; i++)
/* 39:   */     {
/* 40:34 */       int c = getFuelBurn(getTanks()[i].getFluid());
/* 41:36 */       if ((c > 0) && (getTanks()[i].getFluidAmount() >= fluidAmmount()) && (addCoolDown(c, true)))
/* 42:   */       {
/* 43:37 */         addCoolDown(c, false);
/* 44:38 */         getTanks()[i].drain(fluidAmmount(), true);
/* 45:39 */         return true;
/* 46:   */       }
/* 47:   */     }
/* 48:43 */     return false;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public double genLevel()
/* 52:   */   {
/* 53:48 */     return 40.0D;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public int fluidAmmount()
/* 57:   */   {
/* 58:52 */     return 100;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public int getFuelBurn(FluidStack fluid)
/* 62:   */   {
/* 63:56 */     return fluidAmmount();
/* 64:   */   }
/* 65:   */   
/* 66:   */   public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
/* 67:   */   {
/* 68:61 */     return super.fill(from, resource, doFill);
/* 69:   */   }
/* 70:   */   
/* 71:   */   public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
/* 72:   */   {
/* 73:66 */     return super.drain(from, resource, doDrain);
/* 74:   */   }
/* 75:   */   
/* 76:   */   public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
/* 77:   */   {
/* 78:71 */     return super.drain(from, maxDrain, doDrain);
/* 79:   */   }
/* 80:   */   
/* 81:   */   public boolean canFill(ForgeDirection from, Fluid fluid)
/* 82:   */   {
/* 83:76 */     return super.canFill(from, fluid);
/* 84:   */   }
/* 85:   */   
/* 86:   */   public boolean canDrain(ForgeDirection from, Fluid fluid)
/* 87:   */   {
/* 88:81 */     return super.canDrain(from, fluid);
/* 89:   */   }
/* 90:   */   
/* 91:   */   public FluidTankInfo[] getTankInfo(ForgeDirection from)
/* 92:   */   {
/* 93:86 */     return super.getTankInfo(from);
/* 94:   */   }
/* 95:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.generators.TileEntityGeneratorMagma
 * JD-Core Version:    0.7.0.1
 */