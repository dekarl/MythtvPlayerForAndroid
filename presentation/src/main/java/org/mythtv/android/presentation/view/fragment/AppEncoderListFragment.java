package org.mythtv.android.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.mythtv.android.R;
import org.mythtv.android.presentation.internal.di.components.DvrComponent;
import org.mythtv.android.presentation.model.EncoderModel;
import org.mythtv.android.presentation.presenter.EncoderListPresenter;
import org.mythtv.android.presentation.view.EncoderListView;
import org.mythtv.android.presentation.view.adapter.EncodersAdapter;
import org.mythtv.android.presentation.view.adapter.EncodersLayoutManager;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dmfrey on 1/20/16.
 */
public class AppEncoderListFragment extends AppAbstractBaseFragment implements EncoderListView {

    private static final String TAG = AppEncoderListFragment.class.getSimpleName();

    @Inject
    EncoderListPresenter encoderListPresenter;

    @Bind( R.id.rv_encoders )
    RecyclerView rv_encoders;

    @Bind( R.id.rl_progress )
    RelativeLayout rl_progress;

    private EncodersAdapter encodersAdapter;

    public AppEncoderListFragment() {
        super();
    }

    public static AppEncoderListFragment newInstance() {

        return new AppEncoderListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.d( TAG, "onCreateView : enter" );

        View fragmentView = inflater.inflate( R.layout.fragment_app_encoder_list, container, false );
        ButterKnife.bind( this, fragmentView );
        setupUI();

        Log.d( TAG, "onCreateView : exit" );
        return fragmentView;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        Log.d( TAG, "onActivityCreated : enter" );
        super.onActivityCreated( savedInstanceState );

        this.initialize();
        this.loadEncoderList();

        Log.d( TAG, "onActivityCreated : exit" );
    }

    @Override
    public void onResume() {
        Log.d( TAG, "onResume : enter" );
        super.onResume();

        this.encoderListPresenter.resume();

        Log.d( TAG, "onResume : exit" );
    }

    @Override
    public void onPause() {
        Log.d( TAG, "onPause : enter" );
        super.onPause();

        this.encoderListPresenter.pause();

        Log.d( TAG, "onPause : exit" );
    }

    @Override
    public void onDestroy() {
        Log.d( TAG, "onDestroy : enter" );
        super.onDestroy();

        this.encoderListPresenter.destroy();

        Log.d( TAG, "onDestroy : exit" );
    }

    @Override
    public void onDestroyView() {
        Log.d( TAG, "onDestroyView : enter" );
        super.onDestroyView();

        ButterKnife.unbind( this );

        Log.d( TAG, "onDestroyView : exit" );
    }

    private void initialize() {
        Log.d( TAG, "initialize : enter" );

        this.getComponent( DvrComponent.class ).inject( this );
        this.encoderListPresenter.setView( this );
//        this.encoderListPresenter.initialize( contentType );

        Log.d( TAG, "initialize : exit" );
    }

    private void setupUI() {
        Log.d( TAG, "setupUI : enter" );

        this.rv_encoders.setLayoutManager( new EncodersLayoutManager( getActivity() ) );

        this.encodersAdapter = new EncodersAdapter( getActivity(), new ArrayList<EncoderModel>() );
//        this.encodersAdapter.setOnItemClickListener( onItemClickListener );
        this.rv_encoders.setAdapter( encodersAdapter );

        Log.d( TAG, "setupUI : exit" );
    }

    @Override
    public void showLoading() {
        Log.d( TAG, "showLoading : enter" );

        this.rl_progress.setVisibility( View.VISIBLE );
        this.getActivity().setProgressBarIndeterminateVisibility( true );

        Log.d( TAG, "showLoading : exit" );
    }

    @Override
    public void hideLoading() {
        Log.d( TAG, "hideLoading : enter" );

        this.rl_progress.setVisibility( View.GONE );
        this.getActivity().setProgressBarIndeterminateVisibility( false );

        Log.d( TAG, "hideLoading : exit" );
    }

    @Override
    public void showRetry() {
        Log.d( TAG, "showRetry : enter" );

        Log.d( TAG, "showRetry : exit" );
    }

    @Override
    public void hideRetry() {
        Log.d( TAG, "hideRetry : enter" );

        Log.d( TAG, "hideRetry : exit" );
    }

    public void reload() {

        loadEncoderList();

    }

    @Override
    public void renderEncoderList( Collection<EncoderModel> encoderModelCollection ) {
        Log.d( TAG, "renderEncoderInfoList : enter" );

        if( null != encoderModelCollection ) {
            Log.d( TAG, "renderEncoderList : encoderModelCollection is not null, encoderModelCollection=" + encoderModelCollection );

            this.encodersAdapter.setEncodersCollection( encoderModelCollection );

        }

        Log.d( TAG, "renderEncoderList : exit" );
   }

    @Override
    public void showError( String message ) {
        Log.d( TAG, "showError : enter" );

        this.showToastMessage( message, getResources().getString( R.string.retry ), new View.OnClickListener() {

            @Override
            public void onClick( View v ) {

                AppEncoderListFragment.this.loadEncoderList();

            }

        });

        Log.d( TAG, "showError : exit" );
    }

    @Override
    public Context getContext() {
        Log.d( TAG, "getContext : enter" );

        Log.d( TAG, "getContext : exit" );
        return this.getActivity().getApplicationContext();
    }

    /**
     * Loads all encoders.
     */
    private void loadEncoderList() {
        Log.d( TAG, "loadEncoderList : enter" );

        this.encoderListPresenter.initialize();

        Log.d( TAG, "loadEncoderList : exit" );
    }

}
