/*  1:   */ package com.rwtema.extrautils.command;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.entity.Entity;
/*  5:   */ import net.minecraft.entity.EntityLiving;
/*  6:   */ import net.minecraft.world.World;
/*  7:   */ 
/*  8:   */ public class CommandKillNonPersistant
/*  9:   */   extends CommandKillEntities
/* 10:   */ {
/* 11:   */   public CommandKillNonPersistant(String type, Class<? extends Entity> entityclass, boolean except)
/* 12:   */   {
/* 13: 9 */     super("despawns", null, true);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void killEntities(World world)
/* 17:   */   {
/* 18:14 */     for (int i = 0; i < world.loadedEntityList.size(); i++) {
/* 19:15 */       if (!((EntityLiving)world.loadedEntityList.get(i)).isNoDespawnRequired())
/* 20:   */       {
/* 21:16 */         this.numKills += 1;
/* 22:17 */         ((Entity)world.loadedEntityList.get(i)).setDead();
/* 23:   */       }
/* 24:   */     }
/* 25:   */   }
/* 26:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.command.CommandKillNonPersistant
 * JD-Core Version:    0.7.0.1
 */