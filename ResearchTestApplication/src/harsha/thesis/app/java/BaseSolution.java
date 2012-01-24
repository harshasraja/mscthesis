package harsha.thesis.app.java;

public abstract class BaseSolution {

	protected String [] args;
	
	public abstract void run() throws Exception;

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}
	
	
}
