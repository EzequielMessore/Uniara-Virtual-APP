package br.com.uniaravirtual.app.controller.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import br.com.uniaravirtual.R;
import br.com.uniaravirtual.model.entity.Student;
import br.com.uniaravirtual.model.enums.SharedPreferencesValues;
import br.com.uniaravirtual.model.service.UniaraClient;
import br.com.uniaravirtual.model.service.UniaraServiceGenerator;
import br.com.uniaravirtual.util.AppUtil;
import br.com.uniaravirtual.util.StringsUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        if (!StringsUtils.isEmpty(SharedPreferencesValues.TOKEN.getString())) {
            UniaraServiceGenerator.createService(UniaraClient.class).getStudent(new Callback
                    <Student>() {

                @Override
                public void success(Student student, Response response) {
                    SharedPreferencesValues.USER_NAME.putString(student.getName());
                    initTimer(MainActivity.class, 0);
                }

                @Override
                public void failure(RetrofitError error) {
                    initTimer(LoginActivity.class, 0);
                }
            });
        } else {
            initTimer(LoginActivity.class, 1000);
        }
    }

    private synchronized void initTimer(final Class<? extends Activity> activity, final long
            timer) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                AppUtil.startActivityAndFinishPrevious(SplashActivity.this, activity);
            }
        }, timer);
    }
}
