/*   1:    */ package com.rwtema.extrautils.multipart.microblock;
/*   2:    */ 
/*   3:    */ import codechicken.lib.data.MCDataInput;
/*   4:    */ import codechicken.lib.data.MCDataOutput;
/*   5:    */ import codechicken.lib.raytracer.IndexedCuboid6;
/*   6:    */ import codechicken.lib.render.IFaceRenderer;
/*   7:    */ import codechicken.lib.render.Vertex5;
/*   8:    */ import codechicken.lib.vec.Cuboid6;
/*   9:    */ import codechicken.lib.vec.Vector3;
/*  10:    */ import codechicken.microblock.IMicroMaterialRender;
/*  11:    */ import codechicken.microblock.MicroMaterialRegistry;
/*  12:    */ import codechicken.microblock.MicroMaterialRegistry.IMicroMaterial;
/*  13:    */ import codechicken.multipart.IconHitEffects;
/*  14:    */ import codechicken.multipart.JCuboidPart;
/*  15:    */ import codechicken.multipart.JIconHitEffects;
/*  16:    */ import codechicken.multipart.JNormalOcclusion;
/*  17:    */ import codechicken.multipart.NormalOcclusionTest;
/*  18:    */ import codechicken.multipart.TMultiPart;
/*  19:    */ import codechicken.multipart.TileMultipart;
/*  20:    */ import cpw.mods.fml.relauncher.Side;
/*  21:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  22:    */ import java.util.ArrayList;
/*  23:    */ import java.util.Arrays;
/*  24:    */ import net.minecraft.block.Block;
/*  25:    */ import net.minecraft.client.particle.EffectRenderer;
/*  26:    */ import net.minecraft.entity.player.EntityPlayer;
/*  27:    */ import net.minecraft.init.Blocks;
/*  28:    */ import net.minecraft.item.ItemStack;
/*  29:    */ import net.minecraft.nbt.NBTTagCompound;
/*  30:    */ import net.minecraft.util.IIcon;
/*  31:    */ import net.minecraft.util.MovingObjectPosition;
/*  32:    */ 
/*  33:    */ public abstract class PartMicroBlock
/*  34:    */   extends JCuboidPart
/*  35:    */   implements JIconHitEffects, IFaceRenderer, IMicroMaterialRender, JNormalOcclusion, IFence, IMicroBlock
/*  36:    */ {
/*  37: 28 */   public MicroMaterialRegistry.IMicroMaterial mat = null;
/*  38:    */   int material;
/*  39:    */   
/*  40:    */   public PartMicroBlock() {}
/*  41:    */   
/*  42:    */   public PartMicroBlock(int material)
/*  43:    */   {
/*  44: 35 */     this.material = material;
/*  45:    */   }
/*  46:    */   
/*  47: 38 */   boolean overEthereal = false;
/*  48:    */   
/*  49:    */   public boolean isEthereal()
/*  50:    */   {
/*  51: 41 */     return false;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean hideCreativeTab()
/*  55:    */   {
/*  56: 46 */     return false;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean occlusionTest(TMultiPart npart)
/*  60:    */   {
/*  61: 51 */     return NormalOcclusionTest.apply(this, npart);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Iterable<Cuboid6> getOcclusionBoxes()
/*  65:    */   {
/*  66: 56 */     return Arrays.asList(new Cuboid6[] { getBounds() });
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void harvest(MovingObjectPosition hit, EntityPlayer player)
/*  70:    */   {
/*  71: 61 */     super.harvest(hit, player);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void renderFace(Vertex5[] face, int side) {}
/*  75:    */   
/*  76:    */   public void writeDesc(MCDataOutput packet)
/*  77:    */   {
/*  78: 71 */     packet.writeInt(this.material);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void readDesc(MCDataInput packet)
/*  82:    */   {
/*  83: 76 */     this.material = packet.readInt();
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void save(NBTTagCompound tag)
/*  87:    */   {
/*  88: 81 */     super.save(tag);
/*  89: 82 */     tag.setString("mat", MicroMaterialRegistry.materialName(this.material));
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void load(NBTTagCompound tag)
/*  93:    */   {
/*  94: 87 */     super.load(tag);
/*  95: 88 */     this.material = MicroMaterialRegistry.materialID(tag.getString("mat"));
/*  96:    */   }
/*  97:    */   
/*  98:    */   public abstract Iterable<Cuboid6> getCollisionBoxes();
/*  99:    */   
/* 100:    */   public ItemStack getItemDrop()
/* 101:    */   {
/* 102: 95 */     ItemStack item = new ItemStack(ItemMicroBlock.instance, 1, getMetadata());
/* 103: 96 */     NBTTagCompound tag = new NBTTagCompound();
/* 104: 97 */     tag.setString("mat", MicroMaterialRegistry.materialName(this.material));
/* 105: 98 */     item.setTagCompound(tag);
/* 106: 99 */     return item;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public Iterable<ItemStack> getDrops()
/* 110:    */   {
/* 111:104 */     return Arrays.asList(new ItemStack[] { getItemDrop() });
/* 112:    */   }
/* 113:    */   
/* 114:    */   public ItemStack pickItem(MovingObjectPosition hit)
/* 115:    */   {
/* 116:109 */     return getItemDrop();
/* 117:    */   }
/* 118:    */   
/* 119:    */   @SideOnly(Side.CLIENT)
/* 120:    */   public IIcon getBreakingIcon(Object subPart, int side)
/* 121:    */   {
/* 122:115 */     return getBrokenIcon(side);
/* 123:    */   }
/* 124:    */   
/* 125:    */   public MicroMaterialRegistry.IMicroMaterial getMaterial()
/* 126:    */   {
/* 127:119 */     if (this.mat == null) {
/* 128:120 */       this.mat = MicroMaterialRegistry.getMaterial(this.material);
/* 129:    */     }
/* 130:123 */     return this.mat;
/* 131:    */   }
/* 132:    */   
/* 133:    */   @SideOnly(Side.CLIENT)
/* 134:    */   public IIcon getBrokenIcon(int side)
/* 135:    */   {
/* 136:129 */     if (this.mat != null) {
/* 137:130 */       return this.mat.getBreakingIcon(side);
/* 138:    */     }
/* 139:133 */     return Blocks.stone.getIcon(0, 0);
/* 140:    */   }
/* 141:    */   
/* 142:    */   @SideOnly(Side.CLIENT)
/* 143:    */   public void addHitEffects(MovingObjectPosition hit, EffectRenderer effectRenderer)
/* 144:    */   {
/* 145:139 */     IconHitEffects.addHitEffects(this, hit, effectRenderer);
/* 146:    */   }
/* 147:    */   
/* 148:    */   @SideOnly(Side.CLIENT)
/* 149:    */   public void addDestroyEffects(MovingObjectPosition hit, EffectRenderer effectRenderer)
/* 150:    */   {
/* 151:145 */     IconHitEffects.addDestroyEffects(this, effectRenderer, false);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public Cuboid6 getRenderBounds()
/* 155:    */   {
/* 156:150 */     return getBounds();
/* 157:    */   }
/* 158:    */   
/* 159:    */   public int getLightValue()
/* 160:    */   {
/* 161:155 */     return MicroMaterialRegistry.getMaterial(this.material).getLightValue();
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void onNeighborChanged()
/* 165:    */   {
/* 166:160 */     reloadShape();
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void drop()
/* 170:    */   {
/* 171:164 */     TileMultipart.dropItem(getItemDrop(), world(), Vector3.fromTileEntityCenter(tile()));
/* 172:165 */     tile().remPart(this);
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void onPartChanged(TMultiPart part)
/* 176:    */   {
/* 177:170 */     reloadShape();
/* 178:    */   }
/* 179:    */   
/* 180:    */   public Iterable<IndexedCuboid6> getSubParts()
/* 181:    */   {
/* 182:175 */     ArrayList<IndexedCuboid6> boxes = new ArrayList();
/* 183:    */     
/* 184:177 */     this.overEthereal = true;
/* 185:178 */     for (Cuboid6 cuboid6 : getCollisionBoxes()) {
/* 186:179 */       boxes.add(new IndexedCuboid6(Integer.valueOf(0), cuboid6));
/* 187:    */     }
/* 188:181 */     this.overEthereal = false;
/* 189:    */     
/* 190:183 */     return boxes;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void onWorldJoin()
/* 194:    */   {
/* 195:188 */     reloadShape();
/* 196:189 */     super.onWorldJoin();
/* 197:    */   }
/* 198:    */   
/* 199:    */   public abstract void reloadShape();
/* 200:    */   
/* 201:    */   @SideOnly(Side.CLIENT)
/* 202:    */   public boolean renderStatic(Vector3 pos, int pass)
/* 203:    */   {
/* 204:197 */     reloadShape();
/* 205:199 */     if (this.mat == null) {
/* 206:200 */       this.mat = MicroMaterialRegistry.getMaterial(this.material);
/* 207:    */     }
/* 208:203 */     if ((this.mat != null) && (this.mat.canRenderInPass(pass)))
/* 209:    */     {
/* 210:204 */       render(pos, pass);
/* 211:205 */       return true;
/* 212:    */     }
/* 213:207 */     return false;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public abstract void render(Vector3 paramVector3, int paramInt);
/* 217:    */   
/* 218:    */   public float getStrength(MovingObjectPosition hit, EntityPlayer player)
/* 219:    */   {
/* 220:214 */     return getMaterial().getStrength(player);
/* 221:    */   }
/* 222:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.multipart.microblock.PartMicroBlock
 * JD-Core Version:    0.7.0.1
 */