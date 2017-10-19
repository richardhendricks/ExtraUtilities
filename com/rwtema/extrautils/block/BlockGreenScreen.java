/*   1:    */ package com.rwtema.extrautils.block;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*   5:    */ import cpw.mods.fml.relauncher.Side;
/*   6:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   7:    */ import java.util.List;
/*   8:    */ import net.minecraft.block.Block;
/*   9:    */ import net.minecraft.block.material.Material;
/*  10:    */ import net.minecraft.creativetab.CreativeTabs;
/*  11:    */ import net.minecraft.item.Item;
/*  12:    */ import net.minecraft.item.ItemStack;
/*  13:    */ import net.minecraft.world.IBlockAccess;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ 
/*  16:    */ public class BlockGreenScreen
/*  17:    */   extends Block
/*  18:    */ {
/*  19:    */   public BlockGreenScreen()
/*  20:    */   {
/*  21: 19 */     super(Material.cloth);
/*  22: 20 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  23: 21 */     setBlockName("extrautils:greenscreen");
/*  24: 22 */     setBlockTextureName("extrautils:greenscreen");
/*  25: 23 */     setLightOpacity(0);
/*  26: 24 */     setHardness(1.0F);
/*  27: 25 */     setResistance(10.0F);
/*  28:    */   }
/*  29:    */   
/*  30: 28 */   private static final float[][] cols = { { 1.0F, 1.0F, 1.0F }, { 1.0F, 0.5F, 0.0F }, { 1.0F, 0.0F, 1.0F }, { 0.0F, 0.5F, 0.85F }, { 1.0F, 1.0F, 0.0F }, { 0.0F, 1.0F, 0.0F }, { 1.0F, 0.6F, 0.65F }, { 0.5F, 0.5F, 0.5F }, { 0.8F, 0.8F, 0.8F }, { 0.0F, 1.0F, 1.0F }, { 0.7F, 0.2F, 1.0F }, { 0.0F, 0.0F, 1.0F }, { 0.5F, 0.2F, 0.0F }, { 0.0F, 0.6F, 0.0F }, { 1.0F, 0.0F, 0.0F }, { 0.0F, 0.0F, 0.0F } };
/*  31:    */   
/*  32:    */   public static int getLightLevel(int metadata)
/*  33:    */   {
/*  34: 51 */     return (int)((cols[metadata][0] + cols[metadata][1] + cols[metadata][2]) / 3.0F * 15.0F);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public int getLightValue(IBlockAccess world, int x, int y, int z)
/*  38:    */   {
/*  39: 56 */     if (((world instanceof World)) && 
/*  40: 57 */       (!((World)world).blockExists(x, y, z))) {
/*  41: 58 */       return 0;
/*  42:    */     }
/*  43: 61 */     return getLightLevel(world.getBlockMetadata(x, y, z));
/*  44:    */   }
/*  45:    */   
/*  46:    */   @SideOnly(Side.CLIENT)
/*  47:    */   public int getRenderColor(int p_149741_1_)
/*  48:    */   {
/*  49: 67 */     if (p_149741_1_ == 15) {
/*  50: 68 */       return 0;
/*  51:    */     }
/*  52: 70 */     float[] col = cols[(p_149741_1_ & 0xF)];
/*  53: 71 */     return (int)(col[0] * 255.0F) << 16 | (int)(col[1] * 255.0F) << 8 | (int)(col[2] * 255.0F);
/*  54:    */   }
/*  55:    */   
/*  56:    */   @SideOnly(Side.CLIENT)
/*  57:    */   public int colorMultiplier(IBlockAccess world, int x, int y, int z)
/*  58:    */   {
/*  59: 77 */     return getRenderColor(world.getBlockMetadata(x, y, z));
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int damageDropped(int p_149692_1_)
/*  63:    */   {
/*  64: 82 */     return p_149692_1_;
/*  65:    */   }
/*  66:    */   
/*  67:    */   @SideOnly(Side.CLIENT)
/*  68:    */   public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
/*  69:    */   {
/*  70: 88 */     for (int i = 0; i < 16; i++) {
/*  71: 89 */       p_149666_3_.add(new ItemStack(p_149666_1_, 1, i));
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   public boolean renderAsNormalBlock()
/*  76:    */   {
/*  77: 95 */     return false;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean isNormalCube(IBlockAccess world, int x, int y, int z)
/*  81:    */   {
/*  82: 99 */     return true;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getRenderType()
/*  86:    */   {
/*  87:104 */     return ExtraUtilsProxy.fullBrightBlockID;
/*  88:    */   }
/*  89:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BlockGreenScreen
 * JD-Core Version:    0.7.0.1
 */