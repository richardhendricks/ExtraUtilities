/* 1:  */ package com.rwtema.extrautils.core;
/* 2:  */ 
/* 3:  */ public final class NonInstance
/* 4:  */ {
/* 5:  */   private NonInstance()
/* 6:  */   {
/* 7:5 */     throw new IllegalStateException("NonInstance must never be initiated");
/* 8:  */   }
/* 9:  */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.core.NonInstance
 * JD-Core Version:    0.7.0.1
 */