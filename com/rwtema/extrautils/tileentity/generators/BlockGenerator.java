/*   1:    */ package com.rwtema.extrautils.tileentity.generators;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.ExtraUtilsMod;
/*   5:    */ import com.rwtema.extrautils.block.BlockMultiBlock;
/*   6:    */ import com.rwtema.extrautils.block.Box;
/*   7:    */ import com.rwtema.extrautils.block.BoxModel;
/*   8:    */ import com.rwtema.extrautils.block.IBlockTooltip;
/*   9:    */ import com.rwtema.extrautils.block.IMultiBoxBlock;
/*  10:    */ import com.rwtema.extrautils.helper.XURandom;
/*  11:    */ import com.rwtema.extrautils.texture.TextureMultiIcon;
/*  12:    */ import cpw.mods.fml.common.registry.GameRegistry;
/*  13:    */ import cpw.mods.fml.relauncher.Side;
/*  14:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Random;
/*  18:    */ import net.minecraft.block.Block;
/*  19:    */ import net.minecraft.block.material.Material;
/*  20:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  21:    */ import net.minecraft.creativetab.CreativeTabs;
/*  22:    */ import net.minecraft.entity.EntityLivingBase;
/*  23:    */ import net.minecraft.entity.item.EntityItem;
/*  24:    */ import net.minecraft.entity.player.EntityPlayer;
/*  25:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  26:    */ import net.minecraft.init.Blocks;
/*  27:    */ import net.minecraft.init.Items;
/*  28:    */ import net.minecraft.inventory.IInventory;
/*  29:    */ import net.minecraft.item.Item;
/*  30:    */ import net.minecraft.item.ItemStack;
/*  31:    */ import net.minecraft.nbt.NBTTagCompound;
/*  32:    */ import net.minecraft.tileentity.TileEntity;
/*  33:    */ import net.minecraft.util.IIcon;
/*  34:    */ import net.minecraft.util.MathHelper;
/*  35:    */ import net.minecraft.world.IBlockAccess;
/*  36:    */ import net.minecraft.world.World;
/*  37:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  38:    */ import net.minecraftforge.fluids.FluidContainerRegistry;
/*  39:    */ import net.minecraftforge.fluids.FluidStack;
/*  40:    */ import net.minecraftforge.fluids.IFluidHandler;
/*  41:    */ 
/*  42:    */ public class BlockGenerator
/*  43:    */   extends BlockMultiBlock
/*  44:    */   implements IMultiBoxBlock, IBlockTooltip
/*  45:    */ {
/*  46:    */   @SideOnly(Side.CLIENT)
/*  47:    */   public void randomDisplayTick(World world, int x, int y, int z, Random rand)
/*  48:    */   {
/*  49: 59 */     TileEntity tile = world.getTileEntity(x, y, z);
/*  50: 60 */     if ((tile instanceof TileEntityGenerator)) {
/*  51: 61 */       ((TileEntityGenerator)tile).doRandomDisplayTickR(rand);
/*  52:    */     }
/*  53: 63 */     super.randomDisplayTick(world, x, y, z, rand);
/*  54:    */   }
/*  55:    */   
/*  56: 66 */   private static final int[] rotInd = { 0, 1, 4, 5, 3, 2 };
/*  57: 67 */   public static int num_gens = 0;
/*  58: 68 */   public static Class<? extends TileEntityGenerator>[] tiles = new Class[16];
/*  59: 69 */   public static String[] textures = new String[16];
/*  60: 70 */   public static String[] names = new String[16];
/*  61:    */   
/*  62:    */   static
/*  63:    */   {
/*  64: 73 */     addName(0, "stone", "Survivalist", TileEntityGeneratorFurnaceSurvival.class);
/*  65: 74 */     addName(1, "base", "Furnace", TileEntityGeneratorFurnace.class);
/*  66: 75 */     addName(2, "lava", "Lava", TileEntityGeneratorMagma.class);
/*  67: 76 */     addName(3, "ender", "Ender", TileEntityGeneratorEnder.class);
/*  68: 77 */     addName(4, "redflux", "Heated Redstone", TileEntityGeneratorRedFlux.class);
/*  69: 78 */     addName(5, "food", "Culinary", TileEntityGeneratorFood.class);
/*  70: 79 */     addName(6, "potion", "Potions", TileEntityGeneratorPotion.class);
/*  71: 80 */     addName(7, "solar", "Solar", TileEntityGeneratorSolar.class);
/*  72: 81 */     addName(8, "tnt", "TNT", TileEntityGeneratorTNT.class);
/*  73: 82 */     addName(9, "pink", "Pink", TileEntityGeneratorPink.class);
/*  74: 83 */     addName(10, "overclocked", "High-temperature Furnace", TileEntityGeneratorFurnaceOverClocked.class);
/*  75: 84 */     addName(11, "nether", "Nether Star", TileEntityGeneratorNether.class);
/*  76:    */   }
/*  77:    */   
/*  78: 87 */   public static Random random = XURandom.getInstance();
/*  79:    */   public final int numGenerators;
/*  80: 89 */   private IIcon[][] icons = new IIcon[16][8];
/*  81:    */   
/*  82:    */   public BlockGenerator()
/*  83:    */   {
/*  84: 92 */     this(1);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public BlockGenerator(int numGenerators)
/*  88:    */   {
/*  89: 97 */     super(Material.rock);
/*  90: 98 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  91: 99 */     setBlockName("extrautils:generator" + (numGenerators > 1 ? "." + numGenerators : ""));
/*  92:100 */     setHardness(5.0F);
/*  93:101 */     setStepSound(Block.soundTypeMetal);
/*  94:102 */     this.numGenerators = numGenerators;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public static void addUpgradeRecipes(Block a, Block b)
/*  98:    */   {
/*  99:106 */     for (int i = 0; i < num_gens; i++) {
/* 100:107 */       GameRegistry.addRecipe(new ItemStack(b, 1, i), new Object[] { "SSS", "SES", "SSS", Character.valueOf('S'), new ItemStack(a, 1, i), Character.valueOf('E'), ExtraUtils.transferNodeEnabled ? new ItemStack(ExtraUtils.transferNode, 1, 12) : Blocks.redstone_block });
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static void addSuperUpgradeRecipes(Block a, Block b)
/* 105:    */   {
/* 106:113 */     for (int i = 0; i < num_gens; i++) {
/* 107:114 */       GameRegistry.addRecipe(new ItemStack(b, 1, i), new Object[] { "SSS", "SES", "SSS", Character.valueOf('S'), new ItemStack(a, 1, i), Character.valueOf('E'), ExtraUtils.transferNodeEnabled ? new ItemStack(ExtraUtils.transferNode, 1, 13) : Blocks.redstone_block });
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   public static void addRecipes()
/* 112:    */   {
/* 113:120 */     GameRegistry.addRecipe(new ItemStack(ExtraUtils.generator, 1, 0), new Object[] { "iii", "ItI", "rfr", Character.valueOf('i'), Blocks.cobblestone, Character.valueOf('I'), Items.iron_ingot, Character.valueOf('f'), Blocks.furnace, Character.valueOf('r'), Items.redstone, Character.valueOf('t'), Blocks.piston });
/* 114:121 */     GameRegistry.addRecipe(new ItemStack(ExtraUtils.generator, 1, 5), new Object[] { "iii", "iIi", "rfr", Character.valueOf('i'), Items.iron_ingot, Character.valueOf('I'), new ItemStack(ExtraUtils.generator, 1, 0), Character.valueOf('f'), Blocks.furnace, Character.valueOf('r'), Items.redstone });
/* 115:122 */     GameRegistry.addRecipe(new ItemStack(ExtraUtils.generator, 1, 1), new Object[] { "iii", "iIi", "rfr", Character.valueOf('i'), Items.iron_ingot, Character.valueOf('I'), Blocks.iron_block, Character.valueOf('f'), Blocks.furnace, Character.valueOf('r'), Items.redstone });
/* 116:123 */     GameRegistry.addRecipe(new ItemStack(ExtraUtils.generator, 1, 2), new Object[] { "iii", "iIi", "rfr", Character.valueOf('i'), Items.gold_ingot, Character.valueOf('I'), Blocks.iron_block, Character.valueOf('f'), Blocks.furnace, Character.valueOf('r'), Items.redstone });
/* 117:124 */     GameRegistry.addRecipe(new ItemStack(ExtraUtils.generator, 1, 3), new Object[] { "eee", "iIi", "rfr", Character.valueOf('e'), Items.ender_pearl, Character.valueOf('i'), Items.ender_eye, Character.valueOf('I'), Blocks.iron_block, Character.valueOf('f'), Blocks.furnace, Character.valueOf('r'), Items.redstone });
/* 118:    */     
/* 119:126 */     GameRegistry.addRecipe(new ItemStack(ExtraUtils.generator, 1, 4), new Object[] { "iii", "iIi", "rfr", Character.valueOf('i'), Blocks.redstone_block, Character.valueOf('I'), new ItemStack(ExtraUtils.generator, 1, 2), Character.valueOf('f'), Blocks.furnace, Character.valueOf('r'), Items.redstone });
/* 120:    */     
/* 121:128 */     GameRegistry.addRecipe(new ItemStack(ExtraUtils.generator, 1, 6), new Object[] { "iii", "iIi", "rfr", Character.valueOf('i'), Blocks.obsidian, Character.valueOf('I'), Blocks.enchanting_table, Character.valueOf('f'), Blocks.furnace, Character.valueOf('r'), Items.redstone });
/* 122:129 */     GameRegistry.addRecipe(new ItemStack(ExtraUtils.generator, 1, 7), new Object[] { "lql", "qIq", "rfr", Character.valueOf('q'), Items.quartz, Character.valueOf('l'), new ItemStack(Items.dye, 1, 4), Character.valueOf('I'), Blocks.diamond_block, Character.valueOf('f'), Blocks.furnace, Character.valueOf('r'), Items.redstone });
/* 123:    */     
/* 124:131 */     GameRegistry.addRecipe(new ItemStack(ExtraUtils.generator, 1, 8), new Object[] { "iii", "iIi", "rfr", Character.valueOf('i'), Blocks.tnt, Character.valueOf('I'), Blocks.iron_block, Character.valueOf('f'), Blocks.furnace, Character.valueOf('r'), Items.redstone });
/* 125:132 */     GameRegistry.addRecipe(new ItemStack(ExtraUtils.generator, 1, 9), new Object[] { "iii", "iIi", "rfr", Character.valueOf('i'), new ItemStack(Blocks.wool, 1, 6), Character.valueOf('I'), new ItemStack(ExtraUtils.generator, 1, 0), Character.valueOf('f'), Blocks.furnace, Character.valueOf('r'), Items.redstone });
/* 126:    */     
/* 127:134 */     GameRegistry.addRecipe(new ItemStack(ExtraUtils.generator, 1, 10), new Object[] { "iii", "iIi", "rfr", Character.valueOf('i'), Items.iron_ingot, Character.valueOf('I'), new ItemStack(ExtraUtils.generator, 1, 1), Character.valueOf('f'), Blocks.furnace, Character.valueOf('r'), Items.redstone });
/* 128:135 */     GameRegistry.addRecipe(new ItemStack(ExtraUtils.generator, 1, 11), new Object[] { "wiw", "wIw", "rfr", Character.valueOf('w'), new ItemStack(Items.skull, 1, 1), Character.valueOf('i'), Items.nether_star, Character.valueOf('I'), ExtraUtils.decorative1Enabled ? new ItemStack(ExtraUtils.decorative1, 1, 5) : Blocks.iron_block, Character.valueOf('f'), Blocks.furnace, Character.valueOf('r'), Items.redstone });
/* 129:    */   }
/* 130:    */   
/* 131:    */   public static void mapTiles()
/* 132:    */   {
/* 133:140 */     for (int i = 0; i < num_gens; i++) {
/* 134:141 */       ExtraUtils.registerTile(tiles[i], "extrautils:generator" + textures[i]);
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   public static void addName(int i, String texture, String name, Class<? extends TileEntityGenerator> clazz)
/* 139:    */   {
/* 140:146 */     textures[i] = texture;
/* 141:147 */     names[i] = name;
/* 142:148 */     tiles[i] = clazz;
/* 143:149 */     num_gens = Math.max(num_gens, i + 1);
/* 144:    */   }
/* 145:    */   
/* 146:    */   public boolean onBlockActivated(World worldObj, int x, int y, int z, EntityPlayer player, int side, float dx, float dy, float dz)
/* 147:    */   {
/* 148:154 */     if (worldObj.isClient) {
/* 149:155 */       return true;
/* 150:    */     }
/* 151:158 */     TileEntity tile = worldObj.getTileEntity(x, y, z);
/* 152:160 */     if ((player.getCurrentEquippedItem() != null) && ((tile instanceof IFluidHandler)))
/* 153:    */     {
/* 154:161 */       ItemStack item = player.getCurrentEquippedItem();
/* 155:162 */       FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(item);
/* 156:164 */       if ((fluid != null) && 
/* 157:165 */         (((IFluidHandler)tile).fill(ForgeDirection.getOrientation(side), fluid, false) == fluid.amount))
/* 158:    */       {
/* 159:166 */         ((IFluidHandler)tile).fill(ForgeDirection.getOrientation(side), fluid, true);
/* 160:168 */         if (!player.capabilities.isCreativeMode) {
/* 161:169 */           player.setCurrentItemOrArmor(0, item.getItem().getContainerItem(item));
/* 162:    */         }
/* 163:172 */         return true;
/* 164:    */       }
/* 165:    */     }
/* 166:177 */     player.openGui(ExtraUtilsMod.instance, 0, worldObj, x, y, z);
/* 167:178 */     return true;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public int damageDropped(int par1)
/* 171:    */   {
/* 172:183 */     return par1;
/* 173:    */   }
/* 174:    */   
/* 175:    */   @SideOnly(Side.CLIENT)
/* 176:    */   public IIcon getIcon(int par1, int x)
/* 177:    */   {
/* 178:192 */     return this.icons[x][par1];
/* 179:    */   }
/* 180:    */   
/* 181:    */   @SideOnly(Side.CLIENT)
/* 182:    */   public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
/* 183:    */   {
/* 184:198 */     int rot = ((TileEntityGenerator)world.getTileEntity(x, y, z)).rotation;
/* 185:200 */     for (int i = 0; i < rot; i++) {
/* 186:201 */       side = rotInd[side];
/* 187:    */     }
/* 188:204 */     return this.icons[world.getBlockMetadata(x, y, z)][side];
/* 189:    */   }
/* 190:    */   
/* 191:    */   @SideOnly(Side.CLIENT)
/* 192:    */   public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/* 193:    */   {
/* 194:213 */     for (int i = 0; i < num_gens; i++) {
/* 195:214 */       par3List.add(new ItemStack(par1, 1, i));
/* 196:    */     }
/* 197:    */   }
/* 198:    */   
/* 199:    */   @SideOnly(Side.CLIENT)
/* 200:    */   public void registerBlockIcons(IIconRegister par1IIconRegister)
/* 201:    */   {
/* 202:221 */     int w = this.numGenerators == 64 ? 4 : 3;
/* 203:222 */     String s = this.numGenerators > 1 ? "extrautils:supergenerators/generator_" : this.numGenerators > 8 ? "extrautils:supergenerators/sgenerator_" : "extrautils:generator_";
/* 204:224 */     for (int i = 0; i < num_gens; i++)
/* 205:    */     {
/* 206:225 */       String texture = s + textures[i];
/* 207:227 */       for (int a = 0; a < w; a++) {
/* 208:228 */         for (int b = 0; b < 2; b++) {
/* 209:229 */           this.icons[i][(a * 2 + b)] = TextureMultiIcon.registerGridIcon(par1IIconRegister, texture, a, b, w, 2);
/* 210:    */         }
/* 211:    */       }
/* 212:    */     }
/* 213:    */   }
/* 214:    */   
/* 215:    */   public boolean isOpaqueCube()
/* 216:    */   {
/* 217:246 */     return false;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public boolean hasTileEntity(int metadata)
/* 221:    */   {
/* 222:251 */     return tiles[metadata] != null;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public TileEntity createTileEntity(World world, int metadata)
/* 226:    */   {
/* 227:    */     try
/* 228:    */     {
/* 229:257 */       return (TileEntity)tiles[metadata].newInstance();
/* 230:    */     }
/* 231:    */     catch (Exception e)
/* 232:    */     {
/* 233:259 */       e.printStackTrace();
/* 234:260 */       throw new RuntimeException(e);
/* 235:    */     }
/* 236:    */   }
/* 237:    */   
/* 238:    */   public BoxModel genericModel(int meta)
/* 239:    */   {
/* 240:270 */     if (this.numGenerators > 8) {
/* 241:271 */       return superAdvModel(meta);
/* 242:    */     }
/* 243:272 */     if (this.numGenerators > 1) {
/* 244:273 */       return advModel(meta);
/* 245:    */     }
/* 246:275 */     return standardModel(meta);
/* 247:    */   }
/* 248:    */   
/* 249:    */   public BoxModel superAdvModel(int meta)
/* 250:    */   {
/* 251:279 */     BoxModel model = new BoxModel();
/* 252:280 */     model.addBoxI(0, 0, 0, 16, 5, 16);
/* 253:    */     
/* 254:282 */     model.addBoxI(0, 5, 0, 3, 7, 3);
/* 255:283 */     model.addBoxI(0, 5, 13, 3, 7, 16);
/* 256:284 */     model.addBoxI(13, 5, 0, 16, 7, 3);
/* 257:285 */     model.addBoxI(13, 5, 13, 16, 7, 16);
/* 258:    */     
/* 259:287 */     model.addBoxI(4, 7, 4, 12, 15, 12).setTextureSides(new Object[] { Integer.valueOf(0), this.icons[meta][6], Integer.valueOf(1), this.icons[meta][7] });
/* 260:    */     
/* 261:289 */     return model;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public BoxModel advModel(int meta)
/* 265:    */   {
/* 266:293 */     BoxModel model = new BoxModel();
/* 267:294 */     model.addBoxI(0, 0, 0, 16, 4, 16);
/* 268:295 */     model.addBoxI(0, 12, 0, 16, 16, 16);
/* 269:296 */     model.addBoxI(3, 3, 3, 13, 13, 13);
/* 270:297 */     return model;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public BoxModel standardModel(int meta)
/* 274:    */   {
/* 275:301 */     BoxModel model = new BoxModel();
/* 276:    */     
/* 277:303 */     model.addBoxI(2, 14, 3, 14, 15, 13);
/* 278:304 */     model.addBoxI(1, 9, 2, 15, 14, 14);
/* 279:305 */     model.addBoxI(1, 8, 2, 15, 9, 6).setTextureSides(new Object[] { Integer.valueOf(3), this.icons[meta][2] });
/* 280:306 */     model.addBoxI(1, 8, 10, 15, 9, 14);
/* 281:307 */     model.addBoxI(1, 7, 2, 15, 8, 5);
/* 282:308 */     model.addBoxI(1, 4, 2, 15, 7, 4);
/* 283:    */     
/* 284:310 */     model.addBoxI(2, 8, 6, 14, 9, 10);
/* 285:311 */     model.addBoxI(2, 7, 5, 14, 8, 11).setTextureSides(new Object[] { Integer.valueOf(1), this.icons[meta][0] });
/* 286:312 */     model.addBoxI(2, 3, 4, 14, 7, 12).setTextureSides(new Object[] { Integer.valueOf(1), this.icons[meta][0] });
/* 287:313 */     model.addBoxI(2, 2, 5, 14, 3, 11);
/* 288:314 */     model.addBoxI(2, 1, 6, 14, 2, 10);
/* 289:    */     
/* 290:316 */     model.addBoxI(0, 1, 1, 1, 15, 2);
/* 291:317 */     model.addBoxI(0, 1, 14, 1, 15, 15);
/* 292:318 */     model.addBoxI(15, 1, 1, 16, 15, 2);
/* 293:319 */     model.addBoxI(15, 1, 14, 16, 15, 15);
/* 294:320 */     model.addBoxI(1, 0, 1, 15, 1, 2);
/* 295:321 */     model.addBoxI(1, 0, 14, 15, 1, 15);
/* 296:322 */     model.addBoxI(1, 1, 1, 2, 2, 2);
/* 297:323 */     model.addBoxI(14, 1, 1, 15, 2, 2);
/* 298:324 */     model.addBoxI(1, 1, 14, 2, 2, 15);
/* 299:325 */     model.addBoxI(14, 1, 14, 15, 2, 15);
/* 300:326 */     model.addBoxI(0, 15, 2, 1, 16, 14);
/* 301:327 */     model.addBoxI(15, 15, 2, 16, 16, 14);
/* 302:328 */     model.addBoxI(0, 14, 2, 1, 15, 3);
/* 303:329 */     model.addBoxI(0, 14, 13, 1, 15, 14);
/* 304:330 */     model.addBoxI(15, 14, 2, 16, 15, 3);
/* 305:331 */     model.addBoxI(15, 14, 13, 16, 15, 14);
/* 306:332 */     return model;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public BoxModel getWorldModel(IBlockAccess world, int x, int y, int z)
/* 310:    */   {
/* 311:337 */     return genericModel(world.getBlockMetadata(x, y, z)).rotateY((world.getTileEntity(x, y, z) instanceof TileEntityGenerator) ? ((TileEntityGenerator)world.getTileEntity(x, y, z)).rotation : 0);
/* 312:    */   }
/* 313:    */   
/* 314:    */   public BoxModel getInventoryModel(int metadata)
/* 315:    */   {
/* 316:342 */     return genericModel(metadata).rotateY(1);
/* 317:    */   }
/* 318:    */   
/* 319:    */   public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase par5EntityLivingBase, ItemStack item)
/* 320:    */   {
/* 321:347 */     int l = MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) % 4;
/* 322:348 */     l = (l + 6) % 4;
/* 323:349 */     ((TileEntityGenerator)world.getTileEntity(x, y, z)).rotation = ((byte)l);
/* 324:351 */     if (item.hasTagCompound()) {
/* 325:352 */       ((TileEntityGenerator)world.getTileEntity(x, y, z)).readInvFromTags(item.getTagCompound());
/* 326:    */     }
/* 327:    */   }
/* 328:    */   
/* 329:    */   public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest)
/* 330:    */   {
/* 331:358 */     ArrayList<ItemStack> items = getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
/* 332:360 */     if (world.setBlockToAir(x, y, z))
/* 333:    */     {
/* 334:361 */       if (!world.isClient) {
/* 335:362 */         for (ItemStack item : items) {
/* 336:363 */           if ((player == null) || (!player.capabilities.isCreativeMode) || (item.hasTagCompound())) {
/* 337:364 */             dropBlockAsItem_do(world, x, y, z, item);
/* 338:    */           }
/* 339:    */         }
/* 340:    */       }
/* 341:368 */       return true;
/* 342:    */     }
/* 343:370 */     return false;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
/* 347:    */   {
/* 348:376 */     ArrayList<ItemStack> ret = new ArrayList();
/* 349:377 */     ItemStack item = new ItemStack(this, 1, damageDropped(metadata));
/* 350:379 */     if ((world.getTileEntity(x, y, z) instanceof TileEntityGenerator))
/* 351:    */     {
/* 352:380 */       NBTTagCompound tags = new NBTTagCompound();
/* 353:    */       
/* 354:382 */       ((TileEntityGenerator)world.getTileEntity(x, y, z)).writeInvToTags(tags);
/* 355:384 */       if (!tags.hasNoTags()) {
/* 356:385 */         item.setTagCompound(tags);
/* 357:    */       }
/* 358:    */     }
/* 359:389 */     ret.add(item);
/* 360:390 */     return ret;
/* 361:    */   }
/* 362:    */   
/* 363:    */   public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
/* 364:    */   {
/* 365:395 */     par2EntityPlayer.addStat(net.minecraft.stats.StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
/* 366:396 */     par2EntityPlayer.addExhaustion(0.025F);
/* 367:    */   }
/* 368:    */   
/* 369:    */   public void breakBlock(World world, int x, int y, int z, Block par5, int par6)
/* 370:    */   {
/* 371:401 */     if (((world.getTileEntity(x, y, z) instanceof TileEntityGenerator)) && ((world.getTileEntity(x, y, z) instanceof IInventory)))
/* 372:    */     {
/* 373:402 */       IInventory tile = (IInventory)world.getTileEntity(x, y, z);
/* 374:404 */       for (int i = 0; i < tile.getSizeInventory(); i++)
/* 375:    */       {
/* 376:405 */         ItemStack itemstack = tile.getStackInSlot(i);
/* 377:407 */         if (itemstack != null)
/* 378:    */         {
/* 379:408 */           float f = random.nextFloat() * 0.8F + 0.1F;
/* 380:409 */           float f1 = random.nextFloat() * 0.8F + 0.1F;
/* 381:    */           EntityItem entityitem;
/* 382:412 */           for (float f2 = random.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; world.spawnEntityInWorld(entityitem))
/* 383:    */           {
/* 384:413 */             int k1 = random.nextInt(21) + 10;
/* 385:415 */             if (k1 > itemstack.stackSize) {
/* 386:416 */               k1 = itemstack.stackSize;
/* 387:    */             }
/* 388:419 */             itemstack.stackSize -= k1;
/* 389:420 */             entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));
/* 390:421 */             float f3 = 0.05F;
/* 391:422 */             entityitem.motionX = ((float)random.nextGaussian() * f3);
/* 392:423 */             entityitem.motionY = ((float)random.nextGaussian() * f3 + 0.2F);
/* 393:424 */             entityitem.motionZ = ((float)random.nextGaussian() * f3);
/* 394:426 */             if (itemstack.hasTagCompound()) {
/* 395:427 */               entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
/* 396:    */             }
/* 397:    */           }
/* 398:    */         }
/* 399:    */       }
/* 400:    */     }
/* 401:434 */     super.breakBlock(world, x, y, z, par5, par6);
/* 402:    */   }
/* 403:    */   
/* 404:    */   public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
/* 405:    */   {
/* 406:439 */     par3List.add("Power Multiplier: x" + this.numGenerators);
/* 407:440 */     if ((par1ItemStack.hasTagCompound()) && (par1ItemStack.getTagCompound().hasKey("Energy"))) {
/* 408:441 */       par3List.add(par1ItemStack.getTagCompound().getInteger("Energy") + " / " + 100000 * this.numGenerators + " RF");
/* 409:    */     }
/* 410:    */   }
/* 411:    */   
/* 412:    */   public boolean hasComparatorInputOverride()
/* 413:    */   {
/* 414:447 */     return true;
/* 415:    */   }
/* 416:    */   
/* 417:    */   public int getComparatorInputOverride(World world, int x, int y, int z, int par5)
/* 418:    */   {
/* 419:452 */     TileEntity tile = world.getTileEntity(x, y, z);
/* 420:454 */     if ((tile instanceof TileEntityGenerator)) {
/* 421:455 */       return ((TileEntityGenerator)tile).getCompLevel();
/* 422:    */     }
/* 423:457 */     return 0;
/* 424:    */   }
/* 425:    */   
/* 426:    */   public void prepareForRender(String label) {}
/* 427:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.generators.BlockGenerator
 * JD-Core Version:    0.7.0.1
 */