/*  1:   */ package com.rwtema.extrautils.tileentity.generators;
/*  2:   */ 
/*  3:   */ import net.minecraft.item.ItemStack;
/*  4:   */ 
/*  5:   */ public class TileEntityGeneratorFurnaceSurvival
/*  6:   */   extends TileEntityGeneratorFurnace
/*  7:   */ {
/*  8:   */   public int transferLimit()
/*  9:   */   {
/* 10: 8 */     return 160;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public double genLevel()
/* 14:   */   {
/* 15:13 */     return 5.0D;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public double getFuelBurn(ItemStack item)
/* 19:   */   {
/* 20:18 */     return 10 * TileEntityGenerator.getFurnaceBurnTime(item);
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.generators.TileEntityGeneratorFurnaceSurvival
 * JD-Core Version:    0.7.0.1
 */