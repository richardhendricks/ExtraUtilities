/*   1:    */ package com.rwtema.extrautils.block;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.tileentity.TileEntityEnderThermicLavaPump;
/*   5:    */ import cpw.mods.fml.relauncher.Side;
/*   6:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   7:    */ import java.util.List;
/*   8:    */ import net.minecraft.block.Block;
/*   9:    */ import net.minecraft.block.material.Material;
/*  10:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  11:    */ import net.minecraft.creativetab.CreativeTabs;
/*  12:    */ import net.minecraft.entity.EntityLivingBase;
/*  13:    */ import net.minecraft.entity.player.EntityPlayer;
/*  14:    */ import net.minecraft.item.Item;
/*  15:    */ import net.minecraft.item.ItemStack;
/*  16:    */ import net.minecraft.tileentity.TileEntity;
/*  17:    */ import net.minecraft.util.IIcon;
/*  18:    */ import net.minecraft.world.World;
/*  19:    */ 
/*  20:    */ public class BlockEnderthermicPump
/*  21:    */   extends Block
/*  22:    */ {
/*  23:    */   IIcon pump;
/*  24:    */   IIcon pumpTop;
/*  25:    */   IIcon pumpBottom;
/*  26:    */   
/*  27:    */   public BlockEnderthermicPump()
/*  28:    */   {
/*  29: 27 */     super(Material.rock);
/*  30: 28 */     setBlockName("extrautils:enderThermicPump");
/*  31: 29 */     setBlockTextureName("extrautils:enderThermicPump");
/*  32: 30 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  33: 31 */     setHardness(1.0F);
/*  34: 32 */     setStepSound(soundTypeStone);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public boolean hasTileEntity(int metadata)
/*  38:    */   {
/*  39: 37 */     return true;
/*  40:    */   }
/*  41:    */   
/*  42:    */   @SideOnly(Side.CLIENT)
/*  43:    */   public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/*  44:    */   {
/*  45: 46 */     par3List.add(new ItemStack(par1, 1, 0));
/*  46:    */   }
/*  47:    */   
/*  48:    */   @SideOnly(Side.CLIENT)
/*  49:    */   public void registerBlockIcons(IIconRegister par1IIconRegister)
/*  50:    */   {
/*  51: 56 */     this.pump = par1IIconRegister.registerIcon("extrautils:enderThermicPump_side");
/*  52: 57 */     this.pumpTop = par1IIconRegister.registerIcon("extrautils:enderThermicPump_top");
/*  53: 58 */     this.pumpBottom = par1IIconRegister.registerIcon("extrautils:enderThermicPump");
/*  54: 59 */     super.registerBlockIcons(par1IIconRegister);
/*  55:    */   }
/*  56:    */   
/*  57:    */   @SideOnly(Side.CLIENT)
/*  58:    */   public IIcon getIcon(int par1, int par2)
/*  59:    */   {
/*  60: 68 */     if (par1 == 0) {
/*  61: 69 */       return this.pumpBottom;
/*  62:    */     }
/*  63: 70 */     if (par1 == 1) {
/*  64: 71 */       return this.pumpTop;
/*  65:    */     }
/*  66: 73 */     return this.pump;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public TileEntity createTileEntity(World world, int metadata)
/*  70:    */   {
/*  71: 89 */     return new TileEntityEnderThermicLavaPump();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLiving, ItemStack par6ItemStack)
/*  75:    */   {
/*  76:129 */     TileEntity tile = par1World.getTileEntity(par2, par3, par4);
/*  77:131 */     if (((tile instanceof TileEntityEnderThermicLavaPump)) && ((par5EntityLiving instanceof EntityPlayer))) {
/*  78:132 */       ((TileEntityEnderThermicLavaPump)tile).owner = ((EntityPlayer)par5EntityLiving);
/*  79:    */     }
/*  80:    */   }
/*  81:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BlockEnderthermicPump
 * JD-Core Version:    0.7.0.1
 */