/*  1:   */ package com.rwtema.extrautils.core.corestep;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtilsMod;
/*  4:   */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*  5:   */ import cpw.mods.fml.common.FMLCommonHandler;
/*  6:   */ import cpw.mods.fml.common.eventhandler.EventBus;
/*  7:   */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*  8:   */ import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
/*  9:   */ import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
/* 10:   */ import cpw.mods.fml.relauncher.Side;
/* 11:   */ import cpw.mods.fml.relauncher.SideOnly;
/* 12:   */ import java.util.ArrayList;
/* 13:   */ 
/* 14:   */ public class ThreadSafeExecution
/* 15:   */ {
/* 16:   */   static
/* 17:   */   {
/* 18:13 */     FMLCommonHandler.instance().bus().register(new ThreadSafeExecution());
/* 19:   */   }
/* 20:   */   
/* 21:16 */   public static final ArrayList<IDelayCallable> clientCallable = new ArrayList();
/* 22:17 */   public static final ArrayList<IDelayCallable> serverCallable = new ArrayList();
/* 23:   */   
/* 24:   */   public static void assignCode(Side side, IDelayCallable delayCallable)
/* 25:   */   {
/* 26:20 */     if (side.isClient())
/* 27:   */     {
/* 28:21 */       if (ExtraUtilsMod.proxy.isClientSideAvailable()) {
/* 29:22 */         synchronized (clientCallable)
/* 30:   */         {
/* 31:23 */           clientCallable.add(delayCallable);
/* 32:   */         }
/* 33:   */       }
/* 34:   */     }
/* 35:   */     else {
/* 36:27 */       synchronized (serverCallable)
/* 37:   */       {
/* 38:28 */         serverCallable.add(delayCallable);
/* 39:   */       }
/* 40:   */     }
/* 41:   */   }
/* 42:   */   
/* 43:   */   @SubscribeEvent
/* 44:   */   public void server(TickEvent.ServerTickEvent server)
/* 45:   */   {
/* 46:35 */     synchronized (serverCallable)
/* 47:   */     {
/* 48:36 */       for (IDelayCallable iDelayCallable : serverCallable) {
/* 49:   */         try
/* 50:   */         {
/* 51:38 */           iDelayCallable.call();
/* 52:   */         }
/* 53:   */         catch (Exception e)
/* 54:   */         {
/* 55:40 */           new RuntimeException("Network code failed on Server: " + e.toString(), e).printStackTrace();
/* 56:   */         }
/* 57:   */       }
/* 58:43 */       serverCallable.clear();
/* 59:   */     }
/* 60:   */   }
/* 61:   */   
/* 62:   */   @SubscribeEvent
/* 63:   */   @SideOnly(Side.CLIENT)
/* 64:   */   public void client(TickEvent.ClientTickEvent server)
/* 65:   */   {
/* 66:50 */     synchronized (clientCallable)
/* 67:   */     {
/* 68:51 */       for (IDelayCallable iDelayCallable : clientCallable) {
/* 69:   */         try
/* 70:   */         {
/* 71:53 */           iDelayCallable.call();
/* 72:   */         }
/* 73:   */         catch (Exception e)
/* 74:   */         {
/* 75:55 */           new RuntimeException("Network code failed on Client: " + e.toString(), e).printStackTrace();
/* 76:   */         }
/* 77:   */       }
/* 78:58 */       clientCallable.clear();
/* 79:   */     }
/* 80:   */   }
/* 81:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.core.corestep.ThreadSafeExecution
 * JD-Core Version:    0.7.0.1
 */