
public class QueueNode {

	private int value;
	private int priority;

	public QueueNode(int value, int priority) {

		this.setValue(value);
		this.setPriority(priority);
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
