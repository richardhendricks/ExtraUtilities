/*  1:   */ package com.rwtema.extrautils.item;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ExtraUtils;
/*  4:   */ import cpw.mods.fml.relauncher.Side;
/*  5:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  6:   */ import net.minecraft.block.Block;
/*  7:   */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  8:   */ import net.minecraft.entity.EntityLivingBase;
/*  9:   */ import net.minecraft.init.Items;
/* 10:   */ import net.minecraft.item.Item;
/* 11:   */ import net.minecraft.item.Item.ToolMaterial;
/* 12:   */ import net.minecraft.item.ItemPickaxe;
/* 13:   */ import net.minecraft.item.ItemStack;
/* 14:   */ import net.minecraft.util.IIcon;
/* 15:   */ import net.minecraft.world.World;
/* 16:   */ 
/* 17:   */ public class ItemDestructionPickaxe
/* 18:   */   extends ItemPickaxe
/* 19:   */   implements IItemMultiTransparency
/* 20:   */ {
/* 21:   */   private IIcon[] icons;
/* 22:   */   
/* 23:   */   public ItemDestructionPickaxe()
/* 24:   */   {
/* 25:21 */     super(Item.ToolMaterial.EMERALD);
/* 26:22 */     setUnlocalizedName("extrautils:destructionpickaxe");
/* 27:23 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/* 28:24 */     setMaxDamage(Item.ToolMaterial.EMERALD.getMaxUses() * 4);
/* 29:   */   }
/* 30:   */   
/* 31:   */   @SideOnly(Side.CLIENT)
/* 32:   */   public boolean hasEffect(ItemStack par1ItemStack, int pass)
/* 33:   */   {
/* 34:31 */     return false;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public float func_150893_a(ItemStack par1ItemStack, Block par2Block)
/* 38:   */   {
/* 39:40 */     float t = super.func_150893_a(par1ItemStack, par2Block);
/* 40:41 */     t = Math.max(t, Items.diamond_pickaxe.func_150893_a(par1ItemStack, par2Block));
/* 41:42 */     return t;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, Block par3, int par4, int par5, int par6, EntityLivingBase par7EntityLivingBase)
/* 45:   */   {
/* 46:47 */     return true;
/* 47:   */   }
/* 48:   */   
/* 49:   */   @SideOnly(Side.CLIENT)
/* 50:   */   public void registerIcons(IIconRegister par1IIconRegister)
/* 51:   */   {
/* 52:55 */     this.icons = new IIcon[2];
/* 53:56 */     this.itemIcon = (this.icons[0] =  = par1IIconRegister.registerIcon(getUnlocalizedName().substring(5)));
/* 54:57 */     this.icons[1] = par1IIconRegister.registerIcon(getUnlocalizedName().substring(5) + "1");
/* 55:   */   }
/* 56:   */   
/* 57:   */   public int numIcons(ItemStack item)
/* 58:   */   {
/* 59:63 */     return 2;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public IIcon getIconForTransparentRender(ItemStack item, int pass)
/* 63:   */   {
/* 64:68 */     return this.icons[pass];
/* 65:   */   }
/* 66:   */   
/* 67:   */   public float getIconTransparency(ItemStack item, int pass)
/* 68:   */   {
/* 69:73 */     if (pass == 1) {
/* 70:74 */       return 0.5F;
/* 71:   */     }
/* 72:76 */     return 1.0F;
/* 73:   */   }
/* 74:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemDestructionPickaxe
 * JD-Core Version:    0.7.0.1
 */