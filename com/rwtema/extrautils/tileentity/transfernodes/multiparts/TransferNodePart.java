/*   1:    */ package com.rwtema.extrautils.tileentity.transfernodes.multiparts;
/*   2:    */ 
/*   3:    */ import codechicken.lib.data.MCDataInput;
/*   4:    */ import codechicken.lib.data.MCDataOutput;
/*   5:    */ import codechicken.lib.raytracer.IndexedCuboid6;
/*   6:    */ import codechicken.lib.vec.Cuboid6;
/*   7:    */ import codechicken.lib.vec.Vector3;
/*   8:    */ import codechicken.microblock.ISidedHollowConnect;
/*   9:    */ import codechicken.multipart.RedstoneInteractions;
/*  10:    */ import codechicken.multipart.TFacePart;
/*  11:    */ import codechicken.multipart.TMultiPart;
/*  12:    */ import codechicken.multipart.TSlottedPart;
/*  13:    */ import codechicken.multipart.TileMultipart;
/*  14:    */ import codechicken.multipart.scalatraits.TRedstoneTile;
/*  15:    */ import com.rwtema.extrautils.ExtraUtils;
/*  16:    */ import com.rwtema.extrautils.ExtraUtilsMod;
/*  17:    */ import com.rwtema.extrautils.block.Box;
/*  18:    */ import com.rwtema.extrautils.block.BoxModel;
/*  19:    */ import com.rwtema.extrautils.helper.XUHelper;
/*  20:    */ import com.rwtema.extrautils.tileentity.transfernodes.BlockTransferNode;
/*  21:    */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNode;
/*  22:    */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNodeUpgradeInventory;
/*  23:    */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INode;
/*  24:    */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeBuffer;
/*  25:    */ import com.rwtema.extrautils.tileentity.transfernodes.pipes.IPipeCosmetic;
/*  26:    */ import com.rwtema.extrautils.tileentity.transfernodes.pipes.StdPipes;
/*  27:    */ import cpw.mods.fml.relauncher.Side;
/*  28:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  29:    */ import java.util.ArrayList;
/*  30:    */ import java.util.Arrays;
/*  31:    */ import java.util.HashSet;
/*  32:    */ import java.util.List;
/*  33:    */ import net.minecraft.block.Block;
/*  34:    */ import net.minecraft.client.renderer.OpenGlHelper;
/*  35:    */ import net.minecraft.client.renderer.RenderGlobal;
/*  36:    */ import net.minecraft.entity.player.EntityPlayer;
/*  37:    */ import net.minecraft.inventory.IInventory;
/*  38:    */ import net.minecraft.item.ItemStack;
/*  39:    */ import net.minecraft.nbt.NBTTagCompound;
/*  40:    */ import net.minecraft.tileentity.TileEntity;
/*  41:    */ import net.minecraft.util.AxisAlignedBB;
/*  42:    */ import net.minecraft.util.IIcon;
/*  43:    */ import net.minecraft.util.MovingObjectPosition;
/*  44:    */ import net.minecraft.world.IBlockAccess;
/*  45:    */ import net.minecraft.world.World;
/*  46:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  47:    */ import org.lwjgl.opengl.GL11;
/*  48:    */ import scala.util.Random;
/*  49:    */ 
/*  50:    */ public abstract class TransferNodePart
/*  51:    */   extends MCMetaTilePart
/*  52:    */   implements INode, IPipeCosmetic, ISidedHollowConnect, TSlottedPart, TFacePart
/*  53:    */ {
/*  54: 51 */   public static Random rand = new Random();
/*  55: 52 */   private static DummyPipePart[] dummyPipes = new DummyPipePart[6];
/*  56:    */   public TileEntityTransferNode node;
/*  57:    */   
/*  58:    */   static
/*  59:    */   {
/*  60: 55 */     for (int i = 0; i < 6; i++) {
/*  61: 56 */       dummyPipes[i] = new DummyPipePart(i, 0.625F);
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65: 61 */   public boolean valid = true;
/*  66: 63 */   public boolean init = false;
/*  67: 64 */   public int blockMasks = -1;
/*  68: 65 */   public byte[] flagmasks = { 1, 2, 4, 8, 16, 32 };
/*  69: 66 */   int id = rand.nextInt();
/*  70:    */   
/*  71:    */   public TransferNodePart(int meta, TileEntityTransferNode node)
/*  72:    */   {
/*  73: 69 */     super(meta);
/*  74: 70 */     this.node = node;
/*  75: 71 */     node.blockMetadata = meta;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public TransferNodePart(TileEntityTransferNode node)
/*  79:    */   {
/*  80: 76 */     this.node = node;
/*  81: 77 */     node.blockMetadata = this.meta;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public TileEntity getBlockTile()
/*  85:    */   {
/*  86: 82 */     return tile();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int getHollowSize(int side)
/*  90:    */   {
/*  91: 87 */     return 6;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Iterable<ItemStack> getDrops()
/*  95:    */   {
/*  96: 92 */     return Arrays.asList(new ItemStack[] { new ItemStack(getBlock(), 1, getBlock().damageDropped(this.meta)) });
/*  97:    */   }
/*  98:    */   
/*  99:    */   public ItemStack pickItem(MovingObjectPosition hit)
/* 100:    */   {
/* 101: 97 */     return new ItemStack(getBlock(), 1, getBlock().damageDropped(getMetadata()));
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void bufferChanged()
/* 105:    */   {
/* 106:102 */     getNode().bufferChanged();
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean activate(EntityPlayer player, MovingObjectPosition part, ItemStack item)
/* 110:    */   {
/* 111:107 */     if (getWorld().isClient) {
/* 112:108 */       return true;
/* 113:    */     }
/* 114:111 */     if (XUHelper.isWrench(item))
/* 115:    */     {
/* 116:112 */       int newmetadata = StdPipes.getNextPipeType(getWorld(), part.blockX, part.blockY, part.blockZ, getNode().pipe_type);
/* 117:113 */       getNode().pipe_type = ((byte)newmetadata);
/* 118:114 */       sendDescUpdate();
/* 119:    */       
/* 120:116 */       return true;
/* 121:    */     }
/* 122:119 */     player.openGui(ExtraUtilsMod.instance, 0, getWorld(), x(), y(), z());
/* 123:120 */     return true;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void onRemoved()
/* 127:    */   {
/* 128:125 */     if (!getWorld().isClient)
/* 129:    */     {
/* 130:126 */       List<ItemStack> drops = new ArrayList();
/* 131:128 */       for (int i = 0; i < this.node.upgrades.getSizeInventory(); i++) {
/* 132:129 */         if (this.node.upgrades.getStackInSlot(i) != null) {
/* 133:130 */           drops.add(this.node.upgrades.getStackInSlot(i));
/* 134:    */         }
/* 135:    */       }
/* 136:133 */       tile().dropItems(drops);
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void onWorldJoin()
/* 141:    */   {
/* 142:139 */     if (getWorld() != null) {
/* 143:140 */       this.node.setWorldObj(getWorld());
/* 144:    */     }
/* 145:143 */     this.node.xCoord = x();
/* 146:144 */     this.node.yCoord = y();
/* 147:145 */     this.node.zCoord = z();
/* 148:146 */     this.node.onWorldJoin();
/* 149:147 */     reloadBlockMasks();
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void onWorldSeparate()
/* 153:    */   {
/* 154:152 */     this.node.invalidate();
/* 155:    */   }
/* 156:    */   
/* 157:    */   public Iterable<Cuboid6> getCollisionBoxes()
/* 158:    */   {
/* 159:157 */     ArrayList<AxisAlignedBB> t = new ArrayList();
/* 160:158 */     ArrayList<Cuboid6> t2 = new ArrayList();
/* 161:159 */     ExtraUtils.transferNode.addCollisionBoxesToList(getWorld(), x(), y(), z(), AxisAlignedBB.getBoundingBox(x(), y(), z(), x() + 1, y() + 1, z() + 1), t, null);
/* 162:162 */     for (AxisAlignedBB aT : t) {
/* 163:163 */       t2.add(new Cuboid6(aT.minX, aT.minY, aT.minZ, aT.maxX, aT.maxY, aT.maxZ).sub(new Vector3(x(), y(), z())));
/* 164:    */     }
/* 165:166 */     return t2;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void save(NBTTagCompound tag)
/* 169:    */   {
/* 170:171 */     super.save(tag);
/* 171:172 */     NBTTagCompound subtag = new NBTTagCompound();
/* 172:173 */     this.node.writeToNBT(subtag);
/* 173:174 */     tag.setTag("node", subtag);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void load(NBTTagCompound tag)
/* 177:    */   {
/* 178:179 */     super.load(tag);
/* 179:180 */     this.node.readFromNBT(tag.getCompoundTag("node"));
/* 180:    */   }
/* 181:    */   
/* 182:    */   public boolean doesTick()
/* 183:    */   {
/* 184:185 */     return true;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void update()
/* 188:    */   {
/* 189:190 */     if ((this.node != null) && (!world().isClient))
/* 190:    */     {
/* 191:191 */       this.node.blockMetadata = this.meta;
/* 192:193 */       if (getWorld().getTileEntity(x(), y(), z()) == tile())
/* 193:    */       {
/* 194:194 */         if (this.node.getWorldObj() == null) {
/* 195:195 */           onWorldJoin();
/* 196:    */         }
/* 197:198 */         this.node.updateEntity();
/* 198:    */       }
/* 199:    */     }
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void writeDesc(MCDataOutput packet)
/* 203:    */   {
/* 204:205 */     packet.writeByte(this.meta);
/* 205:206 */     packet.writeByte(this.node.pipe_type);
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void readDesc(MCDataInput packet)
/* 209:    */   {
/* 210:211 */     this.meta = packet.readByte();
/* 211:212 */     this.node.pipe_type = packet.readByte();
/* 212:    */   }
/* 213:    */   
/* 214:    */   public Block getBlock()
/* 215:    */   {
/* 216:217 */     return ExtraUtils.transferNode;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public int getNodeX()
/* 220:    */   {
/* 221:222 */     return this.node.getNodeX();
/* 222:    */   }
/* 223:    */   
/* 224:    */   public int getNodeY()
/* 225:    */   {
/* 226:227 */     return this.node.getNodeY();
/* 227:    */   }
/* 228:    */   
/* 229:    */   public int getNodeZ()
/* 230:    */   {
/* 231:232 */     return this.node.getNodeZ();
/* 232:    */   }
/* 233:    */   
/* 234:    */   public ForgeDirection getNodeDir()
/* 235:    */   {
/* 236:237 */     this.node.blockMetadata = this.meta;
/* 237:238 */     return this.node.getNodeDir();
/* 238:    */   }
/* 239:    */   
/* 240:    */   public int getPipeX()
/* 241:    */   {
/* 242:243 */     return this.node.getPipeX();
/* 243:    */   }
/* 244:    */   
/* 245:    */   public int getPipeY()
/* 246:    */   {
/* 247:248 */     return this.node.getPipeY();
/* 248:    */   }
/* 249:    */   
/* 250:    */   public int getPipeZ()
/* 251:    */   {
/* 252:253 */     return this.node.getPipeZ();
/* 253:    */   }
/* 254:    */   
/* 255:    */   public int getPipeDir()
/* 256:    */   {
/* 257:258 */     return this.node.getPipeDir();
/* 258:    */   }
/* 259:    */   
/* 260:    */   public List<ItemStack> getUpgrades()
/* 261:    */   {
/* 262:263 */     return this.node.getUpgrades();
/* 263:    */   }
/* 264:    */   
/* 265:    */   public boolean checkRedstone()
/* 266:    */   {
/* 267:268 */     return this.node.checkRedstone();
/* 268:    */   }
/* 269:    */   
/* 270:    */   public BoxModel getModel(ForgeDirection dir)
/* 271:    */   {
/* 272:273 */     return this.node.getModel(dir);
/* 273:    */   }
/* 274:    */   
/* 275:    */   public String getNodeType()
/* 276:    */   {
/* 277:278 */     return this.node.getNodeType();
/* 278:    */   }
/* 279:    */   
/* 280:    */   public boolean transferItems(IBlockAccess world, int x, int y, int z, ForgeDirection dir, INodeBuffer buffer)
/* 281:    */   {
/* 282:283 */     return getNode().transferItems(world, x, y, z, dir, buffer);
/* 283:    */   }
/* 284:    */   
/* 285:    */   public boolean canInput(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
/* 286:    */   {
/* 287:288 */     return (!isBlocked(dir)) && (getNode().canInput(world, x, y, z, dir));
/* 288:    */   }
/* 289:    */   
/* 290:    */   public ArrayList<ForgeDirection> getOutputDirections(IBlockAccess world, int x, int y, int z, ForgeDirection dir, INodeBuffer buffer)
/* 291:    */   {
/* 292:294 */     return getNode().getOutputDirections(world, x, y, z, dir, buffer);
/* 293:    */   }
/* 294:    */   
/* 295:    */   public boolean canOutput(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
/* 296:    */   {
/* 297:299 */     return (!isBlocked(dir)) && (getNode().canOutput(world, x, y, z, dir));
/* 298:    */   }
/* 299:    */   
/* 300:    */   public int limitTransfer(TileEntity dest, ForgeDirection side, INodeBuffer buffer)
/* 301:    */   {
/* 302:305 */     return getNode().limitTransfer(dest, side, buffer);
/* 303:    */   }
/* 304:    */   
/* 305:    */   public IInventory getFilterInventory(IBlockAccess world, int x, int y, int z)
/* 306:    */   {
/* 307:310 */     return getNode().getFilterInventory(world, x, y, z);
/* 308:    */   }
/* 309:    */   
/* 310:    */   public boolean shouldConnectToTile(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
/* 311:    */   {
/* 312:315 */     return (!isBlocked(dir)) && (getNode().shouldConnectToTile(world, x, y, z, dir));
/* 313:    */   }
/* 314:    */   
/* 315:    */   public void reloadBlockMasks()
/* 316:    */   {
/* 317:320 */     this.blockMasks = 0;
/* 318:322 */     for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
/* 319:    */     {
/* 320:323 */       dummyPipes[dir.ordinal()].h = (0.5F + baseSize());
/* 321:325 */       if (dir == getNodeDir()) {
/* 322:326 */         this.blockMasks |= this.flagmasks[dir.ordinal()];
/* 323:327 */       } else if (!tile().canAddPart(dummyPipes[dir.ordinal()])) {
/* 324:328 */         this.blockMasks |= this.flagmasks[dir.ordinal()];
/* 325:    */       }
/* 326:    */     }
/* 327:    */   }
/* 328:    */   
/* 329:    */   public void onPartChanged(TMultiPart part)
/* 330:    */   {
/* 331:335 */     reloadBlockMasks();
/* 332:    */   }
/* 333:    */   
/* 334:    */   public void onNeighborChanged()
/* 335:    */   {
/* 336:343 */     this.node.updateRedstone();
/* 337:344 */     reloadBlockMasks();
/* 338:    */   }
/* 339:    */   
/* 340:    */   public boolean isPowered()
/* 341:    */   {
/* 342:349 */     return this.node.isPowered();
/* 343:    */   }
/* 344:    */   
/* 345:    */   public boolean recalcRedstone()
/* 346:    */   {
/* 347:354 */     if ((tile() instanceof TRedstoneTile))
/* 348:    */     {
/* 349:355 */       TRedstoneTile rsT = (TRedstoneTile)tile();
/* 350:356 */       for (int side = 0; side < 6; side++) {
/* 351:357 */         if (rsT.weakPowerLevel(side) > 0) {
/* 352:358 */           return true;
/* 353:    */         }
/* 354:    */       }
/* 355:    */     }
/* 356:361 */     for (int side = 0; side < 6; side++) {
/* 357:362 */       if (RedstoneInteractions.getPowerTo(world(), x(), y(), z(), side, 31) > 0) {
/* 358:363 */         return true;
/* 359:    */       }
/* 360:    */     }
/* 361:365 */     return false;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public boolean isBlocked(ForgeDirection dir)
/* 365:    */   {
/* 366:369 */     if (this.node.getWorldObj() == null) {
/* 367:370 */       onWorldJoin();
/* 368:    */     }
/* 369:373 */     if (this.blockMasks < 0) {
/* 370:374 */       reloadBlockMasks();
/* 371:    */     }
/* 372:377 */     return (this.blockMasks & this.flagmasks[dir.ordinal()]) == this.flagmasks[dir.ordinal()];
/* 373:    */   }
/* 374:    */   
/* 375:    */   public IIcon baseTexture()
/* 376:    */   {
/* 377:382 */     return getNode().baseTexture();
/* 378:    */   }
/* 379:    */   
/* 380:    */   public IIcon pipeTexture(ForgeDirection dir, boolean blocked)
/* 381:    */   {
/* 382:387 */     return getNode().pipeTexture(dir, blocked);
/* 383:    */   }
/* 384:    */   
/* 385:    */   public IIcon invPipeTexture(ForgeDirection dir)
/* 386:    */   {
/* 387:392 */     return getNode().invPipeTexture(dir);
/* 388:    */   }
/* 389:    */   
/* 390:    */   public IIcon socketTexture(ForgeDirection dir)
/* 391:    */   {
/* 392:397 */     return getNode().socketTexture(dir);
/* 393:    */   }
/* 394:    */   
/* 395:    */   public String getPipeType()
/* 396:    */   {
/* 397:402 */     return getNode().getPipeType();
/* 398:    */   }
/* 399:    */   
/* 400:    */   public float baseSize()
/* 401:    */   {
/* 402:407 */     return getNode().baseSize();
/* 403:    */   }
/* 404:    */   
/* 405:    */   public boolean occlusionTest(TMultiPart npart)
/* 406:    */   {
/* 407:412 */     return ((npart instanceof DummyPipePart)) || (super.occlusionTest(npart));
/* 408:    */   }
/* 409:    */   
/* 410:    */   public final Cuboid6 getBounds()
/* 411:    */   {
/* 412:418 */     Box bounds = ((BlockTransferNode)getBlock()).getWorldModel(getWorld(), x(), y(), z()).boundingBox();
/* 413:419 */     return new Cuboid6(bounds.minX, bounds.minY, bounds.minZ, bounds.maxX, bounds.maxY, bounds.maxZ);
/* 414:    */   }
/* 415:    */   
/* 416:    */   public final HashSet<IndexedCuboid6> getSubParts()
/* 417:    */   {
/* 418:424 */     HashSet<IndexedCuboid6> boxes = new HashSet();
/* 419:425 */     for (Box bounds : ((BlockTransferNode)getBlock()).getWorldModel(getWorld(), x(), y(), z())) {
/* 420:426 */       boxes.add(new IndexedCuboid6(Integer.valueOf(0), new Cuboid6(bounds.minX, bounds.minY, bounds.minZ, bounds.maxX, bounds.maxY, bounds.maxZ)));
/* 421:    */     }
/* 422:428 */     return boxes;
/* 423:    */   }
/* 424:    */   
/* 425:    */   @SideOnly(Side.CLIENT)
/* 426:    */   public boolean drawHighlight(MovingObjectPosition hit, EntityPlayer player, float frame)
/* 427:    */   {
/* 428:434 */     GL11.glEnable(3042);
/* 429:435 */     OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 430:436 */     GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
/* 431:437 */     GL11.glLineWidth(2.0F);
/* 432:438 */     GL11.glDisable(3553);
/* 433:439 */     GL11.glDepthMask(false);
/* 434:440 */     float f1 = 0.002F;
/* 435:    */     
/* 436:442 */     double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * frame;
/* 437:443 */     double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * frame;
/* 438:444 */     double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * frame;
/* 439:    */     
/* 440:446 */     RenderGlobal.drawOutlinedBoundingBox(getBounds().add(new Vector3(x(), y(), z())).toAABB().expand(f1, f1, f1).getOffsetBoundingBox(-d0, -d1, -d2), -1);
/* 441:    */     
/* 442:448 */     GL11.glDepthMask(true);
/* 443:449 */     GL11.glEnable(3553);
/* 444:450 */     GL11.glDisable(3042);
/* 445:451 */     return true;
/* 446:    */   }
/* 447:    */   
/* 448:    */   public int getSlotMask()
/* 449:    */   {
/* 450:457 */     if (getNode().getNodeDir() == ForgeDirection.UNKNOWN) {
/* 451:458 */       return 64;
/* 452:    */     }
/* 453:460 */     return 0x40 | 1 << getNode().getNodeDir().ordinal();
/* 454:    */   }
/* 455:    */   
/* 456:    */   public int redstoneConductionMap()
/* 457:    */   {
/* 458:466 */     return 0;
/* 459:    */   }
/* 460:    */   
/* 461:    */   public boolean solid(int arg0)
/* 462:    */   {
/* 463:471 */     return false;
/* 464:    */   }
/* 465:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.multiparts.TransferNodePart
 * JD-Core Version:    0.7.0.1
 */