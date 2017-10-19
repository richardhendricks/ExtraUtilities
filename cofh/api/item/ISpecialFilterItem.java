package cofh.api.item;

import net.minecraft.item.ItemStack;

public abstract interface ISpecialFilterItem
{
  public abstract boolean matchesItem(ItemStack paramItemStack1, ItemStack paramItemStack2);
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     cofh.api.item.ISpecialFilterItem
 * JD-Core Version:    0.7.0.1
 */