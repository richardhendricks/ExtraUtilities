package com.rwtema.extrautils.sounds;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public abstract interface ISoundTile
{
  public abstract boolean shouldSoundPlay();
  
  public abstract ResourceLocation getSound();
  
  public abstract TileEntity getTile();
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.sounds.ISoundTile
 * JD-Core Version:    0.7.0.1
 */