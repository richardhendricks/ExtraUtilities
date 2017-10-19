package com.rwtema.extrautils.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public abstract interface IItemMultiTransparency
{
  public abstract int numIcons(ItemStack paramItemStack);
  
  public abstract IIcon getIconForTransparentRender(ItemStack paramItemStack, int paramInt);
  
  public abstract float getIconTransparency(ItemStack paramItemStack, int paramInt);
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.IItemMultiTransparency
 * JD-Core Version:    0.7.0.1
 */