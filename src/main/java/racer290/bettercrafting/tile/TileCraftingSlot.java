package racer290.bettercrafting.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.items.ItemHandlerHelper;
import racer290.bettercrafting.tile.TileModInventory;
import racer290.bettercrafting.tile.TileModInventory.CustomItemHandler;

public class TileCraftingSlot extends TileModInventory {
	
	public TileCraftingSlot() {
		
		super();
		
		this.itemHandler = new CustomItemHandler(new int[] { 1 }, this);
		
	}
	
	public void rightClick(EntityPlayer activator, EnumHand hand) {
		
		ItemStack cachedStack = activator.getHeldItem(hand);
		
		if (this.getInventory().getStackInSlot(0).isEmpty() && !cachedStack.isEmpty()) {
			
			this.getInventory().setStackInSlot(0, ItemHandlerHelper.copyStackWithSize(cachedStack, 1));
			
			cachedStack.shrink(1);
			
		} else {
			
			ItemHandlerHelper.giveItemToPlayer(activator, this.getInventory().getStackInSlot(0), activator.inventory.currentItem);
			
			this.getInventory().setStackInSlot(0, ItemStack.EMPTY);
			
		}
		
	}
	
	public ItemStack getStoredItem() {
		
		return this.getInventory().getStackInSlot(0);
		
	}
	
	public void clear() {
		
		this.getInventory().setStackInSlot(0, ItemStack.EMPTY);
		
	}
	
}
