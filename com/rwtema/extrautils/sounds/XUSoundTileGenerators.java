/*   1:    */ package com.rwtema.extrautils.sounds;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.tileentity.generators.TileEntityGenerator;
/*   4:    */ import cpw.mods.fml.relauncher.Side;
/*   5:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   6:    */ import java.lang.ref.WeakReference;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.Set;
/*   9:    */ import java.util.WeakHashMap;
/*  10:    */ import net.minecraft.client.Minecraft;
/*  11:    */ import net.minecraft.client.audio.ISound.AttenuationType;
/*  12:    */ import net.minecraft.client.audio.ITickableSound;
/*  13:    */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*  14:    */ import net.minecraft.util.ResourceLocation;
/*  15:    */ 
/*  16:    */ @SideOnly(Side.CLIENT)
/*  17:    */ public class XUSoundTileGenerators
/*  18:    */   implements ITickableSound
/*  19:    */ {
/*  20: 19 */   private static WeakReference<XUSoundTileGenerators> instance = null;
/*  21: 21 */   private WeakHashMap<TileEntityGenerator, Void> tiles = new WeakHashMap();
/*  22: 23 */   private boolean donePlaying = false;
/*  23: 24 */   private float zPosF = 0.0F;
/*  24: 25 */   private float xPosF = 0.0F;
/*  25: 26 */   private float yPosF = 0.0F;
/*  26: 27 */   private float volume = 1.0E-006F;
/*  27:    */   private ResourceLocation location;
/*  28:    */   
/*  29:    */   public XUSoundTileGenerators()
/*  30:    */   {
/*  31: 32 */     this.location = TileEntityGenerator.hum;
/*  32:    */   }
/*  33:    */   
/*  34:    */   private static XUSoundTileGenerators getInstance()
/*  35:    */   {
/*  36: 36 */     XUSoundTileGenerators sound = instance != null ? (XUSoundTileGenerators)instance.get() : null;
/*  37: 38 */     if (sound == null)
/*  38:    */     {
/*  39: 39 */       sound = new XUSoundTileGenerators();
/*  40: 40 */       instance = new WeakReference(sound);
/*  41:    */     }
/*  42: 43 */     return sound;
/*  43:    */   }
/*  44:    */   
/*  45:    */   private static void clearInstance()
/*  46:    */   {
/*  47: 47 */     instance = null;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean func_147667_k()
/*  51:    */   {
/*  52: 52 */     return this.donePlaying;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public ResourceLocation func_147650_b()
/*  56:    */   {
/*  57: 57 */     return this.location;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean func_147657_c()
/*  61:    */   {
/*  62: 62 */     return true;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int func_147652_d()
/*  66:    */   {
/*  67: 67 */     return 0;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public float func_147653_e()
/*  71:    */   {
/*  72: 72 */     return this.volume;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public float func_147655_f()
/*  76:    */   {
/*  77: 77 */     return 1.0F;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public float func_147649_g()
/*  81:    */   {
/*  82: 82 */     return this.xPosF;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public float func_147654_h()
/*  86:    */   {
/*  87: 87 */     return this.yPosF;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public float func_147651_i()
/*  91:    */   {
/*  92: 92 */     return this.zPosF;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public ISound.AttenuationType func_147656_j()
/*  96:    */   {
/*  97: 97 */     return ISound.AttenuationType.LINEAR;
/*  98:    */   }
/*  99:    */   
/* 100:    */   @SideOnly(Side.CLIENT)
/* 101:    */   public static void addGenerator(TileEntityGenerator generator)
/* 102:    */   {
/* 103:103 */     XUSoundTileGenerators instance1 = getInstance();
/* 104:    */     
/* 105:105 */     removeOldValues();
/* 106:107 */     if (!instance1.tiles.containsKey(generator))
/* 107:    */     {
/* 108:108 */       instance1.tiles.put(generator, null);
/* 109:109 */       if (instance1.tiles.size() == 1) {
/* 110:110 */         refresh();
/* 111:    */       }
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   @SideOnly(Side.CLIENT)
/* 116:    */   public static void refresh()
/* 117:    */   {
/* 118:117 */     if ((instance == null) || (instance.get() == null)) {
/* 119:118 */       return;
/* 120:    */     }
/* 121:120 */     XUSoundTileGenerators sound = getInstance();
/* 122:122 */     if (!sound.tiles.isEmpty())
/* 123:    */     {
/* 124:123 */       removeOldValues();
/* 125:125 */       if ((!sound.tiles.isEmpty()) && (Sounds.canAddSound(sound)))
/* 126:    */       {
/* 127:126 */         sound.setDonePlaying(false);
/* 128:127 */         sound.volume = 1.0E-007F;
/* 129:128 */         Sounds.tryAddSound(sound);
/* 130:    */       }
/* 131:    */     }
/* 132:    */   }
/* 133:    */   
/* 134:    */   private static void removeOldValues()
/* 135:    */   {
/* 136:134 */     WeakHashMap<TileEntityGenerator, Void> weakHashMap = getInstance().tiles;
/* 137:    */     
/* 138:136 */     Iterator<TileEntityGenerator> iter = weakHashMap.keySet().iterator();
/* 139:137 */     while (iter.hasNext())
/* 140:    */     {
/* 141:138 */       TileEntityGenerator gen = (TileEntityGenerator)iter.next();
/* 142:139 */       if ((gen == null) || (gen.isInvalid()) || (gen.getWorldObj() != Minecraft.getMinecraft().theWorld)) {
/* 143:140 */         iter.remove();
/* 144:    */       }
/* 145:    */     }
/* 146:    */   }
/* 147:    */   
/* 148:    */   public synchronized void update()
/* 149:    */   {
/* 150:146 */     EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
/* 151:147 */     if (player == null)
/* 152:    */     {
/* 153:148 */       setDonePlaying(true);
/* 154:149 */       return;
/* 155:    */     }
/* 156:152 */     removeOldValues();
/* 157:154 */     if (this.tiles.size() == 0)
/* 158:    */     {
/* 159:155 */       if (this.volume > 0.0005D) {
/* 160:156 */         this.volume *= 0.9F;
/* 161:    */       } else {
/* 162:158 */         setDonePlaying(true);
/* 163:    */       }
/* 164:    */     }
/* 165:    */     else
/* 166:    */     {
/* 167:160 */       boolean shouldPlay = false;
/* 168:162 */       if (this.tiles.size() == 1)
/* 169:    */       {
/* 170:163 */         for (TileEntityGenerator gen : this.tiles.keySet()) {
/* 171:164 */           if (gen.shouldSoundPlay())
/* 172:    */           {
/* 173:165 */             shouldPlay = true;
/* 174:166 */             moveTorwards(gen.x() + 0.5F, gen.y() + 0.5F, gen.z() + 0.5F, 0.05F);
/* 175:    */           }
/* 176:    */         }
/* 177:    */       }
/* 178:    */       else
/* 179:    */       {
/* 180:169 */         float x = 0.0F;
/* 181:170 */         float y = 0.0F;
/* 182:171 */         float z = 0.0F;
/* 183:172 */         float total_weight = 0.0F;
/* 184:173 */         for (TileEntityGenerator gen : this.tiles.keySet()) {
/* 185:174 */           if ((gen != null) && (gen.shouldSoundPlay()))
/* 186:    */           {
/* 187:175 */             shouldPlay = true;
/* 188:176 */             float w = weight(gen.getDistanceFrom(player.posX, player.posY, player.posZ));
/* 189:177 */             x += w * gen.x();
/* 190:178 */             y += w * gen.y();
/* 191:179 */             z += w * gen.z();
/* 192:180 */             total_weight += w;
/* 193:    */           }
/* 194:    */         }
/* 195:184 */         if ((shouldPlay) && (total_weight > 0.0F)) {
/* 196:185 */           moveTorwards(x / total_weight + 0.5F, y / total_weight + 0.5F, z / total_weight + 0.5F, 0.05F);
/* 197:    */         } else {
/* 198:187 */           this.volume *= 0.9F;
/* 199:    */         }
/* 200:    */       }
/* 201:190 */       if (shouldPlay)
/* 202:    */       {
/* 203:191 */         if (this.volume < 0.9995000000000001D) {
/* 204:192 */           this.volume = (1.0F - (1.0F - this.volume) * 0.9F);
/* 205:    */         } else {
/* 206:194 */           this.volume = 1.0F;
/* 207:    */         }
/* 208:    */       }
/* 209:196 */       else if (this.volume > 0.0005D) {
/* 210:197 */         this.volume *= 0.9F;
/* 211:    */       } else {
/* 212:199 */         this.volume = 0.0F;
/* 213:    */       }
/* 214:    */     }
/* 215:    */   }
/* 216:    */   
/* 217:    */   private void moveTorwards(float x, float y, float z, float speed)
/* 218:    */   {
/* 219:205 */     if (this.volume == 0.0F) {
/* 220:206 */       speed = 1.0F;
/* 221:    */     }
/* 222:208 */     this.xPosF = (this.xPosF * (1.0F - speed) + x * speed);
/* 223:209 */     this.yPosF = (this.yPosF * (1.0F - speed) + y * speed);
/* 224:210 */     this.zPosF = (this.zPosF * (1.0F - speed) + z * speed);
/* 225:    */   }
/* 226:    */   
/* 227:    */   private static float weight(double d)
/* 228:    */   {
/* 229:214 */     if (d < 1.0D) {
/* 230:215 */       return 1.0F;
/* 231:    */     }
/* 232:217 */     return (float)(1.0D / d);
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setDonePlaying(boolean donePlaying)
/* 236:    */   {
/* 237:221 */     this.donePlaying = donePlaying;
/* 238:222 */     if (donePlaying) {
/* 239:223 */       clearInstance();
/* 240:    */     }
/* 241:    */   }
/* 242:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.sounds.XUSoundTileGenerators
 * JD-Core Version:    0.7.0.1
 */