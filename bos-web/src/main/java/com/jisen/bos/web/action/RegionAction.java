package com.jisen.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jisen.bos.domain.Region;
import com.jisen.bos.service.RegionService;
import com.jisen.bos.utils.PageBean;
import com.jisen.bos.utils.PinYin4jUtils;
import com.jisen.bos.web.action.base.BaseAction;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 区域管理
 * @author Administrator
 */
@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region> {
	@Autowired
	private RegionService regionService;
	
	
	//属性驱动接收上传文件
	private File regionFile;
	public void setRegionFile(File regionFile) {
		this.regionFile = regionFile;
	}
	
	/**
	 * 区域导入
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public String importXls() throws FileNotFoundException, IOException{
		
		List<Region> regionList = new ArrayList<Region>();
		//使用poi解析Excel文件
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(regionFile));
		//获得标签页,即sheet1,sheet2
		HSSFSheet hssfSheet = workbook.getSheetAt(0);
		//循环遍历每一行
		for(Row row : hssfSheet){
			int rowNum = row.getRowNum();
			if(rowNum==0){
				continue;
			}
			String id = row.getCell(0).getStringCellValue();
			String province = row.getCell(1).getStringCellValue();
			String city = row.getCell(2).getStringCellValue();
			String district = row.getCell(3).getStringCellValue();
			String postcode = row.getCell(4).getStringCellValue();
			//包装一个区域对象
			Region region = new Region(id, province, city, district, postcode, null, null, null);
			
			province = province.substring(0,province.length()-1);
			city = city.substring(0,city.length()-1);
			district = district.substring(0,district.length()-1);
			
			String info = province+city+district;
			
			String[] headByString = PinYin4jUtils.getHeadByString(info);
			//将数组连城串
			String shortcode = StringUtils.join(headByString);
			//城市编码
			String citycode = PinYin4jUtils.hanziToPinyin(city,"");
			
			region.setShortcode(shortcode);
			region.setCitycode(citycode);
			regionList.add(region);
		}
		regionService.saveBatch(regionList);
		System.out.println(regionList);
		return NONE;
	}
	
	
	/**
	 * 区域数据分页查询与显示
	 */
	
	//pageBean的封装与查询
	public String pageQuery() throws IOException{
		regionService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[] { "currentPage", "detachedCriteria","pageSize","subareas" });
		return NONE;
	}
	
	
	/**
	 * 查询所有区域,写回json数据
	 * @return
	 */
	
	private String q;
	public void setQ(String q) {
		this.q = q;
	}

	public String listajax(){
		List<Region> list =null; //regionService.findAll();
		if(StringUtils.isNotBlank(q)){
			list = regionService.findListByQ(q);
		}else{
			list = regionService.findAll();
		}
		this.java2Json(list, new String[] { "subareas"});
		return NONE;
		
	}
}
