/*  1:   */ package com.rwtema.extrautils.nei;
/*  2:   */ 
/*  3:   */ import codechicken.lib.gui.GuiDraw;
/*  4:   */ import codechicken.nei.PositionedStack;
/*  5:   */ import codechicken.nei.recipe.TemplateRecipeHandler;
/*  6:   */ import codechicken.nei.recipe.TemplateRecipeHandler.CachedRecipe;
/*  7:   */ import com.rwtema.extrautils.ExtraUtils;
/*  8:   */ import cpw.mods.fml.relauncher.Side;
/*  9:   */ import cpw.mods.fml.relauncher.SideOnly;
/* 10:   */ import java.util.ArrayList;
/* 11:   */ import net.minecraft.client.gui.Gui;
/* 12:   */ import net.minecraft.item.ItemStack;
/* 13:   */ import org.lwjgl.opengl.GL11;
/* 14:   */ 
/* 15:   */ public class SoulHandler
/* 16:   */   extends TemplateRecipeHandler
/* 17:   */ {
/* 18:   */   public String getGuiTexture()
/* 19:   */   {
/* 20:18 */     return "textures/gui/container/inventory.png";
/* 21:   */   }
/* 22:   */   
/* 23:   */   @SideOnly(Side.CLIENT)
/* 24:   */   public void drawForeground(int recipe)
/* 25:   */   {
/* 26:25 */     int x = (GuiDraw.getStringWidth("+") + 10) / 2;
/* 27:26 */     GuiDraw.drawString("+", 60 - x, 40, -12566464, false);
/* 28:   */     
/* 29:28 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 30:29 */     GuiDraw.changeTexture(Gui.icons);
/* 31:30 */     GuiDraw.drawTexturedModalRect(60 + x - 9, 40, 16, 0, 9, 9);
/* 32:31 */     GuiDraw.drawTexturedModalRect(60 + x - 9, 40, 52, 0, 9, 9);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void drawBackground(int recipe)
/* 36:   */   {
/* 37:35 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 38:36 */     GuiDraw.changeTexture(getGuiTexture());
/* 39:37 */     GuiDraw.drawTexturedModalRect(44, 0, 85, 23, 78, 40);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public class SoulRecipe
/* 43:   */     extends TemplateRecipeHandler.CachedRecipe
/* 44:   */   {
/* 45:   */     public SoulRecipe()
/* 46:   */     {
/* 47:40 */       super();
/* 48:   */     }
/* 49:   */     
/* 50:   */     public PositionedStack getResult()
/* 51:   */     {
/* 52:43 */       return new PositionedStack(new ItemStack(ExtraUtils.soul), 103, 13);
/* 53:   */     }
/* 54:   */     
/* 55:   */     public PositionedStack getIngredient()
/* 56:   */     {
/* 57:48 */       return new PositionedStack(new ItemStack(ExtraUtils.ethericSword), 47, 3);
/* 58:   */     }
/* 59:   */   }
/* 60:   */   
/* 61:   */   public void drawExtras(int recipe)
/* 62:   */   {
/* 63:54 */     super.drawExtras(recipe);
/* 64:   */   }
/* 65:   */   
/* 66:   */   public boolean isValid()
/* 67:   */   {
/* 68:60 */     return (ExtraUtils.soulEnabled) && (ExtraUtils.ethericSwordEnabled);
/* 69:   */   }
/* 70:   */   
/* 71:   */   public void loadCraftingRecipes(ItemStack result)
/* 72:   */   {
/* 73:65 */     if ((isValid()) && (result.getItem() == ExtraUtils.soul)) {
/* 74:66 */       this.arecipes.add(new SoulRecipe());
/* 75:   */     }
/* 76:   */   }
/* 77:   */   
/* 78:   */   public void loadUsageRecipes(ItemStack ingredient)
/* 79:   */   {
/* 80:71 */     if ((isValid()) && (ingredient.getItem() == ExtraUtils.ethericSword)) {
/* 81:72 */       this.arecipes.add(new SoulRecipe());
/* 82:   */     }
/* 83:   */   }
/* 84:   */   
/* 85:   */   public String getRecipeName()
/* 86:   */   {
/* 87:77 */     return "Soul Crafting";
/* 88:   */   }
/* 89:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.nei.SoulHandler
 * JD-Core Version:    0.7.0.1
 */