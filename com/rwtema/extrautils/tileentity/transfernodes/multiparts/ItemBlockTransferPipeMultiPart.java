/*  1:   */ package com.rwtema.extrautils.tileentity.transfernodes.multiparts;
/*  2:   */ 
/*  3:   */ import codechicken.lib.vec.BlockCoord;
/*  4:   */ import codechicken.multipart.TMultiPart;
/*  5:   */ import com.rwtema.extrautils.multipart.ItemBlockMultiPart;
/*  6:   */ import com.rwtema.extrautils.tileentity.transfernodes.BlockTransferPipe;
/*  7:   */ import net.minecraft.block.Block;
/*  8:   */ import net.minecraft.item.ItemStack;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ 
/* 11:   */ public class ItemBlockTransferPipeMultiPart
/* 12:   */   extends ItemBlockMultiPart
/* 13:   */ {
/* 14:   */   public final int pipePage;
/* 15:   */   
/* 16:   */   public ItemBlockTransferPipeMultiPart(Block par1)
/* 17:   */   {
/* 18:15 */     super(par1);
/* 19:16 */     this.pipePage = ((BlockTransferPipe)par1).pipePage;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public TMultiPart createMultiPart(World world, BlockCoord pos, ItemStack item, int side)
/* 23:   */   {
/* 24:21 */     return new PipePart(this.pipePage * 16 + getMetadata(item.getItemDamage()));
/* 25:   */   }
/* 26:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.multiparts.ItemBlockTransferPipeMultiPart
 * JD-Core Version:    0.7.0.1
 */