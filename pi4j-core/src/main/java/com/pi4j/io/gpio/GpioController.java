package com.pi4j.io.gpio;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: Java Library (Core)
 * FILENAME      :  GpioController.java  
 * 
 * This file is part of the Pi4J project. More information about 
 * this project can be found here:  http://www.pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 Pi4J
 * %%
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
 * #L%
 */


import java.util.Collection;
import com.pi4j.io.gpio.event.GpioListener;
import com.pi4j.io.gpio.trigger.GpioTrigger;

public interface GpioController
{
    boolean hasPin(Pin... pin);
    
    void export(PinMode mode, Pin... pin);
    void export(PinMode mode, GpioPin... pin);
    boolean isExported(Pin... pin);
    boolean isExported(GpioPin... pin);
    void unexport(Pin... pin);
    void unexport(GpioPin... pin);
    void unexportAll();

    void setMode(PinMode mode, Pin... pin);
    void setMode(PinMode mode, GpioPin... pin);
    PinMode getMode(Pin pin);
    PinMode getMode(GpioPin pin);    
    boolean isMode(PinMode mode, Pin... pin);
    boolean isMode(PinMode mode, GpioPin... pin);

    void setPullResistance(PinPullResistance resistance, Pin... pin);
    void setPullResistance(PinPullResistance resistance, GpioPin... pin);    
    PinPullResistance getPullResistance(Pin pin);
    PinPullResistance getPullResistance(GpioPin pin);
    boolean isPullResistance(PinPullResistance resistance, Pin... pin);
    boolean isPullResistance(PinPullResistance resistance, GpioPin... pin);

    void high(Pin... pin);
    void high(GpioPin... pin);
    boolean isHigh(Pin... pin);
    boolean isHigh(GpioPin... pin);
    void low(Pin... pin);
    void low(GpioPin... pin);
    boolean isLow(Pin... pin);
    boolean isLow(GpioPin... pin);
    
    void setState(PinState state, Pin... pin);
    void setState(PinState state, GpioPin... pin);
    void setState(boolean state, Pin... pin);
    void setState(boolean state, GpioPin... pin);
    boolean isState(PinState state, Pin... pin);
    boolean isState(PinState state, GpioPin... pin);
    PinState getState(Pin pin);
    PinState getState(GpioPin pin);
    
    void toggle(Pin... pin);
    void toggle(GpioPin... pin);
    void pulse(long milliseconds, Pin... pin);
    void pulse(long milliseconds, GpioPin... pin);
    
    void setValue(int value, Pin... pin);
    void setValue(int value, GpioPin... pin);
    int getValue(Pin pin);
    int getValue(GpioPin pin);

    void addListener(GpioListener listener, Pin... pin);
    void addListener(GpioListener[] listeners, Pin... pin);
    void addListener(GpioListener listener, GpioPin... pin);
    void addListener(GpioListener[] listeners, GpioPin... pin);
    void removeListener(GpioListener listener, Pin... pin);
    void removeListener(GpioListener[] listeners, Pin... pin);
    void removeListener(GpioListener listener, GpioPin... pin);
    void removeListener(GpioListener[] listeners, GpioPin... pin);
    void removeAllListeners();
    
    void addTrigger(GpioTrigger trigger, GpioPin... pin);
    void addTrigger(GpioTrigger[] triggers, GpioPin... pin);
    void removeTrigger(GpioTrigger trigger, GpioPin... pin);    
    void removeTrigger(GpioTrigger[] triggers, GpioPin... pin);
    void removeAllTriggers();
    
    GpioPin provisionDigitalInputPin(Pin pin, String name, PinPullResistance resistance);
    GpioPin provisionDigitalInputPin(Pin pin, String name);
    GpioPin provisionDigitalOuputPin(Pin pin, String name, PinState defaultState);
    GpioPin provisionDigitalOuputPin(Pin pin, String name);

    GpioPin provisionAnalogInputPin(Pin pin, String name);    
    GpioPin provisionAnalogOuputPin(Pin pin, String name, int defaultValue);
    GpioPin provisionAnalogOuputPin(Pin pin, String name);

    GpioPin provisionPwmOutputPin(Pin pin, String name, int defaultValue);
    GpioPin provisionPwmOutputPin(Pin pin, String name);
    
    GpioPin provisionPin(Pin pin, String name, PinMode mode);
    
    boolean isProvisioned(Pin... pin);
    GpioPin getProvisionedPin(Pin pin);
    Collection<GpioPin> getProvisionedPins(Pin... pin);
}
