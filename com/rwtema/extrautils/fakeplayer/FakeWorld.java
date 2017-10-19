/*   1:    */ package com.rwtema.extrautils.fakeplayer;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import net.minecraft.entity.Entity;
/*   5:    */ import net.minecraft.nbt.NBTTagCompound;
/*   6:    */ import net.minecraft.world.MinecraftException;
/*   7:    */ import net.minecraft.world.World;
/*   8:    */ import net.minecraft.world.WorldProvider;
/*   9:    */ import net.minecraft.world.chunk.IChunkProvider;
/*  10:    */ import net.minecraft.world.chunk.storage.IChunkLoader;
/*  11:    */ import net.minecraft.world.storage.IPlayerFileData;
/*  12:    */ import net.minecraft.world.storage.ISaveHandler;
/*  13:    */ import net.minecraft.world.storage.WorldInfo;
/*  14:    */ 
/*  15:    */ public class FakeWorld
/*  16:    */   extends World
/*  17:    */ {
/*  18:    */   public FakeWorld()
/*  19:    */   {
/*  20: 19 */     super(FakeSave.instance, "", null, FakeWorldProvider.instance, null);
/*  21:    */   }
/*  22:    */   
/*  23:    */   protected IChunkProvider createChunkProvider()
/*  24:    */   {
/*  25: 24 */     return null;
/*  26:    */   }
/*  27:    */   
/*  28:    */   protected int func_152379_p()
/*  29:    */   {
/*  30: 29 */     return 0;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Entity getEntityByID(int var1)
/*  34:    */   {
/*  35: 34 */     return null;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static class FakeSave
/*  39:    */     implements ISaveHandler
/*  40:    */   {
/*  41: 38 */     public static FakeSave instance = new FakeSave();
/*  42:    */     
/*  43:    */     public WorldInfo loadWorldInfo()
/*  44:    */     {
/*  45: 42 */       return FakeWorld.FakeWorldInfo.instance;
/*  46:    */     }
/*  47:    */     
/*  48:    */     public void checkSessionLock()
/*  49:    */       throws MinecraftException
/*  50:    */     {}
/*  51:    */     
/*  52:    */     public IChunkLoader getChunkLoader(WorldProvider var1)
/*  53:    */     {
/*  54: 52 */       return null;
/*  55:    */     }
/*  56:    */     
/*  57:    */     public void saveWorldInfoWithPlayer(WorldInfo var1, NBTTagCompound var2) {}
/*  58:    */     
/*  59:    */     public void saveWorldInfo(WorldInfo var1) {}
/*  60:    */     
/*  61:    */     public IPlayerFileData getSaveHandler()
/*  62:    */     {
/*  63: 67 */       return null;
/*  64:    */     }
/*  65:    */     
/*  66:    */     public void flush() {}
/*  67:    */     
/*  68:    */     public File getWorldDirectory()
/*  69:    */     {
/*  70: 77 */       return null;
/*  71:    */     }
/*  72:    */     
/*  73:    */     public File getMapFileFromName(String var1)
/*  74:    */     {
/*  75: 82 */       return null;
/*  76:    */     }
/*  77:    */     
/*  78:    */     public String getWorldDirectoryName()
/*  79:    */     {
/*  80: 87 */       return null;
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static class FakeWorldInfo
/*  85:    */     extends WorldInfo
/*  86:    */   {
/*  87: 92 */     public static FakeWorldInfo instance = new FakeWorldInfo();
/*  88:    */     
/*  89:    */     public boolean isInitialized()
/*  90:    */     {
/*  91: 96 */       return true;
/*  92:    */     }
/*  93:    */     
/*  94:    */     public int getVanillaDimension()
/*  95:    */     {
/*  96:101 */       return 0;
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   public static class FakeWorldProvider
/* 101:    */     extends WorldProvider
/* 102:    */   {
/* 103:106 */     public static FakeWorldProvider instance = new FakeWorldProvider();
/* 104:    */     
/* 105:    */     public void calculateInitialWeather() {}
/* 106:    */     
/* 107:    */     public String getDimensionName()
/* 108:    */     {
/* 109:115 */       return "FAKE";
/* 110:    */     }
/* 111:    */     
/* 112:    */     protected void registerWorldChunkManager() {}
/* 113:    */   }
/* 114:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.fakeplayer.FakeWorld
 * JD-Core Version:    0.7.0.1
 */