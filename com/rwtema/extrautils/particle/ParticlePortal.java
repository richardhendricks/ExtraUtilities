/*  1:   */ package com.rwtema.extrautils.particle;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.block.BlockPortal;
/*  4:   */ import net.minecraft.client.particle.EntityFX;
/*  5:   */ import net.minecraft.client.renderer.Tessellator;
/*  6:   */ import net.minecraft.util.IIcon;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public class ParticlePortal
/* 10:   */   extends EntityFX
/* 11:   */ {
/* 12:   */   public final double startX;
/* 13:   */   public final double startY;
/* 14:   */   public final double startZ;
/* 15:   */   
/* 16:   */   public ParticlePortal(World world, double x, double y, double z, float r, float g, float b)
/* 17:   */   {
/* 18:14 */     super(world, x, y, z);
/* 19:15 */     this.startX = x;
/* 20:16 */     this.startY = y;
/* 21:17 */     this.startZ = z;
/* 22:18 */     this.noClip = true;
/* 23:19 */     this.particleRed = r;
/* 24:20 */     this.particleGreen = g;
/* 25:21 */     this.particleBlue = b;
/* 26:22 */     this.particleScale = ((float)(0.2000000029802322D + 0.2000000029802322D * Math.random()));
/* 27:23 */     this.motionY = (0.2000000029802322D * (1.0D + Math.random()) / 4.0D);
/* 28:24 */     this.particleIcon = BlockPortal.particle;
/* 29:25 */     this.particleMaxAge = ((int)(80.0D / (Math.random() * 0.6D + 0.4D)));
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void renderParticle(Tessellator tessellator, float partialTickTime, float rotationX, float rotationXZ, float rotationZ, float rotationYZ, float rotationXY)
/* 33:   */   {
/* 34:30 */     this.particleAlpha = (1.0F - (this.particleAge + partialTickTime) / this.particleMaxAge);
/* 35:31 */     float u1 = this.particleTextureIndexX / 16.0F;
/* 36:32 */     float u2 = u1 + 0.0624375F;
/* 37:33 */     float v1 = this.particleTextureIndexY / 16.0F;
/* 38:34 */     float v2 = v1 + 0.0624375F;
/* 39:35 */     float size = 0.1F * this.particleScale;
/* 40:37 */     if (this.particleIcon != null)
/* 41:   */     {
/* 42:38 */       u1 = this.particleIcon.getMinU();
/* 43:39 */       u2 = this.particleIcon.getMaxU();
/* 44:40 */       v1 = this.particleIcon.getMinV();
/* 45:41 */       v2 = this.particleIcon.getMaxV();
/* 46:   */     }
/* 47:44 */     float x = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTickTime - interpPosX);
/* 48:45 */     float y = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTickTime - interpPosY);
/* 49:46 */     float z = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTickTime - interpPosZ);
/* 50:   */     
/* 51:48 */     float sx = (float)(this.startX - interpPosX);
/* 52:49 */     float sy = (float)(this.startY - interpPosY);
/* 53:50 */     float sz = (float)(this.startZ - interpPosZ);
/* 54:51 */     tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
/* 55:52 */     tessellator.addVertexWithUV(x - rotationX * size + rotationYZ * size, y + rotationXZ * size, z - rotationZ * size + rotationXY * size, u2, v1);
/* 56:53 */     tessellator.addVertexWithUV(x + rotationX * size + rotationYZ * size, y + rotationXZ * size, z + rotationZ * size + rotationXY * size, u1, v1);
/* 57:54 */     tessellator.addVertexWithUV(sx + rotationX * size - rotationYZ * size, sy - rotationXZ * size, sz + rotationZ * size - rotationXY * size, u1, v2);
/* 58:55 */     tessellator.addVertexWithUV(sx - rotationX * size - rotationYZ * size, sy - rotationXZ * size, sz - rotationZ * size - rotationXY * size, u2, v2);
/* 59:   */   }
/* 60:   */   
/* 61:   */   public int getFXLayer()
/* 62:   */   {
/* 63:60 */     return 1;
/* 64:   */   }
/* 65:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.particle.ParticlePortal
 * JD-Core Version:    0.7.0.1
 */