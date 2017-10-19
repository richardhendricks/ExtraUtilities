/*   1:    */ package com.rwtema.extrautils.worldgen.Underdark;
/*   2:    */ 
/*   3:    */ import cpw.mods.fml.common.registry.GameRegistry;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.block.Block;
/*   6:    */ import net.minecraft.init.Blocks;
/*   7:    */ import net.minecraft.world.World;
/*   8:    */ import net.minecraft.world.WorldProvider;
/*   9:    */ import net.minecraft.world.biome.BiomeGenBase;
/*  10:    */ import net.minecraft.world.biome.WorldChunkManager;
/*  11:    */ import net.minecraft.world.chunk.Chunk;
/*  12:    */ import net.minecraft.world.chunk.IChunkProvider;
/*  13:    */ import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
/*  14:    */ import net.minecraft.world.gen.ChunkProviderFlat;
/*  15:    */ import net.minecraft.world.gen.MapGenCaves;
/*  16:    */ import net.minecraft.world.gen.MapGenRavine;
/*  17:    */ import net.minecraft.world.gen.structure.MapGenMineshaft;
/*  18:    */ 
/*  19:    */ public class ChunkProviderUnderdark
/*  20:    */   extends ChunkProviderFlat
/*  21:    */   implements IChunkProvider
/*  22:    */ {
/*  23:    */   public static final int cavern_height = 55;
/*  24: 20 */   public static boolean denyDecor = false;
/*  25: 21 */   Random r = new Random();
/*  26:    */   private Random rand;
/*  27: 23 */   private MapGenCaves caveGenerator = new MapGenCaves();
/*  28: 24 */   private MapGenMineshaft mineshaftGenerator = new MapGenMineshaft();
/*  29: 25 */   private MapGenRavine ravineGenerator = new MapGenRavine();
/*  30: 26 */   private WorldGenBigHole holes_gen = new WorldGenBigHole();
/*  31: 27 */   private WorldGenCastle castle_gen = new WorldGenCastle();
/*  32: 28 */   private WorldGenBedrockTree tree_gen = new WorldGenBedrockTree();
/*  33:    */   private World worldObj;
/*  34: 30 */   private Block[] base_array = new Block[65536];
/*  35:    */   
/*  36:    */   public ChunkProviderUnderdark(World par1World, long par2, boolean par4)
/*  37:    */   {
/*  38: 34 */     super(par1World, par2, par4, "2;7,80x1,4,69x0,100x1,7;3;stronghold,dungeon,mineshaft,decoration");
/*  39: 35 */     this.worldObj = par1World;
/*  40: 36 */     this.rand = new Random(par2);
/*  41: 38 */     for (int i = 0; i < this.base_array.length; i++) {
/*  42: 39 */       this.base_array[i] = Blocks.air;
/*  43:    */     }
/*  44: 41 */     for (int x = 0; x < 16; x++) {
/*  45: 42 */       for (int z = 0; z < 16; z++) {
/*  46: 43 */         for (int y = 0; y <= 83; y++) {
/*  47: 44 */           this.base_array[((x * 16 + z) * 128 + y)] = Blocks.stone;
/*  48:    */         }
/*  49:    */       }
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void populate(IChunkProvider par1IChunkProvider, int par2, int par3)
/*  54:    */   {
/*  55: 50 */     super.populate(par1IChunkProvider, par2, par3);
/*  56: 51 */     int k = par2 * 16;
/*  57: 52 */     int l = par3 * 16;
/*  58: 53 */     this.mineshaftGenerator.generateStructuresInChunk(this.worldObj, this.r, par2, par3);
/*  59: 54 */     denyDecor = true;
/*  60: 55 */     this.worldObj.getBiomeGenForCoords(k, l).decorate(this.worldObj, this.rand, k, l);
/*  61: 56 */     GameRegistry.generateWorld(par2, par3, this.worldObj, this, par1IChunkProvider);
/*  62: 57 */     denyDecor = false;
/*  63: 58 */     this.holes_gen.generate(this.worldObj, this.rand, k + 8, 0, l + 8);
/*  64: 59 */     this.castle_gen.generate(this.worldObj, this.rand, k + 8, 82, l + 8);
/*  65: 60 */     this.tree_gen.generate(this.worldObj, this.rand, k + 8, 82, l + 8);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Chunk provideChunk(int par1, int par2)
/*  69:    */   {
/*  70: 65 */     Chunk chunk = new Chunk(this.worldObj, par1, par2);
/*  71: 66 */     Block[] arr = (Block[])this.base_array.clone();
/*  72: 67 */     this.caveGenerator.func_151539_a(this, this.worldObj, par1, par2, arr);
/*  73: 68 */     this.ravineGenerator.func_151539_a(this, this.worldObj, par1, par2, arr);
/*  74: 69 */     this.r.setSeed(this.worldObj.getSeed() + (par1 >> 2) * 65535 + (par2 >> 2));
/*  75: 70 */     int spire_x = (par1 >> 2 << 2) * 16 + 6 + this.r.nextInt(52) - par1 * 16;
/*  76: 71 */     int spire_z = (par2 >> 2 << 2) * 16 + 6 + this.r.nextInt(52) - par2 * 16;
/*  77: 72 */     this.r.setSeed(this.worldObj.getSeed() + par1 * 65535 + par2);
/*  78: 73 */     Block stone_type = Blocks.stone;
/*  79: 77 */     for (int x = 0; x < 16; x++) {
/*  80: 78 */       for (int z = 0; z < 16; z++)
/*  81:    */       {
/*  82: 79 */         boolean stalegtites = false;
/*  83: 80 */         boolean stalegmites = false;
/*  84: 81 */         float rs = (spire_x - x) * (spire_x - x) + (spire_z - z) * (spire_z - z);
/*  85: 83 */         for (int y = 0; y < 255; y++)
/*  86:    */         {
/*  87: 84 */           int l = y >> 4;
/*  88: 85 */           ExtendedBlockStorage extendedblockstorage = chunk.getBlockStorageArray()[l];
/*  89: 87 */           if (extendedblockstorage == null)
/*  90:    */           {
/*  91: 88 */             extendedblockstorage = new ExtendedBlockStorage(y, !this.worldObj.provider.hasNoSky);
/*  92: 89 */             chunk.getBlockStorageArray()[l] = extendedblockstorage;
/*  93:    */           }
/*  94:    */           Block id;
/*  95:    */           Block id;
/*  96: 97 */           if ((y <= 83) && (arr[((x * 16 + z) * 128 + y)] != Blocks.stone))
/*  97:    */           {
/*  98: 98 */             id = arr[((x * 16 + z) * 128 + y)];
/*  99:    */           }
/* 100:    */           else
/* 101:    */           {
/* 102:    */             Block id;
/* 103: 99 */             if (y == 0)
/* 104:    */             {
/* 105:100 */               id = Blocks.bedrock;
/* 106:    */             }
/* 107:    */             else
/* 108:    */             {
/* 109:    */               Block id;
/* 110:101 */               if (y < 71)
/* 111:    */               {
/* 112:102 */                 id = stone_type;
/* 113:    */               }
/* 114:103 */               else if (y < 81)
/* 115:    */               {
/* 116:    */                 Block id;
/* 117:104 */                 if (stalegmites)
/* 118:    */                 {
/* 119:105 */                   id = Blocks.cobblestone;
/* 120:    */                 }
/* 121:    */                 else
/* 122:    */                 {
/* 123:107 */                   Block id = stone_type;
/* 124:108 */                   stalegmites = this.r.nextInt(82 - y) == 0;
/* 125:    */                 }
/* 126:    */               }
/* 127:    */               else
/* 128:    */               {
/* 129:    */                 Block id;
/* 130:110 */                 if ((y >= 81) && (y < 136))
/* 131:    */                 {
/* 132:111 */                   if ((!stalegtites) && (y > 108) && (y < 136)) {
/* 133:112 */                     stalegtites = this.r.nextInt(1 + (136 - y) * (136 - y) * (136 - y)) == 0;
/* 134:    */                   }
/* 135:    */                   Block id;
/* 136:115 */                   if ((stalegtites) || (y == 81) || ((y == 82) && (this.r.nextInt(8) == 0)) || (y == 135) || (rs < 4.0F) || ((rs < 5.5D) && (this.r.nextBoolean())) || (this.r.nextDouble() < 32 - (y - 81) - 8.0D * Math.sqrt(rs)) || (this.r.nextDouble() < 32 - (136 - y) - 8.0D * Math.sqrt(rs))) {
/* 137:117 */                     id = Blocks.cobblestone;
/* 138:    */                   } else {
/* 139:119 */                     id = Blocks.air;
/* 140:    */                   }
/* 141:    */                 }
/* 142:    */                 else
/* 143:    */                 {
/* 144:    */                   Block id;
/* 145:121 */                   if (y == 136)
/* 146:    */                   {
/* 147:122 */                     id = Blocks.cobblestone;
/* 148:    */                   }
/* 149:    */                   else
/* 150:    */                   {
/* 151:    */                     Block id;
/* 152:123 */                     if (y < 254) {
/* 153:124 */                       id = Blocks.stone;
/* 154:    */                     } else {
/* 155:126 */                       id = Blocks.bedrock;
/* 156:    */                     }
/* 157:    */                   }
/* 158:    */                 }
/* 159:    */               }
/* 160:    */             }
/* 161:    */           }
/* 162:129 */           if (id == null) {
/* 163:130 */             id = Blocks.air;
/* 164:    */           }
/* 165:132 */           extendedblockstorage.func_150818_a(x, y & 0xF, z, id);
/* 166:133 */           extendedblockstorage.setExtBlockMetadata(x, y & 0xF, z, 0);
/* 167:    */         }
/* 168:    */       }
/* 169:    */     }
/* 170:138 */     chunk.generateSkylightMap();
/* 171:139 */     BiomeGenBase[] abiomegenbase = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(null, par1 * 16, par2 * 16, 16, 16);
/* 172:140 */     byte[] abyte = chunk.getBiomeArray();
/* 173:142 */     for (int k1 = 0; k1 < abyte.length; k1++) {
/* 174:143 */       abyte[k1] = ((byte)abiomegenbase[k1].biomeID);
/* 175:    */     }
/* 176:145 */     chunk.generateSkylightMap();
/* 177:146 */     return chunk;
/* 178:    */   }
/* 179:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.worldgen.Underdark.ChunkProviderUnderdark
 * JD-Core Version:    0.7.0.1
 */