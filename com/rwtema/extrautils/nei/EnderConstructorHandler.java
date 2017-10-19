/*   1:    */ package com.rwtema.extrautils.nei;
/*   2:    */ 
/*   3:    */ import codechicken.lib.gui.GuiDraw;
/*   4:    */ import codechicken.nei.NEIServerUtils;
/*   5:    */ import codechicken.nei.recipe.ShapedRecipeHandler;
/*   6:    */ import codechicken.nei.recipe.ShapedRecipeHandler.CachedShapedRecipe;
/*   7:    */ import codechicken.nei.recipe.TemplateRecipeHandler.RecipeTransferRect;
/*   8:    */ import com.rwtema.extrautils.tileentity.enderconstructor.DynamicGuiEnderConstructor;
/*   9:    */ import com.rwtema.extrautils.tileentity.enderconstructor.EnderConstructorRecipesHandler;
/*  10:    */ import java.awt.Rectangle;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.LinkedList;
/*  13:    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*  14:    */ import net.minecraft.item.ItemStack;
/*  15:    */ import net.minecraft.item.crafting.IRecipe;
/*  16:    */ import net.minecraft.item.crafting.ShapedRecipes;
/*  17:    */ import net.minecraft.util.ResourceLocation;
/*  18:    */ import net.minecraftforge.oredict.ShapedOreRecipe;
/*  19:    */ import org.lwjgl.opengl.GL11;
/*  20:    */ 
/*  21:    */ public class EnderConstructorHandler
/*  22:    */   extends ShapedRecipeHandler
/*  23:    */ {
/*  24: 21 */   private static final ResourceLocation texWidgets = new ResourceLocation("extrautils", "textures/guiQED_NEI.png");
/*  25:    */   
/*  26:    */   public Class<? extends GuiContainer> getGuiClass()
/*  27:    */   {
/*  28: 25 */     return DynamicGuiEnderConstructor.class;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void drawBackground(int recipe)
/*  32:    */   {
/*  33: 30 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  34: 31 */     GuiDraw.changeTexture(texWidgets);
/*  35: 32 */     GuiDraw.drawTexturedModalRect(0, 0, 5, 11, 166, 65);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public String getOverlayIdentifier()
/*  39:    */   {
/*  40: 37 */     return "qedcrafting";
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void drawForeground(int recipe)
/*  44:    */   {
/*  45: 42 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  46: 43 */     GL11.glDisable(2896);
/*  47: 44 */     GuiDraw.changeTexture(texWidgets);
/*  48: 45 */     drawExtras(recipe);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void loadTransferRects()
/*  52:    */   {
/*  53: 50 */     this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(84, 23, 24, 18), getOverlayIdentifier(), new Object[0]));
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String getRecipeName()
/*  57:    */   {
/*  58: 55 */     return "QED Recipes";
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void drawExtras(int recipe)
/*  62:    */   {
/*  63: 60 */     drawProgressBar(85, 24, 176, 0, 22, 15, 48, 0);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void loadUsageRecipes(ItemStack ingredient)
/*  67:    */   {
/*  68: 65 */     for (IRecipe irecipe : EnderConstructorRecipesHandler.recipes)
/*  69:    */     {
/*  70: 66 */       ShapedRecipeHandler.CachedShapedRecipe recipe = null;
/*  71: 67 */       if ((irecipe instanceof ShapedRecipes)) {
/*  72: 68 */         recipe = new ShapedRecipeHandler.CachedShapedRecipe(this, (ShapedRecipes)irecipe);
/*  73: 69 */       } else if ((irecipe instanceof ShapedOreRecipe)) {
/*  74: 70 */         recipe = forgeShapedRecipe((ShapedOreRecipe)irecipe);
/*  75:    */       }
/*  76: 72 */       if ((recipe != null) && (recipe.contains(recipe.ingredients, ingredient.getItem())))
/*  77:    */       {
/*  78: 75 */         recipe.computeVisuals();
/*  79: 76 */         if (recipe.contains(recipe.ingredients, ingredient))
/*  80:    */         {
/*  81: 77 */           recipe.setIngredientPermutation(recipe.ingredients, ingredient);
/*  82: 78 */           this.arecipes.add(recipe);
/*  83:    */         }
/*  84:    */       }
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void loadCraftingRecipes(String outputId, Object... results)
/*  89:    */   {
/*  90: 85 */     if (outputId.equals(getOverlayIdentifier())) {
/*  91: 86 */       for (IRecipe irecipe : EnderConstructorRecipesHandler.recipes)
/*  92:    */       {
/*  93: 87 */         ShapedRecipeHandler.CachedShapedRecipe recipe = null;
/*  94: 88 */         if ((irecipe instanceof ShapedRecipes)) {
/*  95: 89 */           recipe = new ShapedRecipeHandler.CachedShapedRecipe(this, (ShapedRecipes)irecipe);
/*  96: 90 */         } else if ((irecipe instanceof ShapedOreRecipe)) {
/*  97: 91 */           recipe = forgeShapedRecipe((ShapedOreRecipe)irecipe);
/*  98:    */         }
/*  99: 93 */         if (recipe != null)
/* 100:    */         {
/* 101: 96 */           recipe.computeVisuals();
/* 102: 97 */           this.arecipes.add(recipe);
/* 103:    */         }
/* 104:    */       }
/* 105:100 */     } else if (outputId.equals("item")) {
/* 106:101 */       loadCraftingRecipes((ItemStack)results[0]);
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void loadCraftingRecipes(ItemStack result)
/* 111:    */   {
/* 112:107 */     for (IRecipe irecipe : EnderConstructorRecipesHandler.recipes) {
/* 113:108 */       if (NEIServerUtils.areStacksSameTypeCrafting(irecipe.getRecipeOutput(), result))
/* 114:    */       {
/* 115:109 */         ShapedRecipeHandler.CachedShapedRecipe recipe = null;
/* 116:110 */         if ((irecipe instanceof ShapedRecipes)) {
/* 117:111 */           recipe = new ShapedRecipeHandler.CachedShapedRecipe(this, (ShapedRecipes)irecipe);
/* 118:112 */         } else if ((irecipe instanceof ShapedOreRecipe)) {
/* 119:113 */           recipe = forgeShapedRecipe((ShapedOreRecipe)irecipe);
/* 120:    */         }
/* 121:115 */         if (recipe != null)
/* 122:    */         {
/* 123:118 */           recipe.computeVisuals();
/* 124:119 */           this.arecipes.add(recipe);
/* 125:    */         }
/* 126:    */       }
/* 127:    */     }
/* 128:    */   }
/* 129:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.nei.EnderConstructorHandler
 * JD-Core Version:    0.7.0.1
 */