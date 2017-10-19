/*  1:   */ package com.rwtema.extrautils.crafting;
/*  2:   */ 
/*  3:   */ import net.minecraft.init.Items;
/*  4:   */ import net.minecraft.inventory.InventoryCrafting;
/*  5:   */ import net.minecraft.item.ItemStack;
/*  6:   */ import net.minecraft.item.crafting.FurnaceRecipes;
/*  7:   */ import net.minecraft.item.crafting.IRecipe;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ import net.minecraftforge.oredict.OreDictionary;
/* 10:   */ 
/* 11:   */ public class RecipeSmasher
/* 12:   */   implements IRecipe
/* 13:   */ {
/* 14:   */   public boolean matches(InventoryCrafting craft, World p_77569_2_)
/* 15:   */   {
/* 16:14 */     return getCraftingResult(craft) != null;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public ItemStack getCraftingResult(InventoryCrafting craft)
/* 20:   */   {
/* 21:20 */     ItemStack ore = null;
/* 22:21 */     for (int i = 0; i < craft.getSizeInventory(); i++)
/* 23:   */     {
/* 24:22 */       ItemStack stackInSlot = craft.getStackInSlot(i);
/* 25:23 */       if (stackInSlot != null) {
/* 26:24 */         if (ore == null) {
/* 27:25 */           ore = stackInSlot;
/* 28:   */         } else {
/* 29:27 */           return null;
/* 30:   */         }
/* 31:   */       }
/* 32:   */     }
/* 33:32 */     if (ore == null) {
/* 34:33 */       return null;
/* 35:   */     }
/* 36:35 */     if (!isOreType(ore, "ore")) {
/* 37:36 */       return null;
/* 38:   */     }
/* 39:38 */     ItemStack smeltingResult = FurnaceRecipes.smelting().func_151395_a(ore);
/* 40:40 */     if (smeltingResult == null) {
/* 41:41 */       return null;
/* 42:   */     }
/* 43:43 */     if (!isOreType(smeltingResult, "ingot")) {
/* 44:44 */       return null;
/* 45:   */     }
/* 46:46 */     ItemStack result = smeltingResult.copy();
/* 47:   */     
/* 48:48 */     result.stackSize *= 3;
/* 49:50 */     if (result.stackSize < result.getMaxStackSize()) {
/* 50:51 */       result.stackSize = result.getMaxStackSize();
/* 51:   */     }
/* 52:53 */     return result;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public boolean isOreType(ItemStack ore, String type)
/* 56:   */   {
/* 57:57 */     for (String s : getOreNames(ore)) {
/* 58:58 */       if (s.startsWith(type)) {
/* 59:59 */         return true;
/* 60:   */       }
/* 61:   */     }
/* 62:62 */     return false;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public String[] getOreNames(ItemStack ore)
/* 66:   */   {
/* 67:66 */     int[] oreIDs = OreDictionary.getOreIDs(ore);
/* 68:67 */     String[] result = new String[oreIDs.length];
/* 69:68 */     for (int i = 0; i < oreIDs.length; i++) {
/* 70:69 */       result[i] = OreDictionary.getOreName(oreIDs[i]);
/* 71:   */     }
/* 72:71 */     return result;
/* 73:   */   }
/* 74:   */   
/* 75:   */   public int getRecipeSize()
/* 76:   */   {
/* 77:76 */     return 1;
/* 78:   */   }
/* 79:   */   
/* 80:   */   public ItemStack getRecipeOutput()
/* 81:   */   {
/* 82:81 */     return new ItemStack(Items.iron_ingot);
/* 83:   */   }
/* 84:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.crafting.RecipeSmasher
 * JD-Core Version:    0.7.0.1
 */