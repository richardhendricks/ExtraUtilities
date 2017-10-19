/*   1:    */ package com.rwtema.extrautils.particle;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.helper.XURandom;
/*   4:    */ import com.rwtema.extrautils.tileentity.transfernodes.BlockTransferNode;
/*   5:    */ import cpw.mods.fml.relauncher.Side;
/*   6:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   7:    */ import java.util.Random;
/*   8:    */ import net.minecraft.client.particle.EntityFX;
/*   9:    */ import net.minecraft.client.renderer.Tessellator;
/*  10:    */ import net.minecraft.util.IIcon;
/*  11:    */ import net.minecraft.world.World;
/*  12:    */ 
/*  13:    */ @SideOnly(Side.CLIENT)
/*  14:    */ public class ParticleTransferNodes
/*  15:    */   extends EntityFX
/*  16:    */ {
/*  17:    */   float baseScale;
/*  18: 17 */   public static Random rand = ;
/*  19:    */   
/*  20:    */   public static double offset()
/*  21:    */   {
/*  22: 20 */     return rand.nextGaussian() * 0.0125D;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public int getFXLayer()
/*  26:    */   {
/*  27: 25 */     return 1;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public ParticleTransferNodes(World par1World, double par2, double par4, double par6, double r, double g, double b)
/*  31:    */   {
/*  32: 29 */     super(par1World, par2 + offset(), par4 + offset(), par6 + offset(), 0.0D, 0.0D, 0.0D);
/*  33: 30 */     this.particleIcon = BlockTransferNode.particle;
/*  34: 31 */     this.motionX *= 0.2D;
/*  35: 32 */     this.motionY *= 0.2D;
/*  36: 33 */     this.motionZ *= 0.2D;
/*  37: 34 */     for (int i = 0; i < 8; i++)
/*  38:    */     {
/*  39: 35 */       float f4 = (float)Math.random() * 0.4F + 0.6F;
/*  40: 36 */       this.particleRed = (this.col[i][0] = (float)((Math.random() * 0.2000000029802322D + 0.800000011920929D) * r * f4));
/*  41: 37 */       this.particleGreen = (this.col[i][1] = (float)((Math.random() * 0.2000000029802322D + 0.800000011920929D) * g * f4));
/*  42: 38 */       this.particleBlue = (this.col[i][2] = (float)((Math.random() * 0.2000000029802322D + 0.800000011920929D) * b * f4));
/*  43:    */     }
/*  44: 40 */     this.particleScale = 0.5F;
/*  45: 41 */     this.baseScale = this.particleScale;
/*  46: 42 */     this.particleMaxAge = ((int)(10.0D / (Math.random() * 0.2D + 0.6D)));
/*  47: 43 */     this.noClip = true;
/*  48:    */   }
/*  49:    */   
/*  50: 46 */   float[][] col = new float[8][3];
/*  51: 47 */   private static final int[][] dirs = { { -1, -1, -1 }, { -1, -1, 1 }, { 1, -1, -1 }, { 1, -1, 1 }, { -1, 1, -1 }, { -1, 1, 1 }, { 1, 1, -1 }, { 1, 1, 1 } };
/*  52:    */   
/*  53:    */   public void renderParticle(Tessellator par1Tessellator, float x, float y, float z, float r, float g, float b)
/*  54:    */   {
/*  55: 59 */     float f6 = 1.0F - (this.particleAge + x) / this.particleMaxAge;
/*  56: 61 */     if (f6 < 0.0F) {
/*  57: 62 */       f6 = 0.0F;
/*  58:    */     }
/*  59: 65 */     if (f6 > 1.0F) {
/*  60: 66 */       f6 = 1.0F;
/*  61:    */     }
/*  62: 69 */     this.particleScale = (this.baseScale * (1.0F - f6 * 0.5F) * Math.min(1.0F, 2.0F * f6));
/*  63: 70 */     float h = 0.125F + (1.0F - f6) * 0.075F;
/*  64: 71 */     for (int i = 0; i < 8; i++) {
/*  65: 72 */       renderParticles(par1Tessellator, x, y, z, r, g, b, h * dirs[i][0], h * dirs[i][1], h * dirs[i][2], this.col[i]);
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void renderParticles(Tessellator par1Tessellator, float x, float y, float z, float r, float g, float b, float dx, float dy, float dz, float[] cols)
/*  70:    */   {
/*  71: 76 */     float f6 = this.particleTextureIndexX / 16.0F;
/*  72: 77 */     float f7 = f6 + 0.0624375F;
/*  73: 78 */     float f8 = this.particleTextureIndexY / 16.0F;
/*  74: 79 */     float f9 = f8 + 0.0624375F;
/*  75: 80 */     float f10 = 0.1F * this.particleScale;
/*  76: 82 */     if (this.particleIcon != null)
/*  77:    */     {
/*  78: 83 */       f6 = this.particleIcon.getMinU();
/*  79: 84 */       f7 = this.particleIcon.getMaxU();
/*  80: 85 */       f8 = this.particleIcon.getMinV();
/*  81: 86 */       f9 = this.particleIcon.getMaxV();
/*  82:    */     }
/*  83: 89 */     double f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * x - interpPosX) + dx;
/*  84: 90 */     double f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * x - interpPosY) + dy;
/*  85: 91 */     double f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * x - interpPosZ) + dz;
/*  86: 92 */     par1Tessellator.setColorRGBA_F(cols[0], cols[1], cols[2], this.particleAlpha);
/*  87: 93 */     par1Tessellator.addVertexWithUV(f11 - y * f10 - g * f10, f12 - z * f10, f13 - r * f10 - b * f10, f7, f9);
/*  88: 94 */     par1Tessellator.addVertexWithUV(f11 - y * f10 + g * f10, f12 + z * f10, f13 - r * f10 + b * f10, f7, f8);
/*  89: 95 */     par1Tessellator.addVertexWithUV(f11 + y * f10 + g * f10, f12 + z * f10, f13 + r * f10 + b * f10, f6, f8);
/*  90: 96 */     par1Tessellator.addVertexWithUV(f11 + y * f10 - g * f10, f12 - z * f10, f13 + r * f10 - b * f10, f6, f9);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void onUpdate()
/*  94:    */   {
/*  95:101 */     this.prevPosX = this.posX;
/*  96:102 */     this.prevPosY = this.posY;
/*  97:103 */     this.prevPosZ = this.posZ;
/*  98:105 */     if (this.particleAge++ >= this.particleMaxAge) {
/*  99:106 */       setDead();
/* 100:    */     }
/* 101:    */   }
/* 102:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.particle.ParticleTransferNodes
 * JD-Core Version:    0.7.0.1
 */