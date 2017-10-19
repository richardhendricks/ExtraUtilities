/*   1:    */ package com.rwtema.extrautils;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.item.ItemAngelRing;
/*   4:    */ import com.rwtema.extrautils.network.PacketHandler;
/*   5:    */ import com.rwtema.extrautils.network.packets.PacketUseItemAlt;
/*   6:    */ import cpw.mods.fml.common.eventhandler.EventBus;
/*   7:    */ import java.util.Map;
/*   8:    */ import net.minecraft.block.Block;
/*   9:    */ import net.minecraft.entity.player.EntityPlayer;
/*  10:    */ import net.minecraft.item.Item;
/*  11:    */ import net.minecraft.item.ItemStack;
/*  12:    */ import net.minecraft.network.INetHandler;
/*  13:    */ import net.minecraft.network.NetHandlerPlayServer;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ import net.minecraftforge.common.MinecraftForge;
/*  16:    */ import net.minecraftforge.event.entity.player.PlayerInteractEvent;
/*  17:    */ 
/*  18:    */ public class ExtraUtilsProxy
/*  19:    */ {
/*  20:    */   public static ExtraUtilsProxy INSTANCE;
/*  21:    */   
/*  22:    */   public ExtraUtilsProxy()
/*  23:    */   {
/*  24: 20 */     INSTANCE = this;
/*  25:    */   }
/*  26:    */   
/*  27: 23 */   public static int colorBlockID = 0;
/*  28: 24 */   public static int fullBrightBlockID = 0;
/*  29: 25 */   public static int multiBlockID = 0;
/*  30: 26 */   public static int spikeBlockID = 0;
/*  31: 27 */   public static int drumRendererID = 0;
/*  32: 28 */   public static int connectedTextureID = 0;
/*  33: 29 */   public static int connectedTextureEtheralID = 0;
/*  34: 31 */   public static Block FMPBlockId = null;
/*  35: 32 */   public static boolean checked = false;
/*  36: 34 */   public static Item MicroBlockId = null;
/*  37: 35 */   public static boolean checked2 = false;
/*  38:    */   
/*  39:    */   public void postInit() {}
/*  40:    */   
/*  41:    */   public void registerEventHandler()
/*  42:    */   {
/*  43: 41 */     MinecraftForge.EVENT_BUS.register(new EventHandlerServer());
/*  44: 42 */     MinecraftForge.EVENT_BUS.register(new EventHandlerSiege());
/*  45: 43 */     MinecraftForge.EVENT_BUS.register(new EventHandlerEntityItemStealer());
/*  46: 44 */     MinecraftForge.EVENT_BUS.register(new EventHandlerChunkLoad());
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void registerRenderInformation() {}
/*  50:    */   
/*  51:    */   public EntityPlayer getPlayerFromNetHandler(INetHandler handler)
/*  52:    */   {
/*  53: 52 */     if ((handler instanceof NetHandlerPlayServer)) {
/*  54: 53 */       return ((NetHandlerPlayServer)handler).playerEntity;
/*  55:    */     }
/*  56: 55 */     return null;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void throwLoadingError(String cause, String... message)
/*  60:    */   {
/*  61: 60 */     String concat = cause + ": ";
/*  62: 61 */     for (String m : message) {
/*  63: 62 */       concat = concat + " - " + m;
/*  64:    */     }
/*  65: 64 */     throw new RuntimeException(concat);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public EntityPlayer getClientPlayer()
/*  69:    */   {
/*  70: 68 */     throw new RuntimeException("getClientPlayer called on server");
/*  71:    */   }
/*  72:    */   
/*  73:    */   public World getClientWorld()
/*  74:    */   {
/*  75: 72 */     throw new RuntimeException("getClientWorld called on server");
/*  76:    */   }
/*  77:    */   
/*  78:    */   public boolean isClientSideAvailable()
/*  79:    */   {
/*  80: 76 */     return false;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void newServerStart()
/*  84:    */   {
/*  85: 80 */     if (ExtraUtils.angelRingEnabled) {
/*  86: 81 */       ItemAngelRing.curFlyingPlayers.clear();
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void registerClientCommands() {}
/*  91:    */   
/*  92:    */   public PacketHandler getNewPacketHandler()
/*  93:    */   {
/*  94: 90 */     return new PacketHandler();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void exectuteClientCode(IClientCode clientCode) {}
/*  98:    */   
/*  99:    */   public void sendUsePacket(PlayerInteractEvent event) {}
/* 100:    */   
/* 101:    */   public void sendUsePacket(int x, int y, int z, int face, ItemStack item, float f, float f1, float f2) {}
/* 102:    */   
/* 103:    */   public void sendAltUsePacket(int x, int y, int z, int face, ItemStack item, float f, float f1, float f2) {}
/* 104:    */   
/* 105:    */   public void sendAltUsePacket(ItemStack item) {}
/* 106:    */   
/* 107:    */   public boolean isAltSneaking()
/* 108:    */   {
/* 109:115 */     return PacketUseItemAlt.altPlace.get() == Boolean.TRUE;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public <F, T> T apply(ISidedFunction<F, T> func, F input)
/* 113:    */   {
/* 114:119 */     return func.applyServer(input);
/* 115:    */   }
/* 116:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.ExtraUtilsProxy
 * JD-Core Version:    0.7.0.1
 */