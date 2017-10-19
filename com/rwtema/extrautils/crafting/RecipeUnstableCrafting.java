/*   1:    */ package com.rwtema.extrautils.crafting;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.item.ItemUnstableIngot;
/*   5:    */ import com.rwtema.extrautils.modintegration.EE3Integration;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import net.minecraft.enchantment.Enchantment;
/*   8:    */ import net.minecraft.init.Items;
/*   9:    */ import net.minecraft.inventory.InventoryCrafting;
/*  10:    */ import net.minecraft.item.Item;
/*  11:    */ import net.minecraft.item.ItemStack;
/*  12:    */ import net.minecraft.item.crafting.IRecipe;
/*  13:    */ import net.minecraft.nbt.NBTTagCompound;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ import net.minecraft.world.WorldProvider;
/*  16:    */ import net.minecraftforge.oredict.ShapedOreRecipe;
/*  17:    */ 
/*  18:    */ public class RecipeUnstableCrafting
/*  19:    */   extends ShapedOreRecipe
/*  20:    */   implements IRecipe
/*  21:    */ {
/*  22: 23 */   public static final NBTTagCompound nbt = new NBTTagCompound();
/*  23:    */   private ItemStack stableOutput;
/*  24:    */   ShapedOreRecipe checker;
/*  25:    */   
/*  26:    */   static
/*  27:    */   {
/*  28: 24 */     nbt.setBoolean("isNEI", true);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public RecipeUnstableCrafting(ItemStack result, Object... recipe)
/*  32:    */   {
/*  33: 32 */     super(result, recipe);
/*  34:    */     
/*  35: 34 */     this.checker = new ShapedOreRecipe(result, recipe);
/*  36:    */     
/*  37: 36 */     Object[] input = super.getInput();
/*  38:    */     
/*  39: 38 */     ArrayList<Object> ee3input = new ArrayList();
/*  40: 40 */     for (Object anInput : input)
/*  41:    */     {
/*  42: 41 */       boolean flag = true;
/*  43: 42 */       if ((anInput instanceof ArrayList))
/*  44:    */       {
/*  45: 43 */         ArrayList<ItemStack> itemStacks = (ArrayList)anInput;
/*  46: 45 */         for (ItemStack itemStack : itemStacks) {
/*  47: 46 */           if ((itemStack.getItem() == ExtraUtils.unstableIngot) && (itemStack.getItemDamage() == 2))
/*  48:    */           {
/*  49: 47 */             itemStack.setTagCompound(nbt);
/*  50: 48 */             ee3input.add(Items.diamond);
/*  51: 49 */             ee3input.add(Items.iron_ingot);
/*  52: 50 */             flag = false;
/*  53: 51 */             break;
/*  54:    */           }
/*  55:    */         }
/*  56:    */       }
/*  57: 56 */       if (flag) {
/*  58: 57 */         ee3input.add(anInput);
/*  59:    */       }
/*  60:    */     }
/*  61: 60 */     EE3Integration.addRecipe(getRecipeOutput().copy(), ee3input.toArray());
/*  62:    */   }
/*  63:    */   
/*  64:    */   public RecipeUnstableCrafting setStableItem(Item stack)
/*  65:    */   {
/*  66: 64 */     if (stack != null) {
/*  67: 65 */       this.stableOutput = new ItemStack(stack);
/*  68:    */     }
/*  69: 67 */     return this;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public RecipeUnstableCrafting setStable(ItemStack stack)
/*  73:    */   {
/*  74: 71 */     this.stableOutput = stack;
/*  75: 72 */     return this;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public RecipeUnstableCrafting addStableEnchant(Enchantment enchantment, int level)
/*  79:    */   {
/*  80: 76 */     if (this.stableOutput == null) {
/*  81: 77 */       this.stableOutput = getRecipeOutput().copy();
/*  82:    */     }
/*  83: 78 */     this.stableOutput.addEnchantment(enchantment, level);
/*  84: 79 */     return this;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World)
/*  88:    */   {
/*  89: 87 */     return (par2World != null) && (this.checker.matches(par1InventoryCrafting, par2World)) && (!hasExpired(par1InventoryCrafting, par2World));
/*  90:    */   }
/*  91:    */   
/*  92:    */   public boolean hasExpired(InventoryCrafting par1InventoryCrafting, World par2World)
/*  93:    */   {
/*  94: 92 */     for (int i = 0; i < par1InventoryCrafting.getSizeInventory(); i++)
/*  95:    */     {
/*  96: 93 */       ItemStack item = par1InventoryCrafting.getStackInSlot(i);
/*  97: 95 */       if ((item != null) && 
/*  98: 96 */         (item.getItem() == ExtraUtils.unstableIngot) && (item.getItemDamage() == 0)) {
/*  99: 98 */         if ((item.hasTagCompound()) && 
/* 100: 99 */           (!item.getTagCompound().hasKey("creative"))) {
/* 101:103 */           if (!item.getTagCompound().hasKey("stable"))
/* 102:    */           {
/* 103:108 */             if ((!item.getTagCompound().hasKey("dimension")) && (!item.getTagCompound().hasKey("time")) && 
/* 104:109 */               (item.getTagCompound().hasKey("crafting"))) {
/* 105:110 */               return true;
/* 106:    */             }
/* 107:114 */             long t = (200L - (par2World.getTotalWorldTime() - item.getTagCompound().getLong("time"))) / 20L;
/* 108:116 */             if ((par2World.provider.dimensionId != item.getTagCompound().getInteger("dimension")) || (t < 0L)) {
/* 109:117 */               return true;
/* 110:    */             }
/* 111:    */           }
/* 112:    */         }
/* 113:    */       }
/* 114:    */     }
/* 115:124 */     return false;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public boolean hasStable(InventoryCrafting par1InventoryCrafting)
/* 119:    */   {
/* 120:129 */     for (int i = 0; i < par1InventoryCrafting.getSizeInventory(); i++)
/* 121:    */     {
/* 122:130 */       ItemStack item = par1InventoryCrafting.getStackInSlot(i);
/* 123:132 */       if ((item != null) && 
/* 124:133 */         (item.getItem() == ExtraUtils.unstableIngot) && (item.getItemDamage() == 0) && 
/* 125:134 */         (item.hasTagCompound()))
/* 126:    */       {
/* 127:135 */         if (!ItemUnstableIngot.isStable(item)) {
/* 128:136 */           return false;
/* 129:    */         }
/* 130:137 */         if (!ItemUnstableIngot.isSuperStable(item)) {
/* 131:138 */           return false;
/* 132:    */         }
/* 133:139 */         if (item.getTagCompound().hasKey("time")) {
/* 134:140 */           return false;
/* 135:    */         }
/* 136:    */       }
/* 137:    */     }
/* 138:146 */     return true;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting)
/* 142:    */   {
/* 143:154 */     if ((this.stableOutput != null) && (hasStable(par1InventoryCrafting))) {
/* 144:155 */       return this.stableOutput.copy();
/* 145:    */     }
/* 146:157 */     return super.getCraftingResult(par1InventoryCrafting);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public static RecipeUnstableCrafting addRecipe(ItemStack itemStack, Object... objects)
/* 150:    */   {
/* 151:161 */     return new RecipeUnstableCrafting(itemStack, objects);
/* 152:    */   }
/* 153:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.crafting.RecipeUnstableCrafting
 * JD-Core Version:    0.7.0.1
 */