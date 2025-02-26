/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shenyu.client.springcloud.register;

import org.apache.shenyu.client.core.disruptor.ShenyuClientRegisterEventPublisher;
import org.apache.shenyu.client.core.register.ApiBean;
import org.apache.shenyu.client.core.register.ClientRegisterConfig;
import org.apache.shenyu.client.springcloud.annotation.ShenyuSpringCloudClient;
import org.apache.shenyu.common.enums.RpcTypeEnum;
import org.apache.shenyu.register.client.api.ShenyuClientRegisterRepository;
import org.apache.shenyu.register.common.dto.MetaDataRegisterDTO;
import org.apache.shenyu.register.common.type.DataTypeParent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

public class SpringCloudApiMetaRegisterTest {

    private TestShenyuClientRegisterEventPublisher testShenyuClientRegisterEventPublisher;

    private SpringCloudApiMetaRegister springCloudApiMetaRegister;

    @BeforeEach
    public void init() {

        testShenyuClientRegisterEventPublisher = new TestShenyuClientRegisterEventPublisher();

        springCloudApiMetaRegister = new SpringCloudApiMetaRegister(
                testShenyuClientRegisterEventPublisher, new TestClientRegisterConfig());
    }

    @Test
    void testAnnotatedClass() throws Exception {

        ApiBean apiBean = createSimpleApiBean(TestApiBeanAnnotatedClass.class);

        springCloudApiMetaRegister.register(apiBean);

        assertThat(testShenyuClientRegisterEventPublisher.metaData.getPath(),
                equalTo("/testContext/testClass/testMethod"));
    }

    @Test
    void testAnnotatedMethod() throws Exception {

        ApiBean apiBean = createSimpleApiBean(TestApiBeanAnnotatedMethod.class);

        springCloudApiMetaRegister.register(apiBean);

        assertThat(testShenyuClientRegisterEventPublisher.metaData.getPath(),
                equalTo("/testContext/testClass/testMethod"));
    }

    @Test
    void testAnnotatedMethodAndClass() throws Exception {

        ApiBean apiBean = createSimpleApiBean(TestApiBeanAnnotatedMethodAndClass.class);

        springCloudApiMetaRegister.register(apiBean);

        assertThat(testShenyuClientRegisterEventPublisher.metaData.getPath(),
                equalTo("/testContext/testClass/testMethod"));
    }

    @Test
    void testApiBeanNoAnnotated() throws Exception {

        ApiBean apiBean = createSimpleApiBean(TestApiBeanNoAnnotated.class);

        springCloudApiMetaRegister.register(apiBean);

        assertThat(testShenyuClientRegisterEventPublisher.metaData, nullValue());
    }

    @Test
    void testPreApiBean() throws Exception {

        ApiBean apiBean = createSimpleApiBean(TestPreApiBean.class);

        springCloudApiMetaRegister.register(apiBean);

        assertThat(testShenyuClientRegisterEventPublisher.metaData.getPath(),
                equalTo("/testContext/testClass/**"));

    }

    @Test
    void testApiBeanDifferentPath() throws Exception {

        ApiBean apiBean = createSimpleApiBean(TestApiBeanDifferentPath.class);

        springCloudApiMetaRegister.register(apiBean);

        assertThat(testShenyuClientRegisterEventPublisher.metaData.getPath(),
                equalTo("/testContext/testClassPath/testMethodPath"));

    }

    private static ApiBean createSimpleApiBean(final Class<?> beanClass) throws Exception {

        ApiBean apiBean = new ApiBean("testContext",
                beanClass.getName(), beanClass.getDeclaredConstructor().newInstance(),
                "/testClass", beanClass);

        apiBean.addApiDefinition(beanClass.getMethod("testMethod"), "/testMethod");

        return apiBean;
    }

    @ShenyuSpringCloudClient
    @RestController
    @RequestMapping("/testClass")
    static class TestApiBeanAnnotatedClass {
        @RequestMapping("/testMethod")
        public String testMethod() {
            return "";
        }
    }

    @RestController
    @RequestMapping("/testClass")
    static class TestApiBeanAnnotatedMethod {

        @RequestMapping("/testMethod")
        @ShenyuSpringCloudClient("/testMethod")
        public String testMethod() {
            return "";
        }
    }

    @ShenyuSpringCloudClient
    @RestController
    @RequestMapping("/testClass")
    static class TestApiBeanAnnotatedMethodAndClass {

        @RequestMapping("/testMethod")
        @ShenyuSpringCloudClient("/testMethod")
        public String testMethod() {
            return "";
        }
    }

    @RestController
    @ShenyuSpringCloudClient("/testClass/**")
    @RequestMapping("/testClass")
    static class TestPreApiBean {

        @RequestMapping("/testMethod")
        @ShenyuSpringCloudClient
        public String testMethod() {
            return "";
        }
    }

    @RestController
    @RequestMapping("/testClass")
    static class TestApiBeanNoAnnotated {

        @RequestMapping("/testMethod")
        public String testMethod() {
            return "";
        }
    }

    @RestController
    @ShenyuSpringCloudClient("/testClassPath")
    @RequestMapping("/testClass")
    static class TestApiBeanDifferentPath {

        @RequestMapping("/testMethod")
        @ShenyuSpringCloudClient("/testMethodPath")
        public String testMethod() {
            return "";
        }
    }

    static class TestShenyuClientRegisterEventPublisher extends ShenyuClientRegisterEventPublisher {

        private MetaDataRegisterDTO metaData;

        @Override
        public void start(final ShenyuClientRegisterRepository shenyuClientRegisterRepository) {
        }

        @Override
        public void publishEvent(final DataTypeParent data) {
            this.metaData = (MetaDataRegisterDTO) data;
        }
    }

    static class TestClientRegisterConfig implements ClientRegisterConfig {
        @Override
        public Integer getPort() {
            return -1;
        }

        @Override
        public String getHost() {
            return "127.0.0.1";
        }

        @Override
        public String getAppName() {
            return "test";
        }

        @Override
        public String getContextPath() {
            return "testContext";
        }

        @Override
        public String getIpAndPort() {
            return "127.0.0.1:80";
        }

        @Override
        public Boolean getAddPrefixed() {
            return false;
        }

        @Override
        public RpcTypeEnum getRpcTypeEnum() {
            return RpcTypeEnum.HTTP;
        }
    }

}
