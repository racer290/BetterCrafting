package racer290.bettercrafting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import racer290.bettercrafting.crafting.CubicCraftingManager;

@Mod(modid = BetterCrafting.MODID, name = BetterCrafting.NAME, version = BetterCrafting.VERSION)
public class BetterCrafting {
	
	public static final String MODID = "bettercrafting";
	public static final String NAME = "Better Crafting";
	public static final String VERSION = "1.11.2-A-1.0.0";
	
	@Mod.Instance
	public static BetterCrafting instance;
	
	@SidedProxy(serverSide = "racer290.bettercrafting.CommonProxy", clientSide = "racer290.bettercrafting.ClientProxy")
	public static CommonProxy proxy = new CommonProxy();
	
	public static final Logger LOGGER = LogManager.getLogger(BetterCrafting.NAME);
	
	public static CubicCraftingManager craftingManager;
	
	public BetterCrafting() {
		
		BetterCrafting.craftingManager = new CubicCraftingManager();
		
	}
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent evt) {
		
		LOGGER.info("PreInitializing!");
		
		proxy.preInit(evt);
		
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent evt) {
		
		LOGGER.info("Initializing!");
		
		proxy.init(evt);
		
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent evt) {
		
		LOGGER.info("PostInitializing!");
		
		proxy.postInit(evt);
		
	}
	
}