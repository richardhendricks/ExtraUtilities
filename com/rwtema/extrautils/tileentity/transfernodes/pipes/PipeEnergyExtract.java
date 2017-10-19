/*  1:   */ package com.rwtema.extrautils.tileentity.transfernodes.pipes;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.tileentity.transfernodes.BlockTransferPipe;
/*  4:   */ import net.minecraft.util.IIcon;
/*  5:   */ import net.minecraftforge.common.util.ForgeDirection;
/*  6:   */ 
/*  7:   */ public class PipeEnergyExtract
/*  8:   */   extends PipeEnergy
/*  9:   */ {
/* 10:   */   public static final String name = "Energy_Extract";
/* 11:   */   
/* 12:   */   public PipeEnergyExtract()
/* 13:   */   {
/* 14:11 */     super("Energy_Extract");
/* 15:   */   }
/* 16:   */   
/* 17:   */   public IIcon baseTexture()
/* 18:   */   {
/* 19:16 */     return BlockTransferPipe.pipes_energy_extract;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public IIcon pipeTexture(ForgeDirection dir, boolean blocked)
/* 23:   */   {
/* 24:21 */     return BlockTransferPipe.pipes_energy_extract;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public IIcon invPipeTexture(ForgeDirection dir)
/* 28:   */   {
/* 29:26 */     return BlockTransferPipe.pipes_energy_extract;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public IIcon socketTexture(ForgeDirection dir)
/* 33:   */   {
/* 34:31 */     return BlockTransferPipe.pipes_nozzle_energy_extract;
/* 35:   */   }
/* 36:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.pipes.PipeEnergyExtract
 * JD-Core Version:    0.7.0.1
 */