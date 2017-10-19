/*  1:   */ package com.rwtema.extrautils.modintegration;
/*  2:   */ 
/*  3:   */ import java.awt.image.BufferedImage;
/*  4:   */ import java.awt.image.DirectColorModel;
/*  5:   */ 
/*  6:   */ public class TConTextureResourcePackUnstableIngot
/*  7:   */   extends TConTextureResourcePackBase
/*  8:   */ {
/*  9:   */   public TConTextureResourcePackUnstableIngot(String name)
/* 10:   */   {
/* 11: 8 */     super(name);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public BufferedImage modifyImage(BufferedImage image)
/* 15:   */   {
/* 16:14 */     int width = image.getWidth();
/* 17:15 */     int height = image.getHeight();
/* 18:   */     
/* 19:17 */     boolean[][] trans = new boolean[width][height];
/* 20:18 */     boolean[][] edge = new boolean[width][height];
/* 21:19 */     for (int x = 0; x < width; x++) {
/* 22:20 */       for (int y = 0; y < height; y++)
/* 23:   */       {
/* 24:21 */         if ((x == 0) || (y == 0) || (x == width - 1) || (y == height - 1)) {
/* 25:22 */           edge[x][y] = 1;
/* 26:   */         }
/* 27:25 */         int c = image.getRGB(x, y);
/* 28:26 */         if ((c == 0) || (rgb.getAlpha(c) < 64))
/* 29:   */         {
/* 30:27 */           trans[x][y] = 1;
/* 31:29 */           if (x > 0) {
/* 32:29 */             edge[(x - 1)][y] = 1;
/* 33:   */           }
/* 34:30 */           if (y > 0) {
/* 35:30 */             edge[x][(y - 1)] = 1;
/* 36:   */           }
/* 37:31 */           if (x < width - 1) {
/* 38:31 */             edge[(x + 1)][y] = 1;
/* 39:   */           }
/* 40:32 */           if (y < height - 1) {
/* 41:32 */             edge[x][(y + 1)] = 1;
/* 42:   */           }
/* 43:   */         }
/* 44:   */       }
/* 45:   */     }
/* 46:37 */     int white = -1;
/* 47:39 */     for (int x = 0; x < width; x++) {
/* 48:40 */       for (int y = 0; y < height; y++) {
/* 49:41 */         if (trans[x][y] == 0)
/* 50:   */         {
/* 51:46 */           int c = image.getRGB(x, y);
/* 52:   */           
/* 53:48 */           int lum = brightness(rgb.getRed(c), rgb.getGreen(c), rgb.getBlue(c));
/* 54:51 */           if (edge[x][y] != 0)
/* 55:   */           {
/* 56:52 */             int alpha = 255;
/* 57:   */             
/* 58:54 */             lum = 256 + (x * 16 / width + y * 16 / height - 16) * 6;
/* 59:55 */             if (lum >= 256) {
/* 60:56 */               lum = 255 - (lum - 256);
/* 61:   */             }
/* 62:59 */             int col = alpha << 24 | lum << 16 | lum << 8 | lum;
/* 63:60 */             image.setRGB(x, y, col);
/* 64:   */           }
/* 65:   */           else
/* 66:   */           {
/* 67:62 */             image.setRGB(x, y, 0);
/* 68:   */           }
/* 69:   */         }
/* 70:   */       }
/* 71:   */     }
/* 72:68 */     return image;
/* 73:   */   }
/* 74:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.modintegration.TConTextureResourcePackUnstableIngot
 * JD-Core Version:    0.7.0.1
 */