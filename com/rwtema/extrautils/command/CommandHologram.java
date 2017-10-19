/*  1:   */ package com.rwtema.extrautils.command;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.EventHandlerClient;
/*  4:   */ import cpw.mods.fml.relauncher.Side;
/*  5:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  6:   */ import java.util.List;
/*  7:   */ import net.minecraft.command.CommandBase;
/*  8:   */ import net.minecraft.command.ICommandSender;
/*  9:   */ 
/* 10:   */ @SideOnly(Side.CLIENT)
/* 11:   */ public class CommandHologram
/* 12:   */   extends CommandBase
/* 13:   */ {
/* 14:   */   public String getCommandName()
/* 15:   */   {
/* 16:13 */     return "xu_holo";
/* 17:   */   }
/* 18:   */   
/* 19:   */   public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
/* 20:   */   {
/* 21:18 */     return true;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String getCommandUsage(ICommandSender icommandsender)
/* 25:   */   {
/* 26:23 */     return "/xu_holo <playername>";
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void processCommand(ICommandSender icommandsender, String[] astring)
/* 30:   */   {
/* 31:28 */     if (EventHandlerClient.holograms.contains(astring[0])) {
/* 32:29 */       EventHandlerClient.holograms.remove(astring[0]);
/* 33:   */     } else {
/* 34:31 */       EventHandlerClient.holograms.add(astring[0]);
/* 35:   */     }
/* 36:   */   }
/* 37:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.command.CommandHologram
 * JD-Core Version:    0.7.0.1
 */