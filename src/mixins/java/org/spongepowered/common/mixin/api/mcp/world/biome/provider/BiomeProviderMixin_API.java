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
package org.spongepowered.common.mixin.api.mcp.world.biome.provider;

import net.minecraft.world.biome.provider.BiomeProvider;
import org.spongepowered.api.world.biome.Biome;
import org.spongepowered.api.world.biome.BiomeProviderTemplate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.common.world.biome.SpongeBiomeProviderTemplate;

import java.util.List;
import java.util.Set;

@Mixin(BiomeProvider.class)
public abstract class BiomeProviderMixin_API implements org.spongepowered.api.world.biome.BiomeProvider {

    // @formatter:off
    @Shadow public abstract List<net.minecraft.world.biome.Biome> shadow$possibleBiomes();
    @Shadow public abstract Set<net.minecraft.world.biome.Biome> shadow$getBiomesWithin(int p_225530_1_, int p_225530_2_, int p_225530_3_, int p_225530_4_);
    // @formatter:on

    @Override
    public List<Biome> choices() {
        return (List<Biome>) (Object) this.shadow$possibleBiomes();
    }

    @Override
    public Set<Biome> within(final int x, final int y, final int z, final int size) {
        return (Set<Biome>) (Object) this.shadow$getBiomesWithin(x, y, z, size);
    }

    @Override
    public BiomeProviderTemplate asTemplate() {
        return new SpongeBiomeProviderTemplate((BiomeProvider) (Object) this);
    }
}
