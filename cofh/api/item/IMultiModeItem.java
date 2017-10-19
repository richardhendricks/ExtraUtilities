package cofh.api.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public abstract interface IMultiModeItem
{
  public abstract int getMode(ItemStack paramItemStack);
  
  public abstract boolean setMode(ItemStack paramItemStack, int paramInt);
  
  public abstract boolean incrMode(ItemStack paramItemStack);
  
  public abstract boolean decrMode(ItemStack paramItemStack);
  
  public abstract int getNumModes(ItemStack paramItemStack);
  
  public abstract void onModeChange(EntityPlayer paramEntityPlayer, ItemStack paramItemStack);
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     cofh.api.item.IMultiModeItem
 * JD-Core Version:    0.7.0.1
 */