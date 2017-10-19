/*  1:   */ package com.rwtema.extrautils.sounds;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtilsMod;
/*  4:   */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*  5:   */ import com.rwtema.extrautils.tileentity.generators.TileEntityGenerator;
/*  6:   */ import cpw.mods.fml.relauncher.ReflectionHelper;
/*  7:   */ import cpw.mods.fml.relauncher.Side;
/*  8:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  9:   */ import java.lang.reflect.Field;
/* 10:   */ import java.util.Map;
/* 11:   */ import net.minecraft.client.Minecraft;
/* 12:   */ import net.minecraft.client.audio.ISound;
/* 13:   */ import net.minecraft.client.audio.SoundHandler;
/* 14:   */ import net.minecraft.client.audio.SoundManager;
/* 15:   */ 
/* 16:   */ @SideOnly(Side.CLIENT)
/* 17:   */ public class Sounds
/* 18:   */ {
/* 19:   */   public static void registerSoundTile(ISoundTile soundTile)
/* 20:   */   {
/* 21:19 */     if (ExtraUtilsMod.proxy.isClientSideAvailable()) {
/* 22:20 */       registerSoundTileDo(soundTile);
/* 23:   */     }
/* 24:   */   }
/* 25:   */   
/* 26:   */   public static void addGenerator(TileEntityGenerator gen)
/* 27:   */   {
/* 28:25 */     if (ExtraUtilsMod.proxy.isClientSideAvailable()) {
/* 29:26 */       XUSoundTileGenerators.addGenerator(gen);
/* 30:   */     }
/* 31:   */   }
/* 32:   */   
/* 33:   */   public static void refresh()
/* 34:   */   {
/* 35:31 */     if (ExtraUtilsMod.proxy.isClientSideAvailable()) {
/* 36:32 */       XUSoundTileGenerators.refresh();
/* 37:   */     }
/* 38:   */   }
/* 39:   */   
/* 40:   */   private static void registerSoundTileDo(ISoundTile soundTile)
/* 41:   */   {
/* 42:37 */     tryAddSound(new XUSoundTile(soundTile));
/* 43:   */   }
/* 44:   */   
/* 45:   */   public static void tryAddSound(ISound sound)
/* 46:   */   {
/* 47:41 */     if (canAddSound(sound)) {
/* 48:42 */       Minecraft.getMinecraft().getSoundHandler().playSound(sound);
/* 49:   */     }
/* 50:   */   }
/* 51:   */   
/* 52:46 */   private static Field playingSounds = null;
/* 53:   */   private static Field soundMgr;
/* 54:   */   
/* 55:   */   public static boolean canAddSound(ISound sound)
/* 56:   */   {
/* 57:50 */     if (playingSounds == null)
/* 58:   */     {
/* 59:51 */       playingSounds = ReflectionHelper.findField(SoundManager.class, new String[] { "playingSounds", "field_148629_h" });
/* 60:52 */       soundMgr = ReflectionHelper.findField(SoundHandler.class, new String[] { "sndManager", "field_147694_f" });
/* 61:   */     }
/* 62:   */     try
/* 63:   */     {
/* 64:55 */       SoundManager manager = (SoundManager)soundMgr.get(Minecraft.getMinecraft().getSoundHandler());
/* 65:56 */       Map map = (Map)playingSounds.get(manager);
/* 66:   */       
/* 67:58 */       return !map.containsValue(sound);
/* 68:   */     }
/* 69:   */     catch (IllegalAccessException e) {}
/* 70:61 */     return false;
/* 71:   */   }
/* 72:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.sounds.Sounds
 * JD-Core Version:    0.7.0.1
 */