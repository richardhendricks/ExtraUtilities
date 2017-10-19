/*   1:    */ package com.rwtema.extrautils.tileentity.endercollector;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   4:    */ import com.rwtema.extrautils.helper.XUHelper.NBTIds;
/*   5:    */ import com.rwtema.extrautils.helper.XURandom;
/*   6:    */ import com.rwtema.extrautils.item.ItemNodeUpgrade;
/*   7:    */ import com.rwtema.extrautils.network.NetworkHandler;
/*   8:    */ import com.rwtema.extrautils.network.packets.PacketParticleCurve;
/*   9:    */ import com.rwtema.extrautils.network.packets.PacketParticleLine;
/*  10:    */ import com.rwtema.extrautils.network.packets.PacketTempChat;
/*  11:    */ import java.util.LinkedList;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.ListIterator;
/*  14:    */ import java.util.Random;
/*  15:    */ import net.minecraft.block.Block;
/*  16:    */ import net.minecraft.entity.DataWatcher;
/*  17:    */ import net.minecraft.entity.Entity;
/*  18:    */ import net.minecraft.entity.item.EntityItem;
/*  19:    */ import net.minecraft.entity.player.EntityPlayer;
/*  20:    */ import net.minecraft.inventory.IInventory;
/*  21:    */ import net.minecraft.item.ItemStack;
/*  22:    */ import net.minecraft.nbt.NBTTagCompound;
/*  23:    */ import net.minecraft.nbt.NBTTagList;
/*  24:    */ import net.minecraft.network.NetworkManager;
/*  25:    */ import net.minecraft.network.Packet;
/*  26:    */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*  27:    */ import net.minecraft.tileentity.TileEntity;
/*  28:    */ import net.minecraft.util.AxisAlignedBB;
/*  29:    */ import net.minecraft.util.ChatComponentText;
/*  30:    */ import net.minecraft.util.Vec3;
/*  31:    */ import net.minecraft.world.World;
/*  32:    */ import net.minecraft.world.WorldProvider;
/*  33:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  34:    */ 
/*  35:    */ public class TileEnderCollector
/*  36:    */   extends TileEntity
/*  37:    */ {
/*  38: 35 */   int range = 8;
/*  39:    */   AxisAlignedBB bounds;
/*  40: 37 */   boolean isStuffed = false;
/*  41: 38 */   boolean init = false;
/*  42:    */   
/*  43:    */   public void setRange(int r)
/*  44:    */   {
/*  45: 45 */     this.range = r;
/*  46: 46 */     this.bounds = AxisAlignedBB.getBoundingBox(this.xCoord - r / 2.0F, this.yCoord - r / 2.0F, this.zCoord - r / 2.0F, this.xCoord + r / 2.0F + 1.0F, this.yCoord + r / 2.0F + 1.0F, this.zCoord + r / 2.0F + 1.0F);
/*  47:    */   }
/*  48:    */   
/*  49: 56 */   LinkedList<ItemStack> items = new LinkedList();
/*  50:    */   
/*  51:    */   public void updateEntity()
/*  52:    */   {
/*  53: 60 */     if (this.worldObj.isClient) {
/*  54: 61 */       return;
/*  55:    */     }
/*  56: 63 */     this.init = true;
/*  57: 65 */     if ((!this.isStuffed) && (this.worldObj.getTotalWorldTime() % 40L == 0L))
/*  58:    */     {
/*  59: 66 */       if (this.bounds == null) {
/*  60: 66 */         setRange(this.range);
/*  61:    */       }
/*  62: 68 */       List<EntityItem> entitiesWithinAABB = this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.bounds);
/*  63: 69 */       for (EntityItem entityItem : entitiesWithinAABB) {
/*  64: 70 */         grabEntity(entityItem);
/*  65:    */       }
/*  66:    */     }
/*  67: 74 */     if (!this.items.isEmpty())
/*  68:    */     {
/*  69: 75 */       if ((this.isStuffed) && (this.worldObj.getTotalWorldTime() % 10L != 0L)) {
/*  70: 76 */         return;
/*  71:    */       }
/*  72: 78 */       int side = getBlockMetadata() % 6;
/*  73: 79 */       ListIterator<ItemStack> iter = this.items.listIterator();
/*  74: 80 */       TileEntity tileEntity = this.worldObj.getTileEntity(this.xCoord + net.minecraft.util.Facing.offsetsXForSide[side], this.yCoord + net.minecraft.util.Facing.offsetsYForSide[side], this.zCoord + net.minecraft.util.Facing.offsetsZForSide[side]);
/*  75: 85 */       if ((tileEntity instanceof IInventory))
/*  76:    */       {
/*  77: 86 */         IInventory inventory = (IInventory)tileEntity;
/*  78: 88 */         while (iter.hasNext())
/*  79:    */         {
/*  80: 89 */           ItemStack next = (ItemStack)iter.next();
/*  81: 90 */           next = XUHelper.invInsert(inventory, next.copy(), side ^ 0x1);
/*  82: 92 */           if (next != null) {
/*  83: 92 */             iter.set(next);
/*  84:    */           } else {
/*  85: 93 */             iter.remove();
/*  86:    */           }
/*  87:    */         }
/*  88:    */       }
/*  89:    */     }
/*  90: 98 */     this.isStuffed = (!this.items.isEmpty());
/*  91:    */     
/*  92:100 */     updateMeta();
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void updateMeta()
/*  96:    */   {
/*  97:104 */     int oldMeta = getBlockMetadata();
/*  98:105 */     int newMeta = oldMeta % 6 + (this.isStuffed ? 6 : 0);
/*  99:106 */     if (newMeta != oldMeta) {
/* 100:107 */       changeMeta(newMeta);
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void changeMeta(int newMeta)
/* 105:    */   {
/* 106:111 */     this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, newMeta, 2);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean inRange(Entity entity)
/* 110:    */   {
/* 111:115 */     if ((this.isStuffed) || (!this.init) || (this.tileEntityInvalid)) {
/* 112:115 */       return false;
/* 113:    */     }
/* 114:116 */     if (this.bounds == null) {
/* 115:116 */       setRange(this.range);
/* 116:    */     }
/* 117:117 */     return this.bounds.intersectsWith(entity.boundingBox);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void invalidate()
/* 121:    */   {
/* 122:122 */     super.invalidate();
/* 123:123 */     CollectorHandler.unregister(this);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void onChunkUnload()
/* 127:    */   {
/* 128:128 */     CollectorHandler.unregister(this);
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setWorldObj(World p_145834_1_)
/* 132:    */   {
/* 133:133 */     super.setWorldObj(p_145834_1_);
/* 134:134 */     if (!this.worldObj.isClient) {
/* 135:135 */       CollectorHandler.register(this);
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void grabEntity(EntityItem entity)
/* 140:    */   {
/* 141:139 */     if (entity.isDead) {
/* 142:140 */       return;
/* 143:    */     }
/* 144:142 */     int side = getBlockMetadata() % 6;
/* 145:143 */     if (this.worldObj.isAirBlock(this.xCoord + net.minecraft.util.Facing.offsetsXForSide[side], this.yCoord + net.minecraft.util.Facing.offsetsYForSide[side], this.zCoord + net.minecraft.util.Facing.offsetsZForSide[side])) {
/* 146:147 */       return;
/* 147:    */     }
/* 148:149 */     TileEntity tileEntity = this.worldObj.getTileEntity(this.xCoord + net.minecraft.util.Facing.offsetsXForSide[side], this.yCoord + net.minecraft.util.Facing.offsetsYForSide[side], this.zCoord + net.minecraft.util.Facing.offsetsZForSide[side]);
/* 149:154 */     if (!(tileEntity instanceof IInventory)) {
/* 150:155 */       return;
/* 151:    */     }
/* 152:157 */     IInventory inv = (IInventory)tileEntity;
/* 153:    */     
/* 154:159 */     ItemStack stack = entity.getDataWatcher().getWatchableObjectItemStack(10);
/* 155:160 */     if (stack == null) {
/* 156:161 */       return;
/* 157:    */     }
/* 158:163 */     if ((this.filter != null) && 
/* 159:164 */       (!ItemNodeUpgrade.matchesFilterItem(stack, this.filter))) {
/* 160:165 */       return;
/* 161:    */     }
/* 162:168 */     ItemStack itemStack = XUHelper.simInvInsert(inv, stack, side ^ 0x1);
/* 163:169 */     if ((itemStack != null) && (itemStack.stackSize == stack.stackSize)) {
/* 164:170 */       return;
/* 165:    */     }
/* 166:172 */     this.items.add(stack);
/* 167:173 */     signalChange(entity);
/* 168:174 */     entity.setEntityItemStack(null);
/* 169:175 */     entity.setDead();
/* 170:    */   }
/* 171:    */   
/* 172:178 */   private static final Vec3[] sides = { Vec3.createVectorHelper(0.0D, -1.0D, 0.0D), Vec3.createVectorHelper(0.0D, 1.0D, 0.0D), Vec3.createVectorHelper(0.0D, 0.0D, -1.0D), Vec3.createVectorHelper(0.0D, 0.0D, 1.0D), Vec3.createVectorHelper(-1.0D, 0.0D, 0.0D), Vec3.createVectorHelper(1.0D, 0.0D, 0.0D) };
/* 173:    */   ItemStack filter;
/* 174:    */   
/* 175:    */   public void signalChange(EntityItem item)
/* 176:    */   {
/* 177:188 */     int side = getBlockMetadata() % 6;
/* 178:    */     
/* 179:    */ 
/* 180:191 */     NetworkHandler.sendToAllAround(new PacketParticleCurve(item, Vec3.createVectorHelper(this.xCoord + 0.5D - ForgeDirection.getOrientation(side).offsetX * 0.4D, this.yCoord + 0.5D - ForgeDirection.getOrientation(side).offsetY * 0.4D, this.zCoord + 0.5D - ForgeDirection.getOrientation(side).offsetZ * 0.4D), sides[side]), this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 32.0D);
/* 181:    */   }
/* 182:    */   
/* 183:    */   public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y, int z)
/* 184:    */   {
/* 185:205 */     return newBlock != oldBlock;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void readFromNBT(NBTTagCompound tag)
/* 189:    */   {
/* 190:210 */     super.readFromNBT(tag);
/* 191:211 */     this.range = tag.getByte("Range");
/* 192:212 */     NBTTagList tagList = tag.getTagList("Items", XUHelper.NBTIds.NBT.id);
/* 193:213 */     for (int i = 0; i < tagList.tagCount(); i++)
/* 194:    */     {
/* 195:214 */       ItemStack itemStack = ItemStack.loadItemStackFromNBT(tagList.getCompoundTagAt(i));
/* 196:215 */       if (itemStack != null) {
/* 197:216 */         this.items.add(itemStack);
/* 198:    */       }
/* 199:    */     }
/* 200:219 */     if (tag.hasKey("Filter")) {
/* 201:220 */       this.filter = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Filter"));
/* 202:    */     }
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void writeToNBT(NBTTagCompound tag)
/* 206:    */   {
/* 207:227 */     super.writeToNBT(tag);
/* 208:228 */     NBTTagList list = new NBTTagList();
/* 209:229 */     for (ItemStack item : this.items) {
/* 210:230 */       list.appendTag(item.writeToNBT(new NBTTagCompound()));
/* 211:    */     }
/* 212:232 */     tag.setTag("Items", list);
/* 213:233 */     tag.setByte("Range", (byte)this.range);
/* 214:235 */     if (this.filter != null) {
/* 215:236 */       tag.setTag("Filter", this.filter.writeToNBT(new NBTTagCompound()));
/* 216:    */     }
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void onNeighbourChange() {}
/* 220:    */   
/* 221:    */   public void drawLine(Vec3 a, Vec3 b)
/* 222:    */   {
/* 223:245 */     NetworkHandler.sendToAllAround(new PacketParticleLine(a, b), this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 32.0D);
/* 224:    */   }
/* 225:    */   
/* 226:    */   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
/* 227:    */   {
/* 228:249 */     if (world.isClient) {
/* 229:250 */       return true;
/* 230:    */     }
/* 231:251 */     if (this.isStuffed)
/* 232:    */     {
/* 233:252 */       dropItems();
/* 234:253 */       return true;
/* 235:    */     }
/* 236:257 */     ItemStack heldItem = player.getHeldItem();
/* 237:258 */     if (heldItem != null)
/* 238:    */     {
/* 239:259 */       if ((this.filter == null) && (ItemNodeUpgrade.isFilter(heldItem)))
/* 240:    */       {
/* 241:260 */         this.filter = heldItem.copy();
/* 242:261 */         heldItem.stackSize = 0;
/* 243:262 */         this.worldObj.markBlockForUpdate(x, y, z);
/* 244:263 */         return true;
/* 245:    */       }
/* 246:264 */       if ((this.filter != null) && (XUHelper.isWrench(heldItem)))
/* 247:    */       {
/* 248:    */         try
/* 249:    */         {
/* 250:266 */           CollectorHandler.dontCollect = true;
/* 251:267 */           dropItem(this.filter.copy());
/* 252:    */         }
/* 253:    */         finally
/* 254:    */         {
/* 255:269 */           CollectorHandler.dontCollect = false;
/* 256:    */         }
/* 257:272 */         this.filter = null;
/* 258:273 */         this.worldObj.markBlockForUpdate(x, y, z);
/* 259:274 */         return true;
/* 260:    */       }
/* 261:    */     }
/* 262:279 */     if (!player.isSneaking())
/* 263:    */     {
/* 264:280 */       this.range += 1;
/* 265:281 */       if (this.range > 8) {
/* 266:281 */         this.range = 1;
/* 267:    */       }
/* 268:    */     }
/* 269:    */     else
/* 270:    */     {
/* 271:283 */       this.range -= 1;
/* 272:284 */       if (this.range < 1) {
/* 273:284 */         this.range = 8;
/* 274:    */       }
/* 275:    */     }
/* 276:287 */     setRange(this.range);
/* 277:288 */     drawCube(this.bounds);
/* 278:    */     
/* 279:290 */     PacketTempChat.sendChat(player, new ChatComponentText(String.format("Range: %s  (%s, %s, %s -> %s, %s, %s)", new Object[] { Float.valueOf(this.range / 2.0F), Double.valueOf(this.bounds.minX), Double.valueOf(this.bounds.minY), Double.valueOf(this.bounds.minZ), Double.valueOf(this.bounds.maxX), Double.valueOf(this.bounds.maxY), Double.valueOf(this.bounds.maxZ) })));
/* 280:    */     
/* 281:292 */     return true;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void drawCube(AxisAlignedBB b)
/* 285:    */   {
/* 286:296 */     double x0 = b.minX;double x1 = b.maxX;
/* 287:297 */     double y0 = b.minY;double y1 = b.maxY;
/* 288:298 */     double z0 = b.minZ;double z1 = b.maxZ;
/* 289:299 */     Vec3 p000 = Vec3.createVectorHelper(x0, y0, z0);
/* 290:300 */     Vec3 p001 = Vec3.createVectorHelper(x0, y0, z1);
/* 291:301 */     Vec3 p010 = Vec3.createVectorHelper(x0, y1, z0);
/* 292:302 */     Vec3 p011 = Vec3.createVectorHelper(x0, y1, z1);
/* 293:303 */     Vec3 p100 = Vec3.createVectorHelper(x1, y0, z0);
/* 294:304 */     Vec3 p101 = Vec3.createVectorHelper(x1, y0, z1);
/* 295:305 */     Vec3 p110 = Vec3.createVectorHelper(x1, y1, z0);
/* 296:306 */     Vec3 p111 = Vec3.createVectorHelper(x1, y1, z1);
/* 297:    */     
/* 298:308 */     Vec3 center = Vec3.createVectorHelper((x0 + x1) / 2.0D, (y0 + y1) / 2.0D, (z0 + z1) / 2.0D);
/* 299:    */     
/* 300:310 */     drawLine(p000, p001);
/* 301:311 */     drawLine(p000, p010);
/* 302:312 */     drawLine(p000, p100);
/* 303:313 */     drawLine(p001, p011);
/* 304:314 */     drawLine(p001, p101);
/* 305:315 */     drawLine(p010, p011);
/* 306:316 */     drawLine(p010, p110);
/* 307:317 */     drawLine(p100, p101);
/* 308:318 */     drawLine(p100, p110);
/* 309:319 */     drawLine(p011, p111);
/* 310:320 */     drawLine(p101, p111);
/* 311:321 */     drawLine(p110, p111);
/* 312:    */     
/* 313:323 */     drawLine(center, p000);
/* 314:324 */     drawLine(center, p001);
/* 315:325 */     drawLine(center, p010);
/* 316:326 */     drawLine(center, p011);
/* 317:327 */     drawLine(center, p100);
/* 318:328 */     drawLine(center, p101);
/* 319:329 */     drawLine(center, p110);
/* 320:330 */     drawLine(center, p111);
/* 321:    */   }
/* 322:    */   
/* 323:333 */   private Random rand = XURandom.getInstance();
/* 324:    */   
/* 325:    */   public void dropItems()
/* 326:    */   {
/* 327:    */     try
/* 328:    */     {
/* 329:337 */       CollectorHandler.dontCollect = true;
/* 330:338 */       for (ItemStack itemstack : this.items) {
/* 331:339 */         dropItem(itemstack);
/* 332:    */       }
/* 333:    */     }
/* 334:    */     finally
/* 335:    */     {
/* 336:342 */       CollectorHandler.dontCollect = false;
/* 337:    */     }
/* 338:344 */     this.items.clear();
/* 339:345 */     this.isStuffed = false;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public void dropItem(ItemStack itemstack)
/* 343:    */   {
/* 344:349 */     float dx = this.rand.nextFloat() * 0.8F + 0.1F;
/* 345:350 */     float dy = this.rand.nextFloat() * 0.8F + 0.1F;
/* 346:351 */     float dz = this.rand.nextFloat() * 0.8F + 0.1F;
/* 347:354 */     while (itemstack.stackSize > 0)
/* 348:    */     {
/* 349:355 */       int j1 = this.rand.nextInt(21) + 10;
/* 350:357 */       if (j1 > itemstack.stackSize) {
/* 351:358 */         j1 = itemstack.stackSize;
/* 352:    */       }
/* 353:361 */       itemstack.stackSize -= j1;
/* 354:362 */       EntityItem entityitem = new EntityItem(this.worldObj, this.xCoord + dx, this.yCoord + dy, this.zCoord + dz, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
/* 355:363 */       if (itemstack.hasTagCompound()) {
/* 356:364 */         entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
/* 357:    */       }
/* 358:366 */       entityitem.motionX = (this.rand.nextGaussian() * 0.0500000007450581D);
/* 359:367 */       entityitem.motionY = (this.rand.nextGaussian() * 0.0500000007450581D + 0.2000000029802322D);
/* 360:368 */       entityitem.motionZ = (this.rand.nextGaussian() * 0.0500000007450581D);
/* 361:    */       
/* 362:370 */       this.worldObj.spawnEntityInWorld(entityitem);
/* 363:    */     }
/* 364:    */   }
/* 365:    */   
/* 366:    */   public Packet getDescriptionPacket()
/* 367:    */   {
/* 368:376 */     NBTTagCompound t = new NBTTagCompound();
/* 369:377 */     if (this.filter != null) {
/* 370:378 */       t.setTag("Filter", this.filter.writeToNBT(new NBTTagCompound()));
/* 371:    */     }
/* 372:379 */     return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 4, t);
/* 373:    */   }
/* 374:    */   
/* 375:    */   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
/* 376:    */   {
/* 377:384 */     super.onDataPacket(net, pkt);
/* 378:385 */     NBTTagCompound tag = pkt.func_148857_g();
/* 379:386 */     if (tag.hasKey("Filter")) {
/* 380:387 */       this.filter = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Filter"));
/* 381:    */     } else {
/* 382:389 */       this.filter = null;
/* 383:    */     }
/* 384:    */   }
/* 385:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.endercollector.TileEnderCollector
 * JD-Core Version:    0.7.0.1
 */