/*   1:    */ package com.rwtema.extrautils;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.network.NetworkHandler;
/*   4:    */ import com.rwtema.extrautils.network.packets.PacketTime;
/*   5:    */ import cpw.mods.fml.common.FMLCommonHandler;
/*   6:    */ import cpw.mods.fml.common.eventhandler.EventBus;
/*   7:    */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*   8:    */ import cpw.mods.fml.common.gameevent.TickEvent.Phase;
/*   9:    */ import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
/*  10:    */ import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
/*  11:    */ import cpw.mods.fml.relauncher.Side;
/*  12:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  13:    */ import java.util.HashSet;
/*  14:    */ import java.util.Locale;
/*  15:    */ import net.minecraft.client.Minecraft;
/*  16:    */ import net.minecraft.client.gui.FontRenderer;
/*  17:    */ import net.minecraft.client.renderer.OpenGlHelper;
/*  18:    */ import net.minecraft.client.renderer.RenderHelper;
/*  19:    */ import net.minecraft.command.CommandBase;
/*  20:    */ import net.minecraft.command.ICommandSender;
/*  21:    */ import net.minecraft.entity.player.EntityPlayer;
/*  22:    */ import net.minecraft.server.MinecraftServer;
/*  23:    */ import net.minecraft.server.management.ServerConfigurationManager;
/*  24:    */ import net.minecraft.util.EnumChatFormatting;
/*  25:    */ import org.lwjgl.opengl.GL11;
/*  26:    */ 
/*  27:    */ public class CommandTPSTimer
/*  28:    */   extends CommandBase
/*  29:    */ {
/*  30: 25 */   public static final CommandTPSTimer INSTANCE = new CommandTPSTimer();
/*  31:    */   
/*  32:    */   public static void init()
/*  33:    */   {
/*  34: 28 */     FMLCommonHandler.instance().bus().register(INSTANCE);
/*  35:    */   }
/*  36:    */   
/*  37: 31 */   public static HashSet<String> playerSet = new HashSet();
/*  38: 32 */   private static long[] clientTimer = new long[100];
/*  39: 33 */   private static int clientCounter = 0;
/*  40: 34 */   private static String displayString = "";
/*  41:    */   
/*  42:    */   public static void add(String commandSenderName)
/*  43:    */   {
/*  44: 37 */     EntityPlayer playerInstance = null;
/*  45: 38 */     for (Object o : MinecraftServer.getServer().getConfigurationManager().playerEntityList)
/*  46:    */     {
/*  47: 39 */       EntityPlayer player = (EntityPlayer)o;
/*  48: 40 */       if (commandSenderName.equals(player.getCommandSenderName())) {
/*  49: 41 */         playerInstance = player;
/*  50:    */       }
/*  51:    */     }
/*  52: 45 */     if (playerInstance == null) {
/*  53: 45 */       return;
/*  54:    */     }
/*  55: 47 */     if (playerSet.contains(commandSenderName))
/*  56:    */     {
/*  57: 48 */       playerSet.remove(commandSenderName);
/*  58: 49 */       NetworkHandler.sendPacketToPlayer(new PacketTime(0L, 255), playerInstance);
/*  59:    */     }
/*  60:    */     else
/*  61:    */     {
/*  62: 51 */       playerSet.add(commandSenderName);
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public static void update(int counter, long time)
/*  67:    */   {
/*  68: 55 */     if ((counter >= 100) || (counter < 0))
/*  69:    */     {
/*  70: 56 */       INSTANCE.display = false;
/*  71: 57 */       return;
/*  72:    */     }
/*  73: 60 */     INSTANCE.display = true;
/*  74: 62 */     while (clientCounter != counter)
/*  75:    */     {
/*  76: 63 */       clientCounter += 1;
/*  77: 64 */       if (clientCounter >= 100) {
/*  78: 64 */         clientCounter = 0;
/*  79:    */       }
/*  80: 65 */       clientTimer[clientCounter] = time;
/*  81:    */     }
/*  82: 68 */     displayString = getDisplayString();
/*  83:    */   }
/*  84:    */   
/*  85:    */   @SubscribeEvent
/*  86:    */   public void onServerTick(TickEvent.ServerTickEvent event)
/*  87:    */   {
/*  88: 73 */     if ((event.phase != TickEvent.Phase.END) || (playerSet.isEmpty())) {
/*  89: 73 */       return;
/*  90:    */     }
/*  91: 74 */     MinecraftServer server = MinecraftServer.getServer();
/*  92: 75 */     int counter = server.getTickCounter() % 100;
/*  93: 76 */     long[] longs = server.tickTimeArray;
/*  94: 77 */     if (longs == null) {
/*  95: 77 */       return;
/*  96:    */     }
/*  97: 79 */     for (Object o : server.getConfigurationManager().playerEntityList)
/*  98:    */     {
/*  99: 80 */       EntityPlayer player = (EntityPlayer)o;
/* 100: 81 */       if (playerSet.contains(player.getCommandSenderName())) {
/* 101: 83 */         NetworkHandler.sendPacketToPlayer(new PacketTime(longs[counter], counter), player);
/* 102:    */       }
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   @SubscribeEvent
/* 107:    */   @SideOnly(Side.CLIENT)
/* 108:    */   public void onDraw(TickEvent.RenderTickEvent event)
/* 109:    */   {
/* 110: 91 */     if ((event.phase != TickEvent.Phase.END) || (!this.display) || (displayString.length() == 0)) {
/* 111: 91 */       return;
/* 112:    */     }
/* 113: 93 */     Minecraft minecraft = Minecraft.getMinecraft();
/* 114: 94 */     if (minecraft.theWorld == null)
/* 115:    */     {
/* 116: 95 */       displayString = "";
/* 117: 96 */       return;
/* 118:    */     }
/* 119: 99 */     FontRenderer fontrenderer = minecraft.fontRenderer;
/* 120:100 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 121:101 */     GL11.glEnable(3042);
/* 122:102 */     OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 123:103 */     GL11.glBlendFunc(770, 771);
/* 124:104 */     RenderHelper.disableStandardItemLighting();
/* 125:    */     
/* 126:    */ 
/* 127:    */ 
/* 128:    */ 
/* 129:    */ 
/* 130:110 */     fontrenderer.drawString(displayString, 0, 0, -1, true);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public static String getDisplayString()
/* 134:    */   {
/* 135:114 */     long sum = 0L;
/* 136:115 */     long max = 0L;
/* 137:116 */     for (long l : clientTimer)
/* 138:    */     {
/* 139:117 */       sum += l;
/* 140:118 */       max = Math.max(max, l);
/* 141:    */     }
/* 142:120 */     return "TPS: " + formatTimer(clientTimer[clientCounter]) + ", " + formatTimer(sum / clientTimer.length) + ", " + formatTimer(max);
/* 143:    */   }
/* 144:    */   
/* 145:    */   public static String formatTimer(double time)
/* 146:    */   {
/* 147:124 */     double tps = Math.min(1000.0D / (time * 1.0E-006D), 20.0D);
/* 148:125 */     boolean tpsDown = tps != 20.0D;
/* 149:126 */     return String.format(Locale.ENGLISH, "%5.2f", new Object[] { Double.valueOf(time * 1.0E-006D) }) + "(" + (tpsDown ? EnumChatFormatting.RED : "") + String.format(Locale.ENGLISH, "%5.2f", new Object[] { Double.valueOf(tps) }) + (tpsDown ? EnumChatFormatting.RESET : "") + ")";
/* 150:    */   }
/* 151:    */   
/* 152:    */   public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_)
/* 153:    */   {
/* 154:131 */     return true;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public String getCommandName()
/* 158:    */   {
/* 159:136 */     return "xu_tps";
/* 160:    */   }
/* 161:    */   
/* 162:    */   public String getCommandUsage(ICommandSender p_71518_1_)
/* 163:    */   {
/* 164:141 */     return "xu_tps";
/* 165:    */   }
/* 166:    */   
/* 167:144 */   public boolean display = false;
/* 168:    */   
/* 169:    */   public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
/* 170:    */   {
/* 171:148 */     displayString = "";
/* 172:149 */     NetworkHandler.sendPacketToServer(new PacketTime());
/* 173:    */   }
/* 174:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.CommandTPSTimer
 * JD-Core Version:    0.7.0.1
 */