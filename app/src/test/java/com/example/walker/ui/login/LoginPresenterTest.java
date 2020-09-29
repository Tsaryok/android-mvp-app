package com.example.walker.ui.login;

import com.example.walker.R;
import com.example.walker.data.DataManager;
import com.example.walker.data.db.model.User;
import com.example.walker.utils.rx.TestSchedulerProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    @Mock
    LoginMvpView mMockLoginMvpView;
    @Mock
    DataManager mMockDataManager;

    private LoginPresenter<LoginMvpView> mLoginPresenter;
    private TestScheduler mTestScheduler;

    @BeforeClass
    public static void onlyOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);
        mLoginPresenter = new LoginPresenter<>(
                mMockDataManager,
                testSchedulerProvider,
                compositeDisposable);
        mLoginPresenter.onAttach(mMockLoginMvpView);
    }

    @Test
    public void testLoginUnsuccessfully() {

        String login = "login";
        String password = "password";
        User user = new User();


        doReturn(Observable.just(user))
                .when(mMockDataManager)
                .authentication(login, password);

        mLoginPresenter.onLoginClick(login, password);

        mTestScheduler.triggerActions();

        verify(mMockLoginMvpView).showLoading();
        verify(mMockLoginMvpView).hideLoading();
        verify(mMockLoginMvpView).onError(R.string.wrong_login_password);
    }

    @Test
    public void testLoginSuccess() {

        Long userId = 1L;
        String login = "login";
        String password = "password";
        String name = "Tsarik";
        long birthday = 1;

        User user = new User(userId, login, password, name, birthday, null);
        user.setGender(User.Gender.MALE);


        doReturn(Observable.just(user))
                .when(mMockDataManager)
                .authentication(login, password);

        mLoginPresenter.onLoginClick(login, password);

        mTestScheduler.triggerActions();

        verify(mMockLoginMvpView).showLoading();
        verify(mMockLoginMvpView).hideLoading();
        verify(mMockLoginMvpView).openMainActivity(1L);
    }


    @After
    public void tearDown() throws Exception {
        mLoginPresenter.onDetach();
    }

}