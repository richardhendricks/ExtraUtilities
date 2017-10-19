/*  1:   */ package com.rwtema.extrautils.helper;
/*  2:   */ 
/*  3:   */ import java.util.AbstractSet;
/*  4:   */ import java.util.Collection;
/*  5:   */ import java.util.Iterator;
/*  6:   */ import java.util.Set;
/*  7:   */ import java.util.WeakHashMap;
/*  8:   */ 
/*  9:   */ public class WeakSet<E>
/* 10:   */   extends AbstractSet<E>
/* 11:   */   implements Set<E>
/* 12:   */ {
/* 13:   */   private transient WeakHashMap<E, Object> map;
/* 14: 8 */   private static final Object PRESENT = new Object();
/* 15:   */   
/* 16:   */   public WeakSet()
/* 17:   */   {
/* 18:11 */     this.map = new WeakHashMap();
/* 19:   */   }
/* 20:   */   
/* 21:   */   public WeakSet(Collection<? extends E> c)
/* 22:   */   {
/* 23:15 */     this.map = new WeakHashMap(Math.max((int)(c.size() / 0.75F) + 1, 16));
/* 24:16 */     addAll(c);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public WeakSet(int initialCapacity, float loadFactor)
/* 28:   */   {
/* 29:20 */     this.map = new WeakHashMap(initialCapacity, loadFactor);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public WeakSet(int initialCapacity)
/* 33:   */   {
/* 34:24 */     this.map = new WeakHashMap(initialCapacity);
/* 35:   */   }
/* 36:   */   
/* 37:   */   WeakSet(int initialCapacity, float loadFactor, boolean dummy)
/* 38:   */   {
/* 39:28 */     this.map = new WeakHashMap(initialCapacity, loadFactor);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public Iterator<E> iterator()
/* 43:   */   {
/* 44:32 */     return this.map.keySet().iterator();
/* 45:   */   }
/* 46:   */   
/* 47:   */   public int size()
/* 48:   */   {
/* 49:36 */     return this.map.size();
/* 50:   */   }
/* 51:   */   
/* 52:   */   public boolean isEmpty()
/* 53:   */   {
/* 54:40 */     return this.map.isEmpty();
/* 55:   */   }
/* 56:   */   
/* 57:   */   public boolean contains(Object o)
/* 58:   */   {
/* 59:44 */     return this.map.containsKey(o);
/* 60:   */   }
/* 61:   */   
/* 62:   */   public boolean add(E e)
/* 63:   */   {
/* 64:48 */     return this.map.put(e, PRESENT) == null;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public boolean remove(Object o)
/* 68:   */   {
/* 69:52 */     return this.map.remove(o) == PRESENT;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public void clear()
/* 73:   */   {
/* 74:56 */     this.map.clear();
/* 75:   */   }
/* 76:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.helper.WeakSet
 * JD-Core Version:    0.7.0.1
 */