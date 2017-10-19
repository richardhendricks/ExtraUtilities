/*  1:   */ package com.rwtema.extrautils.tileentity.transfernodes.multiparts;
/*  2:   */ 
/*  3:   */ import codechicken.lib.vec.Vector3;
/*  4:   */ import codechicken.multipart.minecraft.McMetaPart;
/*  5:   */ import cpw.mods.fml.relauncher.Side;
/*  6:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  7:   */ import net.minecraft.client.renderer.RenderBlocks;
/*  8:   */ import net.minecraft.tileentity.TileEntity;
/*  9:   */ 
/* 10:   */ public abstract class MCMetaTilePart
/* 11:   */   extends McMetaPart
/* 12:   */ {
/* 13:   */   public MCMetaTilePart(int meta)
/* 14:   */   {
/* 15:13 */     super(meta);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public MCMetaTilePart() {}
/* 19:   */   
/* 20:   */   public abstract TileEntity getBlockTile();
/* 21:   */   
/* 22:   */   @SideOnly(Side.CLIENT)
/* 23:   */   public boolean renderStatic(Vector3 pos, int pass)
/* 24:   */   {
/* 25:25 */     if (pass == 0)
/* 26:   */     {
/* 27:26 */       new RenderBlocks(new TilePartMetaAccess(this, getBlockTile())).renderBlockByRenderType(getBlock(), x(), y(), z());
/* 28:27 */       return true;
/* 29:   */     }
/* 30:29 */     return false;
/* 31:   */   }
/* 32:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.multiparts.MCMetaTilePart
 * JD-Core Version:    0.7.0.1
 */