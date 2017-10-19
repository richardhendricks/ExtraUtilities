/*  1:   */ package com.rwtema.extrautils.tileentity.transfernodes;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeBuffer;
/*  4:   */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.ItemBufferRetrieval;
/*  5:   */ import net.minecraft.inventory.IInventory;
/*  6:   */ import net.minecraft.tileentity.TileEntity;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ import net.minecraftforge.common.util.ForgeDirection;
/*  9:   */ 
/* 10:   */ public class TileEntityRetrievalNodeInventory
/* 11:   */   extends TileEntityTransferNodeInventory
/* 12:   */ {
/* 13:   */   public TileEntityRetrievalNodeInventory()
/* 14:   */   {
/* 15:16 */     super("Inv_remote", new ItemBufferRetrieval());this.pr = 0.001F;this.pg = 1.0F;this.pb = 0.0F;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void processBuffer()
/* 19:   */   {
/* 20:21 */     if ((this.worldObj != null) && (!this.worldObj.isClient))
/* 21:   */     {
/* 22:22 */       if (this.coolDown > 0) {
/* 23:23 */         this.coolDown -= this.stepCoolDown;
/* 24:   */       }
/* 25:26 */       if (checkRedstone()) {
/* 26:27 */         return;
/* 27:   */       }
/* 28:30 */       while (this.coolDown <= 0)
/* 29:   */       {
/* 30:31 */         this.coolDown += baseMaxCoolDown;
/* 31:32 */         unloadbuffer();
/* 32:34 */         if (handleInventories()) {
/* 33:35 */           advPipeSearch();
/* 34:   */         }
/* 35:   */       }
/* 36:   */     }
/* 37:   */   }
/* 38:   */   
/* 39:   */   private void unloadbuffer()
/* 40:   */   {
/* 41:42 */     if (!this.buffer.isEmpty())
/* 42:   */     {
/* 43:43 */       ForgeDirection d = getNodeDir();
/* 44:44 */       TileEntity tile = this.worldObj.getTileEntity(this.xCoord + d.offsetX, this.yCoord + d.offsetY, this.zCoord + d.offsetZ);
/* 45:46 */       if ((tile != null) && ((tile instanceof IInventory))) {
/* 46:47 */         ((ItemBufferRetrieval)this.buffer).transferBack(tile, d.getOpposite(), this, this.xCoord, this.yCoord, this.zCoord);
/* 47:   */       }
/* 48:   */     }
/* 49:   */   }
/* 50:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.TileEntityRetrievalNodeInventory
 * JD-Core Version:    0.7.0.1
 */