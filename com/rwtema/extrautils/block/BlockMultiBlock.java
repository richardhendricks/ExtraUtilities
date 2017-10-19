/*  1:   */ package com.rwtema.extrautils.block;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*  4:   */ import java.util.List;
/*  5:   */ import net.minecraft.block.Block;
/*  6:   */ import net.minecraft.block.material.Material;
/*  7:   */ import net.minecraft.entity.Entity;
/*  8:   */ import net.minecraft.util.AxisAlignedBB;
/*  9:   */ import net.minecraft.world.IBlockAccess;
/* 10:   */ import net.minecraft.world.World;
/* 11:   */ 
/* 12:   */ public abstract class BlockMultiBlock
/* 13:   */   extends Block
/* 14:   */   implements IMultiBoxBlock
/* 15:   */ {
/* 16:   */   public BlockMultiBlock(Material xMaterial)
/* 17:   */   {
/* 18:16 */     super(xMaterial);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean renderAsNormalBlock()
/* 22:   */   {
/* 23:21 */     return false;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public boolean isOpaqueCube()
/* 27:   */   {
/* 28:31 */     return false;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public int getRenderType()
/* 32:   */   {
/* 33:39 */     return ExtraUtilsProxy.multiBlockID;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
/* 37:   */   {
/* 38:50 */     List models = getWorldModel(par1World, par2, par3, par4);
/* 39:52 */     if ((models == null) || (models.size() == 0)) {
/* 40:53 */       return;
/* 41:   */     }
/* 42:56 */     for (Object model : models)
/* 43:   */     {
/* 44:57 */       Box b = (Box)model;
/* 45:58 */       AxisAlignedBB axisalignedbb1 = AxisAlignedBB.getBoundingBox(par2 + b.offsetx + b.minX, par3 + b.offsety + b.minY, par4 + b.offsetz + b.minZ, par2 + b.offsetx + b.maxX, par3 + b.offsety + b.maxY, par4 + b.offsetz + b.maxZ);
/* 46:61 */       if ((axisalignedbb1 != null) && (par5AxisAlignedBB.intersectsWith(axisalignedbb1))) {
/* 47:62 */         par6List.add(axisalignedbb1);
/* 48:   */       }
/* 49:   */     }
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int x, int y, int z)
/* 53:   */   {
/* 54:73 */     Box bounds = BoxModel.boundingBox(getWorldModel(par1IBlockAccess, x, y, z));
/* 55:75 */     if (bounds != null) {
/* 56:76 */       setBlockBounds(bounds.minX, bounds.minY, bounds.minZ, bounds.maxX, bounds.maxY, bounds.maxZ);
/* 57:   */     }
/* 58:   */   }
/* 59:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BlockMultiBlock
 * JD-Core Version:    0.7.0.1
 */