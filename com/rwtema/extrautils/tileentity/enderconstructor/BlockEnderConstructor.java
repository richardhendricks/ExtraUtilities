/*   1:    */ package com.rwtema.extrautils.tileentity.enderconstructor;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.ExtraUtilsMod;
/*   5:    */ import com.rwtema.extrautils.block.BlockMultiBlock;
/*   6:    */ import com.rwtema.extrautils.block.Box;
/*   7:    */ import com.rwtema.extrautils.block.BoxModel;
/*   8:    */ import com.rwtema.extrautils.helper.XURandom;
/*   9:    */ import cpw.mods.fml.relauncher.Side;
/*  10:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Random;
/*  13:    */ import net.minecraft.block.Block;
/*  14:    */ import net.minecraft.block.material.Material;
/*  15:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  16:    */ import net.minecraft.creativetab.CreativeTabs;
/*  17:    */ import net.minecraft.entity.Entity;
/*  18:    */ import net.minecraft.entity.item.EntityItem;
/*  19:    */ import net.minecraft.entity.player.EntityPlayer;
/*  20:    */ import net.minecraft.inventory.IInventory;
/*  21:    */ import net.minecraft.item.Item;
/*  22:    */ import net.minecraft.item.ItemStack;
/*  23:    */ import net.minecraft.nbt.NBTTagCompound;
/*  24:    */ import net.minecraft.tileentity.TileEntity;
/*  25:    */ import net.minecraft.util.AxisAlignedBB;
/*  26:    */ import net.minecraft.util.IIcon;
/*  27:    */ import net.minecraft.world.IBlockAccess;
/*  28:    */ import net.minecraft.world.World;
/*  29:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  30:    */ 
/*  31:    */ public class BlockEnderConstructor
/*  32:    */   extends BlockMultiBlock
/*  33:    */ {
/*  34: 34 */   public IIcon[] icons = new IIcon[10];
/*  35:    */   
/*  36:    */   public BlockEnderConstructor()
/*  37:    */   {
/*  38: 37 */     super(Material.rock);
/*  39: 38 */     setBlockName("extrautils:endConstructor");
/*  40: 39 */     setBlockTextureName("extrautils:enderConstructor_pillar_bottom");
/*  41: 40 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  42: 41 */     setHardness(1.0F);
/*  43: 42 */     setResistance(10.0F).setStepSound(soundTypeStone);
/*  44:    */   }
/*  45:    */   
/*  46: 45 */   Random rand = XURandom.getInstance();
/*  47:    */   
/*  48:    */   public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
/*  49:    */   {
/*  50: 49 */     TileEntity tile = p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
/*  51: 51 */     if ((tile != null) && ((tile instanceof TileEnderConstructor)))
/*  52:    */     {
/*  53: 52 */       IInventory inv = ((TileEnderConstructor)tile).inv;
/*  54: 53 */       for (int i1 = 0; i1 < 9; i1++)
/*  55:    */       {
/*  56: 54 */         ItemStack itemstack = inv.getStackInSlot(i1);
/*  57: 55 */         dropItem(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, itemstack);
/*  58:    */       }
/*  59: 58 */       if (((TileEnderConstructor)tile).outputslot != null) {
/*  60: 59 */         dropItem(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, ((TileEnderConstructor)tile).outputslot);
/*  61:    */       }
/*  62: 62 */       p_149749_1_.func_147453_f(p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_);
/*  63:    */     }
/*  64: 65 */     super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void dropItem(World p_149749_1_, float p_149749_2_, float p_149749_3_, float p_149749_4_, ItemStack itemstack)
/*  68:    */   {
/*  69: 69 */     if (itemstack != null)
/*  70:    */     {
/*  71: 70 */       float f = this.rand.nextFloat() * 0.8F + 0.1F;
/*  72: 71 */       float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
/*  73:    */       EntityItem entityitem;
/*  74: 74 */       for (float f2 = this.rand.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; p_149749_1_.spawnEntityInWorld(entityitem))
/*  75:    */       {
/*  76: 75 */         int j1 = this.rand.nextInt(21) + 10;
/*  77: 77 */         if (j1 > itemstack.stackSize) {
/*  78: 78 */           j1 = itemstack.stackSize;
/*  79:    */         }
/*  80: 81 */         itemstack.stackSize -= j1;
/*  81: 82 */         entityitem = new EntityItem(p_149749_1_, p_149749_2_ + f, p_149749_3_ + f1, p_149749_4_ + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
/*  82: 83 */         float f3 = 0.05F;
/*  83: 84 */         entityitem.motionX = ((float)this.rand.nextGaussian() * f3);
/*  84: 85 */         entityitem.motionY = ((float)this.rand.nextGaussian() * f3 + 0.2F);
/*  85: 86 */         entityitem.motionZ = ((float)this.rand.nextGaussian() * f3);
/*  86: 88 */         if (itemstack.hasTagCompound()) {
/*  87: 89 */           entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
/*  88:    */         }
/*  89:    */       }
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int getLightValue(IBlockAccess world, int x, int y, int z)
/*  94:    */   {
/*  95: 97 */     Block block = world.getBlock(x, y, z);
/*  96: 99 */     if ((block != null) && (block != this)) {
/*  97:100 */       return block.getLightValue(world, x, y, z);
/*  98:    */     }
/*  99:103 */     return world.getBlockMetadata(x, y, z) % 2 == 1 ? 10 : 0;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void prepareForRender(String label) {}
/* 103:    */   
/* 104:    */   public int damageDropped(int par1)
/* 105:    */   {
/* 106:113 */     return par1 - par1 % 2 & 0xF;
/* 107:    */   }
/* 108:    */   
/* 109:    */   @SideOnly(Side.CLIENT)
/* 110:    */   public void registerBlockIcons(IIconRegister par1IIconRegister)
/* 111:    */   {
/* 112:123 */     IIcon tmp25_22 = (this.blockIcon = par1IIconRegister.registerIcon(getTextureName()));this.icons[1] = tmp25_22;this.icons[0] = tmp25_22;
/* 113:124 */     this.icons[2] = par1IIconRegister.registerIcon("extrautils:enderConstructor_top");
/* 114:125 */     this.icons[3] = par1IIconRegister.registerIcon("extrautils:enderConstructor_pillar_top");
/* 115:126 */     this.icons[4] = par1IIconRegister.registerIcon("extrautils:enderConstructor_side");
/* 116:127 */     this.icons[5] = par1IIconRegister.registerIcon("extrautils:enderConstructor_pillar");
/* 117:128 */     this.icons[6] = par1IIconRegister.registerIcon("extrautils:enderConstructor_pillar_enabled");
/* 118:129 */     this.icons[7] = par1IIconRegister.registerIcon("extrautils:enderConstructor_pillar_top_enabled");
/* 119:130 */     this.icons[8] = par1IIconRegister.registerIcon("extrautils:enderConstructor_top_enabled");
/* 120:131 */     this.icons[9] = par1IIconRegister.registerIcon("extrautils:enderConstructor_side_enabled");
/* 121:    */   }
/* 122:    */   
/* 123:    */   @SideOnly(Side.CLIENT)
/* 124:    */   public IIcon getIcon(int par1, int par2)
/* 125:    */   {
/* 126:140 */     if (par2 == 2)
/* 127:    */     {
/* 128:141 */       if (par1 == 0) {
/* 129:142 */         return this.icons[1];
/* 130:    */       }
/* 131:143 */       if (par1 == 1) {
/* 132:144 */         return this.icons[3];
/* 133:    */       }
/* 134:146 */       return this.icons[5];
/* 135:    */     }
/* 136:148 */     if (par2 == 3)
/* 137:    */     {
/* 138:149 */       if (par1 == 0) {
/* 139:150 */         return this.icons[1];
/* 140:    */       }
/* 141:151 */       if (par1 == 1) {
/* 142:152 */         return this.icons[7];
/* 143:    */       }
/* 144:154 */       return this.icons[6];
/* 145:    */     }
/* 146:158 */     if (par2 == 1)
/* 147:    */     {
/* 148:159 */       if (par1 == 0) {
/* 149:160 */         return this.icons[0];
/* 150:    */       }
/* 151:161 */       if (par1 == 1) {
/* 152:162 */         return this.icons[8];
/* 153:    */       }
/* 154:164 */       return this.icons[9];
/* 155:    */     }
/* 156:168 */     if (par1 == 0) {
/* 157:169 */       return this.icons[0];
/* 158:    */     }
/* 159:170 */     if (par1 == 1) {
/* 160:171 */       return this.icons[2];
/* 161:    */     }
/* 162:173 */     return this.icons[4];
/* 163:    */   }
/* 164:    */   
/* 165:    */   @SideOnly(Side.CLIENT)
/* 166:    */   public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/* 167:    */   {
/* 168:183 */     par3List.add(new ItemStack(par1, 1, 0));
/* 169:184 */     par3List.add(new ItemStack(par1, 1, 2));
/* 170:    */   }
/* 171:    */   
/* 172:    */   public BoxModel getWorldModel(IBlockAccess world, int x, int y, int z)
/* 173:    */   {
/* 174:189 */     int metadata = world.getBlockMetadata(x, y, z);
/* 175:190 */     BoxModel model = getInventoryModel(metadata);
/* 176:191 */     if ((metadata == 2) || (metadata == 3))
/* 177:    */     {
/* 178:192 */       model.fillIcons(this, metadata);
/* 179:193 */       for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS) {
/* 180:194 */         if (world.isSideSolid(x + d.offsetX, y + d.offsetY, z + d.offsetZ, d.getOpposite(), false))
/* 181:    */         {
/* 182:195 */           model.rotateToSideTex(d);
/* 183:196 */           return model;
/* 184:    */         }
/* 185:    */       }
/* 186:    */     }
/* 187:201 */     return model;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public BoxModel getInventoryModel(int metadata)
/* 191:    */   {
/* 192:206 */     if ((metadata == 2) || (metadata == 3))
/* 193:    */     {
/* 194:207 */       BoxModel model = new BoxModel();
/* 195:208 */       model.add(new Box(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.4375F, 0.9375F));
/* 196:209 */       model.add(new Box(0.25F, 0.4375F, 0.25F, 0.75F, 0.9375F, 0.75F));
/* 197:210 */       return model;
/* 198:    */     }
/* 199:212 */     BoxModel box = BoxModel.newStandardBlock();
/* 200:213 */     return box;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void miscInit() {}
/* 204:    */   
/* 205:    */   public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
/* 206:    */   {
/* 207:223 */     List models = getWorldModel(par1World, par2, par3, par4);
/* 208:225 */     if ((models == null) || (models.size() == 0)) {
/* 209:226 */       return;
/* 210:    */     }
/* 211:229 */     Box b = BoxModel.boundingBox(models);
/* 212:230 */     AxisAlignedBB axisalignedbb1 = AxisAlignedBB.getBoundingBox(par2 + b.offsetx + b.minX, par3 + b.offsety + b.minY, par4 + b.offsetz + b.minZ, par2 + b.offsetx + b.maxX, par3 + b.offsety + b.maxY, par4 + b.offsetz + b.maxZ);
/* 213:233 */     if ((axisalignedbb1 != null) && (par5AxisAlignedBB.intersectsWith(axisalignedbb1))) {
/* 214:234 */       par6List.add(axisalignedbb1);
/* 215:    */     }
/* 216:    */   }
/* 217:    */   
/* 218:    */   public boolean onBlockActivated(World worldObj, int x, int y, int z, EntityPlayer player, int side, float dx, float dy, float dz)
/* 219:    */   {
/* 220:241 */     int metadata = worldObj.getBlockMetadata(x, y, z);
/* 221:243 */     if ((metadata <= 1) || (metadata == 4))
/* 222:    */     {
/* 223:244 */       if (worldObj.isClient) {
/* 224:245 */         return true;
/* 225:    */       }
/* 226:248 */       player.openGui(ExtraUtilsMod.instance, 0, worldObj, x, y, z);
/* 227:249 */       return true;
/* 228:    */     }
/* 229:251 */     return false;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public boolean hasTileEntity(int metadata)
/* 233:    */   {
/* 234:258 */     return true;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public TileEntity createTileEntity(World world, int metadata)
/* 238:    */   {
/* 239:263 */     if ((metadata == 2) || (metadata == 3)) {
/* 240:264 */       return new TileEnderPillar();
/* 241:    */     }
/* 242:267 */     return new TileEnderConstructor();
/* 243:    */   }
/* 244:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.enderconstructor.BlockEnderConstructor
 * JD-Core Version:    0.7.0.1
 */