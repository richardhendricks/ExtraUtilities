/*   1:    */ package com.rwtema.extrautils.crafting;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.inventory.InventoryCrafting;
/*   6:    */ import net.minecraft.item.Item;
/*   7:    */ import net.minecraft.item.ItemStack;
/*   8:    */ import net.minecraft.item.crafting.ShapedRecipes;
/*   9:    */ import net.minecraft.nbt.NBTTagCompound;
/*  10:    */ import net.minecraft.nbt.NBTTagList;
/*  11:    */ import net.minecraft.nbt.NBTTagString;
/*  12:    */ import net.minecraft.world.EnumDifficulty;
/*  13:    */ import net.minecraft.world.World;
/*  14:    */ 
/*  15:    */ public class RecipeDifficultySpecific
/*  16:    */   extends ShapedRecipes
/*  17:    */ {
/*  18: 16 */   private static boolean isRemote = false;
/*  19: 17 */   boolean[] validDifficulty = new boolean[4];
/*  20:    */   private ItemStack fakeStack;
/*  21:    */   
/*  22:    */   public RecipeDifficultySpecific(int par1, int par2, ItemStack[] par3ArrayOfItemStack, ItemStack par4ItemStack, boolean[] validDifficulties, ItemStack fakeStack)
/*  23:    */   {
/*  24: 22 */     super(par1, par2, par3ArrayOfItemStack, par4ItemStack);
/*  25: 23 */     this.validDifficulty = validDifficulties;
/*  26: 24 */     this.fakeStack = fakeStack;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static RecipeDifficultySpecific addRecipe(boolean[] validDifficulties, ItemStack par1ItemStack, String[] LoreText, Object... par2ArrayOfObj)
/*  30:    */   {
/*  31: 28 */     String s = "";
/*  32: 29 */     int i = 0;
/*  33: 30 */     int j = 0;
/*  34: 31 */     int k = 0;
/*  35: 33 */     if ((par2ArrayOfObj[i] instanceof String[]))
/*  36:    */     {
/*  37: 34 */       String[] astring = (String[])par2ArrayOfObj[(i++)];
/*  38: 36 */       for (String s1 : astring)
/*  39:    */       {
/*  40: 37 */         k++;
/*  41: 38 */         j = s1.length();
/*  42: 39 */         s = s + s1;
/*  43:    */       }
/*  44:    */     }
/*  45:    */     else
/*  46:    */     {
/*  47: 42 */       while ((par2ArrayOfObj[i] instanceof String))
/*  48:    */       {
/*  49: 43 */         String s2 = (String)par2ArrayOfObj[(i++)];
/*  50: 44 */         k++;
/*  51: 45 */         j = s2.length();
/*  52: 46 */         s = s + s2;
/*  53:    */       }
/*  54:    */     }
/*  55: 52 */     for (HashMap hashmap = new HashMap(); i < par2ArrayOfObj.length; i += 2)
/*  56:    */     {
/*  57: 53 */       Character character = (Character)par2ArrayOfObj[i];
/*  58: 54 */       ItemStack itemstack1 = null;
/*  59: 56 */       if ((par2ArrayOfObj[(i + 1)] instanceof Item)) {
/*  60: 57 */         itemstack1 = new ItemStack((Item)par2ArrayOfObj[(i + 1)]);
/*  61: 58 */       } else if ((par2ArrayOfObj[(i + 1)] instanceof Block)) {
/*  62: 59 */         itemstack1 = new ItemStack((Block)par2ArrayOfObj[(i + 1)], 1, 32767);
/*  63: 60 */       } else if ((par2ArrayOfObj[(i + 1)] instanceof ItemStack)) {
/*  64: 61 */         itemstack1 = (ItemStack)par2ArrayOfObj[(i + 1)];
/*  65:    */       }
/*  66: 64 */       hashmap.put(character, itemstack1);
/*  67:    */     }
/*  68: 67 */     ItemStack[] aitemstack = new ItemStack[j * k];
/*  69: 69 */     for (int i1 = 0; i1 < j * k; i1++)
/*  70:    */     {
/*  71: 70 */       char c0 = s.charAt(i1);
/*  72: 72 */       if (hashmap.containsKey(Character.valueOf(c0))) {
/*  73: 73 */         aitemstack[i1] = ((ItemStack)hashmap.get(Character.valueOf(c0))).copy();
/*  74:    */       } else {
/*  75: 75 */         aitemstack[i1] = null;
/*  76:    */       }
/*  77:    */     }
/*  78: 79 */     NBTTagList nbttaglist = new NBTTagList();
/*  79: 81 */     for (String aLoreText : LoreText) {
/*  80: 82 */       nbttaglist.appendTag(new NBTTagString(aLoreText));
/*  81:    */     }
/*  82: 85 */     NBTTagCompound display = new NBTTagCompound();
/*  83: 86 */     display.setTag("Lore", nbttaglist);
/*  84: 87 */     NBTTagCompound base = new NBTTagCompound();
/*  85: 88 */     base.setTag("display", display);
/*  86: 89 */     ItemStack item = par1ItemStack.copy();
/*  87: 90 */     item.setTagCompound(base);
/*  88: 91 */     RecipeDifficultySpecific shapedrecipes = new RecipeDifficultySpecific(j, k, aitemstack, par1ItemStack, validDifficulties, item);
/*  89: 92 */     return shapedrecipes;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World)
/*  93:    */   {
/*  94:100 */     if ((par2World != null) && (par2World.difficultySetting != null))
/*  95:    */     {
/*  96:101 */       isRemote = par2World.isClient;
/*  97:103 */       if ((par2World.difficultySetting.getDifficultyId() >= 0) && (par2World.difficultySetting.getDifficultyId() <= 3) && (
/*  98:104 */         (par2World.isClient) || (this.validDifficulty[par2World.difficultySetting.getDifficultyId()] != 0))) {
/*  99:105 */         return super.matches(par1InventoryCrafting, par2World);
/* 100:    */       }
/* 101:    */     }
/* 102:109 */     return false;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting)
/* 106:    */   {
/* 107:117 */     ItemStack item = super.getCraftingResult(par1InventoryCrafting);
/* 108:119 */     if ((isRemote) && (this.fakeStack != null)) {
/* 109:120 */       return this.fakeStack;
/* 110:    */     }
/* 111:123 */     return item;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public ItemStack getRecipeOutput()
/* 115:    */   {
/* 116:128 */     return this.fakeStack;
/* 117:    */   }
/* 118:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.crafting.RecipeDifficultySpecific
 * JD-Core Version:    0.7.0.1
 */