/*  1:   */ package com.rwtema.extrautils.core;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.LogHelper;
/*  4:   */ import cpw.mods.fml.common.FMLCommonHandler;
/*  5:   */ import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
/*  6:   */ 
/*  7:   */ public class TestTransformer
/*  8:   */ {
/*  9:   */   static
/* 10:   */   {
/* 11: 9 */     performTest();
/* 12:10 */     FMLCommonHandler.instance().exitJava(0, true);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public static void performTest()
/* 16:   */   {
/* 17:13 */     for (String s : FMLDeobfuscatingRemapper.INSTANCE.getObfedClasses()) {
/* 18:14 */       LogHelper.info(s + FMLDeobfuscatingRemapper.INSTANCE.map(s), new Object[0]);
/* 19:   */     }
/* 20:   */   }
/* 21:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.core.TestTransformer
 * JD-Core Version:    0.7.0.1
 */