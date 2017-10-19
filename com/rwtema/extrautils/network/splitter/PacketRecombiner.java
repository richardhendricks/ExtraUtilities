/*  1:   */ package com.rwtema.extrautils.network.splitter;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.network.NetworkHandler;
/*  4:   */ import cpw.mods.fml.common.FMLCommonHandler;
/*  5:   */ import cpw.mods.fml.common.network.FMLEmbeddedChannel;
/*  6:   */ import cpw.mods.fml.common.network.NetworkRegistry;
/*  7:   */ import cpw.mods.fml.common.network.internal.FMLProxyPacket;
/*  8:   */ import cpw.mods.fml.relauncher.Side;
/*  9:   */ import io.netty.buffer.ByteBuf;
/* 10:   */ import io.netty.buffer.Unpooled;
/* 11:   */ import io.netty.channel.ChannelHandler.Sharable;
/* 12:   */ import io.netty.channel.ChannelHandlerContext;
/* 13:   */ import io.netty.channel.SimpleChannelInboundHandler;
/* 14:   */ import io.netty.util.Attribute;
/* 15:   */ import java.util.EnumMap;
/* 16:   */ import java.util.HashMap;
/* 17:   */ import java.util.Map;
/* 18:   */ 
/* 19:   */ @ChannelHandler.Sharable
/* 20:   */ public class PacketRecombiner
/* 21:   */   extends SimpleChannelInboundHandler<XUPacketSplit>
/* 22:   */ {
/* 23:19 */   public static Map<Integer, ByteBuf[]> map = new HashMap();
/* 24:   */   
/* 25:   */   protected void channelRead0(ChannelHandlerContext ctx, XUPacketSplit msg)
/* 26:   */     throws Exception
/* 27:   */   {
/* 28:23 */     ByteBuf[] b = (ByteBuf[])map.get(Integer.valueOf(msg.packetIndex));
/* 29:25 */     if ((b == null) || (b.length != msg.total)) {
/* 30:26 */       b = new ByteBuf[msg.total];
/* 31:   */     }
/* 32:28 */     b[msg.seq] = msg.data;
/* 33:   */     
/* 34:30 */     boolean flag = true;
/* 35:31 */     int s = 0;
/* 36:32 */     for (int i = 0; (i < b.length) && (flag); i++) {
/* 37:33 */       if (b[i] != null)
/* 38:   */       {
/* 39:34 */         flag = false;
/* 40:35 */         s += b[i].readableBytes();
/* 41:   */       }
/* 42:   */     }
/* 43:38 */     if (flag)
/* 44:   */     {
/* 45:39 */       ByteBuf data = Unpooled.buffer(s);
/* 46:40 */       for (int i = 0; i < b.length; i++) {
/* 47:41 */         data.writeBytes(b[i]);
/* 48:   */       }
/* 49:43 */       FMLProxyPacket proxy = new FMLProxyPacket(data, (String)((FMLEmbeddedChannel)NetworkHandler.channels.get(Side.CLIENT)).attr(NetworkRegistry.FML_CHANNEL).get());
/* 50:44 */       ((FMLEmbeddedChannel)NetworkHandler.channels.get(FMLCommonHandler.instance().getEffectiveSide())).writeInbound(new Object[] { proxy });
/* 51:45 */       map.remove(Integer.valueOf(msg.packetIndex));
/* 52:46 */       if (map.size() > 1024) {
/* 53:47 */         map.clear();
/* 54:   */       }
/* 55:   */     }
/* 56:   */     else
/* 57:   */     {
/* 58:49 */       map.put(Integer.valueOf(msg.packetIndex), b);
/* 59:   */     }
/* 60:   */   }
/* 61:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.network.splitter.PacketRecombiner
 * JD-Core Version:    0.7.0.1
 */