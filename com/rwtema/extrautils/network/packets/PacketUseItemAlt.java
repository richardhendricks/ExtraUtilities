/*   1:    */ package com.rwtema.extrautils.network.packets;
/*   2:    */ 
/*   3:    */ import com.google.common.base.Throwables;
/*   4:    */ import com.rwtema.extrautils.network.XUPacketBase;
/*   5:    */ import cpw.mods.fml.relauncher.Side;
/*   6:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   7:    */ import io.netty.buffer.ByteBuf;
/*   8:    */ import io.netty.buffer.Unpooled;
/*   9:    */ import io.netty.channel.ChannelHandlerContext;
/*  10:    */ import java.io.IOException;
/*  11:    */ import net.minecraft.entity.player.EntityPlayer;
/*  12:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*  13:    */ import net.minecraft.item.ItemStack;
/*  14:    */ import net.minecraft.network.NetHandlerPlayServer;
/*  15:    */ import net.minecraft.network.PacketBuffer;
/*  16:    */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*  17:    */ 
/*  18:    */ public class PacketUseItemAlt
/*  19:    */   extends XUPacketBase
/*  20:    */ {
/*  21:    */   private int x;
/*  22:    */   private int y;
/*  23:    */   private int z;
/*  24:    */   private int face;
/*  25:    */   private ItemStack item;
/*  26:    */   private float hitX;
/*  27:    */   private float hitY;
/*  28:    */   private float hitZ;
/*  29:    */   private EntityPlayerMP player;
/*  30:    */   
/*  31:    */   public PacketUseItemAlt(int x, int y, int z, int face, ItemStack item, float hitX, float hitY, float hitZ)
/*  32:    */   {
/*  33: 31 */     this.x = x;
/*  34: 32 */     this.y = y;
/*  35: 33 */     this.z = z;
/*  36: 34 */     this.face = face;
/*  37: 35 */     this.item = item;
/*  38: 36 */     this.hitX = hitX;
/*  39: 37 */     this.hitY = hitY;
/*  40: 38 */     this.hitZ = hitZ;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public PacketUseItemAlt() {}
/*  44:    */   
/*  45:    */   public void writeData(ByteBuf data)
/*  46:    */     throws Exception
/*  47:    */   {
/*  48: 47 */     data.writeInt(this.x);
/*  49: 48 */     data.writeInt(this.y);
/*  50: 49 */     data.writeInt(this.z);
/*  51: 50 */     data.writeByte(this.face);
/*  52: 51 */     writeItemStack(data, this.item);
/*  53: 52 */     data.writeByte((byte)(int)(this.hitX * 16.0F));
/*  54: 53 */     data.writeByte((byte)(int)(this.hitY * 16.0F));
/*  55: 54 */     data.writeByte((byte)(int)(this.hitZ * 16.0F));
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void readData(EntityPlayer player, ByteBuf data)
/*  59:    */   {
/*  60: 60 */     this.x = data.readInt();
/*  61: 61 */     this.y = data.readInt();
/*  62: 62 */     this.z = data.readInt();
/*  63: 63 */     this.face = data.readByte();
/*  64: 64 */     this.item = readItemStack(data);
/*  65: 65 */     this.hitX = (data.readByte() * 0.0625F);
/*  66: 66 */     this.hitY = (data.readByte() * 0.0625F);
/*  67: 67 */     this.hitZ = (data.readByte() * 0.0625F);
/*  68: 68 */     this.player = ((EntityPlayerMP)player);
/*  69:    */   }
/*  70:    */   
/*  71: 71 */   public static final ThreadLocal<Boolean> altPlace = new ThreadLocal();
/*  72:    */   
/*  73:    */   public synchronized void doStuffServer(ChannelHandlerContext ctx)
/*  74:    */   {
/*  75:    */     C08PacketPlayerBlockPlacement placement;
/*  76:    */     try
/*  77:    */     {
/*  78: 79 */       PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
/*  79: 80 */       packetbuffer.writeInt(this.x);
/*  80: 81 */       packetbuffer.writeByte(this.y);
/*  81: 82 */       packetbuffer.writeInt(this.z);
/*  82: 83 */       packetbuffer.writeByte(this.face);
/*  83: 84 */       packetbuffer.writeItemStackToBuffer(this.item);
/*  84: 85 */       packetbuffer.writeByte((int)(this.hitX * 16.0F));
/*  85: 86 */       packetbuffer.writeByte((int)(this.hitY * 16.0F));
/*  86: 87 */       packetbuffer.writeByte((int)(this.hitZ * 16.0F));
/*  87: 88 */       placement = new C08PacketPlayerBlockPlacement();
/*  88: 89 */       placement.readPacketData(packetbuffer);
/*  89:    */     }
/*  90:    */     catch (IOException e)
/*  91:    */     {
/*  92: 91 */       throw Throwables.propagate(e);
/*  93:    */     }
/*  94: 94 */     altPlace.set(Boolean.valueOf(true));
/*  95: 95 */     this.player.playerNetServerHandler.processPlayerBlockPlacement(placement);
/*  96: 96 */     altPlace.set(Boolean.valueOf(false));
/*  97:    */   }
/*  98:    */   
/*  99:    */   @SideOnly(Side.CLIENT)
/* 100:    */   public void doStuffClient() {}
/* 101:    */   
/* 102:    */   public boolean isValidSenderSide(Side properSenderSide)
/* 103:    */   {
/* 104:107 */     return properSenderSide == Side.CLIENT;
/* 105:    */   }
/* 106:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.network.packets.PacketUseItemAlt
 * JD-Core Version:    0.7.0.1
 */