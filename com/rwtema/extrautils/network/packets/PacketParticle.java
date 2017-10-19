/*   1:    */ package com.rwtema.extrautils.network.packets;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtilsMod;
/*   4:    */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*   5:    */ import com.rwtema.extrautils.network.XUPacketBase;
/*   6:    */ import cpw.mods.fml.relauncher.Side;
/*   7:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   8:    */ import io.netty.buffer.ByteBuf;
/*   9:    */ import io.netty.channel.ChannelHandlerContext;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import net.minecraft.entity.player.EntityPlayer;
/*  13:    */ import net.minecraft.world.World;
/*  14:    */ 
/*  15:    */ public class PacketParticle
/*  16:    */   extends XUPacketBase
/*  17:    */ {
/*  18: 16 */   public static List<String> particleNames = new ArrayList();
/*  19:    */   public String p;
/*  20:    */   public double x;
/*  21:    */   public double y;
/*  22:    */   public double z;
/*  23:    */   public double vx;
/*  24:    */   public double vy;
/*  25:    */   public double vz;
/*  26:    */   
/*  27:    */   static
/*  28:    */   {
/*  29: 19 */     particleNames.add("bubble");
/*  30: 20 */     particleNames.add("suspended");
/*  31: 21 */     particleNames.add("depthsuspend");
/*  32: 22 */     particleNames.add("townaura");
/*  33: 23 */     particleNames.add("crit");
/*  34: 24 */     particleNames.add("magicCrit");
/*  35: 25 */     particleNames.add("smoke");
/*  36: 26 */     particleNames.add("mobSpell");
/*  37: 27 */     particleNames.add("mobSpellAmbient");
/*  38: 28 */     particleNames.add("spell");
/*  39: 29 */     particleNames.add("instantSpell");
/*  40: 30 */     particleNames.add("witchMagic");
/*  41: 31 */     particleNames.add("note");
/*  42: 32 */     particleNames.add("portal");
/*  43: 33 */     particleNames.add("enchantmenttable");
/*  44: 34 */     particleNames.add("explode");
/*  45: 35 */     particleNames.add("flame");
/*  46: 36 */     particleNames.add("lava");
/*  47: 37 */     particleNames.add("footstep");
/*  48: 38 */     particleNames.add("splash");
/*  49: 39 */     particleNames.add("largesmoke");
/*  50: 40 */     particleNames.add("cloud");
/*  51: 41 */     particleNames.add("reddust");
/*  52: 42 */     particleNames.add("snowballpoof");
/*  53: 43 */     particleNames.add("dripWater");
/*  54: 44 */     particleNames.add("dripLava");
/*  55: 45 */     particleNames.add("snowshovel");
/*  56: 46 */     particleNames.add("slime");
/*  57: 47 */     particleNames.add("heart");
/*  58: 48 */     particleNames.add("angryVillager");
/*  59: 49 */     particleNames.add("happyVillager");
/*  60:    */   }
/*  61:    */   
/*  62:    */   public PacketParticle(String p, double x, double y, double z, double vx, double vy, double vz)
/*  63:    */   {
/*  64: 60 */     this.p = p;
/*  65: 61 */     this.x = x;
/*  66: 62 */     this.y = y;
/*  67: 63 */     this.z = z;
/*  68: 64 */     this.vx = vx;
/*  69: 65 */     this.vy = vy;
/*  70: 66 */     this.vz = vz;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void writeData(ByteBuf dataoutputstream)
/*  74:    */     throws Exception
/*  75:    */   {
/*  76: 71 */     if (!particleNames.contains(this.p)) {
/*  77: 72 */       dataoutputstream.writeByte(0);
/*  78:    */     } else {
/*  79: 74 */       dataoutputstream.writeByte(particleNames.indexOf(this.p));
/*  80:    */     }
/*  81: 76 */     dataoutputstream.writeFloat((float)this.x);
/*  82: 77 */     dataoutputstream.writeFloat((float)this.y);
/*  83: 78 */     dataoutputstream.writeFloat((float)this.z);
/*  84: 79 */     dataoutputstream.writeFloat((float)this.vx);
/*  85: 80 */     dataoutputstream.writeFloat((float)this.vy);
/*  86: 81 */     dataoutputstream.writeFloat((float)this.vz);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void readData(EntityPlayer player, ByteBuf datainputstream)
/*  90:    */   {
/*  91: 86 */     this.p = ((String)particleNames.get(datainputstream.readUnsignedByte()));
/*  92: 87 */     this.x = datainputstream.readFloat();
/*  93: 88 */     this.y = datainputstream.readFloat();
/*  94: 89 */     this.z = datainputstream.readFloat();
/*  95: 90 */     this.vx = datainputstream.readFloat();
/*  96: 91 */     this.vy = datainputstream.readFloat();
/*  97: 92 */     this.vz = datainputstream.readFloat();
/*  98:    */   }
/*  99:    */   
/* 100:    */   @SideOnly(Side.CLIENT)
/* 101:    */   public void doStuffClient()
/* 102:    */   {
/* 103:102 */     ExtraUtilsMod.proxy.getClientWorld().spawnParticle(this.p, this.x, this.y, this.z, this.vx, this.vy, this.vz);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public boolean isValidSenderSide(Side properSenderSide)
/* 107:    */   {
/* 108:107 */     return properSenderSide == Side.SERVER;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public PacketParticle() {}
/* 112:    */   
/* 113:    */   public void doStuffServer(ChannelHandlerContext ctx) {}
/* 114:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.network.packets.PacketParticle
 * JD-Core Version:    0.7.0.1
 */