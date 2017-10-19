package cofh.api.item;

import java.util.Set;
import net.minecraft.item.ItemStack;

public abstract interface IAugmentItem
{
  public abstract int getAugmentLevel(ItemStack paramItemStack, String paramString);
  
  public abstract Set<String> getAugmentTypes(ItemStack paramItemStack);
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     cofh.api.item.IAugmentItem
 * JD-Core Version:    0.7.0.1
 */