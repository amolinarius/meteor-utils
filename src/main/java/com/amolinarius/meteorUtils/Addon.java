package com.amolinarius.meteorUtils;

import com.amolinarius.meteorUtils.commands.JoinCommand;
import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.commands.Commands;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;

public class Addon extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();
    public static final Category CATEGORY = new Category("Meteor Utils");

    @Override
    public void onInitialize() {
        LOG.info("Initializing Meteor Utils Addon.");

        // Modules
        // Modules.get().add(new ModuleExample());

        // Commands
        Commands.add(new JoinCommand());

        // HUD
        // Hud.get().register(HudExample.INFO);
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }

    @Override
    public String getPackage() {
        return "com.amolinarius.meteorUtils";
    }
}
