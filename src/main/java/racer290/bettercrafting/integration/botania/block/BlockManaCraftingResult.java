package racer290.bettercrafting.integration.botania.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import racer290.bettercrafting.BetterCrafting;
import racer290.bettercrafting.block.ModBlock;
import racer290.bettercrafting.integration.botania.render.ManaCraftingResultRenderer;
import racer290.bettercrafting.integration.botania.tile.TileManaCraftingResult;


public class BlockManaCraftingResult extends ModBlock implements ITileEntityProvider {
	
	public BlockManaCraftingResult() {
		
		super(Material.IRON, "manacraftingresult");
		GameRegistry.registerTileEntity(TileManaCraftingResult.class, BetterCrafting.MODID + ".manacraftingresult");
		
		this.setHardness(0.5f);
		this.setCreativeTab(CreativeTabs.DECORATIONS);
		
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		((TileManaCraftingResult) world.getTileEntity(pos)).rightClick(playerIn, hand);
		
		return true;
		
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		
		return new TileManaCraftingResult();
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		
		return BlockRenderLayer.TRANSLUCENT;
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		
		return true;
		
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		
		return false;
		
	}
	
	@Override
	public boolean shouldItemBlockBeRegistered() {
		
		return true;
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void initVisuals() {
		
		super.initVisuals(0, "inventory");
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileManaCraftingResult.class, new ManaCraftingResultRenderer());
		
	}
	
}
