/*   1:    */ package com.rwtema.extrautils.gui;
/*   2:    */ 
/*   3:    */ import net.minecraft.inventory.IInventory;
/*   4:    */ import net.minecraft.inventory.InventoryBasic;
/*   5:    */ import net.minecraft.inventory.Slot;
/*   6:    */ import net.minecraft.item.ItemStack;
/*   7:    */ import net.minecraft.nbt.NBTTagCompound;
/*   8:    */ 
/*   9:    */ public class SlotItemContainer
/*  10:    */   extends Slot
/*  11:    */ {
/*  12: 10 */   static boolean checking = false;
/*  13: 11 */   static boolean iterating = false;
/*  14: 12 */   private static IInventory fakeInv = new InventoryBasic("fakeInventory", true, 54);
/*  15: 13 */   int filterIndex = -1;
/*  16: 14 */   ItemStack curStack = null;
/*  17:    */   private IInventory filterInv;
/*  18:    */   
/*  19:    */   public SlotItemContainer(IInventory par1iInventory, int slot, int x, int y, int filterIndex)
/*  20:    */   {
/*  21: 18 */     super(fakeInv, slot, x, y);
/*  22: 19 */     this.filterInv = par1iInventory;
/*  23: 20 */     this.filterIndex = filterIndex;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public ItemStack getStack()
/*  27:    */   {
/*  28: 28 */     ItemStack filter = this.filterInv.getStackInSlot(this.filterIndex);
/*  29: 30 */     if ((filter != null) && (filter.getTagCompound() != null) && 
/*  30: 31 */       (filter.getTagCompound().hasKey("items_" + getSlotIndex())))
/*  31:    */     {
/*  32: 32 */       if (!checking)
/*  33:    */       {
/*  34: 33 */         this.curStack = ItemStack.loadItemStackFromNBT(filter.getTagCompound().getCompoundTag("items_" + getSlotIndex()));
/*  35: 34 */         return this.curStack;
/*  36:    */       }
/*  37: 36 */       return ItemStack.loadItemStackFromNBT(filter.getTagCompound().getCompoundTag("items_" + getSlotIndex()));
/*  38:    */     }
/*  39: 41 */     if (!checking)
/*  40:    */     {
/*  41: 42 */       this.curStack = null;
/*  42: 43 */       return null;
/*  43:    */     }
/*  44: 45 */     return null;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public ItemStack decrStackSize(int par1)
/*  48:    */   {
/*  49: 55 */     ItemStack curItem = getStack();
/*  50: 57 */     if (curItem != null)
/*  51:    */     {
/*  52: 60 */       if (curItem.stackSize <= par1)
/*  53:    */       {
/*  54: 61 */         ItemStack itemstack = curItem;
/*  55: 62 */         putStack(null);
/*  56: 63 */         return itemstack;
/*  57:    */       }
/*  58: 65 */       ItemStack itemstack = curItem.splitStack(par1);
/*  59: 67 */       if (curItem.stackSize == 0) {
/*  60: 68 */         putStack(null);
/*  61:    */       }
/*  62: 71 */       return itemstack;
/*  63:    */     }
/*  64: 74 */     return null;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public boolean getHasStack()
/*  68:    */   {
/*  69: 83 */     return getStack() != null;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void putStack(ItemStack par1ItemStack)
/*  73:    */   {
/*  74: 91 */     ItemStack filter = this.filterInv.getStackInSlot(this.filterIndex);
/*  75: 93 */     if (filter == null) {
/*  76: 94 */       return;
/*  77:    */     }
/*  78: 97 */     NBTTagCompound tags = filter.getTagCompound();
/*  79: 99 */     if (par1ItemStack != null)
/*  80:    */     {
/*  81:100 */       if (tags == null) {
/*  82:101 */         tags = new NBTTagCompound();
/*  83:    */       }
/*  84:104 */       if (tags.hasKey("items_" + getSlotIndex())) {
/*  85:105 */         tags.removeTag("items_" + getSlotIndex());
/*  86:    */       }
/*  87:108 */       NBTTagCompound itemTags = new NBTTagCompound();
/*  88:109 */       par1ItemStack.writeToNBT(itemTags);
/*  89:110 */       tags.setTag("items_" + getSlotIndex(), itemTags);
/*  90:111 */       filter.setTagCompound(tags);
/*  91:    */     }
/*  92:113 */     else if (tags != null)
/*  93:    */     {
/*  94:114 */       tags.removeTag("items_" + getSlotIndex());
/*  95:116 */       if (tags.hasNoTags()) {
/*  96:117 */         filter.setTagCompound(null);
/*  97:    */       } else {
/*  98:119 */         filter.setTagCompound(tags);
/*  99:    */       }
/* 100:    */     }
/* 101:124 */     if (par1ItemStack != null) {
/* 102:125 */       this.curStack = par1ItemStack;
/* 103:    */     } else {
/* 104:127 */       this.curStack = null;
/* 105:    */     }
/* 106:130 */     if (!iterating) {
/* 107:131 */       onSlotChanged();
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void onSlotChanged()
/* 112:    */   {
/* 113:140 */     checking = true;
/* 114:141 */     ItemStack oldItem = getStack();
/* 115:142 */     checking = false;
/* 116:143 */     boolean flag = false;
/* 117:145 */     if (!ItemStack.areItemStacksEqual(oldItem, this.curStack))
/* 118:    */     {
/* 119:146 */       iterating = true;
/* 120:147 */       putStack(this.curStack);
/* 121:148 */       iterating = false;
/* 122:    */     }
/* 123:151 */     this.filterInv.onInventoryChanged();
/* 124:    */   }
/* 125:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.gui.SlotItemContainer
 * JD-Core Version:    0.7.0.1
 */