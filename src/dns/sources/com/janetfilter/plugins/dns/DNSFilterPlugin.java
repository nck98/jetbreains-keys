package com.janetfilter.plugins.dns;

import com.janetfilter.core.Environment;
import com.janetfilter.core.plugin.MyTransformer;
import com.janetfilter.core.plugin.PluginConfig;
import com.janetfilter.core.plugin.PluginEntry;
import java.util.ArrayList;
import java.util.List;

/* loaded from: dns.jar:com/janetfilter/plugins/dns/DNSFilterPlugin.class */
public class DNSFilterPlugin implements PluginEntry {
    private static final String PLUGIN_NAME = "DNS";
    private final List<MyTransformer> transformers = new ArrayList();

    public void init(Environment environment, PluginConfig config) {
        this.transformers.add(new InetAddressTransformer(config.getBySection(PLUGIN_NAME)));
    }

    public String getName() {
        return PLUGIN_NAME;
    }

    public String getAuthor() {
        return "neo";
    }

    public String getVersion() {
        return "v1.1.0";
    }

    public String getDescription() {
        return "A plugin for the ja-netfilter, it can block dns resolution.";
    }

    public List<MyTransformer> getTransformers() {
        return this.transformers;
    }
}
