/*  1:   */ package com.rwtema.extrautils;
/*  2:   */ 
/*  3:   */ import net.minecraft.world.World;
/*  4:   */ import org.apache.logging.log4j.LogManager;
/*  5:   */ import org.apache.logging.log4j.Logger;
/*  6:   */ 
/*  7:   */ public class LogHelper
/*  8:   */ {
/*  9: 8 */   public static Logger logger = LogManager.getLogger("extrautils");
/* 10:10 */   public static boolean isDeObf = false;
/* 11:   */   
/* 12:   */   static
/* 13:   */   {
/* 14:   */     try
/* 15:   */     {
/* 16:14 */       World.class.getMethod("getBlock", new Class[] { Integer.TYPE, Integer.TYPE, Integer.TYPE });
/* 17:15 */       isDeObf = true;
/* 18:   */     }
/* 19:   */     catch (Throwable ex)
/* 20:   */     {
/* 21:17 */       isDeObf = false;
/* 22:   */     }
/* 23:   */   }
/* 24:   */   
/* 25:   */   public static void debug(Object info, Object... info2)
/* 26:   */   {
/* 27:22 */     if (isDeObf)
/* 28:   */     {
/* 29:23 */       String temp = "Debug: " + info;
/* 30:24 */       for (Object t : info2) {
/* 31:25 */         temp = temp + " " + t;
/* 32:   */       }
/* 33:27 */       logger.info(info);
/* 34:   */     }
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static void info(Object info, Object... info2)
/* 38:   */   {
/* 39:33 */     String temp = "" + info;
/* 40:34 */     for (Object t : info2) {
/* 41:35 */       temp = temp + " " + t;
/* 42:   */     }
/* 43:37 */     logger.info(info);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public static void fine(Object info, Object... info2)
/* 47:   */   {
/* 48:42 */     String temp = "" + info;
/* 49:43 */     for (Object t : info2) {
/* 50:44 */       temp = temp + " " + t;
/* 51:   */     }
/* 52:46 */     logger.debug(temp);
/* 53:   */   }
/* 54:   */   
/* 55:   */   public static void errorThrowable(String message, Throwable t)
/* 56:   */   {
/* 57:51 */     logger.error(message, t);
/* 58:   */   }
/* 59:   */   
/* 60:   */   public static void error(Object info, Object... info2)
/* 61:   */   {
/* 62:55 */     String temp = "" + info;
/* 63:56 */     for (Object t : info2) {
/* 64:57 */       temp = temp + " " + t;
/* 65:   */     }
/* 66:59 */     logger.error(info);
/* 67:   */   }
/* 68:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.LogHelper
 * JD-Core Version:    0.7.0.1
 */