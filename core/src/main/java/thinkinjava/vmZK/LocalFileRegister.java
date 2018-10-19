package thinkinjava.vmZK;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import thinkinjava.rpc.remoting.Url;
import thinkinjava.util.CloseUtil;

/**
 *
 * @author 莫那·鲁道
 * @date 2018/10/15-上午6:39
 */
public class LocalFileRegister implements ServiceRegisterDisCover {

    private static Map<String, Url> cache = new ConcurrentHashMap<String, Url>();

    private static String userHome;
    private static String currentDir;
    private static String lineSeparator;
    private static String configHome;
    static {
        userHome = System.getProperty("user.home");
        configHome = userHome + "/lu/config.conf";
        currentDir = System.getProperty("user.dir");
        lineSeparator = System.getProperty("line.separator");

    }

    public static LocalFileRegister create() {
        return new LocalFileRegister();
    }

    public void registerService(String serviceName, Url url) {
        File file = new File(configHome);
        PrintWriter pw = null;
        try {
            if (!file.exists()) {
                File dir = new File(userHome + "/lu");
                if (dir.mkdirs()) {
                    file = new File(configHome);
                }
            }
            pw = new PrintWriter((new FileOutputStream(file, true)));
            pw.append(serviceName).append("=").append(url.getIp()).append(":").append(String.valueOf(url.getPort()));
            pw.append(lineSeparator);
            pw.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.close(pw);
        }

    }

    public void unregisterService(String serviceName) {
        cache.remove(serviceName);

        flushCache();
    }


    public Url getServiceUrl(String serviceName) {

        Url url = cache.get(serviceName);

        if (url != null) {
            return url;
        }

        readFileAndCache();

        return cache.get(serviceName);
    }

    private void readFileAndCache() {
        File file = new File(configHome);
        long length = file.length();
        byte[] content = new byte[(int) length];
        FileInputStream is = null;
        try {

            is = new FileInputStream(file);
            is.read(content);

            String contentString = new String(content);
            String[] arr = contentString.split(lineSeparator);

            List<String> data = Arrays.asList(arr);

            cacheData(data);

        } catch (java.io.IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.close(is);
        }
    }

    private void flushCache() {
        File file = new File(configHome);
        PrintWriter pw = null;
        try {
            if (!file.exists()) {
                File dir = new File(userHome + "lu");
                if (dir.mkdirs()) {
                    file = new File(configHome);
                }
            }

            pw = new PrintWriter((new FileOutputStream(file)));

            for (String k : cache.keySet()) {
                String serviceName = k;
                Url url = cache.get(k);
                pw.append(serviceName + "=" + url.getIp() + ":" + url.getPort());
                pw.append(lineSeparator);
            }

            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseUtil.close(pw);
        }
    }


    private void cacheData(List<String> data) {
        for (String item : data) {
            String[] arr = item.split("=");
            if (arr.length == 2) {
                String[] serverUrl = arr[1].split(":");
                cache.put(arr[0], new Url(serverUrl[0], Integer.valueOf(serverUrl[1])));
            }
        }
    }

}
