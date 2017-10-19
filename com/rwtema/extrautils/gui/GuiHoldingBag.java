/*  1:   */ package com.rwtema.extrautils.gui;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.relauncher.Side;
/*  4:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  5:   */ import net.minecraft.client.Minecraft;
/*  6:   */ import net.minecraft.client.gui.FontRenderer;
/*  7:   */ import net.minecraft.client.gui.inventory.GuiContainer;
/*  8:   */ import net.minecraft.client.renderer.texture.TextureManager;
/*  9:   */ import net.minecraft.entity.player.EntityPlayer;
/* 10:   */ import net.minecraft.entity.player.InventoryPlayer;
/* 11:   */ import net.minecraft.item.ItemStack;
/* 12:   */ import net.minecraft.util.ResourceLocation;
/* 13:   */ import net.minecraft.util.StatCollector;
/* 14:   */ import org.lwjgl.opengl.GL11;
/* 15:   */ 
/* 16:   */ @SideOnly(Side.CLIENT)
/* 17:   */ public class GuiHoldingBag
/* 18:   */   extends GuiContainer
/* 19:   */ {
/* 20:13 */   private static final ResourceLocation texture = new ResourceLocation("textures/gui/container/generic_54.png");
/* 21:   */   private EntityPlayer player;
/* 22:15 */   private int currentFilter = -1;
/* 23:   */   
/* 24:   */   public GuiHoldingBag(EntityPlayer player, int currentFilter)
/* 25:   */   {
/* 26:18 */     super(new ContainerHoldingBag(player, currentFilter));
/* 27:19 */     this.currentFilter = currentFilter;
/* 28:20 */     this.player = player;
/* 29:21 */     this.field_146999_f = 176;
/* 30:22 */     this.field_147000_g = 222;
/* 31:   */   }
/* 32:   */   
/* 33:   */   protected void func_146979_b(int par1, int par2)
/* 34:   */   {
/* 35:31 */     if (this.player.inventory.getStackInSlot(this.currentFilter) != null) {
/* 36:32 */       this.fontRendererObj.drawString(this.player.inventory.getStackInSlot(this.currentFilter).getDisplayName(), 8, 6, 4210752);
/* 37:   */     }
/* 38:35 */     this.fontRendererObj.drawString(this.player.inventory.isInventoryNameLocalized() ? this.player.inventory.getInventoryName() : StatCollector.translateToLocal(this.player.inventory.getInventoryName()), 8, this.field_147000_g - 96 + 2, 4210752);
/* 39:   */   }
/* 40:   */   
/* 41:   */   protected void func_146976_a(float f, int i, int j)
/* 42:   */   {
/* 43:41 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 44:42 */     this.mc.renderEngine.bindTexture(texture);
/* 45:43 */     int k = (this.width - this.field_146999_f) / 2;
/* 46:44 */     int l = (this.height - this.field_147000_g) / 2;
/* 47:45 */     drawTexturedModalRect(k, l, 0, 0, this.field_146999_f, this.field_147000_g);
/* 48:   */   }
/* 49:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.gui.GuiHoldingBag
 * JD-Core Version:    0.7.0.1
 */