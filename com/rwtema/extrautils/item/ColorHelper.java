/*  1:   */ package com.rwtema.extrautils.item;
/*  2:   */ 
/*  3:   */ public class ColorHelper
/*  4:   */ {
/*  5:   */   public static int colorFromHue(long h)
/*  6:   */   {
/*  7: 5 */     float x = (float)(h % 60L) / 60.0F;
/*  8: 6 */     int k = (int)(h % 360L) / 60;
/*  9: 7 */     float r = 0.0F;float g = 0.0F;float b = 0.0F;
/* 10: 9 */     switch (k)
/* 11:   */     {
/* 12:   */     case 0: 
/* 13:11 */       r = 1.0F;
/* 14:12 */       g = x;
/* 15:13 */       break;
/* 16:   */     case 1: 
/* 17:16 */       r = 1.0F - x;
/* 18:17 */       g = 1.0F;
/* 19:18 */       break;
/* 20:   */     case 2: 
/* 21:21 */       g = 1.0F;
/* 22:22 */       b = x;
/* 23:23 */       break;
/* 24:   */     case 3: 
/* 25:26 */       b = 1.0F;
/* 26:27 */       g = 1.0F - x;
/* 27:28 */       break;
/* 28:   */     case 4: 
/* 29:31 */       b = 1.0F;
/* 30:32 */       r = x;
/* 31:33 */       break;
/* 32:   */     case 5: 
/* 33:36 */       r = 1.0F;
/* 34:37 */       b = 1.0F - x;
/* 35:   */     }
/* 36:41 */     return (int)(r * 255.0F) << 16 | (int)(g * 255.0F) << 8 | (int)(b * 255.0F);
/* 37:   */   }
/* 38:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ColorHelper
 * JD-Core Version:    0.7.0.1
 */