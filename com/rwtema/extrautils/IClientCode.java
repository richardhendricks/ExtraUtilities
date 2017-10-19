package com.rwtema.extrautils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract interface IClientCode
{
  @SideOnly(Side.CLIENT)
  public abstract void exectuteClientCode();
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.IClientCode
 * JD-Core Version:    0.7.0.1
 */