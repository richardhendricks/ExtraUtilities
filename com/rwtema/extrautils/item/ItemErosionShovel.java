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
/* 12:   */ import net.minecraft.item.ItemSpade;
/* 13:   */ import net.minecraft.item.ItemStack;
/* 14:   */ import net.minecraft.util.IIcon;
/* 15:   */ import net.minecraft.world.World;
/* 16:   */ 
/* 17:   */ public class ItemErosionShovel
/* 18:   */   extends ItemSpade
/* 19:   */   implements IItemMultiTransparency
/* 20:   */ {
/* 21:   */   private IIcon[] icons;
/* 22:   */   
/* 23:   */   public ItemErosionShovel()
/* 24:   */   {
/* 25:20 */     super(Item.ToolMaterial.EMERALD);
/* 26:21 */     setUnlocalizedName("extrautils:erosionShovel");
/* 27:22 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/* 28:23 */     setMaxDamage(Item.ToolMaterial.EMERALD.getMaxUses() * 4);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public float func_150893_a(ItemStack par1ItemStack, Block par2Block)
/* 32:   */   {
/* 33:32 */     float t = super.func_150893_a(par1ItemStack, par2Block);
/* 34:33 */     t = Math.max(t, Items.diamond_shovel.func_150893_a(par1ItemStack, par2Block));
/* 35:34 */     return t * 2.2F;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, Block par3, int par4, int par5, int par6, EntityLivingBase par7EntityLivingBase)
/* 39:   */   {
/* 40:39 */     return true;
/* 41:   */   }
/* 42:   */   
/* 43:   */   @SideOnly(Side.CLIENT)
/* 44:   */   public void registerIcons(IIconRegister par1IIconRegister)
/* 45:   */   {
/* 46:45 */     this.icons = new IIcon[2];
/* 47:46 */     this.itemIcon = (this.icons[0] =  = par1IIconRegister.registerIcon(getUnlocalizedName().substring(5)));
/* 48:47 */     this.icons[1] = par1IIconRegister.registerIcon(getUnlocalizedName().substring(5) + "1");
/* 49:   */   }
/* 50:   */   
/* 51:   */   public int numIcons(ItemStack item)
/* 52:   */   {
/* 53:53 */     return 2;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public IIcon getIconForTransparentRender(ItemStack item, int pass)
/* 57:   */   {
/* 58:58 */     return this.icons[pass];
/* 59:   */   }
/* 60:   */   
/* 61:   */   public float getIconTransparency(ItemStack item, int pass)
/* 62:   */   {
/* 63:63 */     if (pass == 1) {
/* 64:64 */       return 0.5F;
/* 65:   */     }
/* 66:66 */     return 1.0F;
/* 67:   */   }
/* 68:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemErosionShovel
 * JD-Core Version:    0.7.0.1
 */