package racer290.bettercrafting.block;

import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import racer290.bettercrafting.BetterCrafting;

public abstract class ModBlock extends Block {
	
	public ModBlock(Material m, String registryName) {
		
		super(m);
		
		this.setRegistryName(new ResourceLocation(BetterCrafting.MODID, registryName));
		this.setUnlocalizedName(BetterCrafting.MODID + "." + registryName);
		
		GameRegistry.register(this);
		
	}
	
	public abstract boolean shouldItemBlockBeRegistered();
	
	@SideOnly(Side.CLIENT)
	public abstract void initVisuals();
	
	@SideOnly(Side.CLIENT)
	public void initVisuals(int meta, String variantForMeta) {
		
		if (variantForMeta == null) throw new NullPointerException("Whoops! This is my fault. Please report this issue and provide your latest minecraft log!");
		
		BetterCrafting.LOGGER.debug("Initializing models for block " + this.getRegistryName().getResourcePath() + " with meta value " + meta + " and variant " + variantForMeta + "!");
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(this), meta, new ModelResourceLocation(this.getRegistryName(), variantForMeta));
		
		// ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), meta, new ModelResourceLocation(this.getRegistryName(), variantForMeta));
		
	}
	
	@SideOnly(Side.CLIENT)
	public void initVisuals(HashMap<Integer, String> metaVariantMapping) {
		
		for (Entry<Integer, String> current : metaVariantMapping.entrySet()) {
			
			if (current.getKey() == null || current.getValue() == null) throw new NullPointerException("Whoops! This is my fault. Please report this issue and provide your latest minecraft log!");
			
			BetterCrafting.LOGGER.debug("Initializing models for block " + this.getRegistryName().getResourcePath() + "with meta value " + current.getKey() + " and variant " + current.getValue() + "!");
			
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(this), current.getKey(), new ModelResourceLocation(this.getRegistryName(), current.getValue()));
			
			// ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), current.getKey(), new ModelResourceLocation(this.getRegistryName(), current.getValue()));
			
		}
		
	}
	
}