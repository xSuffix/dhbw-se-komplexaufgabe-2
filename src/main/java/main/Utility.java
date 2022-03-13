package main;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

public class Utility {
    private static final Configuration config = Configuration.INSTANCE;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static final NumberFormat numberFormat = NumberFormat.getInstance();

    public Utility() {
        numberFormat.setGroupingUsed(true);
    }

    public static String analyseMatter(String matter) {
        if (matter.isEmpty()) return "0 atoms";

        final Configuration c = Configuration.INSTANCE;
        int atoms = matter.length();
        HashMap<String, Integer> materials = new HashMap<>();

        for (char a : matter.toCharArray()) {
            if (a == c.acid) materials.put("Acid", materials.getOrDefault("Acid", 0) + 1);
            else if (a == c.stone) materials.put("Stone", materials.getOrDefault("Stone", 0) + 1);
            else if (a == c.gravel) materials.put("Gravel", materials.getOrDefault("Gravel", 0) + 1);
            else if (a == c.dirt) materials.put("Dirt", materials.getOrDefault("Dirt", 0) + 1);
            else if (a == c.gold) materials.put("Gold", materials.getOrDefault("Gold", 0) + 1);
            else if (a == c.trash) materials.put("Trash", materials.getOrDefault("Trash", 0) + 1);
        }

        int other = atoms - materials.values().stream().mapToInt(x -> x).sum();
        if (other > 0) materials.put("other", other);

        String percentages = materials.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .sorted((e1, e2) -> e2.getValue() - e1.getValue())
                .map(entry -> String.format("%.2f%% %s", (double) 100 * entry.getValue() / atoms, entry.getKey())).collect(Collectors.joining(", "));

        return String.format("%s atoms (%s)", formatNumber(atoms), percentages);
    }

    public static void logInfo(String context, String message) {
        if (showLog(context))
            System.out.printf("[%s  INFO] %s: %s%n", dateFormat.format(new Date()), context, message);
    }

    public static void logError(String context, String message) {
        if (config.alwaysLogErrors || showLog(context))
            System.err.printf("[%s Error] %s: %s%n", dateFormat.format(new Date()), context, message);
    }

    private static boolean showLog(String context) {
        if (Objects.equals(context, config.acidStorageContext) && !config.logAcidStorage) return false;
        else if (Objects.equals(context, config.centrifugeContext) && !config.logCentrifuge) return false;
        else if (Objects.equals(context, config.blockStorageContext) && !config.logBlockStorage) return false;
        return !Objects.equals(context, config.testContext) || config.logTests;
    }

    public static String formatNumber(int number) {
        return numberFormat.format(number);
    }
}
