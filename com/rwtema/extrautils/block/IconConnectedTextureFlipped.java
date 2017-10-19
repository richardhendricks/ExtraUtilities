/*  1:   */ package com.rwtema.extrautils.block;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.relauncher.Side;
/*  4:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  5:   */ 
/*  6:   */ @SideOnly(Side.CLIENT)
/*  7:   */ public class IconConnectedTextureFlipped
/*  8:   */   extends IconConnectedTexture
/*  9:   */ {
/* 10:   */   public IconConnectedTextureFlipped(IconConnectedTexture icon)
/* 11:   */   {
/* 12: 9 */     super(icon.icons[3], icon.icons[1], icon.icons[2], icon.icons[0], icon.icons[4]);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public float getMinV()
/* 16:   */   {
/* 17:17 */     return super.getMaxV();
/* 18:   */   }
/* 19:   */   
/* 20:   */   public float getMaxV()
/* 21:   */   {
/* 22:25 */     return super.getMinV();
/* 23:   */   }
/* 24:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.IconConnectedTextureFlipped
 * JD-Core Version:    0.7.0.1
 */