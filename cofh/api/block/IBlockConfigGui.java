package cofh.api.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public abstract interface IBlockConfigGui
{
  public abstract boolean openConfigGui(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, ForgeDirection paramForgeDirection, EntityPlayer paramEntityPlayer);
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     cofh.api.block.IBlockConfigGui
 * JD-Core Version:    0.7.0.1
 */