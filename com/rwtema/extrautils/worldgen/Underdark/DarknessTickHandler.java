/*  1:   */ package com.rwtema.extrautils.worldgen.Underdark;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtils;
/*  4:   */ import com.rwtema.extrautils.damgesource.DamageSourceDarkness;
/*  5:   */ import com.rwtema.extrautils.helper.XURandom;
/*  6:   */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*  7:   */ import cpw.mods.fml.common.gameevent.TickEvent.Phase;
/*  8:   */ import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
/*  9:   */ import java.util.Random;
/* 10:   */ import net.minecraft.entity.player.EntityPlayer;
/* 11:   */ import net.minecraft.entity.player.EntityPlayerMP;
/* 12:   */ import net.minecraft.nbt.NBTTagCompound;
/* 13:   */ import net.minecraft.world.World;
/* 14:   */ import net.minecraft.world.WorldProvider;
/* 15:   */ 
/* 16:   */ public class DarknessTickHandler
/* 17:   */ {
/* 18:13 */   public static float maxDarkTime = 100.0F;
/* 19:15 */   public static Random random = XURandom.getInstance();
/* 20:16 */   public static int maxLevel = 20 + random.nextInt(120);
/* 21:   */   
/* 22:   */   @SubscribeEvent
/* 23:   */   public void tickStart(TickEvent.PlayerTickEvent event)
/* 24:   */   {
/* 25:22 */     if ((event.phase == TickEvent.Phase.START) && (!event.player.worldObj.isClient) && ((event.player instanceof EntityPlayerMP)))
/* 26:   */     {
/* 27:23 */       EntityPlayerMP player = (EntityPlayerMP)event.player;
/* 28:25 */       if ((!player.worldObj.isClient) && (player.worldObj.provider.dimensionId == ExtraUtils.underdarkDimID) && (player.worldObj.getTotalWorldTime() % 10L == 0L))
/* 29:   */       {
/* 30:26 */         int time = 0;
/* 31:28 */         if (player.getEntityData().hasKey("XU|DarkTimer")) {
/* 32:29 */           time = player.getEntityData().getInteger("XU|DarkTimer");
/* 33:   */         }
/* 34:32 */         if (player.getBrightness(1.0F) < 0.03D)
/* 35:   */         {
/* 36:33 */           if (time > 100) {
/* 37:34 */             player.attackEntityFrom(DamageSourceDarkness.darkness, 1.0F);
/* 38:   */           } else {
/* 39:36 */             time++;
/* 40:   */           }
/* 41:   */         }
/* 42:38 */         else if (time > 0) {
/* 43:39 */           time--;
/* 44:   */         }
/* 45:42 */         player.getEntityData().setInteger("XU|DarkTimer", time);
/* 46:   */       }
/* 47:   */     }
/* 48:   */   }
/* 49:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.worldgen.Underdark.DarknessTickHandler
 * JD-Core Version:    0.7.0.1
 */