/*   1:    */ package com.rwtema.extrautils.block;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.tileentity.TileEntityAntiMobTorch;
/*   5:    */ import cpw.mods.fml.relauncher.Side;
/*   6:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   7:    */ import java.util.Random;
/*   8:    */ import net.minecraft.block.Block;
/*   9:    */ import net.minecraft.block.material.Material;
/*  10:    */ import net.minecraft.init.Blocks;
/*  11:    */ import net.minecraft.tileentity.TileEntity;
/*  12:    */ import net.minecraft.world.World;
/*  13:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  14:    */ 
/*  15:    */ public class BlockChandelier
/*  16:    */   extends Block
/*  17:    */ {
/*  18: 17 */   public static int range = 1;
/*  19: 19 */   public static int[] dx = { -1, 1, 0, 0, 0, 0 };
/*  20: 20 */   public static int[] dy = { 0, 0, 0, 0, -1, 1 };
/*  21: 21 */   public static int[] dz = { 0, 0, -1, 1, 0, 0 };
/*  22:    */   
/*  23:    */   public BlockChandelier()
/*  24:    */   {
/*  25: 24 */     super(Material.circuits);
/*  26: 25 */     setLightLevel(1.0F);
/*  27: 26 */     setLightOpacity(0);
/*  28: 27 */     setBlockName("extrautils:chandelier");
/*  29: 28 */     setBlockTextureName("extrautils:chandelier");
/*  30: 29 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  31: 30 */     setHardness(0.1F);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public boolean isOpaqueCube()
/*  35:    */   {
/*  36: 40 */     return false;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public boolean renderAsNormalBlock()
/*  40:    */   {
/*  41: 49 */     return false;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public int getRenderType()
/*  45:    */   {
/*  46: 57 */     return 1;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
/*  50:    */   {
/*  51: 66 */     if (par1World.isSideSolid(par2, par3 + 1, par4, ForgeDirection.DOWN, true)) {
/*  52: 67 */       return true;
/*  53:    */     }
/*  54: 69 */     Block id = par1World.getBlock(par2, par3 + 1, par4);
/*  55: 70 */     return (id == Blocks.fence) || (id == Blocks.nether_brick_fence) || (id == Blocks.glass) || (id == Blocks.cobblestone_wall);
/*  56:    */   }
/*  57:    */   
/*  58:    */   private boolean dropTorchIfCantStay(World par1World, int par2, int par3, int par4)
/*  59:    */   {
/*  60: 80 */     if (!canPlaceBlockAt(par1World, par2, par3, par4))
/*  61:    */     {
/*  62: 81 */       if (par1World.getBlock(par2, par3, par4) == this)
/*  63:    */       {
/*  64: 82 */         dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
/*  65: 83 */         par1World.setBlockToAir(par2, par3, par4);
/*  66:    */       }
/*  67: 86 */       return false;
/*  68:    */     }
/*  69: 88 */     return true;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void onNeighborBlockChange(World world, int x, int y, int z, Block par5)
/*  73:    */   {
/*  74: 99 */     if (!canPlaceBlockAt(world, x, y, z))
/*  75:    */     {
/*  76:100 */       dropBlockAsItem(world, x, y, z, 0, 0);
/*  77:101 */       world.setBlockToAir(x, y, z);
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public boolean hasTileEntity(int metadata)
/*  82:    */   {
/*  83:107 */     return true;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public TileEntity createTileEntity(World world, int metadata)
/*  87:    */   {
/*  88:122 */     return new TileEntityAntiMobTorch();
/*  89:    */   }
/*  90:    */   
/*  91:    */   @SideOnly(Side.CLIENT)
/*  92:    */   public void randomDisplayTick(World world, int x, int y, int z, Random rand)
/*  93:    */   {
/*  94:131 */     double i = rand.nextInt(5);
/*  95:132 */     double t = (2.0D + i * 3.0D) / 16.0D;
/*  96:134 */     if (rand.nextBoolean())
/*  97:    */     {
/*  98:135 */       world.spawnParticle("smoke", x + t, y + Math.abs(i - 2.0D) * 2.0D / 16.0D, z + t, 0.0D, 0.0D, 0.0D);
/*  99:136 */       world.spawnParticle("flame", x + t, y + Math.abs(i - 2.0D) * 2.0D / 16.0D, z + t, 0.0D, 0.0D, 0.0D);
/* 100:    */     }
/* 101:    */     else
/* 102:    */     {
/* 103:138 */       world.spawnParticle("smoke", x + t, y + Math.abs(i - 2.0D) * 2.0D / 16.0D, z + 1 - t, 0.0D, 0.0D, 0.0D);
/* 104:139 */       world.spawnParticle("flame", x + t, y + Math.abs(i - 2.0D) * 2.0D / 16.0D, z + 1 - t, 0.0D, 0.0D, 0.0D);
/* 105:    */     }
/* 106:    */   }
/* 107:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BlockChandelier
 * JD-Core Version:    0.7.0.1
 */