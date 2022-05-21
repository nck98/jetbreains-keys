package com.janetfilter.plugins.hideme;

import com.janetfilter.core.Environment;
import com.janetfilter.core.plugin.MyTransformer;
import com.janetfilter.core.plugin.PluginConfig;
import com.janetfilter.core.plugin.PluginEntry;
import java.util.ArrayList;
import java.util.List;

/* loaded from: hideme.jar:com/janetfilter/plugins/hideme/HideMePlugin.class */
public class HideMePlugin implements PluginEntry {
    private final List<MyTransformer> transformers = new ArrayList();

    public void init(Environment environment, PluginConfig config) {
        this.transformers.add(new VMTransformer(environment));
        this.transformers.add(new ClassNameTransformer());
    }

    public String getName() {
        return "HideMe";
    }

    public String getAuthor() {
        return "neo";
    }

    public String getVersion() {
        return "v1.1.0";
    }

    public String getDescription() {
        return "A plugin for the ja-netfilter, it can prevent detection against javaagent.";
    }

    public List<MyTransformer> getTransformers() {
        return this.transformers;
    }
}
