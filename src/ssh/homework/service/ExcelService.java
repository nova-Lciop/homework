package ssh.homework.service;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import ssh.homework.domain.Exercise;
import ssh.homework.domain.StudentScore;

public interface ExcelService {
	public void export(List<Exercise> list, ServletOutputStream out) throws Exception;
	public void export(Map<Integer,StudentScore> map, ServletOutputStream out,String workbookInfo) throws Exception;

}
