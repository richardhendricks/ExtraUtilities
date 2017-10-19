/*  1:   */ package com.rwtema.extrautils.tileentity.enderquarry;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtils;
/*  4:   */ import com.rwtema.extrautils.block.BlockMultiBlock;
/*  5:   */ import com.rwtema.extrautils.block.Box;
/*  6:   */ import com.rwtema.extrautils.block.BoxModel;
/*  7:   */ import cpw.mods.fml.relauncher.Side;
/*  8:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  9:   */ import java.util.Random;
/* 10:   */ import net.minecraft.block.material.Material;
/* 11:   */ import net.minecraft.entity.player.EntityPlayer;
/* 12:   */ import net.minecraft.tileentity.TileEntity;
/* 13:   */ import net.minecraft.util.AxisAlignedBB;
/* 14:   */ import net.minecraft.world.IBlockAccess;
/* 15:   */ import net.minecraft.world.World;
/* 16:   */ import net.minecraftforge.common.util.ForgeDirection;
/* 17:   */ 
/* 18:   */ public class BlockEnderMarkers
/* 19:   */   extends BlockMultiBlock
/* 20:   */ {
/* 21:19 */   public static int[] dx = { 0, 0, 1, -1 };
/* 22:20 */   public static int[] dz = { 1, -1, 0, 0 };
/* 23:   */   
/* 24:   */   public BlockEnderMarkers()
/* 25:   */   {
/* 26:23 */     super(Material.circuits);
/* 27:24 */     setBlockName("extrautils:endMarker");
/* 28:25 */     setBlockTextureName("extrautils:endMarker");
/* 29:26 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/* 30:27 */     setStepSound(soundTypeStone);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
/* 34:   */   {
/* 35:31 */     return null;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public boolean isOpaqueCube()
/* 39:   */   {
/* 40:35 */     return false;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public boolean renderAsNormalBlock()
/* 44:   */   {
/* 45:39 */     return false;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/* 49:   */   {
/* 50:43 */     return true;
/* 51:   */   }
/* 52:   */   
/* 53:   */   @SideOnly(Side.CLIENT)
/* 54:   */   public void randomDisplayTick(World world, int x, int y, int z, Random rand)
/* 55:   */   {
/* 56:48 */     int meta = world.getBlockMetadata(x, y, z);
/* 57:50 */     for (int i = 0; i < 4; i++) {
/* 58:51 */       if ((meta & 1 << i) != 0) {
/* 59:52 */         for (int l = 0; l < 3; l++) {
/* 60:53 */           world.spawnParticle("reddust", x + 0.5D + dx[i] * rand.nextDouble() * rand.nextDouble(), y + 0.5D, z + 0.5D + dz[i] * rand.nextDouble() * rand.nextDouble(), 0.501D, 0.0D, 1.0D);
/* 61:   */         }
/* 62:   */       }
/* 63:   */     }
/* 64:   */   }
/* 65:   */   
/* 66:   */   public boolean hasTileEntity(int metadata)
/* 67:   */   {
/* 68:60 */     return true;
/* 69:   */   }
/* 70:   */   
/* 71:   */   public TileEntity createTileEntity(World world, int metadata)
/* 72:   */   {
/* 73:64 */     return new TileEntityEnderMarker();
/* 74:   */   }
/* 75:   */   
/* 76:   */   public void prepareForRender(String label) {}
/* 77:   */   
/* 78:   */   public BoxModel getWorldModel(IBlockAccess world, int x, int y, int z)
/* 79:   */   {
/* 80:74 */     BoxModel model = new BoxModel();
/* 81:75 */     model.addBoxI(7, 0, 7, 9, 13, 9).fillIcons(this, 0);
/* 82:77 */     for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS) {
/* 83:78 */       if (world.isSideSolid(x + d.offsetX, y + d.offsetY, z + d.offsetZ, d.getOpposite(), false))
/* 84:   */       {
/* 85:79 */         model.rotateToSideTex(d);
/* 86:80 */         return model;
/* 87:   */       }
/* 88:   */     }
/* 89:85 */     return model;
/* 90:   */   }
/* 91:   */   
/* 92:   */   public BoxModel getInventoryModel(int metadata)
/* 93:   */   {
/* 94:90 */     BoxModel model = new BoxModel();
/* 95:91 */     model.addBoxI(7, 0, 7, 9, 13, 9);
/* 96:92 */     return model;
/* 97:   */   }
/* 98:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.enderquarry.BlockEnderMarkers
 * JD-Core Version:    0.7.0.1
 */