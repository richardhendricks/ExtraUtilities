/*   1:    */ package com.rwtema.extrautils.item;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.LogHelper;
/*   5:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   6:    */ import com.rwtema.extrautils.helper.XURandom;
/*   7:    */ import cpw.mods.fml.common.Loader;
/*   8:    */ import cpw.mods.fml.relauncher.Side;
/*   9:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  10:    */ import java.lang.reflect.Field;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Random;
/*  14:    */ import net.minecraft.block.Block;
/*  15:    */ import net.minecraft.block.BlockLiquid;
/*  16:    */ import net.minecraft.block.BlockSapling;
/*  17:    */ import net.minecraft.block.IGrowable;
/*  18:    */ import net.minecraft.block.material.Material;
/*  19:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  20:    */ import net.minecraft.creativetab.CreativeTabs;
/*  21:    */ import net.minecraft.entity.Entity;
/*  22:    */ import net.minecraft.entity.monster.EntityEnderman;
/*  23:    */ import net.minecraft.entity.player.EntityPlayer;
/*  24:    */ import net.minecraft.init.Blocks;
/*  25:    */ import net.minecraft.item.EnumAction;
/*  26:    */ import net.minecraft.item.Item;
/*  27:    */ import net.minecraft.item.ItemBlock;
/*  28:    */ import net.minecraft.item.ItemStack;
/*  29:    */ import net.minecraft.tileentity.TileEntity;
/*  30:    */ import net.minecraft.util.AxisAlignedBB;
/*  31:    */ import net.minecraft.util.DamageSource;
/*  32:    */ import net.minecraft.util.IIcon;
/*  33:    */ import net.minecraft.util.MovingObjectPosition;
/*  34:    */ import net.minecraft.util.MovingObjectPosition.MovingObjectType;
/*  35:    */ import net.minecraft.util.Vec3;
/*  36:    */ import net.minecraft.world.World;
/*  37:    */ import net.minecraft.world.biome.BiomeGenBase;
/*  38:    */ import net.minecraftforge.common.IPlantable;
/*  39:    */ 
/*  40:    */ public class ItemWateringCan
/*  41:    */   extends Item
/*  42:    */ {
/*  43: 34 */   public static ArrayList<ItemStack> flowers = null;
/*  44: 35 */   private static Random rand = XURandom.getInstance();
/*  45: 36 */   IIcon busted = null;
/*  46: 37 */   IIcon reinforced = null;
/*  47:    */   
/*  48:    */   public ItemWateringCan()
/*  49:    */   {
/*  50: 41 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  51: 42 */     setUnlocalizedName("extrautils:watering_can");
/*  52: 43 */     setTextureName("extrautils:watering_can");
/*  53: 44 */     setHasSubtypes(false);
/*  54: 45 */     setMaxStackSize(1);
/*  55:    */   }
/*  56:    */   
/*  57:    */   @SideOnly(Side.CLIENT)
/*  58:    */   public IIcon getIconFromDamage(int par1)
/*  59:    */   {
/*  60: 54 */     if (par1 == 2) {
/*  61: 55 */       return this.busted;
/*  62:    */     }
/*  63: 56 */     if (par1 == 3) {
/*  64: 57 */       return this.reinforced;
/*  65:    */     }
/*  66: 59 */     return this.itemIcon;
/*  67:    */   }
/*  68:    */   
/*  69:    */   @SideOnly(Side.CLIENT)
/*  70:    */   public void registerIcons(IIconRegister par1IIconRegister)
/*  71:    */   {
/*  72: 65 */     this.itemIcon = par1IIconRegister.registerIcon(getIconString());
/*  73: 66 */     this.busted = par1IIconRegister.registerIcon("extrautils:watering_can_bust");
/*  74: 67 */     this.reinforced = par1IIconRegister.registerIcon("extrautils:watering_can_reinforced");
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getMaxItemUseDuration(ItemStack par1ItemStack)
/*  78:    */   {
/*  79: 75 */     return 72000;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
/*  83:    */   {
/*  84: 79 */     if ((par1ItemStack.getItemDamage() != 3) || (!XUHelper.isPlayerFake(par2EntityPlayer))) {
/*  85: 80 */       return false;
/*  86:    */     }
/*  87: 82 */     waterLocation(par3World, par4 + 0.5D, par5 + 0.5D, par6 + 0.5D, par7, par1ItemStack, par2EntityPlayer);
/*  88: 83 */     return true;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public ItemStack onItemRightClick(ItemStack par1ItemStack, World world, EntityPlayer player)
/*  92:    */   {
/*  93: 92 */     if (par1ItemStack.getItemDamage() != 1)
/*  94:    */     {
/*  95: 93 */       if (XUHelper.isPlayerFake(player))
/*  96:    */       {
/*  97: 94 */         if (par1ItemStack.getItemDamage() == 0) {
/*  98: 95 */           par1ItemStack.setItemDamage(2);
/*  99:    */         } else {
/* 100: 97 */           onUsingTick(par1ItemStack, player, 0);
/* 101:    */         }
/* 102:    */       }
/* 103:100 */       else if ((par1ItemStack.getItemDamage() == 2) && 
/* 104:101 */         (XUHelper.isThisPlayerACheatyBastardOfCheatBastardness(player))) {
/* 105:102 */         par1ItemStack.setItemDamage(4);
/* 106:    */       }
/* 107:106 */       player.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
/* 108:    */     }
/* 109:    */     else
/* 110:    */     {
/* 111:108 */       MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(world, player, true);
/* 112:110 */       if (movingobjectposition == null) {
/* 113:111 */         return par1ItemStack;
/* 114:    */       }
/* 115:113 */       if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
/* 116:    */       {
/* 117:114 */         int i = movingobjectposition.blockX;
/* 118:115 */         int j = movingobjectposition.blockY;
/* 119:116 */         int k = movingobjectposition.blockZ;
/* 120:118 */         if (world.getBlock(i, j, k).getMaterial() == Material.water)
/* 121:    */         {
/* 122:119 */           if (XUHelper.isThisPlayerACheatyBastardOfCheatBastardness(player)) {
/* 123:120 */             par1ItemStack.setItemDamage(3);
/* 124:    */           } else {
/* 125:122 */             par1ItemStack.setItemDamage(0);
/* 126:    */           }
/* 127:123 */           return par1ItemStack;
/* 128:    */         }
/* 129:    */       }
/* 130:127 */       return par1ItemStack;
/* 131:    */     }
/* 132:131 */     return par1ItemStack;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public String getUnlocalizedName(ItemStack par1ItemStack)
/* 136:    */   {
/* 137:141 */     if (par1ItemStack.getItemDamage() == 1) {
/* 138:142 */       return super.getUnlocalizedName() + ".empty";
/* 139:    */     }
/* 140:145 */     if (par1ItemStack.getItemDamage() == 2) {
/* 141:146 */       return super.getUnlocalizedName() + ".busted";
/* 142:    */     }
/* 143:149 */     if (par1ItemStack.getItemDamage() == 3) {
/* 144:150 */       return super.getUnlocalizedName() + ".reinforced";
/* 145:    */     }
/* 146:154 */     return super.getUnlocalizedName();
/* 147:    */   }
/* 148:    */   
/* 149:    */   public EnumAction getItemUseAction(ItemStack par1ItemStack)
/* 150:    */   {
/* 151:163 */     return EnumAction.none;
/* 152:    */   }
/* 153:    */   
/* 154:    */   @SideOnly(Side.CLIENT)
/* 155:    */   public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/* 156:    */   {
/* 157:172 */     par3List.add(new ItemStack(par1, 1, 0));
/* 158:173 */     par3List.add(new ItemStack(par1, 1, 1));
/* 159:174 */     par3List.add(new ItemStack(par1, 1, 2));
/* 160:175 */     par3List.add(new ItemStack(par1, 1, 3));
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void initFlowers()
/* 164:    */   {
/* 165:179 */     if (flowers != null) {
/* 166:180 */       return;
/* 167:    */     }
/* 168:183 */     flowers = new ArrayList();
/* 169:185 */     if (!Loader.isModLoaded("Forestry")) {
/* 170:186 */       return;
/* 171:    */     }
/* 172:    */     try
/* 173:    */     {
/* 174:190 */       Class flowerManager = Class.forName("forestry.api.apiculture.FlowerManager");
/* 175:191 */       ArrayList<ItemStack> temp = (ArrayList)flowerManager.getField("plainFlowers").get(null);
/* 176:192 */       flowers.addAll(temp);
/* 177:    */     }
/* 178:    */     catch (Exception e)
/* 179:    */     {
/* 180:194 */       e.printStackTrace();
/* 181:    */     }
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
/* 185:    */   {
/* 186:201 */     MovingObjectPosition pos = getMovingObjectPositionFromPlayer(player.worldObj, player, true);
/* 187:203 */     if (pos != null) {
/* 188:204 */       waterLocation(player.worldObj, pos.hitVec.xCoord, pos.hitVec.yCoord, pos.hitVec.zCoord, pos.sideHit, stack, player);
/* 189:    */     }
/* 190:    */   }
/* 191:    */   
/* 192:208 */   public ThreadLocal<Boolean> watering = new ThreadLocal();
/* 193:    */   
/* 194:    */   public void waterLocation(World worldObj, double hitX, double hitY, double hitZ, int side, ItemStack stack, EntityPlayer play)
/* 195:    */   {
/* 196:211 */     int range = 1;
/* 197:212 */     if (stack.getItemDamage() == 3) {
/* 198:213 */       range = 3;
/* 199:    */     }
/* 200:214 */     if (stack.getItemDamage() == 4) {
/* 201:215 */       range = 5;
/* 202:    */     }
/* 203:217 */     if (this.watering.get() == Boolean.TRUE) {
/* 204:217 */       return;
/* 205:    */     }
/* 206:218 */     this.watering.set(Boolean.TRUE);
/* 207:219 */     waterLocation(worldObj, hitX, hitY, hitZ, side, stack, play, range);
/* 208:220 */     this.watering.set(Boolean.FALSE);
/* 209:    */   }
/* 210:    */   
/* 211:    */   private void waterLocation(World worldObj, double hitX, double hitY, double hitZ, int side, ItemStack stack, EntityPlayer play, int range)
/* 212:    */   {
/* 213:225 */     List enderman = worldObj.getEntitiesWithinAABB(EntityEnderman.class, AxisAlignedBB.getBoundingBox(hitX - range, hitY - range, hitZ - range, hitX + range, hitY + 6.0D, hitZ + range));
/* 214:228 */     if (enderman != null) {
/* 215:229 */       for (Object anEnderman : enderman) {
/* 216:230 */         ((EntityEnderman)anEnderman).attackEntityFrom(DamageSource.drown, 1.0F);
/* 217:    */       }
/* 218:    */     }
/* 219:234 */     boolean cheat = (stack.getItemDamage() == 4) && ((XUHelper.isThisPlayerACheatyBastardOfCheatBastardness(play)) || (LogHelper.isDeObf) || (XUHelper.isPlayerFake(play)));
/* 220:236 */     if (worldObj.isClient)
/* 221:    */     {
/* 222:237 */       double dx = net.minecraft.util.Facing.offsetsXForSide[side];double dy = net.minecraft.util.Facing.offsetsYForSide[side];double dz = net.minecraft.util.Facing.offsetsZForSide[side];
/* 223:238 */       double x2 = hitX + dx * 0.1D;double y2 = hitY + dy * 0.1D;double z2 = hitZ + dz * 0.1D;
/* 224:240 */       for (int i = 0; i < (stack.getItemDamage() == 3 ? 100 : stack.getItemDamage() == 2 ? 1 : 10); i++) {
/* 225:241 */         worldObj.spawnParticle("splash", x2 + worldObj.rand.nextGaussian() * 0.6D * range, y2, z2 + worldObj.rand.nextGaussian() * 0.6D * range, 0.0D, 0.0D, 0.0D);
/* 226:    */       }
/* 227:    */     }
/* 228:    */     else
/* 229:    */     {
/* 230:244 */       List ents = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(hitX - range, hitY - range, hitZ - range, hitX + range, hitY + range + 6.0D, hitZ + range));
/* 231:247 */       if (ents != null) {
/* 232:248 */         for (Object ent : ents) {
/* 233:249 */           if (((Entity)ent).isBurning())
/* 234:    */           {
/* 235:250 */             float p = 0.01F;
/* 236:252 */             if ((ent instanceof EntityPlayer)) {
/* 237:253 */               p = 0.333F;
/* 238:    */             }
/* 239:256 */             ((Entity)ent).extinguish();
/* 240:258 */             if (worldObj.rand.nextDouble() < p)
/* 241:    */             {
/* 242:259 */               if (stack.getItemDamage() < 3) {
/* 243:260 */                 stack.setItemDamage(1);
/* 244:    */               }
/* 245:261 */               if (play != null) {
/* 246:262 */                 play.stopUsingItem();
/* 247:    */               }
/* 248:263 */               return;
/* 249:    */             }
/* 250:    */           }
/* 251:    */         }
/* 252:    */       }
/* 253:269 */       int blockX = (int)Math.floor(hitX);
/* 254:270 */       int blockY = (int)Math.floor(hitY);
/* 255:271 */       int blockZ = (int)Math.floor(hitZ);
/* 256:273 */       for (int x = blockX - range; x <= blockX + range; x++) {
/* 257:274 */         for (int y = blockY - range; y <= blockY + range; y++) {
/* 258:275 */           for (int z = blockZ - range; z <= blockZ + range; z++)
/* 259:    */           {
/* 260:276 */             Block id = worldObj.getBlock(x, y, z);
/* 261:278 */             if (!worldObj.isAirBlock(x, y, z))
/* 262:    */             {
/* 263:279 */               if (id == Blocks.fire) {
/* 264:280 */                 worldObj.setBlockToAir(x, y, z);
/* 265:    */               }
/* 266:283 */               if ((id == Blocks.flowing_lava) && 
/* 267:284 */                 (worldObj.rand.nextInt(2) == 0)) {
/* 268:285 */                 Blocks.flowing_lava.updateTick(worldObj, x, y, z, worldObj.rand);
/* 269:    */               }
/* 270:289 */               if ((id == Blocks.farmland) && 
/* 271:290 */                 (worldObj.getBlockMetadata(x, y, z) < 7)) {
/* 272:291 */                 worldObj.setBlockMetadataWithNotify(x, y, z, 7, 2);
/* 273:    */               }
/* 274:295 */               int timer = -1;
/* 275:297 */               if (id == Blocks.grass)
/* 276:    */               {
/* 277:298 */                 timer = 20;
/* 278:300 */                 if ((!cheat) && (worldObj.rand.nextInt(4500) == 0) && 
/* 279:301 */                   (worldObj.isAirBlock(x, y + 1, z)))
/* 280:    */                 {
/* 281:302 */                   initFlowers();
/* 282:304 */                   if ((flowers.size() > 0) && (worldObj.rand.nextInt(5) == 0))
/* 283:    */                   {
/* 284:305 */                     ItemStack flower = (ItemStack)flowers.get(worldObj.rand.nextInt(flowers.size()));
/* 285:307 */                     if (((flower.getItem() instanceof ItemBlock)) && 
/* 286:308 */                       (play != null)) {
/* 287:309 */                       ((ItemBlock)flower.getItem()).placeBlockAt(flower, play, worldObj, x, y + 1, z, 1, 0.5F, 1.0F, 0.5F, flower.getItem().getMetadata(flower.getItemDamage()));
/* 288:    */                     }
/* 289:    */                   }
/* 290:    */                   else
/* 291:    */                   {
/* 292:313 */                     worldObj.getBiomeGenForCoords(x, z).plantFlower(worldObj, rand, x, y + 1, z);
/* 293:    */                   }
/* 294:    */                 }
/* 295:    */               }
/* 296:317 */               else if (id == Blocks.mycelium)
/* 297:    */               {
/* 298:318 */                 timer = 20;
/* 299:    */               }
/* 300:319 */               else if (id == Blocks.wheat)
/* 301:    */               {
/* 302:320 */                 timer = 40;
/* 303:    */               }
/* 304:321 */               else if ((id instanceof BlockSapling))
/* 305:    */               {
/* 306:322 */                 timer = 50;
/* 307:    */               }
/* 308:323 */               else if (((id instanceof IPlantable)) || ((id instanceof IGrowable)))
/* 309:    */               {
/* 310:324 */                 timer = 40;
/* 311:    */               }
/* 312:325 */               else if (id.getMaterial() == Material.grass)
/* 313:    */               {
/* 314:326 */                 timer = 20;
/* 315:    */               }
/* 316:329 */               if (stack.getItemDamage() == 2) {
/* 317:330 */                 timer *= 20;
/* 318:    */               }
/* 319:333 */               timer /= range;
/* 320:335 */               if ((timer > 0) && 
/* 321:336 */                 (id.getTickRandomly())) {
/* 322:337 */                 worldObj.scheduleBlockUpdate(x, y, z, id, worldObj.rand.nextInt(timer));
/* 323:    */               }
/* 324:    */             }
/* 325:    */           }
/* 326:    */         }
/* 327:    */       }
/* 328:344 */       if (cheat) {
/* 329:345 */         for (int i = 0; i < 100; i++) {
/* 330:346 */           for (int x = blockX - range; x <= blockX + range; x++) {
/* 331:347 */             for (int y = blockY - range; y <= blockY + range; y++) {
/* 332:348 */               for (int z = blockZ - range; z <= blockZ + range; z++)
/* 333:    */               {
/* 334:349 */                 Block block = worldObj.getBlock(x, y, z);
/* 335:    */                 
/* 336:351 */                 block.updateTick(worldObj, x, y, z, worldObj.rand);
/* 337:    */                 
/* 338:353 */                 TileEntity tile = worldObj.getTileEntity(x, y, z);
/* 339:354 */                 if ((tile != null) && (tile.canUpdate()) && (!tile.isInvalid())) {
/* 340:355 */                   tile.updateEntity();
/* 341:    */                 }
/* 342:    */               }
/* 343:    */             }
/* 344:    */           }
/* 345:    */         }
/* 346:    */       }
/* 347:    */     }
/* 348:    */   }
/* 349:    */   
/* 350:    */   @SideOnly(Side.CLIENT)
/* 351:    */   public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
/* 352:    */   {
/* 353:367 */     if (par1ItemStack.getItemDamage() == 2)
/* 354:    */     {
/* 355:368 */       par3List.add("It appears that mechanical hands are not delicate enough");
/* 356:369 */       par3List.add("to use the watering can properly.");
/* 357:    */     }
/* 358:    */   }
/* 359:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemWateringCan
 * JD-Core Version:    0.7.0.1
 */