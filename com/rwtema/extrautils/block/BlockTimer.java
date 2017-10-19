/*   1:    */ package com.rwtema.extrautils.block;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import cpw.mods.fml.relauncher.Side;
/*   5:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   6:    */ import java.util.Random;
/*   7:    */ import net.minecraft.block.Block;
/*   8:    */ import net.minecraft.block.material.Material;
/*   9:    */ import net.minecraft.world.IBlockAccess;
/*  10:    */ import net.minecraft.world.World;
/*  11:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  12:    */ 
/*  13:    */ public class BlockTimer
/*  14:    */   extends Block
/*  15:    */ {
/*  16: 15 */   int powered_y = 0;
/*  17: 15 */   int powered_z = 0;
/*  18: 15 */   int powered_x = 0;
/*  19: 16 */   private boolean powered = true;
/*  20: 17 */   private boolean changing = false;
/*  21:    */   
/*  22:    */   public BlockTimer()
/*  23:    */   {
/*  24: 20 */     super(Material.rock);
/*  25: 21 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  26: 22 */     setBlockName("extrautils:timer");
/*  27: 23 */     setBlockTextureName("extrautils:timer");
/*  28: 24 */     setHardness(1.0F);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public int getTickRate(int metadata)
/*  32:    */   {
/*  33: 28 */     return 20;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public int getMobilityFlag()
/*  37:    */   {
/*  38: 37 */     return 2;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
/*  42:    */   {
/*  43: 49 */     return (this.powered) && (par1IBlockAccess.getBlockMetadata(par2, par3, par4) == 1) ? 15 : 0;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
/*  47:    */   {
/*  48: 65 */     return true;
/*  49:    */   }
/*  50:    */   
/*  51:    */   @SideOnly(Side.CLIENT)
/*  52:    */   public boolean isBlockNormalCube()
/*  53:    */   {
/*  54: 71 */     return false;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public boolean canProvidePower()
/*  58:    */   {
/*  59: 80 */     return true;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void onBlockAdded(World world, int x, int y, int z)
/*  63:    */   {
/*  64: 88 */     int metadata = world.getBlockMetadata(x, y, z);
/*  65: 89 */     world.scheduleBlockUpdate(x, y, z, this, getTickRate(metadata << 1) - 2);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void updateTick(World world, int x, int y, int z, Random rand)
/*  69:    */   {
/*  70: 97 */     int metadata = world.getBlockMetadata(x, y, z);
/*  71: 99 */     if (metadata == 0)
/*  72:    */     {
/*  73:100 */       this.changing = true;
/*  74:101 */       world.setBlockMetadataWithNotify(x, y, z, 1, 1);
/*  75:102 */       this.changing = false;
/*  76:103 */       world.scheduleBlockUpdate(x, y, z, this, 2);
/*  77:    */     }
/*  78:104 */     else if (metadata == 1)
/*  79:    */     {
/*  80:105 */       world.setBlockMetadataWithNotify(x, y, z, 0, 1);
/*  81:106 */       this.powered = false;
/*  82:107 */       boolean p = world.getBlockPowerInput(x, y, z) > 0;
/*  83:108 */       this.powered = true;
/*  84:110 */       if (p)
/*  85:    */       {
/*  86:111 */         this.changing = true;
/*  87:112 */         world.setBlockMetadataWithNotify(x, y, z, 2, 0);
/*  88:113 */         this.changing = false;
/*  89:    */       }
/*  90:    */       else
/*  91:    */       {
/*  92:115 */         world.scheduleBlockUpdate(x, y, z, this, getTickRate(metadata) - 2);
/*  93:    */       }
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void onNeighborBlockChange(World world, int x, int y, int z, Block par5)
/*  98:    */   {
/*  99:122 */     if (this.changing) {
/* 100:123 */       return;
/* 101:    */     }
/* 102:126 */     int metadata = world.getBlockMetadata(x, y, z);
/* 103:127 */     this.powered = false;
/* 104:128 */     boolean p = world.getBlockPowerInput(x, y, z) > 0;
/* 105:129 */     this.powered = true;
/* 106:131 */     if ((metadata == 0) && (p))
/* 107:    */     {
/* 108:132 */       world.setBlockMetadataWithNotify(x, y, z, 2, 0);
/* 109:    */     }
/* 110:133 */     else if ((metadata == 2) && (!p))
/* 111:    */     {
/* 112:134 */       world.setBlockMetadataWithNotify(x, y, z, 0, 0);
/* 113:135 */       world.scheduleBlockUpdate(x, y, z, this, getTickRate(metadata));
/* 114:    */     }
/* 115:    */   }
/* 116:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BlockTimer
 * JD-Core Version:    0.7.0.1
 */