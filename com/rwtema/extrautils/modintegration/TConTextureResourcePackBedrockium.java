/*  1:   */ package com.rwtema.extrautils.modintegration;
/*  2:   */ 
/*  3:   */ import com.google.common.base.Throwables;
/*  4:   */ import cpw.mods.fml.common.ObfuscationReflectionHelper;
/*  5:   */ import java.awt.image.BufferedImage;
/*  6:   */ import java.awt.image.DirectColorModel;
/*  7:   */ import java.io.IOException;
/*  8:   */ import java.io.InputStream;
/*  9:   */ import java.util.List;
/* 10:   */ import javax.imageio.ImageIO;
/* 11:   */ import net.minecraft.client.Minecraft;
/* 12:   */ import net.minecraft.client.resources.DefaultResourcePack;
/* 13:   */ import net.minecraft.client.resources.IResourceManager;
/* 14:   */ import net.minecraft.client.resources.IResourcePack;
/* 15:   */ import net.minecraft.client.resources.ResourcePackRepository;
/* 16:   */ import net.minecraft.client.resources.ResourcePackRepository.Entry;
/* 17:   */ import net.minecraft.util.ResourceLocation;
/* 18:   */ 
/* 19:   */ public class TConTextureResourcePackBedrockium
/* 20:   */   extends TConTextureResourcePackBase
/* 21:   */ {
/* 22:   */   public TConTextureResourcePackBedrockium(String name)
/* 23:   */   {
/* 24:20 */     super(name);
/* 25:   */   }
/* 26:   */   
/* 27:23 */   static BufferedImage bedrockImage = null;
/* 28:   */   
/* 29:   */   public BufferedImage modifyImage(BufferedImage image)
/* 30:   */   {
/* 31:28 */     BufferedImage bedrockImage = getBedrockImage();
/* 32:30 */     for (int x = 0; x < image.getWidth(); x++) {
/* 33:31 */       for (int y = 0; y < image.getHeight(); y++)
/* 34:   */       {
/* 35:32 */         int c = image.getRGB(x, y);
/* 36:34 */         if ((c == 0) || (rgb.getAlpha(c) < 16))
/* 37:   */         {
/* 38:35 */           image.setRGB(x, y, 0);
/* 39:   */         }
/* 40:   */         else
/* 41:   */         {
/* 42:39 */           float b = brightness(c) / 255.0F;
/* 43:   */           
/* 44:41 */           int dx = x * bedrockImage.getWidth() / image.getWidth();
/* 45:42 */           int dy = y * bedrockImage.getHeight() / image.getHeight();
/* 46:43 */           int col = bedrockImage.getRGB(dx, dy);
/* 47:   */           
/* 48:45 */           image.setRGB(x, y, rgb.getAlpha(c) << 24 | (int)(rgb.getRed(col) * b) << 16 | (int)(rgb.getGreen(col) * b) << 8 | (int)(rgb.getBlue(col) * b));
/* 49:   */         }
/* 50:   */       }
/* 51:   */     }
/* 52:55 */     return image;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public static BufferedImage getBedrockImage()
/* 56:   */   {
/* 57:59 */     if (bedrockImage == null)
/* 58:   */     {
/* 59:60 */       ResourceLocation bedrockLocation = new ResourceLocation("minecraft", "textures/blocks/bedrock.png");
/* 60:   */       try
/* 61:   */       {
/* 62:62 */         DefaultResourcePack mcDefaultResourcePack = (DefaultResourcePack)ObfuscationReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), new String[] { "mcDefaultResourcePack", "mcDefaultResourcePack" });
/* 63:63 */         InputStream inputStream = mcDefaultResourcePack.getInputStream(bedrockLocation);
/* 64:   */         
/* 65:65 */         List<ResourcePackRepository.Entry> t = Minecraft.getMinecraft().getResourcePackRepository().getRepositoryEntries();
/* 66:66 */         for (ResourcePackRepository.Entry entry : t)
/* 67:   */         {
/* 68:67 */           IResourcePack resourcePack = entry.getResourcePack();
/* 69:68 */           if (resourcePack.resourceExists(bedrockLocation)) {
/* 70:69 */             inputStream = resourcePack.getInputStream(bedrockLocation);
/* 71:   */           }
/* 72:   */         }
/* 73:73 */         bedrockImage = ImageIO.read(inputStream);
/* 74:   */       }
/* 75:   */       catch (IOException e)
/* 76:   */       {
/* 77:75 */         throw Throwables.propagate(e);
/* 78:   */       }
/* 79:   */     }
/* 80:78 */     return bedrockImage;
/* 81:   */   }
/* 82:   */   
/* 83:   */   public void onResourceManagerReload(IResourceManager p_110549_1_)
/* 84:   */   {
/* 85:83 */     super.onResourceManagerReload(p_110549_1_);
/* 86:84 */     bedrockImage = null;
/* 87:   */   }
/* 88:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.modintegration.TConTextureResourcePackBedrockium
 * JD-Core Version:    0.7.0.1
 */