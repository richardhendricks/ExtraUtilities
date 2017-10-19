/*  1:   */ package com.rwtema.extrautils.dynamicgui;
/*  2:   */ 
/*  3:   */ import net.minecraft.util.StatCollector;
/*  4:   */ 
/*  5:   */ public class WidgetTextTranslate
/*  6:   */   extends WidgetText
/*  7:   */ {
/*  8:   */   public WidgetTextTranslate(int x, int y, int w, int h, int align, int color, String msg)
/*  9:   */   {
/* 10: 7 */     super(x, y, w, h, align, color, msg);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public WidgetTextTranslate(int i, int j, String invName, int playerInvWidth)
/* 14:   */   {
/* 15:11 */     super(i, j, invName, playerInvWidth);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getMsgClient()
/* 19:   */   {
/* 20:16 */     return StatCollector.translateToLocal(this.msg);
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.dynamicgui.WidgetTextTranslate
 * JD-Core Version:    0.7.0.1
 */