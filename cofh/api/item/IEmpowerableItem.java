package cofh.api.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public abstract interface IEmpowerableItem
{
  public abstract boolean isEmpowered(ItemStack paramItemStack);
  
  public abstract boolean setEmpoweredState(ItemStack paramItemStack, boolean paramBoolean);
  
  public abstract void onStateChange(EntityPlayer paramEntityPlayer, ItemStack paramItemStack);
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     cofh.api.item.IEmpowerableItem
 * JD-Core Version:    0.7.0.1
 */