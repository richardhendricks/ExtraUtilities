/*   1:    */ package com.rwtema.extrautils.texture;
/*   2:    */ 
/*   3:    */ import cpw.mods.fml.relauncher.Side;
/*   4:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   5:    */ import java.awt.image.BufferedImage;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.io.InputStream;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.imageio.ImageIO;
/*  11:    */ import net.minecraft.client.resources.IResource;
/*  12:    */ import net.minecraft.client.resources.IResourceManager;
/*  13:    */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*  14:    */ import net.minecraft.util.IIcon;
/*  15:    */ import net.minecraft.util.ResourceLocation;
/*  16:    */ import net.minecraftforge.fluids.Fluid;
/*  17:    */ import net.minecraftforge.fluids.FluidStack;
/*  18:    */ 
/*  19:    */ @SideOnly(Side.CLIENT)
/*  20:    */ public class LiquidColorRegistry
/*  21:    */   implements IResourceManagerReloadListener
/*  22:    */ {
/*  23:    */   public static final int emptyColor = 16777215;
/*  24:    */   public static final int defaultColor = 16777215;
/*  25: 23 */   public static Map<IIcon, Integer> m = new HashMap();
/*  26:    */   private static IResourceManager resourcemanager;
/*  27:    */   
/*  28:    */   public static void reset()
/*  29:    */   {
/*  30: 27 */     m.clear();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static int getIconColor(IIcon icon, int defaultColor)
/*  34:    */   {
/*  35: 31 */     if (icon == null) {
/*  36: 32 */       return defaultColor;
/*  37:    */     }
/*  38: 35 */     if (m.containsKey(icon))
/*  39:    */     {
/*  40: 36 */       Integer col = (Integer)m.get(icon);
/*  41: 38 */       if (col == null) {
/*  42: 39 */         return defaultColor;
/*  43:    */       }
/*  44: 41 */       return col.intValue();
/*  45:    */     }
/*  46: 44 */     String t = icon.getIconName();
/*  47:    */     try
/*  48:    */     {
/*  49: 48 */       col = readIconCol(t);
/*  50:    */     }
/*  51:    */     catch (IOException e)
/*  52:    */     {
/*  53: 50 */       col = null;
/*  54:    */     }
/*  55: 53 */     if (col == null)
/*  56:    */     {
/*  57: 54 */       m.put(icon, null);
/*  58: 55 */       return defaultColor;
/*  59:    */     }
/*  60: 57 */     float r = (col.intValue() >> 16 & 0xFF) / 255.0F * ((defaultColor >> 16 & 0xFF) / 255.0F);
/*  61: 58 */     float g = (col.intValue() >> 8 & 0xFF) / 255.0F * ((defaultColor >> 8 & 0xFF) / 255.0F);
/*  62: 59 */     float b = (col.intValue() & 0xFF) / 255.0F * ((defaultColor & 0xFF) / 255.0F);
/*  63: 60 */     Integer col = Integer.valueOf((int)(r * 255.0F) << 16 | (int)(g * 255.0F) << 8 | (int)(b * 255.0F));
/*  64: 61 */     m.put(icon, col);
/*  65: 62 */     return col.intValue();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static Integer readIconCol(String t)
/*  69:    */     throws IOException
/*  70:    */   {
/*  71: 68 */     String s1 = "minecraft";
/*  72: 69 */     String s2 = t;
/*  73: 70 */     int i = t.indexOf(':');
/*  74: 72 */     if (i >= 0)
/*  75:    */     {
/*  76: 73 */       s2 = t.substring(i + 1, t.length());
/*  77: 75 */       if (i > 1) {
/*  78: 76 */         s1 = t.substring(0, i);
/*  79:    */       }
/*  80:    */     }
/*  81: 80 */     s1 = s1.toLowerCase();
/*  82: 81 */     s2 = "textures/blocks/" + s2 + ".png";
/*  83: 82 */     IResource resource = resourcemanager.getResource(new ResourceLocation(s1, s2));
/*  84: 83 */     InputStream inputstream = resource.getInputStream();
/*  85: 84 */     BufferedImage bufferedimage = ImageIO.read(inputstream);
/*  86: 85 */     int height = bufferedimage.getHeight();
/*  87: 86 */     int width = bufferedimage.getWidth();
/*  88: 87 */     int[] aint = new int[height * width];
/*  89: 88 */     bufferedimage.getRGB(0, 0, width, height, aint, 0, width);
/*  90: 90 */     if (aint.length == 0) {
/*  91: 91 */       return null;
/*  92:    */     }
/*  93: 94 */     float[] lab = new float[3];
/*  94:    */     
/*  95: 96 */     CIELabColorSpace colorSpace = CIELabColorSpace.getInstance();
/*  96: 98 */     for (int l : aint)
/*  97:    */     {
/*  98: 99 */       float[] f = colorSpace.fromRGB(l);
/*  99:100 */       for (int j = 0; j < 3; j++) {
/* 100:101 */         lab[j] += f[j];
/* 101:    */       }
/* 102:    */     }
/* 103:105 */     for (int j = 0; j < 3; j++) {
/* 104:106 */       lab[j] /= aint.length;
/* 105:    */     }
/* 106:108 */     float[] col = colorSpace.toRGB(lab);
/* 107:    */     
/* 108:110 */     return Integer.valueOf(0xFF000000 | (int)(col[0] * 255.0F) << 16 | (int)(col[1] * 255.0F) << 8 | (int)(col[2] * 255.0F));
/* 109:    */   }
/* 110:    */   
/* 111:    */   public static int getFluidColor(FluidStack fluid)
/* 112:    */   {
/* 113:114 */     if ((fluid == null) || (fluid.getFluid() == null)) {
/* 114:115 */       return 16777215;
/* 115:    */     }
/* 116:118 */     if (fluid.getFluid().getIcon(fluid) == null) {
/* 117:119 */       return 16777215;
/* 118:    */     }
/* 119:122 */     return getIconColor(fluid.getFluid().getIcon(fluid), fluid.getFluid().getColor(fluid));
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void onResourceManagerReload(IResourceManager resourcemanager)
/* 123:    */   {
/* 124:127 */     reset();
/* 125:128 */     resourcemanager = resourcemanager;
/* 126:    */   }
/* 127:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.texture.LiquidColorRegistry
 * JD-Core Version:    0.7.0.1
 */