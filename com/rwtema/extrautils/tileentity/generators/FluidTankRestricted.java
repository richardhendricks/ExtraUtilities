/*  1:   */ package com.rwtema.extrautils.tileentity.generators;
/*  2:   */ 
/*  3:   */ import net.minecraftforge.fluids.FluidRegistry;
/*  4:   */ import net.minecraftforge.fluids.FluidStack;
/*  5:   */ import net.minecraftforge.fluids.FluidTank;
/*  6:   */ 
/*  7:   */ public class FluidTankRestricted
/*  8:   */   extends FluidTank
/*  9:   */ {
/* 10:   */   String[] name;
/* 11:   */   
/* 12:   */   public FluidTankRestricted(int capacity, String... name)
/* 13:   */   {
/* 14:11 */     super(capacity);
/* 15:12 */     this.name = name;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public int fill(FluidStack resource, boolean doFill)
/* 19:   */   {
/* 20:17 */     if (this.fluid == null)
/* 21:   */     {
/* 22:18 */       String str = FluidRegistry.getFluidName(resource);
/* 23:20 */       for (String aName : this.name) {
/* 24:21 */         if (aName.equals(str)) {
/* 25:22 */           return super.fill(resource, doFill);
/* 26:   */         }
/* 27:   */       }
/* 28:25 */       return 0;
/* 29:   */     }
/* 30:27 */     return super.fill(resource, doFill);
/* 31:   */   }
/* 32:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.generators.FluidTankRestricted
 * JD-Core Version:    0.7.0.1
 */