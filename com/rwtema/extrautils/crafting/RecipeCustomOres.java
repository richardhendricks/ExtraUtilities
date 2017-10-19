/*  1:   */ package com.rwtema.extrautils.crafting;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import net.minecraft.item.ItemStack;
/*  5:   */ import net.minecraftforge.oredict.ShapedOreRecipe;
/*  6:   */ 
/*  7:   */ public class RecipeCustomOres
/*  8:   */   extends ShapedOreRecipe
/*  9:   */ {
/* 10:   */   public RecipeCustomOres(ItemStack result, ItemStack toReplace, ArrayList<ItemStack> customOres, Object... recipe)
/* 11:   */   {
/* 12:10 */     super(result, recipe);
/* 13:11 */     replace(toReplace, customOres);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public static ShapedOreRecipe replace(ShapedOreRecipe recipe, ItemStack toReplace, ArrayList<ItemStack> customOres)
/* 17:   */   {
/* 18:15 */     Object[] input = recipe.getInput();
/* 19:16 */     for (int i = 0; i < input.length; i++) {
/* 20:17 */       if (((input[i] instanceof ItemStack)) && 
/* 21:18 */         (toReplace.isItemEqual((ItemStack)input[i]))) {
/* 22:19 */         input[i] = customOres;
/* 23:   */       }
/* 24:   */     }
/* 25:22 */     return recipe;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public RecipeCustomOres replace(ItemStack toReplace, ArrayList<ItemStack> customOres)
/* 29:   */   {
/* 30:26 */     return (RecipeCustomOres)replace(this, toReplace, customOres);
/* 31:   */   }
/* 32:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.crafting.RecipeCustomOres
 * JD-Core Version:    0.7.0.1
 */