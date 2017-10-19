/*   1:    */ package com.rwtema.extrautils.block;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.tileentity.TileEntityBlockColorData;
/*   5:    */ import cpw.mods.fml.relauncher.Side;
/*   6:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   7:    */ import java.util.List;
/*   8:    */ import net.minecraft.block.Block;
/*   9:    */ import net.minecraft.block.ITileEntityProvider;
/*  10:    */ import net.minecraft.block.material.Material;
/*  11:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  12:    */ import net.minecraft.creativetab.CreativeTabs;
/*  13:    */ import net.minecraft.item.Item;
/*  14:    */ import net.minecraft.tileentity.TileEntity;
/*  15:    */ import net.minecraft.util.AxisAlignedBB;
/*  16:    */ import net.minecraft.world.IBlockAccess;
/*  17:    */ import net.minecraft.world.World;
/*  18:    */ 
/*  19:    */ public class BlockColorData
/*  20:    */   extends Block
/*  21:    */   implements ITileEntityProvider
/*  22:    */ {
/*  23:    */   public BlockColorData()
/*  24:    */   {
/*  25: 22 */     super(Material.air);
/*  26: 23 */     setLightLevel(0.0F);
/*  27: 24 */     setLightOpacity(0);
/*  28: 25 */     setBlockName("extrautils:datablock");
/*  29: 26 */     setBlockBounds(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
/*  30: 27 */     setHardness(0.0F);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static int dataBlockX(int x)
/*  34:    */   {
/*  35: 35 */     return x >> 4 << 4;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static int dataBlockY(int y)
/*  39:    */   {
/*  40: 39 */     return 255;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public static int dataBlockZ(int z)
/*  44:    */   {
/*  45: 47 */     return z >> 4 << 4;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static float[] getColorData(IBlockAccess world, int x, int y, int z)
/*  49:    */   {
/*  50: 51 */     return getColorData(world, x, y, z, world.getBlockMetadata(x, y, z));
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static float[] getColorData(IBlockAccess world, int x, int y, int z, int metadata)
/*  54:    */   {
/*  55: 55 */     x = dataBlockX(x);
/*  56: 56 */     y = dataBlockY(y);
/*  57: 57 */     z = dataBlockZ(z);
/*  58: 58 */     TileEntityBlockColorData datablock = null;
/*  59: 60 */     if ((world.getTileEntity(x, y, z) instanceof TileEntityBlockColorData)) {
/*  60: 61 */       datablock = (TileEntityBlockColorData)world.getTileEntity(x, y, z);
/*  61:    */     } else {
/*  62: 63 */       return BlockColor.initColor[metadata];
/*  63:    */     }
/*  64: 66 */     return datablock.palette[metadata];
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static boolean changeColorData(World world, int x, int y, int z, int metadata, float r, float g, float b)
/*  68:    */   {
/*  69: 70 */     x = dataBlockX(x);
/*  70: 71 */     y = dataBlockY(y);
/*  71: 72 */     z = dataBlockZ(z);
/*  72: 73 */     TileEntityBlockColorData datablock = null;
/*  73: 75 */     if ((world.getTileEntity(x, y, z) instanceof TileEntityBlockColorData))
/*  74:    */     {
/*  75: 76 */       datablock = (TileEntityBlockColorData)world.getTileEntity(x, y, z);
/*  76:    */     }
/*  77: 78 */     else if (world.isAirBlock(x, y, z))
/*  78:    */     {
/*  79: 79 */       world.setBlock(x, y, z, ExtraUtils.colorBlockData);
/*  80: 80 */       datablock = (TileEntityBlockColorData)world.getTileEntity(x, y, z);
/*  81:    */     }
/*  82:    */     else
/*  83:    */     {
/*  84: 82 */       return false;
/*  85:    */     }
/*  86: 86 */     if (datablock == null) {
/*  87: 87 */       return false;
/*  88:    */     }
/*  89: 90 */     datablock.setColor(metadata, r, g, b);
/*  90: 91 */     world.markBlockForUpdate(x, y, z);
/*  91: 92 */     return true;
/*  92:    */   }
/*  93:    */   
/*  94:    */   @SideOnly(Side.CLIENT)
/*  95:    */   public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {}
/*  96:    */   
/*  97:    */   @SideOnly(Side.CLIENT)
/*  98:    */   public void registerBlockIcons(IIconRegister par1IIconRegister) {}
/*  99:    */   
/* 100:    */   public int getRenderType()
/* 101:    */   {
/* 102:117 */     return -1;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public int getMobilityFlag()
/* 106:    */   {
/* 107:126 */     return 1;
/* 108:    */   }
/* 109:    */   
/* 110:    */   @SideOnly(Side.CLIENT)
/* 111:    */   public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
/* 112:    */   {
/* 113:135 */     return null;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
/* 117:    */   {
/* 118:144 */     return null;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean isOpaqueCube()
/* 122:    */   {
/* 123:154 */     return false;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public boolean isCollidable()
/* 127:    */   {
/* 128:162 */     return false;
/* 129:    */   }
/* 130:    */   
/* 131:    */   @SideOnly(Side.CLIENT)
/* 132:    */   public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
/* 133:    */   {
/* 134:168 */     return false;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public boolean isAir(IBlockAccess world, int x, int y, int z)
/* 138:    */   {
/* 139:173 */     return true;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public TileEntity createNewTileEntity(World var1, int var2)
/* 143:    */   {
/* 144:178 */     return new TileEntityBlockColorData();
/* 145:    */   }
/* 146:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BlockColorData
 * JD-Core Version:    0.7.0.1
 */