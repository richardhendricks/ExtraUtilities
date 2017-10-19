/*   1:    */ package com.rwtema.extrautils.block;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtilsMod;
/*   4:    */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import net.minecraft.block.Block;
/*   8:    */ import net.minecraftforge.common.util.ForgeDirection;
/*   9:    */ 
/*  10:    */ public class BoxModel
/*  11:    */   extends ArrayList<Box>
/*  12:    */ {
/*  13: 11 */   public int invModelRotate = 90;
/*  14: 12 */   public String label = "";
/*  15:    */   
/*  16:    */   public BoxModel() {}
/*  17:    */   
/*  18:    */   public BoxModel(Box newBox)
/*  19:    */   {
/*  20: 19 */     add(newBox);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public BoxModel(float par1, float par3, float par5, float par7, float par9, float par11)
/*  24:    */   {
/*  25: 24 */     add(new Box(par1, par3, par5, par7, par9, par11));
/*  26:    */   }
/*  27:    */   
/*  28:    */   public static BoxModel newStandardBlock()
/*  29:    */   {
/*  30: 28 */     Box t = new Box(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  31: 29 */     t.renderAsNormalBlock = true;
/*  32: 30 */     return new BoxModel(t);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static BoxModel hollowBox(float minX, float minY, float minZ, float holeMinX, float holeMinZ, float holeMaxX, float holeMaxZ, float maxX, float maxY, float maxZ)
/*  36:    */   {
/*  37: 34 */     BoxModel t = new BoxModel();
/*  38: 35 */     t.add(new Box(minX, minY, minZ, holeMinX, maxY, maxZ));
/*  39: 36 */     t.add(new Box(holeMinX, minY, minZ, holeMaxX, maxY, holeMinZ));
/*  40: 37 */     t.add(new Box(holeMinX, minY, holeMaxZ, holeMaxX, maxY, maxZ));
/*  41: 38 */     t.add(new Box(holeMaxX, minY, minZ, maxX, maxY, maxZ));
/*  42: 39 */     return t;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public static Box boundingBox(List models)
/*  46:    */   {
/*  47: 43 */     if (models == null) {
/*  48: 44 */       return null;
/*  49:    */     }
/*  50: 47 */     if (models.size() == 0) {
/*  51: 48 */       return null;
/*  52:    */     }
/*  53: 51 */     Box bounds = ((Box)models.get(0)).copy();
/*  54: 53 */     for (int i = 1; i < models.size(); i++) {
/*  55: 54 */       bounds.setBounds(Math.min(bounds.minX, ((Box)models.get(i)).minX), Math.min(bounds.minY, ((Box)models.get(i)).minY), Math.min(bounds.minZ, ((Box)models.get(i)).minZ), Math.max(bounds.maxX, ((Box)models.get(i)).maxX), Math.max(bounds.maxY, ((Box)models.get(i)).maxY), Math.max(bounds.maxZ, ((Box)models.get(i)).maxZ));
/*  56:    */     }
/*  57: 58 */     return bounds;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Box addBoxI(int par1, int par3, int par5, int par7, int par9, int par11)
/*  61:    */   {
/*  62: 62 */     return addBox("", par1 / 16.0F, par3 / 16.0F, par5 / 16.0F, par7 / 16.0F, par9 / 16.0F, par11 / 16.0F);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Box addBox(float par1, float par3, float par5, float par7, float par9, float par11)
/*  66:    */   {
/*  67: 66 */     return addBox("", par1, par3, par5, par7, par9, par11);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public Box addBox(String l, float par1, float par3, float par5, float par7, float par9, float par11)
/*  71:    */   {
/*  72: 70 */     Box b = new Box(l, par1, par3, par5, par7, par9, par11);
/*  73: 71 */     add(b);
/*  74: 72 */     return b;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public BoxModel rotateToSide(ForgeDirection dir)
/*  78:    */   {
/*  79: 76 */     for (Box box : this) {
/*  80: 77 */       box.rotateToSide(dir);
/*  81:    */     }
/*  82: 80 */     return this;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public BoxModel rotateY(int numRotations)
/*  86:    */   {
/*  87: 84 */     for (Box box : this) {
/*  88: 85 */       box.rotateY(numRotations);
/*  89:    */     }
/*  90: 88 */     return this;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public BoxModel offset(float x, float y, float z)
/*  94:    */   {
/*  95: 92 */     for (Box box : this) {
/*  96: 93 */       box.offset(x, y, z);
/*  97:    */     }
/*  98: 96 */     return this;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public BoxModel setColor(int col)
/* 102:    */   {
/* 103:100 */     for (Box box : this) {
/* 104:101 */       box.setColor(col);
/* 105:    */     }
/* 106:104 */     return this;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public BoxModel addYRotations()
/* 110:    */   {
/* 111:108 */     addAll(copy().rotateY(1));
/* 112:109 */     addAll(copy().rotateY(2));
/* 113:110 */     return this;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public Box boundingBox()
/* 117:    */   {
/* 118:114 */     return boundingBox(this);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public BoxModel copy()
/* 122:    */   {
/* 123:118 */     BoxModel newModel = new BoxModel();
/* 124:120 */     for (int i = 0; i < size(); i++) {
/* 125:121 */       newModel.add(((Box)get(i)).copy());
/* 126:    */     }
/* 127:124 */     return newModel;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public BoxModel fillIcons(Block block, int meta)
/* 131:    */   {
/* 132:128 */     if (ExtraUtilsMod.proxy.isClientSideAvailable()) {
/* 133:129 */       for (Box b : this) {
/* 134:130 */         b.fillIcons(block, meta);
/* 135:    */       }
/* 136:    */     }
/* 137:132 */     return this;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public BoxModel rotateToSideTex(ForgeDirection dir)
/* 141:    */   {
/* 142:136 */     for (Box b : this) {
/* 143:137 */       b.rotateToSideTex(dir);
/* 144:    */     }
/* 145:138 */     return this;
/* 146:    */   }
/* 147:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BoxModel
 * JD-Core Version:    0.7.0.1
 */