/*   1:    */ package com.rwtema.extrautils;
/*   2:    */ 
/*   3:    */ import net.minecraft.block.Block;
/*   4:    */ import net.minecraft.block.BlockLeaves;
/*   5:    */ import net.minecraft.block.BlockSnow;
/*   6:    */ import net.minecraft.block.material.Material;
/*   7:    */ import net.minecraft.entity.effect.EntityLightningBolt;
/*   8:    */ import net.minecraft.entity.player.EntityPlayer;
/*   9:    */ import net.minecraft.init.Blocks;
/*  10:    */ import net.minecraft.world.EnumSkyBlock;
/*  11:    */ import net.minecraft.world.World;
/*  12:    */ 
/*  13:    */ public class ActivationRitual
/*  14:    */ {
/*  15: 15 */   public static int max_range = 8;
/*  16: 17 */   public static int num_light = 0;
/*  17: 18 */   public static int required_dirt = 20;
/*  18: 20 */   public static int time_window = 500;
/*  19:    */   
/*  20:    */   public static boolean redstoneCirclePresent(World world, int x, int y, int z)
/*  21:    */   {
/*  22: 23 */     for (int dx = -1; dx <= 1; dx++) {
/*  23: 24 */       for (int dz = -1; dz <= 1; dz++) {
/*  24: 25 */         if ((((dx != 0 ? 1 : 0) | (dz != 0 ? 1 : 0)) != 0) && 
/*  25: 26 */           (world.getBlock(x + dx, y, z + dz) != Blocks.redstone_wire)) {
/*  26: 27 */           return false;
/*  27:    */         }
/*  28:    */       }
/*  29:    */     }
/*  30: 33 */     return true;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static boolean altarInDarkness_Client(World world, int x, int y, int z)
/*  34:    */   {
/*  35: 37 */     for (int dx = -1; dx <= 1; dx++) {
/*  36: 38 */       for (int dz = -1; dz <= 1; dz++) {
/*  37: 39 */         if (world.getSkyBlockTypeBrightness(EnumSkyBlock.Block, x + dx, y, z + dz) + world.getSkyBlockTypeBrightness(EnumSkyBlock.Sky, x + dx, y, z + dz) - world.calculateSkylightSubtracted(1.0F) > 9) {
/*  38: 41 */           return false;
/*  39:    */         }
/*  40:    */       }
/*  41:    */     }
/*  42: 45 */     return world.getSkyBlockTypeBrightness(EnumSkyBlock.Block, x, y + 1, z) + world.getSkyBlockTypeBrightness(EnumSkyBlock.Sky, x, y + 1, z) - world.calculateSkylightSubtracted(1.0F) <= 9;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public static boolean altarInDarkness(World world, int x, int y, int z)
/*  46:    */   {
/*  47: 50 */     for (int dx = -1; dx <= 1; dx++) {
/*  48: 51 */       for (int dz = -1; dz <= 1; dz++) {
/*  49: 52 */         if (world.getBlockLightValue(x + dx, y, z + dz) > 9) {
/*  50: 53 */           return false;
/*  51:    */         }
/*  52:    */       }
/*  53:    */     }
/*  54: 56 */     return world.getBlockLightValue(x, y + 1, z) <= 9;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public static boolean altarCanSeeMoon(World world, int x, int y, int z)
/*  58:    */   {
/*  59: 61 */     return world.canBlockSeeTheSky(x, y, z);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static boolean altarOnEarth(World world, int x, int y, int z)
/*  63:    */   {
/*  64: 65 */     boolean hasDirt = false;
/*  65: 67 */     for (int dx = -1; (dx <= 1) && (!hasDirt); dx++) {
/*  66: 68 */       for (int dz = -1; (dz <= 1) && (!hasDirt); dz++) {
/*  67: 69 */         if ((world.getBlock(x + dx, y - 1, z + dz) != Blocks.dirt) && (world.getBlock(x + dx, y - 1, z + dz) != Blocks.grass)) {
/*  68: 70 */           return false;
/*  69:    */         }
/*  70:    */       }
/*  71:    */     }
/*  72: 75 */     return true;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static int checkTime(long time)
/*  76:    */   {
/*  77: 79 */     time %= 24000L;
/*  78: 81 */     if (time < 18000 - time_window) {
/*  79: 82 */       return -1;
/*  80:    */     }
/*  81: 83 */     if (time > 18000 + time_window) {
/*  82: 84 */       return 1;
/*  83:    */     }
/*  84: 86 */     return 0;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public static boolean naturalEarth(World world, int x, int y, int z)
/*  88:    */   {
/*  89: 91 */     int num_dirt = 0;
/*  90: 93 */     for (int dx = -max_range; dx <= max_range; dx++) {
/*  91: 94 */       for (int dz = -max_range; dz <= max_range; dz++) {
/*  92: 95 */         if (dx * dx + dz * dz < max_range * max_range) {
/*  93: 96 */           for (int dy = Math.min(3 + world.getTopSolidOrLiquidBlock(x + dx, z + dz) - y, max_range); dy >= -max_range; dy--) {
/*  94: 97 */             if (dx * dx + dy * dy + dz * dz <= max_range * max_range)
/*  95:    */             {
/*  96: 98 */               Block id = world.getBlock(x + dx, y + dy, z + dz);
/*  97:100 */               if ((id == Blocks.dirt) || (id == Blocks.grass))
/*  98:    */               {
/*  99:101 */                 if (canShift(world.getBlock(x + dx, y + dy + 1, z + dz)))
/* 100:    */                 {
/* 101:102 */                   num_dirt++;
/* 102:104 */                   if (num_dirt > required_dirt) {
/* 103:105 */                     return true;
/* 104:    */                   }
/* 105:    */                 }
/* 106:    */               }
/* 107:    */               else {
/* 108:112 */                 if (id.isOpaqueCube()) {
/* 109:    */                   break;
/* 110:    */                 }
/* 111:    */               }
/* 112:    */             }
/* 113:    */             else
/* 114:    */             {
/* 115:115 */               if (dy < 0) {
/* 116:    */                 break;
/* 117:    */               }
/* 118:    */             }
/* 119:    */           }
/* 120:    */         } else {
/* 121:119 */           if (dz > 0) {
/* 122:    */             break;
/* 123:    */           }
/* 124:    */         }
/* 125:    */       }
/* 126:    */     }
/* 127:123 */     return false;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public static boolean canShift(Block id)
/* 131:    */   {
/* 132:127 */     if (id == Blocks.air) {
/* 133:128 */       return true;
/* 134:    */     }
/* 135:129 */     return (id != null) && (id.getMaterial() != Material.water) && (id.getMobilityFlag() == 1);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public static void startRitual(World world, int x, int y, int z, EntityPlayer player)
/* 139:    */   {
/* 140:133 */     world.addWeatherEffect(new EntityLightningBolt(world, x, y, z));
/* 141:135 */     if (ExtraUtils.cursedEarth != null)
/* 142:    */     {
/* 143:136 */       com.rwtema.extrautils.block.BlockCursedEarth.powered = 16;
/* 144:138 */       for (int dx = -max_range; dx <= max_range; dx++) {
/* 145:139 */         for (int dz = -max_range; dz <= max_range; dz++) {
/* 146:140 */           if (dx * dx + dz * dz < max_range * max_range) {
/* 147:141 */             for (int dy = max_range; dy > -max_range; dy--) {
/* 148:142 */               if (dx * dx + dy * dy + dz * dz <= max_range * max_range)
/* 149:    */               {
/* 150:143 */                 Block id = world.getBlock(x + dx, y + dy, z + dz);
/* 151:145 */                 if (id != Blocks.air) {
/* 152:147 */                   if ((id == Blocks.dirt) || (id == Blocks.grass))
/* 153:    */                   {
/* 154:148 */                     world.setBlock(x + dx, y + dy, z + dz, ExtraUtils.cursedEarth, 0, 3);
/* 155:    */                   }
/* 156:150 */                   else if ((id instanceof BlockLeaves))
/* 157:    */                   {
/* 158:151 */                     id.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
/* 159:152 */                     world.setBlock(x + dx, y + dy, z + dz, Blocks.air);
/* 160:    */                   }
/* 161:153 */                   else if ((id instanceof BlockSnow))
/* 162:    */                   {
/* 163:154 */                     world.setBlock(x + dx, y + dy, z + dz, Blocks.air);
/* 164:    */                   }
/* 165:155 */                   else if ((id.getMobilityFlag() == 1) && (id != Blocks.redstone_wire))
/* 166:    */                   {
/* 167:156 */                     world.func_147480_a(x + dx, y + dy, z + dz, true);
/* 168:    */                   }
/* 169:    */                   else
/* 170:    */                   {
/* 171:157 */                     if (id.isOpaqueCube()) {
/* 172:    */                       break;
/* 173:    */                     }
/* 174:    */                   }
/* 175:    */                 }
/* 176:    */               }
/* 177:    */               else
/* 178:    */               {
/* 179:160 */                 if (dy < 0) {
/* 180:    */                   break;
/* 181:    */                 }
/* 182:    */               }
/* 183:    */             }
/* 184:    */           } else {
/* 185:164 */             if (dz > 0) {
/* 186:    */               break;
/* 187:    */             }
/* 188:    */           }
/* 189:    */         }
/* 190:    */       }
/* 191:168 */       com.rwtema.extrautils.block.BlockCursedEarth.powered = 0;
/* 192:    */     }
/* 193:    */   }
/* 194:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.ActivationRitual
 * JD-Core Version:    0.7.0.1
 */