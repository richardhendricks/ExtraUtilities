/*  1:   */ package com.rwtema.extrautils.network;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.common.FMLCommonHandler;
/*  4:   */ import cpw.mods.fml.relauncher.Side;
/*  5:   */ import io.netty.channel.ChannelHandler.Sharable;
/*  6:   */ import io.netty.channel.ChannelHandlerContext;
/*  7:   */ import io.netty.channel.SimpleChannelInboundHandler;
/*  8:   */ 
/*  9:   */ @ChannelHandler.Sharable
/* 10:   */ public class PacketHandler
/* 11:   */   extends SimpleChannelInboundHandler<XUPacketBase>
/* 12:   */ {
/* 13:   */   protected void channelRead0(ChannelHandlerContext ctx, XUPacketBase msg)
/* 14:   */     throws Exception
/* 15:   */   {
/* 16:13 */     if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
/* 17:14 */       msg.doStuffServer(ctx);
/* 18:   */     }
/* 19:   */   }
/* 20:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.network.PacketHandler
 * JD-Core Version:    0.7.0.1
 */