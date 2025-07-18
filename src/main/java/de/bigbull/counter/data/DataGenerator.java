package de.bigbull.counter.data;

import de.bigbull.counter.data.lang.ModDeLangProvider;
import de.bigbull.counter.data.lang.ModEnLangProvider;
import de.bigbull.counter.data.lang.ModEsLangProvider;
import de.bigbull.counter.data.lang.ModFrLangProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		FabricDataGenerator.Pack pack = generator.createPack();

		pack.addProvider(ModDeLangProvider::new);
		pack.addProvider(ModEnLangProvider::new);
		pack.addProvider(ModEsLangProvider::new);
		pack.addProvider(ModFrLangProvider::new);
	}
}
