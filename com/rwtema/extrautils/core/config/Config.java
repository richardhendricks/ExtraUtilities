/*  1:   */ package com.rwtema.extrautils.core.config;
/*  2:   */ 
/*  3:   */ import java.util.HashSet;
/*  4:   */ import net.minecraftforge.common.config.Configuration;
/*  5:   */ 
/*  6:   */ public class Config
/*  7:   */ {
/*  8: 7 */   public static HashSet<Config> configs = new HashSet();
/*  9:   */   public String name;
/* 10:   */   public String comment;
/* 11:   */   public boolean shouldReload;
/* 12:   */   
/* 13:   */   public boolean reloadData()
/* 14:   */   {
/* 15:10 */     return false;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void load(Configuration config) {}
/* 19:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.core.config.Config
 * JD-Core Version:    0.7.0.1
 */