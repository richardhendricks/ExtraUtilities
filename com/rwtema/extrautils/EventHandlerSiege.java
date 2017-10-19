/*   1:    */ package com.rwtema.extrautils;
/*   2:    */ 
/*   3:    */ import com.mojang.authlib.GameProfile;
/*   4:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   5:    */ import com.rwtema.extrautils.helper.XURandom;
/*   6:    */ import com.rwtema.extrautils.item.ItemDivisionSigil;
/*   7:    */ import com.rwtema.extrautils.network.packets.PacketTempChat;
/*   8:    */ import cpw.mods.fml.common.eventhandler.Event.Result;
/*   9:    */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.HashSet;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Random;
/*  14:    */ import net.minecraft.block.Block;
/*  15:    */ import net.minecraft.entity.Entity;
/*  16:    */ import net.minecraft.entity.EntityLivingBase;
/*  17:    */ import net.minecraft.entity.EnumCreatureType;
/*  18:    */ import net.minecraft.entity.monster.EntityBlaze;
/*  19:    */ import net.minecraft.entity.monster.EntityCaveSpider;
/*  20:    */ import net.minecraft.entity.monster.EntityCreeper;
/*  21:    */ import net.minecraft.entity.monster.EntityGiantZombie;
/*  22:    */ import net.minecraft.entity.monster.EntityIronGolem;
/*  23:    */ import net.minecraft.entity.monster.EntityMob;
/*  24:    */ import net.minecraft.entity.monster.EntityPigZombie;
/*  25:    */ import net.minecraft.entity.monster.EntitySilverfish;
/*  26:    */ import net.minecraft.entity.monster.EntitySkeleton;
/*  27:    */ import net.minecraft.entity.monster.EntitySpider;
/*  28:    */ import net.minecraft.entity.monster.EntityWitch;
/*  29:    */ import net.minecraft.entity.monster.EntityZombie;
/*  30:    */ import net.minecraft.entity.player.EntityPlayer;
/*  31:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  32:    */ import net.minecraft.init.Blocks;
/*  33:    */ import net.minecraft.init.Items;
/*  34:    */ import net.minecraft.inventory.IInventory;
/*  35:    */ import net.minecraft.item.Item;
/*  36:    */ import net.minecraft.item.ItemPotion;
/*  37:    */ import net.minecraft.item.ItemStack;
/*  38:    */ import net.minecraft.nbt.NBTTagCompound;
/*  39:    */ import net.minecraft.potion.Potion;
/*  40:    */ import net.minecraft.potion.PotionEffect;
/*  41:    */ import net.minecraft.server.MinecraftServer;
/*  42:    */ import net.minecraft.server.management.ServerConfigurationManager;
/*  43:    */ import net.minecraft.tileentity.TileEntityBeacon;
/*  44:    */ import net.minecraft.tileentity.TileEntityHopper;
/*  45:    */ import net.minecraft.util.ChatComponentText;
/*  46:    */ import net.minecraft.util.DamageSource;
/*  47:    */ import net.minecraft.world.World;
/*  48:    */ import net.minecraft.world.WorldProvider;
/*  49:    */ import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
/*  50:    */ import net.minecraftforge.common.DimensionManager;
/*  51:    */ import net.minecraftforge.event.entity.EntityJoinWorldEvent;
/*  52:    */ import net.minecraftforge.event.entity.living.LivingDeathEvent;
/*  53:    */ import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
/*  54:    */ import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
/*  55:    */ import net.minecraftforge.event.world.WorldEvent.PotentialSpawns;
/*  56:    */ import net.minecraftforge.oredict.OreDictionary;
/*  57:    */ 
/*  58:    */ public class EventHandlerSiege
/*  59:    */ {
/*  60:    */   public static final int numKills = 100;
/*  61: 44 */   public static final int[] ddx = { -1, 0, 1, 0 };
/*  62: 45 */   public static final int[] ddz = { 0, -1, 0, 1 };
/*  63: 47 */   public static List<String> SiegeParticipants = new ArrayList();
/*  64: 48 */   public static List<BiomeGenBase.SpawnListEntry> mobSpawns = new ArrayList();
/*  65:    */   public static ItemStack[] earthItems;
/*  66:    */   public static ItemStack[] fireItems;
/*  67:    */   
/*  68:    */   static
/*  69:    */   {
/*  70: 51 */     mobSpawns.add(new BiomeGenBase.SpawnListEntry(EntitySpider.class, 200, 3, 3));
/*  71: 52 */     mobSpawns.add(new BiomeGenBase.SpawnListEntry(EntityCaveSpider.class, 40, 4, 4));
/*  72: 53 */     mobSpawns.add(new BiomeGenBase.SpawnListEntry(EntityZombie.class, 80, 4, 4));
/*  73: 54 */     mobSpawns.add(new BiomeGenBase.SpawnListEntry(EntitySkeleton.class, 200, 4, 4));
/*  74: 55 */     mobSpawns.add(new BiomeGenBase.SpawnListEntry(EntityCreeper.class, 200, 4, 4));
/*  75: 56 */     mobSpawns.add(new BiomeGenBase.SpawnListEntry(EntityBlaze.class, 80, 2, 4));
/*  76: 57 */     mobSpawns.add(new BiomeGenBase.SpawnListEntry(EntityPigZombie.class, 40, 4, 4));
/*  77: 58 */     mobSpawns.add(new BiomeGenBase.SpawnListEntry(EntityWitch.class, 40, 1, 3));
/*  78: 59 */     mobSpawns.add(new BiomeGenBase.SpawnListEntry(EntitySilverfish.class, 40, 3, 3));
/*  79: 60 */     mobSpawns.add(new BiomeGenBase.SpawnListEntry(EntityGiantZombie.class, 5, 1, 1));
/*  80:    */   }
/*  81:    */   
/*  82: 65 */   private static Random rand = XURandom.getInstance();
/*  83:    */   
/*  84:    */   public static void endSiege(World world, boolean announce)
/*  85:    */   {
/*  86: 68 */     if (world.isClient) {
/*  87: 69 */       return;
/*  88:    */     }
/*  89: 72 */     for (int i = 0; i < world.loadedEntityList.size(); i++) {
/*  90: 73 */       if (((world.loadedEntityList.get(i) instanceof EntityMob)) && 
/*  91: 74 */         (((EntityMob)world.loadedEntityList.get(i)).getEntityData().hasKey("Siege"))) {
/*  92: 75 */         world.removeEntity((Entity)world.loadedEntityList.get(i));
/*  93:    */       }
/*  94:    */     }
/*  95: 80 */     if (announce) {
/*  96: 81 */       MinecraftServer.getServer().getConfigurationManager().func_148539_a(new ChatComponentText("The Siege has ended in 'The End'"));
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   public static void upgradeSigil(EntityPlayer player)
/* 101:    */   {
/* 102: 86 */     for (int j = 0; j < player.inventory.mainInventory.length; j++) {
/* 103: 87 */       if ((player.inventory.mainInventory[j] != null) && (player.inventory.mainInventory[j].getItem() == ExtraUtils.divisionSigil))
/* 104:    */       {
/* 105: 88 */         if ((player.inventory.mainInventory[j].hasTagCompound()) && (player.inventory.mainInventory[j].getTagCompound().hasKey("damage"))) {
/* 106: 89 */           player.inventory.mainInventory[j] = ItemDivisionSigil.newStableSigil();
/* 107:    */         }
/* 108: 92 */         return;
/* 109:    */       }
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   public static void beginSiege(World world)
/* 114:    */   {
/* 115: 98 */     if (world.isClient) {
/* 116: 99 */       return;
/* 117:    */     }
/* 118:102 */     if (world.provider.dimensionId != 1) {
/* 119:103 */       return;
/* 120:    */     }
/* 121:106 */     for (int i = 0; i < world.loadedEntityList.size(); i++) {
/* 122:107 */       if ((world.loadedEntityList.get(i) instanceof EntityMob))
/* 123:    */       {
/* 124:108 */         world.removeEntity((Entity)world.loadedEntityList.get(i));
/* 125:    */       }
/* 126:109 */       else if ((world.loadedEntityList.get(i) instanceof EntityPlayer))
/* 127:    */       {
/* 128:110 */         EntityPlayer player = (EntityPlayer)world.loadedEntityList.get(i);
/* 129:111 */         SiegeParticipants.add(player.getGameProfile().getName());
/* 130:112 */         player.getEntityData().setInteger("SiegeKills", 0);
/* 131:    */       }
/* 132:    */     }
/* 133:116 */     if (SiegeParticipants.size() != 0) {
/* 134:117 */       MinecraftServer.getServer().getConfigurationManager().func_148539_a(new ChatComponentText("The Siege has begun in 'The End'"));
/* 135:    */     } else {
/* 136:119 */       endSiege(world, false);
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:    */   public static boolean hasSigil(EntityPlayer player)
/* 141:    */   {
/* 142:124 */     for (int j = 0; j < player.inventory.mainInventory.length; j++) {
/* 143:125 */       if ((player.inventory.mainInventory[j] != null) && (player.inventory.mainInventory[j].getItem() == ExtraUtils.divisionSigil) && 
/* 144:126 */         (player.inventory.mainInventory[j].hasTagCompound()) && (player.inventory.mainInventory[j].getTagCompound().hasKey("damage"))) {
/* 145:127 */         return true;
/* 146:    */       }
/* 147:    */     }
/* 148:132 */     return false;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public static void checkPlayers()
/* 152:    */   {
/* 153:136 */     World worldObj = DimensionManager.getWorld(1);
/* 154:138 */     if ((worldObj == null) || (worldObj.isClient))
/* 155:    */     {
/* 156:139 */       SiegeParticipants.clear();
/* 157:140 */       return;
/* 158:    */     }
/* 159:143 */     if (SiegeParticipants.size() > 0)
/* 160:    */     {
/* 161:144 */       for (int i = 0; i < SiegeParticipants.size(); i++) {
/* 162:145 */         if (worldObj.getPlayerEntityByName((String)SiegeParticipants.get(i)) == null)
/* 163:    */         {
/* 164:146 */           SiegeParticipants.remove(SiegeParticipants.get(i));
/* 165:147 */           i--;
/* 166:    */         }
/* 167:    */       }
/* 168:150 */       if (SiegeParticipants.size() == 0) {
/* 169:151 */         endSiege(worldObj, true);
/* 170:    */       }
/* 171:    */     }
/* 172:    */   }
/* 173:    */   
/* 174:    */   public static int[] getStrength(World world, int x, int y, int z)
/* 175:    */   {
/* 176:157 */     List<ChunkPos> rs = new ArrayList();
/* 177:158 */     List<ChunkPos> st = new ArrayList();
/* 178:159 */     rs.add(new ChunkPos(x, y, z));
/* 179:160 */     st.add(new ChunkPos(x, y, z));
/* 180:161 */     int maxDist = 0;
/* 181:162 */     Block rsId = Blocks.redstone_wire;
/* 182:163 */     Block stId = Blocks.tripwire;
/* 183:164 */     int k2 = 0;
/* 184:166 */     for (int i = 0; i < rs.size(); i++) {
/* 185:167 */       for (int j = 0; j < 4; j++)
/* 186:    */       {
/* 187:168 */         ChunkPos nPos = new ChunkPos(((ChunkPos)rs.get(i)).x + ddx[j], y, ((ChunkPos)rs.get(i)).z + ddz[j]);
/* 188:169 */         int m = mDist(nPos.x - x, nPos.z - z);
/* 189:171 */         if ((m < 16) && (world.getBlock(nPos.x, y, nPos.z) == rsId) && (!rs.contains(nPos)))
/* 190:    */         {
/* 191:172 */           rs.add(nPos);
/* 192:174 */           if (world.getBlockMetadata(nPos.x, y, nPos.z) != 0) {
/* 193:175 */             k2++;
/* 194:    */           }
/* 195:178 */           if (m > maxDist) {
/* 196:179 */             maxDist = m;
/* 197:    */           }
/* 198:    */         }
/* 199:    */       }
/* 200:    */     }
/* 201:184 */     rs.remove(new ChunkPos(x, y, z));
/* 202:185 */     int k = 0;
/* 203:187 */     for (int i = 0; i < st.size(); i++) {
/* 204:188 */       for (int j = 0; j < 4; j++)
/* 205:    */       {
/* 206:189 */         ChunkPos nPos = new ChunkPos(((ChunkPos)st.get(i)).x + ddx[j], y, ((ChunkPos)st.get(i)).z + ddz[j]);
/* 207:190 */         int m = mDist(nPos.x - x, nPos.z - z);
/* 208:192 */         if (m < 16) {
/* 209:193 */           if ((world.getBlock(nPos.x, y, nPos.z) == stId) && (!st.contains(nPos)))
/* 210:    */           {
/* 211:194 */             st.add(nPos);
/* 212:196 */             if (m > maxDist) {
/* 213:197 */               maxDist = m;
/* 214:    */             }
/* 215:    */           }
/* 216:199 */           else if ((i != 0) && (world.getBlock(nPos.x, y, nPos.z) == rsId) && (rs.contains(nPos)))
/* 217:    */           {
/* 218:200 */             k++;
/* 219:    */           }
/* 220:    */         }
/* 221:    */       }
/* 222:    */     }
/* 223:205 */     return new int[] { k, maxDist * maxDist * 4 };
/* 224:    */   }
/* 225:    */   
/* 226:    */   public static int mDist(int x, int z)
/* 227:    */   {
/* 228:209 */     if (x < 0) {
/* 229:210 */       x *= -1;
/* 230:    */     }
/* 231:213 */     if (z < 0) {
/* 232:214 */       z *= -1;
/* 233:    */     }
/* 234:217 */     return x > z ? x : z;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public static int checkChestList(IInventory chest, ItemStack[] items, boolean destroy)
/* 238:    */   {
/* 239:221 */     boolean[] check = new boolean[items.length];
/* 240:222 */     int s = 0;
/* 241:224 */     for (int i = 0; (i < chest.getSizeInventory()) && (s < items.length); i++) {
/* 242:225 */       if (chest.getStackInSlot(i) != null) {
/* 243:226 */         for (int j = 0; (j < items.length) && ((!destroy) || (chest.getStackInSlot(i) != null)); j++) {
/* 244:227 */           if ((check[j] == 0) && (XUHelper.canItemsStack(items[j], chest.getStackInSlot(i), false, true)))
/* 245:    */           {
/* 246:228 */             if (destroy) {
/* 247:229 */               chest.setInventorySlotContents(i, null);
/* 248:    */             }
/* 249:232 */             check[j] = true;
/* 250:233 */             s++;
/* 251:234 */             break;
/* 252:    */           }
/* 253:    */         }
/* 254:    */       }
/* 255:    */     }
/* 256:240 */     return s;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public static int checkChestEarth(IInventory chest, boolean destroy)
/* 260:    */   {
/* 261:244 */     if (chest == null) {
/* 262:245 */       return 0;
/* 263:    */     }
/* 264:248 */     if (earthItems == null) {
/* 265:249 */       earthItems = new ItemStack[] { new ItemStack(Blocks.coal_ore), new ItemStack(Blocks.gold_ore), new ItemStack(Blocks.iron_ore), new ItemStack(Blocks.lapis_ore), new ItemStack(Blocks.diamond_ore), new ItemStack(Blocks.emerald_ore), new ItemStack(Blocks.redstone_ore), new ItemStack(Blocks.grass), new ItemStack(Blocks.dirt), new ItemStack(Blocks.clay), new ItemStack(Blocks.sand), new ItemStack(Blocks.gravel), new ItemStack(Blocks.obsidian) };
/* 266:    */     }
/* 267:254 */     return checkChestList(chest, earthItems, destroy);
/* 268:    */   }
/* 269:    */   
/* 270:    */   public static int checkChestFire(IInventory chest, boolean destroy)
/* 271:    */   {
/* 272:258 */     if (chest == null) {
/* 273:259 */       return 0;
/* 274:    */     }
/* 275:262 */     if (fireItems == null) {
/* 276:263 */       fireItems = new ItemStack[] { new ItemStack(Items.coal, 1, 1), new ItemStack(Blocks.stone), new ItemStack(Items.brick), new ItemStack(Items.cooked_fished), new ItemStack(Blocks.glass), new ItemStack(Items.gold_ingot), new ItemStack(Items.iron_ingot), new ItemStack(Items.baked_potato), new ItemStack(Items.netherbrick), new ItemStack(Items.dye, 1, 2), new ItemStack(Blocks.hardened_clay), new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_beef), new ItemStack(Items.cooked_chicken) };
/* 277:    */     }
/* 278:268 */     return checkChestList(chest, fireItems, destroy);
/* 279:    */   }
/* 280:    */   
/* 281:    */   public static int checkChestWater(IInventory chest, boolean destroy)
/* 282:    */   {
/* 283:272 */     if (chest == null) {
/* 284:273 */       return 0;
/* 285:    */     }
/* 286:276 */     List<PotionEffect> numEffects = new ArrayList();
/* 287:278 */     for (int i = 0; (i < chest.getSizeInventory()) && (numEffects.size() < 12); i++) {
/* 288:279 */       if ((chest.getStackInSlot(i) != null) && (chest.getStackInSlot(i).getItem() == Items.potionitem))
/* 289:    */       {
/* 290:280 */         List temp = Items.potionitem.getEffects(chest.getStackInSlot(i));
/* 291:282 */         if (temp != null) {
/* 292:283 */           for (Object aTemp : temp) {
/* 293:284 */             if (!numEffects.contains(aTemp))
/* 294:    */             {
/* 295:285 */               numEffects.add((PotionEffect)aTemp);
/* 296:287 */               if (destroy) {
/* 297:288 */                 chest.setInventorySlotContents(i, null);
/* 298:    */               }
/* 299:    */             }
/* 300:    */           }
/* 301:    */         }
/* 302:    */       }
/* 303:    */     }
/* 304:296 */     return numEffects.size();
/* 305:    */   }
/* 306:    */   
/* 307:    */   public static int checkChestAir(IInventory chest, boolean destroy)
/* 308:    */   {
/* 309:300 */     if (chest == null) {
/* 310:301 */       return 0;
/* 311:    */     }
/* 312:304 */     int s = 0;
/* 313:305 */     List<ItemStack> pt = OreDictionary.getOres("record");
/* 314:306 */     HashSet<Item> items = new HashSet();
/* 315:308 */     for (int i = 0; (i < chest.getSizeInventory()) && (s < 12); i++) {
/* 316:309 */       if (chest.getStackInSlot(i) != null)
/* 317:    */       {
/* 318:310 */         ItemStack item = chest.getStackInSlot(i);
/* 319:311 */         if (!items.contains(item.getItem()))
/* 320:    */         {
/* 321:314 */           boolean flag = false;
/* 322:316 */           for (ItemStack ore : pt) {
/* 323:317 */             if (OreDictionary.itemMatches(item, ore, false))
/* 324:    */             {
/* 325:318 */               flag = true;
/* 326:319 */               break;
/* 327:    */             }
/* 328:    */           }
/* 329:323 */           if (flag)
/* 330:    */           {
/* 331:327 */             if (destroy) {
/* 332:328 */               chest.setInventorySlotContents(i, null);
/* 333:    */             }
/* 334:331 */             items.add(item.getItem());
/* 335:    */             
/* 336:333 */             s++;
/* 337:    */           }
/* 338:    */         }
/* 339:    */       }
/* 340:    */     }
/* 341:338 */     return s;
/* 342:    */   }
/* 343:    */   
/* 344:    */   @SubscribeEvent
/* 345:    */   public void Siege(EntityJoinWorldEvent event)
/* 346:    */   {
/* 347:343 */     if (event.world.isClient) {
/* 348:344 */       return;
/* 349:    */     }
/* 350:347 */     checkPlayers();
/* 351:349 */     if ((event.entity instanceof EntityPlayer)) {
/* 352:350 */       if (event.world.provider.dimensionId != 1)
/* 353:    */       {
/* 354:351 */         if (event.entity.getEntityData().hasKey("SiegeKills"))
/* 355:    */         {
/* 356:352 */           event.entity.getEntityData().removeTag("SiegeKills");
/* 357:353 */           SiegeParticipants.remove(((EntityPlayer)event.entity).getGameProfile().getName());
/* 358:    */         }
/* 359:    */       }
/* 360:356 */       else if ((event.entity.getEntityData().hasKey("SiegeKills")) && 
/* 361:357 */         (!SiegeParticipants.contains(((EntityPlayer)event.entity).getGameProfile().getName()))) {
/* 362:358 */         SiegeParticipants.add(((EntityPlayer)event.entity).getGameProfile().getName());
/* 363:    */       }
/* 364:    */     }
/* 365:    */   }
/* 366:    */   
/* 367:    */   public double sq(double x, double y, double z)
/* 368:    */   {
/* 369:366 */     return x * x + z * z + y * y;
/* 370:    */   }
/* 371:    */   
/* 372:    */   @SubscribeEvent
/* 373:    */   public void golemDeath(LivingDeathEvent event)
/* 374:    */   {
/* 375:371 */     if ((!event.entity.worldObj.isClient) && (event.entity.worldObj.provider.dimensionId == 1) && 
/* 376:372 */       ((event.entity instanceof EntityIronGolem)) && 
/* 377:373 */       ((event.source.getSourceOfDamage() instanceof EntityPlayer)))
/* 378:    */     {
/* 379:374 */       EntityPlayer player = (EntityPlayer)event.source.getSourceOfDamage();
/* 380:376 */       if (!hasSigil(player)) {
/* 381:377 */         return;
/* 382:    */       }
/* 383:380 */       List t = event.entity.worldObj.field_147482_g;
/* 384:382 */       for (Object aT : t) {
/* 385:383 */         if (aT.getClass().equals(TileEntityBeacon.class))
/* 386:    */         {
/* 387:384 */           TileEntityBeacon beacon = (TileEntityBeacon)aT;
/* 388:385 */           int x = beacon.xCoord;int y = beacon.yCoord;int z = beacon.zCoord;
/* 389:387 */           if (sq(event.entity.posX - x - 0.5D, event.entity.posY - y - 0.5D, event.entity.posZ - z - 0.5D) < 300.0D)
/* 390:    */           {
/* 391:388 */             int[] s = getStrength(event.entity.worldObj, x, y, z);
/* 392:389 */             World world = beacon.getWorldObj();
/* 393:391 */             if (s[0] == 64)
/* 394:    */             {
/* 395:392 */               int debug = 1;
/* 396:393 */               boolean flag = true;
/* 397:395 */               if (checkChestFire(TileEntityHopper.func_145893_b(world, x, y, z - 5), false) < debug) {
/* 398:396 */                 flag = false;
/* 399:    */               }
/* 400:399 */               if ((flag) && (checkChestEarth(TileEntityHopper.func_145893_b(world, x, y, z + 5), false) < debug)) {
/* 401:400 */                 flag = false;
/* 402:    */               }
/* 403:403 */               if ((flag) && (checkChestAir(TileEntityHopper.func_145893_b(world, x - 5, y, z), false) < debug)) {
/* 404:404 */                 flag = false;
/* 405:    */               }
/* 406:407 */               if ((flag) && (checkChestWater(TileEntityHopper.func_145893_b(world, x + 5, y, z), false) < debug)) {
/* 407:408 */                 flag = false;
/* 408:    */               }
/* 409:411 */               if (flag)
/* 410:    */               {
/* 411:412 */                 world.func_147480_a(x, y, z, false);
/* 412:414 */                 for (int j = 0; j < 4; j++) {
/* 413:415 */                   world.func_147480_a(x + ddx[j] * 5, y, z + ddz[j] * 5, false);
/* 414:    */                 }
/* 415:418 */                 world.func_147480_a(x, y, z, false);
/* 416:419 */                 world.createExplosion(null, x, y, z, 6.0F, true);
/* 417:421 */                 for (int j = 0; j < 4; j++) {
/* 418:422 */                   world.createExplosion(null, x + ddx[j] * 5, y, z + ddz[j] * 5, 3.0F, true);
/* 419:    */                 }
/* 420:425 */                 beginSiege(world);
/* 421:426 */                 return;
/* 422:    */               }
/* 423:    */             }
/* 424:    */           }
/* 425:    */         }
/* 426:    */       }
/* 427:    */     }
/* 428:    */   }
/* 429:    */   
/* 430:    */   @SubscribeEvent
/* 431:    */   public void SiegeDeath(LivingDeathEvent event)
/* 432:    */   {
/* 433:439 */     if (SiegeParticipants.isEmpty()) {
/* 434:440 */       return;
/* 435:    */     }
/* 436:443 */     if ((event.entityLiving.worldObj.isClient) || (event.entityLiving.worldObj.provider.dimensionId != 1)) {
/* 437:444 */       return;
/* 438:    */     }
/* 439:447 */     if ((event.entityLiving instanceof EntityPlayer)) {
/* 440:448 */       checkPlayers();
/* 441:    */     }
/* 442:451 */     if (((event.entityLiving instanceof EntityMob)) && ((event.source.getSourceOfDamage() instanceof EntityPlayer)) && (event.entityLiving.getEntityData().hasKey("Siege")))
/* 443:    */     {
/* 444:452 */       EntityPlayer player = (EntityPlayer)event.source.getSourceOfDamage();
/* 445:454 */       if ((player != null) && (SiegeParticipants.contains(player.getGameProfile().getName())) && (hasSigil(player)))
/* 446:    */       {
/* 447:455 */         if (player.getEntityData().hasKey("SiegeKills")) {
/* 448:456 */           player.getEntityData().setInteger("SiegeKills", player.getEntityData().getInteger("SiegeKills") + 1);
/* 449:    */         } else {
/* 450:458 */           player.getEntityData().setInteger("SiegeKills", 1);
/* 451:    */         }
/* 452:461 */         int kills = player.getEntityData().getInteger("SiegeKills");
/* 453:463 */         if (kills > 100)
/* 454:    */         {
/* 455:464 */           upgradeSigil(player);
/* 456:465 */           player.getEntityData().removeTag("SiegeKills");
/* 457:466 */           SiegeParticipants.remove(player.getGameProfile().getName());
/* 458:467 */           PacketTempChat.sendChat(player, "Your Sigil has stabilized");
/* 459:    */         }
/* 460:    */         else
/* 461:    */         {
/* 462:469 */           PacketTempChat.sendChat(player, "Kills: " + player.getEntityData().getInteger("SiegeKills"));
/* 463:    */         }
/* 464:    */       }
/* 465:    */     }
/* 466:472 */     else if ((!(event.entityLiving instanceof EntityPlayer)) || (!SiegeParticipants.contains(((EntityPlayer)event.entityLiving).getGameProfile().getName()))) {}
/* 467:    */   }
/* 468:    */   
/* 469:    */   @SubscribeEvent
/* 470:    */   public void SiegePotentialSpawns(WorldEvent.PotentialSpawns event)
/* 471:    */   {
/* 472:478 */     if ((!event.world.isClient) && (event.world.provider.dimensionId == 1) && (event.type == EnumCreatureType.monster))
/* 473:    */     {
/* 474:479 */       checkPlayers();
/* 475:481 */       if (SiegeParticipants.isEmpty()) {
/* 476:482 */         event.list.removeAll(mobSpawns);
/* 477:484 */       } else if (event.list.size() < mobSpawns.size()) {
/* 478:485 */         event.list.addAll(mobSpawns);
/* 479:    */       }
/* 480:    */     }
/* 481:    */   }
/* 482:    */   
/* 483:    */   @SubscribeEvent
/* 484:    */   public void Siege(LivingEvent.LivingUpdateEvent event)
/* 485:    */   {
/* 486:493 */     if (event.entity.worldObj.isClient) {
/* 487:494 */       return;
/* 488:    */     }
/* 489:497 */     if (SiegeParticipants.isEmpty())
/* 490:    */     {
/* 491:498 */       if (event.entityLiving.getEntityData().hasKey("Siege"))
/* 492:    */       {
/* 493:499 */         event.entity.setDead();
/* 494:500 */         endSiege(event.entity.worldObj, true);
/* 495:    */       }
/* 496:503 */       return;
/* 497:    */     }
/* 498:506 */     if (event.entityLiving.worldObj.rand.nextInt(1000) == 0) {
/* 499:507 */       checkPlayers();
/* 500:    */     }
/* 501:510 */     if (event.entityLiving.worldObj.provider.dimensionId != 1) {
/* 502:511 */       return;
/* 503:    */     }
/* 504:514 */     if (((event.entityLiving instanceof EntityMob)) && (((EntityMob)event.entityLiving).getAttackTarget() == null) && (event.entityLiving.getEntityData().hasKey("Siege")))
/* 505:    */     {
/* 506:515 */       int i = rand.nextInt(SiegeParticipants.size());
/* 507:516 */       EntityPlayer player = event.entityLiving.worldObj.getPlayerEntityByName((String)SiegeParticipants.get(i));
/* 508:518 */       if (player != null)
/* 509:    */       {
/* 510:519 */         ((EntityMob)event.entityLiving).setAttackTarget(player);
/* 511:520 */         ((EntityMob)event.entityLiving).setTarget(player);
/* 512:    */       }
/* 513:    */       else
/* 514:    */       {
/* 515:522 */         SiegeParticipants.remove(i);
/* 516:    */       }
/* 517:    */     }
/* 518:526 */     if ((event.entityLiving instanceof EntityPlayer))
/* 519:    */     {
/* 520:527 */       EntityPlayer player = (EntityPlayer)event.entityLiving;
/* 521:529 */       if ((player.motionY == 0.0D) && (player.fallDistance == 0.0F) && (!player.onGround) && (!player.isOnLadder()) && (!player.isInWater()) && (player.ridingEntity == null)) {
/* 522:530 */         player.attackEntityFrom(DamageSource.generic, 0.5F);
/* 523:    */       }
/* 524:    */     }
/* 525:    */   }
/* 526:    */   
/* 527:    */   @SubscribeEvent
/* 528:    */   public void SiegeCheckSpawn(LivingSpawnEvent.CheckSpawn event)
/* 529:    */   {
/* 530:537 */     if (SiegeParticipants.isEmpty()) {
/* 531:538 */       return;
/* 532:    */     }
/* 533:541 */     if (event.entity.worldObj.isClient) {
/* 534:542 */       return;
/* 535:    */     }
/* 536:545 */     if (event.world.provider.dimensionId != 1) {
/* 537:546 */       return;
/* 538:    */     }
/* 539:549 */     if (((event.entityLiving instanceof EntityMob)) && 
/* 540:550 */       (event.entityLiving.worldObj.checkNoEntityCollision(event.entityLiving.boundingBox)) && (event.entityLiving.worldObj.getCollidingBoundingBoxes(event.entityLiving, event.entityLiving.boundingBox).isEmpty()) && (!event.entityLiving.worldObj.isAnyLiquid(event.entityLiving.boundingBox)))
/* 541:    */     {
/* 542:553 */       event.entityLiving.getEntityData().setBoolean("Siege", true);
/* 543:554 */       event.entityLiving.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 7200, 2));
/* 544:    */       
/* 545:556 */       event.setResult(Event.Result.ALLOW);
/* 546:    */     }
/* 547:    */   }
/* 548:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.EventHandlerSiege
 * JD-Core Version:    0.7.0.1
 */