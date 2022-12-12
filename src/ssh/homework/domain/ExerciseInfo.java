package ssh.homework.domain;

public class ExerciseInfo {
	private Exercise exercise;
	private int count;//某一习题出错误的次数
	public Exercise getExercise() {
		return exercise;
	}
	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

	

}
