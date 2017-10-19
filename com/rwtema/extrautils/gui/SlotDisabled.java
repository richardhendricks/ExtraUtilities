/*  1:   */ package com.rwtema.extrautils.gui;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.player.EntityPlayer;
/*  4:   */ import net.minecraft.inventory.IInventory;
/*  5:   */ import net.minecraft.inventory.Slot;
/*  6:   */ import net.minecraft.item.ItemStack;
/*  7:   */ 
/*  8:   */ public class SlotDisabled
/*  9:   */   extends Slot
/* 10:   */ {
/* 11:   */   public SlotDisabled(IInventory par1iInventory, int par2, int par3, int par4)
/* 12:   */   {
/* 13:10 */     super(par1iInventory, par2, par3, par4);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public boolean isItemValid(ItemStack par1ItemStack)
/* 17:   */   {
/* 18:19 */     return false;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean canTakeStack(EntityPlayer par1EntityPlayer)
/* 22:   */   {
/* 23:27 */     return false;
/* 24:   */   }
/* 25:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.gui.SlotDisabled
 * JD-Core Version:    0.7.0.1
 */