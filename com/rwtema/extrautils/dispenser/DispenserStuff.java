/*  1:   */ package com.rwtema.extrautils.dispenser;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.block.BlockDispenser;
/*  5:   */ import net.minecraft.block.material.Material;
/*  6:   */ import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
/*  7:   */ import net.minecraft.dispenser.IBlockSource;
/*  8:   */ import net.minecraft.init.Items;
/*  9:   */ import net.minecraft.item.Item;
/* 10:   */ import net.minecraft.item.ItemStack;
/* 11:   */ import net.minecraft.tileentity.TileEntityDispenser;
/* 12:   */ import net.minecraft.util.EnumFacing;
/* 13:   */ import net.minecraft.util.IRegistry;
/* 14:   */ import net.minecraft.util.RegistrySimple;
/* 15:   */ import net.minecraft.world.World;
/* 16:   */ 
/* 17:   */ public class DispenserStuff
/* 18:   */ {
/* 19:   */   public static void registerItems()
/* 20:   */   {
/* 21:17 */     if (!((RegistrySimple)BlockDispenser.dispenseBehaviorRegistry).containsKey(Items.glass_bottle)) {
/* 22:18 */       BlockDispenser.dispenseBehaviorRegistry.putObject(Items.glass_bottle, new BehaviorDefaultDispenseItem()
/* 23:   */       {
/* 24:19 */         private final BehaviorDefaultDispenseItem field_150840_b = new BehaviorDefaultDispenseItem();
/* 25:   */         
/* 26:   */         public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
/* 27:   */         {
/* 28:25 */           EnumFacing enumfacing = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata());
/* 29:26 */           World world = par1IBlockSource.getWorld();
/* 30:27 */           int i = par1IBlockSource.getXInt() + enumfacing.getFrontOffsetX();
/* 31:28 */           int j = par1IBlockSource.getYInt() + enumfacing.getFrontOffsetY();
/* 32:29 */           int k = par1IBlockSource.getZInt() + enumfacing.getFrontOffsetZ();
/* 33:30 */           Material material = world.getBlock(i, j, k).getMaterial();
/* 34:31 */           int l = world.getBlockMetadata(i, j, k);
/* 35:   */           Item item;
/* 36:34 */           if ((Material.water.equals(material)) && (l == 0)) {
/* 37:35 */             item = Items.potionitem;
/* 38:   */           } else {
/* 39:37 */             return super.dispenseStack(par1IBlockSource, par2ItemStack);
/* 40:   */           }
/* 41:   */           Item item;
/* 42:39 */           if (--par2ItemStack.stackSize == 0)
/* 43:   */           {
/* 44:40 */             par2ItemStack.func_150996_a(item);
/* 45:41 */             par2ItemStack.stackSize = 1;
/* 46:42 */             par2ItemStack.setTagCompound(null);
/* 47:   */           }
/* 48:43 */           else if (((TileEntityDispenser)par1IBlockSource.getBlockTileEntity()).func_146019_a(new ItemStack(item)) < 0)
/* 49:   */           {
/* 50:44 */             this.field_150840_b.dispense(par1IBlockSource, new ItemStack(item));
/* 51:   */           }
/* 52:47 */           return par2ItemStack;
/* 53:   */         }
/* 54:   */       });
/* 55:   */     }
/* 56:   */   }
/* 57:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.dispenser.DispenserStuff
 * JD-Core Version:    0.7.0.1
 */