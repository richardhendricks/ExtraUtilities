/*   1:    */ package com.rwtema.extrautils.block.render;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*   4:    */ import com.rwtema.extrautils.block.Box;
/*   5:    */ import com.rwtema.extrautils.block.BoxModel;
/*   6:    */ import com.rwtema.extrautils.block.IMultiBoxBlock;
/*   7:    */ import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
/*   8:    */ import cpw.mods.fml.relauncher.Side;
/*   9:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  10:    */ import net.minecraft.block.Block;
/*  11:    */ import net.minecraft.client.renderer.RenderBlocks;
/*  12:    */ import net.minecraft.client.renderer.Tessellator;
/*  13:    */ import net.minecraft.util.IIcon;
/*  14:    */ import net.minecraft.world.IBlockAccess;
/*  15:    */ import org.lwjgl.opengl.GL11;
/*  16:    */ 
/*  17:    */ @SideOnly(Side.CLIENT)
/*  18:    */ public class RenderBlockMultiBlock
/*  19:    */   implements ISimpleBlockRenderingHandler
/*  20:    */ {
/*  21: 19 */   static boolean rendering = true;
/*  22:    */   
/*  23:    */   public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
/*  24:    */   {
/*  25: 23 */     if (!(block instanceof IMultiBoxBlock)) {
/*  26: 24 */       return;
/*  27:    */     }
/*  28: 27 */     BoxModel boxes = ((IMultiBoxBlock)block).getInventoryModel(metadata);
/*  29: 29 */     if (boxes == null) {
/*  30: 30 */       return;
/*  31:    */     }
/*  32: 33 */     if (boxes.size() == 0) {
/*  33: 34 */       return;
/*  34:    */     }
/*  35: 37 */     ((IMultiBoxBlock)block).prepareForRender(boxes.label);
/*  36: 38 */     Box union = boxes.boundingBox();
/*  37: 39 */     float dx = (union.maxX + union.minX) / 2.0F - 0.5F;
/*  38: 40 */     float dy = (union.maxY + union.minY) / 2.0F - 0.5F;
/*  39: 41 */     float dz = (union.maxZ + union.minZ) / 2.0F - 0.5F;
/*  40: 42 */     GL11.glTranslatef(-dx, -dy, -dz);
/*  41: 43 */     GL11.glRotatef(boxes.invModelRotate, 0.0F, 1.0F, 0.0F);
/*  42: 44 */     GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/*  43: 46 */     for (Box b : boxes)
/*  44:    */     {
/*  45: 47 */       block.setBlockBounds(b.minX, b.minY, b.minZ, b.maxX, b.maxY, b.maxZ);
/*  46: 48 */       ((IMultiBoxBlock)block).prepareForRender(b.label);
/*  47: 49 */       Tessellator tessellator = Tessellator.instance;
/*  48: 50 */       renderer.setRenderBoundsFromBlock(block);
/*  49: 51 */       GL11.glColor3f((b.color >> 16 & 0xFF) / 255.0F, (b.color >> 8 & 0xFF) / 255.0F, (b.color & 0xFF) / 255.0F);
/*  50: 52 */       renderer.uvRotateEast = b.uvRotateEast;
/*  51: 53 */       renderer.uvRotateWest = b.uvRotateWest;
/*  52: 54 */       renderer.uvRotateSouth = b.uvRotateSouth;
/*  53: 55 */       renderer.uvRotateNorth = b.uvRotateNorth;
/*  54: 56 */       renderer.uvRotateTop = b.uvRotateTop;
/*  55: 57 */       renderer.uvRotateBottom = b.uvRotateBottom;
/*  56: 59 */       if (b.invisibleSide[0] == 0)
/*  57:    */       {
/*  58: 60 */         tessellator.startDrawingQuads();
/*  59: 61 */         tessellator.setNormal(0.0F, -1.0F, 0.0F);
/*  60: 62 */         renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, getTexture(null, 0, 0, 0, renderer, b, block, 0, metadata));
/*  61: 63 */         tessellator.draw();
/*  62:    */       }
/*  63: 66 */       if (b.invisibleSide[1] == 0)
/*  64:    */       {
/*  65: 67 */         tessellator.startDrawingQuads();
/*  66: 68 */         tessellator.setNormal(0.0F, 1.0F, 0.0F);
/*  67: 69 */         renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, getTexture(null, 0, 0, 0, renderer, b, block, 1, metadata));
/*  68: 70 */         tessellator.draw();
/*  69:    */       }
/*  70: 74 */       if (b.invisibleSide[2] == 0)
/*  71:    */       {
/*  72: 75 */         tessellator.startDrawingQuads();
/*  73: 76 */         tessellator.setNormal(-1.0F, 0.0F, 0.0F);
/*  74: 77 */         renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, getTexture(null, 0, 0, 0, renderer, b, block, 2, metadata));
/*  75: 78 */         tessellator.draw();
/*  76:    */       }
/*  77: 81 */       if (b.invisibleSide[3] == 0)
/*  78:    */       {
/*  79: 82 */         tessellator.startDrawingQuads();
/*  80: 83 */         tessellator.setNormal(1.0F, 0.0F, 0.0F);
/*  81: 84 */         renderer.flipTexture = true;
/*  82: 85 */         renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, getTexture(null, 0, 0, 0, renderer, b, block, 3, metadata));
/*  83: 86 */         renderer.flipTexture = false;
/*  84: 87 */         tessellator.draw();
/*  85:    */       }
/*  86: 90 */       if (b.invisibleSide[4] == 0)
/*  87:    */       {
/*  88: 91 */         tessellator.startDrawingQuads();
/*  89: 92 */         tessellator.setNormal(0.0F, 0.0F, -1.0F);
/*  90: 93 */         renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, getTexture(null, 0, 0, 0, renderer, b, block, 4, metadata));
/*  91: 94 */         tessellator.draw();
/*  92:    */       }
/*  93: 97 */       if (b.invisibleSide[5] == 0)
/*  94:    */       {
/*  95: 98 */         tessellator.startDrawingQuads();
/*  96: 99 */         tessellator.setNormal(0.0F, 0.0F, 1.0F);
/*  97:100 */         renderer.flipTexture = true;
/*  98:101 */         renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, getTexture(null, 0, 0, 0, renderer, b, block, 5, metadata));
/*  99:102 */         renderer.flipTexture = false;
/* 100:103 */         tessellator.draw();
/* 101:    */       }
/* 102:106 */       renderer.uvRotateEast = 0;
/* 103:107 */       renderer.uvRotateWest = 0;
/* 104:108 */       renderer.uvRotateSouth = 0;
/* 105:109 */       renderer.uvRotateNorth = 0;
/* 106:110 */       renderer.uvRotateTop = 0;
/* 107:111 */       renderer.uvRotateBottom = 0;
/* 108:    */     }
/* 109:114 */     GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/* 110:115 */     GL11.glTranslatef(dx, dy, dz);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public IIcon getTexture(IBlockAccess world, int x, int y, int z, RenderBlocks renderer, Box box, Block block, int side, int metadata)
/* 114:    */   {
/* 115:119 */     if (box.textureSide[side] != null) {
/* 116:120 */       return box.textureSide[side];
/* 117:    */     }
/* 118:123 */     if (box.texture != null) {
/* 119:124 */       return box.texture;
/* 120:    */     }
/* 121:127 */     if (world == null) {
/* 122:128 */       return renderer.getBlockIconFromSideAndMetadata(block, side, metadata);
/* 123:    */     }
/* 124:130 */     return renderer.getBlockIcon(block, world, x, y, z, side);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
/* 128:    */   {
/* 129:136 */     int metadata = world.getBlockMetadata(x, y, z);
/* 130:138 */     if (!(block instanceof IMultiBoxBlock)) {
/* 131:139 */       return false;
/* 132:    */     }
/* 133:142 */     BoxModel boxes = ((IMultiBoxBlock)block).getWorldModel(world, x, y, z);
/* 134:144 */     if ((boxes == null) || (boxes.size() == 0)) {
/* 135:145 */       return false;
/* 136:    */     }
/* 137:148 */     ((IMultiBoxBlock)block).prepareForRender(boxes.label);
/* 138:150 */     for (Box b1 : boxes)
/* 139:    */     {
/* 140:152 */       float r = (b1.color >> 16 & 0xFF) / 255.0F;
/* 141:153 */       float g = (b1.color >> 8 & 0xFF) / 255.0F;
/* 142:154 */       float b = (b1.color & 0xFF) / 255.0F;
/* 143:155 */       ((IMultiBoxBlock)block).prepareForRender(b1.label);
/* 144:156 */       block.setBlockBounds(b1.minX, b1.minY, b1.minZ, b1.maxX, b1.maxY, b1.maxZ);
/* 145:157 */       renderer.uvRotateEast = b1.uvRotateEast;
/* 146:158 */       renderer.uvRotateWest = b1.uvRotateWest;
/* 147:159 */       renderer.uvRotateSouth = b1.uvRotateSouth;
/* 148:160 */       renderer.uvRotateNorth = b1.uvRotateNorth;
/* 149:161 */       renderer.uvRotateTop = b1.uvRotateTop;
/* 150:162 */       renderer.uvRotateBottom = b1.uvRotateBottom;
/* 151:163 */       renderer.setRenderBoundsFromBlock(block);
/* 152:165 */       if (b1.renderAsNormalBlock)
/* 153:    */       {
/* 154:166 */         renderer.renderStandardBlock(block, x + b1.offsetx, y + b1.offsety, z + b1.offsetz);
/* 155:    */       }
/* 156:    */       else
/* 157:    */       {
/* 158:168 */         renderer.enableAO = false;
/* 159:169 */         Tessellator tessellator = Tessellator.instance;
/* 160:170 */         tessellator.setBrightness(block.getBlockBrightness(world, x + b1.offsetx, y + b1.offsety, z + b1.offsetz));
/* 161:171 */         tessellator.setColorOpaque_F(r * 0.5F, g * 0.5F, b * 0.5F);
/* 162:    */         
/* 163:173 */         renderer.flipTexture = false;
/* 164:175 */         if (b1.invisibleSide[0] == 0) {
/* 165:176 */           renderer.renderFaceYNeg(block, x + b1.offsetx, y + b1.offsety, z + b1.offsetz, getTexture(world, x, y, z, renderer, b1, block, 0, metadata));
/* 166:    */         }
/* 167:179 */         tessellator.setColorOpaque_F(r, g, b);
/* 168:181 */         if (b1.invisibleSide[1] == 0) {
/* 169:182 */           renderer.renderFaceYPos(block, x + b1.offsetx, y + b1.offsety, z + b1.offsetz, getTexture(world, x, y, z, renderer, b1, block, 1, metadata));
/* 170:    */         }
/* 171:185 */         tessellator.setColorOpaque_F(r * 0.8F, g * 0.8F, b * 0.8F);
/* 172:    */         
/* 173:187 */         renderer.flipTexture = true;
/* 174:189 */         if (b1.invisibleSide[2] == 0) {
/* 175:190 */           renderer.renderFaceZNeg(block, x + b1.offsetx, y + b1.offsety, z + b1.offsetz, getTexture(world, x, y, z, renderer, b1, block, 2, metadata));
/* 176:    */         }
/* 177:192 */         renderer.flipTexture = false;
/* 178:195 */         if (b1.invisibleSide[3] == 0) {
/* 179:196 */           renderer.renderFaceZPos(block, x + b1.offsetx, y + b1.offsety, z + b1.offsetz, getTexture(world, x, y, z, renderer, b1, block, 3, metadata));
/* 180:    */         }
/* 181:198 */         tessellator.setColorOpaque_F(r * 0.6F, g * 0.6F, b * 0.6F);
/* 182:200 */         if (b1.invisibleSide[4] == 0) {
/* 183:201 */           renderer.renderFaceXNeg(block, x + b1.offsetx, y + b1.offsety, z + b1.offsetz, getTexture(world, x, y, z, renderer, b1, block, 4, metadata));
/* 184:    */         }
/* 185:204 */         renderer.flipTexture = true;
/* 186:205 */         if (b1.invisibleSide[5] == 0) {
/* 187:206 */           renderer.renderFaceXPos(block, x + b1.offsetx, y + b1.offsety, z + b1.offsetz, getTexture(world, x, y, z, renderer, b1, block, 5, metadata));
/* 188:    */         }
/* 189:207 */         renderer.flipTexture = false;
/* 190:    */       }
/* 191:210 */       renderer.uvRotateBottom = 0;
/* 192:211 */       renderer.uvRotateTop = 0;
/* 193:212 */       renderer.uvRotateSouth = 0;
/* 194:213 */       renderer.uvRotateNorth = 0;
/* 195:214 */       renderer.uvRotateWest = 0;
/* 196:215 */       renderer.uvRotateEast = 0;
/* 197:    */     }
/* 198:219 */     block.setBlockBoundsBasedOnState(world, x, y, z);
/* 199:220 */     return true;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public boolean shouldRender3DInInventory(int modelId)
/* 203:    */   {
/* 204:225 */     return true;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public int getRenderId()
/* 208:    */   {
/* 209:230 */     return ExtraUtilsProxy.multiBlockID;
/* 210:    */   }
/* 211:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.render.RenderBlockMultiBlock
 * JD-Core Version:    0.7.0.1
 */