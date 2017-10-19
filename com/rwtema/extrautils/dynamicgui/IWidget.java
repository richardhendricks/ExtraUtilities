package com.rwtema.extrautils.dynamicgui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.nbt.NBTTagCompound;

public abstract interface IWidget
{
  public abstract int getX();
  
  public abstract int getY();
  
  public abstract int getW();
  
  public abstract int getH();
  
  public abstract NBTTagCompound getDescriptionPacket(boolean paramBoolean);
  
  public abstract void handleDescriptionPacket(NBTTagCompound paramNBTTagCompound);
  
  @SideOnly(Side.CLIENT)
  public abstract void renderForeground(TextureManager paramTextureManager, DynamicGui paramDynamicGui, int paramInt1, int paramInt2);
  
  @SideOnly(Side.CLIENT)
  public abstract void renderBackground(TextureManager paramTextureManager, DynamicGui paramDynamicGui, int paramInt1, int paramInt2);
  
  public abstract void addToContainer(DynamicContainer paramDynamicContainer);
  
  public abstract List<String> getToolTip();
}


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.dynamicgui.IWidget
 * JD-Core Version:    0.7.0.1
 */