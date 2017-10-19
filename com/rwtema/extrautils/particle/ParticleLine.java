/*  1:   */ package com.rwtema.extrautils.particle;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.particle.EntityFX;
/*  4:   */ import net.minecraft.client.renderer.Tessellator;
/*  5:   */ import net.minecraft.util.IIcon;
/*  6:   */ import net.minecraft.util.Vec3;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public class ParticleLine
/* 10:   */   extends EntityFX
/* 11:   */ {
/* 12:   */   public final Vec3 start;
/* 13:   */   public final Vec3 end;
/* 14:   */   
/* 15:   */   public ParticleLine(World world, Vec3 start, Vec3 end, float r, float g, float b, IIcon particle)
/* 16:   */   {
/* 17:14 */     super(world, start.xCoord, start.yCoord, start.zCoord);
/* 18:15 */     this.start = start;
/* 19:16 */     this.end = end;
/* 20:17 */     this.noClip = true;
/* 21:18 */     this.particleRed = r;
/* 22:19 */     this.particleGreen = g;
/* 23:20 */     this.particleBlue = b;
/* 24:21 */     this.particleScale = ((float)(0.2000000029802322D + 0.2000000029802322D * Math.random()));
/* 25:22 */     this.particleIcon = particle;
/* 26:23 */     this.particleMaxAge = ((int)(10.0D / (Math.random() * 0.6D + 0.4D)));
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void renderParticle(Tessellator tessellator, float partialTickTime, float rotationX, float rotationXZ, float rotationZ, float rotationYZ, float rotationXY)
/* 30:   */   {
/* 31:28 */     this.particleAlpha = (1.0F - (this.particleAge + partialTickTime) / this.particleMaxAge);
/* 32:   */     
/* 33:   */ 
/* 34:   */ 
/* 35:   */ 
/* 36:33 */     float size = 0.25F * this.particleScale;
/* 37:   */     
/* 38:   */ 
/* 39:36 */     float u1 = this.particleIcon.getMinU();
/* 40:37 */     float u2 = this.particleIcon.getMaxU();
/* 41:38 */     float v1 = this.particleIcon.getMinV();
/* 42:39 */     float v2 = this.particleIcon.getMaxV();
/* 43:   */     
/* 44:41 */     float ax = (float)(this.start.xCoord - interpPosX);
/* 45:42 */     float ay = (float)(this.start.yCoord - interpPosY);
/* 46:43 */     float az = (float)(this.start.zCoord - interpPosZ);
/* 47:   */     
/* 48:45 */     float bx = (float)(this.end.xCoord - interpPosX);
/* 49:46 */     float by = (float)(this.end.yCoord - interpPosY);
/* 50:47 */     float bz = (float)(this.end.zCoord - interpPosZ);
/* 51:   */     
/* 52:49 */     tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
/* 53:   */     
/* 54:51 */     tessellator.addVertexWithUV(bx - rotationX * size - rotationYZ * size, by - rotationXZ * size, bz - rotationZ * size - rotationXY * size, u2, v2);
/* 55:52 */     tessellator.addVertexWithUV(ax - rotationX * size + rotationYZ * size, ay + rotationXZ * size, az - rotationZ * size + rotationXY * size, u2, v1);
/* 56:   */     
/* 57:54 */     tessellator.addVertexWithUV(ax + rotationX * size + rotationYZ * size, ay + rotationXZ * size, az + rotationZ * size + rotationXY * size, u1, v1);
/* 58:55 */     tessellator.addVertexWithUV(bx + rotationX * size - rotationYZ * size, by - rotationXZ * size, bz + rotationZ * size - rotationXY * size, u1, v2);
/* 59:   */   }
/* 60:   */   
/* 61:   */   public int getFXLayer()
/* 62:   */   {
/* 63:61 */     return 1;
/* 64:   */   }
/* 65:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.particle.ParticleLine
 * JD-Core Version:    0.7.0.1
 */