/*   1:    */ package com.rwtema.extrautils.block;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.helper.XUHelper;
/*   5:    */ import cpw.mods.fml.relauncher.Side;
/*   6:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   7:    */ import java.lang.reflect.Constructor;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Random;
/*  10:    */ import net.minecraft.block.Block;
/*  11:    */ import net.minecraft.block.material.Material;
/*  12:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  13:    */ import net.minecraft.command.IEntitySelector;
/*  14:    */ import net.minecraft.entity.Entity;
/*  15:    */ import net.minecraft.entity.EntityLiving;
/*  16:    */ import net.minecraft.entity.EnumCreatureType;
/*  17:    */ import net.minecraft.entity.item.EntityItem;
/*  18:    */ import net.minecraft.entity.item.EntityXPOrb;
/*  19:    */ import net.minecraft.inventory.IInventory;
/*  20:    */ import net.minecraft.item.ItemStack;
/*  21:    */ import net.minecraft.item.ItemSword;
/*  22:    */ import net.minecraft.tileentity.TileEntity;
/*  23:    */ import net.minecraft.tileentity.TileEntityHopper;
/*  24:    */ import net.minecraft.util.AxisAlignedBB;
/*  25:    */ import net.minecraft.util.DamageSource;
/*  26:    */ import net.minecraft.util.IIcon;
/*  27:    */ import net.minecraft.world.EnumDifficulty;
/*  28:    */ import net.minecraft.world.IBlockAccess;
/*  29:    */ import net.minecraft.world.World;
/*  30:    */ import net.minecraft.world.WorldServer;
/*  31:    */ import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
/*  32:    */ import net.minecraftforge.common.util.FakePlayer;
/*  33:    */ import net.minecraftforge.common.util.FakePlayerFactory;
/*  34:    */ 
/*  35:    */ public class BlockPeacefulTable
/*  36:    */   extends BlockMultiBlock
/*  37:    */ {
/*  38:    */   private IIcon[] icons;
/*  39:    */   
/*  40:    */   public BlockPeacefulTable()
/*  41:    */   {
/*  42: 39 */     super(Material.wood);
/*  43: 40 */     setCreativeTab(ExtraUtils.creativeTabExtraUtils);
/*  44: 41 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
/*  45: 42 */     setBlockName("extrautils:peaceful_table_top");
/*  46: 43 */     setBlockTextureName("extrautils:peaceful_table_top");
/*  47: 44 */     setTickRandomly(true);
/*  48: 45 */     setHardness(1.0F);
/*  49: 46 */     setResistance(10.0F).setStepSound(soundTypeWood);
/*  50:    */   }
/*  51:    */   
/*  52:    */   @SideOnly(Side.CLIENT)
/*  53:    */   public void registerBlockIcons(IIconRegister par1IIconRegister)
/*  54:    */   {
/*  55: 52 */     this.icons = new IIcon[3];
/*  56: 53 */     this.icons[0] = par1IIconRegister.registerIcon("extrautils:peaceful_table_bottom");
/*  57: 54 */     this.icons[1] = par1IIconRegister.registerIcon("extrautils:peaceful_table_top");
/*  58: 55 */     this.icons[2] = par1IIconRegister.registerIcon("extrautils:peaceful_table_side");
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void updateTick(World world, int x, int y, int z, Random par5Random)
/*  62:    */   {
/*  63:    */     List list1;
/*  64: 63 */     if ((!world.isClient) && ((world instanceof WorldServer)))
/*  65:    */     {
/*  66: 64 */       if ((!ExtraUtils.peacefulTableInAllDifficulties) && (world.difficultySetting != EnumDifficulty.PEACEFUL)) {
/*  67: 65 */         return;
/*  68:    */       }
/*  69: 69 */       BiomeGenBase.SpawnListEntry var7 = ((WorldServer)world).spawnRandomCreature(EnumCreatureType.monster, x, y, z);
/*  70: 71 */       if (var7 != null)
/*  71:    */       {
/*  72: 72 */         IInventory swordInv = null;
/*  73: 73 */         int swordSlot = -1;
/*  74: 74 */         ItemStack sword = null;
/*  75: 76 */         for (int j = 0; j < 6; j++)
/*  76:    */         {
/*  77: 77 */           TileEntity tile = world.getTileEntity(x + net.minecraft.util.Facing.offsetsXForSide[j], y + net.minecraft.util.Facing.offsetsYForSide[j], z + net.minecraft.util.Facing.offsetsZForSide[j]);
/*  78: 79 */           if ((tile instanceof IInventory))
/*  79:    */           {
/*  80: 80 */             IInventory inv = (IInventory)tile;
/*  81: 81 */             for (int i = 0; i < inv.getSizeInventory(); i++)
/*  82:    */             {
/*  83: 82 */               ItemStack item = inv.getStackInSlot(i);
/*  84: 84 */               if ((item != null) && 
/*  85: 85 */                 ((item.getItem() instanceof ItemSword)))
/*  86:    */               {
/*  87: 86 */                 swordInv = inv;
/*  88: 87 */                 swordSlot = i;
/*  89: 88 */                 sword = item;
/*  90: 89 */                 break;
/*  91:    */               }
/*  92:    */             }
/*  93: 93 */             if (sword != null) {
/*  94:    */               break;
/*  95:    */             }
/*  96:    */           }
/*  97:    */         }
/*  98: 99 */         if (sword == null) {
/*  99:    */           return;
/* 100:    */         }
/* 101:    */         EntityLiving t;
/* 102:    */         try
/* 103:    */         {
/* 104:104 */           t = (EntityLiving)var7.entityClass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { world });
/* 105:    */         }
/* 106:    */         catch (Exception var31)
/* 107:    */         {
/* 108:106 */           var31.printStackTrace();
/* 109:107 */           return;
/* 110:    */         }
/* 111:110 */         list1 = world.selectEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(x - 2, y, z - 2, x + 3, y + 4, z + 2), IEntitySelector.selectAnything);
/* 112:111 */         t.setLocationAndAngles(x + 0.5D, y + 1.25D, z + 0.5D, par5Random.nextFloat() * 360.0F, 0.0F);
/* 113:112 */         world.spawnEntityInWorld(t);
/* 114:    */         
/* 115:114 */         EnumDifficulty prev = world.difficultySetting;
/* 116:115 */         world.difficultySetting = EnumDifficulty.HARD;
/* 117:116 */         t.onSpawnWithEgg(null);
/* 118:117 */         world.difficultySetting = prev;
/* 119:    */         
/* 120:119 */         FakePlayer fakePlayer = FakePlayerFactory.getMinecraft((WorldServer)world);
/* 121:    */         
/* 122:121 */         fakePlayer.setCurrentItemOrArmor(0, sword.copy());
/* 123:122 */         float h1 = t.getHealth();
/* 124:123 */         fakePlayer.attackTargetEntityWithCurrentItem(t);
/* 125:124 */         float h2 = t.getHealth();
/* 126:126 */         if (t.isDead)
/* 127:    */         {
/* 128:127 */           if ((fakePlayer.getCurrentEquippedItem() == null) || (fakePlayer.getCurrentEquippedItem().stackSize == 0)) {
/* 129:128 */             swordInv.setInventorySlotContents(swordSlot, null);
/* 130:    */           } else {
/* 131:130 */             swordInv.setInventorySlotContents(swordSlot, fakePlayer.getCurrentEquippedItem());
/* 132:    */           }
/* 133:    */         }
/* 134:    */         else
/* 135:    */         {
/* 136:133 */           if ((fakePlayer.getCurrentEquippedItem() == null) || (fakePlayer.getCurrentEquippedItem().stackSize == 0))
/* 137:    */           {
/* 138:134 */             swordInv.setInventorySlotContents(swordSlot, null);
/* 139:    */           }
/* 140:    */           else
/* 141:    */           {
/* 142:136 */             if (h1 > h2) {
/* 143:137 */               for (float h = h2; h > 0.0F; h -= h1 - h2) {
/* 144:138 */                 sword.hitEntity(t, fakePlayer);
/* 145:    */               }
/* 146:    */             }
/* 147:142 */             if (sword.stackSize == 0) {
/* 148:143 */               swordInv.setInventorySlotContents(swordSlot, null);
/* 149:    */             }
/* 150:    */           }
/* 151:147 */           t.onDeath(DamageSource.causePlayerDamage(fakePlayer));
/* 152:148 */           t.motionX = 0.0D;
/* 153:149 */           t.motionY = 0.0D;
/* 154:150 */           t.motionZ = 0.0D;
/* 155:    */         }
/* 156:153 */         fakePlayer.setCurrentItemOrArmor(0, null);
/* 157:154 */         t.setDead();
/* 158:155 */         List list2 = world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(x - 2, y, z - 2, x + 3, y + 4, z + 2));
/* 159:157 */         for (Object aList2 : list2) {
/* 160:158 */           if (!list1.contains(aList2))
/* 161:    */           {
/* 162:159 */             int[] seq = XUHelper.rndSeq(6, world.rand);
/* 163:161 */             for (int j = 0; j < 6; j++)
/* 164:    */             {
/* 165:162 */               IInventory inv = TileEntityHopper.func_145893_b(world, x + net.minecraft.util.Facing.offsetsXForSide[seq[j]], y + net.minecraft.util.Facing.offsetsYForSide[seq[j]], z + net.minecraft.util.Facing.offsetsZForSide[seq[j]]);
/* 166:164 */               if ((inv != null) && (!((EntityItem)aList2).isDead))
/* 167:    */               {
/* 168:165 */                 ItemStack itemstack = ((EntityItem)aList2).getEntityItem().copy();
/* 169:166 */                 ItemStack itemstack1 = XUHelper.invInsert(inv, itemstack, net.minecraft.util.Facing.oppositeSide[j]);
/* 170:168 */                 if ((itemstack1 != null) && (itemstack1.stackSize != 0)) {
/* 171:169 */                   ((EntityItem)aList2).setEntityItemStack(itemstack1);
/* 172:    */                 } else {
/* 173:171 */                   ((EntityItem)aList2).setDead();
/* 174:    */                 }
/* 175:    */               }
/* 176:    */             }
/* 177:176 */             ((EntityItem)aList2).setDead();
/* 178:    */           }
/* 179:    */         }
/* 180:    */       }
/* 181:    */     }
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void onBlockAdded(World world, int x, int y, int z)
/* 185:    */   {
/* 186:194 */     if (!world.isClient) {
/* 187:195 */       world.scheduleBlockUpdate(x, y, z, this, 5 + world.rand.nextInt(100));
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity par5Entity)
/* 192:    */   {
/* 193:201 */     if ((par5Entity instanceof EntityXPOrb)) {
/* 194:202 */       par5Entity.setDead();
/* 195:    */     }
/* 196:    */   }
/* 197:    */   
/* 198:    */   @SideOnly(Side.CLIENT)
/* 199:    */   public IIcon getIcon(int par1, int x)
/* 200:    */   {
/* 201:212 */     int i = Math.min(par1, 2);
/* 202:213 */     return this.icons[i];
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void prepareForRender(String label) {}
/* 206:    */   
/* 207:    */   public BoxModel getWorldModel(IBlockAccess world, int x, int y, int z)
/* 208:    */   {
/* 209:222 */     BoxModel boxes = new BoxModel();
/* 210:223 */     float h = 0.0625F;
/* 211:224 */     boxes.add(new Box(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F));
/* 212:225 */     boxes.add(new Box(0.0625F, 0.0F, 0.0625F, 0.3125F, 0.75F, 0.3125F));
/* 213:226 */     boxes.add(new Box(0.0625F, 0.0F, 0.0625F, 0.3125F, 0.75F, 0.3125F).rotateY(1));
/* 214:227 */     boxes.add(new Box(0.0625F, 0.0F, 0.0625F, 0.3125F, 0.75F, 0.3125F).rotateY(2));
/* 215:228 */     boxes.add(new Box(0.0625F, 0.0F, 0.0625F, 0.3125F, 0.75F, 0.3125F).rotateY(3));
/* 216:229 */     return boxes;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public BoxModel getInventoryModel(int metadata)
/* 220:    */   {
/* 221:234 */     return getWorldModel(null, 0, 0, 0);
/* 222:    */   }
/* 223:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.block.BlockPeacefulTable
 * JD-Core Version:    0.7.0.1
 */