/*  1:   */ package com.rwtema.extrautils.network.packets;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtilsMod;
/*  4:   */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*  5:   */ import com.rwtema.extrautils.dynamicgui.DynamicContainer;
/*  6:   */ import com.rwtema.extrautils.network.XUPacketBase;
/*  7:   */ import cpw.mods.fml.relauncher.Side;
/*  8:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  9:   */ import io.netty.buffer.ByteBuf;
/* 10:   */ import io.netty.channel.ChannelHandlerContext;
/* 11:   */ import net.minecraft.entity.player.EntityPlayer;
/* 12:   */ import net.minecraft.inventory.Container;
/* 13:   */ import net.minecraft.nbt.NBTTagCompound;
/* 14:   */ 
/* 15:   */ public class PacketGUIWidget
/* 16:   */   extends XUPacketBase
/* 17:   */ {
/* 18:   */   int window_id;
/* 19:   */   NBTTagCompound tag;
/* 20:   */   
/* 21:   */   public PacketGUIWidget() {}
/* 22:   */   
/* 23:   */   public PacketGUIWidget(int window_id, NBTTagCompound tag)
/* 24:   */   {
/* 25:21 */     this.tag = tag;
/* 26:22 */     this.window_id = window_id;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void writeData(ByteBuf data)
/* 30:   */   {
/* 31:28 */     data.writeInt(this.window_id);
/* 32:29 */     writeNBT(data, this.tag);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void readData(EntityPlayer player, ByteBuf data)
/* 36:   */   {
/* 37:   */     try
/* 38:   */     {
/* 39:37 */       this.window_id = data.readInt();
/* 40:38 */       this.tag = readNBT(data);
/* 41:   */     }
/* 42:   */     catch (Exception e)
/* 43:   */     {
/* 44:40 */       e.printStackTrace();
/* 45:   */     }
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void doStuffServer(ChannelHandlerContext ctx) {}
/* 49:   */   
/* 50:   */   @SideOnly(Side.CLIENT)
/* 51:   */   public void doStuffClient()
/* 52:   */   {
/* 53:53 */     if ((this.tag != null) && 
/* 54:54 */       (this.window_id != 0) && (this.window_id == ExtraUtilsMod.proxy.getClientPlayer().openContainer.windowId)) {
/* 55:   */       try
/* 56:   */       {
/* 57:56 */         ((DynamicContainer)ExtraUtilsMod.proxy.getClientPlayer().openContainer).recieveDescriptionPacket(this.tag);
/* 58:   */       }
/* 59:   */       catch (Exception e)
/* 60:   */       {
/* 61:58 */         e.printStackTrace();
/* 62:   */       }
/* 63:   */     }
/* 64:   */   }
/* 65:   */   
/* 66:   */   public boolean isValidSenderSide(Side properSenderSide)
/* 67:   */   {
/* 68:66 */     return properSenderSide == Side.SERVER;
/* 69:   */   }
/* 70:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.network.packets.PacketGUIWidget
 * JD-Core Version:    0.7.0.1
 */