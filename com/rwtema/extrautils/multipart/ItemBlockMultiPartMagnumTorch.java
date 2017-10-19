/*  1:   */ package com.rwtema.extrautils.multipart;
/*  2:   */ 
/*  3:   */ import codechicken.lib.vec.BlockCoord;
/*  4:   */ import codechicken.multipart.TMultiPart;
/*  5:   */ import net.minecraft.block.Block;
/*  6:   */ import net.minecraft.item.ItemStack;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public class ItemBlockMultiPartMagnumTorch
/* 10:   */   extends ItemBlockMultiPart
/* 11:   */ {
/* 12:   */   public ItemBlockMultiPartMagnumTorch(Block par1)
/* 13:   */   {
/* 14:11 */     super(par1);
/* 15:12 */     this.hasBlockMetadata = false;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public TMultiPart createMultiPart(World world, BlockCoord pos, ItemStack item, int side)
/* 19:   */   {
/* 20:17 */     return new MagnumTorchPart();
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.multipart.ItemBlockMultiPartMagnumTorch
 * JD-Core Version:    0.7.0.1
 */