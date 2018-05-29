package cn.jxufe.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jxufe.bean.Message;
import cn.jxufe.entity.User;
import cn.jxufe.service.FarmerService;
import cn.jxufe.service.UserService;

@Controller
@RequestMapping("farm")
public class FarmerController {
	@Autowired
	private UserService userService;
	/**
	 * FarmService接口对象
	 * 
	 * @see cn.jxufe.service.FarmerService
	 */
	@Autowired
	private FarmerService farmerService;

	@RequestMapping(value = "grid")
	public String grid() {
		return "farmer/grid";
	}


	/**
	 * 
	 * @param session
	 *            接收前台的HttpSession信息
	 * @param user
	 *            接收选择的用户信息
	 * @return 将当前的用户信息放入session
	 */
	@RequestMapping(value = "setCurUser", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Message setCurUser(HttpSession session, @RequestBody User user) {
		return farmerService.setCurUser(session, user);
	}
	
	/**
	 * 
	 * @param session
	 *            接收前台的HttpSession信息
	 * @param user
	 *            接收选择的用户信息
	 * @return 将当前的用户信息放入session
	 */
	@RequestMapping(value = "getCurUser", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public User setCurUser(HttpSession session) {
		User userOld=(User)session.getAttribute("user");
		User userNew=userService.findById(userOld.getId());
		if(userNew!=null)session.setAttribute("user",userNew);
		return userNew;
	}
}