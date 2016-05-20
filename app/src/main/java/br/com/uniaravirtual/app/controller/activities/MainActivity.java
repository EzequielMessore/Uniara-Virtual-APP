package br.com.uniaravirtual.app.controller.activities;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import br.com.uniaravirtual.R;
import br.com.uniaravirtual.app.controller.fragments.FilesFragment;
import br.com.uniaravirtual.model.enums.SharedPreferencesValues;
import br.com.uniaravirtual.util.DrawerMenuUtil;
import br.com.uniaravirtual.util.StringsUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureDrawerMenu();

        FragmentTransaction transaction = this.getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new FilesFragment()).commit();
    }

    private void configureDrawerMenu() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerMenuUtil.addProfile(StringsUtils.getStringFromResource(this, R.string.app_name),
                SharedPreferencesValues.USER_NAME.getString(), null);
        DrawerMenuUtil.create(this, toolbar).build();
    }

}
