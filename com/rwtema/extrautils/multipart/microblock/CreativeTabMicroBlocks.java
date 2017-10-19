/*  1:   */ package com.rwtema.extrautils.multipart.microblock;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtils;
/*  4:   */ import com.rwtema.extrautils.helper.XURandom;
/*  5:   */ import cpw.mods.fml.relauncher.Side;
/*  6:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  7:   */ import java.util.Random;
/*  8:   */ import net.minecraft.creativetab.CreativeTabs;
/*  9:   */ import net.minecraft.item.Item;
/* 10:   */ import net.minecraft.item.ItemStack;
/* 11:   */ import scala.Tuple2;
/* 12:   */ 
/* 13:   */ public class CreativeTabMicroBlocks
/* 14:   */   extends CreativeTabs
/* 15:   */ {
/* 16:15 */   public static CreativeTabMicroBlocks instance = null;
/* 17:16 */   public static Random rand = XURandom.getInstance();
/* 18:   */   private static ItemStack item;
/* 19:   */   
/* 20:   */   public CreativeTabMicroBlocks()
/* 21:   */   {
/* 22:20 */     super("extraUtil_FMP");
/* 23:   */   }
/* 24:   */   
/* 25:   */   @SideOnly(Side.CLIENT)
/* 26:   */   public Item getTabIconItem()
/* 27:   */   {
/* 28:26 */     return null;
/* 29:   */   }
/* 30:   */   
/* 31:   */   @SideOnly(Side.CLIENT)
/* 32:   */   public ItemStack getIconItemStack()
/* 33:   */   {
/* 34:31 */     if (item == null) {
/* 35:32 */       item = ItemMicroBlock.getStack(new ItemStack(ExtraUtils.microBlocks, 1, rand.nextBoolean() ? 2 : 1), (String)codechicken.microblock.MicroMaterialRegistry.getIdMap()[rand.nextInt(codechicken.microblock.MicroMaterialRegistry.getIdMap().length)]._1());
/* 36:   */     }
/* 37:35 */     return item;
/* 38:   */   }
/* 39:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.multipart.microblock.CreativeTabMicroBlocks
 * JD-Core Version:    0.7.0.1
 */