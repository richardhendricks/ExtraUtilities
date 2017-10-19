/*   1:    */ package com.rwtema.extrautils.multipart.microblock;
/*   2:    */ 
/*   3:    */ import codechicken.lib.vec.BlockCoord;
/*   4:    */ import codechicken.lib.vec.Cuboid6;
/*   5:    */ import codechicken.lib.vec.Vector3;
/*   6:    */ import codechicken.microblock.MicroMaterialRegistry;
/*   7:    */ import codechicken.microblock.MicroMaterialRegistry.IMicroMaterial;
/*   8:    */ import codechicken.microblock.MicroblockRender;
/*   9:    */ import codechicken.multipart.MultipartGenerator;
/*  10:    */ import codechicken.multipart.TMultiPart;
/*  11:    */ import codechicken.multipart.TileMultipart;
/*  12:    */ import cpw.mods.fml.relauncher.Side;
/*  13:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.List;
/*  16:    */ import net.minecraft.block.Block;
/*  17:    */ import net.minecraft.block.material.Material;
/*  18:    */ import net.minecraft.entity.player.EntityPlayer;
/*  19:    */ import net.minecraft.item.ItemStack;
/*  20:    */ import net.minecraft.util.MovingObjectPosition;
/*  21:    */ import net.minecraft.world.World;
/*  22:    */ 
/*  23:    */ public class PartFence
/*  24:    */   extends PartConnecting
/*  25:    */   implements IFence
/*  26:    */ {
/*  27: 25 */   public static final Cuboid6[] partCuboids = { new Cuboid6(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D), new Cuboid6(0.375D, 0.0D, 0.375D, 0.625D, 1.5D, 0.625D), new Cuboid6(0.375D, 0.0D, 0.0D, 0.625D, 1.5D, 0.375D), new Cuboid6(0.375D, 0.0D, 0.625D, 0.625D, 1.5D, 1.0D), new Cuboid6(0.0D, 0.0D, 0.375D, 0.375D, 1.5D, 0.625D), new Cuboid6(0.625D, 0.0D, 0.375D, 1.0D, 1.5D, 0.625D) };
/*  28: 36 */   public static final Cuboid6[] renderCuboids1 = { null, null, new Cuboid6(0.4375D, 0.75D, 0.0D, 0.5625D, 0.9375D, 0.375D), new Cuboid6(0.4375D, 0.75D, 0.625D, 0.5625D, 0.9375D, 1.0D), new Cuboid6(0.0D, 0.75D, 0.4375D, 0.375D, 0.9375D, 0.5625D), new Cuboid6(0.625D, 0.75D, 0.4375D, 1.0D, 0.9375D, 0.5625D) };
/*  29: 44 */   public static final Cuboid6[] renderCuboids2 = { null, null, new Cuboid6(0.4375D, 0.375D, 0.0D, 0.5625D, 0.5625D, 0.375D), new Cuboid6(0.4375D, 0.375D, 0.625D, 0.5625D, 0.5625D, 1.0D), new Cuboid6(0.0D, 0.375D, 0.4375D, 0.375D, 0.5625D, 0.5625D), new Cuboid6(0.625D, 0.375D, 0.4375D, 1.0D, 0.5625D, 0.5625D) };
/*  30:    */   public static final String type = "extrautils:fence";
/*  31:    */   
/*  32:    */   public PartFence() {}
/*  33:    */   
/*  34:    */   public PartFence(int material)
/*  35:    */   {
/*  36: 58 */     super(material);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String getType()
/*  40:    */   {
/*  41: 63 */     return "extrautils:fence";
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Cuboid6 getBounds()
/*  45:    */   {
/*  46: 68 */     return partCuboids[0];
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Iterable<Cuboid6> getCollisionBoxes()
/*  50:    */   {
/*  51: 73 */     List<Cuboid6> t = new ArrayList();
/*  52: 74 */     if (isEthereal()) {
/*  53: 74 */       return t;
/*  54:    */     }
/*  55: 76 */     t.add(partCuboids[1].copy());
/*  56: 78 */     for (int i = 2; i < 6; i++) {
/*  57: 79 */       if ((this.connectionMask & 1 << i) != 0) {
/*  58: 80 */         t.add(partCuboids[i].copy());
/*  59:    */       }
/*  60:    */     }
/*  61: 83 */     return t;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean shouldConnect(int x, int y, int z, int direction)
/*  65:    */   {
/*  66: 87 */     Block l = world().getBlock(x, y, z);
/*  67: 90 */     if (((world().getTileEntity(x, y, z) instanceof IFence)) && 
/*  68: 91 */       (tile().canAddPart(PartFenceDummyArm.dummyArms[direction]))) {
/*  69: 92 */       return ((TileMultipart)world().getTileEntity(x, y, z)).canAddPart(PartFenceDummyArm.dummyArms[net.minecraft.util.Facing.oppositeSide[direction]]);
/*  70:    */     }
/*  71: 97 */     return (l.getMaterial().isOpaque()) && (l.renderAsNormalBlock()) && (tile().canAddPart(PartFenceDummyArm.dummyArms[direction]));
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void reloadShape()
/*  75:    */   {
/*  76:101 */     int prevMask = this.connectionMask;
/*  77:102 */     this.connectionMask = 0;
/*  78:104 */     for (int i = 2; i < 6; i++) {
/*  79:105 */       if (shouldConnect(x() + net.minecraft.util.Facing.offsetsXForSide[i], y() + net.minecraft.util.Facing.offsetsYForSide[i], z() + net.minecraft.util.Facing.offsetsZForSide[i], i)) {
/*  80:106 */         this.connectionMask |= 1 << i;
/*  81:    */       }
/*  82:    */     }
/*  83:110 */     if (prevMask != this.connectionMask)
/*  84:    */     {
/*  85:112 */       tile().notifyPartChange(this);
/*  86:113 */       tile().markRender();
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   @SideOnly(Side.CLIENT)
/*  91:    */   public boolean renderStatic(Vector3 pos, int pass)
/*  92:    */   {
/*  93:121 */     reloadShape();
/*  94:123 */     if (this.mat == null) {
/*  95:124 */       this.mat = MicroMaterialRegistry.getMaterial(this.material);
/*  96:    */     }
/*  97:127 */     if ((this.mat != null) && (this.mat.canRenderInPass(pass)))
/*  98:    */     {
/*  99:129 */       MicroblockRender.renderCuboid(new Vector3(x(), y(), z()), this.mat, pass, getRenderBounds(), 0);
/* 100:131 */       for (int i = 0; i < 6; i++) {
/* 101:132 */         if ((this.connectionMask & 1 << i) != 0)
/* 102:    */         {
/* 103:133 */           MicroblockRender.renderCuboid(new Vector3(x(), y(), z()), this.mat, pass, renderCuboids1[i], 1 << net.minecraft.util.Facing.oppositeSide[i] | 1 << i);
/* 104:134 */           MicroblockRender.renderCuboid(new Vector3(x(), y(), z()), this.mat, pass, renderCuboids2[i], 1 << net.minecraft.util.Facing.oppositeSide[i] | 1 << i);
/* 105:    */         }
/* 106:    */       }
/* 107:137 */       return true;
/* 108:    */     }
/* 109:140 */     return false;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public float getStrength(MovingObjectPosition hit, EntityPlayer player)
/* 113:    */   {
/* 114:145 */     return getMaterial().getStrength(player);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public int getMetadata()
/* 118:    */   {
/* 119:150 */     return 1;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public TMultiPart newPart(boolean client)
/* 123:    */   {
/* 124:155 */     return new PartFence();
/* 125:    */   }
/* 126:    */   
/* 127:    */   public TMultiPart placePart(ItemStack stack, EntityPlayer player, World world, BlockCoord pos, int side, Vector3 arg5, int materialID)
/* 128:    */   {
/* 129:160 */     return new PartFence(materialID);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void registerPassThroughs()
/* 133:    */   {
/* 134:165 */     MultipartGenerator.registerPassThroughInterface(IFence.class.getName());
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void renderItem(ItemStack item, MicroMaterialRegistry.IMicroMaterial material)
/* 138:    */   {
/* 139:170 */     MicroblockRender.renderCuboid(new Vector3(0.0D, 0.0D, 0.0D), material, -1, partCuboids[0], 0);
/* 140:171 */     MicroblockRender.renderCuboid(new Vector3(0.0D, 0.0D, 0.0D), material, -1, renderCuboids1[2], 0);
/* 141:172 */     MicroblockRender.renderCuboid(new Vector3(0.0D, 0.0D, 0.0D), material, -1, renderCuboids1[3], 0);
/* 142:173 */     MicroblockRender.renderCuboid(new Vector3(0.0D, 0.0D, 0.0D), material, -1, renderCuboids2[2], 0);
/* 143:174 */     MicroblockRender.renderCuboid(new Vector3(0.0D, 0.0D, 0.0D), material, -1, renderCuboids2[3], 0);
/* 144:    */   }
/* 145:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.multipart.microblock.PartFence
 * JD-Core Version:    0.7.0.1
 */