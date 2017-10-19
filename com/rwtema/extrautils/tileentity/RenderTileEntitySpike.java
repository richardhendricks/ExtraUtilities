/*   1:    */ package com.rwtema.extrautils.tileentity;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.helper.GLHelper;
/*   4:    */ import cpw.mods.fml.relauncher.Side;
/*   5:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   6:    */ import net.minecraft.client.Minecraft;
/*   7:    */ import net.minecraft.client.renderer.OpenGlHelper;
/*   8:    */ import net.minecraft.client.renderer.Tessellator;
/*   9:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  10:    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*  11:    */ import net.minecraft.tileentity.TileEntity;
/*  12:    */ import net.minecraft.util.ResourceLocation;
/*  13:    */ import org.lwjgl.opengl.GL11;
/*  14:    */ 
/*  15:    */ @SideOnly(Side.CLIENT)
/*  16:    */ public class RenderTileEntitySpike
/*  17:    */   extends TileEntitySpecialRenderer
/*  18:    */ {
/*  19: 18 */   private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
/*  20: 19 */   public static int hashCode = 0;
/*  21:    */   
/*  22:    */   public static void render(double[] v1, double[] v2, double[] v3, double[] v4, boolean isPoint)
/*  23:    */   {
/*  24: 22 */     Tessellator tes = Tessellator.instance;
/*  25: 23 */     float f8 = 0.125F;
/*  26: 24 */     GL11.glPushMatrix();
/*  27:    */     
/*  28: 26 */     GL11.glScalef(f8, f8, f8);
/*  29: 27 */     float f9 = (float)((Minecraft.getSystemTime() + hashCode) % 3000L) / 3000.0F * 8.0F;
/*  30: 28 */     GL11.glTranslatef(f9, 0.0F, 0.0F);
/*  31: 29 */     GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
/*  32:    */     
/*  33: 31 */     renderFace(v1, v2, v3, v4, tes, isPoint);
/*  34:    */     
/*  35:    */ 
/*  36: 34 */     GL11.glPopMatrix();
/*  37:    */     
/*  38: 36 */     GL11.glPushMatrix();
/*  39:    */     
/*  40: 38 */     GL11.glScalef(f8, f8, f8);
/*  41: 39 */     float f9 = (float)((Minecraft.getSystemTime() + hashCode) % 4873L) / 4873.0F * 8.0F;
/*  42: 40 */     GL11.glTranslatef(-f9, 0.0F, 0.0F);
/*  43: 41 */     GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
/*  44:    */     
/*  45: 43 */     renderFace(v1, v2, v3, v4, tes, isPoint);
/*  46:    */     
/*  47: 45 */     GL11.glPopMatrix();
/*  48:    */   }
/*  49:    */   
/*  50:    */   private static void renderFace(double[] v1, double[] v2, double[] v3, double[] v4, Tessellator tes, boolean isPoint)
/*  51:    */   {
/*  52: 49 */     tes.startDrawingQuads();
/*  53: 50 */     tes.setBrightness(255);
/*  54: 51 */     if (isPoint)
/*  55:    */     {
/*  56: 52 */       tes.addVertexWithUV(v1[0], v1[1], v1[2], 0.0D, 0.5D);
/*  57: 53 */       tes.addVertexWithUV(v2[0], v2[1], v2[2], 0.0D, 0.5D);
/*  58:    */     }
/*  59:    */     else
/*  60:    */     {
/*  61: 55 */       tes.addVertexWithUV(v1[0], v1[1], v1[2], 0.0D, 0.0D);
/*  62: 56 */       tes.addVertexWithUV(v2[0], v2[1], v2[2], 0.0D, 1.0D);
/*  63:    */     }
/*  64: 58 */     tes.addVertexWithUV(v3[0], v3[1], v3[2], 1.0D, 1.0D);
/*  65: 59 */     tes.addVertexWithUV(v4[0], v4[1], v4[2], 1.0D, 0.0D);
/*  66: 60 */     tes.draw();
/*  67:    */   }
/*  68:    */   
/*  69: 63 */   static double[][] pointCoords = { { 0.5D, 1.0D, 0.5D }, { 0.5D, 0.0D, 0.5D }, { 0.5D, 0.5D, 1.0D }, { 0.5D, 0.5D, 0.0D }, { 1.0D, 0.5D, 0.5D }, { 0.0D, 0.5D, 0.5D } };
/*  70: 64 */   static double[][] base1Coords = { { 0.0D, 0.0D, 0.0D }, { 0.0D, 1.0D, 0.0D }, { 0.0D, 0.0D, 0.0D }, { 0.0D, 0.0D, 1.0D }, { 0.0D, 0.0D, 0.0D }, { 1.0D, 0.0D, 0.0D } };
/*  71: 65 */   static double[][] base2Coords = { { 0.0D, 0.0D, 1.0D }, { 0.0D, 1.0D, 1.0D }, { 0.0D, 1.0D, 0.0D }, { 0.0D, 1.0D, 1.0D }, { 0.0D, 0.0D, 1.0D }, { 1.0D, 0.0D, 1.0D } };
/*  72: 66 */   static double[][] base3Coords = { { 1.0D, 0.0D, 1.0D }, { 1.0D, 1.0D, 1.0D }, { 1.0D, 1.0D, 0.0D }, { 1.0D, 1.0D, 1.0D }, { 0.0D, 1.0D, 1.0D }, { 1.0D, 1.0D, 1.0D } };
/*  73: 67 */   static double[][] base4Coords = { { 1.0D, 0.0D, 0.0D }, { 1.0D, 1.0D, 0.0D }, { 1.0D, 0.0D, 0.0D }, { 1.0D, 0.0D, 1.0D }, { 0.0D, 1.0D, 0.0D }, { 1.0D, 1.0D, 0.0D } };
/*  74:    */   
/*  75:    */   public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f)
/*  76:    */   {
/*  77: 72 */     hashCode = 0;
/*  78:    */     
/*  79: 74 */     int side = net.minecraft.util.Facing.oppositeSide[(tileentity.getBlockMetadata() % 6)];
/*  80:    */     
/*  81: 76 */     GL11.glPushMatrix();
/*  82:    */     
/*  83: 78 */     GL11.glTranslated(x, y, z);
/*  84:    */     
/*  85:    */ 
/*  86: 81 */     GL11.glTranslated(0.5D, 0.5D, 0.5D);
/*  87: 82 */     GL11.glScaled(1.01D, 1.01D, 1.01D);
/*  88: 83 */     GL11.glTranslated(-0.5D, -0.5D, -0.5D);
/*  89:    */     
/*  90: 85 */     drawEnchantedSpike(side);
/*  91:    */     
/*  92: 87 */     GL11.glPopMatrix();
/*  93:    */     
/*  94:    */ 
/*  95: 90 */     hashCode = 0;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public static void drawEnchantedSpike(int side)
/*  99:    */   {
/* 100: 94 */     TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
/* 101: 95 */     GLHelper.pushGLState();
/* 102:    */     
/* 103:    */ 
/* 104: 98 */     GL11.glDepthMask(true);
/* 105: 99 */     GLHelper.disableGLState(2884);
/* 106:100 */     GL11.glDepthFunc(515);
/* 107:101 */     GLHelper.disableGLState(2896);
/* 108:102 */     texturemanager.bindTexture(RES_ITEM_GLINT);
/* 109:103 */     GLHelper.enableGLState(3042);
/* 110:104 */     GL11.glBlendFunc(774, 774);
/* 111:105 */     OpenGlHelper.glBlendFunc(768, 1, 1, 0);
/* 112:    */     
/* 113:107 */     float f7 = 0.76F;
/* 114:108 */     GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
/* 115:    */     
/* 116:110 */     GL11.glMatrixMode(5890);
/* 117:111 */     GL11.glPushMatrix();
/* 118:    */     
/* 119:113 */     render(base1Coords[side], base2Coords[side], base3Coords[side], base4Coords[side], false);
/* 120:114 */     hashCode += 123;
/* 121:115 */     render(pointCoords[side], pointCoords[side], base2Coords[side], base1Coords[side], true);
/* 122:116 */     hashCode += 123;
/* 123:117 */     render(pointCoords[side], pointCoords[side], base1Coords[side], base4Coords[side], true);
/* 124:118 */     hashCode += 123;
/* 125:119 */     render(pointCoords[side], pointCoords[side], base2Coords[side], base3Coords[side], true);
/* 126:120 */     hashCode += 123;
/* 127:121 */     render(pointCoords[side], pointCoords[side], base3Coords[side], base4Coords[side], true);
/* 128:    */     
/* 129:    */ 
/* 130:124 */     hashCode = 0;
/* 131:125 */     GL11.glPopMatrix();
/* 132:    */     
/* 133:127 */     GLHelper.popGLState();
/* 134:    */     
/* 135:129 */     GL11.glMatrixMode(5888);
/* 136:130 */     GL11.glDepthFunc(515);
/* 137:131 */     GL11.glDepthMask(true);
/* 138:132 */     GL11.glDepthFunc(515);
/* 139:    */   }
/* 140:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.RenderTileEntitySpike
 * JD-Core Version:    0.7.0.1
 */