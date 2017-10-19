/*  1:   */ package com.rwtema.extrautils.gui;
/*  2:   */ 
/*  3:   */ import invtweaks.api.container.ContainerSection;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.HashMap;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import net.minecraft.entity.player.InventoryPlayer;
/*  9:   */ import net.minecraft.inventory.Container;
/* 10:   */ import net.minecraft.inventory.Slot;
/* 11:   */ 
/* 12:   */ public class InventoryTweaksHelper
/* 13:   */ {
/* 14:   */   public static Map<ContainerSection, List<Slot>> getSlots(Container inventory)
/* 15:   */   {
/* 16:15 */     return getSlots(inventory, false);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static Map<ContainerSection, List<Slot>> getSlots(Container inventory, boolean playerInvOnly)
/* 20:   */   {
/* 21:20 */     Map<ContainerSection, List<Slot>> map = new HashMap();
/* 22:22 */     for (Slot s : inventory.inventorySlots)
/* 23:   */     {
/* 24:23 */       ContainerSection c = null;
/* 25:25 */       if ((s.inventory instanceof InventoryPlayer))
/* 26:   */       {
/* 27:26 */         putSlot(map, s, ContainerSection.INVENTORY);
/* 28:27 */         if (s.slotNumber < 9) {
/* 29:28 */           putSlot(map, s, ContainerSection.INVENTORY_HOTBAR);
/* 30:29 */         } else if (s.slotNumber < 36) {
/* 31:30 */           putSlot(map, s, ContainerSection.INVENTORY_NOT_HOTBAR);
/* 32:   */         } else {
/* 33:32 */           putSlot(map, s, ContainerSection.ARMOR);
/* 34:   */         }
/* 35:   */       }
/* 36:33 */       else if (!playerInvOnly)
/* 37:   */       {
/* 38:34 */         putSlot(map, s, ContainerSection.CHEST);
/* 39:   */       }
/* 40:   */     }
/* 41:38 */     return map;
/* 42:   */   }
/* 43:   */   
/* 44:   */   private static void putSlot(Map<ContainerSection, List<Slot>> map, Slot s, ContainerSection c)
/* 45:   */   {
/* 46:42 */     List<Slot> list = (List)map.get(c);
/* 47:43 */     if (list == null) {
/* 48:44 */       list = new ArrayList();
/* 49:   */     }
/* 50:45 */     list.add(s);
/* 51:46 */     map.put(c, list);
/* 52:   */   }
/* 53:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.gui.InventoryTweaksHelper
 * JD-Core Version:    0.7.0.1
 */