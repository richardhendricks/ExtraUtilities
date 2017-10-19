/*   1:    */ package com.rwtema.extrautils.worldgen.endoftime;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.ExtraUtilsMod;
/*   5:    */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*   6:    */ import com.rwtema.extrautils.IClientCode;
/*   7:    */ import cpw.mods.fml.relauncher.Side;
/*   8:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   9:    */ import net.minecraft.block.Block;
/*  10:    */ import net.minecraft.block.material.Material;
/*  11:    */ import net.minecraft.entity.Entity;
/*  12:    */ import net.minecraft.util.ChunkCoordinates;
/*  13:    */ import net.minecraft.util.Vec3;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ import net.minecraft.world.WorldProvider;
/*  16:    */ import net.minecraft.world.biome.WorldChunkManager;
/*  17:    */ import net.minecraft.world.chunk.IChunkProvider;
/*  18:    */ import net.minecraft.world.storage.WorldInfo;
/*  19:    */ 
/*  20:    */ public class WorldProviderEndOfTime
/*  21:    */   extends WorldProvider
/*  22:    */   implements IClientCode
/*  23:    */ {
/*  24:    */   public static final float celestialAngle = 0.5F;
/*  25:    */   public static final int skyColor = 1052736;
/*  26:    */   
/*  27:    */   public WorldProviderEndOfTime()
/*  28:    */   {
/*  29: 19 */     ExtraUtilsMod.proxy.exectuteClientCode(this);
/*  30:    */   }
/*  31:    */   
/*  32:    */   @SideOnly(Side.CLIENT)
/*  33:    */   public void exectuteClientCode()
/*  34:    */   {
/*  35: 24 */     setCloudRenderer(RenderHandlersEndOfTime.nullRenderer);
/*  36: 25 */     setSkyRenderer(RenderHandlersEndOfTime.skyRenderer);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void registerWorldChunkManager()
/*  40:    */   {
/*  41: 31 */     this.worldChunkMgr = new WorldChunkManager(this.worldObj);
/*  42: 32 */     this.dimensionId = ExtraUtils.endoftimeDimID;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public IChunkProvider createChunkGenerator()
/*  46:    */   {
/*  47: 37 */     return new ChunkProviderEndOfTime(this.worldObj, this.worldObj.getSeed());
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean canCoordinateBeSpawn(int p_76566_1_, int p_76566_2_)
/*  51:    */   {
/*  52: 42 */     return this.worldObj.getTopBlock(p_76566_1_, p_76566_2_).getMaterial().blocksMovement();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public ChunkCoordinates getEntrancePortalLocation()
/*  56:    */   {
/*  57: 48 */     WorldInfo worldInfo = this.worldObj.getWorldInfo();
/*  58: 49 */     if (worldInfo.getSpawnY() != 64) {
/*  59: 50 */       worldInfo.setSpawnPosition(worldInfo.getSpawnX(), 64, worldInfo.getSpawnZ());
/*  60:    */     }
/*  61: 51 */     return new ChunkCoordinates(worldInfo.getSpawnX(), worldInfo.getSpawnY(), worldInfo.getSpawnZ());
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getAverageGroundLevel()
/*  65:    */   {
/*  66: 56 */     return 64;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String getDimensionName()
/*  70:    */   {
/*  71: 61 */     return "The Last Millenium";
/*  72:    */   }
/*  73:    */   
/*  74:    */   @SideOnly(Side.CLIENT)
/*  75:    */   public double getVoidFogYFactor()
/*  76:    */   {
/*  77: 67 */     return 1.0D;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean canRespawnHere()
/*  81:    */   {
/*  82: 72 */     return true;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public boolean isSurfaceWorld()
/*  86:    */   {
/*  87: 77 */     return true;
/*  88:    */   }
/*  89:    */   
/*  90:    */   @SideOnly(Side.CLIENT)
/*  91:    */   public float getCloudHeight()
/*  92:    */   {
/*  93: 83 */     return 256.0F;
/*  94:    */   }
/*  95:    */   
/*  96:    */   protected void generateLightBrightnessTable()
/*  97:    */   {
/*  98: 90 */     float f = 0.0F;
/*  99: 92 */     for (int i = 0; i <= 15; i++)
/* 100:    */     {
/* 101: 94 */       float f1 = 1.0F - i / 15.0F;
/* 102: 95 */       this.lightBrightnessTable[i] = ((1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f);
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   @SideOnly(Side.CLIENT)
/* 107:    */   public String getWelcomeMessage()
/* 108:    */   {
/* 109:103 */     return "Travelling to the End of Time";
/* 110:    */   }
/* 111:    */   
/* 112:    */   public ChunkCoordinates getSpawnPoint()
/* 113:    */   {
/* 114:108 */     return getEntrancePortalLocation();
/* 115:    */   }
/* 116:    */   
/* 117:    */   public ChunkCoordinates getRandomizedSpawnPoint()
/* 118:    */   {
/* 119:113 */     ChunkCoordinates chunkCoordinates = getEntrancePortalLocation();
/* 120:114 */     chunkCoordinates.posY = this.worldObj.getTopSolidOrLiquidBlock(chunkCoordinates.posX, chunkCoordinates.posZ);
/* 121:115 */     return chunkCoordinates;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public float calculateCelestialAngle(long p_76563_1_, float p_76563_3_)
/* 125:    */   {
/* 126:123 */     return 0.5F;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public float getCurrentMoonPhaseFactor()
/* 130:    */   {
/* 131:128 */     return 0.0F;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public double getHorizon()
/* 135:    */   {
/* 136:134 */     return 3333.0D;
/* 137:    */   }
/* 138:    */   
/* 139:    */   @SideOnly(Side.CLIENT)
/* 140:    */   public Vec3 getSkyColor(Entity p_72833_1_, float p_72833_2_)
/* 141:    */   {
/* 142:141 */     int color = 65538;
/* 143:142 */     float r = (color >> 16 & 0xFF) / 255.0F;
/* 144:143 */     float g = (color >> 8 & 0xFF) / 255.0F;
/* 145:144 */     float b = (color & 0xFF) / 255.0F;
/* 146:145 */     float f7 = this.worldObj.getRainStrength(p_72833_2_);
/* 147:149 */     if (f7 > 0.0F)
/* 148:    */     {
/* 149:151 */       float f8 = (r * 0.3F + g * 0.59F + b * 0.11F) * 0.6F;
/* 150:152 */       float f9 = 1.0F - f7 * 0.75F;
/* 151:153 */       r = r * f9 + f8 * (1.0F - f9);
/* 152:154 */       g = g * f9 + f8 * (1.0F - f9);
/* 153:155 */       b = b * f9 + f8 * (1.0F - f9);
/* 154:    */     }
/* 155:158 */     float f8 = this.worldObj.getWeightedThunderStrength(p_72833_2_);
/* 156:160 */     if (f8 > 0.0F)
/* 157:    */     {
/* 158:162 */       float f9 = (r * 0.3F + g * 0.59F + b * 0.11F) * 0.2F;
/* 159:163 */       float f10 = 1.0F - f8 * 0.75F;
/* 160:164 */       r = r * f10 + f9 * (1.0F - f10);
/* 161:165 */       g = g * f10 + f9 * (1.0F - f10);
/* 162:166 */       b = b * f10 + f9 * (1.0F - f10);
/* 163:    */     }
/* 164:169 */     if (this.worldObj.lastLightningBolt > 0)
/* 165:    */     {
/* 166:171 */       float f9 = this.worldObj.lastLightningBolt - p_72833_2_;
/* 167:173 */       if (f9 > 1.0F) {
/* 168:175 */         f9 = 1.0F;
/* 169:    */       }
/* 170:178 */       f9 *= 0.45F;
/* 171:179 */       r = r * (1.0F - f9) + 0.8F * f9;
/* 172:180 */       g = g * (1.0F - f9) + 0.8F * f9;
/* 173:181 */       b = b * (1.0F - f9) + 1.0F * f9;
/* 174:    */     }
/* 175:184 */     return Vec3.createVectorHelper(r, g, b);
/* 176:    */   }
/* 177:    */   
/* 178:    */   public int getMoonPhase(long p_76559_1_)
/* 179:    */   {
/* 180:189 */     return 4;
/* 181:    */   }
/* 182:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.worldgen.endoftime.WorldProviderEndOfTime
 * JD-Core Version:    0.7.0.1
 */