/*  1:   */ package com.rwtema.extrautils.worldgen;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtils;
/*  4:   */ import cpw.mods.fml.common.IWorldGenerator;
/*  5:   */ import cpw.mods.fml.common.eventhandler.EventBus;
/*  6:   */ import java.util.Random;
/*  7:   */ import net.minecraft.block.Block;
/*  8:   */ import net.minecraft.init.Blocks;
/*  9:   */ import net.minecraft.nbt.NBTTagCompound;
/* 10:   */ import net.minecraft.world.World;
/* 11:   */ import net.minecraft.world.WorldProvider;
/* 12:   */ import net.minecraft.world.WorldServer;
/* 13:   */ import net.minecraft.world.chunk.Chunk;
/* 14:   */ import net.minecraft.world.chunk.IChunkProvider;
/* 15:   */ import net.minecraftforge.common.MinecraftForge;
/* 16:   */ import net.minecraftforge.event.world.ChunkDataEvent.Load;
/* 17:   */ import net.minecraftforge.event.world.ChunkDataEvent.Save;
/* 18:   */ 
/* 19:   */ public class WorldGenEnderLillies
/* 20:   */   implements IWorldGenerator
/* 21:   */ {
/* 22:17 */   public static String retrogen = "XU:EnderLilyRetrogen";
/* 23:   */   
/* 24:   */   public WorldGenEnderLillies()
/* 25:   */   {
/* 26:20 */     MinecraftForge.EVENT_BUS.register(this);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static boolean isAirBlock(Block id)
/* 30:   */   {
/* 31:24 */     return (id == Blocks.air) || (id == null);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public static void gen(Random random, Chunk chunk)
/* 35:   */   {
/* 36:28 */     for (int i = 0; i < 32; i++)
/* 37:   */     {
/* 38:29 */       int x = random.nextInt(16);
/* 39:30 */       int y = 10 + random.nextInt(65);
/* 40:31 */       int z = random.nextInt(16);
/* 41:33 */       if ((chunk.func_150810_a(x, y, z) == Blocks.end_stone) && (isAirBlock(chunk.func_150810_a(x, y + 1, z))))
/* 42:   */       {
/* 43:34 */         chunk.func_150807_a(x, y + 1, z, ExtraUtils.enderLily, 7);
/* 44:36 */         if (random.nextDouble() < 0.2D) {
/* 45:37 */           return;
/* 46:   */         }
/* 47:   */       }
/* 48:   */     }
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
/* 52:   */   {
/* 53:45 */     if (world.provider.dimensionId == 1) {
/* 54:46 */       gen(random, world.getChunkFromChunkCoords(chunkX, chunkZ));
/* 55:   */     }
/* 56:   */   }
/* 57:   */   
/* 58:   */   public void saveData(ChunkDataEvent.Save event)
/* 59:   */   {
/* 60:51 */     if (event.world.provider.dimensionId == 1) {
/* 61:52 */       event.getData().setInteger(retrogen, ExtraUtils.enderLilyRetrogenId);
/* 62:   */     }
/* 63:   */   }
/* 64:   */   
/* 65:   */   public void loadData(ChunkDataEvent.Load event)
/* 66:   */   {
/* 67:57 */     if ((ExtraUtils.enderLilyRetrogenId > 0) && (event.world.provider.dimensionId == 1) && ((event.world instanceof WorldServer)) && 
/* 68:58 */       (event.getData().getInteger(retrogen) != ExtraUtils.enderLilyRetrogenId))
/* 69:   */     {
/* 70:59 */       long worldSeed = event.world.getSeed();
/* 71:60 */       Random random = new Random(worldSeed);
/* 72:61 */       long xSeed = random.nextLong() >> 3;
/* 73:62 */       long zSeed = random.nextLong() >> 3;
/* 74:63 */       long chunkSeed = xSeed * event.getChunk().xPosition + zSeed * event.getChunk().zPosition ^ worldSeed;
/* 75:64 */       random.setSeed(chunkSeed);
/* 76:65 */       gen(random, event.getChunk());
/* 77:   */     }
/* 78:   */   }
/* 79:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.worldgen.WorldGenEnderLillies
 * JD-Core Version:    0.7.0.1
 */