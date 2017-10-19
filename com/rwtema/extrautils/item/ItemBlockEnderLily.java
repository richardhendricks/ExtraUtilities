/*   1:    */ package com.rwtema.extrautils.item;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtilsMod;
/*   4:    */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*   5:    */ import com.rwtema.extrautils.block.BlockEnderLily;
/*   6:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   7:    */ import net.minecraft.block.Block;
/*   8:    */ import net.minecraft.block.Block.SoundType;
/*   9:    */ import net.minecraft.block.material.Material;
/*  10:    */ import net.minecraft.entity.player.EntityPlayer;
/*  11:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  12:    */ import net.minecraft.init.Blocks;
/*  13:    */ import net.minecraft.item.ItemBlock;
/*  14:    */ import net.minecraft.item.ItemStack;
/*  15:    */ import net.minecraft.util.ChunkCoordinates;
/*  16:    */ import net.minecraft.util.MovingObjectPosition;
/*  17:    */ import net.minecraft.util.MovingObjectPosition.MovingObjectType;
/*  18:    */ import net.minecraft.util.Vec3;
/*  19:    */ import net.minecraft.world.IBlockAccess;
/*  20:    */ import net.minecraft.world.World;
/*  21:    */ import net.minecraftforge.common.EnumPlantType;
/*  22:    */ import net.minecraftforge.common.IPlantable;
/*  23:    */ 
/*  24:    */ public class ItemBlockEnderLily
/*  25:    */   extends ItemBlock
/*  26:    */   implements IPlantable
/*  27:    */ {
/*  28:    */   BlockEnderLily enderLily;
/*  29:    */   
/*  30:    */   public ItemBlockEnderLily(Block par1)
/*  31:    */   {
/*  32: 22 */     super(par1);
/*  33: 23 */     this.enderLily = ((BlockEnderLily)par1);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z)
/*  37:    */   {
/*  38: 28 */     return EnumPlantType.Cave;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Block getPlant(IBlockAccess world, int x, int y, int z)
/*  42:    */   {
/*  43: 33 */     return this.field_150939_a;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int getPlantMetadata(IBlockAccess world, int x, int y, int z)
/*  47:    */   {
/*  48: 38 */     return 0;
/*  49:    */   }
/*  50:    */   
/*  51: 41 */   private static final ChunkCoordinates zero = new ChunkCoordinates(0, 0, 0);
/*  52:    */   
/*  53:    */   public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
/*  54:    */   {
/*  55: 45 */     Block block = world.getBlock(x, y, z);
/*  56: 47 */     if ((block == Blocks.snow_layer) && ((world.getBlockMetadata(x, y, z) & 0x7) < 1))
/*  57:    */     {
/*  58: 48 */       side = 1;
/*  59:    */     }
/*  60: 49 */     else if ((block != Blocks.vine) && (block != Blocks.tallgrass) && (block != Blocks.deadbush) && (!block.isReplaceable(world, x, y, z)))
/*  61:    */     {
/*  62: 50 */       if (side == 0) {
/*  63: 50 */         y--;
/*  64:    */       }
/*  65: 51 */       if (side == 1) {
/*  66: 51 */         y++;
/*  67:    */       }
/*  68: 52 */       if (side == 2) {
/*  69: 52 */         z--;
/*  70:    */       }
/*  71: 53 */       if (side == 3) {
/*  72: 53 */         z++;
/*  73:    */       }
/*  74: 54 */       if (side == 4) {
/*  75: 54 */         x--;
/*  76:    */       }
/*  77: 55 */       if (side == 5) {
/*  78: 55 */         x++;
/*  79:    */       }
/*  80:    */     }
/*  81: 58 */     int my = y + 8;
/*  82: 59 */     while ((y < my) && (this.enderLily.isFluid(world, x, y, z))) {
/*  83: 60 */       y++;
/*  84:    */     }
/*  85: 63 */     boolean b = world.canPlaceEntityOnSide(this.field_150939_a, x, y, z, false, side, player, itemstack);
/*  86: 65 */     if (itemstack.stackSize == 0) {
/*  87: 66 */       return false;
/*  88:    */     }
/*  89: 67 */     if (!player.canPlayerEdit(x, y, z, side, itemstack)) {
/*  90: 68 */       return false;
/*  91:    */     }
/*  92: 69 */     if ((y == 255) && (this.field_150939_a.getMaterial().isSolid())) {
/*  93: 70 */       return false;
/*  94:    */     }
/*  95: 73 */     if (b)
/*  96:    */     {
/*  97: 74 */       int i1 = getMetadata(itemstack.getItemDamage());
/*  98: 75 */       int j1 = this.field_150939_a.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, i1);
/*  99: 77 */       if (placeBlockAt(itemstack, player, world, x, y, z, side, hitX, hitY, hitZ, j1))
/* 100:    */       {
/* 101: 78 */         world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, this.field_150939_a.stepSound.func_150496_b(), (this.field_150939_a.stepSound.getVolume() + 1.0F) / 2.0F, this.field_150939_a.stepSound.getFrequency() * 0.8F);
/* 102: 79 */         if (!player.capabilities.isCreativeMode) {
/* 103: 80 */           itemstack.stackSize -= 1;
/* 104:    */         }
/* 105:    */       }
/* 106: 83 */       return true;
/* 107:    */     }
/* 108: 85 */     return false;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
/* 112:    */   {
/* 113: 93 */     if ((XUHelper.isPlayerFake(player)) && (zero.equals(player.getPlayerCoordinates()))) {
/* 114: 94 */       return false;
/* 115:    */     }
/* 116: 97 */     MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(world, player, true);
/* 117: 99 */     if (movingobjectposition == null) {
/* 118:100 */       return false;
/* 119:    */     }
/* 120:101 */     if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
/* 121:    */     {
/* 122:103 */       if (movingobjectposition.sideHit == 0) {
/* 123:104 */         return false;
/* 124:    */       }
/* 125:106 */       side = 0;
/* 126:    */       
/* 127:108 */       x = movingobjectposition.blockX;
/* 128:109 */       y = movingobjectposition.blockY;
/* 129:110 */       z = movingobjectposition.blockZ;
/* 130:112 */       if (!this.enderLily.isFluid(world, x, y, z)) {
/* 131:113 */         return false;
/* 132:    */       }
/* 133:115 */       hitX = (float)movingobjectposition.hitVec.xCoord - x;
/* 134:116 */       hitY = (float)movingobjectposition.hitVec.yCoord - y;
/* 135:117 */       hitZ = (float)movingobjectposition.hitVec.zCoord - z;
/* 136:    */       
/* 137:119 */       y++;
/* 138:121 */       if (player.canPlayerEdit(x, y, z, movingobjectposition.sideHit, stack))
/* 139:    */       {
/* 140:122 */         if (onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ))
/* 141:    */         {
/* 142:123 */           if (world.isClient) {
/* 143:124 */             ExtraUtilsMod.proxy.sendUsePacket(x, y, z, side, stack, hitX, hitY, hitZ);
/* 144:    */           }
/* 145:125 */           return true;
/* 146:    */         }
/* 147:128 */         return false;
/* 148:    */       }
/* 149:    */     }
/* 150:133 */     return super.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
/* 151:    */   }
/* 152:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemBlockEnderLily
 * JD-Core Version:    0.7.0.1
 */