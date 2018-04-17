package air.edu.qile.model;

import android.content.Context;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.ListObjectsRequest;
import com.alibaba.sdk.android.oss.model.ListObjectsResult;
import com.orhanobut.logger.Logger;

/**
 * Created by Administrator on 2018/4/11.
 *
 *  登录 oss
 *
 */

public class OssBrowser {

    private Context context;
    private String EndPoint = "oss-cn-shenzhen.aliyuncs.com";
    private String BucketName = "shareworld";
    private String TokenServer = "http://classqile.duapp.com/";
    private  OSS oss;

    public OssBrowser(Context context, TokenBean token) {
        this.context = context;
        OSSLog.enableLog();
        OSSCredentialProvider credentialProvider = new StsGetter(token);
        //该配置类如果不设置，会有默认配置，具体可看该类
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        oss = new OSSClient(context, EndPoint, credentialProvider);

        //   测试
        String url = null;
        try {
            url = oss.presignConstrainedObjectURL(BucketName, "test2/吃西瓜.mp4", 30 * 60);
        } catch (ClientException e) {
            e.printStackTrace();
        }
      //  Logger.w("测试:" + url);
        ShowFolderFile();
    }

    // 显示文件夹下的文件
    private void  ShowFolderFile(){

        ListObjectsRequest listObjects = new ListObjectsRequest(BucketName);
        OSSAsyncTask task = oss.asyncListObjects(listObjects, new OSSCompletedCallback<ListObjectsRequest, ListObjectsResult>() {
            @Override
            public void onSuccess(ListObjectsRequest request, ListObjectsResult result) {
                int size=result.getObjectSummaries().size();
                Logger.w( "Success! "+size);
                for (int i = 0; i < size; i++) {
                    Logger.w(  "object: " + result.getObjectSummaries().get(i).getKey() + " "
                            + result.getObjectSummaries().get(i).getETag() + " "
                            + result.getObjectSummaries().get(i).getLastModified());
                }

            }

            @Override
            public void onFailure(ListObjectsRequest request, ClientException clientException, ServiceException serviceException) {

            }
        });
        task.waitUntilFinished();

    }



    class StsGetter extends OSSFederationCredentialProvider {

        private OSSFederationToken ossFederationToken;
        String ak;
        String sk;
        String token;
        String expiration;

        public StsGetter(TokenBean bean) {
            this.ak = bean.getCredentials().getAccessKeyId();
            this.sk = bean.getCredentials().getAccessKeySecret();
            this.token = bean.getCredentials().getSecurityToken();
            this.expiration = bean.getCredentials().getExpiration();
        }


        @Override
        public OSSFederationToken getFederationToken() throws ClientException {

            return new OSSFederationToken(ak, sk, token, expiration);

        }
    }

}
