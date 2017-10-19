/*  1:   */ package com.rwtema.extrautils.network;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtilsMod;
/*  4:   */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*  5:   */ import com.rwtema.extrautils.LogHelper;
/*  6:   */ import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;
/*  7:   */ import cpw.mods.fml.common.network.NetworkRegistry;
/*  8:   */ import io.netty.buffer.ByteBuf;
/*  9:   */ import io.netty.channel.Channel;
/* 10:   */ import io.netty.channel.ChannelHandler.Sharable;
/* 11:   */ import io.netty.channel.ChannelHandlerContext;
/* 12:   */ import io.netty.util.Attribute;
/* 13:   */ import java.util.ArrayList;
/* 14:   */ import java.util.Collections;
/* 15:   */ import java.util.HashMap;
/* 16:   */ import net.minecraft.entity.player.EntityPlayer;
/* 17:   */ import net.minecraft.network.INetHandler;
/* 18:   */ 
/* 19:   */ @ChannelHandler.Sharable
/* 20:   */ public class PacketCodec
/* 21:   */   extends FMLIndexedMessageToMessageCodec<XUPacketBase>
/* 22:   */ {
/* 23:19 */   public static HashMap<String, Class<? extends XUPacketBase>> classes = new HashMap();
/* 24:   */   
/* 25:   */   public PacketCodec()
/* 26:   */   {
/* 27:22 */     ArrayList<String> t = new ArrayList();
/* 28:23 */     t.addAll(classes.keySet());
/* 29:   */     
/* 30:25 */     Collections.sort(t);
/* 31:27 */     for (int i = 0; i < t.size(); i++)
/* 32:   */     {
/* 33:28 */       LogHelper.fine("Registering Packet class " + (String)t.get(i) + " with discriminator: " + i, new Object[0]);
/* 34:29 */       addDiscriminator(i, (Class)classes.get(t.get(i)));
/* 35:   */     }
/* 36:   */   }
/* 37:   */   
/* 38:   */   public static void addClass(Class clazz)
/* 39:   */   {
/* 40:35 */     classes.put(clazz.getSimpleName(), clazz);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void encodeInto(ChannelHandlerContext ctx, XUPacketBase msg, ByteBuf target)
/* 44:   */     throws Exception
/* 45:   */   {
/* 46:40 */     INetHandler netHandler = (INetHandler)ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
/* 47:41 */     EntityPlayer player = ExtraUtilsMod.proxy.getPlayerFromNetHandler(netHandler);
/* 48:42 */     msg.writeData(target);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void decodeInto(ChannelHandlerContext ctx, ByteBuf source, XUPacketBase msg)
/* 52:   */   {
/* 53:47 */     INetHandler netHandler = (INetHandler)ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
/* 54:48 */     EntityPlayer player = ExtraUtilsMod.proxy.getPlayerFromNetHandler(netHandler);
/* 55:49 */     msg.readData(player, source);
/* 56:   */   }
/* 57:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.network.PacketCodec
 * JD-Core Version:    0.7.0.1
 */