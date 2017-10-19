/*  1:   */ package com.rwtema.extrautils.network.packets;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtilsMod;
/*  4:   */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*  5:   */ import com.rwtema.extrautils.network.XUPacketBase;
/*  6:   */ import com.rwtema.extrautils.tileentity.TileEntityTradingPost;
/*  7:   */ import cpw.mods.fml.relauncher.Side;
/*  8:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  9:   */ import io.netty.buffer.ByteBuf;
/* 10:   */ import io.netty.channel.ChannelHandlerContext;
/* 11:   */ import net.minecraft.entity.player.EntityPlayer;
/* 12:   */ import net.minecraft.nbt.NBTTagCompound;
/* 13:   */ import net.minecraft.tileentity.TileEntity;
/* 14:   */ import net.minecraft.village.MerchantRecipeList;
/* 15:   */ import net.minecraft.world.World;
/* 16:   */ 
/* 17:   */ public class PacketVillager
/* 18:   */   extends XUPacketBase
/* 19:   */ {
/* 20:   */   public int x;
/* 21:   */   public int y;
/* 22:   */   public int z;
/* 23:   */   public NBTTagCompound tag;
/* 24:   */   
/* 25:   */   public PacketVillager() {}
/* 26:   */   
/* 27:   */   public PacketVillager(int x, int y, int z, NBTTagCompound tag)
/* 28:   */   {
/* 29:25 */     this.x = x;
/* 30:26 */     this.y = y;
/* 31:27 */     this.z = z;
/* 32:28 */     this.tag = tag;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void writeData(ByteBuf data)
/* 36:   */     throws Exception
/* 37:   */   {
/* 38:33 */     data.writeInt(this.x);
/* 39:34 */     data.writeInt(this.y);
/* 40:35 */     data.writeInt(this.z);
/* 41:36 */     writeNBT(data, this.tag);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void readData(EntityPlayer player, ByteBuf data)
/* 45:   */   {
/* 46:41 */     this.x = data.readInt();
/* 47:42 */     this.y = data.readInt();
/* 48:43 */     this.z = data.readInt();
/* 49:44 */     this.tag = readNBT(data);
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void doStuffServer(ChannelHandlerContext ctx) {}
/* 53:   */   
/* 54:   */   @SideOnly(Side.CLIENT)
/* 55:   */   public void doStuffClient()
/* 56:   */   {
/* 57:57 */     if (this.tag == null) {
/* 58:58 */       return;
/* 59:   */     }
/* 60:60 */     if (!this.tag.hasKey("player_id")) {
/* 61:61 */       return;
/* 62:   */     }
/* 63:64 */     if (!this.tag.hasKey("n")) {
/* 64:65 */       return;
/* 65:   */     }
/* 66:68 */     TileEntity tile = ExtraUtilsMod.proxy.getClientWorld().getTileEntity(this.x, this.y, this.z);
/* 67:69 */     if (!(tile instanceof TileEntityTradingPost)) {
/* 68:70 */       return;
/* 69:   */     }
/* 70:72 */     TileEntityTradingPost t2 = (TileEntityTradingPost)tile;
/* 71:   */     
/* 72:74 */     int n = this.tag.getInteger("n");
/* 73:75 */     t2.ids = new int[n];
/* 74:76 */     t2.data = new MerchantRecipeList[n];
/* 75:79 */     for (int i = 0; i < n; i++)
/* 76:   */     {
/* 77:80 */       t2.ids[i] = this.tag.getInteger("i" + i);
/* 78:81 */       t2.data[i] = new MerchantRecipeList(this.tag.getCompoundTag("t" + i));
/* 79:   */     }
/* 80:84 */     ExtraUtilsMod.proxy.getClientPlayer().openGui(ExtraUtilsMod.instance, 0, ExtraUtilsMod.proxy.getClientWorld(), this.x, this.y, this.z);
/* 81:   */   }
/* 82:   */   
/* 83:   */   public boolean isValidSenderSide(Side properSenderSide)
/* 84:   */   {
/* 85:89 */     return properSenderSide == Side.SERVER;
/* 86:   */   }
/* 87:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.network.packets.PacketVillager
 * JD-Core Version:    0.7.0.1
 */