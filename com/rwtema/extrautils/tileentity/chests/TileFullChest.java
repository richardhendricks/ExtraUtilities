/*  1:   */ package com.rwtema.extrautils.tileentity.chests;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.helper.XUHelper;
/*  4:   */ import net.minecraft.entity.player.EntityPlayer;
/*  5:   */ import net.minecraft.inventory.IInventory;
/*  6:   */ import net.minecraft.inventory.InventoryBasic;
/*  7:   */ import net.minecraft.item.ItemStack;
/*  8:   */ import net.minecraft.nbt.NBTTagCompound;
/*  9:   */ import net.minecraft.tileentity.TileEntity;
/* 10:   */ import net.minecraft.world.World;
/* 11:   */ 
/* 12:   */ public class TileFullChest
/* 13:   */   extends TileEntity
/* 14:   */   implements IInventory
/* 15:   */ {
/* 16:   */   public boolean canUpdate()
/* 17:   */   {
/* 18:14 */     return false;
/* 19:   */   }
/* 20:   */   
/* 21:17 */   public final InventoryBasic inv = new InventoryBasic("tile.extrautils:chestFull.name", false, 27);
/* 22:   */   
/* 23:   */   public int getSizeInventory()
/* 24:   */   {
/* 25:21 */     return this.inv.getSizeInventory();
/* 26:   */   }
/* 27:   */   
/* 28:   */   public ItemStack getStackInSlot(int p_70301_1_)
/* 29:   */   {
/* 30:26 */     return this.inv.getStackInSlot(p_70301_1_);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
/* 34:   */   {
/* 35:31 */     return this.inv.decrStackSize(p_70298_1_, p_70298_2_);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public ItemStack getStackInSlotOnClosing(int p_70304_1_)
/* 39:   */   {
/* 40:36 */     return this.inv.getStackInSlotOnClosing(p_70304_1_);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_)
/* 44:   */   {
/* 45:41 */     this.inv.setInventorySlotContents(p_70299_1_, p_70299_2_);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public String getInventoryName()
/* 49:   */   {
/* 50:46 */     return this.inv.getInventoryName();
/* 51:   */   }
/* 52:   */   
/* 53:   */   public boolean isInventoryNameLocalized()
/* 54:   */   {
/* 55:51 */     return this.inv.isInventoryNameLocalized();
/* 56:   */   }
/* 57:   */   
/* 58:   */   public int getInventoryStackLimit()
/* 59:   */   {
/* 60:56 */     return this.inv.getInventoryStackLimit();
/* 61:   */   }
/* 62:   */   
/* 63:   */   public void onInventoryChanged()
/* 64:   */   {
/* 65:61 */     this.inv.onInventoryChanged();
/* 66:   */   }
/* 67:   */   
/* 68:   */   public boolean isUseableByPlayer(EntityPlayer player)
/* 69:   */   {
/* 70:67 */     return (this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this) && (player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D);
/* 71:   */   }
/* 72:   */   
/* 73:   */   public void openInventory()
/* 74:   */   {
/* 75:72 */     this.inv.openInventory();
/* 76:   */   }
/* 77:   */   
/* 78:   */   public void closeInventory()
/* 79:   */   {
/* 80:77 */     this.inv.closeInventory();
/* 81:   */   }
/* 82:   */   
/* 83:   */   public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
/* 84:   */   {
/* 85:82 */     return this.inv.isItemValidForSlot(p_94041_1_, p_94041_2_);
/* 86:   */   }
/* 87:   */   
/* 88:   */   public void func_145976_a(String displayName)
/* 89:   */   {
/* 90:86 */     this.inv.func_110133_a(displayName);
/* 91:   */   }
/* 92:   */   
/* 93:   */   public void writeToNBT(NBTTagCompound tag)
/* 94:   */   {
/* 95:91 */     super.writeToNBT(tag);
/* 96:92 */     XUHelper.writeInventoryBasicToNBT(tag, this.inv);
/* 97:   */   }
/* 98:   */   
/* 99:   */   public void readFromNBT(NBTTagCompound tag)
/* :0:   */   {
/* :1:97 */     super.readFromNBT(tag);
/* :2:98 */     XUHelper.readInventoryBasicFromNBT(tag, this.inv);
/* :3:   */   }
/* :4:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.chests.TileFullChest
 * JD-Core Version:    0.7.0.1
 */