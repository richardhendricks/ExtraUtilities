/*  1:   */ package com.rwtema.extrautils.item;
/*  2:   */ 
/*  3:   */ import com.rwtema.extrautils.ICreativeTabSorting;
/*  4:   */ import com.rwtema.extrautils.helper.XUHelper;
/*  5:   */ import com.rwtema.extrautils.tileentity.TileEntityDrum;
/*  6:   */ import cpw.mods.fml.relauncher.Side;
/*  7:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  8:   */ import java.util.List;
/*  9:   */ import java.util.Locale;
/* 10:   */ import net.minecraft.block.Block;
/* 11:   */ import net.minecraft.entity.player.EntityPlayer;
/* 12:   */ import net.minecraft.item.ItemStack;
/* 13:   */ import net.minecraftforge.fluids.FluidStack;
/* 14:   */ import net.minecraftforge.fluids.IFluidContainerItem;
/* 15:   */ import net.minecraftforge.fluids.ItemFluidContainer;
/* 16:   */ 
/* 17:   */ public class ItemBlockDrum
/* 18:   */   extends ItemBlockMetadata
/* 19:   */   implements IFluidContainerItem, ICreativeTabSorting
/* 20:   */ {
/* 21:20 */   protected int capacity = 256000;
/* 22:22 */   public ItemFluidContainer slaveItem = new ItemFluidContainer(-1, this.capacity);
/* 23:   */   
/* 24:   */   public ItemBlockDrum(Block b)
/* 25:   */   {
/* 26:25 */     super(b);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void setCapacityFromMeta(int meta)
/* 30:   */   {
/* 31:29 */     this.slaveItem.setCapacity(TileEntityDrum.getCapacityFromMetadata(meta));
/* 32:   */   }
/* 33:   */   
/* 34:   */   public ItemFluidContainer setCapacity(int capacity)
/* 35:   */   {
/* 36:33 */     return this.slaveItem.setCapacity(capacity);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public FluidStack getFluid(ItemStack container)
/* 40:   */   {
/* 41:38 */     setCapacityFromMeta(container.getItemDamage());
/* 42:39 */     return this.slaveItem.getFluid(container);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public int getCapacity(ItemStack container)
/* 46:   */   {
/* 47:44 */     setCapacityFromMeta(container.getItemDamage());
/* 48:45 */     return this.slaveItem.getCapacity(container);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public int fill(ItemStack container, FluidStack resource, boolean doFill)
/* 52:   */   {
/* 53:50 */     setCapacityFromMeta(container.getItemDamage());
/* 54:51 */     return this.slaveItem.fill(container, resource, doFill);
/* 55:   */   }
/* 56:   */   
/* 57:   */   public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain)
/* 58:   */   {
/* 59:56 */     setCapacityFromMeta(container.getItemDamage());
/* 60:57 */     FluidStack t = this.slaveItem.drain(container, maxDrain, doDrain);
/* 61:59 */     if ((this.slaveItem.getFluid(container) != null) && (this.slaveItem.getFluid(container).amount < 0))
/* 62:   */     {
/* 63:60 */       container.setTagCompound(null);
/* 64:61 */       throw new RuntimeException("Fluid container has been drained into negative numbers. This is a Forge bug.");
/* 65:   */     }
/* 66:64 */     return t;
/* 67:   */   }
/* 68:   */   
/* 69:   */   @SideOnly(Side.CLIENT)
/* 70:   */   public void addInformation(ItemStack item, EntityPlayer par2EntityPlayer, List info, boolean par4)
/* 71:   */   {
/* 72:70 */     setCapacityFromMeta(item.getItemDamage());
/* 73:71 */     FluidStack fluid = this.slaveItem.getFluid(item);
/* 74:73 */     if (fluid != null) {
/* 75:74 */       info.add(XUHelper.getFluidName(fluid) + ": " + String.format(Locale.ENGLISH, "%,d", new Object[] { Integer.valueOf(fluid.amount) }) + " / " + String.format(Locale.ENGLISH, "%,d", new Object[] { Integer.valueOf(this.slaveItem.getCapacity(null)) }));
/* 76:   */     } else {
/* 77:76 */       info.add("Empty: 0 / " + String.format(Locale.ENGLISH, "%,d", new Object[] { Integer.valueOf(this.slaveItem.getCapacity(null)) }));
/* 78:   */     }
/* 79:   */   }
/* 80:   */   
/* 81:   */   public String getItemStackDisplayName(ItemStack item)
/* 82:   */   {
/* 83:82 */     String s = super.getItemStackDisplayName(item);
/* 84:83 */     FluidStack fluid = getFluid(item);
/* 85:85 */     if (fluid != null) {
/* 86:86 */       s = XUHelper.getFluidName(fluid) + " " + s;
/* 87:   */     }
/* 88:89 */     return s.trim();
/* 89:   */   }
/* 90:   */   
/* 91:   */   public String getSortingName(ItemStack item)
/* 92:   */   {
/* 93:94 */     return super.getItemStackDisplayName(item) + ":" + getItemStackDisplayName(item);
/* 94:   */   }
/* 95:   */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.item.ItemBlockDrum
 * JD-Core Version:    0.7.0.1
 */