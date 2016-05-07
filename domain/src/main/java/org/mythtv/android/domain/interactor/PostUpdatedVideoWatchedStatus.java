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

package org.mythtv.android.domain.interactor;

import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.VideoRepository;

import java.util.Map;

import rx.Observable;

/**
 * Created by dmfrey on 4/9/16.
 */
public class PostUpdatedVideoWatchedStatus extends DynamicUseCase {

    private final VideoRepository videoRepository;

    public PostUpdatedVideoWatchedStatus( final VideoRepository videoRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {
        super( threadExecutor, postExecutionThread );

        this.videoRepository = videoRepository;

    }

    @Override
    protected Observable buildUseCaseObservable( Map parameters ) {

        final int videoId = (Integer) parameters.get( "VIDEO_ID" );
        final boolean watched = (Boolean) parameters.get( "WATCHED" );

        return this.videoRepository.updateWatchedStatus( videoId, watched );
    }

}
