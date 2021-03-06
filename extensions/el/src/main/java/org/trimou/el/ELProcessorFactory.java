/*
 * Copyright 2017 Martin Kouba
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.trimou.el;

import javax.el.ELProcessor;

import org.trimou.engine.config.Configuration;
import org.trimou.engine.config.ConfigurationKey;
import org.trimou.engine.config.SimpleConfigurationKey;

/**
 * This factory is used to instantiate a new {@link ELProcessor} when evaluating
 * EL expressions.
 *
 * @author Martin Kouba
 * @since 2.4
 */
public interface ELProcessorFactory {

    /**
     * The default factory.
     */
    static ELProcessorFactory DEFAULT_ELP_FACTORY = (c) -> new ELProcessor();

    /**
     * This configuration key could be used to specify a custom
     * {@link ELProcessorFactory}.
     */
    static ConfigurationKey EL_PROCESSOR_FACTORY_KEY = new SimpleConfigurationKey(ELProcessorFactory.class.getName(),
            DEFAULT_ELP_FACTORY, ELProcessorFactory::convert);

    /**
     *
     * @return a new EL processor instance
     */
    ELProcessor createELProcessor(Configuration configuration);

    /**
     * If the value is an instance of {@link ELProcessorFactory} it's returned. If
     * it's a string then attempt to instantiate of this name. Otherwise throw
     * {@link IllegalStateException}.
     *
     * @param value
     * @return the converted value
     */
    static Object convert(Object value) {
        if (value instanceof ELProcessorFactory) {
            return value;
        } else if (value instanceof String) {
            String clazz = value.toString();
            if (clazz.isEmpty()) {
                return null;
            }
            ClassLoader cl = SecurityActions.getContextClassLoader();
            if (cl == null) {
                cl = SecurityActions.getClassLoader(ELProcessorFactory.class);
            }
            try {
                return cl.loadClass(clazz).newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                throw new IllegalStateException("ELProcessorFactory implementation not found: " + value);
            }
        } else {
            throw new IllegalStateException("Unsupported value type: " + value);
        }
    }

}
