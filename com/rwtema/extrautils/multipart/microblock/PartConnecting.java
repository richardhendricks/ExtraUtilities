/*   1:    */ package com.rwtema.extrautils.multipart.microblock;
/*   2:    */ 
/*   3:    */ import codechicken.lib.data.MCDataInput;
/*   4:    */ import codechicken.lib.data.MCDataOutput;
/*   5:    */ import codechicken.lib.raytracer.IndexedCuboid6;
/*   6:    */ import codechicken.lib.vec.Cuboid6;
/*   7:    */ import codechicken.lib.vec.Vector3;
/*   8:    */ import codechicken.microblock.IMicroMaterialRender;
/*   9:    */ import codechicken.microblock.MicroMaterialRegistry;
/*  10:    */ import codechicken.microblock.MicroMaterialRegistry.IMicroMaterial;
/*  11:    */ import codechicken.multipart.IconHitEffects;
/*  12:    */ import codechicken.multipart.JIconHitEffects;
/*  13:    */ import codechicken.multipart.JNormalOcclusion;
/*  14:    */ import codechicken.multipart.NormalOcclusionTest;
/*  15:    */ import codechicken.multipart.TMultiPart;
/*  16:    */ import codechicken.multipart.TileMultipart;
/*  17:    */ import cpw.mods.fml.relauncher.Side;
/*  18:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  19:    */ import java.util.Arrays;
/*  20:    */ import net.minecraft.block.Block;
/*  21:    */ import net.minecraft.client.particle.EffectRenderer;
/*  22:    */ import net.minecraft.entity.player.EntityPlayer;
/*  23:    */ import net.minecraft.init.Blocks;
/*  24:    */ import net.minecraft.item.ItemStack;
/*  25:    */ import net.minecraft.nbt.NBTTagCompound;
/*  26:    */ import net.minecraft.util.IIcon;
/*  27:    */ import net.minecraft.util.MovingObjectPosition;
/*  28:    */ 
/*  29:    */ public abstract class PartConnecting
/*  30:    */   extends PartMicroBlock
/*  31:    */   implements JIconHitEffects, IMicroMaterialRender, JNormalOcclusion, IMicroBlock
/*  32:    */ {
/*  33: 24 */   public MicroMaterialRegistry.IMicroMaterial mat = null;
/*  34: 25 */   public int connectionMask = 0;
/*  35:    */   int material;
/*  36:    */   
/*  37:    */   public PartConnecting() {}
/*  38:    */   
/*  39:    */   public PartConnecting(int material)
/*  40:    */   {
/*  41: 32 */     this.material = material;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public boolean occlusionTest(TMultiPart npart)
/*  45:    */   {
/*  46: 37 */     return NormalOcclusionTest.apply(this, npart);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Iterable<Cuboid6> getOcclusionBoxes()
/*  50:    */   {
/*  51: 42 */     return Arrays.asList(new Cuboid6[] { getBounds() });
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void harvest(MovingObjectPosition hit, EntityPlayer player)
/*  55:    */   {
/*  56: 47 */     super.harvest(hit, player);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void writeDesc(MCDataOutput packet)
/*  60:    */   {
/*  61: 52 */     packet.writeInt(this.material);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void readDesc(MCDataInput packet)
/*  65:    */   {
/*  66: 57 */     this.material = packet.readInt();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void save(NBTTagCompound tag)
/*  70:    */   {
/*  71: 62 */     super.save(tag);
/*  72: 63 */     tag.setString("mat", MicroMaterialRegistry.materialName(this.material));
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void load(NBTTagCompound tag)
/*  76:    */   {
/*  77: 68 */     super.load(tag);
/*  78: 69 */     this.material = MicroMaterialRegistry.materialID(tag.getString("mat"));
/*  79:    */   }
/*  80:    */   
/*  81:    */   public abstract String getType();
/*  82:    */   
/*  83:    */   public abstract Cuboid6 getBounds();
/*  84:    */   
/*  85:    */   public abstract Iterable<Cuboid6> getCollisionBoxes();
/*  86:    */   
/*  87:    */   public ItemStack getItemDrop()
/*  88:    */   {
/*  89: 82 */     ItemStack item = new ItemStack(ItemMicroBlock.instance, 1, getMetadata());
/*  90: 83 */     NBTTagCompound tag = new NBTTagCompound();
/*  91: 84 */     tag.setString("mat", MicroMaterialRegistry.materialName(this.material));
/*  92: 85 */     item.setTagCompound(tag);
/*  93: 86 */     return item;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public Iterable<ItemStack> getDrops()
/*  97:    */   {
/*  98: 91 */     return Arrays.asList(new ItemStack[] { getItemDrop() });
/*  99:    */   }
/* 100:    */   
/* 101:    */   public ItemStack pickItem(MovingObjectPosition hit)
/* 102:    */   {
/* 103: 96 */     return getItemDrop();
/* 104:    */   }
/* 105:    */   
/* 106:    */   @SideOnly(Side.CLIENT)
/* 107:    */   public IIcon getBreakingIcon(Object subPart, int side)
/* 108:    */   {
/* 109:102 */     return getBrokenIcon(side);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public MicroMaterialRegistry.IMicroMaterial getMaterial()
/* 113:    */   {
/* 114:106 */     if (this.mat == null) {
/* 115:107 */       this.mat = MicroMaterialRegistry.getMaterial(this.material);
/* 116:    */     }
/* 117:110 */     return this.mat;
/* 118:    */   }
/* 119:    */   
/* 120:    */   @SideOnly(Side.CLIENT)
/* 121:    */   public IIcon getBrokenIcon(int side)
/* 122:    */   {
/* 123:116 */     if (this.mat != null) {
/* 124:117 */       return this.mat.getBreakingIcon(side);
/* 125:    */     }
/* 126:120 */     return Blocks.stone.getIcon(0, 0);
/* 127:    */   }
/* 128:    */   
/* 129:    */   @SideOnly(Side.CLIENT)
/* 130:    */   public void addHitEffects(MovingObjectPosition hit, EffectRenderer effectRenderer)
/* 131:    */   {
/* 132:126 */     IconHitEffects.addHitEffects(this, hit, effectRenderer);
/* 133:    */   }
/* 134:    */   
/* 135:    */   @SideOnly(Side.CLIENT)
/* 136:    */   public void addDestroyEffects(MovingObjectPosition hit, EffectRenderer effectRenderer)
/* 137:    */   {
/* 138:132 */     IconHitEffects.addDestroyEffects(this, effectRenderer, false);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public Cuboid6 getRenderBounds()
/* 142:    */   {
/* 143:137 */     return getBounds();
/* 144:    */   }
/* 145:    */   
/* 146:    */   public int getLightValue()
/* 147:    */   {
/* 148:142 */     return MicroMaterialRegistry.getMaterial(this.material).getLightValue();
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void onNeighborChanged()
/* 152:    */   {
/* 153:147 */     reloadShape();
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void drop()
/* 157:    */   {
/* 158:151 */     TileMultipart.dropItem(getItemDrop(), world(), Vector3.fromTileEntityCenter(tile()));
/* 159:152 */     tile().remPart(this);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void onPartChanged(TMultiPart part)
/* 163:    */   {
/* 164:157 */     reloadShape();
/* 165:    */   }
/* 166:    */   
/* 167:    */   public Iterable<IndexedCuboid6> getSubParts()
/* 168:    */   {
/* 169:162 */     IndexedCuboid6 box = new IndexedCuboid6(Integer.valueOf(0), new Cuboid6(0.5D, 0.5D, 0.5D, 0.5D, 0.5D, 0.5D));
/* 170:    */     
/* 171:164 */     this.overEthereal = true;
/* 172:165 */     for (Cuboid6 cuboid6 : getCollisionBoxes()) {
/* 173:166 */       box.enclose(new IndexedCuboid6(Integer.valueOf(0), cuboid6));
/* 174:    */     }
/* 175:168 */     this.overEthereal = false;
/* 176:    */     
/* 177:170 */     box.max.y = 1.0D;
/* 178:171 */     return Arrays.asList(new IndexedCuboid6[] { box });
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void onWorldJoin()
/* 182:    */   {
/* 183:176 */     reloadShape();
/* 184:177 */     super.onWorldJoin();
/* 185:    */   }
/* 186:    */   
/* 187:    */   public abstract boolean shouldConnect(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/* 188:    */   
/* 189:    */   public abstract void reloadShape();
/* 190:    */   
/* 191:    */   @SideOnly(Side.CLIENT)
/* 192:    */   public abstract boolean renderStatic(Vector3 paramVector3, int paramInt);
/* 193:    */   
/* 194:    */   @SideOnly(Side.CLIENT)
/* 195:    */   public void render(Vector3 pos, int pass)
/* 196:    */   {
/* 197:191 */     renderStatic(pos, pass);
/* 198:    */   }
/* 199:    */   
/* 200:    */   public float getStrength(MovingObjectPosition hit, EntityPlayer player)
/* 201:    */   {
/* 202:196 */     return getMaterial().getStrength(player);
/* 203:    */   }
/* 204:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.multipart.microblock.PartConnecting
 * JD-Core Version:    0.7.0.1
 */