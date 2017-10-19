package baubles.api;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public abstract interface IBauble
{
  public abstract BaubleType getBaubleType(ItemStack paramItemStack);
  
  public abstract void onWornTick(ItemStack paramItemStack, EntityLivingBase paramEntityLivingBase);
  
  public abstract void onEquipped(ItemStack paramItemStack, EntityLivingBase paramEntityLivingBase);
  
  public abstract void onUnequipped(ItemStack paramItemStack, EntityLivingBase paramEntityLivingBase);
  
  public abstract boolean canEquip(ItemStack paramItemStack, EntityLivingBase paramEntityLivingBase);
  
  public abstract boolean canUnequip(ItemStack paramItemStack, EntityLivingBase paramEntityLivingBase);
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     baubles.api.IBauble
 * JD-Core Version:    0.7.0.1
 */