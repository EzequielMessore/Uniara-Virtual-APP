package br.com.uniaravirtual.app.controller.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Arrays;
import java.util.List;

import br.com.uniaravirtual.R;
import br.com.uniaravirtual.model.entity.Student;
import br.com.uniaravirtual.model.enums.SharedPreferencesValues;
import br.com.uniaravirtual.model.service.UniaraClient;
import br.com.uniaravirtual.model.service.UniaraServiceGenerator;
import br.com.uniaravirtual.task.GetTokenTask;
import br.com.uniaravirtual.task.OnResultListener;
import br.com.uniaravirtual.util.AppUtil;
import br.com.uniaravirtual.util.StringsUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText mPassword;
    private EditText mStudentRecord;
    private Button mButtonSingIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindElements();

    }

    private void bindElements() {
        mStudentRecord = AppUtil.get(findViewById(R.id.etStudentRecord));
        mPassword = AppUtil.get(findViewById(R.id.etPassword));
        mButtonSingIn = AppUtil.get(findViewById(R.id.btnSingIn));
        bindEvents();
        bindPassword(mPassword);

    }

    private void bindPassword(final EditText editText) {
        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_visibility_on, 0);

        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        if (!(editText.getTransformationMethod() instanceof PasswordTransformationMethod)) {
                            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_visibility_on, 0);
                            editText.setTransformationMethod(new PasswordTransformationMethod());
                        } else {
                            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_visibility_off, 0);
                            editText.setTransformationMethod(null);
                        }
                        return false;
                    }
                }
                return false;
            }
        });
    }

    private void bindEvents() {
        mButtonSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student mStudent = new Student();
                List<EditText> fields = Arrays.asList(mStudentRecord, mPassword);
                boolean validForm = AppUtil.validForm(fields, LoginActivity.this);
                if (validForm) {
                    mStudent.setRegisterStudent(mStudentRecord.getText().toString());
                    mStudent.setPassword(mPassword.getText().toString());
                    new GetTokenTask(LoginActivity.this, new OnResultListener<String>() {
                        @Override
                        public void onSuccess(String result) {
                            if (result != null && !StringsUtils.isEmpty(result)) {
                                SharedPreferencesValues.TOKEN.putString(result);
                                getStudent();
                            }
                        }
                    }).execute(mStudent);

                }
            }
        });
    }

    private void getStudent() {
        UniaraServiceGenerator.
                createService(UniaraClient.class).getStudent(new Callback<Student>() {

            @Override
            public void success(Student student, Response response) {
                SharedPreferencesValues.USER_NAME.putString(student.getName());
                AppUtil.startActivityAndFinishPrevious(LoginActivity.this, MainActivity.class);
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }
}
