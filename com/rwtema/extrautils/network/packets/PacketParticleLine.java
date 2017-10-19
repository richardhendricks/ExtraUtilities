/*  1:   */ package com.rwtema.extrautils.network.packets;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtilsMod;
/*  4:   */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*  5:   */ import com.rwtema.extrautils.helper.XUHelper;
/*  6:   */ import com.rwtema.extrautils.network.XUPacketBase;
/*  7:   */ import com.rwtema.extrautils.particle.ParticleEndSmoke;
/*  8:   */ import com.rwtema.extrautils.particle.ParticleHelperClient;
/*  9:   */ import cpw.mods.fml.relauncher.Side;
/* 10:   */ import cpw.mods.fml.relauncher.SideOnly;
/* 11:   */ import io.netty.buffer.ByteBuf;
/* 12:   */ import io.netty.channel.ChannelHandlerContext;
/* 13:   */ import java.util.Random;
/* 14:   */ import net.minecraft.entity.item.EntityItem;
/* 15:   */ import net.minecraft.entity.player.EntityPlayer;
/* 16:   */ import net.minecraft.tileentity.TileEntity;
/* 17:   */ import net.minecraft.util.Vec3;
/* 18:   */ 
/* 19:   */ public class PacketParticleLine
/* 20:   */   extends XUPacketBase
/* 21:   */ {
/* 22:   */   Vec3 start;
/* 23:   */   Vec3 end;
/* 24:   */   
/* 25:   */   public PacketParticleLine() {}
/* 26:   */   
/* 27:   */   public PacketParticleLine(EntityItem item, TileEntity tile)
/* 28:   */   {
/* 29:26 */     this.start = Vec3.createVectorHelper(item.posX, item.posY, item.posZ);
/* 30:27 */     this.end = Vec3.createVectorHelper(tile.xCoord + 0.5D, tile.yCoord + 0.8D, tile.zCoord + 0.5D);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public PacketParticleLine(Vec3 start, Vec3 end)
/* 34:   */   {
/* 35:31 */     this.start = start;
/* 36:32 */     this.end = end;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void writeData(ByteBuf data)
/* 40:   */     throws Exception
/* 41:   */   {
/* 42:37 */     writeVec(data, this.start);
/* 43:38 */     writeVec(data, this.end);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void readData(EntityPlayer player, ByteBuf data)
/* 47:   */   {
/* 48:43 */     this.start = readVec(data);
/* 49:44 */     this.end = readVec(data);
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void doStuffServer(ChannelHandlerContext ctx) {}
/* 53:   */   
/* 54:   */   @SideOnly(Side.CLIENT)
/* 55:   */   public void doStuffClient()
/* 56:   */   {
/* 57:57 */     double v = 0.25D / this.end.subtract(this.start).lengthVector();
/* 58:59 */     for (double h = 0.0D; h <= 1.0D; h += v)
/* 59:   */     {
/* 60:60 */       float f = XUHelper.rand.nextFloat() * 0.6F + 0.4F;
/* 61:61 */       double x = this.start.xCoord + (this.end.xCoord - this.start.xCoord) * h;
/* 62:62 */       double y = this.start.yCoord + (this.end.yCoord - this.start.yCoord) * h;
/* 63:63 */       double z = this.start.zCoord + (this.end.zCoord - this.start.zCoord) * h;
/* 64:   */       
/* 65:   */ 
/* 66:   */ 
/* 67:   */ 
/* 68:68 */       ParticleHelperClient.addParticle(new ParticleEndSmoke(ExtraUtilsMod.proxy.getClientWorld(), x, y, z, f, f * 0.3F, f * 0.9F));
/* 69:   */     }
/* 70:   */   }
/* 71:   */   
/* 72:   */   public boolean isValidSenderSide(Side properSenderSide)
/* 73:   */   {
/* 74:77 */     return properSenderSide.isServer();
/* 75:   */   }
/* 76:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.network.packets.PacketParticleLine
 * JD-Core Version:    0.7.0.1
 */