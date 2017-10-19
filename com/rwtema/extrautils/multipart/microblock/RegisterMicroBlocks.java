/*  1:   */ package com.rwtema.extrautils.multipart.microblock;
/*  2:   */ 
/*  3:   */ import codechicken.multipart.MultiPartRegistry;
/*  4:   */ import codechicken.multipart.MultiPartRegistry.IPartFactory;
/*  5:   */ import codechicken.multipart.TMultiPart;
/*  6:   */ import java.util.HashMap;
/*  7:   */ import java.util.Map;
/*  8:   */ 
/*  9:   */ public class RegisterMicroBlocks
/* 10:   */   implements MultiPartRegistry.IPartFactory
/* 11:   */ {
/* 12:11 */   public static RegisterMicroBlocks instance = new RegisterMicroBlocks();
/* 13:13 */   public static Map<Integer, IMicroBlock> mParts = new HashMap();
/* 14:14 */   public static Map<String, Integer> mIds = new HashMap();
/* 15:   */   
/* 16:   */   static
/* 17:   */   {
/* 18:17 */     register(new PartPipeJacket());
/* 19:18 */     register(new PartFence());
/* 20:19 */     register(new PartWall());
/* 21:20 */     register(new PartSphere());
/* 22:   */   }
/* 23:   */   
/* 24:   */   public static void register(IMicroBlock block)
/* 25:   */   {
/* 26:24 */     mParts.put(Integer.valueOf(block.getMetadata()), block);
/* 27:25 */     mIds.put(block.getType(), Integer.valueOf(block.getMetadata()));
/* 28:   */   }
/* 29:   */   
/* 30:   */   public static void register()
/* 31:   */   {
/* 32:29 */     String[] s = new String[mParts.size()];
/* 33:31 */     for (int i = 0; i < mParts.size(); i++)
/* 34:   */     {
/* 35:32 */       s[i] = ((IMicroBlock)mParts.get(Integer.valueOf(i))).getType();
/* 36:33 */       ((IMicroBlock)mParts.get(Integer.valueOf(i))).registerPassThroughs();
/* 37:   */     }
/* 38:36 */     MultiPartRegistry.registerParts(instance, s);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public TMultiPart createPart(String arg0, boolean arg1)
/* 42:   */   {
/* 43:41 */     return ((IMicroBlock)mParts.get(mIds.get(arg0))).newPart(arg1);
/* 44:   */   }
/* 45:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.multipart.microblock.RegisterMicroBlocks
 * JD-Core Version:    0.7.0.1
 */