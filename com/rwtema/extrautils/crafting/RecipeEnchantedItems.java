/*  1:   */ package com.rwtema.extrautils.crafting;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.inventory.InventoryCrafting;
/*  6:   */ import net.minecraft.item.Item;
/*  7:   */ import net.minecraft.item.ItemStack;
/*  8:   */ import net.minecraft.item.crafting.IRecipe;
/*  9:   */ import net.minecraft.item.crafting.ShapedRecipes;
/* 10:   */ import net.minecraft.nbt.NBTTagCompound;
/* 11:   */ import net.minecraft.world.World;
/* 12:   */ 
/* 13:   */ public class RecipeEnchantedItems
/* 14:   */   extends ShapedRecipes
/* 15:   */ {
/* 16:   */   public RecipeEnchantedItems(int par1, int par2, ItemStack[] par3ArrayOfItemStack, ItemStack par4ItemStack)
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
/* 58:57 */       if (itemstack1.isItemEnchantable())
/* 59:   */       {
/* 60:58 */         if (!itemstack1.hasTagCompound()) {
/* 61:59 */           itemstack1.setTagCompound(new NBTTagCompound());
/* 62:   */         }
/* 63:62 */         if (!itemstack1.getTagCompound().hasKey("ench")) {
/* 64:63 */           itemstack1.getTagCompound().setTag("ench", new NBTTagCompound());
/* 65:   */         }
/* 66:   */       }
/* 67:67 */       hashmap.put(character, itemstack1);
/* 68:   */     }
/* 69:70 */     ItemStack[] aitemstack = new ItemStack[j * k];
/* 70:72 */     for (int i1 = 0; i1 < j * k; i1++)
/* 71:   */     {
/* 72:73 */       char c0 = s.charAt(i1);
/* 73:75 */       if (hashmap.containsKey(Character.valueOf(c0))) {
/* 74:76 */         aitemstack[i1] = ((ItemStack)hashmap.get(Character.valueOf(c0))).copy();
/* 75:   */       } else {
/* 76:78 */         aitemstack[i1] = null;
/* 77:   */       }
/* 78:   */     }
/* 79:82 */     IRecipe shapedrecipes = new RecipeEnchantedItems(j, k, aitemstack, par1ItemStack);
/* 80:83 */     return shapedrecipes;
/* 81:   */   }
/* 82:   */   
/* 83:   */   public boolean matches(InventoryCrafting inv, World par2World)
/* 84:   */   {
/* 85:88 */     for (int i = 0; i < inv.getSizeInventory(); i++) {
/* 86:89 */       if ((inv.getStackInSlot(i) != null) && (inv.getStackInSlot(i).isItemEnchantable())) {
/* 87:90 */         return false;
/* 88:   */       }
/* 89:   */     }
/* 90:93 */     return super.matches(inv, par2World);
/* 91:   */   }
/* 92:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.crafting.RecipeEnchantedItems
 * JD-Core Version:    0.7.0.1
 */