/*  1:   */ package com.rwtema.extrautils.tileentity.transfernodes.nodebuffer;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityRetrievalNodeLiquid;
/*  4:   */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNode;
/*  5:   */ import com.rwtema.extrautils.tileentity.transfernodes.pipes.IPipe;
/*  6:   */ import net.minecraft.tileentity.TileEntity;
/*  7:   */ import net.minecraftforge.common.util.ForgeDirection;
/*  8:   */ import net.minecraftforge.fluids.FluidTank;
/*  9:   */ import net.minecraftforge.fluids.IFluidHandler;
/* 10:   */ 
/* 11:   */ public class FluidBufferRetrieval
/* 12:   */   extends FluidBuffer
/* 13:   */ {
/* 14:   */   public boolean shouldSearch()
/* 15:   */   {
/* 16:12 */     return this.tank.getFluidAmount() < this.tank.getCapacity();
/* 17:   */   }
/* 18:   */   
/* 19:   */   public boolean transfer(TileEntity tile, ForgeDirection side, IPipe insertingPipe, int x, int y, int z, ForgeDirection travelDir)
/* 20:   */   {
/* 21:17 */     if ((tile instanceof IFluidHandler))
/* 22:   */     {
/* 23:18 */       IFluidHandler destTank = (IFluidHandler)tile;
/* 24:19 */       int drainmax = this.node.getNode().upgradeNo(3) == 0 ? 200 : this.tank.getCapacity();
/* 25:21 */       if ((((TileEntityRetrievalNodeLiquid)this.node.getNode()).fill(this.node.getNodeDir(), destTank.drain(side, drainmax, true), true) > 0) && 
/* 26:22 */         (((TileEntityRetrievalNodeLiquid)this.node.getNode()).fill(this.node.getNodeDir(), destTank.drain(side, drainmax, false), false) > 0)) {
/* 27:23 */         return false;
/* 28:   */       }
/* 29:   */     }
/* 30:28 */     return true;
/* 31:   */   }
/* 32:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.FluidBufferRetrieval
 * JD-Core Version:    0.7.0.1
 */