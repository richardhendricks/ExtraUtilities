package com.rwtema.extrautils.tileentity.transfernodes.pipes;

import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;

public abstract interface IPipeCosmetic
{
  public abstract IIcon baseTexture();
  
  public abstract IIcon pipeTexture(ForgeDirection paramForgeDirection, boolean paramBoolean);
  
  public abstract IIcon invPipeTexture(ForgeDirection paramForgeDirection);
  
  public abstract IIcon socketTexture(ForgeDirection paramForgeDirection);
  
  public abstract float baseSize();
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.pipes.IPipeCosmetic
 * JD-Core Version:    0.7.0.1
 */