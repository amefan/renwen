package com.afan.user.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.afan.user.pojo.User;
import com.afan.user.service.UserService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import util.JwtUtil;

/**
 * user控制器层
 * @author afan
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private JwtUtil jwtUtil;
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@GetMapping()
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",userService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@GetMapping("/{id}")
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",userService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@PostMapping("/search/{page}/{size}")
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",userService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param user
	 */
	@PostMapping()
	public Result add(@RequestBody User user  ){
		userService.add(user);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param user
	 */
	@PutMapping("/{id}")
	public Result update(@RequestBody User user, @PathVariable String id ){
		user.setId(id);
		userService.update(user);
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public Result delete(@PathVariable String id){
		userService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	/**
	 * @Description: 发送短信
	 * @author: afan
	 * @param: [moblie]
	 * @return: entity.Result
	 */
	@PostMapping("/sendsms/{mobile}")
	public Result sendSms(@PathVariable String mobile){
		System.out.println(mobile);
		userService.sendSms(mobile);
		return new Result(true,StatusCode.OK,"发送成功");
	}

	/**
	 * @Description: 用户登录
	 * @author: afan
	 * @param: [mobile, password]
	 * @return: entity.Result
	 */
	@PostMapping("/login")
	public Result login(@RequestBody Map<String,String> map ){
		User user = userService.checkLogin(map.get("mobile"),map.get("password") );
		if (user==null){
			return new Result(false,StatusCode.ERROR,"用户名或密码错误！");
		}
		String token = jwtUtil.createJWT(user.getId(), user.getNickname(), "user");

		Map map1=new HashMap();
		map1.put("token",token);
		map1.put("name",user.getNickname());//昵称
		map1.put("avatar",user.getAvatar());//头像
		return new Result(true,StatusCode.OK,"登录成功",map1);

	}

	/**
	 * @Description: 用户注册
	 * @author: afan
	 * @param: [user, code]
	 * @return: entity.Result
	 */
	@PostMapping("/register/{code}")
	public Result register(@RequestBody User user,@PathVariable String code){
		//System.out.println(user.getMobile()+user.getNickname()+code);
		userService.register(user,code);
		return new Result(true,StatusCode.OK,"注册成功");
	}
}
