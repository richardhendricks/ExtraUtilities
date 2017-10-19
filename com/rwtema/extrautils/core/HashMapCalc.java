/*  1:   */ package com.rwtema.extrautils.core;
/*  2:   */ 
/*  3:   */ import com.google.common.base.Function;
/*  4:   */ import java.util.HashMap;
/*  5:   */ 
/*  6:   */ public abstract class HashMapCalc<K, V>
/*  7:   */   extends HashMap<K, V>
/*  8:   */ {
/*  9:   */   Function<K, V> function;
/* 10:   */   
/* 11:   */   protected HashMapCalc(Function<K, V> function)
/* 12:   */   {
/* 13:11 */     this.function = function;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public V getCalc(K key)
/* 17:   */   {
/* 18:15 */     if (!containsKey(key))
/* 19:   */     {
/* 20:16 */       V calcEntry = this.function.apply(key);
/* 21:17 */       put(key, calcEntry);
/* 22:   */     }
/* 23:19 */     return get(key);
/* 24:   */   }
/* 25:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.core.HashMapCalc
 * JD-Core Version:    0.7.0.1
 */