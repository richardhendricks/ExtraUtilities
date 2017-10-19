/*   1:    */ package com.rwtema.extrautils.network;
/*   2:    */ 
/*   3:    */ import cpw.mods.fml.relauncher.Side;
/*   4:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   5:    */ import io.netty.buffer.ByteBuf;
/*   6:    */ import io.netty.channel.ChannelHandlerContext;
/*   7:    */ import java.io.IOException;
/*   8:    */ import java.nio.charset.Charset;
/*   9:    */ import net.minecraft.entity.player.EntityPlayer;
/*  10:    */ import net.minecraft.item.Item;
/*  11:    */ import net.minecraft.item.ItemStack;
/*  12:    */ import net.minecraft.nbt.CompressedStreamTools;
/*  13:    */ import net.minecraft.nbt.NBTSizeTracker;
/*  14:    */ import net.minecraft.nbt.NBTTagCompound;
/*  15:    */ import net.minecraft.util.IChatComponent;
/*  16:    */ import net.minecraft.util.IChatComponent.Serializer;
/*  17:    */ import net.minecraft.util.Vec3;
/*  18:    */ 
/*  19:    */ public abstract class XUPacketBase
/*  20:    */ {
/*  21:    */   public abstract void writeData(ByteBuf paramByteBuf)
/*  22:    */     throws Exception;
/*  23:    */   
/*  24:    */   public void writeVec(ByteBuf data, Vec3 vec3)
/*  25:    */   {
/*  26: 25 */     data.writeFloat((float)vec3.xCoord);
/*  27: 26 */     data.writeFloat((float)vec3.yCoord);
/*  28: 27 */     data.writeFloat((float)vec3.zCoord);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void writeChatComponent(ByteBuf data, IChatComponent chatComponent)
/*  32:    */   {
/*  33: 31 */     writeString(data, IChatComponent.Serializer.func_150696_a(chatComponent));
/*  34:    */   }
/*  35:    */   
/*  36:    */   public IChatComponent readChatComponent(ByteBuf data)
/*  37:    */   {
/*  38: 35 */     return IChatComponent.Serializer.func_150699_a(readString(data));
/*  39:    */   }
/*  40:    */   
/*  41:    */   public abstract void readData(EntityPlayer paramEntityPlayer, ByteBuf paramByteBuf);
/*  42:    */   
/*  43:    */   public Vec3 readVec(ByteBuf data)
/*  44:    */   {
/*  45: 41 */     return Vec3.createVectorHelper(data.readFloat(), data.readFloat(), data.readFloat());
/*  46:    */   }
/*  47:    */   
/*  48:    */   public abstract void doStuffServer(ChannelHandlerContext paramChannelHandlerContext);
/*  49:    */   
/*  50:    */   @SideOnly(Side.CLIENT)
/*  51:    */   public abstract void doStuffClient();
/*  52:    */   
/*  53:    */   public abstract boolean isValidSenderSide(Side paramSide);
/*  54:    */   
/*  55:    */   public void writeString(ByteBuf data, String string)
/*  56:    */   {
/*  57: 52 */     byte[] stringData = string.getBytes(Charset.forName("UTF-8"));
/*  58:    */     
/*  59: 54 */     data.writeShort(stringData.length);
/*  60: 55 */     data.writeBytes(stringData);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String readString(ByteBuf data)
/*  64:    */   {
/*  65: 59 */     short length = data.readShort();
/*  66: 60 */     byte[] bytes = new byte[length];
/*  67:    */     
/*  68: 62 */     data.readBytes(bytes);
/*  69: 63 */     return new String(bytes, Charset.forName("UTF-8"));
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void writeNBT(ByteBuf data, NBTTagCompound tag)
/*  73:    */   {
/*  74: 67 */     if (tag == null)
/*  75:    */     {
/*  76: 68 */       data.writeShort(-1);
/*  77: 69 */       return;
/*  78:    */     }
/*  79:    */     try
/*  80:    */     {
/*  81: 72 */       byte[] compressed = CompressedStreamTools.compress(tag);
/*  82:    */       
/*  83: 74 */       data.writeShort(compressed.length);
/*  84: 75 */       data.writeBytes(compressed);
/*  85:    */     }
/*  86:    */     catch (IOException e)
/*  87:    */     {
/*  88: 77 */       e.printStackTrace();
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   public NBTTagCompound readNBT(ByteBuf data)
/*  93:    */   {
/*  94: 82 */     short length = data.readShort();
/*  95: 83 */     if (length <= 0) {
/*  96: 83 */       return null;
/*  97:    */     }
/*  98: 84 */     byte[] bytes = new byte[length];
/*  99: 85 */     data.readBytes(bytes);
/* 100:    */     try
/* 101:    */     {
/* 102: 87 */       return CompressedStreamTools.func_152457_a(bytes, NBTSizeTracker.field_152451_a);
/* 103:    */     }
/* 104:    */     catch (IOException e)
/* 105:    */     {
/* 106: 89 */       e.printStackTrace();
/* 107:    */     }
/* 108: 91 */     return null;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void writeItemStack(ByteBuf data, ItemStack item)
/* 112:    */   {
/* 113: 95 */     if (item == null)
/* 114:    */     {
/* 115: 96 */       data.writeShort(-1);
/* 116:    */     }
/* 117:    */     else
/* 118:    */     {
/* 119: 98 */       data.writeShort(Item.getIdFromItem(item.getItem()));
/* 120: 99 */       data.writeByte(item.stackSize);
/* 121:100 */       data.writeShort(item.getItemDamage());
/* 122:    */       
/* 123:102 */       NBTTagCompound nbttagcompound = null;
/* 124:104 */       if ((item.getItem().isDamageable()) || (item.getItem().getShareTag())) {
/* 125:105 */         nbttagcompound = item.stackTagCompound;
/* 126:    */       }
/* 127:108 */       writeNBT(data, nbttagcompound);
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   public ItemStack readItemStack(ByteBuf data)
/* 132:    */   {
/* 133:113 */     ItemStack itemstack = null;
/* 134:114 */     short id = data.readShort();
/* 135:116 */     if (id >= 0)
/* 136:    */     {
/* 137:118 */       byte stackSize = data.readByte();
/* 138:119 */       short metadata = data.readShort();
/* 139:120 */       itemstack = new ItemStack(Item.getItemById(id), stackSize, metadata);
/* 140:121 */       itemstack.stackTagCompound = readNBT(data);
/* 141:    */     }
/* 142:124 */     return itemstack;
/* 143:    */   }
/* 144:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.network.XUPacketBase
 * JD-Core Version:    0.7.0.1
 */