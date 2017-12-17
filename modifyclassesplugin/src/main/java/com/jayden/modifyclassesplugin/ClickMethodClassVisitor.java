package com.jayden.modifyclassesplugin;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

import java.util.Arrays;

/**
 * Created by Jayden on 2017/7/20.
 */

public class ClickMethodClassVisitor extends ClassVisitor {

    private boolean shouldInstrumentOnClick;

    public ClickMethodClassVisitor(ClassVisitor classVisitor) {
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
    public MethodVisitor visitMethod(
            int access, String name, String desc, String signature, String[] exceptions) {
        // Get a method visitor from further down the class visitor chain.
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        if (shouldInstrumentOnClick && name.equals("onClick")) {
            // Add our method visitor to the chain.
            mv = new AdviceAdapter(Opcodes.ASM5, mv, access, name, desc) {
                @Override
                public void visitCode() {
                    //com.jayden.versionopo
                    // D/SPY: saw click on android.widget.Button{a7c6a07 VFED..C.. ...P.... 32,32-248,128 #7f0e0094 app:id/jayden}
                    // D/jayden: wfj onclick
                    mv.visitCode();

                    mv.visitLdcInsn("SPY");
                    mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
                    mv.visitInsn(DUP);
                    mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
                    mv.visitLdcInsn("saw click on ");
                    mv.visitMethodInsn(
                            INVOKEVIRTUAL,
                            "java/lang/StringBuilder",
                            "append",
                            "(Ljava/lang/String;)Ljava/lang/StringBuilder;",
                            false);
                    mv.visitVarInsn(ALOAD, 1);
                    mv.visitMethodInsn(
                            INVOKEVIRTUAL,
                            "java/lang/StringBuilder",
                            "append",
                            "(Ljava/lang/Object;)Ljava/lang/StringBuilder;",
                            false);
                    mv.visitMethodInsn(
                            INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
                    mv.visitMethodInsn(
                            INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
                    mv.visitInsn(POP);
                }
            };
        }
        return mv;
    }


}
