/*  1:   */ package com.rwtema.extrautils;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.common.Mod;
/*  4:   */ import cpw.mods.fml.common.Mod.EventHandler;
/*  5:   */ import cpw.mods.fml.common.Mod.Instance;
/*  6:   */ import cpw.mods.fml.common.SidedProxy;
/*  7:   */ import cpw.mods.fml.common.event.FMLInitializationEvent;
/*  8:   */ import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
/*  9:   */ import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
/* 10:   */ import cpw.mods.fml.common.event.FMLPostInitializationEvent;
/* 11:   */ import cpw.mods.fml.common.event.FMLPreInitializationEvent;
/* 12:   */ import cpw.mods.fml.common.event.FMLServerStartingEvent;
/* 13:   */ 
/* 14:   */ @Mod(modid="ExtraUtilities", name="ExtraUtilities", dependencies="required-after:Forge@[10.13.2.1291,);after:ForgeMultipart@[1.2.0.336,);after:Baubles;after:ThermalFoundation;after:EE3;before:TConstruct@[1.7.10-1.8.5,)")
/* 15:   */ public class ExtraUtilsMod
/* 16:   */ {
/* 17:   */   public static final String modId = "ExtraUtilities";
/* 18:   */   @SidedProxy(clientSide="com.rwtema.extrautils.ExtraUtilsClient", serverSide="com.rwtema.extrautils.ExtraUtilsProxy")
/* 19:   */   public static ExtraUtilsProxy proxy;
/* 20:   */   @Mod.Instance("ExtraUtilities")
/* 21:   */   public static ExtraUtilsMod instance;
/* 22:   */   public static ExtraUtils extraUtils;
/* 23:   */   
/* 24:   */   @Mod.EventHandler
/* 25:   */   public void preInit(FMLPreInitializationEvent event)
/* 26:   */   {
/* 27:22 */     extraUtils = new ExtraUtils();
/* 28:23 */     extraUtils.preInit(event);
/* 29:   */   }
/* 30:   */   
/* 31:   */   @Mod.EventHandler
/* 32:   */   public void init(FMLInitializationEvent event)
/* 33:   */   {
/* 34:28 */     extraUtils.init(event);
/* 35:   */   }
/* 36:   */   
/* 37:   */   @Mod.EventHandler
/* 38:   */   public void postInit(FMLPostInitializationEvent event)
/* 39:   */   {
/* 40:33 */     extraUtils.postInit(event);
/* 41:   */   }
/* 42:   */   
/* 43:   */   @Mod.EventHandler
/* 44:   */   public void serverStarting(FMLServerStartingEvent event)
/* 45:   */   {
/* 46:38 */     extraUtils.serverStarting(event);
/* 47:   */   }
/* 48:   */   
/* 49:   */   @Mod.EventHandler
/* 50:   */   public void serverStart(FMLServerStartingEvent event)
/* 51:   */   {
/* 52:43 */     extraUtils.serverStart(event);
/* 53:   */   }
/* 54:   */   
/* 55:   */   @Mod.EventHandler
/* 56:   */   public void remap(FMLMissingMappingsEvent event)
/* 57:   */   {
/* 58:47 */     extraUtils.remap(event);
/* 59:   */   }
/* 60:   */   
/* 61:   */   @Mod.EventHandler
/* 62:   */   public void loadComplete(FMLLoadCompleteEvent event)
/* 63:   */   {
/* 64:50 */     extraUtils.loadComplete(event);
/* 65:   */   }
/* 66:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.ExtraUtilsMod
 * JD-Core Version:    0.7.0.1
 */