/*  1:   */ package com.rwtema.extrautils.nei;
/*  2:   */ 
/*  3:   */ import codechicken.nei.api.ItemFilter;
/*  4:   */ import com.rwtema.extrautils.item.filters.Matcher;
/*  5:   */ import net.minecraft.item.ItemStack;
/*  6:   */ 
/*  7:   */ public class ItemFilterWrapper
/*  8:   */   implements ItemFilter
/*  9:   */ {
/* 10:   */   private final Matcher base;
/* 11:   */   private final boolean invert;
/* 12:   */   
/* 13:   */   public ItemFilterWrapper(Matcher base, boolean invert)
/* 14:   */   {
/* 15:13 */     this.base = base;
/* 16:14 */     this.invert = invert;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public ItemFilterWrapper(Matcher matcher)
/* 20:   */   {
/* 21:18 */     this(matcher, false);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public boolean matches(ItemStack itemStack)
/* 25:   */   {
/* 26:23 */     return this.base.matchItem(itemStack) != this.invert;
/* 27:   */   }
/* 28:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.nei.ItemFilterWrapper
 * JD-Core Version:    0.7.0.1
 */