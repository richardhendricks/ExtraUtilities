/*  1:   */ package com.rwtema.extrautils.worldgen;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.tileentity.TileEntityPortal;
/*  4:   */ import java.util.Map;
/*  5:   */ import net.minecraft.tileentity.TileEntity;
/*  6:   */ import net.minecraft.world.Teleporter;
/*  7:   */ import net.minecraft.world.WorldServer;
/*  8:   */ import net.minecraft.world.chunk.Chunk;
/*  9:   */ 
/* 10:   */ public class TeleporterBase
/* 11:   */   extends Teleporter
/* 12:   */ {
/* 13:   */   protected final WorldServer worldServerInstance;
/* 14:   */   
/* 15:   */   public TeleporterBase(WorldServer p_i1963_1_)
/* 16:   */   {
/* 17:13 */     super(p_i1963_1_);
/* 18:14 */     this.worldServerInstance = p_i1963_1_;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public TileEntity findPortalInChunk(double x, double z)
/* 22:   */   {
/* 23:19 */     Chunk chunk = this.worldServerInstance.getChunkFromBlockCoords((int)x, (int)z);
/* 24:21 */     for (Object tile : chunk.chunkTileEntityMap.values()) {
/* 25:22 */       if ((tile instanceof TileEntityPortal)) {
/* 26:23 */         return (TileEntity)tile;
/* 27:   */       }
/* 28:   */     }
/* 29:27 */     return null;
/* 30:   */   }
/* 31:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.worldgen.TeleporterBase
 * JD-Core Version:    0.7.0.1
 */