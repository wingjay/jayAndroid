package com.example.lego_processor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;

/**
 * RichViewHolderElement
 *
 * @author wingjay
 * @date 2017/09/23
 */
public class RichViewHolderElement {

    private static final String targetPackageName = "com.wingjay.jayandroid.richlist.uibase";
    private static final String targetClassName = "ViewHolderMapperImpl";

    private static final String superInterfacePackageName = "com.wingjay.jayandroid.richlist.uibase";
    private static final String superInterfaceName = "IViewHolderMapper";

    private static final String paramPackageName = "com.wingjay.jayandroid.richlist.uibase";
    private static final String paramClassName = "IRichViewHolder";

    private List<Pair<String, ClassName>> keyClassNameList;

    public RichViewHolderElement() {
        keyClassNameList = new ArrayList<>();
    }

    void addKeyClassName(String key, ClassName className) {
        keyClassNameList.add(Pair.create(key, className));
    }

    List<Pair<String, ClassName>> getKeyClassNameList() {
        return keyClassNameList;
    }

    String getPackageName() {
        return targetPackageName;
    }

    String getGeneratedClassName() {
        return targetClassName;
    }

    public static List<ExecutableElement> findMethods(Element element, Class<? extends Annotation> clazz) {
        List<ExecutableElement> methods = new ArrayList<>();
        for (Element enclosedElement : element.getEnclosedElements()) {
            Annotation annotation = enclosedElement.getAnnotation(clazz);
            if (annotation != null) {
                methods.add((ExecutableElement) enclosedElement);
            }
        }
        return methods;
    }

    ClassName superInterfaceName() {
        return ClassName.get(superInterfacePackageName, superInterfaceName);
    }

    MethodSpec createMatchMethod() {
        CodeBlock.Builder builder = CodeBlock.builder();

        builder.add("switch (key) {\n");
        for (Pair<String, ClassName> pair : keyClassNameList) {
            builder.indent();
            builder.add("case \"");
            builder.add(pair.first);
            builder.add("\": return ");
            //builder.add("$T;\n", pair.second.toString());
            builder.add("\"" + pair.second.toString() + "\";\n");
            builder.unindent();
        }
        builder.indent();
        builder.add("default: return null;\n");
        builder.unindent();
        builder.add("}\n");

        return MethodSpec.methodBuilder("match")
            .addModifiers(Modifier.PUBLIC)
            .addParameter(String.class, "key")
            .returns(String.class)
            //.returns(ParameterizedTypeName.get(ClassName.get(Class.class),
            //    WildcardTypeName.subtypeOf(ClassName.get(paramPackageName, paramClassName))))
            .addAnnotation(Override.class)
            .addCode(builder.build())
            .build();
    }

    static class Pair<A, B> {
        A first;
        B second;

        Pair(A first, B second) {
            this.first = first;
            this.second = second;
        }

        static <A, B> Pair<A, B> create(A a, B b) {
            return new Pair<>(a, b);
        }
    }
}
