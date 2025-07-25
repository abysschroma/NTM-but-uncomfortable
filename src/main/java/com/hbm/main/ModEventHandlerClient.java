package com.hbm.main;

import baubles.api.BaublesApi;
import com.google.common.collect.Queues;
import com.google.gson.JsonSyntaxException;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.TrappedBrick.Trap;
import com.hbm.capability.HbmCapability;
import com.hbm.config.GeneralConfig;
import com.hbm.entity.mob.EntityHunterChopper;
import com.hbm.entity.projectile.EntityChopperMine;
import com.hbm.entity.siege.SiegeTier;
import com.hbm.flashlight.Flashlight;
import com.hbm.forgefluid.SpecialContainerFillLists.EnumCanister;
import com.hbm.forgefluid.SpecialContainerFillLists.EnumCell;
import com.hbm.forgefluid.SpecialContainerFillLists.EnumGasCanister;
import com.hbm.handler.*;
import com.hbm.interfaces.*;
import com.hbm.inventory.AssemblerRecipes;
import com.hbm.inventory.ChemplantRecipes;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.NbtComparableStack;
import com.hbm.inventory.gui.GUIArmorTable;
import com.hbm.items.ModItems;
import com.hbm.items.ModItems.Armory;
import com.hbm.items.ModItems.ElevatorStyles;
import com.hbm.items.ModItems.Materials.Ingots;
import com.hbm.items.ModItems.ToolSets;
import com.hbm.items.armor.ItemArmorMod;
import com.hbm.items.armor.JetpackBase;
import com.hbm.items.gear.ArmorFSB;
import com.hbm.items.gear.RedstoneSword;
import com.hbm.items.machine.*;
import com.hbm.items.machine.ItemCassette.TrackType;
import com.hbm.items.special.ItemHot;
import com.hbm.items.special.ItemWasteLong;
import com.hbm.items.special.ItemWasteShort;
import com.hbm.items.special.weapon.GunB92;
import com.hbm.items.tool.ItemFluidCanister;
import com.hbm.items.tool.ItemGuideBook;
import com.hbm.items.weapon.*;
import com.hbm.lib.HBMSoundEvents;
import com.hbm.lib.Library;
import com.hbm.lib.RecoilHandler;
import com.hbm.lib.RefStrings;
import com.hbm.modules.ItemHazardModule;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.GunButtonPacket;
import com.hbm.packet.MeathookJumpPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.particle.ParticleBatchRenderer;
import com.hbm.particle.ParticleDSmokeFX;
import com.hbm.particle.ParticleFirstPerson;
import com.hbm.particle.gluon.ParticleGluonBurnTrail;
import com.hbm.render.LightRenderer;
import com.hbm.render.RenderHelper;
import com.hbm.render.amlfrom1710.CompositeBrush;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.anim.HbmAnimations.Animation;
import com.hbm.render.anim.HbmAnimations.BlenderAnimation;
import com.hbm.render.entity.DSmokeRenderer;
import com.hbm.render.item.*;
import com.hbm.render.item.weapon.*;
import com.hbm.render.misc.BeamPronter;
import com.hbm.render.misc.RenderAccessoryUtility;
import com.hbm.render.misc.RenderScreenOverlay;
import com.hbm.render.misc.SoyuzPronter;
import com.hbm.render.modelrenderer.EgonBackpackRenderer;
import com.hbm.render.tileentity.RenderMultiblock;
import com.hbm.render.tileentity.RenderSoyuzMultiblock;
import com.hbm.render.tileentity.RenderStructureMarker;
import com.hbm.render.util.RenderOverhead;
import com.hbm.render.world.RenderNTMSkybox;
import com.hbm.sound.*;
import com.hbm.sound.MovingSoundPlayerLoop.EnumHbmSound;
import com.hbm.tileentity.bomb.TileEntityNukeCustom;
import com.hbm.tileentity.bomb.TileEntityNukeCustom.CustomNukeEntry;
import com.hbm.tileentity.bomb.TileEntityNukeCustom.EnumEntryType;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBase;
import com.hbm.util.ArmorRegistry;
import com.hbm.util.ArmorRegistry.HazardClass;
import com.hbm.util.BobMathUtil;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.I18nUtil;
import com.hbm.util.Tuple.Pair;
import com.hbm.util.Tuple.Triplet;
import com.leafia.contents.control.fuel.nuclearfuel.ItemLeafiaRod;
import com.leafia.contents.control.fuel.nuclearfuel.LeafiaRodBakedModel;
import com.leafia.contents.control.fuel.nuclearfuel.LeafiaRodRender;
import com.leafia.contents.effects.folkvangr.EntityNukeFolkvangr;
import com.leafia.contents.gear.utility.FuzzyIdentifierBakedModel;
import com.leafia.contents.gear.utility.ItemFuzzyIdentifier;
import com.leafia.contents.machines.elevators.car.styles.EvStyleItem;
import com.leafia.contents.worldgen.ModBiome;
import com.leafia.dev.LeafiaUtil;
import com.leafia.dev.container_utility.LeafiaPacket;
import com.leafia.dev.container_utility.LeafiaPacketReceiver;
import com.leafia.passive.LeafiaPassiveLocal;
import com.leafia.passive.effects.IdkWhereThisShitBelongs;
import com.leafia.passive.effects.LeafiaShakecam;
import com.leafia.passive.rendering.TopRender;
import com.leafia.shit.leafiashader.BigBruh;
import com.leafia.shit.recipe_book_elements.LeafiaRecipeBookTab;
import com.leafia.transformer.LeafiaGls;
import com.leafia.transformer.LeafiaGls.LeafiaGlStack;
import com.leafia.unsorted.recipe_book.system.LeafiaDummyRecipe;
import glmath.glm.vec._2.Vec2;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.toasts.RecipeToast;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBiped.ArmPose;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndGateway;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.client.EnumHelperClient;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.glu.Project;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.*;
import java.util.Map.Entry;

public class ModEventHandlerClient {

	public static Set<EntityLivingBase> specialDeathEffectEntities = new HashSet<>();
	public static ArrayDeque<ParticleFirstPerson> firstPersonAuxParticles = Queues.newArrayDeque();

	public static float deltaMouseX;
	public static float deltaMouseY;
	
