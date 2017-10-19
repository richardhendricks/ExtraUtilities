/*   1:    */ package com.rwtema.extrautils.dynamicgui;
/*   2:    */ 
/*   3:    */ import cpw.mods.fml.relauncher.Side;
/*   4:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   5:    */ import java.util.List;
/*   6:    */ import net.minecraft.client.Minecraft;
/*   7:    */ import net.minecraft.client.gui.FontRenderer;
/*   8:    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*   9:    */ import net.minecraft.client.renderer.RenderHelper;
/*  10:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  11:    */ import net.minecraft.util.ResourceLocation;
/*  12:    */ import org.lwjgl.opengl.GL11;
/*  13:    */ 
/*  14:    */ @SideOnly(Side.CLIENT)
/*  15:    */ public class DynamicGui
/*  16:    */   extends GuiContainer
/*  17:    */ {
/*  18: 16 */   private static final ResourceLocation texBackground = new ResourceLocation("extrautils", "textures/guiBase.png");
/*  19: 17 */   private static final ResourceLocation texWidgets = new ResourceLocation("extrautils", "textures/guiWidget.png");
/*  20: 18 */   public static int border = 5;
/*  21:    */   private DynamicContainer container;
/*  22:    */   
/*  23:    */   public DynamicGui(DynamicContainer container)
/*  24:    */   {
/*  25: 22 */     super(container);
/*  26: 23 */     this.field_146999_f = container.width;
/*  27: 24 */     this.field_147000_g = container.height;
/*  28: 25 */     this.field_147003_i = ((this.width - this.field_146999_f) / 2);
/*  29: 26 */     this.field_147009_r = ((this.height - this.field_147000_g) / 2);
/*  30: 27 */     this.container = container;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public float getZLevel()
/*  34:    */   {
/*  35: 31 */     return this.zLevel;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public int getXSize()
/*  39:    */   {
/*  40: 35 */     return this.field_146999_f;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int getYSize()
/*  44:    */   {
/*  45: 39 */     return this.field_147000_g;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public FontRenderer getFontRenderer()
/*  49:    */   {
/*  50: 43 */     return this.fontRendererObj;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public ResourceLocation getBackground()
/*  54:    */   {
/*  55: 47 */     return texBackground;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public ResourceLocation getWidgets()
/*  59:    */   {
/*  60: 51 */     return texWidgets;
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected void func_146976_a(float f, int a, int b)
/*  64:    */   {
/*  65: 56 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  66: 57 */     this.mc.renderEngine.bindTexture(getBackground());
/*  67: 58 */     this.field_146999_f = this.container.width;
/*  68: 59 */     this.field_147000_g = this.container.height;
/*  69: 60 */     drawTexturedModalRect(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f - 4, this.field_147000_g - 4);
/*  70: 61 */     drawTexturedModalRect(this.field_147003_i + this.field_146999_f - 4, this.field_147009_r, 252, 0, 4, 4);
/*  71: 62 */     drawTexturedModalRect(this.field_147003_i + this.field_146999_f - 4, this.field_147009_r + 4, 252, 260 - this.field_147000_g, 4, this.field_147000_g - 4);
/*  72: 63 */     drawTexturedModalRect(this.field_147003_i, this.field_147009_r + this.field_147000_g - 4, 0, 252, 4, 4);
/*  73: 64 */     drawTexturedModalRect(this.field_147003_i + 4, this.field_147009_r + this.field_147000_g - 4, 260 - this.field_146999_f, 252, this.field_146999_f - 8, 4);
/*  74: 65 */     this.mc.renderEngine.bindTexture(getWidgets());
/*  75: 67 */     for (int i = 0; i < this.container.widgets.size(); i++) {
/*  76: 68 */       ((IWidget)this.container.widgets.get(i)).renderBackground(this.mc.renderEngine, this, this.field_147003_i, this.field_147009_r);
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   protected void func_146979_b(int par1, int par2)
/*  81:    */   {
/*  82: 74 */     List<String> tooltip = null;
/*  83: 75 */     GL11.glDisable(32826);
/*  84: 76 */     RenderHelper.disableStandardItemLighting();
/*  85: 77 */     GL11.glDisable(2896);
/*  86: 78 */     GL11.glDisable(2929);
/*  87: 79 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  88: 81 */     for (int i = 0; i < this.container.widgets.size(); i++)
/*  89:    */     {
/*  90: 82 */       this.mc.renderEngine.bindTexture(getWidgets());
/*  91: 83 */       ((IWidget)this.container.widgets.get(i)).renderForeground(this.mc.renderEngine, this, 0, 0);
/*  92: 85 */       if (isInArea(par1 - this.field_147003_i, par2 - this.field_147009_r, (IWidget)this.container.widgets.get(i)))
/*  93:    */       {
/*  94: 86 */         List<String> t = ((IWidget)this.container.widgets.get(i)).getToolTip();
/*  95: 88 */         if (t != null) {
/*  96: 89 */           tooltip = t;
/*  97:    */         }
/*  98:    */       }
/*  99:    */     }
/* 100: 94 */     if (tooltip != null)
/* 101:    */     {
/* 102: 95 */       drawHoveringText(tooltip, par1 - this.field_147003_i, par2 - this.field_147009_r, getFontRenderer());
/* 103: 96 */       GL11.glEnable(2896);
/* 104: 97 */       GL11.glEnable(2929);
/* 105: 98 */       RenderHelper.enableStandardItemLighting();
/* 106:    */     }
/* 107:101 */     RenderHelper.enableGUIStandardItemLighting();
/* 108:    */     
/* 109:103 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 110:104 */     GL11.glEnable(32826);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public boolean isInArea(int x, int y, IWidget w)
/* 114:    */   {
/* 115:108 */     return (x >= w.getX()) && (x < w.getX() + w.getW()) && (y >= w.getY()) && (y < w.getY() + w.getH());
/* 116:    */   }
/* 117:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.dynamicgui.DynamicGui
 * JD-Core Version:    0.7.0.1
 */