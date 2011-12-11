package org.zkforge.zktodo2;
/*
 * Copyright 2002-2006 the original author or authors:
 * http://loom.extrema-sistemas.com/
 * http://sourceforge.net/projects/loom/
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

import java.io.Serializable;

public class EntityNotFoundException extends Exception {
	
	private static final long serialVersionUID = 2867947966331594724L;
	
	/** id that could not be found in the database */
	final private Serializable entityId;
	final private String message;
	
	public EntityNotFoundException(Class<?> entityClass, Serializable entityId) {
		this(entityClass, entityId, null);
	}
	
	public EntityNotFoundException(Class<?> entityClass, Serializable entityId, Throwable cause) {
		this.message = "Instance of " + entityClass.getName() + " with primary key " + entityId + " cannot be found";
		this.entityId = entityId;
	}
	
	public Serializable getEntityId() {
		return entityId;
	}

	@Override
	public String toString() {
		return message;
	}
}
