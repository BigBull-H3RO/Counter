package de.bigbull.counter.neoforge.datagen;

import de.bigbull.counter.common.Counter;
import de.bigbull.counter.neoforge.datagen.lang.ModDeLangProvider;
import de.bigbull.counter.neoforge.datagen.lang.ModEnLangProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class DataGenerators {
    public static void gatherData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();

        try {
            generator.addProvider(true, new ModEnLangProvider(output));
            generator.addProvider(true, new ModDeLangProvider(output));
        } catch (RuntimeException e) {
            Counter.logger.error("Failed to generate data", e);
        }
    }
}
