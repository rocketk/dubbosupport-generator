package py.dubbosupport;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pengyu on 2017/6/18.
 */
public class ResourcesUtil {

    public static List<String> getResourceFiles(String path) throws IOException {
        List<String> filenames = new ArrayList<String>();
        InputStream in = null;
        BufferedReader br = null;
        try {
            in = getResourceAsStream(path);
            br = new BufferedReader(new InputStreamReader(in));
            String resource;
            while ((resource = br.readLine()) != null) {
                filenames.add(resource);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (br != null) {
                br.close();
            }
        }

        return filenames;
    }

    public static InputStream getResourceAsStream(String resource) {
        final InputStream in = ResourcesUtil.class.getResourceAsStream(resource);
        return in == null ? getContextClassLoader().getResourceAsStream(resource) : in;
    }

    public static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static void main(String[] args) throws IOException {
        List<String> list = getResourceFiles("templates");
        System.out.println(Arrays.toString(list.toArray()));
    }

}
