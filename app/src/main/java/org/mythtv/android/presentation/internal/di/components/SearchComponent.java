/*
 * MythtvPlayerForAndroid. An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2016. Daniel Frey
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mythtv.android.presentation.internal.di.components;

import org.mythtv.android.presentation.internal.di.PerActivity;
import org.mythtv.android.presentation.internal.di.modules.ActivityModule;
import org.mythtv.android.presentation.internal.di.modules.NetModule;
import org.mythtv.android.presentation.internal.di.modules.SearchResultsModule;
import org.mythtv.android.presentation.internal.di.modules.SharedPreferencesModule;
import org.mythtv.android.presentation.view.fragment.phone.PhoneSearchResultListFragment;
import org.mythtv.android.presentation.view.fragment.tv.TvSearchResultListFragment;

import dagger.Component;

/**
 * Created by dmfrey on 10/12/15.
 */
@PerActivity
@Component( dependencies = ApplicationComponent.class, modules = { ActivityModule.class, SharedPreferencesModule.class, NetModule.class, SearchResultsModule.class } )
public interface SearchComponent {

    void inject( PhoneSearchResultListFragment phoneSearchResultListFragment);
    void inject( TvSearchResultListFragment tvSearchResultListFragment);

}