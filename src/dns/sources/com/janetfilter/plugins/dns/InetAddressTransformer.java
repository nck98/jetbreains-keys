package com.janetfilter.plugins.dns;

import com.janetfilter.core.models.FilterRule;
import com.janetfilter.core.plugin.MyTransformer;
import java.util.List;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.InsnList;
import jdk.internal.org.objectweb.asm.tree.InsnNode;
import jdk.internal.org.objectweb.asm.tree.JumpInsnNode;
import jdk.internal.org.objectweb.asm.tree.LabelNode;
import jdk.internal.org.objectweb.asm.tree.MethodInsnNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;
import jdk.internal.org.objectweb.asm.tree.VarInsnNode;

/* loaded from: dns.jar:com/janetfilter/plugins/dns/InetAddressTransformer.class */
public class InetAddressTransformer implements MyTransformer {
    private final List<FilterRule> rules;

    public InetAddressTransformer(List<FilterRule> rules) {
        this.rules = rules;
    }

    public String getHookClassName() {
        return "java/net/InetAddress";
    }

    public byte[] transform(String className, byte[] classBytes, int order) throws Exception {
        DNSFilter.setRules(this.rules);
        ClassReader reader = new ClassReader(classBytes);
        ClassNode node = new ClassNode(327680);
        reader.accept(node, 0);
        for (MethodNode m : node.methods) {
            if ("getAllByName".equals(m.name) && "(Ljava/lang/String;Ljava/net/InetAddress;)[Ljava/net/InetAddress;".equals(m.desc)) {
                InsnList list = new InsnList();
                list.add(new VarInsnNode(25, 0));
                list.add(new MethodInsnNode(184, "com/janetfilter/plugins/dns/DNSFilter", "testQuery", "(Ljava/lang/String;)Ljava/lang/String;", false));
                list.add(new InsnNode(87));
                m.instructions.insert(list);
            } else if ("isReachable".equals(m.name) && "(Ljava/net/NetworkInterface;II)Z".equals(m.desc)) {
                InsnList list2 = new InsnList();
                list2.add(new VarInsnNode(25, 0));
                list2.add(new MethodInsnNode(184, "com/janetfilter/plugins/dns/DNSFilter", "testReachable", "(Ljava/net/InetAddress;)Ljava/lang/Object;", false));
                list2.add(new VarInsnNode(58, 4));
                list2.add(new InsnNode(1));
                list2.add(new VarInsnNode(25, 4));
                LabelNode label1 = new LabelNode();
                list2.add(new JumpInsnNode(165, label1));
                list2.add(new InsnNode(3));
                list2.add(new InsnNode(172));
                list2.add(label1);
                m.instructions.insert(list2);
            }
        }
        ClassWriter writer = new ClassWriter(3);
        node.accept(writer);
        return writer.toByteArray();
    }
}
