/*  1:   */ package com.rwtema.extrautils.particle;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.relauncher.Side;
/*  4:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  5:   */ import net.minecraft.client.Minecraft;
/*  6:   */ import net.minecraft.client.particle.EffectRenderer;
/*  7:   */ import net.minecraft.client.particle.EntityFX;
/*  8:   */ import net.minecraft.client.resources.IResourceManager;
/*  9:   */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/* 10:   */ 
/* 11:   */ @SideOnly(Side.CLIENT)
/* 12:   */ public class ParticleHelperClient
/* 13:   */   implements IResourceManagerReloadListener
/* 14:   */ {
/* 15:   */   private static EffectRenderer effectRenderer;
/* 16:   */   
/* 17:   */   @SideOnly(Side.CLIENT)
/* 18:   */   public static void addParticle(EntityFX particle)
/* 19:   */   {
/* 20:17 */     effectRenderer.addEffect(particle);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void onResourceManagerReload(IResourceManager manager)
/* 24:   */   {
/* 25:22 */     effectRenderer = Minecraft.getMinecraft().effectRenderer;
/* 26:23 */     registerTextures(manager);
/* 27:   */   }
/* 28:   */   
/* 29:   */   private static void registerTextures(IResourceManager manager) {}
/* 30:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.particle.ParticleHelperClient
 * JD-Core Version:    0.7.0.1
 */