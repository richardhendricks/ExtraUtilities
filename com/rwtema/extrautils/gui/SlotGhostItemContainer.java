/*  1:   */ package com.rwtema.extrautils.gui;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.player.EntityPlayer;
/*  4:   */ import net.minecraft.inventory.IInventory;
/*  5:   */ 
/*  6:   */ public class SlotGhostItemContainer
/*  7:   */   extends SlotItemContainer
/*  8:   */ {
/*  9:   */   public SlotGhostItemContainer(IInventory par1iInventory, int slot, int x, int y, int filterIndex)
/* 10:   */   {
/* 11: 8 */     super(par1iInventory, slot, x, y, filterIndex);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public boolean canTakeStack(EntityPlayer par1EntityPlayer)
/* 15:   */   {
/* 16:13 */     return false;
/* 17:   */   }
/* 18:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.gui.SlotGhostItemContainer
 * JD-Core Version:    0.7.0.1
 */