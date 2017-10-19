/*  1:   */ package com.rwtema.extrautils.network;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.common.FMLCommonHandler;
/*  4:   */ import cpw.mods.fml.relauncher.Side;
/*  5:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  6:   */ import io.netty.channel.ChannelHandler.Sharable;
/*  7:   */ import io.netty.channel.ChannelHandlerContext;
/*  8:   */ 
/*  9:   */ @ChannelHandler.Sharable
/* 10:   */ @SideOnly(Side.CLIENT)
/* 11:   */ public class PacketHandlerClient
/* 12:   */   extends PacketHandler
/* 13:   */ {
/* 14:   */   protected void channelRead0(ChannelHandlerContext ctx, XUPacketBase msg)
/* 15:   */     throws Exception
/* 16:   */   {
/* 17:14 */     if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
/* 18:15 */       msg.doStuffServer(ctx);
/* 19:   */     } else {
/* 20:17 */       msg.doStuffClient();
/* 21:   */     }
/* 22:   */   }
/* 23:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.network.PacketHandlerClient
 * JD-Core Version:    0.7.0.1
 */