/*  1:   */ package com.rwtema.extrautils.multipart;
/*  2:   */ 
/*  3:   */ import codechicken.lib.render.CCRenderPipeline;
/*  4:   */ import codechicken.lib.render.CCRenderPipeline.PipelineBuilder;
/*  5:   */ import codechicken.lib.render.CCRenderState;
/*  6:   */ import codechicken.lib.render.CCRenderState.IVertexOperation;
/*  7:   */ import codechicken.lib.render.ColourMultiplier;
/*  8:   */ import codechicken.lib.vec.Cuboid6;
/*  9:   */ import codechicken.lib.vec.Vector3;
/* 10:   */ import codechicken.microblock.BlockMicroMaterial;
/* 11:   */ import com.rwtema.extrautils.block.BlockGreenScreen;
/* 12:   */ import cpw.mods.fml.relauncher.Side;
/* 13:   */ import cpw.mods.fml.relauncher.SideOnly;
/* 14:   */ import net.minecraft.block.Block;
/* 15:   */ 
/* 16:   */ public class FullBrightMicroMaterial
/* 17:   */   extends BlockMicroMaterial
/* 18:   */ {
/* 19:   */   public FullBrightMicroMaterial(BlockGreenScreen block, int meta)
/* 20:   */   {
/* 21:15 */     super(block, meta);
/* 22:   */   }
/* 23:   */   
/* 24:   */   @SideOnly(Side.CLIENT)
/* 25:   */   public int getColour(int pass)
/* 26:   */   {
/* 27:21 */     return block().getRenderColor(meta()) << 8 | 0xFF;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public int getLightValue()
/* 31:   */   {
/* 32:26 */     return BlockGreenScreen.getLightLevel(meta());
/* 33:   */   }
/* 34:   */   
/* 35:   */   @SideOnly(Side.CLIENT)
/* 36:   */   public void renderMicroFace(Vector3 pos, int pass, Cuboid6 bounds)
/* 37:   */   {
/* 38:32 */     CCRenderPipeline.PipelineBuilder builder = CCRenderState.pipeline.builder();
/* 39:33 */     builder.add(pos.translation()).add(icont());
/* 40:34 */     builder.add(ColourMultiplier.instance(getColour(pass)));
/* 41:35 */     builder.add(Lighting.instance);
/* 42:36 */     builder.render();
/* 43:   */   }
/* 44:   */   
/* 45:   */   public static class Lighting
/* 46:   */     implements CCRenderState.IVertexOperation
/* 47:   */   {
/* 48:40 */     public static Lighting instance = new Lighting();
/* 49:42 */     public static int id = CCRenderState.registerOperation();
/* 50:   */     
/* 51:   */     public boolean load()
/* 52:   */     {
/* 53:46 */       if (!CCRenderState.computeLighting) {
/* 54:47 */         return false;
/* 55:   */       }
/* 56:49 */       CCRenderState.pipeline.addDependency(CCRenderState.colourAttrib);
/* 57:50 */       CCRenderState.pipeline.addDependency(CCRenderState.lightCoordAttrib);
/* 58:51 */       return true;
/* 59:   */     }
/* 60:   */     
/* 61:   */     public void operate()
/* 62:   */     {
/* 63:56 */       CCRenderState.setBrightness(16711935);
/* 64:   */     }
/* 65:   */     
/* 66:   */     public int operationID()
/* 67:   */     {
/* 68:61 */       return id;
/* 69:   */     }
/* 70:   */   }
/* 71:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.multipart.FullBrightMicroMaterial
 * JD-Core Version:    0.7.0.1
 */