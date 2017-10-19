package com.rwtema.extrautils.sync;

import net.minecraft.nbt.NBTTagCompound;

public abstract class AutoNBT<T>
{
  public abstract void writeToNBT(NBTTagCompound paramNBTTagCompound, T paramT);
  
  public abstract void readFromNBT(NBTTagCompound paramNBTTagCompound, T paramT);
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.sync.AutoNBT
 * JD-Core Version:    0.7.0.1
 */