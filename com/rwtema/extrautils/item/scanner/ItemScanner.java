/*  1:   */ package com.rwtema.extrautils.item.scanner;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtils;
/*  4:   */ import com.rwtema.extrautils.network.packets.PacketTempChatMultiline;
/*  5:   */ import java.util.List;
/*  6:   */ import net.minecraft.block.Block;
/*  7:   */ import net.minecraft.entity.Entity;
/*  8:   */ import net.minecraft.entity.EntityLivingBase;
/*  9:   */ import net.minecraft.entity.player.EntityPlayer;
/* 10:   */ import net.minecraft.item.Item;
/* 11:   */ import net.minecraft.item.ItemStack;
/* 12:   */ import net.minecraft.util.ChatComponentText;
/* 13:   */ import net.minecraft.world.World;
/* 14:   */ import net.minecraftforge.common.util.ForgeDirection;
/* 15:   */ 
/* 16:   */ public class ItemScanner
/* 17:   */   extends Item
/* 18:   */ {
/* 19:18 */   public static boolean scannerOut = false;
/* 20:   */   
/* 21:   */   public ItemScanner()
/* 22:   */   {
/* 23:22 */     setTextureName("extrautils:scanner");
/* 24:23 */     setUnlocalizedName("extrautils:scanner");
/* 25:24 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/* 26:25 */     setMaxStackSize(1);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
/* 30:   */   {
/* 31:30 */     scannerOut = true;
/* 32:31 */     super.onUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
/* 36:   */   {
/* 37:36 */     if (world.isClient) {
/* 38:37 */       return true;
/* 39:   */     }
/* 40:40 */     Block b = world.getBlock(x, y, z);
/* 41:   */     
/* 42:42 */     PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("~~~~~Scan~~~~~"));
/* 43:43 */     PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("Block name: " + b.getUnlocalizedName()));
/* 44:44 */     PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText("Block metadata: " + world.getBlockMetadata(x, y, z)));
/* 45:   */     
/* 46:46 */     Object tile = world.getTileEntity(x, y, z);
/* 47:48 */     if (tile == null)
/* 48:   */     {
/* 49:49 */       PacketTempChatMultiline.sendCached(player);
/* 50:50 */       return false;
/* 51:   */     }
/* 52:53 */     ForgeDirection dir = ForgeDirection.getOrientation(side);
/* 53:54 */     List<String> data = ScannerRegistry.getData(tile, dir, player);
/* 54:56 */     for (String aData : data) {
/* 55:57 */       PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText(aData));
/* 56:   */     }
/* 57:60 */     PacketTempChatMultiline.sendCached(player);
/* 58:   */     
/* 59:62 */     return true;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public boolean itemInteractionForEntity(ItemStack par1ItemStack, EntityPlayer player, EntityLivingBase entity)
/* 63:   */   {
/* 64:67 */     if (player.worldObj.isClient) {
/* 65:68 */       return true;
/* 66:   */     }
/* 67:71 */     if (entity == null) {
/* 68:72 */       return false;
/* 69:   */     }
/* 70:75 */     List<String> data = ScannerRegistry.getData(entity, ForgeDirection.UP, player);
/* 71:77 */     for (String aData : data) {
/* 72:78 */       PacketTempChatMultiline.addChatComponentMessage(new ChatComponentText(aData));
/* 73:   */     }
/* 74:80 */     PacketTempChatMultiline.sendCached(player);
/* 75:   */     
/* 76:82 */     return true;
/* 77:   */   }
/* 78:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.scanner.ItemScanner
 * JD-Core Version:    0.7.0.1
 */