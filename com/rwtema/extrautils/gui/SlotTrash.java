/*  1:   */ package com.rwtema.extrautils.gui;
/*  2:   */ 
/*  3:   */ import net.minecraft.inventory.IInventory;
/*  4:   */ import net.minecraft.inventory.Slot;
/*  5:   */ import net.minecraft.item.ItemStack;
/*  6:   */ 
/*  7:   */ public class SlotTrash
/*  8:   */   extends Slot
/*  9:   */ {
/* 10:   */   public SlotTrash(IInventory par1iInventory, int par2, int par3, int par4)
/* 11:   */   {
/* 12:10 */     super(par1iInventory, par2, par3, par4);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public boolean isItemValid(ItemStack p_75214_1_)
/* 16:   */   {
/* 17:15 */     return !getHasStack();
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void putStack(ItemStack par1ItemStack)
/* 21:   */   {
/* 22:20 */     if (par1ItemStack != null) {
/* 23:21 */       for (int i = 0; i < 64; i++) {
/* 24:22 */         if (this.inventory.getStackInSlot(i) == null)
/* 25:   */         {
/* 26:23 */           com.rwtema.extrautils.tileentity.TileEntityTrashCan.instantAdd = true;
/* 27:24 */           this.inventory.setInventorySlotContents(i, par1ItemStack);
/* 28:25 */           com.rwtema.extrautils.tileentity.TileEntityTrashCan.instantAdd = false;
/* 29:26 */           return;
/* 30:   */         }
/* 31:   */       }
/* 32:   */     }
/* 33:30 */     this.inventory.setInventorySlotContents(getSlotIndex(), par1ItemStack);
/* 34:31 */     onSlotChanged();
/* 35:   */   }
/* 36:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.gui.SlotTrash
 * JD-Core Version:    0.7.0.1
 */