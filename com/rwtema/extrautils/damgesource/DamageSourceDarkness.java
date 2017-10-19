/*  1:   */ package com.rwtema.extrautils.damgesource;
/*  2:   */ 
/*  3:   */ import net.minecraft.util.DamageSource;
/*  4:   */ 
/*  5:   */ public class DamageSourceDarkness
/*  6:   */   extends DamageSource
/*  7:   */ {
/*  8: 6 */   public static DamageSourceDarkness darkness = new DamageSourceDarkness();
/*  9:   */   
/* 10:   */   protected DamageSourceDarkness()
/* 11:   */   {
/* 12: 9 */     super("darkness");
/* 13:10 */     setDamageBypassesArmor();
/* 14:   */   }
/* 15:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.damgesource.DamageSourceDarkness
 * JD-Core Version:    0.7.0.1
 */