/*   1:    */ package com.rwtema.extrautils.multipart.microblock;
/*   2:    */ 
/*   3:    */ import codechicken.lib.data.MCDataInput;
/*   4:    */ import codechicken.lib.data.MCDataOutput;
/*   5:    */ import codechicken.lib.raytracer.IndexedCuboid6;
/*   6:    */ import codechicken.lib.vec.BlockCoord;
/*   7:    */ import codechicken.lib.vec.Cuboid6;
/*   8:    */ import codechicken.lib.vec.Transformation;
/*   9:    */ import codechicken.lib.vec.Vector3;
/*  10:    */ import codechicken.microblock.IMicroMaterialRender;
/*  11:    */ import codechicken.microblock.ISidedHollowConnect;
/*  12:    */ import codechicken.microblock.MicroMaterialRegistry;
/*  13:    */ import codechicken.microblock.MicroMaterialRegistry.IMicroMaterial;
/*  14:    */ import codechicken.microblock.MicroblockRender;
/*  15:    */ import codechicken.multipart.IconHitEffects;
/*  16:    */ import codechicken.multipart.JIconHitEffects;
/*  17:    */ import codechicken.multipart.JPartialOcclusion;
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
/*  32:    */ import net.minecraft.world.World;
/*  33:    */ 
/*  34:    */ public class PartPipeJacket
/*  35:    */   extends PartMicroBlock
/*  36:    */   implements JIconHitEffects, IMicroMaterialRender, IMicroBlock, JPartialOcclusion
/*  37:    */ {
/*  38:    */   public static final String type = "extrautils:pipeJacket";
/*  39: 33 */   public static Cuboid6[] axisCubes = null;
/*  40: 34 */   public MicroMaterialRegistry.IMicroMaterial mat = null;
/*  41: 35 */   public int connectionMask = 0;
/*  42: 36 */   public double pipeSize = 0.3D;
/*  43: 37 */   public TMultiPart centerPart = null;
/*  44:    */   int material;
/*  45:    */   private MicroMaterialRegistry.IMicroMaterial wool;
/*  46:    */   
/*  47:    */   public PartPipeJacket() {}
/*  48:    */   
/*  49:    */   public PartPipeJacket(int material)
/*  50:    */   {
/*  51: 45 */     this.material = material;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void harvest(MovingObjectPosition hit, EntityPlayer player)
/*  55:    */   {
/*  56: 50 */     super.harvest(hit, player);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean occlusionTest(TMultiPart npart)
/*  60:    */   {
/*  61: 55 */     return !npart.getClass().equals(getClass());
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void writeDesc(MCDataOutput packet)
/*  65:    */   {
/*  66: 60 */     packet.writeInt(this.material);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void readDesc(MCDataInput packet)
/*  70:    */   {
/*  71: 65 */     this.material = packet.readInt();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void save(NBTTagCompound tag)
/*  75:    */   {
/*  76: 70 */     super.save(tag);
/*  77: 71 */     tag.setString("mat", MicroMaterialRegistry.materialName(this.material));
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void load(NBTTagCompound tag)
/*  81:    */   {
/*  82: 76 */     super.load(tag);
/*  83: 77 */     this.material = MicroMaterialRegistry.materialID(tag.getString("mat"));
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String getType()
/*  87:    */   {
/*  88: 82 */     return "extrautils:pipeJacket";
/*  89:    */   }
/*  90:    */   
/*  91:    */   public Cuboid6 getBounds()
/*  92:    */   {
/*  93: 87 */     return new Cuboid6(0.5D - this.pipeSize, 0.5D - this.pipeSize, 0.5D - this.pipeSize, 0.5D + this.pipeSize, 0.5D + this.pipeSize, 0.5D + this.pipeSize);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public Iterable<Cuboid6> getCollisionBoxes()
/*  97:    */   {
/*  98: 92 */     ArrayList<Cuboid6> boxes = new ArrayList();
/*  99: 93 */     if (isEthereal()) {
/* 100: 93 */       return boxes;
/* 101:    */     }
/* 102: 94 */     boxes.add(getRenderBounds());
/* 103: 96 */     for (int i = 0; i < 6; i++) {
/* 104: 97 */       if ((this.connectionMask & 1 << i) != 0) {
/* 105: 98 */         boxes.add(new Cuboid6(0.5D - this.pipeSize, 0.5D + this.pipeSize, 0.5D - this.pipeSize, 0.5D + this.pipeSize, 1.0D, 0.5D + this.pipeSize).apply(codechicken.lib.vec.Rotation.sideRotations[net.minecraft.util.Facing.oppositeSide[i]].at(new Vector3(0.5D, 0.5D, 0.5D))));
/* 106:    */       }
/* 107:    */     }
/* 108:102 */     return boxes;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public ItemStack getItemDrop()
/* 112:    */   {
/* 113:106 */     ItemStack item = new ItemStack(ItemMicroBlock.instance, 1);
/* 114:107 */     NBTTagCompound tag = new NBTTagCompound();
/* 115:108 */     tag.setString("mat", MicroMaterialRegistry.materialName(this.material));
/* 116:109 */     item.setTagCompound(tag);
/* 117:110 */     return item;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public Iterable<ItemStack> getDrops()
/* 121:    */   {
/* 122:115 */     return Arrays.asList(new ItemStack[] { getItemDrop() });
/* 123:    */   }
/* 124:    */   
/* 125:    */   public ItemStack pickItem(MovingObjectPosition hit)
/* 126:    */   {
/* 127:120 */     return getItemDrop();
/* 128:    */   }
/* 129:    */   
/* 130:    */   @SideOnly(Side.CLIENT)
/* 131:    */   public IIcon getBreakingIcon(Object subPart, int side)
/* 132:    */   {
/* 133:126 */     return getBrokenIcon(side);
/* 134:    */   }
/* 135:    */   
/* 136:    */   @SideOnly(Side.CLIENT)
/* 137:    */   public IIcon getBrokenIcon(int side)
/* 138:    */   {
/* 139:138 */     if (this.mat != null) {
/* 140:139 */       return this.mat.getBreakingIcon(side);
/* 141:    */     }
/* 142:142 */     return Blocks.stone.getIcon(0, 0);
/* 143:    */   }
/* 144:    */   
/* 145:    */   @SideOnly(Side.CLIENT)
/* 146:    */   public void addHitEffects(MovingObjectPosition hit, EffectRenderer effectRenderer)
/* 147:    */   {
/* 148:148 */     IconHitEffects.addHitEffects(this, hit, effectRenderer);
/* 149:    */   }
/* 150:    */   
/* 151:    */   @SideOnly(Side.CLIENT)
/* 152:    */   public void addDestroyEffects(MovingObjectPosition hit, EffectRenderer effectRenderer)
/* 153:    */   {
/* 154:154 */     IconHitEffects.addDestroyEffects(this, effectRenderer, false);
/* 155:    */   }
/* 156:    */   
/* 157:    */   public Cuboid6 getRenderBounds()
/* 158:    */   {
/* 159:159 */     return getBounds();
/* 160:    */   }
/* 161:    */   
/* 162:    */   public int getLightValue()
/* 163:    */   {
/* 164:164 */     return MicroMaterialRegistry.getMaterial(this.material).getLightValue();
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void onNeighborChanged()
/* 168:    */   {
/* 169:169 */     if (!world().isClient) {
/* 170:170 */       dropIfCantStay();
/* 171:    */     } else {
/* 172:172 */       reloadShape();
/* 173:    */     }
/* 174:    */   }
/* 175:    */   
/* 176:    */   public boolean canStay()
/* 177:    */   {
/* 178:177 */     return tile().partMap(6) != null;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public boolean dropIfCantStay()
/* 182:    */   {
/* 183:181 */     if (!canStay())
/* 184:    */     {
/* 185:182 */       drop();
/* 186:183 */       return true;
/* 187:    */     }
/* 188:186 */     reloadShape();
/* 189:187 */     return false;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void drop()
/* 193:    */   {
/* 194:191 */     TileMultipart.dropItem(getItemDrop(), world(), Vector3.fromTileEntityCenter(tile()));
/* 195:192 */     tile().remPart(this);
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void onPartChanged(TMultiPart part)
/* 199:    */   {
/* 200:197 */     if (!world().isClient) {
/* 201:198 */       dropIfCantStay();
/* 202:    */     }
/* 203:    */   }
/* 204:    */   
/* 205:    */   public Iterable<IndexedCuboid6> getSubParts()
/* 206:    */   {
/* 207:204 */     ArrayList<IndexedCuboid6> boxes = new ArrayList();
/* 208:    */     
/* 209:206 */     this.overEthereal = true;
/* 210:207 */     for (Cuboid6 cuboid6 : getCollisionBoxes()) {
/* 211:208 */       boxes.add(new IndexedCuboid6(Integer.valueOf(0), cuboid6));
/* 212:    */     }
/* 213:210 */     this.overEthereal = false;
/* 214:    */     
/* 215:212 */     return boxes;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void onWorldJoin()
/* 219:    */   {
/* 220:217 */     reloadShape();
/* 221:218 */     super.onWorldJoin();
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void reloadShape()
/* 225:    */   {
/* 226:222 */     int prevMask = this.connectionMask;
/* 227:223 */     this.centerPart = tile().partMap(6);
/* 228:224 */     double prevSize = this.pipeSize;
/* 229:225 */     this.pipeSize = 0.26D;
/* 230:226 */     this.connectionMask = 0;
/* 231:228 */     if (this.centerPart != null)
/* 232:    */     {
/* 233:229 */       if ((this.centerPart instanceof ISidedHollowConnect)) {
/* 234:230 */         for (int side = 0; side < 6; side++) {
/* 235:231 */           this.pipeSize = Math.max(this.pipeSize, (((ISidedHollowConnect)this.centerPart).getHollowSize(side) + 1) / 32.0F);
/* 236:    */         }
/* 237:    */       }
/* 238:234 */       for (int i = 0; i < 6; i++) {
/* 239:236 */         for (Cuboid6 cuboid6 : this.centerPart.getCollisionBoxes()) {
/* 240:237 */           if (cuboid6.intersects(new Cuboid6(this.pipeSize, 0.0D, this.pipeSize, 1.0D - this.pipeSize, 0.01D, 1.0D - this.pipeSize).apply(codechicken.lib.vec.Rotation.sideRotations[i].at(new Vector3(0.5D, 0.5D, 0.5D))))) {
/* 241:238 */             this.connectionMask |= 1 << i;
/* 242:    */           }
/* 243:    */         }
/* 244:    */       }
/* 245:    */     }
/* 246:244 */     if ((prevMask != this.connectionMask) || (prevSize != this.pipeSize))
/* 247:    */     {
/* 248:245 */       tile().notifyPartChange(this);
/* 249:246 */       tile().markRender();
/* 250:    */     }
/* 251:    */   }
/* 252:    */   
/* 253:    */   @SideOnly(Side.CLIENT)
/* 254:    */   public boolean renderStatic(Vector3 pos, int pass)
/* 255:    */   {
/* 256:254 */     reloadShape();
/* 257:256 */     if (this.mat == null) {
/* 258:257 */       this.mat = MicroMaterialRegistry.getMaterial(this.material);
/* 259:    */     }
/* 260:260 */     if ((this.mat != null) && (this.mat.canRenderInPass(pass)))
/* 261:    */     {
/* 262:262 */       MicroblockRender.renderCuboid(new Vector3(x(), y(), z()), this.mat, pass, getRenderBounds(), this.connectionMask);
/* 263:264 */       for (int i = 0; i < 6; i++) {
/* 264:265 */         if ((this.connectionMask & 1 << i) != 0) {
/* 265:266 */           MicroblockRender.renderCuboid(new Vector3(x(), y(), z()), this.mat, pass, new Cuboid6(0.5D - this.pipeSize, 0.5D + this.pipeSize, 0.5D - this.pipeSize, 0.5D + this.pipeSize, 1.0D, 0.5D + this.pipeSize).apply(codechicken.lib.vec.Rotation.sideRotations[net.minecraft.util.Facing.oppositeSide[i]].at(new Vector3(0.5D, 0.5D, 0.5D))), 1 << net.minecraft.util.Facing.oppositeSide[i]);
/* 266:    */         }
/* 267:    */       }
/* 268:269 */       return true;
/* 269:    */     }
/* 270:272 */     return false;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void render(Vector3 pos, int pass) {}
/* 274:    */   
/* 275:    */   public float getStrength(MovingObjectPosition hit, EntityPlayer player)
/* 276:    */   {
/* 277:282 */     return getMaterial().getStrength(player) * 4.0F;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public int getMetadata()
/* 281:    */   {
/* 282:287 */     return 0;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public TMultiPart newPart(boolean client)
/* 286:    */   {
/* 287:292 */     return new PartPipeJacket();
/* 288:    */   }
/* 289:    */   
/* 290:    */   public TMultiPart placePart(ItemStack stack, EntityPlayer player, World world, BlockCoord pos, int side, Vector3 arg5, int materialID)
/* 291:    */   {
/* 292:297 */     TileMultipart tile = TileMultipart.getOrConvertTile(world, new BlockCoord(pos.x, pos.y, pos.z));
/* 293:299 */     if (tile == null) {
/* 294:300 */       return null;
/* 295:    */     }
/* 296:302 */     TMultiPart part = tile.partMap(6);
/* 297:303 */     if (part != null) {
/* 298:304 */       return new PartPipeJacket(materialID);
/* 299:    */     }
/* 300:308 */     return null;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public void registerPassThroughs() {}
/* 304:    */   
/* 305:    */   public void renderItem(ItemStack item, MicroMaterialRegistry.IMicroMaterial material)
/* 306:    */   {
/* 307:317 */     if (this.wool == null) {
/* 308:318 */       this.wool = MicroMaterialRegistry.getMaterial(Blocks.wool.getUnlocalizedName());
/* 309:    */     }
/* 310:322 */     MicroblockRender.renderCuboid(new Vector3(0.0D, 0.0D, 0.0D), this.wool, -1, new Cuboid6(0.1995D, 0.4D, 0.4D, 0.8005D, 0.6D, 0.6D), 15);
/* 311:323 */     MicroblockRender.renderCuboid(new Vector3(0.0D, 0.0D, 0.0D), material, -1, new Cuboid6(0.2D, 0.25D, 0.25D, 0.8D, 0.75D, 0.75D), 0);
/* 312:    */   }
/* 313:    */   
/* 314:    */   public Iterable<Cuboid6> getOcclusionBoxes()
/* 315:    */   {
/* 316:329 */     return Arrays.asList(new Object[0]);
/* 317:    */   }
/* 318:    */   
/* 319:    */   public Iterable<Cuboid6> getPartialOcclusionBoxes()
/* 320:    */   {
/* 321:334 */     return Arrays.asList(new Object[0]);
/* 322:    */   }
/* 323:    */   
/* 324:    */   public boolean allowCompleteOcclusion()
/* 325:    */   {
/* 326:339 */     return true;
/* 327:    */   }
/* 328:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.multipart.microblock.PartPipeJacket
 * JD-Core Version:    0.7.0.1
 */