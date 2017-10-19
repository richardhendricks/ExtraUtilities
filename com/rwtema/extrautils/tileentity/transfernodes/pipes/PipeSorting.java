/*  1:   */ package com.rwtema.extrautils.tileentity.transfernodes.pipes;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.tileentity.transfernodes.BlockTransferPipe;
/*  4:   */ import com.rwtema.extrautils.tileentity.transfernodes.InvHelper;
/*  5:   */ import com.rwtema.extrautils.tileentity.transfernodes.TNHelper;
/*  6:   */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeBuffer;
/*  7:   */ import net.minecraft.inventory.IInventory;
/*  8:   */ import net.minecraft.item.ItemStack;
/*  9:   */ import net.minecraft.tileentity.TileEntity;
/* 10:   */ import net.minecraft.util.IIcon;
/* 11:   */ import net.minecraftforge.common.util.ForgeDirection;
/* 12:   */ 
/* 13:   */ public class PipeSorting
/* 14:   */   extends PipeBase
/* 15:   */ {
/* 16:   */   public PipeSorting()
/* 17:   */   {
/* 18:15 */     super("Sorting");
/* 19:   */   }
/* 20:   */   
/* 21:   */   public int limitTransfer(TileEntity dest, ForgeDirection side, INodeBuffer buffer)
/* 22:   */   {
/* 23:20 */     if (((buffer.getBuffer() instanceof ItemStack)) && 
/* 24:21 */       ((dest instanceof IInventory)))
/* 25:   */     {
/* 26:22 */       ItemStack item = (ItemStack)buffer.getBuffer();
/* 27:23 */       IInventory inv = TNHelper.getInventory(dest);
/* 28:24 */       boolean empty = true;
/* 29:26 */       for (int i : InvHelper.getSlots(inv, side.ordinal())) {
/* 30:27 */         if (inv.getStackInSlot(i) != null)
/* 31:   */         {
/* 32:28 */           empty = false;
/* 33:30 */           if (InvHelper.sameType(inv.getStackInSlot(i), item)) {
/* 34:31 */             return -1;
/* 35:   */           }
/* 36:   */         }
/* 37:   */       }
/* 38:36 */       return empty ? -1 : 0;
/* 39:   */     }
/* 40:40 */     return -1;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public IIcon baseTexture()
/* 44:   */   {
/* 45:45 */     return BlockTransferPipe.pipes_grouping;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public IIcon invPipeTexture(ForgeDirection dir)
/* 49:   */   {
/* 50:50 */     return BlockTransferPipe.pipes_grouping;
/* 51:   */   }
/* 52:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.pipes.PipeSorting
 * JD-Core Version:    0.7.0.1
 */