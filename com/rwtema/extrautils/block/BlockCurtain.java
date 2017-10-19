/*   1:    */ package com.rwtema.extrautils.block;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import cpw.mods.fml.relauncher.Side;
/*   5:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   6:    */ import java.util.List;
/*   7:    */ import net.minecraft.block.Block;
/*   8:    */ import net.minecraft.block.material.Material;
/*   9:    */ import net.minecraft.creativetab.CreativeTabs;
/*  10:    */ import net.minecraft.entity.Entity;
/*  11:    */ import net.minecraft.util.AxisAlignedBB;
/*  12:    */ import net.minecraft.world.IBlockAccess;
/*  13:    */ import net.minecraft.world.World;
/*  14:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  15:    */ 
/*  16:    */ public class BlockCurtain
/*  17:    */   extends BlockMultiBlock
/*  18:    */ {
/*  19:    */   public BlockCurtain()
/*  20:    */   {
/*  21: 20 */     super(Material.cloth);
/*  22: 21 */     setCreativeTab(CreativeTabs.tabBlock);
/*  23: 22 */     setLightOpacity(8);
/*  24: 23 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  25: 24 */     setBlockName("extrautils:curtains");
/*  26: 25 */     setBlockTextureName("extrautils:curtains");
/*  27:    */   }
/*  28:    */   
/*  29:    */   @SideOnly(Side.CLIENT)
/*  30:    */   public String getItemIconName()
/*  31:    */   {
/*  32: 34 */     return "extrautils:curtains";
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void prepareForRender(String label) {}
/*  36:    */   
/*  37:    */   public BoxModel getClothModel(IBlockAccess world, int x, int y, int z, float width)
/*  38:    */   {
/*  39:121 */     BoxModel model = new BoxModel();
/*  40:123 */     for (int i = 2; i < 6; i++)
/*  41:    */     {
/*  42:124 */       ForgeDirection dir = ForgeDirection.getOrientation(i);
/*  43:125 */       Block id = world.getBlock(x + dir.offsetX, y, z + dir.offsetZ);
/*  44:127 */       if ((id == this) || (id.isOpaqueCube())) {
/*  45:128 */         model.add(new Box(0.0F, 0.0F, 0.5F - width, 1.0F, 0.5F + width, 0.5F + width).rotateToSide(dir));
/*  46:    */       }
/*  47:    */     }
/*  48:132 */     if (model.isEmpty())
/*  49:    */     {
/*  50:133 */       model.add(new Box(0.0F, 0.0F, 0.5F - width, 1.0F, 1.0F, 0.5F + width));
/*  51:134 */       model.add(new Box(0.5F - width, 0.0F, 0.0F, 0.5F + width, 1.0F, 1.0F));
/*  52:    */     }
/*  53:137 */     return model;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public BoxModel getWorldModel(IBlockAccess world, int x, int y, int z)
/*  57:    */   {
/*  58:142 */     float width = 0.025F;
/*  59:143 */     return getClothModel(world, x, y, z, width);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public BoxModel getInventoryModel(int metadata)
/*  63:    */   {
/*  64:148 */     return new BoxModel(0.0F, 0.0F, 0.0F, 0.05F, 1.0F, 1.0F);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity) {}
/*  68:    */   
/*  69:    */   @SideOnly(Side.CLIENT)
/*  70:    */   public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
/*  71:    */   {
/*  72:166 */     Box b = BoxModel.boundingBox(getClothModel(par1World, par2, par3, par4, 0.1875F));
/*  73:167 */     return AxisAlignedBB.getBoundingBox(par2 + b.minX, par3 + b.minY, par4 + b.minZ, par2 + b.maxX, par3 + b.maxY, par4 + b.maxZ);
/*  74:    */   }
/*  75:    */   
/*  76:    */   @SideOnly(Side.CLIENT)
/*  77:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
/*  78:    */   {
/*  79:177 */     return getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
/*  80:    */   }
/*  81:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BlockCurtain
 * JD-Core Version:    0.7.0.1
 */