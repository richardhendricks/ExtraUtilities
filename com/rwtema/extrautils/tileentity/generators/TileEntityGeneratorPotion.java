/*   1:    */ package com.rwtema.extrautils.tileentity.generators;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import net.minecraft.init.Items;
/*  11:    */ import net.minecraft.item.Item;
/*  12:    */ import net.minecraft.item.ItemBlock;
/*  13:    */ import net.minecraft.item.ItemStack;
/*  14:    */ import net.minecraft.nbt.NBTTagCompound;
/*  15:    */ import net.minecraft.potion.PotionHelper;
/*  16:    */ 
/*  17:    */ public class TileEntityGeneratorPotion
/*  18:    */   extends TileEntityGeneratorFurnace
/*  19:    */ {
/*  20:    */   public static final int MAX_META_TO_CHECK = 256;
/*  21: 15 */   public static Map<Integer, Integer> powerMap = new HashMap();
/*  22: 16 */   int curLevel = 0;
/*  23:    */   
/*  24:    */   public static void genPotionLevels()
/*  25:    */   {
/*  26: 19 */     XUHelper.resetTimer();
/*  27: 20 */     HashSet<Item> ingredientIDs = new HashSet();
/*  28: 21 */     List<Integer> potionIDs = new ArrayList();
/*  29: 23 */     for (Object anItemRegistry : Item.itemRegistry)
/*  30:    */     {
/*  31: 24 */       Item i = (Item)anItemRegistry;
/*  32:    */       
/*  33: 26 */       int num = getMaxMeta(i);
/*  34: 28 */       for (int meta = 0; meta < num; meta++) {
/*  35: 29 */         if (i.isPotionIngredient(new ItemStack(i, 1, meta))) {
/*  36: 30 */           ingredientIDs.add(i);
/*  37:    */         }
/*  38:    */       }
/*  39:    */     }
/*  40: 35 */     powerMap.put(Integer.valueOf(0), Integer.valueOf(0));
/*  41: 36 */     potionIDs.add(Integer.valueOf(0));
/*  42: 38 */     for (int i = 0; i < potionIDs.size(); i++)
/*  43:    */     {
/*  44: 39 */       int potion = ((Integer)potionIDs.get(i)).intValue();
/*  45: 41 */       for (Iterator i$ = ingredientIDs.iterator(); i$.hasNext(); goto 201)
/*  46:    */       {
/*  47: 41 */         Item ingredient = (Item)i$.next();
/*  48: 42 */         String k = "";
/*  49: 43 */         String s = "";
/*  50:    */         
/*  51: 45 */         int num = getMaxMeta(ingredient);
/*  52: 46 */         int meta = 0;
/*  53: 46 */         if ((meta < num) || (!k.equals(s)))
/*  54:    */         {
/*  55: 47 */           if (ingredient.isPotionIngredient(new ItemStack(ingredient, 1, meta))) {
/*  56:    */             try
/*  57:    */             {
/*  58: 49 */               s = ingredient.getPotionEffect(new ItemStack(ingredient, 1, meta));
/*  59: 50 */               int c = PotionHelper.applyIngredient(potion, s);
/*  60: 51 */               if (!potionIDs.contains(Integer.valueOf(c)))
/*  61:    */               {
/*  62: 52 */                 potionIDs.add(Integer.valueOf(c));
/*  63: 53 */                 powerMap.put(Integer.valueOf(c), Integer.valueOf(((Integer)powerMap.get(Integer.valueOf(potion))).intValue() + 1));
/*  64:    */               }
/*  65: 55 */               k = s == null ? "" : s;
/*  66:    */             }
/*  67:    */             catch (Exception err)
/*  68:    */             {
/*  69: 57 */               throw new RuntimeException("Caught error while applying potion ingredient " + ingredient.toString() + " to " + potion, err);
/*  70:    */             }
/*  71:    */           }
/*  72: 46 */           meta++;
/*  73:    */         }
/*  74:    */       }
/*  75:    */     }
/*  76: 64 */     XUHelper.printTimer("Potion generation");
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static int getMaxMeta(Item i)
/*  80:    */   {
/*  81: 68 */     return isSpecial(i) ? 256 : 1;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static boolean isSpecial(Item i)
/*  85:    */   {
/*  86: 72 */     return (i.getClass() != Item.class) && (i.getClass() != ItemBlock.class) && (i.getHasSubtypes());
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int transferLimit()
/*  90:    */   {
/*  91: 77 */     return Math.max(400, (int)genLevel());
/*  92:    */   }
/*  93:    */   
/*  94:    */   public double genLevel()
/*  95:    */   {
/*  96: 82 */     return 20 * (1 << this.curLevel);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public int getPotionLevel(ItemStack item)
/* 100:    */   {
/* 101: 86 */     if ((item != null) && (item.getItem() == Items.potionitem) && (powerMap.containsKey(Integer.valueOf(item.getItemDamage())))) {
/* 102: 87 */       return ((Integer)powerMap.get(Integer.valueOf(item.getItemDamage()))).intValue();
/* 103:    */     }
/* 104: 89 */     return 0;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public double getFuelBurn(ItemStack item)
/* 108:    */   {
/* 109: 95 */     if (getPotionLevel(item) == 0) {
/* 110: 96 */       return 0.0D;
/* 111:    */     }
/* 112: 99 */     return 800.0D;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public double getGenLevelForStack(ItemStack itemStack)
/* 116:    */   {
/* 117:104 */     int c = getPotionLevel(itemStack);
/* 118:105 */     return 10 * (1 << c);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean processInput()
/* 122:    */   {
/* 123:110 */     if (this.coolDown > 0.0D) {
/* 124:111 */       return false;
/* 125:    */     }
/* 126:114 */     int c = getPotionLevel(this.inv.getStackInSlot(0));
/* 127:116 */     if (c > 0)
/* 128:    */     {
/* 129:117 */       addCoolDown(getFuelBurn(this.inv.getStackInSlot(0)), false);
/* 130:118 */       this.curLevel = c;
/* 131:119 */       this.inv.getStackInSlot(0).setItemDamage(0);
/* 132:120 */       onInventoryChanged();
/* 133:121 */       return true;
/* 134:    */     }
/* 135:124 */     return false;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void readFromNBT(NBTTagCompound nbt)
/* 139:    */   {
/* 140:129 */     super.readFromNBT(nbt);
/* 141:130 */     this.curLevel = nbt.getInteger("curLevel");
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void writeToNBT(NBTTagCompound nbt)
/* 145:    */   {
/* 146:135 */     super.writeToNBT(nbt);
/* 147:136 */     nbt.setInteger("curLevel", this.curLevel);
/* 148:    */   }
/* 149:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.generators.TileEntityGeneratorPotion
 * JD-Core Version:    0.7.0.1
 */