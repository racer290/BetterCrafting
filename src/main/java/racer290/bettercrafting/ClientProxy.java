package racer290.bettercrafting;

import org.apache.logging.log4j.Level;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import racer290.bettercrafting.block.BlockHelper;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void preInit(FMLPreInitializationEvent evt) {
		
		super.preInit(evt);
		
	}
	
	@Override
	public void init(FMLInitializationEvent evt) {
		
		super.init(evt);
		
		BetterCrafting.LOGGER.info("Initializing models ..");
		
		try {
			
			BlockHelper.registerBlockModels();
			
		} catch (IllegalArgumentException | IllegalAccessException e) {
			
			System.out.println("Whoops! This is most likely my fault!");
			
			BetterCrafting.LOGGER.catching(Level.ERROR, e);
			
		}
		
	}
	
}
