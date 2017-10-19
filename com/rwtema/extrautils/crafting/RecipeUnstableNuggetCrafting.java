/*  1:   */ package com.rwtema.extrautils.crafting;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtils;
/*  4:   */ import java.util.HashMap;
/*  5:   */ import net.minecraft.block.Block;
/*  6:   */ import net.minecraft.init.Items;
/*  7:   */ import net.minecraft.item.Item;
/*  8:   */ import net.minecraft.item.ItemStack;
/*  9:   */ import net.minecraft.item.crafting.IRecipe;
/* 10:   */ import net.minecraft.nbt.NBTTagCompound;
/* 11:   */ 
/* 12:   */ public class RecipeUnstableNuggetCrafting
/* 13:   */   extends RecipeUnstableIngotCrafting
/* 14:   */ {
/* 15:   */   public RecipeUnstableNuggetCrafting(int par1, int par2, ItemStack[] par3ArrayOfItemStack, ItemStack par4ItemStack)
/* 16:   */   {
/* 17:15 */     super(par1, par2, par3ArrayOfItemStack, par4ItemStack);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public static IRecipe addRecipe(ItemStack par1ItemStack, Object... par2ArrayOfObj)
/* 21:   */   {
/* 22:19 */     String s = "";
/* 23:20 */     int i = 0;
/* 24:21 */     int j = 0;
/* 25:22 */     int k = 0;
/* 26:24 */     if ((par2ArrayOfObj[i] instanceof String[]))
/* 27:   */     {
/* 28:25 */       String[] astring = (String[])par2ArrayOfObj[(i++)];
/* 29:27 */       for (String s1 : astring)
/* 30:   */       {
/* 31:28 */         k++;
/* 32:29 */         j = s1.length();
/* 33:30 */         s = s + s1;
/* 34:   */       }
/* 35:   */     }
/* 36:   */     else
/* 37:   */     {
/* 38:33 */       while ((par2ArrayOfObj[i] instanceof String))
/* 39:   */       {
/* 40:34 */         String s2 = (String)par2ArrayOfObj[(i++)];
/* 41:35 */         k++;
/* 42:36 */         j = s2.length();
/* 43:37 */         s = s + s2;
/* 44:   */       }
/* 45:   */     }
/* 46:43 */     for (HashMap hashmap = new HashMap(); i < par2ArrayOfObj.length; i += 2)
/* 47:   */     {
/* 48:44 */       Character character = (Character)par2ArrayOfObj[i];
/* 49:45 */       ItemStack itemstack1 = null;
/* 50:47 */       if ((par2ArrayOfObj[(i + 1)] instanceof Item)) {
/* 51:48 */         itemstack1 = new ItemStack((Item)par2ArrayOfObj[(i + 1)]);
/* 52:49 */       } else if ((par2ArrayOfObj[(i + 1)] instanceof Block)) {
/* 53:50 */         itemstack1 = new ItemStack((Block)par2ArrayOfObj[(i + 1)], 1, 32767);
/* 54:51 */       } else if ((par2ArrayOfObj[(i + 1)] instanceof ItemStack)) {
/* 55:52 */         itemstack1 = (ItemStack)par2ArrayOfObj[(i + 1)];
/* 56:   */       }
/* 57:55 */       hashmap.put(character, itemstack1);
/* 58:   */     }
/* 59:58 */     ItemStack[] aitemstack = new ItemStack[j * k];
/* 60:60 */     for (int i1 = 0; i1 < j * k; i1++)
/* 61:   */     {
/* 62:61 */       char c0 = s.charAt(i1);
/* 63:63 */       if (hashmap.containsKey(Character.valueOf(c0))) {
/* 64:64 */         aitemstack[i1] = ((ItemStack)hashmap.get(Character.valueOf(c0))).copy();
/* 65:   */       } else {
/* 66:66 */         aitemstack[i1] = null;
/* 67:   */       }
/* 68:   */     }
/* 69:70 */     NBTTagCompound tags = new NBTTagCompound();
/* 70:71 */     tags.setBoolean("Bug", true);
/* 71:72 */     par1ItemStack.setTagCompound(tags);
/* 72:73 */     return new RecipeUnstableNuggetCrafting(j, k, aitemstack, par1ItemStack);
/* 73:   */   }
/* 74:   */   
/* 75:   */   public ItemStack makeResult(ItemStack n, ItemStack d, ItemStack s)
/* 76:   */   {
/* 77:78 */     if ((n.getItem() == Items.gold_nugget) && (d.getItem() == Items.diamond))
/* 78:   */     {
/* 79:80 */       ItemStack item = new ItemStack(ExtraUtils.unstableIngot, 1, 1);
/* 80:81 */       return item;
/* 81:   */     }
/* 82:84 */     return null;
/* 83:   */   }
/* 84:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.crafting.RecipeUnstableNuggetCrafting
 * JD-Core Version:    0.7.0.1
 */