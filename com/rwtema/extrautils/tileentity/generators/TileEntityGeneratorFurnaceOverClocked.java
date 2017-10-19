/*  1:   */ package com.rwtema.extrautils.tileentity.generators;
/*  2:   */ 
/*  3:   */ import net.minecraft.item.ItemStack;
/*  4:   */ 
/*  5:   */ public class TileEntityGeneratorFurnaceOverClocked
/*  6:   */   extends TileEntityGeneratorFurnace
/*  7:   */ {
/*  8: 6 */   private final double multiplier = 10.0D;
/*  9:   */   
/* 10:   */   public int transferLimit()
/* 11:   */   {
/* 12:10 */     return 100000;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public double genLevel()
/* 16:   */   {
/* 17:15 */     return super.genLevel() * 10.0D;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public double getFuelBurn(ItemStack item)
/* 21:   */   {
/* 22:21 */     return super.getFuelBurn(item) / 10.0D * 0.25D;
/* 23:   */   }
/* 24:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.generators.TileEntityGeneratorFurnaceOverClocked
 * JD-Core Version:    0.7.0.1
 */