/*   1:    */ package com.rwtema.extrautils.item;
/*   2:    */ 
/*   3:    */ import cpw.mods.fml.relauncher.Side;
/*   4:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   5:    */ import net.minecraft.client.Minecraft;
/*   6:    */ import net.minecraft.client.renderer.RenderBlocks;
/*   7:    */ import net.minecraft.client.renderer.Tessellator;
/*   8:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*   9:    */ import net.minecraft.item.ItemStack;
/*  10:    */ import net.minecraft.util.ResourceLocation;
/*  11:    */ import net.minecraftforge.client.IItemRenderer;
/*  12:    */ import net.minecraftforge.client.IItemRenderer.ItemRenderType;
/*  13:    */ import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
/*  14:    */ import org.lwjgl.opengl.GL11;
/*  15:    */ 
/*  16:    */ @SideOnly(Side.CLIENT)
/*  17:    */ public class RenderItemLawSword
/*  18:    */   implements IItemRenderer
/*  19:    */ {
/*  20:    */   public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type)
/*  21:    */   {
/*  22: 18 */     return true;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper)
/*  26:    */   {
/*  27: 23 */     return true;
/*  28:    */   }
/*  29:    */   
/*  30: 27 */   public static final ResourceLocation temaBlade = new ResourceLocation("extrautils", "textures/rwtemaBlade.png");
/*  31:    */   
/*  32:    */   public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data)
/*  33:    */   {
/*  34: 31 */     double offset = -0.5D;
/*  35: 33 */     if (!(item.getItem() instanceof ItemLawSword)) {
/*  36: 34 */       return;
/*  37:    */     }
/*  38: 36 */     boolean firstPerson = (type == IItemRenderer.ItemRenderType.EQUIPPED) || (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON);
/*  39:    */     
/*  40:    */ 
/*  41: 39 */     GL11.glPushMatrix();
/*  42: 41 */     if ((type == IItemRenderer.ItemRenderType.EQUIPPED) || (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON)) {
/*  43: 42 */       offset = 0.0D;
/*  44: 43 */     } else if (type == IItemRenderer.ItemRenderType.ENTITY) {
/*  45: 44 */       GL11.glScalef(0.5F, 0.5F, 0.5F);
/*  46:    */     }
/*  47: 47 */     GL11.glTranslated(offset, offset, offset);
/*  48: 49 */     if (type == IItemRenderer.ItemRenderType.EQUIPPED)
/*  49:    */     {
/*  50: 50 */       GL11.glTranslated(0.5D, 0.5D, 0.5D);
/*  51: 51 */       GL11.glRotated(50.0D, -1.0D, 0.0D, 1.0D);
/*  52: 52 */       GL11.glTranslated(-0.5D, -0.5D, -0.5D);
/*  53:    */     }
/*  54: 56 */     GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/*  55:    */     
/*  56: 58 */     RenderBlocks renderer = (RenderBlocks)data[0];
/*  57:    */     
/*  58: 60 */     renderer.overrideBlockBounds(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D);
/*  59: 61 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  60:    */     
/*  61: 63 */     GL11.glDisable(2884);
/*  62:    */     
/*  63:    */ 
/*  64: 66 */     Minecraft.getMinecraft().renderEngine.bindTexture(temaBlade);
/*  65:    */     
/*  66: 68 */     Tessellator t = Tessellator.instance;
/*  67:    */     
/*  68: 70 */     float h = 87.0F;
/*  69: 71 */     float h2 = 20.0F;
/*  70: 72 */     float w = 18.0F;
/*  71: 73 */     float w2 = 5.0F;
/*  72: 74 */     float w3 = 13.0F;
/*  73: 75 */     double u = w2 / w;
/*  74: 76 */     float h3 = h2 / h;
/*  75:    */     
/*  76:    */ 
/*  77: 79 */     GL11.glScalef(1.7F / h, 1.7F / h, 1.7F / h);
/*  78: 80 */     if (firstPerson)
/*  79:    */     {
/*  80: 81 */       if (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
/*  81: 82 */         GL11.glRotated(220.0D, 0.0D, 1.0D, 0.0D);
/*  82:    */       } else {
/*  83: 84 */         GL11.glRotated(-50.0D, 0.0D, 1.0D, 0.0D);
/*  84:    */       }
/*  85: 85 */       GL11.glScalef(2.7F, 2.7F, 2.7F);
/*  86: 86 */       GL11.glTranslatef(0.0F, h * 0.3F, 0.0F);
/*  87:    */     }
/*  88: 88 */     GL11.glTranslatef(-w2 / 2.0F, -h / 2.0F, 0.0F);
/*  89:    */     
/*  90:    */ 
/*  91: 91 */     GL11.glPushMatrix();
/*  92:    */     
/*  93: 93 */     t.startDrawingQuads();
/*  94:    */     
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98: 98 */     t.setNormal(0.0F, 0.0F, -1.0F);
/*  99: 99 */     t.addVertexWithUV(0.0D, h2, w2 / 2.0F, 0.0D, h3);
/* 100:100 */     t.addVertexWithUV(0.0D, h, w2 / 2.0F, 0.0D, 1.0D);
/* 101:101 */     t.addVertexWithUV(w2, h, w2 / 2.0F, u, 1.0D);
/* 102:102 */     t.addVertexWithUV(w2, h2, w2 / 2.0F, u, h3);
/* 103:    */     
/* 104:    */ 
/* 105:105 */     t.setNormal(0.0F, 0.0F, -1.0F);
/* 106:106 */     t.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/* 107:107 */     t.addVertexWithUV(0.0D, h2, 0.0D, 0.0D, h3);
/* 108:108 */     t.addVertexWithUV(w2, h2, 0.0D, u, h3);
/* 109:109 */     t.addVertexWithUV(w2, 0.0D, 0.0D, u, 0.0D);
/* 110:    */     
/* 111:111 */     t.setNormal(0.0F, 0.0F, 1.0F);
/* 112:112 */     t.addVertexWithUV(w2, 0.0D, w2, u, 0.0D);
/* 113:113 */     t.addVertexWithUV(w2, h2, w2, u, h3);
/* 114:114 */     t.addVertexWithUV(0.0D, h2, w2, 0.0D, h3);
/* 115:115 */     t.addVertexWithUV(0.0D, 0.0D, w2, 0.0D, 0.0D);
/* 116:    */     
/* 117:117 */     t.setNormal(1.0F, 0.0F, 0.0F);
/* 118:118 */     t.addVertexWithUV(w2, 0.0D, w2, u, 0.0D);
/* 119:119 */     t.addVertexWithUV(w2, h2, w2, u, h3);
/* 120:120 */     t.addVertexWithUV(w2, h2, 0.0D, 0.0D, h3);
/* 121:121 */     t.addVertexWithUV(w2, 0.0D, 0.0D, 0.0D, 0.0D);
/* 122:    */     
/* 123:123 */     t.setNormal(-1.0F, 0.0F, 0.0F);
/* 124:124 */     t.addVertexWithUV(0.0D, 0.0D, 0.0D, u, 0.0D);
/* 125:125 */     t.addVertexWithUV(0.0D, h2, 0.0D, u, h3);
/* 126:126 */     t.addVertexWithUV(0.0D, h2, w2, 0.0D, h3);
/* 127:127 */     t.addVertexWithUV(0.0D, 0.0D, w2, 0.0D, 0.0D);
/* 128:    */     
/* 129:    */ 
/* 130:130 */     t.setNormal(0.0F, -1.0F, 0.0F);
/* 131:131 */     t.addVertexWithUV(0.0D, 0.0D, 0.0D, 9.0F / w, 4.0F / h);
/* 132:132 */     t.addVertexWithUV(w2, 0.0D, 0.0D, 13.0F / w, 8.0F / h);
/* 133:133 */     t.addVertexWithUV(w2, 0.0D, w2, 13.0F / w, 8.0F / h);
/* 134:134 */     t.addVertexWithUV(0.0D, 0.0D, w2, 9.0F / w, 4.0F / h);
/* 135:    */     
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:    */ 
/* 143:143 */     t.setNormal(0.0F, -1.0F, 0.0F);
/* 144:144 */     t.addVertexWithUV(-3.0D, 16.0D, -3.0D, 6.0F / w, 18.0F / h);
/* 145:145 */     t.addVertexWithUV(8.0D, 16.0D, -3.0D, 17.0F / w, 18.0F / h);
/* 146:146 */     t.addVertexWithUV(8.0D, 16.0D, 8.0D, 17.0F / w, 29.0F / h);
/* 147:147 */     t.addVertexWithUV(-3.0D, 16.0D, 8.0D, 6.0F / w, 29.0F / h);
/* 148:    */     
/* 149:149 */     t.setNormal(0.0F, 1.0F, 0.0F);
/* 150:150 */     t.addVertexWithUV(-3.0D, 20.0D, -3.0D, 6.0F / w, 1.0F / h);
/* 151:151 */     t.addVertexWithUV(8.0D, 20.0D, -3.0D, 17.0F / w, 1.0F / h);
/* 152:152 */     t.addVertexWithUV(8.0D, 20.0D, 8.0D, 17.0F / w, 12.0F / h);
/* 153:153 */     t.addVertexWithUV(-3.0D, 20.0D, 8.0D, 6.0F / w, 12.0F / h);
/* 154:    */     
/* 155:    */ 
/* 156:156 */     t.setNormal(0.0F, 0.0F, -1.0F);
/* 157:157 */     t.addVertexWithUV(-3.0D, 16.0D, -3.0D, u, 12.0F / h);
/* 158:158 */     t.addVertexWithUV(-3.0D, 20.0D, -3.0D, u, 17.0F / h);
/* 159:159 */     t.addVertexWithUV(8.0D, 20.0D, -3.0D, 1.0D, 17.0F / h);
/* 160:160 */     t.addVertexWithUV(8.0D, 16.0D, -3.0D, 1.0D, 12.0F / h);
/* 161:    */     
/* 162:162 */     t.setNormal(0.0F, 0.0F, 1.0F);
/* 163:163 */     t.addVertexWithUV(-3.0D, 16.0D, 8.0D, u, 12.0F / h);
/* 164:164 */     t.addVertexWithUV(-3.0D, 20.0D, 8.0D, u, 17.0F / h);
/* 165:165 */     t.addVertexWithUV(8.0D, 20.0D, 8.0D, 1.0D, 17.0F / h);
/* 166:166 */     t.addVertexWithUV(8.0D, 16.0D, 8.0D, 1.0D, 12.0F / h);
/* 167:    */     
/* 168:168 */     t.setNormal(1.0F, 0.0F, 0.0F);
/* 169:169 */     t.addVertexWithUV(8.0D, 16.0D, 8.0D, u, 12.0F / h);
/* 170:170 */     t.addVertexWithUV(8.0D, 20.0D, 8.0D, u, 17.0F / h);
/* 171:171 */     t.addVertexWithUV(8.0D, 20.0D, -3.0D, 1.0D, 17.0F / h);
/* 172:172 */     t.addVertexWithUV(8.0D, 16.0D, -3.0D, 1.0D, 12.0F / h);
/* 173:    */     
/* 174:174 */     t.setNormal(-1.0F, 0.0F, 0.0F);
/* 175:175 */     t.addVertexWithUV(-3.0D, 16.0D, 8.0D, u, 12.0F / h);
/* 176:176 */     t.addVertexWithUV(-3.0D, 20.0D, 8.0D, u, 17.0F / h);
/* 177:177 */     t.addVertexWithUV(-3.0D, 20.0D, -3.0D, 1.0D, 17.0F / h);
/* 178:178 */     t.addVertexWithUV(-3.0D, 16.0D, -3.0D, 1.0D, 12.0F / h);
/* 179:179 */     t.draw();
/* 180:    */     
/* 181:181 */     GL11.glPopMatrix();
/* 182:    */     
/* 183:    */ 
/* 184:184 */     renderer.unlockBlockBounds();
/* 185:185 */     renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
/* 186:    */     
/* 187:187 */     GL11.glPopMatrix();
/* 188:    */   }
/* 189:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.RenderItemLawSword
 * JD-Core Version:    0.7.0.1
 */