package cofh.api.energy;

import net.minecraftforge.common.util.ForgeDirection;

public abstract interface IEnergyReceiver
  extends IEnergyConnection
{
  public abstract int receiveEnergy(ForgeDirection paramForgeDirection, int paramInt, boolean paramBoolean);
  
  public abstract int getEnergyStored(ForgeDirection paramForgeDirection);
  
  public abstract int getMaxEnergyStored(ForgeDirection paramForgeDirection);
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     cofh.api.energy.IEnergyReceiver
 * JD-Core Version:    0.7.0.1
 */