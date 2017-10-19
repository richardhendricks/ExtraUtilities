/*   1:    */ package com.rwtema.extrautils.network.packets;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtilsMod;
/*   4:    */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*   5:    */ import com.rwtema.extrautils.helper.SplineHelper;
/*   6:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   7:    */ import com.rwtema.extrautils.network.XUPacketBase;
/*   8:    */ import com.rwtema.extrautils.particle.ParticleEndSmoke;
/*   9:    */ import com.rwtema.extrautils.particle.ParticleHelperClient;
/*  10:    */ import cpw.mods.fml.relauncher.Side;
/*  11:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  12:    */ import io.netty.buffer.ByteBuf;
/*  13:    */ import io.netty.channel.ChannelHandlerContext;
/*  14:    */ import java.util.Random;
/*  15:    */ import net.minecraft.entity.item.EntityItem;
/*  16:    */ import net.minecraft.entity.player.EntityPlayer;
/*  17:    */ import net.minecraft.util.Vec3;
/*  18:    */ 
/*  19:    */ public class PacketParticleCurve
/*  20:    */   extends XUPacketBase
/*  21:    */ {
/*  22:    */   Vec3 startPos;
/*  23:    */   Vec3 endPos;
/*  24:    */   Vec3 startVel;
/*  25:    */   Vec3 endVel;
/*  26:    */   
/*  27:    */   public PacketParticleCurve() {}
/*  28:    */   
/*  29:    */   public PacketParticleCurve(Vec3 startPos, Vec3 endPos, Vec3 startVel, Vec3 endVel)
/*  30:    */   {
/*  31: 28 */     this.startPos = startPos;
/*  32: 29 */     this.endPos = endPos;
/*  33: 30 */     this.startVel = startVel;
/*  34: 31 */     this.endVel = endVel;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static Vec3 matchSpeed(EntityItem item, Vec3 v)
/*  38:    */   {
/*  39: 35 */     double s = 5.0D;
/*  40: 36 */     return Vec3.createVectorHelper(v.xCoord * s, v.yCoord * s, v.zCoord * s);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public PacketParticleCurve(EntityItem item, Vec3 dest, Vec3 vec)
/*  44:    */   {
/*  45: 40 */     this(Vec3.createVectorHelper(item.posX, item.posY, item.posZ), dest, Vec3.createVectorHelper(item.motionX * 5.0D, item.motionY * 5.0D, item.motionZ * 5.0D), matchSpeed(item, vec));
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void writeData(ByteBuf data)
/*  49:    */     throws Exception
/*  50:    */   {
/*  51: 49 */     writeVec(data, this.startPos);
/*  52: 50 */     writeVec(data, this.endPos);
/*  53: 51 */     writeVec(data, this.startVel);
/*  54: 52 */     writeVec(data, this.endVel);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void readData(EntityPlayer player, ByteBuf data)
/*  58:    */   {
/*  59: 57 */     this.startPos = readVec(data);
/*  60: 58 */     this.endPos = readVec(data);
/*  61: 59 */     this.startVel = readVec(data);
/*  62: 60 */     this.endVel = readVec(data);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void doStuffServer(ChannelHandlerContext ctx) {}
/*  66:    */   
/*  67:    */   @SideOnly(Side.CLIENT)
/*  68:    */   public void doStuffClient()
/*  69:    */   {
/*  70: 71 */     double v = 0.15D / this.endPos.subtract(this.startPos).lengthVector();
/*  71:    */     
/*  72: 73 */     double[] xParam = SplineHelper.splineParams(this.startPos.xCoord, this.endPos.xCoord, this.startVel.xCoord, this.endVel.xCoord);
/*  73: 74 */     double[] yParam = SplineHelper.splineParams(this.startPos.yCoord, this.endPos.yCoord, this.startVel.yCoord, this.endVel.yCoord);
/*  74: 75 */     double[] zParam = SplineHelper.splineParams(this.startPos.zCoord, this.endPos.zCoord, this.startVel.zCoord, this.endVel.zCoord);
/*  75:    */     
/*  76:    */ 
/*  77: 78 */     float f = XUHelper.rand.nextFloat() * 0.6F + 0.4F;
/*  78: 80 */     for (double h = v; h <= 1.0D; h += v)
/*  79:    */     {
/*  80: 82 */       double x = SplineHelper.evalSpline(h, xParam);
/*  81: 83 */       double y = SplineHelper.evalSpline(h, yParam);
/*  82: 84 */       double z = SplineHelper.evalSpline(h, zParam);
/*  83:    */       
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93: 95 */       ParticleHelperClient.addParticle(new ParticleEndSmoke(ExtraUtilsMod.proxy.getClientWorld(), x, y, z, f, f * 0.3F, f * 0.9F));
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   public boolean isValidSenderSide(Side properSenderSide)
/*  98:    */   {
/*  99:104 */     return properSenderSide.isServer();
/* 100:    */   }
/* 101:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.network.packets.PacketParticleCurve
 * JD-Core Version:    0.7.0.1
 */