/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.karaf.cellar.obr.shell;

import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.cellar.core.Group;
import org.apache.karaf.cellar.core.event.EventProducer;
import org.apache.karaf.cellar.core.event.EventType;
import org.apache.karaf.cellar.core.shell.CellarCommandSupport;
import org.apache.karaf.cellar.obr.ObrBundleEvent;

/**
 * Deploy a bundle from the OBR.
 */
@Command(scope = "cluster", name = "obr-deploy", description = "Deploy a bundle from the OBR on a cluster group")
public class ObrDeployCommand extends CellarCommandSupport {

    @Argument(index = 0, name = "group", description = "The cluster group where to deploy the bundle from the OBR", required = true, multiValued = false)
    String groupName;

    @Argument(index = 1, name="bundleId", description = "The bundle ID (in the OBR) to deploy", required = true, multiValued = false)
    String bundleId;

    @Override
    protected Object doExecute() throws Exception {
        Group group = groupManager.findGroupByName(groupName);
        EventProducer producer = eventTransportFactory.getEventProducer(groupName, true);
        ObrBundleEvent event = new ObrBundleEvent(bundleId, EventType.INBOUND);
        event.setForce(true);
        event.setSourceGroup(group);
        producer.produce(event);
        return null;
    }

}