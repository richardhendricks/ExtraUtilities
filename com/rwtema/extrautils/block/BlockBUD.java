/*   1:    */ package com.rwtema.extrautils.block;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   5:    */ import com.rwtema.extrautils.tileentity.TileEntityBUD;
/*   6:    */ import cpw.mods.fml.relauncher.Side;
/*   7:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Random;
/*  10:    */ import net.minecraft.block.Block;
/*  11:    */ import net.minecraft.block.material.Material;
/*  12:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  13:    */ import net.minecraft.creativetab.CreativeTabs;
/*  14:    */ import net.minecraft.entity.player.EntityPlayer;
/*  15:    */ import net.minecraft.init.Blocks;
/*  16:    */ import net.minecraft.item.Item;
/*  17:    */ import net.minecraft.item.ItemStack;
/*  18:    */ import net.minecraft.tileentity.TileEntity;
/*  19:    */ import net.minecraft.util.IIcon;
/*  20:    */ import net.minecraft.world.IBlockAccess;
/*  21:    */ import net.minecraft.world.World;
/*  22:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  23:    */ 
/*  24:    */ public class BlockBUD
/*  25:    */   extends Block
/*  26:    */ {
/*  27:    */   private IIcon[] icons;
/*  28:    */   
/*  29:    */   public BlockBUD()
/*  30:    */   {
/*  31: 31 */     super(Material.anvil);
/*  32: 32 */     setBlockName("extrautils:budoff");
/*  33: 33 */     setBlockTextureName("extrautils:budoff");
/*  34: 34 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  35: 35 */     setHardness(1.0F);
/*  36: 36 */     setResistance(10.0F).setStepSound(soundTypeStone);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer player, int par6, float par7, float par8, float par9)
/*  40:    */   {
/*  41: 41 */     if ((!par1World.isClient) && 
/*  42: 42 */       (XUHelper.isWrench(player.getCurrentEquippedItem())))
/*  43:    */     {
/*  44: 43 */       int metadata = par1World.getBlockMetadata(par2, par3, par4);
/*  45: 45 */       if (metadata >= 3)
/*  46:    */       {
/*  47: 46 */         metadata = 3 + (metadata - 2) % 7;
/*  48: 47 */         par1World.setBlockMetadataWithNotify(par2, par3, par4, metadata, 3);
/*  49:    */       }
/*  50:    */     }
/*  51: 52 */     return true;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int damageDropped(int par1)
/*  55:    */   {
/*  56: 61 */     return par1 >= 3 ? 3 : 0;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean hasTileEntity(int metadata)
/*  60:    */   {
/*  61: 66 */     return metadata >= 3;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public TileEntity createTileEntity(World world, int metadata)
/*  65:    */   {
/*  66: 71 */     if (metadata >= 3) {
/*  67: 72 */       return new TileEntityBUD();
/*  68:    */     }
/*  69: 75 */     return null;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public boolean renderAsNormalBlock()
/*  73:    */   {
/*  74: 84 */     return false;
/*  75:    */   }
/*  76:    */   
/*  77:    */   @SideOnly(Side.CLIENT)
/*  78:    */   public void registerBlockIcons(IIconRegister par1IIconRegister)
/*  79:    */   {
/*  80: 91 */     this.icons = new IIcon[6];
/*  81: 92 */     this.icons[0] = par1IIconRegister.registerIcon("extrautils:budoff");
/*  82: 93 */     this.icons[1] = par1IIconRegister.registerIcon("extrautils:budon");
/*  83: 94 */     this.icons[2] = par1IIconRegister.registerIcon("extrautils:advbudoff");
/*  84: 95 */     this.icons[3] = par1IIconRegister.registerIcon("extrautils:advbudon");
/*  85: 96 */     this.icons[4] = par1IIconRegister.registerIcon("extrautils:advbuddisabledoff");
/*  86: 97 */     this.icons[5] = par1IIconRegister.registerIcon("extrautils:advbuddisabledon");
/*  87:    */   }
/*  88:    */   
/*  89:    */   @SideOnly(Side.CLIENT)
/*  90:    */   public IIcon getIcon(int par1, int par2)
/*  91:    */   {
/*  92:107 */     if (par2 >= 3) {
/*  93:108 */       return this.icons[2];
/*  94:    */     }
/*  95:110 */     return this.icons[0];
/*  96:    */   }
/*  97:    */   
/*  98:    */   @SideOnly(Side.CLIENT)
/*  99:    */   public IIcon getIcon(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
/* 100:    */   {
/* 101:120 */     int metadata = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
/* 102:121 */     int i = metadata >= 3 ? 2 : 0;
/* 103:123 */     if ((metadata > 3) && (metadata - 4 != net.minecraft.util.Facing.oppositeSide[par5])) {
/* 104:124 */       i = 4;
/* 105:    */     }
/* 106:127 */     if ((par1IBlockAccess.getTileEntity(par2, par3, par4) instanceof TileEntityBUD)) {
/* 107:128 */       return ((TileEntityBUD)par1IBlockAccess.getTileEntity(par2, par3, par4)).getPowered() ? this.icons[(i + 1)] : this.icons[i];
/* 108:    */     }
/* 109:130 */     return this.icons[(i + metadata % 2)];
/* 110:    */   }
/* 111:    */   
/* 112:    */   public int isProvidingStrongPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
/* 113:    */   {
/* 114:139 */     int metadata = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
/* 115:141 */     if (metadata == 1) {
/* 116:142 */       return 15;
/* 117:    */     }
/* 118:143 */     if (metadata >= 3)
/* 119:    */     {
/* 120:144 */       if ((metadata > 3) && (metadata - 4 != par5)) {
/* 121:145 */         return 0;
/* 122:    */       }
/* 123:148 */       if ((par1IBlockAccess.getTileEntity(par2, par3, par4) instanceof TileEntityBUD)) {
/* 124:149 */         return ((TileEntityBUD)par1IBlockAccess.getTileEntity(par2, par3, par4)).getPowered() ? 15 : 0;
/* 125:    */       }
/* 126:151 */       return 0;
/* 127:    */     }
/* 128:154 */     return 0;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public boolean canProvidePower()
/* 132:    */   {
/* 133:164 */     return true;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
/* 137:    */   {
/* 138:172 */     return isProvidingStrongPower(par1IBlockAccess, par2, par3, par4, par5);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public boolean canConnectRedstone(IBlockAccess iba, int i, int j, int k, int dir)
/* 142:    */   {
/* 143:177 */     return true;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public int tickRate()
/* 147:    */   {
/* 148:181 */     return 2;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block par5)
/* 152:    */   {
/* 153:191 */     if (((par5 != this ? 1 : 0) & (par5 != Blocks.unpowered_repeater ? 1 : 0)) != 0)
/* 154:    */     {
/* 155:192 */       int data = par1World.getBlockMetadata(par2, par3, par4);
/* 156:194 */       if (data == 0) {
/* 157:195 */         par1World.scheduleBlockUpdate(par2, par3, par4, this, tickRate());
/* 158:    */       }
/* 159:    */     }
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void updateRedstone(World par1World, int par2, int par3, int par4)
/* 163:    */   {
/* 164:201 */     par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this);
/* 165:202 */     par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, this);
/* 166:203 */     par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, this);
/* 167:204 */     par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, this);
/* 168:205 */     par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, this);
/* 169:206 */     par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, this);
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
/* 173:    */   {
/* 174:211 */     if (par1World.getBlockMetadata(par2, par3, par4) == 0)
/* 175:    */     {
/* 176:212 */       par1World.setBlockMetadataWithNotify(par2, par3, par4, 1, 3);
/* 177:213 */       par1World.scheduleBlockUpdate(par2, par3, par4, this, tickRate());
/* 178:    */     }
/* 179:214 */     else if (par1World.getBlockMetadata(par2, par3, par4) == 1)
/* 180:    */     {
/* 181:215 */       par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 3);
/* 182:216 */       par1World.scheduleBlockUpdate(par2, par3, par4, this, tickRate() * 3);
/* 183:    */     }
/* 184:217 */     else if (par1World.getBlockMetadata(par2, par3, par4) == 2)
/* 185:    */     {
/* 186:218 */       par1World.setBlockMetadataWithNotify(par2, par3, par4, 0, 3);
/* 187:    */     }
/* 188:221 */     updateRedstone(par1World, par2, par3, par4);
/* 189:    */   }
/* 190:    */   
/* 191:    */   public boolean isBlockSolid(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
/* 192:    */   {
/* 193:231 */     return true;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
/* 197:    */   {
/* 198:248 */     super.isSideSolid(world, x, y, z, side);
/* 199:249 */     return true;
/* 200:    */   }
/* 201:    */   
/* 202:    */   @SideOnly(Side.CLIENT)
/* 203:    */   public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/* 204:    */   {
/* 205:258 */     par3List.add(new ItemStack(par1, 1, 0));
/* 206:259 */     par3List.add(new ItemStack(par1, 1, 3));
/* 207:    */   }
/* 208:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BlockBUD
 * JD-Core Version:    0.7.0.1
 */