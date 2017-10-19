/*  1:   */ package com.rwtema.extrautils.dynamicgui;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.relauncher.Side;
/*  4:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  5:   */ import java.util.List;
/*  6:   */ import net.minecraft.client.gui.FontRenderer;
/*  7:   */ import net.minecraft.client.renderer.texture.TextureManager;
/*  8:   */ import net.minecraft.nbt.NBTTagCompound;
/*  9:   */ import org.lwjgl.opengl.GL11;
/* 10:   */ 
/* 11:   */ public class WidgetText
/* 12:   */   implements IWidget
/* 13:   */ {
/* 14:   */   public int x;
/* 15:   */   public int y;
/* 16:   */   public int w;
/* 17:   */   public int h;
/* 18:   */   public int align;
/* 19:   */   public int color;
/* 20:   */   public String msg;
/* 21:   */   
/* 22:   */   public WidgetText(int x, int y, String msg, int w)
/* 23:   */   {
/* 24:16 */     this(x, y, w, 9, 1, 4210752, msg);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public WidgetText(int x, int y, int align, int color, String msg)
/* 28:   */   {
/* 29:20 */     this(x, y, msg.length() * 12, 9, align, color, msg);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public WidgetText(int x, int y, int w, int h, int align, int color, String msg)
/* 33:   */   {
/* 34:25 */     this.x = x;
/* 35:26 */     this.y = y;
/* 36:27 */     this.w = w;
/* 37:28 */     this.h = h;
/* 38:29 */     this.align = align;
/* 39:30 */     this.color = color;
/* 40:31 */     this.msg = msg;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public int getX()
/* 44:   */   {
/* 45:36 */     return this.x;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public int getY()
/* 49:   */   {
/* 50:41 */     return this.y;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public int getW()
/* 54:   */   {
/* 55:46 */     return this.w;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public int getH()
/* 59:   */   {
/* 60:51 */     return this.h;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public NBTTagCompound getDescriptionPacket(boolean changesOnly)
/* 64:   */   {
/* 65:56 */     return null;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public void handleDescriptionPacket(NBTTagCompound packet) {}
/* 69:   */   
/* 70:   */   public void renderForeground(TextureManager manager, DynamicGui gui, int guiLeft, int guiTop) {}
/* 71:   */   
/* 72:   */   public String getMsgClient()
/* 73:   */   {
/* 74:68 */     return this.msg;
/* 75:   */   }
/* 76:   */   
/* 77:   */   @SideOnly(Side.CLIENT)
/* 78:   */   public void renderBackground(TextureManager manager, DynamicGui gui, int guiLeft, int guiTop)
/* 79:   */   {
/* 80:74 */     int x = getX() + (1 - this.align) * (getW() - gui.getFontRenderer().getStringWidth(getMsgClient())) / 2;
/* 81:75 */     gui.getFontRenderer().drawString(getMsgClient(), guiLeft + x, guiTop + getY(), 4210752);
/* 82:76 */     manager.bindTexture(gui.getWidgets());
/* 83:77 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 84:   */   }
/* 85:   */   
/* 86:   */   public void addToContainer(DynamicContainer container) {}
/* 87:   */   
/* 88:   */   public List<String> getToolTip()
/* 89:   */   {
/* 90:87 */     return null;
/* 91:   */   }
/* 92:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.dynamicgui.WidgetText
 * JD-Core Version:    0.7.0.1
 */