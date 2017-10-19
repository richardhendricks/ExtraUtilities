/*   1:    */ package com.rwtema.extrautils.tileentity.transfernodes;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.ExtraUtilsMod;
/*   5:    */ import com.rwtema.extrautils.block.Box;
/*   6:    */ import com.rwtema.extrautils.block.BoxModel;
/*   7:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   8:    */ import com.rwtema.extrautils.helper.XURandom;
/*   9:    */ import com.rwtema.extrautils.multipart.FMPBase;
/*  10:    */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INode;
/*  11:    */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeBuffer;
/*  12:    */ import com.rwtema.extrautils.tileentity.transfernodes.pipes.StdPipes;
/*  13:    */ import cpw.mods.fml.relauncher.Side;
/*  14:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Random;
/*  17:    */ import net.minecraft.block.Block;
/*  18:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  19:    */ import net.minecraft.creativetab.CreativeTabs;
/*  20:    */ import net.minecraft.entity.item.EntityItem;
/*  21:    */ import net.minecraft.entity.player.EntityPlayer;
/*  22:    */ import net.minecraft.item.Item;
/*  23:    */ import net.minecraft.item.ItemStack;
/*  24:    */ import net.minecraft.nbt.NBTTagCompound;
/*  25:    */ import net.minecraft.tileentity.TileEntity;
/*  26:    */ import net.minecraft.util.IIcon;
/*  27:    */ import net.minecraft.world.IBlockAccess;
/*  28:    */ import net.minecraft.world.World;
/*  29:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  30:    */ 
/*  31:    */ public class BlockTransferNode
/*  32:    */   extends BlockTransferPipe
/*  33:    */ {
/*  34:    */   public static IIcon nodeBase;
/*  35:    */   public static IIcon nodeSideInsert;
/*  36:    */   public static IIcon nodeSideExtract;
/*  37:    */   public static IIcon nodeSideLiquid;
/*  38:    */   public static IIcon nodeSideEnergy;
/*  39:    */   public static IIcon nodeSideEnergyHyper;
/*  40:    */   public static IIcon particle;
/*  41: 40 */   private final Random random = XURandom.getInstance();
/*  42: 41 */   private String curBlockLabel = "";
/*  43:    */   
/*  44:    */   public BlockTransferNode()
/*  45:    */   {
/*  46: 44 */     super(0);
/*  47: 45 */     setBlockName("extrautils:extractor_base");
/*  48: 46 */     setBlockTextureName("extrautils:extractor_base");
/*  49: 47 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  50: 48 */     setHardness(0.5F);
/*  51: 49 */     setStepSound(soundTypeStone);
/*  52:    */   }
/*  53:    */   
/*  54:    */   @SideOnly(Side.CLIENT)
/*  55:    */   public void registerBlockIcons(IIconRegister par1IIconRegister)
/*  56:    */   {
/*  57: 55 */     nodeBase = par1IIconRegister.registerIcon("extrautils:extractor_base");
/*  58: 56 */     nodeSideEnergy = par1IIconRegister.registerIcon("extrautils:extractor_energy");
/*  59: 57 */     nodeSideEnergyHyper = par1IIconRegister.registerIcon("extrautils:extractor_energy_hyper");
/*  60: 58 */     nodeSideLiquid = par1IIconRegister.registerIcon("extrautils:extractor_liquid");
/*  61: 59 */     nodeSideExtract = par1IIconRegister.registerIcon("extrautils:extractor_extract");
/*  62: 60 */     particle = par1IIconRegister.registerIcon("extrautils:particle");
/*  63: 61 */     super.registerBlockIcons(par1IIconRegister);
/*  64:    */   }
/*  65:    */   
/*  66:    */   @SideOnly(Side.CLIENT)
/*  67:    */   public IIcon getIcon(int par1, int par2)
/*  68:    */   {
/*  69: 70 */     if (par2 < 6) {
/*  70: 71 */       return par1 == par2 % 6 ? nodeBase : nodeSideExtract;
/*  71:    */     }
/*  72: 72 */     if (par2 < 12) {
/*  73: 73 */       return par1 == par2 % 6 ? nodeBase : nodeSideLiquid;
/*  74:    */     }
/*  75: 74 */     if (par2 == 13) {
/*  76: 75 */       return nodeSideEnergyHyper;
/*  77:    */     }
/*  78: 77 */     return nodeSideEnergy;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void prepareForRender(String label)
/*  82:    */   {
/*  83: 84 */     this.curBlockLabel = label;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public BoxModel getWorldModel(IBlockAccess world, int x, int y, int z)
/*  87:    */   {
/*  88: 89 */     int pipe_type = 0;int metadata = 0;int node_dir = 0;
/*  89: 92 */     if (!(world.getTileEntity(x, y, z) instanceof INode)) {
/*  90: 93 */       return new BoxModel();
/*  91:    */     }
/*  92: 96 */     INode node = (INode)world.getTileEntity(x, y, z);
/*  93:    */     
/*  94: 98 */     BoxModel boxes = node.getModel(node.getNodeDir());
/*  95: 99 */     List pipe_boxes = getPipeModel(world, x, y, z, null);
/*  96:101 */     if (pipe_boxes.size() > 1) {
/*  97:102 */       boxes.addAll(pipe_boxes);
/*  98:    */     }
/*  99:105 */     return boxes;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public BoxModel getInventoryModel(int metadata)
/* 103:    */   {
/* 104:110 */     if ((metadata == 12) || (metadata == 13)) {
/* 105:111 */       return getEnergyModel();
/* 106:    */     }
/* 107:114 */     return getModel(0);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public BoxModel getEnergyModel()
/* 111:    */   {
/* 112:118 */     BoxModel boxes = new BoxModel();
/* 113:119 */     boxes.add(new Box(0.1875F, 0.3125F, 0.3125F, 0.8125F, 0.6875F, 0.6875F));
/* 114:120 */     boxes.add(new Box(0.3125F, 0.1875F, 0.3125F, 0.6875F, 0.8125F, 0.6875F));
/* 115:121 */     boxes.add(new Box(0.3125F, 0.3125F, 0.1875F, 0.6875F, 0.6875F, 0.8125F));
/* 116:122 */     boxes.add(new Box(0.25F, 0.25F, 0.25F, 0.75F, 0.75F, 0.75F));
/* 117:123 */     return boxes;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public BoxModel getModel(int metadata)
/* 121:    */   {
/* 122:127 */     ForgeDirection dir = ForgeDirection.getOrientation(metadata);
/* 123:128 */     BoxModel boxes = new BoxModel();
/* 124:129 */     float w = 0.125F;
/* 125:130 */     boxes.add(new Box(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.0625F, 0.9375F).rotateToSide(dir).setTextureSides(new Object[] { Integer.valueOf(dir.ordinal()), nodeBase }));
/* 126:131 */     boxes.add(new Box(0.1875F, 0.0625F, 0.1875F, 0.8125F, 0.25F, 0.8125F).rotateToSide(dir));
/* 127:132 */     boxes.add(new Box(0.3125F, 0.25F, 0.3125F, 0.6875F, 0.375F, 0.6875F).rotateToSide(dir));
/* 128:133 */     boxes.add(new Box(0.375F, 0.25F, 0.375F, 0.625F, 0.375F, 0.625F).rotateToSide(dir).setTexture(nodeBase).setAllSideInvisible().setSideInvisible(new Object[] { Integer.valueOf(dir.getOpposite().ordinal()), Boolean.valueOf(false) }));
/* 129:134 */     return boxes;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
/* 133:    */   {
/* 134:142 */     if (par1World.isClient) {
/* 135:143 */       return true;
/* 136:    */     }
/* 137:146 */     TileEntityTransferNode node = (TileEntityTransferNode)par1World.getTileEntity(par2, par3, par4);
/* 138:148 */     if (node == null) {
/* 139:149 */       return false;
/* 140:    */     }
/* 141:152 */     if (par5EntityPlayer.getCurrentEquippedItem() != null) {
/* 142:155 */       if (XUHelper.isWrench(par5EntityPlayer.getCurrentEquippedItem()))
/* 143:    */       {
/* 144:156 */         node.pipe_type = StdPipes.getNextPipeType(par1World, par2, par3, par4, node.pipe_type);
/* 145:157 */         node.onInventoryChanged();
/* 146:158 */         par1World.markBlockForUpdate(par2, par3, par4);
/* 147:159 */         return true;
/* 148:    */       }
/* 149:    */     }
/* 150:163 */     par5EntityPlayer.openGui(ExtraUtilsMod.instance, 0, node.getWorldObj(), node.xCoord, node.yCoord, node.zCoord);
/* 151:164 */     return true;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public int damageDropped(int par1)
/* 155:    */   {
/* 156:173 */     if (par1 < 6) {
/* 157:174 */       return 0;
/* 158:    */     }
/* 159:175 */     if (par1 < 12) {
/* 160:176 */       return 6;
/* 161:    */     }
/* 162:178 */     return par1;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public boolean renderAsNormalBlock()
/* 166:    */   {
/* 167:184 */     return false;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
/* 171:    */   {
/* 172:193 */     if (par9 < 12) {
/* 173:194 */       return ForgeDirection.OPPOSITES[par5] + par9 & 0xF;
/* 174:    */     }
/* 175:196 */     return par9 & 0xF;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block par5)
/* 179:    */   {
/* 180:202 */     if ((par1World.getTileEntity(par2, par3, par4) instanceof TileEntityTransferNode)) {
/* 181:203 */       ((TileEntityTransferNode)par1World.getTileEntity(par2, par3, par4)).updateRedstone();
/* 182:    */     }
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void breakBlock(World world, int x, int y, int z, Block par5, int par6)
/* 186:    */   {
/* 187:209 */     if ((world.getBlock(x, y, z) != FMPBase.getFMPBlockId()) && 
/* 188:210 */       ((world.getTileEntity(x, y, z) instanceof TileEntityTransferNode)))
/* 189:    */     {
/* 190:211 */       TileEntityTransferNode tile = (TileEntityTransferNode)world.getTileEntity(x, y, z);
/* 191:213 */       if (!tile.getUpgrades().isEmpty()) {
/* 192:214 */         for (int i = 0; i < tile.getUpgrades().size(); i++)
/* 193:    */         {
/* 194:215 */           ItemStack itemstack = (ItemStack)tile.getUpgrades().get(i);
/* 195:216 */           float f = this.random.nextFloat() * 0.8F + 0.1F;
/* 196:217 */           float f1 = this.random.nextFloat() * 0.8F + 0.1F;
/* 197:    */           EntityItem entityitem;
/* 198:220 */           for (float f2 = this.random.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; world.spawnEntityInWorld(entityitem))
/* 199:    */           {
/* 200:221 */             int k1 = this.random.nextInt(21) + 10;
/* 201:223 */             if (k1 > itemstack.stackSize) {
/* 202:224 */               k1 = itemstack.stackSize;
/* 203:    */             }
/* 204:227 */             itemstack.stackSize -= k1;
/* 205:228 */             entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));
/* 206:229 */             float f3 = 0.05F;
/* 207:230 */             entityitem.motionX = ((float)this.random.nextGaussian() * f3);
/* 208:231 */             entityitem.motionY = ((float)this.random.nextGaussian() * f3 + 0.2F);
/* 209:232 */             entityitem.motionZ = ((float)this.random.nextGaussian() * f3);
/* 210:234 */             if (itemstack.hasTagCompound()) {
/* 211:235 */               entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
/* 212:    */             }
/* 213:    */           }
/* 214:    */         }
/* 215:    */       }
/* 216:241 */       if ((tile instanceof TileEntityTransferNodeInventory))
/* 217:    */       {
/* 218:242 */         TileEntityTransferNodeInventory tileentity = (TileEntityTransferNodeInventory)tile;
/* 219:    */         
/* 220:244 */         ItemStack itemstack = tileentity.getStackInSlot(0);
/* 221:246 */         if (itemstack != null)
/* 222:    */         {
/* 223:247 */           float f = this.random.nextFloat() * 0.8F + 0.1F;
/* 224:248 */           float f1 = this.random.nextFloat() * 0.8F + 0.1F;
/* 225:    */           EntityItem entityitem;
/* 226:251 */           for (float f2 = this.random.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; world.spawnEntityInWorld(entityitem))
/* 227:    */           {
/* 228:252 */             int k1 = this.random.nextInt(21) + 10;
/* 229:254 */             if (k1 > itemstack.stackSize) {
/* 230:255 */               k1 = itemstack.stackSize;
/* 231:    */             }
/* 232:258 */             itemstack.stackSize -= k1;
/* 233:259 */             entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));
/* 234:260 */             float f3 = 0.05F;
/* 235:261 */             entityitem.motionX = ((float)this.random.nextGaussian() * f3);
/* 236:262 */             entityitem.motionY = ((float)this.random.nextGaussian() * f3 + 0.2F);
/* 237:263 */             entityitem.motionZ = ((float)this.random.nextGaussian() * f3);
/* 238:265 */             if (itemstack.hasTagCompound()) {
/* 239:266 */               entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
/* 240:    */             }
/* 241:    */           }
/* 242:    */         }
/* 243:271 */         world.func_147453_f(x, y, z, par5);
/* 244:    */       }
/* 245:    */     }
/* 246:276 */     super.breakBlock(world, x, y, z, par5, par6);
/* 247:    */   }
/* 248:    */   
/* 249:    */   @SideOnly(Side.CLIENT)
/* 250:    */   public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/* 251:    */   {
/* 252:285 */     par3List.add(new ItemStack(par1, 1, 0));
/* 253:286 */     par3List.add(new ItemStack(par1, 1, 6));
/* 254:287 */     par3List.add(new ItemStack(par1, 1, 12));
/* 255:288 */     par3List.add(new ItemStack(par1, 1, 13));
/* 256:    */   }
/* 257:    */   
/* 258:    */   public boolean hasTileEntity(int metadata)
/* 259:    */   {
/* 260:304 */     return true;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public TileEntity createTileEntity(World world, int metadata)
/* 264:    */   {
/* 265:320 */     if (metadata == 13) {
/* 266:321 */       return new TileEntityTransferNodeHyperEnergy();
/* 267:    */     }
/* 268:322 */     if (metadata == 12) {
/* 269:323 */       return new TileEntityTransferNodeEnergy();
/* 270:    */     }
/* 271:324 */     if ((metadata >= 6) && (metadata < 12)) {
/* 272:325 */       return new TileEntityTransferNodeLiquid();
/* 273:    */     }
/* 274:327 */     return new TileEntityTransferNodeInventory();
/* 275:    */   }
/* 276:    */   
/* 277:    */   public boolean getWeakChanges(IBlockAccess world, int x, int y, int z)
/* 278:    */   {
/* 279:333 */     return true;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public boolean hasComparatorInputOverride()
/* 283:    */   {
/* 284:338 */     return true;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public int getComparatorInputOverride(World world, int x, int y, int z, int par5)
/* 288:    */   {
/* 289:343 */     if ((world.getTileEntity(x, y, z) instanceof TileEntityTransferNode))
/* 290:    */     {
/* 291:344 */       TileEntityTransferNode tile = (TileEntityTransferNode)world.getTileEntity(x, y, z);
/* 292:346 */       if (tile.buffer.shouldSearch()) {
/* 293:347 */         return 15;
/* 294:    */       }
/* 295:    */     }
/* 296:351 */     return 0;
/* 297:    */   }
/* 298:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.BlockTransferNode
 * JD-Core Version:    0.7.0.1
 */