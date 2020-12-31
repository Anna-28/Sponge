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
package org.spongepowered.common.mixin.api.mcp.world.gen.settings;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.gen.settings.StructureSpreadSettings;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.registry.RegistryKey;
import org.spongepowered.api.registry.RegistryReference;
import org.spongepowered.api.registry.RegistryTypes;
import org.spongepowered.api.world.generation.Structure;
import org.spongepowered.api.world.generation.config.structure.SeparatedStructureConfig;
import org.spongepowered.api.world.generation.config.structure.SpacedStructureConfig;
import org.spongepowered.api.world.generation.config.structure.StructureGenerationConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.Nullable;

@Mixin(DimensionStructuresSettings.class)
@Implements(@Interface(iface = StructureGenerationConfig.class, prefix = "structureGenerationConfig$"))
public abstract class DimensionStructuresSettingsMixin_API implements StructureGenerationConfig {

    // @formatter:off
    @Shadow @Nullable public abstract StructureSpreadSettings shadow$stronghold();
    @Shadow public abstract Map<net.minecraft.world.gen.feature.structure.Structure<?>, StructureSeparationSettings> shadow$structureConfig();
    // @formatter:on

    @Shadow @Final private Map<net.minecraft.world.gen.feature.structure.Structure<?>, StructureSeparationSettings> structureConfig;

    @Intrinsic
    public Optional<SpacedStructureConfig> structureGenerationConfig$stronghold() {
        return Optional.ofNullable((SpacedStructureConfig) this.shadow$stronghold());
    }

    @Override
    public Optional<SeparatedStructureConfig> structure(final RegistryReference<Structure> structure) {
        return Optional.ofNullable((SeparatedStructureConfig) this.shadow$structureConfig().get(Objects.requireNonNull(structure, "structure").get(Sponge.getServer().registries())));
    }

    @Override
    public Map<RegistryReference<Structure>, SeparatedStructureConfig> structures() {
        final Map<RegistryReference<Structure>, SeparatedStructureConfig> structures = new Object2ObjectOpenHashMap<>();
        for (final Map.Entry<net.minecraft.world.gen.feature.structure.Structure<?>, StructureSeparationSettings> entry : this.structureConfig.entrySet()) {
            final net.minecraft.world.gen.feature.structure.Structure<?> structure = entry.getKey();
            final StructureSeparationSettings settings = entry.getValue();

            structures.put(RegistryKey.of(RegistryTypes.STRUCTURE, ResourceKey.minecraft(structure.getFeatureName())).asReference(),
                    (SeparatedStructureConfig) settings);
        }

        return structures;
    }
}
