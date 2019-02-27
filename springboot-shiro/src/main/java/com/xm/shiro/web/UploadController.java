package com.xm.shiro.web;

import java.io.File;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.xm.shiro.MediaErrors;
import com.xm.shiro.utils.FileUtil;
import com.xm.shiro.utils.StringUtils;
import com.xm.shiro.utils.Validator;
import com.xm.shiro.utils.WebUtil;


@Controller
@CrossOrigin
public class UploadController {
    // private static Log log = LogFactory.getLog(UploadController.class);

    @Autowired
    private Environment env;
    
    // @Autowired
    // private OssImageStore store;
    /**
     * 
     * @param uploadfile
     * @param prefix 上传文件夹名，后续会创建
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/media/upload", method = RequestMethod.POST)
    public void uploadFile(@RequestParam("file") MultipartFile uploadfile, String prefix,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=utf-8");

        String fileExt = env.getProperty("qmedia.fileExtensions");
        Integer fileSize = env.getProperty("qmedia.uploadedFileMaxSize", Integer.class);
        String[] suffix = fileExt.split(",");
        
        if(!Validator.validateFolderName(prefix)) {
            WebUtil.writeError(response, MediaErrors.InvalidPrefix());
            return;
        }

        if (!FileUtil.validateFile(uploadfile, fileSize * 1024 * 1024, suffix)) {
            WebUtil.writeError(response, MediaErrors.UploadFileSizeTooLarge(fileSize));
            return;
        }
        
        System.out.println(uploadfile.getOriginalFilename());
        Map<String, String> map = FileUtil.parsePath(uploadfile.getOriginalFilename());
        String ext = map.get("ext");
        if (StringUtils.isNotBlank(ext)) {
            ext = "." + ext;
        }

        String fileName = UUID.randomUUID().toString() + ext;

        String useoss = env.getProperty("qmedia.useoss");
        if (useoss != null && "true".equalsIgnoreCase(useoss)) {

            // String url = store.save(prefix, fileName,
            // uploadfile.getInputStream());
            // WebUtil.writeOK(response, "url", url);

        } else {
        	// /media/uploads
            String directory = env.getProperty("qmedia.uploadedFiles");
            // "2*32"
            String depthRange = env.getProperty("qmedia.uploadedDirDepth");
            String hashPath = FileUtil.getHashPath(depthRange);
            String dir = Paths.get(directory, prefix, hashPath).toString();

            if (!FileUtil.createDirs(dir, true)) {
				WebUtil.writeError(response, MediaErrors.CreateDirFailed());
                return;
            }
            System.out.println(new File(dir).getAbsolutePath());
//            File destFile = new File(dir, fileName);
            File destFile = new File(new File(dir).getAbsolutePath(), fileName);
            uploadfile.transferTo(destFile);

            String mediaHost = env.getProperty("qmedia.host");
            if ("sameWithUpload".equals(mediaHost)) {
            	mediaHost = WebUtil.getRequestUrlRoot(request.getRequestURL(),request.getRequestURI());
            }
            System.out.println(destFile.getAbsoluteFile());
            WebUtil.writeOK(response, "url", mediaHost + "uploads/" +prefix +"/"+hashPath + fileName, "path", destFile.getAbsoluteFile());
        }

    }

}
