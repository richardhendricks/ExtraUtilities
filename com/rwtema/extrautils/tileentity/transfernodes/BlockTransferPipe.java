/*   1:    */ package com.rwtema.extrautils.tileentity.transfernodes;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.ExtraUtilsMod;
/*   5:    */ import com.rwtema.extrautils.ICreativeTabSorting;
/*   6:    */ import com.rwtema.extrautils.block.BlockMultiBlockSelection;
/*   7:    */ import com.rwtema.extrautils.block.Box;
/*   8:    */ import com.rwtema.extrautils.block.BoxModel;
/*   9:    */ import com.rwtema.extrautils.helper.XUHelper;
/*  10:    */ import com.rwtema.extrautils.helper.XURandom;
/*  11:    */ import com.rwtema.extrautils.multipart.FMPBase;
/*  12:    */ import com.rwtema.extrautils.tileentity.transfernodes.pipes.IPipe;
/*  13:    */ import com.rwtema.extrautils.tileentity.transfernodes.pipes.IPipeBlock;
/*  14:    */ import com.rwtema.extrautils.tileentity.transfernodes.pipes.IPipeCosmetic;
/*  15:    */ import com.rwtema.extrautils.tileentity.transfernodes.pipes.StdPipes;
/*  16:    */ import cpw.mods.fml.relauncher.Side;
/*  17:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Random;
/*  20:    */ import net.minecraft.block.Block;
/*  21:    */ import net.minecraft.block.material.Material;
/*  22:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  23:    */ import net.minecraft.creativetab.CreativeTabs;
/*  24:    */ import net.minecraft.entity.item.EntityItem;
/*  25:    */ import net.minecraft.entity.player.EntityPlayer;
/*  26:    */ import net.minecraft.inventory.InventoryBasic;
/*  27:    */ import net.minecraft.item.Item;
/*  28:    */ import net.minecraft.item.ItemStack;
/*  29:    */ import net.minecraft.nbt.NBTTagCompound;
/*  30:    */ import net.minecraft.tileentity.TileEntity;
/*  31:    */ import net.minecraft.util.IIcon;
/*  32:    */ import net.minecraft.world.IBlockAccess;
/*  33:    */ import net.minecraft.world.World;
/*  34:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  35:    */ 
/*  36:    */ public class BlockTransferPipe
/*  37:    */   extends BlockMultiBlockSelection
/*  38:    */   implements ICreativeTabSorting, IPipeBlock
/*  39:    */ {
/*  40:    */   public static final float pipe_width = 0.125F;
/*  41:    */   public static IIcon pipes_oneway;
/*  42: 57 */   public static IIcon pipes_nozzle = null;
/*  43: 58 */   public static IIcon pipes_grouping = null;
/*  44: 59 */   public static IIcon pipes = null;
/*  45: 60 */   public static IIcon pipes_noninserting = null;
/*  46: 61 */   public static IIcon pipes_xover = null;
/*  47: 62 */   public static IIcon pipes_1way = null;
/*  48: 63 */   public static IIcon[] pipes_diamond = new IIcon[6];
/*  49: 64 */   public static IIcon pipes_supply = null;
/*  50: 65 */   public static IIcon pipes_modsorting = null;
/*  51: 66 */   public static IIcon pipes_energy = null;
/*  52: 67 */   public static IIcon pipes_nozzle_energy = null;
/*  53: 68 */   public static IIcon pipes_energy_extract = null;
/*  54: 69 */   public static IIcon pipes_nozzle_energy_extract = null;
/*  55: 70 */   public static IIcon pipes_hyperrationing = null;
/*  56: 71 */   private Random random = XURandom.getInstance();
/*  57:    */   public final int pipePage;
/*  58:    */   
/*  59:    */   public BlockTransferPipe(int pipePage)
/*  60:    */   {
/*  61: 75 */     super(Material.sponge);
/*  62: 76 */     this.pipePage = pipePage;
/*  63: 77 */     setBlockName("extrautils:pipes" + (pipePage == 0 ? "" : new StringBuilder().append(".").append(pipePage).toString()));
/*  64: 78 */     setBlockTextureName("extrautils:pipes");
/*  65: 79 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  66: 80 */     setHardness(0.1F);
/*  67: 81 */     setStepSound(soundTypeStone);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getMobilityFlag()
/*  71:    */   {
/*  72: 90 */     return 0;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int damageDropped(int par1)
/*  76:    */   {
/*  77: 99 */     if ((this.pipePage == 0) && ((par1 <= 7) || (par1 == 15))) {
/*  78:100 */       return 0;
/*  79:    */     }
/*  80:102 */     return par1;
/*  81:    */   }
/*  82:    */   
/*  83:    */   @SideOnly(Side.CLIENT)
/*  84:    */   public void registerBlockIcons(IIconRegister par1IIconRegister)
/*  85:    */   {
/*  86:113 */     pipes = par1IIconRegister.registerIcon("extrautils:pipes");
/*  87:114 */     pipes_oneway = par1IIconRegister.registerIcon("extrautils:pipes_1way");
/*  88:115 */     pipes_nozzle = par1IIconRegister.registerIcon("extrautils:pipes_nozzle");
/*  89:116 */     pipes_grouping = par1IIconRegister.registerIcon("extrautils:pipes_grouping");
/*  90:117 */     pipes_noninserting = par1IIconRegister.registerIcon("extrautils:pipes_noninserting");
/*  91:118 */     pipes_1way = par1IIconRegister.registerIcon("extrautils:pipes_1way2");
/*  92:120 */     for (int i = 0; i < 6; i++) {
/*  93:121 */       pipes_diamond[i] = par1IIconRegister.registerIcon("extrautils:pipes_diamond" + i);
/*  94:    */     }
/*  95:124 */     pipes_supply = par1IIconRegister.registerIcon("extrautils:pipes_supply");
/*  96:125 */     pipes_energy = par1IIconRegister.registerIcon("extrautils:pipes_energy");
/*  97:126 */     pipes_energy_extract = par1IIconRegister.registerIcon("extrautils:pipes_energy_extract");
/*  98:127 */     pipes_xover = par1IIconRegister.registerIcon("extrautils:pipes_crossover");
/*  99:128 */     pipes_modsorting = par1IIconRegister.registerIcon("extrautils:pipes_modgrouping");
/* 100:129 */     pipes_nozzle_energy = par1IIconRegister.registerIcon("extrautils:pipes_nozzle_energy");
/* 101:130 */     pipes_nozzle_energy_extract = par1IIconRegister.registerIcon("extrautils:pipes_nozzle_energy_extract");
/* 102:131 */     pipes_hyperrationing = par1IIconRegister.registerIcon("extrautils:pipes_hypersupply");
/* 103:132 */     super.registerBlockIcons(par1IIconRegister);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public boolean renderAsNormalBlock()
/* 107:    */   {
/* 108:137 */     return false;
/* 109:    */   }
/* 110:    */   
/* 111:    */   @SideOnly(Side.CLIENT)
/* 112:    */   public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/* 113:    */   {
/* 114:146 */     switch (this.pipePage)
/* 115:    */     {
/* 116:    */     case 0: 
/* 117:148 */       par3List.add(new ItemStack(par1, 1, 0));
/* 118:149 */       par3List.add(new ItemStack(par1, 1, 8));
/* 119:150 */       par3List.add(new ItemStack(par1, 1, 9));
/* 120:151 */       par3List.add(new ItemStack(par1, 1, 10));
/* 121:152 */       par3List.add(new ItemStack(par1, 1, 11));
/* 122:153 */       par3List.add(new ItemStack(par1, 1, 12));
/* 123:154 */       par3List.add(new ItemStack(par1, 1, 13));
/* 124:155 */       par3List.add(new ItemStack(par1, 1, 14));
/* 125:156 */       break;
/* 126:    */     case 1: 
/* 127:158 */       par3List.add(new ItemStack(par1, 1, 0));
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
/* 132:    */   {
/* 133:168 */     int metadata = world.getBlockMetadata(x, y, z) + this.pipePage * 16;
/* 134:170 */     if (metadata == 9)
/* 135:    */     {
/* 136:171 */       if (world.getTileEntity(x, y, z) != null) {
/* 137:172 */         par5EntityPlayer.openGui(ExtraUtilsMod.instance, 0, world, x, y, z);
/* 138:    */       }
/* 139:175 */       return true;
/* 140:    */     }
/* 141:178 */     if (XUHelper.isWrench(par5EntityPlayer.getCurrentEquippedItem()))
/* 142:    */     {
/* 143:179 */       metadata = StdPipes.getNextPipeType(world, x, y, z, metadata);
/* 144:180 */       if (metadata < 16) {
/* 145:181 */         world.setBlock(x, y, z, ExtraUtils.transferPipe, metadata, 3);
/* 146:    */       } else {
/* 147:183 */         world.setBlock(x, y, z, ExtraUtils.transferPipe2, metadata - 16, 3);
/* 148:    */       }
/* 149:185 */       return true;
/* 150:    */     }
/* 151:188 */     return false;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public BoxModel getWorldModel(IBlockAccess world, int x, int y, int z)
/* 155:    */   {
/* 156:193 */     return getPipeModel(world, x, y, z, null);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public BoxModel getPipeModel(IBlockAccess world, int x, int y, int z, IPipe pipe_underlying)
/* 160:    */   {
/* 161:197 */     if (pipe_underlying == null)
/* 162:    */     {
/* 163:198 */       pipe_underlying = TNHelper.getPipe(world, x, y, z);
/* 164:200 */       if (pipe_underlying == null) {
/* 165:201 */         return new BoxModel();
/* 166:    */       }
/* 167:    */     }
/* 168:205 */     if (!(pipe_underlying instanceof IPipeCosmetic)) {
/* 169:206 */       return new BoxModel();
/* 170:    */     }
/* 171:209 */     IPipeCosmetic pipe = (IPipeCosmetic)pipe_underlying;
/* 172:210 */     BoxModel boxes = new BoxModel();
/* 173:212 */     for (int i = 0; i < 6; i++)
/* 174:    */     {
/* 175:213 */       ForgeDirection dir = ForgeDirection.getOrientation(i);
/* 176:215 */       if (TNHelper.getPipe(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) != null)
/* 177:    */       {
/* 178:216 */         if (TNHelper.doesPipeConnect(world, x, y, z, dir)) {
/* 179:217 */           boxes.add(new Box(0.375F, 0.0F, 0.375F, 0.625F, 0.375F, 0.625F).rotateToSide(dir).setTexture(pipe.pipeTexture(dir, !TNHelper.canInput(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir.getOpposite()))).setLabel("pipe"));
/* 180:    */         }
/* 181:    */       }
/* 182:    */       else
/* 183:    */       {
/* 184:221 */         TileEntity tile = world.getTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
/* 185:223 */         if ((tile != null) && (pipe_underlying.shouldConnectToTile(world, x, y, z, dir)))
/* 186:    */         {
/* 187:224 */           boxes.add(new Box(0.375F, 0.1875F, 0.375F, 0.625F, 0.375F, 0.625F).rotateToSide(dir).setTexture(pipe.invPipeTexture(dir)).setLabel("pipe"));
/* 188:    */           
/* 189:226 */           boxes.add(new Box(0.3125F, 0.0F, 0.3125F, 0.6875F, 0.1875F, 0.6875F).rotateToSide(dir).setTexture(pipe.socketTexture(dir)).setLabel("nozzle"));
/* 190:    */         }
/* 191:    */       }
/* 192:    */     }
/* 193:231 */     boxes.add(new Box(0.5F - pipe.baseSize(), 0.5F - pipe.baseSize(), 0.5F - pipe.baseSize(), 0.5F + pipe.baseSize(), 0.5F + pipe.baseSize(), 0.5F + pipe.baseSize()).setTexture(pipe.baseTexture()).setLabel("base"));
/* 194:    */     
/* 195:233 */     return boxes;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public BoxModel getInventoryModel(int metadata)
/* 199:    */   {
/* 200:238 */     BoxModel boxes = new BoxModel();
/* 201:239 */     IPipe pipe = getPipe(metadata);
/* 202:241 */     if (pipe != null)
/* 203:    */     {
/* 204:242 */       for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
/* 205:243 */         boxes.add(new Box(0.375F, 0.0F, 0.375F, 0.625F, 0.375F, 0.625F).rotateToSide(dir).setTexture(((IPipeCosmetic)pipe).invPipeTexture(dir)).setLabel("pipe"));
/* 206:    */       }
/* 207:247 */       if (((IPipeCosmetic)getPipe(metadata)).baseSize() > 0.125F) {
/* 208:248 */         boxes.add(new Box(0.5F - ((IPipeCosmetic)pipe).baseSize(), 0.5F - ((IPipeCosmetic)pipe).baseSize(), 0.5F - ((IPipeCosmetic)pipe).baseSize(), 0.5F + ((IPipeCosmetic)pipe).baseSize(), 0.5F + ((IPipeCosmetic)pipe).baseSize(), 0.5F + ((IPipeCosmetic)pipe).baseSize()).setTexture(((IPipeCosmetic)getPipe(metadata)).baseTexture()).setLabel("base"));
/* 209:    */       }
/* 210:    */     }
/* 211:253 */     return boxes;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void prepareForRender(String label) {}
/* 215:    */   
/* 216:    */   public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6)
/* 217:    */   {
/* 218:266 */     TileEntity tile = par1World.getTileEntity(par2, par3, par4);
/* 219:268 */     if ((par1World.getBlock(par2, par3, par4) != FMPBase.getFMPBlockId()) && 
/* 220:269 */       ((tile instanceof TileEntityFilterPipe)))
/* 221:    */     {
/* 222:270 */       TileEntityFilterPipe tileentity = (TileEntityFilterPipe)tile;
/* 223:272 */       if (tileentity.items != null)
/* 224:    */       {
/* 225:273 */         for (int i = 0; i < 6; i++)
/* 226:    */         {
/* 227:274 */           ItemStack itemstack = tileentity.items.getStackInSlot(i);
/* 228:276 */           if (itemstack != null)
/* 229:    */           {
/* 230:277 */             float f = this.random.nextFloat() * 0.8F + 0.1F;
/* 231:278 */             float f1 = this.random.nextFloat() * 0.8F + 0.1F;
/* 232:    */             EntityItem entityitem;
/* 233:281 */             for (float f2 = this.random.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; par1World.spawnEntityInWorld(entityitem))
/* 234:    */             {
/* 235:282 */               int k1 = this.random.nextInt(21) + 10;
/* 236:284 */               if (k1 > itemstack.stackSize) {
/* 237:285 */                 k1 = itemstack.stackSize;
/* 238:    */               }
/* 239:288 */               itemstack.stackSize -= k1;
/* 240:289 */               entityitem = new EntityItem(par1World, par2 + f, par3 + f1, par4 + f2, new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));
/* 241:290 */               float f3 = 0.05F;
/* 242:291 */               entityitem.motionX = ((float)this.random.nextGaussian() * f3);
/* 243:292 */               entityitem.motionY = ((float)this.random.nextGaussian() * f3 + 0.2F);
/* 244:293 */               entityitem.motionZ = ((float)this.random.nextGaussian() * f3);
/* 245:295 */               if (itemstack.hasTagCompound()) {
/* 246:296 */                 entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
/* 247:    */               }
/* 248:    */             }
/* 249:    */           }
/* 250:    */         }
/* 251:302 */         par1World.func_147453_f(par2, par3, par4, par5);
/* 252:    */       }
/* 253:    */     }
/* 254:306 */     super.breakBlock(par1World, par2, par3, par4, par5, par6);
/* 255:    */   }
/* 256:    */   
/* 257:    */   public boolean hasTileEntity(int metadata)
/* 258:    */   {
/* 259:311 */     return metadata == 9;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public TileEntity createTileEntity(World world, int metadata)
/* 263:    */   {
/* 264:317 */     return new TileEntityFilterPipe();
/* 265:    */   }
/* 266:    */   
/* 267:    */   public String getSortingName(ItemStack par1ItemStack)
/* 268:    */   {
/* 269:322 */     ItemStack i2 = par1ItemStack.copy();
/* 270:323 */     i2.setItemDamage(0);
/* 271:324 */     return i2.getDisplayName() + "_" + par1ItemStack.getDisplayName();
/* 272:    */   }
/* 273:    */   
/* 274:    */   public IPipe getPipe(int metadata)
/* 275:    */   {
/* 276:329 */     return StdPipes.getPipeType(metadata + this.pipePage * 16);
/* 277:    */   }
/* 278:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.BlockTransferPipe
 * JD-Core Version:    0.7.0.1
 */