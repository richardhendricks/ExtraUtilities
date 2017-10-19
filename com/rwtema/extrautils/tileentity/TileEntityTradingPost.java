/*  1:   */ package com.rwtema.extrautils.tileentity;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.network.XUPacketBase;
/*  4:   */ import com.rwtema.extrautils.network.packets.PacketVillager;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ import java.util.List;
/*  7:   */ import net.minecraft.entity.EntityLiving;
/*  8:   */ import net.minecraft.entity.IMerchant;
/*  9:   */ import net.minecraft.entity.player.EntityPlayer;
/* 10:   */ import net.minecraft.nbt.NBTTagCompound;
/* 11:   */ import net.minecraft.tileentity.TileEntity;
/* 12:   */ import net.minecraft.util.AxisAlignedBB;
/* 13:   */ import net.minecraft.util.Vec3;
/* 14:   */ import net.minecraft.village.MerchantRecipeList;
/* 15:   */ import net.minecraft.world.World;
/* 16:   */ 
/* 17:   */ public class TileEntityTradingPost
/* 18:   */   extends TileEntity
/* 19:   */ {
/* 20:18 */   public static int maxRange = 32;
/* 21:19 */   public int[] ids = null;
/* 22:20 */   public MerchantRecipeList[] data = null;
/* 23:   */   
/* 24:   */   public boolean canUpdate()
/* 25:   */   {
/* 26:24 */     return false;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public AxisAlignedBB getAABB()
/* 30:   */   {
/* 31:28 */     return AxisAlignedBB.getBoundingBox(this.xCoord + 0.5D - maxRange, 0.0D, this.zCoord + 0.5D - maxRange, this.xCoord + 0.5D + maxRange, 255.0D, this.zCoord + 0.5D + maxRange);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public List<IMerchant> getVillagers()
/* 35:   */   {
/* 36:32 */     List t = this.worldObj.getEntitiesWithinAABB(IMerchant.class, getAABB());
/* 37:33 */     List<IMerchant> traders = new ArrayList();
/* 38:35 */     for (Object aT : t) {
/* 39:36 */       if (isValidVillager((IMerchant)aT, true)) {
/* 40:37 */         traders.add((IMerchant)aT);
/* 41:   */       }
/* 42:   */     }
/* 43:40 */     return traders;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public boolean isValidVillager(IMerchant villager, boolean locationAlreadyChecked)
/* 47:   */   {
/* 48:44 */     return ((villager instanceof EntityLiving)) && (!((EntityLiving)villager).isChild()) && ((locationAlreadyChecked) || (getAABB().isVecInside(Vec3.createVectorHelper(((EntityLiving)villager).posX, ((EntityLiving)villager).posY, ((EntityLiving)villager).posZ))));
/* 49:   */   }
/* 50:   */   
/* 51:   */   public XUPacketBase getTradePacket(EntityPlayer player)
/* 52:   */   {
/* 53:49 */     List<IMerchant> traders = getVillagers();
/* 54:51 */     if ((traders == null) || (traders.size() == 0)) {
/* 55:52 */       return null;
/* 56:   */     }
/* 57:55 */     NBTTagCompound pkt = new NBTTagCompound();
/* 58:56 */     int n = 0;
/* 59:57 */     pkt.setInteger("player_id", player.getEntityId());
/* 60:59 */     for (int i = 0; i < traders.size(); i++)
/* 61:   */     {
/* 62:60 */       IMerchant v = (IMerchant)traders.get(i);
/* 63:61 */       pkt.setInteger("i" + i, ((EntityLiving)v).getEntityId());
/* 64:62 */       pkt.setTag("t" + i, v.getRecipes(null).getRecipiesAsTags());
/* 65:63 */       n++;
/* 66:   */     }
/* 67:66 */     if (n == 0) {
/* 68:67 */       return null;
/* 69:   */     }
/* 70:70 */     pkt.setInteger("n", n);
/* 71:71 */     return new PacketVillager(this.xCoord, this.yCoord, this.zCoord, pkt);
/* 72:   */   }
/* 73:   */   
/* 74:   */   public double distSq(double x, double y, double z)
/* 75:   */   {
/* 76:75 */     x -= this.xCoord + 0.5D;
/* 77:76 */     y -= this.yCoord + 0.5D;
/* 78:77 */     z -= this.zCoord + 0.5D;
/* 79:78 */     return x * x + y * y + z * z;
/* 80:   */   }
/* 81:   */   
/* 82:   */   public int toInt(Object x)
/* 83:   */   {
/* 84:82 */     if ((x instanceof Double)) {
/* 85:83 */       return (int)Math.floor(((Double)x).doubleValue());
/* 86:   */     }
/* 87:86 */     if ((x instanceof Float)) {
/* 88:87 */       return (int)Math.floor(((Float)x).floatValue());
/* 89:   */     }
/* 90:90 */     if ((x instanceof Integer)) {
/* 91:91 */       return ((Integer)x).intValue();
/* 92:   */     }
/* 93:94 */     if ((x instanceof String)) {
/* 94:95 */       return Integer.parseInt((String)x);
/* 95:   */     }
/* 96:98 */     return 0;
/* 97:   */   }
/* 98:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.TileEntityTradingPost
 * JD-Core Version:    0.7.0.1
 */