package com.jisen.bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jisen.bos.domain.Decidedzone;
import com.jisen.bos.domain.Region;
import com.jisen.bos.domain.Subarea;
import com.jisen.bos.service.SubareaService;
import com.jisen.bos.utils.FileUtils;
import com.jisen.bos.web.action.base.BaseAction;

/**
 * 分区管理,
 * @author Administrator
 *
 */
@SuppressWarnings("all")
@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea> {
	@Resource
	private SubareaService subareaService;
	
	/**
	 * 添加分区
	 */
	public String add(){
		subareaService.save(model);
		return "list";
	}
	
	/**
	 * 分页查询的死循环问题
	 * 在执行pageQuery的时候,封装的pageBean有两个查询,一个是totalcount,查询总数据量,另一个是查询该页的数据List,
	 * 当查询分页的时候,关联的数据查询有private Decidedzone decidedzone;private Region region;//分区对应的区域
	 * 也就是说关联的外键也要查询,而外键查询存在一个懒加载的问题,当执行完pageQuery的时候region数据还没有查询出来,此时region的值是一个代理对象
	 * 而在region的值还是代理对象的时候执行转json的语句就会出现死循环的情况
	 */
	public String pageQuery(){
		//动态添加过滤条件,过滤条件封装到离线查询里面
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		//动态添加过滤条件
		String addresskey = model.getAddresskey();
		if(StringUtils.isNotBlank(addresskey)){
			//不为空则添加一个模糊查询条件,根据地质关键字创建模糊查询
			dc.add(Restrictions.like("addresskey", "%"+addresskey+"%"));
		}
		
		Region region = model.getRegion();
		if(region!=null){
			String province = region.getProvince();
			String city = region.getCity();
			String district = region.getDistrict();
			
			//参数一分区对象中关联区域对象属性名称,参数二,别名可以任意
			dc.createAlias("region", "r");
			if(StringUtils.isNotBlank(province)){
				//根据省份模糊查询,涉及关联查询,多表查询,使用别名实现
				dc.add(Restrictions.like("r.province", "%"+province+"%"));
			}
			if(StringUtils.isNotBlank(city)){
				//根据省份模糊查询,涉及关联查询,多表查询,使用别名实现
				dc.add(Restrictions.like("r.city", "%"+city+"%"));
			}
			if(StringUtils.isNotBlank(district)){
				//根据省份模糊查询,涉及关联查询,多表查询,使用别名实现
				dc.add(Restrictions.like("r.district", "%"+district+"%"));
			}
		}
		subareaService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"currentPage","detachedCriteria","pageSize","decidedzone","subareas" });
		return NONE;
		
	}
	
	/**
	 * 点击数据到导出,从数据库里查询并返回所有数据,再利用输出流写到Excel
	 * @return
	 * @throws IOException 
	 */
	public String exportXls() throws IOException {
		//第一步,查询所有分区数据
		List<Subarea> list = subareaService.findAll();
		//第二步,使用POI将数据写到Excel文件中
		//在内存中创建一个Excel文件,该Excel文件是个空文件,没有sheet123标签页
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建一个标签页
		HSSFSheet sheet = workbook.createSheet("分区数据");
		//创建标题行sheet
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("分区编号");
		headRow.createCell(1).setCellValue("开始编号");
		headRow.createCell(2).setCellValue("结束编号");
		headRow.createCell(3).setCellValue("位置信息");
		headRow.createCell(4).setCellValue("省市区");
		
		for(Subarea subarea : list){
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum()+1);
			dataRow.createCell(0).setCellValue(subarea.getId());
			dataRow.createCell(1).setCellValue(subarea.getStartnum());
			dataRow.createCell(2).setCellValue(subarea.getEndnum());
			dataRow.createCell(3).setCellValue(subarea.getPosition());
			dataRow.createCell(4).setCellValue(subarea.getRegion().getName());
		}
		//第三步:使用输出流进行文件下载,一个流两个头
		String filename = "分区数据.xls";
		String contentType = ServletActionContext.getServletContext().getMimeType(filename);
		ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
		ServletActionContext.getResponse().setContentType(contentType);
		//获取客户端浏览器类型
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");
		
		String filenameencode = FileUtils.encodeDownloadFilename(filename, agent);
		
		ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename="+filenameencode);
		
		workbook.write(out);
		
		return NONE;
	}
	
	/**
	 * 所有未关联到定取的的分区
	 */
	public String listajax(){
		List<Subarea> list = subareaService.findListNotAssociation();
		this.java2Json(list, new String[]{"decidedzone","region"});
		
		return NONE;
	}

	/**
	 * 根据定区id查询关联分区
	 */
	//属性驱动接收id
	private String decidedzoneId;
	public void setDecidedzoneId(String decidedzoneId) {
		this.decidedzoneId = decidedzoneId;
	}

	public String findListByDecidedzoneId() {
		List<Subarea> list = subareaService.findListByDecidedzoneId(decidedzoneId);
		this.java2Json(list, new String[]{"decidedzone","subareas"});
		return NONE;

	}
}
