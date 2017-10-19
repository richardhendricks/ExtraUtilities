/*   1:    */ package com.rwtema.extrautils.block;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import cpw.mods.fml.relauncher.Side;
/*   5:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Random;
/*   8:    */ import net.minecraft.block.Block;
/*   9:    */ import net.minecraft.block.BlockCrops;
/*  10:    */ import net.minecraft.entity.Entity;
/*  11:    */ import net.minecraft.entity.boss.EntityDragon;
/*  12:    */ import net.minecraft.entity.item.EntityItem;
/*  13:    */ import net.minecraft.entity.monster.EntityEnderman;
/*  14:    */ import net.minecraft.entity.player.EntityPlayer;
/*  15:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  16:    */ import net.minecraft.init.Blocks;
/*  17:    */ import net.minecraft.init.Items;
/*  18:    */ import net.minecraft.item.Item;
/*  19:    */ import net.minecraft.item.ItemStack;
/*  20:    */ import net.minecraft.util.DamageSource;
/*  21:    */ import net.minecraft.util.MathHelper;
/*  22:    */ import net.minecraft.world.IBlockAccess;
/*  23:    */ import net.minecraft.world.World;
/*  24:    */ 
/*  25:    */ public class BlockEnderLily
/*  26:    */   extends BlockCrops
/*  27:    */ {
/*  28:    */   public static final long period_fast = 3700L;
/*  29:    */   public static final long period = 24000L;
/*  30:    */   public static final long period_grass = 96000L;
/*  31:    */   
/*  32:    */   public BlockEnderLily()
/*  33:    */   {
/*  34: 31 */     setBlockTextureName("extrautils:plant/ender_lilly");
/*  35: 32 */     setBlockName("extrautils:plant/ender_lilly");
/*  36: 33 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public boolean canBlockStay(World world, int x, int y, int z)
/*  40:    */   {
/*  41: 38 */     return (super.canBlockStay(world, x, y, z)) || (canBePlantedHere(world, x, y, z));
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected void func_149855_e(World p_149855_1_, int p_149855_2_, int p_149855_3_, int p_149855_4_)
/*  45:    */   {
/*  46: 43 */     if ((!canBlockStay(p_149855_1_, p_149855_2_, p_149855_3_, p_149855_4_)) && (!isEndStone(p_149855_1_, p_149855_2_, p_149855_3_ - 1, p_149855_4_)))
/*  47:    */     {
/*  48: 44 */       dropBlockAsItem(p_149855_1_, p_149855_2_, p_149855_3_, p_149855_4_, p_149855_1_.getBlockMetadata(p_149855_2_, p_149855_3_, p_149855_4_), 0);
/*  49: 45 */       p_149855_1_.setBlock(p_149855_2_, p_149855_3_, p_149855_4_, getBlockById(0), 0, 2);
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int getRenderType()
/*  54:    */   {
/*  55: 50 */     return 1;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity)
/*  59:    */   {
/*  60: 55 */     return !(entity instanceof EntityDragon);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
/*  64:    */   {
/*  65: 60 */     if ((par5EntityPlayer.capabilities.isCreativeMode) && (par5EntityPlayer.getCurrentEquippedItem() == null) && (par5EntityPlayer.isSneaking()))
/*  66:    */     {
/*  67: 61 */       if (world.isClient) {
/*  68: 62 */         return true;
/*  69:    */       }
/*  70: 65 */       world.setBlockMetadataWithNotify(x, y, z, (world.getBlockMetadata(x, y, z) + 1) % 8, 2);
/*  71:    */     }
/*  72: 68 */     return false;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public boolean canBePlantedHere(World world, int x, int y, int z)
/*  76:    */   {
/*  77: 72 */     return ((world.isAirBlock(x, y, z)) && ((canThisPlantGrowOnThisBlockID(world.getBlock(x, y - 1, z))) || (isEndStone(world, x, y - 1, z)) || (isSuperEndStone(world, x, y - 1, z)))) || (isFluid(world, x, y - 1, z));
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean isWater(World world, int x, int y, int z)
/*  81:    */   {
/*  82: 76 */     Block block = world.getBlock(x, y, z);
/*  83: 77 */     if ((block != Blocks.water) && (block != Blocks.flowing_water)) {
/*  84: 78 */       return false;
/*  85:    */     }
/*  86: 80 */     return world.getBlockMetadata(x, y, z) == 0;
/*  87:    */   }
/*  88:    */   
/*  89:    */   protected boolean canThisPlantGrowOnThisBlockID(Block par1)
/*  90:    */   {
/*  91: 85 */     return (par1 == Blocks.grass) || (par1 == Blocks.dirt) || (par1 == Blocks.end_stone) || (par1 == Blocks.farmland);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean isSuperEndStone(World world, int x, int y, int z)
/*  95:    */   {
/*  96: 89 */     Block id = world.getBlock(x, y, z);
/*  97:    */     
/*  98: 91 */     return ((id instanceof BlockDecoration)) && (((BlockDecoration)id).isSuperEnder[world.getBlockMetadata(x, y, z)] != 0);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public boolean isEndStone(World world, int x, int y, int z)
/* 102:    */   {
/* 103: 96 */     Block id = world.getBlock(x, y, z);
/* 104: 97 */     return (id == Blocks.end_stone) || (((id instanceof BlockDecoration)) && (((BlockDecoration)id).isEnder[world.getBlockMetadata(x, y, z)] != 0));
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
/* 108:    */   {
/* 109:102 */     func_149855_e(par1World, par2, par3, par4);
/* 110:    */     
/* 111:104 */     int l = par1World.getBlockMetadata(par2, par3, par4);
/* 112:106 */     if (l < 7) {
/* 113:107 */       if (isSuperEndStone(par1World, par2, par3 - 1, par4))
/* 114:    */       {
/* 115:108 */         if (par5Random.nextInt(40) == 0)
/* 116:    */         {
/* 117:109 */           l++;
/* 118:110 */           par1World.setBlockMetadataWithNotify(par2, par3, par4, l, 2);
/* 119:    */         }
/* 120:    */       }
/* 121:112 */       else if (isEndStone(par1World, par2, par3 - 1, par4))
/* 122:    */       {
/* 123:113 */         if ((l % 2 == 0 ? 1 : 0) == (par1World.getWorldTime() % 48000L < 24000L ? 1 : 0)) {
/* 124:114 */           if (par5Random.nextInt(10) == 0)
/* 125:    */           {
/* 126:115 */             l++;
/* 127:116 */             par1World.setBlockMetadataWithNotify(par2, par3, par4, l, 2);
/* 128:    */           }
/* 129:    */         }
/* 130:    */       }
/* 131:118 */       else if ((l % 2 == 0 ? 1 : 0) == (par1World.getWorldTime() % 192000L < 96000L ? 1 : 0)) {
/* 132:119 */         if (par5Random.nextInt(40) == 0)
/* 133:    */         {
/* 134:120 */           l++;
/* 135:121 */           par1World.setBlockMetadataWithNotify(par2, par3, par4, l, 2);
/* 136:    */         }
/* 137:    */       }
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   @SideOnly(Side.CLIENT)
/* 142:    */   public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
/* 143:    */   {
/* 144:130 */     if ((par5Random.nextInt(5) != 0) || (par1World.getBlockMetadata(par2, par3, par4) < 7)) {
/* 145:131 */       return;
/* 146:    */     }
/* 147:134 */     double d0 = par2 + par5Random.nextFloat();
/* 148:135 */     double d1 = par3 + par5Random.nextFloat();
/* 149:136 */     d0 = par4 + par5Random.nextFloat();
/* 150:137 */     double d2 = 0.0D;
/* 151:138 */     double d3 = 0.0D;
/* 152:139 */     double d4 = 0.0D;
/* 153:140 */     int i1 = par5Random.nextInt(2) * 2 - 1;
/* 154:141 */     int j1 = par5Random.nextInt(2) * 2 - 1;
/* 155:142 */     d2 = (par5Random.nextFloat() - 0.5D) * 0.125D;
/* 156:143 */     d3 = (par5Random.nextFloat() - 0.5D) * 0.125D;
/* 157:144 */     d4 = (par5Random.nextFloat() - 0.5D) * 0.125D;
/* 158:145 */     double d5 = par4 + 0.5D + 0.25D * j1;
/* 159:146 */     d4 = par5Random.nextFloat() * 1.0F * j1;
/* 160:147 */     double d6 = par2 + 0.5D + 0.25D * i1;
/* 161:148 */     d2 = par5Random.nextFloat() * 1.0F * i1;
/* 162:149 */     par1World.spawnParticle("portal", d6, d1, d5, d2, d3, d4);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
/* 166:    */   {
/* 167:154 */     if (par1World.getBlockMetadata(par2, par3, par4) >= 3)
/* 168:    */     {
/* 169:155 */       if ((par5Entity instanceof EntityItem))
/* 170:    */       {
/* 171:156 */         ItemStack item = ((EntityItem)par5Entity).getEntityItem();
/* 172:158 */         if ((item != null) && ((item.getItem() == getSeedItem()) || (item.getItem() == getCropItem()))) {
/* 173:159 */           return;
/* 174:    */         }
/* 175:162 */         if (par1World.isClient) {
/* 176:163 */           par1World.spawnParticle("crit", par5Entity.posX, par5Entity.posY, par5Entity.posZ, 0.0D, 0.0D, 0.0D);
/* 177:    */         }
/* 178:    */       }
/* 179:167 */       if ((par5Entity instanceof EntityEnderman)) {
/* 180:168 */         return;
/* 181:    */       }
/* 182:171 */       par5Entity.attackEntityFrom(DamageSource.cactus, 0.1F);
/* 183:    */     }
/* 184:    */   }
/* 185:    */   
/* 186:    */   public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
/* 187:    */   {
/* 188:178 */     ArrayList<ItemStack> ret = new ArrayList();
/* 189:    */     
/* 190:180 */     ret.add(new ItemStack(getSeedItem(), 1, 0));
/* 191:182 */     if (metadata >= 7)
/* 192:    */     {
/* 193:183 */       ret.add(new ItemStack(getCropItem(), 1, 0));
/* 194:185 */       if (isEndStone(world, x, y - 1, z)) {
/* 195:186 */         while (world.rand.nextInt(50) == 0) {
/* 196:187 */           ret.add(new ItemStack(getSeedItem(), 1, 0));
/* 197:    */         }
/* 198:    */       }
/* 199:189 */       if (isSuperEndStone(world, x, y - 1, z)) {
/* 200:190 */         while (world.rand.nextInt(20) == 0) {
/* 201:191 */           ret.add(new ItemStack(getSeedItem(), 1, 0));
/* 202:    */         }
/* 203:    */       }
/* 204:    */     }
/* 205:197 */     return ret;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void func_149863_m(World par1World, int par2, int par3, int par4)
/* 209:    */   {
/* 210:206 */     int l = par1World.getBlockMetadata(par2, par3, par4);
/* 211:208 */     if (l == 0)
/* 212:    */     {
/* 213:209 */       par1World.func_147480_a(par2, par3, par4, true);
/* 214:    */     }
/* 215:    */     else
/* 216:    */     {
/* 217:211 */       l -= MathHelper.getRandomIntegerInRange(par1World.rand, 1, 5);
/* 218:213 */       if (l < 0) {
/* 219:214 */         l = 0;
/* 220:    */       }
/* 221:217 */       par1World.setBlockMetadataWithNotify(par2, par3, par4, l, 2);
/* 222:    */     }
/* 223:    */   }
/* 224:    */   
/* 225:    */   protected Item getSeedItem()
/* 226:    */   {
/* 227:227 */     return Item.getItemFromBlock(this);
/* 228:    */   }
/* 229:    */   
/* 230:    */   protected Item getCropItem()
/* 231:    */   {
/* 232:235 */     return Items.ender_pearl;
/* 233:    */   }
/* 234:    */   
/* 235:    */   @SideOnly(Side.CLIENT)
/* 236:    */   public String getItemIconName()
/* 237:    */   {
/* 238:244 */     return "extrautils:ender_lilly_seed";
/* 239:    */   }
/* 240:    */   
/* 241:    */   public boolean isFluid(World world, int x, int y, int z)
/* 242:    */   {
/* 243:248 */     return isWater(world, x, y, z);
/* 244:    */   }
/* 245:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BlockEnderLily
 * JD-Core Version:    0.7.0.1
 */