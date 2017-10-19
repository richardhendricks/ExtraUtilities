/*  1:   */ package com.rwtema.extrautils.tileentity.transfernodes.pipes;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.tileentity.transfernodes.BlockTransferPipe;
/*  4:   */ import com.rwtema.extrautils.tileentity.transfernodes.TNHelper;
/*  5:   */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeBuffer;
/*  6:   */ import java.util.ArrayList;
/*  7:   */ import net.minecraft.util.IIcon;
/*  8:   */ import net.minecraft.world.IBlockAccess;
/*  9:   */ import net.minecraftforge.common.util.ForgeDirection;
/* 10:   */ 
/* 11:   */ public class PipeDirectional
/* 12:   */   extends PipeBase
/* 13:   */ {
/* 14:   */   ForgeDirection outDir;
/* 15:   */   
/* 16:   */   public PipeDirectional(ForgeDirection outDir)
/* 17:   */   {
/* 18:16 */     super("Directional_" + outDir);
/* 19:17 */     this.outDir = outDir;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public ArrayList<ForgeDirection> getOutputDirections(IBlockAccess world, int x, int y, int z, ForgeDirection dir, INodeBuffer buffer)
/* 23:   */   {
/* 24:22 */     ArrayList<ForgeDirection> dirs = new ArrayList();
/* 25:23 */     if ((canOutput(world, x, y, z, this.outDir)) && 
/* 26:24 */       (TNHelper.canInput(world, x + this.outDir.offsetX, y + this.outDir.offsetY, z + this.outDir.offsetZ, this.outDir.getOpposite())))
/* 27:   */     {
/* 28:25 */       dirs.add(this.outDir);
/* 29:26 */       return dirs;
/* 30:   */     }
/* 31:30 */     return dirs;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public boolean canOutput(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
/* 35:   */   {
/* 36:35 */     return dir == this.outDir;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public IIcon pipeTexture(ForgeDirection dir, boolean blocked)
/* 40:   */   {
/* 41:40 */     return dir == this.outDir ? BlockTransferPipe.pipes : BlockTransferPipe.pipes_oneway;
/* 42:   */   }
/* 43:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.pipes.PipeDirectional
 * JD-Core Version:    0.7.0.1
 */