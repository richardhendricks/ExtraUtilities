/*   1:    */ package com.rwtema.extrautils.crafting;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import java.util.LinkedHashMap;
/*   5:    */ import java.util.Map;
/*   6:    */ import net.minecraft.enchantment.EnchantmentHelper;
/*   7:    */ import net.minecraft.init.Items;
/*   8:    */ import net.minecraft.inventory.InventoryCrafting;
/*   9:    */ import net.minecraft.item.ItemStack;
/*  10:    */ import net.minecraft.item.crafting.IRecipe;
/*  11:    */ import net.minecraft.nbt.NBTTagCompound;
/*  12:    */ import net.minecraft.world.World;
/*  13:    */ 
/*  14:    */ public class RecipeUnEnchanting
/*  15:    */   implements IRecipe
/*  16:    */ {
/*  17:    */   public boolean matches(InventoryCrafting inventorycrafting, World world)
/*  18:    */   {
/*  19: 17 */     return getCraftingResult(inventorycrafting) != null;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public ItemStack getCraftingResult(InventoryCrafting inventorycrafting)
/*  23:    */   {
/*  24: 22 */     if (inventorycrafting.getSizeInventory() != 9) {
/*  25: 23 */       return null;
/*  26:    */     }
/*  27: 26 */     int curRow = -1;
/*  28: 28 */     for (int x = 0; x < 3; x++) {
/*  29: 29 */       for (int y = 0; y < 3; y++) {
/*  30: 30 */         if (inventorycrafting.getStackInRowAndColumn(x, y) != null)
/*  31:    */         {
/*  32: 31 */           if (curRow == -1)
/*  33:    */           {
/*  34: 32 */             if (y == 0) {
/*  35: 33 */               curRow = x;
/*  36:    */             } else {
/*  37: 35 */               return null;
/*  38:    */             }
/*  39:    */           }
/*  40: 37 */           else if (x != curRow) {
/*  41: 38 */             return null;
/*  42:    */           }
/*  43:    */         }
/*  44: 40 */         else if (curRow == x) {
/*  45: 41 */           return null;
/*  46:    */         }
/*  47:    */       }
/*  48:    */     }
/*  49: 46 */     if (curRow == -1) {
/*  50: 47 */       return null;
/*  51:    */     }
/*  52: 50 */     int x = curRow;
/*  53: 51 */     ItemStack n = inventorycrafting.getStackInRowAndColumn(x, 0);
/*  54: 52 */     ItemStack s = inventorycrafting.getStackInRowAndColumn(x, 1);
/*  55: 53 */     ItemStack d = inventorycrafting.getStackInRowAndColumn(x, 2);
/*  56: 55 */     if (s.getItem() != ExtraUtils.divisionSigil) {
/*  57: 56 */       return null;
/*  58:    */     }
/*  59: 59 */     if ((!s.hasTagCompound()) || (!s.getTagCompound().hasKey("damage"))) {
/*  60: 60 */       return null;
/*  61:    */     }
/*  62: 63 */     if ((n.getItem() == Items.iron_ingot) && (d.getItem() == Items.diamond)) {
/*  63: 64 */       return null;
/*  64:    */     }
/*  65: 67 */     Map ne = EnchantmentHelper.getEnchantments(n);
/*  66: 68 */     Map de = EnchantmentHelper.getEnchantments(d);
/*  67: 70 */     if ((de == null) || (de.isEmpty()))
/*  68:    */     {
/*  69: 71 */       if ((d.getItem() == Items.book) && (n.getItem() != Items.book) && (ne != null) && (!ne.isEmpty()))
/*  70:    */       {
/*  71: 72 */         LinkedHashMap re = new LinkedHashMap();
/*  72: 74 */         for (Object o : ne.keySet())
/*  73:    */         {
/*  74: 75 */           int id = ((Integer)o).intValue();
/*  75: 76 */           int level = ((Integer)ne.get(Integer.valueOf(id))).intValue();
/*  76: 78 */           if (level > 1) {
/*  77: 79 */             re.put(Integer.valueOf(id), Integer.valueOf(level - 1));
/*  78:    */           }
/*  79:    */         }
/*  80: 83 */         ItemStack r = n.copy();
/*  81: 85 */         if (r.hasTagCompound()) {
/*  82: 86 */           r.getTagCompound().removeTag("ench");
/*  83:    */         }
/*  84: 89 */         EnchantmentHelper.setEnchantments(re, r);
/*  85: 90 */         return r;
/*  86:    */       }
/*  87: 93 */       if ((ne == null) || (ne.isEmpty())) {
/*  88: 94 */         return null;
/*  89:    */       }
/*  90:    */     }
/*  91: 98 */     LinkedHashMap re = new LinkedHashMap();
/*  92: 99 */     boolean overlap = false;
/*  93:101 */     for (Object o : ne.keySet())
/*  94:    */     {
/*  95:102 */       int id = ((Integer)o).intValue();
/*  96:103 */       int level = ((Integer)ne.get(Integer.valueOf(id))).intValue();
/*  97:105 */       if ((de != null) && (de.containsKey(Integer.valueOf(id))))
/*  98:    */       {
/*  99:106 */         overlap = true;
/* 100:107 */         level -= ((Integer)de.get(Integer.valueOf(id))).intValue();
/* 101:109 */         if (level > 0) {
/* 102:110 */           re.put(Integer.valueOf(id), Integer.valueOf(level));
/* 103:    */         }
/* 104:    */       }
/* 105:    */       else
/* 106:    */       {
/* 107:113 */         re.put(Integer.valueOf(id), Integer.valueOf(level));
/* 108:    */       }
/* 109:    */     }
/* 110:117 */     if (!overlap) {
/* 111:118 */       return null;
/* 112:    */     }
/* 113:121 */     ItemStack r = n.copy();
/* 114:123 */     if (r.hasTagCompound()) {
/* 115:124 */       r.getTagCompound().removeTag("ench");
/* 116:    */     }
/* 117:127 */     EnchantmentHelper.setEnchantments(re, r);
/* 118:128 */     return r;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public int getRecipeSize()
/* 122:    */   {
/* 123:133 */     return 3;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public ItemStack getRecipeOutput()
/* 127:    */   {
/* 128:138 */     return null;
/* 129:    */   }
/* 130:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.crafting.RecipeUnEnchanting
 * JD-Core Version:    0.7.0.1
 */