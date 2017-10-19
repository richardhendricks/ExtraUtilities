/*  1:   */ package com.rwtema.extrautils.command;
/*  2:   */ 
/*  3:   */ import com.mojang.authlib.GameProfile;
/*  4:   */ import cpw.mods.fml.relauncher.Side;
/*  5:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  6:   */ import net.minecraft.client.Minecraft;
/*  7:   */ import net.minecraft.command.CommandBase;
/*  8:   */ import net.minecraft.command.ICommandSender;
/*  9:   */ import net.minecraft.util.ChatComponentText;
/* 10:   */ import net.minecraft.util.Session;
/* 11:   */ 
/* 12:   */ @SideOnly(Side.CLIENT)
/* 13:   */ public class CommandUUID
/* 14:   */   extends CommandBase
/* 15:   */ {
/* 16:   */   public String getCommandName()
/* 17:   */   {
/* 18:14 */     return "uuid";
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String getCommandUsage(ICommandSender var1)
/* 22:   */   {
/* 23:19 */     return "/uuid";
/* 24:   */   }
/* 25:   */   
/* 26:   */   public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
/* 27:   */   {
/* 28:24 */     return true;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void processCommand(ICommandSender var1, String[] var2)
/* 32:   */   {
/* 33:29 */     var1.addChatMessage(new ChatComponentText("Username: " + Minecraft.getMinecraft().getSession().func_148256_e().getName() + " UUID: " + Minecraft.getMinecraft().getSession().func_148256_e().getId()));
/* 34:   */   }
/* 35:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.command.CommandUUID
 * JD-Core Version:    0.7.0.1
 */