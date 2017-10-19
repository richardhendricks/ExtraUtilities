/*   1:    */ package com.rwtema.extrautils.block;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.ExtraUtilsMod;
/*   5:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   6:    */ import com.rwtema.extrautils.tileentity.TileEntityFilingCabinet;
/*   7:    */ import cpw.mods.fml.relauncher.Side;
/*   8:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.List;
/*  11:    */ import net.minecraft.block.Block;
/*  12:    */ import net.minecraft.block.material.Material;
/*  13:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  14:    */ import net.minecraft.creativetab.CreativeTabs;
/*  15:    */ import net.minecraft.entity.EntityLivingBase;
/*  16:    */ import net.minecraft.entity.player.EntityPlayer;
/*  17:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  18:    */ import net.minecraft.item.Item;
/*  19:    */ import net.minecraft.item.ItemStack;
/*  20:    */ import net.minecraft.nbt.NBTTagCompound;
/*  21:    */ import net.minecraft.tileentity.TileEntity;
/*  22:    */ import net.minecraft.util.IIcon;
/*  23:    */ import net.minecraft.util.MathHelper;
/*  24:    */ import net.minecraft.world.World;
/*  25:    */ 
/*  26:    */ public class BlockFilingCabinet
/*  27:    */   extends Block
/*  28:    */   implements IBlockTooltip
/*  29:    */ {
/*  30: 32 */   private IIcon[] icon = new IIcon[6];
/*  31:    */   
/*  32:    */   public BlockFilingCabinet()
/*  33:    */   {
/*  34: 35 */     super(Material.rock);
/*  35: 36 */     setBlockName("extrautils:filing");
/*  36: 37 */     setHardness(1.5F);
/*  37: 38 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  38:    */   }
/*  39:    */   
/*  40:    */   @SideOnly(Side.CLIENT)
/*  41:    */   public void registerBlockIcons(IIconRegister par1IIconRegister)
/*  42:    */   {
/*  43: 48 */     this.icon[0] = par1IIconRegister.registerIcon("extrautils:filingcabinet");
/*  44: 49 */     this.icon[1] = par1IIconRegister.registerIcon("extrautils:filingcabinet_side");
/*  45: 50 */     this.icon[2] = par1IIconRegister.registerIcon("extrautils:filingcabinet_back");
/*  46: 51 */     this.icon[3] = par1IIconRegister.registerIcon("extrautils:filingcabinet_diamond");
/*  47: 52 */     this.icon[4] = par1IIconRegister.registerIcon("extrautils:filingcabinet_side_diamond");
/*  48: 53 */     this.icon[5] = par1IIconRegister.registerIcon("extrautils:filingcabinet_back_diamond");
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int damageDropped(int par1)
/*  52:    */   {
/*  53: 58 */     return par1 / 6 % 2;
/*  54:    */   }
/*  55:    */   
/*  56:    */   @SideOnly(Side.CLIENT)
/*  57:    */   public IIcon getIcon(int par1, int par2)
/*  58:    */   {
/*  59: 67 */     int side = par2 % 6;
/*  60: 68 */     int type = par2 / 6;
/*  61: 70 */     if (type > 1) {
/*  62: 71 */       return null;
/*  63:    */     }
/*  64: 73 */     if (par2 < 2)
/*  65:    */     {
/*  66: 74 */       type = par2;
/*  67: 76 */       if (par1 == 4) {
/*  68: 77 */         return this.icon[(type * 3)];
/*  69:    */       }
/*  70: 78 */       if (par1 == 5) {
/*  71: 79 */         return this.icon[(2 + type * 3)];
/*  72:    */       }
/*  73: 81 */       return this.icon[(1 + type * 3)];
/*  74:    */     }
/*  75: 84 */     if (par1 == side) {
/*  76: 85 */       return this.icon[(type * 3)];
/*  77:    */     }
/*  78: 86 */     if (par1 == net.minecraft.util.Facing.oppositeSide[side]) {
/*  79: 87 */       return this.icon[(2 + type * 3)];
/*  80:    */     }
/*  81: 89 */     return this.icon[(1 + type * 3)];
/*  82:    */   }
/*  83:    */   
/*  84:    */   public boolean hasTileEntity(int metadata)
/*  85:    */   {
/*  86: 96 */     return true;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public TileEntity createTileEntity(World world, int metadata)
/*  90:    */   {
/*  91:101 */     return new TileEntityFilingCabinet();
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
/*  95:    */   {
/*  96:106 */     if (par1World.isClient) {
/*  97:107 */       return true;
/*  98:    */     }
/*  99:110 */     TileEntity tile = par1World.getTileEntity(par2, par3, par4);
/* 100:111 */     par5EntityPlayer.openGui(ExtraUtilsMod.instance, 0, tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
/* 101:112 */     return true;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest)
/* 105:    */   {
/* 106:126 */     ArrayList<ItemStack> items = getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
/* 107:128 */     if (world.setBlockToAir(x, y, z))
/* 108:    */     {
/* 109:129 */       if (!world.isClient) {
/* 110:130 */         for (ItemStack item : items) {
/* 111:131 */           if ((player == null) || (!player.capabilities.isCreativeMode) || (item.hasTagCompound())) {
/* 112:132 */             dropBlockAsItem_do(world, x, y, z, item);
/* 113:    */           }
/* 114:    */         }
/* 115:    */       }
/* 116:136 */       return true;
/* 117:    */     }
/* 118:138 */     return false;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
/* 122:    */   {
/* 123:144 */     ArrayList<ItemStack> ret = new ArrayList();
/* 124:145 */     ItemStack item = new ItemStack(this, 1, damageDropped(metadata));
/* 125:147 */     if ((world.getTileEntity(x, y, z) instanceof TileEntityFilingCabinet))
/* 126:    */     {
/* 127:148 */       NBTTagCompound tags = new NBTTagCompound();
/* 128:149 */       ((TileEntityFilingCabinet)world.getTileEntity(x, y, z)).writeInvToTags(tags);
/* 129:151 */       if (!tags.hasNoTags()) {
/* 130:152 */         item.setTagCompound(tags);
/* 131:    */       }
/* 132:    */     }
/* 133:156 */     ret.add(item);
/* 134:157 */     return ret;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
/* 138:    */   {
/* 139:162 */     par2EntityPlayer.addStat(net.minecraft.stats.StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
/* 140:163 */     par2EntityPlayer.addExhaustion(0.025F);
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
/* 144:    */   {
/* 145:168 */     int l = MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
/* 146:169 */     int metadata = 0;
/* 147:171 */     if (l == 0) {
/* 148:172 */       metadata = 2;
/* 149:    */     }
/* 150:175 */     if (l == 1) {
/* 151:176 */       metadata = 5;
/* 152:    */     }
/* 153:179 */     if (l == 2) {
/* 154:180 */       metadata = 3;
/* 155:    */     }
/* 156:183 */     if (l == 3) {
/* 157:184 */       metadata = 4;
/* 158:    */     }
/* 159:187 */     metadata += par6ItemStack.getItemDamage() % 2 * 6;
/* 160:188 */     par1World.setBlockMetadataWithNotify(par2, par3, par4, metadata, 2);
/* 161:190 */     if (par6ItemStack.hasTagCompound())
/* 162:    */     {
/* 163:191 */       TileEntity cabinet = par1World.getTileEntity(par2, par3, par4);
/* 164:193 */       if ((cabinet != null) && ((cabinet instanceof TileEntityFilingCabinet))) {
/* 165:194 */         ((TileEntityFilingCabinet)cabinet).readInvFromTags(par6ItemStack.getTagCompound());
/* 166:    */       }
/* 167:    */     }
/* 168:    */   }
/* 169:    */   
/* 170:    */   @SideOnly(Side.CLIENT)
/* 171:    */   public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/* 172:    */   {
/* 173:246 */     par3List.add(new ItemStack(par1, 1, 0));
/* 174:247 */     par3List.add(new ItemStack(par1, 1, 1));
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void addInformation(ItemStack item, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
/* 178:    */   {
/* 179:252 */     if (item.hasTagCompound())
/* 180:    */     {
/* 181:253 */       NBTTagCompound tags = item.getTagCompound();
/* 182:255 */       if (tags.hasKey("item_no"))
/* 183:    */       {
/* 184:256 */         int n = item.getTagCompound().getInteger("item_no");
/* 185:257 */         int k = 0;
/* 186:259 */         for (int i = 0; i < n; i++) {
/* 187:260 */           k += tags.getCompoundTag("item_" + i).getInteger("Size");
/* 188:    */         }
/* 189:263 */         par3List.add("contains " + k + " item" + XUHelper.s(k) + " of " + n + " type" + XUHelper.s(k));
/* 190:    */       }
/* 191:    */     }
/* 192:    */   }
/* 193:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BlockFilingCabinet
 * JD-Core Version:    0.7.0.1
 */