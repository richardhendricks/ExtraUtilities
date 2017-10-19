/*   1:    */ package com.rwtema.extrautils.block.render;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*   4:    */ import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
/*   5:    */ import cpw.mods.fml.relauncher.Side;
/*   6:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   7:    */ import net.minecraft.block.Block;
/*   8:    */ import net.minecraft.client.renderer.EntityRenderer;
/*   9:    */ import net.minecraft.client.renderer.RenderBlocks;
/*  10:    */ import net.minecraft.client.renderer.Tessellator;
/*  11:    */ import net.minecraft.world.IBlockAccess;
/*  12:    */ import org.lwjgl.opengl.GL11;
/*  13:    */ 
/*  14:    */ @SideOnly(Side.CLIENT)
/*  15:    */ public class RenderBlockConnectedTextures
/*  16:    */   implements ISimpleBlockRenderingHandler
/*  17:    */ {
/*  18:    */   public FakeRenderBlocks getFakeRender()
/*  19:    */   {
/*  20: 17 */     return fakeRender;
/*  21:    */   }
/*  22:    */   
/*  23: 20 */   public static FakeRenderBlocks fakeRender = new FakeRenderBlocks();
/*  24:    */   
/*  25:    */   public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
/*  26:    */   {
/*  27: 24 */     Tessellator var4 = Tessellator.instance;
/*  28: 25 */     block.setBlockBoundsForItemRender();
/*  29: 26 */     renderer.setRenderBoundsFromBlock(block);
/*  30: 27 */     GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
/*  31: 28 */     GL11.glTranslatef(-0.5F, -0.0F, -0.5F);
/*  32: 29 */     float f = 1.0F;
/*  33: 30 */     float f1 = 1.0F;
/*  34: 31 */     float f2 = 1.0F;
/*  35: 33 */     if (EntityRenderer.anaglyphEnable)
/*  36:    */     {
/*  37: 34 */       float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
/*  38: 35 */       float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
/*  39: 36 */       float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
/*  40: 37 */       f = f3;
/*  41: 38 */       f1 = f4;
/*  42: 39 */       f2 = f5;
/*  43:    */     }
/*  44: 42 */     GL11.glColor4f(f, f1, f2, 1.0F);
/*  45: 43 */     renderer.colorRedTopLeft *= f;
/*  46: 44 */     renderer.colorRedTopRight *= f;
/*  47: 45 */     renderer.colorRedBottomLeft *= f;
/*  48: 46 */     renderer.colorRedBottomRight *= f;
/*  49: 47 */     renderer.colorGreenTopLeft *= f1;
/*  50: 48 */     renderer.colorGreenTopRight *= f1;
/*  51: 49 */     renderer.colorGreenBottomLeft *= f1;
/*  52: 50 */     renderer.colorGreenBottomRight *= f1;
/*  53: 51 */     renderer.colorBlueTopLeft *= f2;
/*  54: 52 */     renderer.colorBlueTopRight *= f2;
/*  55: 53 */     renderer.colorBlueBottomLeft *= f2;
/*  56: 54 */     renderer.colorBlueBottomRight *= f2;
/*  57: 56 */     if (block.getIcon(0, metadata) == null) {
/*  58: 57 */       return;
/*  59:    */     }
/*  60: 60 */     var4.startDrawingQuads();
/*  61: 61 */     var4.setNormal(0.0F, -1.0F, 0.0F);
/*  62: 62 */     renderer.renderFaceYNeg(block, 0.0D, -0.5D, 0.0D, block.getIcon(0, metadata));
/*  63: 63 */     var4.draw();
/*  64: 64 */     var4.startDrawingQuads();
/*  65: 65 */     var4.setNormal(0.0F, 1.0F, 0.0F);
/*  66: 66 */     renderer.renderFaceYPos(block, 0.0D, -0.5D, 0.0D, block.getIcon(1, metadata));
/*  67: 67 */     var4.draw();
/*  68: 68 */     var4.startDrawingQuads();
/*  69: 69 */     var4.setNormal(0.0F, 0.0F, -1.0F);
/*  70: 70 */     renderer.renderFaceXPos(block, 0.0D, -0.5D, 0.0D, block.getIcon(2, metadata));
/*  71: 71 */     var4.draw();
/*  72: 72 */     var4.startDrawingQuads();
/*  73: 73 */     var4.setNormal(0.0F, 0.0F, 1.0F);
/*  74: 74 */     renderer.renderFaceXNeg(block, 0.0D, -0.5D, 0.0D, block.getIcon(3, metadata));
/*  75: 75 */     var4.draw();
/*  76: 76 */     var4.startDrawingQuads();
/*  77: 77 */     var4.setNormal(-1.0F, 0.0F, 0.0F);
/*  78: 78 */     renderer.renderFaceZNeg(block, 0.0D, -0.5D, 0.0D, block.getIcon(4, metadata));
/*  79: 79 */     var4.draw();
/*  80: 80 */     var4.startDrawingQuads();
/*  81: 81 */     var4.setNormal(1.0F, 0.0F, 0.0F);
/*  82: 82 */     renderer.renderFaceZPos(block, 0.0D, -0.5D, 0.0D, block.getIcon(5, metadata));
/*  83: 83 */     var4.draw();
/*  84:    */     
/*  85: 85 */     GL11.glTranslatef(0.5F, 0.0F, 0.5F);
/*  86: 86 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
/*  90:    */   {
/*  91: 91 */     if (renderer.hasOverrideBlockTexture()) {
/*  92: 92 */       return renderer.renderStandardBlock(block, x, y, z);
/*  93:    */     }
/*  94: 94 */     getFakeRender().setWorld(renderer.blockAccess);
/*  95: 95 */     getFakeRender().curBlock = world.getBlock(x, y, z);
/*  96: 96 */     getFakeRender().curMeta = world.getBlockMetadata(x, y, z);
/*  97: 97 */     block.setBlockBoundsBasedOnState(fakeRender.blockAccess, x, y, z);
/*  98: 98 */     getFakeRender().setRenderBoundsFromBlock(block);
/*  99: 99 */     return getFakeRender().renderStandardBlock(block, x, y, z);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public boolean shouldRender3DInInventory(int modelId)
/* 103:    */   {
/* 104:105 */     return true;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public int getRenderId()
/* 108:    */   {
/* 109:110 */     return ExtraUtilsProxy.connectedTextureID;
/* 110:    */   }
/* 111:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.render.RenderBlockConnectedTextures
 * JD-Core Version:    0.7.0.1
 */