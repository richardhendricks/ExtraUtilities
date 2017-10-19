package cofh.api.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public abstract interface ISpecialFilterFluid
{
  public abstract boolean matchesFluid(ItemStack paramItemStack, FluidStack paramFluidStack);
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     cofh.api.item.ISpecialFilterFluid
 * JD-Core Version:    0.7.0.1
 */