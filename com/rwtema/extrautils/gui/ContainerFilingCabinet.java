/*   1:    */ package com.rwtema.extrautils.gui;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.tileentity.TileEntityFilingCabinet;
/*   4:    */ import cpw.mods.fml.relauncher.Side;
/*   5:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   6:    */ import invtweaks.api.container.ContainerSection;
/*   7:    */ import invtweaks.api.container.ContainerSectionCallback;
/*   8:    */ import invtweaks.api.container.InventoryContainer;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import net.minecraft.entity.player.EntityPlayer;
/*  12:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  13:    */ import net.minecraft.inventory.Container;
/*  14:    */ import net.minecraft.inventory.IInventory;
/*  15:    */ import net.minecraft.inventory.Slot;
/*  16:    */ import net.minecraft.item.ItemStack;
/*  17:    */ 
/*  18:    */ @InventoryContainer
/*  19:    */ public class ContainerFilingCabinet
/*  20:    */   extends Container
/*  21:    */ {
/*  22:    */   public ContainerFilingCabinet(IInventory player, TileEntityFilingCabinet cabinet, boolean client)
/*  23:    */   {
/*  24: 32 */     this.cabinet = cabinet;
/*  25: 33 */     this.client = client;
/*  26: 34 */     for (int j = 0; j < cabinet.getMaxSlots(); j++) {
/*  27: 35 */       addSlotToContainer(new SlotFilingCabinet(cabinet, j, 8 + j * 18, 18));
/*  28:    */     }
/*  29: 37 */     for (int j = 0; j < 3; j++) {
/*  30: 38 */       for (int k = 0; k < 9; k++) {
/*  31: 39 */         addSlotToContainer(new Slot(player, k + j * 9 + 9, 8 + k * 18, 158 + j * 18));
/*  32:    */       }
/*  33:    */     }
/*  34: 42 */     for (int j = 0; j < 9; j++) {
/*  35: 43 */       addSlotToContainer(new Slot(player, j, 8 + j * 18, 216));
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   protected void retrySlotClick(int par1, int par2, boolean par3, EntityPlayer par4EntityPlayer) {}
/*  40:    */   
/*  41:    */   public void putStackInSlot(int par1, ItemStack par2ItemStack)
/*  42:    */   {
/*  43: 51 */     updated = true;
/*  44: 52 */     super.putStackInSlot(par1, par2ItemStack);
/*  45:    */   }
/*  46:    */   
/*  47:    */   @SideOnly(Side.CLIENT)
/*  48:    */   public void putStacksInSlots(ItemStack[] par1ArrayOfItemStack)
/*  49:    */   {
/*  50: 57 */     updated = true;
/*  51: 58 */     for (int i = 0; i < par1ArrayOfItemStack.length; i++) {
/*  52: 59 */       getSlot(i).putStack(par1ArrayOfItemStack[i]);
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public boolean canDragIntoSlot(Slot par1Slot)
/*  57:    */   {
/*  58: 64 */     return par1Slot.slotNumber >= this.cabinet.getMaxSlots();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer par4EntityPlayer)
/*  62:    */   {
/*  63: 68 */     updated = true;
/*  64: 70 */     if (par3 == 2) {
/*  65: 70 */       return null;
/*  66:    */     }
/*  67:    */     ItemStack item;
/*  68:    */     ItemStack item;
/*  69: 71 */     if ((par1 >= 0) && (par1 < this.cabinet.getMaxSlots()) && (par4EntityPlayer.inventory.getItemStack() != null)) {
/*  70: 72 */       item = super.slotClick(this.cabinet.getSizeInventory() - 1, par2, par3, par4EntityPlayer);
/*  71:    */     } else {
/*  72: 75 */       item = super.slotClick(par1, par2, par3, par4EntityPlayer);
/*  73:    */     }
/*  74: 77 */     this.cabinet.handleInput();
/*  75: 78 */     return item;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
/*  79:    */   {
/*  80: 82 */     ItemStack itemstack = null;
/*  81: 83 */     Slot slot = (Slot)this.inventorySlots.get(par2);
/*  82: 84 */     if ((slot != null) && (slot.getHasStack()))
/*  83:    */     {
/*  84: 85 */       ItemStack itemstack2 = slot.getStack();
/*  85: 86 */       itemstack = itemstack2.copy();
/*  86: 87 */       if (par2 < this.cabinet.getMaxSlots())
/*  87:    */       {
/*  88: 88 */         int m = Math.min(itemstack2.stackSize, itemstack2.getMaxStackSize());
/*  89: 89 */         itemstack2.stackSize = m;
/*  90: 90 */         if (!mergeItemStack(itemstack2, this.cabinet.getMaxSlots(), this.inventorySlots.size(), true)) {
/*  91: 91 */           return null;
/*  92:    */         }
/*  93: 93 */         itemstack2.stackSize += itemstack.stackSize - m;
/*  94:    */       }
/*  95: 95 */       else if ((!this.cabinet.isItemValidForSlot(this.cabinet.getSizeInventory() - 1, itemstack2)) || (!mergeItemStack(itemstack2, 0, this.cabinet.getMaxSlots(), false)))
/*  96:    */       {
/*  97: 96 */         return null;
/*  98:    */       }
/*  99: 98 */       if (itemstack2.stackSize == 0) {
/* 100: 99 */         slot.putStack((ItemStack)null);
/* 101:    */       } else {
/* 102:102 */         slot.onSlotChanged();
/* 103:    */       }
/* 104:    */     }
/* 105:105 */     return itemstack;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public boolean canInteractWith(EntityPlayer entityplayer)
/* 109:    */   {
/* 110:109 */     return this.cabinet.isUseableByPlayer(entityplayer);
/* 111:    */   }
/* 112:    */   
/* 113:    */   @ContainerSectionCallback
/* 114:    */   public Map<ContainerSection, List<Slot>> getSlots()
/* 115:    */   {
/* 116:114 */     return InventoryTweaksHelper.getSlots(this, true);
/* 117:    */   }
/* 118:    */   
/* 119:118 */   public static boolean updated = false;
/* 120:    */   private TileEntityFilingCabinet cabinet;
/* 121:    */   private int mimicThreshold;
/* 122:    */   private boolean client;
/* 123:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.gui.ContainerFilingCabinet
 * JD-Core Version:    0.7.0.1
 */