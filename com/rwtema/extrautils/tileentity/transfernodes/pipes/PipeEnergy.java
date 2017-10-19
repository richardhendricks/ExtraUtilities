/*  1:   */ package com.rwtema.extrautils.tileentity.transfernodes.pipes;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.tileentity.transfernodes.BlockTransferPipe;
/*  4:   */ import com.rwtema.extrautils.tileentity.transfernodes.TNHelper;
/*  5:   */ import net.minecraft.tileentity.TileEntity;
/*  6:   */ import net.minecraft.util.IIcon;
/*  7:   */ import net.minecraft.world.IBlockAccess;
/*  8:   */ import net.minecraftforge.common.util.ForgeDirection;
/*  9:   */ 
/* 10:   */ public class PipeEnergy
/* 11:   */   extends PipeBase
/* 12:   */ {
/* 13:   */   public PipeEnergy()
/* 14:   */   {
/* 15:12 */     super("Energy");
/* 16:   */   }
/* 17:   */   
/* 18:   */   public PipeEnergy(String type)
/* 19:   */   {
/* 20:16 */     super(type);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public boolean shouldConnectToTile(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
/* 24:   */   {
/* 25:21 */     if (TNHelper.getPipe(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) != null) {
/* 26:22 */       return false;
/* 27:   */     }
/* 28:25 */     TileEntity tile = world.getTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
/* 29:   */     
/* 30:27 */     return TNHelper.isRFEnergy(tile, dir.getOpposite());
/* 31:   */   }
/* 32:   */   
/* 33:   */   public boolean canInput(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
/* 34:   */   {
/* 35:32 */     IPipe pipe = TNHelper.getPipe(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
/* 36:   */     
/* 37:34 */     return (pipe != null) && (pipe.getPipeType().startsWith("Energy"));
/* 38:   */   }
/* 39:   */   
/* 40:   */   public IIcon socketTexture(ForgeDirection dir)
/* 41:   */   {
/* 42:40 */     return BlockTransferPipe.pipes_nozzle_energy;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public IIcon baseTexture()
/* 46:   */   {
/* 47:45 */     return BlockTransferPipe.pipes_energy;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public IIcon pipeTexture(ForgeDirection dir, boolean blocked)
/* 51:   */   {
/* 52:50 */     return BlockTransferPipe.pipes_energy;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public IIcon invPipeTexture(ForgeDirection dir)
/* 56:   */   {
/* 57:55 */     return BlockTransferPipe.pipes_energy;
/* 58:   */   }
/* 59:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.pipes.PipeEnergy
 * JD-Core Version:    0.7.0.1
 */