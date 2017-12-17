package com.jayden.modifyclassesplugin;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

import java.util.Arrays;

/**
 * 方法耗时 visitor
 */
public class ViewClickClassVisitor extends ClassVisitor {
    private boolean shouldInstrumentOnClick;

    public ViewClickClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor);
    }

    @Override
    public void visit(
            int version,
            int access,
            String name,
            String signature,
            String superName,
            String[] interfaces) {
        // Call down the class visitor chain.
        cv.visit(version, access, name, signature, superName, interfaces);

        shouldInstrumentOnClick =
                Arrays.asList(interfaces).contains("android/view/View$OnClickListener");
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature,
                                     String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
        if (shouldInstrumentOnClick && name.equals("onClick")) {
            methodVisitor = new AdviceAdapter(Opcodes.ASM5, methodVisitor, access, name, desc) {
//                @Override
//                public void visitCode() {
//                    mv.visitCode();
//
//                    mv.visitLdcInsn("SPY");
//                    mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
//                    mv.visitInsn(DUP);
//                    mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
//                    mv.visitLdcInsn("saw click on ");
//                    mv.visitMethodInsn(
//                            INVOKEVIRTUAL,
//                            "java/lang/StringBuilder",
//                            "append",
//                            "(Ljava/lang/String;)Ljava/lang/StringBuilder;",
//                            false);
//                    mv.visitVarInsn(ALOAD, 1);
//                    mv.visitMethodInsn(
//                            INVOKEVIRTUAL,
//                            "java/lang/StringBuilder",
//                            "append",
//                            "(Ljava/lang/Object;)Ljava/lang/StringBuilder;",
//                            false);
//                    mv.visitMethodInsn(
//                            INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
//                    mv.visitMethodInsn(
//                            INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
//                    mv.visitInsn(POP);
//
//                }

                @Override
                protected void onMethodEnter() {
                    mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                    mv.visitLdcInsn("========start=========" + name);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
                            "(Ljava/lang/String;)V", false);
                }

                @Override
                protected void onMethodExit(int i) {
                    mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                    mv.visitLdcInsn("========end=========");
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
                            "(Ljava/lang/String;)V", false);
//                    mv.visitTypeInsn(NEW, "com/jayden/modifyclassesplugin/HookClickListener");
//                    mv.visitInsn(DUP);
//                    mv.visitVarInsn(ALOAD, 1);
//                    mv.visitMethodInsn(INVOKESPECIAL, "com/jayden/modifyclassesplugin/HookClickListener", "<init>", "(Landroid/view/View$OnClickListener;)V", false);
//                    mv.visitVarInsn(ASTORE, 2);
//                    mv.visitVarInsn(ALOAD, 0);
//                    mv.visitMethodInsn(INVOKEVIRTUAL, "android/view/View", "getListenerInfo", "()Landroid/view/View$ListenerInfo;", false);
//                    mv.visitVarInsn(ALOAD, 2);
//                    mv.visitFieldInsn(PUTFIELD, "android/view/View$ListenerInfo", "mOnClickListener", "Landroid/view/View$OnClickListener;");
//                    mv.visitInsn(RETURN);
//                    mv.visitMaxs(3, 3);
                }
            };
        }

        return methodVisitor;
    }
}
