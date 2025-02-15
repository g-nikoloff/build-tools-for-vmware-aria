package com.vmware.pscoe.iac.artifact.store.filters;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

public class CustomFolderFileFilter implements FilenameFilter {
	private final List<String> descriptionNames;
	private final Logger logger;

	public CustomFolderFileFilter(List<String> descriptionNames) {
		super();
		this.descriptionNames = descriptionNames;
		this.logger = LoggerFactory.getLogger(this.getClass());
	}

	/**
	 * Method that filters files based on what is defined in the content.yaml
	 * Accepts only files and filters them by name as defined in their respective categories in the content.yaml
	 * 
	 * @param dir File to filter
	 * @param name name of the file
	 * @return boolean
	 */
	@Override
	public boolean accept(File dir, String name) {
		logger.debug("Process file for filtering '{}/{}'", dir.getAbsolutePath(), name);
		String items = descriptionNames != null ? String.join(", ", descriptionNames) : "NULL";
		logger.debug("Items in descriptor (content.yaml): {}", items);
		File currentFile = new File(dir, name);
		//There are cases where in the main item folder, there is a sub-directory, e.g. catalog-item/forms/
		if (currentFile.isDirectory()) { 
			return false;
		}
		// following name replace regex, matches the file extension only, by which it enables to have "." in the names 
		// for the different entities - bps, forms, etc. e.g. Name "RHEL 8.X Family" -> produced "RHEL 8.X Family.json" 
		// -> run replaceFirst [.][^.]+$ = "" -> RHEL 8.X Family
		return descriptionNames == null || descriptionNames.contains(name.replaceFirst("[.][^.]+$", ""));
	}
}
