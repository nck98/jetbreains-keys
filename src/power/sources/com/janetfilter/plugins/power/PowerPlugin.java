package com.janetfilter.plugins.power;

import com.janetfilter.core.Environment;
import com.janetfilter.core.plugin.MyTransformer;
import com.janetfilter.core.plugin.PluginConfig;
import com.janetfilter.core.plugin.PluginEntry;
import java.util.ArrayList;
import java.util.List;

/* loaded from: power.jar:com/janetfilter/plugins/power/PowerPlugin.class */
public class PowerPlugin implements PluginEntry {
    private final List<MyTransformer> transformers = new ArrayList();

    public void init(Environment environment, PluginConfig config) {
        this.transformers.add(new ArgsTransformer(config.getBySection("Args")));
        this.transformers.add(new ResultTransformer(config.getBySection("Result")));
    }

    public String getName() {
        return "Power";
    }

    public String getAuthor() {
        return "neo";
    }

    public String getVersion() {
        return "v1.1.0";
    }

    public String getDescription() {
        return "A plugin for the ja-netfilter, it is a dragon slayer for asymmetric encryption.";
    }

    public List<MyTransformer> getTransformers() {
        return this.transformers;
    }
}
