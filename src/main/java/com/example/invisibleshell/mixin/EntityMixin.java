package com.example.invisibleshell.mixin;

import com.example.invisibleshell.InvisibleShellClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    // Клиентское "свечение": рисуем контур у всех невидимых игроков в радиусе RADIUS.
    @Inject(method = "isGlowing", at = @At("HEAD"), cancellable = true)
    private void invisibleshell$forceGlowing(CallbackInfoReturnable<Boolean> cir) {
        if (!InvisibleShellClient.shellEnabled) return;
        if (!((Object) this instanceof AbstractClientPlayerEntity other)) return;
        if (!other.isInvisible()) return;

        ClientPlayerEntity me = MinecraftClient.getInstance().player;
        if (me == null) return;

        double r = InvisibleShellClient.RADIUS;
        if (other == me || other.squaredDistanceTo(me) <= r * r) {
            cir.setReturnValue(true);
        }
    }
}
