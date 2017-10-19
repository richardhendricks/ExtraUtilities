package com.rwtema.extrautils.tileentity.transfernodes.nodebuffer;

import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNodeLiquid;
import net.minecraftforge.fluids.IFluidHandler;

public abstract interface INodeLiquid
  extends IFluidHandler
{
  public abstract TileEntityTransferNodeLiquid getNode();
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeLiquid
 * JD-Core Version:    0.7.0.1
 */