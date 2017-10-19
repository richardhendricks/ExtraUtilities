/*   1:    */ package com.rwtema.extrautils.block.render;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*   4:    */ import com.rwtema.extrautils.item.ItemBlockDrum;
/*   5:    */ import com.rwtema.extrautils.texture.LiquidColorRegistry;
/*   6:    */ import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
/*   7:    */ import cpw.mods.fml.relauncher.Side;
/*   8:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   9:    */ import net.minecraft.block.Block;
/*  10:    */ import net.minecraft.client.renderer.RenderBlocks;
/*  11:    */ import net.minecraft.client.renderer.Tessellator;
/*  12:    */ import net.minecraft.item.ItemStack;
/*  13:    */ import net.minecraft.nbt.NBTTagCompound;
/*  14:    */ import net.minecraft.util.IIcon;
/*  15:    */ import net.minecraft.world.IBlockAccess;
/*  16:    */ import net.minecraftforge.fluids.FluidStack;
/*  17:    */ import org.lwjgl.opengl.GL11;
/*  18:    */ 
/*  19:    */ @SideOnly(Side.CLIENT)
/*  20:    */ public class RenderBlockDrum
/*  21:    */   implements ISimpleBlockRenderingHandler
/*  22:    */ {
/*  23:    */   public static final double numTex = 3.0D;
/*  24: 21 */   public static double w = 0.5D;
/*  25: 22 */   static float base_w = 0.425F;
/*  26:    */   
/*  27:    */   public static double du(double i)
/*  28:    */   {
/*  29: 25 */     return i % 3.0D / 3.0D;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static double du2(double i)
/*  33:    */   {
/*  34: 29 */     return (i % 3.0D + 1.0D) / 3.0D;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static double dx(double i)
/*  38:    */   {
/*  39: 33 */     return 0.5D + ddx(i) * w;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static double dz(double i)
/*  43:    */   {
/*  44: 37 */     return 0.5D + ddz(i) * w;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static float ddx(double i)
/*  48:    */   {
/*  49: 41 */     return (float)Math.cos(-(0.5D + i) / 8.0D * 2.0D * 3.141592653589793D);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static float ddz(double i)
/*  53:    */   {
/*  54: 45 */     return (float)Math.sin(-(0.5D + i) / 8.0D * 2.0D * 3.141592653589793D);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public static void drawInvBlock(Block block, ItemStack item)
/*  58:    */   {
/*  59: 49 */     float h = 0.97F;
/*  60: 50 */     float d = 0.2F;
/*  61: 51 */     float h2 = 0.3125F;
/*  62: 52 */     int l = 16777215;
/*  63: 53 */     int meta = item.getItemDamage();
/*  64: 55 */     if ((item.hasTagCompound()) && (item.stackTagCompound.hasKey("color"))) {
/*  65: 56 */       l = item.stackTagCompound.getInteger("color");
/*  66:    */     }
/*  67: 59 */     FluidStack fluid = ((ItemBlockDrum)item.getItem()).getFluid(item);
/*  68: 60 */     l = LiquidColorRegistry.getFluidColor(fluid);
/*  69: 61 */     float f = (l >> 16 & 0xFF) / 255.0F;
/*  70: 62 */     float f1 = (l >> 8 & 0xFF) / 255.0F;
/*  71: 63 */     float f2 = (l & 0xFF) / 255.0F;
/*  72: 64 */     Tessellator t = Tessellator.instance;
/*  73: 65 */     t.setColorOpaque_F(1.0F, 1.0F, 1.0F);
/*  74:    */     
/*  75: 67 */     GL11.glTranslatef(-0.0F, -0.5F, -0.0F);
/*  76: 68 */     IIcon icon = block.getIcon(2, meta);
/*  77: 69 */     float wu = icon.getMaxU() - icon.getMinU();
/*  78: 70 */     float wv = icon.getMaxV() - icon.getMinV();
/*  79: 71 */     float ddv = wv * 0.3125F;
/*  80: 73 */     for (int i = 0; i < 8; i++)
/*  81:    */     {
/*  82: 74 */       w = base_w * h;
/*  83: 75 */       t.startDrawingQuads();
/*  84: 76 */       t.setNormal(ddx(i + 0.5D), 0.0F, ddz(i + 0.5D));
/*  85: 77 */       t.setColorOpaque_F(f, f1, f2);
/*  86: 78 */       t.addVertexWithUV(dx(i + 1), 0.0D, dz(i + 1), icon.getMinU() + du2(i) * wu, icon.getMaxV());
/*  87: 79 */       t.addVertexWithUV(dx(i + 1), h2, dz(i + 1), icon.getMinU() + du2(i) * wu, icon.getMaxV() - ddv);
/*  88: 80 */       t.addVertexWithUV(dx(i), h2, dz(i), icon.getMinU() + du(i) * wu, icon.getMaxV() - ddv);
/*  89: 81 */       t.addVertexWithUV(dx(i), 0.0D, dz(i), icon.getMinU() + du(i) * wu, icon.getMaxV());
/*  90: 82 */       t.addVertexWithUV(dx(i + 1), h2 * 2.0F, dz(i + 1), icon.getMinU() + du2(i) * wu, icon.getMinV() + ddv);
/*  91: 83 */       t.addVertexWithUV(dx(i + 1), 1.0D, dz(i + 1), icon.getMinU() + du2(i) * wu, icon.getMinV());
/*  92: 84 */       t.addVertexWithUV(dx(i), 1.0D, dz(i), icon.getMinU() + du(i) * wu, icon.getMinV());
/*  93: 85 */       t.addVertexWithUV(dx(i), h2 * 2.0F, dz(i), icon.getMinU() + du(i) * wu, icon.getMinV() + ddv);
/*  94: 86 */       t.draw();
/*  95: 87 */       t.startDrawingQuads();
/*  96: 88 */       t.setColorOpaque_F(1.0F, 1.0F, 1.0F);
/*  97: 89 */       t.setNormal(ddx(i + 0.5D), 0.0F, ddz(i + 0.5D));
/*  98: 90 */       t.addVertexWithUV(dx(i + 1), h2, dz(i + 1), icon.getMinU() + du2(i) * wu, icon.getMaxV() - ddv);
/*  99: 91 */       t.addVertexWithUV(dx(i + 1), h2 * 2.0F, dz(i + 1), icon.getMinU() + du2(i) * wu, icon.getMinV() + ddv);
/* 100: 92 */       t.addVertexWithUV(dx(i), h2 * 2.0F, dz(i), icon.getMinU() + du(i) * wu, icon.getMinV() + ddv);
/* 101: 93 */       t.addVertexWithUV(dx(i), h2, dz(i), icon.getMinU() + du(i) * wu, icon.getMaxV() - ddv);
/* 102: 94 */       t.draw();
/* 103: 95 */       w = base_w;
/* 104: 96 */       t.startDrawingQuads();
/* 105: 97 */       t.setColorOpaque_F(0.6F, 0.6F, 0.6F);
/* 106: 98 */       t.setNormal(ddx(i + 0.5D), 0.0F, ddz(i + 0.5D));
/* 107: 99 */       t.addVertexWithUV(dx(i + 1), 0.0D, dz(i + 1), icon.getMinU() + du2(i) * wu, icon.getMaxV());
/* 108:100 */       t.addVertexWithUV(dx(i + 1), 0.05D, dz(i + 1), icon.getMinU() + du2(i) * wu, icon.getMaxV() - wu * 0.05D);
/* 109:101 */       t.addVertexWithUV(dx(i), 0.05D, dz(i), icon.getMinU() + du(i) * wu, icon.getMaxV() - wu * 0.05D);
/* 110:102 */       t.addVertexWithUV(dx(i), 0.0D, dz(i), icon.getMinU() + du(i) * wu, icon.getMaxV());
/* 111:103 */       t.addVertexWithUV(dx(i + 1), 0.95D, dz(i + 1), icon.getMinU() + du2(i) * wu, icon.getMinV() + wu * 0.05D);
/* 112:104 */       t.addVertexWithUV(dx(i + 1), 1.0D, dz(i + 1), icon.getMinU() + du2(i) * wu, icon.getMinV());
/* 113:105 */       t.addVertexWithUV(dx(i), 1.0D, dz(i), icon.getMinU() + du(i) * wu, icon.getMinV());
/* 114:106 */       t.addVertexWithUV(dx(i), 0.95D, dz(i), icon.getMinU() + du(i) * wu, icon.getMinV() + wu * 0.05D);
/* 115:107 */       w = base_w * h * 0.9D;
/* 116:108 */       t.addVertexWithUV(1.0D - dx(i), 0.95D, 1.0D - dz(i), icon.getMinU() + du(i) * wu, icon.getMaxV());
/* 117:109 */       t.addVertexWithUV(1.0D - dx(i), 1.0D, 1.0D - dz(i), icon.getMinU() + du(i) * wu, icon.getMaxV() - wu * 0.05D);
/* 118:110 */       t.addVertexWithUV(1.0D - dx(i + 1), 1.0D, 1.0D - dz(i + 1), icon.getMinU() + du2(i) * wu, icon.getMaxV() - wu * 0.05D);
/* 119:111 */       t.addVertexWithUV(1.0D - dx(i + 1), 0.95D, 1.0D - dz(i + 1), icon.getMinU() + du2(i) * wu, icon.getMaxV());
/* 120:112 */       t.addVertexWithUV(1.0D - dx(i), 0.0D, 1.0D - dz(i), icon.getMinU() + du(i) * wu, icon.getMinV());
/* 121:113 */       t.addVertexWithUV(1.0D - dx(i), 0.05D, 1.0D - dz(i), icon.getMinU() + du(i) * wu, icon.getMinV() + wu * 0.05D);
/* 122:114 */       t.addVertexWithUV(1.0D - dx(i + 1), 0.05D, 1.0D - dz(i + 1), icon.getMinU() + du2(i) * wu, icon.getMinV() + wu * 0.05D);
/* 123:115 */       t.addVertexWithUV(1.0D - dx(i + 1), 0.0D, 1.0D - dz(i + 1), icon.getMinU() + du2(i) * wu, icon.getMinV());
/* 124:116 */       t.draw();
/* 125:    */     }
/* 126:119 */     w = base_w;
/* 127:120 */     icon = block.getIcon(1, meta);
/* 128:121 */     wu = icon.getMaxU() - icon.getMinU();
/* 129:122 */     wv = icon.getMaxV() - icon.getMinV();
/* 130:124 */     for (int i = 0; i < 8; i++)
/* 131:    */     {
/* 132:125 */       t.startDrawingQuads();
/* 133:126 */       t.setColorOpaque_F(0.8F, 0.8F, 0.8F);
/* 134:127 */       t.setNormal(0.0F, 1.0F, 0.0F);
/* 135:128 */       w = base_w;
/* 136:129 */       t.addVertexWithUV(dx(i), 1.0D, dz(i), icon.getMinU() + dx(i) * wu, icon.getMaxV() - dz(i) * wv);
/* 137:130 */       t.addVertexWithUV(dx(i + 1), 1.0D, dz(i + 1), icon.getMinU() + dx(i + 1) * wu, icon.getMaxV() - dz(i + 1) * wv);
/* 138:131 */       w = base_w * h * 0.9D;
/* 139:132 */       t.addVertexWithUV(dx(i + 1), 1.0D, dz(i + 1), icon.getMinU() + dx(i + 1) * wu, icon.getMaxV() - dz(i + 1) * wv);
/* 140:133 */       t.addVertexWithUV(dx(i), 1.0D, dz(i), icon.getMinU() + dx(i) * wu, icon.getMaxV() - dz(i) * wv);
/* 141:134 */       t.draw();
/* 142:135 */       t.startDrawingQuads();
/* 143:136 */       t.setColorOpaque_F(1.0F, 1.0F, 1.0F);
/* 144:137 */       t.setNormal(0.0F, 1.0F, 0.0F);
/* 145:138 */       w = base_w * h * 0.9D;
/* 146:139 */       t.addVertexWithUV(dx(i), h, dz(i), icon.getMinU() + dx(i) * wu, icon.getMaxV() - dz(i) * wv);
/* 147:140 */       t.addVertexWithUV(dx(i + 1), h, dz(i + 1), icon.getMinU() + dx(i + 1) * wu, icon.getMaxV() - dz(i + 1) * wv);
/* 148:141 */       w = 0.0D;
/* 149:142 */       t.addVertexWithUV(dx(i + 1), h, dz(i + 1), icon.getMinU() + dx(i + 1) * wu, icon.getMaxV() - dz(i + 1) * wv);
/* 150:143 */       t.addVertexWithUV(dx(i), h, dz(i), icon.getMinU() + dx(i) * wu, icon.getMaxV() - dz(i) * wv);
/* 151:144 */       t.draw();
/* 152:    */     }
/* 153:147 */     icon = block.getIcon(0, meta);
/* 154:148 */     wu = icon.getMaxU() - icon.getMinU();
/* 155:149 */     wv = icon.getMaxV() - icon.getMinV();
/* 156:151 */     for (int i = 0; i < 8; i++)
/* 157:    */     {
/* 158:152 */       t.startDrawingQuads();
/* 159:153 */       t.setColorOpaque_F(0.8F, 0.8F, 0.8F);
/* 160:154 */       t.setNormal(0.0F, -1.0F, 0.0F);
/* 161:155 */       w = base_w * h * 0.9D;
/* 162:156 */       t.addVertexWithUV(dx(i), 0.0D, dz(i), icon.getMinU() + dx(i) * wu, icon.getMaxV() - dz(i) * wv);
/* 163:157 */       t.addVertexWithUV(dx(i + 1), 0.0D, dz(i + 1), icon.getMinU() + dx(i + 1) * wu, icon.getMaxV() - dz(i + 1) * wv);
/* 164:158 */       w = base_w;
/* 165:159 */       t.addVertexWithUV(dx(i + 1), 0.0D, dz(i + 1), icon.getMinU() + dx(i + 1) * wu, icon.getMaxV() - dz(i + 1) * wv);
/* 166:160 */       t.addVertexWithUV(dx(i), 0.0D, dz(i), icon.getMinU() + dx(i) * wu, icon.getMaxV() - dz(i) * wv);
/* 167:161 */       t.draw();
/* 168:162 */       t.startDrawingQuads();
/* 169:163 */       t.setColorOpaque_F(1.0F, 1.0F, 1.0F);
/* 170:164 */       t.setNormal(0.0F, 1.0F, 0.0F);
/* 171:165 */       w = 0.0D;
/* 172:166 */       t.addVertexWithUV(dx(i), 1.0F - h, dz(i), icon.getMinU() + dx(i) * wu, icon.getMaxV() - dz(i) * wv);
/* 173:167 */       t.addVertexWithUV(dx(i + 1), 1.0F - h, dz(i + 1), icon.getMinU() + dx(i + 1) * wu, icon.getMaxV() - dz(i + 1) * wv);
/* 174:168 */       w = base_w * h * 0.9D;
/* 175:169 */       t.addVertexWithUV(dx(i + 1), 1.0F - h, dz(i + 1), icon.getMinU() + dx(i + 1) * wu, icon.getMaxV() - dz(i + 1) * wv);
/* 176:170 */       t.addVertexWithUV(dx(i), 1.0F - h, dz(i), icon.getMinU() + dx(i) * wu, icon.getMaxV() - dz(i) * wv);
/* 177:171 */       t.draw();
/* 178:    */     }
/* 179:175 */     GL11.glTranslatef(0.0F, 0.5F, 0.0F);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {}
/* 183:    */   
/* 184:    */   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
/* 185:    */   {
/* 186:184 */     int brightness = block.getBlockBrightness(world, x, y, z);
/* 187:185 */     int meta = world.getBlockMetadata(x, y, z);
/* 188:186 */     float h = 0.97F;
/* 189:187 */     float d = 0.2F;
/* 190:188 */     float h2 = 0.3125F;
/* 191:189 */     int l = block.colorMultiplier(world, x, y, z);
/* 192:190 */     float f = (l >> 16 & 0xFF) / 255.0F;
/* 193:191 */     float f1 = (l >> 8 & 0xFF) / 255.0F;
/* 194:192 */     float f2 = (l & 0xFF) / 255.0F;
/* 195:193 */     Tessellator t = Tessellator.instance;
/* 196:194 */     t.setBrightness(brightness);
/* 197:195 */     t.setColorOpaque_F(1.0F, 1.0F, 1.0F);
/* 198:196 */     t.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
/* 199:197 */     IIcon icon = block.getIcon(2, meta);
/* 200:199 */     if (renderer.overrideBlockTexture != null) {
/* 201:200 */       icon = renderer.overrideBlockTexture;
/* 202:    */     }
/* 203:203 */     float wu = icon.getMaxU() - icon.getMinU();
/* 204:204 */     float wv = icon.getMaxV() - icon.getMinV();
/* 205:205 */     float ddv = wv * 0.3125F;
/* 206:207 */     for (int i = 0; i < 8; i++)
/* 207:    */     {
/* 208:208 */       w = base_w * h;
/* 209:209 */       setB(i + 1 - d, 1.0F, f, f1, f2);
/* 210:210 */       t.addVertexWithUV(x + dx(i + 1), y, z + dz(i + 1), icon.getMinU() + du2(i) * wu, icon.getMaxV());
/* 211:211 */       t.addVertexWithUV(x + dx(i + 1), y + h2, z + dz(i + 1), icon.getMinU() + du2(i) * wu, icon.getMaxV() - ddv);
/* 212:212 */       setB(i + d, 0.9F, f, f1, f2);
/* 213:213 */       t.addVertexWithUV(x + dx(i), y + h2, z + dz(i), icon.getMinU() + du(i) * wu, icon.getMaxV() - ddv);
/* 214:214 */       t.addVertexWithUV(x + dx(i), y, z + dz(i), icon.getMinU() + du(i) * wu, icon.getMaxV());
/* 215:215 */       setB(i + 1 - d, 1.0F, f, f1, f2);
/* 216:216 */       t.addVertexWithUV(x + dx(i + 1), y + h2 * 2.0F, z + dz(i + 1), icon.getMinU() + du2(i) * wu, icon.getMinV() + ddv);
/* 217:217 */       t.addVertexWithUV(x + dx(i + 1), y + 1, z + dz(i + 1), icon.getMinU() + du2(i) * wu, icon.getMinV());
/* 218:218 */       setB(i + d, 0.9F, f, f1, f2);
/* 219:219 */       t.addVertexWithUV(x + dx(i), y + 1, z + dz(i), icon.getMinU() + du(i) * wu, icon.getMinV());
/* 220:220 */       t.addVertexWithUV(x + dx(i), y + h2 * 2.0F, z + dz(i), icon.getMinU() + du(i) * wu, icon.getMinV() + ddv);
/* 221:221 */       setB(i + 1 - d, 1.0F);
/* 222:222 */       t.addVertexWithUV(x + dx(i + 1), y + h2, z + dz(i + 1), icon.getMinU() + du2(i) * wu, icon.getMaxV() - ddv);
/* 223:223 */       t.addVertexWithUV(x + dx(i + 1), y + h2 * 2.0F, z + dz(i + 1), icon.getMinU() + du2(i) * wu, icon.getMinV() + ddv);
/* 224:224 */       setB(i + d, 1.0F);
/* 225:225 */       t.addVertexWithUV(x + dx(i), y + h2 * 2.0F, z + dz(i), icon.getMinU() + du(i) * wu, icon.getMinV() + ddv);
/* 226:226 */       t.addVertexWithUV(x + dx(i), y + h2, z + dz(i), icon.getMinU() + du(i) * wu, icon.getMaxV() - ddv);
/* 227:227 */       t.setColorOpaque_F(0.65F, 0.65F, 0.65F);
/* 228:228 */       w = base_w;
/* 229:229 */       setB(i + 1 - d, 0.6F);
/* 230:230 */       t.addVertexWithUV(x + dx(i + 1), y, z + dz(i + 1), icon.getMinU() + du2(i) * wu, icon.getMaxV());
/* 231:231 */       t.addVertexWithUV(x + dx(i + 1), y + 0.05D, z + dz(i + 1), icon.getMinU() + du2(i) * wu, icon.getMaxV() - wu * 0.05D);
/* 232:232 */       setB(i + d, 0.6F);
/* 233:233 */       t.addVertexWithUV(x + dx(i), y + 0.05D, z + dz(i), icon.getMinU() + du(i) * wu, icon.getMaxV() - wu * 0.05D);
/* 234:234 */       t.addVertexWithUV(x + dx(i), y, z + dz(i), icon.getMinU() + du(i) * wu, icon.getMaxV());
/* 235:235 */       setB(i + 1 - d, 0.6F);
/* 236:236 */       t.addVertexWithUV(x + dx(i + 1), y + 0.95D, z + dz(i + 1), icon.getMinU() + du2(i) * wu, icon.getMinV() + wu * 0.05D);
/* 237:237 */       t.addVertexWithUV(x + dx(i + 1), y + 1, z + dz(i + 1), icon.getMinU() + du2(i) * wu, icon.getMinV());
/* 238:238 */       setB(i + d, 0.6F);
/* 239:239 */       t.addVertexWithUV(x + dx(i), y + 1, z + dz(i), icon.getMinU() + du(i) * wu, icon.getMinV());
/* 240:240 */       t.addVertexWithUV(x + dx(i), y + 0.95D, z + dz(i), icon.getMinU() + du(i) * wu, icon.getMinV() + wu * 0.05D);
/* 241:241 */       w = base_w * h * 0.9D;
/* 242:242 */       setB(i + d, 0.6F);
/* 243:243 */       t.addVertexWithUV(x + 1 - dx(i), y + 0.95D, z + 1 - dz(i), icon.getMinU() + du(i) * wu, icon.getMaxV());
/* 244:244 */       t.addVertexWithUV(x + 1 - dx(i), y + 1, z + 1 - dz(i), icon.getMinU() + du(i) * wu, icon.getMaxV() - wu * 0.05D);
/* 245:245 */       setB(i + 1 - d, 0.6F);
/* 246:246 */       t.addVertexWithUV(x + 1 - dx(i + 1), y + 1, z + 1 - dz(i + 1), icon.getMinU() + du2(i) * wu, icon.getMaxV() - wu * 0.05D);
/* 247:247 */       t.addVertexWithUV(x + 1 - dx(i + 1), y + 0.95D, z + 1 - dz(i + 1), icon.getMinU() + du2(i) * wu, icon.getMaxV());
/* 248:248 */       setB(i + d, 0.6F);
/* 249:249 */       t.addVertexWithUV(x + 1 - dx(i), y, z + 1 - dz(i), icon.getMinU() + du(i) * wu, icon.getMinV());
/* 250:250 */       t.addVertexWithUV(x + 1 - dx(i), y + 0.05D, z + 1 - dz(i), icon.getMinU() + du(i) * wu, icon.getMinV() + wu * 0.05D);
/* 251:251 */       setB(i + 1 - d, 0.6F);
/* 252:252 */       t.addVertexWithUV(x + 1 - dx(i + 1), y + 0.05D, z + 1 - dz(i + 1), icon.getMinU() + du2(i) * wu, icon.getMinV() + wu * 0.05D);
/* 253:253 */       t.addVertexWithUV(x + 1 - dx(i + 1), y, z + 1 - dz(i + 1), icon.getMinU() + du2(i) * wu, icon.getMinV());
/* 254:    */     }
/* 255:256 */     w = base_w;
/* 256:257 */     icon = block.getIcon(1, meta);
/* 257:259 */     if (renderer.overrideBlockTexture != null) {
/* 258:260 */       icon = renderer.overrideBlockTexture;
/* 259:    */     }
/* 260:263 */     wu = icon.getMaxU() - icon.getMinU();
/* 261:264 */     wv = icon.getMaxV() - icon.getMinV();
/* 262:266 */     for (int i = 0; i < 8; i++)
/* 263:    */     {
/* 264:267 */       t.setColorOpaque_F(0.8F, 0.8F, 0.8F);
/* 265:268 */       w = base_w;
/* 266:269 */       t.addVertexWithUV(x + dx(i), y + 1, z + dz(i), icon.getMinU() + dx(i) * wu, icon.getMaxV() - dz(i) * wv);
/* 267:270 */       t.addVertexWithUV(x + dx(i + 1), y + 1, z + dz(i + 1), icon.getMinU() + dx(i + 1) * wu, icon.getMaxV() - dz(i + 1) * wv);
/* 268:271 */       w = base_w * h * 0.9D;
/* 269:272 */       t.addVertexWithUV(x + dx(i + 1), y + 1, z + dz(i + 1), icon.getMinU() + dx(i + 1) * wu, icon.getMaxV() - dz(i + 1) * wv);
/* 270:273 */       t.addVertexWithUV(x + dx(i), y + 1, z + dz(i), icon.getMinU() + dx(i) * wu, icon.getMaxV() - dz(i) * wv);
/* 271:274 */       t.setColorOpaque_F(1.0F, 1.0F, 1.0F);
/* 272:275 */       w = base_w * h * 0.9D;
/* 273:276 */       t.addVertexWithUV(x + dx(i), y + h, z + dz(i), icon.getMinU() + dx(i) * wu, icon.getMaxV() - dz(i) * wv);
/* 274:277 */       t.addVertexWithUV(x + dx(i + 1), y + h, z + dz(i + 1), icon.getMinU() + dx(i + 1) * wu, icon.getMaxV() - dz(i + 1) * wv);
/* 275:278 */       w = 0.0D;
/* 276:279 */       t.addVertexWithUV(x + dx(i + 1), y + h, z + dz(i + 1), icon.getMinU() + dx(i + 1) * wu, icon.getMaxV() - dz(i + 1) * wv);
/* 277:280 */       t.addVertexWithUV(x + dx(i), y + h, z + dz(i), icon.getMinU() + dx(i) * wu, icon.getMaxV() - dz(i) * wv);
/* 278:    */     }
/* 279:283 */     t.setColorOpaque_F(0.5F, 0.5F, 0.5F);
/* 280:284 */     icon = block.getIcon(0, meta);
/* 281:286 */     if (renderer.overrideBlockTexture != null) {
/* 282:287 */       icon = renderer.overrideBlockTexture;
/* 283:    */     }
/* 284:290 */     wu = icon.getMaxU() - icon.getMinU();
/* 285:291 */     wv = icon.getMaxV() - icon.getMinV();
/* 286:293 */     for (int i = 0; i < 8; i++)
/* 287:    */     {
/* 288:294 */       t.setColorOpaque_F(0.5F, 0.5F, 0.5F);
/* 289:295 */       w = base_w * h * 0.9D;
/* 290:296 */       t.addVertexWithUV(x + dx(i), y, z + dz(i), icon.getMinU() + dx(i) * wu, icon.getMaxV() - dz(i) * wv);
/* 291:297 */       t.addVertexWithUV(x + dx(i + 1), y, z + dz(i + 1), icon.getMinU() + dx(i + 1) * wu, icon.getMaxV() - dz(i + 1) * wv);
/* 292:298 */       w = base_w;
/* 293:299 */       t.addVertexWithUV(x + dx(i + 1), y, z + dz(i + 1), icon.getMinU() + dx(i + 1) * wu, icon.getMaxV() - dz(i + 1) * wv);
/* 294:300 */       t.addVertexWithUV(x + dx(i), y, z + dz(i), icon.getMinU() + dx(i) * wu, icon.getMaxV() - dz(i) * wv);
/* 295:301 */       t.setColorOpaque_F(0.6F, 0.6F, 0.6F);
/* 296:302 */       w = 0.0D;
/* 297:303 */       t.addVertexWithUV(x + dx(i), y + 1 - h, z + dz(i), icon.getMinU() + dx(i) * wu, icon.getMaxV() - dz(i) * wv);
/* 298:304 */       t.addVertexWithUV(x + dx(i + 1), y + 1 - h, z + dz(i + 1), icon.getMinU() + dx(i + 1) * wu, icon.getMaxV() - dz(i + 1) * wv);
/* 299:305 */       w = base_w * h * 0.9D;
/* 300:306 */       t.addVertexWithUV(x + dx(i + 1), y + 1 - h, z + dz(i + 1), icon.getMinU() + dx(i + 1) * wu, icon.getMaxV() - dz(i + 1) * wv);
/* 301:307 */       t.addVertexWithUV(x + dx(i), y + 1 - h, z + dz(i), icon.getMinU() + dx(i) * wu, icon.getMaxV() - dz(i) * wv);
/* 302:    */     }
/* 303:310 */     return false;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void setB(float i, float p, float r, float g, float b)
/* 307:    */   {
/* 308:314 */     float brightness = (float)(p * (0.7D + Math.cos(((i + 0.5D) / 4.0D * 2.0D + 1.0D) * 3.141592653589793D) * 0.1D));
/* 309:315 */     Tessellator.instance.setColorOpaque_F(brightness * r, brightness * g, brightness * b);
/* 310:    */   }
/* 311:    */   
/* 312:    */   public void setB(float i, float p)
/* 313:    */   {
/* 314:319 */     setB(i, p, 1.0F, 1.0F, 1.0F);
/* 315:    */   }
/* 316:    */   
/* 317:    */   public boolean shouldRender3DInInventory(int modelId)
/* 318:    */   {
/* 319:324 */     return false;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public int getRenderId()
/* 323:    */   {
/* 324:329 */     return ExtraUtilsProxy.drumRendererID;
/* 325:    */   }
/* 326:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.render.RenderBlockDrum
 * JD-Core Version:    0.7.0.1
 */