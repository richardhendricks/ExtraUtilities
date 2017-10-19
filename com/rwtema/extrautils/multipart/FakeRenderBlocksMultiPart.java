/*  1:   */ package com.rwtema.extrautils.multipart;
/*  2:   */ 
/*  3:   */ import codechicken.lib.vec.Cuboid6;
/*  4:   */ import codechicken.lib.vec.Vector3;
/*  5:   */ import codechicken.microblock.FaceMicroblockClient;
/*  6:   */ import codechicken.microblock.HollowMicroblockClient;
/*  7:   */ import codechicken.microblock.MicroMaterialRegistry;
/*  8:   */ import codechicken.microblock.MicroMaterialRegistry.IMicroMaterial;
/*  9:   */ import codechicken.microblock.Microblock;
/* 10:   */ import codechicken.multipart.TMultiPart;
/* 11:   */ import codechicken.multipart.TileMultipart;
/* 12:   */ import com.rwtema.extrautils.block.render.FakeRenderBlocks;
/* 13:   */ import cpw.mods.fml.relauncher.Side;
/* 14:   */ import cpw.mods.fml.relauncher.SideOnly;
/* 15:   */ import net.minecraft.item.ItemBlock;
/* 16:   */ import net.minecraft.item.ItemStack;
/* 17:   */ import net.minecraft.tileentity.TileEntity;
/* 18:   */ import net.minecraft.world.IBlockAccess;
/* 19:   */ import scala.collection.Iterator;
/* 20:   */ import scala.collection.Seq;
/* 21:   */ 
/* 22:   */ @SideOnly(Side.CLIENT)
/* 23:   */ public class FakeRenderBlocksMultiPart
/* 24:   */   extends FakeRenderBlocks
/* 25:   */ {
/* 26:   */   public int getSideFromBounds(Cuboid6 bounds)
/* 27:   */   {
/* 28:18 */     if (bounds.max.y != 1.0D) {
/* 29:19 */       return 0;
/* 30:   */     }
/* 31:22 */     if (bounds.min.y != 0.0D) {
/* 32:23 */       return 1;
/* 33:   */     }
/* 34:26 */     if (bounds.max.z != 1.0D) {
/* 35:27 */       return 2;
/* 36:   */     }
/* 37:30 */     if (bounds.min.z != 0.0D) {
/* 38:31 */       return 3;
/* 39:   */     }
/* 40:34 */     if (bounds.max.x != 1.0D) {
/* 41:35 */       return 4;
/* 42:   */     }
/* 43:38 */     if (bounds.min.x != 0.0D) {
/* 44:39 */       return 5;
/* 45:   */     }
/* 46:42 */     return -1;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public boolean matchBlock(int side2, int x2, int y2, int z2)
/* 50:   */   {
/* 51:47 */     if (this.isOpaque)
/* 52:   */     {
/* 53:48 */       TileEntity tile_base = this.blockAccess.getTileEntity(x2, y2, z2);
/* 54:50 */       if ((tile_base != null) && ((tile_base instanceof TileMultipart)))
/* 55:   */       {
/* 56:51 */         TileMultipart tile = (TileMultipart)tile_base;
/* 57:52 */         Iterator<TMultiPart> parts = tile.partList().toIterator();
/* 58:54 */         while (parts.hasNext())
/* 59:   */         {
/* 60:55 */           TMultiPart part = (TMultiPart)parts.next();
/* 61:57 */           if ((((part instanceof FaceMicroblockClient)) || ((part instanceof HollowMicroblockClient))) && (
/* 62:58 */             (side2 == -1) || (getSideFromBounds(((Microblock)part).getBounds()) == side2)))
/* 63:   */           {
/* 64:59 */             ItemStack t = MicroMaterialRegistry.getMaterial(((Microblock)part).getMaterial()).getItem();
/* 65:61 */             if (((t.getItem() instanceof ItemBlock)) && 
/* 66:62 */               (this.curBlock == ((ItemBlock)t.getItem()).field_150939_a) && (t.getItemDamage() == this.curMeta)) {
/* 67:63 */               return true;
/* 68:   */             }
/* 69:   */           }
/* 70:   */         }
/* 71:   */       }
/* 72:   */     }
/* 73:72 */     return super.matchBlock(side2, x2, y2, z2);
/* 74:   */   }
/* 75:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.multipart.FakeRenderBlocksMultiPart
 * JD-Core Version:    0.7.0.1
 */