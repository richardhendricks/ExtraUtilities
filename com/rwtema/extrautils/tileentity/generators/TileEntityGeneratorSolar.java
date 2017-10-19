/*  1:   */ package com.rwtema.extrautils.tileentity.generators;
/*  2:   */ 
/*  3:   */ import net.minecraft.world.World;
/*  4:   */ 
/*  5:   */ public class TileEntityGeneratorSolar
/*  6:   */   extends TileEntityGeneratorSimpleSolar
/*  7:   */ {
/*  8: 4 */   public int curLevel = -1;
/*  9:   */   
/* 10:   */   public String getBlurb(double coolDown, double energy)
/* 11:   */   {
/* 12: 8 */     if (isPowered())
/* 13:   */     {
/* 14: 9 */       if (coolDown == 0.0D) {
/* 15:10 */         return "Transmitting: Deactivate redstone signal to resume charging";
/* 16:   */       }
/* 17:12 */       return "Time Remaining until transmittion starts:\n" + getCoolDownString(coolDown);
/* 18:   */     }
/* 19:16 */     if (coolDown == 0.0D) {
/* 20:17 */       return "\n\n\nCharging: Apply redstone signal to transmit energy";
/* 21:   */     }
/* 22:19 */     return "PowerLevel:\n" + energy + "\n\nCharging: Apply redstone signal to transmit energy";
/* 23:   */   }
/* 24:   */   
/* 25:   */   public int transferLimit()
/* 26:   */   {
/* 27:25 */     return 20 + (int)(genLevel() / 2.0D);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public int getMaxCoolDown()
/* 31:   */   {
/* 32:30 */     return 200;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public double genLevel()
/* 36:   */   {
/* 37:35 */     if ((this.curLevel == -1) || (this.worldObj.getTotalWorldTime() % 20L == 0L)) {
/* 38:36 */       this.curLevel = genLevelForTime();
/* 39:   */     }
/* 40:39 */     if (this.curLevel == 0) {
/* 41:40 */       return 0.0D;
/* 42:   */     }
/* 43:43 */     if (!this.worldObj.canBlockSeeTheSky(this.xCoord, this.yCoord + 1, this.zCoord)) {
/* 44:44 */       return 0.0D;
/* 45:   */     }
/* 46:46 */     if (isPowered()) {
/* 47:47 */       return 0.0D;
/* 48:   */     }
/* 49:49 */     return this.curLevel;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public boolean processInput()
/* 53:   */   {
/* 54:54 */     if (genLevel() > 0.0D)
/* 55:   */     {
/* 56:55 */       this.coolDown = getMaxCoolDown();
/* 57:56 */       return true;
/* 58:   */     }
/* 59:59 */     return false;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public boolean shouldTransmit()
/* 63:   */   {
/* 64:64 */     return this.coolDown == 0.0D;
/* 65:   */   }
/* 66:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.generators.TileEntityGeneratorSolar
 * JD-Core Version:    0.7.0.1
 */