/*  1:   */ package com.rwtema.extrautils.inventory;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import net.minecraft.inventory.IInventory;
/*  5:   */ import net.minecraft.inventory.ISidedInventory;
/*  6:   */ 
/*  7:   */ public class SlotIterator
/*  8:   */   implements Iterator<Integer>, Iterable<Integer>
/*  9:   */ {
/* 10: 9 */   int[] sided = null;
/* 11:10 */   int i = 0;
/* 12:11 */   int size = 0;
/* 13:   */   
/* 14:   */   public SlotIterator(IInventory inv, int side)
/* 15:   */   {
/* 16:14 */     if ((inv instanceof ISidedInventory)) {
/* 17:15 */       this.sided = ((ISidedInventory)inv).getAccessibleSlotsFromSide(side);
/* 18:   */     } else {
/* 19:17 */       this.size = inv.getSizeInventory();
/* 20:   */     }
/* 21:   */   }
/* 22:   */   
/* 23:   */   public boolean hasNext()
/* 24:   */   {
/* 25:24 */     return this.i + 1 < this.size;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Integer next()
/* 29:   */   {
/* 30:29 */     this.i += 1;
/* 31:30 */     return Integer.valueOf(this.sided == null ? this.i : this.sided[this.i]);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void remove() {}
/* 35:   */   
/* 36:   */   public Iterator<Integer> iterator()
/* 37:   */   {
/* 38:40 */     return this;
/* 39:   */   }
/* 40:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.inventory.SlotIterator
 * JD-Core Version:    0.7.0.1
 */