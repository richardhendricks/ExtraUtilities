/*  1:   */ package com.rwtema.extrautils.tileentity.enderconstructor;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.dynamicgui.DynamicContainer;
/*  4:   */ import com.rwtema.extrautils.dynamicgui.WidgetProgressArrow;
/*  5:   */ import com.rwtema.extrautils.dynamicgui.WidgetSlot;
/*  6:   */ import com.rwtema.extrautils.dynamicgui.WidgetSlotGhost;
/*  7:   */ import com.rwtema.extrautils.dynamicgui.WidgetSlotRespectsInsertExtract;
/*  8:   */ import com.rwtema.extrautils.dynamicgui.WidgetTextData;
/*  9:   */ import invtweaks.api.container.InventoryContainer;
/* 10:   */ import java.util.List;
/* 11:   */ import net.minecraft.entity.player.EntityPlayer;
/* 12:   */ import net.minecraft.inventory.IInventory;
/* 13:   */ import net.minecraft.item.ItemStack;
/* 14:   */ 
/* 15:   */ @InventoryContainer
/* 16:   */ public class DynamicContainerEnderConstructor
/* 17:   */   extends DynamicContainer
/* 18:   */ {
/* 19:   */   public TileEnderConstructor tile;
/* 20:   */   public IInventory player;
/* 21:   */   
/* 22:   */   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
/* 23:   */   {
/* 24:16 */     return super.transferStackInSlot(par1EntityPlayer, par2);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public DynamicContainerEnderConstructor(IInventory player, TileEnderConstructor tile)
/* 28:   */   {
/* 29:23 */     this.tile = tile;
/* 30:24 */     this.player = player;
/* 31:26 */     for (int j = 0; j < 3; j++) {
/* 32:27 */       for (int i = 0; i < 3; i++) {
/* 33:28 */         this.widgets.add(new WidgetSlot(tile.inv, i + j * 3, 30 + i * 18, 17 + j * 18));
/* 34:   */       }
/* 35:   */     }
/* 36:31 */     this.widgets.add(new WidgetSlotRespectsInsertExtract(tile, 9, 124, 35));
/* 37:32 */     this.widgets.add(new Arrow(tile, 90, 35));
/* 38:33 */     this.widgets.add(new WidgetSlotGhost(tile.inv, 9, 92, 13));
/* 39:   */     
/* 40:35 */     this.widgets.add(new WidgetEFText(tile, 9, 75, 124));
/* 41:   */     
/* 42:   */ 
/* 43:38 */     cropAndAddPlayerSlots(player);
/* 44:39 */     validate();
/* 45:   */   }
/* 46:   */   
/* 47:   */   public boolean canInteractWith(EntityPlayer entityplayer)
/* 48:   */   {
/* 49:44 */     return true;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public static class Arrow
/* 53:   */     extends WidgetProgressArrow
/* 54:   */   {
/* 55:   */     TileEnderConstructor tile;
/* 56:   */     
/* 57:   */     public Arrow(TileEnderConstructor tile, int x, int y)
/* 58:   */     {
/* 59:51 */       super(y);
/* 60:52 */       this.tile = tile;
/* 61:   */     }
/* 62:   */     
/* 63:   */     public int getWidth()
/* 64:   */     {
/* 65:57 */       return this.tile.getDisplayProgress();
/* 66:   */     }
/* 67:   */   }
/* 68:   */   
/* 69:   */   public static class WidgetEFText
/* 70:   */     extends WidgetTextData
/* 71:   */   {
/* 72:   */     IEnderFluxHandler tile;
/* 73:   */     
/* 74:   */     public WidgetEFText(IEnderFluxHandler tile, int x, int y, int w)
/* 75:   */     {
/* 76:65 */       super(y, w);
/* 77:66 */       this.tile = tile;
/* 78:   */     }
/* 79:   */     
/* 80:   */     public int getNumParams()
/* 81:   */     {
/* 82:71 */       return 1;
/* 83:   */     }
/* 84:   */     
/* 85:   */     public Object[] getData()
/* 86:   */     {
/* 87:76 */       return new Object[] { Float.valueOf(this.tile.getAmountRequested()), Byte.valueOf((byte)(this.tile.isActive() ? 1 : 0)) };
/* 88:   */     }
/* 89:   */     
/* 90:   */     public String getConstructedText()
/* 91:   */     {
/* 92:81 */       if ((this.curData == null) || (this.curData.length != 2) || (!(this.curData[0] instanceof Float)) || (!(this.curData[1] instanceof Boolean))) {
/* 93:82 */         return "";
/* 94:   */       }
/* 95:83 */       if (((Byte)this.curData[1]).byteValue() == 1) {
/* 96:84 */         return "Ender-Flux: " + ((Float)this.curData[0]).floatValue() / 1000.0F + " EF";
/* 97:   */       }
/* 98:85 */       return "";
/* 99:   */     }
/* :0:   */   }
/* :1:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.enderconstructor.DynamicContainerEnderConstructor
 * JD-Core Version:    0.7.0.1
 */