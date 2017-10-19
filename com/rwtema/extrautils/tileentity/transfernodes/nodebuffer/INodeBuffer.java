package com.rwtema.extrautils.tileentity.transfernodes.nodebuffer;

import com.rwtema.extrautils.tileentity.transfernodes.pipes.IPipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public abstract interface INodeBuffer
{
  public abstract boolean transfer(TileEntity paramTileEntity, ForgeDirection paramForgeDirection1, IPipe paramIPipe, int paramInt1, int paramInt2, int paramInt3, ForgeDirection paramForgeDirection2);
  
  public abstract Object getBuffer();
  
  public abstract String getBufferType();
  
  public abstract void setBuffer(Object paramObject);
  
  public abstract boolean isEmpty();
  
  public abstract boolean shouldSearch();
  
  public abstract void readFromNBT(NBTTagCompound paramNBTTagCompound);
  
  public abstract void writeToNBT(NBTTagCompound paramNBTTagCompound);
  
  public abstract void setNode(INode paramINode);
  
  public abstract INode getNode();
  
  public abstract boolean transferTo(INodeBuffer paramINodeBuffer, int paramInt);
  
  public abstract Object recieve(Object paramObject);
  
  public abstract void markDirty();
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeBuffer
 * JD-Core Version:    0.7.0.1
 */