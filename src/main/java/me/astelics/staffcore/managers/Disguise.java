package me.astelics.staffcore.managers;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.Getter;
import me.astelics.staffcore.utils.NMSHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class Disguise {
    @Getter private String name;
    @Getter private String texture;
    @Getter private String signature;

    private String originalName;
    private String originalTexture;
    private String originalSignature;

    public Disguise(String name, String texture, String signature) {
        this.name = name;
        this.texture = texture;
        this.signature = signature;
    }

    public boolean apply(JavaPlugin source, Player player) {
        GameProfile gameProfile = null;

        try {
            gameProfile = NMSHelper.getInstance().getGameProfile(player);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        if (gameProfile == null) {
            return false;
        }

        this.originalName = player.getName();
        Property originalTextures = NMSHelper.getInstance().getTexturesProperty(gameProfile);
        if (originalTextures != null) {
            this.originalTexture = originalTextures.getValue();
            this.originalSignature = originalTextures.getSignature();
        }

        gameProfile.getProperties().clear();
        gameProfile.getProperties()
                .put("textures",
                        new Property(
                                "textures",
                                texture,
                                signature
                        ));

        try {
            Field field = gameProfile.getClass().getDeclaredField("name");
            field.setAccessible(true);
            field.set(gameProfile, name);
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        if (name != null) player.setDisplayName(name);

        Bukkit.getOnlinePlayers().forEach(all -> {
            all.hidePlayer(source, player);
            all.showPlayer(source, player);
        });

        return true;
    }

    public boolean remove(JavaPlugin plugin, Player player) {
        this.name = originalName;
        this.texture = originalTexture;
        this.signature = originalSignature;
        return apply(plugin, player);
    }
}
