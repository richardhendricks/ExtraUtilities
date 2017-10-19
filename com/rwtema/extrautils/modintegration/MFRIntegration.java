/*  1:   */ package com.rwtema.extrautils.modintegration;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtils;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.item.Item;
/*  6:   */ import net.minecraft.item.ItemStack;
/*  7:   */ import net.minecraft.nbt.NBTTagCompound;
/*  8:   */ import net.minecraft.util.RegistryNamespaced;
/*  9:   */ import powercrystals.minefactoryreloaded.api.FactoryRegistry;
/* 10:   */ 
/* 11:   */ public class MFRIntegration
/* 12:   */ {
/* 13:11 */   private static MFRIntegration instance = null;
/* 14:   */   
/* 15:   */   public static void registerMFRIntegration()
/* 16:   */   {
/* 17:14 */     if (instance != null) {
/* 18:15 */       throw new IllegalStateException("Already registered");
/* 19:   */     }
/* 20:17 */     if (!ExtraUtils.enderLilyEnabled) {
/* 21:18 */       return;
/* 22:   */     }
/* 23:20 */     instance = new MFRIntegration();
/* 24:   */     
/* 25:22 */     FactoryRegistry.sendMessage("registerHarvestable_Crop", new ItemStack(ExtraUtils.enderLily, 1, 7));
/* 26:   */     
/* 27:24 */     NBTTagCompound tag = new NBTTagCompound();
/* 28:25 */     tag.setString("seed", Item.itemRegistry.getNameForObject(Item.getItemFromBlock(ExtraUtils.enderLily)));
/* 29:26 */     tag.setString("crop", Block.blockRegistry.getNameForObject(ExtraUtils.enderLily));
/* 30:27 */     FactoryRegistry.sendMessage("registerPlantable_Standard", tag);
/* 31:   */   }
/* 32:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.modintegration.MFRIntegration
 * JD-Core Version:    0.7.0.1
 */