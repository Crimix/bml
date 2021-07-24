package com.black_dog20.bml.utils.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

/**
 * Utility class for block.
 *
 * @author black_dog20
 */
public class BlockUtil {

    /**
     * Gets the adjacent block position from a block position and a direction.
     *
     * @param pos       the current block position.
     * @param direction the direction.
     * @return the new block position.
     */
    public static BlockPos directionToBlockPos(BlockPos pos, Direction direction) {
        switch (direction) {
            case DOWN:
                return new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
            case UP:
                return new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());
            case NORTH:
                return new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1);
            case SOUTH:
                return new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1);
            case WEST:
                return new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ());
            case EAST:
                return new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ());
            default:
                return new BlockPos(pos.getX(), pos.getY(), pos.getZ());
        }
    }
}
