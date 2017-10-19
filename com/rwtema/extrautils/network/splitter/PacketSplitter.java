/*  1:   */ package com.rwtema.extrautils.network.splitter;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.LogHelper;
/*  4:   */ import com.rwtema.extrautils.helper.XUHelper;
/*  5:   */ import cpw.mods.fml.common.network.internal.FMLProxyPacket;
/*  6:   */ import io.netty.buffer.ByteBuf;
/*  7:   */ import io.netty.buffer.Unpooled;
/*  8:   */ import java.util.ArrayList;
/*  9:   */ import java.util.List;
/* 10:   */ import java.util.Random;
/* 11:   */ 
/* 12:   */ public class PacketSplitter
/* 13:   */ {
/* 14:13 */   static int curSendingIndex = XUHelper.rand.nextInt();
/* 15:   */   public static final int maxSize = 2007136;
/* 16:   */   
/* 17:   */   public static boolean shouldSplit(FMLProxyPacket packet)
/* 18:   */   {
/* 19:18 */     return packet.payload().readableBytes() >= 2007136;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public static List<XUPacketSplit> splitPacket(FMLProxyPacket packet)
/* 23:   */   {
/* 24:22 */     List<XUPacketSplit> out = new ArrayList();
/* 25:23 */     ByteBuf buf = packet.payload().copy();
/* 26:24 */     int n = buf.readableBytes() / 2007136;
/* 27:25 */     if (n * 2007136 < buf.readableBytes()) {
/* 28:25 */       n++;
/* 29:   */     }
/* 30:27 */     curSendingIndex += 1;
/* 31:   */     
/* 32:29 */     LogHelper.debug("Splitting packet to " + n + " packets", new Object[0]);
/* 33:31 */     for (int i = 0; i < n; i++)
/* 34:   */     {
/* 35:32 */       int s = buf.readableBytes() < 2007136 ? buf.readableBytes() : 2007136;
/* 36:33 */       ByteBuf o = Unpooled.buffer(s);
/* 37:34 */       buf.readBytes(o, s);
/* 38:35 */       out.add(new XUPacketSplit(buf, curSendingIndex, i, n));
/* 39:   */     }
/* 40:38 */     return out;
/* 41:   */   }
/* 42:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.network.splitter.PacketSplitter
 * JD-Core Version:    0.7.0.1
 */