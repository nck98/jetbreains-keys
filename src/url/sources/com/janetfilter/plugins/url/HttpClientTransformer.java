package com.janetfilter.plugins.url;

import com.janetfilter.core.models.FilterRule;
import com.janetfilter.core.plugin.MyTransformer;
import java.util.List;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.FieldInsnNode;
import jdk.internal.org.objectweb.asm.tree.InsnList;
import jdk.internal.org.objectweb.asm.tree.InsnNode;
import jdk.internal.org.objectweb.asm.tree.MethodInsnNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;
import jdk.internal.org.objectweb.asm.tree.VarInsnNode;

/* loaded from: url.jar:com/janetfilter/plugins/url/HttpClientTransformer.class */
public class HttpClientTransformer implements MyTransformer {
    private final List<FilterRule> rules;

    public HttpClientTransformer(List<FilterRule> rules) {
        this.rules = rules;
    }

    public String getHookClassName() {
        return "sun/net/www/http/HttpClient";
    }

    public byte[] transform(String className, byte[] classBytes, int order) throws Exception {
        URLFilter.setRules(this.rules);
        ClassReader reader = new ClassReader(classBytes);
        ClassNode node = new ClassNode(327680);
        reader.accept(node, 0);
        for (MethodNode mn : node.methods) {
            if ("openServer".equals(mn.name) && "()V".equals(mn.desc)) {
                InsnList list = new InsnList();
                list.add(new VarInsnNode(25, 0));
                list.add(new FieldInsnNode(180, "sun/net/www/http/HttpClient", "url", "Ljava/net/URL;"));
                list.add(new MethodInsnNode(184, "com/janetfilter/plugins/url/URLFilter", "testURL", "(Ljava/net/URL;)Ljava/net/URL;", false));
                list.add(new InsnNode(87));
                mn.instructions.insert(list);
            }
        }
        ClassWriter writer = new ClassWriter(3);
        node.accept(writer);
        return writer.toByteArray();
    }
}
