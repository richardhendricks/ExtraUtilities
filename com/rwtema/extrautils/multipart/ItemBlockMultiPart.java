/*  1:   */ package com.rwtema.extrautils.multipart;
/*  2:   */ 
/*  3:   */ import codechicken.lib.vec.BlockCoord;
/*  4:   */ import codechicken.multipart.TMultiPart;
/*  5:   */ import codechicken.multipart.TileMultipart;
/*  6:   */ import com.rwtema.extrautils.item.ItemBlockMetadata;
/*  7:   */ import cpw.mods.fml.relauncher.Side;
/*  8:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  9:   */ import net.minecraft.block.Block;
/* 10:   */ import net.minecraft.block.Block.SoundType;
/* 11:   */ import net.minecraft.entity.player.EntityPlayer;
/* 12:   */ import net.minecraft.init.Blocks;
/* 13:   */ import net.minecraft.item.ItemStack;
/* 14:   */ import net.minecraft.world.World;
/* 15:   */ 
/* 16:   */ public class ItemBlockMultiPart
/* 17:   */   extends ItemBlockMetadata
/* 18:   */ {
/* 19:   */   public ItemBlockMultiPart(Block par1)
/* 20:   */   {
/* 21:18 */     super(par1);
/* 22:   */   }
/* 23:   */   
/* 24:   */   @SideOnly(Side.CLIENT)
/* 25:   */   public boolean func_150936_a(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer, ItemStack par7ItemStack)
/* 26:   */   {
/* 27:27 */     return (tryPlaceMultiPart(par1World, new BlockCoord(par2, par3, par4).offset(par5), par7ItemStack, par5, false)) || (super.func_150936_a(par1World, par2, par3, par4, par5, par6EntityPlayer, par7ItemStack));
/* 28:   */   }
/* 29:   */   
/* 30:   */   public TMultiPart createMultiPart(World world, BlockCoord pos, ItemStack item, int side)
/* 31:   */   {
/* 32:32 */     return null;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public boolean tryPlaceMultiPart(World world, BlockCoord pos, ItemStack item, int side, boolean doPlace)
/* 36:   */   {
/* 37:36 */     TileMultipart tile = TileMultipart.getOrConvertTile(world, pos);
/* 38:38 */     if (tile == null) {
/* 39:39 */       return false;
/* 40:   */     }
/* 41:42 */     TMultiPart part = createMultiPart(world, pos, item, side);
/* 42:44 */     if (part == null) {
/* 43:45 */       return false;
/* 44:   */     }
/* 45:48 */     if (tile.canAddPart(part))
/* 46:   */     {
/* 47:49 */       if (doPlace) {
/* 48:50 */         TileMultipart.addPart(world, pos, part);
/* 49:   */       }
/* 50:53 */       return true;
/* 51:   */     }
/* 52:56 */     return false;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
/* 56:   */   {
/* 57:61 */     Block block = par3World.getBlock(par4, par5, par6);
/* 58:63 */     if ((par8 != 0.0F) && (par9 != 0.0F) && (par10 != 0.0F) && (par8 != 1.0F) && (par9 != 1.0F) && (par10 != 1.0F))
/* 59:   */     {
/* 60:64 */       BlockCoord pos = new BlockCoord(par4, par5, par6);
/* 61:66 */       if (tryPlaceMultiPart(par3World, pos, par1ItemStack, par7, !par3World.isClient))
/* 62:   */       {
/* 63:68 */         par3World.playSoundEffect(pos.x + 0.5F, pos.y + 0.5F, pos.z + 0.5F, block.stepSound.func_150496_b(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getFrequency() * 0.8F);
/* 64:69 */         par1ItemStack.stackSize -= 1;
/* 65:70 */         return true;
/* 66:   */       }
/* 67:   */     }
/* 68:75 */     if (((block != Blocks.snow) || ((par3World.getBlockMetadata(par4, par5, par6) & 0x7) >= 1)) && 
/* 69:76 */       (block != Blocks.vine) && (block != Blocks.tallgrass) && (block != Blocks.deadbush) && (block.isReplaceable(par3World, par4, par5, par6)))
/* 70:   */     {
/* 71:77 */       BlockCoord pos = new BlockCoord(par4, par5, par6).offset(par7);
/* 72:79 */       if (tryPlaceMultiPart(par3World, pos, par1ItemStack, par7, !par3World.isClient))
/* 73:   */       {
/* 74:80 */         par3World.playSoundEffect(pos.x + 0.5F, pos.y + 0.5F, pos.z + 0.5F, block.stepSound.func_150496_b(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getFrequency() * 0.8F);
/* 75:81 */         par1ItemStack.stackSize -= 1;
/* 76:82 */         return true;
/* 77:   */       }
/* 78:   */     }
/* 79:86 */     return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
/* 80:   */   }
/* 81:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.multipart.ItemBlockMultiPart
 * JD-Core Version:    0.7.0.1
 */