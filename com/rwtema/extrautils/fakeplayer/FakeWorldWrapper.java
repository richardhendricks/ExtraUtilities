/*    1:     */ package com.rwtema.extrautils.fakeplayer;
/*    2:     */ 
/*    3:     */ import com.google.common.collect.ImmutableSetMultimap;
/*    4:     */ import cpw.mods.fml.relauncher.Side;
/*    5:     */ import cpw.mods.fml.relauncher.SideOnly;
/*    6:     */ import java.util.Calendar;
/*    7:     */ import java.util.Collection;
/*    8:     */ import java.util.List;
/*    9:     */ import java.util.Random;
/*   10:     */ import net.minecraft.block.Block;
/*   11:     */ import net.minecraft.block.material.Material;
/*   12:     */ import net.minecraft.command.IEntitySelector;
/*   13:     */ import net.minecraft.crash.CrashReport;
/*   14:     */ import net.minecraft.crash.CrashReportCategory;
/*   15:     */ import net.minecraft.entity.Entity;
/*   16:     */ import net.minecraft.entity.EnumCreatureType;
/*   17:     */ import net.minecraft.entity.player.EntityPlayer;
/*   18:     */ import net.minecraft.item.ItemStack;
/*   19:     */ import net.minecraft.nbt.NBTTagCompound;
/*   20:     */ import net.minecraft.pathfinding.PathEntity;
/*   21:     */ import net.minecraft.scoreboard.Scoreboard;
/*   22:     */ import net.minecraft.tileentity.TileEntity;
/*   23:     */ import net.minecraft.util.AxisAlignedBB;
/*   24:     */ import net.minecraft.util.ChunkCoordinates;
/*   25:     */ import net.minecraft.util.MovingObjectPosition;
/*   26:     */ import net.minecraft.util.Vec3;
/*   27:     */ import net.minecraft.world.ChunkCoordIntPair;
/*   28:     */ import net.minecraft.world.ChunkPosition;
/*   29:     */ import net.minecraft.world.EnumSkyBlock;
/*   30:     */ import net.minecraft.world.Explosion;
/*   31:     */ import net.minecraft.world.GameRules;
/*   32:     */ import net.minecraft.world.IBlockAccess;
/*   33:     */ import net.minecraft.world.IWorldAccess;
/*   34:     */ import net.minecraft.world.MinecraftException;
/*   35:     */ import net.minecraft.world.World;
/*   36:     */ import net.minecraft.world.WorldProvider;
/*   37:     */ import net.minecraft.world.WorldSavedData;
/*   38:     */ import net.minecraft.world.WorldSettings;
/*   39:     */ import net.minecraft.world.biome.BiomeGenBase;
/*   40:     */ import net.minecraft.world.biome.WorldChunkManager;
/*   41:     */ import net.minecraft.world.chunk.Chunk;
/*   42:     */ import net.minecraft.world.chunk.IChunkProvider;
/*   43:     */ import net.minecraft.world.storage.ISaveHandler;
/*   44:     */ import net.minecraft.world.storage.WorldInfo;
/*   45:     */ import net.minecraftforge.common.ForgeChunkManager.Ticket;
/*   46:     */ import net.minecraftforge.common.util.ForgeDirection;
/*   47:     */ 
/*   48:     */ public class FakeWorldWrapper
/*   49:     */   extends FakeWorld
/*   50:     */ {
/*   51:     */   World world;
/*   52:     */   
/*   53:     */   public FakeWorldWrapper(World world)
/*   54:     */   {
/*   55:  44 */     this.world = world;
/*   56:     */   }
/*   57:     */   
/*   58:     */   public BiomeGenBase getBiomeGenForCoords(int par1, int par2)
/*   59:     */   {
/*   60:  50 */     return this.world.getBiomeGenForCoords(par1, par2);
/*   61:     */   }
/*   62:     */   
/*   63:     */   public BiomeGenBase getBiomeGenForCoordsBody(int par1, int par2)
/*   64:     */   {
/*   65:  55 */     return this.world.getBiomeGenForCoordsBody(par1, par2);
/*   66:     */   }
/*   67:     */   
/*   68:     */   public WorldChunkManager getWorldChunkManager()
/*   69:     */   {
/*   70:  60 */     return this.world.getWorldChunkManager();
/*   71:     */   }
/*   72:     */   
/*   73:     */   protected IChunkProvider createChunkProvider()
/*   74:     */   {
/*   75:  66 */     return null;
/*   76:     */   }
/*   77:     */   
/*   78:     */   public void initialize(WorldSettings par1WorldSettings) {}
/*   79:     */   
/*   80:     */   @SideOnly(Side.CLIENT)
/*   81:     */   public void setSpawnLocation()
/*   82:     */   {
/*   83:  77 */     this.world.setSpawnLocation();
/*   84:     */   }
/*   85:     */   
/*   86:     */   public Block getTopBlock(int p_147474_1_, int p_147474_2_)
/*   87:     */   {
/*   88:  82 */     return this.world.getTopBlock(p_147474_1_, p_147474_2_);
/*   89:     */   }
/*   90:     */   
/*   91:     */   public Block getBlock(int p_147439_1_, int p_147439_2_, int p_147439_3_)
/*   92:     */   {
/*   93:  87 */     return this.world.getBlock(p_147439_1_, p_147439_2_, p_147439_3_);
/*   94:     */   }
/*   95:     */   
/*   96:     */   public boolean isAirBlock(int p_147437_1_, int p_147437_2_, int p_147437_3_)
/*   97:     */   {
/*   98:  92 */     return this.world.isAirBlock(p_147437_1_, p_147437_2_, p_147437_3_);
/*   99:     */   }
/*  100:     */   
/*  101:     */   public boolean blockExists(int par1, int par2, int par3)
/*  102:     */   {
/*  103:  97 */     return this.world.blockExists(par1, par2, par3);
/*  104:     */   }
/*  105:     */   
/*  106:     */   public boolean doChunksNearChunkExist(int par1, int par2, int par3, int par4)
/*  107:     */   {
/*  108: 102 */     return this.world.doChunksNearChunkExist(par1, par2, par3, par4);
/*  109:     */   }
/*  110:     */   
/*  111:     */   public boolean checkChunksExist(int par1, int par2, int par3, int par4, int par5, int par6)
/*  112:     */   {
/*  113: 107 */     return this.world.checkChunksExist(par1, par2, par3, par4, par5, par6);
/*  114:     */   }
/*  115:     */   
/*  116:     */   public boolean chunkExists(int par1, int par2)
/*  117:     */   {
/*  118: 112 */     return this.world.getChunkProvider().chunkExists(par1, par2);
/*  119:     */   }
/*  120:     */   
/*  121:     */   public Chunk getChunkFromBlockCoords(int par1, int par2)
/*  122:     */   {
/*  123: 117 */     return this.world.getChunkFromBlockCoords(par1, par2);
/*  124:     */   }
/*  125:     */   
/*  126:     */   public Chunk getChunkFromChunkCoords(int par1, int par2)
/*  127:     */   {
/*  128: 122 */     return this.world.getChunkFromChunkCoords(par1, par2);
/*  129:     */   }
/*  130:     */   
/*  131:     */   public boolean setBlock(int p_147465_1_, int p_147465_2_, int p_147465_3_, Block p_147465_4_, int p_147465_5_, int p_147465_6_)
/*  132:     */   {
/*  133: 127 */     return this.world.setBlock(p_147465_1_, p_147465_2_, p_147465_3_, p_147465_4_, p_147465_5_, p_147465_6_);
/*  134:     */   }
/*  135:     */   
/*  136:     */   public int getBlockMetadata(int par1, int par2, int par3)
/*  137:     */   {
/*  138: 132 */     return this.world.getBlockMetadata(par1, par2, par3);
/*  139:     */   }
/*  140:     */   
/*  141:     */   public boolean setBlockMetadataWithNotify(int par1, int par2, int par3, int par4, int par5)
/*  142:     */   {
/*  143: 137 */     return this.world.setBlockMetadataWithNotify(par1, par2, par3, par4, par5);
/*  144:     */   }
/*  145:     */   
/*  146:     */   public boolean setBlockToAir(int p_147468_1_, int p_147468_2_, int p_147468_3_)
/*  147:     */   {
/*  148: 142 */     return this.world.setBlockToAir(p_147468_1_, p_147468_2_, p_147468_3_);
/*  149:     */   }
/*  150:     */   
/*  151:     */   public boolean func_147480_a(int p_147480_1_, int p_147480_2_, int p_147480_3_, boolean p_147480_4_)
/*  152:     */   {
/*  153: 147 */     return this.world.func_147480_a(p_147480_1_, p_147480_2_, p_147480_3_, p_147480_4_);
/*  154:     */   }
/*  155:     */   
/*  156:     */   public boolean setBlock(int p_147449_1_, int p_147449_2_, int p_147449_3_, Block p_147449_4_)
/*  157:     */   {
/*  158: 152 */     return this.world.setBlock(p_147449_1_, p_147449_2_, p_147449_3_, p_147449_4_);
/*  159:     */   }
/*  160:     */   
/*  161:     */   public void markBlockForUpdate(int p_147471_1_, int p_147471_2_, int p_147471_3_)
/*  162:     */   {
/*  163: 157 */     this.world.markBlockForUpdate(p_147471_1_, p_147471_2_, p_147471_3_);
/*  164:     */   }
/*  165:     */   
/*  166:     */   public void notifyBlockChange(int p_147444_1_, int p_147444_2_, int p_147444_3_, Block p_147444_4_)
/*  167:     */   {
/*  168: 162 */     this.world.notifyBlockChange(p_147444_1_, p_147444_2_, p_147444_3_, p_147444_4_);
/*  169:     */   }
/*  170:     */   
/*  171:     */   public void markBlocksDirtyVertical(int par1, int par2, int par3, int par4)
/*  172:     */   {
/*  173: 167 */     this.world.markBlocksDirtyVertical(par1, par2, par3, par4);
/*  174:     */   }
/*  175:     */   
/*  176:     */   public void markBlockRangeForRenderUpdate(int p_147458_1_, int p_147458_2_, int p_147458_3_, int p_147458_4_, int p_147458_5_, int p_147458_6_)
/*  177:     */   {
/*  178: 172 */     this.world.markBlockRangeForRenderUpdate(p_147458_1_, p_147458_2_, p_147458_3_, p_147458_4_, p_147458_5_, p_147458_6_);
/*  179:     */   }
/*  180:     */   
/*  181:     */   public void notifyBlocksOfNeighborChange(int p_147459_1_, int p_147459_2_, int p_147459_3_, Block p_147459_4_)
/*  182:     */   {
/*  183: 177 */     this.world.notifyBlocksOfNeighborChange(p_147459_1_, p_147459_2_, p_147459_3_, p_147459_4_);
/*  184:     */   }
/*  185:     */   
/*  186:     */   public void func_147441_b(int p_147441_1_, int p_147441_2_, int p_147441_3_, Block p_147441_4_, int p_147441_5_)
/*  187:     */   {
/*  188: 182 */     this.world.func_147441_b(p_147441_1_, p_147441_2_, p_147441_3_, p_147441_4_, p_147441_5_);
/*  189:     */   }
/*  190:     */   
/*  191:     */   public void func_147460_e(int p_147460_1_, int p_147460_2_, int p_147460_3_, Block p_147460_4_)
/*  192:     */   {
/*  193: 187 */     this.world.func_147460_e(p_147460_1_, p_147460_2_, p_147460_3_, p_147460_4_);
/*  194:     */   }
/*  195:     */   
/*  196:     */   public boolean func_147477_a(int p_147477_1_, int p_147477_2_, int p_147477_3_, Block p_147477_4_)
/*  197:     */   {
/*  198: 192 */     return this.world.func_147477_a(p_147477_1_, p_147477_2_, p_147477_3_, p_147477_4_);
/*  199:     */   }
/*  200:     */   
/*  201:     */   public boolean canBlockSeeTheSky(int par1, int par2, int par3)
/*  202:     */   {
/*  203: 197 */     return this.world.canBlockSeeTheSky(par1, par2, par3);
/*  204:     */   }
/*  205:     */   
/*  206:     */   public int getFullBlockLightValue(int par1, int par2, int par3)
/*  207:     */   {
/*  208: 202 */     return this.world.getFullBlockLightValue(par1, par2, par3);
/*  209:     */   }
/*  210:     */   
/*  211:     */   public int getBlockLightValue(int par1, int par2, int par3)
/*  212:     */   {
/*  213: 207 */     return this.world.getBlockLightValue(par1, par2, par3);
/*  214:     */   }
/*  215:     */   
/*  216:     */   public int getBlockLightValue_do(int par1, int par2, int par3, boolean par4)
/*  217:     */   {
/*  218: 212 */     return this.world.getBlockLightValue_do(par1, par2, par3, par4);
/*  219:     */   }
/*  220:     */   
/*  221:     */   public int getHeightValue(int par1, int par2)
/*  222:     */   {
/*  223: 217 */     return this.world.getHeightValue(par1, par2);
/*  224:     */   }
/*  225:     */   
/*  226:     */   public int getChunkHeightMapMinimum(int par1, int par2)
/*  227:     */   {
/*  228: 222 */     return this.world.getChunkHeightMapMinimum(par1, par2);
/*  229:     */   }
/*  230:     */   
/*  231:     */   @SideOnly(Side.CLIENT)
/*  232:     */   public int getSkyBlockTypeBrightness(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4)
/*  233:     */   {
/*  234: 228 */     return this.world.getSkyBlockTypeBrightness(par1EnumSkyBlock, par2, par3, par4);
/*  235:     */   }
/*  236:     */   
/*  237:     */   public int getSavedLightValue(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4)
/*  238:     */   {
/*  239: 233 */     return this.world.getSavedLightValue(par1EnumSkyBlock, par2, par3, par4);
/*  240:     */   }
/*  241:     */   
/*  242:     */   public void setLightValue(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4, int par5)
/*  243:     */   {
/*  244: 238 */     this.world.setLightValue(par1EnumSkyBlock, par2, par3, par4, par5);
/*  245:     */   }
/*  246:     */   
/*  247:     */   public void func_147479_m(int p_147479_1_, int p_147479_2_, int p_147479_3_)
/*  248:     */   {
/*  249: 243 */     this.world.func_147479_m(p_147479_1_, p_147479_2_, p_147479_3_);
/*  250:     */   }
/*  251:     */   
/*  252:     */   @SideOnly(Side.CLIENT)
/*  253:     */   public int getLightBrightnessForSkyBlocks(int par1, int par2, int par3, int par4)
/*  254:     */   {
/*  255: 249 */     return this.world.getLightBrightnessForSkyBlocks(par1, par2, par3, par4);
/*  256:     */   }
/*  257:     */   
/*  258:     */   public float getLightBrightness(int par1, int par2, int par3)
/*  259:     */   {
/*  260: 254 */     return this.world.getLightBrightness(par1, par2, par3);
/*  261:     */   }
/*  262:     */   
/*  263:     */   public boolean isDaytime()
/*  264:     */   {
/*  265: 259 */     return this.world.isDaytime();
/*  266:     */   }
/*  267:     */   
/*  268:     */   public MovingObjectPosition rayTraceBlocks(Vec3 par1Vec3, Vec3 par2Vec3)
/*  269:     */   {
/*  270: 264 */     return this.world.rayTraceBlocks(par1Vec3, par2Vec3);
/*  271:     */   }
/*  272:     */   
/*  273:     */   public MovingObjectPosition rayTraceBlocks(Vec3 par1Vec3, Vec3 par2Vec3, boolean par3)
/*  274:     */   {
/*  275: 269 */     return this.world.rayTraceBlocks(par1Vec3, par2Vec3, par3);
/*  276:     */   }
/*  277:     */   
/*  278:     */   public MovingObjectPosition func_147447_a(Vec3 p_147447_1_, Vec3 p_147447_2_, boolean p_147447_3_, boolean p_147447_4_, boolean p_147447_5_)
/*  279:     */   {
/*  280: 274 */     return this.world.func_147447_a(p_147447_1_, p_147447_2_, p_147447_3_, p_147447_4_, p_147447_5_);
/*  281:     */   }
/*  282:     */   
/*  283:     */   public void playSoundAtEntity(Entity par1Entity, String par2Str, float par3, float par4)
/*  284:     */   {
/*  285: 279 */     this.world.playSoundAtEntity(par1Entity, par2Str, par3, par4);
/*  286:     */   }
/*  287:     */   
/*  288:     */   public void playSoundToNearExcept(EntityPlayer par1EntityPlayer, String par2Str, float par3, float par4)
/*  289:     */   {
/*  290: 284 */     this.world.playSoundToNearExcept(par1EntityPlayer, par2Str, par3, par4);
/*  291:     */   }
/*  292:     */   
/*  293:     */   public void playSoundEffect(double par1, double par3, double par5, String par7Str, float par8, float par9)
/*  294:     */   {
/*  295: 289 */     this.world.playSoundEffect(par1, par3, par5, par7Str, par8, par9);
/*  296:     */   }
/*  297:     */   
/*  298:     */   public void playSound(double par1, double par3, double par5, String par7Str, float par8, float par9, boolean par10)
/*  299:     */   {
/*  300: 294 */     this.world.playSound(par1, par3, par5, par7Str, par8, par9, par10);
/*  301:     */   }
/*  302:     */   
/*  303:     */   public void playRecord(String par1Str, int par2, int par3, int par4)
/*  304:     */   {
/*  305: 299 */     this.world.playRecord(par1Str, par2, par3, par4);
/*  306:     */   }
/*  307:     */   
/*  308:     */   public void spawnParticle(String par1Str, double par2, double par4, double par6, double par8, double par10, double par12)
/*  309:     */   {
/*  310: 304 */     this.world.spawnParticle(par1Str, par2, par4, par6, par8, par10, par12);
/*  311:     */   }
/*  312:     */   
/*  313:     */   public boolean addWeatherEffect(Entity par1Entity)
/*  314:     */   {
/*  315: 309 */     return this.world.addWeatherEffect(par1Entity);
/*  316:     */   }
/*  317:     */   
/*  318:     */   public boolean spawnEntityInWorld(Entity par1Entity)
/*  319:     */   {
/*  320: 314 */     return this.world.spawnEntityInWorld(par1Entity);
/*  321:     */   }
/*  322:     */   
/*  323:     */   public void onEntityAdded(Entity par1Entity)
/*  324:     */   {
/*  325: 319 */     this.world.onEntityAdded(par1Entity);
/*  326:     */   }
/*  327:     */   
/*  328:     */   public void onEntityRemoved(Entity par1Entity)
/*  329:     */   {
/*  330: 324 */     this.world.onEntityRemoved(par1Entity);
/*  331:     */   }
/*  332:     */   
/*  333:     */   public void removeEntity(Entity par1Entity)
/*  334:     */   {
/*  335: 329 */     this.world.removeEntity(par1Entity);
/*  336:     */   }
/*  337:     */   
/*  338:     */   public void removePlayerEntityDangerously(Entity par1Entity)
/*  339:     */   {
/*  340: 334 */     this.world.removePlayerEntityDangerously(par1Entity);
/*  341:     */   }
/*  342:     */   
/*  343:     */   public void addWorldAccess(IWorldAccess par1IWorldAccess)
/*  344:     */   {
/*  345: 339 */     this.world.addWorldAccess(par1IWorldAccess);
/*  346:     */   }
/*  347:     */   
/*  348:     */   public List getCollidingBoundingBoxes(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB)
/*  349:     */   {
/*  350: 344 */     return this.world.getCollidingBoundingBoxes(par1Entity, par2AxisAlignedBB);
/*  351:     */   }
/*  352:     */   
/*  353:     */   public List func_147461_a(AxisAlignedBB p_147461_1_)
/*  354:     */   {
/*  355: 349 */     return this.world.func_147461_a(p_147461_1_);
/*  356:     */   }
/*  357:     */   
/*  358:     */   public int calculateSkylightSubtracted(float par1)
/*  359:     */   {
/*  360: 354 */     return this.world.calculateSkylightSubtracted(par1);
/*  361:     */   }
/*  362:     */   
/*  363:     */   public void removeWorldAccess(IWorldAccess par1IWorldAccess)
/*  364:     */   {
/*  365: 359 */     this.world.removeWorldAccess(par1IWorldAccess);
/*  366:     */   }
/*  367:     */   
/*  368:     */   @SideOnly(Side.CLIENT)
/*  369:     */   public float getSunBrightness(float par1)
/*  370:     */   {
/*  371: 365 */     return this.world.getSunBrightness(par1);
/*  372:     */   }
/*  373:     */   
/*  374:     */   @SideOnly(Side.CLIENT)
/*  375:     */   public Vec3 getSkyColor(Entity par1Entity, float par2)
/*  376:     */   {
/*  377: 371 */     return this.world.getSkyColor(par1Entity, par2);
/*  378:     */   }
/*  379:     */   
/*  380:     */   @SideOnly(Side.CLIENT)
/*  381:     */   public Vec3 getSkyColorBody(Entity par1Entity, float par2)
/*  382:     */   {
/*  383: 377 */     return this.world.getSkyColorBody(par1Entity, par2);
/*  384:     */   }
/*  385:     */   
/*  386:     */   public float getCelestialAngle(float par1)
/*  387:     */   {
/*  388: 382 */     return this.world.getCelestialAngle(par1);
/*  389:     */   }
/*  390:     */   
/*  391:     */   @SideOnly(Side.CLIENT)
/*  392:     */   public int getMoonPhase()
/*  393:     */   {
/*  394: 388 */     return this.world.getMoonPhase();
/*  395:     */   }
/*  396:     */   
/*  397:     */   public float getCurrentMoonPhaseFactor()
/*  398:     */   {
/*  399: 393 */     return this.world.getCurrentMoonPhaseFactor();
/*  400:     */   }
/*  401:     */   
/*  402:     */   public float getCelestialAngleRadians(float par1)
/*  403:     */   {
/*  404: 398 */     return this.world.getCelestialAngleRadians(par1);
/*  405:     */   }
/*  406:     */   
/*  407:     */   @SideOnly(Side.CLIENT)
/*  408:     */   public Vec3 getCloudColour(float par1)
/*  409:     */   {
/*  410: 404 */     return this.world.getCloudColour(par1);
/*  411:     */   }
/*  412:     */   
/*  413:     */   @SideOnly(Side.CLIENT)
/*  414:     */   public Vec3 drawCloudsBody(float par1)
/*  415:     */   {
/*  416: 410 */     return this.world.drawCloudsBody(par1);
/*  417:     */   }
/*  418:     */   
/*  419:     */   @SideOnly(Side.CLIENT)
/*  420:     */   public Vec3 getFogColor(float par1)
/*  421:     */   {
/*  422: 416 */     return this.world.getFogColor(par1);
/*  423:     */   }
/*  424:     */   
/*  425:     */   public int getPrecipitationHeight(int par1, int par2)
/*  426:     */   {
/*  427: 421 */     return this.world.getPrecipitationHeight(par1, par2);
/*  428:     */   }
/*  429:     */   
/*  430:     */   public int getTopSolidOrLiquidBlock(int par1, int par2)
/*  431:     */   {
/*  432: 426 */     return this.world.getTopSolidOrLiquidBlock(par1, par2);
/*  433:     */   }
/*  434:     */   
/*  435:     */   @SideOnly(Side.CLIENT)
/*  436:     */   public float getStarBrightness(float par1)
/*  437:     */   {
/*  438: 432 */     return this.world.getStarBrightness(par1);
/*  439:     */   }
/*  440:     */   
/*  441:     */   @SideOnly(Side.CLIENT)
/*  442:     */   public float getStarBrightnessBody(float par1)
/*  443:     */   {
/*  444: 438 */     return this.world.getStarBrightnessBody(par1);
/*  445:     */   }
/*  446:     */   
/*  447:     */   public void scheduleBlockUpdate(int p_147464_1_, int p_147464_2_, int p_147464_3_, Block p_147464_4_, int p_147464_5_)
/*  448:     */   {
/*  449: 443 */     this.world.scheduleBlockUpdate(p_147464_1_, p_147464_2_, p_147464_3_, p_147464_4_, p_147464_5_);
/*  450:     */   }
/*  451:     */   
/*  452:     */   public void func_147454_a(int p_147454_1_, int p_147454_2_, int p_147454_3_, Block p_147454_4_, int p_147454_5_, int p_147454_6_)
/*  453:     */   {
/*  454: 448 */     this.world.func_147454_a(p_147454_1_, p_147454_2_, p_147454_3_, p_147454_4_, p_147454_5_, p_147454_6_);
/*  455:     */   }
/*  456:     */   
/*  457:     */   public void func_147446_b(int p_147446_1_, int p_147446_2_, int p_147446_3_, Block p_147446_4_, int p_147446_5_, int p_147446_6_)
/*  458:     */   {
/*  459: 453 */     this.world.func_147446_b(p_147446_1_, p_147446_2_, p_147446_3_, p_147446_4_, p_147446_5_, p_147446_6_);
/*  460:     */   }
/*  461:     */   
/*  462:     */   public void updateEntities()
/*  463:     */   {
/*  464: 458 */     this.world.updateEntities();
/*  465:     */   }
/*  466:     */   
/*  467:     */   public void func_147448_a(Collection p_147448_1_)
/*  468:     */   {
/*  469: 463 */     this.world.func_147448_a(p_147448_1_);
/*  470:     */   }
/*  471:     */   
/*  472:     */   public void updateEntity(Entity par1Entity)
/*  473:     */   {
/*  474: 468 */     this.world.updateEntity(par1Entity);
/*  475:     */   }
/*  476:     */   
/*  477:     */   public void updateEntityWithOptionalForce(Entity par1Entity, boolean par2)
/*  478:     */   {
/*  479: 473 */     this.world.updateEntityWithOptionalForce(par1Entity, par2);
/*  480:     */   }
/*  481:     */   
/*  482:     */   public boolean checkNoEntityCollision(AxisAlignedBB par1AxisAlignedBB)
/*  483:     */   {
/*  484: 478 */     return this.world.checkNoEntityCollision(par1AxisAlignedBB);
/*  485:     */   }
/*  486:     */   
/*  487:     */   public boolean checkNoEntityCollision(AxisAlignedBB par1AxisAlignedBB, Entity par2Entity)
/*  488:     */   {
/*  489: 483 */     return this.world.checkNoEntityCollision(par1AxisAlignedBB, par2Entity);
/*  490:     */   }
/*  491:     */   
/*  492:     */   public boolean checkBlockCollision(AxisAlignedBB par1AxisAlignedBB)
/*  493:     */   {
/*  494: 488 */     return this.world.checkBlockCollision(par1AxisAlignedBB);
/*  495:     */   }
/*  496:     */   
/*  497:     */   public boolean isAnyLiquid(AxisAlignedBB par1AxisAlignedBB)
/*  498:     */   {
/*  499: 493 */     return this.world.isAnyLiquid(par1AxisAlignedBB);
/*  500:     */   }
/*  501:     */   
/*  502:     */   public boolean func_147470_e(AxisAlignedBB p_147470_1_)
/*  503:     */   {
/*  504: 498 */     return this.world.func_147470_e(p_147470_1_);
/*  505:     */   }
/*  506:     */   
/*  507:     */   public boolean handleMaterialAcceleration(AxisAlignedBB par1AxisAlignedBB, Material par2Material, Entity par3Entity)
/*  508:     */   {
/*  509: 503 */     return this.world.handleMaterialAcceleration(par1AxisAlignedBB, par2Material, par3Entity);
/*  510:     */   }
/*  511:     */   
/*  512:     */   public boolean isMaterialInBB(AxisAlignedBB par1AxisAlignedBB, Material par2Material)
/*  513:     */   {
/*  514: 508 */     return this.world.isMaterialInBB(par1AxisAlignedBB, par2Material);
/*  515:     */   }
/*  516:     */   
/*  517:     */   public boolean isAABBInMaterial(AxisAlignedBB par1AxisAlignedBB, Material par2Material)
/*  518:     */   {
/*  519: 513 */     return this.world.isAABBInMaterial(par1AxisAlignedBB, par2Material);
/*  520:     */   }
/*  521:     */   
/*  522:     */   public Explosion createExplosion(Entity par1Entity, double par2, double par4, double par6, float par8, boolean par9)
/*  523:     */   {
/*  524: 518 */     return this.world.createExplosion(par1Entity, par2, par4, par6, par8, par9);
/*  525:     */   }
/*  526:     */   
/*  527:     */   public Explosion newExplosion(Entity par1Entity, double par2, double par4, double par6, float par8, boolean par9, boolean par10)
/*  528:     */   {
/*  529: 523 */     return this.world.newExplosion(par1Entity, par2, par4, par6, par8, par9, par10);
/*  530:     */   }
/*  531:     */   
/*  532:     */   public float getBlockDensity(Vec3 par1Vec3, AxisAlignedBB par2AxisAlignedBB)
/*  533:     */   {
/*  534: 528 */     return this.world.getBlockDensity(par1Vec3, par2AxisAlignedBB);
/*  535:     */   }
/*  536:     */   
/*  537:     */   public boolean extinguishFire(EntityPlayer par1EntityPlayer, int par2, int par3, int par4, int par5)
/*  538:     */   {
/*  539: 533 */     return this.world.extinguishFire(par1EntityPlayer, par2, par3, par4, par5);
/*  540:     */   }
/*  541:     */   
/*  542:     */   @SideOnly(Side.CLIENT)
/*  543:     */   public String getDebugLoadedEntities()
/*  544:     */   {
/*  545: 539 */     return this.world.getDebugLoadedEntities();
/*  546:     */   }
/*  547:     */   
/*  548:     */   @SideOnly(Side.CLIENT)
/*  549:     */   public String getProviderName()
/*  550:     */   {
/*  551: 545 */     return this.world.getProviderName();
/*  552:     */   }
/*  553:     */   
/*  554:     */   public TileEntity getTileEntity(int p_147438_1_, int p_147438_2_, int p_147438_3_)
/*  555:     */   {
/*  556: 550 */     return this.world.getTileEntity(p_147438_1_, p_147438_2_, p_147438_3_);
/*  557:     */   }
/*  558:     */   
/*  559:     */   public void setTileEntity(int p_147455_1_, int p_147455_2_, int p_147455_3_, TileEntity p_147455_4_)
/*  560:     */   {
/*  561: 555 */     this.world.setTileEntity(p_147455_1_, p_147455_2_, p_147455_3_, p_147455_4_);
/*  562:     */   }
/*  563:     */   
/*  564:     */   public void removeTileEntity(int p_147475_1_, int p_147475_2_, int p_147475_3_)
/*  565:     */   {
/*  566: 560 */     this.world.removeTileEntity(p_147475_1_, p_147475_2_, p_147475_3_);
/*  567:     */   }
/*  568:     */   
/*  569:     */   public void func_147457_a(TileEntity p_147457_1_)
/*  570:     */   {
/*  571: 565 */     this.world.func_147457_a(p_147457_1_);
/*  572:     */   }
/*  573:     */   
/*  574:     */   public boolean func_147469_q(int p_147469_1_, int p_147469_2_, int p_147469_3_)
/*  575:     */   {
/*  576: 570 */     return this.world.func_147469_q(p_147469_1_, p_147469_2_, p_147469_3_);
/*  577:     */   }
/*  578:     */   
/*  579:     */   public static boolean doesBlockHaveSolidTopSurface(IBlockAccess p_147466_0_, int p_147466_1_, int p_147466_2_, int p_147466_3_)
/*  580:     */   {
/*  581: 574 */     return World.doesBlockHaveSolidTopSurface(p_147466_0_, p_147466_1_, p_147466_2_, p_147466_3_);
/*  582:     */   }
/*  583:     */   
/*  584:     */   public boolean isBlockNormalCubeDefault(int p_147445_1_, int p_147445_2_, int p_147445_3_, boolean p_147445_4_)
/*  585:     */   {
/*  586: 579 */     return this.world.isBlockNormalCubeDefault(p_147445_1_, p_147445_2_, p_147445_3_, p_147445_4_);
/*  587:     */   }
/*  588:     */   
/*  589:     */   public void calculateInitialSkylight()
/*  590:     */   {
/*  591: 584 */     this.world.calculateInitialSkylight();
/*  592:     */   }
/*  593:     */   
/*  594:     */   public void setAllowedSpawnTypes(boolean par1, boolean par2)
/*  595:     */   {
/*  596: 589 */     this.world.setAllowedSpawnTypes(par1, par2);
/*  597:     */   }
/*  598:     */   
/*  599:     */   public void tick()
/*  600:     */   {
/*  601: 594 */     this.world.tick();
/*  602:     */   }
/*  603:     */   
/*  604:     */   public void calculateInitialWeatherBody()
/*  605:     */   {
/*  606: 599 */     this.world.calculateInitialWeatherBody();
/*  607:     */   }
/*  608:     */   
/*  609:     */   public void updateWeather()
/*  610:     */   {
/*  611: 604 */     this.world.provider.updateWeather();
/*  612:     */   }
/*  613:     */   
/*  614:     */   public void updateWeatherBody()
/*  615:     */   {
/*  616: 609 */     this.world.updateWeatherBody();
/*  617:     */   }
/*  618:     */   
/*  619:     */   public boolean isBlockFreezable(int par1, int par2, int par3)
/*  620:     */   {
/*  621: 615 */     return this.world.isBlockFreezable(par1, par2, par3);
/*  622:     */   }
/*  623:     */   
/*  624:     */   public boolean isBlockFreezableNaturally(int par1, int par2, int par3)
/*  625:     */   {
/*  626: 620 */     return this.world.isBlockFreezableNaturally(par1, par2, par3);
/*  627:     */   }
/*  628:     */   
/*  629:     */   public boolean canBlockFreeze(int par1, int par2, int par3, boolean par4)
/*  630:     */   {
/*  631: 625 */     return this.world.canBlockFreeze(par1, par2, par3, par4);
/*  632:     */   }
/*  633:     */   
/*  634:     */   public boolean canBlockFreezeBody(int par1, int par2, int par3, boolean par4)
/*  635:     */   {
/*  636: 630 */     return this.world.canBlockFreezeBody(par1, par2, par3, par4);
/*  637:     */   }
/*  638:     */   
/*  639:     */   public boolean func_147478_e(int p_147478_1_, int p_147478_2_, int p_147478_3_, boolean p_147478_4_)
/*  640:     */   {
/*  641: 635 */     return this.world.func_147478_e(p_147478_1_, p_147478_2_, p_147478_3_, p_147478_4_);
/*  642:     */   }
/*  643:     */   
/*  644:     */   public boolean canSnowAtBody(int p_147478_1_, int p_147478_2_, int p_147478_3_, boolean p_147478_4_)
/*  645:     */   {
/*  646: 640 */     return this.world.canSnowAtBody(p_147478_1_, p_147478_2_, p_147478_3_, p_147478_4_);
/*  647:     */   }
/*  648:     */   
/*  649:     */   public boolean func_147451_t(int p_147451_1_, int p_147451_2_, int p_147451_3_)
/*  650:     */   {
/*  651: 645 */     return this.world.func_147451_t(p_147451_1_, p_147451_2_, p_147451_3_);
/*  652:     */   }
/*  653:     */   
/*  654:     */   public boolean updateLightByType(EnumSkyBlock p_147463_1_, int p_147463_2_, int p_147463_3_, int p_147463_4_)
/*  655:     */   {
/*  656: 650 */     return this.world.updateLightByType(p_147463_1_, p_147463_2_, p_147463_3_, p_147463_4_);
/*  657:     */   }
/*  658:     */   
/*  659:     */   public boolean tickUpdates(boolean par1)
/*  660:     */   {
/*  661: 655 */     return this.world.tickUpdates(par1);
/*  662:     */   }
/*  663:     */   
/*  664:     */   public List getPendingBlockUpdates(Chunk par1Chunk, boolean par2)
/*  665:     */   {
/*  666: 660 */     return this.world.getPendingBlockUpdates(par1Chunk, par2);
/*  667:     */   }
/*  668:     */   
/*  669:     */   public List getEntitiesWithinAABBExcludingEntity(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB)
/*  670:     */   {
/*  671: 665 */     return this.world.getEntitiesWithinAABBExcludingEntity(par1Entity, par2AxisAlignedBB);
/*  672:     */   }
/*  673:     */   
/*  674:     */   public List getEntitiesWithinAABBExcludingEntity(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB, IEntitySelector par3IEntitySelector)
/*  675:     */   {
/*  676: 670 */     return this.world.getEntitiesWithinAABBExcludingEntity(par1Entity, par2AxisAlignedBB, par3IEntitySelector);
/*  677:     */   }
/*  678:     */   
/*  679:     */   public List getEntitiesWithinAABB(Class par1Class, AxisAlignedBB par2AxisAlignedBB)
/*  680:     */   {
/*  681: 675 */     return this.world.getEntitiesWithinAABB(par1Class, par2AxisAlignedBB);
/*  682:     */   }
/*  683:     */   
/*  684:     */   public List selectEntitiesWithinAABB(Class par1Class, AxisAlignedBB par2AxisAlignedBB, IEntitySelector par3IEntitySelector)
/*  685:     */   {
/*  686: 680 */     return this.world.selectEntitiesWithinAABB(par1Class, par2AxisAlignedBB, par3IEntitySelector);
/*  687:     */   }
/*  688:     */   
/*  689:     */   public Entity findNearestEntityWithinAABB(Class par1Class, AxisAlignedBB par2AxisAlignedBB, Entity par3Entity)
/*  690:     */   {
/*  691: 685 */     return this.world.findNearestEntityWithinAABB(par1Class, par2AxisAlignedBB, par3Entity);
/*  692:     */   }
/*  693:     */   
/*  694:     */   public Entity getEntityByID(int var1)
/*  695:     */   {
/*  696: 690 */     return null;
/*  697:     */   }
/*  698:     */   
/*  699:     */   @SideOnly(Side.CLIENT)
/*  700:     */   public List getLoadedEntityList()
/*  701:     */   {
/*  702: 696 */     return this.world.getLoadedEntityList();
/*  703:     */   }
/*  704:     */   
/*  705:     */   public void func_147476_b(int p_147476_1_, int p_147476_2_, int p_147476_3_, TileEntity p_147476_4_)
/*  706:     */   {
/*  707: 701 */     this.world.func_147476_b(p_147476_1_, p_147476_2_, p_147476_3_, p_147476_4_);
/*  708:     */   }
/*  709:     */   
/*  710:     */   public int countEntities(Class par1Class)
/*  711:     */   {
/*  712: 706 */     return this.world.countEntities(par1Class);
/*  713:     */   }
/*  714:     */   
/*  715:     */   public void addLoadedEntities(List par1List)
/*  716:     */   {
/*  717: 711 */     this.world.addLoadedEntities(par1List);
/*  718:     */   }
/*  719:     */   
/*  720:     */   public void unloadEntities(List par1List)
/*  721:     */   {
/*  722: 716 */     this.world.unloadEntities(par1List);
/*  723:     */   }
/*  724:     */   
/*  725:     */   public boolean canPlaceEntityOnSide(Block p_147472_1_, int p_147472_2_, int p_147472_3_, int p_147472_4_, boolean p_147472_5_, int p_147472_6_, Entity p_147472_7_, ItemStack p_147472_8_)
/*  726:     */   {
/*  727: 721 */     return this.world.canPlaceEntityOnSide(p_147472_1_, p_147472_2_, p_147472_3_, p_147472_4_, p_147472_5_, p_147472_6_, p_147472_7_, p_147472_8_);
/*  728:     */   }
/*  729:     */   
/*  730:     */   public PathEntity getPathEntityToEntity(Entity par1Entity, Entity par2Entity, float par3, boolean par4, boolean par5, boolean par6, boolean par7)
/*  731:     */   {
/*  732: 726 */     return this.world.getPathEntityToEntity(par1Entity, par2Entity, par3, par4, par5, par6, par7);
/*  733:     */   }
/*  734:     */   
/*  735:     */   public PathEntity getEntityPathToXYZ(Entity par1Entity, int par2, int par3, int par4, float par5, boolean par6, boolean par7, boolean par8, boolean par9)
/*  736:     */   {
/*  737: 731 */     return this.world.getEntityPathToXYZ(par1Entity, par2, par3, par4, par5, par6, par7, par8, par9);
/*  738:     */   }
/*  739:     */   
/*  740:     */   public int isBlockProvidingPowerTo(int par1, int par2, int par3, int par4)
/*  741:     */   {
/*  742: 736 */     return this.world.isBlockProvidingPowerTo(par1, par2, par3, par4);
/*  743:     */   }
/*  744:     */   
/*  745:     */   public int getBlockPowerInput(int par1, int par2, int par3)
/*  746:     */   {
/*  747: 741 */     return this.world.getBlockPowerInput(par1, par2, par3);
/*  748:     */   }
/*  749:     */   
/*  750:     */   public boolean getIndirectPowerOutput(int par1, int par2, int par3, int par4)
/*  751:     */   {
/*  752: 746 */     return this.world.getIndirectPowerOutput(par1, par2, par3, par4);
/*  753:     */   }
/*  754:     */   
/*  755:     */   public int getIndirectPowerLevelTo(int par1, int par2, int par3, int par4)
/*  756:     */   {
/*  757: 751 */     return this.world.getIndirectPowerLevelTo(par1, par2, par3, par4);
/*  758:     */   }
/*  759:     */   
/*  760:     */   public boolean isBlockIndirectlyGettingPowered(int par1, int par2, int par3)
/*  761:     */   {
/*  762: 756 */     return this.world.isBlockIndirectlyGettingPowered(par1, par2, par3);
/*  763:     */   }
/*  764:     */   
/*  765:     */   public int getStrongestIndirectPower(int par1, int par2, int par3)
/*  766:     */   {
/*  767: 761 */     return this.world.getStrongestIndirectPower(par1, par2, par3);
/*  768:     */   }
/*  769:     */   
/*  770:     */   public EntityPlayer getClosestPlayerToEntity(Entity par1Entity, double par2)
/*  771:     */   {
/*  772: 766 */     return this.world.getClosestPlayerToEntity(par1Entity, par2);
/*  773:     */   }
/*  774:     */   
/*  775:     */   public EntityPlayer getClosestPlayer(double par1, double par3, double par5, double par7)
/*  776:     */   {
/*  777: 771 */     return this.world.getClosestPlayer(par1, par3, par5, par7);
/*  778:     */   }
/*  779:     */   
/*  780:     */   public EntityPlayer getClosestVulnerablePlayerToEntity(Entity par1Entity, double par2)
/*  781:     */   {
/*  782: 776 */     return this.world.getClosestVulnerablePlayerToEntity(par1Entity, par2);
/*  783:     */   }
/*  784:     */   
/*  785:     */   public EntityPlayer getClosestVulnerablePlayer(double par1, double par3, double par5, double par7)
/*  786:     */   {
/*  787: 781 */     return this.world.getClosestVulnerablePlayer(par1, par3, par5, par7);
/*  788:     */   }
/*  789:     */   
/*  790:     */   public EntityPlayer getPlayerEntityByName(String par1Str)
/*  791:     */   {
/*  792: 786 */     return this.world.getPlayerEntityByName(par1Str);
/*  793:     */   }
/*  794:     */   
/*  795:     */   @SideOnly(Side.CLIENT)
/*  796:     */   public void sendQuittingDisconnectingPacket()
/*  797:     */   {
/*  798: 792 */     this.world.sendQuittingDisconnectingPacket();
/*  799:     */   }
/*  800:     */   
/*  801:     */   public void checkSessionLock()
/*  802:     */     throws MinecraftException
/*  803:     */   {
/*  804: 797 */     this.world.checkSessionLock();
/*  805:     */   }
/*  806:     */   
/*  807:     */   @SideOnly(Side.CLIENT)
/*  808:     */   public void func_82738_a(long par1)
/*  809:     */   {
/*  810: 803 */     this.world.func_82738_a(par1);
/*  811:     */   }
/*  812:     */   
/*  813:     */   public long getSeed()
/*  814:     */   {
/*  815: 808 */     return this.world.getSeed();
/*  816:     */   }
/*  817:     */   
/*  818:     */   public long getTotalWorldTime()
/*  819:     */   {
/*  820: 813 */     return this.world.getTotalWorldTime();
/*  821:     */   }
/*  822:     */   
/*  823:     */   public long getWorldTime()
/*  824:     */   {
/*  825: 818 */     return this.world.getWorldTime();
/*  826:     */   }
/*  827:     */   
/*  828:     */   public void setWorldTime(long par1)
/*  829:     */   {
/*  830: 823 */     this.world.setWorldTime(par1);
/*  831:     */   }
/*  832:     */   
/*  833:     */   public ChunkCoordinates getSpawnPoint()
/*  834:     */   {
/*  835: 828 */     return this.world.getSpawnPoint();
/*  836:     */   }
/*  837:     */   
/*  838:     */   public void setSpawnLocation(int par1, int par2, int par3)
/*  839:     */   {
/*  840: 833 */     this.world.setSpawnLocation(par1, par2, par3);
/*  841:     */   }
/*  842:     */   
/*  843:     */   @SideOnly(Side.CLIENT)
/*  844:     */   public void joinEntityInSurroundings(Entity par1Entity)
/*  845:     */   {
/*  846: 839 */     this.world.joinEntityInSurroundings(par1Entity);
/*  847:     */   }
/*  848:     */   
/*  849:     */   public boolean canMineBlock(EntityPlayer par1EntityPlayer, int par2, int par3, int par4)
/*  850:     */   {
/*  851: 844 */     return this.world.canMineBlock(par1EntityPlayer, par2, par3, par4);
/*  852:     */   }
/*  853:     */   
/*  854:     */   public boolean canMineBlockBody(EntityPlayer par1EntityPlayer, int par2, int par3, int par4)
/*  855:     */   {
/*  856: 849 */     return this.world.canMineBlockBody(par1EntityPlayer, par2, par3, par4);
/*  857:     */   }
/*  858:     */   
/*  859:     */   public void setEntityState(Entity par1Entity, byte par2)
/*  860:     */   {
/*  861: 854 */     this.world.setEntityState(par1Entity, par2);
/*  862:     */   }
/*  863:     */   
/*  864:     */   public IChunkProvider getChunkProvider()
/*  865:     */   {
/*  866: 859 */     return this.world.getChunkProvider();
/*  867:     */   }
/*  868:     */   
/*  869:     */   public void func_147452_c(int p_147452_1_, int p_147452_2_, int p_147452_3_, Block p_147452_4_, int p_147452_5_, int p_147452_6_)
/*  870:     */   {
/*  871: 864 */     this.world.func_147452_c(p_147452_1_, p_147452_2_, p_147452_3_, p_147452_4_, p_147452_5_, p_147452_6_);
/*  872:     */   }
/*  873:     */   
/*  874:     */   public ISaveHandler getSaveHandler()
/*  875:     */   {
/*  876: 869 */     return this.world.getSaveHandler();
/*  877:     */   }
/*  878:     */   
/*  879:     */   public WorldInfo getWorldInfo()
/*  880:     */   {
/*  881: 874 */     return this.world.getWorldInfo();
/*  882:     */   }
/*  883:     */   
/*  884:     */   public GameRules getGameRules()
/*  885:     */   {
/*  886: 879 */     return this.world.getGameRules();
/*  887:     */   }
/*  888:     */   
/*  889:     */   public void updateAllPlayersSleepingFlag()
/*  890:     */   {
/*  891: 884 */     this.world.updateAllPlayersSleepingFlag();
/*  892:     */   }
/*  893:     */   
/*  894:     */   public float getWeightedThunderStrength(float par1)
/*  895:     */   {
/*  896: 889 */     return this.world.getWeightedThunderStrength(par1);
/*  897:     */   }
/*  898:     */   
/*  899:     */   @SideOnly(Side.CLIENT)
/*  900:     */   public void setThunderStrength(float p_147442_1_)
/*  901:     */   {
/*  902: 895 */     this.world.setThunderStrength(p_147442_1_);
/*  903:     */   }
/*  904:     */   
/*  905:     */   public float getRainStrength(float par1)
/*  906:     */   {
/*  907: 900 */     return this.world.getRainStrength(par1);
/*  908:     */   }
/*  909:     */   
/*  910:     */   @SideOnly(Side.CLIENT)
/*  911:     */   public void setRainStrength(float par1)
/*  912:     */   {
/*  913: 906 */     this.world.setRainStrength(par1);
/*  914:     */   }
/*  915:     */   
/*  916:     */   public boolean isThundering()
/*  917:     */   {
/*  918: 911 */     return this.world.isThundering();
/*  919:     */   }
/*  920:     */   
/*  921:     */   public boolean isRaining()
/*  922:     */   {
/*  923: 916 */     return this.world.isRaining();
/*  924:     */   }
/*  925:     */   
/*  926:     */   public boolean canLightningStrikeAt(int par1, int par2, int par3)
/*  927:     */   {
/*  928: 921 */     return this.world.canLightningStrikeAt(par1, par2, par3);
/*  929:     */   }
/*  930:     */   
/*  931:     */   public boolean isBlockHighHumidity(int par1, int par2, int par3)
/*  932:     */   {
/*  933: 926 */     return this.world.isBlockHighHumidity(par1, par2, par3);
/*  934:     */   }
/*  935:     */   
/*  936:     */   public void setItemData(String par1Str, WorldSavedData par2WorldSavedData)
/*  937:     */   {
/*  938: 931 */     this.world.setItemData(par1Str, par2WorldSavedData);
/*  939:     */   }
/*  940:     */   
/*  941:     */   public WorldSavedData loadItemData(Class par1Class, String par2Str)
/*  942:     */   {
/*  943: 936 */     return this.world.loadItemData(par1Class, par2Str);
/*  944:     */   }
/*  945:     */   
/*  946:     */   public int getUniqueDataId(String par1Str)
/*  947:     */   {
/*  948: 941 */     return this.world.getUniqueDataId(par1Str);
/*  949:     */   }
/*  950:     */   
/*  951:     */   public void playBroadcastSound(int par1, int par2, int par3, int par4, int par5)
/*  952:     */   {
/*  953: 946 */     this.world.playBroadcastSound(par1, par2, par3, par4, par5);
/*  954:     */   }
/*  955:     */   
/*  956:     */   public void playAuxSFX(int par1, int par2, int par3, int par4, int par5)
/*  957:     */   {
/*  958: 951 */     this.world.playAuxSFX(par1, par2, par3, par4, par5);
/*  959:     */   }
/*  960:     */   
/*  961:     */   public void playAuxSFXAtEntity(EntityPlayer par1EntityPlayer, int par2, int par3, int par4, int par5, int par6)
/*  962:     */   {
/*  963: 956 */     this.world.playAuxSFXAtEntity(par1EntityPlayer, par2, par3, par4, par5, par6);
/*  964:     */   }
/*  965:     */   
/*  966:     */   public int getHeight()
/*  967:     */   {
/*  968: 961 */     return this.world.getHeight();
/*  969:     */   }
/*  970:     */   
/*  971:     */   public int getActualHeight()
/*  972:     */   {
/*  973: 966 */     return this.world.getActualHeight();
/*  974:     */   }
/*  975:     */   
/*  976:     */   public Random setRandomSeed(int par1, int par2, int par3)
/*  977:     */   {
/*  978: 971 */     return this.world.setRandomSeed(par1, par2, par3);
/*  979:     */   }
/*  980:     */   
/*  981:     */   public ChunkPosition findClosestStructure(String p_147440_1_, int p_147440_2_, int p_147440_3_, int p_147440_4_)
/*  982:     */   {
/*  983: 976 */     return this.world.findClosestStructure(p_147440_1_, p_147440_2_, p_147440_3_, p_147440_4_);
/*  984:     */   }
/*  985:     */   
/*  986:     */   @SideOnly(Side.CLIENT)
/*  987:     */   public boolean extendedLevelsInChunkCache()
/*  988:     */   {
/*  989: 982 */     return this.world.extendedLevelsInChunkCache();
/*  990:     */   }
/*  991:     */   
/*  992:     */   @SideOnly(Side.CLIENT)
/*  993:     */   public double getHorizon()
/*  994:     */   {
/*  995: 988 */     return this.world.getHorizon();
/*  996:     */   }
/*  997:     */   
/*  998:     */   public CrashReportCategory addWorldInfoToCrashReport(CrashReport par1CrashReport)
/*  999:     */   {
/* 1000: 993 */     return this.world.addWorldInfoToCrashReport(par1CrashReport);
/* 1001:     */   }
/* 1002:     */   
/* 1003:     */   public void destroyBlockInWorldPartially(int p_147443_1_, int p_147443_2_, int p_147443_3_, int p_147443_4_, int p_147443_5_)
/* 1004:     */   {
/* 1005: 998 */     this.world.destroyBlockInWorldPartially(p_147443_1_, p_147443_2_, p_147443_3_, p_147443_4_, p_147443_5_);
/* 1006:     */   }
/* 1007:     */   
/* 1008:     */   public Calendar getCurrentDate()
/* 1009:     */   {
/* 1010:1003 */     return this.world.getCurrentDate();
/* 1011:     */   }
/* 1012:     */   
/* 1013:     */   @SideOnly(Side.CLIENT)
/* 1014:     */   public void makeFireworks(double par1, double par3, double par5, double par7, double par9, double par11, NBTTagCompound par13NBTTagCompound)
/* 1015:     */   {
/* 1016:1009 */     this.world.makeFireworks(par1, par3, par5, par7, par9, par11, par13NBTTagCompound);
/* 1017:     */   }
/* 1018:     */   
/* 1019:     */   public Scoreboard getScoreboard()
/* 1020:     */   {
/* 1021:1014 */     return this.world.getScoreboard();
/* 1022:     */   }
/* 1023:     */   
/* 1024:     */   public void func_147453_f(int p_147453_1_, int p_147453_2_, int p_147453_3_, Block p_147453_4_)
/* 1025:     */   {
/* 1026:1019 */     this.world.func_147453_f(p_147453_1_, p_147453_2_, p_147453_3_, p_147453_4_);
/* 1027:     */   }
/* 1028:     */   
/* 1029:     */   public float func_147462_b(double p_147462_1_, double p_147462_3_, double p_147462_5_)
/* 1030:     */   {
/* 1031:1024 */     return this.world.func_147462_b(p_147462_1_, p_147462_3_, p_147462_5_);
/* 1032:     */   }
/* 1033:     */   
/* 1034:     */   public float func_147473_B(int p_147473_1_, int p_147473_2_, int p_147473_3_)
/* 1035:     */   {
/* 1036:1029 */     return this.world.func_147473_B(p_147473_1_, p_147473_2_, p_147473_3_);
/* 1037:     */   }
/* 1038:     */   
/* 1039:     */   public void func_147450_X()
/* 1040:     */   {
/* 1041:1034 */     this.world.func_147450_X();
/* 1042:     */   }
/* 1043:     */   
/* 1044:     */   public void addTileEntity(TileEntity entity)
/* 1045:     */   {
/* 1046:1039 */     this.world.addTileEntity(entity);
/* 1047:     */   }
/* 1048:     */   
/* 1049:     */   public boolean isSideSolid(int x, int y, int z, ForgeDirection side)
/* 1050:     */   {
/* 1051:1044 */     return this.world.isSideSolid(x, y, z, side);
/* 1052:     */   }
/* 1053:     */   
/* 1054:     */   public boolean isSideSolid(int x, int y, int z, ForgeDirection side, boolean _default)
/* 1055:     */   {
/* 1056:1049 */     return this.world.isSideSolid(x, y, z, side, _default);
/* 1057:     */   }
/* 1058:     */   
/* 1059:     */   public ImmutableSetMultimap<ChunkCoordIntPair, ForgeChunkManager.Ticket> getPersistentChunks()
/* 1060:     */   {
/* 1061:1054 */     return this.world.getPersistentChunks();
/* 1062:     */   }
/* 1063:     */   
/* 1064:     */   public int getBlockLightOpacity(int x, int y, int z)
/* 1065:     */   {
/* 1066:1059 */     return this.world.getBlockLightOpacity(x, y, z);
/* 1067:     */   }
/* 1068:     */   
/* 1069:     */   public int countEntities(EnumCreatureType type, boolean forSpawnCount)
/* 1070:     */   {
/* 1071:1064 */     return this.world.countEntities(type, forSpawnCount);
/* 1072:     */   }
/* 1073:     */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.fakeplayer.FakeWorldWrapper
 * JD-Core Version:    0.7.0.1
 */