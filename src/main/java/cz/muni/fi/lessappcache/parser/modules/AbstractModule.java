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
 * Abstract Module - any current module class inherits from it, defines equals and hashCode of modules
 *
 * @author Petr Kunc
 */
public abstract class AbstractModule implements Module {

    private double priority;

    @Override
    public double getPriority() {
        return priority;
    }

    @Override
    public void setPriority(double priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Module) && (this.getPriority() == ((Module)obj).getPriority());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + (int) (Double.doubleToLongBits(this.getPriority()) ^ (Double.doubleToLongBits(this.getPriority()) >>> 32));
        return hash;
    }
}
