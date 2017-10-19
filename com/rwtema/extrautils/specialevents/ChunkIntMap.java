/*  1:   */ package com.rwtema.extrautils.specialevents;
/*  2:   */ 
/*  3:   */ import gnu.trove.map.hash.TLongIntHashMap;
/*  4:   */ 
/*  5:   */ public class ChunkIntMap
/*  6:   */   extends TLongIntHashMap
/*  7:   */ {
/*  8:   */   public static long getKey(int a, int b)
/*  9:   */   {
/* 10: 7 */     return a | b << 32;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public int put(int a, int b, int value)
/* 14:   */   {
/* 15:11 */     return put(getKey(a, b), value);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public int get(int a, int b)
/* 19:   */   {
/* 20:15 */     return get(getKey(a, b));
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.specialevents.ChunkIntMap
 * JD-Core Version:    0.7.0.1
 */