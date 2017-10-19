/*   1:    */ package com.rwtema.extrautils.inventory;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.player.EntityPlayer;
/*   4:    */ import net.minecraft.inventory.ISidedInventory;
/*   5:    */ import net.minecraft.item.ItemStack;
/*   6:    */ import net.minecraft.tileentity.TileEntity;
/*   7:    */ import net.minecraftforge.common.util.ForgeDirection;
/*   8:    */ import net.minecraftforge.fluids.FluidContainerRegistry;
/*   9:    */ import net.minecraftforge.fluids.FluidStack;
/*  10:    */ import net.minecraftforge.fluids.FluidTankInfo;
/*  11:    */ import net.minecraftforge.fluids.IFluidHandler;
/*  12:    */ 
/*  13:    */ public class LiquidInventory
/*  14:    */   implements ISidedInventory
/*  15:    */ {
/*  16:    */   IFluidHandler tank;
/*  17:    */   ForgeDirection side;
/*  18:    */   
/*  19:    */   public LiquidInventory(IFluidHandler tank, ForgeDirection side)
/*  20:    */   {
/*  21: 17 */     this.tank = tank;
/*  22: 18 */     this.side = side;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public int[] getAccessibleSlotsFromSide(int var1)
/*  26:    */   {
/*  27: 24 */     if (this.tank.getTankInfo(this.side) == null) {
/*  28: 25 */       return new int[0];
/*  29:    */     }
/*  30: 27 */     int[] t = new int[this.tank.getTankInfo(this.side).length];
/*  31: 28 */     for (int i = 0; i < t.length; i++) {
/*  32: 29 */       t[i] = i;
/*  33:    */     }
/*  34: 31 */     return t;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public boolean canInsertItem(int var1, ItemStack var2, int var3)
/*  38:    */   {
/*  39: 36 */     FluidStack f = FluidContainerRegistry.getFluidForFilledItem(var2);
/*  40: 37 */     return (f != null) && (f.getFluid() != null) && (this.tank.canFill(this.side, f.getFluid())) && (this.tank.fill(this.side, f, false) == f.amount);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public boolean canExtractItem(int var1, ItemStack var2, int var3)
/*  44:    */   {
/*  45: 42 */     FluidStack f = FluidContainerRegistry.getFluidForFilledItem(var2);
/*  46: 43 */     return (f != null) && (f.getFluid() != null) && (this.tank.canDrain(this.side, f.getFluid())) && (f.isFluidStackIdentical(this.tank.drain(this.side, f, false)));
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int getSizeInventory()
/*  50:    */   {
/*  51: 48 */     return this.tank.getTankInfo(this.side).length;
/*  52:    */   }
/*  53:    */   
/*  54: 51 */   ItemStack[] genericItems = { FluidContainerRegistry.EMPTY_BUCKET, FluidContainerRegistry.EMPTY_BOTTLE };
/*  55:    */   
/*  56:    */   public ItemStack getStackInSlot(int var1)
/*  57:    */   {
/*  58: 55 */     FluidStack f = this.tank.getTankInfo(this.side)[var1].fluid;
/*  59: 56 */     for (ItemStack item : this.genericItems)
/*  60:    */     {
/*  61: 57 */       ItemStack i = FluidContainerRegistry.fillFluidContainer(f, item);
/*  62: 58 */       if (i != null) {
/*  63: 59 */         return i;
/*  64:    */       }
/*  65:    */     }
/*  66: 61 */     return null;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public ItemStack decrStackSize(int var1, int var2)
/*  70:    */   {
/*  71: 66 */     FluidStack f = this.tank.getTankInfo(this.side)[var1].fluid;
/*  72: 67 */     for (ItemStack item : this.genericItems)
/*  73:    */     {
/*  74: 68 */       ItemStack i = FluidContainerRegistry.fillFluidContainer(f, item);
/*  75: 69 */       if (i != null)
/*  76:    */       {
/*  77: 70 */         FluidStack t = FluidContainerRegistry.getFluidForFilledItem(i);
/*  78: 71 */         if ((t != null) && (t.isFluidEqual(this.tank.drain(this.side, t, false))))
/*  79:    */         {
/*  80: 72 */           this.tank.drain(this.side, t, true);
/*  81: 73 */           return i;
/*  82:    */         }
/*  83:    */       }
/*  84:    */     }
/*  85: 78 */     return null;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public ItemStack getStackInSlotOnClosing(int var1)
/*  89:    */   {
/*  90: 83 */     return getStackInSlot(var1);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setInventorySlotContents(int var1, ItemStack var2)
/*  94:    */   {
/*  95: 88 */     FluidStack f = FluidContainerRegistry.getFluidForFilledItem(var2);
/*  96: 89 */     if ((f == null) || (f.getFluid() == null)) {
/*  97: 90 */       return;
/*  98:    */     }
/*  99: 91 */     if (!this.tank.canFill(this.side, f.getFluid())) {
/* 100: 92 */       return;
/* 101:    */     }
/* 102: 93 */     if (this.tank.fill(this.side, f, false) == f.amount) {
/* 103: 94 */       this.tank.fill(this.side, f, true);
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String getInventoryName()
/* 108:    */   {
/* 109: 99 */     return "fakeTank";
/* 110:    */   }
/* 111:    */   
/* 112:    */   public boolean isInventoryNameLocalized()
/* 113:    */   {
/* 114:104 */     return false;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public int getInventoryStackLimit()
/* 118:    */   {
/* 119:109 */     return this.tank.getTankInfo(this.side).length;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void onInventoryChanged()
/* 123:    */   {
/* 124:114 */     if ((this.tank instanceof TileEntity)) {
/* 125:115 */       ((TileEntity)this.tank).onInventoryChanged();
/* 126:    */     }
/* 127:    */   }
/* 128:    */   
/* 129:    */   public boolean isUseableByPlayer(EntityPlayer var1)
/* 130:    */   {
/* 131:120 */     return false;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void openInventory() {}
/* 135:    */   
/* 136:    */   public void closeInventory() {}
/* 137:    */   
/* 138:    */   public boolean isItemValidForSlot(int var1, ItemStack var2)
/* 139:    */   {
/* 140:135 */     return canInsertItem(var1, var2, 0);
/* 141:    */   }
/* 142:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.inventory.LiquidInventory
 * JD-Core Version:    0.7.0.1
 */