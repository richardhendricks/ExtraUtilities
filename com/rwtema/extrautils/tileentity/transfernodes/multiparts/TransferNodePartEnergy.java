/*  1:   */ package com.rwtema.extrautils.tileentity.transfernodes.multiparts;
/*  2:   */ 
/*  3:   */ import codechicken.lib.vec.Cuboid6;
/*  4:   */ import codechicken.multipart.TMultiPart;
/*  5:   */ import cofh.api.energy.IEnergyHandler;
/*  6:   */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNode;
/*  7:   */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNodeEnergy;
/*  8:   */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeEnergy;
/*  9:   */ import cpw.mods.fml.common.Optional.InterfaceList;
/* 10:   */ import java.util.ArrayList;
/* 11:   */ import java.util.List;
/* 12:   */ import net.minecraftforge.common.util.ForgeDirection;
/* 13:   */ 
/* 14:   */ @Optional.InterfaceList({@cpw.mods.fml.common.Optional.Interface(iface="buildcraft.api.mj.ISidedBatteryProvider", modid="BuildCraftAPI|power")})
/* 15:   */ public class TransferNodePartEnergy
/* 16:   */   extends TransferNodePart
/* 17:   */   implements INodeEnergy, IEnergyHandler
/* 18:   */ {
/* 19:   */   public TransferNodePartEnergy(TileEntityTransferNode node)
/* 20:   */   {
/* 21:18 */     super(node);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public TransferNodePartEnergy()
/* 25:   */   {
/* 26:22 */     super(new TileEntityTransferNodeEnergy());
/* 27:   */   }
/* 28:   */   
/* 29:   */   public TransferNodePartEnergy(int meta, TileEntityTransferNodeEnergy node)
/* 30:   */   {
/* 31:26 */     super(meta, node);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Iterable<Cuboid6> getOcclusionBoxes()
/* 35:   */   {
/* 36:31 */     List<Cuboid6> s = new ArrayList();
/* 37:32 */     s.add(new Cuboid6(0.1875D, 0.1875D, 0.1875D, 0.8125D, 0.8125D, 0.8125D));
/* 38:33 */     return s;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public TileEntityTransferNodeEnergy getNode()
/* 42:   */   {
/* 43:38 */     return (TileEntityTransferNodeEnergy)this.node;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public boolean occlusionTest(TMultiPart npart)
/* 47:   */   {
/* 48:43 */     return super.occlusionTest(npart);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public String getType()
/* 52:   */   {
/* 53:48 */     return "extrautils:transfer_node_energy";
/* 54:   */   }
/* 55:   */   
/* 56:   */   public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
/* 57:   */   {
/* 58:53 */     if (isBlocked(from)) {
/* 59:54 */       return 0;
/* 60:   */     }
/* 61:56 */     return getNode().receiveEnergy(from, maxReceive, simulate);
/* 62:   */   }
/* 63:   */   
/* 64:   */   public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
/* 65:   */   {
/* 66:62 */     if (isBlocked(from)) {
/* 67:63 */       return 0;
/* 68:   */     }
/* 69:65 */     return getNode().extractEnergy(from, maxExtract, simulate);
/* 70:   */   }
/* 71:   */   
/* 72:   */   public boolean canConnectEnergy(ForgeDirection from)
/* 73:   */   {
/* 74:71 */     return (!isBlocked(from)) && (getNode().canConnectEnergy(from));
/* 75:   */   }
/* 76:   */   
/* 77:   */   public int getEnergyStored(ForgeDirection from)
/* 78:   */   {
/* 79:76 */     if (isBlocked(from)) {
/* 80:77 */       return 0;
/* 81:   */     }
/* 82:79 */     return getNode().getEnergyStored(from);
/* 83:   */   }
/* 84:   */   
/* 85:   */   public int getMaxEnergyStored(ForgeDirection from)
/* 86:   */   {
/* 87:85 */     if (isBlocked(from)) {
/* 88:86 */       return 0;
/* 89:   */     }
/* 90:88 */     return getNode().getMaxEnergyStored(from);
/* 91:   */   }
/* 92:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.multiparts.TransferNodePartEnergy
 * JD-Core Version:    0.7.0.1
 */