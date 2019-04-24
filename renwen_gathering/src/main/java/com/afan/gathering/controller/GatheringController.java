package com.afan.gathering.controller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import com.afan.gathering.pojo.UserGath;
import com.afan.gathering.service.UserGathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.afan.gathering.pojo.Gathering;
import com.afan.gathering.service.GatheringService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.web.multipart.MultipartFile;

/**
 * gathering控制器层
 * @author afan
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/gathering")
public class GatheringController {

	@Autowired
	private GatheringService gatheringService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@GetMapping()
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",gatheringService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@GetMapping("/{id}")
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",gatheringService.findById(id));
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
		Page<Gathering> pageList = gatheringService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Gathering>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",gatheringService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param gathering
	 */
	@PostMapping()
	public Result add(@RequestBody Gathering gathering  ){
		gatheringService.add(gathering);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param gathering
	 */
	@PutMapping("/{id}")
	public Result update(@RequestBody Gathering gathering, @PathVariable String id ){
		gathering.setId(id);
		gatheringService.update(gathering);
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public Result delete(@PathVariable String id){
		gatheringService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	/**
	 * @Description: 上传logo
	 * @author: afan
	 * @param:
	 * @return:
	 */
	@PostMapping("/uploadlogo")
	public Result upload(MultipartFile file){
		String UPLOAD_FOLDER = "E:/upload/logo/";

		if (file.isEmpty()) {
			return new Result(false,StatusCode.ERROR,"上转文件为空");
		}

		try {
			String subfix = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("" +
					"."));
			String newName = "gathering_"+System.currentTimeMillis()+subfix;
			//System.out.println(newName);
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOAD_FOLDER + newName);
			//如果没有files文件夹，则创建
			if (!Files.isWritable(path)) {
				Files.createDirectories(Paths.get(UPLOAD_FOLDER));
			}
			//文件写入指定路径
			Files.write(path, bytes);
			String url = "http://localhost/logo/"+newName;
			return new Result(true,StatusCode.OK,"成功",url);
		} catch (IOException e) {
			e.printStackTrace();
			return new Result(false, StatusCode.ERROR, "后端异常");
		}
	}


}
