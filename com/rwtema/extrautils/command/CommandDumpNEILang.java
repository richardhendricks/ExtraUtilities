/*  1:   */ package com.rwtema.extrautils.command;
/*  2:   */ 
/*  3:   */ import com.google.common.base.Charsets;
/*  4:   */ import com.google.common.io.Files;
/*  5:   */ import com.rwtema.extrautils.LogHelper;
/*  6:   */ import com.rwtema.extrautils.nei.InfoData;
/*  7:   */ import java.io.File;
/*  8:   */ import java.util.Collections;
/*  9:   */ import java.util.Comparator;
/* 10:   */ import net.minecraft.client.Minecraft;
/* 11:   */ import net.minecraft.command.CommandBase;
/* 12:   */ import net.minecraft.command.ICommandSender;
/* 13:   */ import net.minecraft.item.ItemStack;
/* 14:   */ 
/* 15:   */ public class CommandDumpNEILang
/* 16:   */   extends CommandBase
/* 17:   */ {
/* 18:   */   public String getCommandName()
/* 19:   */   {
/* 20:19 */     return "dumpneilang";
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getCommandUsage(ICommandSender icommandsender)
/* 24:   */   {
/* 25:24 */     return "/dumpneilang";
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void processCommand(ICommandSender icommandsender, String[] astring)
/* 29:   */   {
/* 30:29 */     File f = new File(Minecraft.getMinecraft().mcDataDir, "en_neilang.txt");
/* 31:30 */     String t = "";
/* 32:31 */     Collections.sort(InfoData.data, cmpData.instance);
/* 33:32 */     boolean blocks = true;
/* 34:33 */     t = "[spoiler='New Blocks']\n";
/* 35:35 */     for (InfoData data : InfoData.data)
/* 36:   */     {
/* 37:36 */       if ((blocks) && (!data.isBlock))
/* 38:   */       {
/* 39:37 */         blocks = false;
/* 40:38 */         t = t + "[/spoiler]\n\n";
/* 41:39 */         t = t + "[spoiler='New Items']\n";
/* 42:   */       }
/* 43:42 */       t = t + "[spoiler='" + data.name + "']\n";
/* 44:44 */       if (data.url != null) {
/* 45:45 */         t = t + "[center][img]" + data.url + "[/img][/center]\n";
/* 46:   */       }
/* 47:48 */       boolean extraSpoilerTag = false;
/* 48:50 */       for (String s : data.info) {
/* 49:51 */         if (s.startsWith("Spoilers:"))
/* 50:   */         {
/* 51:52 */           extraSpoilerTag = true;
/* 52:53 */           t = t + "[spoiler='Spoilers!']\n";
/* 53:   */         }
/* 54:   */         else
/* 55:   */         {
/* 56:55 */           t = t + s + "\n";
/* 57:   */         }
/* 58:   */       }
/* 59:59 */       if (extraSpoilerTag) {
/* 60:60 */         t = t + "[/spoiler]\n";
/* 61:   */       }
/* 62:63 */       t = t + "[/spoiler]\n\n";
/* 63:   */     }
/* 64:66 */     t = t + "[/spoiler]\n";
/* 65:   */     try
/* 66:   */     {
/* 67:69 */       Files.write(t, f, Charsets.UTF_8);
/* 68:70 */       LogHelper.info("Dumped Extra Utilities NEI info data to " + f.getAbsolutePath(), new Object[0]);
/* 69:   */     }
/* 70:   */     catch (Exception exception)
/* 71:   */     {
/* 72:72 */       exception.printStackTrace();
/* 73:   */     }
/* 74:   */   }
/* 75:   */   
/* 76:   */   public static class cmpData
/* 77:   */     implements Comparator<InfoData>
/* 78:   */   {
/* 79:77 */     public static cmpData instance = new cmpData();
/* 80:   */     
/* 81:   */     public static String getItem(ItemStack i)
/* 82:   */     {
/* 83:80 */       ItemStack t = new ItemStack(i.getItem(), 1, 0);
/* 84:81 */       return t.getDisplayName();
/* 85:   */     }
/* 86:   */     
/* 87:   */     public int compare(InfoData arg0, InfoData arg1)
/* 88:   */     {
/* 89:86 */       if ((arg0.isBlock) && (!arg1.isBlock)) {
/* 90:87 */         return -1;
/* 91:   */       }
/* 92:90 */       if ((arg1.isBlock) && (!arg0.isBlock)) {
/* 93:91 */         return 1;
/* 94:   */       }
/* 95:97 */       return arg0.name.compareTo(arg1.name);
/* 96:   */     }
/* 97:   */   }
/* 98:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.command.CommandDumpNEILang
 * JD-Core Version:    0.7.0.1
 */