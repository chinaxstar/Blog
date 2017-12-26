package cn.xstar.samplespringboot.templates;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;

@Component
public class SampleFMTemplate implements FMTemplate {
    private Configuration configuration;
    public static final int BIND_TEMPLATE_CODE_OK = 0x1001;
    public static final int BIND_TEMPLATE_CODE_PATH_ERROR = 0x1002;
    public static final int BIND_TEMPLATE_CODE_PROCESS_ERROR = 0x1003;

    public SampleFMTemplate() {
        configuration = new Configuration();
    }

    @Override
    public Configuration getConfg() {
        return configuration;
    }

    @Override
    public FMTemplate bindTemplateDic(Object context, String dicPath) {
        getConfg().setServletContextForTemplateLoading(context, dicPath);
        return this;
    }

    /**
     * @param obj
     * @param writer   要写入的流
     * @param filePath 模版文档
     * @return
     */
    @Override
    public int bindTemplate(Object obj, Writer writer, String filePath) {
        int code = BIND_TEMPLATE_CODE_OK;
        try {
            Template template = getConfg().getTemplate(filePath);
            template.process(obj, writer);
        } catch (IOException e) {
            e.printStackTrace();
            code = BIND_TEMPLATE_CODE_PATH_ERROR;
        } catch (TemplateException e) {
            e.printStackTrace();
            code = BIND_TEMPLATE_CODE_PROCESS_ERROR;
        }
        return code;
    }
}
