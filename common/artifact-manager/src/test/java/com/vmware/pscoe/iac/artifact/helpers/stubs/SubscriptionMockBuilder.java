package com.vmware.pscoe.iac.artifact.helpers.stubs;

/*
 * #%L
 * artifact-manager
 * %%
 * Copyright (C) 2023 VMware
 * %%
 * Build Tools for VMware Aria
 * Copyright 2023 VMware, Inc.
 * 
 * This product is licensed to you under the BSD-2 license (the "License"). You may not use this product except in compliance with the BSD-2 License.  
 * 
 * This product may include a number of subcomponents with separate copyright notices and license terms. Your use of these subcomponents is subject to the terms and conditions of the subcomponent's license, as noted in the LICENSE file.
 * #L%
 */

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vmware.pscoe.iac.artifact.model.vrang.VraNgSubscription;

import org.apache.commons.io.IOUtils;

public class SubscriptionMockBuilder {
	private JsonElement mockData;
	private String		id;
	private	String		name;

	public SubscriptionMockBuilder() throws IOException {
		ClassLoader cl = getClass().getClassLoader();
		try {
			String read = IOUtils.toString( cl.getResourceAsStream("test/fixtures/subscription.json"), StandardCharsets.UTF_8 );;
			this.mockData = JsonParser.parseString(read);
		}
		catch (IOException ex) {
			throw ex;
		}
	}

	public SubscriptionMockBuilder setName(String name){
		this.name = name;
		return this;
	}

	public SubscriptionMockBuilder setId(String id){
		this.id = id;
		return this;
	}

	public SubscriptionMockBuilder setPropertyInRawData(String key, String value) {
		JsonObject subscription = this.mockData.getAsJsonObject();
		if(subscription.has(key)) {
			subscription.remove(key);
			subscription.addProperty(key, value);
		}
		this.mockData = subscription.getAsJsonObject();
		return this;
	}

	public VraNgSubscription build() {
		JsonObject subscription = this.mockData.getAsJsonObject();
		
		return new VraNgSubscription(this.id, this.name, subscription.toString());
	}
}
