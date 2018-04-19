package air.edu.qile.model.bean;

/**
 * Created by Administrator on 2018/4/14 0014.
 *
 *
示例：

 {
 "RequestId": "6C1CD840-06AA-495B-98FA-A36D14986ACC",
 "AssumedRoleUser": {
 "AssumedRoleId": "366943852700332796:external-username",
 "Arn": "acs:ram::1586261923898944:role/aliyunosstokengeneratorrole/external-username"
 },
 "Credentials": {
 "AccessKeySecret": "GVMhqr9S72Vgj8bU7gvDXkdgae27DKG7uXMeQKDH74f9",
 "AccessKeyId": "STS.GMg6bd8vhQryQpEuRRxS6v69L",
 "Expiration": "2018-04-14T03:48:26Z",
 "SecurityToken": "CAISiwJ1q6Ft5B2yfSjIpo7SfdjQ1alJ5rCSU1b0kVIHdNwamfOSrjz2IH5KdXVrCe0csfw3n2tS6fcclrh+W4NIX0rNaY5t9ZlN9wqkbtIPITZPKfhW5qe+EE2/VjTZvqaLEcibIfrZfvCyESOm8gZ43br9cxi7QlWhKufnoJV7b9MRLGLaBHg8c7UwHAZ5r9IAPnb8LOukNgWQ4lDdF011oAFx+wgdgOadupTBukSE0gSrkLRF9tSgfsSeApMybMslYbCcx/drc6fN6ilU5iVR+b1+5K4+ommZ743GUwQLvE3eaLGOrYwxNnxwYqkrBqhDt+Pgkv51vOPekYntwgpKJ/tSVynP7CQyOwV6mIkagAGzUm7e7Ks85f97VLNGo4zUVMR5Sb/uNgpGQNvKWkMKwvoqU3+r2dDNZx8W65uDpQHKVv0Xsjas+bMhrgpV3ec6KkT0HryfaGRpvzEDK/vAJu+B+fPyJq+5kzxpkAc57T+YgS6LWvxSCVyfG+yCc0LNfjRjOb6VotLoNihg1A2QVQ=="
 }
 }


 *
 *
 */

public class TokenBean {

    private  String  RequestId;
    private  AssumedRoleUser  assumedRoleUser;
    private  Credentials  credentials;

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }

    public AssumedRoleUser getAssumedRoleUser() {
        return assumedRoleUser;
    }

    public void setAssumedRoleUser(AssumedRoleUser assumedRoleUser) {
        this.assumedRoleUser = assumedRoleUser;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    class  AssumedRoleUser{
        private String AssumedRoleId;
        private String Arn;

        public String getAssumedRoleId() {
            return AssumedRoleId;
        }

        public void setAssumedRoleId(String assumedRoleId) {
            AssumedRoleId = assumedRoleId;
        }

        public String getArn() {
            return Arn;
        }

        public void setArn(String arn) {
            Arn = arn;
        }

        @Override
        public String toString() {
            return "AssumedRoleUser{" +
                    "AssumedRoleId='" + AssumedRoleId + '\'' +
                    ", Arn='" + Arn + '\'' +
                    '}';
        }
    }

    public class  Credentials{
        private String AccessKeySecret;
        private String AccessKeyId;
        private String Expiration;
        private String SecurityToken;

        public String getAccessKeySecret() {
            return AccessKeySecret;
        }

        public void setAccessKeySecret(String accessKeySecret) {
            AccessKeySecret = accessKeySecret;
        }

        public String getAccessKeyId() {
            return AccessKeyId;
        }

        public void setAccessKeyId(String accessKeyId) {
            AccessKeyId = accessKeyId;
        }

        public String getExpiration() {
            return Expiration;
        }

        public void setExpiration(String expiration) {
            Expiration = expiration;
        }

        public String getSecurityToken() {
            return SecurityToken;
        }

        public void setSecurityToken(String securityToken) {
            SecurityToken = securityToken;
        }

        @Override
        public String toString() {
            return "Credentials{" +
                    "AccessKeySecret='" + AccessKeySecret + '\'' +
                    ", AccessKeyId='" + AccessKeyId + '\'' +
                    ", Expiration='" + Expiration + '\'' +
                    ", SecurityToken='" + SecurityToken + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "TokenBean{" +
                "RequestId='" + RequestId + '\'' +
                ", assumedRoleUser=" + assumedRoleUser +
                ", credentials=" + credentials +
                '}';
    }
}
