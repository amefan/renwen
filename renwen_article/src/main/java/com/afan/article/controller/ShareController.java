package com.afan.article.controller;

import com.afan.article.pojo.Share;
import com.afan.article.service.ShareService;
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
 * article控制器层
 * @author afan
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/share")
public class ShareController {

	@Autowired
	private ShareService shareService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@GetMapping()
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",shareService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@GetMapping("/{id}")
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",shareService.findById(id));
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
		Page<Share> pageList = shareService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Share>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",shareService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param article
	 */
	@PostMapping()
	public Result add(@RequestBody Share article  ){
		shareService.add(article);
		return new Result(true,StatusCode.OK,"发布成功");
	}
	
	/**
	 * 修改
	 * @param article
	 */
	@PutMapping("/{id}")
	public Result update(@RequestBody Share article, @PathVariable String id ){
		article.setId(id);
		shareService.update(article);
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public Result delete(@PathVariable String id){
		shareService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	/**
	 * @Description: 上传封面
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
			String newName = "logo_"+System.currentTimeMillis()+subfix;
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
