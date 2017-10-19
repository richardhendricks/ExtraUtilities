/*  1:   */ package com.rwtema.extrautils.network.packets;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtilsMod;
/*  4:   */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*  5:   */ import com.rwtema.extrautils.IClientCode;
/*  6:   */ import com.rwtema.extrautils.helper.XUHelper;
/*  7:   */ import com.rwtema.extrautils.network.NetworkHandler;
/*  8:   */ import com.rwtema.extrautils.network.XUPacketBase;
/*  9:   */ import cpw.mods.fml.relauncher.Side;
/* 10:   */ import cpw.mods.fml.relauncher.SideOnly;
/* 11:   */ import io.netty.buffer.ByteBuf;
/* 12:   */ import io.netty.channel.ChannelHandlerContext;
/* 13:   */ import net.minecraft.client.Minecraft;
/* 14:   */ import net.minecraft.client.gui.GuiIngame;
/* 15:   */ import net.minecraft.client.gui.GuiNewChat;
/* 16:   */ import net.minecraft.entity.player.EntityPlayer;
/* 17:   */ import net.minecraft.util.ChatComponentText;
/* 18:   */ import net.minecraft.util.IChatComponent;
/* 19:   */ import net.minecraft.world.World;
/* 20:   */ 
/* 21:   */ public class PacketTempChat
/* 22:   */   extends XUPacketBase
/* 23:   */ {
/* 24:   */   IChatComponent chatComponent;
/* 25:   */   
/* 26:   */   public static void sendChat(EntityPlayer player, IChatComponent s)
/* 27:   */   {
/* 28:22 */     if (XUHelper.isPlayerFake(player)) {
/* 29:23 */       return;
/* 30:   */     }
/* 31:25 */     if (player.worldObj.isClient) {
/* 32:26 */       ExtraUtilsMod.proxy.exectuteClientCode(new IClientCode()
/* 33:   */       {
/* 34:   */         @SideOnly(Side.CLIENT)
/* 35:   */         public void exectuteClientCode()
/* 36:   */         {
/* 37:30 */           PacketTempChat.addChat(this.val$s);
/* 38:   */         }
/* 39:   */       });
/* 40:   */     } else {
/* 41:34 */       NetworkHandler.sendPacketToPlayer(new PacketTempChat(s), player);
/* 42:   */     }
/* 43:   */   }
/* 44:   */   
/* 45:   */   public static void sendChat(EntityPlayer player, String s)
/* 46:   */   {
/* 47:39 */     sendChat(player, new ChatComponentText(s));
/* 48:   */   }
/* 49:   */   
/* 50:   */   public PacketTempChat() {}
/* 51:   */   
/* 52:   */   public PacketTempChat(String s)
/* 53:   */   {
/* 54:46 */     this(new ChatComponentText(s));
/* 55:   */   }
/* 56:   */   
/* 57:   */   public PacketTempChat(IChatComponent chatComponent)
/* 58:   */   {
/* 59:50 */     this.chatComponent = chatComponent;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void writeData(ByteBuf data)
/* 63:   */     throws Exception
/* 64:   */   {
/* 65:55 */     writeChatComponent(data, this.chatComponent);
/* 66:   */   }
/* 67:   */   
/* 68:   */   public void readData(EntityPlayer player, ByteBuf data)
/* 69:   */   {
/* 70:60 */     this.chatComponent = readChatComponent(data);
/* 71:   */   }
/* 72:   */   
/* 73:   */   public void doStuffServer(ChannelHandlerContext ctx) {}
/* 74:   */   
/* 75:   */   @SideOnly(Side.CLIENT)
/* 76:   */   public void doStuffClient()
/* 77:   */   {
/* 78:72 */     addChat(this.chatComponent);
/* 79:   */   }
/* 80:   */   
/* 81:   */   @SideOnly(Side.CLIENT)
/* 82:   */   private static void addChat(IChatComponent chat)
/* 83:   */   {
/* 84:77 */     Minecraft.getMinecraft().ingameGUI.getChatGUI().func_146234_a(chat, 7706071);
/* 85:   */   }
/* 86:   */   
/* 87:   */   public boolean isValidSenderSide(Side properSenderSide)
/* 88:   */   {
/* 89:82 */     return properSenderSide == Side.SERVER;
/* 90:   */   }
/* 91:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.network.packets.PacketTempChat
 * JD-Core Version:    0.7.0.1
 */