/*   1:    */ package com.rwtema.extrautils.item;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.block.BlockSpike;
/*   4:    */ import cpw.mods.fml.relauncher.ReflectionHelper;
/*   5:    */ import cpw.mods.fml.relauncher.Side;
/*   6:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   7:    */ import java.lang.reflect.Field;
/*   8:    */ import java.util.List;
/*   9:    */ import net.minecraft.block.Block;
/*  10:    */ import net.minecraft.block.Block.SoundType;
/*  11:    */ import net.minecraft.block.material.Material;
/*  12:    */ import net.minecraft.creativetab.CreativeTabs;
/*  13:    */ import net.minecraft.entity.player.EntityPlayer;
/*  14:    */ import net.minecraft.init.Blocks;
/*  15:    */ import net.minecraft.item.Item;
/*  16:    */ import net.minecraft.item.Item.ToolMaterial;
/*  17:    */ import net.minecraft.item.ItemStack;
/*  18:    */ import net.minecraft.item.ItemSword;
/*  19:    */ import net.minecraft.world.World;
/*  20:    */ 
/*  21:    */ public class ItemSwordSpike
/*  22:    */   extends ItemSword
/*  23:    */ {
/*  24: 20 */   public static final Field mat = ReflectionHelper.findField(ItemSword.class, new String[] { "field_150933_b" });
/*  25:    */   public final BlockSpike spike;
/*  26:    */   
/*  27:    */   public ItemSwordSpike(Block spike)
/*  28:    */   {
/*  29: 23 */     this((BlockSpike)spike);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static Item.ToolMaterial getMaterial(BlockSpike spike)
/*  33:    */   {
/*  34: 28 */     ItemSword item = (ItemSword)spike.swordStack.getItem();
/*  35:    */     try
/*  36:    */     {
/*  37: 30 */       return (Item.ToolMaterial)mat.get(item);
/*  38:    */     }
/*  39:    */     catch (IllegalAccessException e)
/*  40:    */     {
/*  41: 32 */       throw new RuntimeException(e);
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   public ItemSwordSpike(BlockSpike spike)
/*  46:    */   {
/*  47: 39 */     super(getMaterial(spike));
/*  48: 40 */     this.spike = spike;
/*  49: 41 */     spike.itemSpike = this;
/*  50: 42 */     this.maxStackSize = 64;
/*  51: 43 */     setMaxDamage(0);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int getSpriteNumber()
/*  55:    */   {
/*  56: 48 */     return 0;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
/*  60:    */   {
/*  61: 52 */     Block block = p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_);
/*  62: 54 */     if ((block == Blocks.snow_layer) && ((p_77648_3_.getBlockMetadata(p_77648_4_, p_77648_5_, p_77648_6_) & 0x7) < 1))
/*  63:    */     {
/*  64: 55 */       p_77648_7_ = 1;
/*  65:    */     }
/*  66: 56 */     else if ((block != Blocks.vine) && (block != Blocks.tallgrass) && (block != Blocks.deadbush) && (!block.isReplaceable(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_)))
/*  67:    */     {
/*  68: 57 */       if (p_77648_7_ == 0) {
/*  69: 58 */         p_77648_5_--;
/*  70:    */       }
/*  71: 61 */       if (p_77648_7_ == 1) {
/*  72: 62 */         p_77648_5_++;
/*  73:    */       }
/*  74: 65 */       if (p_77648_7_ == 2) {
/*  75: 66 */         p_77648_6_--;
/*  76:    */       }
/*  77: 69 */       if (p_77648_7_ == 3) {
/*  78: 70 */         p_77648_6_++;
/*  79:    */       }
/*  80: 73 */       if (p_77648_7_ == 4) {
/*  81: 74 */         p_77648_4_--;
/*  82:    */       }
/*  83: 77 */       if (p_77648_7_ == 5) {
/*  84: 78 */         p_77648_4_++;
/*  85:    */       }
/*  86:    */     }
/*  87: 82 */     if (p_77648_1_.stackSize == 0) {
/*  88: 83 */       return false;
/*  89:    */     }
/*  90: 84 */     if (!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_)) {
/*  91: 85 */       return false;
/*  92:    */     }
/*  93: 86 */     if ((p_77648_5_ == 255) && (this.spike.getMaterial().isSolid())) {
/*  94: 87 */       return false;
/*  95:    */     }
/*  96: 88 */     if (p_77648_3_.canPlaceEntityOnSide(this.spike, p_77648_4_, p_77648_5_, p_77648_6_, false, p_77648_7_, p_77648_2_, p_77648_1_))
/*  97:    */     {
/*  98: 89 */       int i1 = getMetadata(p_77648_1_.getItemDamage());
/*  99: 90 */       int j1 = this.spike.onBlockPlaced(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_, i1);
/* 100: 92 */       if (placeBlockAt(p_77648_1_, p_77648_2_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_, j1))
/* 101:    */       {
/* 102: 93 */         p_77648_3_.playSoundEffect(p_77648_4_ + 0.5F, p_77648_5_ + 0.5F, p_77648_6_ + 0.5F, this.spike.stepSound.func_150496_b(), (this.spike.stepSound.getVolume() + 1.0F) / 2.0F, this.spike.stepSound.getFrequency() * 0.8F);
/* 103: 94 */         p_77648_1_.stackSize -= 1;
/* 104:    */       }
/* 105: 97 */       return true;
/* 106:    */     }
/* 107: 99 */     return false;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String getUnlocalizedName(ItemStack p_77667_1_)
/* 111:    */   {
/* 112:104 */     return this.spike.getUnlocalizedName();
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String getUnlocalizedName()
/* 116:    */   {
/* 117:108 */     return this.spike.getUnlocalizedName();
/* 118:    */   }
/* 119:    */   
/* 120:    */   @SideOnly(Side.CLIENT)
/* 121:    */   public CreativeTabs getCreativeTab()
/* 122:    */   {
/* 123:113 */     return this.spike.getCreativeTabToDisplayOn();
/* 124:    */   }
/* 125:    */   
/* 126:    */   @SideOnly(Side.CLIENT)
/* 127:    */   public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
/* 128:    */   {
/* 129:118 */     this.spike.getSubBlocks(p_150895_1_, p_150895_2_, p_150895_3_);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
/* 133:    */   {
/* 134:123 */     if (!world.setBlock(x, y, z, this.spike, metadata, 3)) {
/* 135:124 */       return false;
/* 136:    */     }
/* 137:127 */     if (world.getBlock(x, y, z) == this.spike)
/* 138:    */     {
/* 139:128 */       this.spike.onBlockPlacedBy(world, x, y, z, player, stack);
/* 140:129 */       this.spike.onPostBlockPlaced(world, x, y, z, metadata);
/* 141:    */     }
/* 142:132 */     return true;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String getString()
/* 146:    */   {
/* 147:136 */     String s = this.spike.getUnlocalizedName().substring("tile.".length());
/* 148:137 */     s = s.replace("extrautils:", "");
/* 149:138 */     return s;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public boolean isItemTool(ItemStack p_77616_1_)
/* 153:    */   {
/* 154:143 */     return true;
/* 155:    */   }
/* 156:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemSwordSpike
 * JD-Core Version:    0.7.0.1
 */