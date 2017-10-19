/* 1:  */ package com.rwtema.extrautils.tileentity.transfernodes;
/* 2:  */ 
/* 3:  */ import cofh.api.energy.EnergyStorage;
/* 4:  */ 
/* 5:  */ public class TileEntityTransferNodeHyperEnergy
/* 6:  */   extends TileEntityTransferNodeEnergy
/* 7:  */ {
/* 8:  */   public TileEntityTransferNodeHyperEnergy()
/* 9:  */   {
/* ::6 */     int capacity = 1000000;
/* ;:7 */     this.powerHandler.setCapacity(1000000);
/* <:8 */     this.powerHandler.setMaxExtract(1000000);
/* =:9 */     this.powerHandler.setMaxReceive(1000000);
/* >:  */   }
/* ?:  */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNodeHyperEnergy
 * JD-Core Version:    0.7.0.1
 */