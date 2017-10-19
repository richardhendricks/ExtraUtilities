/*  1:   */ package com.rwtema.extrautils.command;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.command.CommandBase;
/*  5:   */ import net.minecraft.command.ICommandSender;
/*  6:   */ import net.minecraft.entity.Entity;
/*  7:   */ import net.minecraft.server.MinecraftServer;
/*  8:   */ import net.minecraft.util.ChatComponentText;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ 
/* 11:   */ public class CommandKillEntities
/* 12:   */   extends CommandBase
/* 13:   */ {
/* 14:   */   private final String type;
/* 15:   */   private final boolean except;
/* 16:   */   protected Class<? extends Entity> entityclass;
/* 17:14 */   protected int numKills = 0;
/* 18:   */   
/* 19:   */   public CommandKillEntities(String type, Class<? extends Entity> entityclass, boolean except)
/* 20:   */   {
/* 21:18 */     this.type = type;
/* 22:19 */     this.entityclass = entityclass;
/* 23:20 */     this.except = except;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String getCommandName()
/* 27:   */   {
/* 28:25 */     return "xu_kill" + this.type;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public int getRequiredPermissionLevel()
/* 32:   */   {
/* 33:30 */     return 2;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public String getCommandUsage(ICommandSender icommandsender)
/* 37:   */   {
/* 38:35 */     return "/xu_kill" + this.type;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void processCommand(ICommandSender icommandsender, String[] astring)
/* 42:   */   {
/* 43:40 */     this.numKills = 0;
/* 44:42 */     for (int j = 0; j < MinecraftServer.getServer().worldServers.length; j++) {
/* 45:43 */       killEntities(MinecraftServer.getServer().worldServers[j]);
/* 46:   */     }
/* 47:46 */     icommandsender.addChatMessage(new ChatComponentText("Killed " + this.numKills + " of type " + this.entityclass.getName()));
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void killEntities(World world)
/* 51:   */   {
/* 52:50 */     for (int i = 0; i < world.loadedEntityList.size(); i++) {
/* 53:51 */       if (this.entityclass.isInstance(world.loadedEntityList.get(i)) == this.except)
/* 54:   */       {
/* 55:52 */         this.numKills += 1;
/* 56:53 */         ((Entity)world.loadedEntityList.get(i)).setDead();
/* 57:   */       }
/* 58:   */     }
/* 59:   */   }
/* 60:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.command.CommandKillEntities
 * JD-Core Version:    0.7.0.1
 */