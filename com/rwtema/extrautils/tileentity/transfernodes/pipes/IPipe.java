package com.rwtema.extrautils.tileentity.transfernodes.pipes;

import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeBuffer;
import java.util.ArrayList;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public abstract interface IPipe
{
  public abstract ArrayList<ForgeDirection> getOutputDirections(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, ForgeDirection paramForgeDirection, INodeBuffer paramINodeBuffer);
  
  public abstract boolean transferItems(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, ForgeDirection paramForgeDirection, INodeBuffer paramINodeBuffer);
  
  public abstract boolean canInput(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, ForgeDirection paramForgeDirection);
  
  public abstract boolean canOutput(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, ForgeDirection paramForgeDirection);
  
  public abstract int limitTransfer(TileEntity paramTileEntity, ForgeDirection paramForgeDirection, INodeBuffer paramINodeBuffer);
  
  public abstract IInventory getFilterInventory(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3);
  
  public abstract boolean shouldConnectToTile(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, ForgeDirection paramForgeDirection);
  
  public abstract String getPipeType();
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.pipes.IPipe
 * JD-Core Version:    0.7.0.1
 */