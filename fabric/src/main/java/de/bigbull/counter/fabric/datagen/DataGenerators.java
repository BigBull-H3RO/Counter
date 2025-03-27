package de.bigbull.counter.fabric.datagen;

import de.bigbull.counter.fabric.datagen.lang.ModDeLangProvider;
import de.bigbull.counter.fabric.datagen.lang.ModEnLangProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DataGenerators implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(ModDeLangProvider::new);
        pack.addProvider(ModEnLangProvider::new);
    }
}
