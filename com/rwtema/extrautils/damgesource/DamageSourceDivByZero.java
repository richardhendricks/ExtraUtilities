/*  1:   */ package com.rwtema.extrautils.damgesource;
/*  2:   */ 
/*  3:   */ import net.minecraft.util.DamageSource;
/*  4:   */ 
/*  5:   */ public class DamageSourceDivByZero
/*  6:   */   extends DamageSource
/*  7:   */ {
/*  8: 6 */   public static DamageSourceDivByZero divbyzero = new DamageSourceDivByZero();
/*  9:   */   
/* 10:   */   protected DamageSourceDivByZero()
/* 11:   */   {
/* 12: 9 */     super("divbyzero");
/* 13:10 */     setDamageBypassesArmor();
/* 14:   */   }
/* 15:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.damgesource.DamageSourceDivByZero
 * JD-Core Version:    0.7.0.1
 */