/*  1:   */ package com.rwtema.extrautils.gui;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.tileentity.TileEntityTrashCan;
/*  4:   */ import invtweaks.api.container.ContainerSection;
/*  5:   */ import invtweaks.api.container.ContainerSectionCallback;
/*  6:   */ import invtweaks.api.container.InventoryContainer;
/*  7:   */ import java.util.List;
/*  8:   */ import java.util.Map;
/*  9:   */ import net.minecraft.entity.player.EntityPlayer;
/* 10:   */ import net.minecraft.inventory.Container;
/* 11:   */ import net.minecraft.inventory.IInventory;
/* 12:   */ import net.minecraft.inventory.Slot;
/* 13:   */ import net.minecraft.item.ItemStack;
/* 14:   */ 
/* 15:   */ @InventoryContainer
/* 16:   */ public class ContainerTrashCan
/* 17:   */   extends Container
/* 18:   */ {
/* 19:   */   private TileEntityTrashCan trashCan;
/* 20:   */   private IInventory player;
/* 21:   */   
/* 22:   */   public ContainerTrashCan(IInventory player, TileEntityTrashCan trashCan)
/* 23:   */   {
/* 24:22 */     this.trashCan = trashCan;
/* 25:23 */     addSlotToContainer(new SlotTrash(trashCan, 0, 80, 42));
/* 26:25 */     for (int iy = 0; iy < 3; iy++) {
/* 27:26 */       for (int ix = 0; ix < 9; ix++) {
/* 28:27 */         addSlotToContainer(new Slot(player, ix + iy * 9 + 9, 8 + ix * 18, 90 + iy * 18));
/* 29:   */       }
/* 30:   */     }
/* 31:31 */     for (int ix = 0; ix < 9; ix++) {
/* 32:32 */       addSlotToContainer(new Slot(player, ix, 8 + ix * 18, 148));
/* 33:   */     }
/* 34:   */   }
/* 35:   */   
/* 36:   */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
/* 37:   */   {
/* 38:42 */     ItemStack itemstack = null;
/* 39:43 */     Slot slot = (Slot)this.inventorySlots.get(par2);
/* 40:45 */     if ((slot != null) && (slot.getHasStack()))
/* 41:   */     {
/* 42:46 */       ItemStack itemstack1 = slot.getStack();
/* 43:47 */       itemstack = itemstack1.copy();
/* 44:49 */       if (par2 == 0)
/* 45:   */       {
/* 46:50 */         if (!mergeItemStack(itemstack1, 1, this.inventorySlots.size(), true)) {
/* 47:51 */           return null;
/* 48:   */         }
/* 49:   */       }
/* 50:   */       else
/* 51:   */       {
/* 52:54 */         TileEntityTrashCan.instantAdd = true;
/* 53:56 */         if (!mergeItemStack(itemstack1, 0, 1, false))
/* 54:   */         {
/* 55:57 */           TileEntityTrashCan.instantAdd = false;
/* 56:58 */           return null;
/* 57:   */         }
/* 58:61 */         TileEntityTrashCan.instantAdd = false;
/* 59:   */       }
/* 60:64 */       if (itemstack1.stackSize == 0) {
/* 61:65 */         slot.putStack(null);
/* 62:   */       } else {
/* 63:67 */         slot.onSlotChanged();
/* 64:   */       }
/* 65:   */     }
/* 66:71 */     return itemstack;
/* 67:   */   }
/* 68:   */   
/* 69:   */   public boolean canInteractWith(EntityPlayer entityplayer)
/* 70:   */   {
/* 71:76 */     return this.trashCan.isUseableByPlayer(entityplayer);
/* 72:   */   }
/* 73:   */   
/* 74:   */   @ContainerSectionCallback
/* 75:   */   public Map<ContainerSection, List<Slot>> getSlots()
/* 76:   */   {
/* 77:82 */     return InventoryTweaksHelper.getSlots(this, true);
/* 78:   */   }
/* 79:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.gui.ContainerTrashCan
 * JD-Core Version:    0.7.0.1
 */