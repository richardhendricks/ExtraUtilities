/*  1:   */ package com.rwtema.extrautils.command;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import java.util.List;
/*  5:   */ import net.minecraft.command.ICommandSender;
/*  6:   */ import net.minecraft.entity.Entity;
/*  7:   */ import net.minecraft.server.MinecraftServer;
/*  8:   */ import net.minecraft.world.WorldServer;
/*  9:   */ 
/* 10:   */ public class CommandKillMostCommonEntities
/* 11:   */   extends CommandKillEntities
/* 12:   */ {
/* 13:   */   public CommandKillMostCommonEntities()
/* 14:   */   {
/* 15:12 */     super("common", null, false);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void processCommand(ICommandSender icommandsender, String[] astring)
/* 19:   */   {
/* 20:17 */     int mx = -1;
/* 21:18 */     this.entityclass = null;
/* 22:19 */     HashMap<Class, Integer> map = new HashMap();
/* 23:21 */     for (WorldServer world : MinecraftServer.getServer().worldServers) {
/* 24:22 */       for (int i = 0; i < world.loadedEntityList.size(); i++)
/* 25:   */       {
/* 26:23 */         Class clazz = ((Entity)world.loadedEntityList.get(i)).getClass();
/* 27:24 */         Integer j = (Integer)map.get(clazz);
/* 28:26 */         if (j == null) {
/* 29:27 */           j = Integer.valueOf(0);
/* 30:   */         }
/* 31:30 */         Integer localInteger1 = j;Integer localInteger2 = j = Integer.valueOf(j.intValue() + 1);
/* 32:32 */         if (j.intValue() > mx)
/* 33:   */         {
/* 34:33 */           mx = j.intValue();
/* 35:34 */           this.entityclass = clazz;
/* 36:   */         }
/* 37:37 */         map.put(clazz, j);
/* 38:   */       }
/* 39:   */     }
/* 40:41 */     if (this.entityclass == null) {
/* 41:42 */       return;
/* 42:   */     }
/* 43:45 */     super.processCommand(icommandsender, astring);
/* 44:   */   }
/* 45:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.command.CommandKillMostCommonEntities
 * JD-Core Version:    0.7.0.1
 */