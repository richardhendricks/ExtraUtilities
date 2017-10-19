/*  1:   */ package com.rwtema.extrautils.tileentity.generators;
/*  2:   */ 
/*  3:   */ import net.minecraft.util.MathHelper;
/*  4:   */ import net.minecraft.world.World;
/*  5:   */ 
/*  6:   */ public class TileEntityGeneratorSimpleSolar
/*  7:   */   extends TileEntityGenerator
/*  8:   */ {
/*  9: 6 */   public int curLevel = -1;
/* 10:   */   
/* 11:   */   public String getBlurb(double coolDown, double energy)
/* 12:   */   {
/* 13:10 */     return "PowerLevel:\n" + energy;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public int transferLimit()
/* 17:   */   {
/* 18:16 */     return 400;
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected int genLevelForTime()
/* 22:   */   {
/* 23:20 */     float f = this.worldObj.getCelestialAngleRadians(1.0F);
/* 24:22 */     if (f < 3.141593F) {
/* 25:23 */       f += (0.0F - f) * 0.2F;
/* 26:   */     } else {
/* 27:25 */       f += (6.283186F - f) * 0.2F;
/* 28:   */     }
/* 29:28 */     int c = Math.min(40, Math.round(40.0F * MathHelper.cos(f)));
/* 30:29 */     return c > 0 ? c : 0;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public int getMaxCoolDown()
/* 34:   */   {
/* 35:34 */     return 240;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public double genLevel()
/* 39:   */   {
/* 40:39 */     if ((this.curLevel == -1) || (this.worldObj.getTotalWorldTime() % 20L == 0L)) {
/* 41:40 */       this.curLevel = genLevelForTime();
/* 42:   */     }
/* 43:43 */     if (this.curLevel == 0) {
/* 44:44 */       return 0.0D;
/* 45:   */     }
/* 46:47 */     if (!this.worldObj.canBlockSeeTheSky(x(), y() + 1, z())) {
/* 47:48 */       return 0.0D;
/* 48:   */     }
/* 49:51 */     return this.curLevel;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public boolean processInput()
/* 53:   */   {
/* 54:56 */     this.coolDown = getMaxCoolDown();
/* 55:57 */     return true;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public boolean shouldProcess()
/* 59:   */   {
/* 60:62 */     return genLevel() > 0.0D;
/* 61:   */   }
/* 62:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.generators.TileEntityGeneratorSimpleSolar
 * JD-Core Version:    0.7.0.1
 */