package com.martinsil.pvzcraft.entity.client;

import com.martinsil.pvzcraft.PvZCraft;
import com.martinsil.pvzcraft.entity.custom.LawnmowerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class LawnmowerRenderer extends MobEntityRenderer<LawnmowerEntity, LawnmowerModel<LawnmowerEntity>> {

    public LawnmowerRenderer(EntityRendererFactory.Context context) {
        super(context, new LawnmowerModel<>(context.getPart(LawnmowerModel.LAWNMOWER)), 0.3f);
    }

    @Override
    public Identifier getTexture(LawnmowerEntity entity) {
        return Identifier.of(PvZCraft.MOD_ID, "textures/entity/lawnmower/lawnmower.png");
    }
}
