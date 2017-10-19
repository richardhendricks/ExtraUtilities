/*  1:   */ package com.rwtema.extrautils;
/*  2:   */ 
/*  3:   */ import com.google.common.base.Throwables;
/*  4:   */ import java.lang.reflect.Field;
/*  5:   */ import net.minecraft.client.settings.KeyBinding;
/*  6:   */ import net.minecraft.util.IntHashMap;
/*  7:   */ 
/*  8:   */ public class KeyHandler
/*  9:   */ {
/* 10:11 */   public static KeyHandler INSTANCE = new KeyHandler();
/* 11:   */   public static KeyBinding CTRL;
/* 12:   */   public static final String CTRL_DESCRIPTION = "key.xu.special";
/* 13:   */   public static final int CTRL_CODE = 29;
/* 14:   */   public static final String CTRL_CATEGORY = "key.categories.gameplay";
/* 15:   */   private static IntHashMap hash;
/* 16:   */   
/* 17:   */   static
/* 18:   */   {
/* 19:21 */     for (Field field : KeyBinding.class.getDeclaredFields()) {
/* 20:22 */       if (field.getType() == IntHashMap.class)
/* 21:   */       {
/* 22:23 */         field.setAccessible(true);
/* 23:   */         try
/* 24:   */         {
/* 25:25 */           hash = (IntHashMap)field.get(null);
/* 26:   */         }
/* 27:   */         catch (IllegalAccessException e)
/* 28:   */         {
/* 29:27 */           throw Throwables.propagate(e);
/* 30:   */         }
/* 31:   */       }
/* 32:   */     }
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void register() {}
/* 36:   */   
/* 37:   */   public static boolean getIsKeyPressed(KeyBinding key)
/* 38:   */   {
/* 39:34 */     KeyBinding lookup = (KeyBinding)hash.lookup(key.getKeyCode());
/* 40:35 */     return (lookup != null) && (lookup.getIsKeyPressed());
/* 41:   */   }
/* 42:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.KeyHandler
 * JD-Core Version:    0.7.0.1
 */