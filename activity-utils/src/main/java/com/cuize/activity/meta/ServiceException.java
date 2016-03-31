package com.cuize.activity.meta;

public class ServiceException extends Exception {

	private static final long serialVersionUID = -6879298763723247455L;
	private String userDefinedReason;

	public ServiceException(String userDefinedReason, Throwable cause) {
		super(cause);
		this.userDefinedReason = userDefinedReason;
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}
	
	public Boolean hasReason(){
		return (this.userDefinedReason!=null && this.userDefinedReason.length()>0);
	}
	
	public String getReason(){
		return this.userDefinedReason;
	}
	
	// 这个是导致异常性能不佳的地方，所以自定义异常要覆写这个方法<br/>
	/**
	 * 除非throw该异常时，把异常堆栈给传递出来了，否则外面catch，填充堆栈信息就到此为止（一般是业务约束校验失败场景用到，比如对方没异常，但是返回的code不对，我们业务要block的）
	 */
//	@Override
//	public Throwable fillInStackTrace() {
//		return this;
//	}
}