/*  1:   */ package com.rwtema.extrautils.fakeplayer;
/*  2:   */ 
/*  3:   */ import com.mojang.authlib.GameProfile;
/*  4:   */ import net.minecraft.world.WorldServer;
/*  5:   */ import net.minecraftforge.common.util.FakePlayer;
/*  6:   */ 
/*  7:   */ public class AdvFakePlayer
/*  8:   */   extends FakePlayer
/*  9:   */ {
/* 10: 7 */   private static AdvFakePlayer playerDefault = null;
/* 11: 8 */   String id = "41C82C87-7AfB-4024-BA57-13D2C99CAE77";
/* 12:   */   
/* 13:   */   public AdvFakePlayer(WorldServer world, GameProfile name)
/* 14:   */   {
/* 15:11 */     super(world, name);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getPlayerIP()
/* 19:   */   {
/* 20:19 */     return "fake.player.user.name=" + getGameProfile().getName();
/* 21:   */   }
/* 22:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.fakeplayer.AdvFakePlayer
 * JD-Core Version:    0.7.0.1
 */