package com.janetfilter.plugins.hideme;

import com.janetfilter.core.Environment;
import com.janetfilter.core.plugin.MyTransformer;
import java.util.Iterator;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.FieldInsnNode;
import jdk.internal.org.objectweb.asm.tree.InsnList;
import jdk.internal.org.objectweb.asm.tree.MethodInsnNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;
import jdk.internal.org.objectweb.asm.tree.VarInsnNode;

/* loaded from: hideme.jar:com/janetfilter/plugins/hideme/VMTransformer.class */
public class VMTransformer implements MyTransformer {
    private final Environment environment;

    public VMTransformer(Environment environment) {
        this.environment = environment;
    }

    public String getHookClassName() {
        return "sun/management/VMManagementImpl";
    }

    public byte[] transform(String className, byte[] classBytes, int order) throws Exception {
        VmArgumentFilter.setEnvironment(this.environment);
        ClassReader reader = new ClassReader(classBytes);
        ClassNode node = new ClassNode(327680);
        reader.accept(node, 0);
        for (MethodNode mn : node.methods) {
            if ("getVmArguments".equals(mn.name) && "()Ljava/util/List;".equals(mn.desc)) {
                InsnList list = new InsnList();
                list.add(new VarInsnNode(25, 0));
                list.add(new VarInsnNode(25, 0));
                list.add(new FieldInsnNode(180, "sun/management/VMManagementImpl", "vmArgs", "Ljava/util/List;"));
                list.add(new MethodInsnNode(184, "com/janetfilter/plugins/hideme/VmArgumentFilter", "testArgs", "(Ljava/util/List;)Ljava/util/List;", false));
                list.add(new FieldInsnNode(181, "sun/management/VMManagementImpl", "vmArgs", "Ljava/util/List;"));
                Iterator<AbstractInsnNode> it = mn.instructions.iterator();
                while (true) {
                    if (it.hasNext()) {
                        AbstractInsnNode in = it.next();
                        if (0 == in.getType() && 176 == in.getOpcode()) {
                            mn.instructions.insert(in.getPrevious().getPrevious(), list);
                            break;
                        }
                    }
                }
            }
        }
        ClassWriter writer = new ClassWriter(3);
        node.accept(writer);
        return writer.toByteArray();
    }
}
