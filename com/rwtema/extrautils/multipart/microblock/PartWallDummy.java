/*  1:   */ package com.rwtema.extrautils.multipart.microblock;
/*  2:   */ 
/*  3:   */ import codechicken.lib.vec.Cuboid6;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ 
/*  6:   */ public class PartWallDummy
/*  7:   */   extends PartFenceDummyArm
/*  8:   */ {
/*  9: 7 */   public static final PartWallDummy[] dummyArms = { null, null, new PartWallDummy(2), new PartWallDummy(3), new PartWallDummy(4), new PartWallDummy(5) };
/* 10:   */   
/* 11:   */   public PartWallDummy(int dir)
/* 12:   */   {
/* 13:11 */     this.boxes.add(PartWall.renderCuboids1[dir]);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public Cuboid6 getBounds()
/* 17:   */   {
/* 18:16 */     return PartWall.partCuboids[this.dir];
/* 19:   */   }
/* 20:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.multipart.microblock.PartWallDummy
 * JD-Core Version:    0.7.0.1
 */