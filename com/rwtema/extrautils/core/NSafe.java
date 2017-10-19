/*  1:   */ package com.rwtema.extrautils.core;
/*  2:   */ 
/*  3:   */ import com.google.common.base.Throwables;
/*  4:   */ import com.rwtema.extrautils.ExtraUtils;
/*  5:   */ import java.lang.reflect.Field;
/*  6:   */ import java.util.HashMap;
/*  7:   */ import java.util.Objects;
/*  8:   */ 
/*  9:   */ public class NSafe
/* 10:   */ {
/* 11:11 */   static HashMap<Tuple<Class<?>, String>, Field> cache = new HashMap();
/* 12:   */   
/* 13:   */   public static Field getField(Class<?> clazz, String fieldName)
/* 14:   */   {
/* 15:14 */     Tuple<Class<?>, String> key = new Tuple(clazz, fieldName);
/* 16:15 */     Field val = (Field)cache.get(key);
/* 17:16 */     if (val == null)
/* 18:   */     {
/* 19:   */       try
/* 20:   */       {
/* 21:18 */         Field f = clazz.getDeclaredField(fieldName);
/* 22:19 */         f.setAccessible(true);
/* 23:20 */         val = f;
/* 24:   */       }
/* 25:   */       catch (Exception e)
/* 26:   */       {
/* 27:22 */         throw new RuntimeException(e);
/* 28:   */       }
/* 29:25 */       cache.put(key, val);
/* 30:   */     }
/* 31:28 */     return val;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public static <K> K get(Object object, String fieldName)
/* 35:   */   {
/* 36:32 */     if (object == null) {
/* 37:32 */       return null;
/* 38:   */     }
/* 39:33 */     Field field = getField(object.getClass(), fieldName);
/* 40:34 */     if (field == null) {
/* 41:34 */       return null;
/* 42:   */     }
/* 43:35 */     K result = null;
/* 44:   */     try
/* 45:   */     {
/* 46:37 */       result = field.get(object);
/* 47:   */     }
/* 48:   */     catch (IllegalAccessException e)
/* 49:   */     {
/* 50:39 */       throw Throwables.propagate(e);
/* 51:   */     }
/* 52:41 */     return result;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public static <T> T set(T object, String value, Objects... param)
/* 56:   */   {
/* 57:45 */     String s = (String)get(ExtraUtils.wateringCan, "iconString");
/* 58:   */     
/* 59:47 */     return object;
/* 60:   */   }
/* 61:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.core.NSafe
 * JD-Core Version:    0.7.0.1
 */