/*  1:   */ package com.rwtema.extrautils.block.render;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*  4:   */ import cpw.mods.fml.relauncher.Side;
/*  5:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  6:   */ import net.minecraft.block.Block;
/*  7:   */ import net.minecraft.client.renderer.RenderBlocks;
/*  8:   */ import net.minecraft.world.IBlockAccess;
/*  9:   */ 
/* 10:   */ @SideOnly(Side.CLIENT)
/* 11:   */ public class RenderBlockConnectedTexturesEthereal
/* 12:   */   extends RenderBlockConnectedTextures
/* 13:   */ {
/* 14:13 */   public static FakeRenderEtherealBlocks fakeRenderEtherealBlocks = new FakeRenderEtherealBlocks();
/* 15:   */   
/* 16:   */   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
/* 17:   */   {
/* 18:17 */     if (renderer.hasOverrideBlockTexture()) {
/* 19:18 */       return renderer.renderStandardBlock(block, x, y, z);
/* 20:   */     }
/* 21:20 */     fakeRender.setWorld(renderer.blockAccess);
/* 22:21 */     fakeRender.curBlock = world.getBlock(x, y, z);
/* 23:22 */     fakeRender.curMeta = world.getBlockMetadata(x, y, z);
/* 24:23 */     block.setBlockBoundsBasedOnState(fakeRender.blockAccess, x, y, z);
/* 25:24 */     fakeRender.setRenderBoundsFromBlock(block);
/* 26:25 */     boolean render = fakeRender.renderStandardBlock(block, x, y, z);
/* 27:   */     
/* 28:27 */     fakeRenderEtherealBlocks.setWorld(renderer.blockAccess);
/* 29:28 */     fakeRenderEtherealBlocks.curBlock = fakeRender.curBlock;
/* 30:29 */     fakeRenderEtherealBlocks.curMeta = fakeRender.curMeta;
/* 31:30 */     double h = 0.05D;
/* 32:31 */     fakeRenderEtherealBlocks.setRenderBounds(h, h, h, 1.0D - h, 1.0D - h, 1.0D - h);
/* 33:   */     
/* 34:33 */     render &= fakeRenderEtherealBlocks.renderStandardBlock(block, x, y, z);
/* 35:34 */     return render;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public int getRenderId()
/* 39:   */   {
/* 40:41 */     return ExtraUtilsProxy.connectedTextureEtheralID;
/* 41:   */   }
/* 42:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.render.RenderBlockConnectedTexturesEthereal
 * JD-Core Version:    0.7.0.1
 */