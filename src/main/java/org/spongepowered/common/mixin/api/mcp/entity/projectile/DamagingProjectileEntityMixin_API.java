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
package org.spongepowered.common.mixin.api.mcp.entity.projectile;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.projectile.DamagingProjectile;
import org.spongepowered.api.projectile.source.ProjectileSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.common.data.manipulator.mutable.entity.SpongeAccelerationData;
import org.spongepowered.common.data.value.mutable.SpongeValue;
import org.spongepowered.common.mixin.api.mcp.entity.EntityMixin_API;
import org.spongepowered.math.vector.Vector3d;
import java.util.Collection;

import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;

@Mixin(DamagingProjectileEntity.class)
public abstract class DamagingProjectileEntityMixin_API extends EntityMixin_API implements DamagingProjectile {

    @Shadow @Nullable public LivingEntity shootingEntity;

    @Shadow public double accelerationX;
    @Shadow public double accelerationY;
    @Shadow public double accelerationZ;
    @Nullable private ProjectileSource projectileSource = null;

    @Override
    public Mutable<Vector3d> acceleration() {
        return new SpongeValue<>(Keys.ACCELERATION, new Vector3d(this.accelerationX, this.accelerationY, this.accelerationZ));
    }

    @Override
    public ProjectileSource getShooter() {
        if (this.shootingEntity instanceof ProjectileSource) {
            return (ProjectileSource) this.shootingEntity;
        }

        if (this.projectileSource != null) {
            return this.projectileSource;
        }

        return ProjectileSource.UNKNOWN;
    }

    @Override
    public void setShooter(final ProjectileSource shooter) {
        this.projectileSource = shooter;
        if (shooter instanceof LivingEntity) {
            this.shootingEntity = (LivingEntity) shooter;
        } else {
            this.shootingEntity = null;
        }
    }

    @Override
    protected void spongeApi$supplyVanillaManipulators(final Collection<? super org.spongepowered.api.data.DataManipulator.Mutable<?, ?>> manipulators) {
        super.spongeApi$supplyVanillaManipulators(manipulators);
        manipulators.add(new SpongeAccelerationData(this.accelerationX, this.accelerationY, this.accelerationZ));
    }

}