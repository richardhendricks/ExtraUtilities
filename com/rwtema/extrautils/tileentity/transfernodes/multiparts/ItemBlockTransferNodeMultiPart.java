/*  1:   */ package com.rwtema.extrautils.tileentity.transfernodes.multiparts;
/*  2:   */ 
/*  3:   */ import codechicken.lib.vec.BlockCoord;
/*  4:   */ import codechicken.multipart.TMultiPart;
/*  5:   */ import com.rwtema.extrautils.ExtraUtils;
/*  6:   */ import com.rwtema.extrautils.multipart.ItemBlockMultiPart;
/*  7:   */ import net.minecraft.block.Block;
/*  8:   */ import net.minecraft.item.ItemStack;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ 
/* 11:   */ public class ItemBlockTransferNodeMultiPart
/* 12:   */   extends ItemBlockMultiPart
/* 13:   */ {
/* 14:   */   public ItemBlockTransferNodeMultiPart(Block par1)
/* 15:   */   {
/* 16:15 */     super(par1);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public TMultiPart createMultiPart(World world, BlockCoord pos, ItemStack item, int side)
/* 20:   */   {
/* 21:20 */     int metadata = item.getItemDamage() & 0xF;
/* 22:22 */     if (metadata > 12) {
/* 23:23 */       return null;
/* 24:   */     }
/* 25:26 */     if (this.field_150939_a == ExtraUtils.transferNode)
/* 26:   */     {
/* 27:   */       TransferNodePart pipePart;
/* 28:   */       TransferNodePart pipePart;
/* 29:29 */       if (metadata < 6)
/* 30:   */       {
/* 31:30 */         pipePart = new TransferNodePartInventory();
/* 32:   */       }
/* 33:   */       else
/* 34:   */       {
/* 35:   */         TransferNodePart pipePart;
/* 36:31 */         if (metadata < 12)
/* 37:   */         {
/* 38:32 */           pipePart = new TransferNodePartLiquid();
/* 39:   */         }
/* 40:   */         else
/* 41:   */         {
/* 42:   */           TransferNodePart pipePart;
/* 43:33 */           if (metadata == 13) {
/* 44:34 */             pipePart = new TransferNodePartHyperEnergy();
/* 45:   */           } else {
/* 46:36 */             pipePart = new TransferNodePartEnergy();
/* 47:   */           }
/* 48:   */         }
/* 49:   */       }
/* 50:38 */       if (metadata < 12) {
/* 51:39 */         metadata += net.minecraft.util.Facing.oppositeSide[side];
/* 52:   */       }
/* 53:42 */       pipePart.meta = ((byte)metadata);
/* 54:43 */       return pipePart;
/* 55:   */     }
/* 56:44 */     if (this.field_150939_a == ExtraUtils.transferNodeRemote)
/* 57:   */     {
/* 58:   */       TransferNodePart pipePart;
/* 59:   */       TransferNodePart pipePart;
/* 60:47 */       if (metadata < 6)
/* 61:   */       {
/* 62:48 */         pipePart = new TransferNodePartInventoryRemote();
/* 63:   */       }
/* 64:   */       else
/* 65:   */       {
/* 66:   */         TransferNodePart pipePart;
/* 67:49 */         if (metadata < 12) {
/* 68:50 */           pipePart = new TransferNodePartLiquidRemote();
/* 69:   */         } else {
/* 70:52 */           pipePart = new TransferNodePartEnergy();
/* 71:   */         }
/* 72:   */       }
/* 73:55 */       if (metadata < 12) {
/* 74:56 */         metadata += net.minecraft.util.Facing.oppositeSide[side];
/* 75:   */       }
/* 76:59 */       pipePart.meta = ((byte)metadata);
/* 77:60 */       return pipePart;
/* 78:   */     }
/* 79:63 */     return null;
/* 80:   */   }
/* 81:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.multiparts.ItemBlockTransferNodeMultiPart
 * JD-Core Version:    0.7.0.1
 */