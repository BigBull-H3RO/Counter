package de.bigbull.counter.data;

import de.bigbull.counter.Counter;
import de.bigbull.counter.data.lang.ModDeLangProvider;
import de.bigbull.counter.data.lang.ModEnLangProvider;
import de.bigbull.counter.data.lang.ModEsLangProvider;
import de.bigbull.counter.data.lang.ModFrLangProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class DataGenerators {
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();

        try {
            generator.addProvider(true, new ModEnLangProvider(output));
            generator.addProvider(true, new ModDeLangProvider(output));
            generator.addProvider(true, new ModFrLangProvider(output));
            generator.addProvider(true, new ModEsLangProvider(output));

        } catch (RuntimeException e) {
            Counter.logger.error("Failed to generate data", e);
        }
    }
}
