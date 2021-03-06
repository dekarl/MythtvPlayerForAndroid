package org.mythtv.android.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import org.mythtv.android.R;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.presentation.internal.di.components.DaggerDvrComponent;
import org.mythtv.android.presentation.internal.di.components.DvrComponent;
import org.mythtv.android.presentation.model.TitleInfoModel;
import org.mythtv.android.presentation.view.fragment.AppTitleInfoListFragment;

/**
 * Activity that shows a list of programs.
 *
 * Created by dmfrey on 9/1/15.
 */
public class AppTitleInfoListActivity extends AppAbstractBaseActivity implements HasComponent<DvrComponent>, AppTitleInfoListFragment.TitleInfoListListener {

    private static final String TAG = AppTitleInfoListActivity.class.getSimpleName();

    public static Intent getCallingIntent( Context context ) {

        return new Intent( context, AppTitleInfoListActivity.class );
    }

    private DvrComponent dvrComponent;

    @Override
    public int getLayoutResource() {

        return R.layout.activity_app_title_info_list;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.d( TAG, "onCreate : enter" );

        requestWindowFeature( Window.FEATURE_INDETERMINATE_PROGRESS );
        super.onCreate( savedInstanceState );

        this.initializeInjector();

        Log.d( TAG, "onCreate : exit" );
    }

    @Override
    protected void onResume() {
        super.onResume();

        setNavigationMenuItemChecked( 1 );

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
    public void onTitleInfoClicked( TitleInfoModel titleInfoModel ) {
        Log.d( TAG, "onTitleInfoClicked : enter" );

        Log.d( TAG, "onTitleInfoClicked : titleInfoModel=" + titleInfoModel );
        navigator.navigateToPrograms( this, true, -1, -1, titleInfoModel.getTitle(), null, null );

        Log.d( TAG, "onTitleInfoClicked : exit" );
    }

}
