/*  1:   */ package com.rwtema.extrautils.tileentity.generators;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtils;
/*  4:   */ import net.minecraft.init.Items;
/*  5:   */ import net.minecraft.item.Item;
/*  6:   */ import net.minecraft.item.ItemStack;
/*  7:   */ import net.minecraftforge.fluids.Fluid;
/*  8:   */ import net.minecraftforge.fluids.FluidContainerRegistry;
/*  9:   */ import net.minecraftforge.fluids.FluidStack;
/* 10:   */ import net.minecraftforge.fluids.FluidTank;
/* 11:   */ 
/* 12:   */ public class TileEntityGeneratorEnder
/* 13:   */   extends TileEntityGeneratorFurnace
/* 14:   */ {
/* 15:12 */   public FluidTank[] tanks = { new FluidTankRestricted(4000, new String[] { "ender" }) };
/* 16:   */   
/* 17:   */   public int transferLimit()
/* 18:   */   {
/* 19:17 */     return 400;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public double genLevel()
/* 23:   */   {
/* 24:22 */     return 40.0D;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public int getMaxCoolDown()
/* 28:   */   {
/* 29:27 */     return 0;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public double getFuelBurn(ItemStack item)
/* 33:   */   {
/* 34:32 */     if (item != null)
/* 35:   */     {
/* 36:33 */       if (item.getItem() == Items.ender_pearl) {
/* 37:34 */         return 750.0D;
/* 38:   */       }
/* 39:37 */       if (item.getItem() == Items.ender_eye) {
/* 40:38 */         return 3000.0D;
/* 41:   */       }
/* 42:41 */       if (item.getItem() == Item.getItemFromBlock(ExtraUtils.enderLily)) {
/* 43:42 */         return 12000.0D;
/* 44:   */       }
/* 45:45 */       FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(item);
/* 46:46 */       if ((fluid != null) && ("ender".equals(fluid.getFluid().getName()))) {
/* 47:47 */         return fluid.amount * 6;
/* 48:   */       }
/* 49:   */     }
/* 50:53 */     return 0.0D;
/* 51:   */   }
/* 52:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.generators.TileEntityGeneratorEnder
 * JD-Core Version:    0.7.0.1
 */