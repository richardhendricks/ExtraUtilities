/*  1:   */ package com.rwtema.extrautils.item;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ 
/*  5:   */ public class ItemFilingCabinet
/*  6:   */   extends ItemBlockMetadata
/*  7:   */ {
/*  8:   */   public ItemFilingCabinet(Block par1)
/*  9:   */   {
/* 10: 7 */     super(par1);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public boolean getShareTag()
/* 14:   */   {
/* 15:11 */     return false;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public boolean isDamageable()
/* 19:   */   {
/* 20:15 */     return false;
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemFilingCabinet
 * JD-Core Version:    0.7.0.1
 */