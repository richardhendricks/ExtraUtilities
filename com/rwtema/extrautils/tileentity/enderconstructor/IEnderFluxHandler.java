package com.rwtema.extrautils.tileentity.enderconstructor;

public abstract interface IEnderFluxHandler
{
  public abstract boolean isActive();
  
  public abstract int recieveEnergy(int paramInt, Transfer paramTransfer);
  
  public abstract float getAmountRequested();
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.enderconstructor.IEnderFluxHandler
 * JD-Core Version:    0.7.0.1
 */