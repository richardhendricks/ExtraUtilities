/*  1:   */ package com.rwtema.extrautils.item;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtils;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.item.Item;
/*  6:   */ import net.minecraft.item.ItemStack;
/*  7:   */ 
/*  8:   */ public class ItemBlockGenerator
/*  9:   */   extends ItemBlockMetadata
/* 10:   */ {
/* 11:   */   public ItemBlockGenerator(Block par1)
/* 12:   */   {
/* 13:11 */     super(par1);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String getUnlocalizedName(ItemStack par1ItemStack)
/* 17:   */   {
/* 18:16 */     if (this.field_150939_a != ExtraUtils.generator) {
/* 19:17 */       return Item.getItemFromBlock(ExtraUtils.generator).getUnlocalizedName(par1ItemStack);
/* 20:   */     }
/* 21:19 */     return super.getUnlocalizedName(par1ItemStack);
/* 22:   */   }
/* 23:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemBlockGenerator
 * JD-Core Version:    0.7.0.1
 */