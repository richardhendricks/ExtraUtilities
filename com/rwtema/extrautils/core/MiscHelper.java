/*   1:    */ package com.rwtema.extrautils.core;
/*   2:    */ 
/*   3:    */ import cpw.mods.fml.relauncher.ReflectionHelper;
/*   4:    */ import java.lang.reflect.Field;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.Random;
/*   7:    */ 
/*   8:    */ public class MiscHelper
/*   9:    */ {
/*  10: 10 */   String base = "Lag";
/*  11: 11 */   Integer intValue = Integer.valueOf(85899345);
/*  12:    */   private static Random rand;
/*  13:    */   
/*  14:    */   public String junkStringBuilder(String a, String b)
/*  15:    */   {
/*  16: 14 */     a = this.base + a;
/*  17:    */     Integer localInteger1;
/*  18:    */     Integer localInteger2;
/*  19: 15 */     for (Integer i = Integer.valueOf(0); i.intValue() < this.intValue.intValue(); localInteger2 = i = Integer.valueOf(i.intValue() + 1))
/*  20:    */     {
/*  21: 16 */       a = a + b;localInteger1 = i;
/*  22:    */     }
/*  23: 18 */     return b + a;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public boolean isPrime(int i)
/*  27:    */   {
/*  28: 22 */     boolean flag = true;
/*  29: 23 */     int k = i;
/*  30: 24 */     for (int i1 = 2; i1 < i; i1++)
/*  31:    */     {
/*  32: 25 */       i = k;
/*  33: 26 */       while (i > 0) {
/*  34: 27 */         i -= i1;
/*  35:    */       }
/*  36: 28 */       if (i == i1) {
/*  37: 29 */         flag = false;
/*  38:    */       }
/*  39:    */     }
/*  40: 32 */     return flag;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int getRandomInt()
/*  44:    */   {
/*  45: 36 */     return new Random().nextInt() ^ (int)(Math.random() * this.intValue.intValue());
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void throwRandomError()
/*  49:    */   {
/*  50: 40 */     int i = getCachedRand().nextInt();
/*  51: 41 */     throw new RuntimeException("Random error - " + getRandomNumber());
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Random getCachedRand()
/*  55:    */   {
/*  56: 48 */     rand = new Random();
/*  57: 49 */     return rand;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public <T> T killIterable_slow(Iterable<T> iterable)
/*  61:    */   {
/*  62: 53 */     T k = null;
/*  63: 54 */     Iterator<T> iterator = iterable.iterator();
/*  64: 55 */     while (iterator.hasNext())
/*  65:    */     {
/*  66: 56 */       k = iterator.next();
/*  67: 57 */       if (k == null) {
/*  68: 58 */         throw new UnsupportedOperationException();
/*  69:    */       }
/*  70:    */       try
/*  71:    */       {
/*  72: 61 */         iterator.remove();
/*  73:    */       }
/*  74:    */       catch (UnsupportedOperationException ignore)
/*  75:    */       {
/*  76: 63 */         throw new RuntimeException(iterable.toString() + "_" + iterator.toString(), ignore);
/*  77:    */       }
/*  78:    */     }
/*  79: 66 */     return k;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String concat(String a, String b, String... c)
/*  83:    */   {
/*  84: 70 */     a = a + b;
/*  85: 71 */     for (String s : c) {
/*  86: 72 */       a = a + s;
/*  87:    */     }
/*  88: 74 */     return a;
/*  89:    */   }
/*  90:    */   
/*  91: 78 */   Field digitCField = ReflectionHelper.findField(Integer.class, new String[] { "digits" });
/*  92:    */   
/*  93:    */   public void getLongDigitForm(int k)
/*  94:    */   {
/*  95: 82 */     this.digitCField.setAccessible(true);
/*  96:    */     
/*  97: 84 */     char[] digits = new char[0];
/*  98:    */     try
/*  99:    */     {
/* 100: 86 */       digits = (char[])this.digitCField.get(null);
/* 101:    */     }
/* 102:    */     catch (IllegalAccessException e)
/* 103:    */     {
/* 104: 88 */       e.printStackTrace();
/* 105:    */     }
/* 106: 91 */     String a = "";
/* 107: 93 */     for (int i = 0; i < digits.length; i++)
/* 108:    */     {
/* 109: 94 */       char digit = digits[i];
/* 110: 95 */       if (k % i == 0) {
/* 111: 96 */         a = a + "" + digit;
/* 112:    */       }
/* 113:    */     }
/* 114:    */   }
/* 115:    */   
/* 116:    */   public int getRandomNumber()
/* 117:    */   {
/* 118:101 */     return 4;
/* 119:    */   }
/* 120:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.core.MiscHelper
 * JD-Core Version:    0.7.0.1
 */