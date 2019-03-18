package com.haiyang.poi;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 功能：Excel文件导入
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD})
public @interface ExcelVOAttribute {
    /**
     * Excel导入名称
     * @return
     */
	public abstract String name();
	
	/**
	 * 配置Excel列的名称，对应A，B，C....
	 * @return
	 */
	public abstract String column();
	
	/**
	 * 提示信息
	 * @return
	 */
	public abstract String prompt() default "";
	
	/**
	 * 设置只能选择不能输入的列内容
	 * @return
	 */
	public abstract String[] combo() default {};
	
	/**
	 * 是否导出数据，应对需求：
	 * 有时我们需要导出一份模板，这是标题但内容需要用户手工填写
	 * @return
	 */
	public abstract boolean isExport() default true;
}
