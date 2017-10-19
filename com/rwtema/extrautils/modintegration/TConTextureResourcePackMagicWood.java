/*   1:    */ package com.rwtema.extrautils.modintegration;
/*   2:    */ 
/*   3:    */ import java.awt.image.BufferedImage;
/*   4:    */ import java.awt.image.DirectColorModel;
/*   5:    */ import net.minecraft.util.MathHelper;
/*   6:    */ 
/*   7:    */ public class TConTextureResourcePackMagicWood
/*   8:    */   extends TConTextureResourcePackBase
/*   9:    */ {
/*  10:    */   public TConTextureResourcePackMagicWood(String name)
/*  11:    */   {
/*  12:  9 */     super(name);
/*  13:    */   }
/*  14:    */   
/*  15:    */   public BufferedImage modifyImage(BufferedImage image)
/*  16:    */   {
/*  17: 14 */     int w = image.getWidth();
/*  18: 15 */     int h = image.getHeight();
/*  19:    */     
/*  20: 17 */     image.getType();
/*  21:    */     
/*  22: 19 */     int[][] pixels = new int[w][h];
/*  23: 20 */     boolean[][] base = new boolean[w][h];
/*  24:    */     
/*  25: 22 */     int mean = 0;
/*  26: 23 */     int div = 0;
/*  27: 25 */     for (int x = 0; x < w; x++) {
/*  28: 26 */       for (int y = 0; y < h; y++)
/*  29:    */       {
/*  30: 27 */         int c = image.getRGB(x, y);
/*  31: 28 */         pixels[x][y] = brightness(c);
/*  32: 29 */         boolean nottrans = (c != 0) && (rgb.getAlpha(c) > 64);
/*  33: 30 */         if (nottrans)
/*  34:    */         {
/*  35: 31 */           base[x][y] = 1;
/*  36: 32 */           mean += pixels[x][y];
/*  37: 33 */           div++;
/*  38:    */         }
/*  39:    */       }
/*  40:    */     }
/*  41: 38 */     mean = mean / div * 2 / 4;
/*  42:    */     int n;
/*  43:    */     int n;
/*  44: 42 */     if (w >= 256)
/*  45:    */     {
/*  46: 43 */       n = 5;
/*  47:    */     }
/*  48:    */     else
/*  49:    */     {
/*  50:    */       int n;
/*  51: 44 */       if (w >= 128)
/*  52:    */       {
/*  53: 45 */         n = 4;
/*  54:    */       }
/*  55:    */       else
/*  56:    */       {
/*  57:    */         int n;
/*  58: 46 */         if (w >= 64)
/*  59:    */         {
/*  60: 47 */           n = 3;
/*  61:    */         }
/*  62:    */         else
/*  63:    */         {
/*  64:    */           int n;
/*  65: 48 */           if (w >= 32) {
/*  66: 49 */             n = 2;
/*  67:    */           } else {
/*  68: 51 */             n = 1;
/*  69:    */           }
/*  70:    */         }
/*  71:    */       }
/*  72:    */     }
/*  73: 54 */     boolean[][] baseSilhouette = contract(base, n);
/*  74:    */     
/*  75: 56 */     boolean[][] interior1 = contract(baseSilhouette, n);
/*  76:    */     
/*  77: 58 */     boolean[][] baseCorners = multI(mult(expand(getCorners(baseSilhouette), n), baseSilhouette), interior1);
/*  78: 59 */     boolean[][] baseCornersShift = orwise(orwise(shift(baseCorners, 0, -1), shift(baseCorners, -1, 0)), shift(baseCorners, -1, -1));
/*  79:    */     
/*  80:    */ 
/*  81: 62 */     boolean[][] interior2 = contract(interior1, 2 * n);
/*  82:    */     
/*  83:    */ 
/*  84: 65 */     boolean[][] interior3 = contract(interior2, n);
/*  85: 66 */     boolean[][] interior4 = contract(interior3, n);
/*  86:    */     
/*  87: 68 */     boolean[][] interiorCorners = multI(mult(expand(getCorners(interior2), n), interior2), interior3);
/*  88: 69 */     boolean[][] interiorCornersShift = orwise(orwise(shift(interiorCorners, -1, 0), shift(interiorCorners, 0, -1)), shift(interiorCorners, -1, -1));
/*  89:    */     
/*  90:    */ 
/*  91: 72 */     int trans = 0;
/*  92: 73 */     int gold = -398001;
/*  93: 74 */     int gold_highlight = -117;
/*  94: 75 */     int wood = -6455217;
/*  95: 76 */     int darkwood = -10071758;
/*  96:    */     
/*  97: 78 */     int[][] outpixels = new int[w][w];
/*  98: 80 */     for (int x = 0; x < w; x++) {
/*  99: 81 */       for (int y = 0; y < h; y++)
/* 100:    */       {
/* 101: 82 */         if (baseSilhouette[x][y] == 0)
/* 102:    */         {
/* 103: 83 */           if (base[x][y] != 0) {
/* 104: 84 */             outpixels[x][y] = multPixel(darkwood, pixels[x][y] / 2);
/* 105:    */           } else {
/* 106: 86 */             outpixels[x][y] = trans;
/* 107:    */           }
/* 108:    */         }
/* 109: 88 */         else if (interior1[x][y] == 0)
/* 110:    */         {
/* 111: 89 */           if (baseCorners[x][y] != 0)
/* 112:    */           {
/* 113: 90 */             if (baseCornersShift[x][y] != 0) {
/* 114: 91 */               outpixels[x][y] = multPixel(gold, Math.max(pixels[x][y], mean));
/* 115:    */             } else {
/* 116: 93 */               outpixels[x][y] = multPixel(gold_highlight, Math.max(pixels[x][y], mean) + 5);
/* 117:    */             }
/* 118:    */           }
/* 119:    */           else {
/* 120: 95 */             outpixels[x][y] = multPixel(darkwood, pixels[x][y]);
/* 121:    */           }
/* 122:    */         }
/* 123: 98 */         else if ((interior2[x][y] == 0) || (interior3[x][y] != 0))
/* 124:    */         {
/* 125: 99 */           if ((interior3[x][y] != 0) && (interior4[x][y] == 0)) {
/* 126:100 */             outpixels[x][y] = multPixel(wood, pixels[x][y] * 3 / 4);
/* 127:    */           } else {
/* 128:102 */             outpixels[x][y] = multPixel(wood, pixels[x][y]);
/* 129:    */           }
/* 130:    */         }
/* 131:106 */         else if (interiorCorners[x][y] != 0)
/* 132:    */         {
/* 133:107 */           if (interiorCornersShift[x][y] != 0) {
/* 134:108 */             outpixels[x][y] = multPixel(gold, Math.max(pixels[x][y], mean));
/* 135:    */           } else {
/* 136:110 */             outpixels[x][y] = multPixel(gold_highlight, Math.max(pixels[x][y], mean) + 5);
/* 137:    */           }
/* 138:    */         }
/* 139:    */         else {
/* 140:112 */           outpixels[x][y] = multPixel(darkwood, pixels[x][y]);
/* 141:    */         }
/* 142:119 */         image.setRGB(x, y, outpixels[x][y]);
/* 143:    */       }
/* 144:    */     }
/* 145:123 */     return image;
/* 146:    */   }
/* 147:    */   
/* 148:    */   private boolean[][] orwise(boolean[][] a, boolean[][] b)
/* 149:    */   {
/* 150:127 */     for (int i = 0; i < a.length; i++) {
/* 151:128 */       for (int j = 0; j < a[i].length; j++) {
/* 152:129 */         a[i][j] |= b[i][j];
/* 153:    */       }
/* 154:    */     }
/* 155:132 */     return a;
/* 156:    */   }
/* 157:    */   
/* 158:    */   private boolean[][] mult(boolean[][] a, boolean[][] b)
/* 159:    */   {
/* 160:136 */     for (int i = 0; i < a.length; i++) {
/* 161:137 */       for (int j = 0; j < a[i].length; j++) {
/* 162:138 */         a[i][j] &= b[i][j];
/* 163:    */       }
/* 164:    */     }
/* 165:141 */     return a;
/* 166:    */   }
/* 167:    */   
/* 168:    */   private boolean[][] multI(boolean[][] a, boolean[][] b)
/* 169:    */   {
/* 170:145 */     for (int i = 0; i < a.length; i++) {
/* 171:146 */       for (int j = 0; j < a[i].length; j++) {
/* 172:147 */         a[i][j] &= (b[i][j] == 0 ? 1 : 0);
/* 173:    */       }
/* 174:    */     }
/* 175:150 */     return a;
/* 176:    */   }
/* 177:    */   
/* 178:    */   private boolean[][] expand(boolean[][] base, int n)
/* 179:    */   {
/* 180:154 */     boolean[][] output = expand(base);
/* 181:155 */     for (int i = 0; i < n - 1; i++) {
/* 182:156 */       output = expand(output);
/* 183:    */     }
/* 184:158 */     return output;
/* 185:    */   }
/* 186:    */   
/* 187:    */   private boolean[][] contract(boolean[][] base, int n)
/* 188:    */   {
/* 189:162 */     boolean[][] output = contract(base);
/* 190:163 */     for (int i = 0; i < n - 1; i++) {
/* 191:164 */       output = contract(output);
/* 192:    */     }
/* 193:166 */     return output;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public int multPixel(int col, int b)
/* 197:    */   {
/* 198:170 */     return 0xFF000000 | clamp(rgb.getRed(col) * b / 255) << 16 | clamp(rgb.getGreen(col) * b / 255) << 8 | clamp(rgb.getBlue(col) * b / 255);
/* 199:    */   }
/* 200:    */   
/* 201:    */   private int clamp(int i)
/* 202:    */   {
/* 203:177 */     return MathHelper.clamp_int(i, 0, 255);
/* 204:    */   }
/* 205:    */   
/* 206:    */   public boolean get(boolean[][] img, int x, int y)
/* 207:    */   {
/* 208:181 */     return (x >= 0) && (y >= 0) && (x < img.length) && (y < img[x].length) && (img[x][y] != 0);
/* 209:    */   }
/* 210:    */   
/* 211:185 */   static int[][] offsets = { { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, -1 }, { -1, -1 }, { -1, 0 }, { -1, 1 } };
/* 212:    */   
/* 213:    */   public boolean[][] shift(boolean[][] img, int dx, int dy)
/* 214:    */   {
/* 215:197 */     int w = img.length;
/* 216:198 */     boolean[][] img2 = new boolean[w][w];
/* 217:200 */     for (int x = Math.max(-dx, 0); x < Math.min(w, w + dx); x++) {
/* 218:201 */       System.arraycopy(img[(x + dx)], Math.max(-dy, 0) + dy, img2[x], Math.max(-dy, 0), Math.min(w, w + dy) - Math.max(-dy, 0));
/* 219:    */     }
/* 220:203 */     return img2;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public boolean[][] getCorners(boolean[][] img)
/* 224:    */   {
/* 225:207 */     int w = img.length;
/* 226:208 */     boolean[][] img2 = new boolean[w][w];
/* 227:210 */     for (int x = 0; x < w; x++) {
/* 228:211 */       for (int y = 0; y < w; y++) {
/* 229:212 */         if (img[x][y] != 0)
/* 230:    */         {
/* 231:215 */           int an = -1;
/* 232:216 */           int n = 0;
/* 233:217 */           for (int[] offset : offsets) {
/* 234:218 */             if (get(img, x + offset[0], y + offset[1]))
/* 235:    */             {
/* 236:219 */               if (an == -1) {
/* 237:220 */                 an = n;
/* 238:    */               }
/* 239:221 */               n = 0;
/* 240:    */             }
/* 241:    */             else
/* 242:    */             {
/* 243:223 */               n++;
/* 244:224 */               if (n == 5) {
/* 245:    */                 break;
/* 246:    */               }
/* 247:    */             }
/* 248:    */           }
/* 249:229 */           if (an != -1) {
/* 250:230 */             n += an;
/* 251:    */           }
/* 252:232 */           if (n >= 5) {
/* 253:233 */             img2[x][y] = 1;
/* 254:    */           }
/* 255:    */         }
/* 256:    */       }
/* 257:    */     }
/* 258:238 */     return img2;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public boolean[][] contract(boolean[][] img)
/* 262:    */   {
/* 263:242 */     int w = img.length;
/* 264:243 */     boolean[][] img2 = new boolean[w][w];
/* 265:245 */     for (int x = 0; x < w; x++) {
/* 266:246 */       System.arraycopy(img[x], 0, img2[x], 0, w);
/* 267:    */     }
/* 268:249 */     for (int x = 0; x < w; x++) {
/* 269:250 */       for (int y = 0; y < w; y++) {
/* 270:251 */         if (img[x][y] != 0)
/* 271:    */         {
/* 272:252 */           if ((x == 0) || (y == 0) || (x == w - 1) || (y == w - 1)) {
/* 273:253 */             img2[x][y] = 0;
/* 274:    */           }
/* 275:    */         }
/* 276:    */         else
/* 277:    */         {
/* 278:255 */           if (x > 0) {
/* 279:255 */             img2[(x - 1)][y] = 0;
/* 280:    */           }
/* 281:256 */           if (y > 0) {
/* 282:256 */             img2[x][(y - 1)] = 0;
/* 283:    */           }
/* 284:257 */           if (x < w - 1) {
/* 285:257 */             img2[(x + 1)][y] = 0;
/* 286:    */           }
/* 287:258 */           if (y < w - 1) {
/* 288:258 */             img2[x][(y + 1)] = 0;
/* 289:    */           }
/* 290:    */         }
/* 291:    */       }
/* 292:    */     }
/* 293:263 */     return img2;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public boolean[][] expand(boolean[][] img)
/* 297:    */   {
/* 298:267 */     int w = img.length;
/* 299:268 */     boolean[][] img2 = new boolean[w][w];
/* 300:270 */     for (int x = 0; x < w; x++) {
/* 301:271 */       System.arraycopy(img[x], 0, img2[x], 0, w);
/* 302:    */     }
/* 303:274 */     for (int x = 0; x < w; x++) {
/* 304:275 */       for (int y = 0; y < w; y++) {
/* 305:276 */         if (img[x][y] != 0) {
/* 306:277 */           for (int[] offset : offsets)
/* 307:    */           {
/* 308:278 */             int dx = x + offset[0];
/* 309:279 */             int dy = y + offset[1];
/* 310:281 */             if ((dx >= 0) && (dy >= 0) && (dx < w) && (dy < w)) {
/* 311:282 */               img2[dx][dy] = 1;
/* 312:    */             }
/* 313:    */           }
/* 314:    */         }
/* 315:    */       }
/* 316:    */     }
/* 317:288 */     return img2;
/* 318:    */   }
/* 319:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.modintegration.TConTextureResourcePackMagicWood
 * JD-Core Version:    0.7.0.1
 */