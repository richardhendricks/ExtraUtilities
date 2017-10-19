/*  1:   */ package com.rwtema.extrautils.command;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.LogHelper;
/*  4:   */ import java.awt.image.BufferedImage;
/*  5:   */ import java.io.File;
/*  6:   */ import java.io.IOException;
/*  7:   */ import java.nio.ByteBuffer;
/*  8:   */ import java.nio.ByteOrder;
/*  9:   */ import java.nio.IntBuffer;
/* 10:   */ import javax.imageio.ImageIO;
/* 11:   */ import javax.imageio.ImageTypeSpecifier;
/* 12:   */ import net.minecraft.client.Minecraft;
/* 13:   */ import net.minecraft.client.renderer.texture.ITextureObject;
/* 14:   */ import net.minecraft.client.renderer.texture.TextureManager;
/* 15:   */ import net.minecraft.client.renderer.texture.TextureMap;
/* 16:   */ import net.minecraft.command.CommandBase;
/* 17:   */ import net.minecraft.command.ICommandSender;
/* 18:   */ import net.minecraft.util.ResourceLocation;
/* 19:   */ import org.lwjgl.opengl.GL11;
/* 20:   */ 
/* 21:   */ public class CommandDumpTextureSheet
/* 22:   */   extends CommandBase
/* 23:   */ {
/* 24:   */   public String getCommandName()
/* 25:   */   {
/* 26:25 */     return "dumpTextureAtlas";
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String getCommandUsage(ICommandSender p_71518_1_)
/* 30:   */   {
/* 31:30 */     return "dumpTextureAtlas";
/* 32:   */   }
/* 33:   */   
/* 34:   */   public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_)
/* 35:   */   {
/* 36:35 */     return true;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
/* 40:   */   {
/* 41:40 */     outputTexture(TextureMap.locationBlocksTexture, "blocks");
/* 42:41 */     outputTexture(TextureMap.locationItemsTexture, "items");
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void outputTexture(ResourceLocation locationTexture, String s)
/* 46:   */   {
/* 47:45 */     int terrainTextureId = Minecraft.getMinecraft().renderEngine.getTexture(locationTexture).getGlTextureId();
/* 48:47 */     if (terrainTextureId == 0) {
/* 49:47 */       return;
/* 50:   */     }
/* 51:49 */     int w = GL11.glGetTexLevelParameteri(3553, 0, 4096);
/* 52:50 */     int h = GL11.glGetTexLevelParameteri(3553, 0, 4097);
/* 53:51 */     int[] pixels = new int[w * h];
/* 54:   */     
/* 55:53 */     IntBuffer pixelBuf = ByteBuffer.allocateDirect(w * h * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
/* 56:54 */     GL11.glGetTexImage(3553, 0, 32993, 5121, pixelBuf);
/* 57:55 */     pixelBuf.limit(w * h);
/* 58:56 */     pixelBuf.get(pixels);
/* 59:   */     
/* 60:58 */     BufferedImage image = ImageTypeSpecifier.createFromBufferedImageType(2).createBufferedImage(w, h);
/* 61:59 */     image.setRGB(0, 0, w, h, pixels, 0, w);
/* 62:   */     
/* 63:61 */     File f = new File(new File(Minecraft.getMinecraft().mcDataDir, "xutexture"), s + ".png");
/* 64:   */     try
/* 65:   */     {
/* 66:64 */       if ((!f.getParentFile().exists()) && (!f.getParentFile().mkdirs())) {
/* 67:65 */         return;
/* 68:   */       }
/* 69:67 */       if ((!f.exists()) && (!f.createNewFile())) {
/* 70:68 */         return;
/* 71:   */       }
/* 72:70 */       ImageIO.write(image, "png", f);
/* 73:   */     }
/* 74:   */     catch (IOException e)
/* 75:   */     {
/* 76:72 */       LogHelper.info("Unable to output " + s, new Object[0]);
/* 77:73 */       e.printStackTrace();
/* 78:   */     }
/* 79:   */   }
/* 80:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.command.CommandDumpTextureSheet
 * JD-Core Version:    0.7.0.1
 */