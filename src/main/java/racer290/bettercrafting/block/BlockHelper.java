package racer290.bettercrafting.block;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.logging.log4j.Level;

import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import racer290.bettercrafting.BetterCrafting;

public class BlockHelper {
	
	public static BlockCraftingSlot blockCraftingSlot;
	public static BlockCraftingResult blockCraftingResult;
	
	public static void registerBlocks() {
		
		BlockHelper.blockCraftingSlot = new BlockCraftingSlot();
		BlockHelper.blockCraftingResult = new BlockCraftingResult();
		
	}
	
	public static void registerItemBlocks() throws IllegalArgumentException, IllegalAccessException {
		
		for (Field f : BlockHelper.class.getFields()) {
			
			Object hold = f.get(null);
			
			if (hold instanceof ModBlock) {
				
				ModBlock block = (ModBlock) hold;
				
				if (block.getRegistryName() != null && block.shouldItemBlockBeRegistered()) {
					
					GameRegistry.register(new ItemBlock(block), block.getRegistryName());
					
				} else {
					
					BetterCrafting.LOGGER.debug("Skipping field " + f.getName() + " during ItemBlock registering, this block will be missing a corresponding inventory variant.");
					
				}
				
			}
			
		}
		
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerBlockModels() throws IllegalArgumentException, IllegalAccessException {
		
		for (Field f : BlockHelper.class.getDeclaredFields()) {
			
			try {
				
				f.get(null).getClass().getMethod("initVisuals", new Class<?>[] {}).invoke(f.get(null), new Object[] {});
				
			} catch (NoSuchMethodException ex) {
				
				BetterCrafting.LOGGER.info("Visuals for field " + f.getName() + " in BlockHelper class not initialized. The field may be of type that does not declare such a method.");
				
			} catch (InvocationTargetException ex) {
				
				BetterCrafting.LOGGER.error("Exception caught while initialiation of visuals for " + f.get(null).getClass().getName() + "! Stack trace:");
				BetterCrafting.LOGGER.catching(Level.ERROR, ex);
				
			}
			
		}
		
	}
	
}