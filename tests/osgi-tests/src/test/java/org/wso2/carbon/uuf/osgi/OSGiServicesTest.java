/*
 *  Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.wso2.carbon.uuf.osgi;

import org.ops4j.pax.exam.ExamFactory;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.ops4j.pax.exam.testng.listener.PaxExam;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.wso2.carbon.container.CarbonContainerFactory;
import org.wso2.carbon.kernel.utils.CarbonServerInfo;
import org.wso2.carbon.uuf.core.API;
import org.wso2.carbon.uuf.sample.petsstore.bundle.service.PetsStoreService;

import java.util.Map;
import javax.inject.Inject;



@Listeners(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
@ExamFactory(CarbonContainerFactory.class)
public class OSGiServicesTest {

    @Inject
    private CarbonServerInfo carbonServerInfo;

    @Inject
    BundleContext bundleContext;

    @Test
    public void testServerStarup() {
        Assert.assertNotNull(carbonServerInfo, "CarbonServerInfo Service is null");
    }

    @Test
    public void testPetsStoreService() {
        ServiceReference serviceReference = bundleContext.getServiceReference(PetsStoreService.class.getName());
        Assert.assertNotNull(serviceReference, "Pets Store Service Reference is null.");

        PetsStoreService petsStoreService = (PetsStoreService) bundleContext.getService(serviceReference);
        Assert.assertNotNull(petsStoreService, "Pets Store Service is null.");

        String serviceOutput = petsStoreService.getHelloMessage("Alice");
        Assert.assertEquals(serviceOutput, "Hello Alice!",
                            "Pets Store Service, getHelloMessage is not working properly.");
    }

    @Test
    public void testOSGiServicesAPI() throws Exception {
        String outputForCallOSGiService = API.callOSGiService(
                "org.wso2.carbon.uuf.sample.petsstore.bundle.service.PetsStoreService",
                "getHelloMessage", "Bob").toString();
        Assert.assertEquals(outputForCallOSGiService, "Hello Bob!");

        Map<String, Object> osgiServices = API.getOSGiServices(
                "org.wso2.carbon.uuf.sample.petsstore.bundle.service.PetsStoreService");
        Object petsStoreService = osgiServices.get(
                "org.wso2.carbon.uuf.sample.petsstore.bundle.internal.impl.PetsManagerImpl");
        Assert.assertNotNull(petsStoreService,
                             "PetsManagerImpl service wasn't retrieved from getOSGiServices method.");

        String serviceOutput = ((PetsStoreService) petsStoreService).getHelloMessage("Alice");
        Assert.assertEquals(serviceOutput, "Hello Alice!");
    }
}
