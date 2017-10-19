/*   1:    */ package com.rwtema.extrautils.item;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ChunkPos;
/*   4:    */ import com.rwtema.extrautils.ExtraUtils;
/*   5:    */ import com.rwtema.extrautils.ExtraUtilsMod;
/*   6:    */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*   7:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   8:    */ import cpw.mods.fml.relauncher.Side;
/*   9:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import net.minecraft.block.Block;
/*  13:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  14:    */ import net.minecraft.entity.EntityLiving;
/*  15:    */ import net.minecraft.entity.player.EntityPlayer;
/*  16:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*  17:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  18:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  19:    */ import net.minecraft.item.EnumAction;
/*  20:    */ import net.minecraft.item.Item;
/*  21:    */ import net.minecraft.item.ItemStack;
/*  22:    */ import net.minecraft.server.MinecraftServer;
/*  23:    */ import net.minecraft.server.management.ServerConfigurationManager;
/*  24:    */ import net.minecraft.util.AxisAlignedBB;
/*  25:    */ import net.minecraft.util.IIcon;
/*  26:    */ import net.minecraft.world.World;
/*  27:    */ 
/*  28:    */ public class ItemBuildersWand
/*  29:    */   extends Item
/*  30:    */   implements IItemMultiTransparency
/*  31:    */ {
/*  32: 26 */   public int maxBlocks = 9;
/*  33:    */   private IIcon[] icons;
/*  34:    */   
/*  35:    */   public ItemBuildersWand(int par2)
/*  36:    */   {
/*  37: 31 */     this.maxStackSize = 1;
/*  38: 32 */     setUnlocalizedName("extrautils:builderswand");
/*  39: 33 */     this.maxBlocks = par2;
/*  40: 34 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  41:    */   }
/*  42:    */   
/*  43:    */   @SideOnly(Side.CLIENT)
/*  44:    */   public boolean hasEffect(ItemStack par1ItemStack, int pass)
/*  45:    */   {
/*  46: 40 */     return false;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving)
/*  50:    */   {
/*  51: 49 */     return true;
/*  52:    */   }
/*  53:    */   
/*  54:    */   @SideOnly(Side.CLIENT)
/*  55:    */   public boolean isFull3D()
/*  56:    */   {
/*  57: 58 */     return true;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public EnumAction getItemUseAction(ItemStack par1ItemStack)
/*  61:    */   {
/*  62: 67 */     return EnumAction.none;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
/*  66:    */   {
/*  67: 72 */     onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
/*  68: 73 */     if (world.isClient) {
/*  69: 74 */       ExtraUtilsMod.proxy.sendUsePacket(x, y, z, side, stack, hitX, hitY, hitZ);
/*  70:    */     }
/*  71: 75 */     return true;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public boolean onItemUse(ItemStack wand, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
/*  75:    */   {
/*  76: 80 */     if (world.isClient) {
/*  77: 81 */       return true;
/*  78:    */     }
/*  79: 84 */     if (!player.capabilities.allowEdit) {
/*  80: 85 */       return false;
/*  81:    */     }
/*  82: 88 */     List<ChunkPos> blocks = getPotentialBlocks(player, world, x, y, z, side);
/*  83: 90 */     if (blocks.size() == 0) {
/*  84: 91 */       return false;
/*  85:    */     }
/*  86: 94 */     Block blockId = world.getBlock(x, y, z);
/*  87: 96 */     if (world.isAirBlock(x, y, z)) {
/*  88: 97 */       return false;
/*  89:    */     }
/*  90:100 */     int data = -1;
/*  91:    */     
/*  92:102 */     Item item1 = Item.getItemFromBlock(blockId);
/*  93:103 */     if (item1.getHasSubtypes()) {
/*  94:104 */       data = blockId.getDamageValue(world, x, y, z);
/*  95:    */     }
/*  96:107 */     if (blocks.size() > 0)
/*  97:    */     {
/*  98:108 */       int slot = 0;
/*  99:110 */       for (ChunkPos temp : blocks)
/* 100:    */       {
/* 101:111 */         for (slot = 0; slot < player.inventory.getSizeInventory(); slot++) {
/* 102:112 */           if ((player.inventory.getStackInSlot(slot) != null) && 
/* 103:113 */             (player.inventory.getStackInSlot(slot).getItem() == item1)) {
/* 104:114 */             if (((data == -1 ? 1 : 0) | (data == player.inventory.getStackInSlot(slot).getItemDamage() ? 1 : 0)) != 0) {
/* 105:    */               break;
/* 106:    */             }
/* 107:    */           }
/* 108:    */         }
/* 109:119 */         if (slot >= player.inventory.getSizeInventory()) {
/* 110:    */           break;
/* 111:    */         }
/* 112:120 */         ItemStack item = player.inventory.getStackInSlot(slot);
/* 113:121 */         if (player.capabilities.isCreativeMode) {
/* 114:122 */           item = item.copy();
/* 115:    */         }
/* 116:124 */         if ((item.tryPlaceItemIntoWorld(player, world, temp.x, temp.y, temp.z, side, hitX, hitY, hitZ)) && 
/* 117:125 */           (!player.capabilities.isCreativeMode) && (item.stackSize == 0)) {
/* 118:126 */           player.inventory.setInventorySlotContents(slot, null);
/* 119:    */         }
/* 120:    */       }
/* 121:134 */       player.inventory.onInventoryChanged();
/* 122:136 */       if ((player instanceof EntityPlayerMP)) {
/* 123:137 */         ((EntityPlayerMP)player).mcServer.getConfigurationManager().syncPlayerInventory((EntityPlayerMP)player);
/* 124:    */       }
/* 125:    */     }
/* 126:141 */     return true;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public List<ChunkPos> getPotentialBlocks(EntityPlayer player, World world, int x, int y, int z, int face)
/* 130:    */   {
/* 131:145 */     List<ChunkPos> blocks = new ArrayList();
/* 132:147 */     if (world == null) {
/* 133:148 */       return blocks;
/* 134:    */     }
/* 135:151 */     Block blockId = world.getBlock(x, y, z);
/* 136:153 */     if (world.isAirBlock(x, y, z)) {
/* 137:154 */       return blocks;
/* 138:    */     }
/* 139:157 */     if ((player == null) || (XUHelper.isPlayerFake(player))) {
/* 140:158 */       return blocks;
/* 141:    */     }
/* 142:161 */     int data = -1;
/* 143:163 */     if (Item.getItemFromBlock(blockId) == null) {
/* 144:164 */       return blocks;
/* 145:    */     }
/* 146:167 */     if (Item.getItemFromBlock(blockId).getHasSubtypes()) {
/* 147:168 */       data = blockId.getDamageValue(world, x, y, z);
/* 148:    */     }
/* 149:171 */     int numBlocks = 0;
/* 150:172 */     ItemStack genericStack = null;
/* 151:174 */     for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
/* 152:175 */       if (player.inventory.getStackInSlot(i) != null)
/* 153:    */       {
/* 154:176 */         if ((player.inventory.getStackInSlot(i).getItem() == Item.getItemFromBlock(blockId)) && (
/* 155:177 */           (data == -1) || (data == player.inventory.getStackInSlot(i).getItemDamage())))
/* 156:    */         {
/* 157:178 */           genericStack = player.inventory.getStackInSlot(i);
/* 158:180 */           if (player.capabilities.isCreativeMode)
/* 159:    */           {
/* 160:181 */             numBlocks = this.maxBlocks;
/* 161:182 */             break;
/* 162:    */           }
/* 163:185 */           numBlocks += player.inventory.getStackInSlot(i).stackSize;
/* 164:    */         }
/* 165:189 */         if (numBlocks >= this.maxBlocks)
/* 166:    */         {
/* 167:190 */           numBlocks = this.maxBlocks;
/* 168:191 */           break;
/* 169:    */         }
/* 170:    */       }
/* 171:    */     }
/* 172:196 */     int dx = net.minecraft.util.Facing.offsetsXForSide[face];int dy = net.minecraft.util.Facing.offsetsYForSide[face];int dz = net.minecraft.util.Facing.offsetsZForSide[face];
/* 173:197 */     int mx = dx == 0 ? 1 : 0;int my = dy == 0 ? 1 : 0;int mz = dz == 0 ? 1 : 0;
/* 174:198 */     boolean hollow = false;
/* 175:200 */     if (ExtraUtilsMod.proxy.isAltSneaking())
/* 176:    */     {
/* 177:201 */       if (player.isSneaking())
/* 178:    */       {
/* 179:202 */         hollow = true;
/* 180:    */       }
/* 181:204 */       else if (face > 1)
/* 182:    */       {
/* 183:205 */         mx = 0;
/* 184:206 */         mz = 0;
/* 185:207 */         my = 1;
/* 186:    */       }
/* 187:    */       else
/* 188:    */       {
/* 189:209 */         return blocks;
/* 190:    */       }
/* 191:    */     }
/* 192:211 */     else if (player.isSneaking()) {
/* 193:212 */       if (face > 1) {
/* 194:213 */         my = 0;
/* 195:    */       } else {
/* 196:215 */         return blocks;
/* 197:    */       }
/* 198:    */     }
/* 199:218 */     AxisAlignedBB var11 = blockId.getCollisionBoundingBoxFromPool(world, x, y, z);
/* 200:220 */     if (numBlocks > 0)
/* 201:    */     {
/* 202:220 */       if ((blockId.canPlaceBlockOnSide(world, x + dx, y + dy, z + dz, face) & y + dy < 255)) {}
/* 203:    */     }
/* 204:    */     else {
/* 205:221 */       return blocks;
/* 206:    */     }
/* 207:224 */     if (!checkAAB(world, var11, dx, dy, dz)) {
/* 208:225 */       return blocks;
/* 209:    */     }
/* 210:228 */     blocks.add(new ChunkPos(x + dx, y + dy, z + dz));
/* 211:230 */     for (int i = 0; ((i < blocks.size() ? 1 : 0) & (blocks.size() < numBlocks ? 1 : 0)) != 0; i++) {
/* 212:231 */       for (int ax = -mx; ax <= mx; ax++) {
/* 213:232 */         for (int ay = -my; ay <= my; ay++) {
/* 214:233 */           for (int az = -mz; az <= mz; az++)
/* 215:    */           {
/* 216:234 */             ChunkPos temp = new ChunkPos(((ChunkPos)blocks.get(i)).x + ax, ((ChunkPos)blocks.get(i)).y + ay, ((ChunkPos)blocks.get(i)).z + az);
/* 217:236 */             if ((blocks.size() < numBlocks) && 
/* 218:237 */               (player.canPlayerEdit(temp.x, temp.y, temp.z, face, genericStack)) && 
/* 219:238 */               (!blocks.contains(temp)) && 
/* 220:239 */               (world.getBlock(temp.x - dx, temp.y - dy, temp.z - dz) == blockId) && (blockId.canPlaceBlockOnSide(world, temp.x, temp.y, temp.z, face)) && 
/* 221:240 */               ((data == -1) || (data == blockId.getDamageValue(world, temp.x - dx, temp.y - dy, temp.z - dz))) && 
/* 222:241 */               (checkAAB(world, var11, temp.x - x, temp.y - y, temp.z - z)) && 
/* 223:242 */               (!blocks.contains(temp))) {
/* 224:243 */               blocks.add(temp);
/* 225:    */             }
/* 226:    */           }
/* 227:    */         }
/* 228:    */       }
/* 229:    */     }
/* 230:248 */     return blocks;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public boolean checkAAB(World world, AxisAlignedBB bounds, int dx, int dy, int dz)
/* 234:    */   {
/* 235:252 */     if (bounds == null) {
/* 236:253 */       return true;
/* 237:    */     }
/* 238:254 */     if (world.checkNoEntityCollision(bounds.getOffsetBoundingBox(dx, dy, dz))) {
/* 239:255 */       return true;
/* 240:    */     }
/* 241:258 */     return false;
/* 242:    */   }
/* 243:    */   
/* 244:    */   @SideOnly(Side.CLIENT)
/* 245:    */   public void registerIcons(IIconRegister par1IIconRegister)
/* 246:    */   {
/* 247:264 */     this.icons = new IIcon[2];
/* 248:265 */     this.itemIcon = (this.icons[0] =  = par1IIconRegister.registerIcon(getUnlocalizedName().substring(5)));
/* 249:266 */     this.icons[1] = par1IIconRegister.registerIcon(getUnlocalizedName().substring(5) + "1");
/* 250:    */   }
/* 251:    */   
/* 252:    */   public int numIcons(ItemStack item)
/* 253:    */   {
/* 254:271 */     return 2;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public IIcon getIconForTransparentRender(ItemStack item, int pass)
/* 258:    */   {
/* 259:276 */     return this.icons[pass];
/* 260:    */   }
/* 261:    */   
/* 262:    */   public float getIconTransparency(ItemStack item, int pass)
/* 263:    */   {
/* 264:281 */     if (pass == 1) {
/* 265:282 */       return 0.5F;
/* 266:    */     }
/* 267:284 */     return 1.0F;
/* 268:    */   }
/* 269:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemBuildersWand
 * JD-Core Version:    0.7.0.1
 */