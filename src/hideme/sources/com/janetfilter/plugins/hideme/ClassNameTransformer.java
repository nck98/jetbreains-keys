package com.janetfilter.plugins.hideme;

import com.janetfilter.core.plugin.MyTransformer;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.InsnList;
import jdk.internal.org.objectweb.asm.tree.MethodInsnNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;
import jdk.internal.org.objectweb.asm.tree.VarInsnNode;

/* loaded from: hideme.jar:com/janetfilter/plugins/hideme/ClassNameTransformer.class */
public class ClassNameTransformer implements MyTransformer {
    public String getHookClassName() {
        return "java/lang/Class";
    }

    public byte[] transform(String className, byte[] classBytes, int order) throws Exception {
        ClassReader reader = new ClassReader(classBytes);
        ClassNode node = new ClassNode(327680);
        reader.accept(node, 0);
        for (MethodNode mn : node.methods) {
            if ("forName".equals(mn.name) && mn.desc.startsWith("(Ljava/lang/String;")) {
                InsnList list = new InsnList();
                list.add(new VarInsnNode(25, 0));
                list.add(new MethodInsnNode(184, "com/janetfilter/plugins/hideme/ClassNameFilter", "testClass", "(Ljava/lang/String;)V", false));
                mn.instructions.insert(list);
            }
        }
        ClassWriter writer = new ClassWriter(3);
        node.accept(writer);
        return writer.toByteArray();
    }
}
