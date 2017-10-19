/*  1:   */ package com.rwtema.extrautils.tileentity.transfernodes.multiparts;
/*  2:   */ 
/*  3:   */ import codechicken.lib.vec.Cuboid6;
/*  4:   */ import codechicken.microblock.ISidedHollowConnect;
/*  5:   */ import codechicken.multipart.JCuboidPart;
/*  6:   */ import codechicken.multipart.JNormalOcclusion;
/*  7:   */ import codechicken.multipart.NormalOcclusionTest;
/*  8:   */ import codechicken.multipart.TMultiPart;
/*  9:   */ import com.rwtema.extrautils.tileentity.transfernodes.pipes.IPipe;
/* 10:   */ import java.util.Arrays;
/* 11:   */ 
/* 12:   */ public class DummyPipePart
/* 13:   */   extends JCuboidPart
/* 14:   */   implements JNormalOcclusion, ISidedHollowConnect
/* 15:   */ {
/* 16:   */   public int dir;
/* 17:   */   public float h;
/* 18:   */   
/* 19:   */   public DummyPipePart(int dir, float h)
/* 20:   */   {
/* 21:19 */     this.dir = dir;
/* 22:20 */     this.h = h;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public boolean occlusionTest(TMultiPart npart)
/* 26:   */   {
/* 27:25 */     return ((npart instanceof IPipe)) || (NormalOcclusionTest.apply(this, npart));
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Iterable<Cuboid6> getOcclusionBoxes()
/* 31:   */   {
/* 32:30 */     return Arrays.asList(new Cuboid6[] { getBounds() });
/* 33:   */   }
/* 34:   */   
/* 35:   */   public Cuboid6 getBounds()
/* 36:   */   {
/* 37:35 */     switch (this.dir)
/* 38:   */     {
/* 39:   */     case 0: 
/* 40:37 */       return new Cuboid6(0.375D, 0.0D, 0.375D, 0.625D, this.h, 0.625D);
/* 41:   */     case 1: 
/* 42:40 */       return new Cuboid6(0.375D, 1.0F - this.h, 0.375D, 0.625D, 1.0D, 0.625D);
/* 43:   */     case 2: 
/* 44:43 */       return new Cuboid6(0.375D, 0.375D, 0.0D, 0.625D, 0.625D, this.h);
/* 45:   */     case 3: 
/* 46:46 */       return new Cuboid6(0.375D, 0.375D, 1.0F - this.h, 0.625D, 0.625D, 1.0D);
/* 47:   */     case 4: 
/* 48:49 */       return new Cuboid6(0.0D, 0.375D, 0.375D, this.h, 0.625D, 0.625D);
/* 49:   */     case 5: 
/* 50:52 */       return new Cuboid6(1.0F - this.h, 0.375D, 0.375D, 1.0D, 0.625D, 0.625D);
/* 51:   */     }
/* 52:55 */     return new Cuboid6(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
/* 53:   */   }
/* 54:   */   
/* 55:   */   public String getType()
/* 56:   */   {
/* 57:61 */     return "dummyPipe";
/* 58:   */   }
/* 59:   */   
/* 60:   */   public int getHollowSize(int side)
/* 61:   */   {
/* 62:66 */     return 2;
/* 63:   */   }
/* 64:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.multiparts.DummyPipePart
 * JD-Core Version:    0.7.0.1
 */