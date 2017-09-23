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
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.WildcardTypeName;

/**
 * RichViewHolderElement
 *
 * @author wingjay
 * @date 2017/09/23
 */
public class RichViewHolderElement {

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
        return "com.wingjay.jayandroid.richlist.v5";
    }

    String getGeneratedClassName() {
        return "ViewHolderMapperImpl";
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
        return ClassName.get("com.wingjay.jayandroid.richlist.uibase", "IViewHolderMapper");
    }

    MethodSpec createMatchMethod() {
        CodeBlock.Builder builder = CodeBlock.builder();

        builder.add("switch (key) {\n");
        for (Pair<String, ClassName> pair : keyClassNameList) {
            builder.indent();
            builder.add("case \"");
            builder.add(pair.first);
            builder.add("\": return ");
            builder.add("$T.class;\n", pair.second);
            builder.unindent();
        }
        builder.indent();
        builder.add("default: return null;\n");
        builder.unindent();
        builder.add("}\n");

        return MethodSpec.methodBuilder("match")
            .addModifiers(Modifier.PUBLIC)
            .addParameter(String.class, "key")
            .returns(ParameterizedTypeName.get(ClassName.get(Class.class),
                WildcardTypeName.subtypeOf(ClassName.get("com.wingjay.jayandroid.richlist.uibase", "IRichViewHolder"))))
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
