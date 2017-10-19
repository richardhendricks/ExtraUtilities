/*  1:   */ package com.rwtema.extrautils.sync;
/*  2:   */ 
/*  3:   */ import net.minecraft.nbt.NBTTagCompound;
/*  4:   */ 
/*  5:   */ public class AutoNBTTest
/*  6:   */   extends AutoNBT
/*  7:   */ {
/*  8:   */   public void writeToNBT(NBTTagCompound tag, Object instance)
/*  9:   */   {
/* 10: 8 */     NBTTest t = (NBTTest)instance;
/* 11: 9 */     NBTHandlers.NBTHandlerFloat.save(tag, "f", t.hat);
/* 12:10 */     NBTHandlers.NBTHandlerInt.save(tag, "i", t.hello);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public void readFromNBT(NBTTagCompound tag, Object instance) {}
/* 16:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.sync.AutoNBTTest
 * JD-Core Version:    0.7.0.1
 */