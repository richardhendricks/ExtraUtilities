/*   1:    */ package com.rwtema.extrautils.command;
/*   2:    */ 
/*   3:    */ import com.google.common.base.Charsets;
/*   4:    */ import com.google.common.io.Files;
/*   5:    */ import com.rwtema.extrautils.LogHelper;
/*   6:    */ import com.rwtema.extrautils.nei.InfoData;
/*   7:    */ import cpw.mods.fml.relauncher.ReflectionHelper;
/*   8:    */ import cpw.mods.fml.relauncher.Side;
/*   9:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  10:    */ import java.io.File;
/*  11:    */ import java.util.Collections;
/*  12:    */ import java.util.Comparator;
/*  13:    */ import java.util.Map;
/*  14:    */ import net.minecraft.client.Minecraft;
/*  15:    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*  16:    */ import net.minecraft.client.renderer.texture.TextureMap;
/*  17:    */ import net.minecraft.command.CommandBase;
/*  18:    */ import net.minecraft.command.ICommandSender;
/*  19:    */ import net.minecraft.item.ItemStack;
/*  20:    */ 
/*  21:    */ @SideOnly(Side.CLIENT)
/*  22:    */ public class CommandDumpNEIInfo
/*  23:    */   extends CommandBase
/*  24:    */ {
/*  25:    */   public String getCommandName()
/*  26:    */   {
/*  27: 27 */     return "dumpneidocs";
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String getCommandUsage(ICommandSender icommandsender)
/*  31:    */   {
/*  32: 32 */     return "/dumpneidocs";
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void processCommand(ICommandSender icommandsender, String[] astring)
/*  36:    */   {
/*  37: 38 */     TextureMap tex = Minecraft.getMinecraft().getTextureMapBlocks();
/*  38: 39 */     Map map = (Map)ReflectionHelper.getPrivateValue(TextureMap.class, tex, new String[] { "mapRegisteredSprites" });
/*  39: 41 */     for (Object o : map.values())
/*  40:    */     {
/*  41: 42 */       TextureAtlasSprite sprite = (TextureAtlasSprite)o;
/*  42: 44 */       if ((sprite.getIconWidth() < 16) || (sprite.getIconHeight() < 16)) {
/*  43: 45 */         LogHelper.debug(sprite.getIconName(), new Object[0]);
/*  44:    */       }
/*  45:    */     }
/*  46: 49 */     File f = new File(Minecraft.getMinecraft().mcDataDir, "extrautilitiesnei.txt");
/*  47: 50 */     String t = "";
/*  48: 51 */     Collections.sort(InfoData.data, cmpData.instance);
/*  49: 52 */     boolean blocks = true;
/*  50: 53 */     t = "[spoiler='New Blocks']\n";
/*  51: 55 */     for (InfoData data : InfoData.data)
/*  52:    */     {
/*  53: 56 */       if ((blocks) && (!data.isBlock))
/*  54:    */       {
/*  55: 57 */         blocks = false;
/*  56: 58 */         t = t + "[/spoiler]\n\n";
/*  57: 59 */         t = t + "[spoiler='New Items']\n";
/*  58:    */       }
/*  59: 62 */       t = t + "[spoiler='" + data.name + "']\n";
/*  60: 64 */       if (data.url != null) {
/*  61: 65 */         t = t + "[center][img]" + data.url + "[/img][/center]\n";
/*  62:    */       }
/*  63: 68 */       boolean extraSpoilerTag = false;
/*  64: 70 */       for (String s : data.info) {
/*  65: 71 */         if (s.startsWith("Spoilers:"))
/*  66:    */         {
/*  67: 72 */           extraSpoilerTag = true;
/*  68: 73 */           t = t + "[spoiler='Spoilers!']\n";
/*  69:    */         }
/*  70:    */         else
/*  71:    */         {
/*  72: 75 */           t = t + s + "\n";
/*  73:    */         }
/*  74:    */       }
/*  75: 79 */       if (extraSpoilerTag) {
/*  76: 80 */         t = t + "[/spoiler]\n";
/*  77:    */       }
/*  78: 83 */       t = t + "[/spoiler]\n\n";
/*  79:    */     }
/*  80: 86 */     t = t + "[/spoiler]\n";
/*  81:    */     try
/*  82:    */     {
/*  83: 89 */       Files.write(t, f, Charsets.UTF_8);
/*  84: 90 */       LogHelper.info("Dumped Extra Utilities NEI info data to " + f.getAbsolutePath(), new Object[0]);
/*  85:    */     }
/*  86:    */     catch (Exception exception)
/*  87:    */     {
/*  88: 92 */       exception.printStackTrace();
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static class cmpData
/*  93:    */     implements Comparator<InfoData>
/*  94:    */   {
/*  95: 97 */     public static cmpData instance = new cmpData();
/*  96:    */     
/*  97:    */     public static String getItem(ItemStack i)
/*  98:    */     {
/*  99:100 */       ItemStack t = new ItemStack(i.getItem(), 1, 0);
/* 100:101 */       return t.getDisplayName();
/* 101:    */     }
/* 102:    */     
/* 103:    */     public int compare(InfoData arg0, InfoData arg1)
/* 104:    */     {
/* 105:106 */       if ((arg0.isBlock) && (!arg1.isBlock)) {
/* 106:107 */         return -1;
/* 107:    */       }
/* 108:110 */       if ((arg1.isBlock) && (!arg0.isBlock)) {
/* 109:111 */         return 1;
/* 110:    */       }
/* 111:117 */       return arg0.name.compareTo(arg1.name);
/* 112:    */     }
/* 113:    */   }
/* 114:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.command.CommandDumpNEIInfo
 * JD-Core Version:    0.7.0.1
 */