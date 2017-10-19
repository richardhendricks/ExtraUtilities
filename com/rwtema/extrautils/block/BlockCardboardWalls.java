/*  1:   */ package com.rwtema.extrautils.block;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.material.Material;
/*  4:   */ import net.minecraft.world.IBlockAccess;
/*  5:   */ import net.minecraftforge.common.util.ForgeDirection;
/*  6:   */ 
/*  7:   */ public class BlockCardboardWalls
/*  8:   */   extends BlockMultiBlock
/*  9:   */ {
/* 10:   */   public BlockCardboardWalls()
/* 11:   */   {
/* 12: 9 */     super(Material.cloth);
/* 13:10 */     setBlockName("extrautils:cardboardwall");
/* 14:11 */     setBlockTextureName("extrautils:cardboard");
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void prepareForRender(String label) {}
/* 18:   */   
/* 19:   */   public boolean isCardBoard(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
/* 20:   */   {
/* 21:20 */     return this == world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public boolean isSide(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
/* 25:   */   {
/* 26:24 */     return world.isSideSolid(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir.getOpposite(), false);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public BoxModel getWorldModel(IBlockAccess world, int x, int y, int z)
/* 30:   */   {
/* 31:29 */     BoxModel model = new BoxModel();
/* 32:   */     
/* 33:   */ 
/* 34:32 */     return model;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public BoxModel getInventoryModel(int metadata)
/* 38:   */   {
/* 39:37 */     BoxModel box = new BoxModel();
/* 40:38 */     box.addBoxI(7, 0, 9, 7, 16, 9);
/* 41:39 */     return box;
/* 42:   */   }
/* 43:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BlockCardboardWalls
 * JD-Core Version:    0.7.0.1
 */