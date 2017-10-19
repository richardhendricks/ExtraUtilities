/*   1:    */ package com.rwtema.extrautils.tileentity.chests;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.helper.XURandom;
/*   5:    */ import cpw.mods.fml.relauncher.Side;
/*   6:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   7:    */ import java.util.Random;
/*   8:    */ import net.minecraft.block.Block;
/*   9:    */ import net.minecraft.block.material.Material;
/*  10:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  11:    */ import net.minecraft.entity.EntityLivingBase;
/*  12:    */ import net.minecraft.entity.item.EntityItem;
/*  13:    */ import net.minecraft.entity.player.EntityPlayer;
/*  14:    */ import net.minecraft.inventory.Container;
/*  15:    */ import net.minecraft.item.ItemStack;
/*  16:    */ import net.minecraft.nbt.NBTTagCompound;
/*  17:    */ import net.minecraft.tileentity.TileEntity;
/*  18:    */ import net.minecraft.util.IIcon;
/*  19:    */ import net.minecraft.util.MathHelper;
/*  20:    */ import net.minecraft.world.World;
/*  21:    */ 
/*  22:    */ public class BlockFullChest
/*  23:    */   extends Block
/*  24:    */ {
/*  25: 23 */   private Random random = XURandom.getInstance();
/*  26:    */   IIcon icon_front;
/*  27:    */   IIcon icon_side;
/*  28:    */   IIcon icon_top;
/*  29:    */   
/*  30:    */   public BlockFullChest()
/*  31:    */   {
/*  32: 26 */     super(Material.wood);
/*  33: 27 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  34: 28 */     setHardness(2.5F).setStepSound(soundTypeWood);
/*  35: 29 */     setBlockName("extrautils:chestFull");
/*  36:    */   }
/*  37:    */   
/*  38:    */   public boolean isOpaqueCube()
/*  39:    */   {
/*  40: 33 */     return true;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public boolean renderAsNormalBlock()
/*  44:    */   {
/*  45: 40 */     return true;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase p_149689_5_, ItemStack itemstack)
/*  49:    */   {
/*  50: 45 */     byte meta = 0;
/*  51: 46 */     int l = MathHelper.floor_double(p_149689_5_.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
/*  52: 49 */     if (l == 0) {
/*  53: 50 */       meta = 2;
/*  54:    */     }
/*  55: 53 */     if (l == 1) {
/*  56: 54 */       meta = 3;
/*  57:    */     }
/*  58: 57 */     if (l == 2) {
/*  59: 58 */       meta = 0;
/*  60:    */     }
/*  61: 61 */     if (l == 3) {
/*  62: 62 */       meta = 1;
/*  63:    */     }
/*  64: 65 */     world.setBlockMetadataWithNotify(x, y, z, meta, 3);
/*  65: 67 */     if (itemstack.hasDisplayName()) {
/*  66: 68 */       ((TileFullChest)world.getTileEntity(x, y, z)).func_145976_a(itemstack.getDisplayName());
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void breakBlock(World world, int x, int y, int z, Block block, int meta)
/*  71:    */   {
/*  72: 74 */     TileFullChest tileentitychest = (TileFullChest)world.getTileEntity(x, y, z);
/*  73: 76 */     if (tileentitychest != null) {
/*  74: 77 */       for (int i1 = 0; i1 < tileentitychest.getSizeInventory(); i1++)
/*  75:    */       {
/*  76: 78 */         ItemStack itemstack = tileentitychest.getStackInSlot(i1);
/*  77: 80 */         if (itemstack != null)
/*  78:    */         {
/*  79: 81 */           float f = this.random.nextFloat() * 0.8F + 0.1F;
/*  80: 82 */           float f1 = this.random.nextFloat() * 0.8F + 0.1F;
/*  81:    */           EntityItem entityitem;
/*  82: 85 */           for (float f2 = this.random.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; world.spawnEntityInWorld(entityitem))
/*  83:    */           {
/*  84: 86 */             int j1 = this.random.nextInt(21) + 10;
/*  85: 88 */             if (j1 > itemstack.stackSize) {
/*  86: 89 */               j1 = itemstack.stackSize;
/*  87:    */             }
/*  88: 92 */             itemstack.stackSize -= j1;
/*  89: 93 */             entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
/*  90: 94 */             float f3 = 0.05F;
/*  91: 95 */             entityitem.motionX = ((float)this.random.nextGaussian() * f3);
/*  92: 96 */             entityitem.motionY = ((float)this.random.nextGaussian() * f3 + 0.2F);
/*  93: 97 */             entityitem.motionZ = ((float)this.random.nextGaussian() * f3);
/*  94: 99 */             if (itemstack.hasTagCompound()) {
/*  95:100 */               entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
/*  96:    */             }
/*  97:    */           }
/*  98:    */         }
/*  99:    */       }
/* 100:    */     }
/* 101:107 */     super.breakBlock(world, x, y, z, block, meta);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/* 105:    */   {
/* 106:111 */     if (world.isClient) {
/* 107:112 */       return true;
/* 108:    */     }
/* 109:114 */     TileFullChest tileentitychest = (TileFullChest)world.getTileEntity(x, y, z);
/* 110:115 */     player.displayGUIChest(tileentitychest);
/* 111:116 */     return true;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public boolean hasTileEntity(int metadata)
/* 115:    */   {
/* 116:122 */     return true;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public TileEntity createTileEntity(World world, int metadata)
/* 120:    */   {
/* 121:127 */     return new TileFullChest();
/* 122:    */   }
/* 123:    */   
/* 124:    */   public boolean hasComparatorInputOverride()
/* 125:    */   {
/* 126:132 */     return true;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public int getComparatorInputOverride(World world, int x, int y, int z, int p_149736_5_)
/* 130:    */   {
/* 131:138 */     return Container.calcRedstoneFromInventory((TileFullChest)world.getTileEntity(x, y, z));
/* 132:    */   }
/* 133:    */   
/* 134:    */   @SideOnly(Side.CLIENT)
/* 135:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 136:    */   {
/* 137:144 */     this.blockIcon = p_149651_1_.registerIcon("planks_oak");
/* 138:145 */     this.icon_front = p_149651_1_.registerIcon("extrautils:fullblockchest_front");
/* 139:146 */     this.icon_side = p_149651_1_.registerIcon("extrautils:fullblockchest_side");
/* 140:147 */     this.icon_top = p_149651_1_.registerIcon("extrautils:fullblockchest_top");
/* 141:    */   }
/* 142:    */   
/* 143:    */   public IIcon getIcon(int side, int meta)
/* 144:    */   {
/* 145:154 */     if (side <= 1) {
/* 146:154 */       return this.icon_top;
/* 147:    */     }
/* 148:156 */     if ((meta == 2) && (side == 2)) {
/* 149:156 */       return this.icon_front;
/* 150:    */     }
/* 151:157 */     if ((meta == 3) && (side == 5)) {
/* 152:157 */       return this.icon_front;
/* 153:    */     }
/* 154:158 */     if ((meta == 0) && (side == 3)) {
/* 155:158 */       return this.icon_front;
/* 156:    */     }
/* 157:159 */     if ((meta == 1) && (side == 4)) {
/* 158:159 */       return this.icon_front;
/* 159:    */     }
/* 160:160 */     return this.icon_side;
/* 161:    */   }
/* 162:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.chests.BlockFullChest
 * JD-Core Version:    0.7.0.1
 */