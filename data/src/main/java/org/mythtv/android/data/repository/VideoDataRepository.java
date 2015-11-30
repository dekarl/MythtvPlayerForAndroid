package org.mythtv.android.data.repository;

import android.util.Log;

import org.mythtv.android.data.entity.mapper.VideoMetadataInfoEntityDataMapper;
import org.mythtv.android.data.repository.datasource.VideoDataStore;
import org.mythtv.android.data.repository.datasource.VideoDataStoreFactory;
import org.mythtv.android.domain.VideoMetadataInfo;
import org.mythtv.android.domain.repository.VideoRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by dmfrey on 11/3/15.
 */
@Singleton
public class VideoDataRepository implements VideoRepository {

    private static final String TAG = VideoDataRepository.class.getSimpleName();

    private final VideoDataStoreFactory videoDataStoreFactory;
    private final VideoMetadataInfoEntityDataMapper videoMetadataInfoEntityDataMapper;

    @Inject
    public VideoDataRepository( VideoDataStoreFactory videoDataStoreFactory, VideoMetadataInfoEntityDataMapper videoMetadataInfoEntityDataMapper ) {

        this.videoDataStoreFactory = videoDataStoreFactory;
        this.videoMetadataInfoEntityDataMapper = videoMetadataInfoEntityDataMapper;

    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<List<VideoMetadataInfo>> getVideoList( String folder, String sort, boolean descending, int startIndex, int count ) {
        Log.d( TAG, "getVideoList : enter" );
        Log.d( TAG, "getVideoList : folder=" + folder + ", sort=" + sort + ", descending=" + descending + ", startIndex=" + startIndex + ", count=" + count );

        final VideoDataStore videoDataStore = videoDataStoreFactory.createMasterBackendDataStore();

        return videoDataStore.getVideos(folder, sort, descending, startIndex, count)
                .map( videoMetadataInfoEntities -> this.videoMetadataInfoEntityDataMapper.transform( videoMetadataInfoEntities ) );
    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<List<VideoMetadataInfo>> getVideoListByContentType( final String contentType ) {
        Log.d( TAG, "getVideoListByContentType : enter" );
        Log.d( TAG, "getVideoListByContentType : contentType=" + contentType );

        final VideoDataStore videoDataStore = videoDataStoreFactory.createCategory( contentType );

        return videoDataStore.getCategory( contentType )
                .map( videoMetadataInfoEntities -> this.videoMetadataInfoEntityDataMapper.transform( videoMetadataInfoEntities ) );

    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<VideoMetadataInfo> getVideo( int id ) {

        final VideoDataStore videoDataStore = videoDataStoreFactory.create( id );

        return videoDataStore.getVideoById(id)
                .map( videoMetadataInfoEntity -> videoMetadataInfoEntityDataMapper.transform( videoMetadataInfoEntity ) );
    }

//    @SuppressWarnings( "Convert2MethodRef" )
//    @Override
//    public Observable<VideoMetadataInfo> getVideoByFileName( String fileName ) {
//        return null;
//    }

}