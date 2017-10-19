/*   1:    */ package com.rwtema.extrautils.tileentity.endercollector;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.EventHandlerEntityItemStealer;
/*   4:    */ import cpw.mods.fml.common.eventhandler.EventBus;
/*   5:    */ import cpw.mods.fml.common.eventhandler.EventPriority;
/*   6:    */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Collections;
/*   9:    */ import java.util.WeakHashMap;
/*  10:    */ import net.minecraft.entity.DataWatcher;
/*  11:    */ import net.minecraft.entity.Entity;
/*  12:    */ import net.minecraft.entity.item.EntityItem;
/*  13:    */ import net.minecraft.item.ItemStack;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ import net.minecraftforge.common.MinecraftForge;
/*  16:    */ import net.minecraftforge.event.entity.EntityJoinWorldEvent;
/*  17:    */ 
/*  18:    */ public class CollectorHandler
/*  19:    */ {
/*  20: 18 */   public static final WeakHashMap<World, WeakHashMap<TileEnderCollector, Object>> map = new WeakHashMap();
/*  21: 19 */   public static CollectorHandler INSTANCE = new CollectorHandler();
/*  22:    */   public static boolean dontCollect;
/*  23:    */   
/*  24:    */   static
/*  25:    */   {
/*  26: 22 */     MinecraftForge.EVENT_BUS.register(INSTANCE);
/*  27:    */   }
/*  28:    */   
/*  29: 27 */   private static final ArrayList<TileEnderCollector> collectors = new ArrayList(1);
/*  30:    */   
/*  31:    */   @SubscribeEvent(priority=EventPriority.LOWEST)
/*  32:    */   public void onEntityJoinWorld(EntityJoinWorldEvent event)
/*  33:    */   {
/*  34: 31 */     if (event.entity.worldObj.isClient) {
/*  35: 32 */       return;
/*  36:    */     }
/*  37: 34 */     if (dontCollect) {
/*  38: 35 */       return;
/*  39:    */     }
/*  40: 37 */     if (map.isEmpty()) {
/*  41: 38 */       return;
/*  42:    */     }
/*  43: 40 */     WeakHashMap<TileEnderCollector, Object> map = (WeakHashMap)map.get(event.entity.worldObj);
/*  44: 42 */     if ((map == null) || (map.isEmpty())) {
/*  45: 43 */       return;
/*  46:    */     }
/*  47: 45 */     Entity entity = event.entity;
/*  48: 47 */     if (!EventHandlerEntityItemStealer.isEntityItem(entity.getClass())) {
/*  49: 48 */       return;
/*  50:    */     }
/*  51: 50 */     ItemStack stack = entity.getDataWatcher().getWatchableObjectItemStack(10);
/*  52: 52 */     if (stack == null) {
/*  53: 53 */       return;
/*  54:    */     }
/*  55: 55 */     collectors.clear();
/*  56: 57 */     for (TileEnderCollector tileEnderCollector : map.keySet()) {
/*  57: 58 */       if (tileEnderCollector.inRange(entity)) {
/*  58: 59 */         collectors.add(tileEnderCollector);
/*  59:    */       }
/*  60:    */     }
/*  61: 63 */     if (collectors.isEmpty()) {
/*  62:    */       return;
/*  63:    */     }
/*  64:    */     EntityItem entityItem;
/*  65: 66 */     if (collectors.size() == 1)
/*  66:    */     {
/*  67: 67 */       ((TileEnderCollector)collectors.get(0)).grabEntity((EntityItem)entity);
/*  68:    */     }
/*  69:    */     else
/*  70:    */     {
/*  71: 69 */       Collections.shuffle(collectors);
/*  72: 70 */       entityItem = (EntityItem)entity;
/*  73: 72 */       for (TileEnderCollector collector : collectors)
/*  74:    */       {
/*  75: 73 */         collector.grabEntity(entityItem);
/*  76: 74 */         if (entity.isDead) {
/*  77:    */           break;
/*  78:    */         }
/*  79:    */       }
/*  80:    */     }
/*  81: 79 */     collectors.clear();
/*  82: 80 */     if (entity.isDead) {
/*  83: 81 */       event.setCanceled(true);
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   public static void register(TileEnderCollector tile)
/*  88:    */   {
/*  89: 86 */     World worldObj = tile.getWorldObj();
/*  90: 87 */     if ((worldObj == null) || (worldObj.isClient)) {
/*  91: 87 */       return;
/*  92:    */     }
/*  93: 88 */     getWorldMap(tile).put(tile, null);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static void unregister(TileEnderCollector tile)
/*  97:    */   {
/*  98: 92 */     World worldObj = tile.getWorldObj();
/*  99: 93 */     if ((worldObj == null) || (worldObj.isClient)) {
/* 100: 93 */       return;
/* 101:    */     }
/* 102: 94 */     getWorldMap(tile).remove(tile);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public static WeakHashMap<TileEnderCollector, Object> getWorldMap(TileEnderCollector tile)
/* 106:    */   {
/* 107: 98 */     WeakHashMap<TileEnderCollector, Object> worldMap = (WeakHashMap)map.get(tile.getWorldObj());
/* 108: 99 */     if (worldMap == null)
/* 109:    */     {
/* 110:100 */       worldMap = new WeakHashMap();
/* 111:101 */       map.put(tile.getWorldObj(), worldMap);
/* 112:    */     }
/* 113:103 */     return worldMap;
/* 114:    */   }
/* 115:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.endercollector.CollectorHandler
 * JD-Core Version:    0.7.0.1
 */