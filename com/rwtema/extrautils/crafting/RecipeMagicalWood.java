/*   1:    */ package com.rwtema.extrautils.crafting;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import net.minecraft.enchantment.Enchantment;
/*   5:    */ import net.minecraft.init.Blocks;
/*   6:    */ import net.minecraft.init.Items;
/*   7:    */ import net.minecraft.inventory.InventoryCrafting;
/*   8:    */ import net.minecraft.item.Item;
/*   9:    */ import net.minecraft.item.ItemEnchantedBook;
/*  10:    */ import net.minecraft.item.ItemStack;
/*  11:    */ import net.minecraft.item.crafting.ShapedRecipes;
/*  12:    */ import net.minecraft.nbt.NBTTagCompound;
/*  13:    */ import net.minecraft.nbt.NBTTagList;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ 
/*  16:    */ public class RecipeMagicalWood
/*  17:    */   extends ShapedRecipes
/*  18:    */ {
/*  19: 14 */   public static final ItemStack gold = new ItemStack(Items.gold_ingot);
/*  20: 15 */   public static final ItemStack bookshelf = new ItemStack(Blocks.bookshelf);
/*  21: 16 */   public static final ItemStack book = new ItemStack(Items.enchanted_book);
/*  22:    */   
/*  23:    */   public RecipeMagicalWood()
/*  24:    */   {
/*  25: 19 */     super(3, 3, new ItemStack[] { gold, book, gold, book, bookshelf, book, gold, book, gold }, new ItemStack(ExtraUtils.decorative1, 1, 8));
/*  26:    */   }
/*  27:    */   
/*  28:    */   public int getEnchantmentLevel(ItemStack book)
/*  29:    */   {
/*  30: 23 */     NBTTagList nbttaglist = book.getItem() == Items.enchanted_book ? Items.enchanted_book.func_92110_g(book) : book.getEnchantmentTagList();
/*  31: 24 */     int m = 0;
/*  32: 26 */     if (nbttaglist != null)
/*  33:    */     {
/*  34: 27 */       if (nbttaglist.tagCount() == 0) {
/*  35: 28 */         return 0;
/*  36:    */       }
/*  37: 31 */       int j = book.getItem().getItemEnchantability();
/*  38: 32 */       j /= 2;
/*  39: 33 */       j = 1 + 2 * (j >> 1);
/*  40: 35 */       if (j < 1) {
/*  41: 36 */         j = 1;
/*  42:    */       }
/*  43: 39 */       for (int i = 0; i < nbttaglist.tagCount(); i++)
/*  44:    */       {
/*  45: 40 */         short short1 = nbttaglist.getCompoundTagAt(i).getShort("id");
/*  46: 41 */         short short2 = nbttaglist.getCompoundTagAt(i).getShort("lvl");
/*  47: 42 */         float k = Enchantment.enchantmentsList[short1].getMinEnchantability(short2);
/*  48:    */         
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52: 47 */         k -= 0.5F;
/*  53: 48 */         k *= 0.869F;
/*  54: 49 */         k -= j;
/*  55: 51 */         if (k < 1.0F) {
/*  56: 52 */           k = 1.0F;
/*  57:    */         }
/*  58: 55 */         m = Math.max(m, (int)Math.floor(k));
/*  59:    */       }
/*  60:    */     }
/*  61: 59 */     return m;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World)
/*  65:    */   {
/*  66: 67 */     return checkMatch(par1InventoryCrafting) > 0;
/*  67:    */   }
/*  68:    */   
/*  69:    */   private int checkMatch(InventoryCrafting par1InventoryCrafting)
/*  70:    */   {
/*  71: 74 */     int n = 0;
/*  72: 76 */     for (int k = 0; k < 3; k++) {
/*  73: 77 */       for (int l = 0; l < 3; l++)
/*  74:    */       {
/*  75: 78 */         int i1 = k;
/*  76: 79 */         int j1 = l;
/*  77: 80 */         ItemStack itemstack = this.recipeItems[(i1 + j1 * this.recipeWidth)];
/*  78: 81 */         ItemStack itemstack1 = par1InventoryCrafting.getStackInRowAndColumn(k, l);
/*  79: 83 */         if (itemstack1 == null) {
/*  80: 84 */           return 0;
/*  81:    */         }
/*  82: 87 */         if (itemstack.getItem() == book.getItem())
/*  83:    */         {
/*  84: 88 */           int m = getEnchantmentLevel(itemstack1);
/*  85: 90 */           if (m == 0) {
/*  86: 91 */             return 0;
/*  87:    */           }
/*  88: 93 */           n += m;
/*  89:    */         }
/*  90:    */         else
/*  91:    */         {
/*  92: 96 */           if (itemstack.getItem() != itemstack1.getItem()) {
/*  93: 97 */             return 0;
/*  94:    */           }
/*  95:100 */           if ((itemstack.getItemDamage() != 32767) && (itemstack.getItemDamage() != itemstack1.getItemDamage())) {
/*  96:101 */             return 0;
/*  97:    */           }
/*  98:    */         }
/*  99:    */       }
/* 100:    */     }
/* 101:107 */     n /= 8;
/* 102:109 */     if (n < 1) {
/* 103:110 */       n = 1;
/* 104:    */     }
/* 105:113 */     if (n > 64) {
/* 106:114 */       return 64;
/* 107:    */     }
/* 108:117 */     return n;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting)
/* 112:    */   {
/* 113:125 */     ItemStack itemstack = getRecipeOutput().copy();
/* 114:126 */     itemstack.stackSize = checkMatch(par1InventoryCrafting);
/* 115:127 */     return itemstack;
/* 116:    */   }
/* 117:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.crafting.RecipeMagicalWood
 * JD-Core Version:    0.7.0.1
 */