/*  1:   */ package com.rwtema.extrautils.gui;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.tileentity.TileEntityFilingCabinet;
/*  4:   */ import java.util.List;
/*  5:   */ import net.minecraft.inventory.IInventory;
/*  6:   */ import net.minecraft.inventory.Slot;
/*  7:   */ import net.minecraft.item.ItemStack;
/*  8:   */ 
/*  9:   */ public class SlotFilingCabinet
/* 10:   */   extends Slot
/* 11:   */ {
/* 12: 9 */   public static boolean drawing = false;
/* 13:   */   
/* 14:   */   public SlotFilingCabinet(IInventory par1iInventory, int par2, int par3, int par4)
/* 15:   */   {
/* 16:12 */     super(par1iInventory, par2, par3, par4);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public ItemStack getStack()
/* 20:   */   {
/* 21:17 */     if ((drawing) && (getSlotIndex() >= ((TileEntityFilingCabinet)this.inventory).itemSlots.size())) {
/* 22:18 */       return null;
/* 23:   */     }
/* 24:20 */     return super.getStack();
/* 25:   */   }
/* 26:   */   
/* 27:   */   public boolean isItemValid(ItemStack item)
/* 28:   */   {
/* 29:26 */     return this.inventory.isItemValidForSlot(getSlotIndex(), item);
/* 30:   */   }
/* 31:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.gui.SlotFilingCabinet
 * JD-Core Version:    0.7.0.1
 */