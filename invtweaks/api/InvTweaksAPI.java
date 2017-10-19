package invtweaks.api;

import invtweaks.api.container.ContainerSection;
import net.minecraft.item.ItemStack;

public abstract interface InvTweaksAPI
{
  public abstract void addOnLoadListener(IItemTreeListener paramIItemTreeListener);
  
  public abstract boolean removeOnLoadListener(IItemTreeListener paramIItemTreeListener);
  
  public abstract void setSortKeyEnabled(boolean paramBoolean);
  
  public abstract void setTextboxMode(boolean paramBoolean);
  
  public abstract int compareItems(ItemStack paramItemStack1, ItemStack paramItemStack2);
  
  public abstract void sort(ContainerSection paramContainerSection, SortingMethod paramSortingMethod);
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     invtweaks.api.InvTweaksAPI
 * JD-Core Version:    0.7.0.1
 */