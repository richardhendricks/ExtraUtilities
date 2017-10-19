/*  1:   */ package com.rwtema.extrautils.tileentity.transfernodes.nodebuffer;
/*  2:   */ 
/*  3:   */ import cofh.api.energy.EnergyStorage;
/*  4:   */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNodeEnergy;
/*  5:   */ import com.rwtema.extrautils.tileentity.transfernodes.pipes.IPipe;
/*  6:   */ import net.minecraft.nbt.NBTTagCompound;
/*  7:   */ import net.minecraft.tileentity.TileEntity;
/*  8:   */ import net.minecraftforge.common.util.ForgeDirection;
/*  9:   */ 
/* 10:   */ public class EnergyBuffer
/* 11:   */   implements INodeBuffer
/* 12:   */ {
/* 13:   */   TileEntityTransferNodeEnergy node;
/* 14:   */   
/* 15:   */   public boolean transfer(TileEntity tile, ForgeDirection side, IPipe insertingPipe, int x, int y, int z, ForgeDirection travelDir)
/* 16:   */   {
/* 17:15 */     return false;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public EnergyStorage getBuffer()
/* 21:   */   {
/* 22:20 */     return this.node.powerHandler;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String getBufferType()
/* 26:   */   {
/* 27:25 */     return "energy";
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void setBuffer(Object buffer)
/* 31:   */   {
/* 32:30 */     this.node.powerHandler = ((EnergyStorage)buffer);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public boolean isEmpty()
/* 36:   */   {
/* 37:35 */     return this.node.powerHandler.getEnergyStored() == 0;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public boolean shouldSearch()
/* 41:   */   {
/* 42:40 */     return false;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void readFromNBT(NBTTagCompound tags) {}
/* 46:   */   
/* 47:   */   public void writeToNBT(NBTTagCompound tags) {}
/* 48:   */   
/* 49:   */   public void setNode(INode node)
/* 50:   */   {
/* 51:55 */     this.node = ((TileEntityTransferNodeEnergy)node);
/* 52:   */   }
/* 53:   */   
/* 54:   */   public INode getNode()
/* 55:   */   {
/* 56:60 */     return this.node;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public boolean transferTo(INodeBuffer receptor, int no)
/* 60:   */   {
/* 61:66 */     if ((isEmpty()) || (!getBufferType().equals(receptor.getBufferType()))) {
/* 62:67 */       return false;
/* 63:   */     }
/* 64:69 */     EnergyStorage t = (EnergyStorage)receptor.getBuffer();
/* 65:   */     
/* 66:71 */     int e = t.receiveEnergy(getBuffer().extractEnergy(no * 240, true), true);
/* 67:72 */     if (e <= 0) {
/* 68:73 */       return false;
/* 69:   */     }
/* 70:75 */     t.receiveEnergy(getBuffer().extractEnergy(e, false), false);
/* 71:   */     
/* 72:77 */     return true;
/* 73:   */   }
/* 74:   */   
/* 75:   */   public synchronized Object recieve(Object a)
/* 76:   */   {
/* 77:83 */     if (!(a instanceof Integer)) {
/* 78:84 */       return a;
/* 79:   */     }
/* 80:86 */     int c = ((Integer)a).intValue();
/* 81:87 */     int b = this.node.powerHandler.receiveEnergy(c, false);
/* 82:   */     
/* 83:89 */     return Integer.valueOf(c - b);
/* 84:   */   }
/* 85:   */   
/* 86:   */   public void markDirty()
/* 87:   */   {
/* 88:94 */     this.node.bufferChanged();
/* 89:   */   }
/* 90:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.EnergyBuffer
 * JD-Core Version:    0.7.0.1
 */