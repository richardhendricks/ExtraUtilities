/*  1:   */ package com.rwtema.extrautils.helper;
/*  2:   */ 
/*  3:   */ import gnu.trove.map.hash.TIntByteHashMap;
/*  4:   */ import gnu.trove.procedure.TIntByteProcedure;
/*  5:   */ import org.lwjgl.opengl.GL11;
/*  6:   */ 
/*  7:   */ public class GLHelper
/*  8:   */ {
/*  9: 8 */   public static int state_level = -1;
/* 10:   */   public static final int max_state_level = 256;
/* 11:10 */   public static final TIntByteHashMap[] maps = new TIntByteHashMap[256];
/* 12:   */   
/* 13:   */   public static void pushGLState()
/* 14:   */   {
/* 15:14 */     state_level += 1;
/* 16:15 */     if (maps[state_level] == null) {
/* 17:16 */       maps[state_level] = new TIntByteHashMap();
/* 18:   */     } else {
/* 19:18 */       maps[state_level].clear();
/* 20:   */     }
/* 21:   */   }
/* 22:   */   
/* 23:   */   public static boolean enableGLState(int state)
/* 24:   */   {
/* 25:22 */     boolean b = GL11.glIsEnabled(state);
/* 26:23 */     maps[state_level].putIfAbsent(state, (byte)(b ? 1 : 0));
/* 27:24 */     GL11.glEnable(state);
/* 28:25 */     return b;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public static boolean disableGLState(int state)
/* 32:   */   {
/* 33:29 */     boolean b = GL11.glIsEnabled(state);
/* 34:30 */     maps[state_level].putIfAbsent(state, (byte)(b ? 1 : 0));
/* 35:31 */     GL11.glDisable(state);
/* 36:32 */     return b;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public static void popGLState()
/* 40:   */   {
/* 41:36 */     maps[state_level].forEachEntry(new TIntByteProcedure()
/* 42:   */     {
/* 43:   */       public boolean execute(int a, byte b)
/* 44:   */       {
/* 45:39 */         if (b == 1) {
/* 46:40 */           GL11.glEnable(a);
/* 47:   */         } else {
/* 48:42 */           GL11.glDisable(a);
/* 49:   */         }
/* 50:43 */         return true;
/* 51:   */       }
/* 52:45 */     });
/* 53:46 */     maps[state_level].clear();
/* 54:47 */     state_level -= 1;
/* 55:   */   }
/* 56:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.helper.GLHelper
 * JD-Core Version:    0.7.0.1
 */