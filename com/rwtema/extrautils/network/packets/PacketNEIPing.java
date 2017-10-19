/*   1:    */ package com.rwtema.extrautils.network.packets;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.network.NetworkHandler;
/*   4:    */ import com.rwtema.extrautils.network.XUPacketBase;
/*   5:    */ import cpw.mods.fml.relauncher.Side;
/*   6:    */ import io.netty.buffer.ByteBuf;
/*   7:    */ import io.netty.channel.ChannelHandlerContext;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Comparator;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.Map.Entry;
/*  12:    */ import java.util.Set;
/*  13:    */ import java.util.TreeSet;
/*  14:    */ import java.util.WeakHashMap;
/*  15:    */ import net.minecraft.entity.player.EntityPlayer;
/*  16:    */ import net.minecraft.inventory.IInventory;
/*  17:    */ import net.minecraft.item.Item;
/*  18:    */ import net.minecraft.item.ItemStack;
/*  19:    */ import net.minecraft.world.ChunkPosition;
/*  20:    */ import net.minecraft.world.World;
/*  21:    */ import net.minecraft.world.chunk.Chunk;
/*  22:    */ 
/*  23:    */ public class PacketNEIPing
/*  24:    */   extends XUPacketBase
/*  25:    */ {
/*  26:    */   private ItemStack itemStack;
/*  27:    */   private EntityPlayer player;
/*  28:    */   
/*  29:    */   public PacketNEIPing() {}
/*  30:    */   
/*  31:    */   public PacketNEIPing(ItemStack itemStack)
/*  32:    */   {
/*  33: 31 */     this.itemStack = itemStack;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void writeData(ByteBuf data)
/*  37:    */     throws Exception
/*  38:    */   {
/*  39: 36 */     writeItemStack(data, this.itemStack);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void readData(EntityPlayer player, ByteBuf data)
/*  43:    */   {
/*  44: 41 */     this.player = player;
/*  45: 42 */     this.itemStack = readItemStack(data);
/*  46:    */   }
/*  47:    */   
/*  48: 45 */   static WeakHashMap<EntityPlayer, Long> timeOutsHandler = new WeakHashMap();
/*  49:    */   static final long TIMEOUT = 10L;
/*  50:    */   static final int RANGE = 16;
/*  51:    */   
/*  52:    */   public void doStuffServer(ChannelHandlerContext ctx)
/*  53:    */   {
/*  54: 52 */     if ((this.player == null) || (this.itemStack == null) || (this.itemStack.getItem() == null)) {
/*  55: 52 */       return;
/*  56:    */     }
/*  57: 54 */     World world = this.player.worldObj;
/*  58:    */     
/*  59: 56 */     long time = world.getTotalWorldTime();
/*  60: 57 */     Long aLong = (Long)timeOutsHandler.get(this.player);
/*  61: 58 */     if ((aLong != null) && 
/*  62: 59 */       (time - aLong.longValue() < 10L)) {
/*  63: 60 */       return;
/*  64:    */     }
/*  65: 63 */     timeOutsHandler.put(this.player, Long.valueOf(time));
/*  66:    */     
/*  67: 65 */     final int x = (int)Math.round(this.player.posX);
/*  68: 66 */     final int y = (int)Math.round(this.player.posY);
/*  69: 67 */     final int z = (int)Math.round(this.player.posZ);
/*  70:    */     
/*  71: 69 */     Item trueItem = this.itemStack.getItem();
/*  72: 70 */     int trueItemDamage = this.itemStack.getItemDamage();
/*  73:    */     
/*  74: 72 */     TreeSet<ChunkPosition> positions = new TreeSet(new Comparator()
/*  75:    */     {
/*  76:    */       public int compare(ChunkPosition o1, ChunkPosition o2)
/*  77:    */       {
/*  78: 75 */         return Double.compare(PacketNEIPing.this.getRange(x, y, z, o1), PacketNEIPing.this.getRange(x, y, z, o2));
/*  79:    */       }
/*  80:    */     });
/*  81: 79 */     for (int cx = x - 16; cx <= x + 16; cx += 16)
/*  82:    */     {
/*  83:    */       Chunk chunk;
/*  84: 80 */       for (int cz = z - 16; cz <= z + 16; cz += 16) {
/*  85: 81 */         if (world.blockExists(cx, y, cz))
/*  86:    */         {
/*  87: 82 */           chunk = world.getChunkFromBlockCoords(cx, cz);
/*  88:    */           
/*  89: 84 */           Set<Map.Entry<ChunkPosition, Object>> entrySet = chunk.chunkTileEntityMap.entrySet();
/*  90: 85 */           for (Map.Entry<ChunkPosition, Object> entry : entrySet)
/*  91:    */           {
/*  92: 86 */             ChunkPosition e = (ChunkPosition)entry.getKey();
/*  93: 87 */             ChunkPosition key = new ChunkPosition(chunk.xPosition * 16 + e.chunkPosX, e.chunkPosY, chunk.zPosition * 16 + e.chunkPosZ);
/*  94: 89 */             if (inRange(x, y, z, key))
/*  95:    */             {
/*  96: 92 */               Object value = entry.getValue();
/*  97: 93 */               if ((value instanceof IInventory))
/*  98:    */               {
/*  99: 96 */                 IInventory inv = (IInventory)value;
/* 100: 97 */                 for (int i = 0; i < inv.getSizeInventory(); i++)
/* 101:    */                 {
/* 102: 98 */                   ItemStack stackInSlot = inv.getStackInSlot(i);
/* 103: 99 */                   if ((stackInSlot != null) && 
/* 104:100 */                     (stackInSlot.getItem() == trueItem)) {
/* 105:102 */                     if ((!trueItem.getHasSubtypes()) || (stackInSlot.getItemDamage() == trueItemDamage))
/* 106:    */                     {
/* 107:106 */                       positions.add(key);
/* 108:108 */                       if (positions.size() < 20) {
/* 109:    */                         break;
/* 110:    */                       }
/* 111:109 */                       positions.pollLast(); break;
/* 112:    */                     }
/* 113:    */                   }
/* 114:    */                 }
/* 115:    */               }
/* 116:    */             }
/* 117:    */           }
/* 118:    */         }
/* 119:    */       }
/* 120:    */     }
/* 121:117 */     if (!positions.isEmpty()) {
/* 122:118 */       NetworkHandler.sendPacketToPlayer(new PacketNEIPong(new ArrayList(positions)), this.player);
/* 123:    */     }
/* 124:    */   }
/* 125:    */   
/* 126:    */   public int getRange(int x, int y, int z, ChunkPosition pos)
/* 127:    */   {
/* 128:123 */     return Math.abs(pos.chunkPosX - x) + Math.abs(pos.chunkPosY - y) + Math.abs(pos.chunkPosZ - z);
/* 129:    */   }
/* 130:    */   
/* 131:    */   public boolean inRange(int x, int y, int z, ChunkPosition pos)
/* 132:    */   {
/* 133:127 */     return (Math.abs(pos.chunkPosX - x) <= 16) && (Math.abs(pos.chunkPosY - y) <= 16) && (Math.abs(pos.chunkPosZ - z) <= 16);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void doStuffClient() {}
/* 137:    */   
/* 138:    */   public boolean isValidSenderSide(Side properSenderSide)
/* 139:    */   {
/* 140:137 */     return properSenderSide.isClient();
/* 141:    */   }
/* 142:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.network.packets.PacketNEIPing
 * JD-Core Version:    0.7.0.1
 */