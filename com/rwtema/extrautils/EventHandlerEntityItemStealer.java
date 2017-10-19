/*   1:    */ package com.rwtema.extrautils;
/*   2:    */ 
/*   3:    */ import cpw.mods.fml.common.eventhandler.EventPriority;
/*   4:    */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.LinkedList;
/*   8:    */ import java.util.List;
/*   9:    */ import net.minecraft.entity.DataWatcher;
/*  10:    */ import net.minecraft.entity.Entity;
/*  11:    */ import net.minecraft.entity.item.EntityItem;
/*  12:    */ import net.minecraft.entity.item.EntityXPOrb;
/*  13:    */ import net.minecraft.entity.monster.EntitySilverfish;
/*  14:    */ import net.minecraft.item.ItemStack;
/*  15:    */ import net.minecraft.util.DamageSource;
/*  16:    */ import net.minecraft.world.World;
/*  17:    */ import net.minecraftforge.event.entity.EntityJoinWorldEvent;
/*  18:    */ 
/*  19:    */ public class EventHandlerEntityItemStealer
/*  20:    */ {
/*  21: 20 */   private static LinkedList<ItemStack> items = new LinkedList();
/*  22: 21 */   private static LinkedList<EntityItem> entityItems = new LinkedList();
/*  23: 23 */   private static boolean capturing = false;
/*  24: 25 */   private static boolean killExperience = false;
/*  25:    */   
/*  26:    */   public static void startCapture(boolean killExp)
/*  27:    */   {
/*  28: 28 */     killExperience = killExp;
/*  29: 29 */     startCapture();
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static void startCapture()
/*  33:    */   {
/*  34: 33 */     if (capturing) {
/*  35: 34 */       throw new IllegalStateException("Capturing item drops twice");
/*  36:    */     }
/*  37: 35 */     capturing = true;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static void stopCapture()
/*  41:    */   {
/*  42: 39 */     capturing = false;
/*  43: 40 */     killExperience = false;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static List<EntityItem> getCapturedEntities()
/*  47:    */   {
/*  48: 45 */     capturing = false;
/*  49:    */     
/*  50: 47 */     List<EntityItem> i = new ArrayList();
/*  51: 48 */     i.addAll(entityItems);
/*  52:    */     
/*  53: 50 */     entityItems.clear();
/*  54: 51 */     items.clear();
/*  55:    */     
/*  56: 53 */     return i;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static List<ItemStack> getCapturedItemStacks()
/*  60:    */   {
/*  61: 58 */     capturing = false;
/*  62:    */     
/*  63: 60 */     List<ItemStack> i = new ArrayList();
/*  64: 61 */     i.addAll(items);
/*  65:    */     
/*  66: 63 */     entityItems.clear();
/*  67: 64 */     items.clear();
/*  68:    */     
/*  69: 66 */     return i;
/*  70:    */   }
/*  71:    */   
/*  72:    */   @SubscribeEvent(priority=EventPriority.LOWEST)
/*  73:    */   public void onEntityJoinWorld(EntityJoinWorldEvent event)
/*  74:    */   {
/*  75: 72 */     if ((!capturing) || (event.entity.worldObj.isClient)) {
/*  76: 73 */       return;
/*  77:    */     }
/*  78: 75 */     Entity entity = event.entity;
/*  79: 77 */     if (entity.getClass() == EntitySilverfish.class)
/*  80:    */     {
/*  81: 78 */       ((EntitySilverfish)entity).onDeath(DamageSource.cactus);
/*  82: 79 */       entity.setDead();
/*  83: 80 */       event.setCanceled(true);
/*  84: 81 */       return;
/*  85:    */     }
/*  86: 84 */     if ((killExperience) && (entity.getClass() == EntityXPOrb.class))
/*  87:    */     {
/*  88: 85 */       entity.setDead();
/*  89: 86 */       event.setCanceled(true);
/*  90: 87 */       return;
/*  91:    */     }
/*  92: 90 */     if (isEntityItem(entity.getClass()))
/*  93:    */     {
/*  94: 91 */       ItemStack stack = entity.getDataWatcher().getWatchableObjectItemStack(10);
/*  95: 93 */       if (stack == null) {
/*  96: 94 */         return;
/*  97:    */       }
/*  98: 98 */       items.add(stack);
/*  99: 99 */       entityItems.add((EntityItem)entity);
/* 100:    */       
/* 101:101 */       entity.setDead();
/* 102:102 */       event.setCanceled(true);
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:106 */   public static final HashMap<Class, Boolean> clazztypes = new HashMap();
/* 107:    */   
/* 108:    */   public static boolean isEntityItem(Class clazz)
/* 109:    */   {
/* 110:109 */     Boolean aBoolean = (Boolean)clazztypes.get(clazz);
/* 111:110 */     if (aBoolean == null)
/* 112:    */     {
/* 113:111 */       aBoolean = Boolean.valueOf(EntityItem.class.isAssignableFrom(clazz));
/* 114:112 */       clazztypes.put(clazz, aBoolean);
/* 115:    */     }
/* 116:115 */     return aBoolean.booleanValue();
/* 117:    */   }
/* 118:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.EventHandlerEntityItemStealer
 * JD-Core Version:    0.7.0.1
 */