package com.yellow.b.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@MapperScan("com.yellow.b.dao")
@EnableTransactionManagement
public class HttpMessageCoverConfig {

    @Bean
    @Primary
    public HttpMessageConverters fastJsonHttpMessageCoverters(){//自定义消息转换器
        FastJsonHttpMessageConverter fastCover = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setDateFormat("yyyy-DD-MM HH:mm:ss"); //时间转换格式
        fastJsonConfig.setSerializerFeatures(//json数据序列化配置
                SerializerFeature.PrettyFormat,//json数据格式化输出
                SerializerFeature.WriteNullStringAsEmpty,//将null的字符串转为空字符串
                SerializerFeature.WriteNullListAsEmpty,//将null的列表转为空字符串
                SerializerFeature.WriteMapNullValue,//将null的map转为空字符串
                SerializerFeature.MapSortField, //map字段排序
                SerializerFeature.DisableCircularReferenceDetect //禁用循环引用
        );
        fastCover.setFastJsonConfig(fastJsonConfig);
        return new HttpMessageConverters(fastCover);
    }
}
