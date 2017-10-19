/*  1:   */ package com.rwtema.extrautils.worldgen.Underdark;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.EventHandlerServer;
/*  4:   */ import com.rwtema.extrautils.ExtraUtils;
/*  5:   */ import com.rwtema.extrautils.helper.XURandom;
/*  6:   */ import cpw.mods.fml.common.eventhandler.Event.Result;
/*  7:   */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*  8:   */ import java.util.List;
/*  9:   */ import java.util.Random;
/* 10:   */ import net.minecraft.entity.Entity;
/* 11:   */ import net.minecraft.entity.EntityLivingBase;
/* 12:   */ import net.minecraft.entity.SharedMonsterAttributes;
/* 13:   */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/* 14:   */ import net.minecraft.entity.monster.EntityMob;
/* 15:   */ import net.minecraft.world.World;
/* 16:   */ import net.minecraft.world.WorldProvider;
/* 17:   */ import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
/* 18:   */ import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate;
/* 19:   */ import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable;
/* 20:   */ 
/* 21:   */ public class EventHandlerUnderdark
/* 22:   */ {
/* 23:18 */   public static Random rand = ;
/* 24:   */   
/* 25:   */   @SubscribeEvent
/* 26:   */   public void noMobs(LivingSpawnEvent.CheckSpawn event)
/* 27:   */   {
/* 28:22 */     if ((event.getResult() == Event.Result.DEFAULT) && 
/* 29:23 */       (event.world.provider.dimensionId == ExtraUtils.underdarkDimID) && 
/* 30:24 */       ((event.entity instanceof EntityMob))) {
/* 31:25 */       if (rand.nextDouble() < Math.min(0.95D, event.entity.posY / 80.0D))
/* 32:   */       {
/* 33:26 */         event.setResult(Event.Result.DENY);
/* 34:   */       }
/* 35:   */       else
/* 36:   */       {
/* 37:28 */         IAttributeInstance t = ((EntityMob)event.entity).getEntityAttribute(SharedMonsterAttributes.maxHealth);
/* 38:29 */         t.setBaseValue(t.getBaseValue() * 2.0D);
/* 39:30 */         ((EntityMob)event.entity).heal((float)t.getAttributeValue());
/* 40:31 */         t = ((EntityMob)event.entity).getEntityAttribute(SharedMonsterAttributes.attackDamage);
/* 41:32 */         t.setBaseValue(t.getBaseValue() * 2.0D);
/* 42:34 */         if ((!EventHandlerServer.isInRangeOfTorch(event.entity)) && 
/* 43:35 */           (event.entityLiving.worldObj.checkNoEntityCollision(event.entityLiving.boundingBox)) && (event.entityLiving.worldObj.getCollidingBoundingBoxes(event.entityLiving, event.entityLiving.boundingBox).isEmpty()) && (!event.entityLiving.worldObj.isAnyLiquid(event.entityLiving.boundingBox))) {
/* 44:38 */           event.setResult(Event.Result.ALLOW);
/* 45:   */         }
/* 46:   */       }
/* 47:   */     }
/* 48:   */   }
/* 49:   */   
/* 50:   */   @SubscribeEvent
/* 51:   */   public void preventDoubleDecor(DecorateBiomeEvent.Decorate decor) {}
/* 52:   */   
/* 53:   */   @SubscribeEvent
/* 54:   */   public void noDirt(OreGenEvent.GenerateMinable event)
/* 55:   */   {
/* 56:56 */     if (event.world.provider.dimensionId == ExtraUtils.underdarkDimID) {
/* 57:57 */       switch (1.$SwitchMap$net$minecraftforge$event$terraingen$OreGenEvent$GenerateMinable$EventType[event.type.ordinal()])
/* 58:   */       {
/* 59:   */       case 1: 
/* 60:   */       case 2: 
/* 61:60 */         event.setResult(Event.Result.DENY);
/* 62:61 */         break;
/* 63:   */       }
/* 64:   */     }
/* 65:   */   }
/* 66:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.worldgen.Underdark.EventHandlerUnderdark
 * JD-Core Version:    0.7.0.1
 */