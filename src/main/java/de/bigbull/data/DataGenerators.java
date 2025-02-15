package de.bigbull.data;

import de.bigbull.Counter;
import de.bigbull.data.lang.ModDeLangProvider;
import de.bigbull.data.lang.ModEnLangProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class DataGenerators {
    public static void gatherData(GatherDataEvent event) {
        try {
            DataGenerator generator = event.getGenerator();
            PackOutput output = generator.getPackOutput();

            generator.addProvider(true, new ModEnLangProvider(output));
            generator.addProvider(true, new ModDeLangProvider(output));

        } catch (RuntimeException e) {
            Counter.logger.error("Failed to generate data", e);
        }
    }
}
