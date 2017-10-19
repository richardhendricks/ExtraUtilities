/*   1:    */ package com.rwtema.extrautils.tileentity.transfernodes.multiparts;
/*   2:    */ 
/*   3:    */ import codechicken.lib.raytracer.IndexedCuboid6;
/*   4:    */ import codechicken.lib.vec.Cuboid6;
/*   5:    */ import codechicken.lib.vec.Vector3;
/*   6:    */ import codechicken.microblock.ISidedHollowConnect;
/*   7:    */ import codechicken.multipart.INeighborTileChange;
/*   8:    */ import codechicken.multipart.TMultiPart;
/*   9:    */ import codechicken.multipart.TSlottedPart;
/*  10:    */ import codechicken.multipart.TileMultipart;
/*  11:    */ import com.rwtema.extrautils.ExtraUtils;
/*  12:    */ import com.rwtema.extrautils.block.Box;
/*  13:    */ import com.rwtema.extrautils.block.BoxModel;
/*  14:    */ import com.rwtema.extrautils.helper.XUHelper;
/*  15:    */ import com.rwtema.extrautils.tileentity.transfernodes.BlockTransferPipe;
/*  16:    */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeBuffer;
/*  17:    */ import com.rwtema.extrautils.tileentity.transfernodes.pipes.IPipe;
/*  18:    */ import com.rwtema.extrautils.tileentity.transfernodes.pipes.IPipeCosmetic;
/*  19:    */ import com.rwtema.extrautils.tileentity.transfernodes.pipes.StdPipes;
/*  20:    */ import cpw.mods.fml.relauncher.Side;
/*  21:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  22:    */ import java.util.ArrayList;
/*  23:    */ import java.util.Arrays;
/*  24:    */ import java.util.HashSet;
/*  25:    */ import net.minecraft.block.Block;
/*  26:    */ import net.minecraft.client.renderer.OpenGlHelper;
/*  27:    */ import net.minecraft.client.renderer.RenderGlobal;
/*  28:    */ import net.minecraft.entity.player.EntityPlayer;
/*  29:    */ import net.minecraft.inventory.IInventory;
/*  30:    */ import net.minecraft.item.ItemStack;
/*  31:    */ import net.minecraft.tileentity.TileEntity;
/*  32:    */ import net.minecraft.util.AxisAlignedBB;
/*  33:    */ import net.minecraft.util.IIcon;
/*  34:    */ import net.minecraft.util.MovingObjectPosition;
/*  35:    */ import net.minecraft.world.IBlockAccess;
/*  36:    */ import net.minecraft.world.World;
/*  37:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  38:    */ import org.lwjgl.opengl.GL11;
/*  39:    */ 
/*  40:    */ public class PipePart
/*  41:    */   extends MCMetaTilePart
/*  42:    */   implements ISidedHollowConnect, IPipe, IPipeCosmetic, INeighborTileChange, TSlottedPart
/*  43:    */ {
/*  44: 39 */   public static DummyPipePart[] dummyPipes = new DummyPipePart[6];
/*  45:    */   
/*  46:    */   static
/*  47:    */   {
/*  48: 42 */     for (int i = 0; i < 6; i++) {
/*  49: 43 */       dummyPipes[i] = new DummyPipePart(i, 0.375F);
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53: 47 */   public int blockMasks = -1;
/*  54: 48 */   public byte[] flagmasks = { 1, 2, 4, 8, 16, 32 };
/*  55:    */   
/*  56:    */   public PipePart(int meta)
/*  57:    */   {
/*  58: 51 */     super(meta);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Iterable<ItemStack> getDrops()
/*  62:    */   {
/*  63: 60 */     return Arrays.asList(new ItemStack[] { new ItemStack(getBlock(), 1, getBlock().damageDropped(getMetadata())) });
/*  64:    */   }
/*  65:    */   
/*  66:    */   public ItemStack pickItem(MovingObjectPosition hit)
/*  67:    */   {
/*  68: 65 */     return new ItemStack(getBlock(), 1, getBlock().damageDropped(getMetadata()));
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getMetadata()
/*  72:    */   {
/*  73: 70 */     return this.meta % 16;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Iterable<Cuboid6> getOcclusionBoxes()
/*  77:    */   {
/*  78: 75 */     return Arrays.asList(new Cuboid6[] { new Cuboid6(0.5F - baseSize(), 0.5F - baseSize(), 0.5F - baseSize(), 0.5F + baseSize(), 0.5F + baseSize(), 0.5F + baseSize()) });
/*  79:    */   }
/*  80:    */   
/*  81:    */   public boolean activate(EntityPlayer player, MovingObjectPosition part, ItemStack item)
/*  82:    */   {
/*  83: 80 */     if (getWorld().isClient) {
/*  84: 81 */       return true;
/*  85:    */     }
/*  86: 84 */     if (XUHelper.isWrench(item))
/*  87:    */     {
/*  88: 86 */       int newmetadata = StdPipes.getNextPipeType(getWorld(), part.blockX, part.blockY, part.blockZ, this.meta);
/*  89: 87 */       this.meta = ((byte)newmetadata);
/*  90: 88 */       sendDescUpdate();
/*  91:    */       
/*  92: 90 */       return true;
/*  93:    */     }
/*  94: 93 */     return false;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public final Cuboid6 getBounds()
/*  98:    */   {
/*  99: 99 */     Box bounds = ((BlockTransferPipe)getBlock()).getWorldModel(getWorld(), x(), y(), z()).boundingBox();
/* 100:100 */     return new Cuboid6(bounds.minX, bounds.minY, bounds.minZ, bounds.maxX, bounds.maxY, bounds.maxZ);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public final HashSet<IndexedCuboid6> getSubParts()
/* 104:    */   {
/* 105:105 */     HashSet<IndexedCuboid6> boxes = new HashSet();
/* 106:106 */     for (Box bounds : ((BlockTransferPipe)getBlock()).getWorldModel(getWorld(), x(), y(), z())) {
/* 107:107 */       boxes.add(new IndexedCuboid6(Integer.valueOf(0), new Cuboid6(bounds.minX, bounds.minY, bounds.minZ, bounds.maxX, bounds.maxY, bounds.maxZ)));
/* 108:    */     }
/* 109:109 */     return boxes;
/* 110:    */   }
/* 111:    */   
/* 112:    */   @SideOnly(Side.CLIENT)
/* 113:    */   public boolean drawHighlight(MovingObjectPosition hit, EntityPlayer player, float frame)
/* 114:    */   {
/* 115:115 */     GL11.glEnable(3042);
/* 116:116 */     OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 117:117 */     GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
/* 118:118 */     GL11.glLineWidth(2.0F);
/* 119:119 */     GL11.glDisable(3553);
/* 120:120 */     GL11.glDepthMask(false);
/* 121:121 */     float f1 = 0.002F;
/* 122:    */     
/* 123:123 */     double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * frame;
/* 124:124 */     double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * frame;
/* 125:125 */     double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * frame;
/* 126:    */     
/* 127:127 */     RenderGlobal.drawOutlinedBoundingBox(getBounds().add(new Vector3(x(), y(), z())).toAABB().expand(f1, f1, f1).getOffsetBoundingBox(-d0, -d1, -d2), -1);
/* 128:    */     
/* 129:129 */     GL11.glDepthMask(true);
/* 130:130 */     GL11.glEnable(3553);
/* 131:131 */     GL11.glDisable(3042);
/* 132:132 */     return true;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public Iterable<Cuboid6> getCollisionBoxes()
/* 136:    */   {
/* 137:137 */     ArrayList<Cuboid6> t2 = new ArrayList();
/* 138:138 */     BoxModel model = ((BlockTransferPipe)getBlock()).getWorldModel(world(), x(), y(), z());
/* 139:140 */     for (Box box : model) {
/* 140:141 */       t2.add(new Cuboid6(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ));
/* 141:    */     }
/* 142:144 */     return t2;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public Block getBlock()
/* 146:    */   {
/* 147:149 */     return this.meta < 16 ? ExtraUtils.transferPipe : ExtraUtils.transferPipe2;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public String getType()
/* 151:    */   {
/* 152:154 */     return "extrautils:transfer_pipe";
/* 153:    */   }
/* 154:    */   
/* 155:    */   public ArrayList<ForgeDirection> getOutputDirections(IBlockAccess world, int x, int y, int z, ForgeDirection dir, INodeBuffer buffer)
/* 156:    */   {
/* 157:159 */     return StdPipes.getPipeType(this.meta).getOutputDirections(world, x, y, z, dir, buffer);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public boolean transferItems(IBlockAccess world, int x, int y, int z, ForgeDirection dir, INodeBuffer buffer)
/* 161:    */   {
/* 162:164 */     return StdPipes.getPipeType(this.meta).transferItems(world, x, y, z, dir, buffer);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public boolean canInput(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
/* 166:    */   {
/* 167:169 */     return (!isBlocked(dir)) && (StdPipes.getPipeType(this.meta).canInput(world, x, y, z, dir));
/* 168:    */   }
/* 169:    */   
/* 170:    */   public boolean canOutput(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
/* 171:    */   {
/* 172:175 */     return (!isBlocked(dir)) && (StdPipes.getPipeType(this.meta).canOutput(getWorld(), x, y, z, dir));
/* 173:    */   }
/* 174:    */   
/* 175:    */   public int limitTransfer(TileEntity dest, ForgeDirection side, INodeBuffer buffer)
/* 176:    */   {
/* 177:181 */     return StdPipes.getPipeType(this.meta).limitTransfer(dest, side, buffer);
/* 178:    */   }
/* 179:    */   
/* 180:    */   public IInventory getFilterInventory(IBlockAccess world, int x, int y, int z)
/* 181:    */   {
/* 182:186 */     return null;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public boolean shouldConnectToTile(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
/* 186:    */   {
/* 187:191 */     return (!isBlocked(dir)) && (StdPipes.getPipeType(this.meta).shouldConnectToTile(world, x, y, z, dir));
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void reloadBlockMasks()
/* 191:    */   {
/* 192:196 */     this.blockMasks = 0;
/* 193:198 */     for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
/* 194:    */     {
/* 195:199 */       dummyPipes[dir.ordinal()].h = (0.5F - baseSize());
/* 196:201 */       if (!tile().canAddPart(dummyPipes[dir.ordinal()])) {
/* 197:202 */         this.blockMasks |= this.flagmasks[dir.ordinal()];
/* 198:    */       }
/* 199:    */     }
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void onPartChanged(TMultiPart part)
/* 203:    */   {
/* 204:209 */     reloadBlockMasks();
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void onNeighborChanged()
/* 208:    */   {
/* 209:217 */     reloadBlockMasks();
/* 210:    */   }
/* 211:    */   
/* 212:    */   public boolean isBlocked(ForgeDirection dir)
/* 213:    */   {
/* 214:221 */     if (this.blockMasks < 0) {
/* 215:222 */       reloadBlockMasks();
/* 216:    */     }
/* 217:225 */     return (this.blockMasks & this.flagmasks[dir.ordinal()]) == this.flagmasks[dir.ordinal()];
/* 218:    */   }
/* 219:    */   
/* 220:    */   public IIcon baseTexture()
/* 221:    */   {
/* 222:230 */     return ((IPipeCosmetic)StdPipes.getPipeType(this.meta)).baseTexture();
/* 223:    */   }
/* 224:    */   
/* 225:    */   public IIcon socketTexture(ForgeDirection dir)
/* 226:    */   {
/* 227:235 */     return ((IPipeCosmetic)StdPipes.getPipeType(this.meta)).socketTexture(dir);
/* 228:    */   }
/* 229:    */   
/* 230:    */   public IIcon pipeTexture(ForgeDirection dir, boolean blocked)
/* 231:    */   {
/* 232:240 */     return ((IPipeCosmetic)StdPipes.getPipeType(this.meta)).pipeTexture(dir, blocked);
/* 233:    */   }
/* 234:    */   
/* 235:    */   public IIcon invPipeTexture(ForgeDirection dir)
/* 236:    */   {
/* 237:245 */     return ((IPipeCosmetic)StdPipes.getPipeType(this.meta)).invPipeTexture(dir);
/* 238:    */   }
/* 239:    */   
/* 240:    */   public String getPipeType()
/* 241:    */   {
/* 242:250 */     return StdPipes.getPipeType(this.meta).getPipeType();
/* 243:    */   }
/* 244:    */   
/* 245:    */   public float baseSize()
/* 246:    */   {
/* 247:255 */     return ((IPipeCosmetic)StdPipes.getPipeType(this.meta)).baseSize();
/* 248:    */   }
/* 249:    */   
/* 250:    */   public boolean occlusionTest(TMultiPart npart)
/* 251:    */   {
/* 252:260 */     return (DummyPipePart.class.equals(npart.getClass())) || (super.occlusionTest(npart));
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void onNeighborTileChanged(int arg0, boolean arg1)
/* 256:    */   {
/* 257:266 */     reloadBlockMasks();
/* 258:    */   }
/* 259:    */   
/* 260:    */   public boolean weakTileChanges()
/* 261:    */   {
/* 262:271 */     return true;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public int getSlotMask()
/* 266:    */   {
/* 267:276 */     return 64;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public int getHollowSize(int side)
/* 271:    */   {
/* 272:281 */     return 6;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public TileEntity getBlockTile()
/* 276:    */   {
/* 277:287 */     return tile();
/* 278:    */   }
/* 279:    */   
/* 280:    */   public PipePart() {}
/* 281:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.multiparts.PipePart
 * JD-Core Version:    0.7.0.1
 */