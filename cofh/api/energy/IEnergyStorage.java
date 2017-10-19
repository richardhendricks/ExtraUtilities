package cofh.api.energy;

public abstract interface IEnergyStorage
{
  public abstract int receiveEnergy(int paramInt, boolean paramBoolean);
  
  public abstract int extractEnergy(int paramInt, boolean paramBoolean);
  
  public abstract int getEnergyStored();
  
  public abstract int getMaxEnergyStored();
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     cofh.api.energy.IEnergyStorage
 * JD-Core Version:    0.7.0.1
 */