package iee.yh;

import iee.yh.Mymall.product.entity.BrandEntity;
import iee.yh.Mymall.product.mymallProductApplication;
import iee.yh.Mymall.product.service.BrandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = mymallProductApplication.class)
public class testdemo {

    @Resource
    public BrandService brandService;

    @Test
    public void contextLoads(){
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("Apple");
        boolean save = brandService.save(brandEntity);
        System.out.println(save);
    }
    @Test
    public void contextLoads2(){
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setBrandId(1L);
        brandEntity.setDescript("m1 yyds");
        boolean save = brandService.updateById(brandEntity);
        System.out.println(save);
    }
//    @Resource
//    private OSSClient ossClient;
//    @Test
//    public void ossTest() throws FileNotFoundException {
////        String endpoint = "oss-cn-beijing.aliyuncs.com";
////        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
////        String accessKeyId = "LTAI5tJN5b6HP2aqnhsUEmvb";
////        String accessKeySecret = "UIj2cpg2xdg9qgauJePxUWl0Fu6Hf4";
////        // 填写Bucket名称，例如examplebucket。
////        String bucketName = "mymall-yang";
////        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
////        String objectName = "ipad.png";
////        // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
////        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
////        String filePath= "/Users/yanghan/Desktop/Mymall/mymall-product/src/test/java/iee/yh/ipad.png";
////
////        // 创建OSSClient实例。
////        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
////
////        try {
////            InputStream inputStream = new FileInputStream(filePath);
////            // 创建PutObject请求。
////            ossClient.putObject(bucketName, objectName, inputStream);
////            System.out.println("上传完成！");
////        } catch (OSSException oe) {
////            System.out.println("Caught an OSSException, which means your request made it to OSS, "
////                    + "but was rejected with an error response for some reason.");
////            System.out.println("Error Message:" + oe.getErrorMessage());
////            System.out.println("Error Code:" + oe.getErrorCode());
////            System.out.println("Request ID:" + oe.getRequestId());
////            System.out.println("Host ID:" + oe.getHostId());
////        } catch (ClientException ce) {
////            System.out.println("Caught an ClientException, which means the client encountered "
////                    + "a serious internal problem while trying to communicate with OSS, "
////                    + "such as not being able to access the network.");
////            System.out.println("Error Message:" + ce.getMessage());
////        } catch (FileNotFoundException e) {
////            e.printStackTrace();
////        } finally {
////            if (ossClient != null) {
////                ossClient.shutdown();
////            }
////        }
//        ossClient.putObject("mymall-yang","ipad.png",new FileInputStream("/Users/yanghan/Desktop/Mymall/mymall-product/src/test/java/iee/yh/ipad.png"));
//        System.out.println("!!!!");
//    }

}
