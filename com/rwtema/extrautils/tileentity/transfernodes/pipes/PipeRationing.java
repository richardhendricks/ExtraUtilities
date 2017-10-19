/*  1:   */ package com.rwtema.extrautils.tileentity.transfernodes.pipes;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.tileentity.transfernodes.BlockTransferPipe;
/*  4:   */ import com.rwtema.extrautils.tileentity.transfernodes.InvHelper;
/*  5:   */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeBuffer;
/*  6:   */ import net.minecraft.inventory.IInventory;
/*  7:   */ import net.minecraft.item.ItemStack;
/*  8:   */ import net.minecraft.tileentity.TileEntity;
/*  9:   */ import net.minecraft.util.IIcon;
/* 10:   */ import net.minecraftforge.common.util.ForgeDirection;
/* 11:   */ 
/* 12:   */ public class PipeRationing
/* 13:   */   extends PipeBase
/* 14:   */ {
/* 15:   */   public PipeRationing()
/* 16:   */   {
/* 17:14 */     super("Rationing");
/* 18:   */   }
/* 19:   */   
/* 20:   */   public int limitTransfer(TileEntity dest, ForgeDirection side, INodeBuffer buffer)
/* 21:   */   {
/* 22:19 */     if (((buffer.getBuffer() instanceof ItemStack)) && ((dest instanceof IInventory)))
/* 23:   */     {
/* 24:20 */       ItemStack item = (ItemStack)buffer.getBuffer();
/* 25:21 */       IInventory inv = (IInventory)dest;
/* 26:22 */       int n = Math.min(inv.getInventoryStackLimit(), item.getMaxStackSize());
/* 27:24 */       for (int i : InvHelper.getSlots(inv, side.ordinal())) {
/* 28:25 */         if ((inv.getStackInSlot(i) != null) && (InvHelper.canStack(inv.getStackInSlot(i), item)))
/* 29:   */         {
/* 30:26 */           n -= inv.getStackInSlot(i).stackSize;
/* 31:28 */           if (n <= 0) {
/* 32:29 */             return 0;
/* 33:   */           }
/* 34:   */         }
/* 35:   */       }
/* 36:34 */       return n < 0 ? 0 : n;
/* 37:   */     }
/* 38:37 */     return -1;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public IIcon baseTexture()
/* 42:   */   {
/* 43:42 */     return BlockTransferPipe.pipes_supply;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public IIcon invPipeTexture(ForgeDirection dir)
/* 47:   */   {
/* 48:47 */     return BlockTransferPipe.pipes_supply;
/* 49:   */   }
/* 50:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.pipes.PipeRationing
 * JD-Core Version:    0.7.0.1
 */