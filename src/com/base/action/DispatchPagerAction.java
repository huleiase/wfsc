package com.base.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.springframework.beans.factory.annotation.Autowired;

import com.base.log.LogUtil;
import com.wfsc.actions.common.CupidBaseAction;
import com.wfsc.common.bo.bym.Attachment;
import com.wfsc.common.bo.system.SystemLog;
import com.wfsc.services.bym.service.ICommonService;
import com.wfsc.services.bym.service.IQuoteService;
import com.wfsc.services.system.ISystemLogService;

public abstract class DispatchPagerAction<T> extends CupidBaseAction<T> {

	private int pageNo;

	private int pageSize = 15;

	private int start;

	private int totalSize;
	
	private File attachment;

	private String attachmentContentType;

	private String attachmentFileName;
	
	protected Logger log = LogUtil.getLogger(LogUtil.SERVER);
	
	@Autowired
	protected ISystemLogService systemLogService;
	
	@Resource(name = "commonService")
	protected ICommonService commonService;
	
	public int getPageNo() {
		return this.pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize(HttpServletRequest request) {
		String size = request.getParameter("pageSize");
		if(size != null && !"".equals(size)){
			return Integer.valueOf(size);
		}
		return this.pageSize;
	}

	public void setPageSize(int pageSize, HttpServletRequest request) {
		//如果用户设置过每页显示条数，则以用户设置的为主
		if(request.getSession().getAttribute("pageSize") != null){
			this.pageSize = Integer.valueOf(request.getSession().getAttribute("pageSize").toString());
		}else{
			this.pageSize = pageSize;
		}
		request.setAttribute("pageSize", Integer.valueOf(pageSize));
	}

	public void prepare() throws Exception {
		this.generatePagerParam(request, totalSize);
	}

	protected void generatePagerParam(HttpServletRequest request, int totalSize) {
		setTotalSize(totalSize);
		String pageNoStr = (request.getParameter("pageNo") == null) ? "1" : request.getParameter("pageNo");
		System.out.println("in pagerAction--------------------------------------------传入的当前页码：" + pageNoStr);
		if (this.pageSize == 0) {
			this.pageSize = 10;
		} else if (request.getAttribute("pageSize") != null && NumberUtils.isDigits(request.getAttribute("pageSize").toString())) {
			// 有数据
			this.pageSize = (Integer) request.getAttribute("pageSize");
//			request.setAttribute("pageSize", this.pageSize);
			request.getSession().setAttribute("pageSize", this.pageSize);
		}else if(StringUtils.isNotEmpty(request.getParameter("pageSize")) ){//用户指定的条数，放到session里
			// 有数据
			this.pageSize = Integer.valueOf(request.getParameter("pageSize"));
			request.getSession().setAttribute("pageSize", this.pageSize);
		}
		setPageSize(this.pageSize, request);
		int totalPage = totalSize / this.pageSize + ((totalSize % this.pageSize == 0) ? 0 : 1);
		if (Integer.parseInt(pageNoStr) > totalPage)
			setPageNo(totalPage);
		else {
			setPageNo(Integer.parseInt(pageNoStr));
		}
		int start = (Integer.valueOf(getPageNo()).intValue() - 1) * Integer.valueOf(this.pageSize).intValue();
		setStart(start);
		request.setAttribute("start", Integer.valueOf(start));
		request.setAttribute("pageNo", Integer.valueOf(Integer.parseInt(pageNoStr)));
		request.setAttribute("totalSize", Integer.valueOf(totalSize));
	}
	
	/**
	 * 获得war工程绝对路径 
	 * @return
	 */
	public String getAbsoluteRootPath(){
		String systemPath = request.getSession().getServletContext().getRealPath("/");
		return systemPath;
	}

	public int getStart(HttpServletRequest request) {
		int startindex = StringUtils.isNotBlank(request.getParameter("start")) ? Integer.parseInt(request.getParameter("start")) : 0;
		start = startindex;
		return ((this.start < 0) ? 0 : this.start);
	}

	public void setStart(int start) {
		this.start = start;
	}


	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	
	public void setTopMenu(){
		String topmenu = request.getParameter("topmenu");
		if(StringUtils.isEmpty(topmenu)){
			topmenu = session.get("topmenu")==null?"":session.get("topmenu").toString();
		}
		String submenu = request.getParameter("submenu");
		if(StringUtils.isEmpty(submenu)){
			submenu = session.get("submenu")==null?"":session.get("submenu").toString();
		}
		session.put("submenu", submenu);
		session.put("topmenu", topmenu);
	}
	
	public String getExcelModel(String fileName){
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=model.xls");
		InputStream  input = null;
		OutputStream out = null;
		try {
			String rootPath = this.getAbsoluteRootPath();
			String filePath = rootPath+"/model/"+fileName;
			File file = new File(filePath);
			input = new FileInputStream(file);
			out = response.getOutputStream();
			byte[] b = new byte[1024];
			int read = 0;
			while ((read = input.read(b)) > 0) {
				out.write(b, 0, read);
			}
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != input){
				try {
					input.close();
				} catch (IOException e){
					e.printStackTrace();
				}
			}
			if(null != out){
				try {
					out.close();
				} catch (IOException e){
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public String exportData(String fileName,List<T> list){
		/*String rootPath = this.getAbsoluteRootPath();
		String filePath = rootPath+"/model/"+fileName;
		OutputStream out = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			File file = new File(filePath);
			XLSTransformer transformer = new XLSTransformer();
			Workbook workbook ;
			map.put("records", list);
			workbook = transformer.transformXLS(new FileInputStream(file), map);
			out= response.getOutputStream();
			workbook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(out!=null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}	
		} */
		return null;
	}
	
	public String importFile(String linkCode){
		 String filePath = this.getAbsoluteRootPath()+"attachment/";
		 File pathFile = new File(filePath);
		 if(!pathFile.exists()){
			 pathFile.mkdir();
		 }
		 String fileName = this.getAttachmentFileName();
		 String sysFileName = System.currentTimeMillis()+fileName.substring(fileName.indexOf("."));
		 FileInputStream fis = null;
		 FileOutputStream fos = null;
		 String successMsg = "文件上传成功";
		 try{
			 File file = new File(filePath+sysFileName);
			 if(!file.exists()){
				 file.createNewFile();
			 }
			 fis = new FileInputStream(this.getAttachment());
			 fos = new FileOutputStream(file);
			 byte[] b = new byte[1024];
			 int length = 0;
			 while((length=fis.read(b))>0){
				 fos.write(b, 0, length);
			 }
			 Attachment attr = commonService.getOnlyAttachmentByKey(linkCode);
			 if(attr==null){
				 attr = new Attachment();
			 }
			 attr.setAttachName(fileName);
			 attr.setAttachPath("attachment/"+sysFileName);
			 attr.setDepositedName(sysFileName);
			 attr.setLinkCode(linkCode);
			 attr.setUploadTime(new Date());
			 attr.setUploadUser(this.getCurrentAdminUser().getUsername());
			 commonService.saveOrUpdateEntity(attr);
		 }catch (Exception e) {
			 successMsg = "文件上传失败";
			 e.printStackTrace();
		}finally{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fos!=null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		request.setAttribute("successMsg", successMsg);
		return "toImport";
		
	}
	
	public String downloadFile(String linkCode){
		Attachment attr = commonService.getOnlyAttachmentByKey(linkCode);
		String fileName = "download";
		String filePath = this.getAbsoluteRootPath();
		if(attr!=null){
			 fileName = attr.getAttachName();
			 filePath += attr.getAttachPath();
			 OutputStream outp = null;
				FileInputStream in = null;
				try {
					String agent = request.getHeader("User-Agent");
					request.setCharacterEncoding("UTF-8");
					response.setCharacterEncoding("UTF-8");
					response.setHeader("Cache-Control", "no-cache");
					response.setContentType("application/x-msdownload");
					if (agent.indexOf("MSIE") != -1) {
						fileName = encodingFileName(fileName);
						response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
						response.setHeader("Content-Transfer-Encoding", "binary");
						response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
						response.setHeader("Pragma", "public");
					} else {
						fileName = removeBlank(fileName);
						response.setHeader("Content-Disposition", "attachment; filename="
								+ new String(fileName.getBytes("UTF-8"), "iso8859-1"));
					}
					outp = response.getOutputStream();
					in = new FileInputStream(filePath);
					byte[] b = new byte[1024];
					int i = 0;
					while ((i = in.read(b)) > 0) {
						outp.write(b, 0, i);
					}
					outp.flush();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (in != null) {
						try {
							in.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						in = null;
					}
					if (outp != null) {
						try {
							outp.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						outp = null;
					}
				}
		 }
		
		return null;
	}
	public String encodingFileName(String fileName) {
		String returnFileName = "";
		try {
			returnFileName = URLEncoder.encode(fileName, "UTF-8");
			returnFileName = StringUtils.replace(returnFileName, "+", "%20");
			if (returnFileName.length() > 150) {
				returnFileName = new String(fileName.getBytes("GB2312"), "ISO8859-1");
				returnFileName = StringUtils.replace(returnFileName, " ", "%20");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return returnFileName;
	}
	public String removeBlank(String fileName) {
		StringBuilder sb = new StringBuilder();
		char c = ' ';
		for (int i = 0; i < fileName.length(); i++) {
			char ch = fileName.charAt(i);
			if (ch != c) {
				sb.append(ch);
			}
		}
		return sb.toString();
	}
	 //创建单元格
	 public void createCell(HSSFRow row,int num) {
	     for(int i=0;i<=num;i++){
	      HSSFCell cell=null;
	      if(row.getCell(i)!=null){
	       cell=row.getCell(i);
	      }else{
	       cell=row.createCell(i);
	      }
	     }
	 }
	 
	 public void saveSystemLog(String tiLevel,String vcContent){
		SystemLog systemLog = new SystemLog(tiLevel, this.getCurrentAdminUser().getUsername(), vcContent);
		systemLogService.saveSystemLog(systemLog);
	 }
	public File getAttachment() {
		return attachment;
	}

	public void setAttachment(File attachment) {
		this.attachment = attachment;
	}

	public String getAttachmentContentType() {
		return attachmentContentType;
	}

	public void setAttachmentContentType(String attachmentContentType) {
		this.attachmentContentType = attachmentContentType;
	}

	public String getAttachmentFileName() {
		return attachmentFileName;
	}

	public void setAttachmentFileName(String attachmentFileName) {
		this.attachmentFileName = attachmentFileName;
	}
}