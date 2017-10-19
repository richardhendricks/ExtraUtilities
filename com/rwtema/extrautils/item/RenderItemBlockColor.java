/*   1:    */ package com.rwtema.extrautils.item;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.block.BlockColorData;
/*   4:    */ import com.rwtema.extrautils.helper.XURandom;
/*   5:    */ import com.rwtema.extrautils.tileentity.TileEntityBlockColorData;
/*   6:    */ import cpw.mods.fml.relauncher.Side;
/*   7:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   8:    */ import java.util.Random;
/*   9:    */ import net.minecraft.block.Block;
/*  10:    */ import net.minecraft.client.Minecraft;
/*  11:    */ import net.minecraft.client.renderer.EntityRenderer;
/*  12:    */ import net.minecraft.client.renderer.OpenGlHelper;
/*  13:    */ import net.minecraft.client.renderer.RenderBlocks;
/*  14:    */ import net.minecraft.client.renderer.Tessellator;
/*  15:    */ import net.minecraft.entity.Entity;
/*  16:    */ import net.minecraft.item.ItemBlock;
/*  17:    */ import net.minecraft.item.ItemStack;
/*  18:    */ import net.minecraft.tileentity.TileEntity;
/*  19:    */ import net.minecraft.world.World;
/*  20:    */ import net.minecraftforge.client.IItemRenderer;
/*  21:    */ import net.minecraftforge.client.IItemRenderer.ItemRenderType;
/*  22:    */ import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
/*  23:    */ import org.lwjgl.opengl.GL11;
/*  24:    */ 
/*  25:    */ @SideOnly(Side.CLIENT)
/*  26:    */ public class RenderItemBlockColor
/*  27:    */   implements IItemRenderer
/*  28:    */ {
/*  29: 26 */   private Random rand = XURandom.getInstance();
/*  30:    */   
/*  31:    */   public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type)
/*  32:    */   {
/*  33: 30 */     switch (1.$SwitchMap$net$minecraftforge$client$IItemRenderer$ItemRenderType[type.ordinal()])
/*  34:    */     {
/*  35:    */     case 1: 
/*  36: 32 */       return true;
/*  37:    */     case 2: 
/*  38: 35 */       return true;
/*  39:    */     case 3: 
/*  40: 38 */       return true;
/*  41:    */     case 4: 
/*  42: 41 */       return true;
/*  43:    */     }
/*  44: 44 */     return false;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper)
/*  48:    */   {
/*  49: 50 */     return true;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data)
/*  53:    */   {
/*  54: 55 */     if ((item == null) || (!(item.getItem() instanceof ItemBlock))) {
/*  55: 56 */       return;
/*  56:    */     }
/*  57: 59 */     Block block = ((ItemBlock)item.getItem()).field_150939_a;
/*  58: 61 */     if ((block == null) || (data == null) || (data.length == 0)) {
/*  59: 62 */       return;
/*  60:    */     }
/*  61: 65 */     int metadata = item.getItemDamage();
/*  62: 67 */     if ((metadata < 0) || (metadata >= 16)) {
/*  63: 68 */       metadata = this.rand.nextInt(16);
/*  64:    */     }
/*  65: 71 */     RenderBlocks renderer = (RenderBlocks)data[0];
/*  66: 72 */     Entity holder = null;
/*  67: 74 */     if ((data.length > 1) && 
/*  68: 75 */       ((data[1] instanceof Entity))) {
/*  69: 76 */       holder = (Entity)data[1];
/*  70:    */     }
/*  71: 79 */     if (holder == null) {
/*  72: 80 */       holder = Minecraft.getMinecraft().thePlayer;
/*  73:    */     }
/*  74: 83 */     Tessellator var4 = Tessellator.instance;
/*  75: 84 */     block.setBlockBoundsForItemRender();
/*  76: 85 */     renderer.setRenderBoundsFromBlock(block);
/*  77: 86 */     GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
/*  78: 88 */     switch (1.$SwitchMap$net$minecraftforge$client$IItemRenderer$ItemRenderType[type.ordinal()])
/*  79:    */     {
/*  80:    */     case 2: 
/*  81:    */     case 3: 
/*  82: 91 */       GL11.glTranslatef(-1.0F, 0.5F, 0.0F);
/*  83: 92 */       break;
/*  84:    */     default: 
/*  85: 95 */       GL11.glTranslatef(-0.5F, -0.0F, -0.5F);
/*  86:    */     }
/*  87: 98 */     float f = com.rwtema.extrautils.block.BlockColor.initColor[metadata][0];
/*  88: 99 */     float f1 = com.rwtema.extrautils.block.BlockColor.initColor[metadata][1];
/*  89:100 */     float f2 = com.rwtema.extrautils.block.BlockColor.initColor[metadata][2];
/*  90:102 */     if ((holder != null) && 
/*  91:103 */       (holder.worldObj != null))
/*  92:    */     {
/*  93:104 */       TileEntity tiledata = holder.worldObj.getTileEntity(BlockColorData.dataBlockX((int)Math.floor(holder.posX)), BlockColorData.dataBlockY((int)holder.posY), BlockColorData.dataBlockZ((int)Math.floor(holder.posZ)));
/*  94:107 */       if ((tiledata instanceof TileEntityBlockColorData))
/*  95:    */       {
/*  96:108 */         f = ((TileEntityBlockColorData)tiledata).palette[metadata][0];
/*  97:109 */         f1 = ((TileEntityBlockColorData)tiledata).palette[metadata][1];
/*  98:110 */         f2 = ((TileEntityBlockColorData)tiledata).palette[metadata][2];
/*  99:    */       }
/* 100:    */     }
/* 101:115 */     if (EntityRenderer.anaglyphEnable)
/* 102:    */     {
/* 103:116 */       float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
/* 104:117 */       float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
/* 105:118 */       float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
/* 106:119 */       f = f3;
/* 107:120 */       f1 = f4;
/* 108:121 */       f2 = f5;
/* 109:    */     }
/* 110:123 */     OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 111:124 */     GL11.glEnable(3008);
/* 112:    */     
/* 113:126 */     GL11.glColor4f(f, f1, f2, 1.0F);
/* 114:127 */     renderer.colorRedTopLeft *= f;
/* 115:128 */     renderer.colorRedTopRight *= f;
/* 116:129 */     renderer.colorRedBottomLeft *= f;
/* 117:130 */     renderer.colorRedBottomRight *= f;
/* 118:131 */     renderer.colorGreenTopLeft *= f1;
/* 119:132 */     renderer.colorGreenTopRight *= f1;
/* 120:133 */     renderer.colorGreenBottomLeft *= f1;
/* 121:134 */     renderer.colorGreenBottomRight *= f1;
/* 122:135 */     renderer.colorBlueTopLeft *= f2;
/* 123:136 */     renderer.colorBlueTopRight *= f2;
/* 124:137 */     renderer.colorBlueBottomLeft *= f2;
/* 125:138 */     renderer.colorBlueBottomRight *= f2;
/* 126:139 */     var4.startDrawingQuads();
/* 127:140 */     var4.setNormal(0.0F, -1.0F, 0.0F);
/* 128:141 */     renderer.renderFaceYNeg(block, 0.0D, -0.5D, 0.0D, block.getIcon(0, metadata));
/* 129:142 */     var4.draw();
/* 130:143 */     var4.startDrawingQuads();
/* 131:144 */     var4.setNormal(0.0F, 1.0F, 0.0F);
/* 132:145 */     renderer.renderFaceYPos(block, 0.0D, -0.5D, 0.0D, block.getIcon(1, metadata));
/* 133:146 */     var4.draw();
/* 134:147 */     var4.startDrawingQuads();
/* 135:148 */     var4.setNormal(0.0F, 0.0F, -1.0F);
/* 136:149 */     renderer.renderFaceXPos(block, 0.0D, -0.5D, 0.0D, block.getIcon(2, metadata));
/* 137:150 */     var4.draw();
/* 138:151 */     var4.startDrawingQuads();
/* 139:152 */     var4.setNormal(0.0F, 0.0F, 1.0F);
/* 140:153 */     renderer.renderFaceXNeg(block, 0.0D, -0.5D, 0.0D, block.getIcon(3, metadata));
/* 141:154 */     var4.draw();
/* 142:155 */     var4.startDrawingQuads();
/* 143:156 */     var4.setNormal(-1.0F, 0.0F, 0.0F);
/* 144:157 */     renderer.renderFaceZNeg(block, 0.0D, -0.5D, 0.0D, block.getIcon(4, metadata));
/* 145:158 */     var4.draw();
/* 146:159 */     var4.startDrawingQuads();
/* 147:160 */     var4.setNormal(1.0F, 0.0F, 0.0F);
/* 148:161 */     renderer.renderFaceZPos(block, 0.0D, -0.5D, 0.0D, block.getIcon(5, metadata));
/* 149:162 */     var4.draw();
/* 150:163 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 151:164 */     GL11.glTranslatef(0.5F, 0.0F, 0.5F);
/* 152:    */   }
/* 153:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.RenderItemBlockColor
 * JD-Core Version:    0.7.0.1
 */