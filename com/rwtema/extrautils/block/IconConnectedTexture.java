/*  1:   */ package com.rwtema.extrautils.block;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.relauncher.Side;
/*  4:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  5:   */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  6:   */ import net.minecraft.util.IIcon;
/*  7:   */ 
/*  8:   */ @SideOnly(Side.CLIENT)
/*  9:   */ public class IconConnectedTexture
/* 10:   */   implements IIcon
/* 11:   */ {
/* 12:10 */   public final IIcon[] icons = new IIcon[5];
/* 13:   */   private int n;
/* 14:   */   
/* 15:   */   public IconConnectedTexture(IIconRegister r, String texture)
/* 16:   */   {
/* 17:14 */     this(r.registerIcon(texture), r.registerIcon(texture + "_vertical"), r.registerIcon(texture + "_horizontal"), r.registerIcon(texture + "_corners"), r.registerIcon(texture + "_anticorners"));
/* 18:   */   }
/* 19:   */   
/* 20:   */   public IconConnectedTexture(IIcon baseIcon, IIcon verticalIcon, IIcon horizontalIcon, IIcon cornerIcon, IIcon anticornerIcon)
/* 21:   */   {
/* 22:18 */     this.icons[0] = cornerIcon;
/* 23:19 */     this.icons[1] = verticalIcon;
/* 24:20 */     this.icons[2] = horizontalIcon;
/* 25:21 */     this.icons[3] = baseIcon;
/* 26:22 */     this.icons[4] = anticornerIcon;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void setType(int i)
/* 30:   */   {
/* 31:26 */     this.n = i;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void resetType()
/* 35:   */   {
/* 36:30 */     setType(0);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public float getMinU()
/* 40:   */   {
/* 41:38 */     return this.icons[this.n].getMinU();
/* 42:   */   }
/* 43:   */   
/* 44:   */   public float getMaxU()
/* 45:   */   {
/* 46:46 */     return this.icons[this.n].getMaxU();
/* 47:   */   }
/* 48:   */   
/* 49:   */   public float getInterpolatedU(double par1)
/* 50:   */   {
/* 51:55 */     float f = getMaxU() - getMinU();
/* 52:56 */     return getMinU() + f * ((float)par1 / 16.0F);
/* 53:   */   }
/* 54:   */   
/* 55:   */   public float getMinV()
/* 56:   */   {
/* 57:64 */     return this.icons[this.n].getMinV();
/* 58:   */   }
/* 59:   */   
/* 60:   */   public float getMaxV()
/* 61:   */   {
/* 62:72 */     return this.icons[this.n].getMaxV();
/* 63:   */   }
/* 64:   */   
/* 65:   */   public float getInterpolatedV(double par1)
/* 66:   */   {
/* 67:81 */     float f = getMaxV() - getMinV();
/* 68:82 */     return getMinV() + f * ((float)par1 / 16.0F);
/* 69:   */   }
/* 70:   */   
/* 71:   */   public String getIconName()
/* 72:   */   {
/* 73:87 */     return this.icons[this.n].getIconName();
/* 74:   */   }
/* 75:   */   
/* 76:   */   @SideOnly(Side.CLIENT)
/* 77:   */   public int getIconWidth()
/* 78:   */   {
/* 79:93 */     return this.icons[this.n].getIconWidth();
/* 80:   */   }
/* 81:   */   
/* 82:   */   @SideOnly(Side.CLIENT)
/* 83:   */   public int getIconHeight()
/* 84:   */   {
/* 85:99 */     return this.icons[this.n].getIconHeight();
/* 86:   */   }
/* 87:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.IconConnectedTexture
 * JD-Core Version:    0.7.0.1
 */