/*  1:   */ package com.rwtema.extrautils.tileentity.transfernodes;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.helper.XUHelper;
/*  4:   */ import net.minecraft.item.ItemStack;
/*  5:   */ 
/*  6:   */ public class Frequency
/*  7:   */ {
/*  8:   */   public final String freq;
/*  9:   */   public final String owner;
/* 10:   */   
/* 11:   */   public Frequency(String freq, String owner)
/* 12:   */   {
/* 13:11 */     this.freq = freq;
/* 14:12 */     this.owner = owner;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public Frequency(ItemStack item)
/* 18:   */   {
/* 19:16 */     this(XUHelper.getAnvilName(item), XUHelper.getPlayerOwner(item));
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean equals(Object o)
/* 23:   */   {
/* 24:21 */     if (this == o) {
/* 25:21 */       return true;
/* 26:   */     }
/* 27:22 */     if ((o == null) || (getClass() != o.getClass())) {
/* 28:22 */       return false;
/* 29:   */     }
/* 30:24 */     Frequency that = (Frequency)o;
/* 31:26 */     if (!this.freq.equals(that.freq)) {
/* 32:26 */       return false;
/* 33:   */     }
/* 34:27 */     if (!this.owner.equals(that.owner)) {
/* 35:27 */       return false;
/* 36:   */     }
/* 37:29 */     return true;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public int hashCode()
/* 41:   */   {
/* 42:34 */     int result = this.freq.hashCode();
/* 43:35 */     result = 31 * result + this.owner.hashCode();
/* 44:36 */     return result;
/* 45:   */   }
/* 46:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.Frequency
 * JD-Core Version:    0.7.0.1
 */