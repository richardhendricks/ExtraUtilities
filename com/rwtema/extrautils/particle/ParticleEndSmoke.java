/*  1:   */ package com.rwtema.extrautils.particle;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.particle.EntityReddustFX;
/*  4:   */ import net.minecraft.world.World;
/*  5:   */ 
/*  6:   */ public class ParticleEndSmoke
/*  7:   */   extends EntityReddustFX
/*  8:   */ {
/*  9: 8 */   public final int textureIndex = 7;
/* 10:   */   
/* 11:   */   public ParticleEndSmoke(World p_i1223_1_, double p_i1223_2_, double p_i1223_4_, double p_i1223_6_, float p_i1223_8_, float p_i1223_9_, float p_i1223_10_)
/* 12:   */   {
/* 13:11 */     super(p_i1223_1_, p_i1223_2_, p_i1223_4_, p_i1223_6_, p_i1223_8_, p_i1223_9_, p_i1223_10_);
/* 14:12 */     setParticleTextureIndex(7);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public ParticleEndSmoke(World p_i1224_1_, double p_i1224_2_, double p_i1224_4_, double p_i1224_6_, float p_i1224_8_, float p_i1224_9_, float p_i1224_10_, float p_i1224_11_)
/* 18:   */   {
/* 19:16 */     super(p_i1224_1_, p_i1224_2_, p_i1224_4_, p_i1224_6_, p_i1224_8_, p_i1224_9_, p_i1224_10_, p_i1224_11_);
/* 20:17 */     setParticleTextureIndex(7);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void onUpdate()
/* 24:   */   {
/* 25:22 */     super.onUpdate();
/* 26:23 */     setParticleTextureIndex(7);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public int getBrightnessForRender(float p_70070_1_)
/* 30:   */   {
/* 31:28 */     int i = super.getBrightnessForRender(p_70070_1_);
/* 32:29 */     float f1 = this.particleAge / this.particleMaxAge;
/* 33:30 */     f1 *= f1;
/* 34:31 */     f1 *= f1;
/* 35:32 */     int j = i & 0xFF;
/* 36:33 */     int k = i >> 16 & 0xFF;
/* 37:34 */     k += (int)(f1 * 15.0F * 16.0F);
/* 38:36 */     if (k > 240) {
/* 39:38 */       k = 240;
/* 40:   */     }
/* 41:41 */     return j | k << 16;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public float getBrightness(float p_70013_1_)
/* 45:   */   {
/* 46:49 */     float f1 = super.getBrightness(p_70013_1_);
/* 47:50 */     float f2 = this.particleAge / this.particleMaxAge;
/* 48:51 */     f2 = f2 * f2 * f2 * f2;
/* 49:52 */     return f1 * (1.0F - f2) + f2;
/* 50:   */   }
/* 51:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.particle.ParticleEndSmoke
 * JD-Core Version:    0.7.0.1
 */