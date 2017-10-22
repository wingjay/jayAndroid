package com.wingjay.jayandroid.autolifecycle;

/**
 * DialogFragmentLifecycle
 *
 * @author wingjay
 * @date 2017/08/10
 */
public enum DialogFragmentLifecycle implements IContextLifecycle {
    ATTACH,
    CREATE,
    VIEW_CREATED,
    START,
    RESUME,
    PAUSE,
    STOP,
    DESTROY_VIEW,
    DESTROY,
    DETACH,
    NULL,
}
