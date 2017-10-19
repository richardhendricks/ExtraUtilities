/*  1:   */ package com.rwtema.extrautils.dynamicgui;
/*  2:   */ 
/*  3:   */ import net.minecraft.nbt.NBTTagCompound;
/*  4:   */ 
/*  5:   */ public abstract class WidgetBase
/*  6:   */   implements IWidget
/*  7:   */ {
/*  8:   */   int x;
/*  9:   */   int y;
/* 10:   */   int w;
/* 11:   */   int h;
/* 12:   */   
/* 13:   */   public WidgetBase(int x, int y, int w, int h)
/* 14:   */   {
/* 15:10 */     this.x = x;
/* 16:11 */     this.y = y;
/* 17:12 */     this.w = w;
/* 18:13 */     this.h = h;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public int getX()
/* 22:   */   {
/* 23:18 */     return this.x;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public int getY()
/* 27:   */   {
/* 28:23 */     return this.y;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public int getW()
/* 32:   */   {
/* 33:28 */     return this.w;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public int getH()
/* 37:   */   {
/* 38:33 */     return this.h;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public NBTTagCompound getDescriptionPacket(boolean changesOnly)
/* 42:   */   {
/* 43:38 */     return null;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void handleDescriptionPacket(NBTTagCompound packet) {}
/* 47:   */   
/* 48:   */   public void addToContainer(DynamicContainer container) {}
/* 49:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.dynamicgui.WidgetBase
 * JD-Core Version:    0.7.0.1
 */