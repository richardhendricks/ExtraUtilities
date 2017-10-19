/*  1:   */ package com.rwtema.extrautils.tileentity.transfernodes;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.relauncher.Side;
/*  4:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  5:   */ import java.util.List;
/*  6:   */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  7:   */ import net.minecraft.creativetab.CreativeTabs;
/*  8:   */ import net.minecraft.item.Item;
/*  9:   */ import net.minecraft.item.ItemStack;
/* 10:   */ import net.minecraft.tileentity.TileEntity;
/* 11:   */ import net.minecraft.util.IIcon;
/* 12:   */ import net.minecraft.world.World;
/* 13:   */ 
/* 14:   */ public class BlockRetrievalNode
/* 15:   */   extends BlockTransferNode
/* 16:   */ {
/* 17:   */   public static IIcon nodeSideLiquidRemote;
/* 18:   */   public static IIcon nodeSideExtractRemote;
/* 19:   */   
/* 20:   */   public BlockRetrievalNode()
/* 21:   */   {
/* 22:21 */     setBlockName("extrautils:extractor_base_remote");
/* 23:22 */     setBlockTextureName("extrautils:extractor_base");
/* 24:   */   }
/* 25:   */   
/* 26:   */   @SideOnly(Side.CLIENT)
/* 27:   */   public void registerBlockIcons(IIconRegister par1IIconRegister)
/* 28:   */   {
/* 29:28 */     nodeSideLiquidRemote = par1IIconRegister.registerIcon("extrautils:extractor_liquid_remote");
/* 30:29 */     nodeSideExtractRemote = par1IIconRegister.registerIcon("extrautils:extractor_extract_remote");
/* 31:   */   }
/* 32:   */   
/* 33:   */   @SideOnly(Side.CLIENT)
/* 34:   */   public IIcon getIcon(int par1, int par2)
/* 35:   */   {
/* 36:38 */     if (par2 < 6) {
/* 37:39 */       return par1 == par2 % 6 ? nodeBase : nodeSideExtractRemote;
/* 38:   */     }
/* 39:40 */     if (par2 < 12) {
/* 40:41 */       return par1 == par2 % 6 ? nodeBase : nodeSideLiquidRemote;
/* 41:   */     }
/* 42:43 */     return nodeSideEnergy;
/* 43:   */   }
/* 44:   */   
/* 45:   */   @SideOnly(Side.CLIENT)
/* 46:   */   public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/* 47:   */   {
/* 48:53 */     par3List.add(new ItemStack(par1, 1, 0));
/* 49:54 */     par3List.add(new ItemStack(par1, 1, 6));
/* 50:   */   }
/* 51:   */   
/* 52:   */   public boolean hasTileEntity(int metadata)
/* 53:   */   {
/* 54:70 */     return true;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public TileEntity createTileEntity(World world, int metadata)
/* 58:   */   {
/* 59:85 */     if (metadata == 12) {
/* 60:86 */       return new TileEntityTransferNodeEnergy();
/* 61:   */     }
/* 62:87 */     if ((metadata >= 6) && (metadata < 12)) {
/* 63:88 */       return new TileEntityRetrievalNodeLiquid();
/* 64:   */     }
/* 65:90 */     return new TileEntityRetrievalNodeInventory();
/* 66:   */   }
/* 67:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.BlockRetrievalNode
 * JD-Core Version:    0.7.0.1
 */