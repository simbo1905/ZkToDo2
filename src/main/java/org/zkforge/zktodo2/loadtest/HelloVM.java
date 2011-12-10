package org.zkforge.zktodo2.loadtest;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

public class HelloVM {

	private String message= "";
	private boolean visible = false;

	public boolean isVisible() {
		return visible;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Command @NotifyChange("message")
	public void pause() throws InterruptedException {
		Thread.sleep(3*1000);
		setMessage("I'm back!");
	}
	
	@Command @NotifyChange("visible")
	public void toggle() {
		this.visible  = !this.visible;
	}
	
	int count = 0;
	
	@Command @NotifyChange("message")
	public void append() {
		setMessage(getMessage()+" clickMe "+(count++));
	}
	
}
