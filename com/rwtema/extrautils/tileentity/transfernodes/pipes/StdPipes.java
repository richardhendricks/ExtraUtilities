/*  1:   */ package com.rwtema.extrautils.tileentity.transfernodes.pipes;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.tileentity.transfernodes.TNHelper;
/*  4:   */ import net.minecraft.world.IBlockAccess;
/*  5:   */ import net.minecraftforge.common.util.ForgeDirection;
/*  6:   */ 
/*  7:   */ public class StdPipes
/*  8:   */ {
/*  9: 8 */   public static IPipe[] pipe = new IPipe[17];
/* 10:   */   
/* 11:   */   static
/* 12:   */   {
/* 13:11 */     pipe[0] = new PipeStandard();
/* 14:13 */     for (int i = 0; i < 6; i++) {
/* 15:14 */       pipe[(i + 1)] = new PipeDirectional(ForgeDirection.getOrientation(i));
/* 16:   */     }
/* 17:17 */     pipe[7] = new PipeNonInserting();
/* 18:18 */     pipe[8] = new PipeSorting();
/* 19:19 */     pipe[9] = new PipeFilter();
/* 20:20 */     pipe[10] = new PipeRationing();
/* 21:21 */     pipe[11] = new PipeEnergy();
/* 22:22 */     pipe[12] = new PipeCrossOver();
/* 23:23 */     pipe[13] = new PipeModSorting();
/* 24:24 */     pipe[14] = new PipeEnergyExtract();
/* 25:25 */     pipe[15] = new PipeEOF();
/* 26:26 */     pipe[16] = new PipeHyperRationing();
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static IPipe getPipeType(int type)
/* 30:   */   {
/* 31:30 */     if ((type < 0) || (type >= pipe.length)) {
/* 32:31 */       return null;
/* 33:   */     }
/* 34:33 */     return pipe[type];
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static int getNextPipeType(IBlockAccess world, int x, int y, int z, int metadata)
/* 38:   */   {
/* 39:38 */     if (((metadata >= 8) && (metadata < 15)) || (metadata > 15)) {
/* 40:39 */       return metadata;
/* 41:   */     }
/* 42:42 */     if (metadata == 7) {
/* 43:43 */       return 0;
/* 44:   */     }
/* 45:45 */     if (metadata == 15) {
/* 46:46 */       return 7;
/* 47:   */     }
/* 48:48 */     if (metadata == 6) {
/* 49:49 */       return 15;
/* 50:   */     }
/* 51:51 */     metadata = (metadata + 1) % 8;
/* 52:   */     
/* 53:53 */     IPipe pipe = TNHelper.getPipe(world, x, y, z);
/* 54:55 */     if (pipe == null) {
/* 55:56 */       return metadata;
/* 56:   */     }
/* 57:59 */     int numNeighbors = 0;
/* 58:61 */     for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
/* 59:62 */       if ((TNHelper.canInput(world, x, y, z, dir)) && (TNHelper.canInput(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir.getOpposite()))) {
/* 60:63 */         numNeighbors++;
/* 61:   */       }
/* 62:   */     }
/* 63:67 */     if (numNeighbors <= 1)
/* 64:   */     {
/* 65:68 */       if ((metadata >= 1) && (metadata <= 6)) {
/* 66:69 */         return 15;
/* 67:   */       }
/* 68:   */     }
/* 69:   */     else
/* 70:   */     {
/* 71:72 */       ForgeDirection dir = ForgeDirection.getOrientation(metadata - 1);
/* 72:74 */       while ((metadata >= 1) && (metadata <= 6) && ((!TNHelper.canInput(world, x, y, z, dir)) || (!TNHelper.canInput(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir.getOpposite()))))
/* 73:   */       {
/* 74:75 */         metadata++;
/* 75:76 */         dir = ForgeDirection.getOrientation(metadata - 1);
/* 76:   */       }
/* 77:78 */       if (metadata == 7) {
/* 78:79 */         return 15;
/* 79:   */       }
/* 80:   */     }
/* 81:82 */     return metadata;
/* 82:   */   }
/* 83:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.pipes.StdPipes
 * JD-Core Version:    0.7.0.1
 */