package ez.fileupload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

import java.util.Map;
import java.util.HashMap;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Iterator;
//스프링에서는 한줄로 처리가 된다.
//이 클래스를 이용해야 업로드 처리가 된다.
public class FileUploadRequestWrapper extends HttpServletRequestWrapper{
	//FileUploadRequestWrapper는 HttpServletRequestWrapper를 상속받는다.
	private boolean multipart = false;
	
	private HashMap parameterMap;//일반 폼 데이터를 담기위한 HashMap객체
	private HashMap fileItemMap;//업로드된 파일을 담기위한 HashMap객체
	
	//첫번째 FileUploadRequestWrapper
	public FileUploadRequestWrapper(HttpServletRequest request) throws FileUploadException, UnsupportedEncodingException{
		this(request, -1, -1, null);
	}
	
	//두번째 FileUploadRequestWrapper(업로드된 정보request, threshold숫자값 ,max=업로드 최대크기, 저장될 경로)
	public FileUploadRequestWrapper(HttpServletRequest request, int threshold, int max, String repositoryPath) throws FileUploadException, UnsupportedEncodingException{
		super(request);
		parsing(request, threshold, max, repositoryPath);//threshold : 한계점
	}

	private void parsing(HttpServletRequest request, int threshold, int max, String repositoryPath)throws FileUploadException, UnsupportedEncodingException {
		if(FileUpload.isMultipartContent(request)) {//boolean isMultipartContent가 true이면 업로드
			multipart = true;
			
			parameterMap = new java.util.HashMap();//HashMap객체를 생성
			fileItemMap = new java.util.HashMap();//HashMap객체를 생성
			
			DiskFileUpload diskFileUpload = new DiskFileUpload();//DiskFileUpload객체를 생성
			if(threshold != -1) {//threshold가 -1이 아니면
				diskFileUpload.setSizeThreshold(threshold);//diskFileUpload에 Threshold값을 셋팅해준다.
			}
			diskFileUpload.setSizeMax(max);
			if(repositoryPath != null) {
				diskFileUpload.setRepositoryPath(repositoryPath);//업로드 경롤를 셋팅한다.
			}
			java.util.List list = diskFileUpload.parseRequest(request);//업로드를 처리한다.
			for(int i=0; i<list.size(); i++) {
				FileItem fileItem = (FileItem) list.get(i);//list에 저장된 FileItem객체를 꺼내온다.
				String name = fileItem.getFieldName();//필드의 이름을 꺼내온다.
				
				if(fileItem.isFormField()) {//fileItem이 폼데이터일 경우 아래 구문 실행.
					String value = fileItem.getString("EUC-KR");
					String[] values = (String[])parameterMap.get(name);
					if(values == null) {
						values = new String[] {value};
					}else {
						String[] tempValues = new String[values.length +1];
						System.arraycopy(values, 0, tempValues, 0, 1);
						tempValues[tempValues.length -1]= value;
						values = tempValues;
					}
					parameterMap.put(name, values);
				}else {//fileItem이 일반 데이터일 경우 아래 구문 실행
					fileItemMap.put(name, fileItem);
				}
			}
			addTo();
		}
	}

	public boolean isMultipartContent() {
		return multipart;
	}

	public String getParameter(String name) {
		if (multipart) {
			String[] values = (String[]) parameterMap.get(name);
			if (values == null)
				return null;
			return values[0];
		} else
			return super.getParameter(name);
	}

	public String[] getParameterValues(String name) {
		if (multipart)
			return (String[]) parameterMap.get(name);
		else
			return super.getParameterValues(name);
	}

	public Enumeration getParameterNames() {
		if (multipart) {
			return new Enumeration() {
				Iterator iter = parameterMap.keySet().iterator();

				public boolean hasMoreElements() {
					return iter.hasNext();
				}

				public Object nextElement() {
					return iter.next();
				}
			};
		} else {
			return super.getParameterNames();
		}
	}

	public Map getParameterMap() {
		if (multipart)
			return parameterMap;
		else
			return super.getParameterMap();
	}

	public FileItem getFileItem(String name) {
		if (multipart)
			return (FileItem) fileItemMap.get(name);
		else
			return null;
	}

	/**
	 * 관련된 FileItem 들의 delete() 메소드를 호출한다.
	 */
	public void delete() {//이 delete를 어디서 쓸까?
		if (multipart) {
			Iterator fileItemIter = fileItemMap.values().iterator();
			while (fileItemIter.hasNext()) {
				FileItem fileItem = (FileItem) fileItemIter.next();
				fileItem.delete();
			}
		}
	}

	public void addTo() {
		System.out.println(FileUploadRequestWrapper.class.getName());
		super.setAttribute(FileUploadRequestWrapper.class.getName(), this);
	}

	public static FileUploadRequestWrapper getFrom(HttpServletRequest request) {
		return (FileUploadRequestWrapper) request.getAttribute(FileUploadRequestWrapper.class.getName());
	}

	public static boolean hasWrapper(HttpServletRequest request) {
		if (FileUploadRequestWrapper.getFrom(request) == null) {
			return false;
		} else {
			return true;
		}
	}
}
