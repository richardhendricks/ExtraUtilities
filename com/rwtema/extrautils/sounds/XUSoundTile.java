/*  1:   */ package com.rwtema.extrautils.sounds;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.relauncher.Side;
/*  4:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  5:   */ import java.lang.ref.WeakReference;
/*  6:   */ import net.minecraft.client.audio.ISound.AttenuationType;
/*  7:   */ import net.minecraft.client.audio.ITickableSound;
/*  8:   */ import net.minecraft.tileentity.TileEntity;
/*  9:   */ import net.minecraft.util.ResourceLocation;
/* 10:   */ 
/* 11:   */ @SideOnly(Side.CLIENT)
/* 12:   */ public class XUSoundTile
/* 13:   */   implements ITickableSound
/* 14:   */ {
/* 15:   */   WeakReference<ISoundTile> sound;
/* 16:13 */   private boolean donePlaying = false;
/* 17:14 */   private float zPosF = 0.0F;
/* 18:15 */   private float xPosF = 0.0F;
/* 19:16 */   private float yPosF = 0.0F;
/* 20:17 */   private float volume = 1.0E-006F;
/* 21:   */   private ResourceLocation location;
/* 22:   */   
/* 23:   */   public XUSoundTile(ISoundTile sound)
/* 24:   */   {
/* 25:21 */     this.sound = new WeakReference(sound);
/* 26:22 */     this.location = sound.getSound();
/* 27:   */   }
/* 28:   */   
/* 29:   */   public boolean func_147667_k()
/* 30:   */   {
/* 31:27 */     return this.donePlaying;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public ResourceLocation func_147650_b()
/* 35:   */   {
/* 36:32 */     return this.location;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public boolean func_147657_c()
/* 40:   */   {
/* 41:37 */     return true;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public int func_147652_d()
/* 45:   */   {
/* 46:42 */     return 0;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public float func_147653_e()
/* 50:   */   {
/* 51:47 */     return this.volume;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public float func_147655_f()
/* 55:   */   {
/* 56:52 */     return 1.0F;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public float func_147649_g()
/* 60:   */   {
/* 61:57 */     return this.xPosF;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public float func_147654_h()
/* 65:   */   {
/* 66:62 */     return this.yPosF;
/* 67:   */   }
/* 68:   */   
/* 69:   */   public float func_147651_i()
/* 70:   */   {
/* 71:67 */     return this.zPosF;
/* 72:   */   }
/* 73:   */   
/* 74:   */   public ISound.AttenuationType func_147656_j()
/* 75:   */   {
/* 76:72 */     return ISound.AttenuationType.LINEAR;
/* 77:   */   }
/* 78:   */   
/* 79:   */   public void update()
/* 80:   */   {
/* 81:77 */     ISoundTile sound = this.sound != null ? (ISoundTile)this.sound.get() : null;
/* 82:79 */     if ((sound == null) || (sound.getTile().isInvalid()))
/* 83:   */     {
/* 84:80 */       this.sound = null;
/* 85:81 */       if (this.volume > 0.0005D) {
/* 86:82 */         this.volume *= 0.9F;
/* 87:   */       } else {
/* 88:84 */         this.donePlaying = true;
/* 89:   */       }
/* 90:   */     }
/* 91:   */     else
/* 92:   */     {
/* 93:86 */       this.xPosF = (sound.getTile().xCoord + 0.5F);
/* 94:87 */       this.yPosF = (sound.getTile().yCoord + 0.5F);
/* 95:88 */       this.zPosF = (sound.getTile().zCoord + 0.5F);
/* 96:90 */       if (sound.shouldSoundPlay())
/* 97:   */       {
/* 98:91 */         if (this.volume < 0.9995000000000001D) {
/* 99:92 */           this.volume = (1.0F - (1.0F - this.volume) * 0.9F);
/* :0:   */         } else {
/* :1:94 */           this.volume = 1.0F;
/* :2:   */         }
/* :3:   */       }
/* :4:96 */       else if (this.volume > 0.0005D) {
/* :5:97 */         this.volume *= 0.9F;
/* :6:   */       } else {
/* :7:99 */         this.volume = 0.0F;
/* :8:   */       }
/* :9:   */     }
/* ;0:   */   }
/* ;1:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.sounds.XUSoundTile
 * JD-Core Version:    0.7.0.1
 */