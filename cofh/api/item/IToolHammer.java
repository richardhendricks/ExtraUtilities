package cofh.api.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public abstract interface IToolHammer
{
  public abstract boolean isUsable(ItemStack paramItemStack, EntityLivingBase paramEntityLivingBase, int paramInt1, int paramInt2, int paramInt3);
  
  public abstract void toolUsed(ItemStack paramItemStack, EntityLivingBase paramEntityLivingBase, int paramInt1, int paramInt2, int paramInt3);
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     cofh.api.item.IToolHammer
 * JD-Core Version:    0.7.0.1
 */