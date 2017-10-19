/*  1:   */ package com.rwtema.extrautils.tileentity.transfernodes.nodebuffer;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.tileentity.transfernodes.InvHelper;
/*  4:   */ import com.rwtema.extrautils.tileentity.transfernodes.TNHelper;
/*  5:   */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNode;
/*  6:   */ import com.rwtema.extrautils.tileentity.transfernodes.pipes.IPipe;
/*  7:   */ import com.rwtema.extrautils.tileentity.transfernodes.pipes.StdPipes;
/*  8:   */ import net.minecraft.inventory.IInventory;
/*  9:   */ import net.minecraft.inventory.ISidedInventory;
/* 10:   */ import net.minecraft.item.ItemStack;
/* 11:   */ import net.minecraft.tileentity.TileEntity;
/* 12:   */ import net.minecraftforge.common.util.ForgeDirection;
/* 13:   */ 
/* 14:   */ public class ItemBufferRetrieval
/* 15:   */   extends ItemBuffer
/* 16:   */ {
/* 17:   */   public boolean shouldSearch()
/* 18:   */   {
/* 19:16 */     return (this.item == null) || (this.item.stackSize < this.item.getMaxStackSize());
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean transferBack(TileEntity tile, ForgeDirection side, IPipe insertingPipe, int x, int y, int z)
/* 23:   */   {
/* 24:20 */     return super.transfer(tile, side, StdPipes.getPipeType(0), x, y, z, side);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public boolean transfer(TileEntity tile, ForgeDirection side, IPipe insertingPipe, int x, int y, int z, ForgeDirection travelDir)
/* 28:   */   {
/* 29:25 */     if (((tile instanceof IInventory)) && (side != ForgeDirection.UNKNOWN) && ((this.node instanceof ISidedInventory)))
/* 30:   */     {
/* 31:26 */       boolean nonSided = !(tile instanceof ISidedInventory);
/* 32:27 */       IInventory nodeInv = (IInventory)this.node;
/* 33:28 */       IInventory inv = TNHelper.getInventory(tile);
/* 34:29 */       boolean extracted = false;
/* 35:31 */       for (int i : InvHelper.getSlots(inv, side.ordinal())) {
/* 36:32 */         if ((inv.getStackInSlot(i) != null) && (
/* 37:33 */           ((this.item == null) && (nodeInv.isItemValidForSlot(0, inv.getStackInSlot(i)))) || (InvHelper.canStack(this.item, inv.getStackInSlot(i)))))
/* 38:   */         {
/* 39:34 */           if (extracted) {
/* 40:35 */             return false;
/* 41:   */           }
/* 42:36 */           if ((this.item == null) || (this.item.stackSize < this.item.getMaxStackSize()))
/* 43:   */           {
/* 44:37 */             int n = nodeInv.getInventoryStackLimit();
/* 45:39 */             if (this.node.getNode().upgradeNo(3) == 0) {
/* 46:40 */               n = 1;
/* 47:   */             }
/* 48:43 */             if (this.item != null) {
/* 49:44 */               n = Math.min(n, this.item.getMaxStackSize() - this.item.stackSize);
/* 50:   */             }
/* 51:47 */             if ((n > 0) && ((nonSided) || (((ISidedInventory)inv).canExtractItem(i, inv.getStackInSlot(i), side.ordinal()))))
/* 52:   */             {
/* 53:48 */               ItemStack r = inv.decrStackSize(i, n);
/* 54:50 */               if ((r != null) && (r.stackSize > 0))
/* 55:   */               {
/* 56:51 */                 if (this.item == null) {
/* 57:52 */                   this.item = r;
/* 58:   */                 } else {
/* 59:54 */                   this.item.stackSize += r.stackSize;
/* 60:   */                 }
/* 61:57 */                 inv.onInventoryChanged();
/* 62:59 */                 if ((n == r.stackSize) && (inv.getStackInSlot(i) != null)) {
/* 63:60 */                   return false;
/* 64:   */                 }
/* 65:63 */                 extracted = true;
/* 66:   */               }
/* 67:   */             }
/* 68:   */           }
/* 69:   */         }
/* 70:   */       }
/* 71:   */     }
/* 72:72 */     return true;
/* 73:   */   }
/* 74:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.ItemBufferRetrieval
 * JD-Core Version:    0.7.0.1
 */