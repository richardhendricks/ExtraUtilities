/*   1:    */ package com.rwtema.extrautils.tileentity.transfernodes.nodebuffer;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.tileentity.transfernodes.pipes.IPipe;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import net.minecraft.nbt.NBTTagCompound;
/*   6:    */ import net.minecraft.tileentity.TileEntity;
/*   7:    */ import net.minecraftforge.common.util.ForgeDirection;
/*   8:    */ import net.minecraftforge.fluids.FluidStack;
/*   9:    */ import net.minecraftforge.fluids.FluidTank;
/*  10:    */ import net.minecraftforge.fluids.IFluidHandler;
/*  11:    */ 
/*  12:    */ public class FluidBuffer
/*  13:    */   implements INodeBuffer
/*  14:    */ {
/*  15:    */   public INode node;
/*  16: 13 */   public FluidTank tank = new FluidTank(8000);
/*  17:    */   
/*  18:    */   public boolean transfer(TileEntity tile, ForgeDirection side, IPipe insertingPipe, int x, int y, int z, ForgeDirection travelDir)
/*  19:    */   {
/*  20: 17 */     if (isEmpty()) {
/*  21: 18 */       return true;
/*  22:    */     }
/*  23: 21 */     if ((tile instanceof IFluidHandler))
/*  24:    */     {
/*  25: 22 */       IFluidHandler destTank = (IFluidHandler)tile;
/*  26: 23 */       int filter = -1;
/*  27: 24 */       boolean eof = false;
/*  28: 26 */       if (insertingPipe != null)
/*  29:    */       {
/*  30: 27 */         filter = insertingPipe.limitTransfer(tile, side, this);
/*  31: 28 */         eof = insertingPipe.getOutputDirections(tile.getWorldObj(), x, y, z, travelDir, this).isEmpty();
/*  32:    */       }
/*  33: 31 */       if (filter < 0) {
/*  34: 32 */         filter = this.tank.getFluidAmount();
/*  35:    */       }
/*  36: 35 */       if ((!eof) && (filter > 1)) {
/*  37: 36 */         filter /= 2;
/*  38:    */       }
/*  39: 39 */       FluidStack b = this.tank.getFluid().copy();
/*  40: 40 */       b.amount = Math.min(b.amount, filter);
/*  41: 41 */       filter = destTank.fill(side, b, false);
/*  42: 42 */       FluidStack c = this.tank.drain(filter, true);
/*  43: 43 */       destTank.fill(side, c, true);
/*  44:    */     }
/*  45: 46 */     return true;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Object getBuffer()
/*  49:    */   {
/*  50: 51 */     return this.tank;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public String getBufferType()
/*  54:    */   {
/*  55: 56 */     return "fluid";
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setBuffer(Object buffer)
/*  59:    */   {
/*  60: 61 */     if ((buffer instanceof FluidTank)) {
/*  61: 62 */       this.tank = ((FluidTank)buffer);
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public boolean isEmpty()
/*  66:    */   {
/*  67: 68 */     if (this.tank.getFluid() != null)
/*  68:    */     {
/*  69: 69 */       if (this.tank.getFluid().amount == 0)
/*  70:    */       {
/*  71: 70 */         this.tank.setFluid(null);
/*  72: 71 */         return true;
/*  73:    */       }
/*  74: 73 */       return false;
/*  75:    */     }
/*  76: 76 */     return true;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void readFromNBT(NBTTagCompound tags)
/*  80:    */   {
/*  81: 82 */     if (tags.hasKey("buffer")) {
/*  82: 83 */       this.tank.readFromNBT(tags.getCompoundTag("buffer"));
/*  83:    */     } else {
/*  84: 85 */       this.tank = new FluidTank(6400);
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void writeToNBT(NBTTagCompound tags)
/*  89:    */   {
/*  90: 91 */     if (this.tank != null)
/*  91:    */     {
/*  92: 92 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*  93: 93 */       this.tank.writeToNBT(nbttagcompound1);
/*  94: 94 */       tags.setTag("buffer", nbttagcompound1);
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setNode(INode node)
/*  99:    */   {
/* 100:100 */     this.node = node;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public INode getNode()
/* 104:    */   {
/* 105:105 */     return this.node;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public boolean transferTo(INodeBuffer receptor, int no)
/* 109:    */   {
/* 110:111 */     if ((getBuffer() == null) || (!getBufferType().equals(receptor.getBufferType()))) {
/* 111:112 */       return false;
/* 112:    */     }
/* 113:114 */     if (!(receptor.getNode() instanceof IFluidHandler)) {
/* 114:115 */       return false;
/* 115:    */     }
/* 116:117 */     ForgeDirection dir = receptor.getNode().getNodeDir();
/* 117:    */     
/* 118:119 */     IFluidHandler dest = (IFluidHandler)receptor.getNode();
/* 119:    */     
/* 120:121 */     int k = dest.fill(dir, this.tank.drain(200 * no, false), false);
/* 121:122 */     if (k <= 0) {
/* 122:123 */       return false;
/* 123:    */     }
/* 124:124 */     dest.fill(dir, this.tank.drain(k, true), true);
/* 125:125 */     receptor.setBuffer(dest);
/* 126:126 */     receptor.markDirty();
/* 127:127 */     return true;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public synchronized Object recieve(Object a)
/* 131:    */   {
/* 132:133 */     if (!(a instanceof FluidStack)) {
/* 133:134 */       return a;
/* 134:    */     }
/* 135:136 */     FluidStack c = (FluidStack)a;
/* 136:137 */     c.amount -= this.tank.fill(c, true);
/* 137:138 */     return c;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void markDirty()
/* 141:    */   {
/* 142:143 */     this.node.bufferChanged();
/* 143:    */   }
/* 144:    */   
/* 145:    */   public boolean shouldSearch()
/* 146:    */   {
/* 147:148 */     return !isEmpty();
/* 148:    */   }
/* 149:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.FluidBuffer
 * JD-Core Version:    0.7.0.1
 */