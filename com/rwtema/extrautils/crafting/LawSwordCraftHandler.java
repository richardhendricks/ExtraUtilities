/*   1:    */ package com.rwtema.extrautils.crafting;
/*   2:    */ 
/*   3:    */ import com.mojang.authlib.GameProfile;
/*   4:    */ import com.rwtema.extrautils.ExtraUtils;
/*   5:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   6:    */ import com.rwtema.extrautils.helper.XURandom;
/*   7:    */ import com.rwtema.extrautils.item.ItemLawSword;
/*   8:    */ import cpw.mods.fml.common.FMLCommonHandler;
/*   9:    */ import cpw.mods.fml.common.eventhandler.EventBus;
/*  10:    */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*  11:    */ import cpw.mods.fml.common.gameevent.TickEvent.Phase;
/*  12:    */ import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
/*  13:    */ import cpw.mods.fml.relauncher.Side;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Random;
/*  16:    */ import java.util.UUID;
/*  17:    */ import net.minecraft.block.BlockFlower;
/*  18:    */ import net.minecraft.enchantment.Enchantment;
/*  19:    */ import net.minecraft.entity.player.EntityPlayer;
/*  20:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  21:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  22:    */ import net.minecraft.init.Items;
/*  23:    */ import net.minecraft.item.Item;
/*  24:    */ import net.minecraft.item.ItemArmor;
/*  25:    */ import net.minecraft.item.ItemStack;
/*  26:    */ import net.minecraft.server.MinecraftServer;
/*  27:    */ import net.minecraft.server.management.ServerConfigurationManager;
/*  28:    */ import net.minecraft.util.AxisAlignedBB;
/*  29:    */ import net.minecraft.util.ChatComponentText;
/*  30:    */ import net.minecraft.world.World;
/*  31:    */ import net.minecraft.world.WorldServer;
/*  32:    */ import net.minecraftforge.common.DimensionManager;
/*  33:    */ 
/*  34:    */ public class LawSwordCraftHandler
/*  35:    */ {
/*  36:    */   private final Random rand;
/*  37:    */   private final String[] strings;
/*  38:    */   private final String flowerType = "tulipPink";
/*  39:    */   private final int pinkColor;
/*  40:    */   private final double pinkR;
/*  41:    */   private final double pinkG;
/*  42:    */   private final double pinkB;
/*  43:    */   
/*  44:    */   public static void init()
/*  45:    */   {
/*  46: 31 */     FMLCommonHandler.instance().bus().register(new LawSwordCraftHandler());
/*  47:    */   }
/*  48:    */   
/*  49:    */   public LawSwordCraftHandler()
/*  50:    */   {
/*  51: 34 */     this.rand = XURandom.getInstance();
/*  52:    */     
/*  53: 36 */     this.strings = new String[] { "I feel pretty", "Oh, so pretty", "I feel pretty", "and witty and bright!", "All you need is Wuv!" };
/*  54:    */     
/*  55:    */ 
/*  56:    */ 
/*  57:    */ 
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61: 44 */     this.flowerType = "tulipPink";
/*  62:    */     
/*  63:    */ 
/*  64:    */ 
/*  65:    */ 
/*  66:    */ 
/*  67:    */ 
/*  68: 51 */     this.pinkColor = 15892389;
/*  69:    */     
/*  70: 53 */     this.pinkR = ((this.pinkColor >> 16 & 0xFF) / 255.0D);
/*  71: 54 */     this.pinkG = ((this.pinkColor >> 8 & 0xFF) / 255.0D);
/*  72: 55 */     this.pinkB = ((this.pinkColor & 0xFF) / 255.0D);
/*  73:    */   }
/*  74:    */   
/*  75:    */   @SubscribeEvent
/*  76:    */   public void event(TickEvent.PlayerTickEvent event)
/*  77:    */   {
/*  78: 61 */     if (event.phase == TickEvent.Phase.END) {
/*  79: 62 */       return;
/*  80:    */     }
/*  81: 64 */     if (event.side == Side.CLIENT)
/*  82:    */     {
/*  83: 65 */       for (int i = 1; i < 5; i++)
/*  84:    */       {
/*  85: 66 */         ItemStack a = event.player.getEquipmentInSlot(i);
/*  86: 67 */         if ((a == null) || (!a.hasTagCompound())) {
/*  87: 68 */           return;
/*  88:    */         }
/*  89: 70 */         if (!(a.getItem() instanceof ItemArmor)) {
/*  90: 70 */           return;
/*  91:    */         }
/*  92: 72 */         ItemArmor b = (ItemArmor)a.getItem();
/*  93: 74 */         if (b.getColor(a) != this.pinkColor) {
/*  94: 75 */           return;
/*  95:    */         }
/*  96: 77 */         if (!this.strings[(4 - i)].equals(a.getDisplayName())) {
/*  97: 78 */           return;
/*  98:    */         }
/*  99:    */       }
/* 100: 81 */       AxisAlignedBB bb = event.player.boundingBox;
/* 101: 83 */       for (int i = 0; i < 5; i++) {
/* 102: 84 */         event.player.worldObj.spawnParticle("reddust", bb.minX + XUHelper.rand.nextFloat() * (bb.maxX - bb.minX), bb.minY + XUHelper.rand.nextFloat() * (bb.maxY - bb.minY) * (1 + i) / 5.0D, bb.minZ + XUHelper.rand.nextFloat() * (bb.maxZ - bb.minZ), this.pinkR, this.pinkG, this.pinkB);
/* 103:    */       }
/* 104: 89 */       return;
/* 105:    */     }
/* 106: 92 */     if (this.rand.nextInt(XUHelper.deObf ? 20 : 800) != 0) {
/* 107: 93 */       return;
/* 108:    */     }
/* 109: 95 */     if (isPlayerCreative(event)) {
/* 110: 96 */       return;
/* 111:    */     }
/* 112: 98 */     if (!isPlayerSprinting(event)) {
/* 113: 99 */       return;
/* 114:    */     }
/* 115:101 */     if (isPlayerAlone()) {
/* 116:102 */       return;
/* 117:    */     }
/* 118:104 */     for (int i = 0; i < 5; i++)
/* 119:    */     {
/* 120:105 */       ItemStack a = event.player.getEquipmentInSlot(i);
/* 121:106 */       if (a == null) {
/* 122:107 */         return;
/* 123:    */       }
/* 124:109 */       if (i == 0)
/* 125:    */       {
/* 126:110 */         if (isPinkFlower(a)) {}
/* 127:    */       }
/* 128:    */       else
/* 129:    */       {
/* 130:112 */         if (!(a.getItem() instanceof ItemArmor)) {
/* 131:112 */           return;
/* 132:    */         }
/* 133:114 */         ItemArmor b = (ItemArmor)a.getItem();
/* 134:116 */         if (b.getColor(a) != this.pinkColor) {
/* 135:117 */           return;
/* 136:    */         }
/* 137:    */       }
/* 138:120 */       if (!this.strings[(4 - i)].equals(a.getDisplayName())) {
/* 139:121 */         return;
/* 140:    */       }
/* 141:    */     }
/* 142:124 */     handlePlayer(event.player);
/* 143:    */   }
/* 144:    */   
/* 145:    */   public boolean isPinkFlower(ItemStack a)
/* 146:    */   {
/* 147:128 */     if (a.getItem() != Item.getItemFromBlock(BlockFlower.func_149857_e("tulipPink"))) {
/* 148:129 */       return false;
/* 149:    */     }
/* 150:130 */     if (a.getItemDamage() != BlockFlower.func_149856_f("tulipPink")) {
/* 151:131 */       return false;
/* 152:    */     }
/* 153:132 */     return true;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public boolean isPlayerCreative(TickEvent.PlayerTickEvent event)
/* 157:    */   {
/* 158:136 */     return event.player.capabilities.isCreativeMode;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public boolean isPlayerSprinting(TickEvent.PlayerTickEvent event)
/* 162:    */   {
/* 163:140 */     return event.player.isSprinting();
/* 164:    */   }
/* 165:    */   
/* 166:    */   public boolean isPlayerAlone()
/* 167:    */   {
/* 168:144 */     return (!XUHelper.deObf) && (MinecraftServer.getServer().getConfigurationManager().playerEntityList.size() <= 1);
/* 169:    */   }
/* 170:    */   
/* 171:    */   public boolean handlePlayer(EntityPlayer player)
/* 172:    */   {
/* 173:148 */     for (int i = 0; i < player.inventory.getSizeInventory(); i++)
/* 174:    */     {
/* 175:149 */       ItemStack item = player.inventory.getStackInSlot(i);
/* 176:150 */       if ((item != null) && (item.getItem() == ExtraUtils.ethericSword) && 
/* 177:151 */         (player.inventory.decrStackSize(i, 1) != null))
/* 178:    */       {
/* 179:153 */         boolean troll = shouldTroll(player);
/* 180:154 */         player.inventory.addItemStackToInventory(troll ? newRoll() : createNewStack());
/* 181:    */         
/* 182:    */ 
/* 183:    */ 
/* 184:158 */         List<EntityPlayer> playerList = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
/* 185:159 */         for (EntityPlayer p : playerList)
/* 186:    */         {
/* 187:160 */           p.addChatComponentMessage(new ChatComponentText(player.getCommandSenderName()).appendText(" is the Prettiest Pink Princess"));
/* 188:161 */           if (troll) {
/* 189:162 */             p.addChatComponentMessage(new ChatComponentText("but sadly not a very lucky one"));
/* 190:    */           }
/* 191:    */         }
/* 192:165 */         player.addChatComponentMessage(new ChatComponentText("Thanks to your commitment to exercise and prettiness. You have been granted the greatest gift!"));
/* 193:166 */         return true;
/* 194:    */       }
/* 195:    */     }
/* 196:170 */     return false;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public static ItemStack createNewStack()
/* 200:    */   {
/* 201:174 */     return ItemLawSword.newSword();
/* 202:    */   }
/* 203:    */   
/* 204:    */   public static ItemStack newRoll()
/* 205:    */   {
/* 206:178 */     return XUHelper.addLore(XUHelper.addEnchant(new ItemStack(Items.record_13, 1, 101).setStackDisplayName("Rick Astley - Never gonna give you up!"), Enchantment.unbreaking, 1), new String[] { "Awesome music to exercise to.", "The greatest gift a pretty fairy could ask for.", "Were you expecting something else?" });
/* 207:    */   }
/* 208:    */   
/* 209:    */   public static boolean shouldTroll(EntityPlayer player)
/* 210:    */   {
/* 211:186 */     long seed = DimensionManager.getWorld(0).getSeed();
/* 212:187 */     int hash = (int)(seed ^ seed >>> 32);
/* 213:    */     
/* 214:189 */     hash = hash * 31 + ExtraUtils.versionHash;
/* 215:    */     
/* 216:191 */     GameProfile gameProfile = player.getGameProfile();
/* 217:192 */     if ((gameProfile == null) || (gameProfile.getId() == null) || (gameProfile.getName() == null)) {
/* 218:193 */       return true;
/* 219:    */     }
/* 220:195 */     hash = hash * 31 + gameProfile.getId().hashCode();
/* 221:196 */     hash = hash * 31 + gameProfile.getName().hashCode();
/* 222:197 */     return new Random(hash).nextBoolean();
/* 223:    */   }
/* 224:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.crafting.LawSwordCraftHandler
 * JD-Core Version:    0.7.0.1
 */