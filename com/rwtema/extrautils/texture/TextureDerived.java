/*  1:   */ package com.rwtema.extrautils.texture;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.client.FMLClientHandler;
/*  4:   */ import java.awt.image.BufferedImage;
/*  5:   */ import java.awt.image.DirectColorModel;
/*  6:   */ import java.io.IOException;
/*  7:   */ import javax.imageio.ImageIO;
/*  8:   */ import net.minecraft.client.Minecraft;
/*  9:   */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/* 10:   */ import net.minecraft.client.resources.IResource;
/* 11:   */ import net.minecraft.client.resources.IResourceManager;
/* 12:   */ import net.minecraft.client.resources.data.AnimationMetadataSection;
/* 13:   */ import net.minecraft.client.settings.GameSettings;
/* 14:   */ import net.minecraft.util.ResourceLocation;
/* 15:   */ 
/* 16:   */ public abstract class TextureDerived
/* 17:   */   extends TextureAtlasSprite
/* 18:   */ {
/* 19:   */   private final String baseIcon;
/* 20:   */   private final String basePath;
/* 21:   */   private final TextureMapType type;
/* 22:   */   
/* 23:   */   public static enum TextureMapType
/* 24:   */   {
/* 25:22 */     BLOCK(0, "textures/blocks"),  ITEM(1, "textures/items");
/* 26:   */     
/* 27:   */     public final int textureMapType;
/* 28:   */     public final String basePath;
/* 29:   */     
/* 30:   */     private TextureMapType(int textureMapType, String basePath)
/* 31:   */     {
/* 32:29 */       this.textureMapType = textureMapType;
/* 33:30 */       this.basePath = basePath;
/* 34:   */     }
/* 35:   */   }
/* 36:   */   
/* 37:   */   public TextureDerived(String p_i1282_1_, String baseIcon, TextureMapType type)
/* 38:   */   {
/* 39:35 */     super(p_i1282_1_);
/* 40:36 */     this.baseIcon = baseIcon;
/* 41:37 */     this.type = type;
/* 42:38 */     this.basePath = type.basePath;
/* 43:   */   }
/* 44:   */   
/* 45:42 */   public static DirectColorModel rgbBase = new DirectColorModel(32, 16711680, 65280, 255, -16777216);
/* 46:48 */   protected DirectColorModel rgb = rgbBase;
/* 47:   */   
/* 48:   */   public int getLuminosity(int col)
/* 49:   */   {
/* 50:51 */     return getLuminosity(this.rgb.getRed(col), this.rgb.getGreen(col), this.rgb.getBlue(col));
/* 51:   */   }
/* 52:   */   
/* 53:   */   public int getLuminosity(int r, int g, int b)
/* 54:   */   {
/* 55:55 */     return (int)(r * 0.2126F + g * 0.7152F + b * 0.0722F);
/* 56:   */   }
/* 57:   */   
/* 58:   */   public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location)
/* 59:   */   {
/* 60:60 */     return true;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public abstract BufferedImage processImage(BufferedImage paramBufferedImage, AnimationMetadataSection paramAnimationMetadataSection);
/* 64:   */   
/* 65:   */   public boolean load(IResourceManager manager, ResourceLocation oldlocation)
/* 66:   */   {
/* 67:68 */     ResourceLocation location = new ResourceLocation(this.baseIcon);
/* 68:69 */     location = completeResourceLocation(location);
/* 69:   */     try
/* 70:   */     {
/* 71:71 */       int mipmapLevels = Minecraft.getMinecraft().gameSettings.mipmapLevels;
/* 72:72 */       int anisotropicFiltering = Minecraft.getMinecraft().gameSettings.anisotropicFiltering;
/* 73:73 */       IResource iresource = manager.getResource(location);
/* 74:74 */       BufferedImage[] abufferedimage = new BufferedImage[1 + mipmapLevels];
/* 75:75 */       abufferedimage[0] = ImageIO.read(iresource.getInputStream());
/* 76:76 */       AnimationMetadataSection animationmetadatasection = (AnimationMetadataSection)iresource.getMetadata("animation");
/* 77:77 */       abufferedimage[0] = processImage(abufferedimage[0], animationmetadatasection);
/* 78:78 */       func_147964_a(abufferedimage, animationmetadatasection, anisotropicFiltering > 1.0F);
/* 79:   */     }
/* 80:   */     catch (RuntimeException runtimeexception)
/* 81:   */     {
/* 82:81 */       FMLClientHandler.instance().trackBrokenTexture(location, runtimeexception.getMessage());
/* 83:82 */       return true;
/* 84:   */     }
/* 85:   */     catch (IOException ioexception1)
/* 86:   */     {
/* 87:85 */       FMLClientHandler.instance().trackMissingTexture(location);
/* 88:86 */       return true;
/* 89:   */     }
/* 90:89 */     return false;
/* 91:   */   }
/* 92:   */   
/* 93:   */   private ResourceLocation completeResourceLocation(ResourceLocation p_147634_1_)
/* 94:   */   {
/* 95:93 */     return new ResourceLocation(p_147634_1_.getResourceDomain(), String.format("%s/%s%s", new Object[] { this.basePath, p_147634_1_.getResourcePath(), ".png" }));
/* 96:   */   }
/* 97:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.texture.TextureDerived
 * JD-Core Version:    0.7.0.1
 */