/*   1:    */ package com.rwtema.extrautils.specialevents;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.IClientCode;
/*   4:    */ import com.rwtema.extrautils.LogHelper;
/*   5:    */ import com.rwtema.extrautils.helper.XURandom;
/*   6:    */ import com.rwtema.extrautils.particle.Particles;
/*   7:    */ import cpw.mods.fml.common.FMLCommonHandler;
/*   8:    */ import cpw.mods.fml.common.eventhandler.EventBus;
/*   9:    */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*  10:    */ import cpw.mods.fml.common.gameevent.TickEvent.Phase;
/*  11:    */ import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
/*  12:    */ import cpw.mods.fml.relauncher.Side;
/*  13:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  14:    */ import gnu.trove.map.hash.TObjectIntHashMap;
/*  15:    */ import net.minecraft.client.Minecraft;
/*  16:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*  17:    */ import net.minecraft.entity.ai.attributes.IAttribute;
/*  18:    */ import net.minecraft.entity.player.EntityPlayer;
/*  19:    */ import net.minecraft.nbt.NBTTagCompound;
/*  20:    */ import net.minecraft.world.World;
/*  21:    */ import net.minecraft.world.WorldProvider;
/*  22:    */ import net.minecraft.world.chunk.Chunk;
/*  23:    */ import net.minecraftforge.common.MinecraftForge;
/*  24:    */ import net.minecraftforge.event.world.ChunkDataEvent.Load;
/*  25:    */ import net.minecraftforge.event.world.ChunkDataEvent.Save;
/*  26:    */ 
/*  27:    */ public class SpecialEventCoordinator
/*  28:    */ {
/*  29:    */   public final String maxHealthName;
/*  30:    */   public final int CAP = 2250;
/*  31:    */   public final String soulDranTag = "XU_SoulDrain";
/*  32: 26 */   public static final XURandom random = ;
/*  33:    */   public TObjectIntHashMap<ChunkLocation> chunkmap;
/*  34:    */   
/*  35:    */   public void init()
/*  36:    */   {
/*  37: 29 */     FMLCommonHandler.instance().bus().register(this);
/*  38: 30 */     MinecraftForge.EVENT_BUS.register(this);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public SpecialEventCoordinator()
/*  42:    */   {
/*  43: 23 */     this.maxHealthName = SharedMonsterAttributes.maxHealth.getAttributeUnlocalizedName();
/*  44: 24 */     this.CAP = 2250;
/*  45: 25 */     this.soulDranTag = "XU_SoulDrain";
/*  46:    */     
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53: 33 */     this.chunkmap = new TObjectIntHashMap(10, 0.5F, 0);
/*  54:    */   }
/*  55:    */   
/*  56:    */   @SubscribeEvent
/*  57:    */   public void getChunkData(ChunkDataEvent.Save event)
/*  58:    */   {
/*  59: 38 */     Chunk chunk = event.getChunk();
/*  60: 39 */     int i = this.chunkmap.get(new ChunkLocation(chunk.worldObj.provider.dimensionId, chunk.xPosition, chunk.zPosition, null));
/*  61: 40 */     if (i != 0) {
/*  62: 41 */       event.getData().setInteger("XU_SoulDrain", i);
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   @SubscribeEvent
/*  67:    */   public void getChunkData(ChunkDataEvent.Load event)
/*  68:    */   {
/*  69: 46 */     int i = event.getData().getInteger("XU_SoulDrain");
/*  70: 47 */     if (i == 0) {
/*  71: 48 */       return;
/*  72:    */     }
/*  73: 49 */     Chunk chunk = event.getChunk();
/*  74: 50 */     this.chunkmap.put(new ChunkLocation(chunk.worldObj.provider.dimensionId, chunk.xPosition, chunk.zPosition, null), i);
/*  75:    */   }
/*  76:    */   
/*  77:    */   @SubscribeEvent
/*  78:    */   public void playerTick(TickEvent.PlayerTickEvent event)
/*  79:    */   {
/*  80: 55 */     if (event.phase == TickEvent.Phase.START) {
/*  81: 55 */       return;
/*  82:    */     }
/*  83: 56 */     EntityPlayer player = event.player;
/*  84: 57 */     if (player.worldObj.isClient) {
/*  85: 58 */       return;
/*  86:    */     }
/*  87: 61 */     if ((!LogHelper.isDeObf) || 
/*  88:    */     
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98:    */ 
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
/* 105:    */ 
/* 106:    */ 
/* 107:    */ 
/* 108:    */ 
/* 109:    */ 
/* 110: 84 */       ((player.worldObj.getTotalWorldTime() & 0x1F) > 0L)) {
/* 111: 85 */       return;
/* 112:    */     }
/* 113: 96 */     int i = this.chunkmap.adjustOrPutValue(new ChunkLocation(event.player), 1, 1);
/* 114: 97 */     if (i > 2250) {}
/* 115:    */   }
/* 116:    */   
/* 117:    */   private final class ChunkLocation
/* 118:    */   {
/* 119:    */     final int dim;
/* 120:    */     final int x;
/* 121:    */     final int z;
/* 122:    */     
/* 123:    */     public ChunkLocation(EntityPlayer player)
/* 124:    */     {
/* 125:109 */       this(player.worldObj.provider.dimensionId, (int)player.posX >> 4, (int)player.posZ >> 4);
/* 126:    */     }
/* 127:    */     
/* 128:    */     public String toString()
/* 129:    */     {
/* 130:114 */       return "ChunkLocation{x=" + this.x + ", z=" + this.z + '}';
/* 131:    */     }
/* 132:    */     
/* 133:    */     private ChunkLocation(int dim, int x, int z)
/* 134:    */     {
/* 135:118 */       this.dim = dim;
/* 136:119 */       this.x = x;
/* 137:120 */       this.z = z;
/* 138:    */     }
/* 139:    */     
/* 140:    */     public boolean equals(Object o)
/* 141:    */     {
/* 142:125 */       if (this == o) {
/* 143:125 */         return true;
/* 144:    */       }
/* 145:126 */       if ((o == null) || (getClass() != o.getClass())) {
/* 146:126 */         return false;
/* 147:    */       }
/* 148:128 */       ChunkLocation that = (ChunkLocation)o;
/* 149:    */       
/* 150:130 */       return (this.dim == that.dim) && (this.x == that.x) && (this.z == that.z);
/* 151:    */     }
/* 152:    */     
/* 153:    */     public int hashCode()
/* 154:    */     {
/* 155:136 */       int result = this.dim;
/* 156:137 */       result = 31 * result + this.x;
/* 157:138 */       result = 31 * result + this.z;
/* 158:139 */       return result;
/* 159:    */     }
/* 160:    */   }
/* 161:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.specialevents.SpecialEventCoordinator
 * JD-Core Version:    0.7.0.1
 */