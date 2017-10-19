/*   1:    */ package com.rwtema.extrautils.block;
/*   2:    */ 
/*   3:    */ import cofh.api.block.IBlockAppearance;
/*   4:    */ import com.rwtema.extrautils.ExtraUtils;
/*   5:    */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*   6:    */ import cpw.mods.fml.relauncher.Side;
/*   7:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   8:    */ import java.util.List;
/*   9:    */ import net.minecraft.block.Block;
/*  10:    */ import net.minecraft.block.material.Material;
/*  11:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  12:    */ import net.minecraft.creativetab.CreativeTabs;
/*  13:    */ import net.minecraft.entity.Entity;
/*  14:    */ import net.minecraft.item.Item;
/*  15:    */ import net.minecraft.item.ItemStack;
/*  16:    */ import net.minecraft.util.IIcon;
/*  17:    */ import net.minecraft.world.IBlockAccess;
/*  18:    */ import net.minecraft.world.World;
/*  19:    */ import net.minecraftforge.common.IPlantable;
/*  20:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  21:    */ 
/*  22:    */ public class BlockDecoration
/*  23:    */   extends Block
/*  24:    */   implements IBlockAppearance
/*  25:    */ {
/*  26: 24 */   public static boolean gettingConnectedTextures = false;
/*  27: 25 */   public String[][] texture = new String[16][6];
/*  28: 26 */   public boolean[][] ctexture = new boolean[16][6];
/*  29: 27 */   public int[] light = new int[16];
/*  30: 28 */   public float[] hardness = new float[16];
/*  31: 29 */   public float[] resistance = new float[16];
/*  32: 30 */   public boolean[] opaque = new boolean[16];
/*  33: 31 */   public int[] opacity = new int[16];
/*  34: 32 */   public boolean[] flipTopBottom = new boolean[16];
/*  35: 33 */   public float[] enchantBonus = new float[16];
/*  36:    */   public boolean solid;
/*  37: 35 */   public boolean[] isSuperEnder = new boolean[16];
/*  38: 36 */   public boolean[] isEnder = new boolean[16];
/*  39: 37 */   private IIcon[][] icons = new IIcon[16][6];
/*  40: 38 */   public String[] name = new String[16];
/*  41: 39 */   private int numBlocks = 0;
/*  42:    */   
/*  43:    */   public BlockDecoration(boolean solid)
/*  44:    */   {
/*  45: 42 */     super(solid ? Material.rock : Material.glass);
/*  46: 43 */     this.solid = solid;
/*  47: 44 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  48: 45 */     setHardness(0.45F).setResistance(10.0F).setStepSound(soundTypeStone);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plant)
/*  52:    */   {
/*  53: 50 */     return ((this.isSuperEnder[world.getBlockMetadata(x, y, z)] != 0) && ((plant instanceof BlockEnderLily))) || (super.canSustainPlant(world, x, y, z, direction, plant));
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getLightOpacity(IBlockAccess world, int x, int y, int z)
/*  57:    */   {
/*  58: 56 */     if (((world instanceof World)) && 
/*  59: 57 */       (!((World)world).blockExists(x, y, z))) {
/*  60: 58 */       return 0;
/*  61:    */     }
/*  62: 60 */     return this.opacity[world.getBlockMetadata(x, y, z)];
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getLightValue(IBlockAccess world, int x, int y, int z)
/*  66:    */   {
/*  67: 65 */     Block block = world.getBlock(x, y, z);
/*  68: 67 */     if ((block != null) && (block != this)) {
/*  69: 68 */       return block.getLightValue(world, x, y, z);
/*  70:    */     }
/*  71: 70 */     return this.light[world.getBlockMetadata(x, y, z)];
/*  72:    */   }
/*  73:    */   
/*  74:    */   public float getBlockHardness(World par1World, int par2, int par3, int par4)
/*  75:    */   {
/*  76: 76 */     if (par1World == null) {
/*  77: 77 */       return this.blockHardness;
/*  78:    */     }
/*  79: 78 */     return this.hardness[getMetadataSafe(par1World, par2, par3, par4)];
/*  80:    */   }
/*  81:    */   
/*  82:    */   public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ)
/*  83:    */   {
/*  84: 83 */     if (world == null) {
/*  85: 84 */       return getExplosionResistance(par1Entity);
/*  86:    */     }
/*  87: 86 */     return this.resistance[getMetadataSafe(world, x, y, z)] / 5.0F;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean canPlaceTorchOnTop(World world, int x, int y, int z)
/*  91:    */   {
/*  92: 91 */     return true;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public boolean isOpaqueCube()
/*  96:    */   {
/*  97: 96 */     return this.solid;
/*  98:    */   }
/*  99:    */   
/* 100:    */   @SideOnly(Side.CLIENT)
/* 101:    */   public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
/* 102:    */   {
/* 103:106 */     return (!par1IBlockAccess.getBlock(par2, par3, par4).isOpaqueCube()) && (par1IBlockAccess.getBlock(par2, par3, par4) != this) ? true : this.solid ? super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5) : false;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public boolean renderAsNormalBlock()
/* 107:    */   {
/* 108:112 */     return this.solid;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void addBlock(int id, String defaultname, String texture)
/* 112:    */   {
/* 113:116 */     addBlock(id, defaultname, texture, false, true);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void addBlock(int id, String defaultname, String texture, boolean connectedTexture, boolean opaque)
/* 117:    */   {
/* 118:120 */     if ((id >= 0) && (id < 16))
/* 119:    */     {
/* 120:121 */       assert (this.name[id] != null);
/* 121:122 */       this.name[id] = texture;
/* 122:124 */       for (int side = 0; side < 6; side++)
/* 123:    */       {
/* 124:125 */         this.texture[id][side] = texture;
/* 125:126 */         this.ctexture[id][side] = connectedTexture;
/* 126:    */       }
/* 127:129 */       this.hardness[id] = this.blockHardness;
/* 128:130 */       this.resistance[id] = (this.blockHardness * 5.0F);
/* 129:131 */       this.opaque[id] = opaque;
/* 130:132 */       this.opacity[id] = (this.solid ? 'ÿ' : 0);
/* 131:133 */       this.enchantBonus[id] = 0.0F;
/* 132:134 */       this.isEnder[id] = false;
/* 133:135 */       this.isSuperEnder[id] = false;
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   public float getEnchantPowerBonus(World world, int x, int y, int z)
/* 138:    */   {
/* 139:141 */     return this.enchantBonus[getMetadataSafe(world, x, y, z)];
/* 140:    */   }
/* 141:    */   
/* 142:    */   public int getMetadataSafe(IBlockAccess world, int x, int y, int z)
/* 143:    */   {
/* 144:145 */     return world.getBlockMetadata(x, y, z);
/* 145:    */   }
/* 146:    */   
/* 147:    */   @SideOnly(Side.CLIENT)
/* 148:    */   public IIcon getIcon(int par1, int par2)
/* 149:    */   {
/* 150:154 */     if ((par1 <= 1) && (this.flipTopBottom[(par2 & 0xF)] != 0) && ((this.icons[(par2 & 0xF)][par1] instanceof IconConnectedTexture))) {
/* 151:155 */       return new IconConnectedTextureFlipped((IconConnectedTexture)this.icons[(par2 & 0xF)][par1]);
/* 152:    */     }
/* 153:158 */     return this.icons[(par2 & 0xF)][par1];
/* 154:    */   }
/* 155:    */   
/* 156:    */   public int damageDropped(int par1)
/* 157:    */   {
/* 158:167 */     return par1 & 0xF;
/* 159:    */   }
/* 160:    */   
/* 161:    */   @SideOnly(Side.CLIENT)
/* 162:    */   public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/* 163:    */   {
/* 164:176 */     for (int i = 0; i < 16; i++) {
/* 165:177 */       if (this.name[i] != null) {
/* 166:178 */         par3List.add(new ItemStack(par1, 1, i));
/* 167:    */       }
/* 168:    */     }
/* 169:    */   }
/* 170:    */   
/* 171:    */   public int getRenderType()
/* 172:    */   {
/* 173:184 */     return ExtraUtilsProxy.connectedTextureID;
/* 174:    */   }
/* 175:    */   
/* 176:    */   @SideOnly(Side.CLIENT)
/* 177:    */   public void registerBlockIcons(IIconRegister par1IIconRegister)
/* 178:    */   {
/* 179:194 */     for (int i = 0; i < 16; i++) {
/* 180:195 */       for (int side = 0; side < 6; side++) {
/* 181:196 */         if ((this.texture[i][side] != null) && (!this.texture[i][side].equals(""))) {
/* 182:197 */           if (this.ctexture[i][side] != 0) {
/* 183:198 */             this.icons[i][side] = new IconConnectedTexture(par1IIconRegister, this.texture[i][side]);
/* 184:    */           } else {
/* 185:200 */             this.icons[i][side] = par1IIconRegister.registerIcon(this.texture[i][side]);
/* 186:    */           }
/* 187:    */         }
/* 188:    */       }
/* 189:    */     }
/* 190:    */   }
/* 191:    */   
/* 192:    */   public Block getVisualBlock(IBlockAccess world, int x, int y, int z, ForgeDirection side)
/* 193:    */   {
/* 194:208 */     return this;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public int getVisualMeta(IBlockAccess world, int x, int y, int z, ForgeDirection side)
/* 198:    */   {
/* 199:213 */     return world.getBlockMetadata(x, y, z);
/* 200:    */   }
/* 201:    */   
/* 202:    */   public boolean supportsVisualConnections()
/* 203:    */   {
/* 204:218 */     return true;
/* 205:    */   }
/* 206:    */   
/* 207:222 */   public int[] fireEncouragement = new int[16];
/* 208:223 */   public int[] fireFlammability = new int[16];
/* 209:224 */   public boolean[] fireSource = new boolean[16];
/* 210:    */   
/* 211:    */   public boolean isFireSource(World world, int x, int y, int z, ForgeDirection side)
/* 212:    */   {
/* 213:228 */     return this.fireSource[getMetadataSafe(world, x, y, z)];
/* 214:    */   }
/* 215:    */   
/* 216:    */   public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face)
/* 217:    */   {
/* 218:233 */     return this.fireEncouragement[getMetadataSafe(world, x, y, z)];
/* 219:    */   }
/* 220:    */   
/* 221:    */   public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face)
/* 222:    */   {
/* 223:238 */     return this.fireFlammability[getMetadataSafe(world, x, y, z)];
/* 224:    */   }
/* 225:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BlockDecoration
 * JD-Core Version:    0.7.0.1
 */