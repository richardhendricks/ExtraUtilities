/*   1:    */ package com.rwtema.extrautils.block.render;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.block.IconConnectedTexture;
/*   4:    */ import cpw.mods.fml.relauncher.Side;
/*   5:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   6:    */ import net.minecraft.block.Block;
/*   7:    */ import net.minecraft.client.renderer.EntityRenderer;
/*   8:    */ import net.minecraft.client.renderer.Tessellator;
/*   9:    */ import net.minecraft.util.IIcon;
/*  10:    */ 
/*  11:    */ @SideOnly(Side.CLIENT)
/*  12:    */ public class FakeRenderEtherealBlocks
/*  13:    */   extends FakeRenderBlocks
/*  14:    */ {
/*  15:    */   private static final double h = 0.005D;
/*  16:    */   private static final float darken = 0.75F;
/*  17:    */   
/*  18:    */   public void setLightAndColor(double u2, double v2, int side) {}
/*  19:    */   
/*  20:    */   public boolean renderStandardBlock(Block p_147784_1_, int p_147784_2_, int p_147784_3_, int p_147784_4_)
/*  21:    */   {
/*  22: 24 */     int l = p_147784_1_.colorMultiplier(this.blockAccess, p_147784_2_, p_147784_3_, p_147784_4_);
/*  23: 25 */     float f = (l >> 16 & 0xFF) / 255.0F;
/*  24: 26 */     float f1 = (l >> 8 & 0xFF) / 255.0F;
/*  25: 27 */     float f2 = (l & 0xFF) / 255.0F;
/*  26: 29 */     if (EntityRenderer.anaglyphEnable)
/*  27:    */     {
/*  28: 30 */       float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
/*  29: 31 */       float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
/*  30: 32 */       float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
/*  31: 33 */       f = f3;
/*  32: 34 */       f1 = f4;
/*  33: 35 */       f2 = f5;
/*  34:    */     }
/*  35: 38 */     f *= 0.75F;
/*  36: 39 */     f1 *= 0.75F;
/*  37: 40 */     f2 *= 0.75F;
/*  38:    */     
/*  39: 42 */     return renderStandardBlockWithColorMultiplier(p_147784_1_, p_147784_2_, p_147784_3_, p_147784_4_, f, f1, f2);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void renderSide(Block block, double x, double y, double z, double ox, double oy, double oz, int ax, int ay, int az, int bx, int by, int bz, IconConnectedTexture icon, int side, double rx, double ry, double rz)
/*  43:    */   {
/*  44: 47 */     Tessellator t = Tessellator.instance;
/*  45: 48 */     byte[] i = new byte[4];
/*  46: 49 */     this.isOpaque = block.isOpaqueCube();
/*  47:    */     
/*  48: 51 */     boolean areSame = true;
/*  49: 53 */     for (int j = 0; j < 4; j++)
/*  50:    */     {
/*  51: 54 */       i[j] = getType(block, side, (int)x, (int)y, (int)z, ax * (int)u[j], ay * (int)u[j], az * (int)u[j], bx * (int)v[j], by * (int)v[j], bz * (int)v[j], (int)(ox * 2.0D - 1.0D), (int)(oy * 2.0D - 1.0D), (int)(oz * 2.0D - 1.0D));
/*  52: 56 */       if ((areSame) && (j > 0) && (i[j] != i[0])) {
/*  53: 57 */         areSame = false;
/*  54:    */       }
/*  55:    */     }
/*  56: 60 */     if (areSame)
/*  57:    */     {
/*  58: 61 */       icon.setType(i[0]);
/*  59: 62 */       double cx = x + rx + ox;
/*  60: 63 */       double cy = y + ry + oy;
/*  61: 64 */       double cz = z + rz + oz;
/*  62: 66 */       for (int k = 3; k >= 0; k--)
/*  63:    */       {
/*  64: 68 */         setLightAndColor(0.5D + u[k] * 0.5D, 0.5D + v[k] * 0.5D, side);
/*  65: 69 */         t.addVertexWithUV(cx + u[k] * ax * 0.5D + v[k] * bx * 0.5D, cy + u[k] * ay * 0.5D + v[k] * by * 0.5D, cz + u[k] * az * 0.5D + v[k] * bz * 0.5D, icon.getInterpolatedU(16.0D - (8.0D + u[k] * 8.0D)), icon.getInterpolatedV(16.0D - (8.0D + v[k] * 8.0D)));
/*  66:    */       }
/*  67: 77 */       icon.resetType();
/*  68: 78 */       return;
/*  69:    */     }
/*  70: 81 */     for (int j = 0; j < 4; j++)
/*  71:    */     {
/*  72: 82 */       icon.setType(i[j]);
/*  73: 83 */       double cx = x + rx + ox + ax * u[j] / 4.0D + bx * v[j] / 4.0D;
/*  74: 84 */       double cy = y + ry + oy + ay * u[j] / 4.0D + by * v[j] / 4.0D;
/*  75: 85 */       double cz = z + rz + oz + az * u[j] / 4.0D + bz * v[j] / 4.0D;
/*  76: 87 */       for (int k = 3; k >= 0; k--)
/*  77:    */       {
/*  78: 88 */         setLightAndColor(0.5D + u[j] * 0.25D + u[k] * 0.25D, 0.5D + v[j] * 0.25D + v[k] * 0.25D, side);
/*  79: 89 */         t.addVertexWithUV(cx + u[k] * ax * 0.25D + v[k] * bx * 0.25D, cy + u[k] * ay * 0.25D + v[k] * by * 0.25D, cz + u[k] * az * 0.25D + v[k] * bz * 0.25D, icon.getInterpolatedU(16.0D - (8.0D + u[j] * 4.0D + u[k] * 4.0D)), icon.getInterpolatedV(16.0D - (8.0D + v[j] * 4.0D + v[k] * 4.0D)));
/*  80:    */       }
/*  81: 97 */       icon.resetType();
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void renderFaceYNeg(Block block, double x, double y, double z, IIcon IIcon)
/*  86:    */   {
/*  87:104 */     if (hasOverrideBlockTexture()) {
/*  88:105 */       IIcon = this.overrideBlockTexture;
/*  89:    */     }
/*  90:108 */     if ((IIcon instanceof IconConnectedTexture)) {
/*  91:109 */       renderSide(block, x, y, z, 0.5D, 0.0D, 0.5D, 1, 0, 0, 0, 0, -1, (IconConnectedTexture)IIcon, 0, 0.0D, 0.005D, 0.0D);
/*  92:    */     } else {
/*  93:111 */       super.renderFaceYPos(block, x, y - 1.0D + 0.005D, z, IIcon);
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void renderFaceYPos(Block block, double x, double y, double z, IIcon IIcon)
/*  98:    */   {
/*  99:118 */     if (hasOverrideBlockTexture()) {
/* 100:119 */       IIcon = this.overrideBlockTexture;
/* 101:    */     }
/* 102:122 */     if ((IIcon instanceof IconConnectedTexture)) {
/* 103:123 */       renderSide(block, x, y, z, 0.5D, 1.0D, 0.5D, -1, 0, 0, 0, 0, -1, (IconConnectedTexture)IIcon, 1, 0.0D, -0.005D, 0.0D);
/* 104:    */     } else {
/* 105:125 */       super.renderFaceYNeg(block, x, y + 1.0D - 0.005D, z, IIcon);
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void renderFaceZNeg(Block block, double x, double y, double z, IIcon IIcon)
/* 110:    */   {
/* 111:131 */     if (hasOverrideBlockTexture()) {
/* 112:132 */       IIcon = this.overrideBlockTexture;
/* 113:    */     }
/* 114:135 */     if ((IIcon instanceof IconConnectedTexture)) {
/* 115:136 */       renderSide(block, x, y, z, 0.5D, 0.5D, 0.0D, 1, 0, 0, 0, 1, 0, (IconConnectedTexture)IIcon, 2, 0.0D, 0.0D, 0.005D);
/* 116:    */     } else {
/* 117:138 */       super.renderFaceZPos(block, x, y, z - 1.0D + 0.005D, IIcon);
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void renderFaceZPos(Block block, double x, double y, double z, IIcon IIcon)
/* 122:    */   {
/* 123:144 */     if (hasOverrideBlockTexture()) {
/* 124:145 */       IIcon = this.overrideBlockTexture;
/* 125:    */     }
/* 126:148 */     if ((IIcon instanceof IconConnectedTexture)) {
/* 127:149 */       renderSide(block, x, y, z, 0.5D, 0.5D, 1.0D, -1, 0, 0, 0, 1, 0, (IconConnectedTexture)IIcon, 3, 0.0D, 0.0D, -0.005D);
/* 128:    */     } else {
/* 129:151 */       super.renderFaceZNeg(block, x, y, z + 1.0D - 0.005D, IIcon);
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void renderFaceXNeg(Block block, double x, double y, double z, IIcon IIcon)
/* 134:    */   {
/* 135:157 */     if (hasOverrideBlockTexture()) {
/* 136:158 */       IIcon = this.overrideBlockTexture;
/* 137:    */     }
/* 138:161 */     if ((IIcon instanceof IconConnectedTexture)) {
/* 139:162 */       renderSide(block, x, y, z, 0.0D, 0.5D, 0.5D, 0, 0, -1, 0, 1, 0, (IconConnectedTexture)IIcon, 4, 0.005D, 0.0D, 0.0D);
/* 140:    */     } else {
/* 141:164 */       super.renderFaceXPos(block, x, y, z, IIcon);
/* 142:    */     }
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void renderFaceXPos(Block block, double x, double y, double z, IIcon IIcon)
/* 146:    */   {
/* 147:170 */     if (hasOverrideBlockTexture()) {
/* 148:171 */       IIcon = this.overrideBlockTexture;
/* 149:    */     }
/* 150:174 */     if ((IIcon instanceof IconConnectedTexture)) {
/* 151:175 */       renderSide(block, x, y, z, 1.0D, 0.5D, 0.5D, 0, 0, 1, 0, 1, 0, (IconConnectedTexture)IIcon, 5, -0.005D, 0.0D, 0.0D);
/* 152:    */     } else {
/* 153:177 */       super.renderFaceXNeg(block, x + 1.0D - 0.005D, y, z, IIcon);
/* 154:    */     }
/* 155:    */   }
/* 156:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.render.FakeRenderEtherealBlocks
 * JD-Core Version:    0.7.0.1
 */