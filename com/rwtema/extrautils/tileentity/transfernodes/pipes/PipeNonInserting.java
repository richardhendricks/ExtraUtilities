/*  1:   */ package com.rwtema.extrautils.tileentity.transfernodes.pipes;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.tileentity.transfernodes.BlockTransferPipe;
/*  4:   */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeBuffer;
/*  5:   */ import net.minecraft.util.IIcon;
/*  6:   */ import net.minecraft.world.IBlockAccess;
/*  7:   */ import net.minecraftforge.common.util.ForgeDirection;
/*  8:   */ 
/*  9:   */ public class PipeNonInserting
/* 10:   */   extends PipeBase
/* 11:   */ {
/* 12:   */   public PipeNonInserting()
/* 13:   */   {
/* 14:11 */     super("NonInserting");
/* 15:   */   }
/* 16:   */   
/* 17:   */   public boolean transferItems(IBlockAccess world, int x, int y, int z, ForgeDirection dir, INodeBuffer buffer)
/* 18:   */   {
/* 19:16 */     return true;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean shouldConnectToTile(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
/* 23:   */   {
/* 24:21 */     return false;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public IIcon baseTexture()
/* 28:   */   {
/* 29:26 */     return BlockTransferPipe.pipes_noninserting;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public IIcon pipeTexture(ForgeDirection dir, boolean blocked)
/* 33:   */   {
/* 34:31 */     return BlockTransferPipe.pipes_noninserting;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public IIcon invPipeTexture(ForgeDirection dir)
/* 38:   */   {
/* 39:36 */     return BlockTransferPipe.pipes_noninserting;
/* 40:   */   }
/* 41:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.pipes.PipeNonInserting
 * JD-Core Version:    0.7.0.1
 */