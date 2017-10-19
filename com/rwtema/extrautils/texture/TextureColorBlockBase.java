/*   1:    */ package com.rwtema.extrautils.texture;
/*   2:    */ 
/*   3:    */ import cpw.mods.fml.relauncher.Side;
/*   4:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   5:    */ import java.awt.image.BufferedImage;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.util.List;
/*   8:    */ import javax.imageio.ImageIO;
/*   9:    */ import net.minecraft.client.Minecraft;
/*  10:    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*  11:    */ import net.minecraft.client.resources.IResource;
/*  12:    */ import net.minecraft.client.resources.IResourceManager;
/*  13:    */ import net.minecraft.client.resources.data.AnimationMetadataSection;
/*  14:    */ import net.minecraft.client.settings.GameSettings;
/*  15:    */ import net.minecraft.util.ResourceLocation;
/*  16:    */ 
/*  17:    */ @SideOnly(Side.CLIENT)
/*  18:    */ public class TextureColorBlockBase
/*  19:    */   extends TextureAtlasSprite
/*  20:    */ {
/*  21:    */   private String name;
/*  22:    */   private final String directory;
/*  23:    */   public float scale;
/*  24:    */   
/*  25:    */   public TextureColorBlockBase(String par1Str)
/*  26:    */   {
/*  27: 23 */     this(par1Str, "blocks");
/*  28:    */   }
/*  29:    */   
/*  30:    */   public TextureColorBlockBase(String par1Str, String directory)
/*  31:    */   {
/*  32: 27 */     super("extrautils:bw_(" + par1Str + ")");
/*  33: 28 */     this.name = par1Str;
/*  34: 29 */     this.directory = directory;
/*  35: 30 */     this.scale = 1.666667F;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public boolean load(IResourceManager manager, ResourceLocation location)
/*  39:    */   {
/*  40: 35 */     String s1 = "minecraft";
/*  41: 36 */     String s2 = this.name;
/*  42: 37 */     int ind = this.name.indexOf(':');
/*  43: 39 */     if (ind >= 0)
/*  44:    */     {
/*  45: 40 */       s2 = this.name.substring(ind + 1, this.name.length());
/*  46: 42 */       if (ind > 1) {
/*  47: 43 */         s1 = this.name.substring(0, ind);
/*  48:    */       }
/*  49:    */     }
/*  50: 47 */     int mp = Minecraft.getMinecraft().gameSettings.mipmapLevels;
/*  51:    */     
/*  52: 49 */     s1 = s1.toLowerCase();
/*  53:    */     
/*  54: 51 */     s2 = "textures/" + this.directory + "/" + s2 + ".png";
/*  55:    */     try
/*  56:    */     {
/*  57: 54 */       IResource iresource = manager.getResource(new ResourceLocation(s1, s2));
/*  58:    */       
/*  59: 56 */       BufferedImage[] abufferedimage = new BufferedImage[1 + mp];
/*  60: 57 */       abufferedimage[0] = ImageIO.read(iresource.getInputStream());
/*  61:    */       
/*  62: 59 */       AnimationMetadataSection animationmetadatasection = (AnimationMetadataSection)iresource.getMetadata("animation");
/*  63:    */       
/*  64: 61 */       func_147964_a(abufferedimage, animationmetadatasection, Minecraft.getMinecraft().gameSettings.anisotropicFiltering > 1.0F);
/*  65:    */     }
/*  66:    */     catch (IOException e)
/*  67:    */     {
/*  68: 63 */       return true;
/*  69:    */     }
/*  70: 66 */     for (int j = 0; j < this.framesTextureData.size(); j++)
/*  71:    */     {
/*  72: 67 */       int[] image = new int[((int[][])(int[][])this.framesTextureData.get(j))[0].length];
/*  73: 68 */       float min_m = 1.0F;float max_m = 0.0F;
/*  74: 70 */       for (int i = 0; i < image.length; i++)
/*  75:    */       {
/*  76: 71 */         int l = ((int[][])(int[][])this.framesTextureData.get(j))[0][i];
/*  77: 73 */         if (l < 0)
/*  78:    */         {
/*  79: 76 */           float r = (-l >> 16 & 0xFF) / 255.0F;
/*  80: 77 */           float g = (-l >> 8 & 0xFF) / 255.0F;
/*  81: 78 */           float b = (-l & 0xFF) / 255.0F;
/*  82: 79 */           r = 1.0F - r;
/*  83: 80 */           g = 1.0F - g;
/*  84: 81 */           b = 1.0F - b;
/*  85: 82 */           float m = r * 0.2126F + g * 0.7152F + b * 0.0722F;
/*  86: 84 */           if (m > max_m) {
/*  87: 85 */             max_m = m;
/*  88:    */           }
/*  89: 88 */           if (m < min_m) {
/*  90: 89 */             min_m = m;
/*  91:    */           }
/*  92:    */         }
/*  93:    */       }
/*  94: 93 */       if ((min_m == 1.0F) && (max_m == 0.0F)) {
/*  95: 94 */         return false;
/*  96:    */       }
/*  97: 96 */       if (max_m == min_m) {
/*  98: 97 */         max_m = min_m + 0.001F;
/*  99:    */       }
/* 100:101 */       float new_max_m = Math.min(max_m * this.scale, 1.0F);
/* 101:102 */       float new_min_m = min_m / max_m * new_max_m;
/* 102:105 */       for (int i = 0; i < image.length; i++)
/* 103:    */       {
/* 104:106 */         int l = ((int[][])(int[][])this.framesTextureData.get(j))[0][i];
/* 105:108 */         if (l < 0)
/* 106:    */         {
/* 107:111 */           float r = (-l >> 16 & 0xFF) / 255.0F;
/* 108:112 */           float g = (-l >> 8 & 0xFF) / 255.0F;
/* 109:113 */           float b = (-l & 0xFF) / 255.0F;
/* 110:114 */           r = 1.0F - r;
/* 111:115 */           g = 1.0F - g;
/* 112:116 */           b = 1.0F - b;
/* 113:117 */           float m = r * 0.2126F + g * 0.7152F + b * 0.0722F;
/* 114:118 */           float dm = (m - min_m) / (max_m - min_m);
/* 115:119 */           m = new_min_m + dm * (new_max_m - new_min_m);
/* 116:120 */           r = g = b = Math.max(Math.min(0.975F, m), 0.025F);
/* 117:121 */           r = 1.0F - r;
/* 118:122 */           g = 1.0F - g;
/* 119:123 */           b = 1.0F - b;
/* 120:124 */           image[i] = (-((int)(r * 255.0F) << 16 | (int)(g * 255.0F) << 8 | (int)(b * 255.0F)));
/* 121:    */         }
/* 122:    */       }
/* 123:126 */       int[][] aint = new int[1 + mp][];
/* 124:127 */       aint[0] = image;
/* 125:128 */       this.framesTextureData.set(j, aint);
/* 126:    */     }
/* 127:131 */     return false;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location)
/* 131:    */   {
/* 132:135 */     return true;
/* 133:    */   }
/* 134:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.texture.TextureColorBlockBase
 * JD-Core Version:    0.7.0.1
 */