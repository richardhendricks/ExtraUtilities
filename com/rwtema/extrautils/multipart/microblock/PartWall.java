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
/*  20:    */ import net.minecraft.world.World;
/*  21:    */ 
/*  22:    */ public class PartWall
/*  23:    */   extends PartConnecting
/*  24:    */   implements IWall
/*  25:    */ {
/*  26: 23 */   public static final Cuboid6[] partCuboids = { new Cuboid6(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D), new Cuboid6(0.25D, 0.0D, 0.25D, 0.75D, 1.5D, 0.75D), new Cuboid6(0.25D, 0.0D, 0.0D, 0.75D, 1.5D, 0.5D), new Cuboid6(0.25D, 0.0D, 0.5D, 0.75D, 1.5D, 1.0D), new Cuboid6(0.0D, 0.0D, 0.25D, 0.5D, 1.5D, 0.75D), new Cuboid6(0.5D, 0.0D, 0.25D, 1.0D, 1.5D, 0.75D) };
/*  27: 34 */   public static final Cuboid6[] renderCuboids1 = { null, null, new Cuboid6(0.3125D, 0.0D, 0.0D, 0.6875D, 0.8125D, 0.25D), new Cuboid6(0.3125D, 0.0D, 0.75D, 0.6875D, 0.8125D, 1.0D), new Cuboid6(0.0D, 0.0D, 0.3125D, 0.25D, 0.8125D, 0.6875D), new Cuboid6(0.75D, 0.0D, 0.3125D, 1.0D, 0.8125D, 0.6875D) };
/*  28: 42 */   public static final Cuboid6[] renderCuboids2 = { null, null, new Cuboid6(0.3125D, 0.0D, 0.0D, 0.6875D, 0.8125D, 0.5D), new Cuboid6(0.3125D, 0.0D, 0.5D, 0.6875D, 0.8125D, 1.0D), new Cuboid6(0.0D, 0.0D, 0.3125D, 0.5D, 0.8125D, 0.6875D), new Cuboid6(0.5D, 0.0D, 0.3125D, 1.0D, 0.8125D, 0.6875D) };
/*  29:    */   public static final String type = "extrautils:wall";
/*  30:    */   
/*  31:    */   public String getType()
/*  32:    */   {
/*  33: 55 */     return "extrautils:wall";
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Cuboid6 getBounds()
/*  37:    */   {
/*  38: 60 */     return partCuboids[0];
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Iterable<Cuboid6> getCollisionBoxes()
/*  42:    */   {
/*  43: 65 */     List<Cuboid6> t = new ArrayList();
/*  44: 66 */     if (isEthereal()) {
/*  45: 66 */       return t;
/*  46:    */     }
/*  47: 68 */     if ((this.connectionMask & 0x2) != 0) {
/*  48: 69 */       t.add(partCuboids[1].copy());
/*  49:    */     }
/*  50: 71 */     for (int i = 2; i < 6; i++) {
/*  51: 72 */       if ((this.connectionMask & 1 << i) != 0) {
/*  52: 73 */         t.add(partCuboids[i].copy());
/*  53:    */       }
/*  54:    */     }
/*  55: 76 */     return t;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public boolean shouldConnect(int x, int y, int z, int direction)
/*  59:    */   {
/*  60: 81 */     Block l = world().getBlock(x, y, z);
/*  61: 83 */     if (((world().getTileEntity(x, y, z) instanceof IWall)) && 
/*  62: 84 */       (tile().canAddPart(PartWallDummy.dummyArms[direction]))) {
/*  63: 85 */       return ((TileMultipart)world().getTileEntity(x, y, z)).canAddPart(PartWallDummy.dummyArms[net.minecraft.util.Facing.oppositeSide[direction]]);
/*  64:    */     }
/*  65: 89 */     return (l.getMaterial().isOpaque()) && (l.renderAsNormalBlock()) && (tile().canAddPart(PartWallDummy.dummyArms[direction]));
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void registerPassThroughs()
/*  69:    */   {
/*  70: 95 */     MultipartGenerator.registerPassThroughInterface(IWall.class.getName());
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void reloadShape()
/*  74:    */   {
/*  75: 99 */     int prevMask = this.connectionMask;
/*  76:100 */     this.connectionMask = 0;
/*  77:103 */     for (int i = 2; i < 6; i++) {
/*  78:104 */       if (shouldConnect(x() + net.minecraft.util.Facing.offsetsXForSide[i], y() + net.minecraft.util.Facing.offsetsYForSide[i], z() + net.minecraft.util.Facing.offsetsZForSide[i], i)) {
/*  79:105 */         this.connectionMask |= 1 << i;
/*  80:    */       }
/*  81:    */     }
/*  82:109 */     if ((!world().isAirBlock(x(), y() + 1, z())) || ((this.connectionMask != 12) && (this.connectionMask != 48))) {
/*  83:110 */       this.connectionMask |= 0x2;
/*  84:    */     }
/*  85:113 */     if (prevMask != this.connectionMask)
/*  86:    */     {
/*  87:115 */       tile().notifyPartChange(this);
/*  88:116 */       tile().markRender();
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   @SideOnly(Side.CLIENT)
/*  93:    */   public boolean renderStatic(Vector3 pos, int pass)
/*  94:    */   {
/*  95:124 */     reloadShape();
/*  96:126 */     if (this.mat == null) {
/*  97:127 */       this.mat = MicroMaterialRegistry.getMaterial(this.material);
/*  98:    */     }
/*  99:130 */     boolean hasCenter = (this.connectionMask & 0x2) != 0;
/* 100:132 */     if ((this.mat != null) && (this.mat.canRenderInPass(pass)))
/* 101:    */     {
/* 102:133 */       if (hasCenter) {
/* 103:134 */         MicroblockRender.renderCuboid(new Vector3(x(), y(), z()), this.mat, pass, getRenderBounds(), 0);
/* 104:    */       }
/* 105:136 */       for (int i = 2; i < 6; i++) {
/* 106:137 */         if ((this.connectionMask & 1 << i) != 0) {
/* 107:138 */           MicroblockRender.renderCuboid(new Vector3(x(), y(), z()), this.mat, pass, hasCenter ? renderCuboids1[i] : renderCuboids2[i], 1 << net.minecraft.util.Facing.oppositeSide[i] | 1 << i);
/* 108:    */         }
/* 109:    */       }
/* 110:141 */       return true;
/* 111:    */     }
/* 112:144 */     return false;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public int getMetadata()
/* 116:    */   {
/* 117:149 */     return 2;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public TMultiPart newPart(boolean client)
/* 121:    */   {
/* 122:155 */     return new PartWall();
/* 123:    */   }
/* 124:    */   
/* 125:    */   public PartWall() {}
/* 126:    */   
/* 127:    */   public PartWall(int material)
/* 128:    */   {
/* 129:163 */     super(material);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public TMultiPart placePart(ItemStack stack, EntityPlayer player, World world, BlockCoord pos, int side, Vector3 arg5, int materialID)
/* 133:    */   {
/* 134:168 */     return new PartWall(materialID);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void renderItem(ItemStack item, MicroMaterialRegistry.IMicroMaterial material)
/* 138:    */   {
/* 139:174 */     MicroblockRender.renderCuboid(new Vector3(0.0D, 0.0D, 0.0D), material, -1, partCuboids[0], 0);
/* 140:175 */     MicroblockRender.renderCuboid(new Vector3(0.0D, 0.0D, 0.0D), material, -1, renderCuboids1[2], 0);
/* 141:176 */     MicroblockRender.renderCuboid(new Vector3(0.0D, 0.0D, 0.0D), material, -1, renderCuboids1[3], 0);
/* 142:    */   }
/* 143:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.multipart.microblock.PartWall
 * JD-Core Version:    0.7.0.1
 */