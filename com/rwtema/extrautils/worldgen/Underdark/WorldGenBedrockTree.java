/*  1:   */ package com.rwtema.extrautils.worldgen.Underdark;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ChunkPos;
/*  4:   */ import com.rwtema.extrautils.helper.XUHelper;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ import java.util.Random;
/*  7:   */ import net.minecraft.init.Blocks;
/*  8:   */ import net.minecraft.init.Items;
/*  9:   */ import net.minecraft.inventory.IInventory;
/* 10:   */ import net.minecraft.item.ItemStack;
/* 11:   */ import net.minecraft.world.World;
/* 12:   */ import net.minecraft.world.chunk.Chunk;
/* 13:   */ import net.minecraft.world.gen.feature.WorldGenerator;
/* 14:   */ 
/* 15:   */ public class WorldGenBedrockTree
/* 16:   */   extends WorldGenerator
/* 17:   */ {
/* 18:17 */   public ItemStack[] items = { new ItemStack(Items.gold_ingot), new ItemStack(Items.iron_ingot), new ItemStack(Items.diamond), new ItemStack(Items.emerald), new ItemStack(Blocks.stone), new ItemStack(Blocks.cobblestone), new ItemStack(Items.coal), new ItemStack(Items.coal), new ItemStack(Blocks.stone), new ItemStack(Blocks.stone), new ItemStack(Blocks.stone), new ItemStack(Blocks.stone), new ItemStack(Blocks.mossy_cobblestone), new ItemStack(Blocks.mossy_cobblestone), new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.torch), new ItemStack(Blocks.torch), new ItemStack(Items.redstone) };
/* 19:23 */   private ArrayList<ChunkPos> torchPos = new ArrayList();
/* 20:   */   private ChunkPos chestPos;
/* 21:   */   
/* 22:   */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/* 23:   */   {
/* 24:28 */     if (par2Random.nextInt(960) == 0)
/* 25:   */     {
/* 26:29 */       XUHelper.resetTimer();
/* 27:30 */       int r = 4 + par2Random.nextInt(6);
/* 28:31 */       int x = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
/* 29:32 */       int z = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);
/* 30:33 */       this.torchPos.clear();
/* 31:34 */       this.chestPos = new ChunkPos(x, 80, z);
/* 32:35 */       genTreePart(par1World, x, 80, z, 1, 10, par2Random);
/* 33:36 */       par1World.setBlock(this.chestPos.x, this.chestPos.y, this.chestPos.z, Blocks.chest);
/* 34:38 */       if ((par1World.getTileEntity(this.chestPos.x, this.chestPos.y, this.chestPos.z) instanceof IInventory))
/* 35:   */       {
/* 36:39 */         IInventory inv = (IInventory)par1World.getTileEntity(this.chestPos.x, this.chestPos.y, this.chestPos.z);
/* 37:41 */         for (int i = 0; i < inv.getSizeInventory(); i++)
/* 38:   */         {
/* 39:42 */           ItemStack t = this.items[par2Random.nextInt(this.items.length)].copy();
/* 40:43 */           t.stackSize = (1 + par2Random.nextInt(1 + par2Random.nextInt(1 + par2Random.nextInt(1 + par2Random.nextInt(1 + par2Random.nextInt(64))))));
/* 41:44 */           inv.setInventorySlotContents(i, t);
/* 42:   */         }
/* 43:   */       }
/* 44:48 */       for (ChunkPos torchPo1 : this.torchPos) {
/* 45:49 */         if (par1World.getBlock(torchPo1.x, torchPo1.y, torchPo1.z) == Blocks.air) {
/* 46:50 */           par1World.getChunkFromBlockCoords(torchPo1.x, torchPo1.z).func_150807_a(torchPo1.x & 0xF, torchPo1.y, torchPo1.z & 0xF, Blocks.torch, 5);
/* 47:   */         }
/* 48:   */       }
/* 49:53 */       for (ChunkPos torchPo : this.torchPos) {
/* 50:54 */         par1World.func_147451_t(torchPo.x, torchPo.y, torchPo.z);
/* 51:   */       }
/* 52:   */     }
/* 53:58 */     return true;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public void genTreePart(World world, int x, int y, int z, int dir, int dist, Random rand)
/* 57:   */   {
/* 58:62 */     if (dist < 1) {
/* 59:63 */       return;
/* 60:   */     }
/* 61:66 */     for (int i = 1; i < dist + 1; i++)
/* 62:   */     {
/* 63:67 */       if (world.getBlock(x + net.minecraft.util.Facing.offsetsXForSide[dir] * i, y + net.minecraft.util.Facing.offsetsYForSide[dir] * i, z + net.minecraft.util.Facing.offsetsZForSide[dir] * i) == Blocks.bedrock) {
/* 64:68 */         return;
/* 65:   */       }
/* 66:71 */       if (y + net.minecraft.util.Facing.offsetsYForSide[dir] * i + 1 > this.chestPos.y) {
/* 67:72 */         this.chestPos = new ChunkPos(x + net.minecraft.util.Facing.offsetsXForSide[dir] * i, y + net.minecraft.util.Facing.offsetsYForSide[dir] * i + 1, z + net.minecraft.util.Facing.offsetsZForSide[dir] * i);
/* 68:   */       }
/* 69:75 */       world.setBlock(x + net.minecraft.util.Facing.offsetsXForSide[dir] * i, y + net.minecraft.util.Facing.offsetsYForSide[dir] * i, z + net.minecraft.util.Facing.offsetsZForSide[dir] * i, Blocks.bedrock);
/* 70:77 */       if (y + net.minecraft.util.Facing.offsetsYForSide[dir] * i < 80) {
/* 71:78 */         return;
/* 72:   */       }
/* 73:81 */       if (y + net.minecraft.util.Facing.offsetsYForSide[dir] * i > 120) {
/* 74:82 */         return;
/* 75:   */       }
/* 76:85 */       if (rand.nextInt(5) == 0) {
/* 77:86 */         this.torchPos.add(new ChunkPos(x, y + 1, z));
/* 78:   */       }
/* 79:   */     }
/* 80:90 */     if (dist <= 1) {
/* 81:91 */       return;
/* 82:   */     }
/* 83:94 */     for (int d = 0; d < 6; d++) {
/* 84:95 */       if (d != net.minecraft.util.Facing.oppositeSide[dir]) {
/* 85:96 */         genTreePart(world, x + net.minecraft.util.Facing.offsetsXForSide[dir] * dist, y + net.minecraft.util.Facing.offsetsYForSide[dir] * dist, z + net.minecraft.util.Facing.offsetsZForSide[dir] * dist, d, (int)Math.floor(rand.nextDouble() * dist), rand);
/* 86:   */       }
/* 87:   */     }
/* 88:   */   }
/* 89:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.worldgen.Underdark.WorldGenBedrockTree
 * JD-Core Version:    0.7.0.1
 */