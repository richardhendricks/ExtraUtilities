/*   1:    */ package com.rwtema.extrautils.worldgen.Underdark;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ChunkPos;
/*   4:    */ import com.rwtema.extrautils.ExtraUtils;
/*   5:    */ import com.rwtema.extrautils.LogHelper;
/*   6:    */ import com.rwtema.extrautils.block.BlockColorData;
/*   7:    */ import com.rwtema.extrautils.helper.XURandom;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Collections;
/*  10:    */ import java.util.Random;
/*  11:    */ import net.minecraft.block.Block;
/*  12:    */ import net.minecraft.init.Blocks;
/*  13:    */ import net.minecraft.item.ItemDoor;
/*  14:    */ import net.minecraft.tileentity.MobSpawnerBaseLogic;
/*  15:    */ import net.minecraft.tileentity.TileEntityChest;
/*  16:    */ import net.minecraft.tileentity.TileEntityMobSpawner;
/*  17:    */ import net.minecraft.util.WeightedRandomChestContent;
/*  18:    */ import net.minecraft.world.World;
/*  19:    */ import net.minecraft.world.chunk.Chunk;
/*  20:    */ import net.minecraft.world.gen.feature.WorldGenerator;
/*  21:    */ import net.minecraftforge.common.ChestGenHooks;
/*  22:    */ import net.minecraftforge.common.DungeonHooks;
/*  23:    */ 
/*  24:    */ public class WorldGenCastle
/*  25:    */   extends WorldGenerator
/*  26:    */ {
/*  27:    */   public static final int rad = 17;
/*  28:    */   private int[][] block;
/*  29: 26 */   public static final int[] dx = { -1, 0, 1, 0 };
/*  30: 27 */   public static final int[] dy = { 0, -1, 0, 1 };
/*  31:    */   public static final int d = 9;
/*  32: 29 */   public static String[] dungeons = { "dungeonChest", "strongholdCorridor", "strongholdLibrary", "pyramidDesertyChest", "pyramidJungleChest", "mineshaftCorridor", "villageBlacksmith", "strongholdCrossing", "dungeonChest", "dungeonChest", "dungeonChest", "dungeonChest" };
/*  33: 32 */   public static Random staticRand = XURandom.getInstance();
/*  34:    */   public long timer;
/*  35:    */   private int[] block_allocations;
/*  36:    */   private boolean colorbricks;
/*  37:    */   private boolean colorWoods;
/*  38:    */   private boolean lightGen;
/*  39:    */   private ArrayList<ChunkPos> torchPos;
/*  40:    */   
/*  41:    */   public WorldGenCastle()
/*  42:    */   {
/*  43: 25 */     this.block = new int[17][17];
/*  44:    */     
/*  45:    */ 
/*  46:    */ 
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51: 33 */     this.timer = 0L;
/*  52: 34 */     this.block_allocations = new int[16];
/*  53:    */     
/*  54:    */ 
/*  55: 37 */     this.torchPos = new ArrayList();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static void setMobSpawner(World world, int x, int y, int z, Random rand)
/*  59:    */   {
/*  60: 40 */     world.setBlock(x, y, z, Blocks.mob_spawner, 0, 2);
/*  61: 41 */     TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner)world.getTileEntity(x, y, z);
/*  62: 43 */     if (tileentitymobspawner != null) {
/*  63: 44 */       tileentitymobspawner.func_145881_a().setMobID(DungeonHooks.getRandomDungeonMob(rand));
/*  64:    */     } else {
/*  65: 46 */       LogHelper.error("Failed to fetch mob spawner entity at (" + x + ", " + y + ", " + z + ")", new Object[0]);
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int castleX(long seed, int x, int z)
/*  70:    */   {
/*  71: 51 */     staticRand.setSeed(seed + (x >> 9) + 65535 * (z >> 9));
/*  72: 52 */     return (x >> 9 << 9) + staticRand.nextInt(390) + 61;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int castleZ(long seed, int x, int z)
/*  76:    */   {
/*  77: 56 */     staticRand.setSeed(seed + (x >> 9) + 65535 * (z >> 9));
/*  78: 57 */     staticRand.nextInt(390);
/*  79: 58 */     return (z >> 9 << 9) + staticRand.nextInt(390) + 61;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean generate(World world, Random rand, int x, int y, int z)
/*  83:    */   {
/*  84: 63 */     int cx = castleX(world.getSeed(), x, z);
/*  85: 64 */     int cz = castleZ(world.getSeed(), x, z);
/*  86: 66 */     if ((cx >> 4 != x >> 4) || (cz >> 4 != z >> 4)) {
/*  87: 67 */       return false;
/*  88:    */     }
/*  89: 70 */     for (int ax = 0; ax < 17; ax++) {
/*  90: 71 */       for (int ay = 0; ay < 17; ay++) {
/*  91: 72 */         s(ax, ay, -1);
/*  92:    */       }
/*  93:    */     }
/*  94: 75 */     ArrayList<Vec2> initBlocks = new ArrayList();
/*  95: 76 */     initBlocks.add(new Vec2(8, 8));
/*  96: 78 */     for (int i = 0; (i < initBlocks.size()) && (initBlocks.size() < 68.0D); i++)
/*  97:    */     {
/*  98: 79 */       int ax = ((Vec2)initBlocks.get(i)).x;
/*  99: 80 */       int ay = ((Vec2)initBlocks.get(i)).y;
/* 100: 81 */       int dj = rand.nextInt(4);
/* 101: 83 */       for (int j = (dj + 1) % 4; j != dj; j = (j + 1) % 4) {
/* 102: 84 */         if (isValid(ax + dx[j], ay + dy[j]))
/* 103:    */         {
/* 104: 85 */           Vec2 t = new Vec2(ax + dx[j], ay + dy[j]);
/* 105: 87 */           if (!initBlocks.contains(t))
/* 106:    */           {
/* 107: 88 */             if (initBlocks.size() - 2 - i > 0) {
/* 108: 89 */               initBlocks.add(i + 1 + rand.nextInt(initBlocks.size() - 2 - i), t);
/* 109:    */             } else {
/* 110: 91 */               initBlocks.add(t);
/* 111:    */             }
/* 112: 94 */             s(ax + dx[j], ay + dy[j], 0);
/* 113: 95 */             s(16 - (ax + dx[j]), 16 - (ay + dy[j]), 0);
/* 114: 96 */             s(ax + dx[j], 16 - (ay + dy[j]), 0);
/* 115: 97 */             s(16 - (ax + dx[j]), ay + dy[j], 0);
/* 116:    */           }
/* 117:    */         }
/* 118:    */       }
/* 119:    */     }
/* 120:103 */     x = cx - 24;
/* 121:104 */     z = cz - 24;
/* 122:105 */     float r = 1.0F;float g = 1.0F;float b = 1.0F;
/* 123:106 */     this.colorbricks = (ExtraUtils.colorBlockBrick != null);
/* 124:107 */     this.colorWoods = (ExtraUtils.coloredWood != null);
/* 125:109 */     if (this.colorbricks)
/* 126:    */     {
/* 127:110 */       for (int i = 0; i < 16; i++)
/* 128:    */       {
/* 129:111 */         int j = rand.nextInt(1 + i);
/* 130:113 */         if (i != j) {
/* 131:114 */           this.block_allocations[i] = this.block_allocations[j];
/* 132:    */         }
/* 133:117 */         this.block_allocations[j] = i;
/* 134:    */       }
/* 135:120 */       float[][] cols = new float[16][3];
/* 136:121 */       r = g = b = 0.4F + 0.6F * rand.nextFloat();
/* 137:122 */       r *= (2.0F + rand.nextFloat()) / 3.0F;
/* 138:123 */       g *= (2.0F + rand.nextFloat()) / 3.0F;
/* 139:124 */       b *= (2.0F + rand.nextFloat()) / 3.0F;
/* 140:126 */       for (int i = 0; i < 16; i++)
/* 141:    */       {
/* 142:127 */         float br = (1.0F + rand.nextFloat()) / 2.0F;
/* 143:128 */         cols[i][0] = (r * br * (5.0F + rand.nextFloat()) / 6.0F);
/* 144:129 */         cols[i][1] = (g * br * (5.0F + rand.nextFloat()) / 6.0F);
/* 145:130 */         cols[i][2] = (b * br * (5.0F + rand.nextFloat()) / 6.0F);
/* 146:    */       }
/* 147:133 */       for (int dx = x - 16; dx <= x + 51 + 16; dx += 16) {
/* 148:134 */         for (int dz = z - 16; dz <= z + 51 + 16; dz += 16) {
/* 149:135 */           for (int i = 0; i < 16; i++) {
/* 150:136 */             BlockColorData.changeColorData(world, dx, y, dz, this.block_allocations[i], cols[i][0], cols[i][1], cols[i][2]);
/* 151:    */           }
/* 152:    */         }
/* 153:    */       }
/* 154:    */     }
/* 155:142 */     this.torchPos.clear();
/* 156:146 */     for (int i = 0; i < 5; i++) {
/* 157:147 */       genLevel(world, rand, x, y + i * 5, z, i);
/* 158:    */     }
/* 159:152 */     for (ChunkPos torchPo1 : this.torchPos) {
/* 160:153 */       world.getChunkFromBlockCoords(torchPo1.x, torchPo1.z).func_150807_a(torchPo1.x & 0xF, torchPo1.y, torchPo1.z & 0xF, Blocks.torch, 5);
/* 161:    */     }
/* 162:156 */     for (ChunkPos torchPo : this.torchPos) {
/* 163:157 */       world.func_147451_t(torchPo.x, torchPo.y, torchPo.z);
/* 164:    */     }
/* 165:161 */     return true;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void genLevel(World world, Random rand, int x, int y, int z, int level)
/* 169:    */   {
/* 170:165 */     for (int ax = 0; ax < 17; ax++) {
/* 171:166 */       for (int ay = 0; ay < 17; ay++) {
/* 172:167 */         if ((this.block[ax][ay] == -1) || (ax <= level) || (16 - ax <= level) || (ay <= level) || (16 - ay <= level)) {
/* 173:168 */           this.block[ax][ay] = -1;
/* 174:    */         } else {
/* 175:170 */           this.block[ax][ay] = 0;
/* 176:    */         }
/* 177:    */       }
/* 178:    */     }
/* 179:173 */     ArrayList<Vec2> list = new ArrayList();
/* 180:174 */     ArrayList<Vec2> corridors = new ArrayList();
/* 181:    */     
/* 182:176 */     this.block[8][8] = 1;
/* 183:177 */     corridors.add(new Vec2(8, 8));
/* 184:179 */     if (level == 0)
/* 185:    */     {
/* 186:180 */       for (int ax = 1; ax < 17; ax++)
/* 187:    */       {
/* 188:181 */         this.block[ax][8] = 1;
/* 189:182 */         corridors.add(new Vec2(ax, 8));
/* 190:    */       }
/* 191:185 */       this.block[0][8] = -1;
/* 192:    */     }
/* 193:188 */     int a = 0;
/* 194:191 */     for (; a < 289; goto 266)
/* 195:    */     {
/* 196:192 */       a++;
/* 197:193 */       Vec2 t = (Vec2)corridors.get(rand.nextInt(corridors.size()));
/* 198:194 */       int k = 1 + rand.nextInt(8);
/* 199:195 */       int d = rand.nextInt(4);
/* 200:196 */       boolean canAdd = true;
/* 201:    */       
/* 202:198 */       int i = 1;
/* 203:198 */       if ((i <= k) && (canAdd))
/* 204:    */       {
/* 205:199 */         if ((!isValid(t.x + dx[d] * i, t.y + dy[d] * i)) || (g(t.x + dx[d] * i, t.y + dy[d] * i) != 0))
/* 206:    */         {
/* 207:200 */           canAdd = false;
/* 208:    */         }
/* 209:    */         else
/* 210:    */         {
/* 211:202 */           int c_n = 0;
/* 212:204 */           for (int j = 0; j < 4; j++) {
/* 213:205 */             if (g(t.x + dx[d] * i + dx[j], t.y + dy[d] * i + dy[j]) != 0) {
/* 214:206 */               c_n++;
/* 215:    */             }
/* 216:    */           }
/* 217:209 */           if (c_n >= 2)
/* 218:    */           {
/* 219:210 */             canAdd = false;
/* 220:    */           }
/* 221:    */           else
/* 222:    */           {
/* 223:212 */             c_n = 0;
/* 224:214 */             for (int ddx = -2; ddx <= 2; ddx++) {
/* 225:215 */               for (int ddy = -2; ddy <= 2; ddy++) {
/* 226:216 */                 if (g(t.x + dx[d] * i + ddx, t.y + dy[d] * i + ddy) != 0) {
/* 227:217 */                   c_n++;
/* 228:    */                 }
/* 229:    */               }
/* 230:    */             }
/* 231:220 */             if (c_n >= 8)
/* 232:    */             {
/* 233:221 */               canAdd = false;
/* 234:    */             }
/* 235:    */             else
/* 236:    */             {
/* 237:223 */               s(t.x + dx[d] * i, t.y + dy[d] * i, 1);
/* 238:224 */               corridors.add(new Vec2(t.x + dx[d] * i, t.y + dy[d] * i));
/* 239:225 */               a += 12;
/* 240:    */             }
/* 241:    */           }
/* 242:    */         }
/* 243:198 */         i++;
/* 244:    */       }
/* 245:    */     }
/* 246:232 */     Collections.shuffle(corridors);
/* 247:234 */     for (int i = 0; (i < corridors.size()) && (i < 10); i++) {
/* 248:235 */       list.add(corridors.get(i));
/* 249:    */     }
/* 250:239 */     int numDoors = 0;
/* 251:240 */     ArrayList<Integer> doorDirections = new ArrayList();
/* 252:241 */     doorDirections.add(Integer.valueOf(0));
/* 253:244 */     for (int i = 0; i < list.size(); i++)
/* 254:    */     {
/* 255:245 */       int ax = ((Vec2)list.get(i)).x;
/* 256:246 */       int ay = ((Vec2)list.get(i)).y;
/* 257:247 */       boolean added = false;
/* 258:249 */       if (g(ax, ay) == 0)
/* 259:    */       {
/* 260:251 */         for (int j = 0; j < 4; j++) {
/* 261:252 */           if ((g(ax + dx[j], ay + dy[j]) >> 1 > 0) && (g(ax + dx[j], ay + dy[j]) >> 1 == g(ax + dx[((j + 1) % 4)], ay + dy[((j + 1) % 4)]) >> 1))
/* 262:    */           {
/* 263:253 */             added = true;
/* 264:254 */             s(ax, ay, (g(ax + dx[j], ay + dy[j]) >> 1) * 2 + 1);
/* 265:    */           }
/* 266:    */         }
/* 267:270 */         if (!added)
/* 268:    */         {
/* 269:271 */           int k = rand.nextInt(4);
/* 270:273 */           for (int j = 0; j < 4; j++) {
/* 271:274 */             if (g(ax + dx[((j + k) % 4)], ay + dy[((j + k) % 4)]) >> 1 > 0)
/* 272:    */             {
/* 273:275 */               added = true;
/* 274:276 */               s(ax, ay, (g(ax + dx[((j + k) % 4)], ay + dy[((j + k) % 4)]) >> 1) * 2 + 1);
/* 275:    */             }
/* 276:    */           }
/* 277:    */         }
/* 278:281 */         if (!added)
/* 279:    */         {
/* 280:282 */           int k = rand.nextInt(4);
/* 281:284 */           for (int j = 0; j < 4; j++) {
/* 282:285 */             if (g(ax + dx[((j + k) % 4)], ay + dy[((j + k) % 4)]) == 1)
/* 283:    */             {
/* 284:286 */               added = true;
/* 285:287 */               numDoors++;
/* 286:288 */               doorDirections.add(Integer.valueOf(j));
/* 287:289 */               s(ax, ay, numDoors * 2);
/* 288:    */             }
/* 289:    */           }
/* 290:    */         }
/* 291:    */       }
/* 292:294 */       for (int j = 0; j < 4; j++) {
/* 293:295 */         if ((isValid(ax + dx[j], ay + dy[j])) && (!list.contains(new Vec2(ax + dx[j], ay + dy[j])))) {
/* 294:296 */           if (list.size() - i >= 1) {
/* 295:297 */             list.add(i + rand.nextInt(list.size() - i), new Vec2(ax + dx[j], ay + dy[j]));
/* 296:    */           } else {
/* 297:299 */             list.add(new Vec2(ax + dx[j], ay + dy[j]));
/* 298:    */           }
/* 299:    */         }
/* 300:    */       }
/* 301:    */     }
/* 302:304 */     int doorDir = 0;
/* 303:306 */     for (int ax = 0; ax < 17; ax++) {
/* 304:307 */       for (int ay = 0; ay < 17; ay++)
/* 305:    */       {
/* 306:308 */         int d = g(ax, ay);
/* 307:310 */         if (d >= 0) {
/* 308:311 */           for (int dax = -1; dax <= 1; dax++) {
/* 309:312 */             for (int day = -1; day <= 1; day++)
/* 310:    */             {
/* 311:313 */               setBrick(world, x + 1 + ax * 3 + dax, y, z + 1 + ay * 3 + day, 0);
/* 312:314 */               Block id = Blocks.air;
/* 313:316 */               if ((dax == 0) && (day == 0))
/* 314:    */               {
/* 315:317 */                 id = Blocks.air;
/* 316:319 */                 if (d > 1) {
/* 317:320 */                   setWood(world, x + 1 + ax * 3 + dax, y, z + 1 + ay * 3 + day, (d >> 1) % 16);
/* 318:    */                 }
/* 319:    */               }
/* 320:322 */               else if ((dax == 0) || (day == 0))
/* 321:    */               {
/* 322:323 */                 doorDir = dax == 1 ? 2 : dax == 0 ? 1 : day == 1 ? 3 : 0;
/* 323:333 */                 if ((g(ax + dax, ay + day) >> 1 == d >> 1) || ((d == 1) && (g(ax + dax, ay + day) <= 0)) || ((d == 1) && (g(ax + dax, ay + day) % 2 == 0)) || ((d % 2 == 0) && (g(ax + dax, ay + day) == 1)))
/* 324:    */                 {
/* 325:335 */                   if ((d % 2 == 0) && (g(ax + dax, ay + day) == 1))
/* 326:    */                   {
/* 327:336 */                     id = Blocks.planks;
/* 328:    */                   }
/* 329:    */                   else
/* 330:    */                   {
/* 331:338 */                     id = Blocks.air;
/* 332:340 */                     if (d > 1) {
/* 333:341 */                       setWood(world, x + 1 + ax * 3 + dax, y, z + 1 + ay * 3 + day, woodPattern(d, ax * 3 + dax, ay * 3 + day));
/* 334:    */                     }
/* 335:    */                   }
/* 336:    */                 }
/* 337:    */                 else {
/* 338:345 */                   id = Blocks.stonebrick;
/* 339:    */                 }
/* 340:    */               }
/* 341:348 */               else if ((d > 1) && (g(ax + dax, ay) >> 1 == d >> 1) && (g(ax, ay + day) >> 1 == d >> 1))
/* 342:    */               {
/* 343:349 */                 id = Blocks.air;
/* 344:350 */                 setWood(world, x + 1 + ax * 3 + dax, y, z + 1 + ay * 3 + day, woodPattern(d, ax * 3 + dax, ay * 3 + day));
/* 345:    */               }
/* 346:    */               else
/* 347:    */               {
/* 348:352 */                 id = Blocks.stonebrick;
/* 349:    */               }
/* 350:356 */               if (id == Blocks.planks)
/* 351:    */               {
/* 352:357 */                 ItemDoor.func_150924_a(world, x + 1 + ax * 3 + dax, y + 1, z + 1 + ay * 3 + day, doorDir, Blocks.wooden_door);
/* 353:358 */                 setBrick(world, x + 1 + ax * 3 + dax, y + 3, z + 1 + ay * 3 + day, 2);
/* 354:359 */                 setBrick(world, x + 1 + ax * 3 + dax, y + 4, z + 1 + ay * 3 + day, 1);
/* 355:    */               }
/* 356:    */               else
/* 357:    */               {
/* 358:361 */                 for (int dh = 0; dh <= 3; dh++) {
/* 359:362 */                   if (id == Blocks.stonebrick) {
/* 360:363 */                     setBrick(world, x + 1 + ax * 3 + dax, y + 1 + dh, z + 1 + ay * 3 + day, dh == 2 ? 2 : 1);
/* 361:    */                   } else {
/* 362:365 */                     world.setBlock(x + 1 + ax * 3 + dax, y + 1 + dh, z + 1 + ay * 3 + day, id, 0, 2);
/* 363:    */                   }
/* 364:    */                 }
/* 365:    */               }
/* 366:368 */               if (d == 1) {
/* 367:369 */                 setBrick(world, x + 1 + ax * 3 + dax, y + 3, z + 1 + ay * 3 + day, 1);
/* 368:    */               }
/* 369:372 */               setBrick(world, x + 1 + ax * 3 + dax, y + 4, z + 1 + ay * 3 + day, 3);
/* 370:    */             }
/* 371:    */           }
/* 372:    */         } else {
/* 373:375 */           for (int dax = -1; dax <= 1; dax++) {
/* 374:376 */             for (int day = -1; day <= 1; day++) {
/* 375:377 */               if ((g(ax + dax, ay + day) >= 0) || (g(ax + dax, ay) >= 0) || (g(ax, ay + day) >= 0))
/* 376:    */               {
/* 377:378 */                 setBrick(world, x + 1 + ax * 3 + dax, y, z + 1 + ay * 3 + day, 0);
/* 378:379 */                 setBrick(world, x + 1 + ax * 3 + dax, y + 4, z + 1 + ay * 3 + day, 4);
/* 379:381 */                 if ((ax + dax + ay + day) % 2 == 0)
/* 380:    */                 {
/* 381:382 */                   setBrick(world, x + 1 + ax * 3 + dax, y + 5, z + 1 + ay * 3 + day, 5);
/* 382:383 */                   this.torchPos.add(new ChunkPos(x + 1 + ax * 3 + dax, y + 6, z + 1 + ay * 3 + day));
/* 383:    */                 }
/* 384:    */               }
/* 385:    */             }
/* 386:    */           }
/* 387:    */         }
/* 388:    */       }
/* 389:    */     }
/* 390:390 */     int ax = 8;
/* 391:391 */     int ay = 8;
/* 392:393 */     for (int h = 0; h <= 4; h++)
/* 393:    */     {
/* 394:394 */       for (int dax = -2; dax <= 2; dax++) {
/* 395:395 */         for (int day = -2; day <= 2; day++) {
/* 396:396 */           if ((Math.abs(dax) < 2) || (Math.abs(day) < 2)) {
/* 397:397 */             if ((h > 0) && (h < 4)) {
/* 398:398 */               world.setBlock(x + 1 + ax * 3 + dax, y + h, z + 1 + ay * 3 + day, Blocks.air, 0, 2);
/* 399:    */             } else {
/* 400:400 */               setBrick(world, x + 1 + ax * 3 + dax, y + h, z + 1 + ay * 3 + day, 3);
/* 401:    */             }
/* 402:    */           }
/* 403:    */         }
/* 404:    */       }
/* 405:405 */       setBrick(world, x + 1 + ax * 3 + 1, y + h, z + 1 + ay * 3, 0);
/* 406:406 */       world.setBlock(x + 1 + ax * 3, y + h, z + 1 + ay * 3, Blocks.ladder, 4, 2);
/* 407:    */     }
/* 408:409 */     if (level == 0) {
/* 409:410 */       for (int dax = -1; dax <= 1; dax++) {
/* 410:411 */         for (int day = -1; day <= 1; day++) {
/* 411:412 */           setBrick(world, x + 1 + ax * 3 + dax, y, z + 1 + ay * 3 + day, 1);
/* 412:    */         }
/* 413:    */       }
/* 414:    */     }
/* 415:415 */     int numChests = (17 - 2 * level) * (17 - 2 * level) / 49;
/* 416:416 */     ArrayList<Vec2> chestPos = new ArrayList();
/* 417:419 */     for (int i = list.size() - 1; (chestPos.size() < numChests) && (i >= 0); i--)
/* 418:    */     {
/* 419:420 */       Vec2 v = (Vec2)list.get(i);
/* 420:    */       
/* 421:422 */       boolean add = g(v.x, v.y) > 1;
/* 422:424 */       if (add) {
/* 423:425 */         for (int j = 0; (add) && (j < chestPos.size()); j++) {
/* 424:426 */           if (((Vec2)chestPos.get(j)).distFrom(v) < 8.0D) {
/* 425:427 */             add = false;
/* 426:    */           }
/* 427:    */         }
/* 428:    */       }
/* 429:431 */       if (add) {
/* 430:432 */         chestPos.add(v);
/* 431:    */       }
/* 432:    */     }
/* 433:436 */     for (Vec2 chestPo : chestPos)
/* 434:    */     {
/* 435:437 */       world.setBlock(x + 1 + 3 * chestPo.x, y + 1, z + 1 + 3 * chestPo.y, Blocks.chest);
/* 436:438 */       TileEntityChest tile = (TileEntityChest)world.getTileEntity(x + 1 + 3 * chestPo.x, y + 1, z + 1 + 3 * chestPo.y);
/* 437:440 */       if (tile != null)
/* 438:    */       {
/* 439:441 */         ChestGenHooks info = ChestGenHooks.getInfo(dungeons[rand.nextInt(dungeons.length)]);
/* 440:442 */         WeightedRandomChestContent.generateChestContents(rand, info.getItems(rand), tile, info.getCount(rand));
/* 441:    */       }
/* 442:445 */       setMobSpawner(world, x + 1 + 3 * chestPo.x, y + 2, z + 1 + 3 * chestPo.y, rand);
/* 443:    */     }
/* 444:    */   }
/* 445:    */   
/* 446:    */   public void setBrick(World world, int x, int y, int z, int type)
/* 447:    */   {
/* 448:451 */     if (this.colorbricks) {
/* 449:452 */       world.setBlock(x, y, z, ExtraUtils.colorBlockBrick, this.block_allocations[type], 2);
/* 450:    */     } else {
/* 451:454 */       world.setBlock(x, y, z, Blocks.stonebrick);
/* 452:    */     }
/* 453:    */   }
/* 454:    */   
/* 455:    */   public void setWood(World world, int x, int y, int z, int type)
/* 456:    */   {
/* 457:459 */     if (this.colorWoods) {
/* 458:460 */       world.setBlock(x, y, z, ExtraUtils.coloredWood, this.block_allocations[type], 2);
/* 459:    */     } else {
/* 460:462 */       world.setBlock(x, y, z, Blocks.planks, type % 4, 2);
/* 461:    */     }
/* 462:    */   }
/* 463:    */   
/* 464:    */   public int g(int x, int y)
/* 465:    */   {
/* 466:467 */     return isValid(x, y) ? this.block[x][y] : -1;
/* 467:    */   }
/* 468:    */   
/* 469:    */   public void s(int x, int y, int i)
/* 470:    */   {
/* 471:471 */     if (isValid(x, y)) {
/* 472:472 */       this.block[x][y] = i;
/* 473:    */     }
/* 474:    */   }
/* 475:    */   
/* 476:    */   public boolean isValid(int x, int y)
/* 477:    */   {
/* 478:477 */     if ((x >= 0) && (y >= 0)) {}
/* 479:477 */     return ((x < 17 ? 1 : 0) & (y < 17 ? 1 : 0)) != 0;
/* 480:    */   }
/* 481:    */   
/* 482:    */   public int woodPattern(int d, int x, int y)
/* 483:    */   {
/* 484:481 */     d = (d >> 1) % 16;
/* 485:483 */     switch (d)
/* 486:    */     {
/* 487:    */     case 0: 
/* 488:485 */       if (x % 2 == y % 2) {
/* 489:486 */         d++;
/* 490:    */       }
/* 491:    */       break;
/* 492:    */     case 3: 
/* 493:492 */       if (x % 2 == 0) {
/* 494:493 */         d++;
/* 495:    */       }
/* 496:    */       break;
/* 497:    */     case 4: 
/* 498:499 */       if (y % 2 == 0) {
/* 499:500 */         d++;
/* 500:    */       }
/* 501:    */       break;
/* 502:    */     case 6: 
/* 503:506 */       if (x % 2 * (y % 2) == 0) {
/* 504:507 */         d++;
/* 505:    */       }
/* 506:    */       break;
/* 507:    */     case 8: 
/* 508:513 */       if (x % 3 * (y % 3) == 0) {
/* 509:514 */         d++;
/* 510:    */       }
/* 511:    */       break;
/* 512:    */     case 9: 
/* 513:520 */       if (x % 4 + y % 4 == 0) {
/* 514:521 */         d++;
/* 515:    */       }
/* 516:    */       break;
/* 517:    */     case 11: 
/* 518:527 */       if (x % 4 + y % 2 == 0) {
/* 519:528 */         d++;
/* 520:    */       }
/* 521:    */       break;
/* 522:    */     case 12: 
/* 523:534 */       if (x % 3 == 0) {
/* 524:535 */         d++;
/* 525:    */       }
/* 526:    */       break;
/* 527:    */     case 13: 
/* 528:541 */       if (x % 6 == 0) {
/* 529:542 */         d++;
/* 530:543 */       } else if (x % 2 == 0) {
/* 531:544 */         d += 2;
/* 532:    */       }
/* 533:    */       break;
/* 534:    */     }
/* 535:552 */     return d % 16;
/* 536:    */   }
/* 537:    */   
/* 538:    */   public static class Vec2
/* 539:    */   {
/* 540:    */     int x;
/* 541:    */     int y;
/* 542:    */     
/* 543:    */     public Vec2(int x, int y)
/* 544:    */     {
/* 545:560 */       this.x = x;
/* 546:561 */       this.y = y;
/* 547:    */     }
/* 548:    */     
/* 549:    */     public boolean equals(Object o)
/* 550:    */     {
/* 551:566 */       return ((o instanceof Vec2)) && (((Vec2)o).x == this.x) && (((Vec2)o).y == this.y);
/* 552:    */     }
/* 553:    */     
/* 554:    */     public double distFrom(Vec2 other)
/* 555:    */     {
/* 556:571 */       return Math.sqrt((this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y));
/* 557:    */     }
/* 558:    */   }
/* 559:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.worldgen.Underdark.WorldGenCastle
 * JD-Core Version:    0.7.0.1
 */