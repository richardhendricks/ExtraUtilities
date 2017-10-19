/*   1:    */ package com.rwtema.extrautils.worldgen.endoftime;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.worldgen.TeleporterBase;
/*   5:    */ import java.util.LinkedList;
/*   6:    */ import net.minecraft.block.Block;
/*   7:    */ import net.minecraft.entity.Entity;
/*   8:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*   9:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  10:    */ import net.minecraft.entity.passive.EntityVillager;
/*  11:    */ import net.minecraft.init.Blocks;
/*  12:    */ import net.minecraft.tileentity.TileEntity;
/*  13:    */ import net.minecraft.util.ChunkCoordinates;
/*  14:    */ import net.minecraft.village.MerchantRecipe;
/*  15:    */ import net.minecraft.village.MerchantRecipeList;
/*  16:    */ import net.minecraft.world.WorldProvider;
/*  17:    */ import net.minecraft.world.WorldServer;
/*  18:    */ import net.minecraft.world.storage.WorldInfo;
/*  19:    */ import net.minecraftforge.common.util.FakePlayerFactory;
/*  20:    */ 
/*  21:    */ public class TeleporterEndOfTime
/*  22:    */   extends TeleporterBase
/*  23:    */ {
/*  24:    */   public TeleporterEndOfTime(WorldServer p_i1963_1_)
/*  25:    */   {
/*  26: 23 */     super(p_i1963_1_);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void placeInPortal(Entity entity, double x, double y, double z, float r)
/*  30:    */   {
/*  31: 28 */     if (!placeInExistingPortal(entity, x, y, z, r)) {
/*  32: 29 */       if (this.worldServerInstance.provider.dimensionId != ExtraUtils.endoftimeDimID)
/*  33:    */       {
/*  34: 30 */         y = this.worldServerInstance.getTopSolidOrLiquidBlock((int)x, (int)z);
/*  35: 31 */         entity.setLocationAndAngles(x, y, z, entity.rotationYaw, 0.0F);
/*  36:    */       }
/*  37:    */       else
/*  38:    */       {
/*  39: 33 */         ChunkCoordinates entrancePortalLocation = this.worldServerInstance.getEntrancePortalLocation();
/*  40: 34 */         if (!placeInExistingPortal(entity, entrancePortalLocation.posX, y, entrancePortalLocation.posZ, r)) {
/*  41: 35 */           makePortal(entity);
/*  42:    */         }
/*  43:    */       }
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean placeInExistingPortal(Entity entity, double x, double y, double z, float r)
/*  48:    */   {
/*  49: 43 */     TileEntity destPortal = null;
/*  50: 45 */     for (int s = 0; (s <= 5) && (destPortal == null); s++) {
/*  51: 46 */       for (int dx = -s; dx <= s; dx++) {
/*  52: 47 */         for (int dz = -s; dz <= s; dz++) {
/*  53: 48 */           if (destPortal == null) {
/*  54: 49 */             destPortal = findPortalInChunk(x + dx * 16, z + dz * 16);
/*  55:    */           }
/*  56:    */         }
/*  57:    */       }
/*  58:    */     }
/*  59: 55 */     if (destPortal != null)
/*  60:    */     {
/*  61: 56 */       entity.setLocationAndAngles(destPortal.xCoord + 0.5D, destPortal.yCoord + 1, destPortal.zCoord + 0.5D, entity.rotationYaw, entity.rotationPitch);
/*  62: 57 */       entity.motionX = (entity.motionY = entity.motionZ = 0.0D);
/*  63: 58 */       return true;
/*  64:    */     }
/*  65: 60 */     return false;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public boolean makePortal(Entity entity)
/*  69:    */   {
/*  70: 66 */     ChunkCoordinates dest = this.worldServerInstance.getEntrancePortalLocation();
/*  71: 67 */     int x = dest.posX;
/*  72: 68 */     int y = dest.posY;
/*  73: 69 */     int z = dest.posZ;
/*  74: 71 */     if (y < 64)
/*  75:    */     {
/*  76: 72 */       y = 64;
/*  77: 73 */       this.worldServerInstance.getWorldInfo().setSpawnPosition(x, 64, z);
/*  78:    */     }
/*  79: 76 */     int r = 8;
/*  80:    */     
/*  81: 78 */     boolean d = ExtraUtils.decorative1 != null;
/*  82:    */     
/*  83: 80 */     Block bricks = d ? ExtraUtils.decorative1 : Blocks.stonebrick;
/*  84: 81 */     int bricksMeta1 = d ? 4 : 0;
/*  85: 82 */     int bricksMeta2 = d ? 0 : 0;
/*  86: 83 */     int bricksMeta3 = d ? 10 : 0;
/*  87:    */     
/*  88: 85 */     Block darkBricks = d ? ExtraUtils.decorative1 : Blocks.obsidian;
/*  89: 86 */     int darkBricksMeta = d ? 2 : 0;
/*  90: 89 */     for (int dx = -r; dx <= r; dx++) {
/*  91: 90 */       for (int dz = -r; dz <= r; dz++)
/*  92:    */       {
/*  93: 91 */         for (int dy = -2; dy < 7; dy++) {
/*  94: 92 */           this.worldServerInstance.setBlock(x + dx, y + dy, z + dz, Blocks.air, 0, 2);
/*  95:    */         }
/*  96: 95 */         int min_r = Math.min(Math.abs(dx), Math.abs(dz));
/*  97: 96 */         int max_r = Math.max(Math.abs(dx), Math.abs(dz));
/*  98: 98 */         if (max_r <= r - 1) {
/*  99: 99 */           this.worldServerInstance.setBlock(x + dx, y - 1, z + dz, Blocks.bedrock, 0, 2);
/* 100:    */         }
/* 101:101 */         if ((dx == 0) && (dz == 0))
/* 102:    */         {
/* 103:102 */           this.worldServerInstance.setBlock(x, y, z, ExtraUtils.portal, 3, 2);
/* 104:    */         }
/* 105:103 */         else if ((max_r == r) && (min_r > 1))
/* 106:    */         {
/* 107:104 */           this.worldServerInstance.setBlock(x + dx, y, z + dz, Blocks.stonebrick, 0, 2);
/* 108:105 */           this.worldServerInstance.setBlock(x + dx, y + 1, z + dz, bricks, bricksMeta1, 2);
/* 109:106 */           this.worldServerInstance.setBlock(x + dx, y + 2, z + dz, Blocks.fence, 0, 2);
/* 110:    */         }
/* 111:107 */         else if (max_r == r)
/* 112:    */         {
/* 113:108 */           this.worldServerInstance.setBlock(x + dx, y, z + dz, Blocks.stonebrick, 3, 2);
/* 114:    */         }
/* 115:109 */         else if (max_r == 1)
/* 116:    */         {
/* 117:110 */           this.worldServerInstance.setBlock(x + dx, y, z + dz, bricks, bricksMeta2, 2);
/* 118:    */         }
/* 119:111 */         else if (min_r == 1)
/* 120:    */         {
/* 121:112 */           this.worldServerInstance.setBlock(x + dx, y, z + dz, bricks, bricksMeta2, 2);
/* 122:    */         }
/* 123:113 */         else if ((max_r == r - 1) && (dx != 0) && (dz != 0))
/* 124:    */         {
/* 125:114 */           this.worldServerInstance.setBlock(x + dx, y, z + dz, Blocks.stonebrick, 0, 2);
/* 126:115 */           this.worldServerInstance.setBlock(x + dx, y + 1, z + dz, Blocks.stone_slab, 5, 2);
/* 127:    */         }
/* 128:116 */         else if (max_r == 5)
/* 129:    */         {
/* 130:117 */           this.worldServerInstance.setBlock(x + dx, y, z + dz, Blocks.stonebrick, 3, 2);
/* 131:    */         }
/* 132:    */         else
/* 133:    */         {
/* 134:119 */           this.worldServerInstance.setBlock(x + dx, y, z + dz, bricks, bricksMeta3, 2);
/* 135:    */         }
/* 136:    */       }
/* 137:    */     }
/* 138:125 */     int lx = x + 3;int lz = z + 3;
/* 139:126 */     int lh = 6;
/* 140:    */     
/* 141:128 */     this.worldServerInstance.setBlock(lx, y + 1, lz, darkBricks, darkBricksMeta, 2);
/* 142:130 */     for (int i = 2; i < lh; i++) {
/* 143:131 */       this.worldServerInstance.setBlock(lx, y + i, lz, Blocks.nether_brick_fence, 0, 2);
/* 144:    */     }
/* 145:134 */     if (ExtraUtils.colorBlockRedstone != null) {
/* 146:135 */       this.worldServerInstance.setBlock(lx, y + lh, lz, ExtraUtils.colorBlockRedstone, 15, 2);
/* 147:    */     } else {
/* 148:137 */       this.worldServerInstance.setBlock(lx, y + lh, lz, Blocks.redstone_block, 0, 2);
/* 149:    */     }
/* 150:139 */     for (int i = 2; i < 6; i++) {
/* 151:140 */       this.worldServerInstance.setBlock(lx + net.minecraft.util.Facing.offsetsXForSide[i], y + lh, lz + net.minecraft.util.Facing.offsetsZForSide[i], Blocks.nether_brick_fence, 0, 2);
/* 152:    */     }
/* 153:143 */     this.worldServerInstance.setBlock(lx, y + lh + 2, lz, Blocks.stone_slab, 6, 2);
/* 154:    */     
/* 155:145 */     this.worldServerInstance.setBlock(lx, y + lh + 1, lz, Blocks.lit_redstone_lamp, 0, 3);
/* 156:    */     
/* 157:    */ 
/* 158:148 */     this.worldServerInstance.setBlock(x + r - 2, y + 1, z - r + 2, Blocks.cauldron, 3, 2);
/* 159:    */     
/* 160:    */ 
/* 161:151 */     EntityVillager villager = new EntityVillager(this.worldServerInstance);
/* 162:152 */     villager.setLocationAndAngles(lx - 0.5D, y + 2, lz - 0.5D, 0.0F, 0.0F);
/* 163:153 */     villager.setProfession(0);
/* 164:154 */     MerchantRecipeList list = villager.getRecipes(FakePlayerFactory.getMinecraft(this.worldServerInstance));
/* 165:155 */     list.clear();
/* 166:157 */     if (!lastVillagerTrades.isEmpty()) {
/* 167:158 */       for (MerchantRecipe merchantRecipe : lastVillagerTrades) {
/* 168:159 */         list.addToListWithCheck(merchantRecipe);
/* 169:    */       }
/* 170:    */     }
/* 171:163 */     villager.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.0D);
/* 172:164 */     villager.setCustomNameTag("The Last Villager");
/* 173:165 */     villager.forceSpawn = true;
/* 174:166 */     villager.func_110163_bv();
/* 175:167 */     this.worldServerInstance.spawnEntityInWorld(villager);
/* 176:    */     
/* 177:169 */     entity.setLocationAndAngles(x + 0.5D, y + 1, z + 0.5D, entity.rotationYaw, entity.rotationPitch);
/* 178:170 */     entity.motionX = (entity.motionY = entity.motionZ = 0.0D);
/* 179:171 */     return true;
/* 180:    */   }
/* 181:    */   
/* 182:174 */   public static final LinkedList<MerchantRecipe> lastVillagerTrades = new LinkedList();
/* 183:    */   
/* 184:    */   public void removeStalePortalLocations(long p_85189_1_) {}
/* 185:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.worldgen.endoftime.TeleporterEndOfTime
 * JD-Core Version:    0.7.0.1
 */