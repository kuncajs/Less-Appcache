/*
 * Copyright 2013 Petr Kunc.
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
package cz.muni.fi.lessappcache.parser.modules;

/**
 * This exception should be thrown whenever a module encounters unrecoverable problem .
 *
 * @author Petr Kunc
 */
public class ModuleException extends Exception {

    /**
     * Empty constructor
     */
    public ModuleException() {
        super();
    }

    /**
     * Constructs exception with the message
     *
     * @param message detail message
     */
    public ModuleException(String message) {
        super(message);
    }

    /**
     * Constructs the exception with message and cause
     *
     * @param message detail message
     * @param cause original exception
     */
    public ModuleException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs the exception with cause of the exception
     *
     * @param cause original exception
     */
    public ModuleException(Throwable cause) {
        super(cause);
    }
}
