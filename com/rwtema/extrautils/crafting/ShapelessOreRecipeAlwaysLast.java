/*  1:   */ package com.rwtema.extrautils.crafting;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.item.Item;
/*  5:   */ import net.minecraft.item.ItemStack;
/*  6:   */ import net.minecraftforge.oredict.ShapelessOreRecipe;
/*  7:   */ 
/*  8:   */ public class ShapelessOreRecipeAlwaysLast
/*  9:   */   extends ShapelessOreRecipe
/* 10:   */ {
/* 11:   */   public ShapelessOreRecipeAlwaysLast(Block result, Object... recipe)
/* 12:   */   {
/* 13:10 */     super(result, recipe);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public ShapelessOreRecipeAlwaysLast(Item result, Object... recipe)
/* 17:   */   {
/* 18:14 */     super(result, recipe);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public ShapelessOreRecipeAlwaysLast(ItemStack result, Object... recipe)
/* 22:   */   {
/* 23:18 */     super(result, recipe);
/* 24:   */   }
/* 25:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.crafting.ShapelessOreRecipeAlwaysLast
 * JD-Core Version:    0.7.0.1
 */