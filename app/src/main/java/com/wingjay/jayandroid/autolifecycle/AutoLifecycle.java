package com.wingjay.jayandroid.autolifecycle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.support.annotation.NonNull;
import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.BaseFragment;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.functions.Func0;

/**
 * AutoLifecycle
 *
 * @author wingjay
 * @date 2017/10/22
 */
public class AutoLifecycle {

    private static AutoLifecycle instance;
    private AutoLifecycle() {}
    public synchronized static AutoLifecycle getInstance() {
        if (instance == null) {
            instance = new AutoLifecycle();
        }

        return instance;
    }

    public void init(@NonNull final Object object, @NonNull ILifecycleProvider lifecycleProvider) {
        Class clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (final Method m : methods) {
            AutoLifecycleEvent event = m.getAnnotation(AutoLifecycleEvent.class);
            if (event == null) {
                continue;
            }
            m.setAccessible(true);
            OnSubscribe<Object> executable = new OnSubscribe<Object>() {
                @Override
                public void call(Subscriber<? super Object> subscriber) {
                    try {
                        m.invoke(object);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            };

            if (event.common() != CommonLifecycle.NULL) {
                registerCommonLifecycle(lifecycleProvider, executable, event.common());
            } else {
                registerFunctionOnLifecycle(lifecycleProvider,
                    executable,
                    event.activity(),
                    event.fragment(),
                    event.dialog());
            }
        }
    }

    private void registerCommonLifecycle(@NonNull ILifecycleProvider lifecycleProvider,
                                         @NonNull final OnSubscribe<Object> executable,
                                         @NonNull CommonLifecycle commonLifecycle) {
        switch (commonLifecycle) {
            case CREATE: {
                registerFunctionOnLifecycle(lifecycleProvider, executable,
                    ActivityLifecycle.CREATE, FragmentLifecycle.CREATE, DialogFragmentLifecycle.CREATE);
                break;
            }
            case START: {
                registerFunctionOnLifecycle(lifecycleProvider, executable,
                    ActivityLifecycle.START, FragmentLifecycle.START, DialogFragmentLifecycle.START);
                break;
            }
            case RESUME: {
                registerFunctionOnLifecycle(lifecycleProvider, executable,
                    ActivityLifecycle.RESUME, FragmentLifecycle.RESUME, DialogFragmentLifecycle.RESUME);
                break;
            }
            case PAUSE: {
                registerFunctionOnLifecycle(lifecycleProvider, executable,
                    ActivityLifecycle.PAUSE, FragmentLifecycle.PAUSE, DialogFragmentLifecycle.PAUSE);
                break;
            }
            case STOP: {
                registerFunctionOnLifecycle(lifecycleProvider, executable,
                    ActivityLifecycle.STOP, FragmentLifecycle.STOP, DialogFragmentLifecycle.STOP);
                break;
            }
            case DESTROY: {
                registerFunctionOnLifecycle(lifecycleProvider, executable,
                    ActivityLifecycle.DESTROY, FragmentLifecycle.DESTROY, DialogFragmentLifecycle.DESTROY);
                break;
            }
            default: break;
        }
    }

    private void registerFunctionOnLifecycle(@NonNull ILifecycleProvider lifecycleProvider,
                                             @NonNull final OnSubscribe<Object> executable,
                                             ActivityLifecycle activityLifecycle,
                                             FragmentLifecycle fragmentLifecycle,
                                             DialogFragmentLifecycle dialogFragmentLifecycle) {
        Observable wrapper = Observable.defer(new Func0<Observable<Object>>() {
            @Override
            public Observable<Object> call() {
                return Observable.create(executable);
            }
        });

        if (lifecycleProvider instanceof BaseActivity
            && activityLifecycle != ActivityLifecycle.NULL) {
            lifecycleProvider.executeWhen(wrapper, activityLifecycle);
        } else if (lifecycleProvider instanceof BaseFragment
            && fragmentLifecycle != FragmentLifecycle.NULL) {
            lifecycleProvider.executeWhen(wrapper, fragmentLifecycle);
        }
        //else if (lifecycleProvider instanceof UIDialogFragmentSystem
        //    && dialogFragmentLifecycle != DialogFragmentLifecycle.NULL) {
        //    lifecycleProvider.executeWhen(wrapper, dialogFragmentLifecycle);
        //}
    }
}
