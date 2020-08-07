/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.common.config.inheritable;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class MovementChecksCategory {

    @Setting(value = "player-moved-too-quickly", comment = "Controls whether the 'player moved too quickly!' check will be enforced")
    private boolean playerMovedTooQuickly = true;

    @Setting(value = "player-vehicle-moved-too-quickly", comment = "Controls whether the 'vehicle of player moved too quickly!' check will be enforced")
    private boolean playerVehicleMovedTooQuickly = true;

    @Setting(value = "moved-wrongly", comment = "Controls whether the 'player/entity moved wrongly!' check will be enforced")
    private boolean movedWrongly = true;

    public boolean playerMovedTooQuickly() {
        return this.playerMovedTooQuickly;
    }

    public boolean playerVehicleMovedTooQuickly() {
        return this.playerVehicleMovedTooQuickly;
    }

    public boolean movedWrongly() {
        return this.movedWrongly;
    }

}