package com.martinsil.pvzcraft.item.custom;

import com.martinsil.pvzcraft.entity.custom.PeaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import static com.martinsil.pvzcraft.util.PlantProjectileConstants.PEA_SPEED;

public class PeaItem extends Item {

    public PeaItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(
                null,
                user.getX(),
                user.getY(),
                user.getZ(),
                SoundEvents.ENTITY_SNOWBALL_THROW,
                SoundCategory.NEUTRAL,
                0.5F,
                0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F)
        );
        if (!world.isClient) {
            PeaEntity peaEntity = new PeaEntity(user, world);
            peaEntity.setNoGravity(true);
            peaEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, PEA_SPEED, 0.0F);
            world.spawnEntity(peaEntity);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        return TypedActionResult.success(itemStack, world.isClient());
    }
}
