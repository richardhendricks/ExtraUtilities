/*  1:   */ package com.rwtema.extrautils.gui;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.tileentity.TileEntityTrashCan;
/*  4:   */ import net.minecraft.client.Minecraft;
/*  5:   */ import net.minecraft.client.gui.inventory.GuiContainer;
/*  6:   */ import net.minecraft.client.renderer.texture.TextureManager;
/*  7:   */ import net.minecraft.inventory.IInventory;
/*  8:   */ import net.minecraft.util.ResourceLocation;
/*  9:   */ import org.lwjgl.opengl.GL11;
/* 10:   */ 
/* 11:   */ public class GuiTrashCan
/* 12:   */   extends GuiContainer
/* 13:   */ {
/* 14:10 */   private static final ResourceLocation texture = new ResourceLocation("extrautils", "textures/guiTrashCan.png");
/* 15:   */   private IInventory player;
/* 16:   */   private TileEntityTrashCan trashCan;
/* 17:   */   
/* 18:   */   public GuiTrashCan(IInventory player, TileEntityTrashCan trashCan)
/* 19:   */   {
/* 20:15 */     super(new ContainerTrashCan(player, trashCan));
/* 21:16 */     this.trashCan = trashCan;
/* 22:17 */     this.player = player;
/* 23:18 */     this.field_146999_f = 176;
/* 24:19 */     this.field_147000_g = 222;
/* 25:   */   }
/* 26:   */   
/* 27:   */   protected void func_146976_a(float f, int i, int j)
/* 28:   */   {
/* 29:24 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 30:25 */     this.mc.renderEngine.bindTexture(texture);
/* 31:26 */     int k = (this.width - this.field_146999_f) / 2;
/* 32:27 */     int l = (this.height - this.field_147000_g) / 2;
/* 33:28 */     drawTexturedModalRect(k, l, 0, 0, this.field_146999_f, this.field_147000_g);
/* 34:   */   }
/* 35:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.gui.GuiTrashCan
 * JD-Core Version:    0.7.0.1
 */