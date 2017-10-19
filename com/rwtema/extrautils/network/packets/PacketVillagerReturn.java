/*  1:   */ package com.rwtema.extrautils.network.packets;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.network.XUPacketBase;
/*  4:   */ import com.rwtema.extrautils.tileentity.TileEntityTradingPost;
/*  5:   */ import cpw.mods.fml.relauncher.Side;
/*  6:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  7:   */ import io.netty.buffer.ByteBuf;
/*  8:   */ import io.netty.channel.ChannelHandlerContext;
/*  9:   */ import net.minecraft.entity.Entity;
/* 10:   */ import net.minecraft.entity.IMerchant;
/* 11:   */ import net.minecraft.entity.player.EntityPlayer;
/* 12:   */ import net.minecraft.entity.player.EntityPlayerMP;
/* 13:   */ import net.minecraft.util.ChatComponentText;
/* 14:   */ import net.minecraft.world.World;
/* 15:   */ import net.minecraft.world.WorldProvider;
/* 16:   */ 
/* 17:   */ public class PacketVillagerReturn
/* 18:   */   extends XUPacketBase
/* 19:   */ {
/* 20:   */   public int merchantId;
/* 21:   */   public int world;
/* 22:   */   public int x;
/* 23:   */   public int y;
/* 24:   */   public int z;
/* 25:   */   EntityPlayerMP p;
/* 26:   */   World theworld;
/* 27:   */   
/* 28:   */   public PacketVillagerReturn() {}
/* 29:   */   
/* 30:   */   public PacketVillagerReturn(int merchantId, int world, int x, int y, int z)
/* 31:   */   {
/* 32:24 */     this.merchantId = merchantId;
/* 33:25 */     this.world = world;
/* 34:26 */     this.x = x;
/* 35:27 */     this.y = y;
/* 36:28 */     this.z = z;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void writeData(ByteBuf data)
/* 40:   */     throws Exception
/* 41:   */   {
/* 42:33 */     data.writeInt(this.merchantId);
/* 43:34 */     data.writeInt(this.world);
/* 44:35 */     data.writeInt(this.x);
/* 45:36 */     data.writeInt(this.y);
/* 46:37 */     data.writeInt(this.z);
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void readData(EntityPlayer player, ByteBuf data)
/* 50:   */   {
/* 51:45 */     this.merchantId = data.readInt();
/* 52:46 */     this.world = data.readInt();
/* 53:47 */     this.x = data.readInt();
/* 54:48 */     this.y = data.readInt();
/* 55:49 */     this.z = data.readInt();
/* 56:50 */     this.p = ((EntityPlayerMP)player);
/* 57:51 */     this.theworld = this.p.getEntityWorld();
/* 58:   */   }
/* 59:   */   
/* 60:   */   public void doStuffServer(ChannelHandlerContext ctx)
/* 61:   */   {
/* 62:   */     try
/* 63:   */     {
/* 64:57 */       if (this.world != this.theworld.provider.dimensionId) {
/* 65:58 */         return;
/* 66:   */       }
/* 67:60 */       if (this.p.getDistance(this.x, this.y, this.z) > 6.0D) {
/* 68:61 */         return;
/* 69:   */       }
/* 70:62 */       if ((this.theworld.getEntityByID(this.merchantId) instanceof IMerchant))
/* 71:   */       {
/* 72:63 */         Entity villager = this.theworld.getEntityByID(this.merchantId);
/* 73:64 */         IMerchant trader = (IMerchant)this.theworld.getEntityByID(this.merchantId);
/* 74:66 */         if (((Math.abs(villager.posX - this.x) < TileEntityTradingPost.maxRange ? 1 : 0) & (Math.abs(villager.posZ - this.z) < TileEntityTradingPost.maxRange ? 1 : 0)) != 0)
/* 75:   */         {
/* 76:68 */           if (!villager.isEntityAlive()) {
/* 77:69 */             PacketTempChat.sendChat(this.p, new ChatComponentText("Villager has died"));
/* 78:70 */           } else if (trader.getCustomer() != null) {
/* 79:71 */             PacketTempChat.sendChat(this.p, new ChatComponentText("Villager is busy"));
/* 80:   */           } else {
/* 81:73 */             this.p.interactWith(villager);
/* 82:   */           }
/* 83:   */         }
/* 84:   */         else {
/* 85:76 */           PacketTempChat.sendChat(this.p, new ChatComponentText("Villager is no longer in range"));
/* 86:   */         }
/* 87:   */       }
/* 88:   */       else
/* 89:   */       {
/* 90:79 */         PacketTempChat.sendChat(this.p, new ChatComponentText("Villager can no longer be found"));
/* 91:   */       }
/* 92:   */     }
/* 93:   */     catch (Exception exception4)
/* 94:   */     {
/* 95:83 */       exception4.printStackTrace();
/* 96:   */     }
/* 97:   */   }
/* 98:   */   
/* 99:   */   @SideOnly(Side.CLIENT)
/* :0:   */   public void doStuffClient() {}
/* :1:   */   
/* :2:   */   public boolean isValidSenderSide(Side properSenderSide)
/* :3:   */   {
/* :4:95 */     return properSenderSide == Side.CLIENT;
/* :5:   */   }
/* :6:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.network.packets.PacketVillagerReturn
 * JD-Core Version:    0.7.0.1
 */