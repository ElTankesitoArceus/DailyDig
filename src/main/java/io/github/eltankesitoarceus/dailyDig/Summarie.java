package io.github.eltankesitoarceus.dailyDig;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Summarie {

    Map<String, Map<String, Comparable<?>>> finalStats = new HashMap<>();
    Map<String, Integer> statCount = new HashMap<>();
    // 0 == no order, 1 = asc, -1 = desc
    Map<String, Integer> order = new HashMap<>();

    /**
     * Will add stats to the map to be displayed with no order. See {@link #addStatOrderedAsc(String, Map, int)} and {@link #addStatOrderedDesc(String, Map, int)}
     *
     * @param statName Will be displayed when the summarie is sent
     * @param stat     A map containing player names as the key and the corresponding value
     * @param count    how many of this will be displayed. Use -1 to send all.
     */
    public void addStat(String statName, Map<String, Comparable<?>> stat, int count) {
        finalStats.put(statName, stat);
        statCount.put(statName, count);
        order.put(statName, 0);
    }

    /**
     * Will add stats to the map to be displayed with ascending order.
     *
     * @param statName Will be displayed when the summarie is sent
     * @param stat     A map containing player names as the key and the corresponding value
     * @param count    how many of this will be displayed. Use -1 to send all.
     */
    public void addStatOrderedAsc(String statName, Map<String, Comparable<?>> stat, int count) {
        finalStats.put(statName, stat);
        statCount.put(statName, count);
        order.put(statName, 1);
    }

    /**
     * Will add stats to the map to be displayed with descending order.
     *
     * @param statName Will be displayed when the summarie is sent
     * @param stat     A map containing player names as the key and the corresponding value
     * @param count    how many of this will be displayed. Use -1 to send all.
     */
    public void addStatOrderedDesc(String statName, Map<String, Comparable<?>> stat, int count) {
        finalStats.put(statName, stat);
        statCount.put(statName, count);
        order.put(statName, -1);
    }

    public Map<String,  Map<String, ?>> exportSummarie() {
        HashMap<String, Map<String, ?>> fin = new HashMap();
        for (String key : finalStats.keySet()) {
            HashMap<String, Map<String, ?>> ret = new HashMap();
            if (order.get(key) == 1) {
                ret.put(key, finalStats.get(key)
                        .entrySet()
                        .stream()
                        .sorted((v1, v2) -> {
                            @SuppressWarnings("unchecked")
                            Comparable<Object> comp1 = (Comparable<Object>) v1;
                            return comp1.compareTo(v2);
                        })
                        .limit(statCount.get(key) > 0 ? statCount.get(key): Long.MAX_VALUE)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new)));
            } else if (order.get(key) == -1){
                ret.put(key, finalStats.get(key)
                        .entrySet()
                        .stream()
                        .sorted(Map.Entry.<String, Comparable<?>>comparingByValue((v1, v2) -> {
                            @SuppressWarnings("unchecked")
                            Comparable<Object> comp1 = (Comparable<Object>) v1;
                            return comp1.compareTo(v2);
                        }).reversed())
                        .limit(statCount.get(key) > 0 ? statCount.get(key): Long.MAX_VALUE)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new)));
            } else {
                ret.put(key, finalStats.get(key)
                        .entrySet()
                        .stream()
                        .limit(statCount.get(key) > 0 ? statCount.get(key): Long.MAX_VALUE)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new)));
            }
            fin.put(key, ret);
        }
        return fin;
    }

}
