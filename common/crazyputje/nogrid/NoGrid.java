package crazyputje.nogrid;

import java.util.EnumSet;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;

@Mod(modid = NoGrid.MOD_ID, name = NoGrid.MOD_NAME, version = NoGrid.VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class NoGrid extends KeyHandler {

	public final static String MOD_ID = "nogrid", MOD_NAME = "No Grid", VERSION = "v1.0";
	private boolean noGrid = false;

	public NoGrid() {
		super(new KeyBinding[] { new KeyBinding("No Grid", Keyboard.KEY_G) }, new boolean[] { false });
	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {
		if (event.getSide().isServer())
			return;

		KeyBindingRegistry.registerKeyBinding(this);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@ForgeSubscribe
	public void onDrawBlockHighlightEvent(DrawBlockHighlightEvent event) {
		if (noGrid)
			event.setCanceled(true);
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}

	@Override
	public String getLabel() {
		return MOD_ID + "_KeyHandler";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat) {
		if (tickEnd && Minecraft.getMinecraft().currentScreen == null) {
			noGrid = !noGrid;
		}
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
	}
}
