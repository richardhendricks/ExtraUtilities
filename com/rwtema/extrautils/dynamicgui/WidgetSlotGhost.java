/*  1:   */ package com.rwtema.extrautils.dynamicgui;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.renderer.texture.TextureManager;
/*  4:   */ import net.minecraft.entity.player.EntityPlayer;
/*  5:   */ import net.minecraft.inventory.IInventory;
/*  6:   */ import net.minecraft.item.ItemStack;
/*  7:   */ import org.lwjgl.opengl.GL11;
/*  8:   */ 
/*  9:   */ public class WidgetSlotGhost
/* 10:   */   extends WidgetSlot
/* 11:   */ {
/* 12:   */   public WidgetSlotGhost(IInventory inv, int slot, int x, int y)
/* 13:   */   {
/* 14:11 */     super(inv, slot, x, y);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void renderBackground(TextureManager manager, DynamicGui gui, int x, int y) {}
/* 18:   */   
/* 19:   */   public boolean isItemValid(ItemStack par1ItemStack)
/* 20:   */   {
/* 21:23 */     return false;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public boolean canTakeStack(EntityPlayer par1EntityPlayer)
/* 25:   */   {
/* 26:28 */     return false;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void putStack(ItemStack p_75215_1_) {}
/* 30:   */   
/* 31:   */   public void renderForeground(TextureManager manager, DynamicGui gui, int guiLeft, int guiTop)
/* 32:   */   {
/* 33:38 */     boolean blendLevel = GL11.glIsEnabled(3042);
/* 34:40 */     if (!blendLevel) {
/* 35:41 */       GL11.glEnable(3042);
/* 36:   */     }
/* 37:44 */     GL11.glBlendFunc(770, 771);
/* 38:45 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.4F);
/* 39:46 */     gui.drawTexturedModalRect(guiLeft + getX(), guiTop + getY(), 0, 0, 18, 18);
/* 40:47 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 41:49 */     if (!blendLevel) {
/* 42:50 */       GL11.glDisable(3042);
/* 43:   */     }
/* 44:   */   }
/* 45:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.dynamicgui.WidgetSlotGhost
 * JD-Core Version:    0.7.0.1
 */