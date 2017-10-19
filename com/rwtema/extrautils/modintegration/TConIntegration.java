/*   1:    */ package com.rwtema.extrautils.modintegration;
/*   2:    */ 
/*   3:    */ import com.rwtema.extrautils.ExtraUtils;
/*   4:    */ import com.rwtema.extrautils.ExtraUtilsMod;
/*   5:    */ import com.rwtema.extrautils.ExtraUtilsProxy;
/*   6:    */ import com.rwtema.extrautils.IClientCode;
/*   7:    */ import com.rwtema.extrautils.ILoading;
/*   8:    */ import cpw.mods.fml.common.FMLCommonHandler;
/*   9:    */ import cpw.mods.fml.common.event.FMLInterModComms;
/*  10:    */ import cpw.mods.fml.common.eventhandler.EventBus;
/*  11:    */ import cpw.mods.fml.relauncher.Side;
/*  12:    */ import cpw.mods.fml.relauncher.SideOnly;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.LinkedList;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.ListIterator;
/*  17:    */ import net.minecraft.item.ItemStack;
/*  18:    */ import net.minecraft.nbt.NBTTagCompound;
/*  19:    */ import net.minecraft.util.EnumChatFormatting;
/*  20:    */ import net.minecraftforge.common.MinecraftForge;
/*  21:    */ import net.minecraftforge.fluids.Fluid;
/*  22:    */ import net.minecraftforge.fluids.FluidRegistry;
/*  23:    */ import net.minecraftforge.fluids.FluidStack;
/*  24:    */ import tconstruct.library.TConstructRegistry;
/*  25:    */ import tconstruct.library.client.TConstructClientRegistry;
/*  26:    */ import tconstruct.library.crafting.CastingRecipe;
/*  27:    */ import tconstruct.library.crafting.FluidType;
/*  28:    */ import tconstruct.library.crafting.LiquidCasting;
/*  29:    */ import tconstruct.library.crafting.ModifyBuilder;
/*  30:    */ import tconstruct.library.crafting.Smeltery;
/*  31:    */ import tconstruct.library.crafting.ToolBuilder;
/*  32:    */ import tconstruct.library.tools.DynamicToolPart;
/*  33:    */ import tconstruct.library.tools.ToolMaterial;
/*  34:    */ import tconstruct.library.util.IPattern;
/*  35:    */ import tconstruct.modifiers.tools.ModExtraModifier;
/*  36:    */ import tconstruct.smeltery.TinkerSmeltery;
/*  37:    */ import tconstruct.tools.TinkerTools;
/*  38:    */ import tconstruct.util.config.PHConstruct;
/*  39:    */ import tconstruct.weaponry.TinkerWeaponry;
/*  40:    */ 
/*  41:    */ public class TConIntegration
/*  42:    */   implements ILoading
/*  43:    */ {
/*  44: 36 */   public static final TConIntegration instance = new TConIntegration();
/*  45:    */   
/*  46:    */   static
/*  47:    */   {
/*  48: 39 */     MinecraftForge.EVENT_BUS.register(new TConEvents());
/*  49:    */   }
/*  50:    */   
/*  51: 43 */   public static Fluid unstable = new Fluid("molten.unstableIngots").setDensity(3000).setViscosity(6000).setTemperature(1300);
/*  52: 44 */   public static Fluid bedrock = new Fluid("molten.bedrockiumIngots").setDensity(3000).setViscosity(6000).setTemperature(1300);
/*  53:    */   
/*  54:    */   public void addBedrockiumMaterial()
/*  55:    */   {
/*  56: 47 */     if ((ExtraUtils.bedrockiumBlock == null) || (ExtraUtils.bedrockium == null))
/*  57:    */     {
/*  58: 48 */       ExtraUtils.tcon_bedrock_material_id = -1;
/*  59: 49 */       return;
/*  60:    */     }
/*  61: 54 */     int id = ExtraUtils.tcon_bedrock_material_id;
/*  62: 55 */     if (id <= 0) {
/*  63: 55 */       return;
/*  64:    */     }
/*  65: 56 */     NBTTagCompound tag = new NBTTagCompound();
/*  66: 57 */     tag.setInteger("Id", id);
/*  67: 58 */     String name = "Bedrockium";
/*  68: 59 */     tag.setString("Name", "Bedrockium");
/*  69: 60 */     tag.setString("localizationString", "material.extrautils.bedrockium");
/*  70: 61 */     tag.setInteger("Durability", 7500);
/*  71: 62 */     tag.setInteger("MiningSpeed", 800);
/*  72: 63 */     tag.setInteger("HarvestLevel", 7);
/*  73: 64 */     tag.setInteger("Attack", 4);
/*  74: 65 */     tag.setFloat("HandleModifier", 1.75F);
/*  75: 66 */     tag.setInteger("Reinforced", 0);
/*  76: 67 */     tag.setFloat("Bow_ProjectileSpeed", 3.0F);
/*  77: 68 */     tag.setInteger("Bow_DrawSpeed", 200);
/*  78: 69 */     tag.setFloat("Projectile_Mass", 40.0F);
/*  79: 70 */     tag.setFloat("Projectile_Fragility", 0.4F);
/*  80: 71 */     tag.setString("Style", EnumChatFormatting.BLACK.toString());
/*  81: 72 */     tag.setInteger("Color", 16777215);
/*  82: 73 */     FMLInterModComms.sendMessage("TConstruct", "addMaterial", tag);
/*  83:    */     
/*  84: 75 */     FluidRegistry.registerFluid(bedrock);
/*  85:    */     
/*  86: 77 */     FluidType.registerFluidType(bedrock.getName(), ExtraUtils.bedrockiumBlock, 0, 850, bedrock, true);
/*  87: 78 */     Smeltery.addMelting(new ItemStack(ExtraUtils.bedrockiumBlock, 1), ExtraUtils.bedrockiumBlock, 0, 850, new FluidStack(bedrock, 1296));
/*  88: 79 */     Smeltery.addMelting(new ItemStack(ExtraUtils.bedrockium, 1, 0), ExtraUtils.bedrockiumBlock, 0, 850, new FluidStack(bedrock, 144));
/*  89:    */     
/*  90: 81 */     ItemStack ingotcast = new ItemStack(TinkerSmeltery.metalPattern, 1, 0);
/*  91: 82 */     TConstructRegistry.getBasinCasting().addCastingRecipe(new ItemStack(ExtraUtils.bedrockiumBlock, 1), new FluidStack(bedrock, 1296), null, true, 100);
/*  92: 83 */     TConstructRegistry.getTableCasting().addCastingRecipe(new ItemStack(ExtraUtils.bedrockium, 1), new FluidStack(bedrock, 144), ingotcast, false, 50);
/*  93:    */     
/*  94: 85 */     tag = new NBTTagCompound();
/*  95: 86 */     tag.setString("FluidName", bedrock.getName());
/*  96: 87 */     tag.setInteger("MaterialId", id);
/*  97: 88 */     FMLInterModComms.sendMessage("TConstruct", "addPartCastingMaterial", tag);
/*  98:    */     
/*  99: 90 */     tag = new NBTTagCompound();
/* 100: 91 */     tag.setInteger("MaterialId", id);
/* 101: 92 */     tag.setTag("Item", new ItemStack(ExtraUtils.bedrockium, 1, 0).writeToNBT(new NBTTagCompound()));
/* 102: 93 */     tag.setInteger("Value", 2);
/* 103: 94 */     FMLInterModComms.sendMessage("TConstruct", "addMaterialItem", tag);
/* 104:    */     
/* 105: 96 */     ExtraUtilsMod.proxy.exectuteClientCode(new IClientCode()
/* 106:    */     {
/* 107:    */       @SideOnly(Side.CLIENT)
/* 108:    */       public void exectuteClientCode()
/* 109:    */       {
/* 110:100 */         new TConTextureResourcePackBedrockium("Bedrockium").register();
/* 111:    */       }
/* 112:    */     });
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void addMagicWoodMaterial()
/* 116:    */   {
/* 117:107 */     if (ExtraUtils.decorative1 == null)
/* 118:    */     {
/* 119:108 */       ExtraUtils.tcon_magical_wood_id = -1;
/* 120:109 */       return;
/* 121:    */     }
/* 122:113 */     int id = ExtraUtils.tcon_magical_wood_id;
/* 123:114 */     if (id <= 0) {
/* 124:114 */       return;
/* 125:    */     }
/* 126:116 */     NBTTagCompound tag = new NBTTagCompound();
/* 127:117 */     tag.setInteger("Id", id);
/* 128:118 */     String name = "MagicWood";
/* 129:119 */     tag.setString("Name", "MagicWood");
/* 130:120 */     tag.setString("localizationString", "material.extrautils.magicwood");
/* 131:    */     
/* 132:122 */     tag.setInteger("Durability", 97);
/* 133:123 */     tag.setInteger("MiningSpeed", 150);
/* 134:124 */     tag.setInteger("HarvestLevel", 1);
/* 135:125 */     tag.setInteger("Attack", 0);
/* 136:126 */     tag.setFloat("HandleModifier", 1.0F);
/* 137:127 */     tag.setInteger("Reinforced", 0);
/* 138:128 */     tag.setFloat("Bow_ProjectileSpeed", 3.0F);
/* 139:129 */     tag.setInteger("Bow_DrawSpeed", 18);
/* 140:130 */     tag.setFloat("Projectile_Mass", 0.69F);
/* 141:131 */     tag.setFloat("Projectile_Fragility", 0.5F);
/* 142:132 */     tag.setString("Style", EnumChatFormatting.YELLOW.toString());
/* 143:133 */     tag.setInteger("Color", 7690273);
/* 144:134 */     FMLInterModComms.sendMessage("TConstruct", "addMaterial", tag);
/* 145:    */     
/* 146:136 */     ItemStack itemstack = new ItemStack(ExtraUtils.decorative1, 1, 8);
/* 147:    */     
/* 148:    */ 
/* 149:139 */     tag = new NBTTagCompound();
/* 150:140 */     tag.setInteger("MaterialId", id);
/* 151:141 */     NBTTagCompound item = new NBTTagCompound();
/* 152:142 */     itemstack.writeToNBT(item);
/* 153:143 */     tag.setTag("Item", item);
/* 154:144 */     tag.setInteger("Value", 2);
/* 155:145 */     FMLInterModComms.sendMessage("TConstruct", "addPartBuilderMaterial", tag);
/* 156:    */     
/* 157:147 */     tag = new NBTTagCompound();
/* 158:148 */     tag.setInteger("MaterialId", id);
/* 159:149 */     tag.setInteger("Value", 2);
/* 160:150 */     item = new NBTTagCompound();
/* 161:151 */     itemstack.writeToNBT(item);
/* 162:152 */     tag.setTag("Item", item);
/* 163:    */     
/* 164:154 */     FMLInterModComms.sendMessage("TConstruct", "addMaterialItem", tag);
/* 165:    */     
/* 166:156 */     ExtraUtilsMod.proxy.exectuteClientCode(new IClientCode()
/* 167:    */     {
/* 168:    */       @SideOnly(Side.CLIENT)
/* 169:    */       public void exectuteClientCode()
/* 170:    */       {
/* 171:160 */         new TConTextureResourcePackMagicWood("MagicWood").register();
/* 172:    */       }
/* 173:    */     });
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void addUnstableMaterial()
/* 177:    */   {
/* 178:167 */     if ((ExtraUtils.unstableIngot == null) || (ExtraUtils.decorative1 == null))
/* 179:    */     {
/* 180:168 */       ExtraUtils.tcon_unstable_material_id = -1;
/* 181:169 */       return;
/* 182:    */     }
/* 183:173 */     final int id = ExtraUtils.tcon_unstable_material_id;
/* 184:174 */     if (id <= 0) {
/* 185:174 */       return;
/* 186:    */     }
/* 187:175 */     NBTTagCompound tag = new NBTTagCompound();
/* 188:176 */     tag.setInteger("Id", id);
/* 189:177 */     String name = "unstableIngot";
/* 190:    */     
/* 191:179 */     final ToolMaterial mat = new ToolMaterial("unstableIngot", "material.extrautils.unstableIngot", 4, 100, 700, 2, 0.6F, 4, 0.0F, EnumChatFormatting.WHITE.toString(), 16777215);
/* 192:    */     
/* 193:    */ 
/* 194:    */ 
/* 195:    */ 
/* 196:    */ 
/* 197:    */ 
/* 198:    */ 
/* 199:    */ 
/* 200:    */ 
/* 201:    */ 
/* 202:    */ 
/* 203:    */ 
/* 204:192 */     TConstructRegistry.addtoolMaterial(id, mat);
/* 205:193 */     TConstructRegistry.addDefaultToolPartMaterial(id);
/* 206:194 */     TConstructRegistry.addBowMaterial(id, 109, 1.0F);
/* 207:195 */     TConstructRegistry.addArrowMaterial(id, 2.4F, 0.0F);
/* 208:    */     
/* 209:197 */     ExtraUtilsMod.proxy.exectuteClientCode(new IClientCode()
/* 210:    */     {
/* 211:    */       @SideOnly(Side.CLIENT)
/* 212:    */       public void exectuteClientCode()
/* 213:    */       {
/* 214:201 */         if (FMLCommonHandler.instance().getSide().isClient()) {
/* 215:202 */           TConstructClientRegistry.addMaterialRenderMapping(id, "tinker", mat.name().toLowerCase(), true);
/* 216:    */         }
/* 217:    */       }
/* 218:206 */     });
/* 219:207 */     FluidRegistry.registerFluid(unstable);
/* 220:    */     
/* 221:209 */     FluidType.registerFluidType(unstable.getName(), ExtraUtils.decorative1, 5, 850, unstable, true);
/* 222:210 */     Smeltery.addMelting(new ItemStack(ExtraUtils.decorative1, 1, 5), ExtraUtils.decorative1, 5, 850, new FluidStack(unstable, 648));
/* 223:211 */     Smeltery.addMelting(new ItemStack(ExtraUtils.unstableIngot, 1, 0), ExtraUtils.decorative1, 5, 850, new FluidStack(unstable, 72));
/* 224:212 */     Smeltery.addMelting(new ItemStack(ExtraUtils.unstableIngot, 1, 1), ExtraUtils.decorative1, 5, 850, new FluidStack(unstable, 8));
/* 225:213 */     Smeltery.addMelting(new ItemStack(ExtraUtils.unstableIngot, 1, 2), ExtraUtils.decorative1, 5, 850, new FluidStack(unstable, 144));
/* 226:    */     
/* 227:215 */     TConstructRegistry.getBasinCasting().addCastingRecipe(new ItemStack(ExtraUtils.decorative1, 1, 5), new FluidStack(unstable, 1296), null, true, 100);
/* 228:    */     
/* 229:    */ 
/* 230:218 */     List<CastingRecipe> newRecipies = new LinkedList();
/* 231:219 */     for (CastingRecipe recipe : TConstructRegistry.getTableCasting().getCastingRecipes()) {
/* 232:220 */       if ((recipe.castingMetal.getFluid() == TinkerSmeltery.moltenIronFluid) && (recipe.cast != null) && ((recipe.cast.getItem() instanceof IPattern)) && ((recipe.getResult().getItem() instanceof DynamicToolPart))) {
/* 233:223 */         newRecipies.add(recipe);
/* 234:    */       }
/* 235:    */     }
/* 236:226 */     FluidType ft = FluidType.getFluidType(unstable);
/* 237:229 */     for (CastingRecipe recipe : newRecipies)
/* 238:    */     {
/* 239:230 */       ItemStack output = recipe.getResult().copy();
/* 240:231 */       output.setItemDamage(id);
/* 241:    */       
/* 242:233 */       FluidStack liquid2 = new FluidStack(unstable, recipe.castingMetal.amount);
/* 243:    */       
/* 244:    */ 
/* 245:236 */       TConstructRegistry.getTableCasting().addCastingRecipe(output, liquid2, recipe.cast, recipe.consumeCast, recipe.coolTime);
/* 246:    */       
/* 247:238 */       Smeltery.addMelting(ft, output, 0, liquid2.amount / 2);
/* 248:    */     }
/* 249:241 */     tag = new NBTTagCompound();
/* 250:242 */     tag.setInteger("MaterialId", id);
/* 251:243 */     tag.setTag("Item", new ItemStack(ExtraUtils.unstableIngot, 1, 0).writeToNBT(new NBTTagCompound()));
/* 252:244 */     tag.setInteger("Value", 2);
/* 253:245 */     FMLInterModComms.sendMessage("TConstruct", "addMaterialItem", tag);
/* 254:    */     
/* 255:247 */     tag = new NBTTagCompound();
/* 256:248 */     tag.setInteger("MaterialId", id);
/* 257:249 */     tag.setTag("Item", new ItemStack(ExtraUtils.unstableIngot, 1, 2).writeToNBT(new NBTTagCompound()));
/* 258:250 */     tag.setInteger("Value", 2);
/* 259:251 */     FMLInterModComms.sendMessage("TConstruct", "addMaterialItem", tag);
/* 260:    */     
/* 261:    */ 
/* 262:254 */     ExtraUtilsMod.proxy.exectuteClientCode(new IClientCode()
/* 263:    */     {
/* 264:    */       @SideOnly(Side.CLIENT)
/* 265:    */       public void exectuteClientCode()
/* 266:    */       {
/* 267:258 */         new TConTextureResourcePackUnstableIngot("unstableIngot").register();
/* 268:    */       }
/* 269:    */     });
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void init()
/* 273:    */   {
/* 274:266 */     addBedrockiumMaterial();
/* 275:267 */     addUnstableMaterial();
/* 276:268 */     addMagicWoodMaterial();
/* 277:269 */     addModifiers();
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void addModifiers()
/* 281:    */   {
/* 282:273 */     ModifyBuilder.registerModifier(new ModExtraModifier(new ItemStack[] { new ItemStack(ExtraUtils.soul, 1, 0) }, "XUSoul"));
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void postInit()
/* 286:    */   {
/* 287:284 */     if (PHConstruct.alternativeBoltRecipe) {
/* 288:285 */       return;
/* 289:    */     }
/* 290:287 */     LiquidCasting tb = TConstructRegistry.getTableCasting();
/* 291:289 */     for (ListIterator<CastingRecipe> iterator = tb.getCastingRecipes().listIterator(); iterator.hasNext();)
/* 292:    */     {
/* 293:290 */       CastingRecipe castingRecipe = (CastingRecipe)iterator.next();
/* 294:292 */       if ((castingRecipe != null) && (castingRecipe.getClass() == CastingRecipe.class) && 
/* 295:    */       
/* 296:    */ 
/* 297:295 */         (castingRecipe.output != null) && (castingRecipe.output.getItem() == TinkerWeaponry.partBolt) && 
/* 298:    */         
/* 299:    */ 
/* 300:298 */         (castingRecipe.cast != null) && (castingRecipe.cast.getItem() == TinkerTools.toolRod))
/* 301:    */       {
/* 302:300 */         int materialID = ToolBuilder.instance.getMaterialID(castingRecipe.cast);
/* 303:301 */         if (materialID > 0)
/* 304:    */         {
/* 305:303 */           if (materialID == ExtraUtils.tcon_unstable_material_id) {
/* 306:304 */             iterator.set(new TConCastingRecipeUnsensitive(castingRecipe));
/* 307:    */           }
/* 308:305 */           if (materialID == ExtraUtils.tcon_bedrock_material_id) {
/* 309:306 */             iterator.set(new TConCastingRecipeUnsensitive(castingRecipe));
/* 310:    */           }
/* 311:    */         }
/* 312:    */       }
/* 313:    */     }
/* 314:    */   }
/* 315:    */   
/* 316:    */   public void preInit() {}
/* 317:    */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.modintegration.TConIntegration
 * JD-Core Version:    0.7.0.1
 */