package air.edu.qile.model;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.ListObjectsRequest;
import com.alibaba.sdk.android.oss.model.ListObjectsResult;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import air.edu.qile.model.bean.BaseData;
import air.edu.qile.model.bean.ModuleConfig;
import air.edu.qile.model.bean.ModuleData;
import air.edu.qile.model.bean.MsgEvent;
import air.edu.qile.model.bean.TokenBean;
import air.edu.qile.tool.XmlTool;
import air.edu.qile.ui.DetailActivity;

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

    private static OssBrowser  instance;

    public static OssBrowser  getInstance(){
        return  instance;
    }

    //  initInstance  必须先执行
    public static void  initInstance(Context context,TokenBean token){
        if(instance==null){
            instance=new OssBrowser(context,token);
        }
    }

    public OssBrowser(Context context,TokenBean token) {
        this.context = context;
        initOss(token);
    }

    private void initOss(TokenBean token){
        //  OSSLog.enableLog();
        OSSCredentialProvider credentialProvider = new StsGetter(token);
        //该配置类如果不设置，会有默认配置，具体可看该类
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        ossclient = new OSSClient(context, EndPoint, credentialProvider);


    }

    // 显示这个模块下的所有文件
    public void  ShowFileinModule(final String  PrefixPath){

        Log.w("test","ShowFileinModule:"+PrefixPath);
        ListObjectsRequest listObjects = new ListObjectsRequest(BucketName);
        listObjects.setPrefix(PrefixPath);
        final List<BaseData>  baseDataList=new ArrayList<>();
        OSSAsyncTask task = ossclient.asyncListObjects(listObjects, new OSSCompletedCallback<ListObjectsRequest, ListObjectsResult>() {
            @Override
            public void onSuccess(ListObjectsRequest request, ListObjectsResult result) {
                int filesize=result.getObjectSummaries().size(); // 文件数目
                for (int i = 0; i < filesize; i++) {
                    String key=result.getObjectSummaries().get(i).getKey();
                    if(key.endsWith("/")){
                        continue;
                    }
                    long objsize  =result.getObjectSummaries().get(i).getSize();
                    String etag=result.getObjectSummaries().get(i).getETag();
                    String url = null;
                    try {
                        url = ossclient.presignConstrainedObjectURL(BucketName,key,30 * 60);
                    } catch (ClientException e) {
                        url =null;
                        e.printStackTrace();
                    }
                    BaseData data=new BaseData();
                    data.setEtag( etag  );
                    data.setFullpath( key );
                    data.setSize( objsize );
                    if( key.endsWith("/")){
                        data.setFolder(true  );
                    }else {
                        data.setFolder(false  );
                    }
                    data.setName( new File( key ).getName()  );
                    data.setUrl(   url );
                   // Log.w("test","oss data  : "+data );
                    baseDataList.add( data );
                }
                MsgEvent msgEvent=new MsgEvent();
                msgEvent.setCmd("BaseDataList");
                msgEvent.setContent("BaseDataList");
                msgEvent.setListdata(  baseDataList );
                EventBus.getDefault().post( msgEvent );
            }

            @Override
            public void onFailure(ListObjectsRequest request, ClientException clientException, ServiceException serviceException) {

            }
        });
        task.waitUntilFinished();

    }

    // 显示文件夹下的模块 设定前缀 .eg: "奇乐课堂/微课堂/"
    public void  ShowModule(final String  PrefixPath){

        Log.w("test","ShowFolderFile:"+PrefixPath);
        ListObjectsRequest listObjects = new ListObjectsRequest(BucketName);
        //   "/" 为文件夹的分隔符
        listObjects.setDelimiter("/");
        listObjects.setPrefix(PrefixPath);

        final List<BaseData>  baseDataList=new ArrayList<>();
        OSSAsyncTask task = ossclient.asyncListObjects(listObjects, new OSSCompletedCallback<ListObjectsRequest, ListObjectsResult>() {
            @Override
            public void onSuccess(ListObjectsRequest request, ListObjectsResult result) {
                int filesize=result.getObjectSummaries().size(); // 文件数目
                int foldersize =result.getCommonPrefixes().size(); //文件夹数目
                Log.w("test", "filesize:"+filesize+"   foldersize:"+foldersize);
                //  文件及 文件父目录
                for (int i = 0; i < filesize; i++) {
                    String key=result.getObjectSummaries().get(i).getKey();
                    long objsize  =result.getObjectSummaries().get(i).getSize();
                    String etag=result.getObjectSummaries().get(i).getETag();
                    String url = null;
                    try {
                        url = ossclient.presignConstrainedObjectURL(BucketName,key,30 * 60);
                    } catch (ClientException e) {
                        url =null;
                        e.printStackTrace();
                    }
                    BaseData data=new BaseData();
                    data.setEtag( etag  );
                    data.setFullpath( key );
                    data.setSize( objsize );
                    if( key.endsWith("/")){
                        data.setFolder(true  );
                    }else {
                        data.setFolder(false  );
                    }
                    data.setName( new File( key ).getName()  );
                    data.setUrl(   url );
                //    Log.w("test","oss data  : "+data );
                    baseDataList.add( data );
                }
                //文件夹
                for (int i = 0; i < foldersize; i++) {
                    String key=result.getCommonPrefixes().get(i);
                    long objsize  =result.getObjectSummaries().get(i).getSize();
                    String etag=result.getObjectSummaries().get(i).getETag();
                    String url = null;
                    try {
                        url = ossclient.presignConstrainedObjectURL(BucketName,key,30 * 60);
                    } catch (ClientException e) {
                        url =null;
                        e.printStackTrace();
                    }
                    BaseData data=new BaseData();
                    data.setEtag( etag  );
                    data.setFullpath( key );
                    data.setSize( objsize );
                    data.setFolder(true  );
                    data.setName( new File( key ).getName()  );
                    data.setUrl(   url );
                 //   Log.w("test","oss data  : "+data );
                    baseDataList.add( data );

                }
               resolverXML(baseDataList,PrefixPath);
            }

            @Override
            public void onFailure(ListObjectsRequest request, ClientException clientException, ServiceException serviceException) {

            }
        });
        task.waitUntilFinished();

    }


    // 注意xml格式必须是  utf-8 无 bom
    private void resolverXML(List<BaseData> dataList ,String fatherfolder ){
        List<ModuleConfig> configlist=new ArrayList<>();
        List<ModuleData> moduleDataList=new ArrayList<>();

        Log.w("test","oss dataList  " +dataList.size());
        for( BaseData data:dataList){
            if( data.getName().equals(ConfigName) ){
            //    Log.w("test","oss resolverXML  " +data.getName()+"\n"+ data.getUrl() );
                configlist.addAll( XmlTool.getInstance().ReadFromXML(data.getUrl() ) );
            }
        }
        for (ModuleConfig config:  configlist){
            ModuleData moduleData=new ModuleData();
            moduleData.setConfig( config );
            moduleData.setFatherModule( fatherfolder  );
            for( BaseData data:dataList){
                if( data.getName().equals(config.getName()) ){
                    moduleData.setFolder( data );
                }
                if( data.getName().equals(config.getCover()) ){
                    moduleData.setCover( data );
                }
            }
            if( moduleData.getCover()!=null ||  moduleData.getFolder()!=null ){
                moduleDataList.add( moduleData );
            }
         //   Log.w("test","oss module    : "+moduleData );
        }
        EventBus.getDefault().post( moduleDataList );

    }

    public void  OssDestolry(){



    }

    /**************************
     *
     *
     * sts 凭证
     *
     * **************************************/
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
