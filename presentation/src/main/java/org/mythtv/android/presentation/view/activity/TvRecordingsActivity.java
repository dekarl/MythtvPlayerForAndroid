package org.mythtv.android.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.mythtv.android.R;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.presentation.internal.di.components.DaggerDvrComponent;
import org.mythtv.android.presentation.internal.di.components.DvrComponent;
import org.mythtv.android.presentation.model.ProgramModel;
import org.mythtv.android.presentation.view.fragment.TvRecordingsFragment;

public class TvRecordingsActivity extends TvAbstractBaseActivity implements HasComponent<DvrComponent>, TvRecordingsFragment.ProgramListListener {

    private static final String TAG = TvRecordingsActivity.class.getSimpleName();

    public static Intent getCallingIntent( Context context ) {

        return new Intent( context, TvRecordingsActivity.class );
    }

    private DvrComponent dvrComponent;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_tv_recordings;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        this.initializeInjector();

    }

    private void initializeInjector() {
        Log.d( TAG, "initializeInjector : enter" );

        this.dvrComponent = DaggerDvrComponent.builder()
                .applicationComponent( getApplicationComponent() )
                .activityModule( getActivityModule() )
                .build();

        Log.d( TAG, "initializeInjector : exit" );
    }

    @Override
    public DvrComponent getComponent() {
        Log.d( TAG, "getComponent : enter" );

        Log.d( TAG, "getComponent : exit" );
        return dvrComponent;
    }

    @Override
    public void onProgramClicked( ProgramModel programModel ) {

    }

}
