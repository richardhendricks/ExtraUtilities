/*   1:    */ package com.rwtema.extrautils.block;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   5:    */ import com.rwtema.extrautils.network.packets.PacketTempChat;
/*   6:    */ import com.rwtema.extrautils.tileentity.TileEntityRainMuffler;
/*   7:    */ import com.rwtema.extrautils.tileentity.TileEntitySoundMuffler;
/*   8:    */ import cpw.mods.fml.relauncher.Side;
/*   9:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  10:    */ import java.util.List;
/*  11:    */ import net.minecraft.block.Block;
/*  12:    */ import net.minecraft.block.material.Material;
/*  13:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  14:    */ import net.minecraft.creativetab.CreativeTabs;
/*  15:    */ import net.minecraft.entity.player.EntityPlayer;
/*  16:    */ import net.minecraft.item.Item;
/*  17:    */ import net.minecraft.item.ItemStack;
/*  18:    */ import net.minecraft.nbt.NBTTagCompound;
/*  19:    */ import net.minecraft.tileentity.TileEntity;
/*  20:    */ import net.minecraft.util.IIcon;
/*  21:    */ import net.minecraft.world.World;
/*  22:    */ 
/*  23:    */ public class BlockSoundMuffler
/*  24:    */   extends Block
/*  25:    */ {
/*  26:    */   private IIcon rainIcon;
/*  27:    */   private IIcon rainOnIcon;
/*  28:    */   
/*  29:    */   public BlockSoundMuffler()
/*  30:    */   {
/*  31: 29 */     super(Material.cloth);
/*  32: 30 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  33: 31 */     setHardness(0.8F);
/*  34: 32 */     setStepSound(soundTypeCloth);
/*  35: 33 */     setBlockName("extrautils:sound_muffler");
/*  36: 34 */     setBlockTextureName("extrautils:sound_muffler");
/*  37:    */   }
/*  38:    */   
/*  39:    */   @SideOnly(Side.CLIENT)
/*  40:    */   public void registerBlockIcons(IIconRegister par1IIconRegister)
/*  41:    */   {
/*  42: 40 */     this.blockIcon = par1IIconRegister.registerIcon("extrautils:sound_muffler");
/*  43: 41 */     this.rainIcon = par1IIconRegister.registerIcon("extrautils:rain_muffler");
/*  44:    */   }
/*  45:    */   
/*  46:    */   @SideOnly(Side.CLIENT)
/*  47:    */   public IIcon getIcon(int par1, int par2)
/*  48:    */   {
/*  49: 47 */     if (par2 == 1) {
/*  50: 48 */       return this.rainIcon;
/*  51:    */     }
/*  52: 51 */     return this.blockIcon;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int damageDropped(int par1)
/*  56:    */   {
/*  57: 56 */     return par1;
/*  58:    */   }
/*  59:    */   
/*  60:    */   @SideOnly(Side.CLIENT)
/*  61:    */   public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/*  62:    */   {
/*  63: 62 */     par3List.add(new ItemStack(par1, 1, 0));
/*  64: 63 */     par3List.add(new ItemStack(par1, 1, 1));
/*  65:    */   }
/*  66:    */   
/*  67:    */   public boolean hasTileEntity(int metadata)
/*  68:    */   {
/*  69: 68 */     return true;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public TileEntity createTileEntity(World world, int metadata)
/*  73:    */   {
/*  74: 73 */     if (metadata == 1) {
/*  75: 74 */       return new TileEntityRainMuffler();
/*  76:    */     }
/*  77: 76 */     return new TileEntitySoundMuffler();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
/*  81:    */   {
/*  82: 82 */     if ((world.getBlockMetadata(x, y, z) == 1) && 
/*  83: 83 */       (!XUHelper.isPlayerFake(player)))
/*  84:    */     {
/*  85: 84 */       NBTTagCompound tags = new NBTTagCompound();
/*  86: 86 */       if (player.getEntityData().hasKey("PlayerPersisted")) {
/*  87: 87 */         tags = player.getEntityData().getCompoundTag("PlayerPersisted");
/*  88:    */       } else {
/*  89: 89 */         player.getEntityData().setTag("PlayerPersisted", tags);
/*  90:    */       }
/*  91: 92 */       if (tags.hasKey("ExtraUtilities|Rain"))
/*  92:    */       {
/*  93: 93 */         if (tags.getBoolean("ExtraUtilities|Rain"))
/*  94:    */         {
/*  95: 94 */           tags.setBoolean("ExtraUtilities|Rain", false);
/*  96: 96 */           if (world.isClient) {
/*  97: 97 */             PacketTempChat.sendChat(player, "You remove the magic wool from your ears");
/*  98:    */           }
/*  99:    */         }
/* 100:    */         else
/* 101:    */         {
/* 102:100 */           tags.setBoolean("ExtraUtilities|Rain", true);
/* 103:102 */           if (world.isClient) {
/* 104:103 */             PacketTempChat.sendChat(player, "You place some magic wool in your ears");
/* 105:    */           }
/* 106:    */         }
/* 107:    */       }
/* 108:    */       else
/* 109:    */       {
/* 110:107 */         tags.setBoolean("ExtraUtilities|Rain", true);
/* 111:109 */         if (world.isClient) {
/* 112:110 */           PacketTempChat.sendChat(player, "You place some magic wool in your ears");
/* 113:    */         }
/* 114:    */       }
/* 115:114 */       return true;
/* 116:    */     }
/* 117:118 */     return false;
/* 118:    */   }
/* 119:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BlockSoundMuffler
 * JD-Core Version:    0.7.0.1
 */