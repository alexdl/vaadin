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
package com.vaadin.data.util;

import com.vaadin.data.Property;

/**
 * Property descriptor that is able to create nested property instances for a
 * bean.
 * 
 * The property is specified in the dotted notation, e.g. "address.street", and
 * can contain multiple levels of nesting.
 * 
 * @param <BT>
 *            bean type
 * 
 * @since 6.6
 */
public class NestedPropertyDescriptor<BT> implements
        VaadinPropertyDescriptor<BT> {

    private final String name;
    private final Class<?> propertyType;
    private final boolean nullBeansAllowed;

    /**
     * Creates a property descriptor that can create MethodProperty instances to
     * access the underlying bean property.
     * 
     * @param name
     *            of the property in a dotted path format, e.g. "address.street"
     * @param beanType
     *            type (class) of the top-level bean
     * @throws IllegalArgumentException
     *             if the property name is invalid
     */
    public NestedPropertyDescriptor(String name, Class<BT> beanType)
            throws IllegalArgumentException {
        this(name, beanType, false);
    }

    /**
     * Creates a property descriptor that can create MethodProperty instances to
     * access the underlying bean property.
     * 
     * @param name
     *            of the property in a dotted path format, e.g. "address.street"
     * @param beanType
     *            type (class) of the top-level bean
     * @param nullBeansAllowed
     *            set true to allow null values from intermediate getters
     * @throws IllegalArgumentException
     *             if the property name is invalid
     */
    public NestedPropertyDescriptor(String name, Class<BT> beanType,
            boolean nullBeansAllowed) throws IllegalArgumentException {
        this.name = name;
        NestedMethodProperty<?> property = new NestedMethodProperty<Object>(
                beanType, name, nullBeansAllowed);
        this.propertyType = property.getType();
        this.nullBeansAllowed = nullBeansAllowed;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<?> getPropertyType() {
        return propertyType;
    }

    @Override
    public Property<?> createProperty(BT bean) {
        return new NestedMethodProperty<Object>(bean, name, nullBeansAllowed);
    }

}
