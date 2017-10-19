/*  1:   */ package com.rwtema.extrautils.dynamicgui;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.client.renderer.texture.TextureManager;
/*  5:   */ import net.minecraft.nbt.NBTTagCompound;
/*  6:   */ 
/*  7:   */ public abstract class WidgetDescPacket
/*  8:   */   extends WidgetBase
/*  9:   */ {
/* 10:   */   public WidgetDescPacket()
/* 11:   */   {
/* 12: 9 */     super(0, 0, 0, 0);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public void renderForeground(TextureManager manager, DynamicGui gui, int guiLeft, int guiTop) {}
/* 16:   */   
/* 17:   */   public void renderBackground(TextureManager manager, DynamicGui gui, int guiLeft, int guiTop) {}
/* 18:   */   
/* 19:   */   public List<String> getToolTip()
/* 20:   */   {
/* 21:24 */     return null;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public abstract NBTTagCompound getDescriptionPacket(boolean paramBoolean);
/* 25:   */   
/* 26:   */   public abstract void handleDescriptionPacket(NBTTagCompound paramNBTTagCompound);
/* 27:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.dynamicgui.WidgetDescPacket
 * JD-Core Version:    0.7.0.1
 */