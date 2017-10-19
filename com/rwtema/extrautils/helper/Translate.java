/*  1:   */ package com.rwtema.extrautils.helper;
/*  2:   */ 
/*  3:   */ import net.minecraft.util.ChatComponentTranslation;
/*  4:   */ import net.minecraft.util.StatCollector;
/*  5:   */ 
/*  6:   */ public class Translate
/*  7:   */ {
/*  8:   */   public static String get(String id, Object... objects)
/*  9:   */   {
/* 10: 9 */     String s = StatCollector.translateToLocal(id);
/* 11:11 */     for (int i = 0; i < objects.length; i++) {
/* 12:12 */       s = s.replaceAll("%" + (i + 1), objects[i].toString());
/* 13:   */     }
/* 14:15 */     ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation(id, objects);
/* 15:   */     
/* 16:17 */     return chatComponentTranslation.getUnformattedTextForChat();
/* 17:   */   }
/* 18:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.helper.Translate
 * JD-Core Version:    0.7.0.1
 */