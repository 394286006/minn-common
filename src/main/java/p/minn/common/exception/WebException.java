package p.minn.common.exception;

/**
 * 
 * @author minn
 * @QQ:3942986006
 */
public class WebException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String selfdescript;
	
	private String causeClz="no detail descript!";

	public WebException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	public WebException(String message,String selfdescript) {
		super(message);
		// TODO Auto-generated constructor stub
		this.selfdescript=selfdescript;
	}

	public WebException(String message, Throwable cause,String selfdescript) {
		super(message,cause);
		// TODO Auto-generated constructor stub
		this.selfdescript=selfdescript;
	}
	
	public WebException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public WebException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public String getSelfdescript() {
		if(selfdescript==null){
			selfdescript=this.getMessage();
		}
		return selfdescript;
	}
	
	public String getCauseMessage(){
		
		if(super.getCause()!=null){
			causeClz=super.getCause().getMessage();
		}
		return causeClz;
	}
	

}
