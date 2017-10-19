/*  1:   */ package com.rwtema.extrautils.tileentity.transfernodes.pipes;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.tileentity.transfernodes.BlockTransferPipe;
/*  4:   */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeBuffer;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ import net.minecraft.util.IIcon;
/*  7:   */ import net.minecraft.world.IBlockAccess;
/*  8:   */ import net.minecraftforge.common.util.ForgeDirection;
/*  9:   */ 
/* 10:   */ public class PipeEOF
/* 11:   */   extends PipeBase
/* 12:   */ {
/* 13:   */   public PipeEOF()
/* 14:   */   {
/* 15:13 */     super("eof");
/* 16:   */   }
/* 17:   */   
/* 18:   */   public boolean canOutput(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
/* 19:   */   {
/* 20:18 */     return false;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public ArrayList<ForgeDirection> getOutputDirections(IBlockAccess world, int x, int y, int z, ForgeDirection dir, INodeBuffer buffer)
/* 24:   */   {
/* 25:23 */     return new ArrayList();
/* 26:   */   }
/* 27:   */   
/* 28:   */   public IIcon pipeTexture(ForgeDirection dir, boolean blocked)
/* 29:   */   {
/* 30:28 */     return BlockTransferPipe.pipes_oneway;
/* 31:   */   }
/* 32:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.pipes.PipeEOF
 * JD-Core Version:    0.7.0.1
 */