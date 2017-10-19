/*  1:   */ package com.rwtema.extrautils.core;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ 
/*  5:   */ public class CastIterator<T>
/*  6:   */   implements Iterable<T>, Iterator<T>
/*  7:   */ {
/*  8:   */   Iterator iterator;
/*  9:   */   
/* 10:   */   public CastIterator(Iterable iterable)
/* 11:   */   {
/* 12: 9 */     this(iterable.iterator());
/* 13:   */   }
/* 14:   */   
/* 15:   */   public CastIterator(Iterator iterator)
/* 16:   */   {
/* 17:13 */     this.iterator = iterator;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public Iterator<T> iterator()
/* 21:   */   {
/* 22:18 */     return this;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public boolean hasNext()
/* 26:   */   {
/* 27:23 */     return this.iterator.hasNext();
/* 28:   */   }
/* 29:   */   
/* 30:   */   public T next()
/* 31:   */   {
/* 32:29 */     return this.iterator.next();
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void remove()
/* 36:   */   {
/* 37:34 */     this.iterator.remove();
/* 38:   */   }
/* 39:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.core.CastIterator
 * JD-Core Version:    0.7.0.1
 */