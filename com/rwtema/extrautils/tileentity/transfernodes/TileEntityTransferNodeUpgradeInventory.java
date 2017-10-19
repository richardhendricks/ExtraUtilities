/*  1:   */ package com.rwtema.extrautils.tileentity.transfernodes;
/*  2:   */ 
/*  3:   */ import cofh.api.energy.IEnergyContainerItem;
/*  4:   */ import cofh.api.energy.IEnergyHandler;
/*  5:   */ import com.rwtema.extrautils.ExtraUtils;
/*  6:   */ import net.minecraft.init.Blocks;
/*  7:   */ import net.minecraft.init.Items;
/*  8:   */ import net.minecraft.inventory.InventoryBasic;
/*  9:   */ import net.minecraft.item.Item;
/* 10:   */ import net.minecraft.item.ItemBlock;
/* 11:   */ import net.minecraft.item.ItemStack;
/* 12:   */ 
/* 13:   */ public class TileEntityTransferNodeUpgradeInventory
/* 14:   */   extends InventoryBasic
/* 15:   */ {
/* 16:   */   private final boolean isEnergy;
/* 17:   */   public TileEntityTransferNode node;
/* 18:   */   
/* 19:   */   public TileEntityTransferNodeUpgradeInventory(int par3, TileEntityTransferNode node)
/* 20:   */   {
/* 21:18 */     super("Upgrade Inventory", true, par3);
/* 22:19 */     this.node = node;
/* 23:20 */     this.isEnergy = (node.getNode() instanceof IEnergyHandler);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public boolean isItemValidForSlot(int par1, ItemStack item)
/* 27:   */   {
/* 28:25 */     return (item != null) && ((pipeType(item) > 0) || (isEnergy(item)) || (hasUpgradeNo(item)));
/* 29:   */   }
/* 30:   */   
/* 31:   */   public boolean hasUpgradeNo(ItemStack item)
/* 32:   */   {
/* 33:33 */     if ((item == null) || (item.getItem() == null)) {
/* 34:34 */       return false;
/* 35:   */     }
/* 36:35 */     return (item.getItem() == Items.glowstone_dust) || (item.getItem() == ExtraUtils.nodeUpgrade) || (item.getItem() == Item.getItemFromBlock(Blocks.redstone_torch));
/* 37:   */   }
/* 38:   */   
/* 39:   */   public int getUpgradeNo(ItemStack item)
/* 40:   */   {
/* 41:41 */     if (item.getItem() == Items.glowstone_dust) {
/* 42:42 */       return -1;
/* 43:   */     }
/* 44:44 */     if (item.getItem() == Item.getItemFromBlock(Blocks.redstone_torch)) {
/* 45:45 */       return -2;
/* 46:   */     }
/* 47:47 */     return item.getItemDamage();
/* 48:   */   }
/* 49:   */   
/* 50:   */   public boolean isEnergy(ItemStack item)
/* 51:   */   {
/* 52:52 */     return (this.isEnergy) && ((item.getItem() instanceof IEnergyContainerItem));
/* 53:   */   }
/* 54:   */   
/* 55:   */   public int pipeType(ItemStack item)
/* 56:   */   {
/* 57:57 */     if ((item == null) || (!(item.getItem() instanceof ItemBlock)) || (!(((ItemBlock)item.getItem()).field_150939_a instanceof BlockTransferPipe))) {
/* 58:58 */       return -1;
/* 59:   */     }
/* 60:60 */     int i = item.getItemDamage() + ((BlockTransferPipe)((ItemBlock)item.getItem()).field_150939_a).pipePage * 16;
/* 61:61 */     return isValidPipeType(i) ? i : -1;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public boolean isValidPipeType(int i)
/* 65:   */   {
/* 66:65 */     return ((i <= 0) || (i > 7)) && (i != 9) && (i != 15);
/* 67:   */   }
/* 68:   */   
/* 69:   */   public void onInventoryChanged()
/* 70:   */   {
/* 71:73 */     this.node.calcUpgradeModifiers();
/* 72:74 */     this.node.onInventoryChanged();
/* 73:75 */     super.onInventoryChanged();
/* 74:   */   }
/* 75:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNodeUpgradeInventory
 * JD-Core Version:    0.7.0.1
 */