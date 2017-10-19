package cofh.api.block;

import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public abstract interface IBlockAppearance
{
  public abstract Block getVisualBlock(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, ForgeDirection paramForgeDirection);
  
  public abstract int getVisualMeta(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, ForgeDirection paramForgeDirection);
  
  public abstract boolean supportsVisualConnections();
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     cofh.api.block.IBlockAppearance
 * JD-Core Version:    0.7.0.1
 */