/*  1:   */ package com.rwtema.extrautils.item;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.relauncher.Side;
/*  4:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ import java.util.HashSet;
/*  7:   */ import java.util.List;
/*  8:   */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  9:   */ import net.minecraft.creativetab.CreativeTabs;
/* 10:   */ import net.minecraft.item.Item;
/* 11:   */ import net.minecraft.item.ItemStack;
/* 12:   */ import net.minecraft.util.IIcon;
/* 13:   */ 
/* 14:   */ public class ItemIngredient
/* 15:   */   extends Item
/* 16:   */ {
/* 17:16 */   public ArrayList<String> textures = new ArrayList();
/* 18:17 */   public ArrayList<String> names = new ArrayList();
/* 19:18 */   public ArrayList<IIcon> icons = new ArrayList();
/* 20:19 */   public HashSet<Integer> ids = new HashSet();
/* 21:20 */   public int numItems = 1;
/* 22:   */   
/* 23:   */   public ItemIngredient()
/* 24:   */   {
/* 25:23 */     addItem(0, "Error", "error");
/* 26:24 */     this.ids.remove(Integer.valueOf(0));
/* 27:   */     
/* 28:   */ 
/* 29:   */ 
/* 30:28 */     setHasSubtypes(true);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void addItem(int metadata, String name, String texture)
/* 34:   */   {
/* 35:32 */     if (this.numItems < 1 + metadata)
/* 36:   */     {
/* 37:33 */       this.numItems = (1 + metadata);
/* 38:34 */       this.textures.ensureCapacity(this.numItems);
/* 39:35 */       this.names.ensureCapacity(this.numItems);
/* 40:36 */       this.icons.ensureCapacity(this.numItems);
/* 41:   */     }
/* 42:39 */     this.textures.set(metadata, texture);
/* 43:40 */     this.names.set(metadata, name);
/* 44:41 */     this.ids.add(Integer.valueOf(metadata));
/* 45:   */   }
/* 46:   */   
/* 47:   */   @SideOnly(Side.CLIENT)
/* 48:   */   public void registerIcons(IIconRegister par1IIconRegister)
/* 49:   */   {
/* 50:48 */     for (Integer i : this.ids) {
/* 51:49 */       this.icons.set(i.intValue(), par1IIconRegister.registerIcon("extrautils:" + (String)this.textures.get(i.intValue())));
/* 52:   */     }
/* 53:   */   }
/* 54:   */   
/* 55:   */   @SideOnly(Side.CLIENT)
/* 56:   */   public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/* 57:   */   {
/* 58:57 */     for (Integer id : this.ids) {
/* 59:58 */       par3List.add(new ItemStack(par1, 1, id.intValue()));
/* 60:   */     }
/* 61:   */   }
/* 62:   */   
/* 63:   */   @SideOnly(Side.CLIENT)
/* 64:   */   public IIcon getIconFromDamage(int par1)
/* 65:   */   {
/* 66:68 */     return (IIcon)this.icons.get(getMetaData(par1));
/* 67:   */   }
/* 68:   */   
/* 69:   */   public int getMetaData(ItemStack item)
/* 70:   */   {
/* 71:72 */     return getMetaData(item.getItemDamage());
/* 72:   */   }
/* 73:   */   
/* 74:   */   public int getMetaData(int metadata)
/* 75:   */   {
/* 76:76 */     if (!this.ids.contains(Integer.valueOf(metadata))) {
/* 77:77 */       return 0;
/* 78:   */     }
/* 79:80 */     return metadata;
/* 80:   */   }
/* 81:   */   
/* 82:   */   public String getUnlocalizedName(ItemStack par1ItemStack)
/* 83:   */   {
/* 84:85 */     return getUnlocalizedName() + "." + getMetaData(par1ItemStack);
/* 85:   */   }
/* 86:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemIngredient
 * JD-Core Version:    0.7.0.1
 */