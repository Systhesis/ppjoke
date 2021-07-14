package com.neil.libnavcompiler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.auto.service.AutoService;
import com.neil.libnavannotation.ActivityDestination;
import com.neil.libnavannotation.FragmentDestination;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

/**
 * @author zhongnan
 * @time 2021/7/13 20:40
 *
 */
@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({"com.neil.libnavannotation.ActivityDestination", "com.neil.libnavannotation.FragmentDestination"})
public class NavProcessor extends AbstractProcessor {
    private Messager messager;
    private Filer filer;
    private static final String OUT_PUT_FILE_NAME = "destination.json";
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> fragmentElements = roundEnvironment.getElementsAnnotatedWith(FragmentDestination.class);
        Set<? extends Element> activityElements = roundEnvironment.getElementsAnnotatedWith(ActivityDestination.class);

        if(!fragmentElements.isEmpty() || !activityElements.isEmpty()) {
            HashMap<String, JSONObject> destMap = new HashMap<>();
            handleDestination(fragmentElements, FragmentDestination.class, destMap);
            handleDestination(activityElements, ActivityDestination.class, destMap);

            FileOutputStream fos = null;
            OutputStreamWriter writer = null;
            //app/src/main/assets
            try {
                FileObject resource = filer.createResource(StandardLocation.CLASS_OUTPUT, "", OUT_PUT_FILE_NAME);
                String resourcePath = resource.toUri().getPath();
                messager.printMessage(Diagnostic.Kind.NOTE, "resourcePath >> " + resourcePath);
                String appPath = resourcePath.substring(0, resourcePath.indexOf("app") + 4);
                String assetsPath = appPath + "src/main/assets";

                File file = new File(assetsPath);
                if(!file.exists()) {
                    file.mkdirs();
                }

                File outputFile = new File(file, OUT_PUT_FILE_NAME);
                if(outputFile.exists()) {
                    outputFile.delete();
                }

                outputFile.createNewFile();
                String content = JSON.toJSONString(destMap);
                messager.printMessage(Diagnostic.Kind.NOTE, "json content is >> " + content);
                fos = new FileOutputStream(outputFile);
                writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                writer.write(content);
                writer.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        return true;
    }

    private void handleDestination(Set<? extends Element> elements, Class<? extends Annotation> annotationClazz, HashMap<String, JSONObject> destMap) {
        for(Element element : elements) {
            TypeElement typeElement = (TypeElement) element;
            String pageUrl = null;
            String clazzName = typeElement.getQualifiedName().toString();
            int id = Math.abs(clazzName.hashCode());
            boolean needLogin = false;
            boolean asStarter = false;
            boolean isFragment = false;

            Annotation annotation = typeElement.getAnnotation(annotationClazz);
            messager.printMessage(Diagnostic.Kind.NOTE, annotation.toString());
            if(annotation instanceof FragmentDestination) {
                FragmentDestination dest = (FragmentDestination) annotation;
                isFragment = true;
                pageUrl = dest.pageUrl();
                needLogin = dest.needLogin();
                asStarter = dest.asStarter();
            } else if(annotation instanceof ActivityDestination) {
                ActivityDestination dest = (ActivityDestination) annotation;
                pageUrl = dest.pageUrl();
                needLogin = dest.needLogin();
                asStarter = dest.asStarter();
            }

            if(destMap.containsKey(pageUrl)) {
                messager.printMessage(Diagnostic.Kind.ERROR, "不同的页面不允许使用相同的pageUrl: " + clazzName);
            } else {
                JSONObject object = new JSONObject();
                object.put("id", id);
                object.put("needLogin", needLogin);
                object.put("asStarter", asStarter);
                object.put("clazzName", clazzName);
                object.put("isFragment", isFragment);
                object.put("pageUrl", pageUrl);
                destMap.put(pageUrl, object);
            }
        }
    }
}
