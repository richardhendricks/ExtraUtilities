/*   1:    */ package com.rwtema.extrautils.tileentity.enderquarry;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.block.BlockMultiBlockSelection;
/*   5:    */ import com.rwtema.extrautils.block.Box;
/*   6:    */ import com.rwtema.extrautils.block.BoxModel;
/*   7:    */ import com.rwtema.extrautils.block.IBlockTooltip;
/*   8:    */ import com.rwtema.extrautils.helper.Translate;
/*   9:    */ import com.rwtema.extrautils.helper.XUHelper;
/*  10:    */ import cpw.mods.fml.relauncher.Side;
/*  11:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  12:    */ import java.util.List;
/*  13:    */ import net.minecraft.block.material.Material;
/*  14:    */ import net.minecraft.client.renderer.IconFlipped;
/*  15:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  16:    */ import net.minecraft.creativetab.CreativeTabs;
/*  17:    */ import net.minecraft.entity.player.EntityPlayer;
/*  18:    */ import net.minecraft.item.Item;
/*  19:    */ import net.minecraft.item.ItemStack;
/*  20:    */ import net.minecraft.util.IIcon;
/*  21:    */ import net.minecraft.world.IBlockAccess;
/*  22:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  23:    */ 
/*  24:    */ public class BlockQuarryUpgrades
/*  25:    */   extends BlockMultiBlockSelection
/*  26:    */   implements IBlockTooltip
/*  27:    */ {
/*  28: 36 */   int[] powerDrain = new int[16];
/*  29: 37 */   IIcon[] icons = new IIcon[16];
/*  30: 38 */   IIcon[] iconsFlipped = new IIcon[16];
/*  31: 39 */   IIcon arms = null;
/*  32:    */   
/*  33:    */   @SideOnly(Side.CLIENT)
/*  34:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/*  35:    */   {
/*  36: 45 */     for (int i = 0; i < 10; i++)
/*  37:    */     {
/*  38: 46 */       this.icons[i] = p_149651_1_.registerIcon("extrautils:quarry_upgrades/quarryUpgrade" + i);
/*  39: 47 */       this.iconsFlipped[i] = new IconFlipped(this.icons[i], true, false);
/*  40:    */     }
/*  41: 49 */     this.blockIcon = (this.arms = p_149651_1_.registerIcon("extrautils:quarry_upgrades/quarryUpgradeArm"));
/*  42:    */   }
/*  43:    */   
/*  44:    */   public BlockQuarryUpgrades()
/*  45:    */   {
/*  46: 53 */     super(Material.rock);
/*  47: 54 */     setBlockName("extrautils:enderQuarryUpgrade");
/*  48: 55 */     setBlockTextureName("extrautils:enderQuarryUpgrade");
/*  49: 56 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  50: 57 */     setHardness(1.0F);
/*  51: 58 */     setStepSound(soundTypeStone);
/*  52:    */   }
/*  53:    */   
/*  54:    */   @SideOnly(Side.CLIENT)
/*  55:    */   public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
/*  56:    */   {
/*  57: 64 */     for (int i = 0; i < 10; i++) {
/*  58: 65 */       p_149666_3_.add(new ItemStack(p_149666_1_, 1, i));
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void prepareForRender(String label) {}
/*  63:    */   
/*  64:    */   public BoxModel getWorldModel(IBlockAccess world, int x, int y, int z)
/*  65:    */   {
/*  66: 75 */     int metadata = world.getBlockMetadata(x, y, z);
/*  67: 76 */     BoxModel model = getInventoryModel(metadata);
/*  68: 77 */     ((Box)model.get(0)).textureSide[2] = this.iconsFlipped[metadata];
/*  69: 78 */     ((Box)model.get(0)).textureSide[3] = null;
/*  70: 80 */     for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
/*  71: 81 */       if (world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) == ExtraUtils.enderQuarry)
/*  72:    */       {
/*  73: 82 */         Box b = new Box(0.125F, 0.0F, 0.125F, 0.875F, 0.0625F, 0.875F);
/*  74: 83 */         b.rotateToSide(dir);
/*  75: 84 */         b.texture = this.arms;
/*  76: 85 */         model.add(b);
/*  77:    */       }
/*  78:    */     }
/*  79: 89 */     return model;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public BoxModel getInventoryModel(int metadata)
/*  83:    */   {
/*  84: 94 */     BoxModel b = new BoxModel();
/*  85: 95 */     b.addBoxI(1, 1, 1, 15, 15, 15);
/*  86: 96 */     ((Box)b.get(0)).texture = this.icons[metadata];
/*  87: 97 */     ((Box)b.get(0)).textureSide[0] = this.iconsFlipped[metadata];
/*  88: 98 */     ((Box)b.get(0)).textureSide[3] = this.iconsFlipped[metadata];
/*  89: 99 */     ((Box)b.get(0)).textureSide[5] = this.iconsFlipped[metadata];
/*  90:100 */     return b;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int damageDropped(int p_149692_1_)
/*  94:    */   {
/*  95:105 */     return p_149692_1_;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
/*  99:    */   {
/* 100:111 */     int meta = par1ItemStack.getItemDamage() & 0xF;
/* 101:112 */     double v = TileEntityEnderQuarry.powerMultipliers[meta];
/* 102:    */     
/* 103:    */ 
/* 104:115 */     String format = XUHelper.niceFormat(v);
/* 105:    */     
/* 106:117 */     par3List.add(Translate.get("power.drain", new Object[] { format }));
/* 107:    */   }
/* 108:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.tileentity.enderquarry.BlockQuarryUpgrades
 * JD-Core Version:    0.7.0.1
 */