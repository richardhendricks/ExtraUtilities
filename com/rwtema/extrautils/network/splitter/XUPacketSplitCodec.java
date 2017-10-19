/*  1:   */ package com.rwtema.extrautils.network.splitter;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;
/*  4:   */ import io.netty.buffer.ByteBuf;
/*  5:   */ import io.netty.buffer.Unpooled;
/*  6:   */ import io.netty.channel.ChannelHandler.Sharable;
/*  7:   */ import io.netty.channel.ChannelHandlerContext;
/*  8:   */ 
/*  9:   */ @ChannelHandler.Sharable
/* 10:   */ public class XUPacketSplitCodec
/* 11:   */   extends FMLIndexedMessageToMessageCodec<XUPacketSplit>
/* 12:   */ {
/* 13:   */   public XUPacketSplitCodec()
/* 14:   */   {
/* 15:12 */     addDiscriminator(0, XUPacketSplit.class);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void encodeInto(ChannelHandlerContext ctx, XUPacketSplit msg, ByteBuf target)
/* 19:   */     throws Exception
/* 20:   */   {
/* 21:17 */     target.writeInt(msg.packetIndex);
/* 22:18 */     target.writeInt(msg.seq);
/* 23:19 */     target.writeInt(msg.total);
/* 24:20 */     target.writeBytes(msg.data);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf source, XUPacketSplit msg)
/* 28:   */   {
/* 29:25 */     msg.packetIndex = source.readInt();
/* 30:26 */     msg.seq = source.readInt();
/* 31:27 */     msg.total = source.readInt();
/* 32:28 */     ByteBuf buffer = Unpooled.buffer(source.readableBytes());
/* 33:29 */     source.readBytes(buffer);
/* 34:30 */     msg.data = buffer;
/* 35:   */   }
/* 36:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.network.splitter.XUPacketSplitCodec
 * JD-Core Version:    0.7.0.1
 */