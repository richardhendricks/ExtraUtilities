/*  1:   */ package com.rwtema.extrautils.multipart;
/*  2:   */ 
/*  3:   */ import codechicken.lib.vec.Cuboid6;
/*  4:   */ import codechicken.lib.vec.Vector3;
/*  5:   */ import codechicken.microblock.BlockMicroMaterial;
/*  6:   */ import codechicken.microblock.MaterialRenderHelper;
/*  7:   */ import codechicken.microblock.MaterialRenderHelper.;
/*  8:   */ import com.rwtema.extrautils.block.BlockColor;
/*  9:   */ import com.rwtema.extrautils.block.BlockColorData;
/* 10:   */ import com.rwtema.extrautils.helper.XUHelperClient;
/* 11:   */ import com.rwtema.extrautils.tileentity.TileEntityBlockColorData;
/* 12:   */ import cpw.mods.fml.relauncher.Side;
/* 13:   */ import cpw.mods.fml.relauncher.SideOnly;
/* 14:   */ import net.minecraft.entity.Entity;
/* 15:   */ import net.minecraft.entity.player.EntityPlayer;
/* 16:   */ import net.minecraft.tileentity.TileEntity;
/* 17:   */ import net.minecraft.world.World;
/* 18:   */ 
/* 19:   */ public class ColoredBlockMicroMaterial
/* 20:   */   extends BlockMicroMaterial
/* 21:   */ {
/* 22:   */   public ColoredBlockMicroMaterial(BlockColor block, int meta)
/* 23:   */   {
/* 24:18 */     super(block, meta);
/* 25:   */   }
/* 26:   */   
/* 27:   */   @SideOnly(Side.CLIENT)
/* 28:   */   public void renderMicroFace(Vector3 pos, int pass, Cuboid6 bounds)
/* 29:   */   {
/* 30:25 */     float[] col = BlockColor.initColor[meta()];
/* 31:28 */     if ((XUHelperClient.clientPlayer().getEntityWorld() != null) && (pass != -1))
/* 32:   */     {
/* 33:29 */       col = BlockColorData.getColorData(XUHelperClient.clientPlayer().getEntityWorld(), (int)pos.x, (int)pos.y, (int)pos.z, meta());
/* 34:   */     }
/* 35:   */     else
/* 36:   */     {
/* 37:31 */       Entity holder = XUHelperClient.clientPlayer();
/* 38:33 */       if (holder != null)
/* 39:   */       {
/* 40:34 */         TileEntity tiledata = holder.worldObj.getTileEntity(BlockColorData.dataBlockX((int)Math.floor(holder.posX)), BlockColorData.dataBlockY((int)holder.posY), BlockColorData.dataBlockZ((int)Math.floor(holder.posZ)));
/* 41:36 */         if ((tiledata instanceof TileEntityBlockColorData)) {
/* 42:37 */           col = ((TileEntityBlockColorData)tiledata).palette[meta()];
/* 43:   */         }
/* 44:   */       }
/* 45:   */     }
/* 46:42 */     int c = (int)(col[0] * 255.0F) << 24 | (int)(col[1] * 255.0F) << 16 | (int)(col[2] * 255.0F) << 8 | 0xFF;
/* 47:   */     
/* 48:   */ 
/* 49:45 */     MaterialRenderHelper.start(pos, pass, icont()).blockColour(c).lighting().render();
/* 50:   */   }
/* 51:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.multipart.ColoredBlockMicroMaterial
 * JD-Core Version:    0.7.0.1
 */