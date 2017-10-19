/*  1:   */ package com.rwtema.extrautils.tileentity.transfernodes.multiparts;
/*  2:   */ 
/*  3:   */ import codechicken.lib.vec.Cuboid6;
/*  4:   */ import codechicken.multipart.TMultiPart;
/*  5:   */ import com.rwtema.extrautils.block.Box;
/*  6:   */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNode;
/*  7:   */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNodeLiquid;
/*  8:   */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeLiquid;
/*  9:   */ import java.util.ArrayList;
/* 10:   */ import java.util.List;
/* 11:   */ import net.minecraftforge.common.util.ForgeDirection;
/* 12:   */ import net.minecraftforge.fluids.Fluid;
/* 13:   */ import net.minecraftforge.fluids.FluidStack;
/* 14:   */ import net.minecraftforge.fluids.FluidTankInfo;
/* 15:   */ import net.minecraftforge.fluids.IFluidHandler;
/* 16:   */ 
/* 17:   */ public class TransferNodePartLiquid
/* 18:   */   extends TransferNodePart
/* 19:   */   implements INodeLiquid
/* 20:   */ {
/* 21:   */   public TransferNodePartLiquid()
/* 22:   */   {
/* 23:21 */     super(new TileEntityTransferNodeLiquid());
/* 24:   */   }
/* 25:   */   
/* 26:   */   public TransferNodePartLiquid(TileEntityTransferNode node)
/* 27:   */   {
/* 28:25 */     super(node);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public TransferNodePartLiquid(int meta, TileEntityTransferNodeLiquid node)
/* 32:   */   {
/* 33:29 */     super(meta, node);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Iterable<Cuboid6> getOcclusionBoxes()
/* 37:   */   {
/* 38:34 */     Box t = new Box(0.125F, 0.0F, 0.125F, 0.875F, 0.375F, 0.875F);
/* 39:35 */     t.rotateToSide(getNodeDir());
/* 40:36 */     List<Cuboid6> s = new ArrayList();
/* 41:37 */     s.add(new Cuboid6(t.minX, t.minY, t.minZ, t.maxX, t.maxY, t.maxZ));
/* 42:38 */     return s;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public TileEntityTransferNodeLiquid getNode()
/* 46:   */   {
/* 47:43 */     return (TileEntityTransferNodeLiquid)this.node;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public boolean occlusionTest(TMultiPart npart)
/* 51:   */   {
/* 52:48 */     return (!(npart instanceof IFluidHandler)) && (super.occlusionTest(npart));
/* 53:   */   }
/* 54:   */   
/* 55:   */   public String getType()
/* 56:   */   {
/* 57:54 */     return "extrautils:transfer_node_liquid";
/* 58:   */   }
/* 59:   */   
/* 60:   */   public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
/* 61:   */   {
/* 62:59 */     return getNode().fill(from, resource, doFill);
/* 63:   */   }
/* 64:   */   
/* 65:   */   public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
/* 66:   */   {
/* 67:64 */     return getNode().drain(from, resource, doDrain);
/* 68:   */   }
/* 69:   */   
/* 70:   */   public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
/* 71:   */   {
/* 72:69 */     return getNode().drain(from, maxDrain, doDrain);
/* 73:   */   }
/* 74:   */   
/* 75:   */   public boolean canFill(ForgeDirection from, Fluid fluid)
/* 76:   */   {
/* 77:74 */     return getNode().canFill(from, fluid);
/* 78:   */   }
/* 79:   */   
/* 80:   */   public boolean canDrain(ForgeDirection from, Fluid fluid)
/* 81:   */   {
/* 82:79 */     return getNode().canDrain(from, fluid);
/* 83:   */   }
/* 84:   */   
/* 85:   */   public FluidTankInfo[] getTankInfo(ForgeDirection from)
/* 86:   */   {
/* 87:84 */     return getNode().getTankInfo(from);
/* 88:   */   }
/* 89:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.multiparts.TransferNodePartLiquid
 * JD-Core Version:    0.7.0.1
 */