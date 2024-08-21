package org.example;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Wall implements Structure {
    private List<Block> blocks;

    @Override
    public Optional<Block> findBlockByColor(String color) {
        for (Block block : blocks) {
            Optional<Block> colorBlock = findBlockByColor(color, block);
            if (colorBlock.isPresent()) {
                return colorBlock;
            }
        }
        return Optional.empty();
    }

    private Optional<Block> findBlockByColor(String color, Block block) {
        if (block instanceof CompositeBlock) {
            for (Block singleBlock : ((CompositeBlock) block).getBlocks()) {
                Optional<Block> colorBlock = findBlockByColor(color, singleBlock);
                if (colorBlock.isPresent()) {
                    return colorBlock;
                }
            }
        } else if (block.getColor().equals(color)) return Optional.of(block);
        return Optional.empty();
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        List<Block> materialBlocks = new ArrayList<>();
        for (Block block : blocks) {
            materialBlocks.addAll(findBlocksByMaterial(material, block));
        }
        return materialBlocks;
    }

    public List<Block> findBlocksByMaterial(String material, Block block) {
        List<Block> materialBlocks = new ArrayList<>();
        if (block instanceof CompositeBlock) {
            for (Block singleBlock : ((CompositeBlock) block).getBlocks()) {
                materialBlocks.addAll(findBlocksByMaterial(material, singleBlock));
            }
        } else if (block.getMaterial().equals(material)) materialBlocks.add(block);
        return materialBlocks;
    }

    @Override
    public int count() {
        int counter = 0;
        for (Block block : blocks) {
            counter += count(block);
        }
        return counter;
    }

    private int count(Block block) {
        int counter = 0;
        if (block instanceof CompositeBlock) {
            for (Block singleBlock : ((CompositeBlock) block).getBlocks()) {
                counter += count(singleBlock);
            }
        } else counter++;
        return counter;
    }
}