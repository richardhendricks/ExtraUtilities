/*  1:   */ package com.rwtema.extrautils.nei;
/*  2:   */ 
/*  3:   */ import codechicken.nei.api.ItemFilter;
/*  4:   */ import net.minecraft.item.Item;
/*  5:   */ import net.minecraft.item.ItemStack;
/*  6:   */ 
/*  7:   */ public class SubsetItemsNBT
/*  8:   */   implements ItemFilter
/*  9:   */ {
/* 10:   */   public Item item;
/* 11:   */   
/* 12:   */   public SubsetItemsNBT(Item item)
/* 13:   */   {
/* 14:11 */     this.item = item;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public boolean matches(ItemStack item)
/* 18:   */   {
/* 19:16 */     return (item.hasTagCompound()) && (this.item.equals(item.getItem()));
/* 20:   */   }
/* 21:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.nei.SubsetItemsNBT
 * JD-Core Version:    0.7.0.1
 */