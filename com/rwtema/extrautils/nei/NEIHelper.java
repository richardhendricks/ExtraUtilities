/*  1:   */ package com.rwtema.extrautils.nei;
/*  2:   */ 
/*  3:   */ import codechicken.nei.recipe.DefaultOverlayHandler;
/*  4:   */ import codechicken.nei.recipe.RecipeInfo;
/*  5:   */ import net.minecraft.client.gui.inventory.GuiContainer;
/*  6:   */ import net.minecraft.client.gui.inventory.GuiCrafting;
/*  7:   */ 
/*  8:   */ public class NEIHelper
/*  9:   */ {
/* 10:   */   public static boolean isCraftingGUI(GuiContainer gui)
/* 11:   */   {
/* 12:10 */     if (gui.getClass() == GuiCrafting.class) {
/* 13:11 */       return true;
/* 14:   */     }
/* 15:13 */     if ((RecipeInfo.hasOverlayHandler(gui, "crafting")) && 
/* 16:14 */       (RecipeInfo.getOverlayHandler(gui, "crafting").getClass() == DefaultOverlayHandler.class)) {
/* 17:15 */       return true;
/* 18:   */     }
/* 19:18 */     return false;
/* 20:   */   }
/* 21:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.nei.NEIHelper
 * JD-Core Version:    0.7.0.1
 */