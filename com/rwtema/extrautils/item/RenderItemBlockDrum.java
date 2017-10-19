/*  1:   */ package com.rwtema.extrautils.item;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.block.render.RenderBlockDrum;
/*  4:   */ import cpw.mods.fml.relauncher.Side;
/*  5:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  6:   */ import net.minecraft.block.Block;
/*  7:   */ import net.minecraft.client.Minecraft;
/*  8:   */ import net.minecraft.client.renderer.OpenGlHelper;
/*  9:   */ import net.minecraft.client.renderer.RenderBlocks;
/* 10:   */ import net.minecraft.client.renderer.Tessellator;
/* 11:   */ import net.minecraft.entity.Entity;
/* 12:   */ import net.minecraft.item.ItemBlock;
/* 13:   */ import net.minecraft.item.ItemStack;
/* 14:   */ import net.minecraftforge.client.IItemRenderer;
/* 15:   */ import net.minecraftforge.client.IItemRenderer.ItemRenderType;
/* 16:   */ import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
/* 17:   */ import org.lwjgl.opengl.GL11;
/* 18:   */ 
/* 19:   */ @SideOnly(Side.CLIENT)
/* 20:   */ public class RenderItemBlockDrum
/* 21:   */   implements IItemRenderer
/* 22:   */ {
/* 23:   */   public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type)
/* 24:   */   {
/* 25:21 */     switch (1.$SwitchMap$net$minecraftforge$client$IItemRenderer$ItemRenderType[type.ordinal()])
/* 26:   */     {
/* 27:   */     case 1: 
/* 28:23 */       return true;
/* 29:   */     case 2: 
/* 30:26 */       return true;
/* 31:   */     case 3: 
/* 32:29 */       return true;
/* 33:   */     case 4: 
/* 34:32 */       return true;
/* 35:   */     }
/* 36:35 */     return false;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper)
/* 40:   */   {
/* 41:41 */     return true;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data)
/* 45:   */   {
/* 46:46 */     if (!(item.getItem() instanceof ItemBlock)) {
/* 47:47 */       return;
/* 48:   */     }
/* 49:50 */     Block block = ((ItemBlock)item.getItem()).field_150939_a;
/* 50:52 */     if (block == null) {
/* 51:53 */       return;
/* 52:   */     }
/* 53:56 */     RenderBlocks renderer = (RenderBlocks)data[0];
/* 54:57 */     Entity holder = null;
/* 55:59 */     if ((data.length > 1) && 
/* 56:60 */       ((data[1] instanceof Entity))) {
/* 57:61 */       holder = (Entity)data[1];
/* 58:   */     }
/* 59:64 */     if (holder == null) {
/* 60:65 */       holder = Minecraft.getMinecraft().thePlayer;
/* 61:   */     }
/* 62:68 */     Tessellator var4 = Tessellator.instance;
/* 63:69 */     block.setBlockBoundsForItemRender();
/* 64:70 */     renderer.setRenderBoundsFromBlock(block);
/* 65:71 */     GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
/* 66:73 */     switch (1.$SwitchMap$net$minecraftforge$client$IItemRenderer$ItemRenderType[type.ordinal()])
/* 67:   */     {
/* 68:   */     case 2: 
/* 69:   */     case 3: 
/* 70:76 */       GL11.glTranslatef(-1.0F, 0.5F, 0.0F);
/* 71:77 */       break;
/* 72:   */     default: 
/* 73:80 */       GL11.glTranslatef(-0.5F, -0.0F, -0.5F);
/* 74:   */     }
/* 75:83 */     OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 76:84 */     GL11.glEnable(3008);
/* 77:   */     
/* 78:86 */     RenderBlockDrum.drawInvBlock(block, item);
/* 79:87 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 80:88 */     GL11.glTranslatef(0.5F, 0.0F, 0.5F);
/* 81:   */   }
/* 82:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.RenderItemBlockDrum
 * JD-Core Version:    0.7.0.1
 */