/*   1:    */ package com.rwtema.extrautils.block;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.ExtraUtilsMod;
/*   5:    */ import com.rwtema.extrautils.helper.XURandom;
/*   6:    */ import com.rwtema.extrautils.item.IBlockLocalization;
/*   7:    */ import com.rwtema.extrautils.tileentity.TileEntityTrashCan;
/*   8:    */ import com.rwtema.extrautils.tileentity.TileEntityTrashCanEnergy;
/*   9:    */ import com.rwtema.extrautils.tileentity.TileEntityTrashCanFluids;
/*  10:    */ import cpw.mods.fml.relauncher.Side;
/*  11:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Random;
/*  14:    */ import net.minecraft.block.Block;
/*  15:    */ import net.minecraft.block.ITileEntityProvider;
/*  16:    */ import net.minecraft.block.material.Material;
/*  17:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  18:    */ import net.minecraft.creativetab.CreativeTabs;
/*  19:    */ import net.minecraft.entity.item.EntityItem;
/*  20:    */ import net.minecraft.entity.player.EntityPlayer;
/*  21:    */ import net.minecraft.item.Item;
/*  22:    */ import net.minecraft.item.ItemStack;
/*  23:    */ import net.minecraft.nbt.NBTTagCompound;
/*  24:    */ import net.minecraft.tileentity.TileEntity;
/*  25:    */ import net.minecraft.util.IIcon;
/*  26:    */ import net.minecraft.world.IBlockAccess;
/*  27:    */ import net.minecraft.world.World;
/*  28:    */ 
/*  29:    */ public class BlockTrashCan
/*  30:    */   extends BlockMultiBlock
/*  31:    */   implements ITileEntityProvider, IBlockLocalization
/*  32:    */ {
/*  33: 32 */   Random random = XURandom.getInstance();
/*  34:    */   private IIcon[][] icons;
/*  35:    */   
/*  36:    */   public BlockTrashCan()
/*  37:    */   {
/*  38: 36 */     super(Material.rock);
/*  39: 37 */     setBlockName("extrautils:trashcan");
/*  40: 38 */     setBlockTextureName("extrautils:trashcan");
/*  41: 39 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  42: 40 */     setHardness(3.5F).setStepSound(soundTypeStone);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int damageDropped(int p_149692_1_)
/*  46:    */   {
/*  47: 45 */     return p_149692_1_;
/*  48:    */   }
/*  49:    */   
/*  50:    */   @SideOnly(Side.CLIENT)
/*  51:    */   public IIcon getIcon(int side, int meta)
/*  52:    */   {
/*  53: 54 */     int i = Math.min(side, 2);
/*  54: 55 */     return this.icons[(meta % 3)][i];
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
/*  58:    */   {
/*  59: 60 */     for (int i = 0; i < 3; i++) {
/*  60: 61 */       p_149666_3_.add(new ItemStack(p_149666_1_, 1, i));
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
/*  65:    */   {
/*  66: 69 */     if (par1World.isClient) {
/*  67: 70 */       return true;
/*  68:    */     }
/*  69: 73 */     if ((par1World.getTileEntity(par2, par3, par4) instanceof TileEntityTrashCan))
/*  70:    */     {
/*  71: 74 */       par5EntityPlayer.openGui(ExtraUtilsMod.instance, 0, par1World, par2, par3, par4);
/*  72: 75 */       return true;
/*  73:    */     }
/*  74: 78 */     return false;
/*  75:    */   }
/*  76:    */   
/*  77:    */   @SideOnly(Side.CLIENT)
/*  78:    */   public void registerBlockIcons(IIconRegister par1IIconRegister)
/*  79:    */   {
/*  80: 84 */     this.icons = new IIcon[3][];
/*  81: 85 */     this.icons[0] = new IIcon[3];
/*  82: 86 */     this.icons[0][0] = par1IIconRegister.registerIcon("extrautils:trashcan_bottom");
/*  83: 87 */     this.icons[0][1] = par1IIconRegister.registerIcon("extrautils:trashcan_top");
/*  84: 88 */     this.icons[0][2] = par1IIconRegister.registerIcon("extrautils:trashcan");
/*  85:    */     
/*  86: 90 */     this.icons[1] = new IIcon[3];
/*  87: 91 */     this.icons[1][0] = par1IIconRegister.registerIcon("extrautils:trashcan1_bottom");
/*  88: 92 */     this.icons[1][1] = par1IIconRegister.registerIcon("extrautils:trashcan1_top");
/*  89: 93 */     this.icons[1][2] = par1IIconRegister.registerIcon("extrautils:trashcan1");
/*  90:    */     
/*  91: 95 */     this.icons[2] = new IIcon[3];
/*  92: 96 */     this.icons[2][0] = par1IIconRegister.registerIcon("extrautils:trashcan2_bottom");
/*  93: 97 */     this.icons[2][1] = par1IIconRegister.registerIcon("extrautils:trashcan2_top");
/*  94: 98 */     this.icons[2][2] = par1IIconRegister.registerIcon("extrautils:trashcan2");
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void prepareForRender(String label) {}
/*  98:    */   
/*  99:    */   public BoxModel getWorldModel(IBlockAccess world, int x, int y, int z)
/* 100:    */   {
/* 101:107 */     return getInventoryModel(0);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public BoxModel getInventoryModel(int metadata)
/* 105:    */   {
/* 106:112 */     BoxModel model = new BoxModel(0.125F, 0.0F, 0.125F, 0.875F, 0.625F, 0.875F);
/* 107:113 */     model.add(new Box(0.0625F, 0.625F, 0.0625F, 0.9375F, 0.875F, 0.9375F));
/* 108:114 */     model.add(new Box(0.3125F, 0.875F, 0.4375F, 0.6875F, 0.9375F, 0.5625F));
/* 109:115 */     return model;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6)
/* 113:    */   {
/* 114:121 */     TileEntity tileEntity = par1World.getTileEntity(par2, par3, par4);
/* 115:122 */     if ((tileEntity instanceof TileEntityTrashCan))
/* 116:    */     {
/* 117:123 */       TileEntityTrashCan tile = (TileEntityTrashCan)tileEntity;
/* 118:124 */       tile.processInv();
/* 119:125 */       ItemStack itemstack = tile.getStackInSlot(0);
/* 120:127 */       if (itemstack != null)
/* 121:    */       {
/* 122:128 */         float f = this.random.nextFloat() * 0.8F + 0.1F;
/* 123:129 */         float f1 = this.random.nextFloat() * 0.8F + 0.1F;
/* 124:    */         EntityItem entityitem;
/* 125:132 */         for (float f2 = this.random.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; par1World.spawnEntityInWorld(entityitem))
/* 126:    */         {
/* 127:133 */           int k1 = this.random.nextInt(21) + 10;
/* 128:135 */           if (k1 > itemstack.stackSize) {
/* 129:136 */             k1 = itemstack.stackSize;
/* 130:    */           }
/* 131:139 */           itemstack.stackSize -= k1;
/* 132:140 */           entityitem = new EntityItem(par1World, par2 + f, par3 + f1, par4 + f2, new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));
/* 133:141 */           float f3 = 0.05F;
/* 134:142 */           entityitem.motionX = ((float)this.random.nextGaussian() * f3);
/* 135:143 */           entityitem.motionY = ((float)this.random.nextGaussian() * f3 + 0.2F);
/* 136:144 */           entityitem.motionZ = ((float)this.random.nextGaussian() * f3);
/* 137:146 */           if (itemstack.hasTagCompound()) {
/* 138:147 */             entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
/* 139:    */           }
/* 140:    */         }
/* 141:    */       }
/* 142:    */     }
/* 143:153 */     super.breakBlock(par1World, par2, par3, par4, par5, par6);
/* 144:    */   }
/* 145:    */   
/* 146:    */   public TileEntity createNewTileEntity(World var1, int meta)
/* 147:    */   {
/* 148:158 */     if (meta == 1) {
/* 149:159 */       return new TileEntityTrashCanFluids();
/* 150:    */     }
/* 151:160 */     if (meta == 2) {
/* 152:161 */       return new TileEntityTrashCanEnergy();
/* 153:    */     }
/* 154:163 */     return new TileEntityTrashCan();
/* 155:    */   }
/* 156:    */   
/* 157:    */   public String getUnlocalizedName(ItemStack par1ItemStack)
/* 158:    */   {
/* 159:170 */     if (par1ItemStack.getItemDamage() == 0) {
/* 160:171 */       return getUnlocalizedName();
/* 161:    */     }
/* 162:173 */     return getUnlocalizedName() + "." + par1ItemStack.getItemDamage();
/* 163:    */   }
/* 164:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BlockTrashCan
 * JD-Core Version:    0.7.0.1
 */