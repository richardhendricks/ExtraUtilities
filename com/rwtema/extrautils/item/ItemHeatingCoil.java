/*  1:   */ package com.rwtema.extrautils.item;
/*  2:   */ 
/*  3:   */ import cofh.api.energy.ItemEnergyContainer;
/*  4:   */ import com.rwtema.extrautils.ExtraUtils;
/*  5:   */ import com.rwtema.extrautils.helper.XUHelper;
/*  6:   */ import cpw.mods.fml.common.IFuelHandler;
/*  7:   */ import cpw.mods.fml.common.registry.GameRegistry;
/*  8:   */ import cpw.mods.fml.relauncher.Side;
/*  9:   */ import cpw.mods.fml.relauncher.SideOnly;
/* 10:   */ import java.util.List;
/* 11:   */ import net.minecraft.client.Minecraft;
/* 12:   */ import net.minecraft.entity.player.EntityPlayer;
/* 13:   */ import net.minecraft.item.ItemStack;
/* 14:   */ import net.minecraft.util.MathHelper;
/* 15:   */ 
/* 16:   */ public class ItemHeatingCoil
/* 17:   */   extends ItemEnergyContainer
/* 18:   */   implements IFuelHandler
/* 19:   */ {
/* 20:   */   public static final int rate = 25;
/* 21:   */   public static final int energyAmmountForOneHeat = 15000;
/* 22:   */   public static final int uses = 100;
/* 23:   */   
/* 24:   */   public ItemHeatingCoil()
/* 25:   */   {
/* 26:23 */     super(1500000, 1500000, 75000);
/* 27:24 */     GameRegistry.registerFuelHandler(this);
/* 28:25 */     setMaxStackSize(1);
/* 29:26 */     setTextureName("extrautils:heatingElement");
/* 30:27 */     setUnlocalizedName("extrautils:heatingElement");
/* 31:28 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/* 32:   */   }
/* 33:   */   
/* 34:   */   @SideOnly(Side.CLIENT)
/* 35:   */   public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
/* 36:   */   {
/* 37:34 */     int i = 30;
/* 38:   */     
/* 39:36 */     float f13 = (float)(Minecraft.getSystemTime() % 6000L) / 3000.0F * 3.141592F * 2.0F;
/* 40:   */     
/* 41:38 */     float t = 0.9F + 0.1F * MathHelper.cos(f13);
/* 42:   */     
/* 43:40 */     double v = 1.0D - getDurabilityForDisplay(par1ItemStack);
/* 44:   */     
/* 45:42 */     int r = i + (int)(v * (255 - i) * t);
/* 46:43 */     if (r > 255) {
/* 47:44 */       r = 255;
/* 48:   */     }
/* 49:45 */     int g = i + (int)(v * (64 - i) * t);
/* 50:   */     
/* 51:47 */     return r << 16 | g << 8 | i;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public boolean showDurabilityBar(ItemStack stack)
/* 55:   */   {
/* 56:52 */     return true;
/* 57:   */   }
/* 58:   */   
/* 59:   */   @SideOnly(Side.CLIENT)
/* 60:   */   public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
/* 61:   */   {
/* 62:58 */     par3List.add(XUHelper.addThousandsCommas(getEnergyStored(par1ItemStack)) + " / " + XUHelper.addThousandsCommas(getMaxEnergyStored(par1ItemStack)));
/* 63:   */   }
/* 64:   */   
/* 65:   */   public double getDurabilityForDisplay(ItemStack stack)
/* 66:   */   {
/* 67:63 */     return 1.0D - getEnergyStored(stack) / this.capacity;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public ItemStack getContainerItem(ItemStack itemStack)
/* 71:   */   {
/* 72:68 */     ItemStack newItem = itemStack.copy();
/* 73:69 */     newItem.stackSize = 1;
/* 74:70 */     extractEnergy(newItem, 15000, false);
/* 75:71 */     return newItem;
/* 76:   */   }
/* 77:   */   
/* 78:   */   public boolean hasContainerItem(ItemStack stack)
/* 79:   */   {
/* 80:76 */     return true;
/* 81:   */   }
/* 82:   */   
/* 83:   */   public int getBurnTime(ItemStack fuel)
/* 84:   */   {
/* 85:81 */     if ((fuel == null) || (fuel.getItem() != this)) {
/* 86:82 */       return 0;
/* 87:   */     }
/* 88:84 */     return extractEnergy(fuel, 15000, true) / 50;
/* 89:   */   }
/* 90:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemHeatingCoil
 * JD-Core Version:    0.7.0.1
 */