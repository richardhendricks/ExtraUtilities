/*  1:   */ package com.rwtema.extrautils.command;
/*  2:   */ 
/*  3:   */ import com.google.common.base.Charsets;
/*  4:   */ import com.google.common.io.Files;
/*  5:   */ import com.rwtema.extrautils.LogHelper;
/*  6:   */ import com.rwtema.extrautils.nei.InfoData;
/*  7:   */ import cpw.mods.fml.common.FMLCommonHandler;
/*  8:   */ import cpw.mods.fml.common.registry.LanguageRegistry;
/*  9:   */ import cpw.mods.fml.relauncher.ReflectionHelper;
/* 10:   */ import java.io.File;
/* 11:   */ import java.util.Comparator;
/* 12:   */ import java.util.Map;
/* 13:   */ import java.util.Map.Entry;
/* 14:   */ import java.util.Properties;
/* 15:   */ import net.minecraft.client.Minecraft;
/* 16:   */ import net.minecraft.command.CommandBase;
/* 17:   */ import net.minecraft.command.ICommandSender;
/* 18:   */ import net.minecraft.item.ItemStack;
/* 19:   */ 
/* 20:   */ public class CommandDumpLocalization
/* 21:   */   extends CommandBase
/* 22:   */ {
/* 23:   */   public String getCommandName()
/* 24:   */   {
/* 25:24 */     return "dumplocalization";
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String getCommandUsage(ICommandSender icommandsender)
/* 29:   */   {
/* 30:29 */     return "/dumplocalization";
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void processCommand(ICommandSender icommandsender, String[] astring)
/* 34:   */   {
/* 35:34 */     File f = new File(Minecraft.getMinecraft().mcDataDir, "extrautilities_localization.txt");
/* 36:   */     
/* 37:   */ 
/* 38:   */ 
/* 39:38 */     Map<String, Properties> k = (Map)ReflectionHelper.getPrivateValue(LanguageRegistry.class, LanguageRegistry.instance(), new String[] { "modLanguageData" });
/* 40:39 */     String lang = FMLCommonHandler.instance().getCurrentLanguage();
/* 41:40 */     Properties p = (Properties)k.get(lang);
/* 42:   */     
/* 43:42 */     String t = "";
/* 44:43 */     for (Map.Entry<Object, Object> entry : p.entrySet()) {
/* 45:44 */       t = t + entry.getKey() + "=" + entry.getValue() + "\n";
/* 46:   */     }
/* 47:   */     try
/* 48:   */     {
/* 49:48 */       Files.write(t, f, Charsets.UTF_8);
/* 50:49 */       LogHelper.info("Dumped Language data to " + f.getAbsolutePath(), new Object[0]);
/* 51:   */     }
/* 52:   */     catch (Exception exception)
/* 53:   */     {
/* 54:51 */       exception.printStackTrace();
/* 55:   */     }
/* 56:   */   }
/* 57:   */   
/* 58:   */   public static class cmpData
/* 59:   */     implements Comparator<InfoData>
/* 60:   */   {
/* 61:56 */     public static cmpData instance = new cmpData();
/* 62:   */     
/* 63:   */     public static String getItem(ItemStack i)
/* 64:   */     {
/* 65:59 */       ItemStack t = new ItemStack(i.getItem(), 1, 0);
/* 66:60 */       return t.getDisplayName();
/* 67:   */     }
/* 68:   */     
/* 69:   */     public int compare(InfoData arg0, InfoData arg1)
/* 70:   */     {
/* 71:65 */       if ((arg0.isBlock) && (!arg1.isBlock)) {
/* 72:66 */         return -1;
/* 73:   */       }
/* 74:69 */       if ((arg1.isBlock) && (!arg0.isBlock)) {
/* 75:70 */         return 1;
/* 76:   */       }
/* 77:76 */       return arg0.name.compareTo(arg1.name);
/* 78:   */     }
/* 79:   */   }
/* 80:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.command.CommandDumpLocalization
 * JD-Core Version:    0.7.0.1
 */