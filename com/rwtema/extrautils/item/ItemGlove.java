/*   1:    */ package com.rwtema.extrautils.item;
/*   2:    */ 
/*   3:    */ import com.google.common.base.Throwables;
/*   4:    */ import com.google.common.collect.HashMultimap;
/*   5:    */ import com.google.common.collect.Multimap;
/*   6:    */ import com.rwtema.extrautils.ExtraUtils;
/*   7:    */ import com.rwtema.extrautils.ExtraUtilsMod;
/*   8:    */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*   9:    */ import cpw.mods.fml.common.eventhandler.Event.Result;
/*  10:    */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*  11:    */ import cpw.mods.fml.relauncher.Side;
/*  12:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  13:    */ import java.util.UUID;
/*  14:    */ import net.minecraft.block.Block;
/*  15:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  16:    */ import net.minecraft.entity.Entity;
/*  17:    */ import net.minecraft.entity.EntityLivingBase;
/*  18:    */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*  19:    */ import net.minecraft.entity.ai.attributes.BaseAttribute;
/*  20:    */ import net.minecraft.entity.ai.attributes.RangedAttribute;
/*  21:    */ import net.minecraft.entity.player.EntityPlayer;
/*  22:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*  23:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  24:    */ import net.minecraft.init.Blocks;
/*  25:    */ import net.minecraft.inventory.Container;
/*  26:    */ import net.minecraft.item.EnumAction;
/*  27:    */ import net.minecraft.item.Item.ToolMaterial;
/*  28:    */ import net.minecraft.item.ItemStack;
/*  29:    */ import net.minecraft.item.ItemSword;
/*  30:    */ import net.minecraft.network.NetHandlerPlayServer;
/*  31:    */ import net.minecraft.network.play.server.S23PacketBlockChange;
/*  32:    */ import net.minecraft.server.MinecraftServer;
/*  33:    */ import net.minecraft.server.management.ServerConfigurationManager;
/*  34:    */ import net.minecraft.util.IIcon;
/*  35:    */ import net.minecraft.world.World;
/*  36:    */ import net.minecraftforge.common.util.EnumHelper;
/*  37:    */ import net.minecraftforge.event.AnvilUpdateEvent;
/*  38:    */ import net.minecraftforge.event.ForgeEventFactory;
/*  39:    */ import net.minecraftforge.event.entity.player.PlayerInteractEvent;
/*  40:    */ import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
/*  41:    */ 
/*  42:    */ public class ItemGlove
/*  43:    */   extends ItemSword
/*  44:    */ {
/*  45:    */   public static final int INVALID_METADATA = 32767;
/*  46: 44 */   static Item.ToolMaterial materialWool = EnumHelper.addToolMaterial("wool", 0, 0, 0.0F, 0.0F, 0);
/*  47:    */   IIcon glove1;
/*  48:    */   IIcon glove2;
/*  49:    */   
/*  50:    */   static
/*  51:    */   {
/*  52: 45 */     materialWool.setRepairItem(new ItemStack(Blocks.wool));
/*  53:    */   }
/*  54:    */   
/*  55:    */   public ItemGlove()
/*  56:    */   {
/*  57: 50 */     super(materialWool);
/*  58: 51 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  59: 52 */     setUnlocalizedName("extrautils:glove");
/*  60: 53 */     setTextureName("extrautils:glove");
/*  61: 54 */     setHasSubtypes(false);
/*  62: 55 */     setMaxStackSize(1);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public boolean hasCustomEntity(ItemStack stack)
/*  66:    */   {
/*  67: 60 */     return (stack != null) && (stack.getItemDamage() == 32767);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public Entity createEntity(World world, Entity location, ItemStack itemstack)
/*  71:    */   {
/*  72: 65 */     if (itemstack.getItemDamage() == 32767) {
/*  73: 66 */       location.setDead();
/*  74:    */     }
/*  75: 69 */     return null;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void registerIcons(IIconRegister register)
/*  79:    */   {
/*  80: 77 */     this.glove1 = register.registerIcon("extrautils:glove_1");
/*  81: 78 */     this.glove2 = register.registerIcon("extrautils:glove_2");
/*  82:    */   }
/*  83:    */   
/*  84:    */   @SideOnly(Side.CLIENT)
/*  85:    */   public IIcon getIconFromDamageForRenderPass(int p_77618_1_, int p_77618_2_)
/*  86:    */   {
/*  87: 83 */     return p_77618_2_ == 0 ? this.glove1 : this.glove2;
/*  88:    */   }
/*  89:    */   
/*  90:    */   @SideOnly(Side.CLIENT)
/*  91:    */   public int getColorFromItemStack(ItemStack p_82790_1_, int pass)
/*  92:    */   {
/*  93: 88 */     int dmg = p_82790_1_.getItemDamage();
/*  94: 89 */     return com.rwtema.extrautils.helper.XUHelper.dyeCols[getColIndex(pass, dmg)];
/*  95:    */   }
/*  96:    */   
/*  97: 93 */   static int genericDmg = 0;
/*  98:    */   
/*  99:    */   public static int getColIndex(int pass, int dmg)
/* 100:    */   {
/* 101: 96 */     if (isInvalidDamage(dmg)) {
/* 102: 97 */       dmg = genericDmg;
/* 103:    */     }
/* 104:100 */     if (pass == 0) {
/* 105:101 */       return dmg & 0xF;
/* 106:    */     }
/* 107:103 */     return dmg >> 4 & 0xF;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public static boolean isInvalidDamage(int dmg)
/* 111:    */   {
/* 112:107 */     return (dmg < 0) || (dmg > 255);
/* 113:    */   }
/* 114:    */   
/* 115:    */   @SideOnly(Side.CLIENT)
/* 116:    */   public boolean requiresMultipleRenderPasses()
/* 117:    */   {
/* 118:112 */     return true;
/* 119:    */   }
/* 120:    */   
/* 121:    */   @SideOnly(Side.CLIENT)
/* 122:    */   public int getRenderPasses(int metadata)
/* 123:    */   {
/* 124:117 */     return 2;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String getUnlocalizedName(ItemStack p_77667_1_)
/* 128:    */   {
/* 129:122 */     if (isInvalidDamage(p_77667_1_.getItemDamage())) {
/* 130:123 */       return super.getUnlocalizedName(p_77667_1_) + ".english";
/* 131:    */     }
/* 132:126 */     return super.getUnlocalizedName(p_77667_1_);
/* 133:    */   }
/* 134:    */   
/* 135:    */   @SubscribeEvent
/* 136:    */   public void attack(PlayerInteractEvent event)
/* 137:    */   {
/* 138:137 */     if (event.action != PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
/* 139:138 */       return;
/* 140:    */     }
/* 141:141 */     EntityPlayer player = event.entityPlayer;
/* 142:143 */     if ((event.world == null) || (event.world.isClient)) {
/* 143:144 */       return;
/* 144:    */     }
/* 145:146 */     if ((player == null) || (!(player instanceof EntityPlayerMP))) {
/* 146:147 */       return;
/* 147:    */     }
/* 148:149 */     ItemStack heldItem = player.getHeldItem();
/* 149:150 */     if ((heldItem == null) || (heldItem.getItem() != this)) {
/* 150:151 */       return;
/* 151:    */     }
/* 152:154 */     if (heldItem.getItemDamage() == 32767)
/* 153:    */     {
/* 154:155 */       heldItem.stackSize = 0;
/* 155:156 */       return;
/* 156:    */     }
/* 157:159 */     int i = player.inventory.currentItem;
/* 158:161 */     if ((i >= 9) || (i < 0)) {
/* 159:162 */       return;
/* 160:    */     }
/* 161:164 */     event.setCanceled(true);
/* 162:    */     
/* 163:166 */     ItemStack item = heldItem.copy();
/* 164:    */     
/* 165:168 */     heldItem.setItemDamage(32767);
/* 166:    */     
/* 167:170 */     player.inventory.setInventorySlotContents(i, null);
/* 168:    */     
/* 169:172 */     int x = event.x;int y = event.y;int z = event.z;int side = event.face;
/* 170:    */     try
/* 171:    */     {
/* 172:175 */       PlayerInteractEvent e = ForgeEventFactory.onPlayerInteract(player, PlayerInteractEvent.Action.LEFT_CLICK_BLOCK, x, y, z, side, player.worldObj);
/* 173:176 */       boolean result = (!e.isCanceled()) && (event.useBlock != Event.Result.DENY);
/* 174:177 */       if (result)
/* 175:    */       {
/* 176:178 */         Block block = event.world.getBlock(x, y, z);
/* 177:179 */         block.onBlockClicked(event.world, x, y, z, player);
/* 178:    */       }
/* 179:    */     }
/* 180:    */     catch (Exception err)
/* 181:    */     {
/* 182:182 */       for (int j = 0; j < player.inventory.getSizeInventory(); j++)
/* 183:    */       {
/* 184:183 */         ItemStack stackInSlot = player.inventory.getStackInSlot(i);
/* 185:184 */         if ((stackInSlot != null) && (stackInSlot.getItem() == this)) {
/* 186:187 */           if (stackInSlot.getItemDamage() == 32767) {
/* 187:188 */             player.inventory.setInventorySlotContents(j, null);
/* 188:    */           }
/* 189:    */         }
/* 190:    */       }
/* 191:191 */       if (player.inventory.getStackInSlot(i) == null) {
/* 192:192 */         player.inventory.setInventorySlotContents(i, item);
/* 193:193 */       } else if (!player.inventory.addItemStackToInventory(item)) {
/* 194:194 */         player.dropPlayerItemWithRandomChoice(item, false);
/* 195:    */       }
/* 196:196 */       throw Throwables.propagate(err);
/* 197:    */     }
/* 198:199 */     ((EntityPlayerMP)player).playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, event.world));
/* 199:    */     
/* 200:201 */     ItemStack newItem = player.inventory.getStackInSlot(i);
/* 201:202 */     player.inventory.setInventorySlotContents(i, item);
/* 202:204 */     if ((newItem != null) && 
/* 203:205 */       (!player.inventory.addItemStackToInventory(newItem.copy()))) {
/* 204:206 */       player.dropPlayerItemWithRandomChoice(newItem.copy(), false);
/* 205:    */     }
/* 206:210 */     ((EntityPlayerMP)player).mcServer.getConfigurationManager().syncPlayerInventory((EntityPlayerMP)player);
/* 207:    */   }
/* 208:    */   
/* 209:    */   public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
/* 210:    */   {
/* 211:215 */     if (player == null) {
/* 212:216 */       return false;
/* 213:    */     }
/* 214:218 */     ItemStack heldItem = player.getHeldItem();
/* 215:219 */     if ((heldItem == null) || (heldItem.getItem() != this)) {
/* 216:220 */       return false;
/* 217:    */     }
/* 218:222 */     if (ExtraUtilsMod.proxy.isAltSneaking())
/* 219:    */     {
/* 220:223 */       if (world.isClient)
/* 221:    */       {
/* 222:224 */         ExtraUtilsMod.proxy.sendAltUsePacket(x, y, z, side, stack, hitX, hitY, hitZ);
/* 223:225 */         return true;
/* 224:    */       }
/* 225:227 */       return false;
/* 226:    */     }
/* 227:230 */     if (heldItem.getItemDamage() == 32767)
/* 228:    */     {
/* 229:231 */       heldItem.stackSize = 0;
/* 230:232 */       return true;
/* 231:    */     }
/* 232:235 */     int i = player.inventory.currentItem;
/* 233:237 */     if ((i >= 9) || (i < 0)) {
/* 234:238 */       return false;
/* 235:    */     }
/* 236:240 */     ItemStack item = heldItem.copy();
/* 237:    */     
/* 238:242 */     heldItem.setItemDamage(32767);
/* 239:    */     
/* 240:244 */     player.inventory.setInventorySlotContents(i, null);
/* 241:    */     try
/* 242:    */     {
/* 243:247 */       PlayerInteractEvent event = ForgeEventFactory.onPlayerInteract(player, PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK, x, y, z, side, player.worldObj);
/* 244:248 */       boolean result = (!event.isCanceled()) && (event.useBlock != Event.Result.DENY);
/* 245:249 */       if (result)
/* 246:    */       {
/* 247:250 */         Block block = world.getBlock(x, y, z);
/* 248:251 */         block.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
/* 249:    */       }
/* 250:    */     }
/* 251:    */     catch (Exception err)
/* 252:    */     {
/* 253:254 */       for (int j = 0; j < player.inventory.getSizeInventory(); j++)
/* 254:    */       {
/* 255:255 */         ItemStack stackInSlot = player.inventory.getStackInSlot(i);
/* 256:256 */         if ((stackInSlot != null) && (stackInSlot.getItem() == this)) {
/* 257:259 */           if (stackInSlot.getItemDamage() == 32767) {
/* 258:260 */             player.inventory.setInventorySlotContents(j, null);
/* 259:    */           }
/* 260:    */         }
/* 261:    */       }
/* 262:263 */       if (player.inventory.getStackInSlot(i) == null) {
/* 263:264 */         player.inventory.setInventorySlotContents(i, item);
/* 264:265 */       } else if (!player.inventory.addItemStackToInventory(item)) {
/* 265:266 */         player.dropPlayerItemWithRandomChoice(item, false);
/* 266:    */       }
/* 267:268 */       throw Throwables.propagate(err);
/* 268:    */     }
/* 269:271 */     ItemStack newItem = player.inventory.getStackInSlot(i);
/* 270:272 */     player.inventory.setInventorySlotContents(i, item);
/* 271:274 */     if ((newItem != null) && 
/* 272:275 */       (!player.inventory.addItemStackToInventory(newItem.copy()))) {
/* 273:276 */       player.dropPlayerItemWithRandomChoice(newItem.copy(), false);
/* 274:    */     }
/* 275:280 */     if (player.worldObj.isClient)
/* 276:    */     {
/* 277:281 */       ExtraUtilsMod.proxy.sendUsePacket(x, y, z, side, item, hitX, hitY, hitZ);
/* 278:    */     }
/* 279:    */     else
/* 280:    */     {
/* 281:283 */       if ((player instanceof EntityPlayerMP)) {
/* 282:284 */         ((EntityPlayerMP)player).mcServer.getConfigurationManager().syncPlayerInventory((EntityPlayerMP)player);
/* 283:    */       }
/* 284:286 */       player.openContainer.detectAndSendChanges();
/* 285:    */     }
/* 286:289 */     return true;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player)
/* 290:    */   {
/* 291:294 */     return true;
/* 292:    */   }
/* 293:    */   
/* 294:297 */   public static BaseAttribute woolProtection = new RangedAttribute("extrautils.freezeProtection", 0.0D, -1.797693134862316E+308D, 1.7976931348623157E+308D);
/* 295:299 */   UUID freezeUUID = UUID.fromString("EC21E5A7-1E80-4913-b55C-6ABD8EC8EA90");
/* 296:    */   
/* 297:    */   public Multimap getAttributeModifiers(ItemStack stack)
/* 298:    */   {
/* 299:304 */     Multimap multimap = HashMultimap.create();
/* 300:305 */     multimap.put(woolProtection.getAttributeUnlocalizedName(), new AttributeModifier(this.freezeUUID, "Weapon modifier", 0.001D, 0));
/* 301:306 */     return multimap;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public EnumAction getItemUseAction(ItemStack p_77661_1_)
/* 305:    */   {
/* 306:311 */     return EnumAction.none;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
/* 310:    */   {
/* 311:317 */     return p_77659_1_;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public boolean hitEntity(ItemStack p_77644_1_, EntityLivingBase p_77644_2_, EntityLivingBase p_77644_3_)
/* 315:    */   {
/* 316:323 */     return true;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public boolean onBlockDestroyed(ItemStack p_150894_1_, World p_150894_2_, Block p_150894_3_, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_)
/* 320:    */   {
/* 321:328 */     return true;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public int getMaxItemUseDuration(ItemStack p_77626_1_)
/* 325:    */   {
/* 326:333 */     return 0;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public int getItemEnchantability()
/* 330:    */   {
/* 331:338 */     return 2;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public boolean isItemTool(ItemStack p_77616_1_)
/* 335:    */   {
/* 336:343 */     return true;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public boolean getIsRepairable(ItemStack p_82789_1_, ItemStack p_82789_2_)
/* 340:    */   {
/* 341:348 */     return false;
/* 342:    */   }
/* 343:    */   
/* 344:    */   @SubscribeEvent
/* 345:    */   public void repair(AnvilUpdateEvent event) {}
/* 346:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemGlove
 * JD-Core Version:    0.7.0.1
 */