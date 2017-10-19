/*   1:    */ package com.rwtema.extrautils.multipart.microblock;
/*   2:    */ 
/*   3:    */ import codechicken.lib.lighting.LC;
/*   4:    */ import codechicken.lib.render.BlockRenderer.BlockFace;
/*   5:    */ import codechicken.lib.render.CCRenderState;
/*   6:    */ import codechicken.lib.render.Vertex5;
/*   7:    */ import codechicken.lib.render.uv.UV;
/*   8:    */ import codechicken.lib.vec.BlockCoord;
/*   9:    */ import codechicken.lib.vec.Cuboid6;
/*  10:    */ import codechicken.lib.vec.Vector3;
/*  11:    */ import codechicken.microblock.MicroMaterialRegistry.IMicroMaterial;
/*  12:    */ import codechicken.multipart.TMultiPart;
/*  13:    */ import com.rwtema.extrautils.LogHelper;
/*  14:    */ import cpw.mods.fml.relauncher.Side;
/*  15:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.Arrays;
/*  18:    */ import net.minecraft.entity.player.EntityPlayer;
/*  19:    */ import net.minecraft.item.ItemStack;
/*  20:    */ import net.minecraft.world.World;
/*  21:    */ 
/*  22:    */ public class PartSphere
/*  23:    */   extends PartMicroBlock
/*  24:    */ {
/*  25: 22 */   final double r = 0.375D;
/*  26:    */   public static Cuboid6 bounds;
/*  27:    */   
/*  28:    */   public PartSphere(int materialID)
/*  29:    */   {
/*  30: 28 */     super(materialID);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public PartSphere() {}
/*  34:    */   
/*  35:    */   public Cuboid6 getBounds()
/*  36:    */   {
/*  37: 36 */     return new Cuboid6(0.125D, 0.125D, 0.125D, 0.875D, 0.875D, 0.875D);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String getType()
/*  41:    */   {
/*  42: 41 */     return "extrautils:sphere";
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Iterable<Cuboid6> getCollisionBoxes()
/*  46:    */   {
/*  47: 46 */     if (isEthereal()) {
/*  48: 46 */       return new ArrayList();
/*  49:    */     }
/*  50: 47 */     return Arrays.asList(new Cuboid6[] { getBounds() });
/*  51:    */   }
/*  52:    */   
/*  53:    */   public boolean hideCreativeTab()
/*  54:    */   {
/*  55: 52 */     return false;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void reloadShape() {}
/*  59:    */   
/*  60:    */   @SideOnly(Side.CLIENT)
/*  61:    */   public void render(Vector3 pos, int pass)
/*  62:    */   {
/*  63: 63 */     renderSphere(pos.copy().add(0.5D), pass, this.mat);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getMetadata()
/*  67:    */   {
/*  68: 68 */     return 3;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public TMultiPart newPart(boolean client)
/*  72:    */   {
/*  73: 73 */     return new PartSphere();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public TMultiPart placePart(ItemStack stack, EntityPlayer player, World world, BlockCoord pos, int side, Vector3 arg5, int materialID)
/*  77:    */   {
/*  78: 78 */     return new PartSphere(materialID);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void registerPassThroughs() {}
/*  82:    */   
/*  83:    */   @SideOnly(Side.CLIENT)
/*  84:    */   public void renderItem(ItemStack item, MicroMaterialRegistry.IMicroMaterial material)
/*  85:    */   {
/*  86: 89 */     Vector3 pos = new Vector3(0.5D, 0.5D, 0.5D);
/*  87: 90 */     if (faces_inv == null)
/*  88:    */     {
/*  89: 91 */       faces_inv = new ArrayList();
/*  90: 92 */       calcSphere(0.5D, 0.5D, faces_inv);
/*  91: 93 */       LogHelper.debug("Calculated faces " + faces_inv.size(), new Object[0]);
/*  92:    */     }
/*  93: 96 */     CCRenderState.setModel(this.face);
/*  94: 98 */     for (Vertex5[] f : faces_inv)
/*  95:    */     {
/*  96: 99 */       this.face.lcComputed = false;
/*  97:100 */       this.face.verts[0].set(f[0]);
/*  98:101 */       this.face.verts[1].set(f[1]);
/*  99:102 */       this.face.verts[2].set(f[2]);
/* 100:103 */       this.face.verts[3].set(f[3]);
/* 101:104 */       this.face.side = f[0].uv.tex;
/* 102:    */       
/* 103:106 */       material.renderMicroFace(pos, -1, getBounds());
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:111 */   public static final Vector3 DOWN = new Vector3(0.0D, -1.0D, 0.0D);
/* 108:112 */   public static final Vector3 UP = new Vector3(0.0D, 1.0D, 0.0D);
/* 109:113 */   public static final Vector3 NORTH = new Vector3(0.0D, 0.0D, -1.0D);
/* 110:114 */   public static final Vector3 SOUTH = new Vector3(0.0D, 0.0D, 1.0D);
/* 111:115 */   public static final Vector3 EAST = new Vector3(-1.0D, 0.0D, 0.0D);
/* 112:116 */   public static final Vector3 WEST = new Vector3(1.0D, 0.0D, 0.0D);
/* 113:119 */   public BlockFaceUniformLighting face = new BlockFaceUniformLighting();
/* 114:    */   
/* 115:    */   public class BlockFaceUniformLighting
/* 116:    */     extends BlockRenderer.BlockFace
/* 117:    */   {
/* 118:    */     public BlockFaceUniformLighting() {}
/* 119:    */     
/* 120:    */     public void prepareVertex()
/* 121:    */     {
/* 122:124 */       CCRenderState.side = 1;
/* 123:    */     }
/* 124:    */     
/* 125:    */     public BlockRenderer.BlockFace computeLightCoords()
/* 126:    */     {
/* 127:129 */       if (!this.lcComputed)
/* 128:    */       {
/* 129:130 */         for (int i = 0; i < 4; i++) {
/* 130:131 */           this.lightCoords[i].set(0, 1.0F, 0.0F, 0.0F, 0.0F);
/* 131:    */         }
/* 132:132 */         this.lcComputed = true;
/* 133:    */       }
/* 134:135 */       return this;
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:140 */   public static ArrayList<Vertex5[]> faces = null;
/* 139:140 */   public static ArrayList<Vertex5[]> faces_inv = null;
/* 140:    */   
/* 141:    */   @SideOnly(Side.CLIENT)
/* 142:    */   public void renderSphere(Vector3 pos, int pass, MicroMaterialRegistry.IMicroMaterial m)
/* 143:    */   {
/* 144:144 */     if (faces == null)
/* 145:    */     {
/* 146:145 */       faces = new ArrayList();
/* 147:146 */       calcSphere(0.25D, 0.25D, faces);
/* 148:147 */       LogHelper.debug("Calculated " + faces.size(), new Object[0]);
/* 149:    */     }
/* 150:150 */     CCRenderState.setModel(this.face);
/* 151:152 */     for (Vertex5[] f : faces)
/* 152:    */     {
/* 153:153 */       this.face.verts[0].set(f[0]);
/* 154:154 */       this.face.verts[1].set(f[1]);
/* 155:155 */       this.face.verts[2].set(f[2]);
/* 156:156 */       this.face.verts[3].set(f[3]);
/* 157:157 */       this.face.side = f[0].uv.tex;
/* 158:    */       
/* 159:159 */       m.renderMicroFace(pos, pass, getBounds());
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void calcSphere(double d, double d2, ArrayList<Vertex5[]> faces)
/* 164:    */   {
/* 165:165 */     renderCurvedSide2(DOWN, NORTH, WEST, 0, d, d2, faces);
/* 166:166 */     renderCurvedSide2(UP, NORTH, EAST, 1, d, d2, faces);
/* 167:167 */     renderCurvedSide(NORTH, EAST, UP, 2, d, d2, faces);
/* 168:168 */     renderCurvedSide(SOUTH, WEST, UP, 3, d, d2, faces);
/* 169:169 */     renderCurvedSide(WEST, NORTH, UP, 4, d, d2, faces);
/* 170:170 */     renderCurvedSide(EAST, SOUTH, UP, 5, d, d2, faces);
/* 171:    */   }
/* 172:    */   
/* 173:    */   public int getLightValue()
/* 174:    */   {
/* 175:175 */     return 15;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void renderCurvedSide(Vector3 forward, Vector3 left, Vector3 up, int side, double d, double d2, ArrayList<Vertex5[]> faces)
/* 179:    */   {
/* 180:179 */     for (double u = 0.0D; u < 1.0D; u += d2) {
/* 181:180 */       for (double v = 0.0D; v < 1.0D; v += d)
/* 182:    */       {
/* 183:181 */         Vertex5[] verts = { new Vertex5(), new Vertex5(), new Vertex5(), new Vertex5() };
/* 184:    */         
/* 185:183 */         calcVec1(verts[0], forward, left, up, u + d2, v, side);
/* 186:184 */         calcVec1(verts[1], forward, left, up, u + d2, v + d, side);
/* 187:185 */         calcVec1(verts[2], forward, left, up, u, v + d, side);
/* 188:186 */         calcVec1(verts[3], forward, left, up, u, v, side);
/* 189:    */         
/* 190:188 */         faces.add(verts);
/* 191:    */       }
/* 192:    */     }
/* 193:    */   }
/* 194:    */   
/* 195:194 */   double h = 1.35D;
/* 196:194 */   double h2 = Math.sqrt(1.0D - this.h * this.h / 4.0D);
/* 197:    */   
/* 198:    */   public void calcVec1(Vertex5 vert, Vector3 forward, Vector3 left, Vector3 up, double u, double v, int side)
/* 199:    */   {
/* 200:197 */     double a = u - 0.5D;
/* 201:    */     
/* 202:199 */     double dy = (v - 0.5D) * this.h;
/* 203:200 */     double dx = Math.sin(a * 3.141592653589793D / 2.0D) * Math.sqrt(1.0D - dy * dy);
/* 204:201 */     double dz = Math.sqrt(1.0D - dx * dx - dy * dy);
/* 205:    */     
/* 206:203 */     vert.vec.set(left.copy().multiply(dx).add(up.copy().multiply(dy)).add(forward.copy().multiply(dz)).multiply(0.375D));
/* 207:204 */     vert.uv.set(u, 1.0D - v, side);
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void renderCurvedSide2(Vector3 forward, Vector3 left, Vector3 up, int side, double d, double d2, ArrayList<Vertex5[]> faces)
/* 211:    */   {
/* 212:209 */     for (double t = 0.0D; t < 1.0D; t += d2 / 4.0D) {
/* 213:210 */       for (double dr = 0.0D; dr < 1.0D; dr += d * 2.0D)
/* 214:    */       {
/* 215:211 */         Vertex5[] verts = { new Vertex5(), new Vertex5(), new Vertex5(), new Vertex5() };
/* 216:    */         
/* 217:213 */         calcVec2(verts[0], forward, left, up, t, dr, side);
/* 218:214 */         calcVec2(verts[1], forward, left, up, t, dr + d * 2.0D, side);
/* 219:215 */         calcVec2(verts[2], forward, left, up, t + d2 / 4.0D, dr + d * 2.0D, side);
/* 220:216 */         calcVec2(verts[3], forward, left, up, t + d2 / 4.0D, dr, side);
/* 221:    */         
/* 222:218 */         faces.add(verts);
/* 223:    */       }
/* 224:    */     }
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void calcVec2(Vertex5 vert, Vector3 forward, Vector3 left, Vector3 up, double t, double dr, int side)
/* 228:    */   {
/* 229:224 */     double du = Math.cos(t * 3.141592653589793D * 2.0D) * dr;
/* 230:225 */     double dv = Math.sin(t * 3.141592653589793D * 2.0D) * dr;
/* 231:    */     
/* 232:227 */     double d = (du == 0.0D) || (dv == 0.0D) ? 0.0D : Math.min(Math.abs(du / dv), Math.abs(dv / du));
/* 233:228 */     d = Math.sqrt(1.0D + d * d);
/* 234:    */     
/* 235:230 */     double dx = du * this.h2;
/* 236:231 */     double dy = dv * this.h2;
/* 237:232 */     double dz = Math.sqrt(1.0D - dx * dx - dy * dy);
/* 238:    */     
/* 239:234 */     vert.vec.set(left.copy().multiply(dx).add(up.copy().multiply(dy)).add(forward.copy().multiply(dz)).multiply(0.375D));
/* 240:235 */     vert.uv.set((1.0D + d * du) / 2.0D, (1.0D + d * dv) / 2.0D, side);
/* 241:    */   }
/* 242:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.multipart.microblock.PartSphere
 * JD-Core Version:    0.7.0.1
 */