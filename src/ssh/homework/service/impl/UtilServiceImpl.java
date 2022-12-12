package ssh.homework.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ssh.homework.domain.StudentWorkbook;
import ssh.homework.service.UtilService;
@Service("utilService")
public class UtilServiceImpl implements UtilService {
	//对学生作业按习题类别排序
	@Override
	public void sortStudentWorkbook(List<StudentWorkbook> list) {
		int n=list.size();
		for(int i =0;i <n- 1;i++)
        {
			
            for(int j = i+1;j < n;j++)// j开始等于1，
            {
            	StudentWorkbook stud2=list.get(j);
            	StudentWorkbook stud1=list.get(i);
    			int k1=Integer.parseInt(stud1.getExercise().getKind()) ;//得到习题类型
            	int k2=Integer.parseInt(stud2.getExercise().getKind());
                if(k1>k2)
                {
                    list.set(j,stud1);
                    list.set(i,stud2);
                }
            }
        }
	

	}

}
