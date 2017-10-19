/*  1:   */ package com.rwtema.extrautils.network.packets;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.CommandTPSTimer;
/*  4:   */ import com.rwtema.extrautils.ExtraUtilsMod;
/*  5:   */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*  6:   */ import com.rwtema.extrautils.network.XUPacketBase;
/*  7:   */ import cpw.mods.fml.common.network.NetworkRegistry;
/*  8:   */ import cpw.mods.fml.relauncher.Side;
/*  9:   */ import io.netty.buffer.ByteBuf;
/* 10:   */ import io.netty.channel.Channel;
/* 11:   */ import io.netty.channel.ChannelHandlerContext;
/* 12:   */ import io.netty.util.Attribute;
/* 13:   */ import net.minecraft.entity.player.EntityPlayer;
/* 14:   */ import net.minecraft.network.INetHandler;
/* 15:   */ 
/* 16:   */ public class PacketTime
/* 17:   */   extends XUPacketBase
/* 18:   */ {
/* 19:   */   long time;
/* 20:   */   int counter;
/* 21:   */   
/* 22:   */   public PacketTime()
/* 23:   */   {
/* 24:18 */     this(0L, 0);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public PacketTime(long time, int counter)
/* 28:   */   {
/* 29:23 */     this.time = time;
/* 30:24 */     this.counter = counter;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void writeData(ByteBuf data)
/* 34:   */     throws Exception
/* 35:   */   {
/* 36:29 */     data.writeLong(this.time);
/* 37:30 */     data.writeByte(this.counter);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void readData(EntityPlayer player, ByteBuf data)
/* 41:   */   {
/* 42:35 */     this.time = data.readLong();
/* 43:36 */     this.counter = data.readUnsignedByte();
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void doStuffServer(ChannelHandlerContext ctx)
/* 47:   */   {
/* 48:41 */     INetHandler netHandler = (INetHandler)ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
/* 49:42 */     EntityPlayer player = ExtraUtilsMod.proxy.getPlayerFromNetHandler(netHandler);
/* 50:43 */     CommandTPSTimer.add(player.getCommandSenderName());
/* 51:   */   }
/* 52:   */   
/* 53:   */   public void doStuffClient()
/* 54:   */   {
/* 55:48 */     CommandTPSTimer.update(this.counter, this.time);
/* 56:   */   }
/* 57:   */   
/* 58:   */   public boolean isValidSenderSide(Side properSenderSide)
/* 59:   */   {
/* 60:53 */     return true;
/* 61:   */   }
/* 62:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.network.packets.PacketTime
 * JD-Core Version:    0.7.0.1
 */