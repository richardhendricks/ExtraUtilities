/*  1:   */ package com.rwtema.extrautils.worldgen.endoftime;
/*  2:   */ 
/*  3:   */ import java.util.Arrays;
/*  4:   */ import java.util.List;
/*  5:   */ import net.minecraft.entity.EnumCreatureType;
/*  6:   */ import net.minecraft.util.IProgressUpdate;
/*  7:   */ import net.minecraft.world.ChunkPosition;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ import net.minecraft.world.biome.BiomeGenBase;
/* 10:   */ import net.minecraft.world.chunk.Chunk;
/* 11:   */ import net.minecraft.world.chunk.IChunkProvider;
/* 12:   */ 
/* 13:   */ public class ChunkProviderEndOfTime
/* 14:   */   implements IChunkProvider
/* 15:   */ {
/* 16:   */   World worldObj;
/* 17:   */   
/* 18:   */   public ChunkProviderEndOfTime(World worldObj, long seed)
/* 19:   */   {
/* 20:19 */     this.worldObj = worldObj;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public boolean chunkExists(int p_73149_1_, int p_73149_2_)
/* 24:   */   {
/* 25:24 */     return true;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Chunk provideChunk(int x, int z)
/* 29:   */   {
/* 30:29 */     Chunk chunk = new Chunk(this.worldObj, x, z);
/* 31:   */     
/* 32:31 */     Arrays.fill(chunk.getBiomeArray(), (byte)BiomeGenBase.plains.biomeID);
/* 33:32 */     chunk.generateSkylightMap();
/* 34:33 */     chunk.isTerrainPopulated = true;
/* 35:34 */     chunk.isLightPopulated = true;
/* 36:35 */     chunk.isModified = true;
/* 37:36 */     return chunk;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public Chunk loadChunk(int p_73158_1_, int p_73158_2_)
/* 41:   */   {
/* 42:41 */     return provideChunk(p_73158_1_, p_73158_2_);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void populate(IChunkProvider provider, int x, int z) {}
/* 46:   */   
/* 47:   */   public boolean saveChunks(boolean p_73151_1_, IProgressUpdate p_73151_2_)
/* 48:   */   {
/* 49:51 */     return true;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public boolean unloadQueuedChunks()
/* 53:   */   {
/* 54:56 */     return false;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public boolean canSave()
/* 58:   */   {
/* 59:61 */     return true;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public String makeString()
/* 63:   */   {
/* 64:66 */     return "EoTLevelSource";
/* 65:   */   }
/* 66:   */   
/* 67:   */   public List getPossibleCreatures(EnumCreatureType p_73155_1_, int p_73155_2_, int p_73155_3_, int p_73155_4_)
/* 68:   */   {
/* 69:71 */     BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(p_73155_2_, p_73155_4_);
/* 70:72 */     return biomegenbase.getSpawnableList(p_73155_1_);
/* 71:   */   }
/* 72:   */   
/* 73:   */   public ChunkPosition func_147416_a(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_)
/* 74:   */   {
/* 75:77 */     return null;
/* 76:   */   }
/* 77:   */   
/* 78:   */   public int getLoadedChunkCount()
/* 79:   */   {
/* 80:82 */     return 0;
/* 81:   */   }
/* 82:   */   
/* 83:   */   public void recreateStructures(int p_82695_1_, int p_82695_2_) {}
/* 84:   */   
/* 85:   */   public void saveExtraData() {}
/* 86:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.worldgen.endoftime.ChunkProviderEndOfTime
 * JD-Core Version:    0.7.0.1
 */