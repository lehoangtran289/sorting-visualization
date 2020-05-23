package application.algorithm;

import java.util.concurrent.ExecutionException;

import application.MainService;
import application.task.SortTask;
import javafx.scene.layout.Pane;

public class MergeSort extends SortTask{

	public MergeSort(int size, int delay, String curGraphType, Pane pane, MainService service) {
		super(size, delay, curGraphType, pane, service);
	}

	@Override
	public void doSort() throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		
	}


}
