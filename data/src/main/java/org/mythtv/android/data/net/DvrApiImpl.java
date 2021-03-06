package org.mythtv.android.data.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.mythtv.android.data.entity.EncoderEntity;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.TitleInfoEntity;
import org.mythtv.android.data.entity.mapper.EncoderEntityJsonMapper;
import org.mythtv.android.data.entity.mapper.ProgramEntityJsonMapper;
import org.mythtv.android.data.entity.mapper.TitleInfoEntityJsonMapper;
import org.mythtv.android.data.exception.NetworkConnectionException;
import org.mythtv.android.domain.SettingsKeys;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by dmfrey on 8/27/15.
 */
public class DvrApiImpl implements DvrApi {

    private static final String TAG = DvrApiImpl.class.getSimpleName();

    private static final DateTimeFormatter fmt = DateTimeFormat.forPattern( "yyyy-MM-dd'T'HH:mm:ss'Z'" );

    private final Context context;
    private final TitleInfoEntityJsonMapper titleInfoEntityJsonMapper;
    private final ProgramEntityJsonMapper programEntityJsonMapper;
    private final EncoderEntityJsonMapper encoderEntityJsonMapper;

    public DvrApiImpl( Context context, TitleInfoEntityJsonMapper titleInfoEntityJsonMapper, ProgramEntityJsonMapper programEntityJsonMapper, EncoderEntityJsonMapper encoderEntityJsonMapper ) {

        if( null == context || null == titleInfoEntityJsonMapper || null == programEntityJsonMapper || null == encoderEntityJsonMapper ) {

            throw new IllegalArgumentException( "The constructor parameters cannot be null!!!" );
        }

        this.context = context.getApplicationContext();
        this.titleInfoEntityJsonMapper = titleInfoEntityJsonMapper;
        this.programEntityJsonMapper = programEntityJsonMapper;
        this.encoderEntityJsonMapper = encoderEntityJsonMapper;

    }

    @Override
    public Observable<List<TitleInfoEntity>> titleInfoEntityList() {

        return Observable.create( new Observable.OnSubscribe<List<TitleInfoEntity>>() {

            @Override
            public void call( Subscriber<? super List<TitleInfoEntity>> subscriber ) {
                Log.d(TAG, "titleInfoEntityList.call : enter");

                if( isThereInternetConnection() ) {
                    Log.d(TAG, "titleInfoEntityList.call : network is connected");

                    try {

                        String responseRecordedProgramEntities = getTitleInfoEntitiesFromApi();
                        if( null != responseRecordedProgramEntities ) {
                            Log.d(TAG, "titleInfoEntityList.call : retrieved title info entities");

                            subscriber.onNext( titleInfoEntityJsonMapper.transformTitleInfoEntityCollection( responseRecordedProgramEntities ) );
                            subscriber.onCompleted();

                        } else {
                            Log.d(TAG, "titleInfoEntityList.call : failed to retrieve title info entities");

                            subscriber.onError( new NetworkConnectionException() );

                        }

                    } catch( Exception e ) {
                        Log.e( TAG, "titleInfoEntityList.call : error", e );

                        subscriber.onError( new NetworkConnectionException( e.getCause() ) );

                    }

                } else {
                    Log.d(TAG, "titleInfoEntityList.call : network is not connected");

                    subscriber.onError( new NetworkConnectionException() );

                }

                Log.d(TAG, "titleInfoEntityList.call : exit");
            }

        });

    }

    @Override
    public Observable<List<ProgramEntity>> recordedProgramEntityList( final boolean descending, final int startIndex, final int count, final String titleRegEx, final String recGroup, final String storageGroup ) {
        Log.d( TAG, "recordedProgramEntityList : enter" );

        Log.d( TAG, "recordedProgramEntityList : exit" );
        return Observable.create( new Observable.OnSubscribe<List<ProgramEntity>>() {

            @Override
            public void call( Subscriber<? super List<ProgramEntity>> subscriber ) {
                Log.d( TAG, "recordedProgramEntityList.call : enter" );

                if( isThereInternetConnection() ) {
                    Log.d( TAG, "recordedProgramEntityList.call : network is connected" );

                    try {

                        String responseRecordedProgramEntities = getRecordedProgramEntitiesFromApi( descending, startIndex, count, titleRegEx, recGroup, storageGroup );
                        if( null != responseRecordedProgramEntities ) {
                            Log.d( TAG, "recordedProgramEntityList.call : retrieved program entities" );

                            subscriber.onNext( programEntityJsonMapper.transformProgramEntityCollection( responseRecordedProgramEntities ) );
                            subscriber.onCompleted();

                        } else {
                            Log.d( TAG, "recordedProgramEntityList.call : failed to retrieve program entities" );

                            subscriber.onError( new NetworkConnectionException() );

                        }

                    } catch( Exception e ) {
                        Log.e( TAG, "recordedProgramEntityList.call : error", e );

                        subscriber.onError( new NetworkConnectionException( e.getCause() ) );

                    }

                } else {
                    Log.d( TAG, "recordedProgramEntityList.call : network is not connected" );

                    subscriber.onError( new NetworkConnectionException() );

                }

                Log.d( TAG, "recordedProgramEntityList.call : exit" );
            }

        });

    }

