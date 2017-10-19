/*   1:    */ package com.rwtema.extrautils.tileentity;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.item.ItemNodeUpgrade;
/*   5:    */ import net.minecraft.entity.player.EntityPlayer;
/*   6:    */ import net.minecraft.inventory.IInventory;
/*   7:    */ import net.minecraft.item.ItemStack;
/*   8:    */ import net.minecraft.nbt.NBTTagCompound;
/*   9:    */ import net.minecraft.tileentity.TileEntity;
/*  10:    */ import net.minecraft.world.World;
/*  11:    */ 
/*  12:    */ public class TileEntityTrashCan
/*  13:    */   extends TileEntity
/*  14:    */   implements IInventory
/*  15:    */ {
/*  16:    */   public static final int NUM_SLOTS = 64;
/*  17: 13 */   private ItemStack[] itemSlots = new ItemStack[64];
/*  18: 14 */   public static boolean instantAdd = false;
/*  19: 15 */   private boolean added = false;
/*  20:    */   private boolean checkedValid;
/*  21:    */   
/*  22:    */   public void updateEntity()
/*  23:    */   {
/*  24: 20 */     if ((!this.checkedValid) && (this.worldObj != null)) {
/*  25: 21 */       if ((ExtraUtils.trashCan == null) || (this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord) != ExtraUtils.trashCan))
/*  26:    */       {
/*  27: 22 */         this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
/*  28: 23 */         invalidate();
/*  29:    */       }
/*  30:    */       else
/*  31:    */       {
/*  32: 25 */         this.checkedValid = true;
/*  33:    */       }
/*  34:    */     }
/*  35: 29 */     if (this.added)
/*  36:    */     {
/*  37: 30 */       this.added = false;
/*  38: 31 */       processInv();
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void readFromNBT(NBTTagCompound par1NBTTagCompound)
/*  43:    */   {
/*  44: 40 */     super.readFromNBT(par1NBTTagCompound);
/*  45: 42 */     if (par1NBTTagCompound.hasKey("filter")) {
/*  46: 43 */       this.itemSlots[0] = ItemStack.loadItemStackFromNBT(par1NBTTagCompound.getCompoundTag("filter"));
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void writeToNBT(NBTTagCompound par1NBTTagCompound)
/*  51:    */   {
/*  52: 52 */     super.writeToNBT(par1NBTTagCompound);
/*  53: 54 */     if (this.itemSlots[0] != null)
/*  54:    */     {
/*  55: 55 */       NBTTagCompound itemTag = new NBTTagCompound();
/*  56: 56 */       this.itemSlots[0].writeToNBT(itemTag);
/*  57: 57 */       par1NBTTagCompound.setTag("filter", itemTag);
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void processInv()
/*  62:    */   {
/*  63: 62 */     if ((this.itemSlots[0] != null) && (
/*  64: 63 */       (ExtraUtils.nodeUpgrade == null) || (!ItemNodeUpgrade.isFilter(this.itemSlots[0])))) {
/*  65: 66 */       this.itemSlots[0] = null;
/*  66:    */     }
/*  67: 69 */     for (int i = 1; i < 64; i++) {
/*  68: 70 */       this.itemSlots[i] = null;
/*  69:    */     }
/*  70: 73 */     onInventoryChanged();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getSizeInventory()
/*  74:    */   {
/*  75: 78 */     return 65;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public ItemStack getStackInSlot(int i)
/*  79:    */   {
/*  80: 83 */     return i == 64 ? null : this.itemSlots[i];
/*  81:    */   }
/*  82:    */   
/*  83:    */   public ItemStack decrStackSize(int par1, int par2)
/*  84:    */   {
/*  85: 88 */     if (par1 == 64) {
/*  86: 89 */       return null;
/*  87:    */     }
/*  88: 91 */     if (this.itemSlots[par1] != null)
/*  89:    */     {
/*  90: 94 */       if (this.itemSlots[par1].stackSize <= par2)
/*  91:    */       {
/*  92: 95 */         ItemStack itemstack = this.itemSlots[par1];
/*  93: 96 */         this.itemSlots[par1] = null;
/*  94: 97 */         onInventoryChanged();
/*  95: 98 */         return itemstack;
/*  96:    */       }
/*  97:100 */       ItemStack itemstack = this.itemSlots[par1].splitStack(par2);
/*  98:102 */       if (this.itemSlots[par1].stackSize == 0) {
/*  99:103 */         this.itemSlots[par1] = null;
/* 100:    */       }
/* 101:106 */       onInventoryChanged();
/* 102:107 */       return itemstack;
/* 103:    */     }
/* 104:110 */     return null;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public ItemStack getStackInSlotOnClosing(int i)
/* 108:    */   {
/* 109:116 */     return getStackInSlot(i);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setInventorySlotContents(int i, ItemStack itemstack)
/* 113:    */   {
/* 114:121 */     if (i == 64) {
/* 115:122 */       return;
/* 116:    */     }
/* 117:123 */     this.itemSlots[i] = itemstack;
/* 118:124 */     onInventoryChanged();
/* 119:126 */     if (instantAdd) {
/* 120:127 */       processInv();
/* 121:    */     } else {
/* 122:129 */       this.added = true;
/* 123:    */     }
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String getInventoryName()
/* 127:    */   {
/* 128:135 */     return "TrashCan";
/* 129:    */   }
/* 130:    */   
/* 131:    */   public boolean isInventoryNameLocalized()
/* 132:    */   {
/* 133:140 */     return false;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public int getInventoryStackLimit()
/* 137:    */   {
/* 138:145 */     return 64;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
/* 142:    */   {
/* 143:154 */     return (this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this) && (par1EntityPlayer.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D);
/* 144:    */   }
/* 145:    */   
/* 146:    */   public boolean isItemValidForSlot(int i, ItemStack itemstack)
/* 147:    */   {
/* 148:159 */     return (i <= 64) && ((this.itemSlots[0] == null) || (ItemNodeUpgrade.matchesFilterItem(itemstack, this.itemSlots[0])));
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void openInventory() {}
/* 152:    */   
/* 153:    */   public void closeInventory() {}
/* 154:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.TileEntityTrashCan
 * JD-Core Version:    0.7.0.1
 */