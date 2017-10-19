/*  1:   */ package com.rwtema.extrautils.multipart;
/*  2:   */ 
/*  3:   */ import codechicken.lib.vec.BlockCoord;
/*  4:   */ import codechicken.multipart.MultiPartRegistry;
/*  5:   */ import codechicken.multipart.MultiPartRegistry.IPartConverter;
/*  6:   */ import codechicken.multipart.MultiPartRegistry.IPartFactory;
/*  7:   */ import codechicken.multipart.TMultiPart;
/*  8:   */ import java.lang.reflect.Constructor;
/*  9:   */ import java.util.HashSet;
/* 10:   */ import java.util.Set;
/* 11:   */ import net.minecraft.block.Block;
/* 12:   */ import net.minecraft.world.World;
/* 13:   */ 
/* 14:   */ public class RegisterBlockPart
/* 15:   */   implements MultiPartRegistry.IPartConverter, MultiPartRegistry.IPartFactory
/* 16:   */ {
/* 17:14 */   public Set<Block> t = null;
/* 18:15 */   Block block = null;
/* 19:16 */   Class<? extends TMultiPart> part = null;
/* 20:17 */   String name = "";
/* 21:   */   
/* 22:   */   public RegisterBlockPart(Block block, Class<? extends TMultiPart> part)
/* 23:   */   {
/* 24:   */     try
/* 25:   */     {
/* 26:21 */       this.name = ((TMultiPart)part.getConstructor(new Class[0]).newInstance(new Object[0])).getType();
/* 27:   */     }
/* 28:   */     catch (Exception e)
/* 29:   */     {
/* 30:23 */       e.printStackTrace();
/* 31:   */     }
/* 32:   */   }
/* 33:   */   
/* 34:   */   public RegisterBlockPart(Block block, Class<? extends TMultiPart> part, String name)
/* 35:   */   {
/* 36:28 */     this.block = block;
/* 37:29 */     this.part = part;
/* 38:30 */     this.name = name;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public TMultiPart createPart(String name, boolean client)
/* 42:   */   {
/* 43:35 */     if (name.equals(name)) {
/* 44:   */       try
/* 45:   */       {
/* 46:37 */         return (TMultiPart)this.part.getConstructor(new Class[0]).newInstance(new Object[0]);
/* 47:   */       }
/* 48:   */       catch (Exception e)
/* 49:   */       {
/* 50:39 */         e.printStackTrace();
/* 51:40 */         return null;
/* 52:   */       }
/* 53:   */     }
/* 54:43 */     return null;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void init()
/* 58:   */   {
/* 59:47 */     if ((this.name.equals("")) || (this.block == null) || (this.part == null)) {
/* 60:48 */       return;
/* 61:   */     }
/* 62:51 */     MultiPartRegistry.registerConverter(this);
/* 63:52 */     MultiPartRegistry.registerParts(this, new String[] { this.name });
/* 64:   */   }
/* 65:   */   
/* 66:   */   public Iterable<Block> blockTypes()
/* 67:   */   {
/* 68:57 */     if (this.t == null)
/* 69:   */     {
/* 70:58 */       this.t = new HashSet();
/* 71:59 */       this.t.add(this.block);
/* 72:   */     }
/* 73:62 */     return this.t;
/* 74:   */   }
/* 75:   */   
/* 76:   */   public TMultiPart convert(World world, BlockCoord pos)
/* 77:   */   {
/* 78:67 */     Block id = world.getBlock(pos.x, pos.y, pos.z);
/* 79:69 */     if (id == this.block) {
/* 80:   */       try
/* 81:   */       {
/* 82:71 */         return (TMultiPart)this.part.getConstructor(new Class[0]).newInstance(new Object[0]);
/* 83:   */       }
/* 84:   */       catch (Exception e)
/* 85:   */       {
/* 86:73 */         e.printStackTrace();
/* 87:74 */         return null;
/* 88:   */       }
/* 89:   */     }
/* 90:78 */     return null;
/* 91:   */   }
/* 92:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.multipart.RegisterBlockPart
 * JD-Core Version:    0.7.0.1
 */