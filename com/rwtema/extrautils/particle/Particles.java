/*  1:   */ package com.rwtema.extrautils.particle;
/*  2:   */ 
/*  3:   */ import net.minecraft.world.World;
/*  4:   */ 
/*  5:   */ public enum Particles
/*  6:   */ {
/*  7: 6 */   BUBBLE,  SUSPENDED,  DEPTHSUSPEND,  TOWNAURA,  CRIT,  MAGICCRIT,  SMOKE,  MOBSPELL,  MOBSPELLAMBIENT,  SPELL,  INSTANTSPELL,  WITCHMAGIC,  NOTE,  PORTAL,  ENCHANTMENTTABLE,  EXPLODE,  FLAME,  LAVA,  FOOTSTEP,  SPLASH,  LARGESMOKE,  CLOUD,  REDDUST,  SNOWBALLPOOF,  DRIPWATER,  DRIPLAVA,  SNOWSHOVEL,  SLIME,  HEART,  ANGRYVILLAGER,  HAPPYVILLAGER;
/*  8:   */   
/*  9:   */   public final String id;
/* 10:   */   
/* 11:   */   private Particles()
/* 12:   */   {
/* 13:41 */     this.id = name().toLowerCase();
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void spawn(World world, double x, double y, double z)
/* 17:   */   {
/* 18:45 */     spawn(world, x, y, z, 0, 0, 0);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void spawn(World world, double x, double y, double z, int r, int g, int b)
/* 22:   */   {
/* 23:49 */     world.spawnParticle(this.id, x, y, z, r, g, b);
/* 24:   */   }
/* 25:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.particle.Particles
 * JD-Core Version:    0.7.0.1
 */