package com.afan.gathering.controller;


import com.afan.gathering.pojo.UserGath;
import com.afan.gathering.service.UserGathService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * @ClassName: UserGathingController
 * @Description: TODO
 * @Author：afan
 * @Date : 2019/4/14 11:27
 */
@RestController
@CrossOrigin
@RequestMapping("/usergath")
public class UserGathingController {
	@Autowired
	private UserGathService userGathService;

	/**
	 * 查询全部数据
	 * @return
	 */
	@GetMapping()
	public Result findAll(){
		return new Result(true, StatusCode.OK,"查询成功",userGathService.findAll());
	}

	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@GetMapping("/{id}")
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",userGathService.findById(id));
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
		Page<UserGath> pageList = userGathService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<UserGath>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
	 * 根据条件查询
	 * @param searchMap
	 * @return
	 */
	@PostMapping("/search")
	public Result findSearch( @RequestBody Map searchMap){
		return new Result(true,StatusCode.OK,"查询成功",userGathService.findSearch(searchMap));
	}

	/**
	 * 增加
	 * @param
	 */
	@PostMapping()
	public Result add(@RequestBody UserGath userGath  ){
		userGathService.add(userGath);
		return new Result(true,StatusCode.OK,"增加成功");
	}

	/**
	 * 修改
	 * @param UserGath
	 */
	@PutMapping("/{id}")
	public Result update(@RequestBody UserGath UserGath, @PathVariable String id ){
		UserGath.setUserid(id);
		userGathService.update(UserGath);
		return new Result(true,StatusCode.OK,"修改成功");
	}

	/**
	 * 删除
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public Result delete(@PathVariable String id){
		userGathService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

}
