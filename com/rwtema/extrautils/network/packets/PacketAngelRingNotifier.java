/*  1:   */ package com.rwtema.extrautils.network.packets;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.EventHandlerClient;
/*  4:   */ import com.rwtema.extrautils.network.XUPacketBase;
/*  5:   */ import cpw.mods.fml.relauncher.Side;
/*  6:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  7:   */ import io.netty.buffer.ByteBuf;
/*  8:   */ import io.netty.channel.ChannelHandlerContext;
/*  9:   */ import java.util.Map;
/* 10:   */ import net.minecraft.entity.player.EntityPlayer;
/* 11:   */ 
/* 12:   */ public class PacketAngelRingNotifier
/* 13:   */   extends XUPacketBase
/* 14:   */ {
/* 15:   */   String username;
/* 16:   */   int wingType;
/* 17:   */   
/* 18:   */   public PacketAngelRingNotifier() {}
/* 19:   */   
/* 20:   */   public PacketAngelRingNotifier(String player, int wing)
/* 21:   */   {
/* 22:20 */     this.username = player;
/* 23:21 */     this.wingType = wing;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void writeData(ByteBuf data)
/* 27:   */     throws Exception
/* 28:   */   {
/* 29:26 */     writeString(data, this.username);
/* 30:27 */     data.writeByte(this.wingType);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void readData(EntityPlayer player, ByteBuf data)
/* 34:   */   {
/* 35:32 */     this.username = readString(data);
/* 36:33 */     this.wingType = data.readByte();
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void doStuffServer(ChannelHandlerContext ctx) {}
/* 40:   */   
/* 41:   */   @SideOnly(Side.CLIENT)
/* 42:   */   public void doStuffClient()
/* 43:   */   {
/* 44:44 */     if (this.wingType > 0) {
/* 45:45 */       EventHandlerClient.flyingPlayers.put(this.username, Integer.valueOf(this.wingType));
/* 46:   */     } else {
/* 47:47 */       EventHandlerClient.flyingPlayers.remove(this.username);
/* 48:   */     }
/* 49:   */   }
/* 50:   */   
/* 51:   */   public boolean isValidSenderSide(Side properSenderSide)
/* 52:   */   {
/* 53:53 */     return properSenderSide == Side.SERVER;
/* 54:   */   }
/* 55:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.network.packets.PacketAngelRingNotifier
 * JD-Core Version:    0.7.0.1
 */