package application.task;

import java.util.concurrent.ExecutionException;

import application.service.MainService;
import javafx.concurrent.Task;
import javafx.scene.layout.Pane;

public abstract class SortTask extends Task<Void> {
	protected int size;
	protected String curGraphType;
	protected Pane pane;
	protected MainService service = new MainService();
	protected int delay;

	public SortTask(int size, int delay, String curGraphType, Pane pane, MainService service) {
		this.size = size;
		this.delay = delay;
		this.curGraphType = curGraphType;
		this.pane = pane;
		this.service = service;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	@Override
	protected Void call() throws Exception {
		try {
			doSort();
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return null;
	}

	public abstract void doSort() throws InterruptedException, ExecutionException;

	public void delay() throws InterruptedException {
		Thread.sleep(delay);
	}

}
