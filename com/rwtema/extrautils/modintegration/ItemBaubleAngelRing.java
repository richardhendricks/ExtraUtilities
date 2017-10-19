/*  1:   */ package com.rwtema.extrautils.modintegration;
/*  2:   */ 
/*  3:   */ import baubles.api.BaubleType;
/*  4:   */ import baubles.api.IBauble;
/*  5:   */ import com.rwtema.extrautils.item.ItemAngelRing;
/*  6:   */ import net.minecraft.entity.EntityLivingBase;
/*  7:   */ import net.minecraft.item.ItemStack;
/*  8:   */ 
/*  9:   */ public class ItemBaubleAngelRing
/* 10:   */   extends ItemAngelRing
/* 11:   */   implements IBauble
/* 12:   */ {
/* 13:   */   public BaubleType getBaubleType(ItemStack itemstack)
/* 14:   */   {
/* 15:16 */     return super.getBaubleType(null);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void onWornTick(ItemStack itemstack, EntityLivingBase player)
/* 19:   */   {
/* 20:21 */     super.onUpdate(itemstack, player.worldObj, player, 0, false);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void onEquipped(ItemStack itemstack, EntityLivingBase player) {}
/* 24:   */   
/* 25:   */   public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {}
/* 26:   */   
/* 27:   */   public boolean canEquip(ItemStack itemstack, EntityLivingBase player)
/* 28:   */   {
/* 29:36 */     return false;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public boolean canUnequip(ItemStack itemstack, EntityLivingBase player)
/* 33:   */   {
/* 34:41 */     return false;
/* 35:   */   }
/* 36:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.modintegration.ItemBaubleAngelRing
 * JD-Core Version:    0.7.0.1
 */