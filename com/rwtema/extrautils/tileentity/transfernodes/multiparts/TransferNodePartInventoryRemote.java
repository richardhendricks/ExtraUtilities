/*  1:   */ package com.rwtema.extrautils.tileentity.transfernodes.multiparts;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtils;
/*  4:   */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityRetrievalNodeInventory;
/*  5:   */ import net.minecraft.block.Block;
/*  6:   */ 
/*  7:   */ public class TransferNodePartInventoryRemote
/*  8:   */   extends TransferNodePartInventory
/*  9:   */ {
/* 10:   */   public TransferNodePartInventoryRemote()
/* 11:   */   {
/* 12:10 */     super(new TileEntityRetrievalNodeInventory());
/* 13:   */   }
/* 14:   */   
/* 15:   */   public TransferNodePartInventoryRemote(int meta, TileEntityRetrievalNodeInventory node)
/* 16:   */   {
/* 17:14 */     super(meta, node);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String getType()
/* 21:   */   {
/* 22:19 */     return "extrautils:transfer_node_inv_remote";
/* 23:   */   }
/* 24:   */   
/* 25:   */   public Block getBlock()
/* 26:   */   {
/* 27:24 */     return ExtraUtils.transferNodeRemote;
/* 28:   */   }
/* 29:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.multiparts.TransferNodePartInventoryRemote
 * JD-Core Version:    0.7.0.1
 */