package com.rwtema.extrautils.tileentity.transfernodes.pipes;

import net.minecraft.inventory.IInventory;
import net.minecraft.world.IBlockAccess;

public abstract interface IFilterPipe
{
  public abstract IInventory getFilterInventory(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3);
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.pipes.IFilterPipe
 * JD-Core Version:    0.7.0.1
 */