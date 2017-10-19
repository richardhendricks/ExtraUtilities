/*  1:   */ package com.rwtema.extrautils.multipart.microblock;
/*  2:   */ 
/*  3:   */ import codechicken.lib.render.CCRenderPipeline;
/*  4:   */ import codechicken.lib.render.CCRenderPipeline.PipelineBuilder;
/*  5:   */ import codechicken.lib.render.CCRenderState;
/*  6:   */ import codechicken.lib.render.TextureUtils;
/*  7:   */ import codechicken.microblock.MicroMaterialRegistry;
/*  8:   */ import codechicken.microblock.MicroMaterialRegistry.IMicroMaterial;
/*  9:   */ import cpw.mods.fml.relauncher.Side;
/* 10:   */ import cpw.mods.fml.relauncher.SideOnly;
/* 11:   */ import java.util.Map;
/* 12:   */ import net.minecraft.client.renderer.OpenGlHelper;
/* 13:   */ import net.minecraft.item.ItemStack;
/* 14:   */ import net.minecraft.nbt.NBTTagCompound;
/* 15:   */ import net.minecraftforge.client.IItemRenderer;
/* 16:   */ import net.minecraftforge.client.IItemRenderer.ItemRenderType;
/* 17:   */ import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
/* 18:   */ import org.lwjgl.opengl.GL11;
/* 19:   */ 
/* 20:   */ @SideOnly(Side.CLIENT)
/* 21:   */ public class RenderItemMicroblock
/* 22:   */   implements IItemRenderer
/* 23:   */ {
/* 24:17 */   MicroMaterialRegistry.IMicroMaterial wool = null;
/* 25:   */   
/* 26:   */   public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type)
/* 27:   */   {
/* 28:21 */     return true;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper)
/* 32:   */   {
/* 33:26 */     return true;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void renderItem(IItemRenderer.ItemRenderType type, ItemStack stack, Object... data)
/* 37:   */   {
/* 38:31 */     if (!stack.hasTagCompound()) {
/* 39:32 */       return;
/* 40:   */     }
/* 41:35 */     String mat = stack.getTagCompound().getString("mat");
/* 42:36 */     MicroMaterialRegistry.IMicroMaterial material = MicroMaterialRegistry.getMaterial(mat);
/* 43:38 */     if (material == null) {
/* 44:39 */       return;
/* 45:   */     }
/* 46:42 */     GL11.glPushMatrix();
/* 47:44 */     if (type == IItemRenderer.ItemRenderType.ENTITY) {
/* 48:45 */       GL11.glScaled(0.5D, 0.5D, 0.5D);
/* 49:   */     }
/* 50:48 */     if ((type == IItemRenderer.ItemRenderType.INVENTORY) || (type == IItemRenderer.ItemRenderType.ENTITY)) {
/* 51:49 */       GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/* 52:   */     }
/* 53:52 */     OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 54:53 */     GL11.glEnable(3008);
/* 55:   */     
/* 56:   */ 
/* 57:56 */     CCRenderPipeline.PipelineBuilder builder = CCRenderState.pipeline.builder();
/* 58:   */     
/* 59:58 */     CCRenderState.reset();
/* 60:   */     
/* 61:60 */     TextureUtils.bindAtlas(0);
/* 62:61 */     CCRenderState.useNormals = true;
/* 63:62 */     CCRenderState.pullLightmap();
/* 64:63 */     CCRenderState.startDrawing();
/* 65:   */     
/* 66:65 */     IMicroBlock part = (IMicroBlock)RegisterMicroBlocks.mParts.get(Integer.valueOf(stack.getItemDamage()));
/* 67:67 */     if (part != null) {
/* 68:68 */       part.renderItem(stack, material);
/* 69:   */     }
/* 70:71 */     CCRenderState.draw();
/* 71:72 */     GL11.glPopMatrix();
/* 72:   */   }
/* 73:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.multipart.microblock.RenderItemMicroblock
 * JD-Core Version:    0.7.0.1
 */