/*   1:    */ package com.rwtema.extrautils.item;
/*   2:    */ 
/*   3:    */ import baubles.api.BaubleType;
/*   4:    */ import baubles.api.IBauble;
/*   5:    */ import com.mojang.authlib.GameProfile;
/*   6:    */ import com.rwtema.extrautils.ExtraUtils;
/*   7:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   8:    */ import com.rwtema.extrautils.network.NetworkHandler;
/*   9:    */ import com.rwtema.extrautils.network.packets.PacketAngelRingNotifier;
/*  10:    */ import cpw.mods.fml.common.FMLCommonHandler;
/*  11:    */ import cpw.mods.fml.common.eventhandler.EventBus;
/*  12:    */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*  13:    */ import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
/*  14:    */ import cpw.mods.fml.relauncher.Side;
/*  15:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  16:    */ import java.util.HashMap;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import net.minecraft.creativetab.CreativeTabs;
/*  20:    */ import net.minecraft.entity.Entity;
/*  21:    */ import net.minecraft.entity.EntityLivingBase;
/*  22:    */ import net.minecraft.entity.player.EntityPlayer;
/*  23:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*  24:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  25:    */ import net.minecraft.item.Item;
/*  26:    */ import net.minecraft.item.ItemStack;
/*  27:    */ import net.minecraft.nbt.NBTTagCompound;
/*  28:    */ import net.minecraft.server.MinecraftServer;
/*  29:    */ import net.minecraft.util.StatCollector;
/*  30:    */ import net.minecraft.world.World;
/*  31:    */ import net.minecraft.world.WorldProvider;
/*  32:    */ import net.minecraftforge.common.MinecraftForge;
/*  33:    */ import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
/*  34:    */ 
/*  35:    */ public class ItemAngelRing
/*  36:    */   extends Item
/*  37:    */   implements IBauble
/*  38:    */ {
/*  39: 34 */   public static boolean foundItem = false;
/*  40: 35 */   public static Map<String, Integer> curFlyingPlayers = new HashMap();
/*  41:    */   
/*  42:    */   public ItemAngelRing()
/*  43:    */   {
/*  44: 39 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  45: 40 */     setUnlocalizedName("extrautils:angelRing");
/*  46: 41 */     setTextureName("extrautils:angelRing");
/*  47: 42 */     setHasSubtypes(true);
/*  48: 43 */     setMaxStackSize(1);
/*  49:    */   }
/*  50:    */   
/*  51:    */   static
/*  52:    */   {
/*  53: 47 */     EventHandlerRing handler = new EventHandlerRing();
/*  54: 48 */     MinecraftForge.EVENT_BUS.register(handler);
/*  55: 49 */     FMLCommonHandler.instance().bus().register(handler);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static void addPlayer(EntityPlayer player, int i, boolean override)
/*  59:    */   {
/*  60: 53 */     String name = player.getGameProfile().getName();
/*  61: 54 */     if ((!curFlyingPlayers.containsKey(name)) || (curFlyingPlayers.get(name) == null) || ((override) && (((Integer)curFlyingPlayers.get(name)).intValue() != i)))
/*  62:    */     {
/*  63: 55 */       curFlyingPlayers.put(name, Integer.valueOf(i));
/*  64: 56 */       NetworkHandler.sendToAllPlayers(new PacketAngelRingNotifier(name, i));
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static void removePlayer(EntityPlayer player)
/*  69:    */   {
/*  70: 61 */     String name = player.getGameProfile().getName();
/*  71: 62 */     if (curFlyingPlayers.containsKey(name))
/*  72:    */     {
/*  73: 63 */       curFlyingPlayers.remove(name);
/*  74: 64 */       NetworkHandler.sendToAllPlayers(new PacketAngelRingNotifier(name, 0));
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   @SideOnly(Side.CLIENT)
/*  79:    */   public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
/*  80:    */   {
/*  81: 71 */     for (int i = 0; i < 5; i++) {
/*  82: 72 */       p_150895_3_.add(new ItemStack(p_150895_1_, 1, i));
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   @SideOnly(Side.CLIENT)
/*  87:    */   public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
/*  88:    */   {
/*  89: 78 */     par3List.add(("" + StatCollector.translateToLocal(new StringBuilder().append(getUnlocalizedNameInefficiently(par1ItemStack)).append(".").append(par1ItemStack.getItemDamage()).append(".name").toString())).trim());
/*  90: 79 */     super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void onUpdate(ItemStack itemstack, World world, Entity entity, int slot, boolean par5)
/*  94:    */   {
/*  95: 84 */     super.onUpdate(itemstack, world, entity, slot, par5);
/*  96: 86 */     if (world.isClient) {
/*  97: 87 */       return;
/*  98:    */     }
/*  99: 89 */     if (!(entity instanceof EntityPlayerMP)) {
/* 100: 90 */       return;
/* 101:    */     }
/* 102: 92 */     NBTTagCompound nbt = XUHelper.getPersistantNBT(entity);
/* 103: 93 */     nbt.setByte("XU|Flying", (byte)20);
/* 104:    */     
/* 105: 95 */     addPlayer((EntityPlayerMP)entity, itemstack.getItemDamage(), par5);
/* 106: 97 */     if ((!((EntityPlayerMP)entity).capabilities.allowFlying) || (!nbt.hasKey("XU|FlyingDim")) || (nbt.getInteger("XU|FlyingDim") != world.provider.dimensionId))
/* 107:    */     {
/* 108: 98 */       addPlayer((EntityPlayerMP)entity, itemstack.getItemDamage(), false);
/* 109: 99 */       ((EntityPlayerMP)entity).capabilities.allowFlying = true;
/* 110:100 */       ((EntityPlayerMP)entity).sendPlayerAbilities();
/* 111:    */     }
/* 112:103 */     nbt.setInteger("XU|FlyingDim", world.provider.dimensionId);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public BaubleType getBaubleType(ItemStack itemstack)
/* 116:    */   {
/* 117:108 */     return BaubleType.RING;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void onWornTick(ItemStack itemstack, EntityLivingBase player)
/* 121:    */   {
/* 122:113 */     onUpdate(itemstack, player.worldObj, player, -1, true);
/* 123:    */   }
/* 124:    */   
/* 125:    */   public boolean canEquip(ItemStack itemstack, EntityLivingBase player)
/* 126:    */   {
/* 127:128 */     return true;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public boolean canUnequip(ItemStack itemstack, EntityLivingBase player)
/* 131:    */   {
/* 132:133 */     return true;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void onEquipped(ItemStack itemstack, EntityLivingBase player) {}
/* 136:    */   
/* 137:    */   public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {}
/* 138:    */   
/* 139:    */   public static class EventHandlerRing
/* 140:    */   {
/* 141:    */     @SubscribeEvent
/* 142:    */     public void playerLogin(PlayerEvent.PlayerLoggedInEvent event)
/* 143:    */     {
/* 144:139 */       for (String name : MinecraftServer.getServer().getAllUsernames()) {
/* 145:140 */         if (ItemAngelRing.curFlyingPlayers.containsKey(name)) {
/* 146:141 */           NetworkHandler.sendPacketToPlayer(new PacketAngelRingNotifier(name, ((Integer)ItemAngelRing.curFlyingPlayers.get(name)).intValue()), event.player);
/* 147:    */         } else {
/* 148:143 */           NetworkHandler.sendPacketToPlayer(new PacketAngelRingNotifier(name, 0), event.player);
/* 149:    */         }
/* 150:    */       }
/* 151:    */     }
/* 152:    */     
/* 153:    */     @SubscribeEvent
/* 154:    */     public void entTick(LivingEvent.LivingUpdateEvent event)
/* 155:    */     {
/* 156:149 */       if (event.entity.worldObj.isClient) {
/* 157:150 */         return;
/* 158:    */       }
/* 159:152 */       ItemAngelRing.foundItem = true;
/* 160:154 */       if ((!XUHelper.hasPersistantNBT(event.entity)) || (!XUHelper.getPersistantNBT(event.entity).func_150297_b("XU|Flying", 1))) {
/* 161:155 */         return;
/* 162:    */       }
/* 163:157 */       Byte t = Byte.valueOf(XUHelper.getPersistantNBT(event.entity).getByte("XU|Flying"));
/* 164:158 */       Byte localByte1 = t;Byte localByte2 = t = Byte.valueOf((byte)(t.byteValue() - 1));
/* 165:160 */       if (t.byteValue() == 0)
/* 166:    */       {
/* 167:161 */         XUHelper.getPersistantNBT(event.entity).removeTag("XU|Flying");
/* 168:163 */         if ((event.entity instanceof EntityPlayerMP))
/* 169:    */         {
/* 170:164 */           EntityPlayerMP entityPlayer = (EntityPlayerMP)event.entity;
/* 171:165 */           ItemAngelRing.removePlayer(entityPlayer);
/* 172:166 */           if (!entityPlayer.capabilities.isCreativeMode)
/* 173:    */           {
/* 174:167 */             entityPlayer.capabilities.allowFlying = false;
/* 175:168 */             entityPlayer.capabilities.isFlying = false;
/* 176:169 */             entityPlayer.sendPlayerAbilities();
/* 177:    */           }
/* 178:    */         }
/* 179:    */       }
/* 180:    */       else
/* 181:    */       {
/* 182:173 */         XUHelper.getPersistantNBT(event.entity).setByte("XU|Flying", t.byteValue());
/* 183:    */       }
/* 184:    */     }
/* 185:    */   }
/* 186:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemAngelRing
 * JD-Core Version:    0.7.0.1
 */