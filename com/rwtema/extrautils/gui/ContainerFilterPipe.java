/*  1:   */ package com.rwtema.extrautils.gui;
/*  2:   */ 
/*  3:   */ import invtweaks.api.container.ContainerSection;
/*  4:   */ import invtweaks.api.container.ContainerSectionCallback;
/*  5:   */ import invtweaks.api.container.InventoryContainer;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import net.minecraft.entity.player.EntityPlayer;
/*  9:   */ import net.minecraft.inventory.Container;
/* 10:   */ import net.minecraft.inventory.IInventory;
/* 11:   */ import net.minecraft.inventory.Slot;
/* 12:   */ import net.minecraft.item.ItemStack;
/* 13:   */ 
/* 14:   */ @InventoryContainer
/* 15:   */ public class ContainerFilterPipe
/* 16:   */   extends Container
/* 17:   */ {
/* 18:   */   public ContainerFilterPipe(IInventory player, IInventory pipe)
/* 19:   */   {
/* 20:19 */     addSlotToContainer(new Slot(pipe, 0, 80, 90));
/* 21:20 */     addSlotToContainer(new Slot(pipe, 1, 80, 15));
/* 22:21 */     addSlotToContainer(new Slot(pipe, 2, 43, 33));
/* 23:22 */     addSlotToContainer(new Slot(pipe, 3, 117, 72));
/* 24:23 */     addSlotToContainer(new Slot(pipe, 4, 43, 72));
/* 25:24 */     addSlotToContainer(new Slot(pipe, 5, 117, 33));
/* 26:26 */     for (int iy = 0; iy < 3; iy++) {
/* 27:27 */       for (int ix = 0; ix < 9; ix++) {
/* 28:28 */         addSlotToContainer(new Slot(player, ix + iy * 9 + 9, 8 + ix * 18, 111 + iy * 18));
/* 29:   */       }
/* 30:   */     }
/* 31:32 */     for (int ix = 0; ix < 9; ix++) {
/* 32:33 */       addSlotToContainer(new Slot(player, ix, 8 + ix * 18, 169));
/* 33:   */     }
/* 34:   */   }
/* 35:   */   
/* 36:   */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
/* 37:   */   {
/* 38:43 */     return null;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public boolean canInteractWith(EntityPlayer entityplayer)
/* 42:   */   {
/* 43:48 */     return true;
/* 44:   */   }
/* 45:   */   
/* 46:   */   @ContainerSectionCallback
/* 47:   */   public Map<ContainerSection, List<Slot>> getSlots()
/* 48:   */   {
/* 49:54 */     return InventoryTweaksHelper.getSlots(this, true);
/* 50:   */   }
/* 51:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.gui.ContainerFilterPipe
 * JD-Core Version:    0.7.0.1
 */