/*   1:    */ package com.rwtema.extrautils.block.render;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*   4:    */ import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
/*   5:    */ import cpw.mods.fml.relauncher.Side;
/*   6:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   7:    */ import net.minecraft.block.Block;
/*   8:    */ import net.minecraft.client.renderer.RenderBlocks;
/*   9:    */ import net.minecraft.client.renderer.Tessellator;
/*  10:    */ import net.minecraft.world.IBlockAccess;
/*  11:    */ import org.lwjgl.opengl.GL11;
/*  12:    */ 
/*  13:    */ @SideOnly(Side.CLIENT)
/*  14:    */ public class RenderBlockFullBright
/*  15:    */   implements ISimpleBlockRenderingHandler
/*  16:    */ {
/*  17:    */   public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
/*  18:    */   {
/*  19: 17 */     Tessellator var4 = Tessellator.instance;
/*  20: 18 */     block.setBlockBoundsForItemRender();
/*  21: 19 */     renderer.setRenderBoundsFromBlock(block);
/*  22: 20 */     GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
/*  23: 21 */     GL11.glTranslatef(-0.5F, -0.0F, -0.5F);
/*  24:    */     
/*  25: 23 */     int l = block.getRenderColor(metadata);
/*  26: 24 */     float r = (l >> 16 & 0xFF) / 255.0F;
/*  27: 25 */     float g = (l >> 8 & 0xFF) / 255.0F;
/*  28: 26 */     float b = (l & 0xFF) / 255.0F;
/*  29: 27 */     GL11.glColor4f(r, g, b, 1.0F);
/*  30: 28 */     GL11.glDisable(2896);
/*  31: 29 */     renderer.enableAO = false;
/*  32: 30 */     var4.startDrawingQuads();
/*  33: 31 */     var4.setNormal(0.0F, 1.0F, 0.0F);
/*  34:    */     
/*  35: 33 */     renderer.renderFaceYNeg(block, 0.0D, -0.5D, 0.0D, block.getIcon(0, metadata));
/*  36:    */     
/*  37:    */ 
/*  38:    */ 
/*  39: 37 */     renderer.renderFaceYPos(block, 0.0D, -0.5D, 0.0D, block.getIcon(1, metadata));
/*  40:    */     
/*  41:    */ 
/*  42:    */ 
/*  43: 41 */     renderer.renderFaceZNeg(block, 0.0D, -0.5D, 0.0D, block.getIcon(2, metadata));
/*  44:    */     
/*  45:    */ 
/*  46:    */ 
/*  47: 45 */     renderer.renderFaceZPos(block, 0.0D, -0.5D, 0.0D, block.getIcon(3, metadata));
/*  48:    */     
/*  49:    */ 
/*  50:    */ 
/*  51: 49 */     renderer.renderFaceXNeg(block, 0.0D, -0.5D, 0.0D, block.getIcon(4, metadata));
/*  52:    */     
/*  53:    */ 
/*  54:    */ 
/*  55: 53 */     renderer.renderFaceXPos(block, 0.0D, -0.5D, 0.0D, block.getIcon(5, metadata));
/*  56: 54 */     var4.draw();
/*  57: 55 */     GL11.glTranslatef(0.5F, 0.0F, 0.5F);
/*  58: 56 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  59: 57 */     GL11.glEnable(2896);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public boolean renderWorldBlock(IBlockAccess world, int par2, int par3, int par4, Block par1Block, int modelId, RenderBlocks renderer)
/*  63:    */   {
/*  64: 62 */     renderer.enableAO = false;
/*  65: 63 */     Tessellator tessellator = Tessellator.instance;
/*  66: 64 */     boolean flag = false;
/*  67: 65 */     tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
/*  68: 66 */     tessellator.setBrightness(240);
/*  69: 67 */     int meta = world.getBlockMetadata(par2, par3, par4);
/*  70: 68 */     int l = par1Block.getRenderColor(meta);
/*  71: 69 */     float r = (l >> 16 & 0xFF) / 255.0F;
/*  72: 70 */     float g = (l >> 8 & 0xFF) / 255.0F;
/*  73: 71 */     float b = (l & 0xFF) / 255.0F;
/*  74: 72 */     tessellator.setColorOpaque_F(r, g, b);
/*  75: 74 */     if ((renderer.renderAllFaces) || (par1Block.shouldSideBeRendered(renderer.blockAccess, par2, par3 - 1, par4, 0)))
/*  76:    */     {
/*  77: 75 */       renderer.renderFaceYNeg(par1Block, par2, par3, par4, renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 0));
/*  78: 76 */       flag = true;
/*  79:    */     }
/*  80: 79 */     if ((renderer.renderAllFaces) || (par1Block.shouldSideBeRendered(renderer.blockAccess, par2, par3 + 1, par4, 1)))
/*  81:    */     {
/*  82: 80 */       renderer.renderFaceYPos(par1Block, par2, par3, par4, renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 1));
/*  83: 81 */       flag = true;
/*  84:    */     }
/*  85: 84 */     if ((renderer.renderAllFaces) || (par1Block.shouldSideBeRendered(renderer.blockAccess, par2, par3, par4 - 1, 2)))
/*  86:    */     {
/*  87: 85 */       renderer.renderFaceZNeg(par1Block, par2, par3, par4, renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 2));
/*  88: 86 */       flag = true;
/*  89:    */     }
/*  90: 89 */     if ((renderer.renderAllFaces) || (par1Block.shouldSideBeRendered(renderer.blockAccess, par2, par3, par4 + 1, 3)))
/*  91:    */     {
/*  92: 90 */       renderer.renderFaceZPos(par1Block, par2, par3, par4, renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 3));
/*  93: 91 */       flag = true;
/*  94:    */     }
/*  95: 94 */     if ((renderer.renderAllFaces) || (par1Block.shouldSideBeRendered(renderer.blockAccess, par2 - 1, par3, par4, 4)))
/*  96:    */     {
/*  97: 95 */       renderer.renderFaceXNeg(par1Block, par2, par3, par4, renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 4));
/*  98: 96 */       flag = true;
/*  99:    */     }
/* 100: 99 */     if ((renderer.renderAllFaces) || (par1Block.shouldSideBeRendered(renderer.blockAccess, par2 + 1, par3, par4, 5)))
/* 101:    */     {
/* 102:100 */       renderer.renderFaceXPos(par1Block, par2, par3, par4, renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 5));
/* 103:101 */       flag = true;
/* 104:    */     }
/* 105:104 */     return flag;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public boolean shouldRender3DInInventory(int modelId)
/* 109:    */   {
/* 110:109 */     return true;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public int getRenderId()
/* 114:    */   {
/* 115:114 */     return ExtraUtilsProxy.fullBrightBlockID;
/* 116:    */   }
/* 117:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.render.RenderBlockFullBright
 * JD-Core Version:    0.7.0.1
 */