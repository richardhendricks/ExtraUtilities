/*    1:     */ package com.rwtema.extrautils;
/*    2:     */ 
/*    3:     */ import codechicken.multipart.MultipartGenerator;
/*    4:     */ import cofh.api.energy.IEnergyHandler;
/*    5:     */ import com.google.common.collect.Lists;
/*    6:     */ import com.rwtema.extrautils.block.BlockAngelBlock;
/*    7:     */ import com.rwtema.extrautils.block.BlockBUD;
/*    8:     */ import com.rwtema.extrautils.block.BlockBedrockium;
/*    9:     */ import com.rwtema.extrautils.block.BlockBedrockium.ItemBedrockium;
/*   10:     */ import com.rwtema.extrautils.block.BlockChandelier;
/*   11:     */ import com.rwtema.extrautils.block.BlockCobblestoneCompressed;
/*   12:     */ import com.rwtema.extrautils.block.BlockColor;
/*   13:     */ import com.rwtema.extrautils.block.BlockColorData;
/*   14:     */ import com.rwtema.extrautils.block.BlockConveyor;
/*   15:     */ import com.rwtema.extrautils.block.BlockCursedEarth;
/*   16:     */ import com.rwtema.extrautils.block.BlockCurtain;
/*   17:     */ import com.rwtema.extrautils.block.BlockDecoration;
/*   18:     */ import com.rwtema.extrautils.block.BlockDrum;
/*   19:     */ import com.rwtema.extrautils.block.BlockEnderLily;
/*   20:     */ import com.rwtema.extrautils.block.BlockEnderthermicPump;
/*   21:     */ import com.rwtema.extrautils.block.BlockEtherealStone;
/*   22:     */ import com.rwtema.extrautils.block.BlockFilingCabinet;
/*   23:     */ import com.rwtema.extrautils.block.BlockGreenScreen;
/*   24:     */ import com.rwtema.extrautils.block.BlockMagnumTorch;
/*   25:     */ import com.rwtema.extrautils.block.BlockPeacefulTable;
/*   26:     */ import com.rwtema.extrautils.block.BlockPortal;
/*   27:     */ import com.rwtema.extrautils.block.BlockPureLove;
/*   28:     */ import com.rwtema.extrautils.block.BlockSoundMuffler;
/*   29:     */ import com.rwtema.extrautils.block.BlockSpike;
/*   30:     */ import com.rwtema.extrautils.block.BlockSpikeDiamond;
/*   31:     */ import com.rwtema.extrautils.block.BlockSpikeGold;
/*   32:     */ import com.rwtema.extrautils.block.BlockSpikeWood;
/*   33:     */ import com.rwtema.extrautils.block.BlockTimer;
/*   34:     */ import com.rwtema.extrautils.block.BlockTradingPost;
/*   35:     */ import com.rwtema.extrautils.block.BlockTrashCan;
/*   36:     */ import com.rwtema.extrautils.command.CommandKillEntities;
/*   37:     */ import com.rwtema.extrautils.crafting.RecipeBagDye;
/*   38:     */ import com.rwtema.extrautils.crafting.RecipeCustomOres;
/*   39:     */ import com.rwtema.extrautils.crafting.RecipeDifficultySpecific;
/*   40:     */ import com.rwtema.extrautils.crafting.RecipeFilterInvert;
/*   41:     */ import com.rwtema.extrautils.crafting.RecipeGBEnchanting;
/*   42:     */ import com.rwtema.extrautils.crafting.RecipeGlove;
/*   43:     */ import com.rwtema.extrautils.crafting.RecipeHorseTransmutation;
/*   44:     */ import com.rwtema.extrautils.crafting.RecipeMagicalWood;
/*   45:     */ import com.rwtema.extrautils.crafting.RecipeSoul;
/*   46:     */ import com.rwtema.extrautils.crafting.RecipeUnEnchanting;
/*   47:     */ import com.rwtema.extrautils.crafting.RecipeUnsigil;
/*   48:     */ import com.rwtema.extrautils.crafting.RecipeUnstableCrafting;
/*   49:     */ import com.rwtema.extrautils.crafting.RecipeUnstableIngotCrafting;
/*   50:     */ import com.rwtema.extrautils.crafting.RecipeUnstableNuggetCrafting;
/*   51:     */ import com.rwtema.extrautils.crafting.ShapedOreRecipeAlwaysLast;
/*   52:     */ import com.rwtema.extrautils.crafting.ShapelessOreRecipeAlwaysLast;
/*   53:     */ import com.rwtema.extrautils.dispenser.DispenserStuff;
/*   54:     */ import com.rwtema.extrautils.helper.ThaumcraftHelper;
/*   55:     */ import com.rwtema.extrautils.helper.XURandom;
/*   56:     */ import com.rwtema.extrautils.item.ItemAngelBlock;
/*   57:     */ import com.rwtema.extrautils.item.ItemAngelRing;
/*   58:     */ import com.rwtema.extrautils.item.ItemBedrockiumIngot;
/*   59:     */ import com.rwtema.extrautils.item.ItemBlockColor;
/*   60:     */ import com.rwtema.extrautils.item.ItemBlockDrum;
/*   61:     */ import com.rwtema.extrautils.item.ItemBlockEnderLily;
/*   62:     */ import com.rwtema.extrautils.item.ItemBlockGenerator;
/*   63:     */ import com.rwtema.extrautils.item.ItemBlockMetadata;
/*   64:     */ import com.rwtema.extrautils.item.ItemBlockQED;
/*   65:     */ import com.rwtema.extrautils.item.ItemBlockSpike;
/*   66:     */ import com.rwtema.extrautils.item.ItemBuildersWand;
/*   67:     */ import com.rwtema.extrautils.item.ItemDestructionPickaxe;
/*   68:     */ import com.rwtema.extrautils.item.ItemDivisionSigil;
/*   69:     */ import com.rwtema.extrautils.item.ItemErosionShovel;
/*   70:     */ import com.rwtema.extrautils.item.ItemEthericSword;
/*   71:     */ import com.rwtema.extrautils.item.ItemFilingCabinet;
/*   72:     */ import com.rwtema.extrautils.item.ItemGlove;
/*   73:     */ import com.rwtema.extrautils.item.ItemGoldenBag;
/*   74:     */ import com.rwtema.extrautils.item.ItemGoldenLasso;
/*   75:     */ import com.rwtema.extrautils.item.ItemHealingAxe;
/*   76:     */ import com.rwtema.extrautils.item.ItemHeatingCoil;
/*   77:     */ import com.rwtema.extrautils.item.ItemLawSword;
/*   78:     */ import com.rwtema.extrautils.item.ItemNodeUpgrade;
/*   79:     */ import com.rwtema.extrautils.item.ItemPaintbrush;
/*   80:     */ import com.rwtema.extrautils.item.ItemPrecisionShears;
/*   81:     */ import com.rwtema.extrautils.item.ItemSonarGoggles;
/*   82:     */ import com.rwtema.extrautils.item.ItemSoul;
/*   83:     */ import com.rwtema.extrautils.item.ItemTemporalHoe;
/*   84:     */ import com.rwtema.extrautils.item.ItemUnstableIngot;
/*   85:     */ import com.rwtema.extrautils.item.ItemWateringCan;
/*   86:     */ import com.rwtema.extrautils.item.scanner.ItemScanner;
/*   87:     */ import com.rwtema.extrautils.modintegration.EE3Integration;
/*   88:     */ import com.rwtema.extrautils.modintegration.MFRIntegration;
/*   89:     */ import com.rwtema.extrautils.modintegration.TE4IMC;
/*   90:     */ import com.rwtema.extrautils.multipart.ItemBlockMultiPartMagnumTorch;
/*   91:     */ import com.rwtema.extrautils.multipart.MagnumTorchPart;
/*   92:     */ import com.rwtema.extrautils.multipart.RegisterBlockPart;
/*   93:     */ import com.rwtema.extrautils.multipart.RegisterMicroMaterials;
/*   94:     */ import com.rwtema.extrautils.multipart.microblock.CreativeTabMicroBlocks;
/*   95:     */ import com.rwtema.extrautils.multipart.microblock.ItemMicroBlock;
/*   96:     */ import com.rwtema.extrautils.multipart.microblock.RecipeMicroBlocks;
/*   97:     */ import com.rwtema.extrautils.multipart.microblock.RegisterMicroBlocks;
/*   98:     */ import com.rwtema.extrautils.network.GuiHandler;
/*   99:     */ import com.rwtema.extrautils.network.NetworkHandler;
/*  100:     */ import com.rwtema.extrautils.network.PacketCodec;
/*  101:     */ import com.rwtema.extrautils.network.XUPacketBase;
/*  102:     */ import com.rwtema.extrautils.tileentity.TileEntityAntiMobTorch;
/*  103:     */ import com.rwtema.extrautils.tileentity.TileEntityBUD;
/*  104:     */ import com.rwtema.extrautils.tileentity.TileEntityBlockColorData;
/*  105:     */ import com.rwtema.extrautils.tileentity.TileEntityDrum;
/*  106:     */ import com.rwtema.extrautils.tileentity.TileEntityEnchantedSpike;
/*  107:     */ import com.rwtema.extrautils.tileentity.TileEntityEnderThermicLavaPump;
/*  108:     */ import com.rwtema.extrautils.tileentity.TileEntityFilingCabinet;
/*  109:     */ import com.rwtema.extrautils.tileentity.TileEntityPortal;
/*  110:     */ import com.rwtema.extrautils.tileentity.TileEntityRainMuffler;
/*  111:     */ import com.rwtema.extrautils.tileentity.TileEntitySoundMuffler;
/*  112:     */ import com.rwtema.extrautils.tileentity.TileEntityTradingPost;
/*  113:     */ import com.rwtema.extrautils.tileentity.TileEntityTrashCan;
/*  114:     */ import com.rwtema.extrautils.tileentity.TileEntityTrashCanEnergy;
/*  115:     */ import com.rwtema.extrautils.tileentity.TileEntityTrashCanFluids;
/*  116:     */ import com.rwtema.extrautils.tileentity.chests.BlockFullChest;
/*  117:     */ import com.rwtema.extrautils.tileentity.chests.BlockMiniChest;
/*  118:     */ import com.rwtema.extrautils.tileentity.chests.TileFullChest;
/*  119:     */ import com.rwtema.extrautils.tileentity.chests.TileMiniChest;
/*  120:     */ import com.rwtema.extrautils.tileentity.endercollector.BlockEnderCollector;
/*  121:     */ import com.rwtema.extrautils.tileentity.endercollector.TileEnderCollector;
/*  122:     */ import com.rwtema.extrautils.tileentity.enderconstructor.BlockEnderConstructor;
/*  123:     */ import com.rwtema.extrautils.tileentity.enderconstructor.EnderConstructorRecipesHandler;
/*  124:     */ import com.rwtema.extrautils.tileentity.enderconstructor.TileEnderConstructor;
/*  125:     */ import com.rwtema.extrautils.tileentity.enderconstructor.TileEnderPillar;
/*  126:     */ import com.rwtema.extrautils.tileentity.enderquarry.BlockBreakingRegistry;
/*  127:     */ import com.rwtema.extrautils.tileentity.enderquarry.BlockEnderMarkers;
/*  128:     */ import com.rwtema.extrautils.tileentity.enderquarry.BlockEnderQuarry;
/*  129:     */ import com.rwtema.extrautils.tileentity.enderquarry.BlockQuarryUpgrades;
/*  130:     */ import com.rwtema.extrautils.tileentity.enderquarry.TileEntityEnderMarker;
/*  131:     */ import com.rwtema.extrautils.tileentity.enderquarry.TileEntityEnderQuarry;
/*  132:     */ import com.rwtema.extrautils.tileentity.generators.BlockGenerator;
/*  133:     */ import com.rwtema.extrautils.tileentity.generators.TileEntityGeneratorPotion;
/*  134:     */ import com.rwtema.extrautils.tileentity.transfernodes.BlockRetrievalNode;
/*  135:     */ import com.rwtema.extrautils.tileentity.transfernodes.BlockTransferNode;
/*  136:     */ import com.rwtema.extrautils.tileentity.transfernodes.BlockTransferPipe;
/*  137:     */ import com.rwtema.extrautils.tileentity.transfernodes.TNHelper;
/*  138:     */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityFilterPipe;
/*  139:     */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityRetrievalNodeInventory;
/*  140:     */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityRetrievalNodeLiquid;
/*  141:     */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNodeEnergy;
/*  142:     */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNodeHyperEnergy;
/*  143:     */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNodeInventory;
/*  144:     */ import com.rwtema.extrautils.tileentity.transfernodes.TileEntityTransferNodeLiquid;
/*  145:     */ import com.rwtema.extrautils.tileentity.transfernodes.multiparts.ItemBlockTransferNodeMultiPart;
/*  146:     */ import com.rwtema.extrautils.tileentity.transfernodes.multiparts.ItemBlockTransferPipeMultiPart;
/*  147:     */ import com.rwtema.extrautils.tileentity.transfernodes.multiparts.RegisterPipeParts;
/*  148:     */ import com.rwtema.extrautils.tileentity.transfernodes.multiparts.RegisterTransferNodeParts;
/*  149:     */ import com.rwtema.extrautils.worldgen.Underdark.DarknessTickHandler;
/*  150:     */ import com.rwtema.extrautils.worldgen.Underdark.EventHandlerUnderdark;
/*  151:     */ import com.rwtema.extrautils.worldgen.Underdark.WorldProviderUnderdark;
/*  152:     */ import com.rwtema.extrautils.worldgen.WorldGenEnderLillies;
/*  153:     */ import com.rwtema.extrautils.worldgen.endoftime.WorldProviderEndOfTime;
/*  154:     */ import cpw.mods.fml.common.FMLCommonHandler;
/*  155:     */ import cpw.mods.fml.common.Loader;
/*  156:     */ import cpw.mods.fml.common.ModContainer;
/*  157:     */ import cpw.mods.fml.common.ModMetadata;
/*  158:     */ import cpw.mods.fml.common.discovery.ASMDataTable;
/*  159:     */ import cpw.mods.fml.common.discovery.ModCandidate;
/*  160:     */ import cpw.mods.fml.common.event.FMLInitializationEvent;
/*  161:     */ import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
/*  162:     */ import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
/*  163:     */ import cpw.mods.fml.common.event.FMLPostInitializationEvent;
/*  164:     */ import cpw.mods.fml.common.event.FMLPreInitializationEvent;
/*  165:     */ import cpw.mods.fml.common.event.FMLServerStartingEvent;
/*  166:     */ import cpw.mods.fml.common.eventhandler.EventBus;
/*  167:     */ import cpw.mods.fml.common.network.NetworkRegistry;
/*  168:     */ import cpw.mods.fml.common.registry.GameRegistry;
/*  169:     */ import java.io.File;
/*  170:     */ import java.util.ArrayList;
/*  171:     */ import java.util.Collections;
/*  172:     */ import java.util.HashSet;
/*  173:     */ import java.util.List;
/*  174:     */ import java.util.Map;
/*  175:     */ import java.util.Set;
/*  176:     */ import net.minecraft.block.Block;
/*  177:     */ import net.minecraft.block.BlockColored;
/*  178:     */ import net.minecraft.block.material.Material;
/*  179:     */ import net.minecraft.enchantment.Enchantment;
/*  180:     */ import net.minecraft.entity.EntityLiving;
/*  181:     */ import net.minecraft.entity.item.EntityItem;
/*  182:     */ import net.minecraft.entity.item.EntityXPOrb;
/*  183:     */ import net.minecraft.entity.monster.EntityMob;
/*  184:     */ import net.minecraft.init.Blocks;
/*  185:     */ import net.minecraft.init.Items;
/*  186:     */ import net.minecraft.item.Item;
/*  187:     */ import net.minecraft.item.ItemBlock;
/*  188:     */ import net.minecraft.item.ItemStack;
/*  189:     */ import net.minecraft.item.crafting.IRecipe;
/*  190:     */ import net.minecraft.item.crafting.ShapedRecipes;
/*  191:     */ import net.minecraft.item.crafting.ShapelessRecipes;
/*  192:     */ import net.minecraft.nbt.NBTTagCompound;
/*  193:     */ import net.minecraft.tileentity.TileEntity;
/*  194:     */ import net.minecraft.util.WeightedRandomChestContent;
/*  195:     */ import net.minecraft.world.World;
/*  196:     */ import net.minecraftforge.common.ChestGenHooks;
/*  197:     */ import net.minecraftforge.common.DimensionManager;
/*  198:     */ import net.minecraftforge.common.ForgeChunkManager;
/*  199:     */ import net.minecraftforge.common.ForgeChunkManager.OrderedLoadingCallback;
/*  200:     */ import net.minecraftforge.common.ForgeChunkManager.Ticket;
/*  201:     */ import net.minecraftforge.common.MinecraftForge;
/*  202:     */ import net.minecraftforge.common.config.Configuration;
/*  203:     */ import net.minecraftforge.common.config.Property;
/*  204:     */ import net.minecraftforge.common.util.ForgeDirection;
/*  205:     */ import net.minecraftforge.oredict.OreDictionary;
/*  206:     */ import net.minecraftforge.oredict.RecipeSorter;
/*  207:     */ import net.minecraftforge.oredict.RecipeSorter.Category;
/*  208:     */ import net.minecraftforge.oredict.ShapedOreRecipe;
/*  209:     */ import net.minecraftforge.oredict.ShapelessOreRecipe;
/*  210:     */ 
/*  211:     */ public class ExtraUtils
/*  212:     */ {
/*  213:     */   public static final int dec1EdgedStoneBricks = 0;
/*  214:     */   public static final int dec1EnderObsidian = 1;
/*  215:     */   public static final int dec1BurntQuartz = 2;
/*  216:     */   public static final int dec1FrostStone = 3;
/*  217:     */   public static final int dec1BorderStone = 4;
/*  218:     */   public static final int dec1UnstableBlock = 5;
/*  219:     */   public static final int dec1GravelBricks = 6;
/*  220:     */   public static final int dec1BorderStone2 = 7;
/*  221:     */   public static final int dec1MagicalWood = 8;
/*  222:     */   public static final int dec1SandyGlass = 9;
/*  223:     */   public static final int dec1GravelRoad = 10;
/*  224:     */   public static final int dec1EnderCore = 11;
/*  225:     */   public static final int dec1DiamondMatrix = 12;
/*  226:     */   public static final int dec1EnderSandAlloy = 13;
/*  227:     */   public static final int dec1EminenceStone = 14;
/*  228:     */   public static final int dec2ThickGlass = 0;
/*  229:     */   public static final int dec2ThickGlassEdged = 1;
/*  230:     */   public static final int dec2ThickGlassBricks = 2;
/*  231:     */   public static final int dec2ThickGlassCarved = 3;
/*  232:     */   public static final int dec2ThickGlassGolden = 4;
/*  233:     */   public static final int dec2ThickGlassObsidian = 5;
/*  234:     */   public static final int dec2ThickGlassSwirling = 6;
/*  235:     */   public static final int dec2ThickGlassGlowstone = 7;
/*  236:     */   public static final int dec2ThickGlassHeart = 8;
/*  237:     */   public static final int dec2ThickGlassSquare = 9;
/*  238:     */   public static final int dec2DarkGlass = 10;
/*  239:     */   public static final int dec2DarkGlassObsidian = 11;
/*  240: 130 */   public static final ArrayList<Item> spikeSwords = new ArrayList();
/*  241: 131 */   public static boolean allNonVanillaDimensionsValidForEnderPump = false;
/*  242:     */   public static Block angelBlock;
/*  243:     */   public static Item buildersWand;
/*  244:     */   public static boolean buildersWandEnabled;
/*  245:     */   public static Block chandelier;
/*  246:     */   public static boolean chandelierEnabled;
/*  247:     */   public static BlockCobblestoneCompressed cobblestoneCompr;
/*  248:     */   public static boolean cobblestoneComprEnabled;
/*  249:     */   public static Block colorBlockData;
/*  250: 140 */   public static List<BlockColor> colorblocks = new ArrayList();
/*  251:     */   public static boolean colorBlockDataEnabled;
/*  252:     */   public static Block conveyor;
/*  253:     */   public static boolean conveyorEnabled;
/*  254:     */   public static Item creativeBuildersWand;
/*  255:     */   public static boolean creativeBuildersWandEnabled;
/*  256:     */   public static CreativeTabExtraUtils creativeTabExtraUtils;
/*  257: 147 */   public static Item creativeTabIcon = null;
/*  258:     */   public static Block cursedEarth;
/*  259:     */   public static boolean cursedEarthEnabled;
/*  260:     */   public static Block curtain;
/*  261:     */   public static boolean curtainEnabled;
/*  262:     */   public static BlockDecoration decorative1;
/*  263:     */   public static boolean decorative1Enabled;
/*  264:     */   public static BlockDecoration decorative2;
/*  265:     */   public static boolean decorative2Enabled;
/*  266:     */   public static BlockBedrockium bedrockiumBlock;
/*  267:     */   public static boolean bedrockiumBlockEnabled;
/*  268:     */   public static BlockPureLove pureLove;
/*  269:     */   public static boolean pureLoveBlockEnabled;
/*  270:     */   public static Item destructionPickaxe;
/*  271:     */   public static boolean destructionPickaxeEnabled;
/*  272:     */   public static int underdarkDimID;
/*  273:     */   public static int endoftimeDimID;
/*  274:     */   public static boolean disableAdvFilingCabinet;
/*  275:     */   public static boolean disableBuildersWandRecipe;
/*  276:     */   public static boolean disableChandelierRecipe;
/*  277:     */   public static boolean disableColoredBlocksRecipes;
/*  278:     */   public static boolean disableCompressedCobblestoneRecipe;
/*  279:     */   public static boolean disableConveyorRecipe;
/*  280: 170 */   public static boolean disableCrossoverPipeRecipe = false;
/*  281:     */   public static boolean disableCurtainRecipe;
/*  282:     */   public static boolean disableDestructionPickaxeRecipe;
/*  283:     */   public static boolean disableDivisionSigilInChests;
/*  284:     */   public static boolean disableDrumRecipe;
/*  285:     */   public static boolean disableEnderLiliesInDungeons;
/*  286:     */   public static boolean disableEnderThermicPumpRecipe;
/*  287: 177 */   public static boolean disableEnergyPipeRecipe = false;
/*  288:     */   public static boolean disableErosionShovelRecipe;
/*  289:     */   public static boolean disableEtherealBlockRecipe;
/*  290:     */   public static boolean disableEthericSwordRecipe;
/*  291:     */   public static boolean disableFilingCabinet;
/*  292:     */   public static boolean disableFilterPipeRecipe;
/*  293:     */   public static boolean disableFilterRecipe;
/*  294: 184 */   public static boolean disableGeneratorRecipe = false;
/*  295:     */   public static boolean disableGoldenBagRecipe;
/*  296:     */   public static boolean disableGoldenLassoRecipe;
/*  297:     */   public static boolean disableHealingAxeRecipe;
/*  298:     */   public static boolean disableMagnumTorchRecipe;
/*  299: 189 */   public static boolean disableModSortingPipesRecipe = false;
/*  300:     */   public static boolean disableEnergyExtractionPipeRecipe;
/*  301:     */   public static boolean disableNodeUpgradeSpeedRecipe;
/*  302:     */   public static boolean disableObsidianRecipe;
/*  303:     */   public static boolean disablePaintbrushRecipe;
/*  304:     */   public static boolean disablePeacefulTableRecipe;
/*  305: 195 */   public static boolean disablePortalTexture = false;
/*  306:     */   public static boolean disablePrecisionShears;
/*  307:     */   public static boolean disableRainMufflerRecipe;
/*  308:     */   public static boolean disableRationingPipeRecipe;
/*  309:     */   public static boolean disableSonarGogglesRecipe;
/*  310:     */   public static boolean disableSortingPipeRecipe;
/*  311:     */   public static boolean disableSoundMufflerRecipe;
/*  312:     */   public static boolean disableSpikeRecipe;
/*  313:     */   public static boolean disableTemporalHoeRecipe;
/*  314:     */   public static boolean disableTimerBlockRecipe;
/*  315:     */   public static boolean disableTradingPostRecipe;
/*  316:     */   public static boolean disableTransferNodeEnergyRecipe;
/*  317:     */   public static boolean disableTransferNodeLiquidRecipe;
/*  318:     */   public static boolean disableTransferNodeLiquidRemoteRecipe;
/*  319:     */   public static boolean disableTransferNodeRecipe;
/*  320:     */   public static boolean disableTransferNodeRemoteRecipe;
/*  321:     */   public static boolean disableTransferPipeRecipe;
/*  322:     */   public static boolean disableTrashCanRecipe;
/*  323:     */   public static boolean disableUnstableIngotRecipe;
/*  324:     */   public static boolean disableWateringCanRecipe;
/*  325:     */   public static boolean disableWitherRecipe;
/*  326:     */   public static boolean disableNodeParticles;
/*  327:     */   public static boolean disableEnderQuarryUpgradesRecipes;
/*  328:     */   public static boolean disableQEDIngotSmeltRecipes;
/*  329:     */   public static boolean showMultiblocksTab;
/*  330:     */   public static Item divisionSigil;
/*  331:     */   public static boolean divisionSigilEnabled;
/*  332:     */   public static Block drum;
/*  333:     */   public static boolean drumEnabled;
/*  334:     */   public static BlockEnderLily enderLily;
/*  335:     */   public static boolean enderLilyEnabled;
/*  336:     */   public static int enderLilyRetrogenId;
/*  337:     */   public static Block enderQuarry;
/*  338:     */   public static boolean enderQuarryEnabled;
/*  339:     */   public static boolean enderQuarryRecipeEnabled;
/*  340:     */   public static Block enderThermicPump;
/*  341:     */   public static boolean enderThermicPumpEnabled;
/*  342:     */   public static Item erosionShovel;
/*  343:     */   public static boolean erosionShovelEnabled;
/*  344:     */   public static Block etheralBlock;
/*  345:     */   public static boolean etherealBlockEnabled;
/*  346:     */   public static Item ethericSword;
/*  347:     */   public static boolean ethericSwordEnabled;
/*  348:     */   public static BlockFilingCabinet filingCabinet;
/*  349:     */   public static boolean filingCabinetEnabled;
/*  350:     */   public static Block generator;
/*  351:     */   public static Block generator2;
/*  352:     */   public static Block generator3;
/*  353:     */   public static boolean generatorEnabled;
/*  354:     */   public static boolean generator2Enabled;
/*  355:     */   public static boolean generator3Enabled;
/*  356:     */   public static Item goldenBag;
/*  357:     */   public static boolean goldenBagEnabled;
/*  358:     */   public static Item goldenLasso;
/*  359:     */   public static boolean goldenLassoEnabled;
/*  360:     */   public static BlockGreenScreen greenScreen;
/*  361:     */   public static boolean greenScreenEnabled;
/*  362: 253 */   public static boolean handlesClientMethods = false;
/*  363:     */   public static Item healingAxe;
/*  364:     */   public static boolean healingAxeEnabled;
/*  365:     */   public static Block magnumTorch;
/*  366:     */   public static boolean magnumTorchEnabled;
/*  367: 258 */   public static Item microBlocks = null;
/*  368:     */   public static boolean microBlocksEnabled;
/*  369:     */   public static ItemNodeUpgrade nodeUpgrade;
/*  370:     */   public static boolean nodeUpgradeEnabled;
/*  371:     */   public static Item paintBrush;
/*  372:     */   public static boolean paintBrushEnabled;
/*  373:     */   public static Block peacefultable;
/*  374:     */   public static boolean peacefultableEnabled;
/*  375:     */   public static Block portal;
/*  376:     */   public static boolean portalEnabled;
/*  377:     */   public static ItemPrecisionShears precisionShears;
/*  378:     */   public static boolean precisionShearsEnabled;
/*  379:     */   public static Item scanner;
/*  380:     */   public static boolean scannerEnabled;
/*  381:     */   public static Item sonarGoggles;
/*  382:     */   public static boolean sonarGogglesEnabled;
/*  383:     */   public static Item lawSword;
/*  384:     */   public static boolean lawSwordEnabled;
/*  385:     */   public static Block soundMuffler;
/*  386:     */   public static boolean soundMufflerEnabled;
/*  387:     */   public static BlockSpike spike;
/*  388:     */   public static boolean spikeEnabled;
/*  389:     */   public static boolean spikeItemSword;
/*  390:     */   public static ItemBedrockiumIngot bedrockium;
/*  391:     */   public static boolean bedrockiumEnabled;
/*  392:     */   public static Item temporalHoe;
/*  393:     */   public static boolean temporalHoeEnabled;
/*  394:     */   public static Block timerBlock;
/*  395:     */   public static boolean timerBlockEnabled;
/*  396:     */   public static Block tradingPost;
/*  397:     */   public static boolean tradingPostEnabled;
/*  398:     */   public static Block transferNode;
/*  399:     */   public static boolean transferNodeEnabled;
/*  400:     */   public static Block transferNodeRemote;
/*  401:     */   public static boolean transferNodeRemoteEnabled;
/*  402:     */   public static BlockTransferPipe transferPipe;
/*  403:     */   public static BlockTransferPipe transferPipe2;
/*  404:     */   public static boolean transferPipeEnabled;
/*  405:     */   public static Block trashCan;
/*  406:     */   public static boolean trashCanEnabled;
/*  407:     */   public static ItemSoul soul;
/*  408:     */   public static boolean soulEnabled;
/*  409:     */   public static Item unstableIngot;
/*  410: 302 */   public static boolean unstableIngotExplosion = true;
/*  411:     */   public static boolean unstableIngotEnabled;
/*  412:     */   public static int[] validDimensionsForEnderPump;
/*  413:     */   public static ItemWateringCan wateringCan;
/*  414:     */   public static boolean wateringCanEnabled;
/*  415:     */   public static boolean qedEnabled;
/*  416:     */   public static BlockEnderConstructor qed;
/*  417: 309 */   public static Set<String> qedList = new HashSet();
/*  418:     */   public static ItemAngelRing angelRing;
/*  419:     */   public static boolean angelRingEnabled;
/*  420:     */   public static BlockEnderMarkers enderMarker;
/*  421:     */   public static boolean enderMarkerEnabled;
/*  422:     */   public static boolean permaSoulDrainOff;
/*  423:     */   public static boolean gloveEnabled;
/*  424:     */   public static Item glove;
/*  425:     */   public static boolean disableChestRecipe;
/*  426:     */   public static boolean disableWitherNoiseUnlessNearby;
/*  427:     */   public static boolean heatingCoilEnabled;
/*  428:     */   public static ItemHeatingCoil heatingCoil;
/*  429:     */   public static BlockColor colorBlockBrick;
/*  430:     */   public static BlockColor coloredWood;
/*  431:     */   public static BlockQuarryUpgrades enderQuarryUpgrade;
/*  432:     */   public static boolean enderQuarryUpgradeEnabled;
/*  433:     */   public static boolean spikeGoldEnabled;
/*  434:     */   public static boolean spikeDiamondEnabled;
/*  435:     */   public static boolean spikeWoodEnabled;
/*  436:     */   public static BlockSpike spikeGold;
/*  437:     */   public static BlockSpike spikeDiamond;
/*  438:     */   public static BlockColor colorQuartz;
/*  439:     */   public static BlockSpikeWood spikeWood;
/*  440:     */   public static BlockColor colorBlockRedstone;
/*  441:     */   public static boolean peacefulTableInAllDifficulties;
/*  442:     */   public static boolean disableInfiniteWater;
/*  443:     */   public static boolean disableCobblegen;
/*  444:     */   public static int versionHash;
/*  445: 337 */   public static Set<Class<? extends IRecipe>> registeredRecipes = new HashSet();
/*  446:     */   private static boolean angelBlockEnabled;
/*  447:     */   private static boolean BUDBlockEnabled;
/*  448:     */   private static BlockBUD BUDBlock;
/*  449:     */   private static boolean disableSuperWateringCanRecipe;
/*  450:     */   boolean hasSpecialInit;
/*  451:     */   public static int tcon_unstable_material_id;
/*  452:     */   public static int tcon_magical_wood_id;
/*  453:     */   public static int tcon_bedrock_material_id;
/*  454:     */   private static boolean disableAngelBlockRecipe;
/*  455:     */   private static boolean disableBUDBlockRecipe;
/*  456:     */   private static boolean disableAdvBUDBlockRecipe;
/*  457:     */   private static Block enderCollector;
/*  458:     */   private static boolean enderCollectorEnabled;
/*  459:     */   public static Block fullChest;
/*  460:     */   public static Block miniChest;
/*  461:     */   public static boolean fullChestEnabled;
/*  462:     */   public static boolean miniChestEnabled;
/*  463:     */   List<ILoading> loaders;
/*  464:     */   
/*  465:     */   public static void addDungeonItem(ItemStack item, int min, int max, String category, double probability)
/*  466:     */   {
/*  467: 360 */     int n = getWeightTotal(ChestGenHooks.getItems(category, XURandom.getInstance()));
/*  468: 361 */     int a = (int)Math.ceil(probability * n);
/*  469: 362 */     ChestGenHooks.addItem(category, new WeightedRandomChestContent(item, min, max, a));
/*  470:     */   }
/*  471:     */   
/*  472:     */   public static void registerTile(Class<? extends TileEntity> clazz, String name)
/*  473:     */   {
/*  474: 366 */     GameRegistry.registerTileEntity(clazz, name);
/*  475:     */   }
/*  476:     */   
/*  477:     */   public static void registerTile(Class<? extends TileEntity> clazz)
/*  478:     */   {
/*  479: 370 */     GameRegistry.registerTileEntity(clazz, clazz.getSimpleName());
/*  480:     */   }
/*  481:     */   
/*  482:     */   public static int getWeightTotal(WeightedRandomChestContent[] items)
/*  483:     */   {
/*  484: 374 */     if ((items == null) || (items.length == 0)) {
/*  485: 375 */       return 1;
/*  486:     */     }
/*  487: 378 */     int weight = 0;
/*  488: 380 */     for (WeightedRandomChestContent item : items) {
/*  489: 381 */       weight += item.itemWeight;
/*  490:     */     }
/*  491: 384 */     return weight;
/*  492:     */   }
/*  493:     */   
/*  494:     */   public static Block registerBlock(Block block)
/*  495:     */   {
/*  496: 388 */     return registerBlock(block, ItemBlock.class);
/*  497:     */   }
/*  498:     */   
/*  499:     */   public static Block registerBlock(Block block, Class<? extends ItemBlock> itemblock)
/*  500:     */   {
/*  501: 392 */     String s = block.getUnlocalizedName().substring("tile.".length());
/*  502: 393 */     s = s.replace("extrautils:", "");
/*  503: 394 */     return GameRegistry.registerBlock(block, itemblock, s);
/*  504:     */   }
/*  505:     */   
/*  506:     */   public static Item registerItem(Item item)
/*  507:     */   {
/*  508: 399 */     String s = getDefaultRegisterName(item);
/*  509: 400 */     registerItem(item, s);
/*  510: 401 */     return item;
/*  511:     */   }
/*  512:     */   
/*  513:     */   public static String getDefaultRegisterName(Item item)
/*  514:     */   {
/*  515: 405 */     String s = item.getUnlocalizedName().substring("item.".length());
/*  516: 406 */     s = s.replace("extrautils:", "");
/*  517: 407 */     return s;
/*  518:     */   }
/*  519:     */   
/*  520:     */   public static void registerItem(Item item, String s)
/*  521:     */   {
/*  522: 411 */     if (creativeTabIcon == null) {
/*  523: 412 */       creativeTabIcon = item;
/*  524:     */     }
/*  525: 414 */     GameRegistry.registerItem(item, s);
/*  526:     */   }
/*  527:     */   
/*  528:     */   public static void addRecipe(IRecipe recipe)
/*  529:     */   {
/*  530: 418 */     registerRecipe(recipe.getClass());
/*  531: 419 */     GameRegistry.addRecipe(recipe);
/*  532:     */   }
/*  533:     */   
/*  534:     */   public static void addRecipe(ItemStack out, Object... ingreds)
/*  535:     */   {
/*  536: 423 */     GameRegistry.addRecipe(new ShapedOreRecipe(out, ingreds));
/*  537:     */   }
/*  538:     */   
/*  539:     */   public static void registerRecipe(Class<? extends IRecipe> recipe)
/*  540:     */   {
/*  541: 427 */     if (registeredRecipes.contains(recipe)) {
/*  542: 428 */       return;
/*  543:     */     }
/*  544: 430 */     if (!recipe.getName().startsWith("com.rwtema.")) {
/*  545: 431 */       return;
/*  546:     */     }
/*  547: 433 */     registeredRecipes.add(recipe);
/*  548: 434 */     LogHelper.fine("Registering " + recipe.getSimpleName() + " to RecipeSorter", new Object[0]);
/*  549: 435 */     if (ShapedOreRecipe.class.isAssignableFrom(recipe)) {
/*  550: 436 */       RecipeSorter.register("extrautils:" + recipe.getSimpleName(), recipe, RecipeSorter.Category.SHAPED, "after:forge:shapedore");
/*  551: 437 */     } else if (ShapelessOreRecipe.class.isAssignableFrom(recipe)) {
/*  552: 438 */       RecipeSorter.register("extrautils:" + recipe.getSimpleName(), recipe, RecipeSorter.Category.SHAPELESS, "after:forge:shapelessore");
/*  553: 439 */     } else if (ShapedRecipes.class.isAssignableFrom(recipe)) {
/*  554: 440 */       RecipeSorter.register("extrautils:" + recipe.getSimpleName(), recipe, RecipeSorter.Category.SHAPED, "after:minecraft:shaped before:minecraft:shapeless");
/*  555: 441 */     } else if (ShapelessRecipes.class.isAssignableFrom(recipe)) {
/*  556: 442 */       RecipeSorter.register("extrautils:" + recipe.getSimpleName(), recipe, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless before:minecraft:bookcloning");
/*  557:     */     } else {
/*  558: 444 */       RecipeSorter.register("extrautils:" + recipe.getSimpleName(), recipe, RecipeSorter.Category.SHAPELESS, "after:forge:shapelessore");
/*  559:     */     }
/*  560:     */   }
/*  561:     */   
/*  562:     */   public void addSigil(String category, double probability)
/*  563:     */   {
/*  564: 448 */     addDungeonItem(new ItemStack(divisionSigil, 1), 1, 1, category, probability);
/*  565:     */   }
/*  566:     */   
/*  567:     */   public ExtraUtils()
/*  568:     */   {
/*  569: 343 */     this.hasSpecialInit = false;
/*  570:     */     
/*  571:     */ 
/*  572:     */ 
/*  573:     */ 
/*  574:     */ 
/*  575:     */ 
/*  576:     */ 
/*  577:     */ 
/*  578:     */ 
/*  579:     */ 
/*  580:     */ 
/*  581:     */ 
/*  582:     */ 
/*  583:     */ 
/*  584:     */ 
/*  585:     */ 
/*  586:     */ 
/*  587:     */ 
/*  588:     */ 
/*  589:     */ 
/*  590:     */ 
/*  591:     */ 
/*  592:     */ 
/*  593:     */ 
/*  594:     */ 
/*  595:     */ 
/*  596:     */ 
/*  597:     */ 
/*  598:     */ 
/*  599:     */ 
/*  600:     */ 
/*  601:     */ 
/*  602:     */ 
/*  603:     */ 
/*  604:     */ 
/*  605:     */ 
/*  606:     */ 
/*  607:     */ 
/*  608:     */ 
/*  609:     */ 
/*  610:     */ 
/*  611:     */ 
/*  612:     */ 
/*  613:     */ 
/*  614:     */ 
/*  615:     */ 
/*  616:     */ 
/*  617:     */ 
/*  618:     */ 
/*  619:     */ 
/*  620:     */ 
/*  621:     */ 
/*  622:     */ 
/*  623:     */ 
/*  624:     */ 
/*  625:     */ 
/*  626:     */ 
/*  627:     */ 
/*  628:     */ 
/*  629:     */ 
/*  630:     */ 
/*  631:     */ 
/*  632:     */ 
/*  633:     */ 
/*  634:     */ 
/*  635:     */ 
/*  636:     */ 
/*  637:     */ 
/*  638:     */ 
/*  639:     */ 
/*  640:     */ 
/*  641:     */ 
/*  642:     */ 
/*  643:     */ 
/*  644:     */ 
/*  645:     */ 
/*  646:     */ 
/*  647:     */ 
/*  648:     */ 
/*  649:     */ 
/*  650:     */ 
/*  651:     */ 
/*  652:     */ 
/*  653:     */ 
/*  654:     */ 
/*  655:     */ 
/*  656:     */ 
/*  657:     */ 
/*  658:     */ 
/*  659:     */ 
/*  660:     */ 
/*  661:     */ 
/*  662:     */ 
/*  663:     */ 
/*  664:     */ 
/*  665:     */ 
/*  666:     */ 
/*  667:     */ 
/*  668:     */ 
/*  669:     */ 
/*  670:     */ 
/*  671:     */ 
/*  672:     */ 
/*  673:     */ 
/*  674:     */ 
/*  675:     */ 
/*  676:     */ 
/*  677: 451 */     this.loaders = new ArrayList();
/*  678:     */   }
/*  679:     */   
/*  680:     */   public void preInit(FMLPreInitializationEvent event)
/*  681:     */   {
/*  682: 454 */     LogHelper.info("Hello World", new Object[0]);
/*  683:     */     
/*  684: 456 */     loadTcon();
/*  685:     */     
/*  686:     */ 
/*  687: 459 */     versionHash = event.getModMetadata().version.hashCode();
/*  688:     */     try
/*  689:     */     {
/*  690: 462 */       IEnergyHandler.class.getMethod("canConnectEnergy", new Class[] { ForgeDirection.class });
/*  691: 464 */       for (ModCandidate t : event.getAsmData().getCandidatesFor("cofh.api.energy"))
/*  692:     */       {
/*  693: 465 */         boolean hasProperApi = false;
/*  694: 466 */         if ((t.getClassList().contains("cofh/api/energy/IEnergyHandler")) && 
/*  695: 467 */           (!t.getClassList().contains("cofh/api/energy/IEnergyConnection"))) {
/*  696: 468 */           for (ModContainer mod : t.getContainedMods()) {
/*  697: 469 */             LogHelper.info("" + mod.getModId() + " (" + mod.getName() + ") appears to be missing the updated COFH api", new Object[0]);
/*  698:     */           }
/*  699:     */         }
/*  700:     */       }
/*  701:     */     }
/*  702:     */     catch (NoSuchMethodException e)
/*  703:     */     {
/*  704: 474 */       e.printStackTrace();
/*  705: 475 */       List<ModContainer> suspects = new ArrayList();
/*  706: 476 */       for (ModCandidate t : event.getAsmData().getCandidatesFor("cofh.api.energy"))
/*  707:     */       {
/*  708: 477 */         boolean hasProperApi = false;
/*  709: 478 */         if ((t.getClassList().contains("cofh/api/energy/IEnergyHandler")) && 
/*  710: 479 */           (!t.getClassList().contains("cofh/api/energy/IEnergyConnection")))
/*  711:     */         {
/*  712: 480 */           for (ModContainer mod : t.getContainedMods()) {
/*  713: 481 */             LogHelper.info("" + mod.getModId() + " (" + mod.getName() + ") appears to be missing the updated COFH api", new Object[0]);
/*  714:     */           }
/*  715: 482 */           suspects.addAll(t.getContainedMods());
/*  716:     */         }
/*  717:     */       }
/*  718: 487 */       String[] data = new String[2 + suspects.size()];
/*  719: 488 */       data[0] = "Some mod is including a older or incorrect copy of the COFH energy api. This will lead to instability and Extra Utilities will not run properly. Possible candidates that do not include the proper api are...";
/*  720: 489 */       data[1] = "";
/*  721: 490 */       for (int i = 0; i < suspects.size(); i++) {
/*  722: 491 */         data[(i + 1)] = ((ModContainer)suspects.get(i)).getModId();
/*  723:     */       }
/*  724: 494 */       ExtraUtilsMod.proxy.throwLoadingError("COFH API Error", data);
/*  725:     */     }
/*  726: 497 */     String networkPath = "com.rwtema.extrautils.network.packets.";
/*  727: 499 */     for (ModCandidate t : event.getAsmData().getCandidatesFor("com.rwtema.extrautils")) {
/*  728: 500 */       for (String s : t.getClassList())
/*  729:     */       {
/*  730: 501 */         s = s.replace('/', '.');
/*  731: 502 */         if (s.startsWith(networkPath)) {
/*  732:     */           try
/*  733:     */           {
/*  734: 505 */             Class<?> clazz = Class.forName(s);
/*  735: 506 */             if (XUPacketBase.class.isAssignableFrom(clazz)) {
/*  736: 507 */               PacketCodec.addClass(clazz);
/*  737:     */             } else {
/*  738: 509 */               clazz = clazz;
/*  739:     */             }
/*  740:     */           }
/*  741:     */           catch (ClassNotFoundException e)
/*  742:     */           {
/*  743: 513 */             throw new RuntimeException("Presented class missing, FML Bug?", e);
/*  744:     */           }
/*  745:     */           catch (NoClassDefFoundError e)
/*  746:     */           {
/*  747: 515 */             LogHelper.error(s + " can't be created", new Object[0]);
/*  748: 516 */             throw new RuntimeException(e);
/*  749:     */           }
/*  750:     */         }
/*  751:     */       }
/*  752:     */     }
/*  753: 523 */     NetworkHandler.register();
/*  754:     */     try
/*  755:     */     {
/*  756: 526 */       World.class.getMethod("getBlock", new Class[] { Integer.TYPE, Integer.TYPE, Integer.TYPE });
/*  757: 527 */       com.rwtema.extrautils.helper.XUHelper.deObf = true;
/*  758: 528 */       LogHelper.info("Dev Enviroment detected. Releasing hounds...", new Object[0]);
/*  759:     */     }
/*  760:     */     catch (NoSuchMethodException e)
/*  761:     */     {
/*  762: 530 */       com.rwtema.extrautils.helper.XUHelper.deObf = false;
/*  763:     */     }
/*  764:     */     catch (SecurityException e)
/*  765:     */     {
/*  766: 532 */       com.rwtema.extrautils.helper.XUHelper.deObf = false;
/*  767:     */     }
/*  768: 535 */     setupConfigs(event.getSuggestedConfigurationFile());
/*  769:     */     
/*  770: 537 */     creativeTabExtraUtils = new CreativeTabExtraUtils("extraUtil");
/*  771:     */     
/*  772: 539 */     registerStuff();
/*  773: 540 */     registerRecipes();
/*  774:     */     
/*  775: 542 */     ExtraUtilsMod.proxy.registerClientCommands();
/*  776: 544 */     for (ILoading loader : this.loaders) {
/*  777: 545 */       loader.preInit();
/*  778:     */     }
/*  779:     */   }
/*  780:     */   
/*  781:     */   public void loadTcon()
/*  782:     */   {
/*  783: 550 */     if (Loader.isModLoaded("TConstruct"))
/*  784:     */     {
/*  785: 551 */       ILoading r = null;
/*  786:     */       try
/*  787:     */       {
/*  788: 553 */         Class<?> clazz = Class.forName("com.rwtema.extrautils.modintegration.TConIntegration");
/*  789: 554 */         r = (ILoading)clazz.newInstance();
/*  790:     */       }
/*  791:     */       catch (Exception e)
/*  792:     */       {
/*  793: 556 */         e.printStackTrace();
/*  794:     */       }
/*  795: 559 */       if (r != null) {
/*  796: 559 */         this.loaders.add(r);
/*  797:     */       }
/*  798:     */     }
/*  799:     */   }
/*  800:     */   
/*  801:     */   public void setupConfigs(File file)
/*  802:     */   {
/*  803: 565 */     Configuration config = new Configuration(file);
/*  804: 566 */     config.load();
/*  805: 567 */     unstableIngotExplosion = config.get("options", "unstableIngotsExplode", true).getBoolean(true);
/*  806: 568 */     disableNodeParticles = config.get("options", "disableTransferNodeParticles", false).getBoolean(false);
/*  807: 569 */     disablePortalTexture = config.get("client_options", "disablePortalAnimation", false).getBoolean(false);
/*  808: 570 */     disableWitherRecipe = config.get("recipes", "disablePeacefulWitherRecipe", false).getBoolean(false);
/*  809: 571 */     peacefulTableInAllDifficulties = config.get("options", "peacefulTableInAllDifficulties", false).getBoolean(false);
/*  810: 572 */     disableInfiniteWater = config.get("options", "disableTransferNodeWatergen", false).getBoolean(false);
/*  811: 573 */     disableCobblegen = config.get("options", "disableTransferNodeCobblegen", false).getBoolean(false);
/*  812: 574 */     permaSoulDrainOff = config.get("options", "soulDrainResetsOnDeath", false).getBoolean(false);
/*  813: 575 */     disableWitherNoiseUnlessNearby = config.get("options", "disableWitherNoisesIfNotNearby", true).getBoolean(true);
/*  814: 577 */     if (config.hasKey("options", "mimicDeobf")) {
/*  815: 578 */       com.rwtema.extrautils.helper.XUHelper.deObf = true;
/*  816:     */     }
/*  817: 581 */     tcon_unstable_material_id = config.get("tinkersIntegration", "tcon_unstable_material_id", 314).getInt(314);
/*  818: 582 */     tcon_bedrock_material_id = config.get("tinkersIntegration", "tcon_bedrock_material_id", 315).getInt(315);
/*  819: 583 */     tcon_magical_wood_id = config.get("tinkersIntegration", "tcon_magical_wood_id", 316).getInt(316);
/*  820:     */     
/*  821: 585 */     fullChestEnabled = getBlock(config, "slightlyLargerChestEnabled");
/*  822: 586 */     miniChestEnabled = getBlock(config, "miniChestEnabled");
/*  823: 587 */     enderCollectorEnabled = getBlock(config, "enderCollectorEnabled");
/*  824: 588 */     gloveEnabled = getItem(config, "gloveEnabled");
/*  825: 589 */     pureLoveBlockEnabled = getBlock(config, "pureLoveBlockEnabled");
/*  826: 590 */     bedrockiumBlockEnabled = getBlock(config, "bedrockiumBlockEnabled");
/*  827: 591 */     enderMarkerEnabled = getBlock(config, "enderMarkerEnabled");
/*  828: 592 */     angelBlockEnabled = getBlock(config, "angelBlockEnabled");
/*  829: 593 */     BUDBlockEnabled = getBlock(config, "BUDBlockEnabled");
/*  830: 594 */     chandelierEnabled = getBlock(config, "chandelierEnabled");
/*  831: 595 */     disableChandelierRecipe = config.get("recipes", "disableChandelierRecipe", false).getBoolean(false);
/*  832: 596 */     disableChestRecipe = config.get("recipes", "disableAltChestRecipe", false).getBoolean(false);
/*  833: 597 */     disableColoredBlocksRecipes = config.get("recipes", "disableColoredBlocksRecipes", false).getBoolean(false);
/*  834: 598 */     colorBlockDataEnabled = getBlock(config, "colorBlockDataEnabled");
/*  835: 599 */     disableCompressedCobblestoneRecipe = config.get("recipes", "disableCompressedCobblestoneRecipe", false).getBoolean(false);
/*  836: 600 */     cobblestoneComprEnabled = getBlock(config, "cobblestoneComprEnabled");
/*  837: 601 */     disableConveyorRecipe = config.get("recipes", "disableConveyorRecipe", false).getBoolean(false);
/*  838: 602 */     conveyorEnabled = getBlock(config, "conveyorEnabled");
/*  839: 603 */     greenScreenEnabled = getBlock(config, "greenScreen");
/*  840: 604 */     disablePeacefulTableRecipe = config.get("recipes", "disablePeacefulTableRecipe", false).getBoolean(false);
/*  841: 605 */     peacefultableEnabled = getBlock(config, "peacefultableEnabled");
/*  842: 606 */     disableSoundMufflerRecipe = config.get("recipes", "disableSoundMufflerRecipe", false).getBoolean(false);
/*  843: 607 */     disableRainMufflerRecipe = config.get("recipes", "disableRainMufflerRecipe", false).getBoolean(false);
/*  844: 608 */     soundMufflerEnabled = getBlock(config, "soundMufflerEnabled");
/*  845: 609 */     disableTradingPostRecipe = config.get("recipes", "disableTradingPostRecipe", false).getBoolean(false);
/*  846: 610 */     tradingPostEnabled = getBlock(config, "tradingPost");
/*  847: 611 */     disableFilterPipeRecipe = config.get("recipes", "disableFilterPipeRecipe", false).getBoolean(false);
/*  848: 612 */     disableSortingPipeRecipe = config.get("recipes", "disableSortingPipeRecipe", false).getBoolean(false);
/*  849: 613 */     disableModSortingPipesRecipe = config.get("recipes", "disableModSortingPipeRecipe", false).getBoolean(false);
/*  850: 614 */     disableEnergyExtractionPipeRecipe = config.get("recipes", "disableEnergyExtractionPipeRecipe", false).getBoolean(false);
/*  851: 615 */     disableRationingPipeRecipe = config.get("recipes", "disableRationingPipeRecipe", false).getBoolean(false);
/*  852: 616 */     disableTransferPipeRecipe = config.get("recipes", "disableTransferPipeRecipe", false).getBoolean(false);
/*  853: 617 */     transferPipeEnabled = getBlock(config, "transferPipeEnabled");
/*  854: 618 */     disableTransferNodeRecipe = config.get("recipes", "disableTransferNodeRecipe", false).getBoolean(false);
/*  855: 619 */     disableTransferNodeLiquidRecipe = config.get("recipes", "disableTransferNodeLiquidRecipe", false).getBoolean(false);
/*  856: 620 */     disableTransferNodeEnergyRecipe = config.get("recipes", "disableTransferNodeEnergyRecipe", false).getBoolean(false);
/*  857: 621 */     transferNodeEnabled = getBlock(config, "transferNodeEnabled");
/*  858: 622 */     disableCurtainRecipe = config.get("recipes", "disableCurtainRecipe", false).getBoolean(false);
/*  859: 623 */     curtainEnabled = getBlock(config, "curtainEnabled");
/*  860: 624 */     cursedEarthEnabled = getBlock(config, "cursedEarth");
/*  861: 625 */     disableTrashCanRecipe = config.get("recipes", "disableTrashCanRecipe", false).getBoolean(false);
/*  862: 626 */     trashCanEnabled = getBlock(config, "trashCan");
/*  863: 627 */     disableSpikeRecipe = config.get("recipes", "disableSpikeRecipe", false).getBoolean(false);
/*  864: 628 */     spikeEnabled = getBlock(config, "spikeEnabled");
/*  865: 629 */     spikeGoldEnabled = getBlock(config, "spikeGoldEnabled");
/*  866: 630 */     spikeDiamondEnabled = getBlock(config, "spikeDiamondEnabled");
/*  867: 631 */     spikeWoodEnabled = getBlock(config, "spikeWoodEnabled");
/*  868: 632 */     disableEtherealBlockRecipe = config.get("recipes", "disableEtherealGlassRecipe", false).getBoolean(false);
/*  869: 633 */     etherealBlockEnabled = getBlock(config, "etherealBlockEnabled");
/*  870: 634 */     disableEnderThermicPumpRecipe = config.get("recipes", "disableEnderThermicPumpRecipe", false).getBoolean(false);
/*  871: 635 */     disableEnderQuarryUpgradesRecipes = config.get("recipes", "disableEnderQuarryUpgradesRecipes", false).getBoolean(false);
/*  872: 636 */     allNonVanillaDimensionsValidForEnderPump = !config.get("options", "disableEnderPumpInAllDimensions", false).getBoolean(false);
/*  873: 637 */     validDimensionsForEnderPump = config.get("options", "EnderPumpDimensionExceptions", new int[0]).getIntList();
/*  874: 638 */     enderThermicPumpEnabled = getBlock(config, "enderThermicPumpEnabled");
/*  875: 639 */     disableTimerBlockRecipe = config.get("recipes", "disableRedstoneClockRecipe", false).getBoolean(false);
/*  876: 640 */     timerBlockEnabled = getBlock(config, "timerBlockEnabled");
/*  877: 641 */     disableMagnumTorchRecipe = config.get("recipes", "disableMagnumTorchRecipe", false).getBoolean(false);
/*  878: 642 */     magnumTorchEnabled = getBlock(config, "magnumTorchEnabled");
/*  879: 643 */     disableDrumRecipe = config.get("recipes", "disableDrumRecipe", false).getBoolean(false);
/*  880: 644 */     drumEnabled = getBlock(config, "drumEnabled");
/*  881: 645 */     decorative1Enabled = getBlock(config, "decorative_1Enabled");
/*  882: 646 */     filingCabinetEnabled = getBlock(config, "filingCabinetEnabled");
/*  883: 647 */     disableFilingCabinet = config.get("recipes", "disableFilingCabinet", false).getBoolean(false);
/*  884: 648 */     disableAdvFilingCabinet = config.get("recipes", "disableAdvFilingCabinet", false).getBoolean(false);
/*  885: 649 */     disableTransferPipeRecipe = config.get("recipes", "disableTransferPipeRecipe", false).getBoolean(false);
/*  886: 650 */     enderLilyEnabled = getBlock(config, "enderLilyEnabled");
/*  887: 651 */     disableEnderLiliesInDungeons = config.get("recipes", "disableEnderLiliesInDungeons", false).getBoolean(false);
/*  888: 652 */     disableEnergyPipeRecipe = config.get("recipes", "disableEnergyPipeRecipe", false).getBoolean(false);
/*  889: 653 */     disableCrossoverPipeRecipe = config.get("recipes", "disableEnergyPipeRecipe", false).getBoolean(false);
/*  890: 654 */     portalEnabled = getBlock(config, "portalEnabled");
/*  891: 655 */     underdarkDimID = config.get("options", "deepDarkDimensionID", -100).getInt(-100);
/*  892: 656 */     endoftimeDimID = config.get("options", "lastMilleniumDimensionID", -112).getInt(-112);
/*  893: 657 */     decorative2Enabled = getBlock(config, "decorative_2Enabled");
/*  894: 658 */     generatorEnabled = getBlock(config, "generatorEnabled");
/*  895: 659 */     generator2Enabled = getBlock(config, "generator8Enabled");
/*  896: 660 */     generator3Enabled = getBlock(config, "generator64Enabled");
/*  897: 661 */     enderQuarryEnabled = getBlock(config, "enderQuarryEnabled");
/*  898: 662 */     enderQuarryUpgradeEnabled = getBlock(config, "enderQuarryUpgradeEnabled");
/*  899: 663 */     disableGeneratorRecipe = config.get("recipes", "disableGeneratorRecipe", false).getBoolean(false);
/*  900: 664 */     transferNodeRemoteEnabled = getBlock(config, "transferNodeRemoteEnabled");
/*  901: 665 */     disableTransferNodeRemoteRecipe = config.get("recipes", "disableRetrievalNodeRecipe", false).getBoolean(false);
/*  902: 666 */     disableTransferNodeLiquidRemoteRecipe = config.get("recipes", "disableRetrievalNodeLiquidRecipe", false).getBoolean(false);
/*  903: 667 */     disableGoldenLassoRecipe = config.get("recipes", "disableGoldenLassoRecipe", false).getBoolean(false);
/*  904: 668 */     goldenLassoEnabled = getItem(config, "goldenLassoEnabled");
/*  905: 669 */     disablePaintbrushRecipe = config.get("recipes", "disablePaintbrushRecipe", false).getBoolean(false);
/*  906: 670 */     paintBrushEnabled = getItem(config, "paintBrush");
/*  907: 671 */     disableUnstableIngotRecipe = config.get("recipes", "disableUnstableIngotRecipe", false).getBoolean(false);
/*  908: 672 */     unstableIngotEnabled = getItem(config, "unstableIngotEnabled");
/*  909: 673 */     disableBuildersWandRecipe = config.get("recipes", "disableBuildersWandRecipe", false).getBoolean(false);
/*  910: 674 */     buildersWandEnabled = getItem(config, "buildersWandEnabled");
/*  911: 675 */     creativeBuildersWandEnabled = getItem(config, "creativeBuildersWandEnabled");
/*  912: 676 */     disableEthericSwordRecipe = config.get("recipes", "disableEthericSwordRecipe", false).getBoolean(false);
/*  913: 677 */     ethericSwordEnabled = getItem(config, "ethericSword");
/*  914: 678 */     disableErosionShovelRecipe = config.get("recipes", "disableErosionShovelRecipe", false).getBoolean(false);
/*  915: 679 */     erosionShovelEnabled = getItem(config, "erosionShovel");
/*  916: 680 */     disableDestructionPickaxeRecipe = config.get("recipes", "disableDestructionPickaxeRecipe", false).getBoolean(false);
/*  917: 681 */     destructionPickaxeEnabled = getItem(config, "destructionPickaxe");
/*  918: 682 */     disableHealingAxeRecipe = config.get("recipes", "disableHealingAxeRecipe", false).getBoolean(false);
/*  919: 683 */     healingAxeEnabled = getItem(config, "healingAxe");
/*  920: 684 */     disableTemporalHoeRecipe = config.get("recipes", "disableReversingHoeRecipe", false).getBoolean(false);
/*  921: 685 */     temporalHoeEnabled = getItem(config, "temporalHoe");
/*  922: 686 */     disableDivisionSigilInChests = config.get("recipes", "disableDivisionSiginInChests", false).getBoolean(false);
/*  923: 687 */     divisionSigilEnabled = getItem(config, "divisionSigilEnabled");
/*  924: 688 */     disableSonarGogglesRecipe = config.get("recipes", "disableSonarGogglesRecipe", false).getBoolean(false);
/*  925: 689 */     sonarGogglesEnabled = getItem(config, "sonarGogglesEnabled");
/*  926: 690 */     disableWateringCanRecipe = config.get("recipes", "disableWateringCanRecipe", false).getBoolean(false);
/*  927: 691 */     disableSuperWateringCanRecipe = config.get("recipes", "disableSuperWateringCanRecipe", false).getBoolean(false);
/*  928: 692 */     wateringCanEnabled = getItem(config, "wateringCanEnabled");
/*  929: 693 */     disableFilterRecipe = config.get("recipes", "disableFilterRecipe", false).getBoolean(false);
/*  930: 694 */     disableNodeUpgradeSpeedRecipe = config.get("recipes", "disableNodeUpgradeSpeedRecipe", false).getBoolean(false);
/*  931: 695 */     nodeUpgradeEnabled = getItem(config, "upgradeNodeEnabled");
/*  932: 696 */     disableGoldenBagRecipe = config.get("recipes", "disableGoldenBagRecipe", false).getBoolean(false);
/*  933: 697 */     goldenBagEnabled = getItem(config, "goldenBagEnabled");
/*  934: 698 */     scannerEnabled = getItem(config, "scannerEnabled");
/*  935: 699 */     bedrockiumEnabled = getItem(config, "bedrockiumIngotEnabled");
/*  936: 700 */     angelRingEnabled = getItem(config, "angelRingEnabled");
/*  937: 701 */     soulEnabled = getItem(config, "soulEnabled");
/*  938: 702 */     lawSwordEnabled = getItem(config, "lawSwordEnabled");
/*  939:     */     
/*  940: 704 */     disableQEDIngotSmeltRecipes = config.get("recipes", "disableQEDIngotSmeltRecipes", false).getBoolean(false);
/*  941:     */     
/*  942: 706 */     heatingCoilEnabled = getItem(config, "heatingCoilEnabled");
/*  943:     */     
/*  944: 708 */     qedEnabled = getBlock(config, "QEDEnabled");
/*  945:     */     
/*  946: 710 */     Property prop_version = config.get("QEDCrafting", "QEDVersion", 0, "Internal version number to add/remove items in future updates. Set to -1 to disable auto-updates.");
/*  947:     */     
/*  948: 712 */     int prev_version = prop_version.getInt();
/*  949:     */     
/*  950: 714 */     Property prop = config.get("QEDCrafting", "QEDItems", new String[] { "tile.extrautils:extractor_base_remote.0", "tile.extrautils:extractor_base_remote.6", "item.extrautils:nodeUpgrade.5", "item.extrautils:nodeUpgrade.6", "tile.extrautils:enderQuarry", "tile.extrautils:magnumTorch" }, "ItemStack names to enforce crafting in the QED");
/*  951:     */     
/*  952:     */ 
/*  953:     */ 
/*  954:     */ 
/*  955:     */ 
/*  956:     */ 
/*  957:     */ 
/*  958:     */ 
/*  959:     */ 
/*  960: 724 */     String[][] toAdd = { { "tile.extrautils:endMarker" }, { "tile.extrautils:extractor_base.12" }, { "tile.extrautils:enderQuarryUpgrade.0", "tile.extrautils:extractor_base.13" } };
/*  961:     */     
/*  962:     */ 
/*  963:     */ 
/*  964:     */ 
/*  965:     */ 
/*  966: 730 */     String[][] toRemove = { { "tile.extrautils:enderQuarry" }, new String[0], new String[0] };
/*  967:     */     
/*  968:     */ 
/*  969:     */ 
/*  970:     */ 
/*  971:     */ 
/*  972: 736 */     assert (toAdd.length == toRemove.length);
/*  973:     */     
/*  974: 738 */     List<String> strings = new ArrayList();
/*  975:     */     
/*  976: 740 */     Collections.addAll(strings, prop.getStringList());
/*  977: 742 */     if ((prev_version >= 0) && (prev_version < toAdd.length))
/*  978:     */     {
/*  979: 743 */       for (int i = prev_version; i < toAdd.length; i++)
/*  980:     */       {
/*  981: 744 */         if (toAdd[i] != null) {
/*  982: 745 */           for (String s : toAdd[i]) {
/*  983: 746 */             if (strings.add(s)) {
/*  984: 747 */               LogHelper.info("QEDCrafting Updater: Added Recipe - " + s, new Object[0]);
/*  985:     */             }
/*  986:     */           }
/*  987:     */         }
/*  988: 750 */         if (toRemove[i] != null) {
/*  989: 751 */           for (String s : toRemove[i]) {
/*  990: 752 */             if (strings.remove(s)) {
/*  991: 753 */               LogHelper.info("QEDCrafting Updater: Removed Recipe - " + s, new Object[0]);
/*  992:     */             }
/*  993:     */           }
/*  994:     */         }
/*  995:     */       }
/*  996: 756 */       prop_version.set(toAdd.length);
/*  997:     */     }
/*  998: 759 */     prop.set((String[])strings.toArray(new String[strings.size()]));
/*  999: 761 */     if (strings.size() > 0)
/* 1000:     */     {
/* 1001: 762 */       for (String s : strings)
/* 1002:     */       {
/* 1003: 763 */         qedList.add(s);
/* 1004: 764 */         LogHelper.fine("Recipes constructing " + s + " will now be used in the Ender Constructor", new Object[0]);
/* 1005:     */       }
/* 1006:     */     }
/* 1007:     */     else
/* 1008:     */     {
/* 1009: 767 */       qedEnabled = false;
/* 1010: 768 */       LogHelper.fine("No Recipes for QED found. QED will be disabled.", new Object[0]);
/* 1011:     */     }
/* 1012: 772 */     if (enderQuarryEnabled)
/* 1013:     */     {
/* 1014: 773 */       TileEntityEnderQuarry.baseDrain = config.get("Ender Quarry Power", "baseDrain", 1800).getInt(1800);
/* 1015: 774 */       TileEntityEnderQuarry.hardnessDrain = config.get("Ender Quarry Power", "hardnessDrain", 200).getInt(200);
/* 1016:     */     }
/* 1017: 777 */     if (Loader.instance().getIndexedModList().containsKey("ForgeMultipart"))
/* 1018:     */     {
/* 1019: 778 */       microBlocksEnabled = getItem(config, "microBlocksEnabled");
/* 1020: 779 */       showMultiblocksTab = (microBlocksEnabled) && (!config.get("options", "disableMultiBlocksCreativeTab", false).getBoolean(false));
/* 1021: 780 */       if (showMultiblocksTab) {
/* 1022: 781 */         CreativeTabMicroBlocks.instance = new CreativeTabMicroBlocks();
/* 1023:     */       }
/* 1024:     */     }
/* 1025: 786 */     precisionShearsEnabled = getItem(config, "precisionShearsId");
/* 1026: 787 */     enderLilyRetrogenId = config.get("worldgen", "retrogen_enderlillies", 1).getInt(1);
/* 1027: 788 */     config.save();
/* 1028:     */   }
/* 1029:     */   
/* 1030:     */   public void registerStuff()
/* 1031:     */   {
/* 1032: 794 */     if (miniChestEnabled)
/* 1033:     */     {
/* 1034: 795 */       registerBlock(ExtraUtils.miniChest = new BlockMiniChest());
/* 1035: 796 */       registerTile(TileMiniChest.class);
/* 1036:     */     }
/* 1037: 799 */     if (fullChestEnabled)
/* 1038:     */     {
/* 1039: 800 */       registerBlock(ExtraUtils.fullChest = new BlockFullChest());
/* 1040: 801 */       registerTile(TileFullChest.class);
/* 1041:     */     }
/* 1042: 804 */     if (enderCollectorEnabled)
/* 1043:     */     {
/* 1044: 805 */       registerBlock(ExtraUtils.enderCollector = new BlockEnderCollector());
/* 1045: 806 */       registerTile(TileEnderCollector.class);
/* 1046:     */     }
/* 1047: 809 */     if (gloveEnabled)
/* 1048:     */     {
/* 1049: 810 */       glove = new ItemGlove();
/* 1050: 811 */       registerItem(glove);
/* 1051:     */     }
/* 1052: 814 */     if (heatingCoilEnabled)
/* 1053:     */     {
/* 1054: 815 */       heatingCoil = new ItemHeatingCoil();
/* 1055: 816 */       registerItem(heatingCoil);
/* 1056:     */     }
/* 1057: 819 */     if (soulEnabled)
/* 1058:     */     {
/* 1059: 820 */       soul = new ItemSoul();
/* 1060: 821 */       registerItem(soul);
/* 1061:     */     }
/* 1062: 824 */     if (angelRingEnabled)
/* 1063:     */     {
/* 1064: 825 */       angelRing = new ItemAngelRing();
/* 1065: 826 */       registerItem(angelRing);
/* 1066:     */     }
/* 1067: 830 */     if (qedEnabled)
/* 1068:     */     {
/* 1069: 831 */       qed = new BlockEnderConstructor();
/* 1070: 832 */       registerBlock(qed, ItemBlockQED.class);
/* 1071: 833 */       registerTile(TileEnderConstructor.class);
/* 1072: 834 */       registerTile(TileEnderPillar.class);
/* 1073:     */     }
/* 1074: 837 */     if (enderMarkerEnabled)
/* 1075:     */     {
/* 1076: 838 */       enderMarker = new BlockEnderMarkers();
/* 1077: 839 */       registerBlock(enderMarker);
/* 1078: 840 */       registerTile(TileEntityEnderMarker.class);
/* 1079:     */     }
/* 1080: 843 */     if (curtainEnabled) {
/* 1081: 844 */       registerBlock(ExtraUtils.curtain = new BlockCurtain());
/* 1082:     */     }
/* 1083: 847 */     if (angelBlockEnabled) {
/* 1084: 848 */       registerBlock(ExtraUtils.angelBlock = new BlockAngelBlock(), ItemAngelBlock.class);
/* 1085:     */     }
/* 1086: 850 */     if (BUDBlockEnabled)
/* 1087:     */     {
/* 1088: 851 */       registerBlock(ExtraUtils.BUDBlock = new BlockBUD(), ItemBlockMetadata.class);
/* 1089: 852 */       registerTile(TileEntityBUD.class);
/* 1090:     */     }
/* 1091: 855 */     if (chandelierEnabled) {
/* 1092: 856 */       registerBlock(ExtraUtils.chandelier = new BlockChandelier());
/* 1093:     */     }
/* 1094: 859 */     if ((decorative1Enabled) && (decorative2Enabled))
/* 1095:     */     {
/* 1096: 860 */       decorative1 = new BlockDecoration(true);
/* 1097: 861 */       decorative1.setBlockName("extrautils:decorativeBlock1");
/* 1098: 862 */       registerBlock(decorative1, ItemBlockMetadata.class);
/* 1099: 863 */       decorative1.addBlock(0, "Edged Stone Bricks", "extrautils:ConnectedTextures/test", true, true);
/* 1100: 864 */       decorative1.addBlock(1, "Ender-infused Obsidian", "extrautils:ConnectedTextures/endsidian", true, true);
/* 1101: 865 */       decorative1.hardness[1] = Blocks.obsidian.getBlockHardness(null, 0, 0, 0);
/* 1102: 866 */       decorative1.resistance[1] = (Blocks.obsidian.getExplosionResistance(null) * 5.0F);
/* 1103: 867 */       decorative1.addBlock(2, "Burnt Quartz", "extrautils:ConnectedTextures/dark", true, true);
/* 1104: 868 */       decorative1.addBlock(3, "Frosted Stone", "extrautils:ConnectedTextures/icystone", true, false);
/* 1105: 869 */       decorative1.addBlock(4, "Border Stone", "extrautils:ConnectedTextures/carved", true, true);
/* 1106: 870 */       decorative1.addBlock(5, "Unstable Ingot Block", "extrautils:ConnectedTextures/uingot", true, true);
/* 1107: 871 */       decorative1.addBlock(6, "Gravel Bricks", "extrautils:ConnectedTextures/gravel_brick");
/* 1108: 872 */       decorative1.addBlock(7, "Border Stone (Alternate)", "extrautils:ConnectedTextures/singlestonebrick", true, true);
/* 1109: 873 */       decorative1.addBlock(8, "Magical Wood", "extrautils:ConnectedTextures/magic_wood_corners");
/* 1110: 874 */       decorative1.enchantBonus[8] = 2.5F;
/* 1111: 875 */       decorative1.addBlock(9, "Sandy Glass", "extrautils:sandedGlass");
/* 1112: 876 */       decorative1.addBlock(10, "Gravel Road", "extrautils:ConnectedTextures/gravel_road", true, true);
/* 1113: 877 */       decorative1.flipTopBottom[10] = true;
/* 1114: 878 */       decorative1.addBlock(11, "Ender Core", "extrautils:endCore");
/* 1115: 879 */       decorative1.enchantBonus[11] = 10.0F;
/* 1116: 880 */       decorative1.isSuperEnder[11] = true;
/* 1117: 881 */       decorative1.light[11] = 5;
/* 1118: 882 */       decorative1.addBlock(12, "Diamond-Etched Computational Matrix", "extrautils:diamondCore");
/* 1119: 883 */       decorative1.light[12] = 14;
/* 1120: 884 */       decorative1.addBlock(13, "Ender-Sand Alloy", "extrautils:ConnectedTextures/endslab", true, true);
/* 1121: 885 */       decorative1.isEnder[13] = true;
/* 1122: 886 */       decorative1.addBlock(14, "Eminence Stone", "extrautils:ConnectedTextures/purplestone", true, true);
/* 1123:     */       
/* 1124: 888 */       OreDictionary.registerOre("bricksGravel", new ItemStack(decorative1, 1, 6));
/* 1125: 889 */       OreDictionary.registerOre("blockEnderObsidian", new ItemStack(decorative1, 1, 1));
/* 1126: 890 */       OreDictionary.registerOre("burntQuartz", new ItemStack(decorative1, 1, 2));
/* 1127: 891 */       OreDictionary.registerOre("blockIcestone", new ItemStack(decorative1, 1, 3));
/* 1128: 892 */       OreDictionary.registerOre("blockUnstable", new ItemStack(decorative1, 1, 5));
/* 1129: 893 */       OreDictionary.registerOre("blockMagicWood", new ItemStack(decorative1, 1, 7));
/* 1130: 894 */       OreDictionary.registerOre("blockEnderCore", new ItemStack(decorative1, 1, 11));
/* 1131: 895 */       OreDictionary.registerOre("blockGlassSandy", new ItemStack(decorative1, 1, 9));
/* 1132:     */       
/* 1133: 897 */       decorative2 = new BlockDecoration(false);
/* 1134: 898 */       decorative2.setBlockName("extrautils:decorativeBlock2");
/* 1135: 899 */       registerBlock(decorative2, ItemBlockMetadata.class);
/* 1136: 900 */       decorative2.addBlock(0, "Thickened Glass", "extrautils:ConnectedTextures/glass1", true, false);
/* 1137: 901 */       decorative2.addBlock(1, "Edged Glass", "extrautils:ConnectedTextures/glass2", true, false);
/* 1138: 902 */       decorative2.addBlock(2, "Glass Bricks", "extrautils:ConnectedTextures/glass3", true, false);
/* 1139: 903 */       decorative2.addBlock(3, "Carved Glass", "extrautils:ConnectedTextures/glass4", true, false);
/* 1140: 904 */       decorative2.addBlock(4, "Golden Edged Glass", "extrautils:ConnectedTextures/glass5", true, false);
/* 1141: 905 */       decorative2.addBlock(5, "Obsidian Glass", "extrautils:ConnectedTextures/glass6", true, false);
/* 1142: 906 */       decorative2.hardness[5] = 4.0F;
/* 1143: 907 */       decorative2.resistance[5] = 2000.0F;
/* 1144: 908 */       decorative2.addBlock(6, "Swirling Glass", "extrautils:ConnectedTextures/glass7", true, false);
/* 1145: 909 */       decorative2.addBlock(7, "Glowstone Glass", "extrautils:ConnectedTextures/glass8", true, false);
/* 1146: 910 */       decorative2.light[7] = 15;
/* 1147: 911 */       decorative2.addBlock(8, "Heart Glass", "extrautils:ConnectedTextures/glass9", true, false);
/* 1148: 912 */       decorative2.addBlock(9, "Square Glass", "extrautils:glassQuadrants", false, false);
/* 1149: 913 */       decorative2.addBlock(10, "Dark Glass", "extrautils:ConnectedTextures/darkglass", true, false);
/* 1150: 914 */       decorative2.opacity[10] = 255;
/* 1151: 915 */       decorative2.addBlock(11, "Dark Obsidian Glass", "extrautils:ConnectedTextures/obsidiandarkglass", true, false);
/* 1152: 916 */       decorative2.opacity[11] = 255;
/* 1153: 917 */       decorative2.hardness[11] = 4.0F;
/* 1154: 918 */       decorative2.resistance[11] = 2000.0F;
/* 1155: 920 */       for (int i = 0; i < 12; i++) {
/* 1156: 921 */         if ((i != 4) && (i != 5) && (i != 7) && (i != 8) && (i != 10) && (i != 11)) {
/* 1157: 922 */           OreDictionary.registerOre("blockGlass", new ItemStack(decorative2, 1, i));
/* 1158:     */         }
/* 1159:     */       }
/* 1160:     */     }
/* 1161: 927 */     if (pureLoveBlockEnabled) {
/* 1162: 928 */       registerBlock(ExtraUtils.pureLove = new BlockPureLove());
/* 1163:     */     }
/* 1164: 930 */     if (bedrockiumBlockEnabled) {
/* 1165: 931 */       registerBlock(ExtraUtils.bedrockiumBlock = new BlockBedrockium(), BlockBedrockium.ItemBedrockium.class);
/* 1166:     */     }
/* 1167: 933 */     if (colorBlockDataEnabled)
/* 1168:     */     {
/* 1169: 934 */       registerBlock(ExtraUtils.colorBlockData = new BlockColorData());
/* 1170: 936 */       if (paintBrushEnabled)
/* 1171:     */       {
/* 1172: 937 */         paintBrush = new ItemPaintbrush();
/* 1173: 938 */         registerItem(paintBrush);
/* 1174:     */       }
/* 1175: 942 */       registerBlock(ExtraUtils.colorBlockBrick = (BlockColor)new BlockColor(Blocks.stonebrick, "bricksStone", "stonebrick").setBlockName("extrautils:colorStoneBrick"), ItemBlockColor.class);
/* 1176: 943 */       registerBlock(ExtraUtils.coloredWood = (BlockColor)new BlockColor(Blocks.planks, "plankWood", "planks_oak").setBlockName("extrautils:colorWoodPlanks"), ItemBlockColor.class);
/* 1177: 944 */       registerBlock(new BlockColor(Blocks.glowstone, "glowstone"), ItemBlockColor.class);
/* 1178: 945 */       registerBlock(new BlockColor(Blocks.stone, "stone"), ItemBlockColor.class);
/* 1179: 946 */       registerBlock(ExtraUtils.colorQuartz = new BlockColor(Blocks.quartz_block, null, "quartz_block_top"), ItemBlockColor.class);
/* 1180: 947 */       registerBlock(new BlockColor(Blocks.soul_sand, "soulsand"), ItemBlockColor.class);
/* 1181: 948 */       registerBlock(new BlockColor(Blocks.lit_redstone_lamp, null, "redstone_lamp_on").setCustomRecipe(6, new Object[] { "SRS", "SDS", "SPS", Character.valueOf('S'), Blocks.redstone_lamp, Character.valueOf('R'), Blocks.redstone_torch, Character.valueOf('D'), "dye", Character.valueOf('P'), paintBrush }), ItemBlockColor.class);
/* 1182: 949 */       registerBlock(new BlockColor(Blocks.brick_block, "bricksClay"), ItemBlockColor.class);
/* 1183: 950 */       registerBlock(new BlockColor(Blocks.cobblestone, "cobblestone"), ItemBlockColor.class);
/* 1184: 951 */       registerBlock(new BlockColor(Blocks.lapis_block, "blockLapis"), ItemBlockColor.class);
/* 1185: 952 */       registerBlock(new BlockColor(Blocks.obsidian, "obsidian"), ItemBlockColor.class);
/* 1186: 953 */       registerBlock(ExtraUtils.colorBlockRedstone = new BlockColor(Blocks.redstone_block, "blockRedstone"), ItemBlockColor.class);
/* 1187: 954 */       registerBlock(new BlockColor(Blocks.coal_block, "blockCoal"), ItemBlockColor.class);
/* 1188:     */       
/* 1189: 956 */       registerTile(TileEntityBlockColorData.class);
/* 1190:     */     }
/* 1191: 959 */     if (cobblestoneComprEnabled)
/* 1192:     */     {
/* 1193: 960 */       registerBlock(ExtraUtils.cobblestoneCompr = new BlockCobblestoneCompressed(Material.rock), ItemBlockMetadata.class);
/* 1194: 962 */       for (int i = 0; i < 16; i++)
/* 1195:     */       {
/* 1196: 963 */         String s = "compressed" + BlockCobblestoneCompressed.getOreDictName(i) + (1 + BlockCobblestoneCompressed.getCompr(i)) + "x";
/* 1197: 964 */         OreDictionary.registerOre(s, new ItemStack(cobblestoneCompr, 1, i));
/* 1198:     */       }
/* 1199:     */     }
/* 1200: 968 */     if (conveyorEnabled) {
/* 1201: 969 */       registerBlock(ExtraUtils.conveyor = new BlockConveyor());
/* 1202:     */     }
/* 1203: 972 */     if (filingCabinetEnabled)
/* 1204:     */     {
/* 1205: 973 */       registerBlock(ExtraUtils.filingCabinet = new BlockFilingCabinet(), ItemFilingCabinet.class);
/* 1206: 974 */       registerTile(TileEntityFilingCabinet.class);
/* 1207:     */     }
/* 1208: 977 */     if (greenScreenEnabled) {
/* 1209: 978 */       registerBlock(ExtraUtils.greenScreen = new BlockGreenScreen(), ItemBlockMetadata.class);
/* 1210:     */     }
/* 1211: 981 */     if (peacefultableEnabled) {
/* 1212: 982 */       registerBlock(ExtraUtils.peacefultable = new BlockPeacefulTable());
/* 1213:     */     }
/* 1214: 985 */     if (tradingPostEnabled)
/* 1215:     */     {
/* 1216: 986 */       registerBlock(ExtraUtils.tradingPost = new BlockTradingPost());
/* 1217: 987 */       registerTile(TileEntityTradingPost.class);
/* 1218:     */     }
/* 1219: 990 */     if (soundMufflerEnabled)
/* 1220:     */     {
/* 1221: 991 */       registerBlock(ExtraUtils.soundMuffler = new BlockSoundMuffler(), ItemBlockMetadata.class);
/* 1222: 992 */       registerTile(TileEntitySoundMuffler.class);
/* 1223: 993 */       registerTile(TileEntityRainMuffler.class);
/* 1224:     */     }
/* 1225: 996 */     if ((transferNodeEnabled) && (transferPipeEnabled))
/* 1226:     */     {
/* 1227: 997 */       if (Loader.isModLoaded("ForgeMultipart"))
/* 1228:     */       {
/* 1229: 998 */         registerBlock(ExtraUtils.transferNode = new BlockTransferNode(), ItemBlockTransferNodeMultiPart.class);
/* 1230: 999 */         registerBlock(ExtraUtils.transferNodeRemote = new BlockRetrievalNode(), ItemBlockTransferNodeMultiPart.class);
/* 1231:1000 */         registerBlock(ExtraUtils.transferPipe = new BlockTransferPipe(0), ItemBlockTransferPipeMultiPart.class);
/* 1232:1001 */         registerBlock(ExtraUtils.transferPipe2 = new BlockTransferPipe(1), ItemBlockTransferPipeMultiPart.class);
/* 1233:     */       }
/* 1234:     */       else
/* 1235:     */       {
/* 1236:1003 */         registerBlock(ExtraUtils.transferNode = new BlockTransferNode(), ItemBlockMetadata.class);
/* 1237:1004 */         registerBlock(ExtraUtils.transferNodeRemote = new BlockRetrievalNode(), ItemBlockMetadata.class);
/* 1238:1005 */         registerBlock(ExtraUtils.transferPipe = new BlockTransferPipe(0), ItemBlockMetadata.class);
/* 1239:1006 */         registerBlock(ExtraUtils.transferPipe2 = new BlockTransferPipe(1), ItemBlockMetadata.class);
/* 1240:     */       }
/* 1241:1009 */       if (Loader.isModLoaded("ForgeMultipart"))
/* 1242:     */       {
/* 1243:1010 */         new RegisterPipeParts().init();
/* 1244:1011 */         new RegisterTransferNodeParts().init();
/* 1245:     */       }
/* 1246:1014 */       registerTile(TileEntityTransferNodeInventory.class);
/* 1247:1015 */       registerTile(TileEntityTransferNodeLiquid.class);
/* 1248:1016 */       registerTile(TileEntityTransferNodeEnergy.class);
/* 1249:1017 */       registerTile(TileEntityTransferNodeHyperEnergy.class);
/* 1250:1018 */       registerTile(TileEntityRetrievalNodeInventory.class);
/* 1251:1019 */       registerTile(TileEntityRetrievalNodeLiquid.class);
/* 1252:1020 */       registerTile(TileEntityFilterPipe.class);
/* 1253:     */     }
/* 1254:1024 */     if (nodeUpgradeEnabled)
/* 1255:     */     {
/* 1256:1025 */       nodeUpgrade = new ItemNodeUpgrade();
/* 1257:1026 */       registerItem(nodeUpgrade);
/* 1258:     */     }
/* 1259:1029 */     if (cursedEarthEnabled) {
/* 1260:1030 */       registerBlock(ExtraUtils.cursedEarth = new BlockCursedEarth(), ItemBlockMetadata.class);
/* 1261:     */     }
/* 1262:1033 */     if (trashCanEnabled)
/* 1263:     */     {
/* 1264:1034 */       registerBlock(ExtraUtils.trashCan = new BlockTrashCan(), ItemBlockMetadata.class);
/* 1265:1035 */       registerTile(TileEntityTrashCan.class);
/* 1266:1036 */       registerTile(TileEntityTrashCanFluids.class);
/* 1267:1037 */       registerTile(TileEntityTrashCanEnergy.class);
/* 1268:     */     }
/* 1269:1041 */     if (spikeEnabled) {
/* 1270:1042 */       registerBlock(ExtraUtils.spike = new BlockSpike(), ItemBlockSpike.class);
/* 1271:     */     }
/* 1272:1044 */     if (spikeDiamondEnabled) {
/* 1273:1045 */       registerBlock(ExtraUtils.spikeDiamond = new BlockSpikeDiamond(), ItemBlockSpike.class);
/* 1274:     */     }
/* 1275:1047 */     if (spikeGoldEnabled) {
/* 1276:1048 */       registerBlock(ExtraUtils.spikeGold = new BlockSpikeGold(), ItemBlockSpike.class);
/* 1277:     */     }
/* 1278:1050 */     if (spikeWoodEnabled) {
/* 1279:1051 */       registerBlock(ExtraUtils.spikeWood = new BlockSpikeWood(), ItemBlockSpike.class);
/* 1280:     */     }
/* 1281:1054 */     if ((spikeEnabled) || (spikeGoldEnabled) || (spikeDiamondEnabled) || (spikeWoodEnabled)) {
/* 1282:1055 */       registerTile(TileEntityEnchantedSpike.class);
/* 1283:     */     }
/* 1284:1058 */     if (enderThermicPumpEnabled)
/* 1285:     */     {
/* 1286:1059 */       registerBlock(ExtraUtils.enderThermicPump = new BlockEnderthermicPump());
/* 1287:1060 */       registerTile(TileEntityEnderThermicLavaPump.class, "enderPump");
/* 1288:     */     }
/* 1289:1063 */     if (enderQuarryEnabled)
/* 1290:     */     {
/* 1291:1064 */       registerBlock(ExtraUtils.enderQuarry = new BlockEnderQuarry());
/* 1292:1065 */       registerBlock(ExtraUtils.enderQuarryUpgrade = new BlockQuarryUpgrades(), ItemBlockMetadata.class);
/* 1293:1066 */       registerTile(TileEntityEnderQuarry.class, "enderQuarry");
/* 1294:     */     }
/* 1295:1069 */     if (enderLilyEnabled)
/* 1296:     */     {
/* 1297:1070 */       registerBlock(ExtraUtils.enderLily = new BlockEnderLily(), ItemBlockEnderLily.class);
/* 1298:1071 */       GameRegistry.registerWorldGenerator(new WorldGenEnderLillies(), 9);
/* 1299:     */     }
/* 1300:1074 */     if (timerBlockEnabled) {
/* 1301:1075 */       registerBlock(ExtraUtils.timerBlock = new BlockTimer());
/* 1302:     */     }
/* 1303:1078 */     if (magnumTorchEnabled)
/* 1304:     */     {
/* 1305:1079 */       if (Loader.isModLoaded("ForgeMultipart")) {
/* 1306:1080 */         registerBlock(ExtraUtils.magnumTorch = new BlockMagnumTorch(), ItemBlockMultiPartMagnumTorch.class);
/* 1307:     */       } else {
/* 1308:1084 */         registerBlock(ExtraUtils.magnumTorch = new BlockMagnumTorch());
/* 1309:     */       }
/* 1310:1086 */       registerTile(TileEntityAntiMobTorch.class);
/* 1311:1088 */       if (Loader.isModLoaded("ForgeMultipart")) {
/* 1312:1089 */         new RegisterBlockPart(magnumTorch, MagnumTorchPart.class, "XU|MagnumTorch").init();
/* 1313:     */       }
/* 1314:     */     }
/* 1315:1094 */     if (drumEnabled)
/* 1316:     */     {
/* 1317:1095 */       registerBlock(ExtraUtils.drum = new BlockDrum(), ItemBlockDrum.class);
/* 1318:1096 */       registerTile(TileEntityDrum.class, "drum");
/* 1319:     */     }
/* 1320:1099 */     if (generatorEnabled)
/* 1321:     */     {
/* 1322:1100 */       registerBlock(ExtraUtils.generator = new BlockGenerator(), ItemBlockGenerator.class);
/* 1323:1101 */       if (generator2Enabled) {
/* 1324:1102 */         registerBlock(ExtraUtils.generator2 = new BlockGenerator(8), ItemBlockGenerator.class);
/* 1325:     */       }
/* 1326:1103 */       if (generator3Enabled) {
/* 1327:1104 */         registerBlock(ExtraUtils.generator3 = new BlockGenerator(64), ItemBlockGenerator.class);
/* 1328:     */       }
/* 1329:1106 */       BlockGenerator.mapTiles();
/* 1330:     */     }
/* 1331:1110 */     if (lawSwordEnabled)
/* 1332:     */     {
/* 1333:1111 */       lawSword = new ItemLawSword();
/* 1334:1112 */       registerItem(lawSword);
/* 1335:     */     }
/* 1336:1115 */     if (goldenLassoEnabled)
/* 1337:     */     {
/* 1338:1116 */       goldenLasso = new ItemGoldenLasso();
/* 1339:1117 */       registerItem(goldenLasso);
/* 1340:     */     }
/* 1341:1120 */     if (divisionSigilEnabled)
/* 1342:     */     {
/* 1343:1121 */       divisionSigil = new ItemDivisionSigil();
/* 1344:1122 */       registerItem(divisionSigil);
/* 1345:     */     }
/* 1346:1125 */     if (unstableIngotEnabled)
/* 1347:     */     {
/* 1348:1126 */       unstableIngot = new ItemUnstableIngot();
/* 1349:1127 */       registerItem(unstableIngot);
/* 1350:1128 */       OreDictionary.registerOre("ingotUnstable", new ItemStack(unstableIngot, 1, 0));
/* 1351:1129 */       OreDictionary.registerOre("ingotUnstable", new ItemStack(unstableIngot, 1, 2));
/* 1352:1130 */       OreDictionary.registerOre("nuggetUnstable", new ItemStack(unstableIngot, 1, 1));
/* 1353:     */     }
/* 1354:1133 */     if ((portalEnabled) && ((endoftimeDimID != 0) || (underdarkDimID != 0)))
/* 1355:     */     {
/* 1356:1134 */       registerBlock(ExtraUtils.portal = new BlockPortal(), ItemBlockMetadata.class);
/* 1357:1135 */       registerTile(TileEntityPortal.class);
/* 1358:1137 */       if (endoftimeDimID != 0)
/* 1359:     */       {
/* 1360:1138 */         if (DimensionManager.isDimensionRegistered(endoftimeDimID)) {
/* 1361:1139 */           ExtraUtilsMod.proxy.throwLoadingError("Invalid id for 'End of Time' dimension. Change endoftimeDimID in config.", new String[0]);
/* 1362:     */         }
/* 1363:1140 */         DimensionManager.registerProviderType(endoftimeDimID, WorldProviderEndOfTime.class, true);
/* 1364:1141 */         DimensionManager.registerDimension(endoftimeDimID, endoftimeDimID);
/* 1365:     */       }
/* 1366:1144 */       if (underdarkDimID != 0)
/* 1367:     */       {
/* 1368:1145 */         DimensionManager.registerProviderType(underdarkDimID, WorldProviderUnderdark.class, false);
/* 1369:1146 */         DimensionManager.registerDimension(underdarkDimID, underdarkDimID);
/* 1370:1147 */         MinecraftForge.EVENT_BUS.register(new EventHandlerUnderdark());
/* 1371:1148 */         MinecraftForge.ORE_GEN_BUS.register(new EventHandlerUnderdark());
/* 1372:1149 */         MinecraftForge.TERRAIN_GEN_BUS.register(new EventHandlerUnderdark());
/* 1373:     */       }
/* 1374:     */     }
/* 1375:1153 */     if (buildersWandEnabled) {
/* 1376:1154 */       registerItem(ExtraUtils.buildersWand = new ItemBuildersWand(9));
/* 1377:     */     }
/* 1378:1157 */     if (precisionShearsEnabled)
/* 1379:     */     {
/* 1380:1158 */       precisionShears = new ItemPrecisionShears();
/* 1381:1159 */       registerItem(precisionShears);
/* 1382:     */     }
/* 1383:1162 */     if (creativeBuildersWandEnabled)
/* 1384:     */     {
/* 1385:1163 */       creativeBuildersWand = new ItemBuildersWand(49).setUnlocalizedName("extrautils:creativebuilderswand");
/* 1386:1164 */       registerItem(creativeBuildersWand);
/* 1387:     */     }
/* 1388:1167 */     if (ethericSwordEnabled)
/* 1389:     */     {
/* 1390:1168 */       ethericSword = new ItemEthericSword();
/* 1391:1169 */       registerItem(ethericSword);
/* 1392:     */     }
/* 1393:1172 */     if (erosionShovelEnabled)
/* 1394:     */     {
/* 1395:1173 */       erosionShovel = new ItemErosionShovel();
/* 1396:1174 */       registerItem(erosionShovel);
/* 1397:     */     }
/* 1398:1177 */     if (destructionPickaxeEnabled)
/* 1399:     */     {
/* 1400:1178 */       destructionPickaxe = new ItemDestructionPickaxe();
/* 1401:1179 */       registerItem(destructionPickaxe);
/* 1402:     */     }
/* 1403:1182 */     if (healingAxeEnabled)
/* 1404:     */     {
/* 1405:1183 */       healingAxe = new ItemHealingAxe();
/* 1406:1184 */       registerItem(healingAxe);
/* 1407:     */     }
/* 1408:1187 */     if (temporalHoeEnabled)
/* 1409:     */     {
/* 1410:1188 */       temporalHoe = new ItemTemporalHoe();
/* 1411:1189 */       registerItem(temporalHoe);
/* 1412:     */     }
/* 1413:1193 */     if (sonarGogglesEnabled)
/* 1414:     */     {
/* 1415:1194 */       sonarGoggles = new ItemSonarGoggles();
/* 1416:1195 */       registerItem(sonarGoggles);
/* 1417:     */     }
/* 1418:1198 */     if (etherealBlockEnabled) {
/* 1419:1199 */       registerBlock(ExtraUtils.etheralBlock = new BlockEtherealStone(), ItemBlockMetadata.class);
/* 1420:     */     }
/* 1421:1202 */     if (wateringCanEnabled)
/* 1422:     */     {
/* 1423:1203 */       wateringCan = new ItemWateringCan();
/* 1424:     */       
/* 1425:1205 */       registerItem(wateringCan);
/* 1426:     */     }
/* 1427:1208 */     if (scannerEnabled)
/* 1428:     */     {
/* 1429:1209 */       scanner = new ItemScanner();
/* 1430:1210 */       registerItem(scanner);
/* 1431:     */     }
/* 1432:1213 */     if (goldenBagEnabled)
/* 1433:     */     {
/* 1434:1214 */       goldenBag = new ItemGoldenBag();
/* 1435:1215 */       registerItem(goldenBag);
/* 1436:     */     }
/* 1437:1218 */     if (bedrockiumEnabled)
/* 1438:     */     {
/* 1439:1219 */       bedrockium = new ItemBedrockiumIngot();
/* 1440:1220 */       registerItem(bedrockium);
/* 1441:     */     }
/* 1442:1224 */     if (microBlocksEnabled)
/* 1443:     */     {
/* 1444:1225 */       microBlocks = new ItemMicroBlock();
/* 1445:1226 */       registerItem(microBlocks);
/* 1446:1227 */       RegisterMicroBlocks.register();
/* 1447:     */     }
/* 1448:     */   }
/* 1449:     */   
/* 1450:     */   public void init(FMLInitializationEvent event)
/* 1451:     */   {
/* 1452:1232 */     NetworkRegistry.INSTANCE.registerGuiHandler(ExtraUtilsMod.instance, new GuiHandler());
/* 1453:1234 */     if (Loader.isModLoaded("ForgeMultipart")) {
/* 1454:1235 */       FMPRegisterPassThroughInterfaces();
/* 1455:     */     }
/* 1456:1238 */     ExtraUtilsMod.proxy.registerEventHandler();
/* 1457:1239 */     ExtraUtilsMod.proxy.registerRenderInformation();
/* 1458:1241 */     if (Loader.isModLoaded("ThermalExpansion")) {
/* 1459:1242 */       TE4IMC.addIntegration();
/* 1460:     */     }
/* 1461:1245 */     if (Loader.isModLoaded("MineFactoryReloaded")) {
/* 1462:1246 */       MFRIntegration.registerMFRIntegration();
/* 1463:     */     }
/* 1464:1250 */     for (ILoading loader : this.loaders) {
/* 1465:1251 */       loader.init();
/* 1466:     */     }
/* 1467:1254 */     EE3Integration.finalRegister();
/* 1468:     */   }
/* 1469:     */   
/* 1470:     */   private void FMPRegisterPassThroughInterfaces()
/* 1471:     */   {
/* 1472:1258 */     if (Loader.isModLoaded("ForgeMultipart"))
/* 1473:     */     {
/* 1474:1259 */       RegisterMicroMaterials.registerBlock(cobblestoneCompr, 0, 16);
/* 1475:1260 */       RegisterMicroMaterials.registerBlock(enderThermicPump);
/* 1476:1261 */       RegisterMicroMaterials.registerBlock(tradingPost);
/* 1477:1262 */       RegisterMicroMaterials.registerConnectedTexture(etheralBlock, 0);
/* 1478:1263 */       RegisterMicroMaterials.registerConnectedTexture(etheralBlock, 1);
/* 1479:1264 */       RegisterMicroMaterials.registerConnectedTexture(etheralBlock, 2);
/* 1480:1265 */       RegisterMicroMaterials.registerConnectedTexture(etheralBlock, 3);
/* 1481:1266 */       RegisterMicroMaterials.registerConnectedTexture(etheralBlock, 4);
/* 1482:1267 */       RegisterMicroMaterials.registerConnectedTexture(etheralBlock, 5);
/* 1483:     */       
/* 1484:1269 */       RegisterMicroMaterials.registerFullBright(greenScreen);
/* 1485:1272 */       for (BlockColor col : colorblocks) {
/* 1486:1273 */         RegisterMicroMaterials.registerColorBlock(col);
/* 1487:     */       }
/* 1488:1275 */       for (int i = 0; i < 16; i++)
/* 1489:     */       {
/* 1490:1276 */         if ((decorative1 != null) && (decorative1.name[i] != null)) {
/* 1491:1277 */           RegisterMicroMaterials.registerConnectedTexture(decorative1, i);
/* 1492:     */         }
/* 1493:1279 */         if ((decorative2 != null) && (decorative2.name[i] != null)) {
/* 1494:1280 */           RegisterMicroMaterials.registerConnectedTexture(decorative2, i);
/* 1495:     */         }
/* 1496:     */       }
/* 1497:     */     }
/* 1498:1286 */     MultipartGenerator.registerPassThroughInterface("com.rwtema.extrautils.tileentity.IAntiMobTorch", false, true);
/* 1499:1287 */     MultipartGenerator.registerPassThroughInterface("com.rwtema.extrautils.tileentity.transfernodes.pipes.IPipe");
/* 1500:1288 */     MultipartGenerator.registerPassThroughInterface("com.rwtema.extrautils.tileentity.transfernodes.pipes.IPipeCosmetic");
/* 1501:1289 */     MultipartGenerator.registerPassThroughInterface("com.rwtema.extrautils.tileentity.transfernodes.pipes.IFilterPipe");
/* 1502:1290 */     MultipartGenerator.registerPassThroughInterface("com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INode");
/* 1503:1291 */     MultipartGenerator.registerPassThroughInterface("com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeInventory");
/* 1504:1292 */     MultipartGenerator.registerPassThroughInterface("com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeLiquid");
/* 1505:1293 */     MultipartGenerator.registerPassThroughInterface("com.rwtema.extrautils.tileentity.transfernodes.nodebuffer.INodeEnergy");
/* 1506:     */     
/* 1507:1295 */     MultipartGenerator.registerPassThroughInterface("cofh.api.energy.IEnergyHandler");
/* 1508:     */   }
/* 1509:     */   
/* 1510:     */   public void postInit(FMLPostInitializationEvent evt)
/* 1511:     */   {
/* 1512:1300 */     ForgeChunkManager.setForcedChunkLoadingCallback(ExtraUtilsMod.instance, new ChunkloadCallback());
/* 1513:     */     
/* 1514:1302 */     CommandTPSTimer.init();
/* 1515:1304 */     if ((underdarkDimID != 0) && (portalEnabled)) {
/* 1516:1305 */       FMLCommonHandler.instance().bus().register(new DarknessTickHandler());
/* 1517:     */     }
/* 1518:1308 */     if ((divisionSigilEnabled) && (!disableDivisionSigilInChests))
/* 1519:     */     {
/* 1520:1309 */       addSigil("dungeonChest", 0.01D);
/* 1521:1310 */       addSigil("mineshaftCorridor", 0.001D);
/* 1522:1311 */       addSigil("pyramidDesertyChest", 0.02D);
/* 1523:1312 */       addSigil("pyramidJungleChest", 0.05D);
/* 1524:1313 */       addSigil("strongholdCrossing", 0.01D);
/* 1525:1314 */       addSigil("strongholdCorridor", 0.01D);
/* 1526:     */     }
/* 1527:1318 */     if ((!disableEnderLiliesInDungeons) && (enderLily != null)) {
/* 1528:1319 */       addDungeonItem(new ItemStack(enderLily), 1, 5, "dungeonChest", 0.03D);
/* 1529:     */     }
/* 1530:1323 */     ExtraUtilsMod.proxy.postInit();
/* 1531:1325 */     if (enderQuarryEnabled) {
/* 1532:1326 */       BlockBreakingRegistry.instance.setupBreaking();
/* 1533:     */     }
/* 1534:1329 */     DispenserStuff.registerItems();
/* 1535:1331 */     if (generatorEnabled) {
/* 1536:1332 */       TileEntityGeneratorPotion.genPotionLevels();
/* 1537:     */     }
/* 1538:1335 */     if (transferPipeEnabled) {
/* 1539:1336 */       TNHelper.initBlocks();
/* 1540:     */     }
/* 1541:1340 */     if (Loader.isModLoaded("Thaumcraft")) {
/* 1542:1341 */       ThaumcraftHelper.registerItems();
/* 1543:     */     }
/* 1544:1344 */     for (ILoading loader : this.loaders) {
/* 1545:1345 */       loader.postInit();
/* 1546:     */     }
/* 1547:     */   }
/* 1548:     */   
/* 1549:     */   private String standardizeName(String name)
/* 1550:     */   {
/* 1551:1351 */     if (name.endsWith("enabled")) {
/* 1552:1352 */       name = name.replaceAll("enabled", "Enabled");
/* 1553:     */     }
/* 1554:1353 */     if (!name.endsWith("Enabled")) {
/* 1555:1354 */       name = name + "Enabled";
/* 1556:     */     }
/* 1557:1356 */     name = name.substring(0, 1).toUpperCase() + name.substring(1);
/* 1558:1357 */     return name;
/* 1559:     */   }
/* 1560:     */   
/* 1561:     */   private boolean getItem(Configuration config, String string)
/* 1562:     */   {
/* 1563:1361 */     return config.get("Items", standardizeName(string), true).getBoolean(true);
/* 1564:     */   }
/* 1565:     */   
/* 1566:     */   private boolean getBlock(Configuration config, String string)
/* 1567:     */   {
/* 1568:1365 */     return config.get("Blocks", standardizeName(string), true).getBoolean(true);
/* 1569:     */   }
/* 1570:     */   
/* 1571:     */   public void specialInit()
/* 1572:     */   {
/* 1573:1369 */     if ((qed != null) && ((!qedList.isEmpty()) || (!disableQEDIngotSmeltRecipes))) {
/* 1574:1370 */       EnderConstructorRecipesHandler.postInit();
/* 1575:     */     }
/* 1576:     */   }
/* 1577:     */   
/* 1578:     */   public void serverStarting(FMLServerStartingEvent event)
/* 1579:     */   {
/* 1580:1374 */     event.registerServerCommand(new CommandKillEntities("items", EntityItem.class, true));
/* 1581:1375 */     event.registerServerCommand(new CommandKillEntities("mobs", EntityMob.class, true));
/* 1582:1376 */     event.registerServerCommand(new CommandKillEntities("living", EntityLiving.class, true));
/* 1583:1377 */     event.registerServerCommand(new CommandKillEntities("xp", EntityXPOrb.class, true));
/* 1584:     */   }
/* 1585:     */   
/* 1586:     */   public void registerRecipes()
/* 1587:     */   {
/* 1588:1382 */     registerRecipe(ShapedOreRecipeAlwaysLast.class, RecipeSorter.Category.SHAPED, "after:forge:shapelessore");
/* 1589:1383 */     registerRecipe(ShapelessOreRecipeAlwaysLast.class, RecipeSorter.Category.SHAPELESS, "after:forge:shapelessore");
/* 1590:     */     
/* 1591:1385 */     String unstableIngot = "ingotUnstable";
/* 1592:1387 */     if (fullChestEnabled) {
/* 1593:1388 */       addRecipe(new ShapedOreRecipe(new ItemStack(fullChest), new Object[] { "sss", "sCs", "sss", Character.valueOf('s'), "stickWood", Character.valueOf('C'), Blocks.chest }));
/* 1594:     */     }
/* 1595:1391 */     if (miniChestEnabled)
/* 1596:     */     {
/* 1597:1392 */       addRecipe(new ShapelessOreRecipeAlwaysLast(new ItemStack(miniChest, 9), new Object[] { Blocks.chest }));
/* 1598:1393 */       if (fullChestEnabled) {
/* 1599:1394 */         addRecipe(new ShapelessOreRecipe(new ItemStack(miniChest, 9), new Object[] { fullChest }));
/* 1600:     */       }
/* 1601:1396 */       addShapedRecipe(new ItemStack(Blocks.chest), new Object[] { "ccc", "ccc", "ccc", Character.valueOf('c'), miniChest });
/* 1602:     */     }
/* 1603:1399 */     if (enderCollectorEnabled) {
/* 1604:1400 */       addRecipe(new ItemStack(enderCollector), new Object[] { "eEe", " E ", "OOO", Character.valueOf('e'), Items.ender_pearl, Character.valueOf('E'), decorative1 == null ? Blocks.obsidian : "blockEnderObsidian", Character.valueOf('O'), Blocks.obsidian });
/* 1605:     */     }
/* 1606:1403 */     if (gloveEnabled) {
/* 1607:1404 */       addRecipe(new RecipeGlove(glove));
/* 1608:     */     }
/* 1609:1407 */     if (heatingCoilEnabled) {
/* 1610:1408 */       addRecipe(new ItemStack(heatingCoil), new Object[] { "III", "I I", "IRI", Character.valueOf('I'), Blocks.heavy_weighted_pressure_plate, Character.valueOf('R'), Items.redstone });
/* 1611:     */     }
/* 1612:1411 */     if (microBlocksEnabled)
/* 1613:     */     {
/* 1614:1412 */       addRecipe(new RecipeMicroBlocks(3, 3, new Object[] { Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), new ItemStack(Blocks.wool, 1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1) }, new ItemStack(microBlocks, 8)));
/* 1615:     */       
/* 1616:     */ 
/* 1617:1415 */       addRecipe(new RecipeMicroBlocks(3, 3, new Object[] { Integer.valueOf(770), Integer.valueOf(772), Integer.valueOf(770), Integer.valueOf(770), Integer.valueOf(772), Integer.valueOf(770), null, Integer.valueOf(772), null }, new ItemStack(microBlocks, 2, 1)));
/* 1618:     */       
/* 1619:     */ 
/* 1620:     */ 
/* 1621:1419 */       addRecipe(new RecipeMicroBlocks(3, 3, new Object[] { null, Integer.valueOf(0), null, Integer.valueOf(4), Integer.valueOf(0), Integer.valueOf(4), Integer.valueOf(4), Integer.valueOf(0), Integer.valueOf(4) }, new ItemStack(microBlocks, 5, 2)));
/* 1622:     */       
/* 1623:     */ 
/* 1624:     */ 
/* 1625:     */ 
/* 1626:     */ 
/* 1627:1425 */       addRecipe(new RecipeMicroBlocks(3, 3, new Object[] { Integer.valueOf(1), Integer.valueOf(4), Integer.valueOf(1), Integer.valueOf(4), new ItemStack(decorative1, 1, 5), Integer.valueOf(4), Integer.valueOf(1), Integer.valueOf(4), Integer.valueOf(1) }, new ItemStack(microBlocks, 1, 3)));
/* 1628:     */     }
/* 1629:1432 */     if ((bedrockiumBlockEnabled) && 
/* 1630:1433 */       (bedrockiumEnabled))
/* 1631:     */     {
/* 1632:1434 */       addRecipe(new ItemStack(bedrockiumBlock), new Object[] { "KKK", "KKK", "KKK", Character.valueOf('K'), bedrockium });
/* 1633:1435 */       addRecipe(new ItemStack(bedrockium, 9), new Object[] { "K", Character.valueOf('K'), bedrockiumBlock });
/* 1634:1436 */       if (cobblestoneComprEnabled) {
/* 1635:1437 */         addSmelting(new ItemStack(cobblestoneCompr, 1, 7), new ItemStack(bedrockiumBlock), 0);
/* 1636:     */       }
/* 1637:     */     }
/* 1638:1441 */     if ((soulEnabled) && (ethericSwordEnabled)) {
/* 1639:1442 */       addRecipe(new RecipeSoul());
/* 1640:     */     }
/* 1641:1445 */     if ((angelBlockEnabled) && 
/* 1642:1446 */       (!disableAngelBlockRecipe)) {
/* 1643:1447 */       addRecipe(new ItemStack(angelBlock, 1), new Object[] { " H ", "WOW", Character.valueOf('H'), Items.gold_ingot, Character.valueOf('W'), Items.feather, Character.valueOf('O'), Blocks.obsidian });
/* 1644:     */     }
/* 1645:1450 */     if (BUDBlockEnabled)
/* 1646:     */     {
/* 1647:1451 */       if (!disableBUDBlockRecipe) {
/* 1648:1452 */         addRecipe(new ItemStack(BUDBlock, 1, 0), new Object[] { "SRS", "SPS", "STS", Character.valueOf('R'), Items.redstone, Character.valueOf('S'), Blocks.stone, Character.valueOf('P'), Blocks.sticky_piston, Character.valueOf('T'), Blocks.redstone_torch });
/* 1649:     */       }
/* 1650:1455 */       if (!disableAdvBUDBlockRecipe) {
/* 1651:1456 */         addRecipe(new ItemStack(BUDBlock, 1, 3), new Object[] { "SRS", "RBR", "SRS", Character.valueOf('R'), Blocks.redstone_block, Character.valueOf('S'), Blocks.stone, Character.valueOf('B'), new ItemStack(BUDBlock, 1, 0) });
/* 1652:     */       }
/* 1653:     */     }
/* 1654:1460 */     if ((chandelierEnabled) && 
/* 1655:1461 */       (!disableChandelierRecipe)) {
/* 1656:1462 */       addRecipe(new ItemStack(chandelier, 1), new Object[] { "GDG", "TTT", " T ", Character.valueOf('G'), Items.gold_ingot, Character.valueOf('D'), Items.diamond, Character.valueOf('T'), Blocks.torch });
/* 1657:     */     }
/* 1658:1465 */     if ((decorative1Enabled) && (decorative2Enabled))
/* 1659:     */     {
/* 1660:1466 */       addRecipe(new ItemStack(decorative1, 9, 0), new Object[] { "SBS", "BBB", "SBS", Character.valueOf('S'), Blocks.stone, Character.valueOf('B'), new ItemStack(Blocks.stonebrick, 1, 0) });
/* 1661:1467 */       addRecipe(new ItemStack(decorative1, 4, 1), new Object[] { " O ", "OEO", " O ", Character.valueOf('O'), Blocks.obsidian, Character.valueOf('E'), Items.ender_pearl });
/* 1662:1468 */       addSmelting(new ItemStack(Blocks.quartz_block), new ItemStack(decorative1, 1, 2), 0);
/* 1663:1469 */       addRecipe(new ItemStack(decorative1, 5, 3), new Object[] { " I ", "ISI", " I ", Character.valueOf('I'), Blocks.ice, Character.valueOf('S'), Blocks.stone });
/* 1664:1470 */       addRecipe(new ItemStack(decorative1, 4, 4), new Object[] { "BB", "BB", Character.valueOf('B'), new ItemStack(decorative1, 1, 0) });
/* 1665:1471 */       addRecipe(new ItemStack(decorative1, 4, 7), new Object[] { "BB", "BB", Character.valueOf('B'), new ItemStack(decorative1, 1, 4) });
/* 1666:1472 */       addRecipe(new ItemStack(decorative1, 1, 6), new Object[] { "GG", "GG", Character.valueOf('G'), Blocks.gravel });
/* 1667:1473 */       addRecipe(new RecipeMagicalWood());
/* 1668:1474 */       addRecipe(new ItemStack(decorative1, 8, 10), new Object[] { "SGS", "GGG", "SGS", Character.valueOf('S'), new ItemStack(Blocks.stone_slab, 1, 5), Character.valueOf('G'), new ItemStack(decorative1, 1, 6) });
/* 1669:1475 */       addRecipe(new ItemStack(decorative1, 1, 11), new Object[] { "GBG", "BDB", "GBG", Character.valueOf('G'), new ItemStack(decorative1, 1, 8), Character.valueOf('D'), Items.ender_eye, Character.valueOf('B'), new ItemStack(decorative1, 1, 1) });
/* 1670:     */       
/* 1671:1477 */       addRecipe(new ItemStack(decorative1, 1, 12), new Object[] { "dEd", "EDE", "dEd", Character.valueOf('D'), new ItemStack(decorative1, 1, 1), Character.valueOf('E'), Items.diamond, Character.valueOf('d'), new ItemStack(decorative1, 1, 2) });
/* 1672:     */       
/* 1673:1479 */       addRecipe(new ShapelessOreRecipe(new ItemStack(decorative1, 4, 13), new Object[] { Blocks.end_stone, "sandstone", "sandstone", Blocks.end_stone }));
/* 1674:1480 */       addRecipe(new ShapelessOreRecipe(new ItemStack(decorative1, 4, 14), new Object[] { "dyeMagenta", "dyePurple", Blocks.stone, Blocks.stone, Blocks.stone, Blocks.stone, Items.ender_pearl }));
/* 1675:     */       
/* 1676:1482 */       addShapedRecipe(new ItemStack(decorative1, 4, 9), new Object[] { "SG", "GS", Character.valueOf('G'), Blocks.glass, Character.valueOf('S'), Blocks.sand });
/* 1677:     */       
/* 1678:1484 */       addSmelting(new ItemStack(decorative1, 1, 9), new ItemStack(decorative2, 1, 0), 0);
/* 1679:1485 */       ItemStack glass = new ItemStack(decorative2, 1, 0);
/* 1680:1486 */       addRecipe(new ItemStack(decorative2, 8, 1), new Object[] { "GGG", "G G", "GGG", Character.valueOf('G'), glass });
/* 1681:1487 */       addRecipe(new ItemStack(decorative2, 4, 2), new Object[] { "GG", "GG", Character.valueOf('G'), glass });
/* 1682:1488 */       addRecipe(new ItemStack(decorative2, 1, 3), new Object[] { "GP", Character.valueOf('G'), glass, Character.valueOf('P'), Items.gunpowder });
/* 1683:1489 */       addRecipe(new ItemStack(decorative2, 1, 4), new Object[] { "ggg", "gGg", "ggg", Character.valueOf('G'), glass, Character.valueOf('g'), Items.gold_nugget });
/* 1684:1490 */       addRecipe(new ItemStack(decorative2, 4, 5), new Object[] { "GOG", "O O", "GOG", Character.valueOf('G'), glass, Character.valueOf('O'), Blocks.obsidian });
/* 1685:1491 */       addRecipe(new ItemStack(decorative2, 5, 6), new Object[] { " G ", "GGG", " G ", Character.valueOf('G'), glass });
/* 1686:1492 */       addRecipe(new ItemStack(decorative2, 1, 7), new Object[] { " g ", "gGg", " g ", Character.valueOf('G'), glass, Character.valueOf('g'), Items.glowstone_dust });
/* 1687:1493 */       addRecipe(new ShapedOreRecipe(new ItemStack(decorative2, 6, 8), new Object[] { "GpG", "GGG", " G ", Character.valueOf('G'), glass, Character.valueOf('p'), "dyePink" }));
/* 1688:1494 */       addRecipe(new ItemStack(decorative2, 1, 9), new Object[] { "G", Character.valueOf('G'), glass });
/* 1689:1495 */       addRecipe(new ShapedOreRecipe(new ItemStack(decorative2, 5, 10), new Object[] { "GCG", "CGC", "GCG", Character.valueOf('G'), glass, Character.valueOf('C'), curtain == null ? Items.coal : curtain, Character.valueOf('I'), "dyeBlack" }));
/* 1690:1496 */       addRecipe(new ItemStack(decorative2, 4, 11), new Object[] { "GOG", "O O", "GOG", Character.valueOf('G'), new ItemStack(decorative2, 1, 10), Character.valueOf('O'), Blocks.obsidian });
/* 1691:     */     }
/* 1692:1500 */     if ((curtainEnabled) && 
/* 1693:1501 */       (!disableCurtainRecipe)) {
/* 1694:1502 */       addRecipe(new ItemStack(curtain, 12), new Object[] { "WW", "WW", "WW", Character.valueOf('W'), Blocks.wool });
/* 1695:     */     }
/* 1696:1505 */     if ((colorBlockDataEnabled) && (paintBrushEnabled))
/* 1697:     */     {
/* 1698:1506 */       if (!disablePaintbrushRecipe) {
/* 1699:1507 */         addRecipe(new ItemStack(paintBrush, 1), new Object[] { "S  ", " W ", "  W", Character.valueOf('S'), Items.string, Character.valueOf('W'), Items.stick });
/* 1700:     */       }
/* 1701:1509 */       for (BlockColor b : colorblocks) {
/* 1702:1511 */         for (int i = 0; i < 16; i++)
/* 1703:     */         {
/* 1704:1512 */           if (b.oreName != null)
/* 1705:     */           {
/* 1706:1513 */             OreDictionary.registerOre(b.oreName, new ItemStack(b, 1, i));
/* 1707:1514 */             OreDictionary.registerOre(b.oreName + com.rwtema.extrautils.helper.XUHelper.dyes[i].substring(3), new ItemStack(b, 1, i));
/* 1708:     */           }
/* 1709:1517 */           if (!disableColoredBlocksRecipes) {
/* 1710:1518 */             if (b.customRecipe == null)
/* 1711:     */             {
/* 1712:1519 */               addRecipe(new ShapedOreRecipe(new ItemStack(b, 7, BlockColored.func_150032_b(i)), new Object[] { "SSS", "SDS", "SPS", Character.valueOf('S'), b.baseBlock, Character.valueOf('D'), com.rwtema.extrautils.helper.XUHelper.dyes[i], Character.valueOf('P'), paintBrush }));
/* 1713:     */             }
/* 1714:     */             else
/* 1715:     */             {
/* 1716:1522 */               Object[] tempRecipe = new Object[b.customRecipe.length];
/* 1717:1523 */               for (int j = 0; j < b.customRecipe.length; j++) {
/* 1718:1524 */                 if ("dye".equals(b.customRecipe[j])) {
/* 1719:1525 */                   tempRecipe[j] = com.rwtema.extrautils.helper.XUHelper.dyes[i];
/* 1720:     */                 } else {
/* 1721:1527 */                   tempRecipe[j] = b.customRecipe[j];
/* 1722:     */                 }
/* 1723:     */               }
/* 1724:1530 */               addRecipe(new ShapedOreRecipe(new ItemStack(b, b.customRecipeNo, BlockColored.func_150032_b(i)), tempRecipe));
/* 1725:     */             }
/* 1726:     */           }
/* 1727:     */         }
/* 1728:     */       }
/* 1729:     */     }
/* 1730:1538 */     if ((cobblestoneComprEnabled) && 
/* 1731:1539 */       (!disableCompressedCobblestoneRecipe)) {
/* 1732:1540 */       for (int i = 0; i < 16; i++) {
/* 1733:1541 */         if (BlockCobblestoneCompressed.getCompr(i) == 0)
/* 1734:     */         {
/* 1735:1542 */           String s = BlockCobblestoneCompressed.getOreDictName(i).toLowerCase();
/* 1736:1543 */           OreDictionary.registerOre(s, BlockCobblestoneCompressed.getBlock(i));
/* 1737:1544 */           addRecipe(new ShapedOreRecipe(new ItemStack(cobblestoneCompr, 1, i), new Object[] { "XXX", "XXX", "XXX", Character.valueOf('X'), s }));
/* 1738:1545 */           addRecipe(new ItemStack(BlockCobblestoneCompressed.getBlock(i), 9), new Object[] { "X", Character.valueOf('X'), new ItemStack(cobblestoneCompr, 1, i) });
/* 1739:     */         }
/* 1740:     */         else
/* 1741:     */         {
/* 1742:1547 */           addRecipe(new ItemStack(cobblestoneCompr, 1, i), new Object[] { "XXX", "XXX", "XXX", Character.valueOf('X'), new ItemStack(cobblestoneCompr, 1, i - 1) });
/* 1743:1548 */           addRecipe(new ItemStack(cobblestoneCompr, 9, i - 1), new Object[] { "X", Character.valueOf('X'), new ItemStack(cobblestoneCompr, 1, i) });
/* 1744:     */         }
/* 1745:     */       }
/* 1746:     */     }
/* 1747:1553 */     if ((conveyorEnabled) && 
/* 1748:1554 */       (!disableConveyorRecipe)) {
/* 1749:1555 */       addRecipe(new ItemStack(conveyor, 8), new Object[] { "TTT", "IRI", "TTT", Character.valueOf('T'), Blocks.rail, Character.valueOf('I'), Items.iron_ingot, Character.valueOf('R'), Items.redstone });
/* 1750:     */     }
/* 1751:1558 */     if (filingCabinetEnabled)
/* 1752:     */     {
/* 1753:1559 */       if (!disableFilingCabinet) {
/* 1754:1560 */         addRecipe(new ItemStack(filingCabinet, 1, 0), new Object[] { "ICI", "ICI", "ICI", Character.valueOf('I'), Items.iron_ingot, Character.valueOf('C'), Blocks.chest });
/* 1755:     */       }
/* 1756:1563 */       if ((!disableAdvFilingCabinet) && (decorative1Enabled)) {
/* 1757:1564 */         addRecipe(new ItemStack(filingCabinet, 1, 1), new Object[] { "ICI", "ICI", "ICI", Character.valueOf('I'), new ItemStack(decorative1, 1, 8), Character.valueOf('C'), new ItemStack(filingCabinet, 1, 0) });
/* 1758:     */       }
/* 1759:     */     }
/* 1760:1568 */     if ((peacefultableEnabled) && 
/* 1761:1569 */       (!disablePeacefulTableRecipe)) {
/* 1762:1570 */       addRecipe(new ItemStack(peacefultable, 1), new Object[] { "EWE", "WDW", "EWE", Character.valueOf('E'), Items.emerald, Character.valueOf('D'), Items.ender_pearl, Character.valueOf('W'), Blocks.planks });
/* 1763:     */     }
/* 1764:1572 */     if ((tradingPostEnabled) && 
/* 1765:1573 */       (!disableTradingPostRecipe)) {
/* 1766:1574 */       addRecipe(new ItemStack(tradingPost, 1), new Object[] { "WEW", "WJW", "WWW", Character.valueOf('W'), Blocks.planks, Character.valueOf('E'), Blocks.emerald_block, Character.valueOf('J'), Blocks.jukebox });
/* 1767:     */     }
/* 1768:1578 */     if (soundMufflerEnabled)
/* 1769:     */     {
/* 1770:1579 */       if (!disableSoundMufflerRecipe) {
/* 1771:1580 */         addRecipe(new ItemStack(soundMuffler, 1, 0), new Object[] { "WWW", "WNW", "WWW", Character.valueOf('W'), Blocks.wool, Character.valueOf('N'), Blocks.noteblock });
/* 1772:     */       }
/* 1773:1582 */       if (!disableRainMufflerRecipe) {
/* 1774:1583 */         addRecipe(new ItemStack(soundMuffler, 1, 1), new Object[] { "WWW", "WBW", "WWW", Character.valueOf('W'), Blocks.wool, Character.valueOf('B'), Items.water_bucket });
/* 1775:     */       }
/* 1776:     */     }
/* 1777:1585 */     if ((transferNodeEnabled) && (transferPipeEnabled) && (transferNodeRemoteEnabled))
/* 1778:     */     {
/* 1779:1586 */       if (!disableTransferPipeRecipe) {
/* 1780:1587 */         addRecipe(new ItemStack(transferPipe, 8), new Object[] { "SSS", "GRG", "SSS", Character.valueOf('S'), new ItemStack(Blocks.stone_slab, 6, 0), Character.valueOf('G'), Blocks.glass, Character.valueOf('R'), Items.redstone });
/* 1781:     */       }
/* 1782:1589 */       if (!disableSortingPipeRecipe) {
/* 1783:1590 */         addRecipe(new ItemStack(transferPipe, 2, 8), new Object[] { "SSS", "G#G", "SSS", Character.valueOf('S'), new ItemStack(Blocks.stone_slab, 6, 0), Character.valueOf('G'), Blocks.glass, Character.valueOf('#'), Items.gold_ingot });
/* 1784:     */       }
/* 1785:1592 */       if (!disableFilterPipeRecipe)
/* 1786:     */       {
/* 1787:1593 */         ArrayList<ItemStack> dyes = new ArrayList();
/* 1788:1595 */         for (String s : new String[] { "dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite" }) {
/* 1789:1596 */           dyes.addAll(OreDictionary.getOres(s));
/* 1790:     */         }
/* 1791:1598 */         addRecipe(new RecipeCustomOres(new ItemStack(transferPipe, 5, 9), new ItemStack(Items.stick), dyes, new Object[] { "sPs", "PPP", "sPs", Character.valueOf('s'), new ItemStack(Items.stick), Character.valueOf('P'), new ItemStack(transferPipe, 1, 0) }));
/* 1792:     */       }
/* 1793:1603 */       if (!disableRationingPipeRecipe)
/* 1794:     */       {
/* 1795:1604 */         addRecipe(new ShapedOreRecipe(new ItemStack(transferPipe, 2, 10), new Object[] { "SSS", "GBG", "SSS", Character.valueOf('B'), new ItemStack(Items.dye, 1, 4), Character.valueOf('S'), new ItemStack(Blocks.stone_slab, 1, 0), Character.valueOf('G'), Blocks.glass }));
/* 1796:     */         
/* 1797:     */ 
/* 1798:1607 */         addRecipe(new ShapedOreRecipe(new ItemStack(transferPipe2, 2, 0), new Object[] { "SSS", "GBG", "SSS", Character.valueOf('B'), new ItemStack(Items.dye, 1, 4), Character.valueOf('S'), new ItemStack(Blocks.stone_button, 1, 0), Character.valueOf('G'), Blocks.glass }));
/* 1799:     */       }
/* 1800:1611 */       if (!disableEnergyPipeRecipe) {
/* 1801:1612 */         addRecipe(new ShapedOreRecipe(new ItemStack(transferPipe, 8, 11), new Object[] { "SSS", "RRR", "SSS", Character.valueOf('R'), Items.redstone, Character.valueOf('S'), new ItemStack(Blocks.stone_slab, 6, 0) }));
/* 1802:     */       }
/* 1803:1615 */       if (!disableCrossoverPipeRecipe) {
/* 1804:1616 */         addRecipe(new ShapedOreRecipe(new ItemStack(transferPipe, 1, 12), new Object[] { " P ", "PBP", " P ", Character.valueOf('P'), new ItemStack(transferPipe, 1, 0), Character.valueOf('B'), Items.redstone }));
/* 1805:     */       }
/* 1806:1619 */       if (!disableModSortingPipesRecipe) {
/* 1807:1620 */         addRecipe(new ShapedOreRecipe(new ItemStack(transferPipe, 1, 13), new Object[] { "BPB", Character.valueOf('P'), new ItemStack(transferPipe, 1, 8), Character.valueOf('B'), Items.redstone }));
/* 1808:     */       }
/* 1809:1623 */       if (!disableEnergyExtractionPipeRecipe) {
/* 1810:1624 */         addRecipe(new ShapedOreRecipe(new ItemStack(transferPipe, 1, 14), new Object[] { "B B", "BPB", " P ", Character.valueOf('P'), new ItemStack(transferPipe, 1, 11), Character.valueOf('B'), Items.gold_ingot }));
/* 1811:     */       }
/* 1812:1627 */       if (!disableTransferNodeRecipe)
/* 1813:     */       {
/* 1814:1628 */         addRecipe(new ItemStack(transferNode, 1, 0), new Object[] { " P ", "RER", "SHS", Character.valueOf('S'), Blocks.stone, Character.valueOf('E'), Blocks.redstone_block, Character.valueOf('H'), Blocks.chest, Character.valueOf('R'), Items.redstone, Character.valueOf('P'), transferPipe });
/* 1815:     */         
/* 1816:1630 */         addRecipe(new ItemStack(transferNode, 4, 0), new Object[] { " P ", "RER", "SHS", Character.valueOf('S'), Blocks.stone, Character.valueOf('E'), Items.ender_pearl, Character.valueOf('H'), Blocks.chest, Character.valueOf('R'), Items.redstone, Character.valueOf('P'), transferPipe });
/* 1817:     */       }
/* 1818:1634 */       if (!disableTransferNodeLiquidRecipe)
/* 1819:     */       {
/* 1820:1635 */         addRecipe(new ItemStack(transferNode, 1, 6), new Object[] { " P ", "LEL", "IHI", Character.valueOf('I'), Items.iron_ingot, Character.valueOf('E'), Blocks.redstone_block, Character.valueOf('H'), Items.bucket, Character.valueOf('L'), new ItemStack(Items.dye, 1, 4), Character.valueOf('P'), transferPipe });
/* 1821:     */         
/* 1822:1637 */         addRecipe(new ItemStack(transferNode, 4, 6), new Object[] { " P ", "LEL", "IHI", Character.valueOf('I'), Items.iron_ingot, Character.valueOf('E'), Items.ender_pearl, Character.valueOf('H'), Items.bucket, Character.valueOf('L'), new ItemStack(Items.dye, 1, 4), Character.valueOf('P'), transferPipe });
/* 1823:     */       }
/* 1824:1644 */       if (!disableTransferNodeRemoteRecipe) {
/* 1825:1645 */         addRecipe(new ItemStack(transferNodeRemote, 1, 0), new Object[] { " E ", "TeT", " E ", Character.valueOf('e'), Items.emerald, Character.valueOf('E'), Items.ender_pearl, Character.valueOf('T'), new ItemStack(transferNode, 2, 0) });
/* 1826:     */       }
/* 1827:1648 */       if (!disableTransferNodeLiquidRemoteRecipe) {
/* 1828:1649 */         addRecipe(new ItemStack(transferNodeRemote, 1, 6), new Object[] { " E ", "TeT", " E ", Character.valueOf('e'), Items.diamond, Character.valueOf('E'), Items.ender_pearl, Character.valueOf('T'), new ItemStack(transferNode, 2, 6) });
/* 1829:     */       }
/* 1830:1652 */       if (!disableTransferNodeEnergyRecipe)
/* 1831:     */       {
/* 1832:1653 */         addRecipe(new ItemStack(transferNode, 1, 12), new Object[] { "GNG", "NRN", "GNG", Character.valueOf('N'), new ItemStack(transferNode, 1, 0), Character.valueOf('G'), Items.gold_ingot, Character.valueOf('R'), nodeUpgrade == null ? Blocks.redstone_block : new ItemStack(nodeUpgrade, 1, 8) });
/* 1833:1654 */         if (bedrockiumEnabled) {
/* 1834:1655 */           addRecipe(new ItemStack(transferNode, 1, 13), new Object[] { " N ", "NBN", " N ", Character.valueOf('N'), new ItemStack(transferNode, 1, 12), Character.valueOf('B'), bedrockium });
/* 1835:     */         }
/* 1836:     */       }
/* 1837:     */     }
/* 1838:1660 */     if (nodeUpgradeEnabled)
/* 1839:     */     {
/* 1840:1661 */       if (!disableFilterRecipe)
/* 1841:     */       {
/* 1842:1662 */         addRecipe(new ItemStack(nodeUpgrade, 1, 1), new Object[] { "RSR", "STS", "RSR", Character.valueOf('R'), Items.redstone, Character.valueOf('S'), Items.stick, Character.valueOf('T'), Items.string });
/* 1843:1663 */         addRecipe(new RecipeFilterInvert(Item.getItemFromBlock(Blocks.redstone_torch), "Inverted", new ItemStack(nodeUpgrade, 1, 1)));
/* 1844:1664 */         addRecipe(new RecipeFilterInvert(Item.getItemFromBlock(Blocks.wool), "FuzzyNBT", new ItemStack(nodeUpgrade, 1, 1)));
/* 1845:1665 */         addRecipe(new RecipeFilterInvert(Items.stick, "FuzzyMeta", new ItemStack(nodeUpgrade, 1, 1)));
/* 1846:     */         
/* 1847:1667 */         addRecipe(new ItemStack(nodeUpgrade, 1, 10), new Object[] { "L L", " T ", "L L", Character.valueOf('L'), "gemLapis", Character.valueOf('T'), new ItemStack(nodeUpgrade, 1, 1) });
/* 1848:1668 */         addRecipe(new RecipeFilterInvert(Item.getItemFromBlock(Blocks.redstone_torch), "Inverted", new ItemStack(nodeUpgrade, 1, 10)));
/* 1849:     */       }
/* 1850:1671 */       if (!disableNodeUpgradeSpeedRecipe)
/* 1851:     */       {
/* 1852:1672 */         addRecipe(new ItemStack(nodeUpgrade, 4, 0), new Object[] { "RgR", "gGg", "RGR", Character.valueOf('R'), Blocks.redstone_block, Character.valueOf('G'), Items.gold_ingot, Character.valueOf('g'), Items.gold_nugget });
/* 1853:1673 */         addRecipe(new ItemStack(nodeUpgrade, 1, 2), new Object[] { "LiL", "iPi", "LiL", Character.valueOf('L'), new ItemStack(Items.dye, 1, 4), Character.valueOf('i'), Items.iron_ingot, Character.valueOf('P'), Items.iron_pickaxe });
/* 1854:1674 */         addRecipe(new ItemStack(nodeUpgrade, 1, 3), new Object[] { "GgG", "DuD", "GDG", Character.valueOf('u'), new ItemStack(nodeUpgrade, 1, 0), Character.valueOf('G'), Items.gold_ingot, Character.valueOf('D'), Items.diamond, Character.valueOf('g'), Items.gold_nugget });
/* 1855:     */         
/* 1856:1676 */         addRecipe(new ItemStack(nodeUpgrade, 1, 5), new Object[] { "ere", "qeq", "ere", Character.valueOf('e'), Items.ender_pearl, Character.valueOf('r'), Blocks.redstone_torch, Character.valueOf('q'), Items.quartz });
/* 1857:1677 */         addRecipe(new ItemStack(nodeUpgrade, 1, 6), new Object[] { "ere", "qeq", "eqe", Character.valueOf('e'), Items.ender_pearl, Character.valueOf('r'), Items.redstone, Character.valueOf('q'), Items.quartz });
/* 1858:     */         
/* 1859:1679 */         addRecipe(new ItemStack(nodeUpgrade, 3, 7), new Object[] { "sRR", "sGs", "GRR", Character.valueOf('R'), Blocks.redstone_block, Character.valueOf('G'), Items.gold_ingot, Character.valueOf('g'), Items.gold_nugget, Character.valueOf('s'), new ItemStack(nodeUpgrade, 4, 0) });
/* 1860:1680 */         addRecipe(new ItemStack(nodeUpgrade, 3, 8), new Object[] { "RRR", "sts", "RRR", Character.valueOf('R'), Blocks.redstone_block, Character.valueOf('G'), Items.gold_ingot, Character.valueOf('g'), Items.gold_nugget, Character.valueOf('s'), new ItemStack(nodeUpgrade, 4, 0), Character.valueOf('t'), new ItemStack(nodeUpgrade, 4, 7) });
/* 1861:1681 */         addRecipe(new ItemStack(nodeUpgrade, 3, 9), new Object[] { "RgR", "RGg", "RgR", Character.valueOf('R'), Blocks.redstone_block, Character.valueOf('G'), Items.gold_ingot, Character.valueOf('g'), Items.gold_nugget, Character.valueOf('s'), new ItemStack(nodeUpgrade, 4, 0) });
/* 1862:     */       }
/* 1863:     */     }
/* 1864:1686 */     if ((trashCanEnabled) && 
/* 1865:1687 */       (!disableTrashCanRecipe))
/* 1866:     */     {
/* 1867:1688 */       addRecipe(new ItemStack(trashCan, 1, 0), new Object[] { "SSS", "CHC", "CCC", Character.valueOf('S'), Blocks.stone, Character.valueOf('C'), Blocks.cobblestone, Character.valueOf('H'), Blocks.chest });
/* 1868:1689 */       addShapelessRecipe(new ItemStack(trashCan, 1, 1), new Object[] { trashCan, Items.bucket });
/* 1869:1690 */       addShapelessRecipe(new ItemStack(trashCan, 1, 2), new Object[] { trashCan, Items.redstone, Items.gold_ingot, Items.redstone, Items.gold_ingot });
/* 1870:     */     }
/* 1871:1695 */     if (!disableSpikeRecipe)
/* 1872:     */     {
/* 1873:1696 */       if (spikeEnabled) {
/* 1874:1697 */         addRecipe(new ItemStack(spike.itemSpike, 4), new Object[] { " S ", "SIS", "ICI", Character.valueOf('S'), Items.iron_sword, Character.valueOf('C'), Blocks.iron_block, Character.valueOf('I'), Items.iron_ingot });
/* 1875:     */       }
/* 1876:1700 */       if (spikeGoldEnabled) {
/* 1877:1701 */         addRecipe(new ItemStack(spikeGold.itemSpike, 4), new Object[] { " S ", "SIS", "ICI", Character.valueOf('S'), Items.golden_sword, Character.valueOf('C'), Blocks.gold_block, Character.valueOf('I'), decorative1Enabled ? new ItemStack(decorative1, 1, 8) : Items.gold_ingot });
/* 1878:     */       }
/* 1879:1704 */       if (spikeDiamondEnabled)
/* 1880:     */       {
/* 1881:1705 */         ItemStack dSword = new ItemStack(Items.diamond_sword, 1);
/* 1882:1706 */         addRecipe(new ItemStack(spikeDiamond.itemSpike, 4), new Object[] { " S ", "SIS", "ICI", Character.valueOf('S'), dSword, Character.valueOf('C'), Blocks.diamond_block, Character.valueOf('I'), spikeGoldEnabled ? spikeGold : Items.diamond });
/* 1883:     */       }
/* 1884:1709 */       if (spikeWoodEnabled) {
/* 1885:1710 */         addRecipe(new ShapedOreRecipe(new ItemStack(spikeWood.itemSpike, 4), new Object[] { " S ", "SIS", "ICI", Character.valueOf('S'), Items.wooden_sword, Character.valueOf('C'), "logWood", Character.valueOf('I'), "plankWood" }));
/* 1886:     */       }
/* 1887:     */     }
/* 1888:1715 */     if ((enderThermicPumpEnabled) && 
/* 1889:1716 */       (!disableEnderThermicPumpRecipe)) {
/* 1890:1717 */       if (!decorative1Enabled)
/* 1891:     */       {
/* 1892:1718 */         addRecipe(new ItemStack(enderThermicPump, 1), new Object[] { "ODO", "LEW", "OPO", Character.valueOf('O'), Blocks.obsidian, Character.valueOf('D'), Items.diamond, Character.valueOf('E'), Items.ender_eye, Character.valueOf('P'), Items.iron_pickaxe, Character.valueOf('L'), Items.lava_bucket, Character.valueOf('W'), Items.water_bucket });
/* 1893:     */         
/* 1894:1720 */         addRecipe(new ItemStack(enderThermicPump, 1), new Object[] { "ODO", "WEL", "OPO", Character.valueOf('O'), Blocks.obsidian, Character.valueOf('D'), Items.diamond, Character.valueOf('E'), Items.ender_eye, Character.valueOf('P'), Items.iron_pickaxe, Character.valueOf('L'), Items.lava_bucket, Character.valueOf('W'), Items.water_bucket });
/* 1895:     */       }
/* 1896:     */       else
/* 1897:     */       {
/* 1898:1723 */         addRecipe(new ItemStack(enderThermicPump, 1), new Object[] { "ODO", "LEW", "OPO", Character.valueOf('O'), new ItemStack(decorative1, 1, 1), Character.valueOf('D'), Items.diamond, Character.valueOf('E'), Items.ender_eye, Character.valueOf('P'), Items.iron_pickaxe, Character.valueOf('L'), Items.lava_bucket, Character.valueOf('W'), Items.water_bucket });
/* 1899:     */         
/* 1900:1725 */         addRecipe(new ItemStack(enderThermicPump, 1), new Object[] { "ODO", "WEL", "OPO", Character.valueOf('O'), new ItemStack(decorative1, 1, 1), Character.valueOf('D'), Items.diamond, Character.valueOf('E'), Items.ender_eye, Character.valueOf('P'), Items.iron_pickaxe, Character.valueOf('L'), Items.lava_bucket, Character.valueOf('W'), Items.water_bucket });
/* 1901:     */       }
/* 1902:     */     }
/* 1903:1732 */     if ((enderQuarryEnabled) && 
/* 1904:1733 */       (decorative1Enabled))
/* 1905:     */     {
/* 1906:1734 */       addRecipe(new ShapedOreRecipe(new ItemStack(enderQuarry), new Object[] { "EsE", "CDC", "pPp", Character.valueOf('E'), new ItemStack(decorative1, 1, 1), Character.valueOf('s'), "treeSapling", Character.valueOf('M'), new ItemStack(decorative1, 1, 8), Character.valueOf('C'), new ItemStack(decorative1, 1, 11), Character.valueOf('D'), new ItemStack(decorative1, 1, 12), Character.valueOf('P'), Items.diamond_pickaxe, Character.valueOf('p'), enderThermicPump == null ? new ItemStack(decorative1, 1, 12) : enderThermicPump }));
/* 1907:1738 */       if ((enderQuarryUpgradeEnabled) && (!disableEnderQuarryUpgradesRecipes)) {
/* 1908:1739 */         TileEntityEnderQuarry.addUpgradeRecipes();
/* 1909:     */       }
/* 1910:     */     }
/* 1911:1743 */     if ((enderMarkerEnabled) && 
/* 1912:1744 */       (decorative1Enabled)) {
/* 1913:1745 */       addRecipe(new ItemStack(enderMarker), new Object[] { "E", "D", "D", Character.valueOf('E'), Items.ender_pearl, Character.valueOf('D'), new ItemStack(decorative1, 1, 1) });
/* 1914:     */     }
/* 1915:1749 */     if ((timerBlockEnabled) && 
/* 1916:1750 */       (!disableTimerBlockRecipe)) {
/* 1917:1751 */       addRecipe(new ItemStack(timerBlock, 1), new Object[] { "RSR", "STS", "RSR", Character.valueOf('S'), Blocks.stone, Character.valueOf('T'), Blocks.redstone_torch, Character.valueOf('R'), Items.redstone });
/* 1918:     */     }
/* 1919:1755 */     if ((magnumTorchEnabled) && 
/* 1920:1756 */       (!disableMagnumTorchRecipe)) {
/* 1921:1757 */       if (chandelier != null) {
/* 1922:1758 */         addRecipe(new ShapedOreRecipe(new ItemStack(magnumTorch), new Object[] { "RCH", "CWC", "CWC", Character.valueOf('C'), chandelier, Character.valueOf('W'), "logWood", Character.valueOf('R'), new ItemStack(Items.potionitem, 1, 8225), Character.valueOf('H'), new ItemStack(Items.potionitem, 1, 8229) }));
/* 1923:     */       } else {
/* 1924:1761 */         addRecipe(new ShapedOreRecipe(new ItemStack(magnumTorch), new Object[] { "RCH", "CWC", "CWC", Character.valueOf('C'), Items.diamond, Character.valueOf('W'), "logWood", Character.valueOf('R'), new ItemStack(Items.potionitem, 1, 8225), Character.valueOf('H'), new ItemStack(Items.potionitem, 1, 8229) }));
/* 1925:     */       }
/* 1926:     */     }
/* 1927:1767 */     if ((drumEnabled) && 
/* 1928:1768 */       (!disableDrumRecipe))
/* 1929:     */     {
/* 1930:1769 */       addRecipe(new ItemStack(drum, 1, 0), new Object[] { "ISI", "ICI", "ISI", Character.valueOf('I'), Items.iron_ingot, Character.valueOf('S'), Blocks.heavy_weighted_pressure_plate, Character.valueOf('C'), Items.cauldron });
/* 1931:1771 */       if (bedrockiumEnabled) {
/* 1932:1772 */         addRecipe(new ItemStack(drum, 1, 1), new Object[] { "ISI", "ICI", "ISI", Character.valueOf('I'), bedrockium, Character.valueOf('S'), Blocks.light_weighted_pressure_plate, Character.valueOf('C'), Items.cauldron });
/* 1933:     */       }
/* 1934:     */     }
/* 1935:1777 */     if ((generatorEnabled) && 
/* 1936:1778 */       (!disableGeneratorRecipe))
/* 1937:     */     {
/* 1938:1779 */       BlockGenerator.addRecipes();
/* 1939:1780 */       if (generator2Enabled)
/* 1940:     */       {
/* 1941:1781 */         BlockGenerator.addUpgradeRecipes(generator, generator2);
/* 1942:1782 */         if (generator3Enabled) {
/* 1943:1783 */           BlockGenerator.addSuperUpgradeRecipes(generator2, generator3);
/* 1944:     */         }
/* 1945:     */       }
/* 1946:     */     }
/* 1947:1790 */     if ((goldenLassoEnabled) && 
/* 1948:1791 */       (!disableGoldenLassoRecipe))
/* 1949:     */     {
/* 1950:1792 */       addRecipe(new ItemStack(goldenLasso, 1), new Object[] { "GSG", "SES", "GSG", Character.valueOf('G'), Items.gold_nugget, Character.valueOf('S'), Items.string, Character.valueOf('E'), Items.ender_eye });
/* 1951:1793 */       addRecipe(new RecipeHorseTransmutation());
/* 1952:     */     }
/* 1953:1797 */     if (divisionSigilEnabled) {
/* 1954:1798 */       addRecipe(new RecipeUnsigil());
/* 1955:     */     }
/* 1956:1801 */     if (unstableIngotEnabled)
/* 1957:     */     {
/* 1958:1802 */       if ((!disableUnstableIngotRecipe) && (divisionSigilEnabled))
/* 1959:     */       {
/* 1960:1803 */         addRecipe(new RecipeUnEnchanting());
/* 1961:1804 */         addRecipe(RecipeUnstableIngotCrafting.addRecipe(new ItemStack(unstableIngot), new Object[] { "I", "S", "D", Character.valueOf('I'), Items.iron_ingot, Character.valueOf('S'), ItemDivisionSigil.newActiveSigil(), Character.valueOf('D'), Items.diamond }));
/* 1962:     */         
/* 1963:1806 */         addRecipe(RecipeUnstableNuggetCrafting.addRecipe(new ItemStack(unstableIngot, 1, 1), new Object[] { "g", "S", "D", Character.valueOf('g'), Items.gold_nugget, Character.valueOf('S'), ItemDivisionSigil.newActiveSigil(), Character.valueOf('D'), Items.diamond }));
/* 1964:     */         
/* 1965:1808 */         ItemStack item = new ItemStack(unstableIngot);
/* 1966:1809 */         NBTTagCompound tags = new NBTTagCompound();
/* 1967:1810 */         tags.setBoolean("stable", true);
/* 1968:1811 */         item.setTagCompound(tags);
/* 1969:1812 */         addRecipe(item, new Object[] { "uuu", "uuu", "uuu", Character.valueOf('u'), new ItemStack(unstableIngot, 1, 1) });
/* 1970:     */       }
/* 1971:1815 */       if (decorative1Enabled) {
/* 1972:1816 */         addRecipe(RecipeUnstableCrafting.addRecipe(new ItemStack(decorative1, 1, 5), new Object[] { "III", "III", "III", Character.valueOf('I'), unstableIngot }));
/* 1973:     */       }
/* 1974:     */     }
/* 1975:1819 */     if ((portalEnabled) && (underdarkDimID != 0))
/* 1976:     */     {
/* 1977:     */       ItemStack u;
/* 1978:     */       ItemStack u;
/* 1979:1822 */       if (unstableIngot != null) {
/* 1980:1823 */         u = new ItemStack(unstableIngot);
/* 1981:     */       } else {
/* 1982:1825 */         u = new ItemStack(Items.nether_star);
/* 1983:     */       }
/* 1984:     */       ItemStack b2;
/* 1985:     */       ItemStack b;
/* 1986:     */       ItemStack b2;
/* 1987:1828 */       if (cobblestoneCompr != null)
/* 1988:     */       {
/* 1989:1829 */         ItemStack b = new ItemStack(cobblestoneCompr, 1, 4);
/* 1990:1830 */         b2 = new ItemStack(cobblestoneCompr, 1, 3);
/* 1991:     */       }
/* 1992:     */       else
/* 1993:     */       {
/* 1994:1832 */         b = new ItemStack(Blocks.diamond_block);
/* 1995:1833 */         b2 = new ItemStack(Blocks.diamond_block);
/* 1996:     */       }
/* 1997:1836 */       addRecipe(RecipeUnstableCrafting.addRecipe(new ItemStack(portal, 1), new Object[] { "cNc", "NCN", "cNc", Character.valueOf('C'), b, Character.valueOf('c'), b2, Character.valueOf('N'), u }));
/* 1998:     */     }
/* 1999:1839 */     if ((portalEnabled) && (endoftimeDimID != 0))
/* 2000:     */     {
/* 2001:1840 */       Object end_stone = decorative1 == null ? Blocks.end_stone : new ItemStack(decorative1, 1, 14);
/* 2002:1841 */       Object obsidian = decorative1 == null ? Blocks.obsidian : "burntQuartz";
/* 2003:1842 */       addRecipe(new ShapedOreRecipe(new ItemStack(portal, 1, 2), new Object[] { "PEP", "ECE", "PEP", Character.valueOf('C'), Items.clock, Character.valueOf('E'), end_stone, Character.valueOf('P'), obsidian }));
/* 2004:     */     }
/* 2005:1847 */     if ((buildersWandEnabled) && 
/* 2006:1848 */       (!disableBuildersWandRecipe) && (unstableIngotEnabled))
/* 2007:     */     {
/* 2008:1849 */       RecipeUnstableCrafting recipe = RecipeUnstableCrafting.addRecipe(new ItemStack(buildersWand, 1), new Object[] { " I", "S ", Character.valueOf('I'), unstableIngot, Character.valueOf('S'), Blocks.obsidian });
/* 2009:1850 */       if (creativeBuildersWandEnabled) {
/* 2010:1851 */         recipe.setStable(new ItemStack(creativeBuildersWand));
/* 2011:     */       }
/* 2012:1852 */       addRecipe(recipe);
/* 2013:     */     }
/* 2014:1856 */     if ((ethericSwordEnabled) && 
/* 2015:1857 */       (!disableEthericSwordRecipe) && (unstableIngotEnabled)) {
/* 2016:1858 */       addRecipe(RecipeUnstableCrafting.addRecipe(new ItemStack(ethericSword, 1), new Object[] { "I", "I", "S", Character.valueOf('I'), unstableIngot, Character.valueOf('S'), Blocks.obsidian }).setStableItem(lawSword));
/* 2017:     */     }
/* 2018:1861 */     if ((erosionShovelEnabled) && 
/* 2019:1862 */       (!disableErosionShovelRecipe) && (unstableIngotEnabled)) {
/* 2020:1863 */       addRecipe(RecipeUnstableCrafting.addRecipe(new ItemStack(erosionShovel, 1), new Object[] { "I", "S", "S", Character.valueOf('I'), unstableIngot, Character.valueOf('S'), Blocks.obsidian }).addStableEnchant(Enchantment.efficiency, 10));
/* 2021:     */     }
/* 2022:1866 */     if ((destructionPickaxeEnabled) && 
/* 2023:1867 */       (!disableDestructionPickaxeRecipe) && (unstableIngotEnabled)) {
/* 2024:1868 */       addRecipe(RecipeUnstableCrafting.addRecipe(new ItemStack(destructionPickaxe, 1), new Object[] { "III", " S ", " S ", Character.valueOf('I'), unstableIngot, Character.valueOf('S'), Blocks.obsidian }).addStableEnchant(Enchantment.efficiency, 10));
/* 2025:     */     }
/* 2026:1871 */     if ((healingAxeEnabled) && 
/* 2027:1872 */       (!disableHealingAxeRecipe) && (unstableIngotEnabled)) {
/* 2028:1873 */       addRecipe(RecipeUnstableCrafting.addRecipe(new ItemStack(healingAxe, 1), new Object[] { "II", "IS", " S", Character.valueOf('I'), unstableIngot, Character.valueOf('S'), Blocks.obsidian }).addStableEnchant(Enchantment.efficiency, 10));
/* 2029:     */     }
/* 2030:1876 */     if ((temporalHoeEnabled) && 
/* 2031:1877 */       (!disableTemporalHoeRecipe) && (unstableIngotEnabled)) {
/* 2032:1878 */       addRecipe(RecipeUnstableCrafting.addRecipe(new ItemStack(temporalHoe, 1), new Object[] { "II", " S", " S", Character.valueOf('I'), unstableIngot, Character.valueOf('S'), Blocks.obsidian }));
/* 2033:     */     }
/* 2034:1881 */     if ((sonarGogglesEnabled) && 
/* 2035:1882 */       (!disableSonarGogglesRecipe) && (unstableIngotEnabled)) {
/* 2036:1883 */       addRecipe(RecipeUnstableCrafting.addRecipe(new ItemStack(sonarGoggles, 1), new Object[] { "III", "EIE", Character.valueOf('I'), unstableIngot, Character.valueOf('E'), Items.ender_eye }));
/* 2037:     */     }
/* 2038:1886 */     if (greenScreen != null)
/* 2039:     */     {
/* 2040:1887 */       addRecipe(RecipeUnstableCrafting.addRecipe(new ItemStack(greenScreen, 4, 8), new Object[] { "GGG", "GIG", "GGG", Character.valueOf('I'), unstableIngot, Character.valueOf('G'), Blocks.stonebrick }));
/* 2041:1888 */       if (colorBlockBrick != null) {
/* 2042:1889 */         for (int i = 0; i < 16; i++) {
/* 2043:1890 */           addRecipe(RecipeUnstableCrafting.addRecipe(new ItemStack(greenScreen, 4, i), new Object[] { "GGG", "GIG", "GGG", Character.valueOf('I'), unstableIngot, Character.valueOf('G'), new ItemStack(colorBlockBrick, 1, i) }));
/* 2044:     */         }
/* 2045:     */       }
/* 2046:1891 */       String[] dyes = { "Black", "Red", "Green", "Brown", "Blue", "Purple", "Cyan", "LightGray", "Gray", "Pink", "Lime", "Yellow", "LightBlue", "Magenta", "Orange", "White" };
/* 2047:1893 */       for (int i = 0; i < 16; i++) {
/* 2048:1894 */         addRecipe(new ShapelessOreRecipe(new ItemStack(greenScreen, 1, i), new Object[] { new ItemStack(greenScreen, 1, 32767), "dye" + dyes[(15 - i)] }));
/* 2049:     */       }
/* 2050:     */     }
/* 2051:1897 */     if ((etherealBlockEnabled) && 
/* 2052:1898 */       (!disableEtherealBlockRecipe) && (unstableIngotEnabled))
/* 2053:     */     {
/* 2054:1899 */       addRecipe(RecipeUnstableCrafting.addRecipe(new ItemStack(etheralBlock, 4, 0), new Object[] { "GGG", "GIG", "GGG", Character.valueOf('I'), unstableIngot, Character.valueOf('G'), Blocks.glass }));
/* 2055:1900 */       if (decorative2Enabled)
/* 2056:     */       {
/* 2057:1901 */         addRecipe(RecipeUnstableCrafting.addRecipe(new ItemStack(etheralBlock, 4, 1), new Object[] { "GGG", "GIG", "GGG", Character.valueOf('I'), unstableIngot, Character.valueOf('G'), new ItemStack(decorative2, 1, 0) }));
/* 2058:1902 */         addRecipe(RecipeUnstableCrafting.addRecipe(new ItemStack(etheralBlock, 4, 2), new Object[] { "GGG", "GIG", "GGG", Character.valueOf('I'), unstableIngot, Character.valueOf('G'), new ItemStack(decorative2, 1, 10) }));
/* 2059:     */       }
/* 2060:1905 */       for (int i = 0; i < 3; i++) {
/* 2061:1906 */         addShapelessRecipe(new ItemStack(etheralBlock, 1, i + 3), new Object[] { new ItemStack(etheralBlock, 1, i), Blocks.redstone_torch });
/* 2062:     */       }
/* 2063:     */     }
/* 2064:1911 */     if (wateringCanEnabled)
/* 2065:     */     {
/* 2066:1912 */       if (!disableWateringCanRecipe)
/* 2067:     */       {
/* 2068:1913 */         addRecipe(new ItemStack(wateringCan, 1, 1), new Object[] { "SB ", "SWS", " S ", Character.valueOf('S'), Items.iron_ingot, Character.valueOf('B'), new ItemStack(Items.dye, 1, 15), Character.valueOf('W'), Items.bowl });
/* 2069:1914 */         addRecipe(RecipeDifficultySpecific.addRecipe(new boolean[] { true, false, false, false }, new ItemStack(wateringCan, 1, 1), new String[] { "Peaceful Mode only" }, new Object[] { "S  ", "SWS", " S ", Character.valueOf('S'), Blocks.stone, Character.valueOf('W'), Items.bowl }));
/* 2070:     */       }
/* 2071:1918 */       if ((!disableSuperWateringCanRecipe) && 
/* 2072:1919 */         (soulEnabled) && (bedrockiumEnabled)) {
/* 2073:1920 */         addRecipe(new ItemStack(wateringCan, 1, 3), new Object[] { "SB ", "SWS", " S ", Character.valueOf('S'), bedrockium, Character.valueOf('B'), soul, Character.valueOf('W'), Items.bowl });
/* 2074:     */       }
/* 2075:     */     }
/* 2076:1925 */     if (scannerEnabled) {
/* 2077:1926 */       addRecipe(new ItemStack(scanner), new Object[] { "III", "ERI", "III", Character.valueOf('E'), Items.ender_eye, Character.valueOf('R'), Items.redstone, Character.valueOf('I'), Items.iron_ingot });
/* 2078:     */     }
/* 2079:1929 */     if ((goldenBagEnabled) && 
/* 2080:1930 */       (!disableGoldenBagRecipe))
/* 2081:     */     {
/* 2082:1931 */       addRecipe(new ItemStack(goldenBag, 1), new Object[] { "WdW", "gCg", "WGW", Character.valueOf('W'), new ItemStack(Blocks.wool, 1, 32767), Character.valueOf('d'), Items.diamond, Character.valueOf('g'), Items.gold_ingot, Character.valueOf('B'), Items.blaze_powder, Character.valueOf('G'), Blocks.gold_block, Character.valueOf('C'), Blocks.chest });
/* 2083:     */       
/* 2084:     */ 
/* 2085:1934 */       addRecipe(new RecipeBagDye());
/* 2086:1936 */       if (decorative1Enabled) {
/* 2087:1937 */         addRecipe(RecipeGBEnchanting.addRecipe(new ItemStack(goldenBag, 1), new Object[] { "WGW", Character.valueOf('d'), Items.diamond, Character.valueOf('G'), new ItemStack(goldenBag, 1), Character.valueOf('W'), new ItemStack(decorative1, 1, 8) }));
/* 2088:     */       }
/* 2089:     */     }
/* 2090:1943 */     if (bedrockiumEnabled) {
/* 2091:1944 */       addRecipe(new ItemStack(bedrockium, 1), new Object[] { "IcI", "cdc", "IcI", Character.valueOf('I'), new ItemStack(cobblestoneCompr, 1, 2), Character.valueOf('d'), Blocks.diamond_block, Character.valueOf('c'), new ItemStack(cobblestoneCompr, 1, 3) });
/* 2092:     */     }
/* 2093:1947 */     if (qedEnabled)
/* 2094:     */     {
/* 2095:1948 */       addRecipe(new ItemStack(qed, 1, 0), new Object[] { "RcR", "EdE", "EEE", Character.valueOf('R'), Items.ender_eye, Character.valueOf('c'), Blocks.crafting_table, Character.valueOf('d'), decorative1Enabled ? new ItemStack(decorative1, 1, 12) : Blocks.diamond_block, Character.valueOf('E'), decorative1Enabled ? new ItemStack(decorative1, 1, 1) : Blocks.obsidian });
/* 2096:1949 */       addRecipe(new ItemStack(qed, 1, 2), new Object[] { " e ", " E ", "EEE", Character.valueOf('e'), Items.ender_eye, Character.valueOf('d'), Items.diamond, Character.valueOf('E'), decorative1Enabled ? new ItemStack(decorative1, 1, 1) : Blocks.obsidian });
/* 2097:     */     }
/* 2098:1953 */     if (!disableWitherRecipe)
/* 2099:     */     {
/* 2100:1954 */       ItemStack t2 = new ItemStack(Items.nether_star, 1);
/* 2101:1955 */       addRecipe(RecipeDifficultySpecific.addRecipe(new boolean[] { true, false, false, false }, t2, new String[] { "Peaceful Mode only" }, new Object[] { "WWW", "SSS", "DSB", Character.valueOf('W'), new ItemStack(Items.skull, 1, 1), Character.valueOf('S'), Blocks.soul_sand, Character.valueOf('D'), Items.diamond_sword, Character.valueOf('B'), Items.bow }));
/* 2102:     */     }
/* 2103:1959 */     if ((precisionShearsEnabled) && 
/* 2104:1960 */       (!disablePrecisionShears) && (unstableIngotEnabled)) {
/* 2105:1961 */       addRecipe(RecipeUnstableCrafting.addRecipe(new ItemStack(precisionShears), new Object[] { "AI", "IA", Character.valueOf('I'), unstableIngot, Character.valueOf('B'), new ItemStack(decorative1, 1, 5), Character.valueOf('A'), angelBlock != null ? angelBlock : Blocks.obsidian }).addStableEnchant(Enchantment.unbreaking, 10));
/* 2106:     */     }
/* 2107:1966 */     if ((angelRingEnabled) && (unstableIngotEnabled))
/* 2108:     */     {
/* 2109:1967 */       Object[] leftWing = { Blocks.glass, Items.feather, new ItemStack(Items.dye, 1, 5), Items.leather, Items.gold_nugget };
/* 2110:1968 */       Object[] rightWing = { Blocks.glass, Items.feather, new ItemStack(Items.dye, 1, 9), Items.leather, Items.gold_nugget };
/* 2111:1969 */       for (int i = 0; i < leftWing.length; i++)
/* 2112:     */       {
/* 2113:1970 */         if (decorative1Enabled) {
/* 2114:1971 */           addRecipe(RecipeUnstableCrafting.addRecipe(new ItemStack(angelRing, 1, i), new Object[] { "LGR", "GNG", "IGI", Character.valueOf('I'), unstableIngot, Character.valueOf('G'), Items.gold_ingot, Character.valueOf('N'), Items.nether_star, Character.valueOf('L'), leftWing[i], Character.valueOf('R'), rightWing[i] }));
/* 2115:     */         }
/* 2116:1972 */         addShapelessRecipe(new ItemStack(angelRing, 1, i), new Object[] { leftWing[i], new ItemStack(angelRing, 1, 32767), rightWing[i] });
/* 2117:     */       }
/* 2118:     */     }
/* 2119:1976 */     if (!disableChestRecipe) {
/* 2120:1977 */       GameRegistry.addRecipe(new ShapedOreRecipeAlwaysLast(new ItemStack(Blocks.chest, 4), new Object[] { "WWW", "W W", "WWW", Character.valueOf('W'), "logWood" }));
/* 2121:     */     }
/* 2122:1980 */     EE3Integration.addEMCRecipes();
/* 2123:     */   }
/* 2124:     */   
/* 2125:     */   public void addSmelting(ItemStack ingredient, ItemStack result, int experience)
/* 2126:     */   {
/* 2127:1985 */     GameRegistry.addSmelting(ingredient, result, experience);
/* 2128:     */   }
/* 2129:     */   
/* 2130:     */   public void addShapedRecipe(ItemStack out, Object... ingreds)
/* 2131:     */   {
/* 2132:1989 */     GameRegistry.addShapedRecipe(out, ingreds);
/* 2133:     */   }
/* 2134:     */   
/* 2135:     */   public void addShapelessRecipe(ItemStack out, Object... ingreds)
/* 2136:     */   {
/* 2137:1993 */     GameRegistry.addShapelessRecipe(out, ingreds);
/* 2138:     */   }
/* 2139:     */   
/* 2140:     */   public void registerRecipe(Class<? extends IRecipe> recipe, RecipeSorter.Category cat, String s)
/* 2141:     */   {
/* 2142:1997 */     if (registeredRecipes.contains(recipe)) {
/* 2143:1998 */       return;
/* 2144:     */     }
/* 2145:2000 */     registeredRecipes.add(recipe);
/* 2146:2001 */     RecipeSorter.register("extrautils:" + recipe.getSimpleName(), recipe, cat, s);
/* 2147:     */   }
/* 2148:     */   
/* 2149:     */   public void serverStart(FMLServerStartingEvent event)
/* 2150:     */   {
/* 2151:2005 */     ExtraUtilsMod.proxy.newServerStart();
/* 2152:     */   }
/* 2153:     */   
/* 2154:     */   public void loadComplete(FMLLoadCompleteEvent event)
/* 2155:     */   {
/* 2156:2013 */     if (!this.hasSpecialInit)
/* 2157:     */     {
/* 2158:2014 */       this.hasSpecialInit = true;
/* 2159:2015 */       specialInit();
/* 2160:     */     }
/* 2161:     */   }
/* 2162:     */   
/* 2163:     */   public void remap(FMLMissingMappingsEvent event) {}
/* 2164:     */   
/* 2165:     */   public class ChunkloadCallback
/* 2166:     */     implements ForgeChunkManager.OrderedLoadingCallback
/* 2167:     */   {
/* 2168:     */     public ChunkloadCallback() {}
/* 2169:     */     
/* 2170:     */     public void ticketsLoaded(List<ForgeChunkManager.Ticket> tickets, World world)
/* 2171:     */     {
/* 2172:2023 */       for (ForgeChunkManager.Ticket ticket : tickets)
/* 2173:     */       {
/* 2174:2024 */         String ticket_id = ticket.getModData().getString("id");
/* 2175:2026 */         if (ticket_id.equals("pump"))
/* 2176:     */         {
/* 2177:2027 */           int pumpX = ticket.getModData().getInteger("pumpX");
/* 2178:2028 */           int pumpY = ticket.getModData().getInteger("pumpY");
/* 2179:2029 */           int pumpZ = ticket.getModData().getInteger("pumpZ");
/* 2180:2030 */           TileEntityEnderThermicLavaPump tq = (TileEntityEnderThermicLavaPump)world.getTileEntity(pumpX, pumpY, pumpZ);
/* 2181:2031 */           tq.forceChunkLoading(ticket);
/* 2182:     */         }
/* 2183:2034 */         if (ticket_id.equals("quarry"))
/* 2184:     */         {
/* 2185:2035 */           int x = ticket.getModData().getInteger("x");
/* 2186:2036 */           int y = ticket.getModData().getInteger("y");
/* 2187:2037 */           int z = ticket.getModData().getInteger("z");
/* 2188:2038 */           TileEntityEnderQuarry tq = (TileEntityEnderQuarry)world.getTileEntity(x, y, z);
/* 2189:2039 */           tq.forceChunkLoading(ticket);
/* 2190:     */         }
/* 2191:     */       }
/* 2192:     */     }
/* 2193:     */     
/* 2194:     */     public List<ForgeChunkManager.Ticket> ticketsLoaded(List<ForgeChunkManager.Ticket> tickets, World world, int maxTicketCount)
/* 2195:     */     {
/* 2196:2046 */       List<ForgeChunkManager.Ticket> validTickets = Lists.newArrayList();
/* 2197:2048 */       for (ForgeChunkManager.Ticket ticket : tickets)
/* 2198:     */       {
/* 2199:2049 */         String ticket_id = ticket.getModData().getString("id");
/* 2200:2051 */         if ((ticket_id.equals("pump")) && (ExtraUtils.enderThermicPump != null))
/* 2201:     */         {
/* 2202:2052 */           int pumpX = ticket.getModData().getInteger("pumpX");
/* 2203:2053 */           int pumpY = ticket.getModData().getInteger("pumpY");
/* 2204:2054 */           int pumpZ = ticket.getModData().getInteger("pumpZ");
/* 2205:2055 */           Block blId = world.getBlock(pumpX, pumpY, pumpZ);
/* 2206:2057 */           if (blId == ExtraUtils.enderThermicPump) {
/* 2207:2058 */             validTickets.add(ticket);
/* 2208:     */           }
/* 2209:     */         }
/* 2210:2062 */         if ((ticket_id.equals("quarry")) && (ExtraUtils.enderQuarry != null) && (!TileEntityEnderQuarry.disableSelfChunkLoading))
/* 2211:     */         {
/* 2212:2063 */           int x = ticket.getModData().getInteger("x");
/* 2213:2064 */           int y = ticket.getModData().getInteger("y");
/* 2214:2065 */           int z = ticket.getModData().getInteger("z");
/* 2215:2066 */           Block blId = world.getBlock(x, y, z);
/* 2216:2068 */           if (blId == ExtraUtils.enderQuarry) {
/* 2217:2069 */             validTickets.add(ticket);
/* 2218:     */           }
/* 2219:     */         }
/* 2220:     */       }
/* 2221:2074 */       return validTickets;
/* 2222:     */     }
/* 2223:     */   }
/* 2224:     */ }


/* Location:           E:\TechnicPlatform\extrautilities\extrautilities-1.2.13.jar
 * Qualified Name:     com.rwtema.extrautils.ExtraUtils
 * JD-Core Version:    0.7.0.1
 */