/*   1:    */ package com.rwtema.extrautils.item;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.HashMultimap;
/*   4:    */ import com.google.common.collect.Multimap;
/*   5:    */ import com.mojang.authlib.GameProfile;
/*   6:    */ import com.rwtema.extrautils.ExtraUtils;
/*   7:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   8:    */ import cpw.mods.fml.relauncher.Side;
/*   9:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.UUID;
/*  12:    */ import net.minecraft.creativetab.CreativeTabs;
/*  13:    */ import net.minecraft.entity.Entity;
/*  14:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*  15:    */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*  16:    */ import net.minecraft.entity.ai.attributes.BaseAttributeMap;
/*  17:    */ import net.minecraft.entity.ai.attributes.IAttribute;
/*  18:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  19:    */ import net.minecraft.entity.player.EntityPlayer;
/*  20:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*  21:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  22:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  23:    */ import net.minecraft.item.Item;
/*  24:    */ import net.minecraft.item.ItemStack;
/*  25:    */ import net.minecraft.nbt.NBTTagCompound;
/*  26:    */ import net.minecraft.server.MinecraftServer;
/*  27:    */ import net.minecraft.server.management.ServerConfigurationManager;
/*  28:    */ import net.minecraft.util.ChatComponentText;
/*  29:    */ import net.minecraft.util.DamageSource;
/*  30:    */ import net.minecraft.world.World;
/*  31:    */ 
/*  32:    */ public class ItemSoul
/*  33:    */   extends Item
/*  34:    */ {
/*  35:    */   public ItemSoul()
/*  36:    */   {
/*  37: 28 */     setUnlocalizedName("extrautils:mini-soul");
/*  38: 29 */     setTextureName("extrautils:mini-soul");
/*  39: 30 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  40: 31 */     setMaxStackSize(1);
/*  41: 32 */     setHasSubtypes(true);
/*  42:    */   }
/*  43:    */   
/*  44:    */   @SideOnly(Side.CLIENT)
/*  45:    */   public void getSubItems(Item item, CreativeTabs p_150895_2_, List list)
/*  46:    */   {
/*  47: 38 */     list.add(new ItemStack(item, 1, 0));
/*  48: 39 */     list.add(new ItemStack(item, 1, 3));
/*  49:    */   }
/*  50:    */   
/*  51:    */   public ItemStack onItemRightClick(ItemStack item, World par3World, EntityPlayer player)
/*  52:    */   {
/*  53: 44 */     if (par3World.isClient) {
/*  54: 45 */       return item;
/*  55:    */     }
/*  56: 47 */     if (!EntityPlayerMP.class.equals(player.getClass())) {
/*  57: 48 */       return item;
/*  58:    */     }
/*  59: 51 */     if ((player.capabilities.isCreativeMode) && (item.getItemDamage() == 3))
/*  60:    */     {
/*  61: 52 */       AttributeModifier mod = player.getAttributeMap().getAttributeInstanceByName(SharedMonsterAttributes.maxHealth.getAttributeUnlocalizedName()).getModifier(uuid);
/*  62: 53 */       if (mod != null) {
/*  63: 54 */         player.getAttributeMap().getAttributeInstanceByName(SharedMonsterAttributes.maxHealth.getAttributeUnlocalizedName()).removeModifier(mod);
/*  64:    */       }
/*  65: 55 */       item.stackSize -= 1;
/*  66: 56 */       return item;
/*  67:    */     }
/*  68: 58 */     if ((item.getItemDamage() == 0) || (item.getItemDamage() == 3))
/*  69:    */     {
/*  70: 60 */       if (item.hasTagCompound())
/*  71:    */       {
/*  72: 61 */         NBTTagCompound tag = item.getTagCompound();
/*  73: 62 */         if ((tag.hasKey("owner_id")) && (player.getGameProfile().getId() != null))
/*  74:    */         {
/*  75: 63 */           if (!player.getGameProfile().getId().toString().equals(tag.getString("owner_id"))) {
/*  76: 64 */             return item;
/*  77:    */           }
/*  78:    */         }
/*  79: 65 */         else if ((tag.hasKey("owner")) && (!player.getCommandSenderName().equals(tag.getString("owner")))) {
/*  80: 66 */           return item;
/*  81:    */         }
/*  82:    */       }
/*  83: 69 */       double l = 0.0D;
/*  84:    */       
/*  85: 71 */       IAttributeInstance a = player.getAttributeMap().getAttributeInstanceByName(SharedMonsterAttributes.maxHealth.getAttributeUnlocalizedName());
/*  86:    */       
/*  87: 73 */       AttributeModifier attr = a.getModifier(uuid);
/*  88: 74 */       if (attr != null) {
/*  89: 75 */         l = attr.getAmount();
/*  90:    */       }
/*  91: 77 */       if (l > -0.1D) {
/*  92: 78 */         return item;
/*  93:    */       }
/*  94: 80 */       l += 0.1D;
/*  95:    */       
/*  96: 82 */       a.removeModifier(attr);
/*  97: 83 */       a.applyModifier(new AttributeModifier(uuid, "Missing Soul", l, 2));
/*  98: 84 */       player.addChatComponentMessage(new ChatComponentText("You feel strangely refreshed (+10% Max Health)"));
/*  99:    */       
/* 100: 86 */       item.stackSize -= 1;
/* 101: 87 */       return item;
/* 102:    */     }
/* 103: 90 */     return item;
/* 104:    */   }
/* 105:    */   
/* 106: 93 */   public static final UUID uuid = UUID.fromString("12345678-9182-3532-aaaa-aaabacadabaa");
/* 107:    */   
/* 108:    */   @SideOnly(Side.CLIENT)
/* 109:    */   public boolean hasEffect(ItemStack par1ItemStack, int pass)
/* 110:    */   {
/* 111: 98 */     return par1ItemStack.getItemDamage() == 3;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer player)
/* 115:    */   {
/* 116:103 */     super.onCreated(par1ItemStack, par2World, player);
/* 117:    */     
/* 118:105 */     par1ItemStack.setItemDamage(1);
/* 119:107 */     if ((!par2World.isClient) && (XUHelper.isPlayerFake(player))) {
/* 120:108 */       return;
/* 121:    */     }
/* 122:110 */     NBTTagCompound tag = par1ItemStack.getTagCompound();
/* 123:111 */     if (tag == null) {
/* 124:112 */       tag = new NBTTagCompound();
/* 125:    */     }
/* 126:114 */     tag.setString("owner", player.getCommandSenderName());
/* 127:115 */     if (player.getGameProfile().getId() != null) {
/* 128:116 */       tag.setString("owner_id", player.getGameProfile().getId().toString());
/* 129:    */     }
/* 130:118 */     par1ItemStack.setTagCompound(tag);
/* 131:120 */     if (!par2World.isClient) {
/* 132:121 */       player.attackEntityFrom(DamageSource.magic, 0.0F);
/* 133:    */     }
/* 134:123 */     double l = 0.0D;
/* 135:    */     
/* 136:125 */     IAttributeInstance a = player.getAttributeMap().getAttributeInstanceByName(SharedMonsterAttributes.maxHealth.getAttributeUnlocalizedName());
/* 137:    */     
/* 138:127 */     AttributeModifier attr = a.getModifier(uuid);
/* 139:128 */     if (attr != null) {
/* 140:129 */       l = attr.getAmount();
/* 141:    */     }
/* 142:131 */     l -= 0.1D;
/* 143:    */     
/* 144:133 */     double c = Math.min(Math.min(a.getBaseValue() * (1.0D + l), a.getAttributeValue()), 20.0D * (1.0D + l));
/* 145:135 */     if (c >= 6.0D)
/* 146:    */     {
/* 147:136 */       par1ItemStack.setItemDamage(0);
/* 148:138 */       if (!par2World.isClient)
/* 149:    */       {
/* 150:139 */         Multimap multimap = HashMultimap.create();
/* 151:140 */         multimap.put(SharedMonsterAttributes.maxHealth.getAttributeUnlocalizedName(), new AttributeModifier(uuid, "Missing Soul", l, 2));
/* 152:    */         
/* 153:142 */         player.getAttributeMap().applyAttributeModifiers(multimap);
/* 154:143 */         player.addChatComponentMessage(new ChatComponentText("You feel diminished (-10% Max Health)"));
/* 155:    */       }
/* 156:    */     }
/* 157:147 */     player.inventory.onInventoryChanged();
/* 158:    */   }
/* 159:    */   
/* 160:    */   public static void updatePlayer(Entity player)
/* 161:    */   {
/* 162:151 */     if ((player instanceof EntityPlayerMP)) {
/* 163:152 */       ((EntityPlayerMP)player).mcServer.getConfigurationManager().syncPlayerInventory((EntityPlayerMP)player);
/* 164:    */     }
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void onUpdate(ItemStack item, World par2World, Entity par3Entity, int par4, boolean par5)
/* 168:    */   {
/* 169:158 */     super.onUpdate(item, par2World, par3Entity, par4, par5);
/* 170:159 */     if ((item.getItemDamage() == 2) && ((par3Entity instanceof EntityPlayerMP)))
/* 171:    */     {
/* 172:160 */       onCreated(item, par2World, (EntityPlayer)par3Entity);
/* 173:161 */       updatePlayer(par3Entity);
/* 174:    */     }
/* 175:    */   }
/* 176:    */   
/* 177:    */   @SideOnly(Side.CLIENT)
/* 178:    */   public void addInformation(ItemStack item, EntityPlayer player, List par3List, boolean par4)
/* 179:    */   {
/* 180:168 */     super.addInformation(item, player, par3List, par4);
/* 181:169 */     NBTTagCompound tag = item.getTagCompound();
/* 182:170 */     if (item.getItemDamage() == 3) {
/* 183:171 */       par3List.add("Soul of a forgotten deity");
/* 184:    */     }
/* 185:172 */     if (item.getItemDamage() == 1) {
/* 186:173 */       par3List.add("Soul is too weak and has been spread too thin");
/* 187:    */     }
/* 188:174 */     if (tag == null) {
/* 189:175 */       return;
/* 190:    */     }
/* 191:176 */     if (tag.hasKey("owner")) {
/* 192:177 */       par3List.add("Owner: " + tag.getString("owner"));
/* 193:    */     }
/* 194:    */   }
/* 195:    */   
/* 196:    */   public boolean hasCustomEntity(ItemStack stack)
/* 197:    */   {
/* 198:182 */     return true;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public Entity createEntity(World world, Entity location, ItemStack itemstack)
/* 202:    */   {
/* 203:187 */     if (itemstack.getItemDamage() == 2) {
/* 204:188 */       location.setDead();
/* 205:    */     }
/* 206:190 */     return null;
/* 207:    */   }
/* 208:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemSoul
 * JD-Core Version:    0.7.0.1
 */