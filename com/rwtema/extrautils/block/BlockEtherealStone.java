/*   1:    */ package com.rwtema.extrautils.block;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*   5:    */ import cpw.mods.fml.relauncher.Side;
/*   6:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   7:    */ import java.util.List;
/*   8:    */ import net.minecraft.block.Block;
/*   9:    */ import net.minecraft.block.material.Material;
/*  10:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  11:    */ import net.minecraft.creativetab.CreativeTabs;
/*  12:    */ import net.minecraft.entity.Entity;
/*  13:    */ import net.minecraft.entity.player.EntityPlayer;
/*  14:    */ import net.minecraft.item.Item;
/*  15:    */ import net.minecraft.item.ItemStack;
/*  16:    */ import net.minecraft.util.AxisAlignedBB;
/*  17:    */ import net.minecraft.util.IIcon;
/*  18:    */ import net.minecraft.world.IBlockAccess;
/*  19:    */ import net.minecraft.world.World;
/*  20:    */ 
/*  21:    */ public class BlockEtherealStone
/*  22:    */   extends Block
/*  23:    */ {
/*  24:    */   private static final int numTypes = 6;
/*  25: 24 */   private IIcon[] icon = new IIcon[16];
/*  26:    */   private final boolean[] dark;
/*  27: 30 */   private final boolean[] polarity = new boolean[16];
/*  28:    */   
/*  29:    */   public BlockEtherealStone()
/*  30:    */   {
/*  31: 41 */     super(Material.glass);
/*  32: 31 */     for (int i = 3; i < 6; i++) {
/*  33: 32 */       this.polarity[i] = true;
/*  34:    */     }
/*  35: 34 */     this.dark = new boolean[16]; int 
/*  36: 35 */       tmp64_63 = 1;this.dark[2] = tmp64_63;this.dark[5] = tmp64_63;
/*  37:    */     
/*  38:    */ 
/*  39:    */ 
/*  40:    */ 
/*  41:    */ 
/*  42:    */ 
/*  43: 42 */     setLightOpacity(0);
/*  44: 43 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  45: 44 */     setBlockName("extrautils:etherealglass");
/*  46: 45 */     setBlockTextureName("extrautils:etherealglass");
/*  47: 46 */     setHardness(0.5F);
/*  48:    */   }
/*  49:    */   
/*  50:    */   @SideOnly(Side.CLIENT)
/*  51:    */   public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
/*  52:    */   {
/*  53: 52 */     for (int i = 0; i < 6; i++) {
/*  54: 53 */       p_149666_3_.add(new ItemStack(p_149666_1_, 1, i));
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   @SideOnly(Side.CLIENT)
/*  59:    */   public void registerBlockIcons(IIconRegister par1IIconRegister)
/*  60:    */   {
/*  61: 64 */     this.blockIcon = (this.icon[0] =  = new IconConnectedTexture(par1IIconRegister, "extrautils:ConnectedTextures/etherealglass"));
/*  62: 65 */     this.icon[1] = new IconConnectedTexture(par1IIconRegister, "extrautils:ConnectedTextures/etherealglass1");
/*  63: 66 */     this.icon[2] = new IconConnectedTexture(par1IIconRegister, "extrautils:ConnectedTextures/etherealdarkglass");
/*  64: 67 */     this.icon[3] = new IconConnectedTexture(par1IIconRegister, "extrautils:ConnectedTextures/untherealglass1");
/*  65: 68 */     this.icon[4] = new IconConnectedTexture(par1IIconRegister, "extrautils:ConnectedTextures/untherealglass");
/*  66: 69 */     this.icon[5] = new IconConnectedTexture(par1IIconRegister, "extrautils:ConnectedTextures/untherealdarkglass");
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean getBlocksMovement(IBlockAccess world, int x, int y, int z)
/*  70:    */   {
/*  71: 74 */     int blockMetadata = world.getBlockMetadata(x, y, z);
/*  72: 75 */     return (blockMetadata < 6) && (this.polarity[blockMetadata] != 0);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getLightOpacity(IBlockAccess world, int x, int y, int z)
/*  76:    */   {
/*  77: 80 */     if (this.dark[world.getBlockMetadata(x, y, z)] != 0) {
/*  78: 81 */       return 255;
/*  79:    */     }
/*  80: 82 */     return super.getLightOpacity(world, x, y, z);
/*  81:    */   }
/*  82:    */   
/*  83:    */   @SideOnly(Side.CLIENT)
/*  84:    */   public IIcon getIcon(int side, int meta)
/*  85:    */   {
/*  86: 88 */     return this.icon[(meta % 6)];
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int getRenderType()
/*  90:    */   {
/*  91: 96 */     return ExtraUtilsProxy.connectedTextureEtheralID;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean isOpaqueCube()
/*  95:    */   {
/*  96:106 */     return false;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public boolean renderAsNormalBlock()
/* 100:    */   {
/* 101:115 */     return false;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean isNormalCube()
/* 105:    */   {
/* 106:120 */     return false;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean isNormalCube(IBlockAccess world, int x, int y, int z)
/* 110:    */   {
/* 111:125 */     return false;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public boolean canCollideCheck(int p_149678_1_, boolean p_149678_2_)
/* 115:    */   {
/* 116:130 */     return super.canCollideCheck(p_149678_1_, p_149678_2_);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_)
/* 120:    */   {
/* 121:136 */     super.onEntityCollidedWithBlock(p_149670_1_, p_149670_2_, p_149670_3_, p_149670_4_, p_149670_5_);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB bbs, List list, Entity entity)
/* 125:    */   {
/* 126:147 */     if (this.polarity[world.getBlockMetadata(x, y, z)] != 0)
/* 127:    */     {
/* 128:148 */       if ((entity instanceof EntityPlayer)) {
/* 129:149 */         super.addCollisionBoxesToList(world, x, y, z, bbs, list, entity);
/* 130:    */       }
/* 131:    */     }
/* 132:    */     else
/* 133:    */     {
/* 134:151 */       if (((entity instanceof EntityPlayer)) && 
/* 135:152 */         (!entity.isSneaking())) {
/* 136:153 */         return;
/* 137:    */       }
/* 138:156 */       super.addCollisionBoxesToList(world, x, y, z, bbs, list, entity);
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
/* 143:    */   {
/* 144:163 */     int meta = world.getBlockMetadata(x, y, z);
/* 145:164 */     if ((meta < 6) && (this.polarity[meta] != 0)) {
/* 146:165 */       return AxisAlignedBB.getBoundingBox(x, y + 0.001D, z, x + 1, y + 1, z + 1);
/* 147:    */     }
/* 148:167 */     return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
/* 149:    */   }
/* 150:    */   
/* 151:    */   @SideOnly(Side.CLIENT)
/* 152:    */   public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
/* 153:    */   {
/* 154:177 */     return par1IBlockAccess.getBlock(par2, par3, par4) != this;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public int damageDropped(int p_149692_1_)
/* 158:    */   {
/* 159:182 */     return p_149692_1_;
/* 160:    */   }
/* 161:    */   
/* 162:    */   @SideOnly(Side.CLIENT)
/* 163:    */   public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
/* 164:    */   {
/* 165:188 */     return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
/* 166:    */   }
/* 167:    */   
/* 168:    */   public boolean isBlockSolid(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
/* 169:    */   {
/* 170:198 */     return this.polarity[(par1IBlockAccess.getBlockMetadata(par2, par3, par4) % 6)] == 0;
/* 171:    */   }
/* 172:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BlockEtherealStone
 * JD-Core Version:    0.7.0.1
 */