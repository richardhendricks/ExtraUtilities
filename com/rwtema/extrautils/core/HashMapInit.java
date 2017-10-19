/*  1:   */ package com.rwtema.extrautils.core;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ 
/*  5:   */ public class HashMapInit<K, V>
/*  6:   */   extends HashMap<K, V>
/*  7:   */ {
/*  8:   */   Class<? extends V> clazz;
/*  9:   */   
/* 10:   */   public HashMapInit(Class<? extends V> clazz)
/* 11:   */   {
/* 12: 9 */     this.clazz = clazz;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public V getOrInit(K key)
/* 16:   */   {
/* 17:13 */     V v = super.get(key);
/* 18:14 */     if (v == null) {
/* 19:   */       try
/* 20:   */       {
/* 21:16 */         v = this.clazz.newInstance();
/* 22:   */       }
/* 23:   */       catch (InstantiationException ignore) {}catch (IllegalAccessException ignore) {}
/* 24:   */     }
/* 25:24 */     return v;
/* 26:   */   }
/* 27:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.core.HashMapInit
 * JD-Core Version:    0.7.0.1
 */