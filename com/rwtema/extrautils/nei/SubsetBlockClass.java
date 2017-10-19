/*  1:   */ package com.rwtema.extrautils.nei;
/*  2:   */ 
/*  3:   */ import codechicken.nei.api.ItemFilter;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.item.ItemStack;
/*  6:   */ 
/*  7:   */ public class SubsetBlockClass
/*  8:   */   implements ItemFilter
/*  9:   */ {
/* 10:   */   public Class<? extends Block> base;
/* 11:   */   
/* 12:   */   public SubsetBlockClass(Class<? extends Block> base)
/* 13:   */   {
/* 14:11 */     this.base = base;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public boolean matches(ItemStack item)
/* 18:   */   {
/* 19:17 */     return this.base.equals(Block.getBlockFromItem(item.getItem()).getClass());
/* 20:   */   }
/* 21:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.nei.SubsetBlockClass
 * JD-Core Version:    0.7.0.1
 */