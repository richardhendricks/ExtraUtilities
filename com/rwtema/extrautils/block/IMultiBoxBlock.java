package com.rwtema.extrautils.block;

import net.minecraft.world.IBlockAccess;

public abstract interface IMultiBoxBlock
{
  public abstract void prepareForRender(String paramString);
  
  public abstract BoxModel getWorldModel(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3);
  
  public abstract BoxModel getInventoryModel(int paramInt);
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.IMultiBoxBlock
 * JD-Core Version:    0.7.0.1
 */