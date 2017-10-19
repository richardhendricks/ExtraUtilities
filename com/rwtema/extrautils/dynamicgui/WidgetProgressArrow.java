/*  1:   */ package com.rwtema.extrautils.dynamicgui;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.client.renderer.texture.TextureManager;
/*  5:   */ import net.minecraft.nbt.NBTTagCompound;
/*  6:   */ 
/*  7:   */ public abstract class WidgetProgressArrow
/*  8:   */   extends WidgetBase
/*  9:   */   implements IWidget
/* 10:   */ {
/* 11: 9 */   byte curWidth = -1;
/* 12:   */   
/* 13:   */   public WidgetProgressArrow(int x, int y)
/* 14:   */   {
/* 15:12 */     super(x, y, 22, 17);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public abstract int getWidth();
/* 19:   */   
/* 20:   */   public NBTTagCompound getDescriptionPacket(boolean changesOnly)
/* 21:   */   {
/* 22:19 */     NBTTagCompound tag = null;
/* 23:20 */     byte newWidth = getAdjustedWidth(getWidth());
/* 24:22 */     if ((!changesOnly) || (this.curWidth != newWidth))
/* 25:   */     {
/* 26:23 */       tag = new NBTTagCompound();
/* 27:24 */       tag.setByte("a", newWidth);
/* 28:   */     }
/* 29:27 */     this.curWidth = newWidth;
/* 30:28 */     return tag;
/* 31:   */   }
/* 32:   */   
/* 33:   */   private byte getAdjustedWidth(int a)
/* 34:   */   {
/* 35:32 */     if (a < 0) {
/* 36:33 */       a = 0;
/* 37:34 */     } else if (a > 22) {
/* 38:35 */       a = 22;
/* 39:   */     }
/* 40:38 */     return (byte)a;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void handleDescriptionPacket(NBTTagCompound packet)
/* 44:   */   {
/* 45:43 */     if (packet.hasKey("a")) {
/* 46:44 */       this.curWidth = packet.getByte("a");
/* 47:   */     }
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void renderForeground(TextureManager manager, DynamicGui gui, int guiLeft, int guiTop)
/* 51:   */   {
/* 52:50 */     manager.bindTexture(gui.getWidgets());
/* 53:51 */     gui.drawTexturedModalRect(guiLeft + getX(), guiTop + getY(), 98, 16, this.curWidth, 16);
/* 54:   */   }
/* 55:   */   
/* 56:   */   public void renderBackground(TextureManager manager, DynamicGui gui, int guiLeft, int guiTop)
/* 57:   */   {
/* 58:56 */     manager.bindTexture(gui.getWidgets());
/* 59:57 */     gui.drawTexturedModalRect(guiLeft + getX(), guiTop + getY(), 98, 0, 22, 16);
/* 60:   */   }
/* 61:   */   
/* 62:   */   public List<String> getToolTip()
/* 63:   */   {
/* 64:62 */     return null;
/* 65:   */   }
/* 66:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.dynamicgui.WidgetProgressArrow
 * JD-Core Version:    0.7.0.1
 */