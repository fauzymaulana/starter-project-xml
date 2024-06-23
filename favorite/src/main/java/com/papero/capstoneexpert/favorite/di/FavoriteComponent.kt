package com.papero.capstoneexpert.favorite.di

import android.content.Context
import com.papero.capstoneexpert.di.FavoriteModuleDependencies
import com.papero.capstoneexpert.favorite.presentation.FavoriteFragment
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteComponent {

    fun inject(activity: FavoriteFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favModuleDependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteComponent
    }

}