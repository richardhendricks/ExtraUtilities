/*   1:    */ package com.rwtema.extrautils.item;
/*   2:    */ 
/*   3:    */ import cpw.mods.fml.relauncher.Side;
/*   4:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   5:    */ import net.minecraft.client.Minecraft;
/*   6:    */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*   7:    */ import net.minecraft.client.renderer.ItemRenderer;
/*   8:    */ import net.minecraft.client.renderer.OpenGlHelper;
/*   9:    */ import net.minecraft.client.renderer.Tessellator;
/*  10:    */ import net.minecraft.item.ItemStack;
/*  11:    */ import net.minecraft.nbt.NBTTagCompound;
/*  12:    */ import net.minecraft.util.IIcon;
/*  13:    */ import net.minecraft.world.World;
/*  14:    */ import net.minecraftforge.client.IItemRenderer;
/*  15:    */ import net.minecraftforge.client.IItemRenderer.ItemRenderType;
/*  16:    */ import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
/*  17:    */ import org.lwjgl.opengl.GL11;
/*  18:    */ 
/*  19:    */ @SideOnly(Side.CLIENT)
/*  20:    */ public class RenderItemUnstable
/*  21:    */   implements IItemRenderer
/*  22:    */ {
/*  23:    */   public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type)
/*  24:    */   {
/*  25: 20 */     return true;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper)
/*  29:    */   {
/*  30: 25 */     return (helper == IItemRenderer.ItemRendererHelper.ENTITY_ROTATION) || (helper == IItemRenderer.ItemRendererHelper.ENTITY_BOBBING);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data)
/*  34:    */   {
/*  35: 31 */     if (!(item.getItem() instanceof IItemMultiTransparency)) {
/*  36: 32 */       return;
/*  37:    */     }
/*  38: 35 */     IItemMultiTransparency itemTrans = (IItemMultiTransparency)item.getItem();
/*  39: 38 */     if (type == IItemRenderer.ItemRenderType.ENTITY)
/*  40:    */     {
/*  41: 39 */       GL11.glTranslatef(-0.5F, -0.25F, 0.0F);
/*  42: 40 */       GL11.glDisable(2884);
/*  43: 41 */       GL11.glTranslatef(1.0F, 0.0F, 0.0F);
/*  44: 42 */       GL11.glScalef(-1.0F, 1.0F, 1.0F);
/*  45:    */     }
/*  46: 45 */     if (type == IItemRenderer.ItemRenderType.INVENTORY) {
/*  47: 46 */       GL11.glScalef(16.0F, 16.0F, 1.0F);
/*  48:    */     }
/*  49: 52 */     Tessellator tessellator = Tessellator.instance;
/*  50: 53 */     float t = 0.0F;
/*  51: 55 */     if (item.hasTagCompound())
/*  52:    */     {
/*  53: 56 */       NBTTagCompound tg = item.getTagCompound();
/*  54: 58 */       if ((tg.hasKey("time")) && (tg.hasKey("dimension")))
/*  55:    */       {
/*  56: 59 */         t = (float)(Minecraft.getMinecraft().thePlayer.worldObj.getTotalWorldTime() - tg.getLong("time")) / 200.0F;
/*  57: 61 */         if (t > 1.0F) {
/*  58: 62 */           t = 1.0F;
/*  59:    */         }
/*  60:    */       }
/*  61:    */     }
/*  62: 67 */     float r = 1.0F;float g = 1.0F;float b = 1.0F;
/*  63: 69 */     if ((t > 0.85D) && (Minecraft.getSystemTime() % 200L < 100L))
/*  64:    */     {
/*  65: 70 */       r = 1.0F;
/*  66: 71 */       g = 1.0F - t * 0.7F / 3.0F;
/*  67: 72 */       b = 0.0F;
/*  68:    */     }
/*  69:    */     else
/*  70:    */     {
/*  71: 74 */       r = 1.0F;
/*  72: 75 */       g = 1.0F - t * 0.7F;
/*  73: 76 */       b = 1.0F - t;
/*  74:    */     }
/*  75: 79 */     GL11.glColor3f(r, g, b);
/*  76: 81 */     for (int i = 0; i < itemTrans.numIcons(item); i++)
/*  77:    */     {
/*  78: 82 */       IIcon icon = itemTrans.getIconForTransparentRender(item, i);
/*  79: 83 */       float trans = itemTrans.getIconTransparency(item, i);
/*  80:    */       
/*  81: 85 */       OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/*  82: 86 */       GL11.glEnable(3008);
/*  83: 88 */       if (trans < 1.0F)
/*  84:    */       {
/*  85: 89 */         GL11.glBlendFunc(770, 771);
/*  86: 90 */         GL11.glEnable(3042);
/*  87: 91 */         GL11.glDisable(3008);
/*  88:    */         
/*  89: 93 */         GL11.glShadeModel(7425);
/*  90: 94 */         GL11.glColor4f(r, g, b, trans);
/*  91:    */       }
/*  92:    */       else
/*  93:    */       {
/*  94: 96 */         GL11.glColor4f(r, g, b, 1.0F);
/*  95:    */       }
/*  96: 99 */       if (type != IItemRenderer.ItemRenderType.INVENTORY)
/*  97:    */       {
/*  98:100 */         GL11.glEnable(32826);
/*  99:101 */         ItemRenderer.renderItemIn2D(tessellator, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
/* 100:102 */         GL11.glDisable(32826);
/* 101:    */       }
/* 102:    */       else
/* 103:    */       {
/* 104:104 */         tessellator.startDrawingQuads();
/* 105:105 */         tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, icon.getMinU(), icon.getMinV());
/* 106:106 */         tessellator.addVertexWithUV(0.0D, 1.0D, 0.0D, icon.getMinU(), icon.getMaxV());
/* 107:107 */         tessellator.addVertexWithUV(1.0D, 1.0D, 0.0D, icon.getMaxU(), icon.getMaxV());
/* 108:108 */         tessellator.addVertexWithUV(1.0D, 0.0D, 0.0D, icon.getMaxU(), icon.getMinV());
/* 109:109 */         tessellator.draw();
/* 110:    */       }
/* 111:112 */       if (trans < 1.0F)
/* 112:    */       {
/* 113:113 */         GL11.glShadeModel(7424);
/* 114:114 */         GL11.glEnable(3008);
/* 115:115 */         GL11.glDisable(3042);
/* 116:    */       }
/* 117:    */     }
/* 118:119 */     if (type == IItemRenderer.ItemRenderType.INVENTORY) {
/* 119:120 */       GL11.glScalef(0.0625F, 0.0625F, 1.0F);
/* 120:    */     }
/* 121:126 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 122:128 */     if (type == IItemRenderer.ItemRenderType.ENTITY)
/* 123:    */     {
/* 124:129 */       GL11.glScalef(-1.0F, 1.0F, 1.0F);
/* 125:130 */       GL11.glTranslatef(1.0F, 0.0F, 0.0F);
/* 126:131 */       GL11.glTranslatef(0.5F, 0.25F, 0.0F);
/* 127:132 */       GL11.glEnable(2884);
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   private int f(float t, long a, int k)
/* 132:    */   {
/* 133:137 */     int b = (int)((2.0D + Math.cos((float)(Minecraft.getSystemTime() % a) / (float)a * 2.0F * 3.141592653589793D) / 3.0D) * (t * t) * k);
/* 134:139 */     if (b < 0) {
/* 135:140 */       return 0;
/* 136:    */     }
/* 137:143 */     if (b > 8) {
/* 138:144 */       return 8;
/* 139:    */     }
/* 140:147 */     return 0;
/* 141:    */   }
/* 142:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.RenderItemUnstable
 * JD-Core Version:    0.7.0.1
 */