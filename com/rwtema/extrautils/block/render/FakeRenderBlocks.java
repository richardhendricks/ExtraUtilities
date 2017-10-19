/*   1:    */ package com.rwtema.extrautils.block.render;
/*   2:    */ 
/*   3:    */ import cofh.api.block.IBlockAppearance;
/*   4:    */ import com.rwtema.extrautils.block.IconConnectedTexture;
/*   5:    */ import com.rwtema.extrautils.block.IconConnectedTextureFlipped;
/*   6:    */ import cpw.mods.fml.relauncher.Side;
/*   7:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   8:    */ import net.minecraft.block.Block;
/*   9:    */ import net.minecraft.client.renderer.RenderBlocks;
/*  10:    */ import net.minecraft.client.renderer.Tessellator;
/*  11:    */ import net.minecraft.util.IIcon;
/*  12:    */ import net.minecraft.world.IBlockAccess;
/*  13:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  14:    */ 
/*  15:    */ @SideOnly(Side.CLIENT)
/*  16:    */ public class FakeRenderBlocks
/*  17:    */   extends RenderBlocks
/*  18:    */ {
/*  19: 18 */   public static final double[] u = { -1.0D, 1.0D, 1.0D, -1.0D };
/*  20: 19 */   public static final double[] v = { 1.0D, 1.0D, -1.0D, -1.0D };
/*  21: 20 */   public Block curBlock = null;
/*  22: 21 */   public int curMeta = 0;
/*  23: 22 */   public boolean isOpaque = false;
/*  24:    */   
/*  25:    */   public void setWorld(IBlockAccess blockAccess)
/*  26:    */   {
/*  27: 25 */     this.blockAccess = blockAccess;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void setLightAndColor(double u2, double v2, int side)
/*  31:    */   {
/*  32: 29 */     if (this.enableAO)
/*  33:    */     {
/*  34: 30 */       Tessellator t = Tessellator.instance;
/*  35: 31 */       double u = 0.0D;double v = 0.0D;
/*  36: 33 */       if ((side == 0) || (side == 1))
/*  37:    */       {
/*  38: 34 */         u = 1.0D - u2;
/*  39: 35 */         v = 1.0D - v2;
/*  40:    */       }
/*  41: 36 */       else if (side == 2)
/*  42:    */       {
/*  43: 37 */         u = v2;
/*  44: 38 */         v = 1.0D - u2;
/*  45:    */       }
/*  46: 39 */       else if (side == 3)
/*  47:    */       {
/*  48: 40 */         u = u2;
/*  49: 41 */         v = v2;
/*  50:    */       }
/*  51: 42 */       else if (side == 4)
/*  52:    */       {
/*  53: 43 */         u = v2;
/*  54: 44 */         v = 1.0D - u2;
/*  55:    */       }
/*  56: 45 */       else if (side == 5)
/*  57:    */       {
/*  58: 46 */         u = 1.0D - v2;
/*  59: 47 */         v = u2;
/*  60:    */       }
/*  61: 50 */       t.setBrightness(mixAoBrightness(this.brightnessTopLeft, this.brightnessTopRight, this.brightnessBottomLeft, this.brightnessBottomRight, u * v, v * (1.0D - u), (1.0D - v) * u, (1.0D - u) * (1.0D - v)));
/*  62: 51 */       t.setColorOpaque_F(mix(this.colorRedTopLeft, this.colorRedTopRight, this.colorRedBottomLeft, this.colorRedBottomRight, u, v), mix(this.colorGreenTopLeft, this.colorGreenTopRight, this.colorGreenBottomLeft, this.colorGreenBottomRight, u, v), mix(this.colorBlueTopLeft, this.colorBlueTopRight, this.colorBlueBottomLeft, this.colorBlueBottomRight, u, v));
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public float mix(double tl, double tr, double bl, double br, double u, double v)
/*  67:    */   {
/*  68: 58 */     return (float)(tl * u * v + tr * (1.0D - u) * v + bl * u * (1.0D - v) + br * (1.0D - u) * (1.0D - v));
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void renderSide(Block block, double x, double y, double z, double ox, double oy, double oz, int ax, int ay, int az, int bx, int by, int bz, IconConnectedTexture icon, int side, double rx, double ry, double rz)
/*  72:    */   {
/*  73: 62 */     Tessellator t = Tessellator.instance;
/*  74: 63 */     byte[] i = new byte[4];
/*  75: 64 */     this.isOpaque = block.isOpaqueCube();
/*  76:    */     
/*  77: 66 */     boolean areSame = true;
/*  78: 68 */     for (int j = 0; j < 4; j++)
/*  79:    */     {
/*  80: 69 */       i[j] = getType(block, side, (int)x, (int)y, (int)z, ax * (int)u[j], ay * (int)u[j], az * (int)u[j], bx * (int)v[j], by * (int)v[j], bz * (int)v[j], (int)(ox * 2.0D - 1.0D), (int)(oy * 2.0D - 1.0D), (int)(oz * 2.0D - 1.0D));
/*  81: 71 */       if ((areSame) && (j > 0) && (i[j] != i[0])) {
/*  82: 72 */         areSame = false;
/*  83:    */       }
/*  84:    */     }
/*  85: 75 */     if (areSame)
/*  86:    */     {
/*  87: 76 */       icon.setType(i[0]);
/*  88: 78 */       for (int j = 0; j < 4; j++)
/*  89:    */       {
/*  90: 79 */         double cx = x + rx + ox + u[j] * ax * 0.5D + v[j] * bx * 0.5D;
/*  91: 80 */         double cy = y + ry + oy + u[j] * ay * 0.5D + v[j] * by * 0.5D;
/*  92: 81 */         double cz = z + rz + oz + u[j] * az * 0.5D + v[j] * bz * 0.5D;
/*  93:    */         
/*  94: 83 */         setLightAndColor(0.5D + u[j] * 0.5D, 0.5D + v[j] * 0.5D, side);
/*  95: 84 */         t.addVertexWithUV(cx, cy, cz, icon.getInterpolatedU(16.0D - (8.0D + u[j] * 8.0D)), icon.getInterpolatedV(16.0D - (8.0D + v[j] * 8.0D)));
/*  96:    */       }
/*  97: 92 */       icon.resetType();
/*  98: 93 */       return;
/*  99:    */     }
/* 100: 97 */     for (int j = 0; j < 4; j++)
/* 101:    */     {
/* 102: 98 */       icon.setType(i[j]);
/* 103: 99 */       double cx = x + rx + ox + ax * u[j] / 4.0D + bx * v[j] / 4.0D;
/* 104:100 */       double cy = y + ry + oy + ay * u[j] / 4.0D + by * v[j] / 4.0D;
/* 105:101 */       double cz = z + rz + oz + az * u[j] / 4.0D + bz * v[j] / 4.0D;
/* 106:103 */       for (int k = 0; k < 4; k++)
/* 107:    */       {
/* 108:104 */         setLightAndColor(0.5D + u[j] * 0.25D + u[k] * 0.25D, 0.5D + v[j] * 0.25D + v[k] * 0.25D, side);
/* 109:105 */         t.addVertexWithUV(cx + u[k] * ax * 0.25D + v[k] * bx * 0.25D, cy + u[k] * ay * 0.25D + v[k] * by * 0.25D, cz + u[k] * az * 0.25D + v[k] * bz * 0.25D, icon.getInterpolatedU(16.0D - (8.0D + u[j] * 4.0D + u[k] * 4.0D)), icon.getInterpolatedV(16.0D - (8.0D + v[j] * 4.0D + v[k] * 4.0D)));
/* 110:    */       }
/* 111:113 */       icon.resetType();
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public int getSideFromDir(int dx, int dy, int dz)
/* 116:    */   {
/* 117:118 */     if (dy < 0) {
/* 118:119 */       return 0;
/* 119:    */     }
/* 120:120 */     if (dy > 0) {
/* 121:121 */       return 1;
/* 122:    */     }
/* 123:122 */     if (dz < 0) {
/* 124:123 */       return 2;
/* 125:    */     }
/* 126:124 */     if (dz > 0) {
/* 127:125 */       return 3;
/* 128:    */     }
/* 129:126 */     if (dx < 0) {
/* 130:127 */       return 4;
/* 131:    */     }
/* 132:128 */     if (dx > 0) {
/* 133:129 */       return 5;
/* 134:    */     }
/* 135:132 */     return 0;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public boolean matchBlock(int side2, int x2, int y2, int z2)
/* 139:    */   {
/* 140:136 */     Block block = this.blockAccess.getBlock(x2, y2, z2);
/* 141:137 */     if (block == this.curBlock) {
/* 142:138 */       return this.curMeta == this.blockAccess.getBlockMetadata(x2, y2, z2);
/* 143:    */     }
/* 144:139 */     if ((block instanceof IBlockAppearance))
/* 145:    */     {
/* 146:140 */       IBlockAppearance block1 = (IBlockAppearance)block;
/* 147:141 */       return (block1.supportsVisualConnections()) && (this.curBlock == block1.getVisualBlock(this.blockAccess, x2, y2, z2, ForgeDirection.getOrientation(side2))) && (this.curMeta == block1.getVisualMeta(this.blockAccess, x2, y2, z2, ForgeDirection.getOrientation(side2)));
/* 148:    */     }
/* 149:143 */     return false;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public byte getType(Block block, int side, int x, int y, int z, int ax, int ay, int az, int bx, int by, int bz, int cx, int cy, int cz)
/* 153:    */   {
/* 154:147 */     int sidea = getSideFromDir(ax, ay, az);
/* 155:148 */     int sideb = getSideFromDir(bx, by, bz);
/* 156:149 */     boolean a = (matchBlock(side, x + ax, y + ay, z + az)) && (!matchBlock(sidea, x + cx, y + cy, z + cz)) && (!matchBlock(net.minecraft.util.Facing.oppositeSide[sidea], x + ax + cx, y + ay + cy, z + az + cz));
/* 157:150 */     boolean b = (matchBlock(side, x + bx, y + by, z + bz)) && (!matchBlock(sideb, x + cx, y + cy, z + cz)) && (!matchBlock(net.minecraft.util.Facing.oppositeSide[sideb], x + bx + cx, y + by + cy, z + bz + cz));
/* 158:152 */     if (a)
/* 159:    */     {
/* 160:153 */       if (b)
/* 161:    */       {
/* 162:154 */         if (matchBlock(side, x + ax + bx, y + ay + by, z + az + bz))
/* 163:    */         {
/* 164:155 */           if ((matchBlock(net.minecraft.util.Facing.oppositeSide[sidea], x + ax + bx + cx, y + ay + by + cy, z + az + bz + cz)) || (matchBlock(net.minecraft.util.Facing.oppositeSide[sideb], x + ax + bx + cx, y + ay + by + cy, z + az + bz + cz)) || (matchBlock(sidea, x + bx + cx, y + by + cy, z + bz + cz)) || (matchBlock(sideb, x + ax + cx, y + ay + cy, z + az + cz))) {
/* 165:158 */             return 4;
/* 166:    */           }
/* 167:160 */           return 3;
/* 168:    */         }
/* 169:163 */         return 4;
/* 170:    */       }
/* 171:166 */       return 2;
/* 172:    */     }
/* 173:169 */     if (b) {
/* 174:170 */       return 1;
/* 175:    */     }
/* 176:172 */     return 0;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void renderFaceYNeg(Block block, double x, double y, double z, IIcon IIcon)
/* 180:    */   {
/* 181:179 */     if (hasOverrideBlockTexture()) {
/* 182:180 */       IIcon = this.overrideBlockTexture;
/* 183:    */     }
/* 184:183 */     if ((IIcon instanceof IconConnectedTexture)) {
/* 185:184 */       renderSide(block, x, y, z, 0.5D, 0.0D, 0.5D, -1, 0, 0, 0, 0, 1, new IconConnectedTextureFlipped((IconConnectedTexture)IIcon), 0, 0.0D, 0.0D, 0.0D);
/* 186:    */     } else {
/* 187:187 */       super.renderFaceYNeg(block, x, y, z, IIcon);
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void renderFaceYPos(Block block, double x, double y, double z, IIcon IIcon)
/* 192:    */   {
/* 193:193 */     if (hasOverrideBlockTexture()) {
/* 194:194 */       IIcon = this.overrideBlockTexture;
/* 195:    */     }
/* 196:197 */     if ((IIcon instanceof IconConnectedTexture)) {
/* 197:198 */       renderSide(block, x, y, z, 0.5D, 1.0D, 0.5D, -1, 0, 0, 0, 0, -1, (IconConnectedTexture)IIcon, 1, 0.0D, 0.0D, 0.0D);
/* 198:    */     } else {
/* 199:200 */       super.renderFaceYPos(block, x, y, z, IIcon);
/* 200:    */     }
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void renderFaceZNeg(Block block, double x, double y, double z, IIcon IIcon)
/* 204:    */   {
/* 205:206 */     if (hasOverrideBlockTexture()) {
/* 206:207 */       IIcon = this.overrideBlockTexture;
/* 207:    */     }
/* 208:210 */     if ((IIcon instanceof IconConnectedTexture)) {
/* 209:211 */       renderSide(block, x, y, z, 0.5D, 0.5D, 0.0D, 1, 0, 0, 0, 1, 0, (IconConnectedTexture)IIcon, 2, 0.0D, 0.0D, 0.0D);
/* 210:    */     } else {
/* 211:213 */       super.renderFaceZNeg(block, x, y, z, IIcon);
/* 212:    */     }
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void renderFaceZPos(Block block, double x, double y, double z, IIcon IIcon)
/* 216:    */   {
/* 217:219 */     if (hasOverrideBlockTexture()) {
/* 218:220 */       IIcon = this.overrideBlockTexture;
/* 219:    */     }
/* 220:223 */     if ((IIcon instanceof IconConnectedTexture)) {
/* 221:224 */       renderSide(block, x, y, z, 0.5D, 0.5D, 1.0D, -1, 0, 0, 0, 1, 0, (IconConnectedTexture)IIcon, 3, 0.0D, 0.0D, 0.0D);
/* 222:    */     } else {
/* 223:226 */       super.renderFaceZPos(block, x, y, z, IIcon);
/* 224:    */     }
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void renderFaceXNeg(Block block, double x, double y, double z, IIcon IIcon)
/* 228:    */   {
/* 229:232 */     if (hasOverrideBlockTexture()) {
/* 230:233 */       IIcon = this.overrideBlockTexture;
/* 231:    */     }
/* 232:236 */     if ((IIcon instanceof IconConnectedTexture)) {
/* 233:237 */       renderSide(block, x, y, z, 0.0D, 0.5D, 0.5D, 0, 0, -1, 0, 1, 0, (IconConnectedTexture)IIcon, 4, 0.0D, 0.0D, 0.0D);
/* 234:    */     } else {
/* 235:239 */       super.renderFaceXNeg(block, x, y, z, IIcon);
/* 236:    */     }
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void renderFaceXPos(Block block, double x, double y, double z, IIcon IIcon)
/* 240:    */   {
/* 241:245 */     if (hasOverrideBlockTexture()) {
/* 242:246 */       IIcon = this.overrideBlockTexture;
/* 243:    */     }
/* 244:249 */     if ((IIcon instanceof IconConnectedTexture)) {
/* 245:250 */       renderSide(block, x, y, z, 1.0D, 0.5D, 0.5D, 0, 0, 1, 0, 1, 0, (IconConnectedTexture)IIcon, 5, 0.0D, 0.0D, 0.0D);
/* 246:    */     } else {
/* 247:252 */       super.renderFaceXPos(block, x, y, z, IIcon);
/* 248:    */     }
/* 249:    */   }
/* 250:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.render.FakeRenderBlocks
 * JD-Core Version:    0.7.0.1
 */