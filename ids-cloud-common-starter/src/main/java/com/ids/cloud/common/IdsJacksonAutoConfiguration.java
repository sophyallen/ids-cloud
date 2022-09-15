package com.ids.cloud.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static com.ids.cloud.common.IdsJacksonAutoConfiguration.SerializerFeature.*;


/**
 * @author zmc
 * @date: 2019/5/20 14:56
 * @description:
 */
@Slf4j
@AutoConfigureBefore(JacksonAutoConfiguration.class)
public class IdsJacksonAutoConfiguration {

    public enum SerializerFeature {
        WriteNullListAsEmpty,
        WriteNullStringAsEmpty,
        WriteNullNumberAsZero,
        WriteNullBooleanAsFalse,
        WriteNullMapAsEmpty;
        public final int mask;

        SerializerFeature() {
            mask = (1 << ordinal());
        }
    }

    public static class FastJsonSerializerFeatureCompatibleForJackson extends BeanSerializerModifier {
        final private JsonSerializer<Object> nullBooleanJsonSerializer;
        final private JsonSerializer<Object> nullNumberJsonSerializer;
        final private JsonSerializer<Object> nullListJsonSerializer;
        final private JsonSerializer<Object> nullStringJsonSerializer;
        final private JsonSerializer<Object> nullMapJsonSerializer;

        FastJsonSerializerFeatureCompatibleForJackson(SerializerFeature... features) {
            int config = 0;
            for (SerializerFeature feature : features) {
                config |= feature.mask;
            }
            nullBooleanJsonSerializer = (config & WriteNullBooleanAsFalse.mask) != 0 ? new NullBooleanSerializer() : null;
            nullNumberJsonSerializer = (config & WriteNullNumberAsZero.mask) != 0 ? new NullNumberSerializer() : null;
            nullListJsonSerializer = (config & WriteNullListAsEmpty.mask) != 0 ? new NullListJsonSerializer() : null;
            nullStringJsonSerializer = (config & WriteNullStringAsEmpty.mask) != 0 ? new NullStringSerializer() : null;
            nullMapJsonSerializer = (config & WriteNullMapAsEmpty.mask) != 0 ? new NullMapSerializer() : null;
        }

        @Override
        public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
            for (BeanPropertyWriter writer : beanProperties) {
                final JavaType javaType = writer.getType();
                final Class<?> rawClass = javaType.getRawClass();
                if (javaType.isArrayType() || javaType.isCollectionLikeType()) {
                    writer.assignNullSerializer(nullListJsonSerializer);
                } else if (Number.class.isAssignableFrom(rawClass) && (rawClass.getName().startsWith("java.lang") || rawClass.getName().startsWith("java.match"))) {
                    writer.assignNullSerializer(nullNumberJsonSerializer);
                } else if (Boolean.class.equals(rawClass)) {
                    writer.assignNullSerializer(nullBooleanJsonSerializer);
                } else if (String.class.equals(rawClass) || Date.class.equals(rawClass)) {
                    writer.assignNullSerializer(nullStringJsonSerializer);
                } else if (!Date.class.equals(rawClass)) {
                    writer.assignNullSerializer(nullMapJsonSerializer);
                }
            }
            return beanProperties;
        }

        private static class NullListJsonSerializer extends JsonSerializer<Object> {
            @Override
            public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
                jgen.writeStartArray();
                jgen.writeEndArray();
            }
        }

        private static class NullNumberSerializer extends JsonSerializer<Object> {
            @Override
            public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
                jgen.writeNumber(0);
            }
        }

        private static class NullBooleanSerializer extends JsonSerializer<Object> {
            @Override
            public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
                jgen.writeBoolean(false);
            }
        }

        private static class NullStringSerializer extends JsonSerializer<Object> {
            @Override
            public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
                jgen.writeString("");
            }
        }

        private static class NullMapSerializer extends JsonSerializer<Object> {
            @Override
            public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
                jgen.writeStartObject();
                jgen.writeEndObject();
            }
        }
    }

    /**
     * Jackson全局配置
     *
     * @param properties
     * @return
     */
    @Bean
    @Primary
    public JacksonProperties jacksonProperties(JacksonProperties properties) {
        properties.setDefaultPropertyInclusion(JsonInclude.Include.ALWAYS);
        properties.getSerialization().put(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        properties.getSerialization().put(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        properties.getSerialization().put(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        properties.getDeserialization().put(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        properties.setDateFormat("yyyy-MM-dd HH:mm:ss");
        properties.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        log.info("JacksonProperties [{}]", properties);
        return properties;
    }

    /**
     * 转换器全局配置
     *
     * @return
     */
    @Bean
    public HttpMessageConverters httpMessageConverters(ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter(objectMapper);
        log.info("MappingJackson2HttpMessageConverter [{}]", jackson2HttpMessageConverter);
        return new HttpMessageConverters(jackson2HttpMessageConverter);
    }

    @Bean
    public ObjectMapper jacksonObjectMapper() {
        ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder()
                .createXmlMapper(false)
//                .serializationInclusion(JsonInclude.Include.ALWAYS)
//                .featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS,
//                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .simpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .timeZone("GMT+8")
//                .featuresToEnable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)
                .build();

        // 兼容fastJson 的一些空值处理
       SerializerFeature[] features = new SerializerFeature[]{
                WriteNullListAsEmpty,
//                WriteNullStringAsEmpty,
//                WriteNullNumberAsZero,
//                WriteNullBooleanAsFalse,
//                WriteNullMapAsEmpty
        };
        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(new FastJsonSerializerFeatureCompatibleForJackson(features)));
        log.info("ObjectMapper [{}]", objectMapper);
        return objectMapper;
    }

}
