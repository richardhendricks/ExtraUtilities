/*   1:    */ package com.rwtema.extrautils.block.render;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*   4:    */ import com.rwtema.extrautils.block.BlockColorData;
/*   5:    */ import com.rwtema.extrautils.helper.XURandom;
/*   6:    */ import com.rwtema.extrautils.tileentity.TileEntityBlockColorData;
/*   7:    */ import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
/*   8:    */ import cpw.mods.fml.relauncher.Side;
/*   9:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  10:    */ import java.util.Random;
/*  11:    */ import net.minecraft.block.Block;
/*  12:    */ import net.minecraft.client.Minecraft;
/*  13:    */ import net.minecraft.client.renderer.EntityRenderer;
/*  14:    */ import net.minecraft.client.renderer.RenderBlocks;
/*  15:    */ import net.minecraft.client.renderer.Tessellator;
/*  16:    */ import net.minecraft.tileentity.TileEntity;
/*  17:    */ import net.minecraft.world.IBlockAccess;
/*  18:    */ import org.lwjgl.opengl.GL11;
/*  19:    */ 
/*  20:    */ @SideOnly(Side.CLIENT)
/*  21:    */ public class RenderBlockColor
/*  22:    */   implements ISimpleBlockRenderingHandler
/*  23:    */ {
/*  24: 24 */   Random rand = XURandom.getInstance();
/*  25:    */   
/*  26:    */   public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
/*  27:    */   {
/*  28: 28 */     Tessellator var4 = Tessellator.instance;
/*  29: 29 */     block.setBlockBoundsForItemRender();
/*  30: 30 */     renderer.setRenderBoundsFromBlock(block);
/*  31: 31 */     GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
/*  32: 32 */     GL11.glTranslatef(-0.5F, -0.0F, -0.5F);
/*  33: 34 */     if ((metadata >= 16) || (metadata < 0)) {
/*  34: 35 */       metadata = this.rand.nextInt(16);
/*  35:    */     }
/*  36: 38 */     float f = com.rwtema.extrautils.block.BlockColor.initColor[metadata][0];
/*  37: 39 */     float f1 = com.rwtema.extrautils.block.BlockColor.initColor[metadata][1];
/*  38: 40 */     float f2 = com.rwtema.extrautils.block.BlockColor.initColor[metadata][2];
/*  39: 42 */     if (EntityRenderer.anaglyphEnable)
/*  40:    */     {
/*  41: 43 */       float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
/*  42: 44 */       float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
/*  43: 45 */       float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
/*  44: 46 */       f = f3;
/*  45: 47 */       f1 = f4;
/*  46: 48 */       f2 = f5;
/*  47:    */     }
/*  48: 51 */     GL11.glColor4f(f, f1, f2, 1.0F);
/*  49: 52 */     renderer.colorRedTopLeft *= f;
/*  50: 53 */     renderer.colorRedTopRight *= f;
/*  51: 54 */     renderer.colorRedBottomLeft *= f;
/*  52: 55 */     renderer.colorRedBottomRight *= f;
/*  53: 56 */     renderer.colorGreenTopLeft *= f1;
/*  54: 57 */     renderer.colorGreenTopRight *= f1;
/*  55: 58 */     renderer.colorGreenBottomLeft *= f1;
/*  56: 59 */     renderer.colorGreenBottomRight *= f1;
/*  57: 60 */     renderer.colorBlueTopLeft *= f2;
/*  58: 61 */     renderer.colorBlueTopRight *= f2;
/*  59: 62 */     renderer.colorBlueBottomLeft *= f2;
/*  60: 63 */     renderer.colorBlueBottomRight *= f2;
/*  61: 64 */     var4.startDrawingQuads();
/*  62: 65 */     var4.setNormal(0.0F, -1.0F, 0.0F);
/*  63: 66 */     renderer.renderFaceYNeg(block, 0.0D, -0.5D, 0.0D, block.getIcon(0, metadata));
/*  64: 67 */     var4.draw();
/*  65: 68 */     var4.startDrawingQuads();
/*  66: 69 */     var4.setNormal(0.0F, 1.0F, 0.0F);
/*  67: 70 */     renderer.renderFaceYPos(block, 0.0D, -0.5D, 0.0D, block.getIcon(1, metadata));
/*  68: 71 */     var4.draw();
/*  69: 72 */     var4.startDrawingQuads();
/*  70: 73 */     var4.setNormal(0.0F, 0.0F, -1.0F);
/*  71: 74 */     renderer.renderFaceXPos(block, 0.0D, -0.5D, 0.0D, block.getIcon(2, metadata));
/*  72: 75 */     var4.draw();
/*  73: 76 */     var4.startDrawingQuads();
/*  74: 77 */     var4.setNormal(0.0F, 0.0F, 1.0F);
/*  75: 78 */     renderer.renderFaceXNeg(block, 0.0D, -0.5D, 0.0D, block.getIcon(3, metadata));
/*  76: 79 */     var4.draw();
/*  77: 80 */     var4.startDrawingQuads();
/*  78: 81 */     var4.setNormal(-1.0F, 0.0F, 0.0F);
/*  79: 82 */     renderer.renderFaceZNeg(block, 0.0D, -0.5D, 0.0D, block.getIcon(4, metadata));
/*  80: 83 */     var4.draw();
/*  81: 84 */     var4.startDrawingQuads();
/*  82: 85 */     var4.setNormal(1.0F, 0.0F, 0.0F);
/*  83: 86 */     renderer.renderFaceZPos(block, 0.0D, -0.5D, 0.0D, block.getIcon(5, metadata));
/*  84: 87 */     var4.draw();
/*  85: 88 */     GL11.glTranslatef(0.5F, 0.0F, 0.5F);
/*  86: 89 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean renderWorldBlock(IBlockAccess world, int par2, int par3, int par4, Block par1Block, int modelId, RenderBlocks renderer)
/*  90:    */   {
/*  91: 94 */     Tessellator var8 = Tessellator.instance;
/*  92: 95 */     int i = world.getBlockMetadata(par2, par3, par4);
/*  93: 96 */     float f = com.rwtema.extrautils.block.BlockColor.initColor[i][0];
/*  94: 97 */     float f1 = com.rwtema.extrautils.block.BlockColor.initColor[i][1];
/*  95: 98 */     float f2 = com.rwtema.extrautils.block.BlockColor.initColor[i][2];
/*  96: 99 */     TileEntity data = world.getTileEntity(BlockColorData.dataBlockX(par2), BlockColorData.dataBlockY(par3), BlockColorData.dataBlockZ(par4));
/*  97:101 */     if ((data instanceof TileEntityBlockColorData))
/*  98:    */     {
/*  99:102 */       f = ((TileEntityBlockColorData)data).palette[i][0];
/* 100:103 */       f1 = ((TileEntityBlockColorData)data).palette[i][1];
/* 101:104 */       f2 = ((TileEntityBlockColorData)data).palette[i][2];
/* 102:    */     }
/* 103:107 */     if (EntityRenderer.anaglyphEnable)
/* 104:    */     {
/* 105:108 */       float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
/* 106:109 */       float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
/* 107:110 */       float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
/* 108:111 */       f = f3;
/* 109:112 */       f1 = f4;
/* 110:113 */       f2 = f5;
/* 111:    */     }
/* 112:116 */     return (Minecraft.isAmbientOcclusionEnabled()) && (par1Block.getLightValue() == 0) ? renderer.renderStandardBlockWithAmbientOcclusion(par1Block, par2, par3, par4, f, f1, f2) : renderer.renderStandardBlockWithColorMultiplier(par1Block, par2, par3, par4, f, f1, f2);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public boolean shouldRender3DInInventory(int modelId)
/* 116:    */   {
/* 117:122 */     return true;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public int getRenderId()
/* 121:    */   {
/* 122:127 */     return ExtraUtilsProxy.colorBlockID;
/* 123:    */   }
/* 124:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.render.RenderBlockColor
 * JD-Core Version:    0.7.0.1
 */