/*  1:   */ package com.rwtema.extrautils;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.tileentity.enderquarry.IChunkLoad;
/*  4:   */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*  5:   */ import java.util.Collection;
/*  6:   */ import java.util.Iterator;
/*  7:   */ import java.util.Map;
/*  8:   */ import net.minecraft.world.chunk.Chunk;
/*  9:   */ import net.minecraftforge.event.world.ChunkEvent.Load;
/* 10:   */ 
/* 11:   */ public class EventHandlerChunkLoad
/* 12:   */ {
/* 13:   */   @SubscribeEvent
/* 14:   */   public void load(ChunkEvent.Load event)
/* 15:   */   {
/* 16:14 */     Iterator iter = event.getChunk().chunkTileEntityMap.values().iterator();
/* 17:15 */     while (iter.hasNext())
/* 18:   */     {
/* 19:16 */       Object t = iter.next();
/* 20:17 */       if ((t instanceof IChunkLoad)) {
/* 21:18 */         ((IChunkLoad)t).onChunkLoad();
/* 22:   */       }
/* 23:   */     }
/* 24:   */   }
/* 25:   */   
/* 26:   */   @SubscribeEvent
/* 27:   */   public void unload(ChunkEvent.Load event)
/* 28:   */   {
/* 29:24 */     Iterator iter = event.getChunk().chunkTileEntityMap.values().iterator();
/* 30:25 */     while (iter.hasNext())
/* 31:   */     {
/* 32:26 */       Object t = iter.next();
/* 33:27 */       if ((t instanceof IChunkLoad)) {
/* 34:28 */         ((IChunkLoad)t).onChunkUnload();
/* 35:   */       }
/* 36:   */     }
/* 37:   */   }
/* 38:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.EventHandlerChunkLoad
 * JD-Core Version:    0.7.0.1
 */