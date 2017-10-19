/*   1:    */ package com.rwtema.extrautils.item;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import cpw.mods.fml.common.eventhandler.Event.Result;
/*   5:    */ import cpw.mods.fml.common.eventhandler.EventBus;
/*   6:    */ import cpw.mods.fml.relauncher.Side;
/*   7:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   8:    */ import net.minecraft.block.Block;
/*   9:    */ import net.minecraft.block.Block.SoundType;
/*  10:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  11:    */ import net.minecraft.entity.player.EntityPlayer;
/*  12:    */ import net.minecraft.init.Blocks;
/*  13:    */ import net.minecraft.item.Item.ToolMaterial;
/*  14:    */ import net.minecraft.item.ItemHoe;
/*  15:    */ import net.minecraft.item.ItemStack;
/*  16:    */ import net.minecraft.util.IIcon;
/*  17:    */ import net.minecraft.world.World;
/*  18:    */ import net.minecraftforge.common.MinecraftForge;
/*  19:    */ import net.minecraftforge.event.entity.player.UseHoeEvent;
/*  20:    */ 
/*  21:    */ public class ItemTemporalHoe
/*  22:    */   extends ItemHoe
/*  23:    */   implements IItemMultiTransparency
/*  24:    */ {
/*  25:    */   private IIcon[] icons;
/*  26:    */   
/*  27:    */   public ItemTemporalHoe()
/*  28:    */   {
/*  29: 24 */     super(Item.ToolMaterial.EMERALD);
/*  30: 25 */     this.maxStackSize = 1;
/*  31: 26 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  32: 27 */     setUnlocalizedName("extrautils:temporalHoe");
/*  33:    */   }
/*  34:    */   
/*  35:    */   @SideOnly(Side.CLIENT)
/*  36:    */   public boolean hasEffect(ItemStack par1ItemStack, int pass)
/*  37:    */   {
/*  38: 33 */     return false;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
/*  42:    */   {
/*  43: 43 */     if (!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_)) {
/*  44: 45 */       return false;
/*  45:    */     }
/*  46: 49 */     UseHoeEvent event = new UseHoeEvent(p_77648_2_, p_77648_1_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_);
/*  47: 50 */     if (MinecraftForge.EVENT_BUS.post(event)) {
/*  48: 52 */       return false;
/*  49:    */     }
/*  50: 55 */     if (event.getResult() == Event.Result.ALLOW) {
/*  51: 57 */       return true;
/*  52:    */     }
/*  53: 60 */     Block block = p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_);
/*  54: 62 */     if ((p_77648_7_ != 0) && (p_77648_3_.getBlock(p_77648_4_, p_77648_5_ + 1, p_77648_6_).isAir(p_77648_3_, p_77648_4_, p_77648_5_ + 1, p_77648_6_)) && ((block == Blocks.grass) || (block == Blocks.dirt)))
/*  55:    */     {
/*  56: 64 */       Block block1 = Blocks.farmland;
/*  57: 65 */       p_77648_3_.playSoundEffect(p_77648_4_ + 0.5F, p_77648_5_ + 0.5F, p_77648_6_ + 0.5F, block1.stepSound.getStepResourcePath(), (block1.stepSound.getVolume() + 1.0F) / 2.0F, block1.stepSound.getFrequency() * 0.8F);
/*  58: 67 */       if (p_77648_3_.isClient) {
/*  59: 69 */         return true;
/*  60:    */       }
/*  61: 73 */       p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, block1);
/*  62: 74 */       return true;
/*  63:    */     }
/*  64: 79 */     return false;
/*  65:    */   }
/*  66:    */   
/*  67:    */   @SideOnly(Side.CLIENT)
/*  68:    */   public boolean isFull3D()
/*  69:    */   {
/*  70: 90 */     return true;
/*  71:    */   }
/*  72:    */   
/*  73:    */   @SideOnly(Side.CLIENT)
/*  74:    */   public void registerIcons(IIconRegister par1IIconRegister)
/*  75:    */   {
/*  76: 96 */     this.icons = new IIcon[2];
/*  77: 97 */     this.itemIcon = (this.icons[0] =  = par1IIconRegister.registerIcon(getUnlocalizedName().substring(5)));
/*  78: 98 */     this.icons[1] = par1IIconRegister.registerIcon(getUnlocalizedName().substring(5) + "1");
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int numIcons(ItemStack item)
/*  82:    */   {
/*  83:103 */     return 2;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public IIcon getIconForTransparentRender(ItemStack item, int pass)
/*  87:    */   {
/*  88:108 */     return this.icons[pass];
/*  89:    */   }
/*  90:    */   
/*  91:    */   public float getIconTransparency(ItemStack item, int pass)
/*  92:    */   {
/*  93:113 */     if (pass == 1) {
/*  94:114 */       return 0.5F;
/*  95:    */     }
/*  96:116 */     return 1.0F;
/*  97:    */   }
/*  98:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemTemporalHoe
 * JD-Core Version:    0.7.0.1
 */