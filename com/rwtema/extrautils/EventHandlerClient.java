/*   1:    */ package com.rwtema.extrautils;
/*   2:    */ 
/*   3:    */ import com.mojang.authlib.GameProfile;
/*   4:    */ import com.rwtema.extrautils.core.CastIterator;
/*   5:    */ import com.rwtema.extrautils.item.ItemBuildersWand;
/*   6:    */ import com.rwtema.extrautils.item.ItemLawSword;
/*   7:    */ import com.rwtema.extrautils.tileentity.TileEntityRainMuffler;
/*   8:    */ import com.rwtema.extrautils.tileentity.TileEntitySoundMuffler;
/*   9:    */ import com.rwtema.extrautils.tileentity.generators.DynamicContainerGenerator;
/*  10:    */ import com.rwtema.extrautils.tileentity.generators.TileEntityGeneratorFurnace;
/*  11:    */ import cpw.mods.fml.common.eventhandler.EventPriority;
/*  12:    */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*  13:    */ import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
/*  14:    */ import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
/*  15:    */ import cpw.mods.fml.relauncher.Side;
/*  16:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.Collection;
/*  19:    */ import java.util.HashMap;
/*  20:    */ import java.util.Iterator;
/*  21:    */ import java.util.List;
/*  22:    */ import java.util.Locale;
/*  23:    */ import java.util.Map;
/*  24:    */ import net.minecraft.block.Block;
/*  25:    */ import net.minecraft.client.Minecraft;
/*  26:    */ import net.minecraft.client.audio.ISound;
/*  27:    */ import net.minecraft.client.audio.ISound.AttenuationType;
/*  28:    */ import net.minecraft.client.audio.ITickableSound;
/*  29:    */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*  30:    */ import net.minecraft.client.model.ModelBiped;
/*  31:    */ import net.minecraft.client.model.ModelRenderer;
/*  32:    */ import net.minecraft.client.multiplayer.WorldClient;
/*  33:    */ import net.minecraft.client.renderer.Tessellator;
/*  34:    */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*  35:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  36:    */ import net.minecraft.client.renderer.texture.TextureMap;
/*  37:    */ import net.minecraft.client.settings.GameSettings;
/*  38:    */ import net.minecraft.entity.Entity;
/*  39:    */ import net.minecraft.entity.EntityLivingBase;
/*  40:    */ import net.minecraft.entity.boss.EntityWither;
/*  41:    */ import net.minecraft.entity.player.EntityPlayer;
/*  42:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  43:    */ import net.minecraft.init.Blocks;
/*  44:    */ import net.minecraft.item.ItemStack;
/*  45:    */ import net.minecraft.nbt.NBTTagCompound;
/*  46:    */ import net.minecraft.tileentity.TileEntity;
/*  47:    */ import net.minecraft.util.AxisAlignedBB;
/*  48:    */ import net.minecraft.util.EnumChatFormatting;
/*  49:    */ import net.minecraft.util.IIcon;
/*  50:    */ import net.minecraft.util.MathHelper;
/*  51:    */ import net.minecraft.util.MovingObjectPosition;
/*  52:    */ import net.minecraft.util.ResourceLocation;
/*  53:    */ import net.minecraft.world.EnumSkyBlock;
/*  54:    */ import net.minecraft.world.World;
/*  55:    */ import net.minecraft.world.WorldProvider;
/*  56:    */ import net.minecraft.world.chunk.Chunk;
/*  57:    */ import net.minecraftforge.client.event.DrawBlockHighlightEvent;
/*  58:    */ import net.minecraftforge.client.event.RenderLivingEvent.Post;
/*  59:    */ import net.minecraftforge.client.event.RenderLivingEvent.Pre;
/*  60:    */ import net.minecraftforge.client.event.RenderPlayerEvent.Specials.Post;
/*  61:    */ import net.minecraftforge.client.event.TextureStitchEvent.Pre;
/*  62:    */ import net.minecraftforge.client.event.sound.PlaySoundEvent17;
/*  63:    */ import net.minecraftforge.event.entity.player.ItemTooltipEvent;
/*  64:    */ import org.lwjgl.opengl.GL11;
/*  65:    */ 
/*  66:    */ @SideOnly(Side.CLIENT)
/*  67:    */ public class EventHandlerClient
/*  68:    */ {
/*  69: 43 */   private static final ResourceLocation[] wing_textures = { null, new ResourceLocation("extrautils", "textures/wing_feather.png"), new ResourceLocation("extrautils", "textures/wing_butterfly.png"), new ResourceLocation("extrautils", "textures/wing_demon.png"), new ResourceLocation("extrautils", "textures/wing_golden.png"), new ResourceLocation("extrautils", "textures/wing_bat.png") };
/*  70: 51 */   public static Map<String, Integer> flyingPlayers = new HashMap();
/*  71: 52 */   public static List<String> holograms = new ArrayList();
/*  72:    */   public boolean resetRender;
/*  73:    */   boolean avoidRecursion;
/*  74:    */   int maxSonarRange;
/*  75:    */   List<ChunkPos> sonar_edges;
/*  76:    */   ChunkPos curTarget;
/*  77:    */   private static float renderTickTime;
/*  78:    */   
/*  79:    */   public EventHandlerClient()
/*  80:    */   {
/*  81: 54 */     this.resetRender = false;
/*  82: 55 */     this.avoidRecursion = false;
/*  83: 56 */     this.maxSonarRange = 4;
/*  84: 57 */     this.sonar_edges = new ArrayList();
/*  85: 58 */     this.curTarget = new ChunkPos(-1, -1, -1);
/*  86:    */   }
/*  87:    */   
/*  88: 60 */   private static long time = 0L;
/*  89:    */   public static IIcon particle;
/*  90:    */   
/*  91:    */   public float getRenderTickTime()
/*  92:    */   {
/*  93: 65 */     return renderTickTime;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public float getRenderTime()
/*  97:    */   {
/*  98: 69 */     return (float)time + renderTickTime;
/*  99:    */   }
/* 100:    */   
/* 101:    */   @SubscribeEvent
/* 102:    */   public void getTimer(TickEvent.ClientTickEvent event)
/* 103:    */   {
/* 104: 74 */     time += 1L;
/* 105:    */   }
/* 106:    */   
/* 107:    */   @SubscribeEvent
/* 108:    */   public void registerParticle(TextureStitchEvent.Pre event)
/* 109:    */   {
/* 110: 79 */     if (event.map.getTextureType() != 0) {
/* 111: 80 */       return;
/* 112:    */     }
/* 113: 81 */     particle = event.map.registerIcon("extrautils:particle");
/* 114:    */   }
/* 115:    */   
/* 116:    */   @SubscribeEvent
/* 117:    */   public void getTimer(TickEvent.RenderTickEvent event)
/* 118:    */   {
/* 119: 86 */     renderTickTime = event.renderTickTime;
/* 120:    */   }
/* 121:    */   
/* 122:    */   @SubscribeEvent
/* 123:    */   public void generatorTooltip(ItemTooltipEvent event)
/* 124:    */   {
/* 125: 91 */     if (!ExtraUtils.generatorEnabled) {
/* 126: 92 */       return;
/* 127:    */     }
/* 128: 94 */     if ((event.entityPlayer == null) || (event.entityPlayer.openContainer == null)) {
/* 129: 95 */       return;
/* 130:    */     }
/* 131: 97 */     if (event.entityPlayer.openContainer.getClass() == DynamicContainerGenerator.class)
/* 132:    */     {
/* 133: 98 */       TileEntityGeneratorFurnace gen = ((DynamicContainerGenerator)event.entityPlayer.openContainer).genFurnace;
/* 134: 99 */       if (gen != null)
/* 135:    */       {
/* 136:100 */         double burn = gen.getFuelBurn(event.itemStack);
/* 137:101 */         if (burn > 0.0D)
/* 138:    */         {
/* 139:102 */           double level = gen.getGenLevelForStack(event.itemStack);
/* 140:103 */           double amount = level * burn;
/* 141:104 */           String s = "Generates ";
/* 142:105 */           if (amount == (int)amount) {
/* 143:106 */             s = s + String.format(Locale.ENGLISH, "%,d", new Object[] { Integer.valueOf((int)amount) });
/* 144:    */           } else {
/* 145:108 */             s = s + String.format(Locale.ENGLISH, "%,.1f", new Object[] { Double.valueOf(amount) });
/* 146:    */           }
/* 147:110 */           s = s + " RF at ";
/* 148:111 */           if (level == (int)level) {
/* 149:112 */             s = s + String.format(Locale.ENGLISH, "%,d", new Object[] { Integer.valueOf((int)level) });
/* 150:    */           } else {
/* 151:114 */             s = s + String.format(Locale.ENGLISH, "%,.1f", new Object[] { Double.valueOf(level) });
/* 152:    */           }
/* 153:116 */           s = s + " RF/T";
/* 154:    */           
/* 155:118 */           event.toolTip.add(s);
/* 156:    */         }
/* 157:    */       }
/* 158:    */     }
/* 159:    */   }
/* 160:    */   
/* 161:    */   @SubscribeEvent
/* 162:    */   public void renderWings(RenderPlayerEvent.Specials.Post event)
/* 163:    */   {
/* 164:126 */     if (flyingPlayers.containsKey(event.entityPlayer.getGameProfile().getName()))
/* 165:    */     {
/* 166:127 */       int tex = ((Integer)flyingPlayers.get(event.entityPlayer.getGameProfile().getName())).intValue();
/* 167:128 */       if ((tex <= 0) || (tex >= wing_textures.length)) {
/* 168:129 */         return;
/* 169:    */       }
/* 170:131 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 171:    */       
/* 172:133 */       GL11.glPushMatrix();
/* 173:    */       
/* 174:135 */       event.renderer.modelBipedMain.bipedBody.postRender(0.0625F);
/* 175:136 */       Minecraft.getMinecraft().renderEngine.bindTexture(wing_textures[tex]);
/* 176:    */       
/* 177:138 */       GL11.glTranslatef(0.0F, -0.3125F, 0.25F);
/* 178:139 */       Tessellator t = Tessellator.instance;
/* 179:    */       
/* 180:141 */       double d = 0.0D;
/* 181:    */       
/* 182:143 */       float a = (1.0F + (float)Math.cos(getRenderTime() / 4.0F)) * 2.0F;
/* 183:144 */       if (event.entityPlayer.capabilities.isFlying) {
/* 184:145 */         a = (1.0F + (float)Math.cos(getRenderTime() / 4.0F)) * 20.0F;
/* 185:    */       }
/* 186:147 */       a += 5.0F;
/* 187:    */       
/* 188:149 */       GL11.glPushMatrix();
/* 189:    */       
/* 190:151 */       GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
/* 191:    */       
/* 192:153 */       GL11.glRotatef(-a, 0.0F, 1.0F, 0.0F);
/* 193:    */       
/* 194:155 */       t.startDrawingQuads();
/* 195:156 */       t.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/* 196:157 */       t.addVertexWithUV(0.0D, 1.0D, 0.0D, 0.0D, 1.0D);
/* 197:158 */       t.addVertexWithUV(1.0D, 1.0D, d, 1.0D, 1.0D);
/* 198:159 */       t.addVertexWithUV(1.0D, 0.0D, d, 1.0D, 0.0D);
/* 199:160 */       t.draw();
/* 200:    */       
/* 201:162 */       GL11.glPopMatrix();
/* 202:163 */       GL11.glPushMatrix();
/* 203:    */       
/* 204:165 */       GL11.glRotatef(a, 0.0F, 1.0F, 0.0F);
/* 205:166 */       GL11.glRotatef(20.0F, 0.0F, 1.0F, 0.0F);
/* 206:167 */       t.startDrawingQuads();
/* 207:168 */       t.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/* 208:169 */       t.addVertexWithUV(0.0D, 1.0D, 0.0D, 0.0D, 1.0D);
/* 209:170 */       t.addVertexWithUV(-1.0D, 1.0D, d, 1.0D, 1.0D);
/* 210:171 */       t.addVertexWithUV(-1.0D, 0.0D, d, 1.0D, 0.0D);
/* 211:172 */       t.draw();
/* 212:    */       
/* 213:174 */       GL11.glPopMatrix();
/* 214:    */       
/* 215:176 */       GL11.glPopMatrix();
/* 216:    */     }
/* 217:    */   }
/* 218:    */   
/* 219:    */   public float min0(float x)
/* 220:    */   {
/* 221:181 */     if (x < 0.0F) {
/* 222:182 */       return 0.0F;
/* 223:    */     }
/* 224:183 */     return x;
/* 225:    */   }
/* 226:    */   
/* 227:186 */   public static final ResourceLocation temaSword = new ResourceLocation("extrautils", "textures/rwtemaSword.png");
/* 228:    */   
/* 229:    */   @SubscribeEvent
/* 230:    */   public void renderSword(RenderPlayerEvent.Specials.Post event)
/* 231:    */   {
/* 232:190 */     if (!"RWTema".equals(event.entityPlayer.getGameProfile().getName())) {
/* 233:191 */       return;
/* 234:    */     }
/* 235:193 */     if (event.entityPlayer.getHideCape()) {
/* 236:194 */       return;
/* 237:    */     }
/* 238:196 */     boolean holdingSword = false;
/* 239:197 */     if ((event.entityPlayer.getHeldItem() != null) && ((event.entityPlayer.getHeldItem().getItem() instanceof ItemLawSword))) {
/* 240:198 */       holdingSword = true;
/* 241:    */     }
/* 242:202 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 243:    */     
/* 244:204 */     GL11.glPushMatrix();
/* 245:    */     
/* 246:206 */     event.renderer.modelBipedMain.bipedBody.postRender(0.0625F);
/* 247:207 */     Minecraft.getMinecraft().renderEngine.bindTexture(temaSword);
/* 248:    */     
/* 249:209 */     GL11.glTranslatef(0.0F, 0.2375F, 0.25F);
/* 250:210 */     Tessellator t = Tessellator.instance;
/* 251:    */     
/* 252:    */ 
/* 253:213 */     GL11.glRotatef(-55.0F, 0.0F, 1.0F, 1.0F);
/* 254:    */     
/* 255:215 */     float h = 87.0F;
/* 256:216 */     float h2 = holdingSword ? 20.0F : 0.0F;
/* 257:217 */     float w = 18.0F;
/* 258:218 */     float w2 = 5.0F;
/* 259:219 */     float w3 = 13.0F;
/* 260:220 */     double u = w2 / w;
/* 261:221 */     float h3 = h2 / h;
/* 262:    */     
/* 263:223 */     GL11.glScalef(1.7F / h, 1.7F / h, 1.7F / h);
/* 264:224 */     GL11.glTranslatef(-w2 / 2.0F, -h / 2.0F, 0.0F);
/* 265:    */     
/* 266:    */ 
/* 267:227 */     GL11.glPushMatrix();
/* 268:    */     
/* 269:229 */     t.startDrawingQuads();
/* 270:    */     
/* 271:    */ 
/* 272:232 */     t.setNormal(0.0F, 0.0F, -1.0F);
/* 273:233 */     t.addVertexWithUV(0.0D, h2, 0.0D, 0.0D, h3);
/* 274:234 */     t.addVertexWithUV(0.0D, h, 0.0D, 0.0D, 1.0D);
/* 275:235 */     t.addVertexWithUV(w2, h, 0.0D, u, 1.0D);
/* 276:236 */     t.addVertexWithUV(w2, h2, 0.0D, u, h3);
/* 277:    */     
/* 278:238 */     t.setNormal(0.0F, 0.0F, 1.0F);
/* 279:239 */     t.addVertexWithUV(w2, h2, w2, u, h3);
/* 280:240 */     t.addVertexWithUV(w2, h, w2, u, 1.0D);
/* 281:241 */     t.addVertexWithUV(0.0D, h, w2, 0.0D, 1.0D);
/* 282:242 */     t.addVertexWithUV(0.0D, h2, w2, 0.0D, h3);
/* 283:    */     
/* 284:244 */     t.setNormal(1.0F, 0.0F, 0.0F);
/* 285:245 */     t.addVertexWithUV(w2, h2, w2, u, h3);
/* 286:246 */     t.addVertexWithUV(w2, h, w2, u, 1.0D);
/* 287:247 */     t.addVertexWithUV(w2, h, 0.0D, 0.0D, 1.0D);
/* 288:248 */     t.addVertexWithUV(w2, h2, 0.0D, 0.0D, h3);
/* 289:    */     
/* 290:250 */     t.setNormal(-1.0F, 0.0F, 0.0F);
/* 291:251 */     t.addVertexWithUV(0.0D, h2, 0.0D, u, h3);
/* 292:252 */     t.addVertexWithUV(0.0D, h, 0.0D, u, 1.0D);
/* 293:253 */     t.addVertexWithUV(0.0D, h, w2, 0.0D, 1.0D);
/* 294:254 */     t.addVertexWithUV(0.0D, h2, w2, 0.0D, h3);
/* 295:256 */     if (!holdingSword)
/* 296:    */     {
/* 297:257 */       t.setNormal(0.0F, -1.0F, 0.0F);
/* 298:258 */       t.addVertexWithUV(0.0D, 0.0D, 0.0D, 9.0F / w, 4.0F / h);
/* 299:259 */       t.addVertexWithUV(w2, 0.0D, 0.0D, 13.0F / w, 8.0F / h);
/* 300:260 */       t.addVertexWithUV(w2, 0.0D, w2, 13.0F / w, 8.0F / h);
/* 301:261 */       t.addVertexWithUV(0.0D, 0.0D, w2, 9.0F / w, 4.0F / h);
/* 302:    */     }
/* 303:264 */     t.setNormal(0.0F, 1.0F, 0.0F);
/* 304:265 */     t.addVertexWithUV(0.0D, h, 0.0D, 9.0F / w, 4.0F / h);
/* 305:266 */     t.addVertexWithUV(w2, h, 0.0D, 13.0F / w, 8.0F / h);
/* 306:267 */     t.addVertexWithUV(w2, h, w2, 13.0F / w, 8.0F / h);
/* 307:268 */     t.addVertexWithUV(0.0D, h, w2, 9.0F / w, 4.0F / h);
/* 308:271 */     if (!holdingSword)
/* 309:    */     {
/* 310:272 */       t.setNormal(0.0F, -1.0F, 0.0F);
/* 311:273 */       t.addVertexWithUV(-3.0D, 16.0D, -3.0D, 6.0F / w, 18.0F / h);
/* 312:274 */       t.addVertexWithUV(8.0D, 16.0D, -3.0D, 17.0F / w, 18.0F / h);
/* 313:275 */       t.addVertexWithUV(8.0D, 16.0D, 8.0D, 17.0F / w, 29.0F / h);
/* 314:276 */       t.addVertexWithUV(-3.0D, 16.0D, 8.0D, 6.0F / w, 29.0F / h);
/* 315:    */       
/* 316:278 */       t.setNormal(0.0F, 1.0F, 0.0F);
/* 317:279 */       t.addVertexWithUV(-3.0D, 20.0D, -3.0D, 6.0F / w, 1.0F / h);
/* 318:280 */       t.addVertexWithUV(8.0D, 20.0D, -3.0D, 17.0F / w, 1.0F / h);
/* 319:281 */       t.addVertexWithUV(8.0D, 20.0D, 8.0D, 17.0F / w, 12.0F / h);
/* 320:282 */       t.addVertexWithUV(-3.0D, 20.0D, 8.0D, 6.0F / w, 12.0F / h);
/* 321:    */       
/* 322:    */ 
/* 323:285 */       t.setNormal(0.0F, 0.0F, -1.0F);
/* 324:286 */       t.addVertexWithUV(-3.0D, 16.0D, -3.0D, u, 12.0F / h);
/* 325:287 */       t.addVertexWithUV(-3.0D, 20.0D, -3.0D, u, 17.0F / h);
/* 326:288 */       t.addVertexWithUV(8.0D, 20.0D, -3.0D, 1.0D, 17.0F / h);
/* 327:289 */       t.addVertexWithUV(8.0D, 16.0D, -3.0D, 1.0D, 12.0F / h);
/* 328:    */       
/* 329:291 */       t.setNormal(0.0F, 0.0F, 1.0F);
/* 330:292 */       t.addVertexWithUV(-3.0D, 16.0D, 8.0D, u, 12.0F / h);
/* 331:293 */       t.addVertexWithUV(-3.0D, 20.0D, 8.0D, u, 17.0F / h);
/* 332:294 */       t.addVertexWithUV(8.0D, 20.0D, 8.0D, 1.0D, 17.0F / h);
/* 333:295 */       t.addVertexWithUV(8.0D, 16.0D, 8.0D, 1.0D, 12.0F / h);
/* 334:    */       
/* 335:297 */       t.setNormal(1.0F, 0.0F, 0.0F);
/* 336:298 */       t.addVertexWithUV(8.0D, 16.0D, 8.0D, u, 12.0F / h);
/* 337:299 */       t.addVertexWithUV(8.0D, 20.0D, 8.0D, u, 17.0F / h);
/* 338:300 */       t.addVertexWithUV(8.0D, 20.0D, -3.0D, 1.0D, 17.0F / h);
/* 339:301 */       t.addVertexWithUV(8.0D, 16.0D, -3.0D, 1.0D, 12.0F / h);
/* 340:    */       
/* 341:303 */       t.setNormal(-1.0F, 0.0F, 0.0F);
/* 342:304 */       t.addVertexWithUV(-3.0D, 16.0D, 8.0D, u, 12.0F / h);
/* 343:305 */       t.addVertexWithUV(-3.0D, 20.0D, 8.0D, u, 17.0F / h);
/* 344:306 */       t.addVertexWithUV(-3.0D, 20.0D, -3.0D, 1.0D, 17.0F / h);
/* 345:307 */       t.addVertexWithUV(-3.0D, 16.0D, -3.0D, 1.0D, 12.0F / h);
/* 346:    */     }
/* 347:309 */     t.draw();
/* 348:    */     
/* 349:    */ 
/* 350:312 */     GL11.glPopMatrix();
/* 351:    */     
/* 352:314 */     GL11.glPopMatrix();
/* 353:    */   }
/* 354:    */   
/* 355:    */   @SubscribeEvent(priority=EventPriority.HIGHEST)
/* 356:    */   public void entityColorRender(RenderLivingEvent.Pre event)
/* 357:    */   {
/* 358:321 */     String s = EnumChatFormatting.getTextWithoutFormattingCodes(event.entity.getCommandSenderName());
/* 359:323 */     if ((s.startsWith("Aureylian")) && (!(event.entity instanceof EntityPlayer)))
/* 360:    */     {
/* 361:326 */       GL11.glColor4f(0.9686F, 0.7059F, 0.8392F, 1.0F);
/* 362:327 */       this.resetRender = true;
/* 363:    */     }
/* 364:330 */     if ((holograms.contains(s)) && ((!(event.entity instanceof EntityPlayer)) || (!((EntityPlayer)event.entity).getHideCape())))
/* 365:    */     {
/* 366:331 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.45F);
/* 367:332 */       GL11.glEnable(3042);
/* 368:333 */       GL11.glBlendFunc(770, 771);
/* 369:334 */       this.resetRender = true;
/* 370:    */     }
/* 371:337 */     if ((s.equals("RWTema")) && (!(event.entity instanceof EntityPlayer)))
/* 372:    */     {
/* 373:340 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.65F);
/* 374:341 */       GL11.glEnable(3042);
/* 375:342 */       GL11.glBlendFunc(770, 771);
/* 376:343 */       this.resetRender = true;
/* 377:    */     }
/* 378:346 */     if ((s.equals("jadedcat")) && ((!(event.entity instanceof EntityPlayer)) || (!((EntityPlayer)event.entity).getHideCape())))
/* 379:    */     {
/* 380:347 */       GL11.glColor4f(0.69F, 0.392F, 0.847F, 1.0F);
/* 381:348 */       this.resetRender = true;
/* 382:    */     }
/* 383:    */   }
/* 384:    */   
/* 385:    */   @SubscribeEvent
/* 386:    */   public void entityColorRender(RenderLivingEvent.Post event)
/* 387:    */   {
/* 388:354 */     if ((!this.avoidRecursion) && (this.resetRender))
/* 389:    */     {
/* 390:355 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 391:356 */       GL11.glDisable(3042);
/* 392:    */     }
/* 393:    */   }
/* 394:    */   
/* 395:    */   @SubscribeEvent
/* 396:    */   public void SonarRender(DrawBlockHighlightEvent event)
/* 397:    */   {
/* 398:363 */     if (Minecraft.getMinecraft().gameSettings.thirdPersonView != 0) {
/* 399:364 */       return;
/* 400:    */     }
/* 401:367 */     if ((ExtraUtils.sonarGoggles == null) || (event.player.getCurrentArmor(3) == null) || (event.player.getCurrentArmor(3).getItem() != ExtraUtils.sonarGoggles)) {
/* 402:368 */       return;
/* 403:    */     }
/* 404:371 */     Block id = event.player.worldObj.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ);
/* 405:373 */     if (id == Blocks.air) {
/* 406:374 */       return;
/* 407:    */     }
/* 408:377 */     if (!id.isOpaqueCube()) {
/* 409:378 */       return;
/* 410:    */     }
/* 411:383 */     int x = MathHelper.floor_double(event.player.chunkCoordX);
/* 412:384 */     int y = MathHelper.floor_double(event.player.chunkCoordY);
/* 413:385 */     int z = MathHelper.floor_double(event.player.chunkCoordZ);
/* 414:    */     
/* 415:    */ 
/* 416:    */ 
/* 417:    */ 
/* 418:    */ 
/* 419:    */ 
/* 420:    */ 
/* 421:    */ 
/* 422:394 */     double transparency = 1.0D - (event.player.worldObj.getSkyBlockTypeBrightness(EnumSkyBlock.Block, x, y, z) + event.player.worldObj.getSkyBlockTypeBrightness(EnumSkyBlock.Sky, x, y, z) - event.player.worldObj.calculateSkylightSubtracted(1.0F)) / 12.0D;
/* 423:401 */     if (transparency <= 0.0D) {
/* 424:402 */       return;
/* 425:    */     }
/* 426:405 */     GL11.glEnable(3042);
/* 427:406 */     GL11.glBlendFunc(770, 771);
/* 428:407 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.35F);
/* 429:408 */     GL11.glLineWidth(3.0F);
/* 430:409 */     GL11.glDisable(3553);
/* 431:410 */     GL11.glDisable(2929);
/* 432:411 */     GL11.glShadeModel(7425);
/* 433:412 */     double px = event.player.lastTickPosX + (event.player.chunkCoordX - event.player.lastTickPosX) * event.partialTicks;
/* 434:413 */     double py = event.player.lastTickPosY + (event.player.chunkCoordY - event.player.lastTickPosY) * event.partialTicks;
/* 435:414 */     double pz = event.player.lastTickPosZ + (event.player.chunkCoordZ - event.player.lastTickPosZ) * event.partialTicks;
/* 436:415 */     GL11.glTranslated(-px, -py, -pz);
/* 437:417 */     if ((this.curTarget.x != event.target.blockX) || (this.curTarget.y != event.target.blockY) || (this.curTarget.z != event.target.blockZ))
/* 438:    */     {
/* 439:418 */       this.curTarget = new ChunkPos(event.target.blockX, event.target.blockY, event.target.blockZ);
/* 440:419 */       this.sonar_edges.clear();
/* 441:420 */       List<ChunkPos> blocks = new ArrayList();
/* 442:421 */       blocks.add(this.curTarget);
/* 443:423 */       for (int i = 0; i < blocks.size(); i++)
/* 444:    */       {
/* 445:424 */         int j = 0;
/* 446:425 */         boolean[] s = new boolean[27];
/* 447:427 */         for (int dy = -1; dy <= 1; dy++) {
/* 448:428 */           for (int dx = -1; dx <= 1; dx++) {
/* 449:429 */             for (int dz = -1; dz <= 1; dz++)
/* 450:    */             {
/* 451:430 */               s[j] = false;
/* 452:431 */               ChunkPos b = new ChunkPos(((ChunkPos)blocks.get(i)).x + dx, ((ChunkPos)blocks.get(i)).y + dy, ((ChunkPos)blocks.get(i)).z + dz);
/* 453:433 */               if (!blocks.contains(b))
/* 454:    */               {
/* 455:434 */                 if ((dist(b.x - event.target.blockX, b.y - event.target.blockY, b.z - event.target.blockZ) < this.maxSonarRange) && 
/* 456:435 */                   (event.player.worldObj.getBlock(b.x, b.y, b.z) == id))
/* 457:    */                 {
/* 458:436 */                   if (((dx == 0) && (dy == 0)) || ((dz == 0) && (dy == 0)) || ((dx == 0) && (dz == 0))) {
/* 459:437 */                     blocks.add(b);
/* 460:    */                   }
/* 461:440 */                   s[j] = true;
/* 462:    */                 }
/* 463:    */               }
/* 464:    */               else {
/* 465:444 */                 s[j] = true;
/* 466:    */               }
/* 467:447 */               j++;
/* 468:    */             }
/* 469:    */           }
/* 470:    */         }
/* 471:452 */         int x = ((ChunkPos)blocks.get(i)).x;int y = ((ChunkPos)blocks.get(i)).y;int z = ((ChunkPos)blocks.get(i)).z;
/* 472:454 */         if (d(s[10], s[12], s[9]))
/* 473:    */         {
/* 474:456 */           this.sonar_edges.add(new ChunkPos(x, y, z));
/* 475:457 */           this.sonar_edges.add(new ChunkPos(x, y + 1, z));
/* 476:    */         }
/* 477:460 */         if (d(s[10], s[14], s[11]))
/* 478:    */         {
/* 479:462 */           this.sonar_edges.add(new ChunkPos(x, y, z + 1));
/* 480:463 */           this.sonar_edges.add(new ChunkPos(x, y + 1, z + 1));
/* 481:    */         }
/* 482:466 */         if (d(s[16], s[12], s[15]))
/* 483:    */         {
/* 484:468 */           this.sonar_edges.add(new ChunkPos(x + 1, y, z));
/* 485:469 */           this.sonar_edges.add(new ChunkPos(x + 1, y + 1, z));
/* 486:    */         }
/* 487:472 */         if (d(s[16], s[14], s[17]))
/* 488:    */         {
/* 489:474 */           this.sonar_edges.add(new ChunkPos(x + 1, y, z + 1));
/* 490:475 */           this.sonar_edges.add(new ChunkPos(x + 1, y + 1, z + 1));
/* 491:    */         }
/* 492:478 */         if (d(s[22], s[10], s[19]))
/* 493:    */         {
/* 494:480 */           this.sonar_edges.add(new ChunkPos(x, y + 1, z));
/* 495:481 */           this.sonar_edges.add(new ChunkPos(x, y + 1, z + 1));
/* 496:    */         }
/* 497:484 */         if (d(s[22], s[16], s[25]))
/* 498:    */         {
/* 499:486 */           this.sonar_edges.add(new ChunkPos(x + 1, y + 1, z));
/* 500:487 */           this.sonar_edges.add(new ChunkPos(x + 1, y + 1, z + 1));
/* 501:    */         }
/* 502:490 */         if (d(s[22], s[12], s[21]))
/* 503:    */         {
/* 504:492 */           this.sonar_edges.add(new ChunkPos(x, y + 1, z));
/* 505:493 */           this.sonar_edges.add(new ChunkPos(x + 1, y + 1, z));
/* 506:    */         }
/* 507:496 */         if (d(s[22], s[14], s[23]))
/* 508:    */         {
/* 509:498 */           this.sonar_edges.add(new ChunkPos(x, y + 1, z + 1));
/* 510:499 */           this.sonar_edges.add(new ChunkPos(x + 1, y + 1, z + 1));
/* 511:    */         }
/* 512:502 */         if (d(s[4], s[10], s[1]))
/* 513:    */         {
/* 514:504 */           this.sonar_edges.add(new ChunkPos(x, y, z));
/* 515:505 */           this.sonar_edges.add(new ChunkPos(x, y, z + 1));
/* 516:    */         }
/* 517:508 */         if (d(s[4], s[16], s[7]))
/* 518:    */         {
/* 519:510 */           this.sonar_edges.add(new ChunkPos(x + 1, y, z));
/* 520:511 */           this.sonar_edges.add(new ChunkPos(x + 1, y, z + 1));
/* 521:    */         }
/* 522:514 */         if (d(s[4], s[12], s[3]))
/* 523:    */         {
/* 524:516 */           this.sonar_edges.add(new ChunkPos(x, y, z));
/* 525:517 */           this.sonar_edges.add(new ChunkPos(x + 1, y, z));
/* 526:    */         }
/* 527:520 */         if (d(s[4], s[14], s[5]))
/* 528:    */         {
/* 529:522 */           this.sonar_edges.add(new ChunkPos(x, y, z + 1));
/* 530:523 */           this.sonar_edges.add(new ChunkPos(x + 1, y, z + 1));
/* 531:    */         }
/* 532:    */       }
/* 533:    */     }
/* 534:528 */     Tessellator t = Tessellator.instance;
/* 535:529 */     t.startDrawing(1);
/* 536:530 */     t.setColorRGBA(255, 255, 255, 255);
/* 537:532 */     for (ChunkPos sonar_edge : this.sonar_edges)
/* 538:    */     {
/* 539:533 */       t.setColorRGBA(255, 255, 255, (int)(transparency * (155.0D - 100.0D * dist(sonar_edge.x - px, sonar_edge.y - py, sonar_edge.z - pz) / (this.maxSonarRange + 1))));
/* 540:534 */       t.addVertex(sonar_edge.x, sonar_edge.y, sonar_edge.z);
/* 541:    */     }
/* 542:537 */     t.draw();
/* 543:538 */     GL11.glShadeModel(7424);
/* 544:539 */     GL11.glEnable(3553);
/* 545:540 */     GL11.glEnable(2929);
/* 546:541 */     GL11.glDisable(3042);
/* 547:542 */     GL11.glTranslated(px, py, pz);
/* 548:    */   }
/* 549:    */   
/* 550:    */   public boolean d(boolean side1, boolean side2, boolean corner)
/* 551:    */   {
/* 552:546 */     return ((!side1) && (!side2)) || ((!corner) && (side1) && (side2));
/* 553:    */   }
/* 554:    */   
/* 555:    */   public double dist(double x, double y, double z)
/* 556:    */   {
/* 557:550 */     return (int)Math.max(Math.max(Math.abs(x), Math.abs(y)), Math.abs(z));
/* 558:    */   }
/* 559:    */   
/* 560:    */   @SubscribeEvent
/* 561:    */   public void witherSoundKilling(PlaySoundEvent17 event)
/* 562:    */   {
/* 563:555 */     if (!ExtraUtils.disableWitherNoiseUnlessNearby) {
/* 564:556 */       return;
/* 565:    */     }
/* 566:558 */     if (!"mob.wither.spawn".equals(event.name)) {
/* 567:559 */       return;
/* 568:    */     }
/* 569:562 */     for (Entity e : new CastIterator(Minecraft.getMinecraft().theWorld.getLoadedEntityList())) {
/* 570:563 */       if (e.getClass() == EntityWither.class) {
/* 571:564 */         return;
/* 572:    */       }
/* 573:    */     }
/* 574:568 */     event.result = null;
/* 575:    */   }
/* 576:    */   
/* 577:    */   @SubscribeEvent
/* 578:    */   public void rainKiller(PlaySoundEvent17 event)
/* 579:    */   {
/* 580:573 */     if ((Minecraft.getMinecraft().theWorld != null) && (Minecraft.getMinecraft().thePlayer != null) && (ExtraUtils.soundMuffler != null))
/* 581:    */     {
/* 582:575 */       if ((event == null) || (!"ambient.weather.rain".equals(event.name))) {
/* 583:576 */         return;
/* 584:    */       }
/* 585:579 */       World world = Minecraft.getMinecraft().theWorld;
/* 586:580 */       NBTTagCompound tags = new NBTTagCompound();
/* 587:582 */       if (Minecraft.getMinecraft().thePlayer.getEntityData().hasKey("PlayerPersisted")) {
/* 588:583 */         tags = Minecraft.getMinecraft().thePlayer.getEntityData().getCompoundTag("PlayerPersisted");
/* 589:    */       } else {
/* 590:585 */         Minecraft.getMinecraft().thePlayer.getEntityData().setTag("PlayerPersisted", tags);
/* 591:    */       }
/* 592:588 */       boolean force = (tags.hasKey("ExtraUtilities|Rain")) && (tags.getBoolean("ExtraUtilities|Rain"));
/* 593:590 */       if ((!force) && (TileEntityRainMuffler.playerNeedsMuffler))
/* 594:    */       {
/* 595:591 */         TileEntityRainMuffler.playerNeedsMufflerInstantCheck = false;
/* 596:    */       }
/* 597:    */       else
/* 598:    */       {
/* 599:593 */         event.result = null;
/* 600:595 */         if (force) {
/* 601:596 */           return;
/* 602:    */         }
/* 603:599 */         boolean flag = false;
/* 604:601 */         if (world.provider.dimensionId != TileEntityRainMuffler.curDimension) {
/* 605:602 */           flag = true;
/* 606:603 */         } else if (!(world.getTileEntity(TileEntityRainMuffler.curX, TileEntityRainMuffler.curY, TileEntityRainMuffler.curZ) instanceof TileEntityRainMuffler)) {
/* 607:604 */           flag = true;
/* 608:605 */         } else if (world.getTileEntity(TileEntityRainMuffler.curX, TileEntityRainMuffler.curY, TileEntityRainMuffler.curZ).getDistanceFrom(Minecraft.getMinecraft().thePlayer.chunkCoordX, Minecraft.getMinecraft().thePlayer.chunkCoordX, Minecraft.getMinecraft().thePlayer.chunkCoordZ) > 4096.0D) {
/* 609:607 */           flag = true;
/* 610:    */         }
/* 611:610 */         if (flag)
/* 612:    */         {
/* 613:611 */           TileEntityRainMuffler.playerNeedsMuffler = true;
/* 614:612 */           TileEntityRainMuffler.playerNeedsMufflerInstantCheck = true;
/* 615:613 */           TileEntityRainMuffler.curDimension = -100;
/* 616:    */         }
/* 617:    */       }
/* 618:    */     }
/* 619:    */   }
/* 620:    */   
/* 621:    */   @SubscribeEvent
/* 622:    */   public void soundMufflerPlay(PlaySoundEvent17 event)
/* 623:    */   {
/* 624:621 */     if ((Minecraft.getMinecraft().theWorld != null) && (ExtraUtils.soundMuffler != null) && (event.result != null))
/* 625:    */     {
/* 626:622 */       World world = Minecraft.getMinecraft().theWorld;
/* 627:624 */       if ((event.result instanceof ITickableSound)) {
/* 628:625 */         return;
/* 629:    */       }
/* 630:627 */       float x = event.result.func_147649_g();
/* 631:628 */       float y = event.result.func_147654_h();
/* 632:629 */       float z = event.result.func_147651_i();
/* 633:631 */       for (int dx = (int)Math.floor(x - 8.0F) >> 4; dx <= (int)Math.floor(x + 8.0F) >> 4; dx++) {
/* 634:632 */         for (int dz = (int)Math.floor(z - 8.0F) >> 4; dz <= (int)Math.floor(z + 8.0F) >> 4; dz++)
/* 635:    */         {
/* 636:633 */           Iterator var18 = world.getChunkFromChunkCoords(dx, dz).chunkTileEntityMap.values().iterator();
/* 637:635 */           while (var18.hasNext())
/* 638:    */           {
/* 639:636 */             TileEntity var22 = (TileEntity)var18.next();
/* 640:638 */             if ((var22 instanceof TileEntitySoundMuffler)) {
/* 641:639 */               if (var22.getBlockMetadata() != 1)
/* 642:    */               {
/* 643:642 */                 double d = (var22.xCoord + 0.5D - x) * (var22.xCoord + 0.5D - x) + (var22.yCoord + 0.5D - y) * (var22.yCoord + 0.5D - y) + (var22.zCoord + 0.5D - z) * (var22.zCoord + 0.5D - z);
/* 644:645 */                 if ((d <= 64.0D) && (d > 0.0D))
/* 645:    */                 {
/* 646:648 */                   event.result = new SoundMuffled(event.result);
/* 647:    */                   
/* 648:650 */                   d = Math.sqrt(d);
/* 649:652 */                   if (d != 0.0D) {
/* 650:653 */                     d = 1.0D / d / 8.0D;
/* 651:    */                   }
/* 652:656 */                   world.spawnParticle("smoke", x, y, z, (var22.xCoord + 0.5D - x) * d, (var22.yCoord + 0.5D - y) * d, (var22.zCoord + 0.5D - z) * d);
/* 653:    */                 }
/* 654:    */               }
/* 655:    */             }
/* 656:    */           }
/* 657:    */         }
/* 658:    */       }
/* 659:    */     }
/* 660:    */   }
/* 661:    */   
/* 662:    */   public class SoundMuffled
/* 663:    */     implements ISound
/* 664:    */   {
/* 665:    */     protected final ISound sound;
/* 666:    */     
/* 667:    */     public SoundMuffled(ISound sound)
/* 668:    */     {
/* 669:669 */       this.sound = sound;
/* 670:    */     }
/* 671:    */     
/* 672:    */     public ResourceLocation func_147650_b()
/* 673:    */     {
/* 674:674 */       return this.sound.func_147650_b();
/* 675:    */     }
/* 676:    */     
/* 677:    */     public boolean func_147657_c()
/* 678:    */     {
/* 679:679 */       return this.sound.func_147657_c();
/* 680:    */     }
/* 681:    */     
/* 682:    */     public int func_147652_d()
/* 683:    */     {
/* 684:684 */       return this.sound.func_147652_d();
/* 685:    */     }
/* 686:    */     
/* 687:    */     public float func_147653_e()
/* 688:    */     {
/* 689:689 */       return this.sound.func_147653_e() / 10.0F;
/* 690:    */     }
/* 691:    */     
/* 692:    */     public float func_147655_f()
/* 693:    */     {
/* 694:694 */       return this.sound.func_147655_f();
/* 695:    */     }
/* 696:    */     
/* 697:    */     public float func_147649_g()
/* 698:    */     {
/* 699:699 */       return this.sound.func_147649_g();
/* 700:    */     }
/* 701:    */     
/* 702:    */     public float func_147654_h()
/* 703:    */     {
/* 704:704 */       return this.sound.func_147654_h();
/* 705:    */     }
/* 706:    */     
/* 707:    */     public float func_147651_i()
/* 708:    */     {
/* 709:709 */       return this.sound.func_147651_i();
/* 710:    */     }
/* 711:    */     
/* 712:    */     public ISound.AttenuationType func_147656_j()
/* 713:    */     {
/* 714:714 */       return this.sound.func_147656_j();
/* 715:    */     }
/* 716:    */   }
/* 717:    */   
/* 718:    */   @SubscribeEvent
/* 719:    */   public void BuildersWandRender(DrawBlockHighlightEvent event)
/* 720:    */   {
/* 721:720 */     if ((event.currentItem != null) && 
/* 722:721 */       ((event.currentItem.getItem() instanceof ItemBuildersWand)))
/* 723:    */     {
/* 724:722 */       List<ChunkPos> blocks = ((ItemBuildersWand)event.currentItem.getItem()).getPotentialBlocks(event.player, event.player.worldObj, event.target.blockX, event.target.blockY, event.target.blockZ, event.target.sideHit);
/* 725:    */       
/* 726:724 */       Block blockId = event.player.worldObj.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ);
/* 727:726 */       if (((blockId != Blocks.air ? 1 : 0) & (blocks.size() > 0 ? 1 : 0)) != 0)
/* 728:    */       {
/* 729:727 */         GL11.glEnable(3042);
/* 730:728 */         GL11.glBlendFunc(770, 771);
/* 731:729 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.35F);
/* 732:730 */         GL11.glLineWidth(3.0F);
/* 733:731 */         GL11.glDisable(3553);
/* 734:732 */         GL11.glDisable(2929);
/* 735:733 */         GL11.glDepthMask(false);
/* 736:734 */         double px = event.player.lastTickPosX + (event.player.posX - event.player.lastTickPosX) * event.partialTicks;
/* 737:735 */         double py = event.player.lastTickPosY + (event.player.posY - event.player.lastTickPosY) * event.partialTicks;
/* 738:736 */         double pz = event.player.lastTickPosZ + (event.player.posZ - event.player.lastTickPosZ) * event.partialTicks;
/* 739:    */         
/* 740:738 */         GL11.glTranslated(-px, -py, -pz);
/* 741:742 */         for (ChunkPos temp : blocks) {
/* 742:743 */           drawOutlinedBoundingBox(AxisAlignedBB.getBoundingBox(temp.x, temp.y, temp.z, temp.x + 1, temp.y + 1, temp.z + 1));
/* 743:    */         }
/* 744:746 */         GL11.glDepthMask(true);
/* 745:747 */         GL11.glEnable(3553);
/* 746:748 */         GL11.glDisable(3042);
/* 747:749 */         GL11.glEnable(2929);
/* 748:    */         
/* 749:    */ 
/* 750:    */ 
/* 751:    */ 
/* 752:754 */         GL11.glTranslated(px, py, pz);
/* 753:755 */         event.setCanceled(true);
/* 754:    */       }
/* 755:    */     }
/* 756:    */   }
/* 757:    */   
/* 758:    */   private void drawOutlinedBoundingBox(AxisAlignedBB par1AxisAlignedBB)
/* 759:    */   {
/* 760:768 */     Tessellator var2 = Tessellator.instance;
/* 761:769 */     var2.startDrawing(3);
/* 762:770 */     var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
/* 763:771 */     var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
/* 764:772 */     var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
/* 765:773 */     var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
/* 766:774 */     var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
/* 767:775 */     var2.draw();
/* 768:776 */     var2.startDrawing(3);
/* 769:777 */     var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
/* 770:778 */     var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
/* 771:779 */     var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
/* 772:780 */     var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
/* 773:781 */     var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
/* 774:782 */     var2.draw();
/* 775:783 */     var2.startDrawing(1);
/* 776:784 */     var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
/* 777:785 */     var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
/* 778:786 */     var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
/* 779:787 */     var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
/* 780:788 */     var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
/* 781:789 */     var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
/* 782:790 */     var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
/* 783:791 */     var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
/* 784:792 */     var2.draw();
/* 785:    */   }
/* 786:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.EventHandlerClient
 * JD-Core Version:    0.7.0.1
 */