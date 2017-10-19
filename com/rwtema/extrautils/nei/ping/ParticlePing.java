/*  1:   */ package com.rwtema.extrautils.nei.ping;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.helper.XURandom;
/*  4:   */ import java.util.Random;
/*  5:   */ import net.minecraft.client.multiplayer.WorldClient;
/*  6:   */ import net.minecraft.client.particle.EntityReddustFX;
/*  7:   */ import net.minecraft.client.renderer.Tessellator;
/*  8:   */ import net.minecraft.world.ChunkPosition;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ import org.lwjgl.opengl.GL11;
/* 11:   */ 
/* 12:   */ public class ParticlePing
/* 13:   */   extends EntityReddustFX
/* 14:   */ {
/* 15:14 */   public static final Random RANDOM = ;
/* 16:   */   public static final float r = 1.0F;
/* 17:   */   public static final float g = 1.0F;
/* 18:   */   public static final float b = 1.0F;
/* 19:   */   
/* 20:   */   public ParticlePing(World world, int x, int y, int z)
/* 21:   */   {
/* 22:20 */     super(world, x + randOffset(), y + randOffset(), z + randOffset(), 1.0F, 1.0F, 1.0F);
/* 23:   */     
/* 24:   */ 
/* 25:   */ 
/* 26:   */ 
/* 27:25 */     this.noClip = true;
/* 28:26 */     this.particleMaxAge *= 10;
/* 29:27 */     this.motionX *= 0.1D;
/* 30:28 */     this.motionY *= 0.1D;
/* 31:29 */     this.motionZ *= 0.1D;
/* 32:30 */     this.particleScale *= 2.0F;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public static double randOffset()
/* 36:   */   {
/* 37:34 */     return 0.5D + (RANDOM.nextDouble() - 0.5D) * RANDOM.nextDouble();
/* 38:   */   }
/* 39:   */   
/* 40:   */   public ParticlePing(WorldClient theWorld, ChunkPosition p)
/* 41:   */   {
/* 42:38 */     this(theWorld, p.chunkPosX, p.chunkPosY, p.chunkPosZ);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void renderParticle(Tessellator tessellator, float partialTickTime, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
/* 46:   */   {
/* 47:43 */     super.renderParticle(tessellator, partialTickTime, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
/* 48:44 */     tessellator.draw();
/* 49:   */     
/* 50:46 */     GL11.glDisable(2929);
/* 51:47 */     tessellator.startDrawingQuads();
/* 52:48 */     super.renderParticle(tessellator, partialTickTime, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
/* 53:49 */     tessellator.draw();
/* 54:50 */     GL11.glEnable(2929);
/* 55:   */     
/* 56:52 */     tessellator.startDrawingQuads();
/* 57:   */   }
/* 58:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.nei.ping.ParticlePing
 * JD-Core Version:    0.7.0.1
 */