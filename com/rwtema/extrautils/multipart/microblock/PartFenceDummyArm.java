/*  1:   */ package com.rwtema.extrautils.multipart.microblock;
/*  2:   */ 
/*  3:   */ import codechicken.lib.vec.Cuboid6;
/*  4:   */ import codechicken.multipart.JCuboidPart;
/*  5:   */ import codechicken.multipart.JNormalOcclusion;
/*  6:   */ import codechicken.multipart.NormalOcclusionTest;
/*  7:   */ import codechicken.multipart.TMultiPart;
/*  8:   */ import java.util.ArrayList;
/*  9:   */ 
/* 10:   */ public class PartFenceDummyArm
/* 11:   */   extends JCuboidPart
/* 12:   */   implements JNormalOcclusion
/* 13:   */ {
/* 14:12 */   public static final PartFenceDummyArm[] dummyArms = { null, null, new PartFenceDummyArm(2), new PartFenceDummyArm(3), new PartFenceDummyArm(4), new PartFenceDummyArm(5) };
/* 15:13 */   protected final ArrayList<Cuboid6> boxes = new ArrayList();
/* 16:   */   public int dir;
/* 17:   */   
/* 18:   */   public PartFenceDummyArm() {}
/* 19:   */   
/* 20:   */   public PartFenceDummyArm(int dir)
/* 21:   */   {
/* 22:21 */     this.dir = dir;
/* 23:22 */     this.boxes.add(PartFence.renderCuboids1[dir]);
/* 24:23 */     this.boxes.add(PartFence.renderCuboids2[dir]);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public Cuboid6 getBounds()
/* 28:   */   {
/* 29:28 */     return PartFence.partCuboids[this.dir];
/* 30:   */   }
/* 31:   */   
/* 32:   */   public String getType()
/* 33:   */   {
/* 34:33 */     return "extrautils:fence_dummy_part_should_never_actually_be_created_(if_it_is,_it_is_bug)";
/* 35:   */   }
/* 36:   */   
/* 37:   */   public boolean occlusionTest(TMultiPart npart)
/* 38:   */   {
/* 39:38 */     return NormalOcclusionTest.apply(this, npart);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public Iterable<Cuboid6> getOcclusionBoxes()
/* 43:   */   {
/* 44:43 */     return this.boxes;
/* 45:   */   }
/* 46:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.multipart.microblock.PartFenceDummyArm
 * JD-Core Version:    0.7.0.1
 */