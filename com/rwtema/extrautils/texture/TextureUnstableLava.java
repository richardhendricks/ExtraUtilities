/*  1:   */ package com.rwtema.extrautils.texture;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.helper.XURandom;
/*  4:   */ import java.awt.image.BufferedImage;
/*  5:   */ import java.awt.image.DirectColorModel;
/*  6:   */ import net.minecraft.client.resources.data.AnimationMetadataSection;
/*  7:   */ 
/*  8:   */ public class TextureUnstableLava
/*  9:   */   extends TextureDerived
/* 10:   */ {
/* 11:   */   public TextureUnstableLava(String p_i1282_1_, String baseIcon)
/* 12:   */   {
/* 13:11 */     super(p_i1282_1_, baseIcon, TextureDerived.TextureMapType.BLOCK);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public BufferedImage processImage(BufferedImage image, AnimationMetadataSection animationmetadatasection)
/* 17:   */   {
/* 18:16 */     int w = image.getWidth();
/* 19:17 */     int h = image.getHeight();
/* 20:18 */     int[] aint = new int[h * w];
/* 21:19 */     int[] c = new int[256];
/* 22:20 */     image.getRGB(0, 0, w, h, aint, 0, w);
/* 23:   */     
/* 24:22 */     int n1 = 0;
/* 25:24 */     for (int i = 0; i < aint.length; i++) {
/* 26:25 */       if (this.rgb.getAlpha(aint[i]) > 10)
/* 27:   */       {
/* 28:26 */         aint[i] = getLuminosity(aint[i]);
/* 29:27 */         n1 = Math.max(n1, aint[i]);
/* 30:   */       }
/* 31:   */       else
/* 32:   */       {
/* 33:29 */         aint[i] = 255;
/* 34:   */       }
/* 35:   */     }
/* 36:32 */     int v = h / w;
/* 37:34 */     for (int i = 0; i < aint.length; i++)
/* 38:   */     {
/* 39:36 */       int x = i % w;
/* 40:37 */       int y = (i - x) / w % w;
/* 41:38 */       int sn = (i - x) / w / w;
/* 42:   */       
/* 43:40 */       int p = 1;
/* 44:41 */       int lum = 256 + (x * 16 / w + y * 16 / w - 16) % 32 * 1 * 2;
/* 45:42 */       int t = 0;
/* 46:43 */       while (((lum >= 256) || (lum < 240)) && (t < 100))
/* 47:   */       {
/* 48:44 */         t++;
/* 49:45 */         if (lum >= 256) {
/* 50:46 */           lum = 511 - lum;
/* 51:   */         }
/* 52:48 */         if (lum < 240) {
/* 53:49 */           lum = 480 - lum;
/* 54:   */         }
/* 55:   */       }
/* 56:54 */       int col = aint[i];
/* 57:   */       
/* 58:56 */       int l = col + n1;
/* 59:57 */       l = 255 - (255 - l) * 2;
/* 60:58 */       if (l < 0) {
/* 61:58 */         l = 0;
/* 62:   */       }
/* 63:60 */       l = 192 + (l >> 2);
/* 64:61 */       if (XURandom.getInstance().nextInt(3) != 0) {
/* 65:62 */         l -= XURandom.getInstance().nextInt(4);
/* 66:   */       }
/* 67:64 */       l = l * lum / 255;
/* 68:66 */       if (l > 255) {
/* 69:66 */         l = 255;
/* 70:   */       }
/* 71:67 */       if (l < 128) {
/* 72:67 */         l = 128;
/* 73:   */       }
/* 74:70 */       aint[i] = (0xFF000000 | l * 65793);
/* 75:   */     }
/* 76:73 */     image.setRGB(0, 0, w, h, aint, 0, w);
/* 77:74 */     return image;
/* 78:   */   }
/* 79:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.texture.TextureUnstableLava
 * JD-Core Version:    0.7.0.1
 */