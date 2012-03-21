package harsha.thesis.app.java;


import harsha.api.connection.ConnectionDefinition;


public abstract class BaseSolution {

	protected String [] args;
	
	protected ConnectionDefinition conDef = null;
	
	public abstract void run() throws Exception;

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	public void setConnectionDefinition(ConnectionDefinition conDef){
		this.conDef = conDef;
	}
	
	
}
