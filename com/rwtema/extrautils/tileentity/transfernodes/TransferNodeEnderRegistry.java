/*  1:   */ package com.rwtema.extrautils.tileentity.transfernodes;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INode;
/*  4:   */ import com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeBuffer;
/*  5:   */ import java.lang.ref.WeakReference;
/*  6:   */ import java.util.ArrayList;
/*  7:   */ import java.util.Collections;
/*  8:   */ import java.util.HashMap;
/*  9:   */ import java.util.HashSet;
/* 10:   */ import java.util.Iterator;
/* 11:   */ import java.util.List;
/* 12:   */ import java.util.Set;
/* 13:   */ import net.minecraft.tileentity.TileEntity;
/* 14:   */ import net.minecraft.world.World;
/* 15:   */ import net.minecraft.world.WorldProvider;
/* 16:   */ 
/* 17:   */ public class TransferNodeEnderRegistry
/* 18:   */ {
/* 19:10 */   private static final HashMap<Frequency, List<WeakReference<INodeBuffer>>> receptors = new HashMap();
/* 20:   */   
/* 21:   */   public static synchronized void clearTileRegistrations(INodeBuffer buffer)
/* 22:   */   {
/* 23:13 */     synchronized (receptors)
/* 24:   */     {
/* 25:14 */       Set<Frequency> t = null;
/* 26:15 */       for (Frequency fs : receptors.keySet())
/* 27:   */       {
/* 28:16 */         List<WeakReference<INodeBuffer>> list = (List)receptors.get(fs);
/* 29:17 */         Iterator<WeakReference<INodeBuffer>> iter = list.iterator();
/* 30:18 */         while (iter.hasNext())
/* 31:   */         {
/* 32:19 */           INodeBuffer next = (INodeBuffer)((WeakReference)iter.next()).get();
/* 33:20 */           if ((next == null) || (next == buffer)) {
/* 34:21 */             iter.remove();
/* 35:   */           }
/* 36:   */         }
/* 37:24 */         if (list.isEmpty())
/* 38:   */         {
/* 39:25 */           if (t == null) {
/* 40:26 */             t = new HashSet();
/* 41:   */           }
/* 42:27 */           t.add(fs);
/* 43:   */         }
/* 44:   */       }
/* 45:31 */       if (t != null) {
/* 46:32 */         for (Frequency fs : t) {
/* 47:33 */           receptors.remove(fs);
/* 48:   */         }
/* 49:   */       }
/* 50:   */     }
/* 51:   */   }
/* 52:   */   
/* 53:   */   public static synchronized void registerTile(Frequency freq, INodeBuffer buffer)
/* 54:   */   {
/* 55:38 */     synchronized (receptors)
/* 56:   */     {
/* 57:39 */       TileEntity a = buffer.getNode().getNode();
/* 58:40 */       for (Frequency fs : receptors.keySet())
/* 59:   */       {
/* 60:41 */         Iterator<WeakReference<INodeBuffer>> iter = ((List)receptors.get(fs)).iterator();
/* 61:42 */         while (iter.hasNext())
/* 62:   */         {
/* 63:43 */           INodeBuffer next = (INodeBuffer)((WeakReference)iter.next()).get();
/* 64:44 */           if (next == null)
/* 65:   */           {
/* 66:45 */             iter.remove();
/* 67:   */           }
/* 68:   */           else
/* 69:   */           {
/* 70:47 */             TileEntity b = next.getNode().getNode();
/* 71:48 */             if ((a.xCoord == b.xCoord) && (a.zCoord == b.zCoord) && (a.yCoord == b.yCoord) && (a.getWorldObj().provider.dimensionId == b.getWorldObj().provider.dimensionId)) {
/* 72:50 */               return;
/* 73:   */             }
/* 74:   */           }
/* 75:   */         }
/* 76:   */       }
/* 77:56 */       List<WeakReference<INodeBuffer>> b = (List)receptors.get(freq);
/* 78:57 */       if (b == null)
/* 79:   */       {
/* 80:58 */         b = new ArrayList();
/* 81:59 */         receptors.put(freq, b);
/* 82:   */       }
/* 83:62 */       b.add(new WeakReference(buffer));
/* 84:   */     }
/* 85:   */   }
/* 86:   */   
/* 87:   */   public static synchronized void doTransfer(INodeBuffer sender, Frequency f, int no)
/* 88:   */   {
/* 89:67 */     List<WeakReference<INodeBuffer>> b = (List)receptors.get(f);
/* 90:68 */     if (b == null) {
/* 91:69 */       return;
/* 92:   */     }
/* 93:71 */     Collections.shuffle(b);
/* 94:   */     
/* 95:73 */     Iterator<WeakReference<INodeBuffer>> iterator = b.iterator();
/* 96:75 */     while (iterator.hasNext())
/* 97:   */     {
/* 98:76 */       WeakReference<INodeBuffer> ref = (WeakReference)iterator.next();
/* 99:77 */       INodeBuffer reciever = (INodeBuffer)ref.get();
/* :0:78 */       if (reciever == null) {
/* :1:79 */         iterator.remove();
/* :2:80 */       } else if (reciever != sender) {
/* :3:81 */         sender.transferTo(reciever, no);
/* :4:   */       }
/* :5:   */     }
/* :6:   */   }
/* :7:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.transfernodes.TransferNodeEnderRegistry
 * JD-Core Version:    0.7.0.1
 */