/*   1:    */ package com.rwtema.extrautils.block;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtilsMod;
/*   4:    */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*   5:    */ import com.rwtema.extrautils.IClientCode;
/*   6:    */ import net.minecraft.block.Block;
/*   7:    */ import net.minecraft.util.IIcon;
/*   8:    */ import net.minecraftforge.common.util.ForgeDirection;
/*   9:    */ 
/*  10:    */ public class Box
/*  11:    */ {
/*  12:    */   public float minX;
/*  13:    */   public float minY;
/*  14:    */   public float minZ;
/*  15:    */   public float maxX;
/*  16:    */   public float maxY;
/*  17:    */   public float maxZ;
/*  18: 16 */   public int offsetx = 0;
/*  19: 17 */   public int offsety = 0;
/*  20: 18 */   public int offsetz = 0;
/*  21: 19 */   public String label = "";
/*  22: 20 */   public IIcon texture = null;
/*  23: 21 */   public IIcon[] textureSide = new IIcon[6];
/*  24: 22 */   public boolean invisible = false;
/*  25: 23 */   public boolean renderAsNormalBlock = false;
/*  26: 24 */   public boolean[] invisibleSide = new boolean[6];
/*  27: 25 */   public int uvRotateEast = 0;
/*  28: 26 */   public int uvRotateWest = 0;
/*  29: 27 */   public int uvRotateSouth = 0;
/*  30: 28 */   public int uvRotateNorth = 0;
/*  31: 29 */   public int uvRotateTop = 0;
/*  32: 30 */   public int uvRotateBottom = 0;
/*  33: 31 */   public int color = 16777215;
/*  34: 32 */   public int[] rotAdd = { 0, 1, 2, 3 };
/*  35:    */   
/*  36:    */   public Box(String l, float par1, float par3, float par5, float par7, float par9, float par11)
/*  37:    */   {
/*  38: 35 */     this.label = l;
/*  39: 36 */     this.minX = Math.min(par1, par7);
/*  40: 37 */     this.minY = Math.min(par3, par9);
/*  41: 38 */     this.minZ = Math.min(par5, par11);
/*  42: 39 */     this.maxX = Math.max(par1, par7);
/*  43: 40 */     this.maxY = Math.max(par3, par9);
/*  44: 41 */     this.maxZ = Math.max(par5, par11);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Box(float par1, float par3, float par5, float par7, float par9, float par11)
/*  48:    */   {
/*  49: 45 */     this("", par1, par3, par5, par7, par9, par11);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Box setTextures(IIcon[] icons)
/*  53:    */   {
/*  54: 49 */     for (int i = 0; (i < 6) && (i < icons.length); i++) {
/*  55: 50 */       this.textureSide[i] = icons[i];
/*  56:    */     }
/*  57: 53 */     return this;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Box setTextureSides(Object... tex)
/*  61:    */   {
/*  62: 57 */     this.textureSide = new IIcon[6];
/*  63: 58 */     int s = 0;
/*  64: 60 */     for (Object aTex : tex) {
/*  65: 61 */       if ((aTex instanceof Integer))
/*  66:    */       {
/*  67: 62 */         s = ((Integer)aTex).intValue();
/*  68:    */       }
/*  69: 63 */       else if (((aTex instanceof IIcon)) && 
/*  70: 64 */         (s < 6) && (s >= 0))
/*  71:    */       {
/*  72: 65 */         this.textureSide[s] = ((IIcon)aTex);
/*  73: 66 */         s++;
/*  74:    */       }
/*  75:    */     }
/*  76: 71 */     return this;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Box setColor(int col)
/*  80:    */   {
/*  81: 75 */     this.color = col;
/*  82: 76 */     return this;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Box setAllSideInvisible()
/*  86:    */   {
/*  87: 80 */     for (int i = 0; i < 6; i++) {
/*  88: 81 */       this.invisibleSide[i] = true;
/*  89:    */     }
/*  90: 84 */     return this;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Box setSideInvisible(Object... tex)
/*  94:    */   {
/*  95: 88 */     int s = 0;
/*  96: 90 */     for (Object aTex : tex) {
/*  97: 91 */       if ((aTex instanceof Integer))
/*  98:    */       {
/*  99: 92 */         s = ((Integer)aTex).intValue();
/* 100: 93 */         this.invisibleSide[s] = true;
/* 101:    */       }
/* 102: 94 */       else if ((aTex instanceof Boolean))
/* 103:    */       {
/* 104: 95 */         this.invisibleSide[s] = ((Boolean)aTex).booleanValue();
/* 105: 96 */         s++;
/* 106:    */       }
/* 107:    */     }
/* 108:100 */     return this;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public Box setTexture(IIcon l)
/* 112:    */   {
/* 113:104 */     this.texture = l;
/* 114:105 */     return this;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public Box setLabel(String l)
/* 118:    */   {
/* 119:109 */     this.label = l;
/* 120:110 */     return this;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public Box copy()
/* 124:    */   {
/* 125:114 */     return new Box(this.label, this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setBounds(float par1, float par3, float par5, float par7, float par9, float par11)
/* 129:    */   {
/* 130:118 */     this.minX = Math.min(par1, par7);
/* 131:119 */     this.minY = Math.min(par3, par9);
/* 132:120 */     this.minZ = Math.min(par5, par11);
/* 133:121 */     this.maxX = Math.max(par1, par7);
/* 134:122 */     this.maxY = Math.max(par3, par9);
/* 135:123 */     this.maxZ = Math.max(par5, par11);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public Box offset(float x, float y, float z)
/* 139:    */   {
/* 140:127 */     this.minX += x;
/* 141:128 */     this.minY += y;
/* 142:129 */     this.minZ += z;
/* 143:130 */     this.maxX += x;
/* 144:131 */     this.maxY += y;
/* 145:132 */     this.maxZ += z;
/* 146:133 */     return this;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public Box rotateY(int numRotations)
/* 150:    */   {
/* 151:137 */     if (numRotations == 0) {
/* 152:138 */       return this;
/* 153:    */     }
/* 154:141 */     if (numRotations < 0) {
/* 155:142 */       numRotations += 4;
/* 156:    */     }
/* 157:145 */     numRotations &= 0x3;
/* 158:147 */     for (int i = 0; i < numRotations; i++)
/* 159:    */     {
/* 160:148 */       Box prev = copy();
/* 161:149 */       this.minZ = prev.minX;
/* 162:150 */       this.maxZ = prev.maxX;
/* 163:151 */       this.minX = (1.0F - prev.maxZ);
/* 164:152 */       this.maxX = (1.0F - prev.minZ);
/* 165:153 */       IIcon temp = this.textureSide[2];
/* 166:154 */       this.textureSide[2] = this.textureSide[4];
/* 167:155 */       this.textureSide[4] = this.textureSide[3];
/* 168:156 */       this.textureSide[3] = this.textureSide[5];
/* 169:157 */       this.textureSide[5] = temp;
/* 170:    */     }
/* 171:160 */     this.uvRotateTop = ((this.uvRotateTop + this.rotAdd[numRotations]) % 2);
/* 172:161 */     this.uvRotateBottom = ((this.uvRotateBottom + this.rotAdd[numRotations]) % 2);
/* 173:162 */     return this;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public Box fillIcons(final Block b, final int meta)
/* 177:    */   {
/* 178:166 */     ExtraUtilsMod.proxy.exectuteClientCode(new IClientCode()
/* 179:    */     {
/* 180:    */       public void exectuteClientCode()
/* 181:    */       {
/* 182:169 */         for (int side = 0; side < 6; side++) {
/* 183:170 */           Box.this.textureSide[side] = b.getIcon(side, meta);
/* 184:    */         }
/* 185:    */       }
/* 186:173 */     });
/* 187:174 */     return this;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public Box swapIcons(int a, int b)
/* 191:    */   {
/* 192:178 */     IIcon temp = this.textureSide[a];
/* 193:179 */     this.textureSide[a] = this.textureSide[b];
/* 194:180 */     this.textureSide[b] = temp;
/* 195:181 */     return this;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public Box rotateToSideTex(ForgeDirection dir)
/* 199:    */   {
/* 200:185 */     Box prev = copy();
/* 201:186 */     rotateToSide(dir);
/* 202:187 */     switch (2.$SwitchMap$net$minecraftforge$common$util$ForgeDirection[dir.ordinal()])
/* 203:    */     {
/* 204:    */     case 1: 
/* 205:    */       break;
/* 206:    */     case 2: 
/* 207:191 */       swapIcons(0, 1);
/* 208:192 */       this.uvRotateEast = 3;
/* 209:193 */       this.uvRotateWest = 3;
/* 210:194 */       this.uvRotateSouth = 3;
/* 211:195 */       this.uvRotateNorth = 3;
/* 212:196 */       break;
/* 213:    */     case 3: 
/* 214:198 */       swapIcons(1, 3);
/* 215:199 */       swapIcons(0, 2);
/* 216:200 */       this.uvRotateSouth = 2;
/* 217:201 */       this.uvRotateNorth = 1;
/* 218:202 */       this.uvRotateTop = 3;
/* 219:203 */       this.uvRotateBottom = 3;
/* 220:204 */       break;
/* 221:    */     case 4: 
/* 222:206 */       swapIcons(1, 2);
/* 223:207 */       swapIcons(0, 3);
/* 224:208 */       this.uvRotateSouth = 1;
/* 225:209 */       this.uvRotateNorth = 2;
/* 226:210 */       break;
/* 227:    */     case 5: 
/* 228:212 */       swapIcons(1, 5);
/* 229:213 */       swapIcons(0, 4);
/* 230:214 */       this.uvRotateEast = 2;
/* 231:215 */       this.uvRotateWest = 1;
/* 232:216 */       this.uvRotateTop = 1;
/* 233:217 */       this.uvRotateBottom = 2;
/* 234:218 */       break;
/* 235:    */     case 6: 
/* 236:220 */       swapIcons(1, 4);
/* 237:221 */       swapIcons(0, 5);
/* 238:222 */       this.uvRotateEast = 1;
/* 239:223 */       this.uvRotateWest = 2;
/* 240:224 */       this.uvRotateTop = 2;
/* 241:225 */       this.uvRotateBottom = 1;
/* 242:    */       
/* 243:227 */       break;
/* 244:    */     }
/* 245:231 */     return this;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public Box rotateToSide(ForgeDirection dir)
/* 249:    */   {
/* 250:235 */     Box prev = copy();
/* 251:237 */     switch (2.$SwitchMap$net$minecraftforge$common$util$ForgeDirection[dir.ordinal()])
/* 252:    */     {
/* 253:    */     case 1: 
/* 254:    */       break;
/* 255:    */     case 2: 
/* 256:242 */       this.minY = (1.0F - prev.maxY);
/* 257:243 */       this.maxY = (1.0F - prev.minY);
/* 258:244 */       break;
/* 259:    */     case 3: 
/* 260:247 */       this.minZ = prev.minY;
/* 261:248 */       this.maxZ = prev.maxY;
/* 262:249 */       this.minY = prev.minX;
/* 263:250 */       this.maxY = prev.maxX;
/* 264:251 */       this.minX = prev.minZ;
/* 265:252 */       this.maxX = prev.maxZ;
/* 266:253 */       break;
/* 267:    */     case 4: 
/* 268:256 */       this.minZ = (1.0F - prev.maxY);
/* 269:257 */       this.maxZ = (1.0F - prev.minY);
/* 270:258 */       this.minY = prev.minX;
/* 271:259 */       this.maxY = prev.maxX;
/* 272:260 */       this.minX = (1.0F - prev.maxZ);
/* 273:261 */       this.maxX = (1.0F - prev.minZ);
/* 274:262 */       break;
/* 275:    */     case 5: 
/* 276:265 */       this.minX = prev.minY;
/* 277:266 */       this.maxX = prev.maxY;
/* 278:267 */       this.minY = prev.minX;
/* 279:268 */       this.maxY = prev.maxX;
/* 280:269 */       this.minZ = (1.0F - prev.maxZ);
/* 281:270 */       this.maxZ = (1.0F - prev.minZ);
/* 282:271 */       break;
/* 283:    */     case 6: 
/* 284:274 */       this.minX = (1.0F - prev.maxY);
/* 285:275 */       this.maxX = (1.0F - prev.minY);
/* 286:276 */       this.minY = prev.minX;
/* 287:277 */       this.maxY = prev.maxX;
/* 288:278 */       break;
/* 289:    */     }
/* 290:284 */     return this;
/* 291:    */   }
/* 292:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.Box
 * JD-Core Version:    0.7.0.1
 */