    @Override
    public Observable<ProgramEntity> recordedProgramById( int chanId, DateTime startTime ) {

        return Observable.create( new Observable.OnSubscribe<ProgramEntity>() {

            @Override
            public void call( Subscriber<? super ProgramEntity> subscriber ) {

                if( isThereInternetConnection() ) {

                    try {

                        String responseProgramDetails = getRecordedProgramDetailsFromApi( chanId, startTime );
                        if( null != responseProgramDetails ) {

                            subscriber.onNext( programEntityJsonMapper.transformProgramEntity( responseProgramDetails ) );
                            subscriber.onCompleted();

                        } else {

                            subscriber.onError( new NetworkConnectionException() );

                        }

                    } catch( Exception e ) {

                        subscriber.onError( new NetworkConnectionException( e.getCause() ) );

                    }

                } else {

                    subscriber.onError( new NetworkConnectionException() );

                }

            }

        });

    }

    @Override
    public Observable<List<ProgramEntity>> upcomingProgramEntityList(int startIndex, int count, boolean showAll, int recordId, int recStatus) {
        Log.d( TAG, "upcomingProgramEntityList : enter" );

        Log.d( TAG, "upcomingProgramEntityList : exit" );
        return Observable.create( new Observable.OnSubscribe<List<ProgramEntity>>() {

            @Override
            public void call( Subscriber<? super List<ProgramEntity>> subscriber ) {
                Log.d( TAG, "upcomingProgramEntityList.call : enter" );

                if( isThereInternetConnection() ) {
                    Log.d( TAG, "upcomingProgramEntityList.call : network is connected" );

                    try {

                        String responseUpcomingProgramEntities = getUpcomingProgramEntitiesFromApi( startIndex, count, showAll, recordId, recStatus );
                        if( null != responseUpcomingProgramEntities ) {
                            Log.d( TAG, "upcomingEntityList.call : retrieved program entities" );

                            subscriber.onNext( programEntityJsonMapper.transformProgramEntityCollection( responseUpcomingProgramEntities ) );
                            subscriber.onCompleted();

                        } else {
                            Log.d( TAG, "upcomingProgramEntityList.call : failed to retrieve program entities" );

                            subscriber.onError( new NetworkConnectionException() );

                        }

                    } catch( Exception e ) {
                        Log.e( TAG, "upcomingProgramEntityList.call : error", e );

                        subscriber.onError( new NetworkConnectionException( e.getCause() ) );

                    }

                } else {
                    Log.d( TAG, "upcomingProgramEntityList.call : network is not connected" );

                    subscriber.onError( new NetworkConnectionException() );

                }

                Log.d( TAG, "upcomingProgramEntityList.call : exit" );
            }

        });

    }

    @Override
    public Observable<List<EncoderEntity>> encoderEntityList() {

        return Observable.create( new Observable.OnSubscribe<List<EncoderEntity>>() {

            @Override
            public void call( Subscriber<? super List<EncoderEntity>> subscriber ) {
                Log.d( TAG, "encoderEntityList.call : enter" );

                if( isThereInternetConnection() ) {
                    Log.d(TAG, "encoderEntityList.call : network is connected");

                    try {

                        String responseEncoderEntities = getEncoderEntitiesFromApi();
                        if( null != responseEncoderEntities ) {
                            Log.d(TAG, "encoderEntityList.call : retrieved encoder entities");

                            subscriber.onNext( encoderEntityJsonMapper.transformEncoderEntityCollection( responseEncoderEntities ) );
                            subscriber.onCompleted();

                        } else {
                            Log.d(TAG, "encoderEntityList.call : failed to retrieve encoder entities");

                            subscriber.onError( new NetworkConnectionException() );

                        }

                    } catch( Exception e ) {
                        Log.e( TAG, "encoderEntityList.call : error", e );

                        subscriber.onError( new NetworkConnectionException( e.getCause() ) );

                    }

                } else {
                    Log.d( TAG, "encoderEntityList.call : network is not connected" );

                    subscriber.onError( new NetworkConnectionException() );

                }

                Log.d( TAG, "encoderEntityList.call : exit" );
            }

        });

    }

    private String getTitleInfoEntitiesFromApi() throws MalformedURLException {

        return ApiConnection.createGET( getMasterBackendUrl() + TITLE_INFO_LIST_URL ).requestSyncCall();
    }

