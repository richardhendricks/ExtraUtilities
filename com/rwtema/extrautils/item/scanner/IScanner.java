package com.rwtema.extrautils.item.scanner;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;

public abstract interface IScanner
{
  public abstract Class<?> getTargetClass();
  
  public abstract void addData(Object paramObject, List<String> paramList, ForgeDirection paramForgeDirection, EntityPlayer paramEntityPlayer);
  
  public abstract int getPriority();
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.scanner.IScanner
 * JD-Core Version:    0.7.0.1
 */