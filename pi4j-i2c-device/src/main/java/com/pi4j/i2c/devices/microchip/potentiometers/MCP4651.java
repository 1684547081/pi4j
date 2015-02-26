package com.pi4j.i2c.devices.microchip.potentiometers;

import java.io.IOException;

import com.pi4j.i2c.devices.microchip.potentiometers.impl.Channel;
import com.pi4j.i2c.devices.microchip.potentiometers.impl.DeviceStatus;
import com.pi4j.i2c.devices.microchip.potentiometers.impl.PotentiometerImpl;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: I2C Device Abstractions
 * FILENAME      :  MCP4651.java  
 * 
 * This file is part of the Pi4J project. More information about 
 * this project can be found here:  http://www.pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2013 Pi4J
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

/**
 * Pi4J-device for MCP4651.
 * 
 * @author <a href="http://raspelikan.blogspot.co.at">Raspelikan</a>
 */
public class MCP4651 extends PotentiometerImpl {

	private static final Channel[] supportedChannels = new Channel[] {
		Channel.A, Channel.B
	};
	
	/**
	 * Builds an instance which is ready to use.
	 * 
	 * @param i2cBus The Pi4J-I2CBus to which the device is connected to
	 * @param pinA0 Whether the device's address pin A0 is high (true) or low (false)
	 * @param pinA1 Whether the device's address pin A1 (if available) is high (true) or low (false)
	 * @param pinA2 Whether the device's address pin A2 (if available) is high (true) or low (false)
	 * @param channel Which of the potentiometers provided by the device to control
	 * @param initialValue Initial value of wiper
	 * @throws IOException Thrown if communication fails or device returned a malformed result
	 */
	public MCP4651(final I2CBus i2cBus, final boolean pinA0,
			final boolean pinA1, final boolean pinA2,
			final Channel channel, final int initialValue)  throws IOException {
		
		super(i2cBus, pinA0, pinA1, pinA2,
				channel, NonVolatileMode.VOLATILE_ONLY, initialValue);
		
	}

	/**
	 * @return Whether device is capable of non volatile wipers (false for MCP4651)
	 */
	@Override
	public boolean isCapableOfNonVolatileWiper() {
		
		return false;
		
	}
	
	/**
	 * @return The maximal value at which a wiper can be (256 for MCP4651)
	 */
	@Override
	public int getMaxValue() {
		
		return MCP4651.maxValue();
		
	}

	/**
	 * @return The maximal value at which a wiper can be (256 for MCP4651)
	 */
	public static int maxValue() {
		
		return 256;
		
	}

	/**
	 * @return Whether this device is a potentiometer or a rheostat (false for MCP4651)
	 */
	@Override
	public boolean isRheostat() {
		
		return false;
		
	}
	
	/**
	 * @return All channels supported by the underlying device (A, B for MCP4651)
	 */
	@Override
	public Channel[] getSupportedChannelsByDevice() {
		
		return supportedChannels;
		
	}
	
	public static void main(String[] args) throws IOException {
		
		// initialize bus
		final I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);
		try {
			
			final MCP4651 a = new MCP4651(
					bus, false, false, false, Channel.A, 0);
			final MCP4651 b = new MCP4651(
					bus, false, false, false, Channel.B, MCP4651.maxValue());
			
			// Check device-status
			final DeviceStatus aStatus = a.getDeviceStatus();
			System.out.println("WiperLock for A active: " + aStatus.isWiperLockActive());
			final DeviceStatus bStatus = b.getDeviceStatus();
			System.out.println("WiperLock for B active: " + bStatus.isWiperLockActive());
			
			// for about 5 seconds
			
			for (int i = 0; i < MCP4651.maxValue(); ++i) {
				
				// increase a
				a.increase();
				
				// decrease b
				b.decrease();
				
				// wait a little bit
				try {
					Thread.sleep(19); // assume 1 ms for I2C-communication
				} catch (InterruptedException e) {
					// never mind
				}
				
			}
			
			// print current values
			System.out.println("A: " + a.getCurrentValue()
					+ "/" + a.updateCacheFromDevice());
			System.out.println("B: " + b.getCurrentValue()
					+ "/" + b.updateCacheFromDevice());
			
			// for a minute (59 seconds at 26 steps and one second at 12 steps)
			boolean aDirectionUp = false;
			boolean bDirectionUp = true;
			final int counter = 59 * 26 + 12;
			for (int i = 0; i < counter; ++i) {
				
				// change wipers
				if (aDirectionUp) {
					a.increase(10);
				} else {
					a.decrease(10);
				}
				if (bDirectionUp) {
					b.increase(10);
				} else {
					b.decrease(10);
				}
				
				// reverse direction
				if ((aDirectionUp && (a.getCurrentValue() == a.getMaxValue()))
						|| (!aDirectionUp && (a.getCurrentValue() == 0))) {
					aDirectionUp = !aDirectionUp;
				}
				if ((bDirectionUp && (b.getCurrentValue() == b.getMaxValue()))
						|| (!aDirectionUp && (b.getCurrentValue() == 0))) {
					bDirectionUp = !bDirectionUp;
				}

				// wait a little bit
				try {
					Thread.sleep(39); // assume 1 ms for I2C-communication
				} catch (InterruptedException e) {
					// never mind
				}
				
			}
			
			// print current values
			System.out.println("A: " + a.getCurrentValue()
					+ "/" + a.updateCacheFromDevice());
			System.out.println("B: " + b.getCurrentValue()
					+ "/" + b.updateCacheFromDevice());
			
		} finally {
			
			try {
				bus.close();
			} catch (Throwable e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
}
