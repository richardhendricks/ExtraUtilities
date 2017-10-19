/*  1:   */ package com.rwtema.extrautils.tileentity.transfernodes;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.FluidBufferRetrieval;
/*  4:   */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeBuffer;
/*  5:   */ import net.minecraft.world.World;
/*  6:   */ import net.minecraftforge.common.util.ForgeDirection;
/*  7:   */ import net.minecraftforge.fluids.FluidTank;
/*  8:   */ import net.minecraftforge.fluids.IFluidHandler;
/*  9:   */ 
/* 10:   */ public class TileEntityRetrievalNodeLiquid
/* 11:   */   extends TileEntityTransferNodeLiquid
/* 12:   */ {
/* 13:   */   public TileEntityRetrievalNodeLiquid()
/* 14:   */   {
/* 15:11 */     super("liquid_remote", new FluidBufferRetrieval());
/* 16:   */     
/* 17:   */ 
/* 18:   */ 
/* 19:15 */     this.pr = 0.001F;
/* 20:16 */     this.pg = 1.0F;
/* 21:17 */     this.pb = 1.0F;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void processBuffer()
/* 25:   */   {
/* 26:22 */     if ((this.worldObj != null) && (!this.worldObj.isClient))
/* 27:   */     {
/* 28:23 */       if (this.coolDown > 0) {
/* 29:24 */         this.coolDown -= this.stepCoolDown;
/* 30:   */       }
/* 31:27 */       if (checkRedstone()) {
/* 32:28 */         return;
/* 33:   */       }
/* 34:31 */       while (this.coolDown <= 0)
/* 35:   */       {
/* 36:32 */         this.coolDown += baseMaxCoolDown;
/* 37:33 */         unloadTank();
/* 38:35 */         if (handleInventories()) {
/* 39:36 */           advPipeSearch();
/* 40:   */         }
/* 41:   */       }
/* 42:   */     }
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void unloadTank()
/* 46:   */   {
/* 47:43 */     if (this.buffer.isEmpty()) {
/* 48:44 */       return;
/* 49:   */     }
/* 50:47 */     int dir = getBlockMetadata() % 6;
/* 51:48 */     ForgeDirection side = ForgeDirection.getOrientation(dir).getOpposite();
/* 52:51 */     if ((this.worldObj.getTileEntity(this.xCoord + net.minecraft.util.Facing.offsetsXForSide[dir], this.yCoord + net.minecraft.util.Facing.offsetsYForSide[dir], this.zCoord + net.minecraft.util.Facing.offsetsZForSide[dir]) instanceof IFluidHandler))
/* 53:   */     {
/* 54:52 */       IFluidHandler dest = (IFluidHandler)this.worldObj.getTileEntity(this.xCoord + net.minecraft.util.Facing.offsetsXForSide[dir], this.yCoord + net.minecraft.util.Facing.offsetsYForSide[dir], this.zCoord + net.minecraft.util.Facing.offsetsZForSide[dir]);
/* 55:   */       
/* 56:54 */       FluidTank tank = (FluidTank)this.buffer.getBuffer();
/* 57:55 */       int a = dest.fill(side, tank.getFluid(), initDirection());
/* 58:57 */       if (a > 0) {
/* 59:58 */         dest.fill(side, tank.drain(a, true), true);
/* 60:   */       }
/* 61:   */     }
/* 62:   */   }
/* 63:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.TileEntityRetrievalNodeLiquid
 * JD-Core Version:    0.7.0.1
 */