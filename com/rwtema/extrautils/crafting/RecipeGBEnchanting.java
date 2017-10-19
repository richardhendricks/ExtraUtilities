/*  1:   */ package com.rwtema.extrautils.crafting;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtils;
/*  4:   */ import com.rwtema.extrautils.item.ItemGoldenBag;
/*  5:   */ import java.util.HashMap;
/*  6:   */ import net.minecraft.block.Block;
/*  7:   */ import net.minecraft.inventory.InventoryCrafting;
/*  8:   */ import net.minecraft.item.Item;
/*  9:   */ import net.minecraft.item.ItemStack;
/* 10:   */ import net.minecraft.item.crafting.IRecipe;
/* 11:   */ import net.minecraft.item.crafting.ShapedRecipes;
/* 12:   */ 
/* 13:   */ public class RecipeGBEnchanting
/* 14:   */   extends ShapedRecipes
/* 15:   */ {
/* 16:   */   public RecipeGBEnchanting(int par1, int par2, ItemStack[] par3ArrayOfItemStack, ItemStack par4ItemStack)
/* 17:   */   {
/* 18:16 */     super(par1, par2, par3ArrayOfItemStack, par4ItemStack);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public static IRecipe addRecipe(ItemStack par1ItemStack, Object... par2ArrayOfObj)
/* 22:   */   {
/* 23:20 */     String s = "";
/* 24:21 */     int i = 0;
/* 25:22 */     int j = 0;
/* 26:23 */     int k = 0;
/* 27:25 */     if ((par2ArrayOfObj[i] instanceof String[]))
/* 28:   */     {
/* 29:26 */       String[] astring = (String[])par2ArrayOfObj[(i++)];
/* 30:28 */       for (String s1 : astring)
/* 31:   */       {
/* 32:29 */         k++;
/* 33:30 */         j = s1.length();
/* 34:31 */         s = s + s1;
/* 35:   */       }
/* 36:   */     }
/* 37:   */     else
/* 38:   */     {
/* 39:34 */       while ((par2ArrayOfObj[i] instanceof String))
/* 40:   */       {
/* 41:35 */         String s2 = (String)par2ArrayOfObj[(i++)];
/* 42:36 */         k++;
/* 43:37 */         j = s2.length();
/* 44:38 */         s = s + s2;
/* 45:   */       }
/* 46:   */     }
/* 47:44 */     for (HashMap hashmap = new HashMap(); i < par2ArrayOfObj.length; i += 2)
/* 48:   */     {
/* 49:45 */       Character character = (Character)par2ArrayOfObj[i];
/* 50:46 */       ItemStack itemstack1 = null;
/* 51:48 */       if ((par2ArrayOfObj[(i + 1)] instanceof Item)) {
/* 52:49 */         itemstack1 = new ItemStack((Item)par2ArrayOfObj[(i + 1)]);
/* 53:50 */       } else if ((par2ArrayOfObj[(i + 1)] instanceof Block)) {
/* 54:51 */         itemstack1 = new ItemStack((Block)par2ArrayOfObj[(i + 1)], 1, 32767);
/* 55:52 */       } else if ((par2ArrayOfObj[(i + 1)] instanceof ItemStack)) {
/* 56:53 */         itemstack1 = (ItemStack)par2ArrayOfObj[(i + 1)];
/* 57:   */       }
/* 58:56 */       hashmap.put(character, itemstack1);
/* 59:   */     }
/* 60:59 */     ItemStack[] aitemstack = new ItemStack[j * k];
/* 61:61 */     for (int i1 = 0; i1 < j * k; i1++)
/* 62:   */     {
/* 63:62 */       char c0 = s.charAt(i1);
/* 64:64 */       if (hashmap.containsKey(Character.valueOf(c0))) {
/* 65:65 */         aitemstack[i1] = ((ItemStack)hashmap.get(Character.valueOf(c0))).copy();
/* 66:   */       } else {
/* 67:67 */         aitemstack[i1] = null;
/* 68:   */       }
/* 69:   */     }
/* 70:71 */     ItemGoldenBag.setMagic(par1ItemStack);
/* 71:72 */     IRecipe shapedrecipes = new RecipeGBEnchanting(j, k, aitemstack, par1ItemStack);
/* 72:73 */     return shapedrecipes;
/* 73:   */   }
/* 74:   */   
/* 75:   */   public ItemStack getCraftingResult(InventoryCrafting inv)
/* 76:   */   {
/* 77:78 */     ItemStack item = super.getCraftingResult(inv);
/* 78:80 */     for (int i = 0; i < inv.getSizeInventory(); i++) {
/* 79:81 */       if ((inv.getStackInSlot(i) != null) && (inv.getStackInSlot(i).getItem() == ExtraUtils.goldenBag))
/* 80:   */       {
/* 81:82 */         ItemStack item2 = inv.getStackInSlot(i).copy();
/* 82:83 */         ItemGoldenBag.setMagic(item2);
/* 83:84 */         return item2;
/* 84:   */       }
/* 85:   */     }
/* 86:88 */     return item;
/* 87:   */   }
/* 88:   */   
/* 89:   */   public int getRecipeSize()
/* 90:   */   {
/* 91:93 */     return 3;
/* 92:   */   }
/* 93:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.crafting.RecipeGBEnchanting
 * JD-Core Version:    0.7.0.1
 */