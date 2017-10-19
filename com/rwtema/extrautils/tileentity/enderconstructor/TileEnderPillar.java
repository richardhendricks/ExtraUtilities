/*   1:    */ package com.rwtema.extrautils.tileentity.enderconstructor;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ChunkPos;
/*   4:    */ import com.rwtema.extrautils.helper.XURandom;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.LinkedHashSet;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Random;
/*   9:    */ import net.minecraft.block.Block;
/*  10:    */ import net.minecraft.tileentity.TileEntity;
/*  11:    */ import net.minecraft.world.World;
/*  12:    */ import net.minecraft.world.chunk.Chunk;
/*  13:    */ 
/*  14:    */ public class TileEnderPillar
/*  15:    */   extends TileEntity
/*  16:    */ {
/*  17:    */   public static final int transmitLimit = 10;
/*  18: 16 */   public static int range = 10;
/*  19: 17 */   public static Random rand = XURandom.getInstance();
/*  20: 18 */   public LinkedHashSet<ChunkPos> targets = new LinkedHashSet();
/*  21: 19 */   int coolDown = 0;
/*  22: 21 */   boolean init = false;
/*  23:    */   
/*  24:    */   public boolean shouldRefresh(Block oldID, Block newID, int oldMeta, int newMeta, World world, int x, int y, int z)
/*  25:    */   {
/*  26: 25 */     if (oldID == newID) {}
/*  27: 25 */     return (oldMeta > 0 ? 1 : 0) != (newMeta > 0 ? 1 : 0);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void getNearbyTiles()
/*  31:    */   {
/*  32: 30 */     this.init = true;
/*  33: 31 */     this.targets.clear();
/*  34: 33 */     for (int x = this.xCoord - range >> 4 << 4; x < (this.xCoord + range >> 4 << 4) + 16; x += 16) {
/*  35: 34 */       for (int z = this.zCoord - range >> 4 << 4; z < (this.zCoord + range >> 4 << 4) + 16; z += 16) {
/*  36: 35 */         if (this.worldObj.blockExists(x, 100, z))
/*  37:    */         {
/*  38: 36 */           Chunk chunk = this.worldObj.getChunkFromBlockCoords(x, z);
/*  39: 38 */           for (Object obj : chunk.chunkTileEntityMap.values())
/*  40:    */           {
/*  41: 39 */             TileEntity tile = (TileEntity)obj;
/*  42: 41 */             if ((Math.abs(tile.xCoord - this.xCoord) + Math.abs(tile.yCoord - this.yCoord) + Math.abs(tile.zCoord - this.zCoord) < range) && 
/*  43: 42 */               ((tile instanceof IEnderFluxHandler))) {
/*  44: 43 */               this.targets.add(new ChunkPos(tile.xCoord, tile.yCoord, tile.zCoord));
/*  45:    */             }
/*  46:    */           }
/*  47:    */         }
/*  48:    */       }
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void updateEntity()
/*  53:    */   {
/*  54: 53 */     if ((!this.init) || (this.worldObj.getTotalWorldTime() % 40L == 0L)) {
/*  55: 54 */       getNearbyTiles();
/*  56:    */     }
/*  57: 57 */     boolean sent = false;
/*  58: 59 */     if ((this.targets.size() > 0) && (
/*  59: 60 */       ((this.worldObj.isClient) && (getBlockMetadata() == 3)) || (!this.worldObj.isClient)))
/*  60:    */     {
/*  61: 61 */       Iterator<ChunkPos> iterator = this.targets.iterator();
/*  62: 63 */       while (iterator.hasNext())
/*  63:    */       {
/*  64: 64 */         ChunkPos c = (ChunkPos)iterator.next();
/*  65:    */         TileEntity tile;
/*  66: 67 */         if ((this.worldObj.blockExists(c.x, c.y, c.z)) && (((tile = this.worldObj.getTileEntity(c.x, c.y, c.z)) instanceof IEnderFluxHandler)))
/*  67:    */         {
/*  68: 68 */           if (((IEnderFluxHandler)tile).isActive()) {
/*  69: 69 */             if (this.worldObj.isClient)
/*  70:    */             {
/*  71: 70 */               double f = 0.5D;
/*  72: 72 */               for (int i = 0; i < 1; i++)
/*  73:    */               {
/*  74: 73 */                 double dx = c.x + f / 2.0D + (1.0D - f) * rand.nextDouble();
/*  75: 74 */                 double dy = c.y + f / 2.0D + (1.0D - f) * rand.nextDouble();
/*  76: 75 */                 double dz = c.z + f / 2.0D + (1.0D - f) * rand.nextDouble();
/*  77: 76 */                 double dx2 = this.xCoord + f / 2.0D + (1.0D - f) * rand.nextDouble();
/*  78: 77 */                 double dy2 = this.yCoord + f / 2.0D + (1.0D - f) * rand.nextDouble() - 0.5D;
/*  79: 78 */                 double dz2 = this.zCoord + f / 2.0D + (1.0D - f) * rand.nextDouble();
/*  80: 79 */                 this.worldObj.spawnParticle("portal", dx, dy, dz, dx2 - dx, dy2 - dy, dz2 - dz);
/*  81:    */               }
/*  82:    */             }
/*  83:    */             else
/*  84:    */             {
/*  85: 82 */               int a = ((IEnderFluxHandler)tile).recieveEnergy(10, Transfer.PERFORM);
/*  86: 84 */               if (a > 0) {
/*  87: 85 */                 sent = true;
/*  88:    */               }
/*  89:    */             }
/*  90:    */           }
/*  91:    */         }
/*  92:    */         else {
/*  93: 90 */           iterator.remove();
/*  94:    */         }
/*  95:    */       }
/*  96:    */     }
/*  97: 96 */     if (!this.worldObj.isClient)
/*  98:    */     {
/*  99: 97 */       if (sent)
/* 100:    */       {
/* 101: 98 */         this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 3, 2);
/* 102: 99 */         this.coolDown = 20;
/* 103:    */       }
/* 104:102 */       if (this.coolDown > 0)
/* 105:    */       {
/* 106:103 */         this.coolDown -= 1;
/* 107:105 */         if (this.coolDown == 0) {
/* 108:106 */           this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 2, 2);
/* 109:    */         }
/* 110:    */       }
/* 111:    */     }
/* 112:    */   }
/* 113:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.enderconstructor.TileEnderPillar
 * JD-Core Version:    0.7.0.1
 */