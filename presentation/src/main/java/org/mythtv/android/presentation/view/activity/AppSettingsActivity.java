package org.mythtv.android.presentation.view.activity;

import android.content.Context;
import android.content.Intent;

import org.mythtv.android.R;

/**
 * Created by dmfrey on 9/2/15.
 */
public class AppSettingsActivity extends AppAbstractBaseActivity {

    public static Intent getCallingIntent( Context context ) {

        return new Intent( context, AppSettingsActivity.class );
    }

    @Override
    public int getLayoutResource() {

        return R.layout.activity_app_settings;
    }

    @Override
    protected void onResume() {
        super.onResume();

        setNavigationMenuItemChecked( 3 );

    }

}
