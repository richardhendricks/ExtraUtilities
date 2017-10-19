/*   1:    */ package com.rwtema.extrautils.tileentity;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.block.BlockBUD;
/*   4:    */ import cpw.mods.fml.relauncher.Side;
/*   5:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   6:    */ import net.minecraft.block.Block;
/*   7:    */ import net.minecraft.init.Blocks;
/*   8:    */ import net.minecraft.nbt.NBTTagCompound;
/*   9:    */ import net.minecraft.network.NetworkManager;
/*  10:    */ import net.minecraft.network.Packet;
/*  11:    */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*  12:    */ import net.minecraft.tileentity.TileEntity;
/*  13:    */ import net.minecraft.world.World;
/*  14:    */ 
/*  15:    */ public class TileEntityBUD
/*  16:    */   extends TileEntity
/*  17:    */ {
/*  18:    */   public static final int maxCountDown = 8;
/*  19: 18 */   public int countDown = 0;
/*  20: 19 */   public boolean powered = false;
/*  21: 20 */   int[] metadata = new int[6];
/*  22: 21 */   int[] hashCode = new int[6];
/*  23: 22 */   boolean init = false;
/*  24: 23 */   private Block[] ids = new Block[6];
/*  25:    */   private boolean ready;
/*  26:    */   
/*  27:    */   public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y, int z)
/*  28:    */   {
/*  29: 28 */     if (oldBlock == newBlock) {}
/*  30: 28 */     return (oldMeta >= 3 ? 1 : 0) != (newMeta >= 3 ? 1 : 0);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Packet getDescriptionPacket()
/*  34:    */   {
/*  35: 33 */     this.powered = (this.countDown >= 6);
/*  36: 34 */     NBTTagCompound t = new NBTTagCompound();
/*  37: 35 */     t.setBoolean("powered", this.powered);
/*  38: 36 */     return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 4, t);
/*  39:    */   }
/*  40:    */   
/*  41:    */   @SideOnly(Side.CLIENT)
/*  42:    */   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
/*  43:    */   {
/*  44: 42 */     if ((this.worldObj.isClient) && 
/*  45: 43 */       (pkt.func_148857_g().hasKey("powered")))
/*  46:    */     {
/*  47: 44 */       this.powered = pkt.func_148857_g().getBoolean("powered");
/*  48: 45 */       this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void updateEntity()
/*  53:    */   {
/*  54: 51 */     if (this.worldObj.isClient) {
/*  55: 52 */       return;
/*  56:    */     }
/*  57: 55 */     this.powered = (this.countDown >= 7);
/*  58: 57 */     if (this.countDown > 0)
/*  59:    */     {
/*  60: 58 */       this.countDown -= 1;
/*  61: 60 */       if ((this.countDown == 0) || (this.countDown == 7) || (this.countDown == 5))
/*  62:    */       {
/*  63: 61 */         this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
/*  64: 62 */         ((BlockBUD)getBlockType()).updateRedstone(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
/*  65: 63 */         this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, getBlockType());
/*  66:    */       }
/*  67:    */     }
/*  68: 67 */     this.ready = (this.countDown == 0);
/*  69: 69 */     if (!this.init)
/*  70:    */     {
/*  71: 70 */       this.init = true;
/*  72: 71 */       this.ready = false;
/*  73:    */     }
/*  74: 74 */     for (int i = 0; i < 6; i++) {
/*  75: 75 */       checkDir(i);
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void checkDir(int i)
/*  80:    */   {
/*  81: 80 */     int dx = this.xCoord + net.minecraft.util.Facing.offsetsXForSide[i];
/*  82: 81 */     int dy = this.yCoord + net.minecraft.util.Facing.offsetsYForSide[i];
/*  83: 82 */     int dz = this.zCoord + net.minecraft.util.Facing.offsetsZForSide[i];
/*  84: 84 */     if (this.worldObj.blockExists(dx, dy, dz))
/*  85:    */     {
/*  86: 85 */       Block id = this.worldObj.getBlock(dx, dy, dz);
/*  87: 87 */       if (id != getBlockType()) {
/*  88: 88 */         if (this.worldObj.isAirBlock(dx, dy, dz))
/*  89:    */         {
/*  90: 89 */           if (this.ids[i] != Blocks.air)
/*  91:    */           {
/*  92: 90 */             if (this.ready) {
/*  93: 91 */               this.countDown = 8;
/*  94:    */             }
/*  95: 94 */             this.ids[i] = Blocks.air;
/*  96: 95 */             this.metadata[i] = 0;
/*  97: 96 */             this.hashCode[i] = 0;
/*  98:    */           }
/*  99:    */         }
/* 100:    */         else
/* 101:    */         {
/* 102: 99 */           boolean idChange = false;
/* 103:101 */           if (id != this.ids[i])
/* 104:    */           {
/* 105:102 */             if (this.ready) {
/* 106:103 */               this.countDown = 8;
/* 107:    */             }
/* 108:106 */             idChange = true;
/* 109:107 */             this.ids[i] = id;
/* 110:    */           }
/* 111:110 */           int md = this.worldObj.getBlockMetadata(dx, dy, dz);
/* 112:112 */           if (md != this.metadata[i])
/* 113:    */           {
/* 114:113 */             if (this.ready) {
/* 115:114 */               this.countDown = 8;
/* 116:    */             }
/* 117:117 */             idChange = true;
/* 118:118 */             this.metadata[i] = md;
/* 119:    */           }
/* 120:121 */           TileEntity tile = this.worldObj.getTileEntity(dx, dy, dz);
/* 121:123 */           if (tile != null)
/* 122:    */           {
/* 123:124 */             NBTTagCompound t = new NBTTagCompound();
/* 124:125 */             tile.writeToNBT(t);
/* 125:    */             
/* 126:127 */             int h = t.hashCode();
/* 127:129 */             if (h != this.hashCode[i])
/* 128:    */             {
/* 129:130 */               if ((this.ready) || (!idChange)) {
/* 130:131 */                 this.countDown = 8;
/* 131:    */               }
/* 132:134 */               this.hashCode[i] = h;
/* 133:    */             }
/* 134:    */           }
/* 135:    */         }
/* 136:    */       }
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:    */   public boolean getPowered()
/* 141:    */   {
/* 142:143 */     return this.countDown >= 6 ? true : this.worldObj.isClient ? this.powered : false;
/* 143:    */   }
/* 144:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.TileEntityBUD
 * JD-Core Version:    0.7.0.1
 */