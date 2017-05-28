package racer290.bettercrafting.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.items.ItemHandlerHelper;

public class TileCraftingSlot extends TileModInventory {
	
	public TileCraftingSlot() {
		
		super();
		
		this.itemHandler = new CustomItemHandler(new int[] { 1 }, this);
		
	}
	
	public void rightClick(EntityPlayer activator, EnumHand hand) {
		
		if (this.getInventory().getStackInSlot(0).isEmpty() && !activator.getHeldItem(hand).isEmpty()) {
			
			this.getInventory().setStackInSlot(0, new ItemStack(activator.getHeldItem(hand).getItem(), 1));
			
			activator.getHeldItem(hand).shrink(1);
			
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
