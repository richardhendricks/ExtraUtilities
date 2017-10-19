/*  1:   */ package com.rwtema.extrautils.network.packets;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.nei.ping.ParticlePing;
/*  4:   */ import com.rwtema.extrautils.network.XUPacketBase;
/*  5:   */ import cpw.mods.fml.relauncher.Side;
/*  6:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  7:   */ import io.netty.buffer.ByteBuf;
/*  8:   */ import io.netty.channel.ChannelHandlerContext;
/*  9:   */ import java.util.ArrayList;
/* 10:   */ import java.util.List;
/* 11:   */ import net.minecraft.client.Minecraft;
/* 12:   */ import net.minecraft.client.entity.EntityClientPlayerMP;
/* 13:   */ import net.minecraft.client.particle.EffectRenderer;
/* 14:   */ import net.minecraft.entity.player.EntityPlayer;
/* 15:   */ import net.minecraft.world.ChunkPosition;
/* 16:   */ 
/* 17:   */ public class PacketNEIPong
/* 18:   */   extends XUPacketBase
/* 19:   */ {
/* 20:   */   List<ChunkPosition> positionList;
/* 21:   */   public static final int MAX_SIZE = 20;
/* 22:   */   
/* 23:   */   public PacketNEIPong() {}
/* 24:   */   
/* 25:   */   public PacketNEIPong(List<ChunkPosition> positionList)
/* 26:   */   {
/* 27:21 */     this.positionList = positionList;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void writeData(ByteBuf data)
/* 31:   */     throws Exception
/* 32:   */   {
/* 33:29 */     data.writeByte(this.positionList.size());
/* 34:31 */     for (ChunkPosition pos : this.positionList)
/* 35:   */     {
/* 36:32 */       data.writeInt(pos.chunkPosX);
/* 37:33 */       data.writeByte(pos.chunkPosY);
/* 38:34 */       data.writeInt(pos.chunkPosZ);
/* 39:   */     }
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void readData(EntityPlayer player, ByteBuf data)
/* 43:   */   {
/* 44:42 */     int n = data.readUnsignedByte();
/* 45:43 */     if (n > 20) {
/* 46:43 */       n = 20;
/* 47:   */     }
/* 48:45 */     this.positionList = new ArrayList(n);
/* 49:46 */     for (int i = 0; i < n; i++)
/* 50:   */     {
/* 51:47 */       int x = data.readInt();
/* 52:48 */       int y = data.readUnsignedByte();
/* 53:49 */       int z = data.readInt();
/* 54:50 */       this.positionList.add(new ChunkPosition(x, y, z));
/* 55:   */     }
/* 56:   */   }
/* 57:   */   
/* 58:   */   public void doStuffServer(ChannelHandlerContext ctx) {}
/* 59:   */   
/* 60:   */   @SideOnly(Side.CLIENT)
/* 61:   */   public void doStuffClient()
/* 62:   */   {
/* 63:62 */     Minecraft.getMinecraft().thePlayer.closeScreen();
/* 64:64 */     for (ChunkPosition chunkPosition : this.positionList) {
/* 65:65 */       for (int i = 0; i < 20; i++) {
/* 66:66 */         Minecraft.getMinecraft().effectRenderer.addEffect(new ParticlePing(Minecraft.getMinecraft().theWorld, chunkPosition));
/* 67:   */       }
/* 68:   */     }
/* 69:   */   }
/* 70:   */   
/* 71:   */   public boolean isValidSenderSide(Side properSenderSide)
/* 72:   */   {
/* 73:73 */     return properSenderSide == Side.SERVER;
/* 74:   */   }
/* 75:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.network.packets.PacketNEIPong
 * JD-Core Version:    0.7.0.1
 */