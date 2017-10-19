/*  1:   */ package com.rwtema.extrautils.helper;
/*  2:   */ 
/*  3:   */ public class SplineHelper
/*  4:   */ {
/*  5:   */   public static double[] splineParams(double p0, double p1, double d0, double d1)
/*  6:   */   {
/*  7: 5 */     return new double[] { 2.0D * p0 - 2.0D * p1 + d0 + d1, -3.0D * p0 + 3.0D * p1 - 2.0D * d0 - d1, d0, p0 };
/*  8:   */   }
/*  9:   */   
/* 10:   */   public static double evalSpline(double t, double[] p)
/* 11:   */   {
/* 12:14 */     return ((p[0] * t + p[1]) * t + p[2]) * t + p[3];
/* 13:   */   }
/* 14:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.helper.SplineHelper
 * JD-Core Version:    0.7.0.1
 */