/*   1:    */ package com.rwtema.extrautils.tileentity.transfernodes.pipes;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.tileentity.transfernodes.BlockTransferPipe;
/*   4:    */ import com.rwtema.extrautils.tileentity.transfernodes.TNHelper;
/*   5:    */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeBuffer;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import net.minecraft.inventory.IInventory;
/*   8:    */ import net.minecraft.tileentity.TileEntity;
/*   9:    */ import net.minecraft.util.IIcon;
/*  10:    */ import net.minecraft.world.IBlockAccess;
/*  11:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  12:    */ 
/*  13:    */ public class PipeBase
/*  14:    */   implements IPipe, IPipeCosmetic
/*  15:    */ {
/*  16:    */   public String type;
/*  17:    */   
/*  18:    */   public PipeBase(String type)
/*  19:    */   {
/*  20: 19 */     this.type = type;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public ArrayList<ForgeDirection> getOutputDirections(IBlockAccess world, int x, int y, int z, ForgeDirection dir, INodeBuffer buffer)
/*  24:    */   {
/*  25: 24 */     ArrayList<ForgeDirection> results = new ArrayList();
/*  26: 26 */     for (ForgeDirection d : TNHelper.randomDirections()) {
/*  27: 27 */       if ((d != dir.getOpposite()) && (TNHelper.canOutput(world, x, y, z, d)) && 
/*  28: 28 */         (TNHelper.canInput(world, x + d.offsetX, y + d.offsetY, z + d.offsetZ, d.getOpposite()))) {
/*  29: 29 */         results.add(d);
/*  30:    */       }
/*  31:    */     }
/*  32: 33 */     return results;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public boolean transferItems(IBlockAccess world, int x, int y, int z, ForgeDirection dir, INodeBuffer buffer)
/*  36:    */   {
/*  37: 38 */     IPipe pipe = TNHelper.getPipe(world, x, y, z);
/*  38: 40 */     if (pipe == null) {
/*  39: 41 */       return true;
/*  40:    */     }
/*  41: 44 */     boolean advance = true;
/*  42: 46 */     for (ForgeDirection d : TNHelper.randomDirections()) {
/*  43: 47 */       if ((pipe.shouldConnectToTile(world, x, y, z, d)) && 
/*  44: 48 */         (!buffer.transfer(world.getTileEntity(x + d.offsetX, y + d.offsetY, z + d.offsetZ), d.getOpposite(), pipe, x, y, z, dir))) {
/*  45: 49 */         advance = false;
/*  46:    */       }
/*  47:    */     }
/*  48: 54 */     return advance;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public boolean canInput(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
/*  52:    */   {
/*  53: 59 */     return true;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public boolean canOutput(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
/*  57:    */   {
/*  58: 64 */     return true;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int limitTransfer(TileEntity dest, ForgeDirection side, INodeBuffer buffer)
/*  62:    */   {
/*  63: 69 */     return -1;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public IInventory getFilterInventory(IBlockAccess world, int x, int y, int z)
/*  67:    */   {
/*  68: 74 */     return null;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public boolean shouldConnectToTile(IBlockAccess world, int x, int y, int z, ForgeDirection dir)
/*  72:    */   {
/*  73: 79 */     return TNHelper.isValidTileEntity(world, x, y, z, dir);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public IIcon baseTexture()
/*  77:    */   {
/*  78: 84 */     return BlockTransferPipe.pipes;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public IIcon pipeTexture(ForgeDirection dir, boolean blocked)
/*  82:    */   {
/*  83: 89 */     if (blocked) {
/*  84: 90 */       return BlockTransferPipe.pipes_1way;
/*  85:    */     }
/*  86: 92 */     return BlockTransferPipe.pipes;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public IIcon invPipeTexture(ForgeDirection dir)
/*  90:    */   {
/*  91: 98 */     return BlockTransferPipe.pipes;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public IIcon socketTexture(ForgeDirection dir)
/*  95:    */   {
/*  96:104 */     return BlockTransferPipe.pipes_nozzle;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String getPipeType()
/* 100:    */   {
/* 101:109 */     return this.type;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public float baseSize()
/* 105:    */   {
/* 106:114 */     return 0.125F;
/* 107:    */   }
/* 108:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.pipes.PipeBase
 * JD-Core Version:    0.7.0.1
 */