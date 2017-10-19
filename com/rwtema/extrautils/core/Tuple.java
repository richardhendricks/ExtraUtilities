/*  1:   */ package com.rwtema.extrautils.core;
/*  2:   */ 
/*  3:   */ import java.util.Objects;
/*  4:   */ 
/*  5:   */ public final class Tuple<U, V>
/*  6:   */ {
/*  7:   */   private final U a;
/*  8:   */   private final V b;
/*  9:   */   
/* 10:   */   public Tuple(U a, V b)
/* 11:   */   {
/* 12:11 */     this.a = a;
/* 13:12 */     this.b = b;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public U getA()
/* 17:   */   {
/* 18:17 */     return this.a;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public V getB()
/* 22:   */   {
/* 23:22 */     return this.b;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public boolean equals(Object o)
/* 27:   */   {
/* 28:27 */     if (this == o) {
/* 29:27 */       return true;
/* 30:   */     }
/* 31:28 */     if ((o == null) || (getClass() != o.getClass())) {
/* 32:28 */       return false;
/* 33:   */     }
/* 34:30 */     Tuple tuple = (Tuple)o;
/* 35:31 */     return (Objects.equals(this.a, tuple.a)) && (Objects.equals(this.b, tuple.b));
/* 36:   */   }
/* 37:   */   
/* 38:   */   public int hashCode()
/* 39:   */   {
/* 40:36 */     int result = this.a != null ? this.a.hashCode() : 0;
/* 41:37 */     result = 31 * result + (this.b != null ? this.b.hashCode() : 0);
/* 42:38 */     return result;
/* 43:   */   }
/* 44:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.core.Tuple
 * JD-Core Version:    0.7.0.1
 */