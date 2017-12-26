package cn.xstar.samplespringboot.templates;

import freemarker.template.Configuration;

import java.io.Writer;

/**
 * freeemarker 模版接口
 */
public interface FMTemplate {
    /**
     * 获取freemarker配置实例
     *
     * @return
     */
    Configuration getConfg();

    /**
     * 绑定模版文件夹
     *
     * @param context servlet上下文
     * @param dicPath 模版文件夹路径
     * @return
     */
    FMTemplate bindTemplateDic(Object context, String dicPath);

    /**
     * 根据文件名获绑定模版
     *
     * @param object   要绑定的数据
     * @param writer   要写入的流
     * @param filePath 模版文档
     * @return status code
     */
    int bindTemplate(Object object, Writer writer, String filePath);
}
