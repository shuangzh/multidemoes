package com.shuangzh;

import org.apache.http.client.CookieStore;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by admin on 2017/4/18.
 */
public class SSLClient extends DefaultHttpClient {
    public SSLClient() throws Exception{
        super();
        SSLContext ctx = SSLContext.getInstance("TLS");
        X509TrustManager tm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }
            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        ctx.init(null, new TrustManager[]{tm}, null);
        SSLSocketFactory ssf = new SSLSocketFactory(ctx,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        ClientConnectionManager ccm = this.getConnectionManager();
        SchemeRegistry sr = ccm.getSchemeRegistry();
        sr.register(new Scheme("https", 443, ssf));
    }

//        CookieStore cookieStore = this.getCookieStore();
//        String cookies = "__guid=85OLHh58ca2e7d9ba8a5.33304212; LiveWSMBY90996241=1489645196571441042263; NMBY90996241fistvisitetime=1489645196579; vlstatId=vlstat-1489645196000-2613626222; __jsluid=85688deee6af1dce0181d49667e9dce5; LiveWSMBY90996241sessionid=1492485915761610677649; NMBY90996241visitecounts=2; _nyjy_newadv_=1; User_datas=%7B%22name%22%3A%22%5Cu5468%5Cu53cc%22%2C%22sex%22%3A0%2C%22avatar%22%3A%22%5C%2Fstatic%5C%2Fv4%5C%2Fstyle%5C%2Fglobal%5C%2Fu_0.jpg%22%2C%22phone%22%3A%2213809884175%22%2C%22fid%22%3A%223627257%22%7D; __jsl_clearance=1492503696.943|0|r6fscQkrr9%2B8Lj8a0LQ4GgyWpdE%3D";
//        String[] cks = cookies.split(";");
//        for (String s : cks) {
//            s = s.trim();
//            String[] ss=s.split("=");
//            cookieStore.addCookie(new BasicClientCookie(ss[0], ss[1]));
//        }
//        cookieStore.addCookie(new BasicClientCookie("__guid","85OLHh58ca2e7d9ba8a5.33304212"));
//        cookieStore.addCookie(new BasicClientCookie("LiveWSMBY90996241","1489645196571441042263"));
//        cookieStore.addCookie(new BasicClientCookie("NMBY90996241fistvisitetime","1489645196579"));
//        cookieStore.addCookie(new BasicClientCookie("vlstatId","vlstat-1489645196000-2613626222"));
//        cookieStore.addCookie(new BasicClientCookie("__jsluid","85688deee6af1dce0181d49667e9dce5"));
//        cookieStore.addCookie(new BasicClientCookie("LiveWSMBY90996241sessionid","1492485915761610677649"));
//        cookieStore.addCookie(new BasicClientCookie(""));
//    }


}

