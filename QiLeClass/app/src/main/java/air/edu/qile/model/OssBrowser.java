package air.edu.qile.model;

import android.content.Context;
import android.util.Log;

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

import java.util.ArrayList;
import java.util.List;

import air.edu.qile.model.bean.DataConfig;
import air.edu.qile.model.bean.ObjectData;
import air.edu.qile.model.bean.TokenBean;
import air.edu.qile.tool.XmlTool;

/**
 * Created by Administrator on 2018/4/11.
 *
 *  登录 oss
 *
 */

public class OssBrowser {

    private Context context;
    private String ConfigName="DataConfig.txt";
    private String EndPoint = "oss-cn-shenzhen.aliyuncs.com";
    private String BucketName = "shareworld";
    private String TokenServer = "http://classqile.duapp.com/";
    private  OSS ossclient;

    public OssBrowser(Context context, TokenBean token) {
        this.context = context;
      //  OSSLog.enableLog();
        OSSCredentialProvider credentialProvider = new StsGetter(token);
        //该配置类如果不设置，会有默认配置，具体可看该类
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        ossclient = new OSSClient(context, EndPoint, credentialProvider);

//        //   测试
//        String url = null;
//        try {
//            url = oss.presignConstrainedObjectURL(BucketName, "test2/吃西瓜.mp4", 30 * 60);
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }

    }

    // 显示文件夹下的文件
    // 设定前缀 .eg: "奇乐课堂/微课堂/"
    public void  ShowFolderFile(final String  PrefixPath){
      //  Log.w("test","ShowFolderFile:"+PrefixPath);
        ListObjectsRequest listObjects = new ListObjectsRequest(BucketName);
        //   "/" 为文件夹的分隔符
        listObjects.setDelimiter("/");
        listObjects.setPrefix(PrefixPath);
        final List<ObjectData> dataList=new ArrayList<>();
        OSSAsyncTask task = ossclient.asyncListObjects(listObjects, new OSSCompletedCallback<ListObjectsRequest, ListObjectsResult>() {
            @Override
            public void onSuccess(ListObjectsRequest request, ListObjectsResult result) {
                int filesize=result.getObjectSummaries().size(); // 文件数目
                int foldersize =result.getCommonPrefixes().size(); //文件夹数目
                Log.w("test", "filesize:"+filesize+"   foldersize:"+foldersize);
                for (int i = 0; i < filesize; i++) {
                    String key=result.getObjectSummaries().get(i).getKey();
                    long objsize  =result.getObjectSummaries().get(i).getSize();
                    String etag=result.getObjectSummaries().get(i).getETag();
                //    Log.w("test","文件名key: " +key + "   etag: "+etag );
                    dataList.addAll( resolveOssData(key,etag,   objsize) );

                }
                for (int i = 0; i < foldersize; i++) {
                    String key=result.getCommonPrefixes().get(i);
                    long objsize  =result.getObjectSummaries().get(i).getSize();
                    String etag=result.getObjectSummaries().get(i).getETag();
                //    Log.w("test","文件夹key: " +key + "   etag: "+etag );
                    dataList.addAll( resolveOssData(key,etag,   objsize) );

                }
                resolverXML(dataList);
            }

            @Override
            public void onFailure(ListObjectsRequest request, ClientException clientException, ServiceException serviceException) {

            }
        });
        task.waitUntilFinished();

    }

  // 解析 oss 返回的数据
    private List<ObjectData> resolveOssData(String key,String etag, long objsize){
        List<ObjectData> dataList=new ArrayList<>();
        try {
            String url = ossclient.presignConstrainedObjectURL(BucketName, key,
                    30 * 60);
            ObjectData data=new ObjectData();
            data.setFullpath(key);
            data.setEtag(etag);
            data.setUrl(url);
            data.setObjsize(objsize);
            dataList.add( data );
         //  Logger.w("data:"+data);

        } catch (ClientException e) {
            e.printStackTrace();
        }
        return dataList;
    }
    // 注意xml格式必须是  utf-8 无 bom
    private void resolverXML(List<ObjectData> dataList  ){
        List<DataConfig> configlist=new ArrayList<>();
        List<ObjectData> dataList_folder=new ArrayList<>();
        for( ObjectData data:dataList){
            if( data.getFullpath().endsWith(ConfigName) ){
                //  Log.w("test","解析:"+data);
                configlist.addAll( XmlTool.getInstance().ReadFromXML(data.getUrl() ) );
            }
            // 这个对象是文件夹
            if(data.getFullpath().endsWith("/")){
                dataList_folder.add( data );
            }
        }
        for (DataConfig  config:  configlist){
           // Log.w("test","config:"+config);
            for(ObjectData folder:dataList_folder){
               // Log.w("test",folder.getSimplName()+"  folder:"+folder);
                if(config.getName().equals(  folder.getSimplName() )){
                    folder.setFolderConfig( config );
                    //  设置封面 url
                    String cover_url = null;
                    try {
                        String covername=folder.getParentPath()+ config.getCover();
                        cover_url = ossclient.presignConstrainedObjectURL(BucketName,covername,
                                30 * 60);
                        folder.setFolderCover( cover_url );
                        Logger.w(  covername+"   完整data:"+folder);
                    } catch (ClientException e) {
                        e.printStackTrace();
                    }

                }
            }
        }





    }

    /**************************  sts 凭证      **************************************/
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
