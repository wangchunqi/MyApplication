package sunyandong.bawei.com.sunyandongcs.Utils;

/**
 * Created by 悻 on 2018/1/19.
 */

public interface OnNetWorkListener<T> {
    //失败的回调
    void onFailure(Exception e);
    //成功的回调
    void onSucceed(T t);
}
