/*  1:   */ package com.rwtema.extrautils.helper;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ 
/*  5:   */ public class XURandom
/*  6:   */   extends Random
/*  7:   */ {
/*  8: 6 */   private static final XURandom INSTANCE = new XURandom();
/*  9: 8 */   private long[] rng = new long[16];
/* 10:   */   private int i;
/* 11:   */   
/* 12:   */   public static XURandom getInstance()
/* 13:   */   {
/* 14:12 */     return INSTANCE;
/* 15:   */   }
/* 16:   */   
/* 17:   */   private synchronized void fillRNG(long seed)
/* 18:   */   {
/* 19:16 */     this.i = 0;
/* 20:17 */     seed = (seed ^ 0xDEECE66D) & 0xFFFFFFFF;
/* 21:18 */     this.rng = new long[16];
/* 22:19 */     this.rng[0] = seed;
/* 23:20 */     for (int i = 1; i < this.rng.length; i++)
/* 24:   */     {
/* 25:21 */       seed ^= seed >> 12;
/* 26:22 */       seed ^= seed << 25;
/* 27:23 */       seed ^= seed >> 27;
/* 28:24 */       this.rng[i] = (seed * 2685821657736338717L);
/* 29:   */     }
/* 30:   */   }
/* 31:   */   
/* 32:   */   public XURandom()
/* 33:   */   {
/* 34:31 */     if (this.rng[0] == 0L) {
/* 35:31 */       setSeed(new Random().nextLong());
/* 36:   */     }
/* 37:   */   }
/* 38:   */   
/* 39:   */   public XURandom(long seed)
/* 40:   */   {
/* 41:35 */     super(seed);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public synchronized void setSeed(long seed)
/* 45:   */   {
/* 46:40 */     super.setSeed(seed);
/* 47:41 */     this.i = 0;
/* 48:42 */     fillRNG(seed);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public synchronized void setRNGArray(long... rngArray)
/* 52:   */   {
/* 53:46 */     this.i = 0;
/* 54:47 */     System.arraycopy(rngArray, 0, this.rng, 0, this.rng.length);
/* 55:   */   }
/* 56:   */   
/* 57:   */   public int next(int nbits)
/* 58:   */   {
/* 59:52 */     long x = nextLong() & (1L << nbits) - 1L;
/* 60:53 */     return (int)x;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public synchronized long nextLong()
/* 64:   */   {
/* 65:58 */     if (this.rng == null) {
/* 66:58 */       return 0L;
/* 67:   */     }
/* 68:59 */     long a = this.rng[this.i];
/* 69:60 */     this.i = (this.i + 1 & 0xF);
/* 70:61 */     long b = this.rng[this.i];
/* 71:62 */     b ^= b << 31;
/* 72:63 */     b ^= b >> 11;
/* 73:64 */     a ^= a >> 30;
/* 74:65 */     this.rng[this.i] = (a ^ b);
/* 75:66 */     return this.rng[this.i] * 1181783497276652981L;
/* 76:   */   }
/* 77:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.helper.XURandom
 * JD-Core Version:    0.7.0.1
 */