/*   1:    */ package com.rwtema.extrautils.network.packets;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   4:    */ import com.rwtema.extrautils.network.NetworkHandler;
/*   5:    */ import com.rwtema.extrautils.network.XUPacketBase;
/*   6:    */ import cpw.mods.fml.relauncher.Side;
/*   7:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   8:    */ import io.netty.buffer.ByteBuf;
/*   9:    */ import io.netty.channel.ChannelHandlerContext;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import net.minecraft.client.Minecraft;
/*  13:    */ import net.minecraft.client.gui.GuiIngame;
/*  14:    */ import net.minecraft.client.gui.GuiNewChat;
/*  15:    */ import net.minecraft.entity.player.EntityPlayer;
/*  16:    */ import net.minecraft.util.ChatComponentText;
/*  17:    */ import net.minecraft.util.IChatComponent;
/*  18:    */ import net.minecraft.world.World;
/*  19:    */ 
/*  20:    */ public class PacketTempChatMultiline
/*  21:    */   extends XUPacketBase
/*  22:    */ {
/*  23:    */   IChatComponent[] chat;
/*  24:    */   static final int START_ID = 983423323;
/*  25: 23 */   static int lastNum = 0;
/*  26:    */   
/*  27:    */   public PacketTempChatMultiline() {}
/*  28:    */   
/*  29:    */   public PacketTempChatMultiline(List<String> chat)
/*  30:    */   {
/*  31: 30 */     this.chat = new IChatComponent[chat.size()];
/*  32: 31 */     for (int i = 0; i < chat.size(); i++) {
/*  33: 32 */       this.chat[i] = new ChatComponentText((String)chat.get(i));
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   public PacketTempChatMultiline(IChatComponent[] chat)
/*  38:    */   {
/*  39: 37 */     this.chat = chat;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void writeData(ByteBuf data)
/*  43:    */     throws Exception
/*  44:    */   {
/*  45: 42 */     data.writeShort(this.chat.length);
/*  46: 43 */     for (IChatComponent iChatComponent : this.chat) {
/*  47: 44 */       writeChatComponent(data, iChatComponent);
/*  48:    */     }
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void readData(EntityPlayer player, ByteBuf data)
/*  52:    */   {
/*  53: 50 */     this.chat = new IChatComponent[data.readUnsignedShort()];
/*  54: 51 */     for (int i = 0; i < this.chat.length; i++) {
/*  55: 52 */       this.chat[i] = readChatComponent(data);
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void doStuffServer(ChannelHandlerContext ctx) {}
/*  60:    */   
/*  61:    */   @SideOnly(Side.CLIENT)
/*  62:    */   public void doStuffClient()
/*  63:    */   {
/*  64: 64 */     addChat(this.chat);
/*  65:    */   }
/*  66:    */   
/*  67:    */   private static synchronized void addChat(IChatComponent[] chat)
/*  68:    */   {
/*  69: 68 */     GuiNewChat chatGUI = Minecraft.getMinecraft().ingameGUI.getChatGUI();
/*  70: 70 */     for (int i = 0; i < chat.length; i++)
/*  71:    */     {
/*  72: 71 */       IChatComponent iChatComponent = chat[i];
/*  73: 72 */       chatGUI.func_146234_a(iChatComponent, 983423323 + i);
/*  74:    */     }
/*  75: 75 */     for (int i = chat.length; i < lastNum; i++) {
/*  76: 76 */       chatGUI.func_146242_c(983423323 + i);
/*  77:    */     }
/*  78: 79 */     lastNum = Math.max(lastNum, chat.length);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public boolean isValidSenderSide(Side properSenderSide)
/*  82:    */   {
/*  83: 84 */     return properSenderSide == Side.SERVER;
/*  84:    */   }
/*  85:    */   
/*  86: 87 */   static ThreadLocal<List<IChatComponent>> chatComponents = new ThreadLocal()
/*  87:    */   {
/*  88:    */     protected List<IChatComponent> initialValue()
/*  89:    */     {
/*  90: 90 */       return new ArrayList();
/*  91:    */     }
/*  92:    */   };
/*  93:    */   
/*  94:    */   public static void addChatComponentMessage(IChatComponent chatComponentText)
/*  95:    */   {
/*  96: 95 */     ((List)chatComponents.get()).add(chatComponentText);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static void sendCached(EntityPlayer player)
/* 100:    */   {
/* 101: 99 */     List<IChatComponent> componentList = (List)chatComponents.get();
/* 102:101 */     if (componentList.isEmpty()) {
/* 103:101 */       return;
/* 104:    */     }
/* 105:103 */     if (!XUHelper.isPlayerFake(player))
/* 106:    */     {
/* 107:104 */       IChatComponent[] iChatComponents = (IChatComponent[])componentList.toArray(new IChatComponent[componentList.size()]);
/* 108:105 */       if (player.worldObj.isClient) {
/* 109:106 */         addChat(iChatComponents);
/* 110:    */       } else {
/* 111:108 */         NetworkHandler.sendPacketToPlayer(new PacketTempChatMultiline(iChatComponents), player);
/* 112:    */       }
/* 113:    */     }
/* 114:111 */     componentList.clear();
/* 115:    */   }
/* 116:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.network.packets.PacketTempChatMultiline
 * JD-Core Version:    0.7.0.1
 */