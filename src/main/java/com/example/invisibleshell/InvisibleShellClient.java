package com.example.invisibleshell;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class InvisibleShellClient implements ClientModInitializer {
    /** Включён ли показ контура (переключается клавишей J). */
    public static boolean shellEnabled = false;

    /** Радиус (в блоках), в котором показываются контуры невидимых игроков. Меняй здесь. */
    public static final double RADIUS = 32.0;

    private static KeyBinding toggleKey;

    @Override
    public void onInitializeClient() {
        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.invisibleshell.toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                "key.categories.invisibleshell"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.wasPressed()) {
                shellEnabled = !shellEnabled;
                if (client.player != null) {
                    client.player.sendMessage(Text.translatable(
                            shellEnabled ? "message.invisibleshell.on" : "message.invisibleshell.off"), true);
                }
            }
        });
    }
}
