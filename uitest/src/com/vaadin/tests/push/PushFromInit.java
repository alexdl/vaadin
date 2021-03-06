/*
 * Copyright 2000-2013 Vaadin Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.tests.push;

import com.vaadin.server.VaadinRequest;
import com.vaadin.tests.components.AbstractTestUIWithLog;
import com.vaadin.ui.Button;

public class PushFromInit extends AbstractTestUIWithLog {

    @Override
    protected void setup(VaadinRequest request) {
        new Thread() {
            @Override
            public void run() {
                access(new Runnable() {
                    @Override
                    public void run() {
                        log("Logged from background thread started in init");
                    }
                });
            }
        }.start();
        log("Logged in init");
        addComponent(new Button("Sync"));
    }

    @Override
    protected String getTestDescription() {
        return "Pusing something to a newly created UI should not cause race conditions";
    }

    @Override
    protected Integer getTicketNumber() {
        return Integer.valueOf(11529);
    }

}
