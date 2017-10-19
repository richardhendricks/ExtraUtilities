/*  1:   */ package com.rwtema.extrautils.crafting;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtils;
/*  4:   */ import net.minecraft.inventory.InventoryCrafting;
/*  5:   */ import net.minecraft.item.ItemStack;
/*  6:   */ import net.minecraft.item.crafting.IRecipe;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public class RecipeSoul
/* 10:   */   implements IRecipe
/* 11:   */ {
/* 12:   */   public boolean matches(InventoryCrafting var1, World var2)
/* 13:   */   {
/* 14:12 */     if (var1.getSizeInventory() != 4) {
/* 15:13 */       return false;
/* 16:   */     }
/* 17:14 */     boolean foundSword = false;
/* 18:15 */     for (int i = 0; i < var1.getSizeInventory(); i++) {
/* 19:16 */       if (var1.getStackInSlot(i) != null)
/* 20:   */       {
/* 21:17 */         if (foundSword) {
/* 22:18 */           return false;
/* 23:   */         }
/* 24:19 */         if (var1.getStackInSlot(i).getItem() != ExtraUtils.ethericSword) {
/* 25:20 */           return false;
/* 26:   */         }
/* 27:22 */         foundSword = true;
/* 28:   */       }
/* 29:   */     }
/* 30:26 */     return foundSword;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public ItemStack getCraftingResult(InventoryCrafting var1)
/* 34:   */   {
/* 35:31 */     return new ItemStack(ExtraUtils.soul, 1, 2);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public int getRecipeSize()
/* 39:   */   {
/* 40:36 */     return 1;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public ItemStack getRecipeOutput()
/* 44:   */   {
/* 45:41 */     return new ItemStack(ExtraUtils.soul);
/* 46:   */   }
/* 47:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.crafting.RecipeSoul
 * JD-Core Version:    0.7.0.1
 */