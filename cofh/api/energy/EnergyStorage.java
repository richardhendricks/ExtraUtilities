/*   1:    */ package cofh.api.energy;
/*   2:    */ 
/*   3:    */ import net.minecraft.nbt.NBTTagCompound;
/*   4:    */ 
/*   5:    */ public class EnergyStorage
/*   6:    */   implements IEnergyStorage
/*   7:    */ {
/*   8:    */   protected int energy;
/*   9:    */   protected int capacity;
/*  10:    */   protected int maxReceive;
/*  11:    */   protected int maxExtract;
/*  12:    */   
/*  13:    */   public EnergyStorage(int capacity)
/*  14:    */   {
/*  15: 20 */     this(capacity, capacity, capacity);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public EnergyStorage(int capacity, int maxTransfer)
/*  19:    */   {
/*  20: 25 */     this(capacity, maxTransfer, maxTransfer);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public EnergyStorage(int capacity, int maxReceive, int maxExtract)
/*  24:    */   {
/*  25: 30 */     this.capacity = capacity;
/*  26: 31 */     this.maxReceive = maxReceive;
/*  27: 32 */     this.maxExtract = maxExtract;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public EnergyStorage readFromNBT(NBTTagCompound nbt)
/*  31:    */   {
/*  32: 37 */     this.energy = nbt.getInteger("Energy");
/*  33: 39 */     if (this.energy > this.capacity) {
/*  34: 40 */       this.energy = this.capacity;
/*  35:    */     }
/*  36: 42 */     return this;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public NBTTagCompound writeToNBT(NBTTagCompound nbt)
/*  40:    */   {
/*  41: 47 */     if (this.energy < 0) {
/*  42: 48 */       this.energy = 0;
/*  43:    */     }
/*  44: 50 */     nbt.setInteger("Energy", this.energy);
/*  45: 51 */     return nbt;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setCapacity(int capacity)
/*  49:    */   {
/*  50: 56 */     this.capacity = capacity;
/*  51: 58 */     if (this.energy > capacity) {
/*  52: 59 */       this.energy = capacity;
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setMaxTransfer(int maxTransfer)
/*  57:    */   {
/*  58: 65 */     setMaxReceive(maxTransfer);
/*  59: 66 */     setMaxExtract(maxTransfer);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setMaxReceive(int maxReceive)
/*  63:    */   {
/*  64: 71 */     this.maxReceive = maxReceive;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setMaxExtract(int maxExtract)
/*  68:    */   {
/*  69: 76 */     this.maxExtract = maxExtract;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int getMaxReceive()
/*  73:    */   {
/*  74: 81 */     return this.maxReceive;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getMaxExtract()
/*  78:    */   {
/*  79: 86 */     return this.maxExtract;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setEnergyStored(int energy)
/*  83:    */   {
/*  84: 97 */     this.energy = energy;
/*  85: 99 */     if (this.energy > this.capacity) {
/*  86:100 */       this.energy = this.capacity;
/*  87:101 */     } else if (this.energy < 0) {
/*  88:102 */       this.energy = 0;
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void modifyEnergyStored(int energy)
/*  93:    */   {
/*  94:114 */     this.energy += energy;
/*  95:116 */     if (this.energy > this.capacity) {
/*  96:117 */       this.energy = this.capacity;
/*  97:118 */     } else if (this.energy < 0) {
/*  98:119 */       this.energy = 0;
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public int receiveEnergy(int maxReceive, boolean simulate)
/* 103:    */   {
/* 104:127 */     int energyReceived = Math.min(this.capacity - this.energy, Math.min(this.maxReceive, maxReceive));
/* 105:129 */     if (!simulate) {
/* 106:130 */       this.energy += energyReceived;
/* 107:    */     }
/* 108:132 */     return energyReceived;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public int extractEnergy(int maxExtract, boolean simulate)
/* 112:    */   {
/* 113:138 */     int energyExtracted = Math.min(this.energy, Math.min(this.maxExtract, maxExtract));
/* 114:140 */     if (!simulate) {
/* 115:141 */       this.energy -= energyExtracted;
/* 116:    */     }
/* 117:143 */     return energyExtracted;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public int getEnergyStored()
/* 121:    */   {
/* 122:149 */     return this.energy;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public int getMaxEnergyStored()
/* 126:    */   {
/* 127:155 */     return this.capacity;
/* 128:    */   }
/* 129:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     cofh.api.energy.EnergyStorage
 * JD-Core Version:    0.7.0.1
 */