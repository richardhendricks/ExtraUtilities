/*  1:   */ package com.rwtema.extrautils.texture;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.relauncher.Side;
/*  4:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  5:   */ import net.minecraft.util.IIcon;
/*  6:   */ 
/*  7:   */ @SideOnly(Side.CLIENT)
/*  8:   */ public class IconMultiIcon
/*  9:   */   implements IIcon
/* 10:   */ {
/* 11:   */   public int grid_x;
/* 12:   */   public int grid_y;
/* 13:   */   public int grid_w;
/* 14:   */   public int grid_h;
/* 15:   */   public IIcon icon;
/* 16:   */   
/* 17:   */   public IconMultiIcon(IIcon icon, int grid_x, int grid_y, int grid_w, int grid_h)
/* 18:   */   {
/* 19:14 */     this.grid_x = grid_x;
/* 20:15 */     this.grid_y = grid_y;
/* 21:16 */     this.grid_w = grid_w;
/* 22:17 */     this.grid_h = grid_h;
/* 23:18 */     this.icon = icon;
/* 24:   */   }
/* 25:   */   
/* 26:   */   @SideOnly(Side.CLIENT)
/* 27:   */   public int getIconWidth()
/* 28:   */   {
/* 29:24 */     return this.icon.getIconWidth() / this.grid_w;
/* 30:   */   }
/* 31:   */   
/* 32:   */   @SideOnly(Side.CLIENT)
/* 33:   */   public int getIconHeight()
/* 34:   */   {
/* 35:30 */     return this.icon.getIconHeight() / this.grid_h;
/* 36:   */   }
/* 37:   */   
/* 38:   */   @SideOnly(Side.CLIENT)
/* 39:   */   public float getMinU()
/* 40:   */   {
/* 41:36 */     return this.icon.getInterpolatedU(this.grid_x / this.grid_w * 16.0D);
/* 42:   */   }
/* 43:   */   
/* 44:   */   @SideOnly(Side.CLIENT)
/* 45:   */   public float getMaxU()
/* 46:   */   {
/* 47:42 */     return this.icon.getInterpolatedU((this.grid_x + 1) / this.grid_w * 16.0D);
/* 48:   */   }
/* 49:   */   
/* 50:   */   @SideOnly(Side.CLIENT)
/* 51:   */   public float getInterpolatedU(double par1)
/* 52:   */   {
/* 53:48 */     float f = getMaxU() - getMinU();
/* 54:49 */     return getMinU() + f * (float)par1 / 16.0F;
/* 55:   */   }
/* 56:   */   
/* 57:   */   @SideOnly(Side.CLIENT)
/* 58:   */   public float getMinV()
/* 59:   */   {
/* 60:55 */     return this.icon.getInterpolatedV(this.grid_y / this.grid_h * 16.0D);
/* 61:   */   }
/* 62:   */   
/* 63:   */   @SideOnly(Side.CLIENT)
/* 64:   */   public float getMaxV()
/* 65:   */   {
/* 66:61 */     return this.icon.getInterpolatedV((this.grid_y + 1) / this.grid_h * 16.0D);
/* 67:   */   }
/* 68:   */   
/* 69:   */   @SideOnly(Side.CLIENT)
/* 70:   */   public float getInterpolatedV(double par1)
/* 71:   */   {
/* 72:67 */     float f = getMaxV() - getMinV();
/* 73:68 */     return getMinV() + f * (float)par1 / 16.0F;
/* 74:   */   }
/* 75:   */   
/* 76:   */   @SideOnly(Side.CLIENT)
/* 77:   */   public String getIconName()
/* 78:   */   {
/* 79:75 */     return this.icon.getIconName();
/* 80:   */   }
/* 81:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.texture.IconMultiIcon
 * JD-Core Version:    0.7.0.1
 */