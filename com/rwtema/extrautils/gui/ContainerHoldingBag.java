/*   1:    */ package com.rwtema.extrautils.gui;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import invtweaks.api.container.ChestContainer;
/*   5:    */ import invtweaks.api.container.ContainerSection;
/*   6:    */ import invtweaks.api.container.ContainerSectionCallback;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import net.minecraft.entity.player.EntityPlayer;
/*  11:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  12:    */ import net.minecraft.inventory.Container;
/*  13:    */ import net.minecraft.inventory.Slot;
/*  14:    */ import net.minecraft.item.ItemStack;
/*  15:    */ 
/*  16:    */ @ChestContainer
/*  17:    */ public class ContainerHoldingBag
/*  18:    */   extends Container
/*  19:    */ {
/*  20: 18 */   private EntityPlayer player = null;
/*  21: 19 */   private int currentFilter = -1;
/*  22:    */   private ItemStack itemStack;
/*  23:    */   
/*  24:    */   public ContainerHoldingBag(EntityPlayer player, int invId)
/*  25:    */   {
/*  26: 23 */     this.player = player;
/*  27: 24 */     this.currentFilter = invId;
/*  28:    */     
/*  29: 26 */     int i = 36;
/*  30: 28 */     for (int j = 0; j < 6; j++) {
/*  31: 29 */       for (int k = 0; k < 9; k++) {
/*  32: 30 */         addSlotToContainer(new SlotItemContainer(player.inventory, k + j * 9, 8 + k * 18, 18 + j * 18, this.currentFilter));
/*  33:    */       }
/*  34:    */     }
/*  35: 34 */     for (j = 0; j < 3; j++) {
/*  36: 35 */       for (int k = 0; k < 9; k++) {
/*  37: 36 */         addSlotToContainer(new Slot(player.inventory, k + j * 9 + 9, 8 + k * 18, 104 + j * 18 + i));
/*  38:    */       }
/*  39:    */     }
/*  40: 40 */     for (j = 0; j < 9; j++) {
/*  41: 41 */       if (j == this.currentFilter) {
/*  42: 42 */         addSlotToContainer(new SlotDisabled(player.inventory, j, 8 + j * 18, 162 + i));
/*  43:    */       } else {
/*  44: 44 */         addSlotToContainer(new Slot(player.inventory, j, 8 + j * 18, 162 + i));
/*  45:    */       }
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected void retrySlotClick(int par1, int par2, boolean par3, EntityPlayer par4EntityPlayer) {}
/*  50:    */   
/*  51:    */   @ContainerSectionCallback
/*  52:    */   public Map<ContainerSection, List<Slot>> getSlotType()
/*  53:    */   {
/*  54: 56 */     HashMap<ContainerSection, List<Slot>> hashMap = new HashMap();
/*  55: 57 */     hashMap.put(ContainerSection.CHEST, this.inventorySlots.subList(0, 54));
/*  56: 58 */     hashMap.put(ContainerSection.INVENTORY_NOT_HOTBAR, this.inventorySlots.subList(54, 81));
/*  57: 59 */     hashMap.put(ContainerSection.INVENTORY_HOTBAR, this.inventorySlots.subList(81, 90));
/*  58: 60 */     hashMap.put(ContainerSection.INVENTORY, this.inventorySlots.subList(54, 90));
/*  59: 61 */     return hashMap;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer par4EntityPlayer)
/*  63:    */   {
/*  64: 66 */     if ((par3 == 2) && (par2 == this.currentFilter)) {
/*  65: 67 */       return null;
/*  66:    */     }
/*  67: 70 */     ItemStack filter = this.player.inventory.getStackInSlot(this.currentFilter);
/*  68: 72 */     if ((filter == null) || (filter.getItem() != ExtraUtils.goldenBag)) {
/*  69: 73 */       return null;
/*  70:    */     }
/*  71: 76 */     return super.slotClick(par1, par2, par3, par4EntityPlayer);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
/*  75:    */   {
/*  76: 85 */     ItemStack itemstack = null;
/*  77: 86 */     Slot slot = (Slot)this.inventorySlots.get(par2);
/*  78: 88 */     if ((slot != null) && (slot.getHasStack()))
/*  79:    */     {
/*  80: 89 */       ItemStack itemstack1 = slot.getStack();
/*  81: 90 */       itemstack = itemstack1.copy();
/*  82: 92 */       if (par2 < 54)
/*  83:    */       {
/*  84: 93 */         if (!mergeItemStack(itemstack1, 54, this.inventorySlots.size(), true)) {
/*  85: 94 */           return null;
/*  86:    */         }
/*  87:    */       }
/*  88: 96 */       else if (!mergeItemStack(itemstack1, 0, 54, false)) {
/*  89: 97 */         return null;
/*  90:    */       }
/*  91:100 */       if (itemstack1.stackSize == 0)
/*  92:    */       {
/*  93:101 */         slot.putStack(null);
/*  94:102 */         return null;
/*  95:    */       }
/*  96:104 */       slot.onSlotChanged();
/*  97:    */     }
/*  98:108 */     return itemstack;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public boolean canInteractWith(EntityPlayer entityplayer)
/* 102:    */   {
/* 103:113 */     ItemStack filter = this.player.inventory.getStackInSlot(this.currentFilter);
/* 104:114 */     return (filter != null) && (filter.getItem() == ExtraUtils.goldenBag);
/* 105:    */   }
/* 106:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.gui.ContainerHoldingBag
 * JD-Core Version:    0.7.0.1
 */