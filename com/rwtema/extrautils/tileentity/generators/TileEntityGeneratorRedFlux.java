/*   1:    */ package com.rwtema.extrautils.tileentity.generators;
/*   2:    */ 
/*   3:    */ import net.minecraft.init.Items;
/*   4:    */ import net.minecraft.item.ItemStack;
/*   5:    */ import net.minecraft.nbt.NBTTagCompound;
/*   6:    */ import net.minecraftforge.common.util.ForgeDirection;
/*   7:    */ import net.minecraftforge.fluids.Fluid;
/*   8:    */ import net.minecraftforge.fluids.FluidRegistry;
/*   9:    */ import net.minecraftforge.fluids.FluidStack;
/*  10:    */ import net.minecraftforge.fluids.FluidTank;
/*  11:    */ import net.minecraftforge.fluids.FluidTankInfo;
/*  12:    */ import net.minecraftforge.fluids.IFluidHandler;
/*  13:    */ 
/*  14:    */ public class TileEntityGeneratorRedFlux
/*  15:    */   extends TileEntityGeneratorFurnace
/*  16:    */   implements IFluidHandler
/*  17:    */ {
/*  18: 10 */   public FluidTank[] tanks = { new FluidTankRestricted(4000, new String[] { "redstone", "lava" }) };
/*  19: 11 */   public int curLevel = 0;
/*  20:    */   
/*  21:    */   public FluidTank[] getTanks()
/*  22:    */   {
/*  23: 15 */     return this.tanks;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public int getMaxCoolDown()
/*  27:    */   {
/*  28: 20 */     return 0;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public boolean processInput()
/*  32:    */   {
/*  33: 25 */     double c = getFuelBurn(getTanks()[0].getFluid());
/*  34: 27 */     if ((c > 0.0D) && (getTanks()[0].getFluidAmount() >= fluidAmmount()) && (addCoolDown(c, true)))
/*  35:    */     {
/*  36: 28 */       if ("lava".equals(FluidRegistry.getFluidName(getTanks()[0].getFluid())))
/*  37:    */       {
/*  38: 29 */         double d = getFuelBurn(this.inv.getStackInSlot(0));
/*  39: 31 */         if (d > 0.0D)
/*  40:    */         {
/*  41: 32 */           this.inv.decrStackSize(0, 1);
/*  42: 33 */           this.curLevel = 80;
/*  43:    */         }
/*  44:    */         else
/*  45:    */         {
/*  46: 35 */           return false;
/*  47:    */         }
/*  48:    */       }
/*  49:    */       else
/*  50:    */       {
/*  51: 38 */         this.curLevel = 80;
/*  52:    */       }
/*  53: 41 */       addCoolDown(c, false);
/*  54: 42 */       getTanks()[0].drain(fluidAmmount(), true);
/*  55: 43 */       onInventoryChanged();
/*  56: 44 */       return true;
/*  57:    */     }
/*  58: 47 */     return false;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public double genLevel()
/*  62:    */   {
/*  63: 52 */     return this.curLevel;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int fluidAmmount()
/*  67:    */   {
/*  68: 56 */     return 125;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public double getFuelBurn(FluidStack fluid)
/*  72:    */   {
/*  73: 60 */     return fluidAmmount() * 2.5D;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public double getFuelBurn(ItemStack item)
/*  77:    */   {
/*  78: 65 */     return (item != null) && (item.getItem() == Items.redstone) ? 1.0D : 0.0D;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
/*  82:    */   {
/*  83: 70 */     return super.fill(from, resource, doFill);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
/*  87:    */   {
/*  88: 75 */     return super.drain(from, resource, doDrain);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
/*  92:    */   {
/*  93: 80 */     return super.drain(from, maxDrain, doDrain);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public boolean canFill(ForgeDirection from, Fluid fluid)
/*  97:    */   {
/*  98: 85 */     return super.canFill(from, fluid);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public boolean canDrain(ForgeDirection from, Fluid fluid)
/* 102:    */   {
/* 103: 90 */     return super.canDrain(from, fluid);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public FluidTankInfo[] getTankInfo(ForgeDirection from)
/* 107:    */   {
/* 108: 95 */     return super.getTankInfo(from);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void readFromNBT(NBTTagCompound nbt)
/* 112:    */   {
/* 113:100 */     super.readFromNBT(nbt);
/* 114:101 */     this.curLevel = nbt.getInteger("curLevel");
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void writeToNBT(NBTTagCompound nbt)
/* 118:    */   {
/* 119:106 */     super.writeToNBT(nbt);
/* 120:107 */     nbt.setInteger("curLevel", this.curLevel);
/* 121:    */   }
/* 122:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.generators.TileEntityGeneratorRedFlux
 * JD-Core Version:    0.7.0.1
 */