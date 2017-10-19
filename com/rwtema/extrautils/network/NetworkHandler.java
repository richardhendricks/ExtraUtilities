/*   1:    */ package com.rwtema.extrautils.network;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.ExtraUtilsMod;
/*   5:    */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*   6:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   7:    */ import com.rwtema.extrautils.item.scanner.ItemScanner;
/*   8:    */ import com.rwtema.extrautils.network.packets.PacketParticle;
/*   9:    */ import com.rwtema.extrautils.network.packets.PacketParticleEvent;
/*  10:    */ import com.rwtema.extrautils.network.packets.PacketPlaySound;
/*  11:    */ import cpw.mods.fml.common.FMLCommonHandler;
/*  12:    */ import cpw.mods.fml.common.network.FMLEmbeddedChannel;
/*  13:    */ import cpw.mods.fml.common.network.FMLOutboundHandler;
/*  14:    */ import cpw.mods.fml.common.network.FMLOutboundHandler.OutboundTarget;
/*  15:    */ import cpw.mods.fml.common.network.NetworkRegistry;
/*  16:    */ import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
/*  17:    */ import cpw.mods.fml.relauncher.Side;
/*  18:    */ import io.netty.channel.ChannelFuture;
/*  19:    */ import io.netty.channel.ChannelFutureListener;
/*  20:    */ import io.netty.channel.ChannelHandler;
/*  21:    */ import io.netty.util.Attribute;
/*  22:    */ import java.util.EnumMap;
/*  23:    */ import java.util.List;
/*  24:    */ import net.minecraft.entity.player.EntityPlayer;
/*  25:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*  26:    */ import net.minecraft.item.ItemStack;
/*  27:    */ import net.minecraft.server.MinecraftServer;
/*  28:    */ import net.minecraft.server.management.ServerConfigurationManager;
/*  29:    */ import net.minecraft.world.ChunkCoordIntPair;
/*  30:    */ import net.minecraft.world.World;
/*  31:    */ import net.minecraft.world.WorldProvider;
/*  32:    */ 
/*  33:    */ public class NetworkHandler
/*  34:    */ {
/*  35:    */   public static EnumMap<Side, FMLEmbeddedChannel> channels;
/*  36:    */   
/*  37:    */   public static void register()
/*  38:    */   {
/*  39: 28 */     channels = NetworkRegistry.INSTANCE.newChannel("XU|Pkt", new ChannelHandler[] { new PacketCodec(), ExtraUtilsMod.proxy.getNewPacketHandler() });
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static void checkPacket(XUPacketBase packet, Side properSenderSide)
/*  43:    */   {
/*  44: 32 */     if (!packet.isValidSenderSide(properSenderSide)) {
/*  45: 33 */       throw new RuntimeException("Sending packet class" + packet.getClass().getSimpleName() + " from wrong side");
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static void sendToAllPlayers(XUPacketBase packet)
/*  50:    */   {
/*  51: 37 */     checkPacket(packet, Side.SERVER);
/*  52: 38 */     ((FMLEmbeddedChannel)channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
/*  53: 39 */     ((FMLEmbeddedChannel)channels.get(Side.SERVER)).writeOutbound(new Object[] { packet });
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static void sendPacketToPlayer(XUPacketBase packet, EntityPlayer player)
/*  57:    */   {
/*  58: 43 */     checkPacket(packet, Side.SERVER);
/*  59: 44 */     if (XUHelper.isPlayerFake(player)) {
/*  60: 44 */       return;
/*  61:    */     }
/*  62: 45 */     ((FMLEmbeddedChannel)channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
/*  63: 46 */     ((FMLEmbeddedChannel)channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
/*  64: 47 */     ((FMLEmbeddedChannel)channels.get(Side.SERVER)).writeOutbound(new Object[] { packet });
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static void sendToAllAround(XUPacketBase packet, int dimension, double x, double y, double z, double range)
/*  68:    */   {
/*  69: 52 */     sendToAllAround(packet, new NetworkRegistry.TargetPoint(dimension, x, y, z, range));
/*  70:    */   }
/*  71:    */   
/*  72:    */   public static void sendToAllAround(XUPacketBase packet, NetworkRegistry.TargetPoint point)
/*  73:    */   {
/*  74: 56 */     checkPacket(packet, Side.SERVER);
/*  75: 57 */     ((FMLEmbeddedChannel)channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
/*  76: 58 */     ((FMLEmbeddedChannel)channels.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
/*  77: 59 */     ((FMLEmbeddedChannel)channels.get(Side.SERVER)).writeAndFlush(packet).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static void sendPacketToServer(XUPacketBase packet)
/*  81:    */   {
/*  82: 63 */     checkPacket(packet, Side.CLIENT);
/*  83: 64 */     ((FMLEmbeddedChannel)channels.get(Side.CLIENT)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
/*  84: 65 */     ((FMLEmbeddedChannel)channels.get(Side.CLIENT)).writeOutbound(new Object[] { packet });
/*  85:    */   }
/*  86:    */   
/*  87:    */   public static void sendParticle(World world, String p, double x, double y, double z, double vx, double vy, double vz, boolean scannersOnly)
/*  88:    */   {
/*  89: 69 */     int maxDistance = 32;
/*  90: 71 */     if ((scannersOnly) && (ExtraUtils.scanner == null)) {
/*  91: 72 */       return;
/*  92:    */     }
/*  93: 75 */     PacketParticle packet = new PacketParticle(p, x, y, z, vx, vy, vz);
/*  94: 77 */     if ((scannersOnly) && (!ItemScanner.scannerOut)) {
/*  95: 78 */       return;
/*  96:    */     }
/*  97: 80 */     boolean noScanners = true;
/*  98: 82 */     for (int j = 0; j < world.playerEntities.size(); j++)
/*  99:    */     {
/* 100: 83 */       EntityPlayerMP player = (EntityPlayerMP)world.playerEntities.get(j);
/* 101: 85 */       if ((!scannersOnly) || ((player.getCurrentEquippedItem() != null) && (player.getCurrentEquippedItem().getItem() == ExtraUtils.scanner))) {
/* 102: 87 */         if ((Math.abs(player.posX - x) <= maxDistance) && (Math.abs(player.posY - y) <= maxDistance) && (Math.abs(player.posZ - z) <= maxDistance)) {
/* 103: 88 */           sendPacketToPlayer(packet, player);
/* 104:    */         }
/* 105:    */       }
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   public static void sendParticleEvent(World world, int type, int x, int y, int z)
/* 110:    */   {
/* 111: 95 */     int maxDistance = 24;
/* 112: 96 */     if (type < 0) {
/* 113: 97 */       return;
/* 114:    */     }
/* 115: 99 */     PacketParticleEvent packet = new PacketParticleEvent(x, y, z, (byte)type);
/* 116:    */     
/* 117:101 */     sendToAllAround(packet, world.provider.dimensionId, x, y, z, maxDistance);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public static void sendSoundEvent(World world, int type, float x, float y, float z)
/* 121:    */   {
/* 122:106 */     int maxDistance = 32;
/* 123:107 */     if (type < 0) {
/* 124:108 */       return;
/* 125:    */     }
/* 126:110 */     PacketPlaySound packet = new PacketPlaySound((short)type, x, y, z);
/* 127:    */     
/* 128:112 */     sendToAllAround(packet, world.provider.dimensionId, x, y, z, maxDistance);
/* 129:    */   }
/* 130:    */   
/* 131:    */   public static void sendToAllAround(XUPacketBase packet, int chunkX, int chunkZ)
/* 132:    */   {
/* 133:118 */     ChunkCoordIntPair chunkCoordIntPair = new ChunkCoordIntPair(chunkX, chunkZ);
/* 134:119 */     for (EntityPlayerMP player : FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().playerEntityList) {
/* 135:121 */       if (player.loadedChunks.contains(chunkCoordIntPair)) {
/* 136:122 */         sendPacketToPlayer(packet, player);
/* 137:    */       }
/* 138:    */     }
/* 139:    */   }
/* 140:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.network.NetworkHandler
 * JD-Core Version:    0.7.0.1
 */