/*  1:   */ package com.rwtema.extrautils.gui;
/*  2:   */ 
/*  3:   */ import net.minecraft.inventory.IInventory;
/*  4:   */ import net.minecraft.inventory.Slot;
/*  5:   */ import net.minecraft.item.ItemStack;
/*  6:   */ 
/*  7:   */ public class SlotChecksValidity
/*  8:   */   extends Slot
/*  9:   */ {
/* 10:   */   public SlotChecksValidity(IInventory par1iInventory, int par2, int par3, int par4)
/* 11:   */   {
/* 12: 9 */     super(par1iInventory, par2, par3, par4);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public boolean isItemValid(ItemStack itemstack)
/* 16:   */   {
/* 17:18 */     return this.inventory.isItemValidForSlot(this.slotNumber, itemstack);
/* 18:   */   }
/* 19:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.gui.SlotChecksValidity
 * JD-Core Version:    0.7.0.1
 */