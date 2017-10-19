package com.rwtema.extrautils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract interface ISidedFunction<F, T>
{
  @SideOnly(Side.SERVER)
  public abstract T applyServer(F paramF);
  
  @SideOnly(Side.CLIENT)
  public abstract T applyClient(F paramF);
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.ISidedFunction
 * JD-Core Version:    0.7.0.1
 */