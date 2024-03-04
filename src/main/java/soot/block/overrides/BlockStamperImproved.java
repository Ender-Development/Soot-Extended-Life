package soot.block.overrides;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import soot.tile.overrides.TileEntityStamperImproved;
import soot.util.EmberUtil;
import soot.util.IMigrateable;
import soot.util.MigrationUtil;
import teamroots.embers.block.BlockStamper;
import teamroots.embers.item.ItemTinkerHammer;
import teamroots.embers.register.BlockRegister;

import javax.annotation.Nullable;

public class BlockStamperImproved extends BlockStamper implements IMigrateable {
    public BlockStamperImproved(Material material, String name, boolean addToTab) {
        super(material, name, addToTab);
        EmberUtil.overrideRegistryLocation(this,name);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float x, float y, float z) {
        ItemStack heldItem = player.getHeldItem(hand);
        if (heldItem.getItem() instanceof ItemTinkerHammer && state.getBlock() == this){
            MigrationUtil.migrateBlock(world,pos);
            return true;
        }
        return super.onBlockActivated(world, pos, state, player, hand, facing, x, y, z);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityStamperImproved();
    }

    @Override
    public IBlockState getReplacementState(IBlockState state) {
        return BlockRegister.STAMPER.getDefaultState().withProperty(facing,state.getValue(facing));
    }
}
