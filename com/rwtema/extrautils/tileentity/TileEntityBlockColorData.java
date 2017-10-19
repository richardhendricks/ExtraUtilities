/*   1:    */ package com.rwtema.extrautils.tileentity;
/*   2:    */ 
/*   3:    */ import cpw.mods.fml.relauncher.Side;
/*   4:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   5:    */ import net.minecraft.nbt.NBTTagCompound;
/*   6:    */ import net.minecraft.nbt.NBTTagFloat;
/*   7:    */ import net.minecraft.network.NetworkManager;
/*   8:    */ import net.minecraft.network.Packet;
/*   9:    */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*  10:    */ import net.minecraft.tileentity.TileEntity;
/*  11:    */ import net.minecraft.world.World;
/*  12:    */ 
/*  13:    */ public class TileEntityBlockColorData
/*  14:    */   extends TileEntity
/*  15:    */ {
/*  16: 14 */   public float[][] palette = new float[16][3];
/*  17: 15 */   private int rerenderTimer = 0;
/*  18: 16 */   private int rerenderDelay = 20;
/*  19:    */   
/*  20:    */   public TileEntityBlockColorData()
/*  21:    */   {
/*  22: 19 */     for (int i = 0; i < 16; i++) {
/*  23: 20 */       for (int j = 0; j < 3; j++) {
/*  24: 21 */         if (this.palette[i][j] == 0.0F) {
/*  25: 22 */           this.palette[i][j] = com.rwtema.extrautils.block.BlockColor.initColor[i][j];
/*  26:    */         }
/*  27:    */       }
/*  28:    */     }
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void readFromNBT(NBTTagCompound par1NBTTagCompound)
/*  32:    */   {
/*  33: 31 */     super.readFromNBT(par1NBTTagCompound);
/*  34: 33 */     for (int i = 0; i < 16; i++) {
/*  35: 34 */       for (int j = 0; j < 3; j++) {
/*  36: 35 */         if (par1NBTTagCompound.hasKey("col" + i + "_" + j))
/*  37:    */         {
/*  38: 36 */           if ((par1NBTTagCompound.getTag("col" + i + "_" + j) instanceof NBTTagFloat)) {
/*  39: 37 */             this.palette[i][j] = par1NBTTagCompound.getFloat("col" + i + "_" + j);
/*  40:    */           }
/*  41:    */         }
/*  42:    */         else {
/*  43: 40 */           this.palette[i][j] = com.rwtema.extrautils.block.BlockColor.initColor[i][j];
/*  44:    */         }
/*  45:    */       }
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void writeToNBT(NBTTagCompound par1NBTTagCompound)
/*  50:    */   {
/*  51: 49 */     super.writeToNBT(par1NBTTagCompound);
/*  52: 51 */     for (int i = 0; i < 16; i++) {
/*  53: 52 */       for (int j = 0; j < 3; j++) {
/*  54: 53 */         par1NBTTagCompound.setFloat("col" + i + "_" + j, this.palette[i][j]);
/*  55:    */       }
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setColor(int metadata, float r, float g, float b)
/*  60:    */   {
/*  61: 58 */     if (this.worldObj.isClient) {
/*  62: 59 */       return;
/*  63:    */     }
/*  64: 62 */     if ((this.palette[metadata][0] == r) && (this.palette[metadata][1] == g) && (this.palette[metadata][2] == b)) {
/*  65: 63 */       return;
/*  66:    */     }
/*  67: 66 */     this.palette[metadata][0] = r;
/*  68: 67 */     this.palette[metadata][1] = g;
/*  69: 68 */     this.palette[metadata][2] = b;
/*  70: 69 */     boolean notDefault = false;
/*  71: 71 */     for (int i = 0; (i < 16) && (!notDefault); i++) {
/*  72: 72 */       for (int j = 0; (j < 3) && (!notDefault); j++) {
/*  73: 73 */         if (this.palette[i][j] != com.rwtema.extrautils.block.BlockColor.initColor[i][j])
/*  74:    */         {
/*  75: 74 */           notDefault = true;
/*  76: 75 */           break;
/*  77:    */         }
/*  78:    */       }
/*  79:    */     }
/*  80: 78 */     if (notDefault) {
/*  81: 79 */       this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
/*  82:    */     } else {
/*  83: 81 */       this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
/*  84:    */     }
/*  85: 84 */     onInventoryChanged();
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Packet getDescriptionPacket()
/*  89:    */   {
/*  90: 92 */     NBTTagCompound t = new NBTTagCompound();
/*  91: 93 */     writeToNBT(t);
/*  92: 94 */     return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 4, t);
/*  93:    */   }
/*  94:    */   
/*  95:    */   @SideOnly(Side.CLIENT)
/*  96:    */   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
/*  97:    */   {
/*  98:100 */     readFromNBT(pkt.func_148857_g());
/*  99:102 */     if ((this.worldObj.isClient) && 
/* 100:103 */       (this.rerenderTimer == 0))
/* 101:    */     {
/* 102:104 */       this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, 0, this.zCoord, this.xCoord + 16, 255, this.zCoord + 16);
/* 103:105 */       this.rerenderTimer = this.rerenderDelay;
/* 104:106 */       this.rerenderDelay = ((int)(this.rerenderDelay * 1.1D));
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void updateEntity()
/* 109:    */   {
/* 110:118 */     if (this.worldObj.isClient) {
/* 111:119 */       if (this.rerenderTimer > 0)
/* 112:    */       {
/* 113:120 */         this.rerenderTimer -= 1;
/* 114:122 */         if (this.rerenderTimer == 0) {
/* 115:123 */           this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, 0, this.zCoord, this.xCoord + 16, 255, this.zCoord + 16);
/* 116:    */         }
/* 117:    */       }
/* 118:126 */       else if (this.rerenderDelay > 10)
/* 119:    */       {
/* 120:127 */         this.rerenderDelay -= 1;
/* 121:    */       }
/* 122:    */       else
/* 123:    */       {
/* 124:129 */         this.rerenderDelay = 10;
/* 125:    */       }
/* 126:    */     }
/* 127:    */   }
/* 128:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.TileEntityBlockColorData
 * JD-Core Version:    0.7.0.1
 */