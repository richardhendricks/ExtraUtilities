/*   1:    */ package com.rwtema.extrautils;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.item.ItemDivisionSigil;
/*   4:    */ import com.rwtema.extrautils.item.ItemGoldenBag;
/*   5:    */ import com.rwtema.extrautils.item.ItemSoul;
/*   6:    */ import com.rwtema.extrautils.item.ItemUnstableIngot;
/*   7:    */ import com.rwtema.extrautils.network.NetworkHandler;
/*   8:    */ import com.rwtema.extrautils.network.packets.PacketTempChat;
/*   9:    */ import com.rwtema.extrautils.tileentity.IAntiMobTorch;
/*  10:    */ import cpw.mods.fml.common.eventhandler.Event.Result;
/*  11:    */ import cpw.mods.fml.common.eventhandler.EventPriority;
/*  12:    */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Random;
/*  16:    */ import net.minecraft.entity.Entity;
/*  17:    */ import net.minecraft.entity.EntityLivingBase;
/*  18:    */ import net.minecraft.entity.EnumCreatureType;
/*  19:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*  20:    */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*  21:    */ import net.minecraft.entity.ai.attributes.BaseAttributeMap;
/*  22:    */ import net.minecraft.entity.ai.attributes.IAttribute;
/*  23:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  24:    */ import net.minecraft.entity.boss.EntityWither;
/*  25:    */ import net.minecraft.entity.item.EntityItem;
/*  26:    */ import net.minecraft.entity.monster.EntityEnderman;
/*  27:    */ import net.minecraft.entity.monster.EntityMob;
/*  28:    */ import net.minecraft.entity.monster.EntitySilverfish;
/*  29:    */ import net.minecraft.entity.player.EntityPlayer;
/*  30:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*  31:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  32:    */ import net.minecraft.init.Blocks;
/*  33:    */ import net.minecraft.init.Items;
/*  34:    */ import net.minecraft.inventory.IInventory;
/*  35:    */ import net.minecraft.item.ItemStack;
/*  36:    */ import net.minecraft.nbt.NBTTagCompound;
/*  37:    */ import net.minecraft.tileentity.TileEntity;
/*  38:    */ import net.minecraft.util.AxisAlignedBB;
/*  39:    */ import net.minecraft.util.DamageSource;
/*  40:    */ import net.minecraft.world.GameRules;
/*  41:    */ import net.minecraft.world.World;
/*  42:    */ import net.minecraft.world.WorldProvider;
/*  43:    */ import net.minecraftforge.event.ServerChatEvent;
/*  44:    */ import net.minecraftforge.event.entity.EntityJoinWorldEvent;
/*  45:    */ import net.minecraftforge.event.entity.item.ItemTossEvent;
/*  46:    */ import net.minecraftforge.event.entity.living.EnderTeleportEvent;
/*  47:    */ import net.minecraftforge.event.entity.living.LivingDeathEvent;
/*  48:    */ import net.minecraftforge.event.entity.living.LivingDropsEvent;
/*  49:    */ import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
/*  50:    */ import net.minecraftforge.event.entity.living.LivingSpawnEvent;
/*  51:    */ import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
/*  52:    */ import net.minecraftforge.event.entity.player.EntityInteractEvent;
/*  53:    */ import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
/*  54:    */ import net.minecraftforge.event.entity.player.PlayerDropsEvent;
/*  55:    */ import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
/*  56:    */ import net.minecraftforge.event.entity.player.PlayerInteractEvent;
/*  57:    */ import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
/*  58:    */ import net.minecraftforge.event.terraingen.PopulateChunkEvent.Post;
/*  59:    */ import net.minecraftforge.oredict.OreDictionary;
/*  60:    */ 
/*  61:    */ public class EventHandlerServer
/*  62:    */ {
/*  63: 50 */   public static String debug = "";
/*  64: 71 */   public static List<int[]> magnumTorchRegistry = new ArrayList();
/*  65: 72 */   private String silverName = "nuggetSilver";
/*  66:    */   
/*  67:    */   public static boolean isInRangeOfTorch(Entity entity)
/*  68:    */   {
/*  69: 75 */     for (int[] coord : magnumTorchRegistry) {
/*  70: 76 */       if ((coord[0] == entity.worldObj.provider.dimensionId) && 
/*  71: 77 */         (entity.worldObj.blockExists(coord[1], coord[2], coord[3])) && ((entity.worldObj.getTileEntity(coord[1], coord[2], coord[3]) instanceof IAntiMobTorch)))
/*  72:    */       {
/*  73: 78 */         TileEntity tile = entity.worldObj.getTileEntity(coord[1], coord[2], coord[3]);
/*  74: 79 */         double dx = tile.xCoord + 0.5F - entity.posX;
/*  75: 80 */         double dy = tile.yCoord + 0.5F - entity.posY;
/*  76: 81 */         double dz = tile.zCoord + 0.5F - entity.posZ;
/*  77: 83 */         if ((dx * dx + dz * dz) / ((IAntiMobTorch)tile).getHorizontalTorchRangeSquared() + dy * dy / ((IAntiMobTorch)tile).getVerticalTorchRangeSquared() <= 1.0D) {
/*  78: 84 */           return true;
/*  79:    */         }
/*  80:    */       }
/*  81:    */     }
/*  82: 90 */     return false;
/*  83:    */   }
/*  84:    */   
/*  85:    */   @SubscribeEvent
/*  86:    */   public void soulDropping(LivingDropsEvent event)
/*  87:    */   {
/*  88: 96 */     int prob = 43046721;
/*  89: 97 */     if ((ExtraUtils.lawSwordEnabled) && ((event.source.getEntity() instanceof EntityPlayer)))
/*  90:    */     {
/*  91: 98 */       EntityPlayer player = (EntityPlayer)event.source.getEntity();
/*  92: 99 */       if ((player.getHeldItem() != null) && (player.getHeldItem().getItem() == ExtraUtils.lawSword)) {
/*  93:100 */         prob /= 10;
/*  94:    */       }
/*  95:    */     }
/*  96:103 */     if ((ExtraUtils.soul != null) && ((event.entityLiving instanceof EntityMob)) && (!event.entity.worldObj.isClient) && (event.entity.worldObj.rand.nextInt(prob) == 0)) {
/*  97:104 */       event.drops.add(new EntityItem(event.entityLiving.worldObj, event.entityLiving.posX, event.entity.posY, event.entityLiving.posZ, new ItemStack(ExtraUtils.soul, 1, 3)));
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   @SubscribeEvent
/* 102:    */   public void silverFishDrop(LivingDropsEvent event)
/* 103:    */   {
/* 104:113 */     if (((event.entityLiving instanceof EntitySilverfish)) && (!event.entity.worldObj.isClient) && (event.entity.worldObj.rand.nextInt(5) == 0) && (event.recentlyHit) && 
/* 105:114 */       (OreDictionary.getOres(this.silverName).size() > 0))
/* 106:    */     {
/* 107:115 */       ItemStack item = ((ItemStack)OreDictionary.getOres(this.silverName).get(0)).copy();
/* 108:117 */       if (event.drops.size() > 0) {
/* 109:118 */         for (int i = 0; i < event.drops.size(); i++)
/* 110:    */         {
/* 111:119 */           ItemStack t = ((EntityItem)event.drops.get(i)).getEntityItem();
/* 112:121 */           if ((t != null) && ((t.getItem() == item.getItem()) || (t.getItemDamage() == item.getItemDamage()))) {
/* 113:122 */             return;
/* 114:    */           }
/* 115:    */         }
/* 116:    */       }
/* 117:127 */       event.drops.add(new EntityItem(event.entityLiving.worldObj, event.entityLiving.posX, event.entity.posY, event.entityLiving.posZ, item));
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   @SubscribeEvent
/* 122:    */   public void netherDrop(LivingDropsEvent event)
/* 123:    */   {
/* 124:134 */     if ((ExtraUtils.divisionSigil != null) && ((event.entityLiving instanceof EntityWither)) && ((event.source.getSourceOfDamage() instanceof EntityPlayer)) && (event.entity.worldObj != null) && (event.entity.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot")))
/* 125:    */     {
/* 126:136 */       EntityPlayer player = (EntityPlayer)event.source.getSourceOfDamage();
/* 127:137 */       NBTTagCompound t = new NBTTagCompound();
/* 128:139 */       if (player.getEntityData().hasKey("PlayerPersisted")) {
/* 129:140 */         t = player.getEntityData().getCompoundTag("PlayerPersisted");
/* 130:    */       } else {
/* 131:142 */         player.getEntityData().setTag("PlayerPersisted", t);
/* 132:    */       }
/* 133:145 */       int kills = 0;
/* 134:147 */       if (t.hasKey("witherKills")) {
/* 135:148 */         kills = t.getInteger("witherKills");
/* 136:    */       }
/* 137:151 */       kills++;
/* 138:152 */       t.setInteger("witherKills", kills);
/* 139:154 */       if ((kills == 1) || (!t.hasKey("hasSigil")) || (event.entity.worldObj.rand.nextInt(10) == 0))
/* 140:    */       {
/* 141:155 */         ItemStack item = new ItemStack(ExtraUtils.divisionSigil);
/* 142:156 */         EntityItem entityitem = new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, item);
/* 143:157 */         entityitem.delayBeforeCanPickup = 10;
/* 144:158 */         event.drops.add(entityitem);
/* 145:    */       }
/* 146:    */     }
/* 147:    */   }
/* 148:    */   
/* 149:    */   @SubscribeEvent
/* 150:    */   public void decoratePiEasterEgg(PopulateChunkEvent.Post event)
/* 151:    */   {
/* 152:242 */     if ((event.chunkX == 196349) && (event.chunkZ == 22436) && (event.world.provider.dimensionId == 0))
/* 153:    */     {
/* 154:243 */       event.world.setBlock(3141592, 65, 358979, Blocks.chest);
/* 155:244 */       TileEntity chest = event.world.getTileEntity(3141592, 65, 358979);
/* 156:246 */       if ((chest instanceof IInventory)) {
/* 157:247 */         ((IInventory)chest).setInventorySlotContents(0, new ItemStack(Items.pumpkin_pie));
/* 158:    */       }
/* 159:    */     }
/* 160:    */   }
/* 161:    */   
/* 162:    */   @SubscribeEvent
/* 163:    */   public void magnumTorchDenyTeleport(EnderTeleportEvent event)
/* 164:    */   {
/* 165:254 */     if (((event.entityLiving instanceof EntityEnderman)) && (!((EntityEnderman)event.entityLiving).isScreaming())) {
/* 166:255 */       for (int[] coord : magnumTorchRegistry) {
/* 167:256 */         if ((coord[0] == event.entity.worldObj.provider.dimensionId) && 
/* 168:257 */           (event.entity.worldObj.blockExists(coord[1], coord[2], coord[3])) && ((event.entity.worldObj.getTileEntity(coord[1], coord[2], coord[3]) instanceof IAntiMobTorch)))
/* 169:    */         {
/* 170:258 */           TileEntity tile = event.entity.worldObj.getTileEntity(coord[1], coord[2], coord[3]);
/* 171:259 */           double dx = tile.xCoord + 0.5F - event.targetX;
/* 172:260 */           double dy = tile.yCoord + 0.5F - event.targetY;
/* 173:261 */           double dz = tile.zCoord + 0.5F - event.targetZ;
/* 174:263 */           if ((dx * dx + dz * dz) / ((IAntiMobTorch)tile).getHorizontalTorchRangeSquared() + dy * dy / ((IAntiMobTorch)tile).getVerticalTorchRangeSquared() <= 1.0D)
/* 175:    */           {
/* 176:264 */             double dx2 = tile.xCoord + 0.5F - event.entity.posX;
/* 177:265 */             double dy2 = tile.yCoord + 0.5F - event.entity.posY;
/* 178:266 */             double dz2 = tile.zCoord + 0.5F - event.entity.posZ;
/* 179:268 */             if (dx * dx + dy * dy + dz * dz < dx2 * dx2 + dy2 * dy2 + dz2 * dz2) {
/* 180:269 */               event.setCanceled(true);
/* 181:    */             }
/* 182:    */           }
/* 183:    */         }
/* 184:    */       }
/* 185:    */     }
/* 186:    */   }
/* 187:    */   
/* 188:    */   @SubscribeEvent
/* 189:    */   public void magnumTorchDenySpawn(LivingSpawnEvent.CheckSpawn event)
/* 190:    */   {
/* 191:279 */     if (event.getResult() == Event.Result.ALLOW) {
/* 192:280 */       return;
/* 193:    */     }
/* 194:283 */     if ((event.entityLiving.isCreatureType(EnumCreatureType.monster, false)) && 
/* 195:284 */       (isInRangeOfTorch(event.entity))) {
/* 196:285 */       event.setResult(Event.Result.DENY);
/* 197:    */     }
/* 198:    */   }
/* 199:    */   
/* 200:    */   @SubscribeEvent
/* 201:    */   public void rainInformer(EntityJoinWorldEvent event)
/* 202:    */   {
/* 203:292 */     if ((!event.world.isClient) && ((event.entity instanceof EntityPlayerMP))) {}
/* 204:    */   }
/* 205:    */   
/* 206:    */   @SubscribeEvent
/* 207:    */   public void debugValueLoad(ServerChatEvent event)
/* 208:    */   {
/* 209:323 */     debug = event.message;
/* 210:    */   }
/* 211:    */   
/* 212:    */   @SubscribeEvent
/* 213:    */   public void angelBlockDestroy(PlayerInteractEvent event)
/* 214:    */   {
/* 215:328 */     if ((event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) && (ExtraUtils.angelBlock != null) && 
/* 216:329 */       (event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z) == ExtraUtils.angelBlock) && 
/* 217:330 */       (event.entityPlayer.inventory.addItemStackToInventory(new ItemStack(ExtraUtils.angelBlock))) && 
/* 218:331 */       (!event.entityPlayer.worldObj.isClient))
/* 219:    */     {
/* 220:332 */       event.entityPlayer.worldObj.func_147480_a(event.x, event.y, event.z, false);
/* 221:333 */       event.setCanceled(true);
/* 222:    */     }
/* 223:    */   }
/* 224:    */   
/* 225:    */   @SubscribeEvent
/* 226:    */   public void unstableIngotDestroyer(EntityItemPickupEvent event)
/* 227:    */   {
/* 228:342 */     if ((ExtraUtils.unstableIngot != null) && 
/* 229:343 */       (event.item.getEntityItem().getItem() == ExtraUtils.unstableIngot) && 
/* 230:344 */       (event.item.getEntityItem().hasTagCompound()) && (
/* 231:345 */       (event.item.getEntityItem().getTagCompound().hasKey("crafting")) || (event.item.getEntityItem().getTagCompound().hasKey("time"))))
/* 232:    */     {
/* 233:346 */       event.item.setDead();
/* 234:347 */       event.setCanceled(true);
/* 235:    */     }
/* 236:    */   }
/* 237:    */   
/* 238:    */   @SubscribeEvent
/* 239:    */   public void unstableIngotExploder(ItemTossEvent event)
/* 240:    */   {
/* 241:364 */     if ((ExtraUtils.unstableIngot != null) && 
/* 242:365 */       (event.entityItem.getEntityItem().getItem() == ExtraUtils.unstableIngot) && 
/* 243:366 */       (event.entityItem.getEntityItem().hasTagCompound()) && 
/* 244:367 */       (event.entityItem.getEntityItem().getTagCompound().hasKey("time"))) {
/* 245:368 */       ItemUnstableIngot.explode(event.player);
/* 246:    */     }
/* 247:    */   }
/* 248:    */   
/* 249:    */   @SubscribeEvent(priority=EventPriority.HIGHEST)
/* 250:    */   public void updateSoulDrain(LivingSpawnEvent event)
/* 251:    */   {
/* 252:376 */     if (event.world.isClient) {
/* 253:377 */       return;
/* 254:    */     }
/* 255:379 */     if (!EntityPlayerMP.class.equals(event.getClass())) {
/* 256:380 */       return;
/* 257:    */     }
/* 258:382 */     IAttributeInstance a = event.entityLiving.getAttributeMap().getAttributeInstanceByName(SharedMonsterAttributes.maxHealth.getAttributeUnlocalizedName());
/* 259:383 */     AttributeModifier attr = a.getModifier(ItemSoul.uuid);
/* 260:385 */     if ((attr == null) || (attr.getOperation() == 2)) {
/* 261:386 */       return;
/* 262:    */     }
/* 263:388 */     double l = attr.getAmount() / 20.0D;
/* 264:    */     
/* 265:390 */     a.removeModifier(attr);
/* 266:391 */     a.applyModifier(new AttributeModifier(ItemSoul.uuid, "Missing Soul", l, 0));
/* 267:    */   }
/* 268:    */   
/* 269:    */   @SubscribeEvent
/* 270:    */   public void persistSoulDrain(PlayerEvent.Clone event)
/* 271:    */   {
/* 272:397 */     if ((event.entityPlayer.worldObj.isClient) || (ExtraUtils.permaSoulDrainOff)) {
/* 273:398 */       return;
/* 274:    */     }
/* 275:400 */     EntityPlayer original = event.original;
/* 276:401 */     EntityPlayer clone = event.entityPlayer;
/* 277:    */     
/* 278:403 */     AttributeModifier attr = original.getAttributeMap().getAttributeInstanceByName(SharedMonsterAttributes.maxHealth.getAttributeUnlocalizedName()).getModifier(ItemSoul.uuid);
/* 279:404 */     if (attr == null)
/* 280:    */     {
/* 281:405 */       attr = clone.getAttributeMap().getAttributeInstanceByName(SharedMonsterAttributes.maxHealth.getAttributeUnlocalizedName()).getModifier(ItemSoul.uuid);
/* 282:406 */       if (attr != null) {
/* 283:407 */         clone = event.original;
/* 284:    */       }
/* 285:    */     }
/* 286:411 */     if (attr != null) {
/* 287:    */       try
/* 288:    */       {
/* 289:413 */         clone.getAttributeMap().getAttributeInstanceByName(SharedMonsterAttributes.maxHealth.getAttributeUnlocalizedName()).applyModifier(attr);
/* 290:    */       }
/* 291:    */       catch (IllegalArgumentException ignore) {}
/* 292:    */     }
/* 293:    */   }
/* 294:    */   
/* 295:    */   @SubscribeEvent
/* 296:    */   public void updateEntity(LivingEvent.LivingUpdateEvent event)
/* 297:    */   {
/* 298:421 */     if (event.entity.worldObj.isClient) {
/* 299:422 */       return;
/* 300:    */     }
/* 301:424 */     if (event.entity.getEntityData().hasKey("CursedEarth"))
/* 302:    */     {
/* 303:425 */       int c = event.entity.getEntityData().getInteger("CursedEarth");
/* 304:426 */       if (c == 0)
/* 305:    */       {
/* 306:427 */         event.entity.setDead();
/* 307:428 */         NetworkHandler.sendParticle(event.entity.worldObj, "smoke", event.entity.posX, event.entity.posY + event.entity.height / 4.0F, event.entity.posZ, 0.0D, 0.0D, 0.0D, false);
/* 308:    */       }
/* 309:    */       else
/* 310:    */       {
/* 311:433 */         c--;
/* 312:434 */         event.entity.getEntityData().setInteger("CursedEarth", c);
/* 313:    */       }
/* 314:    */     }
/* 315:    */   }
/* 316:    */   
/* 317:    */   @SubscribeEvent
/* 318:    */   public void ActivationRitual(LivingDeathEvent event)
/* 319:    */   {
/* 320:441 */     if ((ExtraUtils.divisionSigil == null) && (ExtraUtils.cursedEarth == null)) {
/* 321:442 */       return;
/* 322:    */     }
/* 323:445 */     if (!(event.source.getSourceOfDamage() instanceof EntityPlayer)) {
/* 324:446 */       return;
/* 325:    */     }
/* 326:449 */     EntityPlayer player = (EntityPlayer)event.source.getSourceOfDamage();
/* 327:451 */     if (ExtraUtils.divisionSigil != null)
/* 328:    */     {
/* 329:452 */       boolean flag = false;
/* 330:454 */       for (int j = 0; j < player.inventory.getSizeInventory(); j++)
/* 331:    */       {
/* 332:455 */         ItemStack item = player.inventory.getStackInSlot(j);
/* 333:457 */         if ((item != null) && (item.getItem() == ExtraUtils.divisionSigil))
/* 334:    */         {
/* 335:458 */           flag = true;
/* 336:459 */           break;
/* 337:    */         }
/* 338:    */       }
/* 339:463 */       if (!flag) {
/* 340:464 */         return;
/* 341:    */       }
/* 342:    */     }
/* 343:468 */     World world = event.entityLiving.worldObj;
/* 344:470 */     if (world.isClient) {
/* 345:471 */       return;
/* 346:    */     }
/* 347:474 */     int x = (int)event.entityLiving.posX;
/* 348:475 */     int y = (int)event.entityLiving.boundingBox.minY;
/* 349:476 */     int z = (int)event.entityLiving.posZ;
/* 350:477 */     boolean found = false;
/* 351:478 */     long time = world.getWorldTime() % 24000L;
/* 352:480 */     if ((time < 12000L) || (time > 24000L)) {
/* 353:481 */       return;
/* 354:    */     }
/* 355:484 */     for (int dx = -2; ((!found ? 1 : 0) & (dx <= 2 ? 1 : 0)) != 0; dx++) {
/* 356:485 */       for (int dy = -2; ((!found ? 1 : 0) & (dy <= 0 ? 1 : 0)) != 0; dy++) {
/* 357:486 */         for (int dz = -2; ((!found ? 1 : 0) & (dz <= 2 ? 1 : 0)) != 0; dz++) {
/* 358:487 */           if (world.getBlock(x + dx, y + dy, z + dz) == Blocks.enchanting_table)
/* 359:    */           {
/* 360:488 */             found = true;
/* 361:489 */             x += dx;
/* 362:490 */             y += dy;
/* 363:491 */             z += dz;
/* 364:    */           }
/* 365:    */         }
/* 366:    */       }
/* 367:    */     }
/* 368:497 */     if (!found) {
/* 369:498 */       return;
/* 370:    */     }
/* 371:501 */     if (world.getBlock(x, y, z) != Blocks.enchanting_table) {
/* 372:502 */       return;
/* 373:    */     }
/* 374:505 */     if (!ActivationRitual.redstoneCirclePresent(world, x, y, z)) {
/* 375:506 */       return;
/* 376:    */     }
/* 377:509 */     if (!ActivationRitual.altarCanSeeMoon(world, x, y, z))
/* 378:    */     {
/* 379:510 */       PacketTempChat.sendChat(player, "Activation Ritual Failure: Altar cannot see the moon");
/* 380:511 */       return;
/* 381:    */     }
/* 382:514 */     if (!ActivationRitual.altarOnEarth(world, x, y, z))
/* 383:    */     {
/* 384:515 */       PacketTempChat.sendChat(player, "Activation Ritual Failure: Altar and circle must be on natural earth");
/* 385:516 */       return;
/* 386:    */     }
/* 387:519 */     if (!ActivationRitual.altarInDarkness(world, x, y, z))
/* 388:    */     {
/* 389:520 */       PacketTempChat.sendChat(player, "Activation Ritual Failure: Altar is too brightly lit");
/* 390:521 */       return;
/* 391:    */     }
/* 392:524 */     if (!ActivationRitual.naturalEarth(world, x, y, z))
/* 393:    */     {
/* 394:525 */       PacketTempChat.sendChat(player, "Activation Ritual Failure: Altar requires more natural earth");
/* 395:526 */       return;
/* 396:    */     }
/* 397:529 */     int i = ActivationRitual.checkTime(world.getWorldTime());
/* 398:531 */     if (i == -1)
/* 399:    */     {
/* 400:532 */       PacketTempChat.sendChat(player, "Activation Ritual Failure: Too early");
/* 401:533 */       return;
/* 402:    */     }
/* 403:534 */     if (i == 1)
/* 404:    */     {
/* 405:535 */       PacketTempChat.sendChat(player, "Activation Ritual Failure: Too late");
/* 406:536 */       return;
/* 407:    */     }
/* 408:539 */     ActivationRitual.startRitual(world, x, y, z, player);
/* 409:    */     
/* 410:541 */     NetworkHandler.sendSoundEvent(world, 0, x + 0.5F, y + 0.5F, z + 0.5F);
/* 411:543 */     if (ExtraUtils.divisionSigil != null) {
/* 412:544 */       for (int j = 0; j < player.inventory.getSizeInventory(); j++)
/* 413:    */       {
/* 414:545 */         ItemStack item = player.inventory.getStackInSlot(j);
/* 415:547 */         if ((item != null) && (item.getItem() == ExtraUtils.divisionSigil))
/* 416:    */         {
/* 417:    */           NBTTagCompound tags;
/* 418:    */           NBTTagCompound tags;
/* 419:550 */           if (item.hasTagCompound()) {
/* 420:551 */             tags = item.getTagCompound();
/* 421:    */           } else {
/* 422:553 */             tags = new NBTTagCompound();
/* 423:    */           }
/* 424:556 */           tags.setInteger("damage", ItemDivisionSigil.maxdamage);
/* 425:557 */           item.setTagCompound(tags);
/* 426:    */         }
/* 427:    */       }
/* 428:    */     }
/* 429:    */   }
/* 430:    */   
/* 431:    */   @SubscribeEvent
/* 432:    */   public void goldenLasso(EntityInteractEvent event)
/* 433:    */   {
/* 434:564 */     ItemStack itemstack = event.entityPlayer.getCurrentEquippedItem();
/* 435:566 */     if ((itemstack != null) && 
/* 436:567 */       (ExtraUtils.goldenLasso != null) && 
/* 437:568 */       (itemstack.getItem() == ExtraUtils.goldenLasso) && 
/* 438:569 */       ((event.target instanceof EntityLivingBase)) && 
/* 439:570 */       (itemstack.interactWithEntity(event.entityPlayer, (EntityLivingBase)event.target)))
/* 440:    */     {
/* 441:571 */       if (itemstack.stackSize <= 0) {
/* 442:572 */         event.entityPlayer.destroyCurrentEquippedItem();
/* 443:    */       }
/* 444:575 */       event.setCanceled(true);
/* 445:    */     }
/* 446:    */   }
/* 447:    */   
/* 448:    */   @SubscribeEvent
/* 449:    */   public void captureGoldenBagDrop(PlayerDropsEvent event)
/* 450:    */   {
/* 451:584 */     if (ExtraUtils.goldenBag == null) {
/* 452:585 */       return;
/* 453:    */     }
/* 454:588 */     if (event.entity.getEntityData().getCompoundTag("PlayerPersisted").hasKey("XU|GoldenBags")) {
/* 455:589 */       return;
/* 456:    */     }
/* 457:592 */     int j = 0;
/* 458:593 */     NBTTagCompound t = new NBTTagCompound();
/* 459:595 */     for (int i = 0; i < event.drops.size(); i++)
/* 460:    */     {
/* 461:596 */       ItemStack item = ((EntityItem)event.drops.get(i)).getEntityItem();
/* 462:598 */       if ((item != null) && (item.getItem() == ExtraUtils.goldenBag) && (ItemGoldenBag.isMagic(item)))
/* 463:    */       {
/* 464:599 */         NBTTagCompound tags = new NBTTagCompound();
/* 465:600 */         item.writeToNBT(tags);
/* 466:601 */         t.setTag("item_" + j, tags);
/* 467:602 */         j++;
/* 468:603 */         event.drops.remove(i);
/* 469:604 */         i--;
/* 470:    */       }
/* 471:    */     }
/* 472:608 */     t.setInteger("no_items", j);
/* 473:    */     NBTTagCompound e;
/* 474:    */     NBTTagCompound e;
/* 475:611 */     if (event.entityPlayer.getEntityData().hasKey("PlayerPersisted"))
/* 476:    */     {
/* 477:612 */       e = event.entityPlayer.getEntityData().getCompoundTag("PlayerPersisted");
/* 478:    */     }
/* 479:    */     else
/* 480:    */     {
/* 481:614 */       e = new NBTTagCompound();
/* 482:615 */       event.entityPlayer.getEntityData().setTag("PlayerPersisted", e);
/* 483:    */     }
/* 484:618 */     e.setTag("XU|GoldenBags", t);
/* 485:    */   }
/* 486:    */   
/* 487:    */   @SubscribeEvent
/* 488:    */   public void recreateGoldenBags(EntityJoinWorldEvent event)
/* 489:    */   {
/* 490:623 */     if ((event.world.isClient) || (ExtraUtils.goldenBag == null)) {
/* 491:624 */       return;
/* 492:    */     }
/* 493:627 */     if (((event.entity instanceof EntityPlayer)) && 
/* 494:628 */       (event.entity.getEntityData().hasKey("PlayerPersisted")) && 
/* 495:629 */       (event.entity.getEntityData().getCompoundTag("PlayerPersisted").hasKey("XU|GoldenBags")))
/* 496:    */     {
/* 497:630 */       NBTTagCompound tags = event.entity.getEntityData().getCompoundTag("PlayerPersisted").getCompoundTag("XU|GoldenBags");
/* 498:631 */       int n = tags.getInteger("no_items");
/* 499:633 */       for (int i = 0; i < n; i++)
/* 500:    */       {
/* 501:634 */         ItemStack item = ItemStack.loadItemStackFromNBT(tags.getCompoundTag("item_" + i));
/* 502:636 */         if (item != null) {
/* 503:637 */           ((EntityPlayer)event.entity).inventory.addItemStackToInventory(ItemGoldenBag.clearMagic(item));
/* 504:    */         }
/* 505:    */       }
/* 506:641 */       event.entity.getEntityData().getCompoundTag("PlayerPersisted").removeTag("XU|GoldenBags");
/* 507:    */     }
/* 508:    */   }
/* 509:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.EventHandlerServer
 * JD-Core Version:    0.7.0.1
 */