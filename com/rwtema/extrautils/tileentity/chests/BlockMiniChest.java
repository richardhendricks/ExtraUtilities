/*   1:    */ package com.rwtema.extrautils.tileentity.chests;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.ExtraUtilsMod;
/*   5:    */ import com.rwtema.extrautils.block.BlockMultiBlock;
/*   6:    */ import com.rwtema.extrautils.block.Box;
/*   7:    */ import com.rwtema.extrautils.block.BoxModel;
/*   8:    */ import com.rwtema.extrautils.helper.XURandom;
/*   9:    */ import cpw.mods.fml.relauncher.Side;
/*  10:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  11:    */ import java.util.Random;
/*  12:    */ import net.minecraft.block.Block;
/*  13:    */ import net.minecraft.block.material.Material;
/*  14:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  15:    */ import net.minecraft.entity.EntityLivingBase;
/*  16:    */ import net.minecraft.entity.item.EntityItem;
/*  17:    */ import net.minecraft.entity.player.EntityPlayer;
/*  18:    */ import net.minecraft.inventory.Container;
/*  19:    */ import net.minecraft.item.ItemStack;
/*  20:    */ import net.minecraft.nbt.NBTTagCompound;
/*  21:    */ import net.minecraft.tileentity.TileEntity;
/*  22:    */ import net.minecraft.util.IIcon;
/*  23:    */ import net.minecraft.util.MathHelper;
/*  24:    */ import net.minecraft.world.IBlockAccess;
/*  25:    */ import net.minecraft.world.World;
/*  26:    */ 
/*  27:    */ public class BlockMiniChest
/*  28:    */   extends BlockMultiBlock
/*  29:    */ {
/*  30: 28 */   private Random random = XURandom.getInstance();
/*  31:    */   IIcon icon_front;
/*  32:    */   IIcon icon_side;
/*  33:    */   IIcon icon_top;
/*  34:    */   
/*  35:    */   public BlockMiniChest()
/*  36:    */   {
/*  37: 31 */     super(Material.wood);
/*  38: 32 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  39: 33 */     setHardness(0.5F).setStepSound(soundTypeWood).setBlockName("extrautils:chestMini");
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean isOpaqueCube()
/*  43:    */   {
/*  44: 39 */     return false;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean renderAsNormalBlock()
/*  48:    */   {
/*  49: 46 */     return false;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase p_149689_5_, ItemStack itemstack)
/*  53:    */   {
/*  54: 51 */     byte meta = 0;
/*  55: 52 */     int l = MathHelper.floor_double(p_149689_5_.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
/*  56: 54 */     if (l == 0) {
/*  57: 55 */       meta = 0;
/*  58:    */     }
/*  59: 58 */     if (l == 1) {
/*  60: 59 */       meta = 1;
/*  61:    */     }
/*  62: 62 */     if (l == 2) {
/*  63: 63 */       meta = 2;
/*  64:    */     }
/*  65: 66 */     if (l == 3) {
/*  66: 67 */       meta = 3;
/*  67:    */     }
/*  68: 70 */     world.setBlockMetadataWithNotify(x, y, z, meta, 3);
/*  69: 72 */     if (itemstack.hasDisplayName()) {
/*  70: 73 */       ((TileMiniChest)world.getTileEntity(x, y, z)).func_145976_a(itemstack.getDisplayName());
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void prepareForRender(String label) {}
/*  75:    */   
/*  76:    */   public BoxModel getWorldModel(IBlockAccess world, int x, int y, int z)
/*  77:    */   {
/*  78: 84 */     BoxModel boxes = new BoxModel();
/*  79: 85 */     boxes.addBoxI(5, 0, 5, 11, 6, 11).fillIcons(this, 0);
/*  80: 86 */     boxes.rotateY(world.getBlockMetadata(x, y, z) & 0x3);
/*  81: 87 */     return boxes;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public BoxModel getInventoryModel(int metadata)
/*  85:    */   {
/*  86: 92 */     BoxModel boxes = new BoxModel();
/*  87: 93 */     boxes.addBoxI(5, 0, 5, 11, 6, 11).fillIcons(this, 0).rotateY(1);
/*  88: 94 */     return boxes;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void breakBlock(World world, int x, int y, int z, Block block, int meta)
/*  92:    */   {
/*  93: 98 */     TileMiniChest tileentitychest = (TileMiniChest)world.getTileEntity(x, y, z);
/*  94:100 */     if (tileentitychest != null) {
/*  95:101 */       for (int i1 = 0; i1 < tileentitychest.getSizeInventory(); i1++)
/*  96:    */       {
/*  97:102 */         ItemStack itemstack = tileentitychest.getStackInSlot(i1);
/*  98:104 */         if (itemstack != null)
/*  99:    */         {
/* 100:105 */           float f = this.random.nextFloat() * 0.8F + 0.1F;
/* 101:106 */           float f1 = this.random.nextFloat() * 0.8F + 0.1F;
/* 102:    */           EntityItem entityitem;
/* 103:109 */           for (float f2 = this.random.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; world.spawnEntityInWorld(entityitem))
/* 104:    */           {
/* 105:110 */             int j1 = this.random.nextInt(21) + 10;
/* 106:112 */             if (j1 > itemstack.stackSize) {
/* 107:113 */               j1 = itemstack.stackSize;
/* 108:    */             }
/* 109:116 */             itemstack.stackSize -= j1;
/* 110:117 */             entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
/* 111:118 */             float f3 = 0.05F;
/* 112:119 */             entityitem.motionX = ((float)this.random.nextGaussian() * f3);
/* 113:120 */             entityitem.motionY = ((float)this.random.nextGaussian() * f3 + 0.2F);
/* 114:121 */             entityitem.motionZ = ((float)this.random.nextGaussian() * f3);
/* 115:123 */             if (itemstack.hasTagCompound()) {
/* 116:124 */               entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
/* 117:    */             }
/* 118:    */           }
/* 119:    */         }
/* 120:    */       }
/* 121:    */     }
/* 122:131 */     super.breakBlock(world, x, y, z, block, meta);
/* 123:    */   }
/* 124:    */   
/* 125:    */   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/* 126:    */   {
/* 127:135 */     if (world.isClient) {
/* 128:136 */       return true;
/* 129:    */     }
/* 130:138 */     player.openGui(ExtraUtilsMod.instance, 0, world, x, y, z);
/* 131:139 */     return true;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public boolean hasTileEntity(int metadata)
/* 135:    */   {
/* 136:145 */     return true;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public TileEntity createTileEntity(World world, int metadata)
/* 140:    */   {
/* 141:150 */     return new TileMiniChest();
/* 142:    */   }
/* 143:    */   
/* 144:    */   public boolean hasComparatorInputOverride()
/* 145:    */   {
/* 146:155 */     return true;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public int getComparatorInputOverride(World world, int x, int y, int z, int p_149736_5_)
/* 150:    */   {
/* 151:161 */     return Container.calcRedstoneFromInventory((TileMiniChest)world.getTileEntity(x, y, z));
/* 152:    */   }
/* 153:    */   
/* 154:    */   @SideOnly(Side.CLIENT)
/* 155:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 156:    */   {
/* 157:167 */     this.blockIcon = p_149651_1_.registerIcon("planks_oak");
/* 158:168 */     this.icon_front = p_149651_1_.registerIcon("extrautils:minichest_front");
/* 159:169 */     this.icon_side = p_149651_1_.registerIcon("extrautils:minichest_side");
/* 160:170 */     this.icon_top = p_149651_1_.registerIcon("extrautils:minichest_top");
/* 161:    */   }
/* 162:    */   
/* 163:    */   public IIcon getIcon(int side, int p_149691_2_)
/* 164:    */   {
/* 165:177 */     if (side <= 1) {
/* 166:177 */       return this.icon_top;
/* 167:    */     }
/* 168:178 */     if (side == 2) {
/* 169:178 */       return this.icon_front;
/* 170:    */     }
/* 171:179 */     return this.icon_side;
/* 172:    */   }
/* 173:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.chests.BlockMiniChest
 * JD-Core Version:    0.7.0.1
 */