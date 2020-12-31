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
package org.spongepowered.common.mixin.api.mcp.world.gen;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.FlatLayerInfo;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.registry.RegistryKey;
import org.spongepowered.api.registry.RegistryReference;
import org.spongepowered.api.registry.RegistryTypes;
import org.spongepowered.api.world.biome.Biome;
import org.spongepowered.api.world.generation.config.FlatGeneratorConfig;
import org.spongepowered.api.world.generation.config.flat.LayerConfig;
import org.spongepowered.api.world.generation.config.structure.StructureGenerationConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.common.server.BootstrapProperties;

import java.util.List;
import java.util.Optional;

@Mixin(FlatGenerationSettings.class)
public abstract class FlatGenerationSettingsMixin_API implements FlatGeneratorConfig {

    // @formatter:off
    @Shadow private boolean decoration;
    @Shadow private boolean addLakes;

    @Shadow public abstract net.minecraft.world.biome.Biome getBiome();
    @Shadow public abstract DimensionStructuresSettings shadow$structureSettings();
    @Shadow public abstract List<FlatLayerInfo> shadow$getLayersInfo();
    // @formatter:on

    @Override
    public StructureGenerationConfig structureConfig() {
        return (StructureGenerationConfig) this.shadow$structureSettings();
    }

    @Override
    public RegistryReference<Biome> biome() {
        return RegistryKey.of(RegistryTypes.BIOME, (ResourceKey) (Object) BootstrapProperties.registries.registryOrThrow(Registry.BIOME_REGISTRY).getKey(this.getBiome())).asReference();
    }

    @Override
    public List<LayerConfig> layers() {
        return (List<LayerConfig>) (Object) this.shadow$getLayersInfo();
    }

    @Override
    public Optional<LayerConfig> layer(final int index) {
        return Optional.ofNullable((LayerConfig) this.shadow$getLayersInfo().get(index));
    }

    @Override
    public boolean performDecoration() {
        return this.decoration;
    }

    @Override
    public boolean populateLakes() {
        return this.addLakes;
    }
}
