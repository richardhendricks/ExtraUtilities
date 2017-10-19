/*   1:    */ package com.rwtema.extrautils.tileentity.endercollector;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.block.BlockMultiBlock;
/*   5:    */ import com.rwtema.extrautils.block.Box;
/*   6:    */ import com.rwtema.extrautils.block.BoxModel;
/*   7:    */ import net.minecraft.block.Block;
/*   8:    */ import net.minecraft.block.material.Material;
/*   9:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  10:    */ import net.minecraft.entity.player.EntityPlayer;
/*  11:    */ import net.minecraft.tileentity.TileEntity;
/*  12:    */ import net.minecraft.util.IIcon;
/*  13:    */ import net.minecraft.world.IBlockAccess;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  16:    */ 
/*  17:    */ public class BlockEnderCollector
/*  18:    */   extends BlockMultiBlock
/*  19:    */ {
/*  20:    */   IIcon side;
/*  21:    */   IIcon bottom;
/*  22:    */   IIcon top1;
/*  23:    */   IIcon top2;
/*  24:    */   IIcon side_disabled;
/*  25:    */   IIcon top2_disabled;
/*  26:    */   
/*  27:    */   public BlockEnderCollector()
/*  28:    */   {
/*  29: 20 */     super(Material.rock);
/*  30: 21 */     setBlockName("extrautils:enderCollector");
/*  31: 22 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  32: 23 */     setHardness(1.5F).setStepSound(soundTypeStone);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void prepareForRender(String label) {}
/*  36:    */   
/*  37:    */   public BoxModel getWorldModel(IBlockAccess world, int x, int y, int z)
/*  38:    */   {
/*  39: 33 */     return getInventoryModel(world.getBlockMetadata(x, y, z));
/*  40:    */   }
/*  41:    */   
/*  42:    */   public BoxModel getInventoryModel(int metadata)
/*  43:    */   {
/*  44: 40 */     boolean disabled = metadata >= 6;
/*  45: 41 */     BoxModel boxes = new BoxModel();
/*  46: 42 */     IIcon sideIcon = disabled ? this.side_disabled : this.side;
/*  47: 43 */     boxes.addBoxI(1, 0, 4, 15, 2, 12).setTexture(sideIcon).setTextureSides(new Object[] { this.bottom, this.top1 });
/*  48: 44 */     boxes.addBoxI(4, 0, 1, 12, 2, 15).setTexture(sideIcon).setTextureSides(new Object[] { this.bottom, this.top1 });
/*  49: 45 */     boxes.addBoxI(4, 2, 4, 12, 4, 12).setTexture(sideIcon).setTextureSides(new Object[] { this.bottom, this.top1 });
/*  50: 46 */     boxes.addBoxI(5, 4, 5, 11, 6, 11).setTexture(sideIcon).setTextureSides(new Object[] { this.bottom, this.top1 });
/*  51: 47 */     boxes.addBoxI(6, 6, 6, 10, 16, 10).setTexture(sideIcon).setTextureSides(new Object[] { this.bottom, this.top1 });
/*  52:    */     
/*  53: 49 */     IIcon top2Icon = disabled ? this.top2_disabled : this.top2;
/*  54: 50 */     boxes.addBoxI(6, 10, 1, 10, 14, 15).setTexture(sideIcon).setTextureSides(new Object[] { top2Icon, top2Icon });
/*  55: 51 */     boxes.addBoxI(1, 10, 6, 15, 14, 10).setTexture(sideIcon).setTextureSides(new Object[] { top2Icon, top2Icon });
/*  56:    */     
/*  57: 53 */     boxes.rotateToSideTex(ForgeDirection.getOrientation(metadata % 6));
/*  58: 54 */     return boxes;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
/*  62:    */   {
/*  63: 59 */     TileEntity tileEntity = world.getTileEntity(x, y, z);
/*  64: 60 */     if ((tileEntity instanceof TileEnderCollector)) {
/*  65: 61 */       ((TileEnderCollector)tileEntity).onNeighbourChange();
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void registerBlockIcons(IIconRegister register)
/*  70:    */   {
/*  71: 67 */     this.side = register.registerIcon("extrautils:enderCollectorSide");
/*  72: 68 */     this.side_disabled = register.registerIcon("extrautils:enderCollectorSide_disabled");
/*  73: 69 */     this.blockIcon = (this.bottom = register.registerIcon("extrautils:enderCollectorBottom"));
/*  74: 70 */     this.top1 = register.registerIcon("extrautils:enderCollectorTop1");
/*  75: 71 */     this.top2 = register.registerIcon("extrautils:enderCollectorTop2");
/*  76: 72 */     this.top2_disabled = register.registerIcon("extrautils:enderCollectorTop2_disabled");
/*  77:    */   }
/*  78:    */   
/*  79:    */   public boolean hasTileEntity(int metadata)
/*  80:    */   {
/*  81: 77 */     return true;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public TileEntity createTileEntity(World world, int metadata)
/*  85:    */   {
/*  86: 82 */     return new TileEnderCollector();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int onBlockPlaced(World world, int x, int y, int z, int side, float p_149660_6_, float p_149660_7_, float p_149660_8_, int meta)
/*  90:    */   {
/*  91: 87 */     return side ^ 0x1;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
/*  95:    */   {
/*  96: 92 */     TileEntity tileEntity = world.getTileEntity(x, y, z);
/*  97: 93 */     if ((tileEntity instanceof TileEnderCollector)) {
/*  98: 94 */       return ((TileEnderCollector)tileEntity).onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
/*  99:    */     }
/* 100: 96 */     return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void breakBlock(World world, int x, int y, int z, Block p_149749_5_, int p_149749_6_)
/* 104:    */   {
/* 105:101 */     TileEntity tileEntity = world.getTileEntity(x, y, z);
/* 106:102 */     if ((tileEntity instanceof TileEnderCollector)) {
/* 107:103 */       ((TileEnderCollector)tileEntity).dropItems();
/* 108:    */     }
/* 109:106 */     super.breakBlock(world, x, y, z, p_149749_5_, p_149749_6_);
/* 110:    */   }
/* 111:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.endercollector.BlockEnderCollector
 * JD-Core Version:    0.7.0.1
 */