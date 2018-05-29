package cn.jxufe.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jxufe.bean.EasyUIData;
import cn.jxufe.bean.EasyUIDataPageRequest;
import cn.jxufe.bean.Message;
import cn.jxufe.entity.User;
import cn.jxufe.service.UserService;

/**
 * MVC框架里的controller控制层类
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	/**
	 * UserService接口对象
	 * 
	 * @see cn.jxufe.service.UserService
	 */
	private UserService userService;
	@RequestMapping(value = "grid")
	/**
	 * 
	 * @return 返回字符串到RequestMapping里面
	 */
	public String grid() {
		return "user/grid";
	}
	
	@RequestMapping(value = "userSelect")
	public String selectGrid() {
		return "user/userSelect";
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
		return userService.setCurUser(session, user);
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
	
	/**
	 * @param pageRequest
	 *            接收前台传来的pageRequest对象
	 * @see EasyUIDataPageRequest
	 * @param nickname
	 *            用于接受前台传来的名字查询字符
	 * @param model
	 *            MVC框架里Model对象
	 * @return EasyUIData的一个对象结果以JSON的格式返回
	 */
	@RequestMapping(value = "gridData", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public EasyUIData<?> gridData(EasyUIDataPageRequest pageRequest, @RequestParam(defaultValue = "") String nickname,
			Model model) {
		List<Sort.Order> orders = new ArrayList<Sort.Order>();
		if (pageRequest.getOrder().equals("asc")) {
			orders.add(new Sort.Order(Direction.ASC, pageRequest.getSort()));
		} else {
			orders.add(new Sort.Order(Direction.DESC, pageRequest.getSort()));
		}
		Pageable pageable = new PageRequest(pageRequest.getPage() - 1, pageRequest.getRows(), new Sort(orders));
		if (nickname.equals("")) {
			return userService.findALL(pageable);
		} else {
			return userService.findByNicknameLike(nickname, pageable);
		}
	}
	/**
	 * @param pageRequest
	 *            接收前台传来的pageRequest对象
	 * @see EasyUIDataPageRequest
	 * @param nickname
	 *            用于接受前台传来的名字查询字符
	 * @param model
	 *            MVC框架里Model对象
	 * @return EasyUIData的一个对象结果以JSON的格式返回
	 */
	@RequestMapping(value = "gridDataALL", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Iterable<User> gridData(Model model) {
		return userService.findALL();
	}

	/**
	 * 
	 * @param user
	 *            接收选中或创建的一个User对象
	 * @see cn.jxufe.entity.User
	 * @param model
	 *            MVC框架里Model对象
	 * @return 执行结果的Message对象以JSON的格式返回
	 */
	@RequestMapping(value = "save", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Message save(User user, Model model) {
		return userService.save(user);
	}

	/**
	 * @param user
	 *            接收前台选中的一个User对象
	 * @see cn.jxufe.entity.User
	 * @param model
	 *            MVC框架里Model对象
	 * @return 执行结果的Message对象以JSON的格式返回
	 */
	@RequestMapping(value = "delete", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Message delete(User user, Model model) {
		return userService.delete(user);
	}

}