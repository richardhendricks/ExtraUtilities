/*   1:    */ package com.rwtema.extrautils.network.packets;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.ExtraUtilsMod;
/*   5:    */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*   6:    */ import com.rwtema.extrautils.helper.XURandom;
/*   7:    */ import com.rwtema.extrautils.network.XUPacketBase;
/*   8:    */ import com.rwtema.extrautils.particle.ParticleTransferNodes;
/*   9:    */ import cpw.mods.fml.relauncher.Side;
/*  10:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  11:    */ import io.netty.buffer.ByteBuf;
/*  12:    */ import io.netty.channel.ChannelHandlerContext;
/*  13:    */ import java.util.Random;
/*  14:    */ import net.minecraft.client.Minecraft;
/*  15:    */ import net.minecraft.client.particle.EffectRenderer;
/*  16:    */ import net.minecraft.entity.player.EntityPlayer;
/*  17:    */ import net.minecraft.world.World;
/*  18:    */ 
/*  19:    */ public class PacketParticleEvent
/*  20:    */   extends XUPacketBase
/*  21:    */ {
/*  22:    */   int x;
/*  23:    */   int y;
/*  24:    */   int z;
/*  25:    */   byte i;
/*  26:    */   
/*  27:    */   public PacketParticleEvent() {}
/*  28:    */   
/*  29:    */   public PacketParticleEvent(int x, int y, int z, byte i)
/*  30:    */   {
/*  31: 26 */     this.x = x;
/*  32: 27 */     this.y = y;
/*  33: 28 */     this.z = z;
/*  34: 29 */     this.i = i;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void writeData(ByteBuf data)
/*  38:    */     throws Exception
/*  39:    */   {
/*  40: 34 */     data.writeInt(this.x);
/*  41: 35 */     data.writeInt(this.y);
/*  42: 36 */     data.writeInt(this.z);
/*  43: 37 */     data.writeByte(this.i);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void readData(EntityPlayer player, ByteBuf data)
/*  47:    */   {
/*  48: 42 */     this.x = data.readInt();
/*  49: 43 */     this.y = data.readInt();
/*  50: 44 */     this.z = data.readInt();
/*  51: 45 */     this.i = data.readByte();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void doStuffServer(ChannelHandlerContext ctx) {}
/*  55:    */   
/*  56:    */   @SideOnly(Side.CLIENT)
/*  57:    */   public void doStuffClient()
/*  58:    */   {
/*  59: 55 */     doParticleEvent(this.i, this.x, this.y, this.z);
/*  60:    */   }
/*  61:    */   
/*  62: 58 */   private static Random rand = ;
/*  63:    */   
/*  64:    */   @SideOnly(Side.CLIENT)
/*  65:    */   private void doParticleEvent(int type, int x, int y, int z)
/*  66:    */   {
/*  67: 62 */     switch (type)
/*  68:    */     {
/*  69:    */     case 0: 
/*  70: 65 */       int dy = 1;
/*  71: 67 */       for (int y1 = y + dy; y1 < 256; y1 += dy)
/*  72:    */       {
/*  73: 68 */         ExtraUtilsMod.proxy.getClientWorld().spawnParticle("portal", x + 0.5D, y1 + 0.5D, z + 0.5D, rand.nextGaussian() * 0.1D, -dy + rand.nextGaussian() * 0.1D, rand.nextGaussian() * 0.1D);
/*  74: 69 */         dy = rand.nextInt(8);
/*  75:    */       }
/*  76: 72 */       break;
/*  77:    */     case 1: 
/*  78: 75 */       ExtraUtilsMod.proxy.getClientWorld().spawnParticle("reddust", x + 0.5D, y + 0.5D, z + 0.5D, 1.0D, 0.3D, 0.9D);
/*  79: 76 */       break;
/*  80:    */     case 2: 
/*  81: 80 */       int dy = 1;
/*  82: 82 */       for (int y1 = y + dy; y1 < 256; y1 += dy)
/*  83:    */       {
/*  84: 83 */         ExtraUtilsMod.proxy.getClientWorld().spawnParticle("portal", x + 0.5D, y1 + 0.5D, z + 0.5D, rand.nextGaussian() * 0.1D, dy + rand.nextGaussian() * 0.1D, rand.nextGaussian() * 0.1D);
/*  85: 84 */         dy = rand.nextInt(8);
/*  86:    */       }
/*  87: 87 */       break;
/*  88:    */     case 3: 
/*  89: 91 */       spawnNodeParticles(x, y, z, 1.0F, 0.0F, 0.0F);
/*  90: 92 */       break;
/*  91:    */     case 4: 
/*  92: 94 */       spawnNodeParticles(x, y, z, 0.0F, 0.0F, 1.0F);
/*  93: 95 */       break;
/*  94:    */     case 5: 
/*  95: 97 */       spawnNodeParticles(x, y, z, 0.0F, 1.0F, 0.0F);
/*  96: 98 */       break;
/*  97:    */     case 6: 
/*  98:100 */       spawnNodeParticles(x, y, z, 0.0F, 1.0F, 1.0F);
/*  99:101 */       break;
/* 100:    */     case 7: 
/* 101:103 */       spawnNodeParticles(x, y, z, 1.0F, 1.0F, 0.0F);
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:109 */   public static final int[] dx = { 0, 1, 0, 1, 0, 1, 0, 1 };
/* 106:110 */   public static final int[] dy = { 0, 0, 1, 1, 0, 0, 1, 1 };
/* 107:111 */   public static final int[] dz = { 0, 0, 0, 0, 1, 1, 1, 1 };
/* 108:    */   public static final double w = 0.2D;
/* 109:    */   public static final double w1 = 0.3D;
/* 110:    */   public static final double w2 = 0.4D;
/* 111:    */   
/* 112:    */   @SideOnly(Side.CLIENT)
/* 113:    */   private void spawnNodeParticles(int x, int y, int z, float pr, float pg, float pb)
/* 114:    */   {
/* 115:118 */     if (ExtraUtils.disableNodeParticles) {
/* 116:119 */       return;
/* 117:    */     }
/* 118:121 */     Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleTransferNodes(ExtraUtilsMod.proxy.getClientWorld(), x + 0.5D, y + 0.5D, z + 0.5D, pr, pg, pb));
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean isValidSenderSide(Side properSenderSide)
/* 122:    */   {
/* 123:128 */     return properSenderSide == Side.SERVER;
/* 124:    */   }
/* 125:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.network.packets.PacketParticleEvent
 * JD-Core Version:    0.7.0.1
 */