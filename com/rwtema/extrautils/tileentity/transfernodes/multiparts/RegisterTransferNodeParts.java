/*   1:    */ package com.rwtema.extrautils.tileentity.transfernodes.multiparts;
/*   2:    */ 
/*   3:    */ import codechicken.lib.vec.BlockCoord;
/*   4:    */ import codechicken.multipart.MultiPartRegistry;
/*   5:    */ import codechicken.multipart.MultiPartRegistry.IPartConverter;
/*   6:    */ import codechicken.multipart.MultiPartRegistry.IPartFactory;
/*   7:    */ import codechicken.multipart.TMultiPart;
/*   8:    */ import com.rwtema.extrautils.ExtraUtils;
/*   9:    */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityRetrievalNodeInventory;
/*  10:    */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityRetrievalNodeLiquid;
/*  11:    */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNodeEnergy;
/*  12:    */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNodeHyperEnergy;
/*  13:    */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNodeInventory;
/*  14:    */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNodeLiquid;
/*  15:    */ import java.util.HashSet;
/*  16:    */ import java.util.Set;
/*  17:    */ import net.minecraft.block.Block;
/*  18:    */ import net.minecraft.world.World;
/*  19:    */ 
/*  20:    */ public class RegisterTransferNodeParts
/*  21:    */   implements MultiPartRegistry.IPartFactory, MultiPartRegistry.IPartConverter
/*  22:    */ {
/*  23:    */   public void init()
/*  24:    */   {
/*  25: 18 */     MultiPartRegistry.registerConverter(this);
/*  26: 19 */     MultiPartRegistry.registerParts(this, new String[] { "extrautils:transfer_node_inv", "extrautils:transfer_node_liquid", "extrautils:transfer_node_energy", "extrautils:transfer_node_inv_remote", "extrautils:transfer_node_liquid_remote", "extrautils:transfer_node_energy_hyper" });
/*  27:    */   }
/*  28:    */   
/*  29:    */   public Iterable<Block> blockTypes()
/*  30:    */   {
/*  31: 31 */     Set<Block> s = new HashSet();
/*  32: 32 */     s.add(ExtraUtils.transferNode);
/*  33: 33 */     s.add(ExtraUtils.transferNodeRemote);
/*  34: 34 */     return s;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public TMultiPart convert(World world, BlockCoord pos)
/*  38:    */   {
/*  39: 39 */     Block id = world.getBlock(pos.x, pos.y, pos.z);
/*  40: 40 */     int meta = world.getBlockMetadata(pos.x, pos.y, pos.z);
/*  41: 42 */     if (id == ExtraUtils.transferNode) {
/*  42: 43 */       if (meta < 6)
/*  43:    */       {
/*  44: 44 */         if ((world.getTileEntity(pos.x, pos.y, pos.z) instanceof TileEntityTransferNodeInventory)) {
/*  45: 45 */           return new TransferNodePartInventory(meta, (TileEntityTransferNodeInventory)world.getTileEntity(pos.x, pos.y, pos.z));
/*  46:    */         }
/*  47:    */       }
/*  48: 47 */       else if (meta < 12)
/*  49:    */       {
/*  50: 48 */         if ((world.getTileEntity(pos.x, pos.y, pos.z) instanceof TileEntityTransferNodeLiquid)) {
/*  51: 49 */           return new TransferNodePartLiquid(meta, (TileEntityTransferNodeLiquid)world.getTileEntity(pos.x, pos.y, pos.z));
/*  52:    */         }
/*  53:    */       }
/*  54: 52 */       else if (meta == 12)
/*  55:    */       {
/*  56: 53 */         if ((world.getTileEntity(pos.x, pos.y, pos.z) instanceof TileEntityTransferNodeEnergy)) {
/*  57: 54 */           return new TransferNodePartEnergy(meta, (TileEntityTransferNodeEnergy)world.getTileEntity(pos.x, pos.y, pos.z));
/*  58:    */         }
/*  59:    */       }
/*  60: 57 */       else if ((meta == 13) && 
/*  61: 58 */         ((world.getTileEntity(pos.x, pos.y, pos.z) instanceof TileEntityTransferNodeHyperEnergy))) {
/*  62: 59 */         return new TransferNodePartHyperEnergy(meta, (TileEntityTransferNodeHyperEnergy)world.getTileEntity(pos.x, pos.y, pos.z));
/*  63:    */       }
/*  64:    */     }
/*  65: 64 */     if (id == ExtraUtils.transferNodeRemote) {
/*  66: 65 */       if (meta < 6)
/*  67:    */       {
/*  68: 66 */         if ((world.getTileEntity(pos.x, pos.y, pos.z) instanceof TileEntityRetrievalNodeInventory)) {
/*  69: 67 */           return new TransferNodePartInventoryRemote(meta, (TileEntityRetrievalNodeInventory)world.getTileEntity(pos.x, pos.y, pos.z));
/*  70:    */         }
/*  71:    */       }
/*  72: 69 */       else if (meta < 12)
/*  73:    */       {
/*  74: 70 */         if ((world.getTileEntity(pos.x, pos.y, pos.z) instanceof TileEntityRetrievalNodeLiquid)) {
/*  75: 71 */           return new TransferNodePartLiquidRemote(meta, (TileEntityRetrievalNodeLiquid)world.getTileEntity(pos.x, pos.y, pos.z));
/*  76:    */         }
/*  77:    */       }
/*  78: 73 */       else if ((meta == 12) && 
/*  79: 74 */         ((world.getTileEntity(pos.x, pos.y, pos.z) instanceof TileEntityTransferNodeEnergy))) {
/*  80: 75 */         return new TransferNodePartEnergy(meta, (TileEntityTransferNodeEnergy)world.getTileEntity(pos.x, pos.y, pos.z));
/*  81:    */       }
/*  82:    */     }
/*  83: 80 */     return null;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public TMultiPart createPart(String name, boolean client)
/*  87:    */   {
/*  88: 85 */     if (name.equals("extrautils:transfer_node_inv")) {
/*  89: 86 */       return new TransferNodePartInventory();
/*  90:    */     }
/*  91: 89 */     if (name.equals("extrautils:transfer_node_liquid")) {
/*  92: 90 */       return new TransferNodePartLiquid();
/*  93:    */     }
/*  94: 93 */     if (name.equals("extrautils:transfer_node_energy")) {
/*  95: 94 */       return new TransferNodePartEnergy();
/*  96:    */     }
/*  97: 97 */     if (name.equals("extrautils:transfer_node_inv_remote")) {
/*  98: 98 */       return new TransferNodePartInventoryRemote();
/*  99:    */     }
/* 100:101 */     if (name.equals("extrautils:transfer_node_liquid_remote")) {
/* 101:102 */       return new TransferNodePartLiquidRemote();
/* 102:    */     }
/* 103:105 */     if (name.equals("extrautils:transfer_node_energy_hyper")) {
/* 104:106 */       return new TransferNodePartHyperEnergy();
/* 105:    */     }
/* 106:109 */     return null;
/* 107:    */   }
/* 108:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.multiparts.RegisterTransferNodeParts
 * JD-Core Version:    0.7.0.1
 */