/*   1:    */ package com.rwtema.extrautils.block;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*   5:    */ import com.rwtema.extrautils.tileentity.TileEntityEnchantedSpike;
/*   6:    */ import cpw.mods.fml.relauncher.ReflectionHelper;
/*   7:    */ import cpw.mods.fml.relauncher.Side;
/*   8:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   9:    */ import java.lang.reflect.Field;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Random;
/*  13:    */ import net.minecraft.block.Block;
/*  14:    */ import net.minecraft.block.material.Material;
/*  15:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  16:    */ import net.minecraft.enchantment.Enchantment;
/*  17:    */ import net.minecraft.enchantment.EnchantmentHelper;
/*  18:    */ import net.minecraft.entity.Entity;
/*  19:    */ import net.minecraft.entity.EntityLiving;
/*  20:    */ import net.minecraft.entity.EntityLivingBase;
/*  21:    */ import net.minecraft.entity.item.EntityItem;
/*  22:    */ import net.minecraft.entity.player.EntityPlayer;
/*  23:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  24:    */ import net.minecraft.init.Items;
/*  25:    */ import net.minecraft.item.Item;
/*  26:    */ import net.minecraft.item.ItemStack;
/*  27:    */ import net.minecraft.nbt.NBTTagList;
/*  28:    */ import net.minecraft.tileentity.TileEntity;
/*  29:    */ import net.minecraft.util.AxisAlignedBB;
/*  30:    */ import net.minecraft.util.DamageSource;
/*  31:    */ import net.minecraft.util.EntityDamageSource;
/*  32:    */ import net.minecraft.util.IIcon;
/*  33:    */ import net.minecraft.util.MovingObjectPosition;
/*  34:    */ import net.minecraft.world.IBlockAccess;
/*  35:    */ import net.minecraft.world.World;
/*  36:    */ import net.minecraft.world.WorldServer;
/*  37:    */ import net.minecraftforge.common.util.FakePlayer;
/*  38:    */ import net.minecraftforge.common.util.FakePlayerFactory;
/*  39:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  40:    */ 
/*  41:    */ public class BlockSpike
/*  42:    */   extends Block
/*  43:    */ {
/*  44: 39 */   public static final Field experienceField = ReflectionHelper.findField(EntityLiving.class, new String[] { "experienceValue", "experienceValue" });
/*  45:    */   protected IIcon ironIcon;
/*  46:    */   public final ItemStack swordStack;
/*  47:    */   public Item itemSpike;
/*  48:    */   
/*  49:    */   public BlockSpike()
/*  50:    */   {
/*  51: 47 */     this(Material.iron, new ItemStack(Items.iron_sword));
/*  52:    */   }
/*  53:    */   
/*  54:    */   public BlockSpike(ItemStack swordStack)
/*  55:    */   {
/*  56: 51 */     this(Material.iron, swordStack);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public BlockSpike(Material material, ItemStack swordStack)
/*  60:    */   {
/*  61: 55 */     super(material);
/*  62: 56 */     setBlockName("extrautils:spike_base");
/*  63: 57 */     setBlockTextureName("extrautils:spike_base");
/*  64: 58 */     setHardness(5.0F);
/*  65: 59 */     setResistance(500.0F);
/*  66: 60 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  67: 61 */     setStepSound(Block.soundTypeMetal);
/*  68: 62 */     this.swordStack = swordStack;
/*  69:    */   }
/*  70:    */   
/*  71:    */   @SideOnly(Side.CLIENT)
/*  72:    */   public void registerBlockIcons(IIconRegister par1IIconRegister)
/*  73:    */   {
/*  74: 72 */     super.registerBlockIcons(par1IIconRegister);
/*  75: 73 */     this.ironIcon = par1IIconRegister.registerIcon("extrautils:spike_side");
/*  76:    */   }
/*  77:    */   
/*  78:    */   @SideOnly(Side.CLIENT)
/*  79:    */   public IIcon getIcon(int par1, int par2)
/*  80:    */   {
/*  81: 82 */     int side = par2 % 6;
/*  82: 84 */     if (par1 == net.minecraft.util.Facing.oppositeSide[side]) {
/*  83: 85 */       return this.blockIcon;
/*  84:    */     }
/*  85: 88 */     return this.ironIcon;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public boolean renderAsNormalBlock()
/*  89:    */   {
/*  90: 93 */     return false;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public boolean isOpaqueCube()
/*  94:    */   {
/*  95:103 */     return false;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public int getRenderType()
/*  99:    */   {
/* 100:111 */     return ExtraUtilsProxy.spikeBlockID;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
/* 104:    */   {
/* 105:120 */     int meta = par5 % 6;
/* 106:121 */     int flag = 0;
/* 107:122 */     ForgeDirection side = ForgeDirection.getOrientation(meta);
/* 108:123 */     if (!par1World.isSideSolid(par2 - side.offsetX, par3 - side.offsetY, par4 - side.offsetZ, side.getOpposite())) {
/* 109:124 */       for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
/* 110:125 */         if (side != dir)
/* 111:    */         {
/* 112:126 */           if (par1World.isSideSolid(par2 - dir.offsetX, par3 - dir.offsetY, par4 - dir.offsetZ, dir.getOpposite())) {
/* 113:127 */             return flag + dir.ordinal();
/* 114:    */           }
/* 115:129 */           if (par1World.getBlock(par2 - dir.offsetX, par3 - dir.offsetY, par4 - dir.offsetZ) == this) {
/* 116:130 */             par5 = par1World.getBlockMetadata(par2 - dir.offsetX, par3 - dir.offsetY, par4 - dir.offsetZ) % 6;
/* 117:    */           }
/* 118:    */         }
/* 119:    */       }
/* 120:    */     }
/* 121:136 */     return flag + par5;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest)
/* 125:    */   {
/* 126:142 */     if ((!player.capabilities.isCreativeMode) && (canHarvestBlock(player, world.getBlockMetadata(x, y, z))))
/* 127:    */     {
/* 128:143 */       ArrayList<ItemStack> items = getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
/* 129:145 */       if (world.setBlockToAir(x, y, z))
/* 130:    */       {
/* 131:146 */         if (!world.isClient) {
/* 132:147 */           for (ItemStack item : items) {
/* 133:149 */             dropBlockAsItem_do(world, x, y, z, item);
/* 134:    */           }
/* 135:    */         }
/* 136:153 */         return true;
/* 137:    */       }
/* 138:155 */       return false;
/* 139:    */     }
/* 140:158 */     return super.removedByPlayer(world, player, x, y, z, willHarvest);
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
/* 144:    */   {
/* 145:164 */     par2EntityPlayer.addStat(net.minecraft.stats.StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
/* 146:165 */     par2EntityPlayer.addExhaustion(0.025F);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
/* 150:    */   {
/* 151:170 */     return (ItemStack)getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0).get(0);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player)
/* 155:    */   {
/* 156:175 */     return (ItemStack)getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0).get(0);
/* 157:    */   }
/* 158:    */   
/* 159:    */   protected ItemStack createStackedBlock(int p_149644_1_)
/* 160:    */   {
/* 161:180 */     return new ItemStack(this.itemSpike, 1, 0);
/* 162:    */   }
/* 163:    */   
/* 164:    */   public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
/* 165:    */   {
/* 166:185 */     ArrayList<ItemStack> ret = new ArrayList();
/* 167:186 */     Item item = getItemDropped(metadata, world.rand, fortune);
/* 168:    */     
/* 169:188 */     ItemStack stack = new ItemStack(item, 1, damageDropped(metadata));
/* 170:189 */     TileEntity tile = world.getTileEntity(x, y, z);
/* 171:190 */     if ((tile instanceof TileEntityEnchantedSpike)) {
/* 172:191 */       stack.setTagCompound(((TileEntityEnchantedSpike)tile).getEnchantmentTagList());
/* 173:    */     }
/* 174:194 */     ret.add(stack);
/* 175:195 */     return ret;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
/* 179:    */   {
/* 180:200 */     NBTTagList enchantments = stack.getEnchantmentTagList();
/* 181:201 */     if (enchantments != null)
/* 182:    */     {
/* 183:202 */       int newmeta = 6 + world.getBlockMetadata(x, y, z) % 6;
/* 184:203 */       world.setBlock(x, y, z, this, newmeta, 2);
/* 185:204 */       TileEntity tile = world.getTileEntity(x, y, z);
/* 186:205 */       if ((tile instanceof TileEntityEnchantedSpike)) {
/* 187:206 */         ((TileEntityEnchantedSpike)tile).setEnchantmentTagList(enchantments);
/* 188:    */       }
/* 189:    */     }
/* 190:    */   }
/* 191:    */   
/* 192:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
/* 193:    */   {
/* 194:217 */     double h = 0.0625D;
/* 195:218 */     int side = par1World.getBlockMetadata(par2, par3, par4) % 6;
/* 196:220 */     switch (side)
/* 197:    */     {
/* 198:    */     case 0: 
/* 199:222 */       return AxisAlignedBB.getBoundingBox(par2 + h, par3 + h, par4 + h, par2 + 1.0D - h, par3 + 1.0D, par4 + 1.0D - h);
/* 200:    */     case 1: 
/* 201:225 */       return AxisAlignedBB.getBoundingBox(par2 + h, par3, par4 + h, par2 + 1.0D - h, par3 + 1.0D - h, par4 + 1.0D - h);
/* 202:    */     case 2: 
/* 203:228 */       return AxisAlignedBB.getBoundingBox(par2 + h, par3 + h, par4 + h, par2 + 1.0D - h, par3 + 1.0D - h, par4 + 1.0D);
/* 204:    */     case 3: 
/* 205:231 */       return AxisAlignedBB.getBoundingBox(par2 + h, par3 + h, par4, par2 + 1.0D - h, par3 + 1.0D - h, par4 + 1.0D - h);
/* 206:    */     case 4: 
/* 207:234 */       return AxisAlignedBB.getBoundingBox(par2 + h, par3 + h, par4 + h, par2 + 1.0D, par3 + 1.0D - h, par4 + 1.0D - h);
/* 208:    */     case 5: 
/* 209:237 */       return AxisAlignedBB.getBoundingBox(par2, par3 + h, par4 + h, par2 + 1.0D - h, par3 + 1.0D - h, par4 + 1.0D - h);
/* 210:    */     }
/* 211:239 */     return AxisAlignedBB.getBoundingBox(par2 + h, par3 + h, par4 + h, par2 + 1.0D - h, par3 + 1.0D - h, par4 + 1.0D - h);
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity target)
/* 215:    */   {
/* 216:249 */     if ((world.isClient) || (!(world instanceof WorldServer))) {
/* 217:250 */       return;
/* 218:    */     }
/* 219:253 */     FakePlayer fakeplayer = FakePlayerFactory.getMinecraft((WorldServer)world);
/* 220:256 */     if (!(target instanceof EntityLivingBase)) {
/* 221:257 */       return;
/* 222:    */     }
/* 223:261 */     TileEntity tile = world.getTileEntity(x, y, z);
/* 224:262 */     float damage = getDamageMultipliers(4.0F, tile, (EntityLivingBase)target);
/* 225:    */     
/* 226:264 */     float h = ((EntityLivingBase)target).getHealth();
/* 227:    */     boolean flag;
/* 228:    */     boolean flag;
/* 229:266 */     if ((h > damage) || ((target instanceof EntityPlayer)))
/* 230:    */     {
/* 231:267 */       flag = target.attackEntityFrom(DamageSource.cactus, damage - 0.001F);
/* 232:    */     }
/* 233:    */     else
/* 234:    */     {
/* 235:    */       boolean flag;
/* 236:270 */       if (world.rand.nextInt(5) == 0) {
/* 237:271 */         flag = doPlayerLastHit((WorldServer)world, target, tile);
/* 238:    */       } else {
/* 239:273 */         flag = target.attackEntityFrom(DamageSource.cactus, 400.0F);
/* 240:    */       }
/* 241:    */     }
/* 242:277 */     if (flag)
/* 243:    */     {
/* 244:278 */       doPostAttack(world, target, tile, x, y, z);
/* 245:279 */       if ((target instanceof EntityLiving)) {
/* 246:    */         try
/* 247:    */         {
/* 248:281 */           experienceField.setInt(target, 0);
/* 249:    */         }
/* 250:    */         catch (IllegalAccessException ignore) {}
/* 251:    */       }
/* 252:    */     }
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void doPostAttack(World world, Entity target, TileEntity tile, int x, int y, int z)
/* 256:    */   {
/* 257:290 */     if (!(tile instanceof TileEntityEnchantedSpike)) {
/* 258:291 */       return;
/* 259:    */     }
/* 260:294 */     ItemStack stack = this.swordStack.copy();
/* 261:295 */     stack.setTagCompound(((TileEntityEnchantedSpike)tile).getEnchantmentTagList());
/* 262:    */     
/* 263:297 */     int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, stack);
/* 264:299 */     if (i > 0)
/* 265:    */     {
/* 266:300 */       int m = world.getBlockMetadata(x, y, z) % 6;
/* 267:    */       
/* 268:302 */       float dx = net.minecraft.util.Facing.offsetsXForSide[m];
/* 269:303 */       float dz = net.minecraft.util.Facing.offsetsZForSide[m];
/* 270:304 */       target.addVelocity(-dx * i * 0.5F, 0.1D, -dz * i * 0.5F);
/* 271:    */     }
/* 272:307 */     int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);
/* 273:309 */     if (j > 0) {
/* 274:310 */       target.setFire(j * 4);
/* 275:    */     }
/* 276:    */   }
/* 277:    */   
/* 278:    */   public Item getSwordItem()
/* 279:    */   {
/* 280:316 */     return Items.iron_sword;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public float getDamageMultipliers(float f, TileEntity tile, EntityLivingBase target)
/* 284:    */   {
/* 285:320 */     if (!(tile instanceof TileEntityEnchantedSpike)) {
/* 286:321 */       return f;
/* 287:    */     }
/* 288:325 */     ItemStack swordStack = new ItemStack(getSwordItem(), 1, 0);
/* 289:    */     
/* 290:327 */     swordStack.setTagCompound(((TileEntityEnchantedSpike)tile).getEnchantmentTagList());
/* 291:328 */     float f1 = EnchantmentHelper.func_152377_a(swordStack, target.getCreatureAttribute());
/* 292:329 */     if (f1 > 0.0F) {
/* 293:329 */       f += f1;
/* 294:    */     }
/* 295:331 */     return f;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
/* 299:    */   {
/* 300:342 */     if ((par7Entity instanceof EntityItem))
/* 301:    */     {
/* 302:343 */       AxisAlignedBB axisalignedbb1 = AxisAlignedBB.getBoundingBox(par2, par3, par4, par2 + 1, par3 + 1, par4 + 1);
/* 303:345 */       if ((axisalignedbb1 != null) && (par5AxisAlignedBB.intersectsWith(axisalignedbb1))) {
/* 304:346 */         par6List.add(axisalignedbb1);
/* 305:    */       }
/* 306:    */     }
/* 307:    */     else
/* 308:    */     {
/* 309:349 */       super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
/* 310:    */     }
/* 311:    */   }
/* 312:    */   
/* 313:    */   public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
/* 314:    */   {
/* 315:356 */     return world.getBlockMetadata(x, y, z) % 6 == side.getOpposite().ordinal();
/* 316:    */   }
/* 317:    */   
/* 318:    */   public boolean canPlaceTorchOnTop(World world, int x, int y, int z)
/* 319:    */   {
/* 320:362 */     return (world.getBlockMetadata(x, y, z) % 6 == 1) || (super.canPlaceTorchOnTop(world, x, y, z));
/* 321:    */   }
/* 322:    */   
/* 323:    */   public boolean hasTileEntity(int metadata)
/* 324:    */   {
/* 325:368 */     return metadata >= 6;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public TileEntity createTileEntity(World world, int metadata)
/* 329:    */   {
/* 330:373 */     return new TileEntityEnchantedSpike();
/* 331:    */   }
/* 332:    */   
/* 333:    */   public boolean doPlayerLastHit(World world, Entity target, TileEntity tile)
/* 334:    */   {
/* 335:377 */     FakePlayer fakePlayer = FakePlayerFactory.getMinecraft((WorldServer)world);
/* 336:    */     try
/* 337:    */     {
/* 338:380 */       ItemStack stack = this.swordStack.copy();
/* 339:382 */       if ((tile instanceof TileEntityEnchantedSpike)) {
/* 340:383 */         stack.setTagCompound(((TileEntityEnchantedSpike)tile).getEnchantmentTagList());
/* 341:    */       }
/* 342:386 */       fakePlayer.setCurrentItemOrArmor(0, stack);
/* 343:    */       
/* 344:    */ 
/* 345:389 */       boolean b = target.attackEntityFrom(DamageSource.causePlayerDamage(fakePlayer), 400.0F);
/* 346:390 */       fakePlayer.setCurrentItemOrArmor(0, null);
/* 347:391 */       b = target.attackEntityFrom(DamageSource.causePlayerDamage(fakePlayer), 400.0F) | b;
/* 348:392 */       b = target.attackEntityFrom(DamageSource.cactus, 400.0F) | b;
/* 349:393 */       return b;
/* 350:    */     }
/* 351:    */     finally
/* 352:    */     {
/* 353:395 */       fakePlayer.setCurrentItemOrArmor(0, null);
/* 354:    */     }
/* 355:    */   }
/* 356:    */   
/* 357:    */   public static class DamageSourceSpike
/* 358:    */     extends EntityDamageSource
/* 359:    */   {
/* 360:    */     public DamageSourceSpike(String p_i1567_1_, Entity p_i1567_2_)
/* 361:    */     {
/* 362:402 */       super(p_i1567_2_);
/* 363:    */     }
/* 364:    */   }
/* 365:    */   
/* 366:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 367:    */   {
/* 368:409 */     return this.itemSpike;
/* 369:    */   }
/* 370:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BlockSpike
 * JD-Core Version:    0.7.0.1
 */