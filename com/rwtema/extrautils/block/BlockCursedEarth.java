/*   1:    */ package com.rwtema.extrautils.block;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   5:    */ import com.rwtema.extrautils.item.IBlockLocalization;
/*   6:    */ import cpw.mods.fml.relauncher.ReflectionHelper;
/*   7:    */ import cpw.mods.fml.relauncher.Side;
/*   8:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   9:    */ import java.lang.reflect.Constructor;
/*  10:    */ import java.lang.reflect.Method;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Random;
/*  13:    */ import net.minecraft.block.Block;
/*  14:    */ import net.minecraft.block.material.Material;
/*  15:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  16:    */ import net.minecraft.creativetab.CreativeTabs;
/*  17:    */ import net.minecraft.entity.Entity;
/*  18:    */ import net.minecraft.entity.EntityCreature;
/*  19:    */ import net.minecraft.entity.EntityFlying;
/*  20:    */ import net.minecraft.entity.EntityList;
/*  21:    */ import net.minecraft.entity.EntityLiving;
/*  22:    */ import net.minecraft.entity.EnumCreatureType;
/*  23:    */ import net.minecraft.entity.monster.EntityMob;
/*  24:    */ import net.minecraft.entity.player.EntityPlayer;
/*  25:    */ import net.minecraft.init.Blocks;
/*  26:    */ import net.minecraft.item.Item;
/*  27:    */ import net.minecraft.item.ItemStack;
/*  28:    */ import net.minecraft.nbt.NBTTagCompound;
/*  29:    */ import net.minecraft.potion.Potion;
/*  30:    */ import net.minecraft.potion.PotionEffect;
/*  31:    */ import net.minecraft.tileentity.MobSpawnerBaseLogic;
/*  32:    */ import net.minecraft.tileentity.TileEntityMobSpawner;
/*  33:    */ import net.minecraft.util.AxisAlignedBB;
/*  34:    */ import net.minecraft.util.DamageSource;
/*  35:    */ import net.minecraft.util.IIcon;
/*  36:    */ import net.minecraft.world.EnumDifficulty;
/*  37:    */ import net.minecraft.world.IBlockAccess;
/*  38:    */ import net.minecraft.world.World;
/*  39:    */ import net.minecraft.world.WorldServer;
/*  40:    */ import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
/*  41:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  42:    */ 
/*  43:    */ public class BlockCursedEarth
/*  44:    */   extends Block
/*  45:    */   implements IBlockLocalization
/*  46:    */ {
/*  47: 37 */   public static int powered = 0;
/*  48:    */   private IIcon cursedSide;
/*  49:    */   private IIcon cursedTop;
/*  50:    */   private IIcon blessedSide;
/*  51:    */   private IIcon blessedTop;
/*  52:    */   private IIcon dirt;
/*  53:    */   
/*  54:    */   public BlockCursedEarth()
/*  55:    */   {
/*  56: 45 */     super(Material.grass);
/*  57: 46 */     setTickRandomly(true);
/*  58: 47 */     setCreativeTab(CreativeTabs.tabBlock);
/*  59: 48 */     setStepSound(Block.soundTypeGravel);
/*  60: 49 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  61: 50 */     setBlockName("extrautils:cursedearthside");
/*  62: 51 */     setBlockTextureName("extrautils:cursedearthside");
/*  63: 52 */     setHardness(0.5F);
/*  64: 53 */     this.blockResistance = 200.0F;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata)
/*  68:    */   {
/*  69: 58 */     return true;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/*  73:    */   {
/*  74: 66 */     return Item.getItemFromBlock(Blocks.dirt);
/*  75:    */   }
/*  76:    */   
/*  77:    */   @SideOnly(Side.CLIENT)
/*  78:    */   public IIcon getIcon(int par1, int par2)
/*  79:    */   {
/*  80: 75 */     if (par1 == 0) {
/*  81: 76 */       return this.dirt;
/*  82:    */     }
/*  83: 79 */     if (par2 >> 1 == 0)
/*  84:    */     {
/*  85: 80 */       if (par1 == 1) {
/*  86: 81 */         return this.cursedTop;
/*  87:    */       }
/*  88: 83 */       return this.cursedSide;
/*  89:    */     }
/*  90: 86 */     if (par2 >> 1 == 1)
/*  91:    */     {
/*  92: 87 */       if (par1 == 1) {
/*  93: 88 */         return this.blessedTop;
/*  94:    */       }
/*  95: 90 */       return this.blessedSide;
/*  96:    */     }
/*  97: 93 */     return this.dirt;
/*  98:    */   }
/*  99:    */   
/* 100:    */   @SideOnly(Side.CLIENT)
/* 101:    */   public void registerBlockIcons(IIconRegister par1IIconRegister)
/* 102:    */   {
/* 103:104 */     this.dirt = par1IIconRegister.registerIcon("dirt");
/* 104:105 */     this.cursedSide = par1IIconRegister.registerIcon("extrautils:cursedearthside");
/* 105:106 */     this.cursedTop = par1IIconRegister.registerIcon("extrautils:cursedearthtop");
/* 106:    */     
/* 107:108 */     this.blessedSide = par1IIconRegister.registerIcon("extrautils:blessedEarthSide");
/* 108:109 */     this.blessedTop = par1IIconRegister.registerIcon("extrautils:blessedEarthTop");
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void onEntityWalking(World world, int x, int y, int z, Entity entity)
/* 112:    */   {
/* 113:114 */     if (world.isClient) {
/* 114:115 */       return;
/* 115:    */     }
/* 116:118 */     if ((world.getBlockMetadata(x, y, z) & 0xE) == 2) {
/* 117:119 */       return;
/* 118:    */     }
/* 119:121 */     if (entity.isEntityAlive()) {
/* 120:122 */       if (world.getBlockLightValue(x, y + 1, z) > 9)
/* 121:    */       {
/* 122:123 */         if (!(entity instanceof EntityPlayer)) {
/* 123:124 */           entity.attackEntityFrom(DamageSource.cactus, 1.0F);
/* 124:    */         }
/* 125:127 */         if ((world.rand.nextInt(24) == 0) && 
/* 126:128 */           (world.canBlockSeeTheSky(x, y + 1, z)) && 
/* 127:129 */           (world.isAirBlock(x, y + 1, z)))
/* 128:    */         {
/* 129:130 */           world.setBlock(x, y + 1, z, Blocks.fire);
/* 130:131 */           world.scheduleBlockUpdate(x, y, z, Blocks.fire, 1 + world.rand.nextInt(200));
/* 131:    */         }
/* 132:134 */         for (int i = 0; i < 20; i++)
/* 133:    */         {
/* 134:135 */           int dx = x + world.rand.nextInt(9) - 4;
/* 135:136 */           int dy = y + world.rand.nextInt(5) - 3;
/* 136:137 */           int dz = z + world.rand.nextInt(9) - 4;
/* 137:139 */           if (((world.getBlock(dx, dy, dz) == this ? 1 : 0) & ((world.getBlockLightOpacity(dx, dy + 1, dz) > 2 ? 1 : 0) | (world.getBlockLightValue(dx, dy + 1, dz) > 9 ? 1 : 0))) != 0) {
/* 138:140 */             world.scheduleBlockUpdate(dx, dy, dz, this, 10 + world.rand.nextInt(600));
/* 139:    */           }
/* 140:    */         }
/* 141:    */       }
/* 142:144 */       else if ((entity instanceof EntityMob))
/* 143:    */       {
/* 144:145 */         ((EntityMob)entity).addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 7200, 1));
/* 145:146 */         ((EntityMob)entity).addPotionEffect(new PotionEffect(Potion.damageBoost.id, 7200, 1));
/* 146:147 */         ((EntityMob)entity).addPotionEffect(new PotionEffect(Potion.regeneration.id, 20, 0));
/* 147:148 */         ((EntityMob)entity).addPotionEffect(new PotionEffect(Potion.resistance.id, 40, 0));
/* 148:    */       }
/* 149:149 */       else if ((entity instanceof EntityPlayer))
/* 150:    */       {
/* 151:150 */         for (int i = 0; i < 3; i++)
/* 152:    */         {
/* 153:151 */           int var7 = x + world.rand.nextInt(9) - 4;
/* 154:152 */           int var8 = y + world.rand.nextInt(5) - 3;
/* 155:153 */           int var9 = z + world.rand.nextInt(9) - 4;
/* 156:155 */           if ((world.getBlock(var7, var8, var9) == this) && (world.getBlockLightValue(var7, var8 + 1, var9) < 9)) {
/* 157:156 */             world.scheduleBlockUpdate(var7, var8, var9, this, world.rand.nextInt(100));
/* 158:    */           }
/* 159:    */         }
/* 160:    */       }
/* 161:    */     }
/* 162:    */   }
/* 163:    */   
/* 164:    */   public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
/* 165:    */   {
/* 166:173 */     return powered;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public boolean renderAsNormalBlock()
/* 170:    */   {
/* 171:182 */     return powered == 0;
/* 172:    */   }
/* 173:    */   
/* 174:185 */   private Method cache = null;
/* 175:    */   
/* 176:    */   public void resetTimer(MobSpawnerBaseLogic logic)
/* 177:    */   {
/* 178:188 */     if (this.cache == null) {
/* 179:189 */       this.cache = ReflectionHelper.findMethod(MobSpawnerBaseLogic.class, logic, new String[] { "resetTimer", "resetTimer" }, new Class[0]);
/* 180:    */     }
/* 181:    */     try
/* 182:    */     {
/* 183:192 */       this.cache.invoke(logic, new Object[0]);
/* 184:    */     }
/* 185:    */     catch (Exception e)
/* 186:    */     {
/* 187:194 */       e.printStackTrace();
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:    */   public int onBlockPlaced(World world, int x, int y, int z, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_)
/* 192:    */   {
/* 193:200 */     if ((world.getTileEntity(x, y + 1, z) instanceof TileEntityMobSpawner)) {
/* 194:201 */       world.scheduleBlockUpdate(x, y, z, this, 10);
/* 195:    */     }
/* 196:203 */     return super.onBlockPlaced(world, x, y, z, p_149660_5_, p_149660_6_, p_149660_7_, p_149660_8_, p_149660_9_);
/* 197:    */   }
/* 198:    */   
/* 199:    */   public int getMobilityFlag()
/* 200:    */   {
/* 201:208 */     return 2;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void onNeighborBlockChange(World p_149695_1_, int x, int y, int z, Block p_149695_5_)
/* 205:    */   {
/* 206:213 */     if (p_149695_5_ == Blocks.mob_spawner) {
/* 207:214 */       p_149695_1_.scheduleBlockUpdate(x, y, z, this, 10);
/* 208:    */     }
/* 209:216 */     super.onNeighborBlockChange(p_149695_1_, x, y, z, p_149695_5_);
/* 210:    */   }
/* 211:    */   
/* 212:    */   public int damageDropped(int p_149692_1_)
/* 213:    */   {
/* 214:221 */     return p_149692_1_ & 0xE;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void spawnLogic(MobSpawnerBaseLogic logic)
/* 218:    */   {
/* 219:228 */     if (logic.spawnDelay == -1) {
/* 220:229 */       resetTimer(logic);
/* 221:    */     }
/* 222:232 */     if (logic.spawnDelay > 0)
/* 223:    */     {
/* 224:233 */       logic.spawnDelay -= 100;
/* 225:234 */       if (logic.spawnDelay < 0) {
/* 226:235 */         logic.spawnDelay = 0;
/* 227:    */       }
/* 228:236 */       return;
/* 229:    */     }
/* 230:239 */     boolean flag = false;
/* 231:    */     
/* 232:241 */     NBTTagCompound tags = new NBTTagCompound();
/* 233:242 */     logic.writeToNBT(tags);
/* 234:    */     
/* 235:244 */     int spawnCount = tags.getShort("SpawnCount");
/* 236:245 */     int maxNearbyEntities = tags.getShort("MaxNearbyEntities");
/* 237:246 */     int spawnRange = tags.getShort("SpawnRange");
/* 238:248 */     for (int i = 0; i < spawnCount; i++)
/* 239:    */     {
/* 240:249 */       Entity entity = EntityList.createEntityByName(logic.getEntityNameToSpawn(), logic.getSpawnerWorld());
/* 241:251 */       if (entity == null) {
/* 242:252 */         return;
/* 243:    */       }
/* 244:255 */       int j = logic.getSpawnerWorld().getEntitiesWithinAABB(entity.getClass(), AxisAlignedBB.getBoundingBox(logic.getSpawnerX(), logic.getSpawnerY(), logic.getSpawnerZ(), logic.getSpawnerX() + 1, logic.getSpawnerY() + 1, logic.getSpawnerZ() + 1).expand(spawnRange * 2, 4.0D, spawnRange * 2)).size();
/* 245:257 */       if (j >= maxNearbyEntities)
/* 246:    */       {
/* 247:258 */         resetTimer(logic);
/* 248:259 */         return;
/* 249:    */       }
/* 250:262 */       double d2 = logic.getSpawnerX() + (logic.getSpawnerWorld().rand.nextDouble() - logic.getSpawnerWorld().rand.nextDouble()) * spawnRange;
/* 251:263 */       double d3 = logic.getSpawnerY() + logic.getSpawnerWorld().rand.nextInt(3) - 1;
/* 252:264 */       double d4 = logic.getSpawnerZ() + (logic.getSpawnerWorld().rand.nextDouble() - logic.getSpawnerWorld().rand.nextDouble()) * spawnRange;
/* 253:265 */       EntityLiving entityliving = (entity instanceof EntityLiving) ? (EntityLiving)entity : null;
/* 254:266 */       entity.setLocationAndAngles(d2, d3, d4, logic.getSpawnerWorld().rand.nextFloat() * 360.0F, 0.0F);
/* 255:268 */       if ((entityliving != null) && (SpawnMob(entityliving)))
/* 256:    */       {
/* 257:269 */         logic.func_98265_a(entityliving);
/* 258:270 */         logic.getSpawnerWorld().playAuxSFX(2004, logic.getSpawnerX(), logic.getSpawnerY(), logic.getSpawnerZ(), 0);
/* 259:    */         
/* 260:272 */         entityliving.spawnExplosionParticle();
/* 261:    */         
/* 262:274 */         flag = true;
/* 263:    */       }
/* 264:    */     }
/* 265:278 */     if (flag) {
/* 266:279 */       resetTimer(logic);
/* 267:    */     }
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void updateTick(World world, int x, int y, int z, Random rand)
/* 271:    */   {
/* 272:286 */     if ((!world.isClient) && ((world instanceof WorldServer)))
/* 273:    */     {
/* 274:287 */       if ((world.getTileEntity(x, y + 1, z) instanceof TileEntityMobSpawner))
/* 275:    */       {
/* 276:288 */         MobSpawnerBaseLogic logic = ((TileEntityMobSpawner)world.getTileEntity(x, y + 1, z)).func_145881_a();
/* 277:289 */         spawnLogic(logic);
/* 278:290 */         world.scheduleBlockUpdate(x, y, z, this, 100);
/* 279:291 */         return;
/* 280:    */       }
/* 281:294 */       boolean isWorldGen = (world.getBlockMetadata(x, y, z) & 0x1) == 1;
/* 282:    */       
/* 283:296 */       boolean blessed = (world.getBlockMetadata(x, y, z) & 0xE) == 2;
/* 284:298 */       if ((blessed) || (world.getBlockLightValue(x, y + 1, z) < 9))
/* 285:    */       {
/* 286:299 */         boolean flag = (blessed) || (world.difficultySetting.getDifficultyId() > 0);
/* 287:301 */         if ((flag) && (isWorldGen) && 
/* 288:302 */           (world.getClosestPlayer(x, y, z, 10.0D) == null)) {
/* 289:303 */           flag = false;
/* 290:    */         }
/* 291:307 */         if (flag)
/* 292:    */         {
/* 293:308 */           int var12 = world.getEntitiesWithinAABB(EntityCreature.class, AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1).expand(7.0D, 2.0D, 7.0D)).size();
/* 294:309 */           flag = rand.nextInt(blessed ? 8 : 4) >= var12;
/* 295:    */         }
/* 296:312 */         if (flag)
/* 297:    */         {
/* 298:314 */           EnumCreatureType type = EnumCreatureType.monster;
/* 299:315 */           if ((world.getBlockMetadata(x, y, z) & 0xE) == 2) {
/* 300:316 */             type = EnumCreatureType.creature;
/* 301:    */           }
/* 302:318 */           BiomeGenBase.SpawnListEntry var7 = ((WorldServer)world).spawnRandomCreature(type, x, y, z);
/* 303:321 */           if ((var7 != null) && (!EntityFlying.class.isAssignableFrom(var7.entityClass)))
/* 304:    */           {
/* 305:    */             EntityLiving t;
/* 306:    */             try
/* 307:    */             {
/* 308:323 */               t = (EntityLiving)var7.entityClass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { world });
/* 309:    */             }
/* 310:    */             catch (Exception var31)
/* 311:    */             {
/* 312:325 */               var31.printStackTrace();
/* 313:326 */               return;
/* 314:    */             }
/* 315:329 */             t.setLocationAndAngles(x + 0.5D, y + 1, z + 0.5D, rand.nextFloat() * 360.0F, 0.0F);
/* 316:    */             
/* 317:331 */             int meta = world.getBlockMetadata(x, y, z);
/* 318:332 */             world.setBlock(x, y, z, Blocks.grass, 0, 0);
/* 319:333 */             if (SpawnMob(t))
/* 320:    */             {
/* 321:334 */               world.spawnEntityInWorld(t);
/* 322:335 */               t.playLivingSound();
/* 323:    */             }
/* 324:337 */             world.setBlock(x, y, z, this, meta, 0);
/* 325:    */           }
/* 326:    */         }
/* 327:    */       }
/* 328:342 */       if (blessed) {
/* 329:343 */         return;
/* 330:    */       }
/* 331:345 */       if ((world.getBlockLightOpacity(x, y + 1, z) > 2) || (world.getBlockLightValue(x, y + 1, z) > 9))
/* 332:    */       {
/* 333:346 */         boolean nearbyFire = world.getBlock(x, y + 1, z) == Blocks.fire;
/* 334:348 */         if (nearbyFire)
/* 335:    */         {
/* 336:349 */           if (rand.nextInt(3) == 0) {
/* 337:350 */             world.setBlock(x, y, z, Blocks.dirt);
/* 338:    */           }
/* 339:    */         }
/* 340:353 */         else if ((world.isAirBlock(x, y + 1, z)) && (world.canBlockSeeTheSky(x, y + 1, z)))
/* 341:    */         {
/* 342:354 */           world.setBlock(x, y + 1, z, Blocks.fire);
/* 343:355 */           nearbyFire = true;
/* 344:    */         }
/* 345:358 */         if (!nearbyFire) {
/* 346:359 */           for (int i = 0; i < 20; i++)
/* 347:    */           {
/* 348:360 */             int dx = x + rand.nextInt(9) - 4;
/* 349:361 */             int dy = y + rand.nextInt(5) - 3;
/* 350:362 */             int dz = z + rand.nextInt(9) - 4;
/* 351:364 */             if (world.getBlock(dx, dy, dz) == Blocks.fire)
/* 352:    */             {
/* 353:365 */               nearbyFire = true;
/* 354:366 */               break;
/* 355:    */             }
/* 356:    */           }
/* 357:    */         }
/* 358:371 */         if (nearbyFire) {
/* 359:372 */           for (int i = 0; i < 20; i++)
/* 360:    */           {
/* 361:373 */             int dx = x + rand.nextInt(9) - 4;
/* 362:374 */             int dy = y + rand.nextInt(5) - 3;
/* 363:375 */             int dz = z + rand.nextInt(9) - 4;
/* 364:377 */             if ((((world.getBlock(dx, dy, dz) == this ? 1 : 0) & ((world.getBlockLightOpacity(dx, dy + 1, dz) > 2 ? 1 : 0) | (world.getBlockLightValue(dx, dy + 1, dz) > 9 ? 1 : 0))) != 0) && 
/* 365:378 */               (world.getBlock(dx, dy + 1, dz) != Blocks.fire)) {
/* 366:379 */               if ((rand.nextInt(4) == 0) && (world.isAirBlock(dx, dy + 1, dz))) {
/* 367:380 */                 world.setBlock(dx, dy + 1, dz, Blocks.fire);
/* 368:    */               } else {
/* 369:382 */                 world.setBlock(dx, dy, dz, Blocks.dirt);
/* 370:    */               }
/* 371:    */             }
/* 372:    */           }
/* 373:    */         }
/* 374:    */       }
/* 375:388 */       else if (world.getBlockLightValue(x, y + 1, z) < 9)
/* 376:    */       {
/* 377:389 */         for (int var6 = 0; var6 < 4; var6++)
/* 378:    */         {
/* 379:390 */           int var7 = x + rand.nextInt(3) - 1;
/* 380:391 */           int var8 = y + rand.nextInt(5) - 3;
/* 381:392 */           int var9 = z + rand.nextInt(3) - 1;
/* 382:394 */           if (((world.getBlock(var7, var8, var9) == Blocks.dirt) || (world.getBlock(var7, var8, var9) == Blocks.grass)) && (world.getBlockLightOpacity(var7, var8 + 1, var9) <= 2) && (world.getBlockLightValue(var7, var8 + 1, var9) < 9)) {
/* 383:396 */             world.setBlock(var7, var8, var9, this, world.getBlockMetadata(x, y, z), 3);
/* 384:    */           }
/* 385:    */         }
/* 386:    */       }
/* 387:    */     }
/* 388:    */   }
/* 389:    */   
/* 390:    */   public boolean SpawnMob(EntityLiving t)
/* 391:    */   {
/* 392:404 */     if (t.getCanSpawnHere())
/* 393:    */     {
/* 394:405 */       t.onSpawnWithEgg(null);
/* 395:406 */       if ((t instanceof EntityMob))
/* 396:    */       {
/* 397:407 */         t.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 3600, 1));
/* 398:408 */         t.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 3600, 1));
/* 399:    */       }
/* 400:    */       else
/* 401:    */       {
/* 402:410 */         t.addPotionEffect(new PotionEffect(Potion.regeneration.id, 3600, 1));
/* 403:    */       }
/* 404:412 */       t.getEntityData().setLong("CursedEarth", 3600L);
/* 405:413 */       t.forceSpawn = true;
/* 406:414 */       t.func_110163_bv();
/* 407:415 */       return true;
/* 408:    */     }
/* 409:417 */     return false;
/* 410:    */   }
/* 411:    */   
/* 412:    */   public boolean isBlockSolid(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
/* 413:    */   {
/* 414:427 */     return true;
/* 415:    */   }
/* 416:    */   
/* 417:    */   public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
/* 418:    */   {
/* 419:432 */     return true;
/* 420:    */   }
/* 421:    */   
/* 422:    */   public boolean isFireSource(World world, int x, int y, int z, ForgeDirection side)
/* 423:    */   {
/* 424:437 */     return true;
/* 425:    */   }
/* 426:    */   
/* 427:    */   @SideOnly(Side.CLIENT)
/* 428:    */   public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
/* 429:    */   {
/* 430:443 */     super.randomDisplayTick(par1World, par2, par3, par4, par5Random);
/* 431:445 */     if ((par5Random.nextInt(2) == 0) && (par1World.getBlockMetadata(par2, par3, par4) == 0)) {
/* 432:446 */       par1World.spawnParticle("portal", par2 + par5Random.nextFloat(), par3 + 1.1F, par4 + par5Random.nextFloat(), 0.0D, 0.05D, 0.0D);
/* 433:    */     }
/* 434:    */   }
/* 435:    */   
/* 436:    */   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/* 437:    */   {
/* 438:452 */     if ((XUHelper.isThisPlayerACheatyBastardOfCheatBastardness(player)) || (XUHelper.deObf))
/* 439:    */     {
/* 440:453 */       world.setBlock(x, y, z, this, 2, 3);
/* 441:454 */       return true;
/* 442:    */     }
/* 443:456 */     return false;
/* 444:    */   }
/* 445:    */   
/* 446:    */   public String getUnlocalizedName(ItemStack par1ItemStack)
/* 447:    */   {
/* 448:461 */     if (par1ItemStack.getItemDamage() == 0) {
/* 449:462 */       return getUnlocalizedName();
/* 450:    */     }
/* 451:464 */     return getUnlocalizedName() + ".blessed";
/* 452:    */   }
/* 453:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BlockCursedEarth
 * JD-Core Version:    0.7.0.1
 */