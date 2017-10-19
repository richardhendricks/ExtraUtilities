/*   1:    */ package com.rwtema.extrautils.crafting;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import net.minecraft.inventory.InventoryCrafting;
/*   6:    */ import net.minecraft.item.Item;
/*   7:    */ import net.minecraft.item.ItemStack;
/*   8:    */ import net.minecraft.item.crafting.IRecipe;
/*   9:    */ import net.minecraft.item.crafting.ShapelessRecipes;
/*  10:    */ import net.minecraft.nbt.NBTTagCompound;
/*  11:    */ import net.minecraft.world.World;
/*  12:    */ 
/*  13:    */ public class RecipeFilterInvert
/*  14:    */   extends ShapelessRecipes
/*  15:    */   implements IRecipe
/*  16:    */ {
/*  17:    */   Item key;
/*  18:    */   String keyName;
/*  19:    */   private final ItemStack filter;
/*  20:    */   
/*  21:    */   public RecipeFilterInvert(Item key, String keyName, ItemStack filter)
/*  22:    */   {
/*  23: 20 */     super(makeResult(key, keyName, filter.copy()), makeRecipes(key, keyName, filter.copy()));
/*  24: 21 */     this.key = key;
/*  25: 22 */     this.keyName = keyName;
/*  26: 23 */     this.filter = filter;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static ItemStack makeResult(Item key, String keyName, ItemStack filter)
/*  30:    */   {
/*  31: 27 */     NBTTagCompound tags = new NBTTagCompound();
/*  32:    */     
/*  33: 29 */     tags.setBoolean(keyName, true);
/*  34: 30 */     filter.setTagCompound(tags);
/*  35: 31 */     return filter;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static List makeRecipes(Item key, String keyName, ItemStack filter)
/*  39:    */   {
/*  40: 35 */     List items = new ArrayList();
/*  41: 36 */     items.add(new ItemStack(key, 1, 1));
/*  42: 37 */     items.add(filter.copy());
/*  43: 38 */     return items;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean matches(InventoryCrafting inv, World world)
/*  47:    */   {
/*  48: 43 */     return getCraftingResult(inv) != null;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public ItemStack getCraftingResult(InventoryCrafting inv)
/*  52:    */   {
/*  53: 48 */     ItemStack filter = null;
/*  54: 49 */     boolean hasItem = false;
/*  55: 51 */     for (int i = 0; i < inv.getSizeInventory(); i++)
/*  56:    */     {
/*  57: 52 */       ItemStack item = inv.getStackInSlot(i);
/*  58: 54 */       if (item != null)
/*  59:    */       {
/*  60: 56 */         if ((item.getItem() == this.filter.getItem()) && (item.getItemDamage() == this.filter.getItemDamage())) {
/*  61: 57 */           if (filter == null) {
/*  62: 58 */             filter = item;
/*  63:    */           } else {
/*  64: 60 */             return null;
/*  65:    */           }
/*  66:    */         }
/*  67: 64 */         if (item.getItem() == this.key)
/*  68:    */         {
/*  69: 65 */           if (hasItem) {
/*  70: 66 */             return null;
/*  71:    */           }
/*  72: 68 */           hasItem = true;
/*  73:    */         }
/*  74:    */       }
/*  75:    */     }
/*  76: 74 */     if ((hasItem) && (filter != null))
/*  77:    */     {
/*  78: 75 */       ItemStack newFilter = filter.copy();
/*  79: 76 */       newFilter.stackSize = 1;
/*  80:    */       
/*  81: 78 */       NBTTagCompound tags = newFilter.getTagCompound();
/*  82: 80 */       if (tags == null) {
/*  83: 81 */         tags = new NBTTagCompound();
/*  84:    */       }
/*  85: 84 */       if ((tags.hasKey(this.keyName)) && (tags.getBoolean(this.keyName)))
/*  86:    */       {
/*  87: 85 */         tags.removeTag(this.keyName);
/*  88: 87 */         if (tags.hasNoTags()) {
/*  89: 88 */           tags = null;
/*  90:    */         }
/*  91:    */       }
/*  92:    */       else
/*  93:    */       {
/*  94: 91 */         tags.setBoolean(this.keyName, true);
/*  95:    */       }
/*  96: 94 */       newFilter.setTagCompound(tags);
/*  97: 95 */       return newFilter;
/*  98:    */     }
/*  99: 98 */     return null;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public int getRecipeSize()
/* 103:    */   {
/* 104:103 */     return 2;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public ItemStack getRecipeOutput()
/* 108:    */   {
/* 109:108 */     return makeResult(this.key, this.keyName, this.filter);
/* 110:    */   }
/* 111:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.crafting.RecipeFilterInvert
 * JD-Core Version:    0.7.0.1
 */