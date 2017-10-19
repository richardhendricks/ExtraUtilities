/*   1:    */ package com.rwtema.extrautils.modintegration;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import cpw.mods.fml.common.event.FMLInterModComms;
/*   5:    */ import net.minecraft.init.Blocks;
/*   6:    */ import net.minecraft.item.ItemStack;
/*   7:    */ import net.minecraft.nbt.NBTTagCompound;
/*   8:    */ import net.minecraftforge.fluids.FluidRegistry;
/*   9:    */ import net.minecraftforge.fluids.FluidStack;
/*  10:    */ 
/*  11:    */ public class TE4IMC
/*  12:    */ {
/*  13:    */   public static void addIntegration()
/*  14:    */   {
/*  15: 15 */     if (ExtraUtils.decorative1 != null) {
/*  16: 16 */       addTransposerFill(0, new ItemStack(Blocks.obsidian), new ItemStack(ExtraUtils.decorative1, 1, 1), createFluidTag("ender", 50), false);
/*  17:    */     }
/*  18: 22 */     if (ExtraUtils.decorative2 != null) {
/*  19: 23 */       addSmelter(800, new ItemStack(Blocks.sand), new ItemStack(Blocks.glass), new ItemStack(ExtraUtils.decorative2, 2, 0));
/*  20:    */     }
/*  21: 26 */     if (ExtraUtils.enderLily != null) {
/*  22: 27 */       addCrucible(80000, new ItemStack(ExtraUtils.enderLily), createFluidTag("ender", 4000));
/*  23:    */     }
/*  24: 30 */     if ((ExtraUtils.decorative1 != null) && 
/*  25: 31 */       (FluidRegistry.getFluid("xpjuice") != null)) {
/*  26: 32 */       addTransposerFill(0, new ItemStack(Blocks.bookshelf, 1), new ItemStack(ExtraUtils.decorative1, 1, 8), createFluidTag("xpjuice", 8000), false);
/*  27:    */     }
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static void addSmelter(int i, ItemStack itemStack, ItemStack itemStack1, ItemStack itemStack2)
/*  31:    */   {
/*  32: 40 */     addSmelter(i, itemStack, itemStack1, itemStack2, null, 0);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static NBTTagCompound getItemStackNBT(ItemStack item, int newStackSize)
/*  36:    */   {
/*  37: 44 */     NBTTagCompound tag = getItemStackNBT(item);
/*  38: 45 */     tag.setByte("Count", (byte)newStackSize);
/*  39: 46 */     return tag;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static NBTTagCompound getItemStackNBT(ItemStack item)
/*  43:    */   {
/*  44: 50 */     NBTTagCompound tag = new NBTTagCompound();
/*  45: 51 */     item.writeToNBT(tag);
/*  46: 52 */     return tag;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static ItemStack cloneStack(ItemStack item, int newStackSize)
/*  50:    */   {
/*  51: 56 */     ItemStack newitem = item.copy();
/*  52: 57 */     newitem.stackSize = newStackSize;
/*  53: 58 */     return newitem;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static NBTTagCompound createFluidTag(String fluid, int amount)
/*  57:    */   {
/*  58: 62 */     NBTTagCompound nbt = new NBTTagCompound();
/*  59: 63 */     nbt.setString("FluidName", fluid);
/*  60: 64 */     nbt.setInteger("Amount", amount);
/*  61: 65 */     return nbt;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static void addSmelter(int energy, ItemStack a, ItemStack b, ItemStack output, ItemStack altOutput, int prob)
/*  65:    */   {
/*  66: 70 */     if ((a == null) || (b == null) || (output == null)) {
/*  67: 71 */       return;
/*  68:    */     }
/*  69: 73 */     if (energy <= 0) {
/*  70: 74 */       energy = 4000;
/*  71:    */     }
/*  72: 76 */     NBTTagCompound tag = new NBTTagCompound();
/*  73: 77 */     tag.setInteger("energy", energy);
/*  74: 78 */     tag.setTag("primaryInput", getItemStackNBT(a));
/*  75: 79 */     tag.setTag("secondaryInput", getItemStackNBT(b));
/*  76: 80 */     tag.setTag("primaryOutput", getItemStackNBT(output));
/*  77: 81 */     if (altOutput != null)
/*  78:    */     {
/*  79: 82 */       tag.setTag("secondaryOutput", getItemStackNBT(altOutput));
/*  80: 83 */       tag.setInteger("secondaryChance", prob);
/*  81:    */     }
/*  82: 85 */     FMLInterModComms.sendMessage("ThermalExpansion", "SmelterRecipe", tag);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static void addPulverizer(int energy, ItemStack input, ItemStack output, ItemStack altOutput, int prob)
/*  86:    */   {
/*  87: 89 */     if (energy <= 0) {
/*  88: 90 */       energy = 3200;
/*  89:    */     }
/*  90: 92 */     NBTTagCompound tag = new NBTTagCompound();
/*  91: 93 */     tag.setInteger("energy", energy);
/*  92: 94 */     tag.setTag("input", getItemStackNBT(input));
/*  93: 95 */     tag.setTag("primaryOutput", getItemStackNBT(output));
/*  94: 97 */     if (altOutput != null)
/*  95:    */     {
/*  96: 98 */       tag.setTag("secondaryOutput", getItemStackNBT(altOutput));
/*  97: 99 */       tag.setInteger("secondaryChance", prob);
/*  98:    */     }
/*  99:102 */     FMLInterModComms.sendMessage("ThermalExpansion", "PulverizerRecipe", tag);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public static void addSawmill(int energy, ItemStack input, ItemStack output, ItemStack altOutput, int prob)
/* 103:    */   {
/* 104:106 */     if (energy <= 0) {
/* 105:107 */       energy = 2400;
/* 106:    */     }
/* 107:109 */     NBTTagCompound tag = new NBTTagCompound();
/* 108:110 */     tag.setInteger("energy", energy);
/* 109:111 */     tag.setTag("input", getItemStackNBT(input));
/* 110:112 */     tag.setTag("primaryOutput", getItemStackNBT(output));
/* 111:114 */     if (altOutput != null)
/* 112:    */     {
/* 113:115 */       tag.setTag("secondaryOutput", getItemStackNBT(altOutput));
/* 114:116 */       tag.setInteger("secondaryChance", prob);
/* 115:    */     }
/* 116:119 */     FMLInterModComms.sendMessage("ThermalExpansion", "SawmillRecipe", tag);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public static void addTransposerFill(int energy, ItemStack input, ItemStack output, FluidStack fluid, boolean reversible)
/* 120:    */   {
/* 121:123 */     NBTTagCompound fluidTag = new NBTTagCompound();
/* 122:124 */     fluid.writeToNBT(fluidTag);
/* 123:125 */     addTransposerFill(energy, input, output, fluidTag, reversible);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public static void addTransposerFill(int energy, ItemStack input, ItemStack output, NBTTagCompound fluid, boolean reversible)
/* 127:    */   {
/* 128:129 */     if (energy <= 0) {
/* 129:130 */       energy = 8000;
/* 130:    */     }
/* 131:132 */     NBTTagCompound tag = new NBTTagCompound();
/* 132:133 */     tag.setInteger("energy", energy);
/* 133:134 */     tag.setTag("input", getItemStackNBT(input));
/* 134:135 */     tag.setTag("output", getItemStackNBT(output));
/* 135:136 */     tag.setTag("fluid", fluid);
/* 136:137 */     tag.setBoolean("reversible", reversible);
/* 137:    */     
/* 138:139 */     FMLInterModComms.sendMessage("ThermalExpansion", "TransposerFillRecipe", tag);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public static void addCrucible(int energy, ItemStack input, FluidStack fluid)
/* 142:    */   {
/* 143:143 */     NBTTagCompound fluidTag = new NBTTagCompound();
/* 144:144 */     fluid.writeToNBT(fluidTag);
/* 145:145 */     addCrucible(energy, input, fluidTag);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public static void addCrucible(int energy, ItemStack input, NBTTagCompound fluid)
/* 149:    */   {
/* 150:149 */     if (energy <= 0) {
/* 151:150 */       energy = 8000;
/* 152:    */     }
/* 153:152 */     NBTTagCompound tag = new NBTTagCompound();
/* 154:153 */     tag.setInteger("energy", energy);
/* 155:154 */     tag.setTag("input", getItemStackNBT(input));
/* 156:155 */     tag.setTag("output", fluid);
/* 157:    */     
/* 158:157 */     FMLInterModComms.sendMessage("ThermalExpansion", "CrucibleRecipe", tag);
/* 159:    */   }
/* 160:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.modintegration.TE4IMC
 * JD-Core Version:    0.7.0.1
 */