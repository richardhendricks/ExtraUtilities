package com.rwtema.extrautils.tileentity.transfernodes.nodebuffer;

import com.rwtema.extrautils.block.BoxModel;
import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNode;
import com.rwtema.extrautils.tileentity.transfernodes.pipes.IPipe;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public abstract interface INode
  extends IPipe
{
  public abstract TileEntityTransferNode getNode();
  
  public abstract int getNodeX();
  
  public abstract int getNodeY();
  
  public abstract int getNodeZ();
  
  public abstract ForgeDirection getNodeDir();
  
  public abstract int getPipeX();
  
  public abstract int getPipeY();
  
  public abstract int getPipeZ();
  
  public abstract int getPipeDir();
  
  public abstract List<ItemStack> getUpgrades();
  
  public abstract boolean checkRedstone();
  
  public abstract BoxModel getModel(ForgeDirection paramForgeDirection);
  
  public abstract String getNodeType();
  
  public abstract void bufferChanged();
  
  public abstract boolean isPowered();
  
  public abstract boolean recalcRedstone();
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INode
 * JD-Core Version:    0.7.0.1
 */