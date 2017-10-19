/*   1:    */ package cofh.api.energy;
/*   2:    */ 
/*   3:    */ import net.minecraft.item.Item;
/*   4:    */ import net.minecraft.item.ItemStack;
/*   5:    */ import net.minecraft.nbt.NBTTagCompound;
/*   6:    */ 
/*   7:    */ public class ItemEnergyContainer
/*   8:    */   extends Item
/*   9:    */   implements IEnergyContainerItem
/*  10:    */ {
/*  11:    */   protected int capacity;
/*  12:    */   protected int maxReceive;
/*  13:    */   protected int maxExtract;
/*  14:    */   
/*  15:    */   public ItemEnergyContainer() {}
/*  16:    */   
/*  17:    */   public ItemEnergyContainer(int capacity)
/*  18:    */   {
/*  19: 25 */     this(capacity, capacity, capacity);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public ItemEnergyContainer(int capacity, int maxTransfer)
/*  23:    */   {
/*  24: 30 */     this(capacity, maxTransfer, maxTransfer);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public ItemEnergyContainer(int capacity, int maxReceive, int maxExtract)
/*  28:    */   {
/*  29: 35 */     this.capacity = capacity;
/*  30: 36 */     this.maxReceive = maxReceive;
/*  31: 37 */     this.maxExtract = maxExtract;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public ItemEnergyContainer setCapacity(int capacity)
/*  35:    */   {
/*  36: 42 */     this.capacity = capacity;
/*  37: 43 */     return this;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setMaxTransfer(int maxTransfer)
/*  41:    */   {
/*  42: 48 */     setMaxReceive(maxTransfer);
/*  43: 49 */     setMaxExtract(maxTransfer);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setMaxReceive(int maxReceive)
/*  47:    */   {
/*  48: 54 */     this.maxReceive = maxReceive;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setMaxExtract(int maxExtract)
/*  52:    */   {
/*  53: 59 */     this.maxExtract = maxExtract;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate)
/*  57:    */   {
/*  58: 66 */     if (container.stackTagCompound == null) {
/*  59: 67 */       container.stackTagCompound = new NBTTagCompound();
/*  60:    */     }
/*  61: 69 */     int energy = container.stackTagCompound.getInteger("Energy");
/*  62: 70 */     int energyReceived = Math.min(this.capacity - energy, Math.min(this.maxReceive, maxReceive));
/*  63: 72 */     if (!simulate)
/*  64:    */     {
/*  65: 73 */       energy += energyReceived;
/*  66: 74 */       container.stackTagCompound.setInteger("Energy", energy);
/*  67:    */     }
/*  68: 76 */     return energyReceived;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int extractEnergy(ItemStack container, int maxExtract, boolean simulate)
/*  72:    */   {
/*  73: 82 */     if ((container.stackTagCompound == null) || (!container.stackTagCompound.hasKey("Energy"))) {
/*  74: 83 */       return 0;
/*  75:    */     }
/*  76: 85 */     int energy = container.stackTagCompound.getInteger("Energy");
/*  77: 86 */     int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
/*  78: 88 */     if (!simulate)
/*  79:    */     {
/*  80: 89 */       energy -= energyExtracted;
/*  81: 90 */       container.stackTagCompound.setInteger("Energy", energy);
/*  82:    */     }
/*  83: 92 */     return energyExtracted;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int getEnergyStored(ItemStack container)
/*  87:    */   {
/*  88: 98 */     if ((container.stackTagCompound == null) || (!container.stackTagCompound.hasKey("Energy"))) {
/*  89: 99 */       return 0;
/*  90:    */     }
/*  91:101 */     return container.stackTagCompound.getInteger("Energy");
/*  92:    */   }
/*  93:    */   
/*  94:    */   public int getMaxEnergyStored(ItemStack container)
/*  95:    */   {
/*  96:107 */     return this.capacity;
/*  97:    */   }
/*  98:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     cofh.api.energy.ItemEnergyContainer
 * JD-Core Version:    0.7.0.1
 */