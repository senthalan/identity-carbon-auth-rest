/*
 * Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.cors.valve.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.wso2.carbon.identity.cors.service.CORSManager;
import org.wso2.carbon.user.core.service.RealmService;

/**
 * Service component class for the CORS valve.
 */
@Component(name = "identity.cors.valve.component", immediate = true)
public class CORSValveServiceComponent {

    private static final Log log = LogFactory.getLog(CORSValveServiceComponent.class);

    /**
     * Activate the CORSValveServiceComponent.
     *
     * @param context
     */
    @Activate
    protected void activate(ComponentContext context) {

        if (log.isDebugEnabled()) {
            log.debug("CORSValveServiceComponent is activated.");
        }
    }

    /**
     * Deactivate the CORSServiceComponent.
     *
     * @param context
     */
    @Deactivate
    protected void deactivate(ComponentContext context) {

        if (log.isDebugEnabled()) {
            log.debug("CORSServiceComponent is deactivated.");
        }
    }

    /**
     * Register the CORSManager.
     *
     * @param corsManager
     */
    @Reference(
            name = "cors.service",
            service = CORSManager.class,
            cardinality = ReferenceCardinality.MANDATORY,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unregisterCORSManager")
    protected void registerCorsManager(CORSManager corsManager) {

        if (log.isDebugEnabled()) {
            log.debug("Registering the CORSConfigurationDAO in CORSValve.");
        }
        CORSValveServiceHolder.getInstance().setCorsManager(corsManager);
    }

    /**
     * Unregister the CORSManager.
     *
     * @param corsManager
     */
    protected void unregisterCORSManager(CORSManager corsManager) {

        if (log.isDebugEnabled()) {
            log.debug("Unregistering the CORSConfigurationDAO in CORSValve.");
        }
        CORSValveServiceHolder.getInstance().setCorsManager(null);
    }

    @Reference(
            name = "user.realmservice.default",
            service = org.wso2.carbon.user.core.service.RealmService.class,
            cardinality = ReferenceCardinality.MANDATORY,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unsetRealmService")
    protected void setRealmService(RealmService realmService) {
        if (log.isDebugEnabled()) {
            log.debug("Setting the Realm Service.");
        }
        CORSValveServiceHolder.getInstance().setRealmService(realmService);
    }

    protected void unsetRealmService(RealmService realmService) {
        if (log.isDebugEnabled()) {
            log.debug("Unsetting the Realm Service.");
        }
        CORSValveServiceHolder.getInstance().setRealmService(null);
    }
}
