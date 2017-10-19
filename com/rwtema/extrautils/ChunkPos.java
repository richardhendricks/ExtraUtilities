/*  1:   */ package com.rwtema.extrautils;
/*  2:   */ 
/*  3:   */ import net.minecraft.util.MathHelper;
/*  4:   */ import net.minecraft.util.Vec3;
/*  5:   */ 
/*  6:   */ public class ChunkPos
/*  7:   */ {
/*  8:   */   public int x;
/*  9:   */   public int y;
/* 10:   */   public int z;
/* 11:   */   
/* 12:   */   public ChunkPos(int x, int y, int z)
/* 13:   */   {
/* 14:12 */     this.x = x;
/* 15:13 */     this.y = y;
/* 16:14 */     this.z = z;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public ChunkPos(Vec3 p_i45364_1_)
/* 20:   */   {
/* 21:18 */     this(MathHelper.floor_double(p_i45364_1_.xCoord), MathHelper.floor_double(p_i45364_1_.yCoord), MathHelper.floor_double(p_i45364_1_.zCoord));
/* 22:   */   }
/* 23:   */   
/* 24:   */   public boolean equals(Object par1Obj)
/* 25:   */   {
/* 26:22 */     if (!(par1Obj instanceof ChunkPos)) {
/* 27:23 */       return false;
/* 28:   */     }
/* 29:25 */     ChunkPos pos = (ChunkPos)par1Obj;
/* 30:26 */     return (pos.x == this.x) && (pos.y == this.y) && (pos.z == this.z);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public int hashCode()
/* 34:   */   {
/* 35:31 */     return this.x * 8976890 + this.y * 981131 + this.z;
/* 36:   */   }
/* 37:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.ChunkPos
 * JD-Core Version:    0.7.0.1
 */