package air.edu.qile.model;

import com.alibaba.fastjson.JSON;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import air.edu.qile.model.bean.TokenBean;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2018/4/14 0014.
 *
 *  从 bae 服务器上 获取 登录口令
 *
 */

public class OssTokenGet {

    public static String  base_url="http://classqile.duapp.com";
    private interface  ossToken_apiInterface{
        // 获取  access TokenBean
        @GET("/")
        Call<ResponseBody> getAccessToken();
    }

   /****************************************************************/


    private OssTokenListen getlisten;
    private ossToken_apiInterface  apiInterface;
    private Retrofit ossToken_retrofit;

    public OssTokenGet(OssTokenListen getlisten) {
        this.getlisten = getlisten;
        ossToken_retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiInterface = ossToken_retrofit.create(ossToken_apiInterface.class);
    }




    public void getAccessToken() {

        Call<ResponseBody> call = apiInterface.getAccessToken();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody body = response.body();
                if (body != null) {
                    try {
                        final String txt = body.string();
                        if (txt != null) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    TranToObj(txt);
                                }
                            }).start();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Logger.w(" 获取 token 失败 ................. ");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Logger.w(" getAccessToken    onFailure  ............" );
            }
        });
    }

    //  转换为 TokenBean 对象
    private void  TranToObj(String  json){

        TokenBean token = JSON.parseObject(  json , TokenBean.class);
//         Logger.w( "token:\n"+ token.getCredentials().getAccessKeyId()+"\n"+
//                token.getCredentials().getAccessKeySecret()+"\n"+
//                token.getCredentials().getSecurityToken()
//                +"..................");
        Hawk.put(  TokenBean.class.getSimpleName() ,  token );

        getlisten.GetToken(token);
        EventBus.getDefault().post( token );
    }


    public interface    OssTokenListen {


        public  void GetToken(TokenBean token);

    }


}
