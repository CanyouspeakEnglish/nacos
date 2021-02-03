/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.nacos.client.config.listener.impl;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigChangeItem;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.alibaba.nacos.client.config.impl.ConfigChangeHandler;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executor;

public class ConfigChangeHandlerTest {

    @Test
    public void testParseProperties() throws IOException {
        Map properties = ConfigChangeHandler.getInstance().parseChangeData("", "app.name = nacos", "properties");
        Assert.assertEquals("nacos", ((ConfigChangeItem) properties.get("app.name")).getNewValue());
    }

    @Test
    public void testParseYaml() throws IOException {
        Map properties = ConfigChangeHandler.getInstance().parseChangeData("", "app:\n  name: nacos", "yaml");
        Assert.assertEquals("nacos", ((ConfigChangeItem) properties.get("app.name")).getNewValue());
    }
    @Test
    public void testConfig(){

        try {
            NamingService namingService = NacosFactory.createNamingService("118.190.155.155:8848");
            namingService.registerInstance("test","192.168.1.1",8000);
            namingService.registerInstance("test","test","192.168.1.1",8000);
            namingService.subscribe("test",(v)->{
                NamingEvent namingEvent = (NamingEvent)v;
                System.out.println(namingEvent.getServiceName());

            });
           /* ConfigService configService = NacosFactory.createConfigService("118.190.155.155:8848");
            configService.getConfig("com.lzx.test.config","lzx-group",10000);
            configService.addListener("com.lzx.test.config", "lzx-group", new Listener() {
                @Override
                public Executor getExecutor() {
                    return null;
                }

                @Override
                public void receiveConfigInfo(String configInfo) {
                    System.out.println("--------------------"+configInfo);
                }
            });*/
            System.in.read();
        } catch (NacosException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
