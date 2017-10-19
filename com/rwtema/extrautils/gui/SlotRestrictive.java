/*  1:   */ package com.rwtema.extrautils.gui;
/*  2:   */ 
/*  3:   */ import net.minecraft.inventory.IInventory;
/*  4:   */ import net.minecraft.inventory.Slot;
/*  5:   */ import net.minecraft.item.ItemStack;
/*  6:   */ 
/*  7:   */ public class SlotRestrictive
/*  8:   */   extends Slot
/*  9:   */ {
/* 10: 9 */   ItemStack item = null;
/* 11:   */   
/* 12:   */   public SlotRestrictive(IInventory par1iInventory, int par2, int par3, int par4, ItemStack item)
/* 13:   */   {
/* 14:12 */     super(par1iInventory, par2, par3, par4);
/* 15:13 */     this.item = item;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public boolean isItemValid(ItemStack par1ItemStack)
/* 19:   */   {
/* 20:22 */     if ((this.item != null) && (par1ItemStack != null) && 
/* 21:23 */       (par1ItemStack.getItem() == this.item.getItem()) && ((par1ItemStack.getItemDamage() == this.item.getItemDamage()) || (this.item.getItemDamage() == 32767))) {
/* 22:24 */       return true;
/* 23:   */     }
/* 24:27 */     return false;
/* 25:   */   }
/* 26:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.gui.SlotRestrictive
 * JD-Core Version:    0.7.0.1
 */