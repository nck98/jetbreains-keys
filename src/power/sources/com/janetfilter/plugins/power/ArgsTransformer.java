package com.janetfilter.plugins.power;

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

/* loaded from: power.jar:com/janetfilter/plugins/power/ArgsTransformer.class */
public class ArgsTransformer implements MyTransformer {
    public ArgsTransformer(List<FilterRule> rules) {
        ArgsFilter.setRules(rules);
    }

    public String getHookClassName() {
        return "java/math/BigInteger";
    }

    public byte[] transform(String className, byte[] classBytes, int order) throws Exception {
        ClassReader reader = new ClassReader(classBytes);
        ClassNode node = new ClassNode(327680);
        reader.accept(node, 0);
        for (MethodNode mn : node.methods) {
            if ("oddModPow".equals(mn.name) && "(Ljava/math/BigInteger;Ljava/math/BigInteger;)Ljava/math/BigInteger;".equals(mn.desc)) {
                InsnList list = new InsnList();
                list.add(new VarInsnNode(25, 0));
                list.add(new VarInsnNode(25, 1));
                list.add(new VarInsnNode(25, 2));
                list.add(new MethodInsnNode(184, "com/janetfilter/plugins/power/ArgsFilter", "testFilter", "(Ljava/math/BigInteger;Ljava/math/BigInteger;Ljava/math/BigInteger;)[Ljava/math/BigInteger;", false));
                list.add(new VarInsnNode(58, 3));
                list.add(new InsnNode(1));
                list.add(new VarInsnNode(25, 3));
                LabelNode label0 = new LabelNode();
                list.add(new JumpInsnNode(165, label0));
                list.add(new VarInsnNode(25, 3));
                list.add(new InsnNode(3));
                list.add(new InsnNode(50));
                list.add(new VarInsnNode(58, 1));
                list.add(new VarInsnNode(25, 3));
                list.add(new InsnNode(4));
                list.add(new InsnNode(50));
                list.add(new VarInsnNode(58, 2));
                list.add(label0);
                mn.instructions.insert(list);
            }
        }
        ClassWriter writer = new ClassWriter(3);
        node.accept(writer);
        return writer.toByteArray();
    }
}
