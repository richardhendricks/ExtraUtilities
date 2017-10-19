/*  1:   */ package com.rwtema.extrautils.network.splitter;
/*  2:   */ 
/*  3:   */ import io.netty.buffer.ByteBuf;
/*  4:   */ 
/*  5:   */ public class XUPacketSplit
/*  6:   */ {
/*  7:   */   ByteBuf data;
/*  8:   */   int seq;
/*  9:   */   int packetIndex;
/* 10:   */   int total;
/* 11:   */   
/* 12:   */   public XUPacketSplit() {}
/* 13:   */   
/* 14:   */   public XUPacketSplit(ByteBuf data, int packetIndex, int seq, int total)
/* 15:   */   {
/* 16:17 */     this.data = data;
/* 17:18 */     this.packetIndex = packetIndex;
/* 18:19 */     this.seq = seq;
/* 19:20 */     this.total = total;
/* 20:   */   }
/* 21:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.network.splitter.XUPacketSplit
 * JD-Core Version:    0.7.0.1
 */