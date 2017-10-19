/*    1:     */ package com.rwtema.extrautils.tileentity.enderquarry;
/*    2:     */ 
/*    3:     */ import cofh.api.energy.EnergyStorage;
/*    4:     */ import cofh.api.energy.IEnergyHandler;
/*    5:     */ import com.rwtema.extrautils.EventHandlerEntityItemStealer;
/*    6:     */ import com.rwtema.extrautils.ExtraUtils;
/*    7:     */ import com.rwtema.extrautils.ExtraUtilsMod;
/*    8:     */ import com.rwtema.extrautils.crafting.RecipeEnchantCrafting;
/*    9:     */ import com.rwtema.extrautils.helper.XUHelper;
/*   10:     */ import com.rwtema.extrautils.helper.XURandom;
/*   11:     */ import com.rwtema.extrautils.network.NetworkHandler;
/*   12:     */ import com.rwtema.extrautils.network.packets.PacketTempChat;
/*   13:     */ import com.rwtema.extrautils.network.packets.PacketTempChatMultiline;
/*   14:     */ import java.util.ArrayList;
/*   15:     */ import java.util.Collection;
/*   16:     */ import java.util.Random;
/*   17:     */ import net.minecraft.block.Block;
/*   18:     */ import net.minecraft.block.BlockFlower;
/*   19:     */ import net.minecraft.block.IGrowable;
/*   20:     */ import net.minecraft.enchantment.Enchantment;
/*   21:     */ import net.minecraft.entity.player.EntityPlayer;
/*   22:     */ import net.minecraft.init.Blocks;
/*   23:     */ import net.minecraft.init.Items;
/*   24:     */ import net.minecraft.inventory.IInventory;
/*   25:     */ import net.minecraft.item.Item;
/*   26:     */ import net.minecraft.item.ItemStack;
/*   27:     */ import net.minecraft.nbt.NBTTagCompound;
/*   28:     */ import net.minecraft.tileentity.TileEntity;
/*   29:     */ import net.minecraft.util.ChatComponentText;
/*   30:     */ import net.minecraft.world.ChunkCoordIntPair;
/*   31:     */ import net.minecraft.world.World;
/*   32:     */ import net.minecraft.world.WorldProvider;
/*   33:     */ import net.minecraft.world.WorldServer;
/*   34:     */ import net.minecraft.world.biome.BiomeGenBase;
/*   35:     */ import net.minecraftforge.common.ForgeChunkManager;
/*   36:     */ import net.minecraftforge.common.ForgeChunkManager.Ticket;
/*   37:     */ import net.minecraftforge.common.ForgeChunkManager.Type;
/*   38:     */ import net.minecraftforge.common.IPlantable;
/*   39:     */ import net.minecraftforge.common.util.FakePlayer;
/*   40:     */ import net.minecraftforge.common.util.FakePlayerFactory;
/*   41:     */ import net.minecraftforge.common.util.ForgeDirection;
/*   42:     */ import net.minecraftforge.event.ForgeEventFactory;
/*   43:     */ import net.minecraftforge.fluids.FluidStack;
/*   44:     */ import net.minecraftforge.fluids.IFluidHandler;
/*   45:     */ 
/*   46:     */ public class TileEntityEnderQuarry
/*   47:     */   extends TileEntity
/*   48:     */   implements IEnergyHandler
/*   49:     */ {
/*   50:  47 */   private static final Random rand = ;
/*   51:  48 */   public static boolean disableSelfChunkLoading = false;
/*   52:  49 */   public static int baseDrain = 1800;
/*   53:  50 */   public static float hardnessDrain = 200.0F;
/*   54:  52 */   public ArrayList<ItemStack> items = new ArrayList();
/*   55:  53 */   public int dx = 1;
/*   56:  53 */   public int dy = 0;
/*   57:  53 */   public int dz = 0;
/*   58:  54 */   public EnergyStorage energy = new EnergyStorage(10000000);
/*   59:  55 */   public int inventoryMask = -1;
/*   60:  56 */   public int fluidMask = -1;
/*   61:  57 */   public long progress = 0L;
/*   62:  58 */   public int neededEnergy = -1;
/*   63:  59 */   public boolean started = false;
/*   64:  60 */   public boolean finished = false;
/*   65:  61 */   public FluidStack fluid = null;
/*   66:  62 */   int chunk_x = 0;
/*   67:  62 */   int chunk_z = 0;
/*   68:  63 */   int chunk_y = 0;
/*   69:  64 */   byte t = 0;
/*   70:  65 */   boolean searching = false;
/*   71:  66 */   int fence_x = this.xCoord;
/*   72:  66 */   int fence_y = this.yCoord;
/*   73:  66 */   int fence_z = this.zCoord;
/*   74:  66 */   int fence_dir = 2;
/*   75:  66 */   int fence_elev = -1;
/*   76:  67 */   int min_x = this.xCoord;
/*   77:  67 */   int max_x = this.xCoord;
/*   78:  68 */   int min_z = this.zCoord;
/*   79:  68 */   int max_z = this.zCoord;
/*   80:     */   private ForgeChunkManager.Ticket chunkTicket;
/*   81:     */   private EntityPlayer owner;
/*   82:     */   
/*   83:     */   public boolean shouldRefresh(Block oldID, Block newID, int oldMeta, int newMeta, World world, int x, int y, int z)
/*   84:     */   {
/*   85:  74 */     return oldID != newID;
/*   86:     */   }
/*   87:     */   
/*   88:     */   public void readFromNBT(NBTTagCompound tags)
/*   89:     */   {
/*   90:  82 */     super.readFromNBT(tags);
/*   91:  83 */     this.energy.readFromNBT(tags);
/*   92:  84 */     int n = tags.getInteger("item_no");
/*   93:  85 */     this.items.clear();
/*   94:  87 */     for (int i = 0; i < n; i++)
/*   95:     */     {
/*   96:  88 */       NBTTagCompound t = tags.getCompoundTag("item_" + i);
/*   97:  89 */       this.items.add(ItemStack.loadItemStackFromNBT(t));
/*   98:     */     }
/*   99:  92 */     if (tags.hasKey("fluid")) {
/*  100:  93 */       this.fluid = FluidStack.loadFluidStackFromNBT(tags.getCompoundTag("fluid"));
/*  101:     */     }
/*  102:  95 */     this.finished = tags.getBoolean("finished");
/*  103:  97 */     if (this.finished) {
/*  104:  98 */       return;
/*  105:     */     }
/*  106: 101 */     this.started = tags.getBoolean("started");
/*  107: 103 */     if (!this.started) {
/*  108: 104 */       return;
/*  109:     */     }
/*  110: 107 */     this.min_x = tags.getInteger("min_x");
/*  111: 108 */     this.min_z = tags.getInteger("min_z");
/*  112: 109 */     this.max_x = tags.getInteger("max_x");
/*  113: 110 */     this.max_z = tags.getInteger("max_z");
/*  114: 111 */     this.chunk_x = tags.getInteger("chunk_x");
/*  115: 112 */     this.chunk_y = tags.getInteger("chunk_y");
/*  116: 113 */     this.chunk_z = tags.getInteger("chunk_z");
/*  117: 114 */     this.dx = tags.getInteger("dx");
/*  118: 115 */     this.dy = tags.getInteger("dy");
/*  119: 116 */     this.dz = tags.getInteger("dz");
/*  120: 117 */     this.progress = tags.getLong("progress");
/*  121:     */   }
/*  122:     */   
/*  123:     */   public void writeToNBT(NBTTagCompound tags)
/*  124:     */   {
/*  125: 125 */     super.writeToNBT(tags);
/*  126: 126 */     this.energy.writeToNBT(tags);
/*  127: 128 */     for (int i = 0; i < this.items.size(); i++)
/*  128:     */     {
/*  129: 129 */       while ((i < this.items.size()) && (this.items.get(i) == null)) {
/*  130: 130 */         this.items.remove(i);
/*  131:     */       }
/*  132: 133 */       if (i < this.items.size())
/*  133:     */       {
/*  134: 134 */         NBTTagCompound t = new NBTTagCompound();
/*  135: 135 */         ((ItemStack)this.items.get(i)).writeToNBT(t);
/*  136: 136 */         tags.setTag("item_" + i, t);
/*  137:     */       }
/*  138:     */     }
/*  139: 140 */     tags.setInteger("item_no", this.items.size());
/*  140: 142 */     if (this.fluid != null)
/*  141:     */     {
/*  142: 143 */       NBTTagCompound t = new NBTTagCompound();
/*  143: 144 */       this.fluid.writeToNBT(t);
/*  144: 145 */       tags.setTag("fluid", t);
/*  145:     */     }
/*  146: 149 */     if (this.finished)
/*  147:     */     {
/*  148: 150 */       tags.setBoolean("finished", true);
/*  149:     */     }
/*  150: 151 */     else if (this.started)
/*  151:     */     {
/*  152: 152 */       tags.setBoolean("started", true);
/*  153: 153 */       tags.setInteger("min_x", this.min_x);
/*  154: 154 */       tags.setInteger("max_x", this.max_x);
/*  155: 155 */       tags.setInteger("min_z", this.min_z);
/*  156: 156 */       tags.setInteger("max_z", this.max_z);
/*  157: 157 */       tags.setInteger("chunk_x", this.chunk_x);
/*  158: 158 */       tags.setInteger("chunk_y", this.chunk_y);
/*  159: 159 */       tags.setInteger("chunk_z", this.chunk_z);
/*  160: 160 */       tags.setInteger("dx", this.dx);
/*  161: 161 */       tags.setInteger("dy", this.dy);
/*  162: 162 */       tags.setInteger("dz", this.dz);
/*  163: 163 */       tags.setLong("progress", this.progress);
/*  164:     */     }
/*  165:     */   }
/*  166:     */   
/*  167:     */   public void startDig()
/*  168:     */   {
/*  169: 168 */     this.started = true;
/*  170: 169 */     this.chunk_y += 5;
/*  171: 170 */     this.chunk_x = (this.min_x + 1 >> 4);
/*  172: 171 */     this.chunk_z = (this.min_z + 1 >> 4);
/*  173: 172 */     this.dx = Math.max(0, this.min_x + 1 - (this.chunk_x << 4));
/*  174: 173 */     this.dy = this.chunk_y;
/*  175: 174 */     this.dz = Math.max(0, this.min_z + 1 - (this.chunk_z << 4));
/*  176: 176 */     if (!stopHere()) {
/*  177: 177 */       nextBlock();
/*  178:     */     }
/*  179: 180 */     this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 1, 2);
/*  180:     */   }
/*  181:     */   
/*  182:     */   public void nextBlock()
/*  183:     */   {
/*  184: 184 */     nextSubBlock();
/*  185: 186 */     while (!stopHere()) {
/*  186: 187 */       nextSubBlock();
/*  187:     */     }
/*  188:     */   }
/*  189:     */   
/*  190:     */   public void nextSubBlock()
/*  191:     */   {
/*  192: 192 */     this.progress += 1L;
/*  193: 193 */     this.dy -= 1;
/*  194: 195 */     if (this.dy <= 0)
/*  195:     */     {
/*  196: 196 */       this.dx += 1;
/*  197: 198 */       if ((this.dx >= 16) || ((this.chunk_x << 4) + this.dx >= this.max_x))
/*  198:     */       {
/*  199: 199 */         this.dx = Math.max(0, this.min_x + 1 - (this.chunk_x << 4));
/*  200: 200 */         this.dz += 1;
/*  201: 202 */         if ((this.dz >= 16) || ((this.chunk_z << 4) + this.dz >= this.max_z))
/*  202:     */         {
/*  203: 203 */           nextChunk();
/*  204: 204 */           this.dx = Math.max(0, this.min_x + 1 - (this.chunk_x << 4));
/*  205: 205 */           this.dz = Math.max(0, this.min_z + 1 - (this.chunk_z << 4));
/*  206:     */         }
/*  207:     */       }
/*  208: 209 */       this.dy = this.chunk_y;
/*  209:     */     }
/*  210:     */   }
/*  211:     */   
/*  212:     */   public void nextChunk()
/*  213:     */   {
/*  214: 214 */     unloadChunk();
/*  215: 215 */     this.chunk_x += 1;
/*  216: 217 */     if (this.chunk_x << 4 >= this.max_x)
/*  217:     */     {
/*  218: 218 */       this.chunk_x = (this.min_x + 1 >> 4);
/*  219: 219 */       this.chunk_z += 1;
/*  220: 221 */       if (this.chunk_z << 4 >= this.max_z)
/*  221:     */       {
/*  222: 222 */         this.finished = true;
/*  223: 223 */         this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 2, 2);
/*  224: 224 */         ForgeChunkManager.releaseTicket(this.chunkTicket);
/*  225: 225 */         return;
/*  226:     */       }
/*  227:     */     }
/*  228: 229 */     this.dy = this.chunk_y;
/*  229: 230 */     loadChunk();
/*  230:     */   }
/*  231:     */   
/*  232:     */   public boolean stopHere()
/*  233:     */   {
/*  234: 234 */     return (this.finished) || (isValid((this.chunk_x << 4) + this.dx, (this.chunk_z << 4) + this.dz));
/*  235:     */   }
/*  236:     */   
/*  237:     */   public boolean isValid(int x, int z)
/*  238:     */   {
/*  239: 238 */     return (this.min_x < x) && (x < this.max_x) && (this.min_z < z) && (z < this.max_z);
/*  240:     */   }
/*  241:     */   
/*  242:     */   public void forceChunkLoading(ForgeChunkManager.Ticket ticket)
/*  243:     */   {
/*  244: 242 */     if (this.chunkTicket == null) {
/*  245: 243 */       this.chunkTicket = ticket;
/*  246:     */     }
/*  247: 246 */     if (!disableSelfChunkLoading) {
/*  248: 247 */       ForgeChunkManager.forceChunk(this.chunkTicket, new ChunkCoordIntPair(this.xCoord >> 4, this.zCoord >> 4));
/*  249:     */     }
/*  250: 250 */     loadChunk();
/*  251:     */   }
/*  252:     */   
/*  253:     */   public void updateEntity()
/*  254:     */   {
/*  255: 315 */     if (this.worldObj.isClient) {
/*  256: 316 */       return;
/*  257:     */     }
/*  258: 319 */     if (this.inventoryMask < 0) {
/*  259: 320 */       detectInventories();
/*  260:     */     }
/*  261: 323 */     this.t = ((byte)(this.t + 1));
/*  262: 325 */     if ((this.t < getSpeedNo()) && (!this.overClock)) {
/*  263: 326 */       return;
/*  264:     */     }
/*  265: 329 */     this.t = 0;
/*  266: 331 */     if (this.searching) {
/*  267: 332 */       advFencing();
/*  268:     */     }
/*  269: 335 */     if ((!this.started) || (this.finished)) {
/*  270: 336 */       return;
/*  271:     */     }
/*  272: 339 */     if (this.chunkTicket == null)
/*  273:     */     {
/*  274: 340 */       this.chunkTicket = ForgeChunkManager.requestTicket(ExtraUtilsMod.instance, this.worldObj, ForgeChunkManager.Type.NORMAL);
/*  275: 342 */       if (this.chunkTicket == null)
/*  276:     */       {
/*  277: 343 */         if (this.owner != null) {
/*  278: 344 */           this.owner.addChatComponentMessage(new ChatComponentText("Problem registering chunk-preserving method"));
/*  279:     */         }
/*  280: 347 */         this.finished = true;
/*  281: 348 */         return;
/*  282:     */       }
/*  283: 351 */       this.chunkTicket.getModData().setString("id", "quarry");
/*  284: 352 */       this.chunkTicket.getModData().setInteger("x", this.xCoord);
/*  285: 353 */       this.chunkTicket.getModData().setInteger("y", this.yCoord);
/*  286: 354 */       this.chunkTicket.getModData().setInteger("z", this.zCoord);
/*  287: 356 */       if (!disableSelfChunkLoading) {
/*  288: 357 */         ForgeChunkManager.forceChunk(this.chunkTicket, new ChunkCoordIntPair(this.xCoord >> 4, this.zCoord >> 4));
/*  289:     */       }
/*  290: 360 */       loadChunk();
/*  291:     */     }
/*  292: 363 */     if ((this.neededEnergy > 0) && (this.worldObj.getTotalWorldTime() % 100L == 0L)) {
/*  293: 364 */       this.neededEnergy = -1;
/*  294:     */     }
/*  295: 368 */     int n = this.overClock ? 200 : getSpeedStack();
/*  296: 370 */     for (int k = 0; k < n; k++)
/*  297:     */     {
/*  298: 371 */       if ((!this.items.isEmpty()) || (this.fluid != null))
/*  299:     */       {
/*  300: 372 */         if (!this.overClock) {
/*  301: 373 */           this.energy.extractEnergy(baseDrain, false);
/*  302:     */         }
/*  303:     */       }
/*  304: 374 */       else if ((this.overClock) || ((this.energy.getEnergyStored() >= this.neededEnergy) && (this.energy.extractEnergy(baseDrain, true) == baseDrain)))
/*  305:     */       {
/*  306: 375 */         int x = (this.chunk_x << 4) + this.dx;
/*  307: 376 */         int z = (this.chunk_z << 4) + this.dz;
/*  308: 377 */         int y = this.dy;
/*  309: 379 */         if (y >= 0)
/*  310:     */         {
/*  311: 380 */           NetworkHandler.sendParticleEvent(this.worldObj, 1, x, y, z);
/*  312: 381 */           if (mineBlock(x, y, z, this.upgrades[1] == 0))
/*  313:     */           {
/*  314: 382 */             this.neededEnergy = -1;
/*  315: 383 */             nextBlock();
/*  316:     */           }
/*  317:     */         }
/*  318:     */         else
/*  319:     */         {
/*  320: 386 */           nextBlock();
/*  321:     */         }
/*  322:     */       }
/*  323: 390 */       if ((!this.items.isEmpty()) && 
/*  324: 391 */         (this.inventoryMask > 0)) {
/*  325: 392 */         for (int i = 0; i < 6; i++) {
/*  326: 393 */           if ((this.inventoryMask & 1 << i) > 0)
/*  327:     */           {
/*  328:     */             TileEntity tile;
/*  329: 396 */             if (((tile = this.worldObj.getTileEntity(this.xCoord + net.minecraft.util.Facing.offsetsXForSide[i], this.yCoord + net.minecraft.util.Facing.offsetsYForSide[i], this.zCoord + net.minecraft.util.Facing.offsetsZForSide[i])) instanceof IInventory))
/*  330:     */             {
/*  331: 397 */               IInventory inv = (IInventory)tile;
/*  332: 399 */               for (int j = 0; j < this.items.size(); j++) {
/*  333: 400 */                 if (XUHelper.invInsert(inv, (ItemStack)this.items.get(j), net.minecraft.util.Facing.oppositeSide[i]) == null)
/*  334:     */                 {
/*  335: 401 */                   this.items.remove(j);
/*  336: 402 */                   j--;
/*  337:     */                 }
/*  338:     */               }
/*  339:     */             }
/*  340:     */             else
/*  341:     */             {
/*  342: 406 */               detectInventories();
/*  343:     */             }
/*  344:     */           }
/*  345:     */         }
/*  346:     */       }
/*  347: 413 */       if ((this.fluid != null) && 
/*  348: 414 */         (this.fluidMask > 0)) {
/*  349: 415 */         for (int i = 0; (this.fluid != null) && (i < 6); i++) {
/*  350: 416 */           if ((this.fluidMask & 1 << i) > 0)
/*  351:     */           {
/*  352:     */             TileEntity tile;
/*  353: 419 */             if (((tile = this.worldObj.getTileEntity(this.xCoord + net.minecraft.util.Facing.offsetsXForSide[i], this.yCoord + net.minecraft.util.Facing.offsetsYForSide[i], this.zCoord + net.minecraft.util.Facing.offsetsZForSide[i])) instanceof IFluidHandler))
/*  354:     */             {
/*  355: 420 */               IFluidHandler tank = (IFluidHandler)tile;
/*  356:     */               
/*  357: 422 */               this.fluid.amount -= tank.fill(ForgeDirection.getOrientation(i).getOpposite(), this.fluid, true);
/*  358: 423 */               if (this.fluid.amount == 0)
/*  359:     */               {
/*  360: 424 */                 this.fluid = null;
/*  361: 425 */                 break;
/*  362:     */               }
/*  363:     */             }
/*  364:     */             else
/*  365:     */             {
/*  366: 429 */               detectInventories();
/*  367:     */             }
/*  368:     */           }
/*  369:     */         }
/*  370:     */       }
/*  371:     */     }
/*  372:     */   }
/*  373:     */   
/*  374:     */   private int getSpeedNo()
/*  375:     */   {
/*  376: 439 */     if (this.upgrades[6] != 0) {
/*  377: 440 */       return 1;
/*  378:     */     }
/*  379: 441 */     if (this.upgrades[7] != 0) {
/*  380: 442 */       return 1;
/*  381:     */     }
/*  382: 443 */     if (this.upgrades[8] != 0) {
/*  383: 444 */       return 1;
/*  384:     */     }
/*  385: 445 */     return 3;
/*  386:     */   }
/*  387:     */   
/*  388:     */   private int getSpeedStack()
/*  389:     */   {
/*  390: 449 */     if (this.upgrades[6] != 0) {
/*  391: 450 */       return 1;
/*  392:     */     }
/*  393: 451 */     if (this.upgrades[7] != 0) {
/*  394: 452 */       return 3;
/*  395:     */     }
/*  396: 453 */     if (this.upgrades[8] != 0) {
/*  397: 454 */       return 9;
/*  398:     */     }
/*  399: 455 */     return 1;
/*  400:     */   }
/*  401:     */   
/*  402:     */   public DigType getDigType()
/*  403:     */   {
/*  404: 459 */     if (this.upgrades[2] != 0) {
/*  405: 460 */       return DigType.SILK;
/*  406:     */     }
/*  407: 461 */     if (this.upgrades[3] != 0) {
/*  408: 462 */       return DigType.FORTUNE;
/*  409:     */     }
/*  410: 463 */     if (this.upgrades[4] != 0) {
/*  411: 464 */       return DigType.FORTUNE2;
/*  412:     */     }
/*  413: 465 */     if (this.upgrades[5] != 0) {
/*  414: 466 */       return DigType.FORTUNE3;
/*  415:     */     }
/*  416: 468 */     return DigType.NORMAL;
/*  417:     */   }
/*  418:     */   
/*  419:     */   public void invalidate()
/*  420:     */   {
/*  421: 473 */     ForgeChunkManager.releaseTicket(this.chunkTicket);
/*  422: 474 */     super.invalidate();
/*  423:     */   }
/*  424:     */   
/*  425:     */   private void loadChunk()
/*  426:     */   {
/*  427: 478 */     if ((this.xCoord >> 4 != this.chunk_x) || (this.zCoord >> 4 != this.chunk_z)) {
/*  428: 479 */       ForgeChunkManager.forceChunk(this.chunkTicket, new ChunkCoordIntPair(this.chunk_x, this.chunk_z));
/*  429:     */     }
/*  430:     */   }
/*  431:     */   
/*  432:     */   private void unloadChunk()
/*  433:     */   {
/*  434: 484 */     if ((this.xCoord >> 4 != this.chunk_x) || (this.zCoord >> 4 != this.chunk_z)) {
/*  435: 485 */       ForgeChunkManager.unforceChunk(this.chunkTicket, new ChunkCoordIntPair(this.chunk_x, this.chunk_z));
/*  436:     */     }
/*  437:     */   }
/*  438:     */   
/*  439:     */   public boolean mineBlock(int x, int y, int z, boolean replaceWithDirt)
/*  440:     */   {
/*  441: 490 */     Block block = this.worldObj.getBlock(x, y, z);
/*  442: 492 */     if ((block == Blocks.air) || (this.worldObj.isAirBlock(x, y, z)))
/*  443:     */     {
/*  444: 493 */       if (!this.overClock) {
/*  445: 494 */         this.energy.extractEnergy(baseDrain, false);
/*  446:     */       }
/*  447: 495 */       return true;
/*  448:     */     }
/*  449: 498 */     if (BlockBreakingRegistry.blackList(block))
/*  450:     */     {
/*  451: 500 */       if ((this.upgrades[9] != 0) && (XUHelper.isFluidBlock(block))) {
/*  452: 501 */         this.fluid = XUHelper.drainBlock(this.worldObj, x, y, z, true);
/*  453:     */       }
/*  454: 504 */       if (!this.overClock) {
/*  455: 505 */         this.energy.extractEnergy(baseDrain, false);
/*  456:     */       }
/*  457: 506 */       return true;
/*  458:     */     }
/*  459: 509 */     if ((replaceWithDirt) && ((block.isLeaves(this.worldObj, x, y, z)) || (block.isFoliage(this.worldObj, x, y, z)) || (block.isWood(this.worldObj, x, y, z)) || ((block instanceof IPlantable)) || ((block instanceof IGrowable))))
/*  460:     */     {
/*  461: 512 */       if (!this.overClock) {
/*  462: 513 */         this.energy.extractEnergy(baseDrain, false);
/*  463:     */       }
/*  464: 514 */       return true;
/*  465:     */     }
/*  466: 517 */     int meta = this.worldObj.getBlockMetadata(x, y, z);
/*  467: 518 */     float hardness = block.getBlockHardness(this.worldObj, x, y, z);
/*  468: 520 */     if (hardness < 0.0F)
/*  469:     */     {
/*  470: 521 */       if (!this.overClock) {
/*  471: 522 */         this.energy.extractEnergy(baseDrain, false);
/*  472:     */       }
/*  473: 523 */       return true;
/*  474:     */     }
/*  475: 526 */     int amount = (int)Math.ceil(baseDrain + hardness * hardnessDrain * getPowerMultiplier());
/*  476: 528 */     if (amount > this.energy.getMaxEnergyStored()) {
/*  477: 529 */       amount = this.energy.getMaxEnergyStored();
/*  478:     */     }
/*  479: 532 */     if (this.overClock) {
/*  480: 533 */       amount = 0;
/*  481:     */     }
/*  482: 535 */     if (this.energy.extractEnergy(amount, true) < amount)
/*  483:     */     {
/*  484: 536 */       this.neededEnergy = amount;
/*  485: 537 */       return false;
/*  486:     */     }
/*  487: 539 */     this.energy.extractEnergy(amount, false);
/*  488: 542 */     if ((replaceWithDirt) && ((block == Blocks.grass) || (block == Blocks.dirt)))
/*  489:     */     {
/*  490: 543 */       if (this.worldObj.canBlockSeeTheSky(x, y + 1, z)) {
/*  491: 544 */         this.worldObj.setBlock(x, y, z, Blocks.grass, 0, 3);
/*  492:     */       }
/*  493: 547 */       if ((rand.nextInt(16) == 0) && (this.worldObj.isAirBlock(x, y + 1, z))) {
/*  494: 548 */         if (rand.nextInt(5) == 0) {
/*  495: 549 */           this.worldObj.getBiomeGenForCoords(x, z).plantFlower(this.worldObj, rand, x, y + 1, z);
/*  496: 550 */         } else if (rand.nextInt(2) == 0) {
/*  497: 551 */           this.worldObj.setBlock(x, y + 1, z, Blocks.yellow_flower, rand.nextInt(BlockFlower.field_149858_b.length), 3);
/*  498:     */         } else {
/*  499: 553 */           this.worldObj.setBlock(x, y + 1, z, Blocks.red_flower, rand.nextInt(BlockFlower.field_149859_a.length), 3);
/*  500:     */         }
/*  501:     */       }
/*  502: 557 */       return true;
/*  503:     */     }
/*  504: 560 */     return harvestBlock(block, x, y, z, meta, replaceWithDirt, getDigType());
/*  505:     */   }
/*  506:     */   
/*  507:     */   public static enum DigType
/*  508:     */   {
/*  509: 564 */     NORMAL(null, 0),  SILK(Enchantment.silkTouch, 1),  FORTUNE(Enchantment.fortune, 1),  FORTUNE2(Enchantment.fortune, 2),  FORTUNE3(Enchantment.fortune, 3),  SPEED(Enchantment.efficiency, 1),  SPEED2(Enchantment.efficiency, 3),  SPEED3(Enchantment.efficiency, 5);
/*  510:     */     
/*  511:     */     public Enchantment ench;
/*  512:     */     public int level;
/*  513:     */     
/*  514:     */     private DigType(Enchantment ench, int level)
/*  515:     */     {
/*  516: 577 */       this.ench = ench;
/*  517: 578 */       this.level = level;
/*  518:     */     }
/*  519:     */     
/*  520:     */     public int getFortuneModifier()
/*  521:     */     {
/*  522: 582 */       if (this.ench == Enchantment.fortune) {
/*  523: 583 */         return this.level;
/*  524:     */       }
/*  525: 585 */       return 0;
/*  526:     */     }
/*  527:     */     
/*  528:     */     public ItemStack newStack(Item pick)
/*  529:     */     {
/*  530: 589 */       ItemStack stack = new ItemStack(pick);
/*  531: 590 */       if (this.ench != null) {
/*  532: 591 */         stack.addEnchantment(this.ench, this.level);
/*  533:     */       }
/*  534: 592 */       return stack;
/*  535:     */     }
/*  536:     */     
/*  537:     */     public boolean isSilkTouch()
/*  538:     */     {
/*  539: 596 */       return this.ench == Enchantment.silkTouch;
/*  540:     */     }
/*  541:     */   }
/*  542:     */   
/*  543:     */   public boolean harvestBlock(Block block, int x, int y, int z, int meta, boolean replaceWithDirt, DigType digType)
/*  544:     */   {
/*  545: 601 */     boolean isOpaque = block.isOpaqueCube();
/*  546: 602 */     boolean seesSky = (replaceWithDirt) && (isOpaque) && (this.worldObj.canBlockSeeTheSky(x, y + 1, z));
/*  547: 603 */     FakePlayer fakePlayer = FakePlayerFactory.getMinecraft((WorldServer)this.worldObj);
/*  548: 604 */     fakePlayer.setCurrentItemOrArmor(0, digType.newStack(Items.diamond_pickaxe));
/*  549:     */     try
/*  550:     */     {
/*  551:     */       boolean flag;
/*  552: 612 */       if (BlockBreakingRegistry.isSpecial(block))
/*  553:     */       {
/*  554: 615 */         EventHandlerEntityItemStealer.startCapture(true);
/*  555:     */         
/*  556: 617 */         block.onBlockHarvested(this.worldObj, x, y, z, meta, fakePlayer);
/*  557: 619 */         if (!block.removedByPlayer(this.worldObj, fakePlayer, x, y, z, true))
/*  558:     */         {
/*  559: 620 */           this.items.addAll(EventHandlerEntityItemStealer.getCapturedItemStacks());
/*  560: 621 */           return false;
/*  561:     */         }
/*  562: 624 */         block.harvestBlock(this.worldObj, fakePlayer, x, y, z, meta);
/*  563:     */         
/*  564: 626 */         block.onBlockDestroyedByPlayer(this.worldObj, x, y, z, meta);
/*  565: 628 */         if ((replaceWithDirt) && (isOpaque)) {
/*  566: 629 */           this.worldObj.setBlock(x, y, z, seesSky ? Blocks.grass : Blocks.dirt, 0, 3);
/*  567:     */         }
/*  568: 632 */         this.items.addAll(EventHandlerEntityItemStealer.getCapturedItemStacks());
/*  569:     */       }
/*  570:     */       else
/*  571:     */       {
/*  572: 634 */         EventHandlerEntityItemStealer.startCapture(true);
/*  573: 635 */         flag = this.worldObj.setBlock(x, y, z, (replaceWithDirt) && (isOpaque) ? Blocks.dirt : seesSky ? Blocks.grass : Blocks.air, 0, 3);
/*  574: 636 */         this.items.addAll(EventHandlerEntityItemStealer.getCapturedItemStacks());
/*  575: 638 */         if (!flag) {
/*  576: 639 */           return false;
/*  577:     */         }
/*  578: 642 */         Object i = new ArrayList();
/*  579: 644 */         if ((digType.isSilkTouch()) && (block.canSilkHarvest(this.worldObj, fakePlayer, x, y, z, meta)))
/*  580:     */         {
/*  581: 645 */           int j = 0;
/*  582: 646 */           Item item = Item.getItemFromBlock(block);
/*  583: 648 */           if (item != null)
/*  584:     */           {
/*  585: 649 */             if (item.getHasSubtypes()) {
/*  586: 650 */               j = meta;
/*  587:     */             }
/*  588: 652 */             ItemStack itemstack = new ItemStack(item, 1, j);
/*  589: 653 */             ((ArrayList)i).add(itemstack);
/*  590:     */           }
/*  591:     */         }
/*  592:     */         else
/*  593:     */         {
/*  594: 656 */           ((ArrayList)i).addAll(block.getDrops(this.worldObj, x, y, z, meta, digType.getFortuneModifier()));
/*  595:     */         }
/*  596: 658 */         float p = ForgeEventFactory.fireBlockHarvesting((ArrayList)i, this.worldObj, block, x, y, z, meta, digType.getFortuneModifier(), 1.0F, digType.isSilkTouch(), fakePlayer);
/*  597: 660 */         if ((p > 0.0F) && (!((ArrayList)i).isEmpty()) && ((p == 1.0F) || (rand.nextFloat() < p))) {
/*  598: 661 */           this.items.addAll((Collection)i);
/*  599:     */         }
/*  600:     */       }
/*  601: 665 */       NetworkHandler.sendParticleEvent(this.worldObj, 0, x, y, z);
/*  602: 667 */       if ((seesSky) && (rand.nextInt(16) == 0) && (this.worldObj.isAirBlock(x, y + 1, z))) {
/*  603: 668 */         if (rand.nextInt(5) == 0) {
/*  604: 669 */           this.worldObj.getBiomeGenForCoords(x, z).plantFlower(this.worldObj, rand, x, y + 1, z);
/*  605: 670 */         } else if (rand.nextInt(2) == 0) {
/*  606: 671 */           this.worldObj.setBlock(x, y + 1, z, Blocks.yellow_flower, rand.nextInt(BlockFlower.field_149858_b.length), 3);
/*  607:     */         } else {
/*  608: 673 */           this.worldObj.setBlock(x, y + 1, z, Blocks.red_flower, rand.nextInt(BlockFlower.field_149859_a.length), 3);
/*  609:     */         }
/*  610:     */       }
/*  611: 677 */       return true;
/*  612:     */     }
/*  613:     */     finally
/*  614:     */     {
/*  615: 679 */       fakePlayer.setCurrentItemOrArmor(0, null);
/*  616:     */     }
/*  617:     */   }
/*  618:     */   
/*  619: 683 */   private boolean overClock = false;
/*  620:     */   
/*  621:     */   public void debug()
/*  622:     */   {
/*  623: 686 */     this.overClock = true;
/*  624:     */   }
/*  625:     */   
/*  626:     */   public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
/*  627:     */   {
/*  628: 691 */     return this.energy.receiveEnergy(maxReceive, simulate);
/*  629:     */   }
/*  630:     */   
/*  631:     */   public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
/*  632:     */   {
/*  633: 696 */     return 0;
/*  634:     */   }
/*  635:     */   
/*  636:     */   public boolean canConnectEnergy(ForgeDirection from)
/*  637:     */   {
/*  638: 701 */     return true;
/*  639:     */   }
/*  640:     */   
/*  641:     */   public int getEnergyStored(ForgeDirection from)
/*  642:     */   {
/*  643: 706 */     return this.energy.getEnergyStored();
/*  644:     */   }
/*  645:     */   
/*  646:     */   public int getMaxEnergyStored(ForgeDirection from)
/*  647:     */   {
/*  648: 711 */     return this.energy.getMaxEnergyStored();
/*  649:     */   }
/*  650:     */   
/*  651: 714 */   public boolean[] upgrades = new boolean[16];
/*  652:     */   public static final int UPGRADE_BLANK = 0;
/*  653:     */   public static final int UPGRADE_VOID = 1;
/*  654:     */   public static final int UPGRADE_SILK = 2;
/*  655:     */   public static final int UPGRADE_FORTUNE1 = 3;
/*  656:     */   public static final int UPGRADE_FORTUNE2 = 4;
/*  657:     */   public static final int UPGRADE_FORTUNE3 = 5;
/*  658:     */   public static final int UPGRADE_SPEED1 = 6;
/*  659:     */   public static final int UPGRADE_SPEED2 = 7;
/*  660:     */   public static final int UPGRADE_SPEED3 = 8;
/*  661:     */   public static final int UPGRADE_FLUID = 9;
/*  662:     */   
/*  663:     */   public static void addUpgradeRecipes()
/*  664:     */   {
/*  665: 727 */     ItemStack base = new ItemStack(ExtraUtils.enderQuarryUpgrade, 1, 0);
/*  666: 728 */     ItemStack burntQuartz = new ItemStack(ExtraUtils.decorative1, 1, 2);
/*  667: 729 */     ItemStack endersidian = new ItemStack(ExtraUtils.decorative1, 1, 1);
/*  668: 730 */     ExtraUtils.addRecipe(new ItemStack(ExtraUtils.enderQuarryUpgrade, 1, 0), new Object[] { " E ", "EQE", " E ", Character.valueOf('E'), endersidian, Character.valueOf('Q'), burntQuartz });
/*  669: 731 */     ExtraUtils.addRecipe(new ItemStack(ExtraUtils.enderQuarryUpgrade, 1, 1), new Object[] { " T ", "RBR", Character.valueOf('B'), base, Character.valueOf('T'), ExtraUtils.trashCan, Character.valueOf('R'), Blocks.quartz_block });
/*  670: 732 */     ExtraUtils.addRecipe(new RecipeEnchantCrafting(new ItemStack(ExtraUtils.enderQuarryUpgrade, 1, 2), new Object[] { " P ", "RBR", Character.valueOf('B'), base, Character.valueOf('P'), DigType.SILK.newStack(Items.golden_pickaxe), Character.valueOf('R'), Items.redstone }));
/*  671: 733 */     ExtraUtils.addRecipe(new RecipeEnchantCrafting(new ItemStack(ExtraUtils.enderQuarryUpgrade, 1, 3), new Object[] { " P ", "RBR", Character.valueOf('B'), base, Character.valueOf('P'), DigType.FORTUNE.newStack(Items.iron_pickaxe), Character.valueOf('R'), Items.redstone }));
/*  672: 734 */     ExtraUtils.addRecipe(new RecipeEnchantCrafting(new ItemStack(ExtraUtils.enderQuarryUpgrade, 1, 4), new Object[] { " P ", "RBR", Character.valueOf('B'), new ItemStack(ExtraUtils.enderQuarryUpgrade, 1, 3), Character.valueOf('P'), DigType.FORTUNE.newStack(Items.golden_pickaxe), Character.valueOf('R'), Items.redstone }));
/*  673: 735 */     ExtraUtils.addRecipe(new RecipeEnchantCrafting(new ItemStack(ExtraUtils.enderQuarryUpgrade, 1, 5), new Object[] { "P P", "RBR", Character.valueOf('B'), new ItemStack(ExtraUtils.enderQuarryUpgrade, 1, 4), Character.valueOf('P'), DigType.FORTUNE.newStack(Items.diamond_pickaxe), Character.valueOf('R'), Items.redstone }));
/*  674: 736 */     if (ExtraUtils.nodeUpgrade != null)
/*  675:     */     {
/*  676: 737 */       ExtraUtils.addRecipe(new RecipeEnchantCrafting(new ItemStack(ExtraUtils.enderQuarryUpgrade, 1, 6), new Object[] { " R ", "TBT", Character.valueOf('B'), base, Character.valueOf('T'), new ItemStack(ExtraUtils.nodeUpgrade, 1, 0), Character.valueOf('R'), DigType.SPEED.newStack(Items.diamond_pickaxe) }));
/*  677: 738 */       ExtraUtils.addRecipe(new RecipeEnchantCrafting(new ItemStack(ExtraUtils.enderQuarryUpgrade, 1, 7), new Object[] { " R ", "TBT", Character.valueOf('B'), new ItemStack(ExtraUtils.enderQuarryUpgrade, 1, 6), Character.valueOf('T'), new ItemStack(ExtraUtils.nodeUpgrade, 1, 0), Character.valueOf('R'), DigType.SPEED2.newStack(Items.diamond_pickaxe) }));
/*  678: 739 */       ExtraUtils.addRecipe(new RecipeEnchantCrafting(new ItemStack(ExtraUtils.enderQuarryUpgrade, 1, 8), new Object[] { "R R", "TBT", Character.valueOf('B'), new ItemStack(ExtraUtils.enderQuarryUpgrade, 1, 7), Character.valueOf('T'), new ItemStack(ExtraUtils.nodeUpgrade, 1, 3), Character.valueOf('R'), DigType.SPEED3.newStack(Items.diamond_pickaxe) }));
/*  679:     */     }
/*  680: 741 */     ExtraUtils.addRecipe(new ItemStack(ExtraUtils.enderQuarryUpgrade, 1, 9), new Object[] { " T ", "RBR", Character.valueOf('B'), base, Character.valueOf('T'), Items.bucket, Character.valueOf('R'), Items.redstone });
/*  681:     */   }
/*  682:     */   
/*  683: 744 */   public static double[] powerMultipliers = { 1.0D, 1.0D, 1.5D, 5.0D, 20.0D, 80.0D, 1.0D, 1.5D, 2.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D };
/*  684:     */   
/*  685:     */   public double getPowerMultiplier()
/*  686:     */   {
/*  687: 763 */     double multiplier = 1.0D;
/*  688: 764 */     for (int i = 0; i < 16; i++) {
/*  689: 765 */       if (this.upgrades[i] != 0) {
/*  690: 766 */         multiplier *= powerMultipliers[i];
/*  691:     */       }
/*  692:     */     }
/*  693: 767 */     return multiplier;
/*  694:     */   }
/*  695:     */   
/*  696:     */   public void detectInventories()
/*  697:     */   {
/*  698: 771 */     this.inventoryMask = 0;
/*  699: 772 */     this.fluidMask = 0;
/*  700: 773 */     this.upgrades = new boolean[16];
/*  701: 775 */     for (int i = 0; i < 6; i++)
/*  702:     */     {
/*  703: 776 */       int x = this.xCoord + net.minecraft.util.Facing.offsetsXForSide[i];
/*  704: 777 */       int y = this.yCoord + net.minecraft.util.Facing.offsetsYForSide[i];
/*  705: 778 */       int z = this.zCoord + net.minecraft.util.Facing.offsetsZForSide[i];
/*  706:     */       
/*  707: 780 */       TileEntity tile = this.worldObj.getTileEntity(x, y, z);
/*  708: 782 */       if ((tile instanceof IInventory)) {
/*  709: 783 */         this.inventoryMask |= 1 << i;
/*  710:     */       }
/*  711: 786 */       if ((tile instanceof IFluidHandler)) {
/*  712: 787 */         this.fluidMask |= 1 << i;
/*  713:     */       }
/*  714: 790 */       if (this.worldObj.getBlock(x, y, z) == ExtraUtils.enderQuarryUpgrade) {
/*  715: 791 */         this.upgrades[this.worldObj.getBlockMetadata(x, y, z)] = true;
/*  716:     */       }
/*  717:     */     }
/*  718:     */   }
/*  719:     */   
/*  720:     */   public void startFencing(EntityPlayer player)
/*  721:     */   {
/*  722: 797 */     if (this.finished)
/*  723:     */     {
/*  724: 798 */       PacketTempChat.sendChat(player, new ChatComponentText("Quarry has finished"));
/*  725: 799 */       return;
/*  726:     */     }
/*  727: 802 */     if (this.started)
/*  728:     */     {
/*  729: 803 */       PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("Mining at: (" + ((this.chunk_x << 4) + this.dx) + "," + this.dy + "," + ((this.chunk_z << 4) + this.dz) + ")"));
/*  730: 804 */       PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("" + this.progress + " blocks scanned."));
/*  731: 805 */       PacketTempChatMultiline.sendCached(player);
/*  732: 806 */       return;
/*  733:     */     }
/*  734: 809 */     if (this.searching)
/*  735:     */     {
/*  736: 810 */       PacketTempChat.sendChat(player, new ChatComponentText("Searching fence boundary at: (" + this.fence_x + "," + this.fence_y + "," + this.fence_z + ")"));
/*  737: 811 */       return;
/*  738:     */     }
/*  739: 814 */     this.owner = player;
/*  740: 815 */     player.addChatComponentMessage(new ChatComponentText("Analyzing Fence boundary"));
/*  741: 817 */     if (checkForMarkers(player)) {
/*  742: 818 */       return;
/*  743:     */     }
/*  744: 820 */     this.fence_x = this.xCoord;
/*  745: 821 */     this.fence_y = this.yCoord;
/*  746: 822 */     this.fence_z = this.zCoord;
/*  747: 823 */     this.fence_elev = -1;
/*  748: 824 */     this.fence_dir = -1;
/*  749: 825 */     int j = 0;
/*  750: 827 */     for (int i = 2; i < 6; i++) {
/*  751: 828 */       if (isFence(this.fence_x, this.fence_y, this.fence_z, i))
/*  752:     */       {
/*  753: 829 */         if (this.fence_dir < 0) {
/*  754: 830 */           this.fence_dir = i;
/*  755:     */         }
/*  756: 833 */         j++;
/*  757: 835 */         if (j > 2)
/*  758:     */         {
/*  759: 836 */           stopFencing("Quarry is connected to more than fences on more than 2 sides", false);
/*  760: 837 */           return;
/*  761:     */         }
/*  762:     */       }
/*  763:     */     }
/*  764: 842 */     if (j < 2)
/*  765:     */     {
/*  766: 843 */       if (j == 0) {
/*  767: 844 */         stopFencing("Unable to detect fence boundary", false);
/*  768:     */       }
/*  769: 847 */       if (j == 1) {
/*  770: 848 */         stopFencing("Quarry is only connected to fence boundary on one side", false);
/*  771:     */       }
/*  772: 851 */       return;
/*  773:     */     }
/*  774: 854 */     this.chunk_y = this.yCoord;
/*  775: 855 */     this.fence_x = (this.xCoord + net.minecraft.util.Facing.offsetsXForSide[this.fence_dir]);
/*  776: 856 */     this.fence_y = (this.yCoord + net.minecraft.util.Facing.offsetsYForSide[this.fence_dir]);
/*  777: 857 */     this.fence_z = (this.zCoord + net.minecraft.util.Facing.offsetsZForSide[this.fence_dir]);
/*  778: 858 */     this.min_x = this.xCoord;
/*  779: 859 */     this.max_x = this.xCoord;
/*  780: 860 */     this.min_z = this.zCoord;
/*  781: 861 */     this.max_z = this.zCoord;
/*  782: 862 */     this.searching = true;
/*  783:     */   }
/*  784:     */   
/*  785:     */   public boolean checkForMarkers(EntityPlayer player)
/*  786:     */   {
/*  787: 866 */     for (ForgeDirection d : new ForgeDirection[] { ForgeDirection.EAST, ForgeDirection.WEST, ForgeDirection.NORTH, ForgeDirection.SOUTH })
/*  788:     */     {
/*  789: 867 */       int[] test = { getWorldObj().provider.dimensionId, this.xCoord + d.offsetX, this.yCoord, this.zCoord + d.offsetZ };
/*  790: 868 */       int[] test_forward = null;
/*  791: 869 */       int[] test_side = null;
/*  792:     */       
/*  793: 871 */       boolean flag = true;
/*  794: 872 */       for (int[] a : TileEntityEnderMarker.markers) {
/*  795: 873 */         if (isIntEqual(a, test))
/*  796:     */         {
/*  797: 874 */           flag = false;
/*  798: 875 */           break;
/*  799:     */         }
/*  800:     */       }
/*  801: 879 */       if (!flag)
/*  802:     */       {
/*  803: 882 */         player.addChatComponentMessage(new ChatComponentText("Found attached ender-marker"));
/*  804: 884 */         for (int[] a : TileEntityEnderMarker.markers) {
/*  805: 885 */           if ((a[0] == test[0]) && (a[2] == test[2]) && (
/*  806: 886 */             (a[1] != test[1]) || (a[3] != test[3])))
/*  807:     */           {
/*  808: 888 */             if ((sign(a[1] - test[1]) == d.offsetX) && (sign(a[3] - test[3]) == d.offsetZ)) {
/*  809: 889 */               if (test_forward == null) {
/*  810: 890 */                 test_forward = a;
/*  811: 891 */               } else if (!isIntEqual(a, test_forward)) {
/*  812: 892 */                 player.addChatComponentMessage(new ChatComponentText("Quarry marker square is ambiguous - multiple markers found at (" + a[1] + "," + a[3] + ") and (" + test_forward[1] + "," + test_forward[3] + ")"));
/*  813:     */               }
/*  814:     */             }
/*  815: 896 */             if (((d.offsetX == 0) && (a[3] == test[3])) || ((d.offsetZ == 0) && (a[1] == test[1]))) {
/*  816: 897 */               if (test_side == null) {
/*  817: 898 */                 test_side = a;
/*  818: 899 */               } else if (!isIntEqual(a, test_side)) {
/*  819: 900 */                 player.addChatComponentMessage(new ChatComponentText("Quarry marker square is ambiguous - multiple markers found at (" + a[1] + "," + a[3] + ") and (" + test_side[1] + "," + test_side[3] + ")"));
/*  820:     */               }
/*  821:     */             }
/*  822:     */           }
/*  823:     */         }
/*  824: 908 */         if (test_forward == null)
/*  825:     */         {
/*  826: 909 */           player.addChatComponentMessage(new ChatComponentText("Quarry marker square is incomplete"));
/*  827: 910 */           return false;
/*  828:     */         }
/*  829: 913 */         if (test_side == null)
/*  830:     */         {
/*  831: 914 */           player.addChatComponentMessage(new ChatComponentText("Quarry marker square is incomplete"));
/*  832: 915 */           return false;
/*  833:     */         }
/*  834: 918 */         int amin_x = Math.min(Math.min(test[1], test_forward[1]), test_side[1]);
/*  835: 919 */         int amax_x = Math.max(Math.max(test[1], test_forward[1]), test_side[1]);
/*  836: 920 */         int amin_z = Math.min(Math.min(test[3], test_forward[3]), test_side[3]);
/*  837: 921 */         int amax_z = Math.max(Math.max(test[3], test_forward[3]), test_side[3]);
/*  838: 923 */         if ((amax_x - amin_x <= 2) || (amax_z - amin_z <= 2))
/*  839:     */         {
/*  840: 924 */           stopFencing("Region created by ender markers is too small", false);
/*  841: 925 */           return false;
/*  842:     */         }
/*  843: 928 */         this.owner.addChatComponentMessage(new ChatComponentText("Sucessfully established boundary"));
/*  844: 930 */         if (disableSelfChunkLoading) {
/*  845: 931 */           this.owner.addChatComponentMessage(new ChatComponentText("Note: Quarry is configured not to self-chunkload."));
/*  846:     */         }
/*  847: 935 */         this.chunk_y = this.yCoord;
/*  848: 936 */         this.min_x = amin_x;
/*  849: 937 */         this.max_x = amax_x;
/*  850: 938 */         this.min_z = amin_z;
/*  851: 939 */         this.max_z = amax_z;
/*  852: 940 */         this.searching = false;
/*  853:     */         
/*  854:     */ 
/*  855: 943 */         startDig();
/*  856:     */         
/*  857: 945 */         return true;
/*  858:     */       }
/*  859:     */     }
/*  860: 949 */     return false;
/*  861:     */   }
/*  862:     */   
/*  863:     */   public static int sign(int d)
/*  864:     */   {
/*  865: 953 */     if (d == 0) {
/*  866: 954 */       return 0;
/*  867:     */     }
/*  868: 955 */     if (d > 0) {
/*  869: 956 */       return 1;
/*  870:     */     }
/*  871: 958 */     return -1;
/*  872:     */   }
/*  873:     */   
/*  874:     */   public static boolean isIntEqual(int[] a, int[] b)
/*  875:     */   {
/*  876: 963 */     if (a == b) {
/*  877: 964 */       return true;
/*  878:     */     }
/*  879: 965 */     for (int i = 0; i < 4; i++) {
/*  880: 966 */       if (a[i] != b[i]) {
/*  881: 967 */         return false;
/*  882:     */       }
/*  883:     */     }
/*  884: 969 */     return true;
/*  885:     */   }
/*  886:     */   
/*  887:     */   public void stopFencing(String reason, boolean sendLocation)
/*  888:     */   {
/*  889: 973 */     this.searching = false;
/*  890: 975 */     if (sendLocation) {
/*  891: 976 */       reason = reason + ": (" + this.fence_x + "," + this.fence_y + "," + this.fence_z + ")";
/*  892:     */     }
/*  893: 979 */     if (this.owner != null) {
/*  894: 980 */       this.owner.addChatComponentMessage(new ChatComponentText(reason));
/*  895:     */     }
/*  896:     */   }
/*  897:     */   
/*  898:     */   private void advFencing()
/*  899:     */   {
/*  900: 985 */     Long t = Long.valueOf(System.nanoTime());
/*  901: 987 */     while ((this.searching) && (System.nanoTime() - t.longValue() < 100000L)) {
/*  902: 988 */       advFence();
/*  903:     */     }
/*  904:     */   }
/*  905:     */   
/*  906:     */   public void advFence()
/*  907:     */   {
/*  908: 993 */     int new_dir = -1;
/*  909: 995 */     for (int i = 0; i < 6; i++) {
/*  910: 996 */       if ((this.fence_elev < 0 ? i != net.minecraft.util.Facing.oppositeSide[this.fence_dir] : i != net.minecraft.util.Facing.oppositeSide[this.fence_elev]) && 
/*  911: 997 */         (isFence(this.fence_x, this.fence_y, this.fence_z, i))) {
/*  912: 998 */         if (new_dir == -1)
/*  913:     */         {
/*  914: 999 */           new_dir = i;
/*  915:     */         }
/*  916:     */         else
/*  917:     */         {
/*  918:1001 */           stopFencing("Fence boundary splits at", true);
/*  919:1002 */           return;
/*  920:     */         }
/*  921:     */       }
/*  922:     */     }
/*  923:1008 */     if (new_dir < 0)
/*  924:     */     {
/*  925:1009 */       stopFencing("Fence boundary stops at", true);
/*  926:1010 */       return;
/*  927:     */     }
/*  928:1011 */     if (new_dir <= 1)
/*  929:     */     {
/*  930:1012 */       this.fence_elev = new_dir;
/*  931:1013 */       this.fence_y += net.minecraft.util.Facing.offsetsYForSide[new_dir];
/*  932:1015 */       if (new_dir == 1) {
/*  933:1016 */         this.chunk_y = Math.max(this.chunk_y, this.fence_y);
/*  934:     */       }
/*  935:     */     }
/*  936:     */     else
/*  937:     */     {
/*  938:1019 */       if (this.fence_dir != new_dir)
/*  939:     */       {
/*  940:1020 */         if (((this.min_z < this.fence_z) && (this.fence_z < this.max_z)) || ((this.min_x < this.fence_x) && (this.fence_x < this.max_x)))
/*  941:     */         {
/*  942:1021 */           stopFencing("Fence boundary must be square", true);
/*  943:1022 */           return;
/*  944:     */         }
/*  945:1025 */         boolean flag = false;
/*  946:1027 */         if (this.fence_z < this.zCoord)
/*  947:     */         {
/*  948:1028 */           flag = (this.fence_z != this.min_z) && (this.min_z != this.zCoord);
/*  949:1029 */           this.min_z = this.fence_z;
/*  950:     */         }
/*  951:1032 */         if ((this.fence_x < this.xCoord) && (!flag))
/*  952:     */         {
/*  953:1033 */           flag = (this.fence_x != this.min_x) && (this.min_x != this.xCoord);
/*  954:1034 */           this.min_x = this.fence_x;
/*  955:     */         }
/*  956:1037 */         if ((this.fence_z > this.zCoord) && (!flag))
/*  957:     */         {
/*  958:1038 */           flag = (this.fence_z != this.max_z) && (this.max_z != this.zCoord);
/*  959:1039 */           this.max_z = this.fence_z;
/*  960:     */         }
/*  961:1042 */         if ((this.fence_x > this.xCoord) && (!flag))
/*  962:     */         {
/*  963:1043 */           flag = (this.fence_x != this.max_x) && (this.max_x != this.xCoord);
/*  964:1044 */           this.max_x = this.fence_x;
/*  965:     */         }
/*  966:1047 */         if (flag)
/*  967:     */         {
/*  968:1048 */           stopFencing("Fence boundary must be square", true);
/*  969:1049 */           return;
/*  970:     */         }
/*  971:     */       }
/*  972:1053 */       this.fence_x += net.minecraft.util.Facing.offsetsXForSide[new_dir];
/*  973:1054 */       this.fence_z += net.minecraft.util.Facing.offsetsZForSide[new_dir];
/*  974:1055 */       this.fence_dir = new_dir;
/*  975:1056 */       this.fence_elev = -1;
/*  976:     */     }
/*  977:1059 */     if ((this.fence_x == this.xCoord) && (this.fence_y == this.yCoord) && (this.fence_z == this.zCoord))
/*  978:     */     {
/*  979:1060 */       if ((this.max_x - this.min_x <= 2) || (this.max_z - this.min_z <= 2))
/*  980:     */       {
/*  981:1061 */         stopFencing("Region created by fence is too small", false);
/*  982:1062 */         return;
/*  983:     */       }
/*  984:1065 */       this.owner.addChatComponentMessage(new ChatComponentText("Sucessfully established boundary"));
/*  985:1067 */       if (disableSelfChunkLoading) {
/*  986:1068 */         this.owner.addChatComponentMessage(new ChatComponentText("Note: Quarry is configured not to self-chunkload."));
/*  987:     */       }
/*  988:1071 */       startDig();
/*  989:1072 */       this.searching = false;
/*  990:     */     }
/*  991:     */   }
/*  992:     */   
/*  993:     */   public boolean isFence(int x, int y, int z, int dir)
/*  994:     */   {
/*  995:1077 */     return isFence(x + net.minecraft.util.Facing.offsetsXForSide[dir], y + net.minecraft.util.Facing.offsetsYForSide[dir], z + net.minecraft.util.Facing.offsetsZForSide[dir]);
/*  996:     */   }
/*  997:     */   
/*  998:     */   public boolean isFence(int x, int y, int z)
/*  999:     */   {
/* 1000:1081 */     Block id = this.worldObj.getBlock(x, y, z);
/* 1001:1083 */     if (BlockBreakingRegistry.isFence(id)) {
/* 1002:1084 */       return true;
/* 1003:     */     }
/* 1004:1093 */     return (x == this.xCoord) && (z == this.zCoord) && (y == this.yCoord);
/* 1005:     */   }
/* 1006:     */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.enderquarry.TileEntityEnderQuarry
 * JD-Core Version:    0.7.0.1
 */