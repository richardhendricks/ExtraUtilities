/*  1:   */ package com.rwtema.extrautils.worldgen.Underdark;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.init.Blocks;
/*  5:   */ import net.minecraft.world.World;
/*  6:   */ import net.minecraft.world.gen.feature.WorldGenerator;
/*  7:   */ 
/*  8:   */ public class WorldGenBigHole
/*  9:   */   extends WorldGenerator
/* 10:   */ {
/* 11:   */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/* 12:   */   {
/* 13:12 */     if (par2Random.nextInt(96) == 0)
/* 14:   */     {
/* 15:13 */       int r = 4 + par2Random.nextInt(6);
/* 16:14 */       int x = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
/* 17:15 */       int z = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);
/* 18:17 */       for (int y = 0; y < 82 + r; y++) {
/* 19:18 */         for (int dx = -r; dx <= r; dx++) {
/* 20:19 */           for (int dz = -r; dz <= r; dz++)
/* 21:   */           {
/* 22:20 */             int ey = y - 82;
/* 23:22 */             if (ey < 0) {
/* 24:23 */               ey = 0;
/* 25:   */             }
/* 26:26 */             if (par2Random.nextInt(1 + ey * ey + dx * dx + dz * dz) < 1 + (r - 1) * (r - 1) / 2) {
/* 27:27 */               par1World.setBlock(x + dx, y, z + dz, Blocks.air, 0, 2);
/* 28:   */             }
/* 29:   */           }
/* 30:   */         }
/* 31:   */       }
/* 32:   */     }
/* 33:34 */     return true;
/* 34:   */   }
/* 35:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.worldgen.Underdark.WorldGenBigHole
 * JD-Core Version:    0.7.0.1
 */