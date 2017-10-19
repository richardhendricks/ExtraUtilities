/*  1:   */ package com.rwtema.extrautils.helper;
/*  2:   */ 
/*  3:   */ import java.lang.ref.WeakReference;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import java.util.LinkedList;
/*  6:   */ 
/*  7:   */ public class WeakList<E>
/*  8:   */   implements Iterable<E>
/*  9:   */ {
/* 10:   */   LinkedList<WeakReference<E>> list;
/* 11:   */   
/* 12:   */   public WeakList()
/* 13:   */   {
/* 14: 8 */     this.list = new LinkedList();
/* 15:   */   }
/* 16:   */   
/* 17:   */   public boolean add(E a)
/* 18:   */   {
/* 19:11 */     return (a != null) && (this.list.add(new WeakReference(a)));
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void clear()
/* 23:   */   {
/* 24:15 */     this.list.clear();
/* 25:   */   }
/* 26:   */   
/* 27:   */   public Iterator<E> iterator()
/* 28:   */   {
/* 29:20 */     return new WeakIterator();
/* 30:   */   }
/* 31:   */   
/* 32:   */   public class WeakIterator
/* 33:   */     implements Iterator<E>
/* 34:   */   {
/* 35:   */     E next;
/* 36:   */     Iterator<WeakReference<E>> iter;
/* 37:   */     
/* 38:   */     public WeakIterator()
/* 39:   */     {
/* 40:29 */       this.next = null;
/* 41:30 */       loadNext();
/* 42:   */     }
/* 43:   */     
/* 44:   */     private void loadNext()
/* 45:   */     {
/* 46:34 */       this.next = null;
/* 47:35 */       while (this.iter.hasNext())
/* 48:   */       {
/* 49:36 */         this.next = ((WeakReference)this.iter.next()).get();
/* 50:37 */         if (this.next != null) {
/* 51:38 */           return;
/* 52:   */         }
/* 53:40 */         this.iter.remove();
/* 54:   */       }
/* 55:   */     }
/* 56:   */     
/* 57:   */     public boolean hasNext()
/* 58:   */     {
/* 59:46 */       return this.next != null;
/* 60:   */     }
/* 61:   */     
/* 62:   */     public E next()
/* 63:   */     {
/* 64:51 */       E e = this.next;
/* 65:52 */       loadNext();
/* 66:53 */       return e;
/* 67:   */     }
/* 68:   */     
/* 69:   */     public void remove()
/* 70:   */     {
/* 71:58 */       this.iter.remove();
/* 72:   */     }
/* 73:   */   }
/* 74:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.helper.WeakList
 * JD-Core Version:    0.7.0.1
 */