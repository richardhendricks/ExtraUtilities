/*  1:   */ package com.rwtema.extrautils.tileentity.transfernodes.multiparts;
/*  2:   */ 
/*  3:   */ import codechicken.lib.vec.BlockCoord;
/*  4:   */ import codechicken.multipart.MultiPartRegistry;
/*  5:   */ import codechicken.multipart.MultiPartRegistry.IPartConverter;
/*  6:   */ import codechicken.multipart.MultiPartRegistry.IPartFactory;
/*  7:   */ import codechicken.multipart.TMultiPart;
/*  8:   */ import com.rwtema.extrautils.ExtraUtils;
/*  9:   */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityFilterPipe;
/* 10:   */ import java.util.HashSet;
/* 11:   */ import java.util.Set;
/* 12:   */ import net.minecraft.block.Block;
/* 13:   */ import net.minecraft.inventory.InventoryBasic;
/* 14:   */ import net.minecraft.world.World;
/* 15:   */ 
/* 16:   */ public class RegisterPipeParts
/* 17:   */   implements MultiPartRegistry.IPartFactory, MultiPartRegistry.IPartConverter
/* 18:   */ {
/* 19:   */   public TMultiPart createPart(String name, boolean client)
/* 20:   */   {
/* 21:20 */     if (name.equals("extrautils:transfer_pipe_filter")) {
/* 22:21 */       return new FilterPipePart();
/* 23:   */     }
/* 24:24 */     if (name.equals("extrautils:transfer_pipe")) {
/* 25:25 */       return new PipePart();
/* 26:   */     }
/* 27:28 */     return null;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void init()
/* 31:   */   {
/* 32:32 */     MultiPartRegistry.registerConverter(this);
/* 33:33 */     MultiPartRegistry.registerParts(this, new String[] { "extrautils:transfer_pipe", "extrautils:transfer_pipe_filter" });
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Iterable<Block> blockTypes()
/* 37:   */   {
/* 38:39 */     Set<Block> set = new HashSet();
/* 39:40 */     set.add(ExtraUtils.transferPipe);
/* 40:41 */     set.add(ExtraUtils.transferPipe2);
/* 41:42 */     return set;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public TMultiPart convert(World world, BlockCoord pos)
/* 45:   */   {
/* 46:47 */     Block id = world.getBlock(pos.x, pos.y, pos.z);
/* 47:48 */     int meta = world.getBlockMetadata(pos.x, pos.y, pos.z);
/* 48:50 */     if ((id == ExtraUtils.transferPipe) || (id == ExtraUtils.transferPipe2))
/* 49:   */     {
/* 50:51 */       if (id == ExtraUtils.transferPipe2) {
/* 51:52 */         meta += 16;
/* 52:   */       }
/* 53:54 */       if (meta == 9)
/* 54:   */       {
/* 55:55 */         if ((world.getTileEntity(pos.x, pos.y, pos.z) instanceof TileEntityFilterPipe))
/* 56:   */         {
/* 57:56 */           InventoryBasic t = ((TileEntityFilterPipe)world.getTileEntity(pos.x, pos.y, pos.z)).items;
/* 58:57 */           return new FilterPipePart(t);
/* 59:   */         }
/* 60:60 */         return new FilterPipePart();
/* 61:   */       }
/* 62:62 */       return new PipePart(meta);
/* 63:   */     }
/* 64:66 */     return null;
/* 65:   */   }
/* 66:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.multiparts.RegisterPipeParts
 * JD-Core Version:    0.7.0.1
 */