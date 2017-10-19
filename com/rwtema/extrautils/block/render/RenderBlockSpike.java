/*   1:    */ package com.rwtema.extrautils.block.render;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*   4:    */ import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
/*   5:    */ import cpw.mods.fml.relauncher.Side;
/*   6:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   7:    */ import net.minecraft.block.Block;
/*   8:    */ import net.minecraft.client.Minecraft;
/*   9:    */ import net.minecraft.client.renderer.RenderBlocks;
/*  10:    */ import net.minecraft.client.renderer.Tessellator;
/*  11:    */ import net.minecraft.util.IIcon;
/*  12:    */ import net.minecraft.world.IBlockAccess;
/*  13:    */ import org.lwjgl.opengl.GL11;
/*  14:    */ 
/*  15:    */ @SideOnly(Side.CLIENT)
/*  16:    */ public class RenderBlockSpike
/*  17:    */   implements ISimpleBlockRenderingHandler
/*  18:    */ {
/*  19:    */   public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
/*  20:    */   {
/*  21: 20 */     Tessellator t = Tessellator.instance;
/*  22: 21 */     GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
/*  23: 22 */     GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/*  24: 23 */     renderSpikeBlock(Minecraft.getMinecraft().theWorld, 0, 0, 0, 1, 0, block, renderer, -1);
/*  25: 24 */     GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
/*  26: 25 */     GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public boolean renderSpikeBlock(IBlockAccess world, int x, int y, int z, int side, int type, Block block, RenderBlocks renderer, int brightness)
/*  30:    */   {
/*  31: 33 */     float ax = 0.0F;float ay = 0.0F;float az = 0.0F;
/*  32: 34 */     float bx = 1.0F;float by = 0.0F;float bz = 0.0F;
/*  33: 35 */     float cx = 0.0F;float cy = 0.0F;float cz = 1.0F;
/*  34: 36 */     float dx = 1.0F;float dy = 0.0F;float dz = 1.0F;
/*  35: 37 */     float ex = 0.0F;float ey = 1.0F;float ez = 0.0F;
/*  36: 38 */     float fx = 1.0F;float fy = 1.0F;float fz = 0.0F;
/*  37: 39 */     float gx = 0.0F;float gy = 1.0F;float gz = 1.0F;
/*  38: 40 */     float hx = 1.0F;float hy = 1.0F;float hz = 1.0F;
/*  39: 42 */     switch (side)
/*  40:    */     {
/*  41:    */     case 0: 
/*  42: 45 */       ax = az = bx = bz = cx = cz = dx = dz = 0.5F;
/*  43: 46 */       break;
/*  44:    */     case 1: 
/*  45: 50 */       ex = ez = fx = fz = gx = gz = hx = hz = 0.5F;
/*  46: 51 */       break;
/*  47:    */     case 2: 
/*  48: 55 */       ay = by = ey = fy = ax = bx = ex = fx = 0.5F;
/*  49: 56 */       break;
/*  50:    */     case 3: 
/*  51: 60 */       cy = dy = gy = hy = cx = dx = gx = hx = 0.5F;
/*  52: 61 */       break;
/*  53:    */     case 4: 
/*  54: 65 */       ay = cy = ey = gy = az = cz = ez = gz = 0.5F;
/*  55: 66 */       break;
/*  56:    */     case 5: 
/*  57: 70 */       by = dy = fy = hy = bz = dz = fz = hz = 0.5F;
/*  58: 71 */       break;
/*  59:    */     default: 
/*  60: 74 */       return false;
/*  61:    */     }
/*  62: 77 */     IIcon texture = block.getIcon(side, type);
/*  63: 79 */     if (renderer.hasOverrideBlockTexture()) {
/*  64: 80 */       texture = renderer.overrideBlockTexture;
/*  65:    */     }
/*  66: 83 */     Tessellator tessellator = Tessellator.instance;
/*  67: 85 */     if (brightness >= 0) {
/*  68: 86 */       tessellator.setBrightness(brightness);
/*  69:    */     }
/*  70: 89 */     boolean inventory = brightness < 0;
/*  71: 92 */     if (brightness >= 0) {
/*  72: 93 */       tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
/*  73:    */     }
/*  74: 96 */     if (!renderer.hasOverrideBlockTexture()) {
/*  75: 97 */       texture = block.getIcon(0, side + type * 6);
/*  76:    */     }
/*  77:100 */     if (inventory)
/*  78:    */     {
/*  79:101 */       tessellator.startDrawingQuads();
/*  80:102 */       tessellator.setNormal(0.0F, -1.0F, 0.0F);
/*  81:    */     }
/*  82:105 */     if (side != 0)
/*  83:    */     {
/*  84:106 */       float[] u = { ax, bx, dx, cx };
/*  85:107 */       float[] v = { az, bz, dz, cz };
/*  86:108 */       int rotation = calcRotation(0, side);
/*  87:109 */       tessellator.addVertexWithUV(x + ax, y + ay, z + az, getU(0, texture, rotation, u, v), getV(0, texture, rotation, u, v));
/*  88:110 */       tessellator.addVertexWithUV(x + bx, y + by, z + bz, getU(1, texture, rotation, u, v), getV(1, texture, rotation, u, v));
/*  89:111 */       tessellator.addVertexWithUV(x + dx, y + dy, z + dz, getU(2, texture, rotation, u, v), getV(2, texture, rotation, u, v));
/*  90:112 */       tessellator.addVertexWithUV(x + cx, y + cy, z + cz, getU(3, texture, rotation, u, v), getV(3, texture, rotation, u, v));
/*  91:    */     }
/*  92:115 */     if (inventory) {
/*  93:116 */       tessellator.draw();
/*  94:    */     }
/*  95:121 */     if (inventory)
/*  96:    */     {
/*  97:122 */       tessellator.startDrawingQuads();
/*  98:123 */       tessellator.setNormal(0.0F, 1.0F, 0.0F);
/*  99:    */     }
/* 100:126 */     if (brightness >= 0) {
/* 101:127 */       tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
/* 102:    */     }
/* 103:130 */     if (!renderer.hasOverrideBlockTexture()) {
/* 104:131 */       texture = block.getIcon(1, side + type * 6);
/* 105:    */     }
/* 106:134 */     if (side != 1)
/* 107:    */     {
/* 108:135 */       float[] u = { ex, gx, hx, fx };
/* 109:136 */       float[] v = { ez, gz, hz, fz };
/* 110:137 */       int rotation = calcRotation(1, side);
/* 111:138 */       tessellator.addVertexWithUV(x + ex, y + ey, z + ez, getU(0, texture, rotation, u, v), getV(0, texture, rotation, u, v));
/* 112:139 */       tessellator.addVertexWithUV(x + gx, y + gy, z + gz, getU(1, texture, rotation, u, v), getV(1, texture, rotation, u, v));
/* 113:140 */       tessellator.addVertexWithUV(x + hx, y + hy, z + hz, getU(2, texture, rotation, u, v), getV(2, texture, rotation, u, v));
/* 114:141 */       tessellator.addVertexWithUV(x + fx, y + fy, z + fz, getU(3, texture, rotation, u, v), getV(3, texture, rotation, u, v));
/* 115:    */     }
/* 116:144 */     if (inventory) {
/* 117:145 */       tessellator.draw();
/* 118:    */     }
/* 119:149 */     if (brightness >= 0) {
/* 120:150 */       if (side == 0) {
/* 121:151 */         tessellator.setColorOpaque_F(0.65F, 0.65F, 0.65F);
/* 122:152 */       } else if (side == 1) {
/* 123:153 */         tessellator.setColorOpaque_F(0.9F, 0.9F, 0.9F);
/* 124:    */       } else {
/* 125:155 */         tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
/* 126:    */       }
/* 127:    */     }
/* 128:158 */     if (!renderer.hasOverrideBlockTexture()) {
/* 129:159 */       texture = block.getIcon(2, side + type * 6);
/* 130:    */     }
/* 131:162 */     if (inventory)
/* 132:    */     {
/* 133:163 */       tessellator.startDrawingQuads();
/* 134:164 */       tessellator.setNormal(0.0F, 0.445F, 0.894F);
/* 135:    */     }
/* 136:167 */     if (side != 2)
/* 137:    */     {
/* 138:168 */       float[] u = { 1.0F - ax, 1.0F - ex, 1.0F - fx, 1.0F - bx };
/* 139:169 */       float[] v = { 1.0F - ay, 1.0F - ey, 1.0F - fy, 1.0F - by };
/* 140:170 */       int rotation = calcRotation(2, side);
/* 141:171 */       tessellator.addVertexWithUV(x + ax, y + ay, z + az, getU(0, texture, rotation, u, v), getV(0, texture, rotation, u, v));
/* 142:172 */       tessellator.addVertexWithUV(x + ex, y + ey, z + ez, getU(1, texture, rotation, u, v), getV(1, texture, rotation, u, v));
/* 143:173 */       tessellator.addVertexWithUV(x + fx, y + fy, z + fz, getU(2, texture, rotation, u, v), getV(2, texture, rotation, u, v));
/* 144:174 */       tessellator.addVertexWithUV(x + bx, y + by, z + bz, getU(3, texture, rotation, u, v), getV(3, texture, rotation, u, v));
/* 145:    */     }
/* 146:177 */     if (inventory) {
/* 147:178 */       tessellator.draw();
/* 148:    */     }
/* 149:182 */     if (brightness >= 0) {
/* 150:183 */       if (side == 0) {
/* 151:184 */         tessellator.setColorOpaque_F(0.65F, 0.65F, 0.65F);
/* 152:185 */       } else if (side == 1) {
/* 153:186 */         tessellator.setColorOpaque_F(0.9F, 0.9F, 0.9F);
/* 154:    */       } else {
/* 155:188 */         tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
/* 156:    */       }
/* 157:    */     }
/* 158:191 */     if (!renderer.hasOverrideBlockTexture()) {
/* 159:192 */       texture = block.getIcon(3, side + type * 6);
/* 160:    */     }
/* 161:195 */     if (inventory)
/* 162:    */     {
/* 163:196 */       tessellator.startDrawingQuads();
/* 164:197 */       tessellator.setNormal(0.0F, 0.445F, -0.894F);
/* 165:    */     }
/* 166:200 */     if (side != 3)
/* 167:    */     {
/* 168:201 */       float[] u = { dx, hx, gx, cx };
/* 169:202 */       float[] v = { 1.0F - dy, 1.0F - hy, 1.0F - gy, 1.0F - cy };
/* 170:203 */       int rotation = calcRotation(3, side);
/* 171:204 */       tessellator.addVertexWithUV(x + dx, y + dy, z + dz, getU(0, texture, rotation, u, v), getV(0, texture, rotation, u, v));
/* 172:205 */       tessellator.addVertexWithUV(x + hx, y + hy, z + hz, getU(1, texture, rotation, u, v), getV(1, texture, rotation, u, v));
/* 173:206 */       tessellator.addVertexWithUV(x + gx, y + gy, z + gz, getU(2, texture, rotation, u, v), getV(2, texture, rotation, u, v));
/* 174:207 */       tessellator.addVertexWithUV(x + cx, y + cy, z + cz, getU(3, texture, rotation, u, v), getV(3, texture, rotation, u, v));
/* 175:    */     }
/* 176:210 */     if (inventory) {
/* 177:211 */       tessellator.draw();
/* 178:    */     }
/* 179:215 */     if (brightness >= 0) {
/* 180:216 */       if (side == 0) {
/* 181:217 */         tessellator.setColorOpaque_F(0.55F, 0.55F, 0.55F);
/* 182:218 */       } else if (side == 1) {
/* 183:219 */         tessellator.setColorOpaque_F(0.7F, 0.7F, 0.7F);
/* 184:    */       } else {
/* 185:221 */         tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
/* 186:    */       }
/* 187:    */     }
/* 188:224 */     if (!renderer.hasOverrideBlockTexture()) {
/* 189:225 */       texture = block.getIcon(4, side + type * 6);
/* 190:    */     }
/* 191:228 */     if (inventory)
/* 192:    */     {
/* 193:229 */       tessellator.startDrawingQuads();
/* 194:230 */       tessellator.setNormal(0.894F, 0.445F, 0.0F);
/* 195:    */     }
/* 196:233 */     if (side != 4)
/* 197:    */     {
/* 198:234 */       float[] u = { cz, gz, ez, az };
/* 199:235 */       float[] v = { 1.0F - cy, 1.0F - gy, 1.0F - ey, 1.0F - ay };
/* 200:236 */       int rotation = calcRotation(4, side);
/* 201:237 */       tessellator.addVertexWithUV(x + cx, y + cy, z + cz, getU(0, texture, rotation, u, v), getV(0, texture, rotation, u, v));
/* 202:238 */       tessellator.addVertexWithUV(x + gx, y + gy, z + gz, getU(1, texture, rotation, u, v), getV(1, texture, rotation, u, v));
/* 203:239 */       tessellator.addVertexWithUV(x + ex, y + ey, z + ez, getU(2, texture, rotation, u, v), getV(2, texture, rotation, u, v));
/* 204:240 */       tessellator.addVertexWithUV(x + ax, y + ay, z + az, getU(3, texture, rotation, u, v), getV(3, texture, rotation, u, v));
/* 205:    */     }
/* 206:243 */     if (inventory) {
/* 207:244 */       tessellator.draw();
/* 208:    */     }
/* 209:248 */     if (brightness >= 0) {
/* 210:249 */       if (side == 0) {
/* 211:250 */         tessellator.setColorOpaque_F(0.55F, 0.55F, 0.55F);
/* 212:251 */       } else if (side == 1) {
/* 213:252 */         tessellator.setColorOpaque_F(0.7F, 0.7F, 0.7F);
/* 214:    */       } else {
/* 215:254 */         tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
/* 216:    */       }
/* 217:    */     }
/* 218:257 */     if (!renderer.hasOverrideBlockTexture()) {
/* 219:258 */       texture = block.getIcon(5, side + type * 6);
/* 220:    */     }
/* 221:261 */     if (inventory)
/* 222:    */     {
/* 223:262 */       tessellator.startDrawingQuads();
/* 224:263 */       tessellator.setNormal(-0.894F, 0.445F, 0.0F);
/* 225:    */     }
/* 226:266 */     if (side != 5)
/* 227:    */     {
/* 228:267 */       float[] u = { 1.0F - bz, 1.0F - fz, 1.0F - hz, 1.0F - dz };
/* 229:268 */       float[] v = { 1.0F - by, 1.0F - fy, 1.0F - hy, 1.0F - dy };
/* 230:269 */       int rotation = calcRotation(5, side);
/* 231:270 */       tessellator.addVertexWithUV(x + bx, y + by, z + bz, getU(0, texture, rotation, u, v), getV(0, texture, rotation, u, v));
/* 232:271 */       tessellator.addVertexWithUV(x + fx, y + fy, z + fz, getU(1, texture, rotation, u, v), getV(1, texture, rotation, u, v));
/* 233:272 */       tessellator.addVertexWithUV(x + hx, y + hy, z + hz, getU(2, texture, rotation, u, v), getV(2, texture, rotation, u, v));
/* 234:273 */       tessellator.addVertexWithUV(x + dx, y + dy, z + dz, getU(3, texture, rotation, u, v), getV(3, texture, rotation, u, v));
/* 235:    */     }
/* 236:276 */     if (inventory) {
/* 237:277 */       tessellator.draw();
/* 238:    */     }
/* 239:280 */     return true;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public float getU(int i, IIcon texture, int rotation, float[] u, float[] v)
/* 243:    */   {
/* 244:284 */     switch (rotation % 4)
/* 245:    */     {
/* 246:    */     case 0: 
/* 247:286 */       return texture.getInterpolatedU(u[(i % 4)] * 16.0F);
/* 248:    */     case 1: 
/* 249:289 */       return texture.getInterpolatedU(v[(i % 4)] * 16.0F);
/* 250:    */     case 2: 
/* 251:292 */       return texture.getInterpolatedU(16.0F - u[(i % 4)] * 16.0F);
/* 252:    */     case 3: 
/* 253:295 */       return texture.getInterpolatedU(16.0F - v[(i % 4)] * 16.0F);
/* 254:    */     }
/* 255:298 */     return 0.0F;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public float getV(int i, IIcon texture, int rotation, float[] u, float[] v)
/* 259:    */   {
/* 260:303 */     switch (rotation % 4)
/* 261:    */     {
/* 262:    */     case 0: 
/* 263:305 */       return texture.getInterpolatedV(v[(i % 4)] * 16.0F);
/* 264:    */     case 1: 
/* 265:308 */       return texture.getInterpolatedV(16.0F - u[(i % 4)] * 16.0F);
/* 266:    */     case 2: 
/* 267:311 */       return texture.getInterpolatedV(16.0F - v[(i % 4)] * 16.0F);
/* 268:    */     case 3: 
/* 269:314 */       return texture.getInterpolatedV(u[(i % 4)] * 16.0F);
/* 270:    */     }
/* 271:317 */     return 0.0F;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public int calcRotation(int side, int direction)
/* 275:    */   {
/* 276:322 */     if (side == direction) {
/* 277:323 */       return 0;
/* 278:    */     }
/* 279:326 */     if (side == net.minecraft.util.Facing.oppositeSide[direction]) {
/* 280:327 */       return 0;
/* 281:    */     }
/* 282:330 */     if (direction == 1) {
/* 283:331 */       return 0;
/* 284:    */     }
/* 285:334 */     if (direction == 0) {
/* 286:335 */       return 2;
/* 287:    */     }
/* 288:338 */     if ((side == 0) || (side == 1)) {
/* 289:339 */       return new int[] { 0, 2, 3, 1 }[(direction - 2)];
/* 290:    */     }
/* 291:342 */     return 1 + (side + direction + direction / 2) % 2 * 2;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
/* 295:    */   {
/* 296:347 */     int side = world.getBlockMetadata(x, y, z) % 6;
/* 297:348 */     int type = (world.getBlockMetadata(x, y, z) - side) / 6;
/* 298:349 */     int brightness = block.getBlockBrightness(world, x, y, z);
/* 299:350 */     return renderSpikeBlock(world, x, y, z, side, type, block, renderer, brightness);
/* 300:    */   }
/* 301:    */   
/* 302:    */   public boolean shouldRender3DInInventory(int modelId)
/* 303:    */   {
/* 304:355 */     return true;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public int getRenderId()
/* 308:    */   {
/* 309:360 */     return ExtraUtilsProxy.spikeBlockID;
/* 310:    */   }
/* 311:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.render.RenderBlockSpike
 * JD-Core Version:    0.7.0.1
 */