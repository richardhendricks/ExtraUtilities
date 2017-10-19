/*   1:    */ package com.rwtema.extrautils.tileentity.generators;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.player.EntityPlayer;
/*   4:    */ import net.minecraft.inventory.ISidedInventory;
/*   5:    */ import net.minecraft.item.Item;
/*   6:    */ import net.minecraft.item.ItemStack;
/*   7:    */ import net.minecraft.nbt.NBTTagCompound;
/*   8:    */ 
/*   9:    */ public class TileEntityGeneratorFurnace
/*  10:    */   extends TileEntityGenerator
/*  11:    */   implements ISidedInventory
/*  12:    */ {
/*  13:  9 */   InventoryGeneric inv = new InventoryGeneric("generatorFurnace", false, 1);
/*  14: 10 */   int[] slots = null;
/*  15:    */   
/*  16:    */   public int transferLimit()
/*  17:    */   {
/*  18: 14 */     return 400;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public void readFromNBT(NBTTagCompound nbt)
/*  22:    */   {
/*  23: 19 */     super.readFromNBT(nbt);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public boolean isItemValidForSlot(int i, ItemStack itemstack)
/*  27:    */   {
/*  28: 24 */     return (getFuelBurn(itemstack) != 0.0D) && (getInventory().isItemValidForSlot(i, itemstack));
/*  29:    */   }
/*  30:    */   
/*  31:    */   public int getMaxCoolDown()
/*  32:    */   {
/*  33: 29 */     return 0;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public boolean shouldProcess()
/*  37:    */   {
/*  38: 34 */     return (this.coolDown == 0.0D) || (this.coolDown < getMaxCoolDown());
/*  39:    */   }
/*  40:    */   
/*  41:    */   public boolean processInput()
/*  42:    */   {
/*  43: 39 */     return burnItem();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public double getGenLevelForStack(ItemStack itemStack)
/*  47:    */   {
/*  48: 43 */     return getFuelBurn(itemStack) != 0.0D ? genLevel() : 0.0D;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void adjustGenLevel(ItemStack item) {}
/*  52:    */   
/*  53:    */   public boolean burnItem()
/*  54:    */   {
/*  55: 51 */     ItemStack itemStack = this.inv.getStackInSlot(0);
/*  56: 52 */     double c = getFuelBurn(itemStack);
/*  57: 54 */     if (c > 0.0D)
/*  58:    */     {
/*  59: 55 */       if (itemStack.getItem().hasContainerItem(itemStack))
/*  60:    */       {
/*  61: 56 */         if (itemStack.stackSize == 1)
/*  62:    */         {
/*  63: 57 */           addCoolDown(c, false);
/*  64: 58 */           adjustGenLevel(itemStack);
/*  65: 59 */           this.inv.setInventorySlotContents(0, itemStack.getItem().getContainerItem(itemStack));
/*  66: 60 */           onInventoryChanged();
/*  67: 61 */           return true;
/*  68:    */         }
/*  69: 63 */         return false;
/*  70:    */       }
/*  71: 67 */       adjustGenLevel(itemStack);
/*  72: 68 */       addCoolDown(c, false);
/*  73: 69 */       this.inv.decrStackSize(0, 1);
/*  74: 70 */       onInventoryChanged();
/*  75: 71 */       return true;
/*  76:    */     }
/*  77: 74 */     return false;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public double genLevel()
/*  81:    */   {
/*  82: 80 */     return 40.0D;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public InventoryGeneric getInventory()
/*  86:    */   {
/*  87: 85 */     return this.inv;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean canExtractItem(int i, ItemStack itemstack, int j)
/*  91:    */   {
/*  92: 90 */     return (getFuelBurn(itemstack) == 0.0D) || ((itemstack != null) && (itemstack.getItem().hasContainerItem(itemstack)) && (itemstack.stackSize > 1));
/*  93:    */   }
/*  94:    */   
/*  95:    */   public double getFuelBurn(ItemStack item)
/*  96:    */   {
/*  97: 94 */     return TileEntityGenerator.getFurnaceBurnTime(item) * 12.5D / 40.0D;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public int getSizeInventory()
/* 101:    */   {
/* 102: 99 */     return getInventory().getSizeInventory();
/* 103:    */   }
/* 104:    */   
/* 105:    */   public ItemStack getStackInSlot(int i)
/* 106:    */   {
/* 107:104 */     return getInventory().getStackInSlot(i);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public ItemStack decrStackSize(int i, int j)
/* 111:    */   {
/* 112:109 */     return getInventory().decrStackSize(i, j);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public ItemStack getStackInSlotOnClosing(int i)
/* 116:    */   {
/* 117:114 */     return getInventory().getStackInSlotOnClosing(i);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setInventorySlotContents(int i, ItemStack itemstack)
/* 121:    */   {
/* 122:119 */     getInventory().setInventorySlotContents(i, itemstack);
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String getInventoryName()
/* 126:    */   {
/* 127:124 */     return getInventory().getInventoryName();
/* 128:    */   }
/* 129:    */   
/* 130:    */   public boolean isInventoryNameLocalized()
/* 131:    */   {
/* 132:129 */     return getInventory().isInventoryNameLocalized();
/* 133:    */   }
/* 134:    */   
/* 135:    */   public int getInventoryStackLimit()
/* 136:    */   {
/* 137:134 */     return getInventory().getInventoryStackLimit();
/* 138:    */   }
/* 139:    */   
/* 140:    */   public boolean isUseableByPlayer(EntityPlayer entityplayer)
/* 141:    */   {
/* 142:139 */     return getInventory().isUseableByPlayer(entityplayer);
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void openInventory() {}
/* 146:    */   
/* 147:    */   public void closeInventory() {}
/* 148:    */   
/* 149:    */   public int[] getAccessibleSlotsFromSide(int var1)
/* 150:    */   {
/* 151:152 */     if (this.slots == null)
/* 152:    */     {
/* 153:153 */       int t = getSizeInventory();
/* 154:154 */       this.slots = new int[t];
/* 155:156 */       for (int i = 0; i < t; i++) {
/* 156:157 */         this.slots[i] = i;
/* 157:    */       }
/* 158:    */     }
/* 159:161 */     return this.slots;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public boolean canInsertItem(int i, ItemStack itemstack, int j)
/* 163:    */   {
/* 164:166 */     return true;
/* 165:    */   }
/* 166:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.generators.TileEntityGeneratorFurnace
 * JD-Core Version:    0.7.0.1
 */