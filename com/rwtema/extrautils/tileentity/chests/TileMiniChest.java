/*   1:    */ package com.rwtema.extrautils.tileentity.chests;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.dynamicgui.DynamicContainer;
/*   4:    */ import com.rwtema.extrautils.dynamicgui.IDynamicContainerProvider;
/*   5:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   6:    */ import net.minecraft.entity.player.EntityPlayer;
/*   7:    */ import net.minecraft.inventory.IInventory;
/*   8:    */ import net.minecraft.inventory.InventoryBasic;
/*   9:    */ import net.minecraft.item.ItemStack;
/*  10:    */ import net.minecraft.nbt.NBTTagCompound;
/*  11:    */ import net.minecraft.tileentity.TileEntity;
/*  12:    */ import net.minecraft.world.World;
/*  13:    */ 
/*  14:    */ public class TileMiniChest
/*  15:    */   extends TileEntity
/*  16:    */   implements IInventory, IDynamicContainerProvider
/*  17:    */ {
/*  18:    */   public boolean canUpdate()
/*  19:    */   {
/*  20: 17 */     return false;
/*  21:    */   }
/*  22:    */   
/*  23: 20 */   public final InventoryBasic inv = new InventoryBasic("tile.extrautils:chestMini.name", false, 1);
/*  24:    */   
/*  25:    */   public int getSizeInventory()
/*  26:    */   {
/*  27: 24 */     return this.inv.getSizeInventory();
/*  28:    */   }
/*  29:    */   
/*  30:    */   public ItemStack getStackInSlot(int p_70301_1_)
/*  31:    */   {
/*  32: 29 */     return this.inv.getStackInSlot(p_70301_1_);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
/*  36:    */   {
/*  37: 34 */     return this.inv.decrStackSize(p_70298_1_, p_70298_2_);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public ItemStack getStackInSlotOnClosing(int p_70304_1_)
/*  41:    */   {
/*  42: 39 */     return this.inv.getStackInSlotOnClosing(p_70304_1_);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_)
/*  46:    */   {
/*  47: 44 */     this.inv.setInventorySlotContents(p_70299_1_, p_70299_2_);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String getInventoryName()
/*  51:    */   {
/*  52: 49 */     return this.inv.getInventoryName();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean isInventoryNameLocalized()
/*  56:    */   {
/*  57: 54 */     return this.inv.isInventoryNameLocalized();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getInventoryStackLimit()
/*  61:    */   {
/*  62: 59 */     return this.inv.getInventoryStackLimit();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void onInventoryChanged()
/*  66:    */   {
/*  67: 64 */     this.inv.onInventoryChanged();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean isUseableByPlayer(EntityPlayer player)
/*  71:    */   {
/*  72: 70 */     return (this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this) && (player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void openInventory()
/*  76:    */   {
/*  77: 75 */     this.inv.openInventory();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void closeInventory()
/*  81:    */   {
/*  82: 80 */     this.inv.closeInventory();
/*  83:    */   }
/*  84:    */   
/*  85:    */   public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
/*  86:    */   {
/*  87: 85 */     return this.inv.isItemValidForSlot(p_94041_1_, p_94041_2_);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void func_145976_a(String displayName)
/*  91:    */   {
/*  92: 89 */     this.inv.func_110133_a(displayName);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public DynamicContainer getContainer(EntityPlayer player)
/*  96:    */   {
/*  97: 94 */     return new DynamicContainerMiniChest(player.inventory, this);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void writeToNBT(NBTTagCompound tag)
/* 101:    */   {
/* 102: 99 */     super.writeToNBT(tag);
/* 103:100 */     XUHelper.writeInventoryBasicToNBT(tag, this.inv);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void readFromNBT(NBTTagCompound tag)
/* 107:    */   {
/* 108:105 */     super.readFromNBT(tag);
/* 109:106 */     XUHelper.readInventoryBasicFromNBT(tag, this.inv);
/* 110:    */   }
/* 111:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.chests.TileMiniChest
 * JD-Core Version:    0.7.0.1
 */