package br.com.uniaravirtual.util;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import br.com.uniaravirtual.R;
import br.com.uniaravirtual.app.controller.activities.LoginActivity;
import br.com.uniaravirtual.app.controller.fragments.AbsencesFragment;
import br.com.uniaravirtual.app.controller.fragments.FilesFragment;
import br.com.uniaravirtual.app.controller.fragments.GradesFragment;
import br.com.uniaravirtual.model.enums.SharedPreferencesValues;
import br.com.uniaravirtual.model.persistence.absence.AbsenceProvider;
import br.com.uniaravirtual.model.persistence.files.StudentFileProvider;
import br.com.uniaravirtual.model.persistence.grades.GradesProvider;


public final class DrawerMenuUtil {

    private static ProfileDrawerItem mProfileDrawer;

    private static AccountHeader buildHeader(Object mContext) {
        return new AccountHeaderBuilder()
                .withActivity((Activity) mContext)
                .withHeaderBackground(R.drawable.background_drawer)
                .withTranslucentStatusBar(false)
                .addProfiles(mProfileDrawer)
                .build();
    }

    public static DrawerBuilder create(final Context context, final Toolbar mToolbar) {
        IDrawerItem[] drawerItems = new IDrawerItem[]{
                new PrimaryDrawerItem().withName(
                        StringsUtils.getStringFromResource(context, R.string.txt_available_files))
                        .withIdentifier(1),
                new PrimaryDrawerItem().withName(StringsUtils.getStringFromResource(context, R
                        .string.txt_absences))
                        .withIdentifier(2),
                new PrimaryDrawerItem().withName(
                        StringsUtils.getStringFromResource(context, R.string.txt_grades))
                        .withIdentifier(3),
                new PrimaryDrawerItem().withName(StringsUtils.getStringFromResource(context, R
                        .string.txt_exit))
                        .withIdentifier(4)
        };

        return new DrawerBuilder()
                .withToolbar(mToolbar)
                .withActivity((Activity) context)
                .withAccountHeader(buildHeader(context))
                .withDisplayBelowToolbar(true)
                .withTranslucentStatusBar(true)
                .withActionBarDrawerToggle(true)
                .addDrawerItems(drawerItems)
                .withActionBarDrawerToggleAnimated(true)
                .withFullscreen(true)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(AdapterView<?> parent, View view, int position,
                                               long id, IDrawerItem drawerItem) {
                        Activity activity = (Activity) context;
                        FragmentTransaction transaction = activity.getFragmentManager
                                ().beginTransaction();
                        switch (drawerItem.getIdentifier()) {
                            case 1:
                                transaction.replace(R.id.container, new FilesFragment());
                                break;
                            case 2:
                                transaction.replace(R.id.container, new AbsencesFragment());
                                break;
                            case 3:
                                transaction.replace(R.id.container, new GradesFragment());
                                break;
                            case 4:
                                SharedPreferencesValues.removeAll();
                                AppUtil.startActivityAndFinishPrevious(context, LoginActivity.class);
                                AbsenceProvider.getInstance().deleteAll();
                                GradesProvider.getInstance().deleteAll();
                                StudentFileProvider.getInstance().deleteAll();
                                break;
                            default:
                                break;
                        }
                        transaction.commit();
                        return false;
                    }

                });
    }

    public static void addProfile(String name, String email, Drawable icon) {
        mProfileDrawer = new ProfileDrawerItem();
        mProfileDrawer.withName(name);
        mProfileDrawer.withEmail(email);
        mProfileDrawer.withIcon(icon);
    }

}