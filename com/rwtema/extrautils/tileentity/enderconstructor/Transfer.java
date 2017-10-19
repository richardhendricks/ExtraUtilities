/* 1:  */ package com.rwtema.extrautils.tileentity.enderconstructor;
/* 2:  */ 
/* 3:  */ public enum Transfer
/* 4:  */ {
/* 5:4 */   PERFORM(false),  SIMULATE(true);
/* 6:  */   
/* 7:  */   boolean simulate;
/* 8:  */   
/* 9:  */   private Transfer(boolean simulate)
/* ::  */   {
/* ;:9 */     this.simulate = simulate;
/* <:  */   }
/* =:  */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.enderconstructor.Transfer
 * JD-Core Version:    0.7.0.1
 */