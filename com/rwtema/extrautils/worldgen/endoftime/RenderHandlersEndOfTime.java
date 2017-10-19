/*   1:    */ package com.rwtema.extrautils.worldgen.endoftime;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtilsMod;
/*   4:    */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*   5:    */ import com.rwtema.extrautils.IClientCode;
/*   6:    */ import cpw.mods.fml.relauncher.ReflectionHelper;
/*   7:    */ import cpw.mods.fml.relauncher.Side;
/*   8:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   9:    */ import java.lang.reflect.Field;
/*  10:    */ import net.minecraft.client.Minecraft;
/*  11:    */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*  12:    */ import net.minecraft.client.multiplayer.WorldClient;
/*  13:    */ import net.minecraft.client.renderer.OpenGlHelper;
/*  14:    */ import net.minecraft.client.renderer.RenderGlobal;
/*  15:    */ import net.minecraft.client.renderer.RenderHelper;
/*  16:    */ import net.minecraft.client.renderer.Tessellator;
/*  17:    */ import net.minecraft.client.settings.GameSettings;
/*  18:    */ import net.minecraft.util.MathHelper;
/*  19:    */ import net.minecraft.util.Vec3;
/*  20:    */ import net.minecraft.world.WorldProvider;
/*  21:    */ import net.minecraftforge.client.IRenderHandler;
/*  22:    */ import org.lwjgl.opengl.GL11;
/*  23:    */ 
/*  24:    */ public class RenderHandlersEndOfTime
/*  25:    */ {
/*  26:    */   @SideOnly(Side.CLIENT)
/*  27:    */   public static NullRenderer nullRenderer;
/*  28:    */   @SideOnly(Side.CLIENT)
/*  29:    */   public static SkyRenderer skyRenderer;
/*  30:    */   
/*  31:    */   static
/*  32:    */   {
/*  33: 30 */     ExtraUtilsMod.proxy.exectuteClientCode(new IClientCode()
/*  34:    */     {
/*  35:    */       @SideOnly(Side.CLIENT)
/*  36:    */       public void exectuteClientCode()
/*  37:    */       {
/*  38: 34 */         RenderHandlersEndOfTime.skyRenderer = new RenderHandlersEndOfTime.SkyRenderer();
/*  39: 35 */         RenderHandlersEndOfTime.nullRenderer = new RenderHandlersEndOfTime.NullRenderer();
/*  40:    */       }
/*  41:    */     });
/*  42:    */   }
/*  43:    */   
/*  44:    */   @SideOnly(Side.CLIENT)
/*  45:    */   public static class SkyRenderer
/*  46:    */     extends IRenderHandler
/*  47:    */   {
/*  48:    */     int glSkyList;
/*  49:    */     int glSkyList2;
/*  50: 55 */     RenderGlobal renderGlobal = null;
/*  51: 57 */     Field field_glSkyList = ReflectionHelper.findField(RenderGlobal.class, new String[] { "glSkyList", "glSkyList" });
/*  52: 58 */     Field field_glSkyList2 = ReflectionHelper.findField(RenderGlobal.class, new String[] { "glSkyList2", "glSkyList2" });
/*  53:    */     
/*  54:    */     @SideOnly(Side.CLIENT)
/*  55:    */     public void render(float p_72714_1_, WorldClient theWorld, Minecraft mc)
/*  56:    */     {
/*  57: 65 */       if (mc.renderGlobal != this.renderGlobal)
/*  58:    */       {
/*  59: 66 */         this.renderGlobal = mc.renderGlobal;
/*  60:    */         try
/*  61:    */         {
/*  62: 69 */           this.glSkyList = this.field_glSkyList.getInt(this.renderGlobal);
/*  63: 70 */           this.glSkyList2 = this.field_glSkyList2.getInt(this.renderGlobal);
/*  64:    */         }
/*  65:    */         catch (IllegalAccessException e)
/*  66:    */         {
/*  67: 72 */           throw new RuntimeException(e);
/*  68:    */         }
/*  69:    */       }
/*  70:    */       else
/*  71:    */       {
/*  72: 76 */         GL11.glDisable(2912);
/*  73: 77 */         GL11.glDisable(3008);
/*  74: 78 */         GL11.glEnable(3042);
/*  75: 79 */         OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/*  76: 80 */         RenderHelper.disableStandardItemLighting();
/*  77: 81 */         GL11.glDepthMask(false);
/*  78: 82 */         GL11.glDisable(3553);
/*  79: 83 */         Tessellator tessellator = Tessellator.instance;
/*  80:    */         
/*  81: 85 */         Vec3 vec3 = theWorld.getSkyColor(mc.renderViewEntity, p_72714_1_);
/*  82: 86 */         float r = (float)vec3.xCoord;
/*  83: 87 */         float g = (float)vec3.yCoord;
/*  84: 88 */         float b = (float)vec3.zCoord;
/*  85: 91 */         for (int i = 0; i < 6; i++)
/*  86:    */         {
/*  87: 93 */           GL11.glPushMatrix();
/*  88: 95 */           if (i == 1) {
/*  89: 97 */             GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/*  90:    */           }
/*  91:100 */           if (i == 2) {
/*  92:102 */             GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
/*  93:    */           }
/*  94:105 */           if (i == 3) {
/*  95:107 */             GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
/*  96:    */           }
/*  97:110 */           if (i == 4) {
/*  98:112 */             GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
/*  99:    */           }
/* 100:115 */           if (i == 5) {
/* 101:117 */             GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
/* 102:    */           }
/* 103:120 */           tessellator.startDrawingQuads();
/* 104:121 */           tessellator.setColorOpaque_F(r, g, b);
/* 105:122 */           tessellator.addVertexWithUV(-100.0D, -100.0D, -100.0D, 0.0D, 0.0D);
/* 106:123 */           tessellator.addVertexWithUV(-100.0D, -100.0D, 100.0D, 0.0D, 16.0D);
/* 107:124 */           tessellator.addVertexWithUV(100.0D, -100.0D, 100.0D, 16.0D, 16.0D);
/* 108:125 */           tessellator.addVertexWithUV(100.0D, -100.0D, -100.0D, 16.0D, 0.0D);
/* 109:126 */           tessellator.draw();
/* 110:127 */           GL11.glPopMatrix();
/* 111:    */         }
/* 112:130 */         GL11.glDepthMask(true);
/* 113:131 */         GL11.glEnable(3553);
/* 114:132 */         GL11.glEnable(3008);
/* 115:    */         
/* 116:134 */         return;
/* 117:    */       }
/* 118:137 */       Vec3 vec3 = theWorld.getSkyColor(mc.renderViewEntity, p_72714_1_);
/* 119:138 */       float f1 = (float)vec3.xCoord;
/* 120:139 */       float f2 = (float)vec3.yCoord;
/* 121:140 */       float f3 = (float)vec3.zCoord;
/* 122:143 */       if (mc.gameSettings.anaglyph)
/* 123:    */       {
/* 124:144 */         float f4 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
/* 125:145 */         float f5 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
/* 126:146 */         float f6 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
/* 127:147 */         f1 = f4;
/* 128:148 */         f2 = f5;
/* 129:149 */         f3 = f6;
/* 130:    */       }
/* 131:152 */       GL11.glColor3f(f1, f2, f3);
/* 132:153 */       Tessellator tessellator1 = Tessellator.instance;
/* 133:154 */       GL11.glDepthMask(false);
/* 134:155 */       GL11.glEnable(2912);
/* 135:156 */       GL11.glColor3f(f1, f2, f3);
/* 136:157 */       GL11.glCallList(this.glSkyList);
/* 137:158 */       GL11.glDisable(2912);
/* 138:159 */       GL11.glDisable(3008);
/* 139:160 */       GL11.glEnable(3042);
/* 140:161 */       OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 141:162 */       RenderHelper.disableStandardItemLighting();
/* 142:163 */       float[] afloat = theWorld.provider.calcSunriseSunsetColors(theWorld.getCelestialAngle(p_72714_1_), p_72714_1_);
/* 143:169 */       if (afloat != null)
/* 144:    */       {
/* 145:170 */         GL11.glDisable(3553);
/* 146:171 */         GL11.glShadeModel(7425);
/* 147:172 */         GL11.glPushMatrix();
/* 148:173 */         GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 149:174 */         GL11.glRotatef(MathHelper.sin(theWorld.getCelestialAngleRadians(p_72714_1_)) < 0.0F ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
/* 150:175 */         GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
/* 151:176 */         float f6 = afloat[0];
/* 152:177 */         float f7 = afloat[1];
/* 153:178 */         float f8 = afloat[2];
/* 154:181 */         if (mc.gameSettings.anaglyph)
/* 155:    */         {
/* 156:182 */           float f9 = (f6 * 30.0F + f7 * 59.0F + f8 * 11.0F) / 100.0F;
/* 157:183 */           float f10 = (f6 * 30.0F + f7 * 70.0F) / 100.0F;
/* 158:184 */           float f11 = (f6 * 30.0F + f8 * 70.0F) / 100.0F;
/* 159:185 */           f6 = f9;
/* 160:186 */           f7 = f10;
/* 161:187 */           f8 = f11;
/* 162:    */         }
/* 163:190 */         tessellator1.startDrawing(6);
/* 164:191 */         tessellator1.setColorRGBA_F(f6, f7, f8, afloat[3]);
/* 165:192 */         tessellator1.addVertex(0.0D, 100.0D, 0.0D);
/* 166:193 */         byte b0 = 16;
/* 167:194 */         tessellator1.setColorRGBA_F(afloat[0], afloat[1], afloat[2], 0.0F);
/* 168:196 */         for (int j = 0; j <= b0; j++)
/* 169:    */         {
/* 170:197 */           float f11 = j * 3.141593F * 2.0F / b0;
/* 171:198 */           float f12 = MathHelper.sin(f11);
/* 172:199 */           float f13 = MathHelper.cos(f11);
/* 173:200 */           tessellator1.addVertex(f12 * 120.0F, f13 * 120.0F, -f13 * 40.0F * afloat[3]);
/* 174:    */         }
/* 175:203 */         tessellator1.draw();
/* 176:204 */         GL11.glPopMatrix();
/* 177:205 */         GL11.glShadeModel(7424);
/* 178:    */       }
/* 179:208 */       GL11.glEnable(3553);
/* 180:209 */       OpenGlHelper.glBlendFunc(770, 1, 1, 0);
/* 181:210 */       GL11.glPushMatrix();
/* 182:211 */       float f6 = 1.0F - theWorld.getRainStrength(p_72714_1_);
/* 183:212 */       float f7 = 0.0F;
/* 184:213 */       float f8 = 0.0F;
/* 185:214 */       float f9 = 0.0F;
/* 186:215 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, f6);
/* 187:216 */       GL11.glTranslatef(f7, f8, f9);
/* 188:217 */       GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
/* 189:218 */       GL11.glRotatef(theWorld.getCelestialAngle(p_72714_1_) * 360.0F, 1.0F, 0.0F, 0.0F);
/* 190:219 */       float f10 = 30.0F;
/* 191:    */       
/* 192:    */ 
/* 193:222 */       GL11.glDisable(3553);
/* 194:223 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 195:224 */       GL11.glDisable(3042);
/* 196:225 */       GL11.glEnable(3008);
/* 197:226 */       GL11.glEnable(2912);
/* 198:227 */       GL11.glPopMatrix();
/* 199:228 */       GL11.glDisable(3553);
/* 200:229 */       GL11.glColor3f(0.0F, 0.0F, 0.0F);
/* 201:230 */       double d0 = mc.thePlayer.getPosition(p_72714_1_).yCoord - theWorld.getHorizon();
/* 202:232 */       if (d0 < 0.0D)
/* 203:    */       {
/* 204:233 */         GL11.glPushMatrix();
/* 205:234 */         GL11.glTranslatef(0.0F, 12.0F, 0.0F);
/* 206:235 */         GL11.glCallList(this.glSkyList2);
/* 207:236 */         GL11.glPopMatrix();
/* 208:237 */         f8 = 1.0F;
/* 209:238 */         f9 = -(float)(d0 + 65.0D);
/* 210:239 */         f10 = -f8;
/* 211:240 */         tessellator1.startDrawingQuads();
/* 212:241 */         tessellator1.setColorRGBA_I(0, 255);
/* 213:242 */         tessellator1.addVertex(-f8, f9, f8);
/* 214:243 */         tessellator1.addVertex(f8, f9, f8);
/* 215:244 */         tessellator1.addVertex(f8, f10, f8);
/* 216:245 */         tessellator1.addVertex(-f8, f10, f8);
/* 217:246 */         tessellator1.addVertex(-f8, f10, -f8);
/* 218:247 */         tessellator1.addVertex(f8, f10, -f8);
/* 219:248 */         tessellator1.addVertex(f8, f9, -f8);
/* 220:249 */         tessellator1.addVertex(-f8, f9, -f8);
/* 221:250 */         tessellator1.addVertex(f8, f10, -f8);
/* 222:251 */         tessellator1.addVertex(f8, f10, f8);
/* 223:252 */         tessellator1.addVertex(f8, f9, f8);
/* 224:253 */         tessellator1.addVertex(f8, f9, -f8);
/* 225:254 */         tessellator1.addVertex(-f8, f9, -f8);
/* 226:255 */         tessellator1.addVertex(-f8, f9, f8);
/* 227:256 */         tessellator1.addVertex(-f8, f10, f8);
/* 228:257 */         tessellator1.addVertex(-f8, f10, -f8);
/* 229:258 */         tessellator1.addVertex(-f8, f10, -f8);
/* 230:259 */         tessellator1.addVertex(-f8, f10, f8);
/* 231:260 */         tessellator1.addVertex(f8, f10, f8);
/* 232:261 */         tessellator1.addVertex(f8, f10, -f8);
/* 233:262 */         tessellator1.draw();
/* 234:    */       }
/* 235:265 */       if (theWorld.provider.isSkyColored()) {
/* 236:266 */         GL11.glColor3f(f1 * 0.2F + 0.04F, f2 * 0.2F + 0.04F, f3 * 0.6F + 0.1F);
/* 237:    */       } else {
/* 238:268 */         GL11.glColor3f(f1, f2, f3);
/* 239:    */       }
/* 240:271 */       GL11.glPushMatrix();
/* 241:272 */       GL11.glTranslatef(0.0F, -(float)(d0 - 16.0D), 0.0F);
/* 242:273 */       GL11.glCallList(this.glSkyList2);
/* 243:274 */       GL11.glPopMatrix();
/* 244:275 */       GL11.glEnable(3553);
/* 245:276 */       GL11.glDepthMask(true);
/* 246:    */     }
/* 247:    */   }
/* 248:    */   
/* 249:    */   @SideOnly(Side.CLIENT)
/* 250:    */   public static class NullRenderer
/* 251:    */     extends IRenderHandler
/* 252:    */   {
/* 253:    */     @SideOnly(Side.CLIENT)
/* 254:    */     public void render(float partialTicks, WorldClient world, Minecraft mc) {}
/* 255:    */   }
/* 256:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.worldgen.endoftime.RenderHandlersEndOfTime
 * JD-Core Version:    0.7.0.1
 */