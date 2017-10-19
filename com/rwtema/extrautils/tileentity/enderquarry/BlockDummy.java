/*  1:   */ package com.rwtema.extrautils.tileentity.enderquarry;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.block.material.Material;
/*  5:   */ import net.minecraft.entity.player.EntityPlayer;
/*  6:   */ import net.minecraft.world.World;
/*  7:   */ 
/*  8:   */ public class BlockDummy
/*  9:   */   extends Block
/* 10:   */ {
/* 11:   */   public BlockDummy(Material par2Material)
/* 12:   */   {
/* 13:11 */     super(par2Material);
/* 14:12 */     throw new RuntimeException("This block is a dummy and must never be assigned");
/* 15:   */   }
/* 16:   */   
/* 17:   */   public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest)
/* 18:   */   {
/* 19:17 */     throw new RuntimeException("This block's methods must never be called");
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z)
/* 23:   */   {
/* 24:23 */     throw new RuntimeException("This block's methods must never be called");
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6)
/* 28:   */   {
/* 29:28 */     throw new RuntimeException("This block's methods must never be called");
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void onBlockPreDestroy(World par1World, int par2, int par3, int par4, int par5)
/* 33:   */   {
/* 34:33 */     throw new RuntimeException("This block's methods must never be called");
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
/* 38:   */   {
/* 39:38 */     throw new RuntimeException("This block's methods must never be called");
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5)
/* 43:   */   {
/* 44:43 */     throw new RuntimeException("This block's methods must never be called");
/* 45:   */   }
/* 46:   */   
/* 47:   */   public void onBlockHarvested(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer)
/* 48:   */   {
/* 49:48 */     throw new RuntimeException("This block's methods must never be called");
/* 50:   */   }
/* 51:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.enderquarry.BlockDummy
 * JD-Core Version:    0.7.0.1
 */