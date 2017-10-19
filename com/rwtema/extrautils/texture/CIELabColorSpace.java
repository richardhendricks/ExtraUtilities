/*   1:    */ package com.rwtema.extrautils.texture;
/*   2:    */ 
/*   3:    */ import java.awt.color.ColorSpace;
/*   4:    */ 
/*   5:    */ public class CIELabColorSpace
/*   6:    */   extends ColorSpace
/*   7:    */ {
/*   8:  7 */   private final float INV255 = 0.003921569F;
/*   9:    */   private static final long serialVersionUID = 5027741380892134289L;
/*  10:    */   
/*  11:    */   public static CIELabColorSpace getInstance()
/*  12:    */   {
/*  13: 10 */     return Holder.INSTANCE;
/*  14:    */   }
/*  15:    */   
/*  16:    */   public float[] fromCIEXYZ(float[] colorvalue)
/*  17:    */   {
/*  18: 15 */     double l = f(colorvalue[1] * 1.0D);
/*  19: 16 */     double L = 116.0D * l - 16.0D;
/*  20: 17 */     double a = 500.0D * (f(colorvalue[0] * 1.052111060843583D) - l);
/*  21: 18 */     double b = 200.0D * (l - f(colorvalue[2] * 0.9184170164304805D));
/*  22: 19 */     return new float[] { (float)L, (float)a, (float)b };
/*  23:    */   }
/*  24:    */   
/*  25:    */   public float[] fromRGB(float[] rgbvalue)
/*  26:    */   {
/*  27: 24 */     float[] xyz = CIEXYZ.fromRGB(rgbvalue);
/*  28: 25 */     return fromCIEXYZ(xyz);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public float[] fromRGB(int r, int g, int b)
/*  32:    */   {
/*  33: 29 */     return fromRGB(new float[] { r * 0.003921569F, g * 0.003921569F, b * 0.003921569F });
/*  34:    */   }
/*  35:    */   
/*  36:    */   public float[] fromRGB(int col)
/*  37:    */   {
/*  38: 33 */     return fromRGB(new float[] { ((col & 0xFF0000) >> 16) * 0.003921569F, ((col & 0xFF00) >> 8) * 0.003921569F, (col & 0xFF) * 0.003921569F });
/*  39:    */   }
/*  40:    */   
/*  41:    */   public float getMaxValue(int component)
/*  42:    */   {
/*  43: 44 */     return 128.0F;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public float getMinValue(int component)
/*  47:    */   {
/*  48: 49 */     return component == 0 ? 0.0F : -128.0F;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String getName(int idx)
/*  52:    */   {
/*  53: 54 */     return String.valueOf("Lab".charAt(idx));
/*  54:    */   }
/*  55:    */   
/*  56:    */   public float[] toCIEXYZ(float[] colorvalue)
/*  57:    */   {
/*  58: 59 */     double i = (colorvalue[0] + 16.0D) * 0.008620689655172414D;
/*  59: 60 */     double X = fInv(i + colorvalue[1] * 0.002D) * 0.95047D;
/*  60: 61 */     double Y = fInv(i) * 1.0D;
/*  61: 62 */     double Z = fInv(i - colorvalue[2] * 0.005D) * 1.08883D;
/*  62: 63 */     return new float[] { (float)X, (float)Y, (float)Z };
/*  63:    */   }
/*  64:    */   
/*  65:    */   public float[] toRGB(float[] colorvalue)
/*  66:    */   {
/*  67: 68 */     float[] xyz = toCIEXYZ(colorvalue);
/*  68: 69 */     return CIEXYZ.toRGB(xyz);
/*  69:    */   }
/*  70:    */   
/*  71:    */   CIELabColorSpace()
/*  72:    */   {
/*  73: 73 */     super(1, 3);
/*  74:    */   }
/*  75:    */   
/*  76:    */   private static double f(double x)
/*  77:    */   {
/*  78: 77 */     if (x > 0.008856451679035631D) {
/*  79: 78 */       return Math.cbrt(x);
/*  80:    */     }
/*  81: 80 */     return 7.787037037037037D * x + 0.1379310344827586D;
/*  82:    */   }
/*  83:    */   
/*  84:    */   private static double fInv(double x)
/*  85:    */   {
/*  86: 85 */     if (x > 0.2068965517241379D) {
/*  87: 86 */       return x * x * x;
/*  88:    */     }
/*  89: 88 */     return 0.1284185493460167D * (x - 0.1379310344827586D);
/*  90:    */   }
/*  91:    */   
/*  92:    */   private Object readResolve()
/*  93:    */   {
/*  94: 93 */     return getInstance();
/*  95:    */   }
/*  96:    */   
/*  97:    */   private static class Holder
/*  98:    */   {
/*  99: 97 */     static final CIELabColorSpace INSTANCE = new CIELabColorSpace();
/* 100:    */   }
/* 101:    */   
/* 102:102 */   private static final ColorSpace CIEXYZ = ColorSpace.getInstance(1001);
/* 103:    */   private static final double N = 0.1379310344827586D;
/* 104:    */   private static final double X0 = 0.95047D;
/* 105:    */   private static final double XI = 1.052111060843583D;
/* 106:    */   private static final double Y0 = 1.0D;
/* 107:    */   private static final double YI = 1.0D;
/* 108:    */   private static final double Z0 = 1.08883D;
/* 109:    */   private static final double ZI = 0.9184170164304805D;
/* 110:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.texture.CIELabColorSpace
 * JD-Core Version:    0.7.0.1
 */