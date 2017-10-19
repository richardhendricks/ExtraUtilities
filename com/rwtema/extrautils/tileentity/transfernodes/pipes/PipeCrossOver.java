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
/* 11:   */ public class PipeCrossOver
/* 12:   */   extends PipeBase
/* 13:   */ {
/* 14:   */   public PipeCrossOver()
/* 15:   */   {
/* 16:14 */     super("Crossover");
/* 17:   */   }
/* 18:   */   
/* 19:   */   public IIcon baseTexture()
/* 20:   */   {
/* 21:19 */     return BlockTransferPipe.pipes_xover;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public float baseSize()
/* 25:   */   {
/* 26:24 */     return 0.1875F;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public ArrayList<ForgeDirection> getOutputDirections(IBlockAccess world, int x, int y, int z, ForgeDirection dir, INodeBuffer buffer)
/* 30:   */   {
/* 31:29 */     ArrayList<ForgeDirection> dirs = new ArrayList();
/* 32:30 */     if ((TNHelper.canOutput(world, x, y, z, dir)) && 
/* 33:31 */       (TNHelper.canInput(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir.getOpposite()))) {
/* 34:32 */       dirs.add(dir);
/* 35:   */     }
/* 36:36 */     return dirs;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public boolean transferItems(IBlockAccess world, int x, int y, int z, ForgeDirection dir, INodeBuffer buffer)
/* 40:   */   {
/* 41:41 */     boolean advance = true;
/* 42:42 */     IPipe pipe = TNHelper.getPipe(world, x, y, z);
/* 43:44 */     if (pipe == null) {
/* 44:45 */       return true;
/* 45:   */     }
/* 46:48 */     if ((pipe.shouldConnectToTile(world, x, y, z, dir)) && 
/* 47:49 */       (!buffer.transfer(world.getTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ), dir.getOpposite(), pipe, x, y, z, dir))) {
/* 48:50 */       advance = false;
/* 49:   */     }
/* 50:54 */     return advance;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public boolean canOutput(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
/* 54:   */   {
/* 55:60 */     if (dir == ForgeDirection.UNKNOWN) {
/* 56:61 */       return false;
/* 57:   */     }
/* 58:63 */     IPipe pipe = TNHelper.getPipe(world, x + dir.getOpposite().offsetX, y + dir.getOpposite().offsetY, z + dir.getOpposite().offsetZ);
/* 59:66 */     if (pipe == null) {
/* 60:67 */       return false;
/* 61:   */     }
/* 62:69 */     if (pipe.getPipeType().equals(getPipeType())) {
/* 63:70 */       return true;
/* 64:   */     }
/* 65:72 */     return pipe.canOutput(world, x + dir.getOpposite().offsetX, y + dir.getOpposite().offsetY, z + dir.getOpposite().offsetZ, dir);
/* 66:   */   }
/* 67:   */   
/* 68:   */   public boolean canInput(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
/* 69:   */   {
/* 70:80 */     if (dir == ForgeDirection.UNKNOWN) {
/* 71:81 */       return false;
/* 72:   */     }
/* 73:83 */     IPipe pipe = TNHelper.getPipe(world, x + dir.getOpposite().offsetX, y + dir.getOpposite().offsetY, z + dir.getOpposite().offsetZ);
/* 74:86 */     if (pipe == null) {
/* 75:87 */       return super.shouldConnectToTile(world, x, y, z, dir.getOpposite());
/* 76:   */     }
/* 77:89 */     if (pipe.getPipeType().equals(getPipeType())) {
/* 78:90 */       return true;
/* 79:   */     }
/* 80:92 */     return pipe.canInput(world, x + dir.getOpposite().offsetX, y + dir.getOpposite().offsetY, z + dir.getOpposite().offsetZ, dir);
/* 81:   */   }
/* 82:   */   
/* 83:   */   public boolean shouldConnectToTile(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
/* 84:   */   {
/* 85:99 */     return (TNHelper.canOutput(world, x + dir.getOpposite().offsetX, y + dir.getOpposite().offsetY, z + dir.getOpposite().offsetZ, dir)) && (TNHelper.isValidTileEntity(world, x, y, z, dir));
/* 86:   */   }
/* 87:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.pipes.PipeCrossOver
 * JD-Core Version:    0.7.0.1
 */