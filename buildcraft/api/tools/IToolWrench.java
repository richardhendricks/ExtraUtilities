package buildcraft.api.tools;

import net.minecraft.entity.player.EntityPlayer;

public abstract interface IToolWrench
{
  public abstract boolean canWrench(EntityPlayer paramEntityPlayer, int paramInt1, int paramInt2, int paramInt3);
  
  public abstract void wrenchUsed(EntityPlayer paramEntityPlayer, int paramInt1, int paramInt2, int paramInt3);
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     buildcraft.api.tools.IToolWrench
 * JD-Core Version:    0.7.0.1
 */