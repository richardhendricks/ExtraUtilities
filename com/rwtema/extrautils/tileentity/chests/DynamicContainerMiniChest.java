/*  1:   */ package com.rwtema.extrautils.tileentity.chests;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.dynamicgui.DynamicContainer;
/*  4:   */ import com.rwtema.extrautils.dynamicgui.WidgetDescPacket;
/*  5:   */ import com.rwtema.extrautils.dynamicgui.WidgetSlot;
/*  6:   */ import java.util.List;
/*  7:   */ import net.minecraft.entity.player.EntityPlayer;
/*  8:   */ import net.minecraft.inventory.IInventory;
/*  9:   */ import net.minecraft.nbt.NBTTagCompound;
/* 10:   */ 
/* 11:   */ public class DynamicContainerMiniChest
/* 12:   */   extends DynamicContainer
/* 13:   */ {
/* 14:   */   private final TileMiniChest inv;
/* 15:   */   
/* 16:   */   public DynamicContainerMiniChest(IInventory player, TileMiniChest inv)
/* 17:   */   {
/* 18:15 */     this.inv = inv;
/* 19:16 */     int midPoint = 76;
/* 20:17 */     this.widgets.add(new WidgetSlot(inv, 0, midPoint, 19));
/* 21:   */     
/* 22:   */ 
/* 23:20 */     this.widgets.add(new WidgetDescPacket()
/* 24:   */     {
/* 25:   */       public NBTTagCompound getDescriptionPacket(boolean changesOnly)
/* 26:   */       {
/* 27:23 */         if ((changesOnly) || (!DynamicContainerMiniChest.this.inv.isInventoryNameLocalized())) {
/* 28:24 */           return null;
/* 29:   */         }
/* 30:26 */         NBTTagCompound tag = new NBTTagCompound();
/* 31:27 */         tag.setString("Name", DynamicContainerMiniChest.this.inv.getInventoryName());
/* 32:28 */         return tag;
/* 33:   */       }
/* 34:   */       
/* 35:   */       public void handleDescriptionPacket(NBTTagCompound packet)
/* 36:   */       {
/* 37:33 */         String name = packet.getString("Name");
/* 38:34 */         if (name.length() > 0) {
/* 39:35 */           DynamicContainerMiniChest.this.inv.func_145976_a(name);
/* 40:   */         }
/* 41:   */       }
/* 42:38 */     });
/* 43:39 */     addTitle(inv.getInventoryName(), !inv.isInventoryNameLocalized());
/* 44:   */     
/* 45:41 */     cropAndAddPlayerSlots(player);
/* 46:42 */     validate();
/* 47:   */   }
/* 48:   */   
/* 49:   */   public boolean canInteractWith(EntityPlayer player)
/* 50:   */   {
/* 51:47 */     return this.inv.isUseableByPlayer(player);
/* 52:   */   }
/* 53:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.chests.DynamicContainerMiniChest
 * JD-Core Version:    0.7.0.1
 */