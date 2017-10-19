/*  1:   */ package com.rwtema.extrautils.network.packets;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtilsMod;
/*  4:   */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*  5:   */ import com.rwtema.extrautils.network.XUPacketBase;
/*  6:   */ import cpw.mods.fml.relauncher.Side;
/*  7:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  8:   */ import io.netty.buffer.ByteBuf;
/*  9:   */ import io.netty.channel.ChannelHandlerContext;
/* 10:   */ import net.minecraft.entity.player.EntityPlayer;
/* 11:   */ import net.minecraft.nbt.NBTTagCompound;
/* 12:   */ 
/* 13:   */ public class PacketRain
/* 14:   */   extends XUPacketBase
/* 15:   */ {
/* 16:   */   private boolean shouldRain;
/* 17:   */   
/* 18:   */   public PacketRain() {}
/* 19:   */   
/* 20:   */   public PacketRain(boolean shouldRain)
/* 21:   */   {
/* 22:20 */     this.shouldRain = shouldRain;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void writeData(ByteBuf data)
/* 26:   */   {
/* 27:25 */     data.writeBoolean(this.shouldRain);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void readData(EntityPlayer player, ByteBuf data)
/* 31:   */   {
/* 32:30 */     this.shouldRain = data.readBoolean();
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void doStuffServer(ChannelHandlerContext ctx) {}
/* 36:   */   
/* 37:   */   @SideOnly(Side.CLIENT)
/* 38:   */   public void doStuffClient()
/* 39:   */   {
/* 40:40 */     NBTTagCompound t = new NBTTagCompound();
/* 41:   */     
/* 42:42 */     EntityPlayer player = ExtraUtilsMod.proxy.getClientPlayer();
/* 43:43 */     if (player.getEntityData().hasKey("PlayerPersisted")) {
/* 44:44 */       t = player.getEntityData().getCompoundTag("PlayerPersisted");
/* 45:   */     } else {
/* 46:46 */       player.getEntityData().setTag("PlayerPersisted", t);
/* 47:   */     }
/* 48:49 */     t.setBoolean("ExtraUtilities|Rain", this.shouldRain);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public boolean isValidSenderSide(Side properSenderSide)
/* 52:   */   {
/* 53:54 */     return properSenderSide == Side.SERVER;
/* 54:   */   }
/* 55:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.network.packets.PacketRain
 * JD-Core Version:    0.7.0.1
 */