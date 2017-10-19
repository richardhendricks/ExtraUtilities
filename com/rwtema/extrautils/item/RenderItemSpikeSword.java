/*  1:   */ package com.rwtema.extrautils.item;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtilsClient;
/*  4:   */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*  5:   */ import com.rwtema.extrautils.block.render.RenderBlockSpike;
/*  6:   */ import com.rwtema.extrautils.helper.GLHelper;
/*  7:   */ import com.rwtema.extrautils.tileentity.RenderTileEntitySpike;
/*  8:   */ import net.minecraft.client.renderer.RenderBlocks;
/*  9:   */ import net.minecraft.item.ItemStack;
/* 10:   */ import net.minecraftforge.client.IItemRenderer;
/* 11:   */ import net.minecraftforge.client.IItemRenderer.ItemRenderType;
/* 12:   */ import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
/* 13:   */ import org.lwjgl.opengl.GL11;
/* 14:   */ 
/* 15:   */ public class RenderItemSpikeSword
/* 16:   */   implements IItemRenderer
/* 17:   */ {
/* 18:   */   public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type)
/* 19:   */   {
/* 20:16 */     return true;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper)
/* 24:   */   {
/* 25:21 */     return true;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data)
/* 29:   */   {
/* 30:   */     
/* 31:28 */     if (type == IItemRenderer.ItemRenderType.ENTITY) {
/* 32:29 */       GL11.glScaled(0.5D, 0.5D, 0.5D);
/* 33:   */     }
/* 34:31 */     if ((type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) || (type == IItemRenderer.ItemRenderType.EQUIPPED)) {
/* 35:32 */       GL11.glTranslated(0.5D, 0.5D, 0.5D);
/* 36:   */     }
/* 37:35 */     ExtraUtilsClient.renderBlockSpike.renderInventoryBlock(((ItemBlockSpike)item.getItem()).spike, 0, ExtraUtilsProxy.spikeBlockID, (RenderBlocks)data[0]);
/* 38:37 */     if (item.hasEffect(0))
/* 39:   */     {
/* 40:38 */       GL11.glTranslated(0.0D, 0.0D, -1.0D);
/* 41:39 */       GL11.glScaled(1.01D, 1.01D, 1.01D);
/* 42:40 */       GL11.glTranslated(-0.5D, -0.5D, -0.5D);
/* 43:41 */       GLHelper.pushGLState();
/* 44:42 */       GLHelper.enableGLState(3008);
/* 45:   */       
/* 46:44 */       RenderTileEntitySpike.hashCode = System.identityHashCode(item);
/* 47:45 */       RenderTileEntitySpike.drawEnchantedSpike(0);
/* 48:   */       
/* 49:47 */       GLHelper.popGLState();
/* 50:   */     }
/* 51:50 */     GL11.glPopMatrix();
/* 52:   */   }
/* 53:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.RenderItemSpikeSword
 * JD-Core Version:    0.7.0.1
 */