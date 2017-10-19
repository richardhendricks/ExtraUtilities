/*  1:   */ package com.rwtema.extrautils.texture;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.modintegration.TConTextureResourcePackBedrockium;
/*  4:   */ import java.awt.image.BufferedImage;
/*  5:   */ import java.awt.image.DirectColorModel;
/*  6:   */ import net.minecraft.client.resources.data.AnimationMetadataSection;
/*  7:   */ import net.minecraft.util.MathHelper;
/*  8:   */ 
/*  9:   */ public class TextureBedrockLava
/* 10:   */   extends TextureDerived
/* 11:   */ {
/* 12:   */   public TextureBedrockLava(String p_i1282_1_, String baseIcon)
/* 13:   */   {
/* 14:11 */     super(p_i1282_1_, baseIcon, TextureDerived.TextureMapType.BLOCK);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public BufferedImage processImage(BufferedImage image, AnimationMetadataSection animationmetadatasection)
/* 18:   */   {
/* 19:16 */     int w = image.getWidth();
/* 20:17 */     int h = image.getHeight();
/* 21:   */     
/* 22:19 */     int[] pixels = new int[h * w];
/* 23:20 */     image.getRGB(0, 0, w, h, pixels, 0, w);
/* 24:   */     
/* 25:22 */     double mean = 0.0D;
/* 26:23 */     for (int i = 0; i < pixels.length; i++)
/* 27:   */     {
/* 28:24 */       pixels[i] = getLuminosity(pixels[i]);
/* 29:25 */       mean += pixels[i];
/* 30:   */     }
/* 31:28 */     mean /= pixels.length;
/* 32:   */     
/* 33:30 */     BufferedImage bedrockImage = TConTextureResourcePackBedrockium.getBedrockImage();
/* 34:32 */     for (int i = 0; i < pixels.length; i++)
/* 35:   */     {
/* 36:33 */       int x = i % w;
/* 37:34 */       int y = (i - x) / w % w;
/* 38:35 */       int sn = (i - x) / w / w;
/* 39:   */       
/* 40:37 */       int dx = x * bedrockImage.getWidth() / w;
/* 41:38 */       int dy = y * bedrockImage.getHeight() / w;
/* 42:   */       
/* 43:40 */       int col = bedrockImage.getRGB(dx, dy);
/* 44:   */       
/* 45:42 */       double f = pixels[i] / mean;
/* 46:   */       
/* 47:44 */       int r = clamp(this.rgb.getRed(col) * f);
/* 48:45 */       int g = clamp(this.rgb.getGreen(col) * f);
/* 49:46 */       int b = clamp(this.rgb.getBlue(col) * f);
/* 50:   */       
/* 51:48 */       pixels[i] = (0xFF000000 | r << 16 | g << 8 | b);
/* 52:   */     }
/* 53:51 */     image.setRGB(0, 0, w, h, pixels, 0, w);
/* 54:   */     
/* 55:53 */     return image;
/* 56:   */   }
/* 57:   */   
/* 58:   */   private int clamp(double v)
/* 59:   */   {
/* 60:57 */     return MathHelper.clamp_int((int)v, 0, 255);
/* 61:   */   }
/* 62:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.texture.TextureBedrockLava
 * JD-Core Version:    0.7.0.1
 */