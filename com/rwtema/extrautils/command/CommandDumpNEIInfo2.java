/*  1:   */ package com.rwtema.extrautils.command;
/*  2:   */ 
/*  3:   */ import com.google.common.base.Charsets;
/*  4:   */ import com.google.common.io.Files;
/*  5:   */ import com.rwtema.extrautils.LogHelper;
/*  6:   */ import com.rwtema.extrautils.nei.InfoData;
/*  7:   */ import cpw.mods.fml.relauncher.Side;
/*  8:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  9:   */ import java.io.File;
/* 10:   */ import java.util.Collections;
/* 11:   */ import java.util.Comparator;
/* 12:   */ import net.minecraft.client.Minecraft;
/* 13:   */ import net.minecraft.command.CommandBase;
/* 14:   */ import net.minecraft.command.ICommandSender;
/* 15:   */ import net.minecraft.item.Item;
/* 16:   */ import net.minecraft.item.ItemStack;
/* 17:   */ 
/* 18:   */ @SideOnly(Side.CLIENT)
/* 19:   */ public class CommandDumpNEIInfo2
/* 20:   */   extends CommandBase
/* 21:   */ {
/* 22:   */   public String getCommandName()
/* 23:   */   {
/* 24:22 */     return "dumpneidocs2";
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String getCommandUsage(ICommandSender icommandsender)
/* 28:   */   {
/* 29:27 */     return "/dumpneidocs2";
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void processCommand(ICommandSender icommandsender, String[] astring)
/* 33:   */   {
/* 34:32 */     File f = new File(Minecraft.getMinecraft().mcDataDir, "en_US_doc.lang");
/* 35:33 */     String t = "";
/* 36:34 */     Collections.sort(InfoData.data, cmpData.instance);
/* 37:36 */     for (InfoData data : InfoData.data)
/* 38:   */     {
/* 39:   */       String id;
/* 40:   */       String id;
/* 41:38 */       if (data.precise) {
/* 42:40 */         id = data.item.getUnlocalizedName();
/* 43:   */       } else {
/* 44:42 */         id = data.item.getItem().getUnlocalizedName();
/* 45:   */       }
/* 46:45 */       t = t + "doc." + id + ".name=" + data.name + "\n";
/* 47:47 */       if (data.info.length == 1) {
/* 48:48 */         t = t + "doc." + id + ".info=" + data.info[0].replace('\n', ' ') + "\n";
/* 49:   */       } else {
/* 50:50 */         for (int i = 0; i < data.info.length; i++) {
/* 51:51 */           t = t + "doc." + id + ".info." + i + "=" + data.info[i].replace('\n', ' ') + "\n";
/* 52:   */         }
/* 53:   */       }
/* 54:   */     }
/* 55:   */     try
/* 56:   */     {
/* 57:56 */       Files.write(t, f, Charsets.UTF_8);
/* 58:57 */       LogHelper.info("Dumped Extra Utilities NEI info data to " + f.getAbsolutePath(), new Object[0]);
/* 59:   */     }
/* 60:   */     catch (Exception exception)
/* 61:   */     {
/* 62:59 */       exception.printStackTrace();
/* 63:   */     }
/* 64:   */   }
/* 65:   */   
/* 66:   */   public static class cmpData
/* 67:   */     implements Comparator<InfoData>
/* 68:   */   {
/* 69:64 */     public static cmpData instance = new cmpData();
/* 70:   */     
/* 71:   */     public static String getItem(ItemStack i)
/* 72:   */     {
/* 73:67 */       ItemStack t = new ItemStack(i.getItem(), 1, 0);
/* 74:68 */       return t.getDisplayName();
/* 75:   */     }
/* 76:   */     
/* 77:   */     public int compare(InfoData arg0, InfoData arg1)
/* 78:   */     {
/* 79:84 */       return arg0.item.getUnlocalizedName().compareTo(arg1.item.getUnlocalizedName());
/* 80:   */     }
/* 81:   */   }
/* 82:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.command.CommandDumpNEIInfo2
 * JD-Core Version:    0.7.0.1
 */