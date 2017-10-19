/*  1:   */ package com.rwtema.extrautils.tileentity.transfernodes.multiparts;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNodeHyperEnergy;
/*  4:   */ 
/*  5:   */ public class TransferNodePartHyperEnergy
/*  6:   */   extends TransferNodePartEnergy
/*  7:   */ {
/*  8:   */   public TransferNodePartHyperEnergy()
/*  9:   */   {
/* 10: 8 */     super(new TileEntityTransferNodeHyperEnergy());
/* 11:   */   }
/* 12:   */   
/* 13:   */   public TransferNodePartHyperEnergy(int meta, TileEntityTransferNodeHyperEnergy node)
/* 14:   */   {
/* 15:12 */     super(meta, node);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getType()
/* 19:   */   {
/* 20:17 */     return "extrautils:transfer_node_energy_hyper";
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.multiparts.TransferNodePartHyperEnergy
 * JD-Core Version:    0.7.0.1
 */