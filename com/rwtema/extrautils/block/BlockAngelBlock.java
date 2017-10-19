/*  1:   */ package com.rwtema.extrautils.block;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtils;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.block.material.Material;
/*  6:   */ 
/*  7:   */ public class BlockAngelBlock
/*  8:   */   extends Block
/*  9:   */ {
/* 10:   */   public BlockAngelBlock()
/* 11:   */   {
/* 12: 9 */     super(Material.rock);
/* 13:10 */     setBlockName("extrautils:angelBlock");
/* 14:11 */     setBlockTextureName("extrautils:angelBlock");
/* 15:12 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/* 16:13 */     setHardness(1.0F);
/* 17:14 */     setStepSound(Block.soundTypeStone);
/* 18:   */   }
/* 19:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BlockAngelBlock
 * JD-Core Version:    0.7.0.1
 */