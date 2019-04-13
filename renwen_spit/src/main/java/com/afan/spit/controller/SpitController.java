package com.afan.spit.controller;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.afan.spit.pojo.Spit;
import com.afan.spit.service.SpitService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
/**
 * spit控制器层
 * @author afan
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

	@Autowired
	private SpitService spitService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@GetMapping()
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",spitService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@GetMapping("/{id}")
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",spitService.findById(id));
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
		Page<Spit> pageList = spitService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Spit>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",spitService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param spit
	 */
	@PostMapping()
	public Result add(@RequestBody Spit spit  ){
		spitService.add(spit);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param spit
	 */
	@PutMapping("/{id}")
	public Result update(@RequestBody Spit spit, @PathVariable String id ){
		spit.setId(id);
		spitService.update(spit);
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public Result delete(@PathVariable String id){
		spitService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	
}
