/*  1:   */ package com.rwtema.extrautils.nei;
/*  2:   */ 
/*  3:   */ import codechicken.nei.api.ItemFilter;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import net.minecraft.item.Item;
/*  6:   */ import net.minecraft.item.ItemStack;
/*  7:   */ 
/*  8:   */ public class SubsetItems
/*  9:   */   implements ItemFilter
/* 10:   */ {
/* 11:10 */   public ArrayList<Item> items = new ArrayList();
/* 12:   */   
/* 13:   */   public SubsetItems(Item... items)
/* 14:   */   {
/* 15:13 */     for (Item i : items) {
/* 16:14 */       if (i != null) {
/* 17:15 */         this.items.add(i);
/* 18:   */       }
/* 19:   */     }
/* 20:   */   }
/* 21:   */   
/* 22:   */   public SubsetItems addItem(Item item)
/* 23:   */   {
/* 24:19 */     this.items.add(item);
/* 25:20 */     return this;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public boolean matches(ItemStack item)
/* 29:   */   {
/* 30:26 */     for (Item i : this.items) {
/* 31:27 */       if (i.equals(item.getItem())) {
/* 32:28 */         return true;
/* 33:   */       }
/* 34:   */     }
/* 35:30 */     return false;
/* 36:   */   }
/* 37:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.nei.SubsetItems
 * JD-Core Version:    0.7.0.1
 */