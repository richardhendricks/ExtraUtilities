/*  1:   */ package com.rwtema.extrautils.tileentity.transfernodes.multiparts;
/*  2:   */ 
/*  3:   */ import codechicken.lib.vec.BlockCoord;
/*  4:   */ import codechicken.multipart.minecraft.IPartMeta;
/*  5:   */ import codechicken.multipart.minecraft.PartMetaAccess;
/*  6:   */ import cpw.mods.fml.relauncher.Side;
/*  7:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  8:   */ import net.minecraft.tileentity.TileEntity;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ 
/* 11:   */ public class TilePartMetaAccess
/* 12:   */   extends PartMetaAccess
/* 13:   */ {
/* 14:   */   TileEntity tile;
/* 15:   */   
/* 16:   */   public TilePartMetaAccess(IPartMeta p, TileEntity tileEntity)
/* 17:   */   {
/* 18:13 */     super(p);
/* 19:14 */     this.tile = tileEntity;
/* 20:   */   }
/* 21:   */   
/* 22:   */   @SideOnly(Side.CLIENT)
/* 23:   */   public boolean isAirBlock(int i, int j, int k)
/* 24:   */   {
/* 25:20 */     return ((i != this.part.getPos().x) || (j != this.part.getPos().y) || (k != this.part.getPos().z)) && (this.part.getWorld().isAirBlock(i, j, k));
/* 26:   */   }
/* 27:   */   
/* 28:   */   public TileEntity getTileEntity(int i, int j, int k)
/* 29:   */   {
/* 30:27 */     if ((i == this.part.getPos().x) && (j == this.part.getPos().y) && (k == this.part.getPos().z)) {
/* 31:28 */       return this.tile;
/* 32:   */     }
/* 33:29 */     return super.getTileEntity(i, j, k);
/* 34:   */   }
/* 35:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.multiparts.TilePartMetaAccess
 * JD-Core Version:    0.7.0.1
 */