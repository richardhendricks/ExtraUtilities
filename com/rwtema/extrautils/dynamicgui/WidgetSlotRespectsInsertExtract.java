/*  1:   */ package com.rwtema.extrautils.dynamicgui;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.player.EntityPlayer;
/*  4:   */ import net.minecraft.inventory.ISidedInventory;
/*  5:   */ import net.minecraft.item.ItemStack;
/*  6:   */ 
/*  7:   */ public class WidgetSlotRespectsInsertExtract
/*  8:   */   extends WidgetSlot
/*  9:   */ {
/* 10:   */   public WidgetSlotRespectsInsertExtract(ISidedInventory inv, int slot, int x, int y)
/* 11:   */   {
/* 12: 9 */     super(inv, slot, x, y);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public boolean canTakeStack(EntityPlayer par1EntityPlayer)
/* 16:   */   {
/* 17:14 */     if (getStack() == null) {
/* 18:15 */       return false;
/* 19:   */     }
/* 20:17 */     return ((ISidedInventory)this.inventory).canExtractItem(this.slotNumber, getStack(), 0);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public boolean isItemValid(ItemStack par1ItemStack)
/* 24:   */   {
/* 25:22 */     if ((getHasStack()) || (!super.isItemValid(par1ItemStack))) {
/* 26:23 */       return false;
/* 27:   */     }
/* 28:25 */     return ((ISidedInventory)this.inventory).canInsertItem(this.slotNumber, par1ItemStack, 0);
/* 29:   */   }
/* 30:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.dynamicgui.WidgetSlotRespectsInsertExtract
 * JD-Core Version:    0.7.0.1
 */