/*  1:   */ package com.rwtema.extrautils.item;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.relauncher.Side;
/*  4:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  5:   */ import net.minecraft.client.renderer.ItemRenderer;
/*  6:   */ import net.minecraft.client.renderer.OpenGlHelper;
/*  7:   */ import net.minecraft.client.renderer.Tessellator;
/*  8:   */ import net.minecraft.item.ItemStack;
/*  9:   */ import net.minecraft.util.IIcon;
/* 10:   */ import net.minecraftforge.client.IItemRenderer;
/* 11:   */ import net.minecraftforge.client.IItemRenderer.ItemRenderType;
/* 12:   */ import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
/* 13:   */ import org.lwjgl.opengl.GL11;
/* 14:   */ 
/* 15:   */ @SideOnly(Side.CLIENT)
/* 16:   */ public class RenderItemMultiTransparency
/* 17:   */   implements IItemRenderer
/* 18:   */ {
/* 19:   */   public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type)
/* 20:   */   {
/* 21:18 */     return true;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper)
/* 25:   */   {
/* 26:23 */     return (helper == IItemRenderer.ItemRendererHelper.ENTITY_ROTATION) || (helper == IItemRenderer.ItemRendererHelper.ENTITY_BOBBING);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data)
/* 30:   */   {
/* 31:29 */     if (!(item.getItem() instanceof IItemMultiTransparency)) {
/* 32:30 */       return;
/* 33:   */     }
/* 34:33 */     GL11.glEnable(2896);
/* 35:34 */     GL11.glEnable(2929);
/* 36:   */     
/* 37:36 */     IItemMultiTransparency itemTrans = (IItemMultiTransparency)item.getItem();
/* 38:38 */     if (type == IItemRenderer.ItemRenderType.INVENTORY)
/* 39:   */     {
/* 40:39 */       GL11.glScalef(16.0F, 16.0F, 1.0F);
/* 41:   */     }
/* 42:40 */     else if (type == IItemRenderer.ItemRenderType.ENTITY)
/* 43:   */     {
/* 44:41 */       GL11.glTranslatef(-0.5F, -0.25F, 0.0F);
/* 45:42 */       GL11.glDisable(2884);
/* 46:   */     }
/* 47:45 */     Tessellator tessellator = Tessellator.instance;
/* 48:47 */     for (int i = 0; i < itemTrans.numIcons(item); i++)
/* 49:   */     {
/* 50:48 */       IIcon icon = itemTrans.getIconForTransparentRender(item, i);
/* 51:49 */       float trans = itemTrans.getIconTransparency(item, i);
/* 52:50 */       OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 53:51 */       GL11.glEnable(3008);
/* 54:53 */       if (trans < 1.0F)
/* 55:   */       {
/* 56:54 */         GL11.glBlendFunc(770, 771);
/* 57:55 */         GL11.glEnable(3042);
/* 58:56 */         GL11.glDisable(3008);
/* 59:   */         
/* 60:58 */         GL11.glShadeModel(7425);
/* 61:59 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, trans);
/* 62:61 */         if (type != IItemRenderer.ItemRenderType.INVENTORY)
/* 63:   */         {
/* 64:62 */           GL11.glEnable(32826);
/* 65:63 */           ItemRenderer.renderItemIn2D(tessellator, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
/* 66:64 */           GL11.glDisable(32826);
/* 67:   */         }
/* 68:   */         else
/* 69:   */         {
/* 70:66 */           tessellator.startDrawingQuads();
/* 71:67 */           tessellator.addVertexWithUV(0.0D, 0.0D, 0.03125D, icon.getMinU(), icon.getMinV());
/* 72:68 */           tessellator.addVertexWithUV(0.0D, 1.0D, 0.03125D, icon.getMinU(), icon.getMaxV());
/* 73:69 */           tessellator.addVertexWithUV(1.0D, 1.0D, 0.03125D, icon.getMaxU(), icon.getMaxV());
/* 74:70 */           tessellator.addVertexWithUV(1.0D, 0.0D, 0.03125D, icon.getMaxU(), icon.getMinV());
/* 75:71 */           tessellator.draw();
/* 76:   */         }
/* 77:74 */         GL11.glShadeModel(7424);
/* 78:75 */         GL11.glEnable(3008);
/* 79:76 */         GL11.glDisable(3042);
/* 80:   */       }
/* 81:78 */       else if (type != IItemRenderer.ItemRenderType.INVENTORY)
/* 82:   */       {
/* 83:79 */         GL11.glEnable(32826);
/* 84:80 */         ItemRenderer.renderItemIn2D(tessellator, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
/* 85:81 */         GL11.glDisable(32826);
/* 86:   */       }
/* 87:   */       else
/* 88:   */       {
/* 89:83 */         tessellator.startDrawingQuads();
/* 90:84 */         tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, icon.getMinU(), icon.getMinV());
/* 91:85 */         tessellator.addVertexWithUV(0.0D, 1.0D, 0.0D, icon.getMinU(), icon.getMaxV());
/* 92:86 */         tessellator.addVertexWithUV(1.0D, 1.0D, 0.0D, icon.getMaxU(), icon.getMaxV());
/* 93:87 */         tessellator.addVertexWithUV(1.0D, 0.0D, 0.0D, icon.getMaxU(), icon.getMinV());
/* 94:88 */         tessellator.draw();
/* 95:   */       }
/* 96:   */     }
/* 97:93 */     if (type == IItemRenderer.ItemRenderType.INVENTORY)
/* 98:   */     {
/* 99:94 */       GL11.glScalef(0.0625F, 0.0625F, 1.0F);
/* :0:   */     }
/* :1:95 */     else if (type == IItemRenderer.ItemRenderType.ENTITY)
/* :2:   */     {
/* :3:96 */       GL11.glTranslatef(0.5F, 0.25F, 0.0F);
/* :4:97 */       GL11.glEnable(2884);
/* :5:   */     }
/* :6:   */   }
/* :7:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.RenderItemMultiTransparency
 * JD-Core Version:    0.7.0.1
 */