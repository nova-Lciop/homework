package ssh.homework.service;

import java.util.List;

import ssh.homework.domain.StudentWorkbook;

public interface SimilarityService {
	public double doSimilarity(String s1,String s2);

	public void studentWorkbookSimilarity(List<StudentWorkbook> studentWorkbooks,float checkRate);
}
