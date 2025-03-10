package net.minecraft.trident.mixin;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.trident.util.EntityHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author ji_GGO
 * @date 2021/03/08
 */
@SideOnly(Side.CLIENT)
@Mixin(RenderLivingBase.class)
public abstract class MixinRenderLivingBase<T extends EntityLivingBase> extends Render<T> {

    protected MixinRenderLivingBase(RenderManager renderManager) {
        super(renderManager);
    }

    @Inject(method = "applyRotations(Lnet/minecraft/entity/EntityLivingBase;FFF)V", cancellable = true,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/text/TextFormatting;getTextWithoutFormattingCodes(Ljava/lang/String;)Ljava/lang/String;", opcode = 1))
    public void renderSpinAttack(T entityLiving, float ageInTicks, float rotationYaw, float partialTicks, CallbackInfo info){
        if (entityLiving instanceof EntityPlayer) {
            if (EntityHelper.isSpinAttacking((EntityPlayer)entityLiving)){
                GlStateManager.rotate(-90.0F - entityLiving.rotationPitch, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(((float)entityLiving.ticksExisted + partialTicks) * -75.0F, 0.0F, 1.0F, 0.0F);
                info.cancel();
            }
        }
    }

}