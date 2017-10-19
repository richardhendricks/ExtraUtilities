/*   1:    */ package com.rwtema.extrautils.texture;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Lists;
/*   4:    */ import cpw.mods.fml.relauncher.Side;
/*   5:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   6:    */ import java.awt.image.BufferedImage;
/*   7:    */ import java.io.IOException;
/*   8:    */ import java.io.InputStream;
/*   9:    */ import java.util.List;
/*  10:    */ import javax.imageio.ImageIO;
/*  11:    */ import net.minecraft.client.Minecraft;
/*  12:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  13:    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*  14:    */ import net.minecraft.client.renderer.texture.TextureMap;
/*  15:    */ import net.minecraft.client.resources.IResource;
/*  16:    */ import net.minecraft.client.resources.IResourceManager;
/*  17:    */ import net.minecraft.client.resources.data.AnimationMetadataSection;
/*  18:    */ import net.minecraft.client.settings.GameSettings;
/*  19:    */ import net.minecraft.util.IIcon;
/*  20:    */ import net.minecraft.util.ResourceLocation;
/*  21:    */ 
/*  22:    */ @SideOnly(Side.CLIENT)
/*  23:    */ public class TextureMultiIcon
/*  24:    */   extends TextureAtlasSprite
/*  25:    */ {
/*  26:    */   public int grid_x;
/*  27:    */   public int grid_y;
/*  28:    */   public int grid_w;
/*  29:    */   public int grid_h;
/*  30:    */   public String iconName;
/*  31:    */   
/*  32:    */   public TextureMultiIcon(String par1Str, int grid_x, int grid_y, int grid_w, int grid_h)
/*  33:    */   {
/*  34: 27 */     super(par1Str + "_" + grid_x + "_" + grid_y);
/*  35: 28 */     this.iconName = par1Str;
/*  36: 29 */     if (this.iconName.contains(":")) {
/*  37: 30 */       this.iconName = this.iconName.substring(1 + this.iconName.indexOf(":"), this.iconName.length());
/*  38:    */     }
/*  39: 32 */     this.grid_x = grid_x;
/*  40: 33 */     this.grid_y = grid_y;
/*  41: 34 */     this.grid_w = grid_w;
/*  42: 35 */     this.grid_h = grid_h;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public static IIcon registerGridIcon(IIconRegister register, String texture, int grid_x, int grid_y, int w, int h)
/*  46:    */   {
/*  47: 39 */     String entry = texture + "_" + grid_x + "_" + grid_y;
/*  48: 40 */     TextureAtlasSprite result = ((TextureMap)register).getTextureExtry(entry);
/*  49: 42 */     if (result == null)
/*  50:    */     {
/*  51: 43 */       result = new TextureMultiIcon(texture, grid_x, grid_y, w, h);
/*  52: 44 */       ((TextureMap)register).setTextureEntry(result.getIconName(), result);
/*  53:    */     }
/*  54: 47 */     return result;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public boolean hasAnimationMetadata()
/*  58:    */   {
/*  59: 52 */     return false;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int[][] func_147965_a(int par1)
/*  63:    */   {
/*  64: 57 */     return (int[][])this.framesTextureData.get(par1);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void func_147964_a(BufferedImage[] p_147964_1_, AnimationMetadataSection p_147964_2_, boolean p_147964_3_)
/*  68:    */   {
/*  69: 62 */     throw new RuntimeException("Likely Optifine error. TextureAtlasSprite.loadSprite(BufferedImage[] p_147964_1_, AnimationMetadataSection p_147964_2_, boolean p_147964_3_) called after TextureAtlasSprite.load(IResourceManager manager, ResourceLocation location) returned false (which is the correct return value for successful loading).\nThis is not supposed to happen and will result in a missing texture.\nThis is likely an Optifine error.");
/*  70:    */   }
/*  71:    */   
/*  72: 69 */   boolean successfullyLoad = false;
/*  73:    */   
/*  74:    */   public void copyFrom(TextureAtlasSprite par1TextureAtlasSprite)
/*  75:    */   {
/*  76: 73 */     if (this.successfullyLoad)
/*  77:    */     {
/*  78: 74 */       if ("missingno".equals(par1TextureAtlasSprite.getIconName())) {
/*  79: 75 */         throw new RuntimeException("Likely Optifine error. Please only report if it occurs without Optifine installed:\nA Sprite with custom loading was not placed in the texture sheet despite TextureMultiIcon.load(IResourceManager manager, ResourceLocation location) completing successfully and returning false (which is the correct return value for successful loading).\nThis should not happen.");
/*  80:    */       }
/*  81:    */     }
/*  82:    */     else {
/*  83: 80 */       super.copyFrom(par1TextureAtlasSprite);
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean load(IResourceManager manager, ResourceLocation location)
/*  88:    */   {
/*  89: 85 */     String altPath = "textures/blocks/" + this.iconName + ".png";
/*  90: 87 */     if (!(this.iconName + "_" + this.grid_x + "_" + this.grid_y).equals(location.getResourcePath())) {
/*  91: 88 */       throw new RuntimeException("Likely Optifine error. Please only report if it occurs without Optifine installed:\nTextureAtlasSprite.load(IResourceManager manager, ResourceLocation location) called with bad parameters.\nExpected resource path " + this.iconName + "_" + this.grid_x + "_" + this.grid_y + ", recieved " + location.getResourcePath() + ".\n");
/*  92:    */     }
/*  93: 93 */     ResourceLocation altLocation = new ResourceLocation(location.getResourceDomain(), altPath);
/*  94:    */     try
/*  95:    */     {
/*  96: 96 */       altLoadSprite(manager.getResource(altLocation));
/*  97:    */     }
/*  98:    */     catch (IOException e)
/*  99:    */     {
/* 100: 98 */       e.printStackTrace();
/* 101: 99 */       this.successfullyLoad = false;
/* 102:100 */       return true;
/* 103:    */     }
/* 104:102 */     this.successfullyLoad = true;
/* 105:103 */     return false;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location)
/* 109:    */   {
/* 110:107 */     return true;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void altLoadSprite(IResource par1Resource)
/* 114:    */     throws IOException
/* 115:    */   {
/* 116:111 */     setFramesTextureData(Lists.newArrayList());
/* 117:112 */     this.frameCounter = 0;
/* 118:113 */     this.tickCounter = 0;
/* 119:114 */     InputStream inputstream = par1Resource.getInputStream();
/* 120:115 */     BufferedImage bufferedimage = ImageIO.read(inputstream);
/* 121:116 */     this.height = (bufferedimage.getHeight() / this.grid_h);
/* 122:117 */     this.width = (bufferedimage.getWidth() / this.grid_w);
/* 123:119 */     if ((this.height < this.grid_h) || (this.width < this.grid_w)) {
/* 124:120 */       throw new RuntimeException("Texture too small, must be at least " + this.grid_w + " pixels wide and " + this.grid_h + " pixels tall");
/* 125:    */     }
/* 126:123 */     if ((this.grid_x < 0) || (this.grid_x >= this.grid_w)) {
/* 127:124 */       throw new RuntimeException("GridTextureIcon called with an invalid grid_x");
/* 128:    */     }
/* 129:127 */     if ((this.grid_y < 0) || (this.grid_y >= this.grid_h)) {
/* 130:128 */       throw new RuntimeException("GridTextureIcon called with an invalid grid_y");
/* 131:    */     }
/* 132:131 */     int[] aint = new int[this.height * this.width];
/* 133:132 */     bufferedimage.getRGB(this.grid_x * this.width, this.grid_y * this.height, this.width, this.height, aint, 0, this.width);
/* 134:134 */     if (this.height != this.width) {
/* 135:135 */       throw new RuntimeException("broken aspect ratio, must be in ratio: " + this.grid_w + ":" + this.grid_h);
/* 136:    */     }
/* 137:138 */     this.framesTextureData.add(prepareAnisotropicFiltering(aint, this.width, this.height, Minecraft.getMinecraft().gameSettings.mipmapLevels, Minecraft.getMinecraft().gameSettings.anisotropicFiltering > 1.0F));
/* 138:    */   }
/* 139:    */   
/* 140:    */   private int[][] prepareAnisotropicFiltering(int[] p_147960_1_, int p_147960_2_, int p_147960_3_, int mipMap, boolean useAnisotropicFiltering)
/* 141:    */   {
/* 142:143 */     int[][] aint1 = new int[mipMap + 1][];
/* 143:144 */     aint1[0] = p_147960_1_;
/* 144:145 */     return aint1;
/* 145:    */   }
/* 146:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.texture.TextureMultiIcon
 * JD-Core Version:    0.7.0.1
 */