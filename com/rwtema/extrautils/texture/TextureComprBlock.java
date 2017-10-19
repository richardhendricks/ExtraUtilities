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
/*  14:    */ import net.minecraft.client.resources.data.TextureMetadataSection;
/*  15:    */ import net.minecraft.client.settings.GameSettings;
/*  16:    */ import net.minecraft.util.ResourceLocation;
/*  17:    */ 
/*  18:    */ @SideOnly(Side.CLIENT)
/*  19:    */ public class TextureComprBlock
/*  20:    */   extends TextureAtlasSprite
/*  21:    */ {
/*  22:    */   private int n;
/*  23:    */   private ResourceLocation textureLocation;
/*  24:    */   
/*  25:    */   public TextureComprBlock(String par1Str, String base, int n)
/*  26:    */   {
/*  27: 24 */     super(par1Str);
/*  28: 25 */     this.n = n;
/*  29: 26 */     this.textureLocation = new ResourceLocation("minecraft", "textures/blocks/" + base + ".png");
/*  30:    */   }
/*  31:    */   
/*  32:    */   public boolean load(IResourceManager manager, ResourceLocation location)
/*  33:    */   {
/*  34: 32 */     int mp = Minecraft.getMinecraft().gameSettings.mipmapLevels;
/*  35:    */     float nh;
/*  36:    */     float br;
/*  37:    */     int j;
/*  38:    */     try
/*  39:    */     {
/*  40: 36 */       IResource iresource = manager.getResource(location);
/*  41:    */       
/*  42: 38 */       BufferedImage[] abufferedimage = new BufferedImage[1 + Minecraft.getMinecraft().gameSettings.mipmapLevels];
/*  43: 39 */       abufferedimage[0] = ImageIO.read(iresource.getInputStream());
/*  44: 40 */       TextureMetadataSection texturemetadatasection = (TextureMetadataSection)iresource.getMetadata("texture");
/*  45:    */       
/*  46: 42 */       AnimationMetadataSection animationmetadatasection = (AnimationMetadataSection)iresource.getMetadata("animation");
/*  47:    */       
/*  48: 44 */       func_147964_a(abufferedimage, animationmetadatasection, Minecraft.getMinecraft().gameSettings.anisotropicFiltering > 1.0F);
/*  49:    */     }
/*  50:    */     catch (IOException e)
/*  51:    */     {
/*  52:    */       try
/*  53:    */       {
/*  54: 49 */         IResource iresource = manager.getResource(this.textureLocation);
/*  55:    */         
/*  56: 51 */         BufferedImage[] abufferedimage = new BufferedImage[1 + Minecraft.getMinecraft().gameSettings.mipmapLevels];
/*  57: 52 */         abufferedimage[0] = ImageIO.read(iresource.getInputStream());
/*  58: 53 */         TextureMetadataSection texturemetadatasection = (TextureMetadataSection)iresource.getMetadata("texture");
/*  59:    */         
/*  60: 55 */         AnimationMetadataSection animationmetadatasection = (AnimationMetadataSection)iresource.getMetadata("animation");
/*  61:    */         
/*  62: 57 */         func_147964_a(abufferedimage, animationmetadatasection, Minecraft.getMinecraft().gameSettings.anisotropicFiltering > 1.0F);
/*  63:    */       }
/*  64:    */       catch (IOException e1)
/*  65:    */       {
/*  66: 60 */         e.printStackTrace();
/*  67: 61 */         return true;
/*  68:    */       }
/*  69: 64 */       nh = this.n / 8.5F;
/*  70: 65 */       br = 1.0F - nh;
/*  71:    */       
/*  72: 67 */       j = 0;
/*  73:    */     }
/*  74: 67 */     for (; j < this.framesTextureData.size(); j++)
/*  75:    */     {
/*  76: 68 */       int[] image = new int[((int[][])(int[][])this.framesTextureData.get(j))[0].length];
/*  77: 70 */       for (int i = 0; i < image.length; i++)
/*  78:    */       {
/*  79: 71 */         int x = i % this.width;
/*  80: 72 */         int y = i / this.height;
/*  81: 73 */         int l = ((int[][])(int[][])this.framesTextureData.get(j))[0][i];
/*  82: 74 */         float r = (-l >> 16 & 0xFF) / 255.0F;
/*  83: 75 */         float g = (-l >> 8 & 0xFF) / 255.0F;
/*  84: 76 */         float b = (-l & 0xFF) / 255.0F;
/*  85: 77 */         float dx = 2 * x / (this.width - 1) - 1.0F;
/*  86: 78 */         float dy = 2 * y / (this.height - 1) - 1.0F;
/*  87: 79 */         float db = Math.max(Math.abs(dx), Math.abs(dy));
/*  88: 80 */         db = Math.max(db, (float)Math.sqrt(dx * dx + dy * dy) / 1.4F);
/*  89: 81 */         float d = 1.0F - db + 1.0F - nh;
/*  90: 82 */         float rb = 1.0F - (2 + this.n) / 32.0F;
/*  91: 83 */         float k = 1.0F;
/*  92: 85 */         if (db > rb) {
/*  93: 86 */           k = 0.7F + 0.1F * (db - rb) / (1.0F - rb);
/*  94:    */         }
/*  95: 89 */         d *= k * k;
/*  96: 91 */         if (d > 1.0F) {
/*  97: 92 */           d = 1.0F;
/*  98: 93 */         } else if (d < 0.0F) {
/*  99: 94 */           d = 0.0F;
/* 100:    */         }
/* 101: 97 */         r = 1.0F - (1.0F - r) * br * d;
/* 102: 98 */         g = 1.0F - (1.0F - g) * br * d;
/* 103: 99 */         b = 1.0F - (1.0F - b) * br * d;
/* 104:    */         
/* 105:101 */         image[i] = (-((int)(r * 255.0F) << 16 | (int)(g * 255.0F) << 8 | (int)(b * 255.0F)));
/* 106:    */       }
/* 107:104 */       int[][] aint = new int[1 + mp][];
/* 108:105 */       aint[0] = image;
/* 109:106 */       this.framesTextureData.set(j, aint);
/* 110:    */     }
/* 111:110 */     return false;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location)
/* 115:    */   {
/* 116:114 */     return true;
/* 117:    */   }
/* 118:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.texture.TextureComprBlock
 * JD-Core Version:    0.7.0.1
 */