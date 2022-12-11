package com.isntyet.java.practice.human.infra.config;

import com.fasterxml.jackson.databind.type.TypeFactory;
import feign.FeignException;
import feign.RequestInterceptor;
import feign.Response;
import feign.codec.Decoder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ResolvableType;

import java.io.IOException;
import java.lang.reflect.Type;

public class HumanFeignClientConfig {
    @Bean
    public RequestInterceptor requestInterceptor() throws InterruptedException {
        return requestTemplate -> requestTemplate.header("header-name", "header-value");
    }

    @Bean
    public Decoder decoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        return new HumanServiceDecoder(new SpringDecoder(messageConverters));
    }

    public static class HumanServiceDecoder implements Decoder {
        private final Decoder decoder;

        public HumanServiceDecoder(Decoder decoder) {
            this.decoder = decoder;
        }

        @Override
        public Object decode(Response response, Type type) throws IOException, FeignException {
            var returnType = TypeFactory.rawClass(type);
            var forClassWithGenerics =
                    ResolvableType.forClassWithGenerics(HumanServiceCommonResponse.class, returnType);

            try {
                return ((HumanServiceCommonResponse<?>) decoder.decode(response,
                        forClassWithGenerics.getType())).getData();
            } catch (Exception e) {
                return decoder.decode(response, forClassWithGenerics.getType());
            }
        }
    }

    @Getter
    @Setter
    public static class HumanServiceCommonResponse<T> {
        private Result result;
        private T data;
        private String message;
        private String errorCode;

        public enum Result {
            SUCCESS, FAIL
        }
    }
}