	public static float currentFOV = 70;
	public static MusicTicker.MusicType darkness;
	public static MusicTicker.MusicType darkness2;
	public static boolean nextMusic = true;
	private static final Map<String,BigBruh> shaderGroups = new HashMap<>();
	int lastW = 0;
	int lastH = 0;
	//private static final Map<String, Shader> shaders = new HashMap<>();
	public static final Logger LOGGER = LogManager.getLogger();
	void addShader(String key,ResourceLocation resourceLocationIn)
	{
		if (OpenGlHelper.shadersSupported) {
			if (ShaderLinkHelper.getStaticShaderLinkHelper() == null) {
				ShaderLinkHelper.setNewStaticShaderLinkHelper();
			}
			LOGGER.info("Trying to load shader: {}",resourceLocationIn);
			Minecraft mc = Minecraft.getMinecraft();
			lastW = mc.getFramebuffer().framebufferWidth;
			lastH = mc.getFramebuffer().framebufferHeight;
			try {
				BigBruh shaderGroup = new BigBruh(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), resourceLocationIn);
				shaderGroup.createBindFramebuffers(mc.displayWidth, mc.displayHeight);
				shaderGroups.put(key,shaderGroup);
				LOGGER.warn("Successfully put shader: {}", resourceLocationIn);
			} catch (IOException ioexception) {
				LOGGER.warn("Failed to load shader: {}", resourceLocationIn, ioexception);
			} catch (JsonSyntaxException jsonsyntaxexception) {
				LOGGER.warn("Failed to load shader: {}", resourceLocationIn, jsonsyntaxexception);
			}
		}
	}
	/*
	ShaderManager putShader(String key, String program, Framebuffer canvasRead, Framebuffer canvasWrite) {
		if (OpenGlHelper.shadersSupported) {
			LOGGER.info("Putting shader: {}",program);
			Minecraft mc = Minecraft.getMinecraft();
			if (ShaderLinkHelper.getStaticShaderLinkHelper() == null) {
				ShaderLinkHelper.setNewStaticShaderLinkHelper();
			}
			try {
				Shader shader = new Shader(mc.getResourceManager(), program, canvasRead, canvasWrite);
				shader.setProjectionMatrix(generateMatrix(canvasRead));
				shaders.put(key, shader);
				LOGGER.info("Successfully put shader: {}",program);
				return shader.getShaderManager();
			} catch (Exception exception) {
				LOGGER.warn("Failed to put shader: {}", program, exception);
			}
		}
		return null;
	}*/

	public ModEventHandlerClient() {
		Minecraft mc = Minecraft.getMinecraft();
		LeafiaShakecam.noise = new NoiseGeneratorPerlin(new Random(),1);
		Framebuffer mainCanvas = mc.getFramebuffer();
		darkness = EnumHelperClient.addMusicType("DARKNESS_GAME", HBMSoundEvents.darkness, 12000, 24000);
		darkness2 = EnumHelperClient.addMusicType("DARKNESS_MENU", HBMSoundEvents.darkness2, 12000, 14000);
		this.addShader("tom",new ResourceLocation("hbm:shaders/help/tom_desat.json"));
		this.addShader("nuclear",new ResourceLocation("hbm:shaders/help/nuclear.json"));
		//Framebuffer tempCanvas = new Framebuffer(mc.getFramebuffer().framebufferWidth,mc.getFramebuffer().framebufferHeight,mc.getFramebuffer().useDepth);
		//putShader("myaw","invert",mainCanvas,tempCanvas).getShaderUniformOrDefault("InverseAmount").set(0.8F);
		//putShader("blit","blit",tempCanvas,mainCanvas);
	}

	
	public static void updateMouseDelta() {
		Minecraft mc = Minecraft.getMinecraft();
		if(mc.inGameHasFocus && Display.isActive()) {
			mc.mouseHelper.mouseXYChange();
			float f = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
			float f1 = f * f * f * 8.0F;
			deltaMouseX = (float) mc.mouseHelper.deltaX * f1;
			deltaMouseY = (float) mc.mouseHelper.deltaY * f1;
		} else {
			deltaMouseX = 0;
			deltaMouseY = 0;
		}
	}
	
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event) {

		int i = 0;
		ResourceLocation[] list = new ResourceLocation[EnumCanister.values().length];
		for(EnumCanister e : EnumCanister.values()) {
			list[i] = e.getResourceLocation();
			i++;
		}
		ModelLoader.registerItemVariants(ModItems.canister_generic, list);

		i = 0;
		list = new ResourceLocation[EnumCell.values().length];
		for(EnumCell e : EnumCell.values()) {
			list[i] = e.getResourceLocation();
			i++;
		}
		ModelLoader.registerItemVariants(ModItems.cell, list);

		i = 0;
		list = new ResourceLocation[EnumGasCanister.values().length];
		for(EnumGasCanister e : EnumGasCanister.values()) {
			list[i] = e.getResourceLocation();
			i++;
		}
		ModelLoader.registerItemVariants(ModItems.cell, list);

		for(Item item : ModItems.ALL_ITEMS) {
			registerModel(item, 0);
		}
		for(Block block : ModBlocks.ALL_BLOCKS) {
			registerBlockModel(block, 0);
		}

		registerBedrockOreModels();
	}

	public static void registerBedrockOreModels(){
		ResourceLocation[] list = new ResourceLocation[300];
		for(int i = 0; i < list.length; i++) {
			ModelLoader.setCustomModelResourceLocation(ModItems.ore_bedrock, i, new ModelResourceLocation(ModItems.ore_bedrock.getRegistryName(), "inventory"));
			ModelLoader.setCustomModelResourceLocation(ModItems.ore_bedrock_centrifuged, i, new ModelResourceLocation(ModItems.ore_bedrock_centrifuged.getRegistryName(), "inventory"));
			ModelLoader.setCustomModelResourceLocation(ModItems.ore_bedrock_cleaned, i, new ModelResourceLocation(ModItems.ore_bedrock_cleaned.getRegistryName(), "inventory"));
			ModelLoader.setCustomModelResourceLocation(ModItems.ore_bedrock_separated, i, new ModelResourceLocation(ModItems.ore_bedrock_separated.getRegistryName(), "inventory"));
			ModelLoader.setCustomModelResourceLocation(ModItems.ore_bedrock_purified, i, new ModelResourceLocation(ModItems.ore_bedrock_purified.getRegistryName(), "inventory"));
			ModelLoader.setCustomModelResourceLocation(ModItems.ore_bedrock_nitrated, i, new ModelResourceLocation(ModItems.ore_bedrock_nitrated.getRegistryName(), "inventory"));
			ModelLoader.setCustomModelResourceLocation(ModItems.ore_bedrock_nitrocrystalline, i, new ModelResourceLocation(ModItems.ore_bedrock_nitrocrystalline.getRegistryName(), "inventory"));
			ModelLoader.setCustomModelResourceLocation(ModItems.ore_bedrock_deepcleaned, i, new ModelResourceLocation(ModItems.ore_bedrock_deepcleaned.getRegistryName(), "inventory"));
			ModelLoader.setCustomModelResourceLocation(ModItems.ore_bedrock_seared, i, new ModelResourceLocation(ModItems.ore_bedrock_seared.getRegistryName(), "inventory"));
			ModelLoader.setCustomModelResourceLocation(ModItems.ore_bedrock_enriched, i, new ModelResourceLocation(ModItems.ore_bedrock_enriched.getRegistryName(), "inventory"));
			ModelLoader.setCustomModelResourceLocation(ModItems.ore_bedrock_exquisite, i, new ModelResourceLocation(ModItems.ore_bedrock_exquisite.getRegistryName(), "inventory"));
			ModelLoader.setCustomModelResourceLocation(ModItems.ore_bedrock_perfect, i, new ModelResourceLocation(ModItems.ore_bedrock_perfect.getRegistryName(), "inventory"));
		}	
	}

	private void registerBlockModel(Block block, int meta) {
		registerModel(Item.getItemFromBlock(block), meta);
	}

	private void registerModel(Item item, int meta) {
		if(item == Items.AIR)
			return;

		//Drillgon200: I hate myself for making this
		if(item == ModItems.chemistry_template){
			ChemplantRecipes.registerRecipes();
		}

		if(item == ModItems.chemistry_icon) {
			for(int i: ChemplantRecipes.recipeNames.keySet()){
				ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(RefStrings.MODID + ":chem_icon_" + ChemplantRecipes.getName(i).toLowerCase(), "inventory"));
			}
		} else if(item == ModItems.chemistry_template) {
			for(int i: ChemplantRecipes.recipeNames.keySet()){
				ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName(), "inventory"));
			}
		} else if(item == ModItems.siren_track) {
			for(int i = 0; i < TrackType.values().length; i++) {
				ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName(), "inventory"));
			}
		} else if(item == Ingots.ingot_u238m2) {
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
			ModelLoader.setCustomModelResourceLocation(item, 1, new ModelResourceLocation(RefStrings.MODID + ":hs-elements", "inventory"));
			ModelLoader.setCustomModelResourceLocation(item, 2, new ModelResourceLocation(RefStrings.MODID + ":hs-arsenic", "inventory"));
			ModelLoader.setCustomModelResourceLocation(item, 3, new ModelResourceLocation(RefStrings.MODID + ":hs-vault", "inventory"));
		} else if(item == ModItems.polaroid || item == ModItems.glitch) {
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName() + "_" + MainRegistry.polaroidID, "inventory"));
		} else if(item == Item.getItemFromBlock(ModBlocks.brick_jungle_glyph)){
			for(int i = 0; i < 16; i ++)
				ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName().toString() + i, "inventory"));
		} else if(item == Item.getItemFromBlock(ModBlocks.brick_jungle_trap)){
			for(int i = 0; i < Trap.values().length; i ++)
				ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		} else if(item instanceof ItemGuideBook){
			for(int i = 0; i < ItemGuideBook.BookType.values().length; i ++)
				ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		} else if(item instanceof ItemHot){
			for(int i = 0; i < 15; i ++)
				ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName(), "inventory"));
			ModelLoader.setCustomModelResourceLocation(item, 15, new ModelResourceLocation(item.getRegistryName() + "_hot", "inventory"));
		} else if(item instanceof ItemRBMKPellet){
			for(int xe = 0; xe < 2; xe ++){
				for(int en = 0; en < 5; en ++){
					ModelLoader.setCustomModelResourceLocation(item, en+xe*5, new ModelResourceLocation(item.getRegistryName() + "_e" + en + (xe > 0 ? "_xe" : ""), "inventory"));
				}
			}
		} else if(item instanceof ItemWasteLong){
			for(int i = 0; i < ItemWasteLong.WasteClass.values().length; i ++){
				ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName(), "inventory"));
			}
		} else if(item instanceof ItemWasteShort){
			for(int i = 0; i < ItemWasteShort.WasteClass.values().length; i ++){
				ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName(), "inventory"));
			}
		} else if(item == ModItems.coin_siege){
			for(int i = 0; i < SiegeTier.getLength(); i ++){
				ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(RefStrings.MODID + ":coin_siege_" + SiegeTier.tiers[i].name, "inventory"));
			}
		} else if(item == Item.getItemFromBlock(ModBlocks.volcano_core)) {
			for (int i = 0; i < 4; i++) {
				ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName(), "inventory"));
			}
		} else if (item instanceof ItemLeafiaRod.EmptyLeafiaRod) {
			ModelLoader.setCustomModelResourceLocation(item, 15, new ModelResourceLocation(item.getRegistryName() + "_overlay", "inventory"));
			ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName() + "_empty", "inventory"));
		} else if(item instanceof IHasCustomModel) {
			ModelLoader.setCustomModelResourceLocation(item, meta, ((IHasCustomModel) item).getResourceLocation());
		} else {
			ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}

	@SubscribeEvent
	public void modelBaking(ModelBakeEvent evt) {

		for(EnumCanister e : EnumCanister.values()) {
			Object o = evt.getModelRegistry().getObject(e.getResourceLocation());
			if(o instanceof IBakedModel)
				e.putRenderModel((IBakedModel) o);
		}
		for(EnumCell e : EnumCell.values()) {
			Object o = evt.getModelRegistry().getObject(e.getResourceLocation());
			if(o instanceof IBakedModel)
				e.putRenderModel((IBakedModel) o);
		}
		for(EnumGasCanister e : EnumGasCanister.values()) {
			Object o = evt.getModelRegistry().getObject(e.getResourceLocation());
			if(o instanceof IBakedModel)
				e.putRenderModel((IBakedModel) o);
		}

		// Drillgon200: Sigh... find a better custom model loading system.
		// Drillgon200: Removed todo, found a better way. Now I just have to
		// deal with all these ugly things. That can wait.
		ResourceManager.init();
		Object object1 = evt.getModelRegistry().getObject(RedstoneSword.rsModel);
		if(object1 instanceof IBakedModel) {
			IBakedModel model = (IBakedModel) object1;
			ItemRedstoneSwordRender.INSTANCE.itemModel = model;
			evt.getModelRegistry().putObject(RedstoneSword.rsModel, new ItemRenderRedstoneSword());
		}
		Object object2 = evt.getModelRegistry().getObject(ItemAssemblyTemplate.location);
		if(object2 instanceof IBakedModel) {
			IBakedModel model = (IBakedModel) object2;
			AssemblyTemplateRender.INSTANCE.itemModel = model;
			evt.getModelRegistry().putObject(ItemAssemblyTemplate.location, new AssemblyTemplateBakedModel());
		}

		Object object3 = evt.getModelRegistry().getObject(GunB92.b92Model);
		if(object3 instanceof IBakedModel) {
			IBakedModel model = (IBakedModel) object3;
			ItemRenderGunAnim.INSTANCE.b92ItemModel = model;
			evt.getModelRegistry().putObject(GunB92.b92Model, new B92BakedModel());
		}
		Object object4 = evt.getModelRegistry().getObject(ItemFluidTank.fluidTankModel);
		if(object4 instanceof IBakedModel) {
			IBakedModel model = (IBakedModel) object4;
			FluidTankRender.INSTANCE.itemModel = model;
			evt.getModelRegistry().putObject(ItemFluidTank.fluidTankModel, new FluidTankBakedModel());
		}
		Object object5 = evt.getModelRegistry().getObject(ItemFluidTank.fluidBarrelModel);
		if(object5 instanceof IBakedModel) {
			IBakedModel model = (IBakedModel) object5;
			FluidBarrelRender.INSTANCE.itemModel = model;
			evt.getModelRegistry().putObject(ItemFluidTank.fluidBarrelModel, new FluidBarrelBakedModel());
		}
		Object object6 = evt.getModelRegistry().getObject(ItemFluidCanister.fluidCanisterModel);
		if(object6 instanceof IBakedModel) {
			IBakedModel model = (IBakedModel) object6;
			FluidCanisterRender.INSTANCE.itemModel = model;
			evt.getModelRegistry().putObject(ItemFluidCanister.fluidCanisterModel, new FluidCanisterBakedModel());
		}
		Object object7 = evt.getModelRegistry().getObject(ItemChemistryTemplate.chemModel);
		if(object7 instanceof IBakedModel) {
			IBakedModel model = (IBakedModel) object7;
			ChemTemplateRender.INSTANCE.itemModel = model;
			evt.getModelRegistry().putObject(ItemChemistryTemplate.chemModel, new ChemTemplateBakedModel());
		}
		Object object8 = evt.getModelRegistry().getObject(ItemForgeFluidIdentifier.identifierModel);
		if(object8 instanceof IBakedModel) {
			IBakedModel model = (IBakedModel) object8;
			FFIdentifierRender.INSTANCE.itemModel = model;
			evt.getModelRegistry().putObject(ItemForgeFluidIdentifier.identifierModel, new FFIdentifierModel());
		}
		Object object9 = evt.getModelRegistry().getObject(ItemLeafiaRod.rodModel);
		if(object9 instanceof IBakedModel) {
			IBakedModel model = (IBakedModel) object9;
			LeafiaRodRender.INSTANCE.itemModel = model;
			evt.getModelRegistry().putObject(ItemLeafiaRod.rodModel, new LeafiaRodBakedModel());
		}
		Object objecta = evt.getModelRegistry().getObject(ItemFuzzyIdentifier.fuzzyModel);
		if(objecta instanceof IBakedModel) {
			IBakedModel model = (IBakedModel) objecta;
			FFIdentifierRender.INSTANCE.itemModelFuzzy = model;
			evt.getModelRegistry().putObject(ItemFuzzyIdentifier.fuzzyModel, new FuzzyIdentifierBakedModel());
		}

		IRegistry<ModelResourceLocation, IBakedModel> reg = evt.getModelRegistry();
		swapModelsNoGui(Armory.gun_revolver_nightmare, reg);
		swapModelsNoGui(Armory.gun_revolver_nightmare2, reg);
		swapModels(Armory.gun_revolver, reg);
		swapModels(Armory.gun_revolver_iron, reg);
		swapModels(Armory.gun_revolver_gold, reg);
		swapModels(Armory.gun_revolver_lead, reg);
		swapModels(Armory.gun_revolver_saturnite, reg);
		swapModels(Armory.gun_revolver_schrabidium, reg);
		swapModelsNoGui(Armory.gun_revolver_cursed, reg);
		swapModelsNoGui(Armory.gun_revolver_pip, reg);
		swapModelsNoGui(Armory.gun_revolver_nopip, reg);
		swapModelsNoGui(Armory.gun_revolver_blackjack, reg);
		swapModelsNoGui(Armory.gun_revolver_silver, reg);
		swapModelsNoGui(Armory.gun_revolver_red, reg);
		swapModelsNoGui(Armory.gun_lever_action, reg);
		swapModelsNoGui(Armory.gun_spark, reg);
		swapModelsNoGui(Armory.gun_b93, reg);
		swapModelsNoGui(Armory.gun_rpg, reg);
		swapModelsNoGui(Armory.gun_karl, reg);
		swapModelsNoGui(Armory.gun_panzerschreck, reg);
		swapModels(Armory.gun_hk69, reg);
		swapModelsNoGui(Armory.gun_deagle, reg);
		swapModelsNoGui(Armory.gun_supershotgun, reg);
		swapModelsNoGui(Armory.gun_fatman, reg);
		swapModelsNoGui(Armory.gun_proto, reg);
		swapModelsNoGui(Armory.gun_mirv, reg);
		swapModelsNoGui(Armory.gun_bf, reg);
		swapModelsNoGui(Armory.gun_zomg, reg);
		swapModelsNoGui(Armory.gun_xvl1456, reg);
		swapModelsNoGui(Armory.gun_hp, reg);
		swapModelsNoGui(Armory.gun_defabricator, reg);
		swapModelsNoGui(Armory.gun_uboinik, reg);
		swapModelsNoGui(Armory.gun_euthanasia, reg);
		swapModelsNoGui(Armory.gun_stinger, reg);
		swapModelsNoGui(Armory.gun_skystinger, reg);
		swapModelsNoGui(Armory.gun_mp, reg);
		swapModelsNoGui(Armory.gun_cryolator, reg);
		swapModelsNoGui(Armory.gun_jack, reg);
		swapModelsNoGui(Armory.gun_immolator, reg);
		swapModelsNoGui(Armory.gun_osipr, reg);
		swapModelsNoGui(Armory.gun_emp, reg);
		swapModels(Armory.gun_revolver_inverted, reg);
		swapModelsNoGui(Armory.gun_lever_action_sonata, reg);
		swapModelsNoGui(Armory.gun_bolt_action_saturnite, reg);
		swapModelsNoGui(Armory.gun_folly, reg);
		swapModelsNoGui(Armory.gun_dampfmaschine, reg);
		swapModelsNoGui(Armory.gun_calamity, reg);
		swapModelsNoGui(Armory.gun_calamity_dual, reg);
		swapModelsNoGui(Armory.gun_minigun, reg);
		swapModelsNoGui(Armory.gun_avenger, reg);
		swapModelsNoGui(Armory.gun_lacunae, reg);
		swapModelsNoGui(Armory.gun_lever_action_dark, reg);
		swapModelsNoGui(Armory.gun_bolt_action, reg);
		swapModelsNoGui(Armory.gun_bolt_action_green, reg);
		swapModelsNoGui(Armory.gun_uzi, reg);
		swapModelsNoGui(Armory.gun_uzi_silencer, reg);
		swapModelsNoGui(Armory.gun_uzi_saturnite, reg);
		swapModelsNoGui(Armory.gun_uzi_saturnite_silencer, reg);
		swapModelsNoGui(Armory.gun_mp40, reg);
		swapModels(ModItems.cell, reg);
		swapModels(ModItems.gas_canister, reg);
		swapModelsNoGui(ModItems.multitool_dig, reg);
		swapModelsNoGui(ModItems.multitool_silk, reg);
		swapModelsNoGui(ModItems.multitool_ext, reg);
		swapModelsNoGui(ModItems.multitool_miner, reg);
		swapModelsNoGui(ModItems.multitool_hit, reg);
		swapModelsNoGui(ModItems.multitool_beam, reg);
		swapModelsNoGui(ModItems.multitool_sky, reg);
		swapModelsNoGui(ModItems.multitool_mega, reg);
		swapModelsNoGui(ModItems.multitool_joule, reg);
		swapModelsNoGui(ModItems.multitool_decon, reg);
		swapModelsNoGui(ModItems.big_sword, reg);
		swapModelsNoGui(ModItems.shimmer_sledge, reg);
		swapModelsNoGui(ModItems.shimmer_axe, reg);
		swapModels(ModItems.ff_fluid_duct, reg);
		swapModels(ModItems.fluid_icon, reg);
		swapModelsNoGui(Armory.gun_brimstone, reg);
		swapModelsNoGui(ModItems.stopsign, reg);
		swapModelsNoGui(ModItems.sopsign, reg);
		swapModels(Armory.gun_ks23, reg);
		swapModels(Armory.gun_flamer, reg);
		swapModels(Armory.gun_flechette, reg);
		swapModels(Armory.gun_quadro, reg);
		swapModels(Armory.gun_sauer, reg);
		swapModelsNoGui(ModItems.chernobylsign, reg);
		swapModels(Item.getItemFromBlock(ModBlocks.radiorec), reg);
		swapModels(Armory.gun_vortex, reg);
		swapModels(Armory.gun_thompson, reg);
		swapModelsNoGui(ModItems.wood_gavel, reg);
		swapModelsNoGui(ModItems.lead_gavel, reg);
		swapModelsNoGui(ModItems.diamond_gavel, reg);
		swapModelsNoGui(ModItems.mese_gavel, reg);
		swapModels(Armory.gun_bolter, reg);
		swapModels(Ingots.ingot_steel_dusted, reg);
		swapModels(Ingots.ingot_chainsteel, reg);
		swapModels(Ingots.ingot_meteorite, reg);
		swapModels(Ingots.ingot_meteorite_forged, reg);
		swapModels(ModItems.blade_meteorite, reg);
		swapModels(Armory.crucible, reg);
		swapModels(Armory.hs_sword, reg);
		swapModels(Armory.hf_sword, reg);
		swapModels(Armory.cc_plasma_gun, reg);
		swapModels(Armory.gun_egon, reg);
		swapModels(Armory.jshotgun, reg);
		swapModels(Armory.gun_ar15, reg);
		
		swapModels(ToolSets.meteorite_sword_seared, reg);
		swapModels(ToolSets.meteorite_sword_reforged, reg);
		swapModels(ToolSets.meteorite_sword_hardened, reg);
		swapModels(ToolSets.meteorite_sword_alloyed, reg);
		swapModels(ToolSets.meteorite_sword_machined, reg);
		swapModels(ToolSets.meteorite_sword_treated, reg);
		swapModels(ToolSets.meteorite_sword_etched, reg);
		swapModels(ToolSets.meteorite_sword_bred, reg);
		swapModels(ToolSets.meteorite_sword_irradiated, reg);
		swapModels(ToolSets.meteorite_sword_fused, reg);
		swapModels(ToolSets.meteorite_sword_baleful, reg);
		swapModels(ToolSets.meteorite_sword_warped, reg);
		swapModels(ToolSets.meteorite_sword_demonic, reg);

		swapModels(ModItems.ore_bedrock, reg);
		swapModels(ModItems.ore_bedrock_centrifuged, reg);
		swapModels(ModItems.ore_bedrock_cleaned, reg);
		swapModels(ModItems.ore_bedrock_separated, reg);
		swapModels(ModItems.ore_bedrock_purified, reg);
		swapModels(ModItems.ore_bedrock_nitrated, reg);
		swapModels(ModItems.ore_bedrock_nitrocrystalline, reg);
		swapModels(ModItems.ore_bedrock_deepcleaned, reg);
		swapModels(ModItems.ore_bedrock_seared, reg);
		swapModels(ModItems.ore_bedrock_enriched, reg);
		swapModels(ModItems.ore_bedrock_exquisite, reg);
		swapModels(ModItems.ore_bedrock_perfect, reg);

		swapModels(ModItems.detonator_laser, reg);

		for (EvStyleItem styleItem : ElevatorStyles.styleItems)
			swapModels(styleItem,reg);

		swapModels(ModItems.pwr_piece, reg);
		swapModels(ModItems.pwr_shrapnel, reg);
		swapModels(ModItems.pwr_shard, reg);
		
		for(Entry<Item, ItemRenderBase> entry : ItemRenderLibrary.renderers.entrySet()){
			swapModels(entry.getKey(), reg);
		}

		MainRegistry.proxy.registerMissileItems(reg);
	}


	public static void swapModels(Item item, IRegistry<ModelResourceLocation, IBakedModel> reg) {
		ModelResourceLocation loc = new ModelResourceLocation(item.getRegistryName(), "inventory");
		IBakedModel model = reg.getObject(loc);
		TileEntityItemStackRenderer render = item.getTileEntityItemStackRenderer();
		if(render instanceof TEISRBase) {
			((TEISRBase) render).itemModel = model;
			reg.putObject(loc, new BakedModelCustom((TEISRBase) render));
		}
	}

	public static void swapModelsNoGui(Item item, IRegistry<ModelResourceLocation, IBakedModel> reg) {
		ModelResourceLocation loc = new ModelResourceLocation(item.getRegistryName(), "inventory");
		IBakedModel model = reg.getObject(loc);
		TileEntityItemStackRenderer render = item.getTileEntityItemStackRenderer();
		if(render instanceof TEISRBase) {
			((TEISRBase) render).itemModel = model;
			reg.putObject(loc, new BakedModelNoGui((TEISRBase) render));
		}
	}
	
	@SubscribeEvent
	public void itemColorsEvent(ColorHandlerEvent.Item evt) {
		evt.getItemColors().registerItemColorHandler((ItemStack stack, int tintIndex) -> {
			if(tintIndex == 1) {
				int j = TrackType.getEnum(stack.getItemDamage()).getColor();

				if(j < 0) {
					j = 0xFFFFFF;
				}

				return j;
			}
			return 0xFFFFFF;
		}, ModItems.siren_track);
	}

	@SubscribeEvent
	public void textureStitch(TextureStitchEvent.Pre evt) {
		DSmokeRenderer.sprites[0] = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "particle/d_smoke1"));
		DSmokeRenderer.sprites[1] = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "particle/d_smoke2"));
		DSmokeRenderer.sprites[2] = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "particle/d_smoke3"));
		DSmokeRenderer.sprites[3] = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "particle/d_smoke4"));
		DSmokeRenderer.sprites[4] = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "particle/d_smoke5"));
		DSmokeRenderer.sprites[5] = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "particle/d_smoke6"));
		DSmokeRenderer.sprites[6] = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "particle/d_smoke7"));
		DSmokeRenderer.sprites[7] = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "particle/d_smoke8"));
		ParticleDSmokeFX.sprites = DSmokeRenderer.sprites;

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/steam_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/steam_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/hotsteam_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/hotsteam_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/superhotsteam_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/superhotsteam_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/ultrahotsteam_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/ultrahotsteam_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/coolant_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/coolant_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/hotcoolant_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/hotcoolant_flowing"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/heavywater_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/heavywater_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/deuterium_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/deuterium_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/tritium_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/tritium_flowing"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/oil_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/oil_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/hotoil_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/hotoil_flowing"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/crackoil_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/crackoil_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/hotcrackoil_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/hotcrackoil_flowing"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/heavyoil_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/heavyoil_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/bitumen_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/bitumen_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/smear_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/smear_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/heatingoil_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/heatingoil_flowing"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/reclaimed_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/reclaimed_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/petroil_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/petroil_flowing"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/fracksol_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/fracksol_flowing"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/lubricant_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/lubricant_flowing"));

		// Yes yes I know, I spelled 'naphtha' wrong.
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/napatha_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/napatha_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/diesel_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/diesel_flowing"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/lightoil_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/lightoil_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/kerosene_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/kerosene_flowing"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/gas_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/gas_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/petroleum_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/petroleum_flowing"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/aromatics_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/aromatics_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/unsaturateds_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/unsaturateds_flowing"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/biogas_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/biogas_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/biofuel_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/biofuel_flowing"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/ethanol_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/ethanol_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/fishoil_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/fishoil_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/sunfloweroil_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/sunfloweroil_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/colloid_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/colloid_flowing"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/nitan_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/nitan_flowing"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/uf6_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/uf6_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/puf6_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/puf6_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/sas3_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/sas3_flowing"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/amat_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/amat_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/aschrab_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/aschrab_flowing"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/acid_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/acid_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/sulfuric_acid_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/sulfuric_acid_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/nitric_acid_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/nitric_acid_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/solvent_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/solvent_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/radiosolvent_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/radiosolvent_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/nitroglycerin_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/nitroglycerin_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/liquid_osmiridium_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/liquid_osmiridium_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/watz_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/watz_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/cryogel_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/cryogel_flowing"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/hydrogen_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/hydrogen_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/oxygen_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/oxygen_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/carbondioxide_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/carbondioxide_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/xenon_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/xenon_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/balefire_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/balefire_flowing"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/mercury_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/mercury_flowing"));
		
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/plasma_dt_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/plasma_dt_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/plasma_hd_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/plasma_hd_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/plasma_ht_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/plasma_ht_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/plasma_put_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/plasma_put_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/plasma_xm_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/plasma_xm_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/plasma_bf_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/plasma_bf_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/uu_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/uu_flowing"));
		
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/gasoline_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/gasoline_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/experience_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/experience_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/spentsteam_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/spentsteam_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/pain_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/pain_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/wastefluid_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/wastefluid_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/wastegas_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/wastegas_flowing"));

		contrail = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID + ":particle/contrail"));
		particle_base = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "particle/particle_base"));
		fog = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "particle/fog"));
		uv_debug = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "misc/uv_debug"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "items/ore_bedrock_layer"));
	}

	@SubscribeEvent
	public void textureStitchPost(TextureStitchEvent.Post evt) {
		RenderStructureMarker.fac_ti[0][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/factory_titanium_hull");
		RenderStructureMarker.fac_ti[0][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/factory_titanium_hull");
		RenderStructureMarker.fac_ti[1][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/factory_titanium_hull");
		RenderStructureMarker.fac_ti[1][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/factory_titanium_furnace");
		RenderStructureMarker.fac_ti[2][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/factory_titanium_core");
		RenderStructureMarker.fac_ti[2][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/factory_titanium_core");

		RenderStructureMarker.reactor[0][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/reactor_element_top");
		RenderStructureMarker.reactor[0][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/reactor_element_side");
		RenderStructureMarker.reactor[1][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/reactor_computer");
		RenderStructureMarker.reactor[1][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/reactor_computer");
		RenderStructureMarker.reactor[2][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/reactor_control_top");
		RenderStructureMarker.reactor[2][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/reactor_control_side");
		RenderStructureMarker.reactor[3][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/brick_concrete");
		RenderStructureMarker.reactor[3][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/reactor_hatch");
		RenderStructureMarker.reactor[4][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/reactor_conductor_top");
		RenderStructureMarker.reactor[4][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/reactor_conductor_side");
		RenderStructureMarker.reactor[5][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/brick_concrete");
		RenderStructureMarker.reactor[5][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/brick_concrete");

		RenderStructureMarker.fusion[0][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/block_steel");
		RenderStructureMarker.fusion[0][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fusion_conductor_side_alt3");
		RenderStructureMarker.fusion[1][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fusion_heater_top");
		RenderStructureMarker.fusion[1][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fusion_heater_side");
		RenderStructureMarker.fusion[2][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/block_tungsten");
		RenderStructureMarker.fusion[2][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fusion_hatch");
		RenderStructureMarker.fusion[3][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fusion_motor_top_alt");
		RenderStructureMarker.fusion[3][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fusion_motor_side_alt");
		RenderStructureMarker.fusion[4][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fusion_center_top_alt");
		RenderStructureMarker.fusion[4][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fusion_center_side_alt");
		RenderStructureMarker.fusion[5][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fusion_center_top_alt");
		RenderStructureMarker.fusion[5][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fusion_core_side_alt");
		RenderStructureMarker.fusion[6][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/block_tungsten");
		RenderStructureMarker.fusion[6][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/block_tungsten");
		
		RenderStructureMarker.watz[0][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/reinforced_brick");
		RenderStructureMarker.watz[0][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/reinforced_brick");
		RenderStructureMarker.watz[1][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/reinforced_brick");
		RenderStructureMarker.watz[1][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/watz_hatch");
		RenderStructureMarker.watz[2][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/watz_control_top");
		RenderStructureMarker.watz[2][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/watz_control_side");
		RenderStructureMarker.watz[3][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/watz_end");
		RenderStructureMarker.watz[3][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/watz_end");
		RenderStructureMarker.watz[4][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/watz_conductor_top");
		RenderStructureMarker.watz[4][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/watz_conductor_side");
		RenderStructureMarker.watz[5][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/watz_computer");
		RenderStructureMarker.watz[5][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/watz_computer");
		RenderStructureMarker.watz[6][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/watz_cooler");
		RenderStructureMarker.watz[6][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/watz_cooler");
		RenderStructureMarker.watz[7][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/watz_element_top");
		RenderStructureMarker.watz[7][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/watz_element_side");

		RenderStructureMarker.fwatz[0][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fwatz_scaffold");
		RenderStructureMarker.fwatz[0][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fwatz_scaffold");
		RenderStructureMarker.fwatz[1][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fwatz_scaffold");
		RenderStructureMarker.fwatz[1][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fwatz_hatch");
		RenderStructureMarker.fwatz[2][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fwatz_cooler_top");
		RenderStructureMarker.fwatz[2][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fwatz_cooler");
		RenderStructureMarker.fwatz[3][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fwatz_tank");
		RenderStructureMarker.fwatz[3][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fwatz_tank");
		RenderStructureMarker.fwatz[4][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/block_combine_steel");
		RenderStructureMarker.fwatz[4][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fwatz_conductor_side");
		RenderStructureMarker.fwatz[5][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fwatz_computer");
		RenderStructureMarker.fwatz[5][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fwatz_computer");
		RenderStructureMarker.fwatz[6][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fwatz_core");
		RenderStructureMarker.fwatz[6][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fwatz_core");

		RenderMultiblock.structLauncher = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/struct_launcher");
		RenderMultiblock.structScaffold = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/struct_scaffold");

		RenderSoyuzMultiblock.blockIcons[0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/struct_launcher");
		RenderSoyuzMultiblock.blockIcons[1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/concrete_smooth");
		RenderSoyuzMultiblock.blockIcons[2] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/struct_scaffold");
	}

	public static TextureAtlasSprite contrail;
	public static TextureAtlasSprite particle_base;
	public static TextureAtlasSprite fog;
	public static TextureAtlasSprite uv_debug;

	// All of these are called via coremod, EntityRenderer on line 1018. current
	// is the current value for each, and the returned value is added to the
	// current
	public static float getRLightmapColor(float current) {
		return 0.0F;
	}

	public static float getGLightmapColor(float current) {
		return 0.0F;
	}

	public static float getBLightmapColor(float current) {
		return 0.0F;
	}

	// Drillgon200: All this random flashlight shader stuff was ultimately
	// abandoned because it would have caused too many mod incompatibilities and
	// isn't used anywhere. The coremod still exists, but has no transformers so it doesn't do anything.

	private static boolean sentUniforms = false;
	public static boolean renderingDepthOnly = false;

	// Called from asm via coremod, in ChunkRenderContainer#preRenderChunk
	@Deprecated
	public static void preRenderChunk(RenderChunk chunk) {
		if(!GeneralConfig.useShaders || renderingDepthOnly)
			return;
		GL20.glUniform3i(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "chunkPos"), chunk.getPosition().getX(), chunk.getPosition().getY(), chunk.getPosition().getZ());
	}

	// Called from asm via coremod, in Profiler#endStartSection
	@Deprecated
	public static void profilerStart(String name) {
		if(!GeneralConfig.useShaders || renderingDepthOnly)
			return;
		if(name.equals("terrain")) {
			HbmShaderManager.useShader(HbmShaderManager.flashlightWorld);
			GL20.glUniform1i(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "lightmap"), 1);
			GL20.glUniform1i(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "flashlightDepth"), 6);
			GL20.glUniform4f(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "colorMult"), 1.0F, 1.0F, 1.0F, 0.0F);

			if(!sentUniforms) {
				Flashlight.setUniforms();
				sentUniforms = true;
			}
		}
		if(name.equals("sky")) {
			HbmShaderManager.releaseShader();
		}
		if(name.equals("particles")) {
			HbmShaderManager.releaseShader();
		}
		if(name.equals("litParticles")) {
			if(!HbmShaderManager.isActiveShader(HbmShaderManager.flashlightWorld)) {
				HbmShaderManager.useShader(HbmShaderManager.flashlightWorld);
				GL20.glUniform1i(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "lightmap"), 1);
				GL20.glUniform1i(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "flashlightDepth"), 6);
				GL20.glUniform4f(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "colorMult"), 1.0F, 1.0F, 1.0F, 0.0F);
			}
		}
		if(name.equals("weather")) {
			HbmShaderManager.releaseShader();
		}
		if(name.equals("hand")) {
			HbmShaderManager.releaseShader();
		}
		if(name.equals("translucent")) {
			HbmShaderManager.useShader(HbmShaderManager.flashlightWorld);
			GL20.glUniform1i(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "lightmap"), 1);
			GL20.glUniform1i(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "flashlightDepth"), 6);
			GL20.glUniform4f(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "colorMult"), 1.0F, 1.0F, 1.0F, 0.0F);
		}
		if(name.equals("gui")) {
			HbmShaderManager.releaseShader();
		}
	}

	// Called from asm via coremod, in RenderManager#renderEntity
	@Deprecated
	public static void onEntityRender(Entity e) {
		LOGGER.info("I hate this! x1000");
		if(!GeneralConfig.useShaders || renderingDepthOnly)
			return;
		if(!HbmShaderManager.isActiveShader(HbmShaderManager.flashlightWorld)) {
			HbmShaderManager.useShader(HbmShaderManager.flashlightWorld);
			GL20.glUniform1i(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "lightmap"), 1);
			GL20.glUniform1i(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "flashlightDepth"), 6);
			GL20.glUniform4f(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "colorMult"), 1.0F, 1.0F, 1.0F, 0.0F);
		} else {
			GL20.glUniform4f(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "colorMult"), 1.0F, 1.0F, 1.0F, 0.0F);
		}
		if(e instanceof EntityLivingBase) {
			EntityLivingBase living = (EntityLivingBase) e;
			if(living.deathTime > 0 || living.hurtTime > 0) {
				GL20.glUniform4f(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "colorMult"), 1.0F, 0.0F, 0.0F, 0.3F);
			} else {
				GL20.glUniform4f(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "colorMult"), 1.0F, 1.0F, 1.0F, 0.0F);
			}
		}
	}

	// Called from asm via coremod, in TileEntityRendererDispatcher#render
	@Deprecated
	public static void onTileEntityRender(TileEntity t) {
		if(!GeneralConfig.useShaders || renderingDepthOnly)
			return;
		if(t instanceof TileEntityEndPortal || t instanceof TileEntityEndGateway) {
			HbmShaderManager.releaseShader();
		} else {
			if(!HbmShaderManager.isActiveShader(HbmShaderManager.flashlightWorld)) {
				HbmShaderManager.useShader(HbmShaderManager.flashlightWorld);
				GL20.glUniform1i(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "lightmap"), 1);
				GL20.glUniform1i(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "flashlightDepth"), 6);
				GL20.glUniform4f(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "colorMult"), 1.0F, 1.0F, 1.0F, 0.0F);
			} else {
				GL20.glUniform4f(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "colorMult"), 1.0F, 1.0F, 1.0F, 0.0F);
			}
		}
	}

	// Called from asm via coremod, in LeafiaGls#disableLighting
	@Deprecated
	public static void onLightingDisable() {
		if(HbmShaderManager.isActiveShader(HbmShaderManager.flashlightWorld)) {
			GL20.glUniform1i(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "lightingEnabled"), 0);
		}
	}

	// Called from asm via coremod, in LeafiaGls#enableLighting
	@Deprecated
	public static void onLightingEnable() {
		if(HbmShaderManager.isActiveShader(HbmShaderManager.flashlightWorld)) {
			GL20.glUniform1i(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "lightingEnabled"), 1);
		}
	}
	
	@SubscribeEvent
	public void renderTick(RenderTickEvent e){
		if (e.phase == Phase.START) {
			LeafiaGls.resetStacks();
		}
		EntityPlayer player = Minecraft.getMinecraft().player;
		if (player != null) {
			if(player.getHeldItemMainhand().getItem() instanceof ItemSwordCutter && ItemSwordCutter.clicked){
				updateMouseDelta();
				player.turn(deltaMouseX, deltaMouseY);
				float oldPitch = player.rotationPitch;
				float oldYaw = player.rotationYaw;
				float y = player.rotationYaw - ItemSwordCutter.yaw;
				if(y > ItemSwordCutter.MAX_DYAW){
					player.rotationYaw = ItemSwordCutter.yaw + ItemSwordCutter.MAX_DYAW;
				}
				if(y < -ItemSwordCutter.MAX_DYAW){
					player.rotationYaw = ItemSwordCutter.yaw - ItemSwordCutter.MAX_DYAW;
				}
				float p = player.rotationPitch - ItemSwordCutter.pitch;
				if(p > ItemSwordCutter.MAX_DPITCH){
					player.rotationPitch = ItemSwordCutter.pitch + ItemSwordCutter.MAX_DPITCH;
				}
				if(p < -ItemSwordCutter.MAX_DPITCH){
					player.rotationPitch = ItemSwordCutter.pitch - ItemSwordCutter.MAX_DPITCH;
				}
				player.prevRotationYaw += player.rotationYaw-oldYaw;
				player.prevRotationPitch += player.rotationPitch-oldPitch;
			}
			if (e.phase == Phase.END) {
				LeafiaGls._push();
				boolean needsUpdate = false;
				for (BigBruh shaderGroup : shaderGroups.values()) {
					LeafiaGls.matrixMode(5890);
					LeafiaGls.pushMatrix();
					LeafiaGls.loadIdentity();
					Minecraft mc = Minecraft.getMinecraft();
					Framebuffer mainCanvas = mc.getFramebuffer();
					if (shaderGroup != null)
					{
						if (lastW != mainCanvas.framebufferWidth || lastH != mainCanvas.framebufferHeight || needsUpdate) {
							lastW = mc.getFramebuffer().framebufferWidth;
							lastH = mc.getFramebuffer().framebufferHeight;
							shaderGroup.createBindFramebuffers(mainCanvas.framebufferWidth,mainCanvas.framebufferHeight);
							needsUpdate = true;
						}
						shaderGroup.render(e.renderTickTime);
					}
					LeafiaGls.popMatrix();
				}
				LeafiaGls._pop();
				/*
				//LeafiaGls.color(1.0F, 1.0F, 1.0F, 1.0F);
				LeafiaGls.enableBlend();
				LeafiaGls.enableDepth();
				LeafiaGls.enableAlpha();
				//LeafiaGls.enableFog();
				LeafiaGls.enableLighting();
				LeafiaGls.enableColorMaterial();*/
			}
		}
	}
	public static float getViewADS(EntityPlayer player) {
		if (player.isSneaking()) {
			boolean canADS = true;
			ItemStack main = player.getHeldItemMainhand();
			ItemStack sub = player.getHeldItemOffhand();
			if ((!main.isEmpty() || !sub.isEmpty()) && (main.isEmpty() != sub.isEmpty())) {
				Item holding = main.isEmpty() ? sub.getItem() : main.getItem();
				if (holding instanceof IHoldableWeapon) {
					IHoldableWeapon weapon = (IHoldableWeapon)holding;
					if (weapon.getADS() != 1f) {
						return weapon.getADS()*(main.isEmpty()  ? -1 : 1)*(player.getPrimaryHand().equals(EnumHandSide.RIGHT) ? 1 : -1);
					}
				}
			}
		}
		return 0;
	}
	@SubscribeEvent
	public void fovUpdate(FOVUpdateEvent e){
		EntityPlayer player = e.getEntity();
		float multiplier = 1.0F;
		if(player.getHeldItemMainhand().getItem() == Armory.gun_supershotgun && ItemGunShotty.hasHookedEntity(player.world, player.getHeldItemMainhand())) {
			multiplier *= 1.1F;
		}
		float viewADS = getViewADS(player);
		if (viewADS != 0)
			multiplier *= Math.abs(viewADS);
		multiplier *= IdkWhereThisShitBelongs.fovM;
		e.setNewfov(e.getFov()*multiplier);
	}
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public void fovModifier(EntityViewRenderEvent.FOVModifier e){
		currentFOV = e.getFOV();
	}
	
	@SubscribeEvent
	public void inputUpdate(InputUpdateEvent e) {
		EntityPlayer player = e.getEntityPlayer();
		if(player.getHeldItemMainhand().getItem() == Armory.gun_supershotgun && ItemGunShotty.hasHookedEntity(player.world, player.getHeldItemMainhand())) {
			MovementInput m = e.getMovementInput();
			//To make it extra responsive, swings faster if the player is swinging in the opposite direction.
			float coeff = 0.25F;
			if((ItemGunShotty.motionStrafe < 0 && m.moveStrafe > 0) || (ItemGunShotty.motionStrafe > 0 && m.moveStrafe < 0))
				coeff *= 2;
			ItemGunShotty.motionStrafe+=m.moveStrafe*coeff;
			m.moveStrafe = 0;
			m.moveForward = 0;
			//If the player jumps, add some velocity in their look direction (don't want to add velocity down though, so always increase y velocity by at least 1)
			if(m.jump) {
				Vec3d look = player.getLookVec().scale(0.75);
				player.motionX += look.x*1.5;
				player.motionY = 1 + MathHelper.clamp(look.y, 0, 1);
				player.motionZ += look.z*1.5;
				ItemGunShotty.setHookedEntity(player, player.getHeldItemMainhand(), null);
				PacketDispatcher.wrapper.sendToServer(new MeathookJumpPacket());
				m.jump = false;
			}
		}
		JetpackHandler.inputUpdate(e);
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void setNTMSkybox(ClientTickEvent event){
		if(event.phase == Phase.START && GeneralConfig.enableSkybox) {
			
			World world = Minecraft.getMinecraft().world;
			
			if(world != null && world.provider instanceof WorldProviderSurface && !RenderNTMSkybox.didLastRender) {
				
				IRenderHandler sky = world.provider.getSkyRenderer();
				if(!(sky instanceof RenderNTMSkybox)) {
					world.provider.setSkyRenderer(new RenderNTMSkybox(sky));
				}
			}
			
			RenderNTMSkybox.didLastRender = false;
		}
	}
	Map<ModBiome,Float> getBiomeRatios(Entity entity) {
		World world = entity.world;
		int mops = 0;
		Map<ModBiome,Integer> mop = new HashMap<>();
		BlockPos pos = entity.getPosition();
		for (int ox = -3; ox <= 3; ox++) {
			for (int oz = -3; oz <= 3; oz++) {
				Biome biome = world.getBiome(pos.add(ox*5,0,oz*5));
				if (biome instanceof ModBiome)
					mop.put((ModBiome)biome,mop.getOrDefault((ModBiome)biome,0)+1);
				mops++;
			}
		}
		Map<ModBiome,Float> map = new HashMap<>();
		for (Entry<ModBiome,Integer> entry : mop.entrySet())
			map.put(entry.getKey(),entry.getValue().floatValue()/mops);
		return map;
	}
	@SubscribeEvent
	public void overrideFog(EntityViewRenderEvent.RenderFogEvent event) {
		LeafiaGlStack stack = LeafiaGls.getLastStack();
		float density = stack.fogDensity;
		float start = stack.fogStart;
		float end = stack.fogEnd;
		float ogDensity = density;
		float ogStart = start;
		float ogEnd = end;
		for (Entry<ModBiome,Float> entry : getBiomeRatios(event.getEntity()).entrySet()) {
			{
				float delta = entry.getKey().getFogDensity(ogDensity)-density;
				density += delta*entry.getValue();
			}
			{
				float delta = entry.getKey().getFogStart(ogStart)-start;
				start += delta*entry.getValue();
			}
			{
				float delta = entry.getKey().getFogEnd(ogEnd)-end;
				end += delta*entry.getValue();
			}
		}
		density = (float)Math.max(Math.pow(IdkWhereThisShitBelongs.darkness*(IdkWhereThisShitBelongs.dustDisplayTicks/30f),0.1)*4,density);
		if (density != ogDensity)
			LeafiaGls.setFogDensity(start);
		if (start != ogStart)
			LeafiaGls.setFogStart(start);
		if (end != ogEnd)
			LeafiaGls.setFogEnd(end);
	}
	@SubscribeEvent
	public void setFogColor(EntityViewRenderEvent.FogColors event) {
		Entity entity = event.getEntity();
		World world = entity.world;
		Vec3d viewport = ActiveRenderInfo.projectViewFromEntity(entity,event.getRenderPartialTicks());
		BlockPos viewportPos = new BlockPos(viewport);
		IBlockState viewportState = world.getBlockState(viewportPos);
		Vec3d inMaterialColor = viewportState.getBlock().getFogColor(world, viewportPos, viewportState, entity, new Vec3d(1,0,0), (float)event.getRenderPartialTicks());

		Vec3d col = new Vec3d(event.getRed(),event.getGreen(),event.getBlue());
		for (Entry<ModBiome,Float> entry : getBiomeRatios(entity).entrySet()) {
			int code = entry.getKey().getFogColor();
			Vec3d add = new Vec3d(code>>>16&0xFF,code>>>8&0xFF,code&0xFF).scale(1/255d).subtract(col);
			double alpha = (1-(code>>24&0xFF)/255d)*entry.getValue();
			col = col.add(add.scale(alpha));
		}

		event.setRed((float)MathHelper.clamp(col.x+(1-IdkWhereThisShitBelongs.darkness)*IdkWhereThisShitBelongs.infernal*0.7/1.5,0,1));
		event.setGreen((float)MathHelper.clamp(col.y+(1-IdkWhereThisShitBelongs.darkness)*IdkWhereThisShitBelongs.infernal*0.4/1.5,0,1));
		event.setBlue((float)MathHelper.clamp(col.z+(1-IdkWhereThisShitBelongs.darkness)*IdkWhereThisShitBelongs.infernal*0.1/1.5,0,1));

		//event.setRed((float)inMaterialColor.x);
		//event.setGreen((float)inMaterialColor.y);
		//event.setBlue((float)inMaterialColor.z);
	}
	
	@SubscribeEvent
	public void clientTick(ClientTickEvent e) {
		if(e.phase == Phase.END) {
			if(!firstPersonAuxParticles.isEmpty()){
				Iterator<ParticleFirstPerson> i = firstPersonAuxParticles.iterator();
				while(i.hasNext()){
					Particle p = i.next();
					p.onUpdate();
					if(!p.isAlive()){
						i.remove();
						continue;
					}
				}
			}
			Iterator<EntityLivingBase> itr = specialDeathEffectEntities.iterator();
			while(itr.hasNext()){
				Entity ent = itr.next();
				if(ent.isDead)
					itr.remove();
			}
			EntityPlayer player = Minecraft.getMinecraft().player;
			if(player != null) {
				boolean isHooked = player.getHeldItemMainhand().getItem() == Armory.gun_supershotgun && ItemGunShotty.hasHookedEntity(player.world, player.getHeldItemMainhand());
				if(isHooked)
					player.distanceWalkedModified = player.prevDistanceWalkedModified; //Stops the held shotgun from bobbing when hooked
			}
			if (Minecraft.getMinecraft().world != null)
				LeafiaPassiveLocal.priorTick(Minecraft.getMinecraft().world);
		} else {
			if(Minecraft.getMinecraft().world != null){
				LeafiaPassiveLocal.onTick(Minecraft.getMinecraft().world);
				//Drillgon200: If I add more guns like this, I'll abstract it.
				for(EntityPlayer player : Minecraft.getMinecraft().world.playerEntities){
					if(player.getHeldItemMainhand().getItem() == Armory.gun_egon && !ItemGunEgon.soundsByPlayer.containsKey(player)){
						boolean firing = player == Minecraft.getMinecraft().player ? ItemGunEgon.m1 && Library.countInventoryItem(player.inventory, ItemGunEgon.getBeltType(player, player.getHeldItemMainhand(), true)) >= 2 : ItemGunEgon.getIsFiring(player.getHeldItemMainhand());
						if(firing){
							ItemGunEgon.soundsByPlayer.put(player, new GunEgonSoundHandler(player));
						}
					}
				}
			}
			Iterator<GunEgonSoundHandler> itr = ItemGunEgon.soundsByPlayer.values().iterator();
			while(itr.hasNext()){
				GunEgonSoundHandler g = itr.next();
				g.update();
				if(g.ticks == -1)
					itr.remove();
			}
		}
		if(Minecraft.getMinecraft().player != null){
			JetpackHandler.clientTick(e);
			if (e.phase == Phase.END) {
				if (Minecraft.getMinecraft().world != null) {
					List<TileEntity> entities = Minecraft.getMinecraft().world.loadedTileEntityList;
					BlockPos pos = Minecraft.getMinecraft().player.getPosition();
					if (validatedTEs.size() > 0) {
						Set<TileEntity> removalQueue = new HashSet<>();
						for (TileEntity entity : validatedTEs) {
							if (!entities.contains(entity) || !(entity instanceof LeafiaPacketReceiver))
								removalQueue.add(entity);
							else if (!entity.isInvalid()) {
								LeafiaPacketReceiver receiver = (LeafiaPacketReceiver)entity;
								if (entity.getPos().getDistance(pos.getX(),pos.getY(),pos.getZ()) > receiver.affectionRange()*1.25) {
									removalQueue.add(entity);
								}
							}
						}
						for (TileEntity entity : removalQueue) {
							validatedTEs.remove(entity);
						}
					}
					for (TileEntity entity : entities) {
						if (!entity.isInvalid() && entity instanceof LeafiaPacketReceiver && !validatedTEs.contains(entity)) {
							LeafiaPacketReceiver receiver = (LeafiaPacketReceiver)entity;
							if (entity.getPos().getDistance(pos.getX(),pos.getY(),pos.getZ()) <= receiver.affectionRange()) {
								validatedTEs.add(entity);
								LeafiaPacket._validate(entity);
							}
						}
					}
				}
				LeafiaShakecam.localTick();
				IdkWhereThisShitBelongs.localTick();
				EntityNukeFolkvangr.FolkvangrVacuumPacket.Handler.localTick();
				for (String s : shaderGroups.keySet()) {
					BigBruh shader = shaderGroups.get(s);
					switch(s) {
						case "tom":
							shader.accessor.get("intensity").set((float)(IdkWhereThisShitBelongs.darkness)*(IdkWhereThisShitBelongs.dustDisplayTicks/30f)/2f);
							break;
						case "nuclear":
							shader.accessor.get("blur").set(LeafiaShakecam.blurSum);
							shader.accessor.get("bloom").set(LeafiaShakecam.bloomSum);
							break;
					}
				}
			}
		}
	}
	final Set<TileEntity> validatedTEs = new HashSet<>();
	
	//Sus
	@SubscribeEvent
	public void onArmorRenderEvent(RenderPlayerEvent.Pre event){
		EntityPlayer player = event.getEntityPlayer();
		GL11.glPushMatrix();
		GL11.glTranslated(0, player.isSneaking() ? 1.1 : 1.4, 0);
		GL11.glRotated(180, 0, 0, 1);
		
		for(int i = 0; i < 4; i++) {
			
			ItemStack armor = player.inventory.armorItemInSlot(i);
			
			if(armor != null && ArmorModHandler.hasMods(armor)) {
				
				for(ItemStack mod : ArmorModHandler.pryMods(armor)) {
					
					if(mod != null && mod.getItem() instanceof ItemArmorMod) {
						((ItemArmorMod)mod.getItem()).modRender(event, armor);
					}
				}
			}
			
			//because armor that isn't ItemArmor doesn't render at all
			if(armor != null && armor.getItem() instanceof JetpackBase) {
				((ItemArmorMod)armor.getItem()).modRender(event, armor);
			}
		}
		GL11.glPopMatrix();
	}
	
	@SubscribeEvent
	public void renderSpecificHand(RenderSpecificHandEvent e){
		if(Minecraft.getMinecraft().player.getHeldItem(e.getHand()).getItem() == Armory.crucible){
			e.setCanceled(true);
			Minecraft.getMinecraft().getItemRenderer().renderItemInFirstPerson(Minecraft.getMinecraft().player, e.getPartialTicks(), e.getInterpolatedPitch(), EnumHand.MAIN_HAND, 0, Minecraft.getMinecraft().player.getHeldItem(e.getHand()), 0);
		} else if(e.getHand() == EnumHand.MAIN_HAND && Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemSwordCutter){
			Animation anim = HbmAnimations.getRelevantAnim(EnumHand.MAIN_HAND);
			if(anim != null && anim.animation != null){
				e.setCanceled(true);
				Minecraft.getMinecraft().getItemRenderer().renderItemInFirstPerson(Minecraft.getMinecraft().player, e.getPartialTicks(), e.getInterpolatedPitch(), EnumHand.MAIN_HAND, 0, Minecraft.getMinecraft().player.getHeldItem(e.getHand()), 0);
			}
		}
	}
	
	@SubscribeEvent
	public void cameraSetup(EntityViewRenderEvent.CameraSetup e){
		RecoilHandler.modifiyCamera(e);
		JetpackHandler.handleCameraTransform(e);
		IdkWhereThisShitBelongs.shakeCam();
		LeafiaShakecam.shakeCam();
	}
	
	FloatBuffer MODELVIEW = GLAllocation.createDirectFloatBuffer(16);
	FloatBuffer PROJECTION = GLAllocation.createDirectFloatBuffer(16);
	IntBuffer VIEWPORT = GLAllocation.createDirectIntBuffer(16);
	FloatBuffer POSITION = GLAllocation.createDirectFloatBuffer(4);

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void renderWorld(RenderWorldLastEvent evt) {
		HbmShaderManager2.createInvMVP();
		LeafiaGls.enableDepth();
		List<Entity> list = Minecraft.getMinecraft().world.loadedEntityList;
		ClientProxy.renderingConstant = true;

		Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
		float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
		double d3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks;
		double d4 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks;
		double d5 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks;
		for(Entity e : list) {
			if(e instanceof IConstantRenderer) {
				double d0 = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double) partialTicks;
				double d1 = e.lastTickPosY + (e.posY - e.lastTickPosY) * (double) partialTicks;
				double d2 = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double) partialTicks;
				float f = e.prevRotationYaw + (e.rotationYaw - e.prevRotationYaw) * partialTicks;

				Render<Entity> r = Minecraft.getMinecraft().getRenderManager().getEntityRenderObject(e);
				r.doRender(e, d0 - d3, d1 - d4, d2 - d5, f, partialTicks);
			}
		}
		ClientProxy.renderingConstant = false;

		//SSG meathook icon projection
		if(ItemGunShotty.rayTrace != null) {
			GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, MODELVIEW);
			GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, PROJECTION);
			GL11.glGetInteger(GL11.GL_VIEWPORT, VIEWPORT);

			Project.gluProject((float) (ItemGunShotty.rayTrace.x - d3), (float) (ItemGunShotty.rayTrace.y - d4), (float) (ItemGunShotty.rayTrace.z - d5), MODELVIEW, PROJECTION, VIEWPORT, POSITION);

			ItemGunShotty.screenPos = new Vec2(POSITION.get(0), POSITION.get(1));
		} else {
			ItemGunShotty.screenPos = new Vec2(Minecraft.getMinecraft().displayWidth / 2, Minecraft.getMinecraft().displayHeight / 2);
		}

		//SSG meathook chain rendering
		ItemStack stack = Minecraft.getMinecraft().player.getHeldItemMainhand();
		if(ItemGunShotty.hasHookedEntity(Minecraft.getMinecraft().world, stack)) {
			Entity e = ItemGunShotty.getHookedEntity(Minecraft.getMinecraft().world, stack);
			
			//Left/right, up/down, forward/backward
			Vec3d ssgChainPos = new Vec3d(-0.08, -0.1, 0.35);
			ssgChainPos = ssgChainPos.rotatePitch((float) Math.toRadians(-(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks)));
			ssgChainPos = ssgChainPos.rotateYaw((float) Math.toRadians(-(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks)));

			ssgChainPos = ssgChainPos.add(0, entity.getEyeHeight(), 0);
			
			double d0 = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double) partialTicks;
			double d1 = e.lastTickPosY + (e.posY - e.lastTickPosY) * (double) partialTicks;
			double d2 = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double) partialTicks;
			Vec3d tester = new Vec3d(d0 - d3, d1 + e.getEyeHeight()*0.75 - d4, d2 - d5).subtract(ssgChainPos);

			double yaw = Math.toDegrees(Math.atan2(tester.x, tester.z));
			double sqrt = MathHelper.sqrt(tester.x * tester.x + tester.z * tester.z);
			double pitch = Math.toDegrees(Math.atan2(tester.y, sqrt));

			GL11.glPushMatrix();
			LeafiaGls.translate(ssgChainPos.x, ssgChainPos.y, ssgChainPos.z);
			GL11.glRotated(yaw + 90, 0, 1, 0);
			GL11.glRotated(-pitch + 90, 0, 0, 1);
			GL11.glScaled(0.125, 0.25, 0.125);
			
			double len = MathHelper.clamp(tester.length()*2, 0, 40);

			RenderHelper.bindTexture(ResourceManager.universal);
			LeafiaGls.enableLighting();
			CompositeBrush.instance.startDrawing(GL11.GL_TRIANGLES);
			for(int i = 0; i < Math.ceil(len); i++) {
				float offset = 0;
				if(ItemGunShotty.motionStrafe != 0){
					if(i < len*0.75){
						offset = (float) Library.smoothstep(i, 0, len*0.5);
					} else {
						offset = 1-(float) Library.smoothstep(i, len*0.5, len);
					}
					if(ItemGunShotty.motionStrafe > 0)
						offset = -offset;
				}
				float scale = (float) (len/20F);
				CompositeBrush.instance.setTranslation(0, i, offset*scale);
				ResourceManager.n45_chain.tessellateAll(CompositeBrush.instance);
			}

			CompositeBrush.instance.draw();
			GL11.glPopMatrix();
		}		
		
		int dist = 300;
		int x = 0;
		int y = 500;
		int z = 0;
		
		Vec3 vec = Vec3.createVectorHelper(x - d3, y - d4, z - d5);
		
		if(vec.length() < dist) {
			GL11.glPushMatrix();
			GL11.glTranslated(vec.xCoord, vec.yCoord, vec.zCoord);
			
			
            net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
			GL11.glRotated(80, 0, 0, 1);
			GL11.glRotated(30, 0, 1, 0);
			
	        double sine = Math.sin(System.currentTimeMillis() * 0.0005) * 5;
	        double sin3 = Math.sin(System.currentTimeMillis() * 0.0005 + Math.PI * 0.5) * 5;
	        GL11.glRotated(sine, 0, 0, 1);
	        GL11.glRotated(sin3, 1, 0, 0);
			
			GL11.glTranslated(0, -3, 0);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 6500F, 30F);
			SoyuzPronter.prontCapsule();

			GL11.glRotated(System.currentTimeMillis() * 0.025 % 360, 0, -1, 0);
			
			String msg = HTTPHandler.capsule;

			GL11.glTranslated(0, 3.75, 0);
			GL11.glRotated(180, 1, 0, 0);
			
			float rot = 0F;
			
			//looks dumb but we'll use this technology for the cyclotron
			for(char c : msg.toCharArray()) {
				GL11.glPushMatrix();

				GL11.glRotatef(rot, 0, 1, 0);

				float width = Minecraft.getMinecraft().fontRenderer.getStringWidth(msg);
				float scale = 5 / width;

				rot -= Minecraft.getMinecraft().fontRenderer.getCharWidth(c) * scale * 50;

				GL11.glTranslated(2, 0, 0);

				GL11.glRotatef(-90, 0, 1, 0);

				GL11.glScalef(scale, scale, scale);
				LeafiaGls.disableCull();
				Minecraft.getMinecraft().fontRenderer.drawString(String.valueOf(c), 0, 0, 0xff00ff);
				LeafiaGls.enableCull();
	    		GL11.glPopMatrix();
			}
			
            net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
    		
    		GL11.glPopMatrix();
		}
		
		
		if(ArmorFSB.hasFSBArmor(Minecraft.getMinecraft().player) && HbmCapability.getData(Minecraft.getMinecraft().player).getEnableHUD()) {
			ItemStack plate = Minecraft.getMinecraft().player.inventory.armorInventory.get(2);
			ArmorFSB chestplate = (ArmorFSB)plate.getItem();

			if(chestplate.thermal)
				RenderOverhead.renderThermalSight(evt.getPartialTicks());
		}
		
		if(entity instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) entity;
			net.minecraft.client.renderer.Tessellator tes = net.minecraft.client.renderer.Tessellator.getInstance();
			BufferBuilder buf = tes.getBuffer();
			if(player.getHeldItemMainhand().getItem() instanceof ItemSwordCutter && ItemSwordCutter.clicked){
				if(Mouse.isButtonDown(1) && ItemSwordCutter.startPos != null){
					/*ItemSwordCutter.x += deltaMouseX*0.01F;
					ItemSwordCutter.y -= deltaMouseY*0.01F;
					if(ItemSwordCutter.x + ItemSwordCutter.y == 0){
						ItemSwordCutter.x = 1F;
					}
					double lenRcp = 1D/Math.sqrt(ItemSwordCutter.x*ItemSwordCutter.x+ItemSwordCutter.y*ItemSwordCutter.y);
					ItemSwordCutter.x *= lenRcp;
					ItemSwordCutter.y *= lenRcp;
					double angle = Math.atan2(ItemSwordCutter.y, ItemSwordCutter.x);
					GL11.glPushMatrix();
					GL11.glTranslated(0, player.getEyeHeight(), 0);
					GL11.glRotated(-player.rotationYaw-90, 0, 1, 0);
					GL11.glRotated(-player.rotationPitch, 0, 0, 1);
					GL11.glTranslated(-0.3, 0, 0);
					GL11.glRotated(Math.toDegrees(angle), 1, 0, 0);
					GL11.glTranslated(0, 0.2, 0);
					LeafiaGls.disableCull();
					LeafiaGls.disableTexture2D();
					LeafiaGls.enableBlend();
					LeafiaGls.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
					LeafiaGls.color(0.7F, 0.7F, 0.7F, 0.4F);
					buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
					buf.pos(0, 0, -2).endVertex();
					buf.pos(3, 0, -2).endVertex();
					buf.pos(3, 0, 2).endVertex();
					buf.pos(0, 0, 2).endVertex();
					tes.draw();
					LeafiaGls.enableTexture2D();
					LeafiaGls.disableBlend();
					LeafiaGls.enableCull();
					
					Vec3d[] positions = BobMathUtil.worldFromLocal(new Vector4f(0, 0, -2, 1), new Vector4f(3, 0, -2, 1), new Vector4f(3, 0, 2, 1));
					Vec3d norm = positions[1].subtract(positions[0]).crossProduct(positions[2].subtract(positions[0])).normalize();
					ItemSwordCutter.plane = new Vec3d[]{positions[0], norm};
					GL11.glPopMatrix();
					LeafiaGls.disableTexture2D();
					LeafiaGls.color(1F, 0F, 0F, 1F);
					buf.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
					Vec3d pos1 = positions[1].subtract(player.getPositionEyes(partialTicks));
					buf.pos(pos1.x, pos1.y+player.getEyeHeight(), pos1.z).endVertex();
					buf.pos(pos1.x+norm.x, pos1.y+norm.y+player.getEyeHeight(), pos1.z+norm.z).endVertex();
					tes.draw();
					LeafiaGls.enableTexture2D();
					player.turn(deltaMouseX, deltaMouseY);
					LeafiaGls.color(1F, 1F, 1F, 1F);*/
					if(!(player.getHeldItemMainhand().getItem() instanceof ItemCrucible && ItemCrucible.getCharges(player.getHeldItemMainhand()) == 0)){
						Vec3d pos1 = ItemSwordCutter.startPos;
						Vec3d pos2 = player.getLook(partialTicks);
						Vec3d norm = ItemSwordCutter.startPos.crossProduct(player.getLook(partialTicks));
						LeafiaGls.disableTexture2D();
						LeafiaGls.color(0, 0, 0, 1);
						GL11.glPushMatrix();
						GL11.glLoadIdentity();
						
						GL11.glRotated(player.rotationPitch, 1, 0, 0);
						GL11.glRotated(player.rotationYaw+180, 0, 1, 0);
						
						buf.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
						buf.pos(pos1.x, pos1.y, pos1.z).endVertex();
						buf.pos(pos2.x, pos2.y, pos2.z).endVertex();
						tes.draw();
						GL11.glPopMatrix();
						LeafiaGls.color(1, 1, 1, 1);
						LeafiaGls.enableTexture2D();
						if(norm.lengthSquared() > 0.001F){
							ItemSwordCutter.planeNormal = norm.normalize();
						}
					} else {
						ItemSwordCutter.clicked = false;
						ItemSwordCutter.planeNormal = null;
					}
				} else {
					ItemSwordCutter.clicked = false;
					ItemSwordCutter.planeNormal = null;
				}
			}
			/*Vec3d euler = BobMathUtil.getEulerAngles(player.getLookVec());
			javax.vecmath.Matrix3f rot = BakedModelUtil.eulerToMat((float)Math.toRadians(euler.x), (float)Math.toRadians(euler.y+90), player.world.rand.nextFloat()*2F*(float)Math.PI);
			Vec3d c1 = new Vec3d(rot.m00, rot.m01, rot.m02);
			Vec3d c2 = new Vec3d(rot.m10, rot.m11, rot.m12);
			Vec3d c3 = new Vec3d(rot.m20, rot.m21, rot.m22);
			GL11.glPushMatrix();
			GL11.glTranslated(0, player.getEyeHeight(), 0);
			Vec3d look = player.getLook(partialTicks).scale(2);
			GL11.glTranslated(look.x, look.y, look.z);
			LeafiaGls.disableTexture2D();
			buf.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
			buf.pos(0, 0, 0).endVertex();
			buf.pos(c1.x, c1.y, c1.z).endVertex();
			buf.pos(0, 0, 0).endVertex();
			buf.pos(c2.x, c2.y, c2.z).endVertex();
			buf.pos(0, 0, 0).endVertex();
			buf.pos(c3.x, c3.y, c3.z).endVertex();
			tes.draw();
			LeafiaGls.enableTexture2D();
			GL11.glPopMatrix();*/
			
			//GLUON GUN//
			if(player.getHeldItemMainhand().getItem() == Armory.gun_egon && ItemGunEgon.activeTicks > 0 && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0){
				GL11.glPushMatrix();
				float[] angles = ItemGunEgon.getBeamDirectionOffset(player.world.getTotalWorldTime()+partialTicks);
				Vec3d look = Library.changeByAngle(player.getLook(partialTicks), angles[0], angles[1]);
				RayTraceResult r = Library.rayTraceIncludeEntitiesCustomDirection(player, look, 50, partialTicks);
				Vec3d pos = player.getPositionEyes(partialTicks);
				Vec3d hitPos = pos.add(look.scale(50));
				if(r == null || r.typeOfHit == Type.MISS){
				} else {
					hitPos = r.hitVec.add(look.scale(-0.1));
				}
				float[] offset = ItemRenderGunEgon.getOffset(player.world.getTotalWorldTime()+partialTicks);
				//I'll at least attempt to make it look consistent at different fovs
				float fovDiff = (currentFOV-70)*0.0002F;
				Vec3d start = new Vec3d(-0.18+offset[0]*0.075F-fovDiff, -0.2+offset[1]*0.1F, 0.35-fovDiff*30);
				start = start.rotatePitch((float) Math.toRadians(-(player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * partialTicks)));
				start = start.rotateYaw((float) Math.toRadians(-(player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * partialTicks)));

				start = start.add(0, player.getEyeHeight(), 0);
				GL11.glTranslated(start.x, start.y, start.z);
				BeamPronter.gluonBeam(new Vec3(0, 0, 0), new Vec3(hitPos.subtract(pos).subtract(start.subtract(0, player.getEyeHeight(), 0))), 0.4F);
				GL11.glPopMatrix();
				
			}
		}
		
		for(EntityPlayer player : Minecraft.getMinecraft().world.playerEntities){
			
			//FSB world rendering
			if(ArmorFSB.hasFSBArmor(player)){
				ItemStack plate = player.inventory.armorInventory.get(2);
				ArmorFSB chestplate = (ArmorFSB)plate.getItem();
				if(chestplate.flashlightPosition != null && plate.hasTagCompound() && plate.getTagCompound().getBoolean("flActive")){
					Vec3d start = chestplate.flashlightPosition.rotatePitch(-(float) Math.toRadians(player.rotationPitch)).rotateYaw(-(float) Math.toRadians(player.rotationYaw)).add(player.getPositionEyes(partialTicks));
					boolean volume = true;
					if(player == Minecraft.getMinecraft().player && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0)
						volume = false;
					LightRenderer.addFlashlight(start, start.add(player.getLook(partialTicks).scale(30)), 30, 200, ResourceManager.fl_cookie, volume, true, true, true);
				}
			}
			
			//Gun world rendering
			if(player.getHeldItemMainhand().getItem() instanceof ItemGunBase){
				((ItemGunBase)player.getHeldItemMainhand().getItem()).playerWorldRender(player, evt, EnumHand.MAIN_HAND);
			}
			if(player.getHeldItemOffhand().getItem() instanceof ItemGunBase){
				((ItemGunBase)player.getHeldItemOffhand().getItem()).playerWorldRender(player, evt, EnumHand.OFF_HAND);
			}
			
			//Gluon gun world rendering
			if(player.getHeldItemMainhand().getItem() != Armory.gun_egon){
				ItemGunEgon.activeTrailParticles.remove(player);
				continue;
			}
			boolean firing = player == Minecraft.getMinecraft().player ? ItemGunEgon.m1 && Library.countInventoryItem(player.inventory, ItemGunEgon.getBeltType(player, player.getHeldItemMainhand(), true)) >= 2 : ItemGunEgon.getIsFiring(player.getHeldItemMainhand());
			if(!firing){
				ItemGunEgon.activeTrailParticles.remove(player);
				continue;
			}
			float[] angles = ItemGunEgon.getBeamDirectionOffset(player.world.getTotalWorldTime()+partialTicks);
			Vec3d look = Library.changeByAngle(player.getLook(partialTicks), angles[0], angles[1]);
			RayTraceResult r = Library.rayTraceIncludeEntitiesCustomDirection(player, look, 50, partialTicks);
			if(r != null && r.hitVec != null && r.typeOfHit == Type.BLOCK){
				ParticleGluonBurnTrail currentTrailParticle = null;
				if(!ItemGunEgon.activeTrailParticles.containsKey(player)){
					currentTrailParticle = new ParticleGluonBurnTrail(player.world, 0.4F, player);
					Minecraft.getMinecraft().effectRenderer.addEffect(currentTrailParticle);
					ItemGunEgon.activeTrailParticles.put(player, currentTrailParticle);
				} else {
					currentTrailParticle = ItemGunEgon.activeTrailParticles.get(player);
				}
				Vec3d normal = Library.normalFromRayTrace(r);
				if(!currentTrailParticle.tryAddNewPosition(r.hitVec.add(normal.scale(0.02)), normal)){
					currentTrailParticle = null;
					ItemGunEgon.activeTrailParticles.remove(player);
				}
			} else {
				ItemGunEgon.activeTrailParticles.remove(player);
			}
		}

		TopRender.main(evt);
		
		for(Runnable r : ClientProxy.deferredRenderers){
			r.run();
		}
		ClientProxy.deferredRenderers.clear();

		HbmShaderManager2.blitDepth();
		
		ParticleBatchRenderer.renderLast(evt);
		
		LightRenderer.worldRender();
		//RenderHelper.renderFlashlights();
		
		//WorldSpaceFPRender.doHandRendering(evt);
		
		/*for(Particle p : firstPersonAuxParticles){
			if(p instanceof ParticlePhysicsBlocks)
				p.renderParticle(null, Minecraft.getMinecraft().getRenderViewEntity(), MainRegistry.proxy.partialTicks(), 0, 0, 0, 0, 0);
		}*/
		//HbmShaderManager2.doPostProcess();
		if(!(Minecraft.getMinecraft().player.getHeldItemMainhand().getItem() instanceof IPostRender || Minecraft.getMinecraft().player.getHeldItemOffhand().getItem() instanceof IPostRender)){
			HbmShaderManager2.postProcess();
		}
	}
	
	@SubscribeEvent
	public void renderHand(RenderHandEvent e){
		if(Minecraft.getMinecraft().player.getHeldItemMainhand().getItem() instanceof IPostRender || Minecraft.getMinecraft().player.getHeldItemOffhand().getItem() instanceof IPostRender){
			e.setCanceled(true);
			Minecraft mc = Minecraft.getMinecraft();
			boolean flag = mc.getRenderViewEntity() instanceof EntityLivingBase && ((EntityLivingBase)mc.getRenderViewEntity()).isPlayerSleeping();
			if (mc.gameSettings.thirdPersonView == 0 && !flag && !mc.gameSettings.hideGUI && !mc.playerController.isSpectator())
	        {
	            mc.entityRenderer.enableLightmap();
	            mc.entityRenderer.itemRenderer.renderItemInFirstPerson(e.getPartialTicks());
	            mc.entityRenderer.disableLightmap();
	        }
			HbmShaderManager2.postProcess();
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void cancelVanished(RenderLivingEvent.Pre<EntityLivingBase> event){
		if(MainRegistry.proxy.isVanished(event.getEntity())){
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void preRenderEvent(RenderLivingEvent.Pre<EntityLivingBase> event) {
		EntityPlayer player = Minecraft.getMinecraft().player;
		if(ArmorFSB.hasFSBArmor(player) && HbmCapability.getData(player).getEnableHUD()) {
			ItemStack plate = player.inventory.armorInventory.get(2);
			ArmorFSB chestplate = (ArmorFSB)plate.getItem();

			if(chestplate.vats) {

				int count = (int)Math.min(event.getEntity().getMaxHealth(), 100);

				int bars = (int)Math.ceil(event.getEntity().getHealth() * count / event.getEntity().getMaxHealth());

				String bar = TextFormatting.RED + "";

				for(int i = 0; i < count; i++) {

					if(i == bars)
						bar += TextFormatting.RESET + "";

						bar += "|";
				}
				RenderOverhead.renderTag(event.getEntity(), event.getX(), event.getY(), event.getZ(), event.getRenderer(), bar, chestplate.thermal);
			}
		}
	}

	public boolean hasBauble(EntityPlayer player, Item bauble){
		try{
			if(BaublesApi.isBaubleEquipped(player, bauble) != -1){
				return true;
			}
		} catch(Throwable t){
		}
		return false;
	}

	@SubscribeEvent
	public void onOverlayRender(RenderGameOverlayEvent.Pre event) {
		EntityPlayer player = Minecraft.getMinecraft().player;
		if(event.getType() == ElementType.CROSSHAIRS && player.getHeldItemMainhand().getItem() == Armory.gun_supershotgun && !ItemGunShotty.hasHookedEntity(player.world, player.getHeldItemMainhand())) {
			float x1 = ItemGunShotty.prevScreenPos.x + (ItemGunShotty.screenPos.x - ItemGunShotty.prevScreenPos.x) * event.getPartialTicks();
			float y1 = ItemGunShotty.prevScreenPos.y + (ItemGunShotty.screenPos.y - ItemGunShotty.prevScreenPos.y) * event.getPartialTicks();
			float x = BobMathUtil.remap(x1, 0, Minecraft.getMinecraft().displayWidth, 0, event.getResolution().getScaledWidth());
			float y = event.getResolution().getScaledHeight() - BobMathUtil.remap(y1, 0, Minecraft.getMinecraft().displayHeight, 0, event.getResolution().getScaledHeight());
			RenderHelper.bindTexture(ResourceManager.meathook_marker);
			LeafiaGls.color(1.0F, 1.0F, 1.0F, 1.0F);
			LeafiaGls.enableBlend();
			LeafiaGls.tryBlendFuncSeparate(SourceFactor.ONE_MINUS_DST_COLOR, DestFactor.ONE_MINUS_SRC_COLOR, SourceFactor.ONE, DestFactor.ZERO);
			RenderHelper.drawGuiRect(x - 2.5F, y - 2.5F, 0, 0, 5, 5, 1, 1);
			LeafiaGls.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
			LeafiaGls.disableBlend();
		}
		/// HANDLE GUN AND AMMO OVERLAYS ///
		if(player.getHeldItem(EnumHand.MAIN_HAND) != null && player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemGunBase) {
			((IItemHUD)player.getHeldItem(EnumHand.MAIN_HAND).getItem()).renderHUD(event, event.getType(), player, player.getHeldItem(EnumHand.MAIN_HAND), EnumHand.MAIN_HAND);
		}

		if(player.getHeldItem(EnumHand.OFF_HAND) != null && player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof ItemGunBase) {
			((IItemHUD)player.getHeldItem(EnumHand.OFF_HAND).getItem()).renderHUD(event, event.getType(), player, player.getHeldItem(EnumHand.OFF_HAND), EnumHand.OFF_HAND);
		}

		/// HANDLE GEIGER COUNTER AND JETPACK HUD ///
		if(event.getType() == ElementType.HOTBAR) {
			if(!(ArmorFSB.hasFSBArmorHelmet(player) && ((ArmorFSB)player.inventory.armorInventory.get(3).getItem()).customGeiger)) {
				if(Library.hasInventoryItem(player.inventory, ModItems.geiger_counter) || hasBauble(player, ModItems.geiger_counter)) {
	
					float rads = (float)Library.getEntRadCap(player).getRads();
	
					RenderScreenOverlay.renderRadCounter(event.getResolution(), rads, Minecraft.getMinecraft().ingameGUI);
				}
			}
			if(Library.hasInventoryItem(player.inventory, ModItems.digamma_diagnostic) || hasBauble(player, ModItems.digamma_diagnostic)) {
	
				float digamma = (float)Library.getEntRadCap(player).getDigamma();

				RenderScreenOverlay.renderDigCounter(event.getResolution(), digamma, Minecraft.getMinecraft().ingameGUI);
			}
			if(JetpackHandler.hasJetpack(player)){
				JetpackHandler.renderHUD(player, event.getResolution());
			}
		}
		
		/// DODD DIAG HOOK FOR RBMK
		if(event.getType() == ElementType.CROSSHAIRS) {
			Minecraft mc = Minecraft.getMinecraft();
			World world = mc.world;
			RayTraceResult mop = mc.objectMouseOver;
			
			if(mop != null && mop.typeOfHit == mop.typeOfHit.BLOCK) {
				if(world.getBlockState(mop.getBlockPos()).getBlock() instanceof ILookOverlay) {
					((ILookOverlay) world.getBlockState(mop.getBlockPos()).getBlock()).printHook(event, world, mop.getBlockPos().getX(), mop.getBlockPos().getY(), mop.getBlockPos().getZ());
				}
				if (mc.player.getHeldItemOffhand().getItem() == ModItems.wand_d) {
					Chunk chunk = world.getChunk(mop.getBlockPos());
					TileEntity entity = chunk.getTileEntity(mop.getBlockPos(),Chunk.EnumCreateEntityType.CHECK);
					if (entity != null) {
						NBTTagCompound nbt = new NBTTagCompound();
						entity.writeToNBT(nbt);
						LeafiaGls.pushMatrix();
						LeafiaGls.scale(0.6,0.6,1);
						mc.fontRenderer.drawStringWithShadow("Replicated blockdata",4,4,LeafiaUtil.colorFromTextFormat(TextFormatting.GREEN));
						int textX = 10;
						int textY = 4;
						List<Triplet<Integer,Integer,List<Pair<String,NBTBase>>>> stack = new ArrayList<>();
						stack.add(new Triplet<>(0,0,new ArrayList<>()));
						for (String key : nbt.getKeySet()) {
							stack.get(0).getC().add(new Pair<>(key,nbt.getTag(key)));
						}
						while (stack.size() > 0) {
							Triplet<Integer,Integer,List<Pair<String,NBTBase>>> stackItem = stack.get(stack.size()-1);
							List<Pair<String,NBTBase>> compound = stackItem.getC();
							if (compound.size() > 0) {
								Pair<String,NBTBase> entry = compound.remove(0);
								textY += 10;
								String lineTxt = (entry.getA() != null) ? TextFormatting.YELLOW+"["+entry.getA()+"] " : "["+stackItem.getB()+"] ";
								stackItem.setB(stackItem.getB()+1);
								NBTBase value = entry.getB();
								if (value instanceof NBTTagByte)
									lineTxt += TextFormatting.BLUE + "" + ((NBTTagByte) value).getByte();
								if (value instanceof NBTTagShort)
									lineTxt += TextFormatting.DARK_AQUA + "" + ((NBTTagShort) value).getShort();
								if (value instanceof NBTTagInt)
									lineTxt += TextFormatting.AQUA + "" + ((NBTTagInt) value).getInt();
								if (value instanceof NBTTagLong)
									lineTxt += TextFormatting.GOLD + "" + ((NBTTagLong) value).getLong();
								if (value instanceof NBTTagFloat)
									lineTxt += TextFormatting.GREEN + "" + ((NBTTagFloat) value).getFloat() + "f";
								if (value instanceof NBTTagDouble)
									lineTxt += TextFormatting.RED + "" + ((NBTTagDouble) value).getDouble() + "d";
								if (value instanceof NBTTagByteArray)
									lineTxt += TextFormatting.DARK_GRAY + "" + value;
								if (value instanceof NBTTagIntArray)
									lineTxt += TextFormatting.DARK_GRAY + "" + value;
								if (value instanceof NBTTagLongArray)
									lineTxt += TextFormatting.GRAY + "" + value;
								if (value instanceof NBTTagList) {
									lineTxt += TextFormatting.RESET + "[";
									List<Pair<String,NBTBase>> subCompound = new ArrayList<>();
									for (NBTBase item : ((NBTTagList) value)) {
										subCompound.add(new Pair<>(null,item));
									}
									stack.add(new Triplet<>(1,0,subCompound));
								} if (value instanceof NBTTagString)
									lineTxt += TextFormatting.LIGHT_PURPLE + "" + ((NBTTagString)value).getString();
								if (value instanceof NBTTagCompound) {
									lineTxt += "{";
									List<Pair<String,NBTBase>> subCompound = new ArrayList<>();
									NBTTagCompound nbtValue = (NBTTagCompound)value;
									for (String key : nbtValue.getKeySet()) {
										subCompound.add(new Pair<>(key,nbtValue.getTag(key)));
									}
									stack.add(new Triplet<>(2,0,subCompound));
								}
								mc.fontRenderer.drawStringWithShadow(lineTxt,textX,textY,-1);
								if (value instanceof NBTTagCompound)
									textX += 6;
								if (value instanceof NBTTagList)
									textX += 6;
							}
							if (stack.get(stack.size()-1).getC().size() <= 0) {
								switch(stack.get(stack.size()-1).getA()) {
									case 1:
										textX -= 6;
										textY += 10;
										mc.fontRenderer.drawStringWithShadow("]",textX,textY,LeafiaUtil.colorFromTextFormat(TextFormatting.WHITE));
										break;
									case 2:
										textX -= 6;
										textY += 10;
										mc.fontRenderer.drawStringWithShadow("}",textX,textY,LeafiaUtil.colorFromTextFormat(TextFormatting.YELLOW));
										break;
								}
								stack.remove(stack.size() - 1);
							}
						}
						LeafiaGls.popMatrix();
					}
				}
			}
			TileEntityRBMKBase.diagnosticPrintHook(event);
		}

		/// HANDLE ANIMATION BUSES ///

		if(event.getType() == ElementType.ALL){
			for(int i = 0; i < HbmAnimations.hotbar.length; i++) {
				Animation animation = HbmAnimations.hotbar[i];

				if(animation == null)
					continue;

				long time = System.currentTimeMillis() - animation.startMillis;

				int duration = 0;
				if(animation instanceof BlenderAnimation){
					BlenderAnimation banim = ((BlenderAnimation)animation);
					//duration = (int) Math.ceil(banim.wrapper.anim.length * (1F/Math.abs(banim.wrapper.speedScale)));
					EnumHand hand = i < 9 ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
					if(!Minecraft.getMinecraft().player.getHeldItem(hand).getTranslationKey().equals(banim.key))
						HbmAnimations.hotbar[i] = null;
					if(animation.animation != null){
						if(time > animation.animation.getDuration()){
							animation.animation = null;
						}
					}
				} else {
					duration = animation.animation.getDuration();
					if(time > duration)
						HbmAnimations.hotbar[i] = null;
				}
				
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_O) && Minecraft.getMinecraft().currentScreen == null) {
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(0, 0, 0, 999, 0));
		}
		LeafiaGls.color(1.0F, 1.0F, 1.0F, 1.0F);
		ItemStack helmet = player.inventory.armorInventory.get(3);

		if(helmet.getItem() instanceof ArmorFSB) {
			((ArmorFSB)helmet.getItem()).handleOverlay(event, player);
		}

	}
	@SubscribeEvent
	public void postOverlayRender(RenderGameOverlayEvent.Post event) {
		if(event.getType() == ElementType.ALL) {
			RenderScreenOverlay.renderTomDust(event.getResolution(),Minecraft.getMinecraft().ingameGUI);
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void preRenderPlayer(RenderPlayerEvent.Pre evt) {
		PotionEffect invis = evt.getEntityPlayer().getActivePotionEffect(MobEffects.INVISIBILITY);

		if(invis != null && invis.getAmplifier() >= 0){
			evt.setCanceled(true);
			return;
		}
		// event.setCanceled(true);
		AbstractClientPlayer player = (AbstractClientPlayer) evt.getEntityPlayer();

		ModelPlayer renderer = evt.getRenderer().getMainModel();
		
		if(player.getHeldItem(EnumHand.MAIN_HAND) != null && player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof IHoldableWeapon) {
			renderer.rightArmPose = ArmPose.BOW_AND_ARROW;
			// renderer.getMainModel().bipedLeftArm.rotateAngleY = 90;
		}
		if(player.getHeldItem(EnumHand.OFF_HAND) != null && player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof IHoldableWeapon) {
			renderer.leftArmPose = ArmPose.BOW_AND_ARROW;
		}
		JetpackHandler.preRenderPlayer(player);
		if(player.getHeldItemMainhand().getItem() == Armory.gun_egon){
			EgonBackpackRenderer.showBackpack = true;
		}
		
		ResourceLocation cloak = RenderAccessoryUtility.getCloakFromPlayer(player);
		// GL11.glRotated(180, 1, 0, 0);
		NetworkPlayerInfo info = Minecraft.getMinecraft().getConnection().getPlayerInfo(player.getUniqueID());
		if(cloak != null)
			RenderAccessoryUtility.loadCape(info, cloak);
	}

	@SubscribeEvent
	public void preRenderLiving(RenderLivingEvent.Pre<AbstractClientPlayer> event) {

		if(specialDeathEffectEntities.contains(event.getEntity())){
			event.setCanceled(true);
		}
		if(event.getEntity() instanceof AbstractClientPlayer && event.getRenderer().getMainModel() instanceof ModelBiped){
			AbstractClientPlayer player = (AbstractClientPlayer) event.getEntity();

			ModelBiped renderer = (ModelBiped) event.getRenderer().getMainModel();
			
			if(player.getHeldItem(EnumHand.MAIN_HAND) != null && player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof IHoldableWeapon) {
				renderer.rightArmPose = ArmPose.BOW_AND_ARROW;
			}
			if(player.getHeldItem(EnumHand.OFF_HAND) != null && player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof IHoldableWeapon) {
				renderer.leftArmPose = ArmPose.BOW_AND_ARROW;
			}
		}
	}
	
	@SubscribeEvent
	public void postRenderPlayer(RenderPlayerEvent.Post event) {
		JetpackHandler.postRenderPlayer(event.getEntityPlayer());
		EntityPlayer player = event.getEntityPlayer();
		//GLUON GUN//
		boolean firing = player == Minecraft.getMinecraft().player ? ItemGunEgon.m1 && Library.countInventoryItem(player.inventory, ItemGunEgon.getBeltType(player, player.getHeldItemMainhand(), true)) >= 2 : ItemGunEgon.getIsFiring(player.getHeldItemMainhand());
		EgonBackpackRenderer.showBackpack = false;
		if(player.getHeldItemMainhand().getItem() == Armory.gun_egon && firing){
			GL11.glPushMatrix();
			float partialTicks = event.getPartialRenderTick();
			float[] angles = ItemGunEgon.getBeamDirectionOffset(player.world.getTotalWorldTime()+partialTicks);
			Vec3d look = Library.changeByAngle(player.getLook(partialTicks), angles[0], angles[1]);
			RayTraceResult r = Library.rayTraceIncludeEntitiesCustomDirection(player, look, 50, event.getPartialRenderTick());
			Vec3d pos = player.getPositionEyes(event.getPartialRenderTick());
			Vec3d hitPos = pos.add(look.scale(50));
			if(r == null || r.typeOfHit == Type.MISS){
			} else {
				hitPos = r.hitVec.add(look.scale(-0.1));
			}
			Vec3d start = new Vec3d(-0.18, -0.1, 0.35);
			start = start.rotatePitch((float) Math.toRadians(-(player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * partialTicks)));
			start = start.rotateYaw((float) Math.toRadians(-(player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * partialTicks)));

			Vec3d diff = player.getPositionEyes(partialTicks).subtract(TileEntityRendererDispatcher.staticPlayerX, TileEntityRendererDispatcher.staticPlayerY, TileEntityRendererDispatcher.staticPlayerZ);
			GL11.glTranslated(start.x+diff.x, start.y+diff.y, start.z+diff.z);
			BeamPronter.gluonBeam(new Vec3(0, 0, 0), new Vec3(hitPos.subtract(pos)), 0.4F);
			GL11.glPopMatrix();
		}
	}

	@SubscribeEvent
	public void clickHandler(MouseEvent event) {
		EntityPlayer player = Minecraft.getMinecraft().player;
		if(event.getButton() == 1 && !event.isButtonstate())
			ItemSwordCutter.canClick = true;

		boolean m1 = ItemGunBase.m1;
		boolean m2 = ItemGunBase.m2;
		if(player.getHeldItem(EnumHand.MAIN_HAND) != null && player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemGunBase) {

			if(event.getButton() == 0)
				event.setCanceled(true);

			ItemGunBase item = (ItemGunBase) player.getHeldItem(EnumHand.MAIN_HAND).getItem();
			if(event.getButton() == 0 && !m1 && !m2) {
				ItemGunBase.m1 = true;
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(true, (byte) 0, EnumHand.MAIN_HAND));
				item.startActionClient(player.getHeldItemMainhand(), player.world, player, true, EnumHand.MAIN_HAND);
			}
			else if(event.getButton() == 1 && !m2 && !m1) {
				ItemGunBase.m2 = true;
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(true, (byte) 1, EnumHand.MAIN_HAND));
				item.startActionClient(player.getHeldItemMainhand(), player.world, player, false, EnumHand.MAIN_HAND);
			}
		}
		if(player.getHeldItem(EnumHand.OFF_HAND) != null && player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof ItemGunBase) {

			if(event.getButton() == 0)
				event.setCanceled(true);

			ItemGunBase item = (ItemGunBase) player.getHeldItem(EnumHand.OFF_HAND).getItem();
			if(event.getButton() == 0 && !m1 && !m2) {
				ItemGunBase.m1 = true;
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(true, (byte) 0, EnumHand.OFF_HAND));
				item.startActionClient(player.getHeldItemOffhand(), player.world, player, true, EnumHand.OFF_HAND);
			}
			else if(event.getButton() == 1 && !m2 && !m1) {
				ItemGunBase.m2 = true;
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(true, (byte) 1, EnumHand.OFF_HAND));
				item.startActionClient(player.getHeldItemOffhand(), player.world, player, false, EnumHand.OFF_HAND);
			}
		}
	}

	@Spaghetti("please get this shit out of my face")
	@SubscribeEvent
	public void onPlaySound(PlaySoundEvent e) {
		ResourceLocation r = e.getSound().getSoundLocation();
		WorldClient wc = Minecraft.getMinecraft().world;
		if (wc != null) {
			if (MainRegistry.proxy.getImpactDust(wc, Minecraft.getMinecraft().player.dimension) > 0.75F)
				if (e.getName().matches("music\\.422fgame") || e.getName().matches("music\\.422fmenu")) {
					nextMusic = !nextMusic;
					//e.setResultSound(null);
					//Minecraft.getMinecraft().getMusicTicker().playMusic(MusicTicker.MusicType.MENU);
				}
		}

		// Alright, alright, I give the fuck up, you've wasted my time enough
		// with this bullshit. You win.
		// A winner is you.
		// Conglaturations.
		// Fuck you.

		if(r.toString().equals("hbm:misc.nulltau") && Library.getClosestPlayerForSound(wc, e.getSound().getXPosF(), e.getSound().getYPosF(), e.getSound().getZPosF(), 2) != null) {
			EntityPlayer ent = Library.getClosestPlayerForSound(wc, e.getSound().getXPosF(), e.getSound().getYPosF(), e.getSound().getZPosF(), 2);

			if(MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundTauLoop) == null) {
				MovingSoundPlayerLoop.globalSoundList.add(new MovingSoundXVL1456(HBMSoundEvents.tauChargeLoop2, SoundCategory.PLAYERS, ent, EnumHbmSound.soundTauLoop));
				MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundTauLoop).setPitch(0.5F);
			} else {
				if(MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundTauLoop).getPitch() < 1.5F)
					MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundTauLoop).setPitch(MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundTauLoop).getPitch() + 0.01F);
			}
		}
		if(r.toString().equals("hbm:misc.nullradar") && Library.getClosestPlayerForSound(wc, e.getSound().getXPosF(), e.getSound().getYPosF(), e.getSound().getZPosF(), 2) != null) {
			EntityPlayer ent = Library.getClosestPlayerForSound(wc, e.getSound().getXPosF(), e.getSound().getYPosF(), e.getSound().getZPosF(), 2);
			if(MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundRadarLoop) == null) {
				MovingSoundPlayerLoop.globalSoundList.add(new MovingSoundRadarLoop(HBMSoundEvents.alarmAirRaid, SoundCategory.PLAYERS, ent, EnumHbmSound.soundRadarLoop));
				MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundRadarLoop).setVolume(1.0F);
			}
		}

		if(r.toString().equals("hbm:misc.nullchopper") && Library.getClosestChopperForSound(wc, e.getSound().getXPosF(), e.getSound().getYPosF(), e.getSound().getZPosF(), 2) != null) {
			EntityHunterChopper ent = Library.getClosestChopperForSound(wc, e.getSound().getXPosF(), e.getSound().getYPosF(), e.getSound().getZPosF(), 2);

			if(MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundChopperLoop) == null) {
				MovingSoundPlayerLoop.globalSoundList.add(new MovingSoundChopper(HBMSoundEvents.chopperFlyingLoop, SoundCategory.HOSTILE, ent, EnumHbmSound.soundChopperLoop));
				MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundChopperLoop).setVolume(10.0F);
			}
		}

		if(r.toString().equals("hbm:misc.nullcrashing") && Library.getClosestChopperForSound(wc, e.getSound().getXPosF(), e.getSound().getYPosF(), e.getSound().getZPosF(), 2) != null) {
			EntityHunterChopper ent = Library.getClosestChopperForSound(wc, e.getSound().getXPosF(), e.getSound().getYPosF(), e.getSound().getZPosF(), 2);

			if(MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundCrashingLoop) == null) {
				MovingSoundPlayerLoop.globalSoundList.add(new MovingSoundCrashing(HBMSoundEvents.chopperCrashingLoop, SoundCategory.HOSTILE, ent, EnumHbmSound.soundCrashingLoop));
				MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundCrashingLoop).setVolume(10.0F);
			}
		}

		if(r.toString().equals("hbm:misc.nullmine") && Library.getClosestMineForSound(wc, e.getSound().getXPosF(), e.getSound().getYPosF(), e.getSound().getZPosF(), 2) != null) {
			EntityChopperMine ent = Library.getClosestMineForSound(wc, e.getSound().getXPosF(), e.getSound().getYPosF(), e.getSound().getZPosF(), 2);

			if(MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundMineLoop) == null) {
				MovingSoundPlayerLoop.globalSoundList.add(new MovingSoundChopperMine(HBMSoundEvents.chopperMineLoop, SoundCategory.HOSTILE, ent, EnumHbmSound.soundMineLoop));
				MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundMineLoop).setVolume(10.0F);
			}
		}

		for(MovingSoundPlayerLoop sounds : MovingSoundPlayerLoop.globalSoundList) {
			if(!sounds.init || sounds.isDonePlaying()) {
				sounds.init = true;
				sounds.setDone(false);
				Minecraft.getMinecraft().getSoundHandler().playSound(sounds);
			}
		}
	}
	public static Set<Item> unlockedRecipes = new HashSet<>();
	public static void loadRecipeFromString(String s,boolean isNew) {
		Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(s));
		unlockedRecipes.add(item);
		if (!isNew)
			LeafiaRecipeBookTab.dontAnimate.add(item);
	}
	public static void unlockRecipeFromString(String s) {
		Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(s));
		unlockedRecipes.add(item);
		RecipeToast.addOrUpdate(Minecraft.getMinecraft().getToastGui(),new LeafiaDummyRecipe(item));
	}
	public static void tryUnlockRecipe(Item item) {
		if (!unlockedRecipes.contains(item)) {
			unlockedRecipes.add(item);
			RecipeToast.addOrUpdate(Minecraft.getMinecraft().getToastGui(),new LeafiaDummyRecipe(item));
		}
	}
	@SubscribeEvent
	public void clientDisconnectFromServer(ClientDisconnectionFromServerEvent e) {
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT && AssemblerRecipes.backupRecipeList != null) {
			AssemblerRecipes.recipeList = AssemblerRecipes.backupRecipeList;
			AssemblerRecipes.recipes = AssemblerRecipes.backupRecipes;
			AssemblerRecipes.time = AssemblerRecipes.backupTime;
			AssemblerRecipes.hidden = AssemblerRecipes.backupHidden;
			AssemblerRecipes.backupRecipeList = null;
			AssemblerRecipes.backupRecipes = null;
			AssemblerRecipes.backupTime = null;
			AssemblerRecipes.backupHidden = null;
			LeafiaShakecam.instances.clear();
			unlockedRecipes.clear();
			LeafiaRecipeBookTab.dontAnimate.clear();
		}
	}

	@SubscribeEvent
	public void drawBlockSelectionBox(DrawBlockHighlightEvent evt){
		if(evt.getTarget().typeOfHit == RayTraceResult.Type.BLOCK){
			BlockPos pos = evt.getTarget().getBlockPos();
			IBlockState state =  Minecraft.getMinecraft().world.getBlockState(pos);
			Block block = state.getBlock();
			if(block instanceof ICustomSelectionBox){
				LeafiaGls.enableBlend();
	            LeafiaGls.tryBlendFuncSeparate(LeafiaGls.SourceFactor.SRC_ALPHA, LeafiaGls.DestFactor.ONE_MINUS_SRC_ALPHA, LeafiaGls.SourceFactor.ONE, LeafiaGls.DestFactor.ZERO);
	            LeafiaGls.glLineWidth(2.0F);
	            LeafiaGls.disableTexture2D();
	            LeafiaGls.depthMask(false);
	            EntityPlayer player = evt.getPlayer();
	            float partialTicks = evt.getPartialTicks();
	            double d3 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)partialTicks;
                double d4 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)partialTicks;
                double d5 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)partialTicks;
				if(((ICustomSelectionBox)block).renderBox(Minecraft.getMinecraft().world, player, state, evt.getTarget().getBlockPos(), pos.getX()-d3, pos.getY()-d4, pos.getZ()-d5, partialTicks)){
					evt.setCanceled(true);
				}
				LeafiaGls.depthMask(true);
	            LeafiaGls.enableTexture2D();
	            LeafiaGls.disableBlend();
			}
		}
	}
	
	@SubscribeEvent
	public void drawTooltip(ItemTooltipEvent event) {

		ItemStack stack = event.getItemStack();
		List<String> list = event.getToolTip();

		/// HAZMAT INFO ///
		List<HazardClass> hazInfo = ArmorRegistry.hazardClasses.get(stack.getItem());
		
		if(hazInfo != null) {
			
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				list.add(TextFormatting.GOLD + I18nUtil.resolveKey("hazard.prot"));
				for(HazardClass clazz : hazInfo) {
					list.add(TextFormatting.YELLOW + "  " + I18nUtil.resolveKey(clazz.lang));
				}
			} else {
				
				list.add(I18nUtil.resolveKey("desc.tooltip.hold", "LSHIFT"));
			}
		}

		/// CLADDING ///
		double rad = HazmatRegistry.getResistance(stack);
		rad = ((int) (rad * 100)) / 100D;
		if(rad > 0)
			list.add(TextFormatting.YELLOW + I18nUtil.resolveKey("trait.radResistance", rad));
		

		/// ARMOR MODS ///
		if(stack.getItem() instanceof ItemArmor && ArmorModHandler.hasMods(stack)) {
			
			if(!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && !(Minecraft.getMinecraft().currentScreen instanceof GUIArmorTable)) {
				
				list.add(TextFormatting.DARK_GRAY + "" + TextFormatting.ITALIC +"Hold <" +
						TextFormatting.YELLOW + "" + TextFormatting.ITALIC + "LSHIFT" +
						TextFormatting.DARK_GRAY + "" + TextFormatting.ITALIC + "> to display installed armor mods");
				
			} else {
				
				list.add(TextFormatting.YELLOW + "Mods:");
				
				ItemStack[] mods = ArmorModHandler.pryMods(stack);
				
				for(int i = 0; i < 8; i++) {
					
					if(mods[i] != null && mods[i].getItem() instanceof ItemArmorMod) {
						
						((ItemArmorMod)mods[i].getItem()).addDesc(list, mods[i], stack);
					}
				}
			}
		}

		/// CUSTOM NUKE ///
		ComparableStack comp = new NbtComparableStack(stack).makeSingular();
		CustomNukeEntry entry = TileEntityNukeCustom.entries.get(comp);

		if(entry != null) {

			if(!list.isEmpty())
				list.add("");

			if(entry.entry == EnumEntryType.ADD)
				list.add(TextFormatting.GOLD + "Adds " + entry.value + " to the custom nuke stage " + entry.type);

			if(entry.entry == EnumEntryType.MULT)
				list.add(TextFormatting.GOLD + "Adds multiplier " + entry.value + " to the custom nuke stage " + entry.type);
		}
		if (!stack.isEmpty() && !(stack.getItem() instanceof IItemHazard)) {
			for (int id : OreDictionary.getOreIDs(stack)) {
				ItemHazardModule module = OreDictManager.fiaOreHazards.get(OreDictionary.getOreName(id));
				if (module != null)
					module.addInformation(stack,list,event.getFlags());
			}
		}

		/// NEUTRON RADS ///
		float activationRads = ContaminationUtil.getNeutronRads(stack);
		if(activationRads > 0) {
			ItemHazardModule module = new ItemHazardModule();
			module.radiation.setActivation(activationRads);
			module.addInformation(stack,list,event.getFlags());
			/* Why are we still using those
			list.add(TextFormatting.GREEN + "[" + I18nUtil.resolveKey("trait._hazarditem.radioactive") + "]");
			float stackRad = activationRads / stack.getCount();
			list.add(TextFormatting.YELLOW + (Library.roundFloat(ItemHazardModule.getNewValue(stackRad), 3) + ItemHazardModule.getSuffix(stackRad) + " RAD/s"));
			
			if(stack.getCount() > 1) {
				list.add(TextFormatting.YELLOW + ("Stack: " + Library.roundFloat(ItemHazardModule.getNewValue(activationRads), 3) + ItemHazardModule.getSuffix(activationRads) + " RAD/s"));
			}*/
		}

		//MKU
		if(stack.hasTagCompound()){
			if(stack.getTagCompound().getBoolean("ntmContagion"))
				list.add("§4§l[" + I18nUtil.resolveKey("trait.mkuinfected") + "§4§l]");
		}
	}
	
	private static final ResourceLocation poster = new ResourceLocation(RefStrings.MODID + ":textures/models/misc/poster.png");

	@SubscribeEvent
	public void renderFrame(RenderItemInFrameEvent event) {

		if(event.getItem() != null && event.getItem().getItem() == ModItems.flame_pony) {
			event.setCanceled(true);

			double p = 0.0625D;
			double o = p * 2.75D;

			LeafiaGls.disableLighting();
			Minecraft.getMinecraft().renderEngine.bindTexture(poster);
			net.minecraft.client.renderer.Tessellator tess = net.minecraft.client.renderer.Tessellator.getInstance();
			BufferBuilder buf = tess.getBuffer();
			buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			buf.pos(0.5, 0.5 + o, p * 0.5).tex(1, 0).endVertex();
			buf.pos(-0.5, 0.5 + o, p * 0.5).tex(0, 0).endVertex();
			buf.pos(-0.5, -0.5 + o, p * 0.5).tex(0, 1).endVertex();
			buf.pos(0.5, -0.5 + o, p * 0.5).tex(1, 1).endVertex();
			tess.draw();
			LeafiaGls.enableLighting();
		}
	}
}
