/*   1:    */ package com.rwtema.extrautils.worldgen.Underdark;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import cpw.mods.fml.relauncher.Side;
/*   5:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   6:    */ import net.minecraft.util.ChunkCoordinates;
/*   7:    */ import net.minecraft.util.Vec3;
/*   8:    */ import net.minecraft.world.World;
/*   9:    */ import net.minecraft.world.WorldProvider;
/*  10:    */ import net.minecraft.world.biome.WorldChunkManager;
/*  11:    */ import net.minecraft.world.chunk.IChunkProvider;
/*  12:    */ 
/*  13:    */ public class WorldProviderUnderdark
/*  14:    */   extends WorldProvider
/*  15:    */ {
/*  16: 14 */   private float[] colorsSunriseSunset = new float[4];
/*  17:    */   
/*  18:    */   public void registerWorldChunkManager()
/*  19:    */   {
/*  20: 22 */     this.worldChunkMgr = new WorldChunkManager(this.worldObj);
/*  21: 23 */     this.dimensionId = ExtraUtils.underdarkDimID;
/*  22: 24 */     this.hasNoSky = true;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public long getSeed()
/*  26:    */   {
/*  27: 29 */     return super.getSeed() + (ChunkProviderUnderdark.denyDecor ? 1 : 0);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public IChunkProvider createChunkGenerator()
/*  31:    */   {
/*  32: 34 */     return new ChunkProviderUnderdark(this.worldObj, this.worldObj.getSeed(), false);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public int getAverageGroundLevel()
/*  36:    */   {
/*  37: 41 */     return 81;
/*  38:    */   }
/*  39:    */   
/*  40:    */   @SideOnly(Side.CLIENT)
/*  41:    */   public boolean doesXZShowFog(int par1, int par2)
/*  42:    */   {
/*  43: 47 */     return true;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String getDimensionName()
/*  47:    */   {
/*  48: 52 */     return "Underdark";
/*  49:    */   }
/*  50:    */   
/*  51:    */   public boolean renderStars()
/*  52:    */   {
/*  53: 56 */     return false;
/*  54:    */   }
/*  55:    */   
/*  56:    */   @SideOnly(Side.CLIENT)
/*  57:    */   public float getStarBrightness(World world, float f)
/*  58:    */   {
/*  59: 61 */     return 0.0F;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public boolean renderClouds()
/*  63:    */   {
/*  64: 65 */     return false;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public boolean renderVoidFog()
/*  68:    */   {
/*  69: 69 */     return true;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public boolean renderEndSky()
/*  73:    */   {
/*  74: 73 */     return false;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public float setSunSize()
/*  78:    */   {
/*  79: 77 */     return 0.0F;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public float setMoonSize()
/*  83:    */   {
/*  84: 81 */     return 0.0F;
/*  85:    */   }
/*  86:    */   
/*  87:    */   @SideOnly(Side.CLIENT)
/*  88:    */   public boolean isSkyColored()
/*  89:    */   {
/*  90: 87 */     return false;
/*  91:    */   }
/*  92:    */   
/*  93:    */   @SideOnly(Side.CLIENT)
/*  94:    */   public double getVoidFogYFactor()
/*  95:    */   {
/*  96: 93 */     return 1.0D;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public boolean canRespawnHere()
/* 100:    */   {
/* 101: 98 */     return false;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean isSurfaceWorld()
/* 105:    */   {
/* 106:103 */     return false;
/* 107:    */   }
/* 108:    */   
/* 109:    */   @SideOnly(Side.CLIENT)
/* 110:    */   public float getCloudHeight()
/* 111:    */   {
/* 112:109 */     return 256.0F;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public boolean canCoordinateBeSpawn(int par1, int par2)
/* 116:    */   {
/* 117:114 */     return false;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public ChunkCoordinates getEntrancePortalLocation()
/* 121:    */   {
/* 122:119 */     return new ChunkCoordinates(50, 5, 0);
/* 123:    */   }
/* 124:    */   
/* 125:    */   protected void generateLightBrightnessTable()
/* 126:    */   {
/* 127:124 */     float f = 0.0F;
/* 128:126 */     for (int i = 0; i <= 15; i++)
/* 129:    */     {
/* 130:127 */       float p = i / 15.0F;
/* 131:128 */       float f1 = 1.0F - p;
/* 132:129 */       this.lightBrightnessTable[i] = (p / (f1 * 3.0F + 1.0F));
/* 133:131 */       if (this.lightBrightnessTable[i] < 0.2F) {
/* 134:132 */         this.lightBrightnessTable[i] *= this.lightBrightnessTable[i] / 0.2F;
/* 135:    */       }
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   @SideOnly(Side.CLIENT)
/* 140:    */   public String getWelcomeMessage()
/* 141:    */   {
/* 142:140 */     if ((this instanceof WorldProviderUnderdark)) {
/* 143:141 */       return "Entering the 'Deep Dark'";
/* 144:    */     }
/* 145:144 */     return null;
/* 146:    */   }
/* 147:    */   
/* 148:    */   @SideOnly(Side.CLIENT)
/* 149:    */   public float[] calcSunriseSunsetColors(float par1, float par2)
/* 150:    */   {
/* 151:150 */     return null;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public float calculateCelestialAngle(long par1, float par3)
/* 155:    */   {
/* 156:155 */     int j = 18000;
/* 157:156 */     float f1 = (j + par3) / 24000.0F - 0.25F;
/* 158:158 */     if (f1 < 0.0F) {
/* 159:159 */       f1 += 1.0F;
/* 160:    */     }
/* 161:162 */     if (f1 > 1.0F) {
/* 162:163 */       f1 -= 1.0F;
/* 163:    */     }
/* 164:166 */     float f2 = f1;
/* 165:167 */     f1 = 1.0F - (float)((Math.cos(f1 * 3.141592653589793D) + 1.0D) / 2.0D);
/* 166:168 */     f1 = f2 + (f1 - f2) / 3.0F;
/* 167:169 */     return f1;
/* 168:    */   }
/* 169:    */   
/* 170:    */   @SideOnly(Side.CLIENT)
/* 171:    */   public Vec3 getFogColor(float par1, float par2)
/* 172:    */   {
/* 173:175 */     return Vec3.createVectorHelper(9.999999974752427E-007D, 9.999999974752427E-007D, 9.999999974752427E-007D);
/* 174:    */   }
/* 175:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.worldgen.Underdark.WorldProviderUnderdark
 * JD-Core Version:    0.7.0.1
 */