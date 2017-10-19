/*   1:    */ package com.rwtema.extrautils.gui;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   4:    */ import invtweaks.api.container.ContainerSection;
/*   5:    */ import invtweaks.api.container.ContainerSectionCallback;
/*   6:    */ import invtweaks.api.container.InventoryContainer;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import net.minecraft.entity.player.EntityPlayer;
/*  10:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  11:    */ import net.minecraft.inventory.Container;
/*  12:    */ import net.minecraft.inventory.Slot;
/*  13:    */ import net.minecraft.item.ItemStack;
/*  14:    */ import net.minecraft.nbt.NBTTagCompound;
/*  15:    */ import net.minecraftforge.fluids.FluidContainerRegistry;
/*  16:    */ 
/*  17:    */ @InventoryContainer
/*  18:    */ public class ContainerFilter
/*  19:    */   extends Container
/*  20:    */ {
/*  21: 19 */   private EntityPlayer player = null;
/*  22: 20 */   private int currentFilter = -1;
/*  23:    */   
/*  24:    */   public ContainerFilter(EntityPlayer player, int invId)
/*  25:    */   {
/*  26: 23 */     this.player = player;
/*  27: 24 */     this.currentFilter = invId;
/*  28: 27 */     for (int k = 0; k < 9; k++) {
/*  29: 28 */       addSlotToContainer(new SlotGhostItemContainer(player.inventory, k, 8 + k * 18, 18, this.currentFilter));
/*  30:    */     }
/*  31: 31 */     for (int j = 0; j < 3; j++) {
/*  32: 32 */       for (k = 0; k < 9; k++) {
/*  33: 33 */         addSlotToContainer(new Slot(player.inventory, k + j * 9 + 9, 8 + k * 18, 50 + j * 18));
/*  34:    */       }
/*  35:    */     }
/*  36: 37 */     for (j = 0; j < 9; j++) {
/*  37: 38 */       if (j == this.currentFilter) {
/*  38: 39 */         addSlotToContainer(new SlotDisabled(player.inventory, j, 8 + j * 18, 108));
/*  39:    */       } else {
/*  40: 41 */         addSlotToContainer(new Slot(player.inventory, j, 8 + j * 18, 108));
/*  41:    */       }
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer par4EntityPlayer)
/*  46:    */   {
/*  47: 48 */     if ((par1 >= 0) && (par1 < 9))
/*  48:    */     {
/*  49: 49 */       ItemStack item = par4EntityPlayer.inventory.getItemStack();
/*  50:    */       
/*  51: 51 */       return clickItemStack(par1, item);
/*  52:    */     }
/*  53: 53 */     return super.slotClick(par1, par2, par3, par4EntityPlayer);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public ItemStack clickItemStack(int par1, ItemStack item)
/*  57:    */   {
/*  58: 58 */     if (item != null)
/*  59:    */     {
/*  60: 59 */       item = item.copy();
/*  61: 60 */       item.stackSize = 1;
/*  62:    */     }
/*  63: 63 */     String keyname = "items_" + par1;
/*  64:    */     
/*  65: 65 */     ItemStack filter = this.player.inventory.getStackInSlot(this.currentFilter);
/*  66: 67 */     if (filter == null) {
/*  67: 68 */       return item;
/*  68:    */     }
/*  69: 71 */     NBTTagCompound tags = filter.getTagCompound();
/*  70: 73 */     if (item != null)
/*  71:    */     {
/*  72: 74 */       if (tags == null) {
/*  73: 75 */         tags = new NBTTagCompound();
/*  74:    */       }
/*  75: 78 */       if (tags.hasKey(keyname))
/*  76:    */       {
/*  77: 79 */         if ((FluidContainerRegistry.isFilledContainer(item)) && (ItemStack.areItemStacksEqual(ItemStack.loadItemStackFromNBT(tags.getCompoundTag(keyname)), item)))
/*  78:    */         {
/*  79: 80 */           NBTTagCompound fluidTags = new NBTTagCompound();
/*  80: 82 */           if (tags.hasKey("isLiquid_" + par1)) {
/*  81: 83 */             tags.removeTag("isLiquid_" + par1);
/*  82:    */           } else {
/*  83: 85 */             tags.setBoolean("isLiquid_" + par1, true);
/*  84:    */           }
/*  85: 88 */           return item;
/*  86:    */         }
/*  87: 90 */         if (tags.hasKey("isLiquid_" + par1)) {
/*  88: 91 */           tags.removeTag("isLiquid_" + par1);
/*  89:    */         }
/*  90: 94 */         tags.removeTag(keyname);
/*  91:    */       }
/*  92: 96 */       else if (tags.hasKey("isLiquid_" + par1))
/*  93:    */       {
/*  94: 97 */         tags.removeTag("isLiquid_" + par1);
/*  95:    */       }
/*  96:100 */       NBTTagCompound itemTags = new NBTTagCompound();
/*  97:101 */       item.writeToNBT(itemTags);
/*  98:102 */       tags.setTag(keyname, itemTags);
/*  99:103 */       filter.setTagCompound(tags);
/* 100:    */     }
/* 101:105 */     else if (tags != null)
/* 102:    */     {
/* 103:106 */       if (tags.hasKey("isLiquid_" + par1)) {
/* 104:107 */         tags.removeTag("isLiquid_" + par1);
/* 105:    */       }
/* 106:110 */       tags.removeTag(keyname);
/* 107:112 */       if (tags.hasNoTags()) {
/* 108:113 */         filter.setTagCompound(null);
/* 109:    */       } else {
/* 110:115 */         filter.setTagCompound(tags);
/* 111:    */       }
/* 112:    */     }
/* 113:120 */     return item;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
/* 117:    */   {
/* 118:129 */     Slot slot = (Slot)this.inventorySlots.get(par2);
/* 119:131 */     if ((slot != null) && (slot.getHasStack())) {
/* 120:132 */       if ((slot instanceof SlotGhostItemContainer)) {
/* 121:133 */         slotClick(slot.slotNumber, 0, 0, par1EntityPlayer);
/* 122:    */       } else {
/* 123:135 */         for (int i = 0; i < 9; i++)
/* 124:    */         {
/* 125:136 */           if (!((SlotGhostItemContainer)this.inventorySlots.get(i)).getHasStack())
/* 126:    */           {
/* 127:137 */             clickItemStack(i, slot.getStack());
/* 128:138 */             return null;
/* 129:    */           }
/* 130:140 */           if (XUHelper.canItemsStack(slot.getStack(), ((SlotGhostItemContainer)this.inventorySlots.get(i)).getStack())) {
/* 131:141 */             return null;
/* 132:    */           }
/* 133:    */         }
/* 134:    */       }
/* 135:    */     }
/* 136:148 */     return null;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public boolean canInteractWith(EntityPlayer entityplayer)
/* 140:    */   {
/* 141:153 */     return true;
/* 142:    */   }
/* 143:    */   
/* 144:    */   @ContainerSectionCallback
/* 145:    */   public Map<ContainerSection, List<Slot>> getSlots()
/* 146:    */   {
/* 147:159 */     return InventoryTweaksHelper.getSlots(this, true);
/* 148:    */   }
/* 149:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.gui.ContainerFilter
 * JD-Core Version:    0.7.0.1
 */