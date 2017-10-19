package com.rwtema.extrautils.multipart.microblock;

import codechicken.lib.vec.BlockCoord;
import codechicken.lib.vec.Vector3;
import codechicken.microblock.MicroMaterialRegistry.IMicroMaterial;
import codechicken.multipart.TMultiPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract interface IMicroBlock
{
  public abstract int getMetadata();
  
  public abstract String getType();
  
  public abstract TMultiPart newPart(boolean paramBoolean);
  
  public abstract TMultiPart placePart(ItemStack paramItemStack, EntityPlayer paramEntityPlayer, World paramWorld, BlockCoord paramBlockCoord, int paramInt1, Vector3 paramVector3, int paramInt2);
  
  public abstract void registerPassThroughs();
  
  public abstract void renderItem(ItemStack paramItemStack, MicroMaterialRegistry.IMicroMaterial paramIMicroMaterial);
  
  public abstract boolean hideCreativeTab();
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.multipart.microblock.IMicroBlock
 * JD-Core Version:    0.7.0.1
 */