package com.example.walker.ui.registry;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.walker.R;
import com.example.walker.model.User;
import com.example.walker.di.component.ActivityComponent;
import com.example.walker.ui.base.BaseFragment;
import com.example.walker.ui.main.MainActivity;
import com.example.walker.utils.CommonUtils;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RegistryFragment extends BaseFragment implements RegistryMvpView {

    public static final String TAG = "RegistryFragment";

    @Inject
    RegistryMvpPresenter<RegistryMvpView> mPresenter;

    @BindView(R.id.register_et_name)
    EditText mNameEditText;

    @BindView(R.id.register_et_date)
    EditText mDateEditText;

    @BindView(R.id.register_et_email)
    EditText mEmailEditText;

    @BindView(R.id.register_et_password)
    EditText mPasswordEditText;

    @BindView(R.id.register_et_password2)
    EditText mRePasswordEditText;

    @BindView(R.id.register_rb_male)
    RadioButton mMaleRadioButton;

    @BindView(R.id.register_rb_female)
    RadioButton mFemaleRadioButton;

    Calendar date;

    FirebaseUser mFirebaseUser;

    public static RegistryFragment newInstance() {
        Bundle args = new Bundle();
        RegistryFragment fragment = new RegistryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registry, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }
        setUp(view);
        return view;
    }

    @OnClick(R.id.btn_back)
    void onBackClick(View v){
        backToLoginFragment();
    }

    @OnClick(R.id.btn_done)
    void onDoneClick(View v){
        if (isDataValid()){
            createUser();
        }
    }

    public void backToLoginFragment() {
        getBaseActivity().getSupportFragmentManager().popBackStack();
    }

    boolean isDataValid(){
        if (mNameEditText.getText().toString().isEmpty()){
            //show message
            onError(R.string.empty_name);
            return false;
        }
        if (mEmailEditText.getText().toString().isEmpty()){
            //show message
            onError(R.string.empty_email);
            return false;
        }
        if (!mPasswordEditText.getText().toString().matches(mRePasswordEditText.getText().toString()) || mPasswordEditText.getText().toString().isEmpty()){
            //show message
            onError(R.string.wrong_password);
            return false;
        }
        if (mDateEditText.getText().toString().isEmpty()){
            //show message
            onError(R.string.empty_date);
            return false;
        }
        if (!mMaleRadioButton.isChecked() && !mFemaleRadioButton.isChecked()){
            //show message
            onError(R.string.empty_gender);
            return false;
        }
        if (CommonUtils.isEmailValid(mEmailEditText.getText().toString())){
            onError(mEmailEditText.getText().toString());
            return false;
        }
        return true;
    }

    @Override
    public void createUser() {
        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        User user = new User(mNameEditText.getText().toString(),
                date.getTimeInMillis(),
                mMaleRadioButton.isChecked()?"male":"female");
        mPresenter.onUserCreate(email, password, user);
    }

    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.getStartIntent(getContext());
        startActivity(intent);
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    protected void setUp(View view) {
        date = Calendar.getInstance();
        mDateEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideKeyboard();
                if(hasFocus) {
                    new DatePickerDialog(getContext(), dateListener,
                            date.get(Calendar.YEAR),
                            date.get(Calendar.MONTH),
                            date.get(Calendar.DAY_OF_MONTH))
                            .show();
                }
                v.clearFocus();
            }
        });
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }

    private DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            date.set(year,month,dayOfMonth);
            mDateEditText.setText(DateUtils.formatDateTime(getContext(),
                    date.getTimeInMillis(),
                    DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
        }
    };
}
