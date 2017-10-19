/*  1:   */ package com.rwtema.extrautils.tileentity.enderquarry;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtils;
/*  4:   */ import cpw.mods.fml.relauncher.Side;
/*  5:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  6:   */ import net.minecraft.block.Block;
/*  7:   */ import net.minecraft.block.material.Material;
/*  8:   */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  9:   */ import net.minecraft.entity.player.EntityPlayer;
/* 10:   */ import net.minecraft.entity.player.PlayerCapabilities;
/* 11:   */ import net.minecraft.tileentity.TileEntity;
/* 12:   */ import net.minecraft.util.ChatComponentText;
/* 13:   */ import net.minecraft.util.IIcon;
/* 14:   */ import net.minecraft.world.World;
/* 15:   */ 
/* 16:   */ public class BlockEnderQuarry
/* 17:   */   extends Block
/* 18:   */ {
/* 19:16 */   int[] tiletype = { 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };
/* 20:17 */   IIcon[] top = new IIcon[3];
/* 21:17 */   IIcon[] bottom = new IIcon[3];
/* 22:17 */   IIcon[] side = new IIcon[3];
/* 23:   */   
/* 24:   */   public BlockEnderQuarry()
/* 25:   */   {
/* 26:20 */     super(Material.rock);
/* 27:21 */     setBlockName("extrautils:enderQuarry");
/* 28:22 */     setBlockTextureName("extrautils:enderQuarry");
/* 29:23 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/* 30:24 */     setHardness(1.0F);
/* 31:25 */     setStepSound(soundTypeStone);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public boolean hasTileEntity(int metadata)
/* 35:   */   {
/* 36:30 */     return true;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public TileEntity createTileEntity(World world, int metadata)
/* 40:   */   {
/* 41:35 */     return new TileEntityEnderQuarry();
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block par5)
/* 45:   */   {
/* 46:   */     TileEntity tile;
/* 47:42 */     if (((tile = par1World.getTileEntity(par2, par3, par4)) instanceof TileEntityEnderQuarry)) {
/* 48:43 */       ((TileEntityEnderQuarry)tile).detectInventories();
/* 49:   */     }
/* 50:   */   }
/* 51:   */   
/* 52:   */   public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
/* 53:   */   {
/* 54:49 */     if (par1World.isClient) {
/* 55:50 */       return true;
/* 56:   */     }
/* 57:   */     TileEntity tile;
/* 58:55 */     if (((tile = par1World.getTileEntity(par2, par3, par4)) instanceof TileEntityEnderQuarry))
/* 59:   */     {
/* 60:56 */       ((TileEntityEnderQuarry)tile).startFencing(par5EntityPlayer);
/* 61:58 */       if ((par5EntityPlayer.getHeldItem() == null) && (par5EntityPlayer.capabilities.isCreativeMode) && (par5EntityPlayer.isSneaking()) && (((TileEntityEnderQuarry)tile).started))
/* 62:   */       {
/* 63:59 */         par5EntityPlayer.addChatComponentMessage(new ChatComponentText("Overclock Mode Activated"));
/* 64:60 */         ((TileEntityEnderQuarry)tile).debug();
/* 65:   */       }
/* 66:   */     }
/* 67:65 */     return true;
/* 68:   */   }
/* 69:   */   
/* 70:   */   @SideOnly(Side.CLIENT)
/* 71:   */   public void registerBlockIcons(IIconRegister par1IIconRegister)
/* 72:   */   {
/* 73:71 */     IIcon tmp18_13 = par1IIconRegister.registerIcon("extrautils:enderQuarry_top");this.top[2] = tmp18_13;this.top[0] = tmp18_13;
/* 74:72 */     this.top[1] = par1IIconRegister.registerIcon("extrautils:enderQuarry_top_active"); IIcon 
/* 75:73 */       tmp60_59 = (this.bottom[2] =  = par1IIconRegister.registerIcon("extrautils:enderQuarry_bottom"));this.bottom[1] = tmp60_59;this.bottom[0] = tmp60_59; IIcon 
/* 76:74 */       tmp77_72 = par1IIconRegister.registerIcon("extrautils:enderQuarry");this.blockIcon = tmp77_72;this.side[0] = tmp77_72;
/* 77:75 */     this.side[1] = par1IIconRegister.registerIcon("extrautils:enderQuarry_active");
/* 78:76 */     this.side[2] = par1IIconRegister.registerIcon("extrautils:enderQuarry_finished");
/* 79:   */   }
/* 80:   */   
/* 81:   */   @SideOnly(Side.CLIENT)
/* 82:   */   public IIcon getIcon(int par1, int par2)
/* 83:   */   {
/* 84:82 */     if ((par2 > 2) || (par2 < 0)) {
/* 85:83 */       par2 = 0;
/* 86:   */     }
/* 87:86 */     if (par1 == 0) {
/* 88:87 */       return this.bottom[par2];
/* 89:   */     }
/* 90:88 */     if (par1 == 1) {
/* 91:89 */       return this.top[par2];
/* 92:   */     }
/* 93:91 */     return this.side[par2];
/* 94:   */   }
/* 95:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.enderquarry.BlockEnderQuarry
 * JD-Core Version:    0.7.0.1
 */