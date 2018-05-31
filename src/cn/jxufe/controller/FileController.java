package cn.jxufe.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import cn.jxufe.bean.Message;
import cn.jxufe.utils.FileSaver;

@Controller
@RequestMapping("file")
public class FileController {
	/**
	 * 
	 * @param request 接收request请求HttpServletRequest对象
	 * @param uploadFile 接收上传的文件MultipartFile对象
	 * @return 返回保存文件处理消息体Message对象
	 */
	@RequestMapping(value = "saveHeadImg", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Message saveHeadImg(HttpServletRequest request, @RequestParam("filePathName") MultipartFile uploadFile) {
		return FileSaver.save("images/headImages/", request, uploadFile);
	}
}