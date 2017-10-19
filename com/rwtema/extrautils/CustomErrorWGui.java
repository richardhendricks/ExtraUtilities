/*  1:   */ package com.rwtema.extrautils;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.client.CustomModLoadingErrorDisplayException;
/*  4:   */ import cpw.mods.fml.relauncher.Side;
/*  5:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  6:   */ import java.util.ArrayList;
/*  7:   */ import java.util.List;
/*  8:   */ import net.minecraft.client.gui.FontRenderer;
/*  9:   */ import net.minecraft.client.gui.GuiErrorScreen;
/* 10:   */ 
/* 11:   */ @SideOnly(Side.CLIENT)
/* 12:   */ public class CustomErrorWGui
/* 13:   */   extends CustomModLoadingErrorDisplayException
/* 14:   */ {
/* 15:   */   String cause;
/* 16:   */   String[] message;
/* 17:   */   
/* 18:   */   public CustomErrorWGui(String cause, String... message)
/* 19:   */   {
/* 20:18 */     this.cause = cause;
/* 21:19 */     this.message = message;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void initGui(GuiErrorScreen errorScreen, FontRenderer fontRenderer) {}
/* 25:   */   
/* 26:   */   public void drawScreen(GuiErrorScreen errorScreen, FontRenderer fontRenderer, int mouseRelX, int mouseRelY, float tickTime)
/* 27:   */   {
/* 28:29 */     errorScreen.drawDefaultBackground();
/* 29:30 */     List t = new ArrayList();
/* 30:31 */     for (String m : this.message) {
/* 31:32 */       if (m != null) {
/* 32:33 */         t.addAll(fontRenderer.listFormattedStringToWidth(m, errorScreen.width));
/* 33:   */       }
/* 34:   */     }
/* 35:35 */     int offset = Math.max(85 - t.size() * 10, 10);
/* 36:   */     
/* 37:   */ 
/* 38:38 */     errorScreen.drawCenteredString(fontRenderer, this.cause, errorScreen.width / 2, offset, 16777215);
/* 39:39 */     offset += 10;
/* 40:40 */     for (Object aT : t)
/* 41:   */     {
/* 42:41 */       errorScreen.drawCenteredString(fontRenderer, (String)aT, errorScreen.width / 2, offset, 16777215);
/* 43:42 */       offset += 10;
/* 44:   */     }
/* 45:   */   }
/* 46:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.CustomErrorWGui
 * JD-Core Version:    0.7.0.1
 */