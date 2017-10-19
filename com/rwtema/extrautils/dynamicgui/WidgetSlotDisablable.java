/*  1:   */ package com.rwtema.extrautils.dynamicgui;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Method;
/*  4:   */ import net.minecraft.client.renderer.texture.TextureManager;
/*  5:   */ import net.minecraft.entity.player.EntityPlayer;
/*  6:   */ import net.minecraft.inventory.IInventory;
/*  7:   */ import net.minecraft.nbt.NBTTagCompound;
/*  8:   */ import org.lwjgl.opengl.GL11;
/*  9:   */ 
/* 10:   */ public class WidgetSlotDisablable
/* 11:   */   extends WidgetSlot
/* 12:   */ {
/* 13:10 */   boolean enabled = true;
/* 14:   */   String methodName;
/* 15:   */   
/* 16:   */   public WidgetSlotDisablable(IInventory inv, int slot, int x, int y, String methodName)
/* 17:   */   {
/* 18:14 */     super(inv, slot, x, y);
/* 19:15 */     this.methodName = methodName;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean canTakeStack(EntityPlayer par1EntityPlayer)
/* 23:   */   {
/* 24:20 */     return this.enabled;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public boolean isEnabled()
/* 28:   */   {
/* 29:   */     try
/* 30:   */     {
/* 31:25 */       return ((Boolean)this.inventory.getClass().getMethod(this.methodName, new Class[0]).invoke(this.inventory, new Object[0])).booleanValue();
/* 32:   */     }
/* 33:   */     catch (Exception e)
/* 34:   */     {
/* 35:27 */       throw new RuntimeException(e);
/* 36:   */     }
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void renderForeground(TextureManager manager, DynamicGui gui, int guiLeft, int guiTop)
/* 40:   */   {
/* 41:33 */     if (!this.enabled)
/* 42:   */     {
/* 43:34 */       boolean blendLevel = GL11.glIsEnabled(3042);
/* 44:36 */       if (!blendLevel) {
/* 45:37 */         GL11.glEnable(3042);
/* 46:   */       }
/* 47:40 */       GL11.glBlendFunc(770, 771);
/* 48:41 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.4F);
/* 49:42 */       gui.drawTexturedModalRect(guiLeft + getX(), guiTop + getY(), 0, 0, 18, 18);
/* 50:43 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 51:45 */       if (!blendLevel) {
/* 52:46 */         GL11.glDisable(3042);
/* 53:   */       }
/* 54:   */     }
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void handleDescriptionPacket(NBTTagCompound packet)
/* 58:   */   {
/* 59:53 */     this.enabled = packet.getBoolean("a");
/* 60:   */   }
/* 61:   */   
/* 62:   */   public NBTTagCompound getDescriptionPacket(boolean changesOnly)
/* 63:   */   {
/* 64:58 */     boolean newEnabled = isEnabled();
/* 65:60 */     if ((!changesOnly) || (newEnabled != this.enabled))
/* 66:   */     {
/* 67:61 */       this.enabled = newEnabled;
/* 68:62 */       NBTTagCompound tags = new NBTTagCompound();
/* 69:63 */       tags.setBoolean("a", this.enabled);
/* 70:64 */       return tags;
/* 71:   */     }
/* 72:67 */     return null;
/* 73:   */   }
/* 74:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.dynamicgui.WidgetSlotDisablable
 * JD-Core Version:    0.7.0.1
 */