/*  1:   */ package com.rwtema.extrautils.gui;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.Minecraft;
/*  4:   */ import net.minecraft.client.gui.inventory.GuiContainer;
/*  5:   */ import net.minecraft.client.renderer.texture.TextureManager;
/*  6:   */ import net.minecraft.inventory.IInventory;
/*  7:   */ import net.minecraft.util.ResourceLocation;
/*  8:   */ import org.lwjgl.opengl.GL11;
/*  9:   */ 
/* 10:   */ public class GuiFilterPipe
/* 11:   */   extends GuiContainer
/* 12:   */ {
/* 13: 9 */   private static final ResourceLocation texture = new ResourceLocation("extrautils", "textures/guiSortingPipe.png");
/* 14:   */   
/* 15:   */   public GuiFilterPipe(IInventory player, IInventory pipe)
/* 16:   */   {
/* 17:12 */     super(new ContainerFilterPipe(player, pipe));
/* 18:13 */     this.field_146999_f = 175;
/* 19:14 */     this.field_147000_g = 192;
/* 20:   */   }
/* 21:   */   
/* 22:   */   protected void func_146976_a(float f, int i, int j)
/* 23:   */   {
/* 24:19 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 25:20 */     this.mc.renderEngine.bindTexture(texture);
/* 26:21 */     int k = (this.width - this.field_146999_f) / 2;
/* 27:22 */     int l = (this.height - this.field_147000_g) / 2;
/* 28:23 */     drawTexturedModalRect(k, l, 0, 0, this.field_146999_f, this.field_147000_g);
/* 29:   */   }
/* 30:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.gui.GuiFilterPipe
 * JD-Core Version:    0.7.0.1
 */