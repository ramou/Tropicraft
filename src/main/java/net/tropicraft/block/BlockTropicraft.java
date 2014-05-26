package net.tropicraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.tropicraft.info.TCInfo;
import net.tropicraft.registry.TCCreativeTabRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Base class for most Tropicraft blocks to extend
 */
public class BlockTropicraft extends Block {

	public BlockTropicraft() {
		this(Material.rock);
	}
	
	public BlockTropicraft(Material material) {
		super(material);
		this.setCreativeTab(TCCreativeTabRegistry.tabBlock);
	}
	
	/**
	 * @return The unlocalized block name
	 */
	@Override
    public String getUnlocalizedName() {
        return String.format("tile.%s%s", TCInfo.ICON_LOCATION, getActualName(super.getUnlocalizedName()));
    }
	
	/**
	 * Get the true name of the block
	 * @param unlocalizedName tile.%truename%
	 * @return The actual name of the block, rather than tile.%truename%
	 */
	protected String getActualName(String unlocalizedName) {
		return unlocalizedName.substring(unlocalizedName.indexOf('.') + 1);
	}
	
	/**
	 * Register all the icons of the block
	 * @param iconRegister Icon registry
	 */
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        blockIcon = iconRegister.registerIcon(String.format("%s", getActualName(this.getUnlocalizedName())));
    }

}
