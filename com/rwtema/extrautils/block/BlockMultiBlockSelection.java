/*  1:   */ package com.rwtema.extrautils.block;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.material.Material;
/*  4:   */ import net.minecraft.util.MovingObjectPosition;
/*  5:   */ import net.minecraft.util.Vec3;
/*  6:   */ import net.minecraft.world.IBlockAccess;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public abstract class BlockMultiBlockSelection
/* 10:   */   extends BlockMultiBlock
/* 11:   */ {
/* 12:10 */   public Box boundsOveride = null;
/* 13:   */   
/* 14:   */   public BlockMultiBlockSelection(Material xMaterial)
/* 15:   */   {
/* 16:13 */     super(xMaterial);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 start, Vec3 end)
/* 20:   */   {
/* 21:18 */     MovingObjectPosition result = null;
/* 22:19 */     for (Box box : getWorldModel(world, x, y, z))
/* 23:   */     {
/* 24:20 */       this.boundsOveride = box;
/* 25:21 */       MovingObjectPosition r = super.collisionRayTrace(world, x, y, z, start, end);
/* 26:22 */       if ((r != null) && (
/* 27:23 */         (result == null) || (start.distanceTo(r.hitVec) < start.distanceTo(result.hitVec)))) {
/* 28:24 */         result = r;
/* 29:   */       }
/* 30:   */     }
/* 31:28 */     this.boundsOveride = null;
/* 32:29 */     return result;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int x, int y, int z)
/* 36:   */   {
/* 37:   */     Box bounds;
/* 38:   */     Box bounds;
/* 39:35 */     if (this.boundsOveride != null) {
/* 40:36 */       bounds = this.boundsOveride;
/* 41:   */     } else {
/* 42:38 */       bounds = BoxModel.boundingBox(getWorldModel(par1IBlockAccess, x, y, z));
/* 43:   */     }
/* 44:40 */     if (bounds != null) {
/* 45:41 */       setBlockBounds(bounds.minX, bounds.minY, bounds.minZ, bounds.maxX, bounds.maxY, bounds.maxZ);
/* 46:   */     }
/* 47:   */   }
/* 48:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BlockMultiBlockSelection
 * JD-Core Version:    0.7.0.1
 */