/*  1:   */ package com.rwtema.extrautils.helper;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.relauncher.Side;
/*  4:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  5:   */ import java.util.Locale;
/*  6:   */ import net.minecraft.client.Minecraft;
/*  7:   */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  8:   */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*  9:   */ import net.minecraft.client.renderer.texture.TextureMap;
/* 10:   */ import net.minecraft.entity.player.EntityPlayer;
/* 11:   */ import net.minecraft.util.IIcon;
/* 12:   */ import net.minecraft.world.World;
/* 13:   */ 
/* 14:   */ @SideOnly(Side.CLIENT)
/* 15:   */ public class XUHelperClient
/* 16:   */ {
/* 17:   */   public static EntityPlayer clientPlayer()
/* 18:   */   {
/* 19:18 */     return Minecraft.getMinecraft().thePlayer;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public static World clientWorld()
/* 23:   */   {
/* 24:22 */     return Minecraft.getMinecraft().theWorld;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public static IIcon registerCustomIcon(String texture, IIconRegister register, TextureAtlasSprite sprite)
/* 28:   */   {
/* 29:26 */     IIcon result = ((TextureMap)register).getTextureExtry(texture);
/* 30:28 */     if (result == null)
/* 31:   */     {
/* 32:29 */       TextureAtlasSprite t2 = sprite;
/* 33:30 */       result = t2;
/* 34:31 */       ((TextureMap)register).setTextureEntry(texture, t2);
/* 35:   */     }
/* 36:34 */     return result;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public static String commaDelimited(int n)
/* 40:   */   {
/* 41:38 */     return String.format(Locale.ENGLISH, "%,d", new Object[] { Integer.valueOf(n) });
/* 42:   */   }
/* 43:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.helper.XUHelperClient
 * JD-Core Version:    0.7.0.1
 */