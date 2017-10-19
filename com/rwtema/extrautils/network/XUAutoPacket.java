/*  1:   */ package com.rwtema.extrautils.network;
/*  2:   */ 
/*  3:   */ import io.netty.buffer.ByteBuf;
/*  4:   */ import java.lang.reflect.Field;
/*  5:   */ import net.minecraft.entity.player.EntityPlayer;
/*  6:   */ import net.minecraft.nbt.NBTTagCompound;
/*  7:   */ 
/*  8:   */ public abstract class XUAutoPacket
/*  9:   */   extends XUPacketBase
/* 10:   */ {
/* 11:10 */   boolean init = false;
/* 12:   */   
/* 13:   */   public void getReflectData()
/* 14:   */   {
/* 15:13 */     getClass().getDeclaredFields();
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void writeField(Field f, ByteBuf data)
/* 19:   */     throws IllegalAccessException
/* 20:   */   {
/* 21:17 */     Class type = f.getType();
/* 22:18 */     if (String.class.equals(type)) {
/* 23:19 */       writeString(data, (String)f.get(this));
/* 24:20 */     } else if (Byte.TYPE.equals(type)) {
/* 25:21 */       data.writeByte(((Byte)f.get(this)).byteValue());
/* 26:22 */     } else if (Short.TYPE.equals(type)) {
/* 27:23 */       data.writeShort(((Short)f.get(this)).shortValue());
/* 28:24 */     } else if (Integer.TYPE.equals(type)) {
/* 29:25 */       data.writeInt(((Integer)f.get(this)).intValue());
/* 30:26 */     } else if (Long.TYPE.equals(type)) {
/* 31:27 */       data.writeDouble(((Long)f.get(this)).longValue());
/* 32:28 */     } else if (Float.TYPE.equals(type)) {
/* 33:29 */       data.writeFloat(((Float)f.get(this)).floatValue());
/* 34:30 */     } else if (Double.TYPE.equals(type)) {
/* 35:31 */       data.writeDouble(((Double)f.get(this)).doubleValue());
/* 36:32 */     } else if (NBTTagCompound.class.equals(type)) {
/* 37:33 */       writeNBT(data, (NBTTagCompound)f.get(this));
/* 38:   */     }
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void readField(Field f, ByteBuf data)
/* 42:   */     throws IllegalAccessException
/* 43:   */   {
/* 44:38 */     Class type = f.getType();
/* 45:40 */     if (String.class.equals(type)) {
/* 46:41 */       f.set(this, readString(data));
/* 47:42 */     } else if (Byte.TYPE.equals(type)) {
/* 48:43 */       f.set(this, Byte.valueOf(data.readByte()));
/* 49:44 */     } else if (Short.TYPE.equals(type)) {
/* 50:45 */       f.set(this, Short.valueOf(data.readShort()));
/* 51:46 */     } else if (Integer.TYPE.equals(type)) {
/* 52:47 */       f.set(this, Integer.valueOf(data.readInt()));
/* 53:48 */     } else if (Long.TYPE.equals(type)) {
/* 54:49 */       f.set(this, Long.valueOf(data.readLong()));
/* 55:50 */     } else if (Float.TYPE.equals(type)) {
/* 56:51 */       f.set(this, Float.valueOf(data.readFloat()));
/* 57:52 */     } else if (Double.TYPE.equals(type)) {
/* 58:53 */       f.set(this, Double.valueOf(data.readDouble()));
/* 59:54 */     } else if (NBTTagCompound.class.equals(type)) {
/* 60:55 */       f.set(this, readNBT(data));
/* 61:   */     }
/* 62:   */   }
/* 63:   */   
/* 64:   */   public void writeData(ByteBuf data)
/* 65:   */     throws Exception
/* 66:   */   {}
/* 67:   */   
/* 68:   */   public void readData(EntityPlayer player, ByteBuf data) {}
/* 69:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.network.XUAutoPacket
 * JD-Core Version:    0.7.0.1
 */