/*  1:   */ package com.rwtema.extrautils.multipart;
/*  2:   */ 
/*  3:   */ import codechicken.microblock.BlockMicroMaterial;
/*  4:   */ import codechicken.microblock.MicroMaterialRegistry;
/*  5:   */ import com.rwtema.extrautils.LogHelper;
/*  6:   */ import com.rwtema.extrautils.block.BlockColor;
/*  7:   */ import com.rwtema.extrautils.block.BlockGreenScreen;
/*  8:   */ import net.minecraft.block.Block;
/*  9:   */ 
/* 10:   */ public class RegisterMicroMaterials
/* 11:   */ {
/* 12:   */   public static void registerBlock(Block block)
/* 13:   */   {
/* 14:12 */     if (block != null) {
/* 15:13 */       BlockMicroMaterial.createAndRegister(block, 0);
/* 16:   */     }
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static void registerFullBright(BlockGreenScreen block)
/* 20:   */   {
/* 21:18 */     if (block != null) {
/* 22:19 */       for (int m = 0; m < 16; m++) {
/* 23:20 */         MicroMaterialRegistry.registerMaterial(new FullBrightMicroMaterial(block, m), block.getUnlocalizedName() + (m > 0 ? "_" + m : ""));
/* 24:   */       }
/* 25:   */     }
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static void registerColorBlock(BlockColor block)
/* 29:   */   {
/* 30:25 */     if (block != null) {
/* 31:26 */       for (int m = 0; m < 16; m++) {
/* 32:27 */         MicroMaterialRegistry.registerMaterial(new ColoredBlockMicroMaterial(block, m), block.getUnlocalizedName() + (m > 0 ? "_" + m : ""));
/* 33:   */       }
/* 34:   */     }
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static void registerConnectedTexture(Block block, int m)
/* 38:   */   {
/* 39:32 */     if (block != null) {
/* 40:   */       try
/* 41:   */       {
/* 42:34 */         MicroMaterialRegistry.registerMaterial(new ConnectedTextureMicroMaterial(block, m), block.getUnlocalizedName() + (m > 0 ? "_" + m : ""));
/* 43:   */       }
/* 44:   */       catch (Throwable err)
/* 45:   */       {
/* 46:36 */         Throwable e = err;
/* 47:37 */         while (e != null)
/* 48:   */         {
/* 49:38 */           LogHelper.info("-------", new Object[0]);
/* 50:   */           
/* 51:   */ 
/* 52:41 */           LogHelper.info(e.getMessage(), new Object[0]);
/* 53:43 */           for (Throwable f : e.getSuppressed())
/* 54:   */           {
/* 55:44 */             LogHelper.info("Found supressed error", new Object[0]);
/* 56:45 */             f.printStackTrace();
/* 57:   */           }
/* 58:47 */           for (StackTraceElement f : e.getStackTrace()) {
/* 59:48 */             LogHelper.info(f.getClassName() + " " + f.getMethodName() + " " + f.getFileName() + " " + f.getLineNumber() + " " + f.isNativeMethod(), new Object[0]);
/* 60:   */           }
/* 61:51 */           e = e.getCause();
/* 62:   */         }
/* 63:53 */         throw new RuntimeException(e);
/* 64:   */       }
/* 65:   */     }
/* 66:   */   }
/* 67:   */   
/* 68:   */   public static void registerBlock(Block block, int m)
/* 69:   */   {
/* 70:59 */     if (block != null) {
/* 71:60 */       MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(block, m), block.getUnlocalizedName() + (m > 0 ? "_" + m : ""));
/* 72:   */     }
/* 73:   */   }
/* 74:   */   
/* 75:   */   public static void registerBlock(Block block, int from, int to)
/* 76:   */   {
/* 77:65 */     for (int i = from; i < to; i++) {
/* 78:66 */       registerBlock(block, i);
/* 79:   */     }
/* 80:   */   }
/* 81:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.multipart.RegisterMicroMaterials
 * JD-Core Version:    0.7.0.1
 */