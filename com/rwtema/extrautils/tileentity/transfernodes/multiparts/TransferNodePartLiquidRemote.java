/*  1:   */ package com.rwtema.extrautils.tileentity.transfernodes.multiparts;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtils;
/*  4:   */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityRetrievalNodeLiquid;
/*  5:   */ import net.minecraft.block.Block;
/*  6:   */ 
/*  7:   */ public class TransferNodePartLiquidRemote
/*  8:   */   extends TransferNodePartLiquid
/*  9:   */ {
/* 10:   */   public TransferNodePartLiquidRemote()
/* 11:   */   {
/* 12:10 */     super(new TileEntityRetrievalNodeLiquid());
/* 13:   */   }
/* 14:   */   
/* 15:   */   public TransferNodePartLiquidRemote(int meta, TileEntityRetrievalNodeLiquid node)
/* 16:   */   {
/* 17:14 */     super(meta, node);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String getType()
/* 21:   */   {
/* 22:19 */     return "extrautils:transfer_node_liquid_remote";
/* 23:   */   }
/* 24:   */   
/* 25:   */   public Block getBlock()
/* 26:   */   {
/* 27:24 */     return ExtraUtils.transferNodeRemote;
/* 28:   */   }
/* 29:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.multiparts.TransferNodePartLiquidRemote
 * JD-Core Version:    0.7.0.1
 */