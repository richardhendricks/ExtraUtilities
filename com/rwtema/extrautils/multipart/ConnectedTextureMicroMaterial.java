/*   1:    */ package com.rwtema.extrautils.multipart;
/*   2:    */ 
/*   3:    */ import codechicken.lib.lighting.LC;
/*   4:    */ import codechicken.lib.render.BlockRenderer.BlockFace;
/*   5:    */ import codechicken.lib.render.CCRenderState;
/*   6:    */ import codechicken.lib.render.Vertex5;
/*   7:    */ import codechicken.lib.render.uv.MultiIconTransformation;
/*   8:    */ import codechicken.lib.render.uv.UV;
/*   9:    */ import codechicken.lib.vec.BlockCoord;
/*  10:    */ import codechicken.lib.vec.Cuboid6;
/*  11:    */ import codechicken.lib.vec.Vector3;
/*  12:    */ import codechicken.microblock.BlockMicroMaterial;
/*  13:    */ import codechicken.microblock.CommonMicroblock;
/*  14:    */ import codechicken.microblock.HollowMicroblockClient;
/*  15:    */ import codechicken.microblock.MicroMaterialRegistry;
/*  16:    */ import codechicken.multipart.JNormalOcclusion;
/*  17:    */ import codechicken.multipart.TMultiPart;
/*  18:    */ import codechicken.multipart.TileMultipart;
/*  19:    */ import com.rwtema.extrautils.block.BlockDecoration;
/*  20:    */ import com.rwtema.extrautils.block.IconConnectedTexture;
/*  21:    */ import com.rwtema.extrautils.block.render.FakeRenderBlocks;
/*  22:    */ import com.rwtema.extrautils.block.render.RenderBlockConnectedTextures;
/*  23:    */ import com.rwtema.extrautils.helper.XUHelperClient;
/*  24:    */ import cpw.mods.fml.relauncher.Side;
/*  25:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  26:    */ import java.util.List;
/*  27:    */ import net.minecraft.block.Block;
/*  28:    */ import net.minecraft.entity.player.EntityPlayer;
/*  29:    */ import net.minecraft.tileentity.TileEntity;
/*  30:    */ import net.minecraft.util.IIcon;
/*  31:    */ import net.minecraft.world.World;
/*  32:    */ import scala.collection.Seq;
/*  33:    */ 
/*  34:    */ public class ConnectedTextureMicroMaterial
/*  35:    */   extends BlockMicroMaterial
/*  36:    */ {
/*  37: 32 */   public static final double[] u = { -1.0D, 1.0D, 1.0D, -1.0D };
/*  38: 33 */   public static final double[] v = { 1.0D, 1.0D, -1.0D, -1.0D };
/*  39: 34 */   public boolean isGlass = true;
/*  40:    */   public boolean[] isConnected;
/*  41:    */   public boolean hasConnected;
/*  42:    */   private int pass;
/*  43:    */   private World world;
/*  44:    */   
/*  45:    */   public ConnectedTextureMicroMaterial(Block block, int meta)
/*  46:    */   {
/*  47: 41 */     super(block, meta);
/*  48: 43 */     if ((block instanceof BlockDecoration))
/*  49:    */     {
/*  50: 44 */       this.isGlass = (((BlockDecoration)block).opaque[meta()] == 0);
/*  51: 45 */       this.isConnected = ((BlockDecoration)block).ctexture[meta()];
/*  52: 46 */       for (boolean b : this.isConnected) {
/*  53: 47 */         if (b)
/*  54:    */         {
/*  55: 48 */           this.hasConnected = true;
/*  56: 49 */           break;
/*  57:    */         }
/*  58:    */       }
/*  59:    */     }
/*  60:    */     else
/*  61:    */     {
/*  62: 52 */       this.isGlass = (!block.isOpaqueCube());
/*  63: 53 */       this.isConnected = new boolean[6];
/*  64: 54 */       this.hasConnected = false;
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getLightValue()
/*  69:    */   {
/*  70: 61 */     if ((block() instanceof BlockDecoration)) {
/*  71: 62 */       return ((BlockDecoration)block()).light[meta()];
/*  72:    */     }
/*  73: 65 */     return 0;
/*  74:    */   }
/*  75:    */   
/*  76: 68 */   int[] cols = { 269488383, -1, 269549567, 285151487, -15724289, -15663105, -61185 };
/*  77:    */   @SideOnly(Side.CLIENT)
/*  78:    */   IconConnectedTexture resetIcons;
/*  79:    */   
/*  80:    */   @SideOnly(Side.CLIENT)
/*  81:    */   public TMultiPart getPart(Vector3 pos, Cuboid6 bounds)
/*  82:    */   {
/*  83: 76 */     World world = XUHelperClient.clientWorld();
/*  84: 77 */     TileMultipart t = TileMultipart.getOrConvertTile(world, new BlockCoord(pos));
/*  85: 78 */     if (t == null) {
/*  86: 79 */       return null;
/*  87:    */     }
/*  88: 81 */     for (java.util.Iterator i$ = t.jPartList().iterator(); i$.hasNext();)
/*  89:    */     {
/*  90: 81 */       part = (TMultiPart)i$.next();
/*  91: 82 */       if ((part instanceof JNormalOcclusion)) {
/*  92: 83 */         for (Cuboid6 bound : ((JNormalOcclusion)part).getOcclusionBoxes()) {
/*  93: 84 */           if (bound.intersects(bounds)) {
/*  94: 85 */             return part;
/*  95:    */           }
/*  96:    */         }
/*  97:    */       }
/*  98:    */     }
/*  99:    */     TMultiPart part;
/* 100: 90 */     return null;
/* 101:    */   }
/* 102:    */   
/* 103:    */   @SideOnly(Side.CLIENT)
/* 104:    */   public void renderMicroFace(Vector3 pos, int pass, Cuboid6 b)
/* 105:    */   {
/* 106: 98 */     if (!CCRenderState.model.getClass().equals(BlockRenderer.BlockFace.class))
/* 107:    */     {
/* 108: 99 */       super.renderMicroFace(pos, pass, b);
/* 109:100 */       return;
/* 110:    */     }
/* 111:103 */     Cuboid6 bounds = b.copy();
/* 112:    */     
/* 113:105 */     Cuboid6 renderBounds = bounds.copy();
/* 114:    */     
/* 115:107 */     TMultiPart part = getPart(pos, bounds);
/* 116:109 */     if (pass >= 0)
/* 117:    */     {
/* 118:110 */       boolean isHollow = part instanceof HollowMicroblockClient;
/* 119:111 */       int s = ((BlockRenderer.BlockFace)CCRenderState.model).side;
/* 120:113 */       if (isHollow) {
/* 121:114 */         for (Cuboid6 b2 : ((JNormalOcclusion)part).getOcclusionBoxes()) {
/* 122:115 */           bounds.enclose(b2);
/* 123:    */         }
/* 124:    */       }
/* 125:119 */       if (this.isGlass) {
/* 126:120 */         glassChange(bounds);
/* 127:    */       }
/* 128:    */     }
/* 129:124 */     if ((!this.hasConnected) || (!renderConnected(pos, pass, bounds, renderBounds))) {
/* 130:125 */       super.renderMicroFace(pos, pass, bounds);
/* 131:    */     }
/* 132:128 */     if (this.resetIcons != null) {
/* 133:129 */       this.resetIcons.resetType();
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   @SideOnly(Side.CLIENT)
/* 138:    */   public void glassChange(Cuboid6 c)
/* 139:    */   {
/* 140:135 */     BlockRenderer.BlockFace face = (BlockRenderer.BlockFace)CCRenderState.model;
/* 141:136 */     int side = face.side;
/* 142:    */     
/* 143:138 */     double x1 = c.min.x;double x2 = c.max.x;
/* 144:139 */     double y1 = c.min.y;double y2 = c.max.y;
/* 145:140 */     double z1 = c.min.z;double z2 = c.max.z;
/* 146:    */     double u1;
/* 147:    */     double u2;
/* 148:    */     double v1;
/* 149:    */     double v2;
/* 150:143 */     switch (side)
/* 151:    */     {
/* 152:    */     case 0: 
/* 153:145 */       u1 = x1;
/* 154:146 */       u2 = x2;
/* 155:147 */       v1 = z1;
/* 156:148 */       v2 = z2;
/* 157:149 */       break;
/* 158:    */     case 1: 
/* 159:151 */       u1 = x1;
/* 160:152 */       u2 = x2;
/* 161:153 */       v1 = z1;
/* 162:154 */       v2 = z2;
/* 163:155 */       break;
/* 164:    */     case 2: 
/* 165:157 */       u1 = 1.0D - x2;
/* 166:158 */       u2 = 1.0D - x1;
/* 167:159 */       v1 = 1.0D - y2;
/* 168:160 */       v2 = 1.0D - y1;
/* 169:161 */       break;
/* 170:    */     case 3: 
/* 171:163 */       u1 = x1;
/* 172:164 */       u2 = x2;
/* 173:165 */       v1 = 1.0D - y2;
/* 174:166 */       v2 = 1.0D - y1;
/* 175:167 */       break;
/* 176:    */     case 4: 
/* 177:169 */       u1 = z1;
/* 178:170 */       v1 = 1.0D - y2;
/* 179:171 */       u2 = z2;
/* 180:172 */       v2 = 1.0D - y1;
/* 181:173 */       break;
/* 182:    */     case 5: 
/* 183:175 */       u1 = 1.0D - z2;
/* 184:176 */       u2 = 1.0D - z1;
/* 185:177 */       v1 = 1.0D - y2;
/* 186:178 */       v2 = 1.0D - y1;
/* 187:179 */       break;
/* 188:    */     default: 
/* 189:181 */       return;
/* 190:    */     }
/* 191:184 */     if ((v1 == v2) || (u1 == u2)) {
/* 192:185 */       return;
/* 193:    */     }
/* 194:187 */     for (Vertex5 v : face.verts)
/* 195:    */     {
/* 196:188 */       v.uv.u = ((v.uv.u - u1) / (u2 - u1));
/* 197:189 */       v.uv.v = ((v.uv.v - v1) / (v2 - v1));
/* 198:    */     }
/* 199:192 */     face.lcComputed = false;
/* 200:193 */     face.computeLightCoords();
/* 201:    */   }
/* 202:    */   
/* 203:    */   public boolean isInt(double t)
/* 204:    */   {
/* 205:198 */     return t == (int)t;
/* 206:    */   }
/* 207:    */   
/* 208:    */   @SideOnly(Side.CLIENT)
/* 209:    */   public boolean renderConnected(Vector3 pos, int pass, Cuboid6 bounds, Cuboid6 renderBounds)
/* 210:    */   {
/* 211:203 */     this.pass = pass;
/* 212:204 */     if (pass < 0) {
/* 213:205 */       return false;
/* 214:    */     }
/* 215:208 */     if ((!isInt(pos.x)) || (!isInt(pos.y)) || (!isInt(pos.z))) {
/* 216:209 */       return false;
/* 217:    */     }
/* 218:211 */     if (!CCRenderState.model.getClass().equals(BlockRenderer.BlockFace.class)) {
/* 219:212 */       return false;
/* 220:    */     }
/* 221:214 */     if (!isFlush(bounds)) {
/* 222:215 */       return false;
/* 223:    */     }
/* 224:217 */     int s = getSideFromBounds(bounds);
/* 225:218 */     BlockRenderer.BlockFace face = (BlockRenderer.BlockFace)CCRenderState.model;
/* 226:219 */     int side = face.side;
/* 227:221 */     if (this.isConnected[side] == 0) {
/* 228:222 */       return false;
/* 229:    */     }
/* 230:224 */     if (s == -1) {
/* 231:225 */       s = side;
/* 232:    */     }
/* 233:229 */     IIcon icon = icont().icons[side];
/* 234:231 */     if (!(icon instanceof IconConnectedTexture)) {
/* 235:232 */       return false;
/* 236:    */     }
/* 237:234 */     this.world = XUHelperClient.clientPlayer().getEntityWorld();
/* 238:236 */     if (s == side)
/* 239:    */     {
/* 240:238 */       int c = getColour(pass);
/* 241:240 */       if (this.isGlass)
/* 242:    */       {
/* 243:241 */         double h = 0.001D;
/* 244:243 */         switch (s)
/* 245:    */         {
/* 246:    */         case 0: 
/* 247:245 */           renderHalfSide(block(), 0.5D, h, 0.5D, 1, 0, 0, 0, 0, -1, (IconConnectedTexture)icon, bounds, side, pos, renderBounds);
/* 248:246 */           break;
/* 249:    */         case 1: 
/* 250:249 */           renderHalfSide(block(), 0.5D, 1.0D - h, 0.5D, -1, 0, 0, 0, 0, -1, (IconConnectedTexture)icon, bounds, side, pos, renderBounds);
/* 251:250 */           break;
/* 252:    */         case 2: 
/* 253:253 */           renderHalfSide(block(), 0.5D, 0.5D, h, 1, 0, 0, 0, 1, 0, (IconConnectedTexture)icon, bounds, side, pos, renderBounds);
/* 254:254 */           break;
/* 255:    */         case 3: 
/* 256:257 */           renderHalfSide(block(), 0.5D, 0.5D, 1.0D - h, -1, 0, 0, 0, 1, 0, (IconConnectedTexture)icon, bounds, side, pos, renderBounds);
/* 257:258 */           break;
/* 258:    */         case 4: 
/* 259:261 */           renderHalfSide(block(), h, 0.5D, 0.5D, 0, 0, -1, 0, 1, 0, (IconConnectedTexture)icon, bounds, side, pos, renderBounds);
/* 260:262 */           break;
/* 261:    */         case 5: 
/* 262:265 */           renderHalfSide(block(), 1.0D - h, 0.5D, 0.5D, 0, 0, 1, 0, 1, 0, (IconConnectedTexture)icon, bounds, side, pos, renderBounds);
/* 263:    */         }
/* 264:    */       }
/* 265:    */       else
/* 266:    */       {
/* 267:269 */         FakeRenderBlocks fr = RenderBlockConnectedTextures.fakeRender;
/* 268:270 */         fr.setWorld(this.world);
/* 269:    */         
/* 270:272 */         fr.curBlock = block();
/* 271:273 */         fr.curMeta = meta();
/* 272:275 */         switch (s)
/* 273:    */         {
/* 274:    */         case 0: 
/* 275:277 */           renderSide(block(), 0.5D, 0.0D, 0.5D, 1, 0, 0, 0, 0, -1, (IconConnectedTexture)icon, side, pos, c, icont(), renderBounds, bounds);
/* 276:278 */           break;
/* 277:    */         case 1: 
/* 278:281 */           renderSide(block(), 0.5D, 1.0D, 0.5D, -1, 0, 0, 0, 0, -1, (IconConnectedTexture)icon, side, pos, c, icont(), renderBounds, bounds);
/* 279:282 */           break;
/* 280:    */         case 2: 
/* 281:285 */           renderSide(block(), 0.5D, 0.5D, 0.0D, 1, 0, 0, 0, 1, 0, (IconConnectedTexture)icon, side, pos, c, icont(), renderBounds, bounds);
/* 282:286 */           break;
/* 283:    */         case 3: 
/* 284:289 */           renderSide(block(), 0.5D, 0.5D, 1.0D, -1, 0, 0, 0, 1, 0, (IconConnectedTexture)icon, side, pos, c, icont(), renderBounds, bounds);
/* 285:290 */           break;
/* 286:    */         case 4: 
/* 287:293 */           renderSide(block(), 0.0D, 0.5D, 0.5D, 0, 0, -1, 0, 1, 0, (IconConnectedTexture)icon, side, pos, c, icont(), renderBounds, bounds);
/* 288:294 */           break;
/* 289:    */         case 5: 
/* 290:297 */           renderSide(block(), 1.0D, 0.5D, 0.5D, 0, 0, 1, 0, 1, 0, (IconConnectedTexture)icon, side, pos, c, icont(), renderBounds, bounds);
/* 291:    */         }
/* 292:    */       }
/* 293:301 */       return true;
/* 294:    */     }
/* 295:302 */     if (side == net.minecraft.util.Facing.oppositeSide[s])
/* 296:    */     {
/* 297:303 */       double h = sideSize(bounds);
/* 298:305 */       switch (net.minecraft.util.Facing.oppositeSide[s])
/* 299:    */       {
/* 300:    */       case 0: 
/* 301:307 */         renderHalfSide(block(), 0.5D, h, 0.5D, 1, 0, 0, 0, 0, -1, (IconConnectedTexture)icon, bounds, side, pos, renderBounds);
/* 302:308 */         break;
/* 303:    */       case 1: 
/* 304:311 */         renderHalfSide(block(), 0.5D, h, 0.5D, -1, 0, 0, 0, 0, -1, (IconConnectedTexture)icon, bounds, side, pos, renderBounds);
/* 305:312 */         break;
/* 306:    */       case 2: 
/* 307:315 */         renderHalfSide(block(), 0.5D, 0.5D, h, 1, 0, 0, 0, 1, 0, (IconConnectedTexture)icon, bounds, side, pos, renderBounds);
/* 308:316 */         break;
/* 309:    */       case 3: 
/* 310:319 */         renderHalfSide(block(), 0.5D, 0.5D, h, -1, 0, 0, 0, 1, 0, (IconConnectedTexture)icon, bounds, side, pos, renderBounds);
/* 311:320 */         break;
/* 312:    */       case 4: 
/* 313:323 */         renderHalfSide(block(), h, 0.5D, 0.5D, 0, 0, -1, 0, 1, 0, (IconConnectedTexture)icon, bounds, side, pos, renderBounds);
/* 314:324 */         break;
/* 315:    */       case 5: 
/* 316:327 */         renderHalfSide(block(), h, 0.5D, 0.5D, 0, 0, 1, 0, 1, 0, (IconConnectedTexture)icon, bounds, side, pos, renderBounds);
/* 317:    */       }
/* 318:330 */       return true;
/* 319:    */     }
/* 320:331 */     if (this.isGlass)
/* 321:    */     {
/* 322:332 */       double d = renderBounds.getSide(side);
/* 323:333 */       return ((d == 0.0D) || (d == 1.0D)) && (hasMatchingPart(bounds, (int)pos.x + net.minecraft.util.Facing.offsetsXForSide[side], (int)pos.y + net.minecraft.util.Facing.offsetsYForSide[side], (int)pos.z + net.minecraft.util.Facing.offsetsZForSide[side]));
/* 324:    */     }
/* 325:336 */     return false;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public double dist(Vector3 a, Vector3 b)
/* 329:    */   {
/* 330:340 */     return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y) + (a.z - b.z) * (a.z - b.z));
/* 331:    */   }
/* 332:    */   
/* 333:    */   public boolean isFlush(Cuboid6 bounds)
/* 334:    */   {
/* 335:345 */     int i = 0;
/* 336:346 */     if (bounds.max.y != 1.0D) {
/* 337:346 */       i++;
/* 338:    */     }
/* 339:347 */     if (bounds.min.y != 0.0D) {
/* 340:347 */       i++;
/* 341:    */     }
/* 342:348 */     if (bounds.max.z != 1.0D) {
/* 343:348 */       i++;
/* 344:    */     }
/* 345:349 */     if (bounds.min.z != 0.0D) {
/* 346:349 */       i++;
/* 347:    */     }
/* 348:350 */     if (bounds.max.x != 1.0D) {
/* 349:350 */       i++;
/* 350:    */     }
/* 351:351 */     if (bounds.min.x != 0.0D) {
/* 352:351 */       i++;
/* 353:    */     }
/* 354:353 */     return i <= 1;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public double sideSize(Cuboid6 bounds)
/* 358:    */   {
/* 359:357 */     if (bounds.max.y != 1.0D) {
/* 360:358 */       return bounds.max.y;
/* 361:    */     }
/* 362:360 */     if (bounds.min.y != 0.0D) {
/* 363:361 */       return bounds.min.y;
/* 364:    */     }
/* 365:363 */     if (bounds.max.z != 1.0D) {
/* 366:364 */       return bounds.max.z;
/* 367:    */     }
/* 368:366 */     if (bounds.min.z != 0.0D) {
/* 369:367 */       return bounds.min.z;
/* 370:    */     }
/* 371:369 */     if (bounds.max.x != 1.0D) {
/* 372:370 */       return bounds.max.x;
/* 373:    */     }
/* 374:372 */     if (bounds.min.x != 0.0D) {
/* 375:373 */       return bounds.min.x;
/* 376:    */     }
/* 377:375 */     return 0.0D;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public int getSideFromBounds(Cuboid6 bounds)
/* 381:    */   {
/* 382:380 */     if (bounds.max.y != 1.0D) {
/* 383:381 */       return 0;
/* 384:    */     }
/* 385:384 */     if (bounds.min.y != 0.0D) {
/* 386:385 */       return 1;
/* 387:    */     }
/* 388:388 */     if (bounds.max.z != 1.0D) {
/* 389:389 */       return 2;
/* 390:    */     }
/* 391:392 */     if (bounds.min.z != 0.0D) {
/* 392:393 */       return 3;
/* 393:    */     }
/* 394:396 */     if (bounds.max.x != 1.0D) {
/* 395:397 */       return 4;
/* 396:    */     }
/* 397:400 */     if (bounds.min.x != 0.0D) {
/* 398:401 */       return 5;
/* 399:    */     }
/* 400:404 */     return -1;
/* 401:    */   }
/* 402:    */   
/* 403:    */   public boolean isTransparent()
/* 404:    */   {
/* 405:410 */     return this.isGlass;
/* 406:    */   }
/* 407:    */   
/* 408:    */   @SideOnly(Side.CLIENT)
/* 409:    */   public void renderSide(Block block, double ox, double oy, double oz, int ax, int ay, int az, int bx, int by, int bz, IconConnectedTexture icon, int side, Vector3 pos, int colour, MultiIconTransformation icont, Cuboid6 renderBounds, Cuboid6 bounds)
/* 410:    */   {
/* 411:415 */     int[] tex = new int[4];
/* 412:416 */     boolean isDifferent = false;
/* 413:418 */     for (int j = 0; j < 4; j++)
/* 414:    */     {
/* 415:419 */       RenderBlockConnectedTextures.fakeRender.isOpaque = (!this.isGlass);
/* 416:420 */       tex[j] = RenderBlockConnectedTextures.fakeRender.getType(block, side, (int)pos.x, (int)pos.y, (int)pos.z, ax * (int)u[j], ay * (int)u[j], az * (int)u[j], bx * (int)v[j], by * (int)v[j], bz * (int)v[j], (int)(ox * 2.0D - 1.0D), (int)(oy * 2.0D - 1.0D), (int)(oz * 2.0D - 1.0D));
/* 417:423 */       if (tex[j] != tex[0]) {
/* 418:424 */         isDifferent = true;
/* 419:    */       }
/* 420:    */     }
/* 421:427 */     BlockRenderer.BlockFace face = (BlockRenderer.BlockFace)CCRenderState.model;
/* 422:    */     
/* 423:429 */     face.lcComputed = false;
/* 424:431 */     if (isDifferent)
/* 425:    */     {
/* 426:432 */       for (int j = 0; j < 4; j++)
/* 427:    */       {
/* 428:433 */         double cx = ox + ax * u[j] / 4.0D + bx * v[j] / 4.0D;
/* 429:434 */         double cy = oy + ay * u[j] / 4.0D + by * v[j] / 4.0D;
/* 430:435 */         double cz = oz + az * u[j] / 4.0D + bz * v[j] / 4.0D;
/* 431:    */         
/* 432:437 */         Cuboid6 b = new Cuboid6(cx, cy, cz, cx, cy, cz);
/* 433:    */         
/* 434:439 */         b.setSide(side, bounds.getSide(side));
/* 435:440 */         b.setSide(net.minecraft.util.Facing.oppositeSide[side], bounds.getSide(net.minecraft.util.Facing.oppositeSide[side]));
/* 436:442 */         for (int k = 0; k < 4; k++) {
/* 437:443 */           expandToInclude(b, new Vector3(cx + u[k] * ax * 0.25D + v[k] * bx * 0.25D, cy + u[k] * ay * 0.25D + v[k] * by * 0.25D, cz + u[k] * az * 0.25D + v[k] * bz * 0.25D));
/* 438:    */         }
/* 439:449 */         b = shrinkToEnclose(b.copy(), renderBounds);
/* 440:451 */         if (!isEmpty(b))
/* 441:    */         {
/* 442:454 */           if (this.isGlass)
/* 443:    */           {
/* 444:455 */             for (int i = 0; i < 4; i++) {
/* 445:456 */               face.lightCoords[i].computeO(face.verts[i].vec, side);
/* 446:    */             }
/* 447:457 */             face.lcComputed = true;
/* 448:    */           }
/* 449:    */           else
/* 450:    */           {
/* 451:459 */             face.lcComputed = false;
/* 452:    */           }
/* 453:461 */           for (IIcon ic : icont().icons) {
/* 454:462 */             if ((ic instanceof IconConnectedTexture)) {
/* 455:463 */               ((IconConnectedTexture)ic).setType(tex[j]);
/* 456:    */             }
/* 457:    */           }
/* 458:466 */           face.loadCuboidFace(b, side);
/* 459:    */           
/* 460:468 */           super.renderMicroFace(pos, this.pass, b);
/* 461:470 */           for (IIcon ic : icont().icons) {
/* 462:471 */             if ((ic instanceof IconConnectedTexture)) {
/* 463:472 */               ((IconConnectedTexture)ic).resetType();
/* 464:    */             }
/* 465:    */           }
/* 466:    */         }
/* 467:    */       }
/* 468:    */     }
/* 469:    */     else
/* 470:    */     {
/* 471:476 */       bounds = shrinkToEnclose(bounds.copy(), renderBounds);
/* 472:477 */       if (isEmpty(bounds)) {
/* 473:478 */         return;
/* 474:    */       }
/* 475:480 */       face.loadCuboidFace(bounds, side);
/* 476:482 */       for (IIcon ic : icont().icons) {
/* 477:483 */         if ((ic instanceof IconConnectedTexture)) {
/* 478:484 */           ((IconConnectedTexture)ic).setType(tex[0]);
/* 479:    */         }
/* 480:    */       }
/* 481:487 */       super.renderMicroFace(pos, this.pass, bounds);
/* 482:488 */       for (IIcon ic : icont().icons) {
/* 483:489 */         if ((ic instanceof IconConnectedTexture)) {
/* 484:490 */           ((IconConnectedTexture)ic).resetType();
/* 485:    */         }
/* 486:    */       }
/* 487:    */     }
/* 488:    */   }
/* 489:    */   
/* 490:    */   private double interp(double v)
/* 491:    */   {
/* 492:497 */     return v / 16.0D;
/* 493:    */   }
/* 494:    */   
/* 495:    */   @SideOnly(Side.CLIENT)
/* 496:    */   public boolean hasMatchingPart(Cuboid6 part, int x, int y, int z)
/* 497:    */   {
/* 498:502 */     TileEntity tile_base = this.world.getTileEntity(x, y, z);
/* 499:504 */     if ((tile_base != null) && ((tile_base instanceof TileMultipart)))
/* 500:    */     {
/* 501:505 */       scala.collection.Iterator<TMultiPart> parts = ((TileMultipart)tile_base).partList().toIterator();
/* 502:507 */       while (parts.hasNext())
/* 503:    */       {
/* 504:508 */         TMultiPart p = (TMultiPart)parts.next();
/* 505:510 */         if ((p instanceof CommonMicroblock))
/* 506:    */         {
/* 507:511 */           CommonMicroblock mat = (CommonMicroblock)p;
/* 508:512 */           if (equalCubes(mat.getBounds(), part))
/* 509:    */           {
/* 510:513 */             int material = mat.getMaterial();
/* 511:514 */             if (MicroMaterialRegistry.getMaterial(material) == this) {
/* 512:515 */               return true;
/* 513:    */             }
/* 514:    */           }
/* 515:    */         }
/* 516:    */       }
/* 517:    */     }
/* 518:522 */     return false;
/* 519:    */   }
/* 520:    */   
/* 521:    */   public boolean equalCubes(Cuboid6 a, Cuboid6 b)
/* 522:    */   {
/* 523:526 */     return (getSideFromBounds(a) == getSideFromBounds(b)) && (sideSize(a) == sideSize(b));
/* 524:    */   }
/* 525:    */   
/* 526:    */   @SideOnly(Side.CLIENT)
/* 527:    */   public int getHalfType(Block block, int side, int x, int y, int z, int ax, int ay, int az, int bx, int by, int bz, Cuboid6 part)
/* 528:    */   {
/* 529:532 */     boolean a = hasMatchingPart(part, x + ax, y + ay, z + az);
/* 530:533 */     boolean b = hasMatchingPart(part, x + bx, y + by, z + bz);
/* 531:535 */     if (a)
/* 532:    */     {
/* 533:536 */       if (b)
/* 534:    */       {
/* 535:537 */         if (hasMatchingPart(part, x + ax + bx, y + ay + by, z + az + bz)) {
/* 536:538 */           return 3;
/* 537:    */         }
/* 538:540 */         return 4;
/* 539:    */       }
/* 540:543 */       return 2;
/* 541:    */     }
/* 542:546 */     if (b) {
/* 543:547 */       return 1;
/* 544:    */     }
/* 545:549 */     return 0;
/* 546:    */   }
/* 547:    */   
/* 548:    */   public Cuboid6 getBounds(Cuboid6 b, int side)
/* 549:    */   {
/* 550:556 */     return b;
/* 551:    */   }
/* 552:    */   
/* 553:    */   public Cuboid6 expandToInclude(Cuboid6 a, Vector3 v)
/* 554:    */   {
/* 555:560 */     if (a.min.x > v.x) {
/* 556:560 */       a.min.x = v.x;
/* 557:    */     }
/* 558:561 */     if (a.min.y > v.y) {
/* 559:561 */       a.min.y = v.y;
/* 560:    */     }
/* 561:562 */     if (a.min.z > v.z) {
/* 562:562 */       a.min.z = v.z;
/* 563:    */     }
/* 564:563 */     if (a.max.y < v.y) {
/* 565:563 */       a.max.y = v.y;
/* 566:    */     }
/* 567:564 */     if (a.max.x < v.x) {
/* 568:564 */       a.max.x = v.x;
/* 569:    */     }
/* 570:565 */     if (a.max.z < v.z) {
/* 571:565 */       a.max.z = v.z;
/* 572:    */     }
/* 573:567 */     return a;
/* 574:    */   }
/* 575:    */   
/* 576:    */   public boolean isEmpty(Cuboid6 a)
/* 577:    */   {
/* 578:571 */     return (a.min.x >= a.max.x) || (a.min.y >= a.max.y) || (a.min.z >= a.max.z);
/* 579:    */   }
/* 580:    */   
/* 581:    */   public Cuboid6 shrinkToEnclose(Cuboid6 a, Cuboid6 c)
/* 582:    */   {
/* 583:575 */     if (a.min.x < c.min.x) {
/* 584:575 */       a.min.x = c.min.x;
/* 585:    */     }
/* 586:576 */     if (a.min.y < c.min.y) {
/* 587:576 */       a.min.y = c.min.y;
/* 588:    */     }
/* 589:577 */     if (a.min.z < c.min.z) {
/* 590:577 */       a.min.z = c.min.z;
/* 591:    */     }
/* 592:578 */     if (a.max.x > c.max.x) {
/* 593:578 */       a.max.x = c.max.x;
/* 594:    */     }
/* 595:579 */     if (a.max.y > c.max.y) {
/* 596:579 */       a.max.y = c.max.y;
/* 597:    */     }
/* 598:580 */     if (a.max.z > c.max.z) {
/* 599:580 */       a.max.z = c.max.z;
/* 600:    */     }
/* 601:581 */     return a;
/* 602:    */   }
/* 603:    */   
/* 604:    */   @SideOnly(Side.CLIENT)
/* 605:    */   public void renderHalfSide(Block block, double ox, double oy, double oz, int ax, int ay, int az, int bx, int by, int bz, IconConnectedTexture icon, Cuboid6 bounds, int side, Vector3 pos, Cuboid6 renderBounds)
/* 606:    */   {
/* 607:586 */     int[] tex = new int[4];
/* 608:587 */     boolean isDifferent = false;
/* 609:    */     
/* 610:589 */     int s = net.minecraft.util.Facing.oppositeSide[getSideFromBounds(bounds)];
/* 611:591 */     for (int j = 0; j < 4; j++)
/* 612:    */     {
/* 613:592 */       RenderBlockConnectedTextures.fakeRender.isOpaque = (!this.isGlass);
/* 614:593 */       tex[j] = getHalfType(block, side, (int)pos.x, (int)pos.y, (int)pos.z, ax * (int)u[j], ay * (int)u[j], az * (int)u[j], bx * (int)v[j], by * (int)v[j], bz * (int)v[j], bounds);
/* 615:595 */       if (tex[j] != tex[0]) {
/* 616:596 */         isDifferent = true;
/* 617:    */       }
/* 618:    */     }
/* 619:599 */     BlockRenderer.BlockFace face = (BlockRenderer.BlockFace)CCRenderState.model;
/* 620:    */     
/* 621:601 */     face.lcComputed = false;
/* 622:603 */     if (isDifferent)
/* 623:    */     {
/* 624:604 */       s = s;
/* 625:605 */       for (int j = 0; j < 4; j++)
/* 626:    */       {
/* 627:606 */         double cx = ox + ax * u[j] / 4.0D + bx * v[j] / 4.0D;
/* 628:607 */         double cy = oy + ay * u[j] / 4.0D + by * v[j] / 4.0D;
/* 629:608 */         double cz = oz + az * u[j] / 4.0D + bz * v[j] / 4.0D;
/* 630:    */         
/* 631:610 */         Cuboid6 b = new Cuboid6(cx, cy, cz, cx, cy, cz);
/* 632:    */         
/* 633:612 */         b.setSide(face.side, bounds.getSide(face.side));
/* 634:613 */         b.setSide(net.minecraft.util.Facing.oppositeSide[face.side], bounds.getSide(net.minecraft.util.Facing.oppositeSide[face.side]));
/* 635:615 */         for (int k = 0; k < 4; k++) {
/* 636:616 */           expandToInclude(b, new Vector3(cx + u[k] * ax * 0.25D + v[k] * bx * 0.25D, cy + u[k] * ay * 0.25D + v[k] * by * 0.25D, cz + u[k] * az * 0.25D + v[k] * bz * 0.25D));
/* 637:    */         }
/* 638:622 */         b = shrinkToEnclose(b.copy(), renderBounds);
/* 639:624 */         if (!isEmpty(b))
/* 640:    */         {
/* 641:627 */           if (this.isGlass)
/* 642:    */           {
/* 643:628 */             for (int i = 0; i < 4; i++) {
/* 644:629 */               face.lightCoords[i].computeO(face.verts[i].vec, side);
/* 645:    */             }
/* 646:630 */             face.lcComputed = true;
/* 647:    */           }
/* 648:    */           else
/* 649:    */           {
/* 650:632 */             face.lcComputed = false;
/* 651:    */           }
/* 652:634 */           for (IIcon ic : icont().icons) {
/* 653:635 */             if ((ic instanceof IconConnectedTexture)) {
/* 654:636 */               ((IconConnectedTexture)ic).setType(tex[j]);
/* 655:    */             }
/* 656:    */           }
/* 657:639 */           face.loadCuboidFace(b, face.side);
/* 658:    */           
/* 659:641 */           super.renderMicroFace(pos, this.pass, b);
/* 660:643 */           for (IIcon ic : icont().icons) {
/* 661:644 */             if ((ic instanceof IconConnectedTexture)) {
/* 662:645 */               ((IconConnectedTexture)ic).resetType();
/* 663:    */             }
/* 664:    */           }
/* 665:    */         }
/* 666:    */       }
/* 667:    */     }
/* 668:    */     else
/* 669:    */     {
/* 670:649 */       bounds = shrinkToEnclose(bounds.copy(), renderBounds);
/* 671:650 */       if (isEmpty(bounds)) {
/* 672:651 */         return;
/* 673:    */       }
/* 674:653 */       face.loadCuboidFace(bounds, side);
/* 675:655 */       for (IIcon ic : icont().icons) {
/* 676:656 */         if ((ic instanceof IconConnectedTexture)) {
/* 677:657 */           ((IconConnectedTexture)ic).setType(tex[0]);
/* 678:    */         }
/* 679:    */       }
/* 680:660 */       super.renderMicroFace(pos, this.pass, bounds);
/* 681:661 */       for (IIcon ic : icont().icons) {
/* 682:662 */         if ((ic instanceof IconConnectedTexture)) {
/* 683:663 */           ((IconConnectedTexture)ic).resetType();
/* 684:    */         }
/* 685:    */       }
/* 686:    */     }
/* 687:    */   }
/* 688:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.multipart.ConnectedTextureMicroMaterial
 * JD-Core Version:    0.7.0.1
 */