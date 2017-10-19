/*  1:   */ package com.rwtema.extrautils.multipart;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*  4:   */ import com.rwtema.extrautils.LogHelper;
/*  5:   */ import cpw.mods.fml.common.Loader;
/*  6:   */ import net.minecraft.block.Block;
/*  7:   */ import net.minecraft.init.Blocks;
/*  8:   */ import net.minecraft.item.Item;
/*  9:   */ import net.minecraft.util.RegistryNamespaced;
/* 10:   */ 
/* 11:   */ public class FMPBase
/* 12:   */ {
/* 13:   */   public static Item getMicroBlockItemId()
/* 14:   */   {
/* 15:14 */     if (!ExtraUtilsProxy.checked2)
/* 16:   */     {
/* 17:15 */       ExtraUtilsProxy.checked2 = true;
/* 18:17 */       if (Loader.isModLoaded("ForgeMultipart"))
/* 19:   */       {
/* 20:18 */         ExtraUtilsProxy.MicroBlockId = (Item)Item.itemRegistry.getObject("ForgeMicroblock:microblock");
/* 21:19 */         if (ExtraUtilsProxy.MicroBlockId == null) {
/* 22:20 */           ExtraUtilsProxy.checked2 = false;
/* 23:   */         }
/* 24:   */       }
/* 25:   */     }
/* 26:24 */     return ExtraUtilsProxy.MicroBlockId;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static Block getFMPBlockId()
/* 30:   */   {
/* 31:28 */     if (!ExtraUtilsProxy.checked)
/* 32:   */     {
/* 33:29 */       ExtraUtilsProxy.checked = true;
/* 34:31 */       if (Loader.isModLoaded("ForgeMultipart")) {
/* 35:   */         try
/* 36:   */         {
/* 37:33 */           Block b = (Block)Block.blockRegistry.getObject("ForgeMultipart:block");
/* 38:34 */           ExtraUtilsProxy.FMPBlockId = b;
/* 39:35 */           if ((b == null) || (b == Blocks.air)) {
/* 40:36 */             ExtraUtilsProxy.checked = false;
/* 41:   */           }
/* 42:   */         }
/* 43:   */         catch (Exception e)
/* 44:   */         {
/* 45:38 */           LogHelper.error("Unable to load FMP block id.", new Object[0]);
/* 46:39 */           throw new RuntimeException(e);
/* 47:   */         }
/* 48:   */       } else {
/* 49:42 */         ExtraUtilsProxy.FMPBlockId = null;
/* 50:   */       }
/* 51:   */     }
/* 52:45 */     return ExtraUtilsProxy.FMPBlockId;
/* 53:   */   }
/* 54:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.multipart.FMPBase
 * JD-Core Version:    0.7.0.1
 */