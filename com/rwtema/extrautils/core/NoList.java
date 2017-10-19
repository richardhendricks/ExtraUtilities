/*  1:   */ package com.rwtema.extrautils.core;
/*  2:   */ 
/*  3:   */ import java.util.AbstractList;
/*  4:   */ import java.util.List;
/*  5:   */ 
/*  6:   */ public class NoList<E>
/*  7:   */   extends AbstractList<E>
/*  8:   */   implements List<E>
/*  9:   */ {
/* 10:   */   public int size()
/* 11:   */   {
/* 12: 9 */     return 0;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public boolean isEmpty()
/* 16:   */   {
/* 17:14 */     return true;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean contains(Object o)
/* 21:   */   {
/* 22:19 */     return false;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public boolean add(E e)
/* 26:   */   {
/* 27:24 */     return false;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public boolean remove(Object o)
/* 31:   */   {
/* 32:29 */     return false;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void clear() {}
/* 36:   */   
/* 37:   */   public E get(int index)
/* 38:   */   {
/* 39:40 */     return null;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public E set(int index, E element)
/* 43:   */   {
/* 44:45 */     return null;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public void add(int index, E element) {}
/* 48:   */   
/* 49:   */   public E remove(int index)
/* 50:   */   {
/* 51:55 */     return null;
/* 52:   */   }
/* 53:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.core.NoList
 * JD-Core Version:    0.7.0.1
 */