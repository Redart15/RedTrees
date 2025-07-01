package redart15.redtrees;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.net.command.CommandManager;
import net.minecraft.core.net.command.commands.CommandDamage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redart15.redtrees.command.CommandGrow;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class RedTrees implements ModInitializer, RecipeEntrypoint, GameStartEntrypoint {
    public static final String MOD_ID = "examplemod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitialize() {
        LOGGER.info("ExampleMod initialized.");
    }

	@Override
	public void onRecipesReady() {

	}

	@Override
	public void initNamespaces() {
		CommandManager.registerCommand(new CommandGrow());
	}

	@Override
	public void beforeGameStart() {

	}

	@Override
	public void afterGameStart() {
	}
}