    private String getRecordedProgramEntitiesFromApi( final boolean descending, final int startIndex, final int count, final String titleRegEx, final String recGroup, final String storageGroup ) throws MalformedURLException {

        StringBuilder sb = new StringBuilder();
        sb.append( getMasterBackendUrl() );
        sb.append( DvrApi.RECORDED_LIST_BASE_URL );
        sb.append( "?" );
        sb.append( String.format( DESCENDING_QS, descending ) );

        if( startIndex != -1 ) {

            sb.append( "&" );
            sb.append( String.format( START_INDEX_QS, startIndex ) );

        }

        if( count != -1 ) {

            sb.append( "&" );
            sb.append( String.format( COUNT_QS, startIndex ) );

        }

        if( null != titleRegEx && !"".equals( titleRegEx ) ) {

            String  dottedTitleRegex = titleRegEx.replaceAll( "[\\$\\(\\)\\*\\+\\?\\[\\]\\^\\{\\|\\}]", "."  );

            try {

                sb.append( "&" );
                sb.append( String.format( TITLE_REG_EX_QS, URLEncoder.encode( dottedTitleRegex, "UTF-8" ) ) );

            } catch( UnsupportedEncodingException e ) {
                Log.e( TAG, "getRecordedProgramEntitiesFromApi : error", e );
            }
        }

        if( null != recGroup && !"".equals( recGroup ) ) {

            sb.append( "&" );
            sb.append( String.format( REC_GROUP_QS, recGroup ) );

        }

        if( null != storageGroup && !"".equals( storageGroup ) ) {

            sb.append( "&" );
            sb.append( String.format( STORAGE_GROUP_QS, storageGroup ) );

        }

        Log.i( TAG, "getRecordedProgramEntitiesFromApi : url=" + sb.toString() );
        return ApiConnection.createGET( sb.toString() ).requestSyncCall();
    }

    private String getRecordedProgramDetailsFromApi(int chanId, DateTime startTime ) throws MalformedURLException {

        String apiUrl = String.format( DvrApi.RECORDED_BASE_URL, chanId, fmt.print( startTime.withZone( DateTimeZone.UTC ) ) );

        Log.i( TAG, "getRecordedProgramDetailsFromApi : apiUrl=" + ( getMasterBackendUrl() + apiUrl ) );
        return ApiConnection.createGET( getMasterBackendUrl() + apiUrl ).requestSyncCall();
    }

    private String getUpcomingProgramEntitiesFromApi( final int startIndex, final int count, final boolean showAll, final int recordId, final int recStatus ) throws MalformedURLException {

        StringBuilder sb = new StringBuilder();
        sb.append( getMasterBackendUrl() );
        sb.append( DvrApi.UPCOMING_LIST_BASE_URL );
        sb.append( "?" );
        sb.append( String.format( SHOW_ALL_QS, showAll ) );

        if( startIndex != -1 ) {

            sb.append( "&" );
            sb.append( String.format( START_INDEX_QS, startIndex ) );

        }

        if( count != -1 ) {

            sb.append( "&" );
            sb.append( String.format( COUNT_QS, startIndex ) );

        }

        if( recordId != -1 ) {

            sb.append( "&" );
            sb.append( String.format( RECORD_ID_QS, recordId ) );

        }

        if( recStatus != -1 ) {

            sb.append( "&" );
            sb.append( String.format( REC_STATUS_QS, recStatus ) );

        }

        Log.i( TAG, "getUpcomingProgramEntitiesFromApi : url=" + sb.toString() );
        return ApiConnection.createGET( sb.toString() ).requestSyncCall();
    }

    private String getEncoderEntitiesFromApi() throws MalformedURLException {

        Log.i( TAG, "getEncoderEntitiesFromApi : url=" + ( getMasterBackendUrl() + ENCODER_LIST_BASE_URL ) );
        return ApiConnection.createGET( getMasterBackendUrl() + ENCODER_LIST_BASE_URL ).requestSyncCall();
    }

    private boolean isThereInternetConnection() {

        boolean isConnected;

        ConnectivityManager connectivityManager = (ConnectivityManager) this.context.getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = ( networkInfo != null && networkInfo.isConnectedOrConnecting() );

        return isConnected;
    }

    private String getMasterBackendUrl() {

        String host = getFromPreferences( this.context, SettingsKeys.KEY_PREF_BACKEND_URL );
        String port = getFromPreferences( this.context, SettingsKeys.KEY_PREF_BACKEND_PORT );

        String masterBackend = "http://" + host + ":" + port;
        Log.d(TAG, "getMasterBackendUrl : masterBackend=" + masterBackend);

        return masterBackend;
    }

    public String getFromPreferences( Context context, String key ) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( context );

        return sharedPreferences.getString( key, "" );
    }

}
