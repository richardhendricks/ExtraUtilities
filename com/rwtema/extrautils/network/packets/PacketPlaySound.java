/*  1:   */ package com.rwtema.extrautils.network.packets;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.network.XUPacketBase;
/*  4:   */ import com.rwtema.extrautils.sounds.Sounds;
/*  5:   */ import cpw.mods.fml.relauncher.Side;
/*  6:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  7:   */ import io.netty.buffer.ByteBuf;
/*  8:   */ import io.netty.channel.ChannelHandlerContext;
/*  9:   */ import net.minecraft.client.audio.PositionedSoundRecord;
/* 10:   */ import net.minecraft.entity.player.EntityPlayer;
/* 11:   */ import net.minecraft.util.ResourceLocation;
/* 12:   */ 
/* 13:   */ public class PacketPlaySound
/* 14:   */   extends XUPacketBase
/* 15:   */ {
/* 16:   */   public short sound_id;
/* 17:   */   public float x;
/* 18:   */   public float y;
/* 19:   */   public float z;
/* 20:   */   
/* 21:   */   public PacketPlaySound()
/* 22:   */   {
/* 23:20 */     this.sound_id = -1;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public PacketPlaySound(short sound_id, float x, float y, float z)
/* 27:   */   {
/* 28:24 */     this.sound_id = sound_id;
/* 29:25 */     this.x = x;
/* 30:26 */     this.y = y;
/* 31:27 */     this.z = z;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void writeData(ByteBuf data)
/* 35:   */     throws Exception
/* 36:   */   {
/* 37:32 */     data.writeByte(this.sound_id);
/* 38:33 */     data.writeFloat(this.x);
/* 39:34 */     data.writeFloat(this.y);
/* 40:35 */     data.writeFloat(this.z);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void readData(EntityPlayer player, ByteBuf data)
/* 44:   */   {
/* 45:40 */     this.sound_id = data.readUnsignedByte();
/* 46:41 */     this.x = data.readFloat();
/* 47:42 */     this.y = data.readFloat();
/* 48:43 */     this.z = data.readFloat();
/* 49:   */   }
/* 50:   */   
/* 51:51 */   ResourceLocation[] sounds = { new ResourceLocation("extrautils", "hostile.creepy_laugh") };
/* 52:   */   
/* 53:   */   public void doStuffServer(ChannelHandlerContext ctx) {}
/* 54:   */   
/* 55:   */   @SideOnly(Side.CLIENT)
/* 56:   */   public void doStuffClient()
/* 57:   */   {
/* 58:56 */     if ((this.sound_id < 0) || (this.sound_id >= this.sounds.length)) {
/* 59:57 */       return;
/* 60:   */     }
/* 61:59 */     Sounds.tryAddSound(PositionedSoundRecord.func_147675_a(this.sounds[this.sound_id], this.x, this.y, this.z));
/* 62:   */   }
/* 63:   */   
/* 64:   */   public boolean isValidSenderSide(Side properSenderSide)
/* 65:   */   {
/* 66:64 */     return properSenderSide == Side.SERVER;
/* 67:   */   }
/* 68:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.network.packets.PacketPlaySound
 * JD-Core Version:    0.7.0.1
 */