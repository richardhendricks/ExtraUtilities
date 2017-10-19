/*  1:   */ package com.rwtema.extrautils.block;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtils;
/*  4:   */ import com.rwtema.extrautils.network.NetworkHandler;
/*  5:   */ import com.rwtema.extrautils.network.XUPacketBase;
/*  6:   */ import com.rwtema.extrautils.network.packets.PacketTempChat;
/*  7:   */ import com.rwtema.extrautils.tileentity.TileEntityTradingPost;
/*  8:   */ import cpw.mods.fml.relauncher.Side;
/*  9:   */ import cpw.mods.fml.relauncher.SideOnly;
/* 10:   */ import net.minecraft.block.Block;
/* 11:   */ import net.minecraft.block.BlockContainer;
/* 12:   */ import net.minecraft.block.material.Material;
/* 13:   */ import net.minecraft.client.renderer.texture.IIconRegister;
/* 14:   */ import net.minecraft.entity.player.EntityPlayer;
/* 15:   */ import net.minecraft.tileentity.TileEntity;
/* 16:   */ import net.minecraft.util.IIcon;
/* 17:   */ import net.minecraft.world.World;
/* 18:   */ 
/* 19:   */ public class BlockTradingPost
/* 20:   */   extends BlockContainer
/* 21:   */ {
/* 22:19 */   private IIcon[] icons = new IIcon[3];
/* 23:   */   
/* 24:   */   public BlockTradingPost()
/* 25:   */   {
/* 26:22 */     super(Material.wood);
/* 27:23 */     setBlockName("extrautils:trading_post");
/* 28:24 */     setBlockTextureName("extrautils:trading_post");
/* 29:25 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/* 30:26 */     setHardness(1.0F);
/* 31:27 */     setResistance(10.0F).setStepSound(soundTypeWood);
/* 32:   */   }
/* 33:   */   
/* 34:   */   @SideOnly(Side.CLIENT)
/* 35:   */   public void registerBlockIcons(IIconRegister par1IIconRegister)
/* 36:   */   {
/* 37:33 */     this.icons[0] = par1IIconRegister.registerIcon("planks");
/* 38:34 */     this.icons[1] = par1IIconRegister.registerIcon("extrautils:trading_post_top");
/* 39:   */     
/* 40:   */ 
/* 41:37 */     this.icons[2] = par1IIconRegister.registerIcon("extrautils:trading_post_side");
/* 42:   */   }
/* 43:   */   
/* 44:   */   @SideOnly(Side.CLIENT)
/* 45:   */   public IIcon getIcon(int par1, int par2)
/* 46:   */   {
/* 47:46 */     if (par1 <= 1) {
/* 48:47 */       return this.icons[par1];
/* 49:   */     }
/* 50:50 */     return this.icons[2];
/* 51:   */   }
/* 52:   */   
/* 53:   */   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
/* 54:   */   {
/* 55:67 */     if (!world.isClient)
/* 56:   */     {
/* 57:68 */       if ((world.getTileEntity(x, y, z) instanceof TileEntityTradingPost))
/* 58:   */       {
/* 59:69 */         XUPacketBase packet = ((TileEntityTradingPost)world.getTileEntity(x, y, z)).getTradePacket(player);
/* 60:71 */         if (packet != null) {
/* 61:72 */           NetworkHandler.sendPacketToPlayer(packet, player);
/* 62:   */         } else {
/* 63:74 */           PacketTempChat.sendChat(player, "No villagers found in range");
/* 64:   */         }
/* 65:77 */         return true;
/* 66:   */       }
/* 67:   */     }
/* 68:   */     else {
/* 69:80 */       return true;
/* 70:   */     }
/* 71:83 */     return false;
/* 72:   */   }
/* 73:   */   
/* 74:   */   public TileEntity createNewTileEntity(World var1, int var2)
/* 75:   */   {
/* 76:88 */     return new TileEntityTradingPost();
/* 77:   */   }
/* 78:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BlockTradingPost
 * JD-Core Version:    0.7.0.1
 */