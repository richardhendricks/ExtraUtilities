/*   1:    */ package com.rwtema.extrautils.item;
/*   2:    */ 
/*   3:    */ import cofh.api.block.IDismantleable;
/*   4:    */ import com.rwtema.extrautils.EventHandlerEntityItemStealer;
/*   5:    */ import com.rwtema.extrautils.ExtraUtils;
/*   6:    */ import com.rwtema.extrautils.helper.XURandom;
/*   7:    */ import com.rwtema.extrautils.network.NetworkHandler;
/*   8:    */ import cpw.mods.fml.relauncher.Side;
/*   9:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Random;
/*  13:    */ import net.minecraft.block.Block;
/*  14:    */ import net.minecraft.client.Minecraft;
/*  15:    */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*  16:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  17:    */ import net.minecraft.enchantment.Enchantment;
/*  18:    */ import net.minecraft.enchantment.EnchantmentHelper;
/*  19:    */ import net.minecraft.enchantment.EnumEnchantmentType;
/*  20:    */ import net.minecraft.entity.Entity;
/*  21:    */ import net.minecraft.entity.EntityLivingBase;
/*  22:    */ import net.minecraft.entity.item.EntityItem;
/*  23:    */ import net.minecraft.entity.player.EntityPlayer;
/*  24:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*  25:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  26:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  27:    */ import net.minecraft.init.Items;
/*  28:    */ import net.minecraft.item.Item;
/*  29:    */ import net.minecraft.item.ItemShears;
/*  30:    */ import net.minecraft.item.ItemStack;
/*  31:    */ import net.minecraft.nbt.NBTTagCompound;
/*  32:    */ import net.minecraft.server.MinecraftServer;
/*  33:    */ import net.minecraft.server.management.ItemInWorldManager;
/*  34:    */ import net.minecraft.server.management.ServerConfigurationManager;
/*  35:    */ import net.minecraft.util.AxisAlignedBB;
/*  36:    */ import net.minecraft.util.IIcon;
/*  37:    */ import net.minecraft.world.World;
/*  38:    */ import net.minecraft.world.WorldProvider;
/*  39:    */ import net.minecraftforge.common.ForgeHooks;
/*  40:    */ 
/*  41:    */ public class ItemPrecisionShears
/*  42:    */   extends ItemShears
/*  43:    */   implements IItemMultiTransparency
/*  44:    */ {
/*  45: 36 */   public static final Item[] toolsToMimic = { Items.stone_pickaxe, Items.stone_axe, Items.stone_shovel, Items.stone_sword, Items.stone_hoe, Items.shears };
/*  46: 37 */   public static final ItemStack[] toolStacks = new ItemStack[toolsToMimic.length];
/*  47:    */   
/*  48:    */   static
/*  49:    */   {
/*  50: 40 */     for (int i = 0; i < toolsToMimic.length; i++) {
/*  51: 41 */       toolStacks[i] = new ItemStack(toolsToMimic[i]);
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static int getCooldown(ItemStack stack)
/*  56:    */   {
/*  57: 46 */     int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack);
/*  58: 47 */     if (i < 0) {
/*  59: 47 */       i = 0;
/*  60:    */     }
/*  61: 48 */     if (i >= COOLDOWN.length) {
/*  62: 48 */       i = COOLDOWN.length - 1;
/*  63:    */     }
/*  64: 49 */     return COOLDOWN[i];
/*  65:    */   }
/*  66:    */   
/*  67: 52 */   public static final int[] COOLDOWN = { 20, 16, 12, 8, 4, 0 };
/*  68: 53 */   public Random rand = XURandom.getInstance();
/*  69:    */   private IIcon[] icons;
/*  70:    */   
/*  71:    */   public ItemPrecisionShears()
/*  72:    */   {
/*  73: 58 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  74: 59 */     setUnlocalizedName("extrautils:shears");
/*  75: 60 */     setMaxStackSize(1);
/*  76: 61 */     setMaxDamage(1024);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public int cofh_canEnchantApply(ItemStack stack, Enchantment ench)
/*  80:    */   {
/*  81: 66 */     return ench.type == EnumEnchantmentType.digger ? 1 : -1;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public int getItemEnchantability()
/*  85:    */   {
/*  86: 71 */     return Items.iron_pickaxe.getItemEnchantability();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean isItemTool(ItemStack p_77616_1_)
/*  90:    */   {
/*  91: 76 */     return p_77616_1_.stackSize == 1;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean onBlockStartBreak(ItemStack itemstack, int x, int y, int z, EntityPlayer player)
/*  95:    */   {
/*  96: 82 */     World worldObj = player.worldObj;
/*  97: 84 */     if (worldObj.isClient) {
/*  98: 84 */       return false;
/*  99:    */     }
/* 100: 86 */     Block block = worldObj.getBlock(x, y, z);
/* 101: 87 */     int meta = worldObj.getBlockMetadata(x, y, z);
/* 102: 88 */     worldObj.playAuxSFXAtEntity(player, 2001, x, y, z, Block.getIdFromBlock(block) + (worldObj.getBlockMetadata(x, y, z) << 12));
/* 103:    */     
/* 104:    */ 
/* 105: 91 */     boolean flag1 = block.canHarvestBlock(player, meta);
/* 106: 93 */     if (itemstack != null)
/* 107:    */     {
/* 108: 94 */       itemstack.func_150999_a(worldObj, block, x, y, z, player);
/* 109: 96 */       if (itemstack.stackSize == 0) {
/* 110: 97 */         player.destroyCurrentEquippedItem();
/* 111:    */       }
/* 112:    */     }
/* 113:101 */     List<EntityItem> extraDrops = null;
/* 114:102 */     List<EntityItem> baseCapturedDrops = null;
/* 115:    */     
/* 116:104 */     EventHandlerEntityItemStealer.startCapture();
/* 117:105 */     if (((block instanceof IDismantleable)) && (((IDismantleable)block).canDismantle(player, worldObj, x, y, z)))
/* 118:    */     {
/* 119:106 */       ((IDismantleable)block).dismantleBlock(player, worldObj, x, y, z, false);
/* 120:    */     }
/* 121:    */     else
/* 122:    */     {
/* 123:108 */       block.onBlockHarvested(worldObj, x, y, z, meta, player);
/* 124:110 */       if (block.removedByPlayer(worldObj, player, x, y, z, true))
/* 125:    */       {
/* 126:111 */         block.onBlockDestroyedByPlayer(worldObj, x, y, z, meta);
/* 127:113 */         if ((flag1) || (player.capabilities.isCreativeMode))
/* 128:    */         {
/* 129:114 */           extraDrops = EventHandlerEntityItemStealer.getCapturedEntities();
/* 130:115 */           EventHandlerEntityItemStealer.startCapture();
/* 131:116 */           block.harvestBlock(worldObj, player, x, y, z, meta);
/* 132:117 */           baseCapturedDrops = EventHandlerEntityItemStealer.getCapturedEntities();
/* 133:    */         }
/* 134:    */       }
/* 135:    */     }
/* 136:122 */     EventHandlerEntityItemStealer.stopCapture();
/* 137:    */     
/* 138:124 */     boolean added = false;
/* 139:127 */     if (baseCapturedDrops == null) {
/* 140:128 */       baseCapturedDrops = EventHandlerEntityItemStealer.getCapturedEntities();
/* 141:    */     }
/* 142:131 */     if (extraDrops != null) {
/* 143:132 */       baseCapturedDrops.addAll(extraDrops);
/* 144:    */     }
/* 145:135 */     for (EntityItem j : baseCapturedDrops)
/* 146:    */     {
/* 147:136 */       if (player.inventory.addItemStackToInventory(j.getEntityItem()))
/* 148:    */       {
/* 149:137 */         added = true;
/* 150:138 */         NetworkHandler.sendParticle(worldObj, "reddust", j.posX, j.posY, j.posZ, 0.5D + this.rand.nextDouble() * 0.15D, 0.35D, 0.65D + this.rand.nextDouble() * 0.3D, false);
/* 151:    */       }
/* 152:141 */       if ((j.getEntityItem() != null) && (j.getEntityItem().stackSize > 0)) {
/* 153:142 */         worldObj.spawnEntityInWorld(new EntityItem(j.worldObj, j.posX, j.posY, j.posZ, j.getEntityItem()));
/* 154:    */       }
/* 155:    */     }
/* 156:146 */     if (added)
/* 157:    */     {
/* 158:147 */       for (int i = 0; i < 10; i++) {
/* 159:148 */         NetworkHandler.sendParticle(worldObj, "reddust", x + this.rand.nextDouble(), y + this.rand.nextDouble(), z + this.rand.nextDouble(), 0.5D + this.rand.nextDouble() * 0.15D, 0.35D, 0.65D + this.rand.nextDouble() * 0.3D, false);
/* 160:    */       }
/* 161:152 */       ((EntityPlayerMP)player).mcServer.getConfigurationManager().syncPlayerInventory((EntityPlayerMP)player);
/* 162:    */     }
/* 163:154 */     return true;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
/* 167:    */   {
/* 168:160 */     if (!player.isSneaking()) {
/* 169:161 */       return false;
/* 170:    */     }
/* 171:164 */     if (!check(par1ItemStack, world)) {
/* 172:165 */       return true;
/* 173:    */     }
/* 174:167 */     if (world.isAirBlock(x, y, z)) {
/* 175:168 */       return false;
/* 176:    */     }
/* 177:171 */     Block block = world.getBlock(x, y, z);
/* 178:    */     
/* 179:173 */     int meta = world.getBlockMetadata(x, y, z);
/* 180:175 */     if ((block.getBlockHardness(world, x, y, z) < 0.0F) && (
/* 181:176 */       (!(block instanceof IDismantleable)) || (!((IDismantleable)block).canDismantle(player, world, x, y, z)))) {
/* 182:177 */       return false;
/* 183:    */     }
/* 184:181 */     if (block.canHarvestBlock(player, meta))
/* 185:    */     {
/* 186:182 */       player.swingItem();
/* 187:184 */       if ((world.isClient) || (!(player instanceof EntityPlayerMP))) {
/* 188:185 */         return true;
/* 189:    */       }
/* 190:188 */       if (!check(par1ItemStack, world)) {
/* 191:189 */         return true;
/* 192:    */       }
/* 193:192 */       if ((!world.isAirBlock(x, y, z)) && 
/* 194:193 */         (block.getBlockHardness(world, x, y, z) >= 0.0F)) {
/* 195:194 */         ((EntityPlayerMP)player).theItemInWorldManager.tryHarvestBlock(x, y, z);
/* 196:    */       }
/* 197:198 */       return true;
/* 198:    */     }
/* 199:201 */     return false;
/* 200:    */   }
/* 201:    */   
/* 202:    */   private void collectItems(World world, EntityPlayer player, double x, double y, double z, List before, List after)
/* 203:    */   {
/* 204:205 */     Iterator iter = after.iterator();
/* 205:206 */     boolean added = false;
/* 206:208 */     while (iter.hasNext())
/* 207:    */     {
/* 208:209 */       EntityItem j = (EntityItem)iter.next();
/* 209:211 */       if ((j.getClass() == EntityItem.class) && (!before.contains(j)) && 
/* 210:212 */         (player.inventory.addItemStackToInventory(j.getEntityItem())))
/* 211:    */       {
/* 212:213 */         NetworkHandler.sendParticle(world, "reddust", j.posX, j.posY, j.posZ, 0.5D + this.rand.nextDouble() * 0.15D, 0.35D, 0.65D + this.rand.nextDouble() * 0.3D, false);
/* 213:214 */         added = true;
/* 214:216 */         if ((j.getEntityItem() == null) || (j.getEntityItem().stackSize == 0)) {
/* 215:217 */           j.setDead();
/* 216:    */         }
/* 217:    */       }
/* 218:    */     }
/* 219:222 */     if (added)
/* 220:    */     {
/* 221:223 */       for (int i = 0; i < 10; i++) {
/* 222:224 */         NetworkHandler.sendParticle(world, "reddust", x + this.rand.nextDouble(), y + this.rand.nextDouble(), z + this.rand.nextDouble(), 0.5D + this.rand.nextDouble() * 0.15D, 0.35D, 0.65D + this.rand.nextDouble() * 0.3D, false);
/* 223:    */       }
/* 224:228 */       ((EntityPlayerMP)player).mcServer.getConfigurationManager().syncPlayerInventory((EntityPlayerMP)player);
/* 225:    */     }
/* 226:    */   }
/* 227:    */   
/* 228:    */   public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer player, EntityLivingBase entity)
/* 229:    */   {
/* 230:234 */     AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(entity.posX, entity.posY, entity.posZ, entity.posX, entity.posY, entity.posZ).offset(0.5D, 0.5D, 0.5D).expand(3.0D, 3.0D, 3.0D);
/* 231:235 */     List items = player.worldObj.getEntitiesWithinAABB(EntityItem.class, aabb);
/* 232:236 */     boolean sheared = super.itemInteractionForEntity(itemstack, player, entity);
/* 233:238 */     if (sheared) {
/* 234:239 */       collectItems(player.worldObj, player, entity.posX - 0.5D, entity.posY - 0.5D, entity.posZ - 0.5D, items, player.worldObj.getEntitiesWithinAABB(EntityItem.class, aabb));
/* 235:    */     }
/* 236:242 */     return sheared;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public boolean canHarvestBlock(Block par1Block, ItemStack item)
/* 240:    */   {
/* 241:247 */     for (Item tool : toolsToMimic) {
/* 242:248 */       if (tool.canHarvestBlock(par1Block, new ItemStack(tool))) {
/* 243:249 */         return true;
/* 244:    */       }
/* 245:    */     }
/* 246:252 */     return false;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public float func_150893_a(ItemStack stack, Block block)
/* 250:    */   {
/* 251:257 */     for (ItemStack tool : toolStacks) {
/* 252:258 */       if (ForgeHooks.isToolEffective(tool, block, 0)) {
/* 253:259 */         return 4.0F;
/* 254:    */       }
/* 255:    */     }
/* 256:262 */     return super.func_150893_a(stack, block);
/* 257:    */   }
/* 258:    */   
/* 259:    */   @SideOnly(Side.CLIENT)
/* 260:    */   public void registerIcons(IIconRegister par1IIconRegister)
/* 261:    */   {
/* 262:268 */     this.icons = new IIcon[2];
/* 263:269 */     this.itemIcon = (this.icons[0] =  = par1IIconRegister.registerIcon(getUnlocalizedName().substring(5)));
/* 264:270 */     this.icons[1] = par1IIconRegister.registerIcon(getUnlocalizedName().substring(5) + "1");
/* 265:    */   }
/* 266:    */   
/* 267:    */   @SideOnly(Side.CLIENT)
/* 268:    */   public int numIcons(ItemStack item)
/* 269:    */   {
/* 270:277 */     if ((Minecraft.getMinecraft().thePlayer != null) && (Minecraft.getMinecraft().thePlayer.worldObj != null) && 
/* 271:278 */       (!check(item, Minecraft.getMinecraft().thePlayer.worldObj))) {
/* 272:279 */       return 1;
/* 273:    */     }
/* 274:282 */     return 2;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public IIcon getIconForTransparentRender(ItemStack item, int pass)
/* 278:    */   {
/* 279:287 */     return this.icons[pass];
/* 280:    */   }
/* 281:    */   
/* 282:    */   public float getIconTransparency(ItemStack item, int pass)
/* 283:    */   {
/* 284:292 */     if (pass == 1) {
/* 285:293 */       return 0.5F;
/* 286:    */     }
/* 287:295 */     return 1.0F;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public boolean onBlockDestroyed(ItemStack itemstack, World par2World, Block par3, int par4, int par5, int par6, EntityLivingBase par7EntityLivingBase)
/* 291:    */   {
/* 292:301 */     itemstack.damageItem(1, par7EntityLivingBase);
/* 293:302 */     NBTTagCompound tag = itemstack.getTagCompound();
/* 294:304 */     if (tag == null) {
/* 295:305 */       tag = new NBTTagCompound();
/* 296:    */     }
/* 297:308 */     tag.setInteger("dim", par2World.provider.dimensionId);
/* 298:309 */     tag.setLong("time", par2World.getTotalWorldTime());
/* 299:310 */     itemstack.setTagCompound(tag);
/* 300:311 */     return true;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public boolean check(ItemStack itemstack, World world)
/* 304:    */   {
/* 305:315 */     if (!itemstack.hasTagCompound()) {
/* 306:316 */       return true;
/* 307:    */     }
/* 308:319 */     if ((!itemstack.getTagCompound().hasKey("dim")) && (!itemstack.getTagCompound().hasKey("time"))) {
/* 309:320 */       return true;
/* 310:    */     }
/* 311:323 */     long totalWorldTime = world.getTotalWorldTime();
/* 312:324 */     long time = itemstack.getTagCompound().getLong("time") + getCooldown(itemstack);
/* 313:325 */     if ((itemstack.getTagCompound().getInteger("dim") != world.provider.dimensionId) || (time < totalWorldTime))
/* 314:    */     {
/* 315:327 */       if (!world.isClient)
/* 316:    */       {
/* 317:328 */         itemstack.getTagCompound().removeTag("dim");
/* 318:329 */         itemstack.getTagCompound().removeTag("time");
/* 319:331 */         if (itemstack.getTagCompound().hasNoTags()) {
/* 320:332 */           itemstack.setTagCompound(null);
/* 321:    */         }
/* 322:    */       }
/* 323:336 */       return true;
/* 324:    */     }
/* 325:339 */     return false;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public void onUpdate(ItemStack itemstack, World par2World, Entity par3Entity, int par4, boolean par5)
/* 329:    */   {
/* 330:344 */     check(itemstack, par2World);
/* 331:    */   }
/* 332:    */   
/* 333:    */   public Entity createEntity(World world, Entity location, ItemStack itemstack)
/* 334:    */   {
/* 335:349 */     if (itemstack.hasTagCompound())
/* 336:    */     {
/* 337:350 */       itemstack.getTagCompound().removeTag("dim");
/* 338:351 */       itemstack.getTagCompound().removeTag("time");
/* 339:353 */       if (itemstack.getTagCompound().hasNoTags()) {
/* 340:354 */         itemstack.setTagCompound(null);
/* 341:    */       }
/* 342:    */     }
/* 343:358 */     return null;
/* 344:    */   }
/* 345:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemPrecisionShears
 * JD-Core Version:    0.7.0.1
 */