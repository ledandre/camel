/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.direct;

import org.apache.camel.CamelContext;
import org.apache.camel.ComponentConfiguration;
import org.apache.camel.ContextTestSupport;
import org.apache.camel.EndpointConfiguration;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Test;

public class DirectComponentConfigurationAndDocumentationTest extends ContextTestSupport {

    @Override
    public boolean isUseRouteBuilder() {
        return false;
    }

    @Test
    public void testComponentConfiguration() throws Exception {
        DirectComponent comp = context.getComponent("direct", DirectComponent.class);
        EndpointConfiguration conf = comp.createConfiguration("direct:foo?block=true");

        assertEquals("true", conf.getParameter("block"));

        ComponentConfiguration compConf = comp.createComponentConfiguration();
        String json = compConf.createParameterJsonSchema();
        assertNotNull(json);

        assertTrue(json.contains("\"timeout\": { \"kind\": \"parameter\", \"label\": \"producer\", \"type\": \"integer\""));
        assertTrue(json.contains("\"block\": { \"kind\": \"parameter\", \"label\": \"producer\", \"type\": \"boolean\""));
    }

    @Test
    public void testComponentDocumentation() throws Exception {
        CamelContext context = new DefaultCamelContext();
        String html = context.getComponentDocumentation("direct");
        assertNotNull("Should have found some auto-generated HTML", html);
    }

    @Test
    public void testComponentJsonSchema() throws Exception {
        CamelContext context = new DefaultCamelContext();
        String json = context.getComponentParameterJsonSchema("direct");
        assertNotNull("Should have found some auto-generated JSON", json);
        log.info(json);

        // should include javadoc
        assertTrue("Should include javadoc", json.contains("\"timeout\": { \"kind\": \"parameter\", \"label\": \"producer\","
                + " \"type\": \"integer\", \"javaType\": \"long\", \"deprecated\": \"false\", \"defaultValue\": \"30000\","
                + " \"description\": \"The timeout value to use if block is enabled."));
    }

}
