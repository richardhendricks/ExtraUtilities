/*  1:   */ package com.rwtema.extrautils.tileentity.enderconstructor;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.dynamicgui.DynamicContainer;
/*  4:   */ import com.rwtema.extrautils.dynamicgui.DynamicGui;
/*  5:   */ import cpw.mods.fml.relauncher.Side;
/*  6:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  7:   */ import net.minecraft.util.ResourceLocation;
/*  8:   */ 
/*  9:   */ @SideOnly(Side.CLIENT)
/* 10:   */ public class DynamicGuiEnderConstructor
/* 11:   */   extends DynamicGui
/* 12:   */ {
/* 13:11 */   private static final ResourceLocation texBackground = new ResourceLocation("extrautils", "textures/guiBase2.png");
/* 14:12 */   private static final ResourceLocation texWidgets = new ResourceLocation("extrautils", "textures/guiWidget2.png");
/* 15:   */   
/* 16:   */   public DynamicGuiEnderConstructor(DynamicContainer container)
/* 17:   */   {
/* 18:15 */     super(container);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public ResourceLocation getBackground()
/* 22:   */   {
/* 23:19 */     return texBackground;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public ResourceLocation getWidgets()
/* 27:   */   {
/* 28:23 */     return texWidgets;
/* 29:   */   }
/* 30:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.enderconstructor.DynamicGuiEnderConstructor
 * JD-Core Version:    0.7.0.1
 */