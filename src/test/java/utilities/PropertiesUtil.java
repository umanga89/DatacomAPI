package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesUtil {

    private static Properties props;

    public static Properties readPropertyFile(String filePath) throws Exception {
        try{
            props= new Properties();
            File propertyFile = new File(filePath);
            FileInputStream fis = new FileInputStream(propertyFile);
            props.load(fis);
        }catch(Exception e){
            throw e;
        }
        return props;
    }
}
