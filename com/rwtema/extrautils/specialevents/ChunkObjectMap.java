/*  1:   */ package com.rwtema.extrautils.specialevents;
/*  2:   */ 
/*  3:   */ import com.google.common.base.Function;
/*  4:   */ import gnu.trove.map.hash.TLongObjectHashMap;
/*  5:   */ 
/*  6:   */ public class ChunkObjectMap<V>
/*  7:   */ {
/*  8: 7 */   TLongObjectHashMap<V> map = new TLongObjectHashMap();
/*  9: 8 */   Function<Void, V> init = null;
/* 10:   */   
/* 11:   */   public static long getKey(int a, int b)
/* 12:   */   {
/* 13:11 */     return a | b << 32;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public V putChunk(int x, int z, V value)
/* 17:   */   {
/* 18:15 */     return this.map.put(getKey(x, z), value);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public V getChunk(int x, int z)
/* 22:   */   {
/* 23:19 */     if (this.init == null) {
/* 24:20 */       return this.map.get(getKey(x, z));
/* 25:   */     }
/* 26:22 */     long key = getKey(x, z);
/* 27:23 */     V v = this.map.get(key);
/* 28:24 */     if (v == null)
/* 29:   */     {
/* 30:25 */       v = this.init.apply(null);
/* 31:26 */       this.map.put(key, v);
/* 32:   */     }
/* 33:28 */     return v;
/* 34:   */   }
/* 35:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.specialevents.ChunkObjectMap
 * JD-Core Version:    0.7.0.1
 */