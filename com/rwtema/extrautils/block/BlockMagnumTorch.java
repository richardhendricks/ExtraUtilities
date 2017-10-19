/*   1:    */ package com.rwtema.extrautils.block;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.tileentity.TileEntityAntiMobTorch;
/*   5:    */ import cpw.mods.fml.relauncher.Side;
/*   6:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   7:    */ import java.util.Random;
/*   8:    */ import net.minecraft.block.Block;
/*   9:    */ import net.minecraft.block.material.Material;
/*  10:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  11:    */ import net.minecraft.tileentity.TileEntity;
/*  12:    */ import net.minecraft.util.IIcon;
/*  13:    */ import net.minecraft.world.World;
/*  14:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  15:    */ 
/*  16:    */ public class BlockMagnumTorch
/*  17:    */   extends Block
/*  18:    */ {
/*  19:    */   private IIcon iconTop;
/*  20:    */   private IIcon iconBase;
/*  21:    */   
/*  22:    */   public BlockMagnumTorch()
/*  23:    */   {
/*  24: 24 */     super(Material.circuits);
/*  25: 25 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  26: 26 */     setBlockName("extrautils:magnumTorch");
/*  27: 27 */     setBlockTextureName("extrautils:magnumTorch");
/*  28: 28 */     setHardness(1.2F);
/*  29: 29 */     setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);
/*  30: 30 */     setLightLevel(1.0F);
/*  31: 31 */     setLightOpacity(0);
/*  32:    */   }
/*  33:    */   
/*  34:    */   @SideOnly(Side.CLIENT)
/*  35:    */   public void registerBlockIcons(IIconRegister par1IIconRegister)
/*  36:    */   {
/*  37: 41 */     this.blockIcon = par1IIconRegister.registerIcon("extrautils:magnumTorch");
/*  38: 42 */     this.iconTop = par1IIconRegister.registerIcon("extrautils:magnumTorchTop");
/*  39: 43 */     this.iconBase = par1IIconRegister.registerIcon("extrautils:magnumTorchBase");
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean renderAsNormalBlock()
/*  43:    */   {
/*  44: 48 */     return false;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean isOpaqueCube()
/*  48:    */   {
/*  49: 58 */     return false;
/*  50:    */   }
/*  51:    */   
/*  52:    */   @SideOnly(Side.CLIENT)
/*  53:    */   public IIcon getIcon(int par1, int par2)
/*  54:    */   {
/*  55: 67 */     switch (par1)
/*  56:    */     {
/*  57:    */     case 0: 
/*  58: 69 */       return this.iconBase;
/*  59:    */     case 1: 
/*  60: 72 */       return this.iconTop;
/*  61:    */     }
/*  62: 75 */     return this.blockIcon;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public boolean hasTileEntity(int metadata)
/*  66:    */   {
/*  67: 81 */     return true;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public TileEntity createTileEntity(World world, int metadata)
/*  71:    */   {
/*  72: 96 */     return new TileEntityAntiMobTorch();
/*  73:    */   }
/*  74:    */   
/*  75:    */   @SideOnly(Side.CLIENT)
/*  76:    */   public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
/*  77:    */   {
/*  78:105 */     float dx = (float)((Math.random() * 2.0D - 1.0D) * 0.125D);
/*  79:106 */     float dy = (float)((Math.random() * 2.0D - 1.0D) * 0.0625D);
/*  80:107 */     float dz = (float)((Math.random() * 2.0D - 1.0D) * 0.125D);
/*  81:108 */     par1World.spawnParticle("smoke", par2 + 0.5F + dx, par3 + 1 + dy, par4 + 0.5F + dz, 0.0D, 0.0D, 0.0D);
/*  82:109 */     par1World.spawnParticle("flame", par2 + 0.5F + dx, par3 + 1 + dy, par4 + 0.5F + dz, 0.0D, 0.0D, 0.0D);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
/*  86:    */   {
/*  87:118 */     return par1World.isSideSolid(par2, par3 - 1, par4, ForgeDirection.UP, true);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block par5)
/*  91:    */   {
/*  92:128 */     if ((!canPlaceBlockAt(par1World, par2, par3, par4)) && 
/*  93:129 */       (par1World.getBlock(par2, par3, par4) == this))
/*  94:    */     {
/*  95:130 */       dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
/*  96:131 */       par1World.setBlockToAir(par2, par3, par4);
/*  97:    */     }
/*  98:    */   }
/*  99:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BlockMagnumTorch
 * JD-Core Version:    0.7.0.1
 */