public class Simple {
	private int state = 0;

	public int getState() {
		return state;
	}

	public void nextChar(int c) {
		if(state == 0) {
			if(c == '[') state = 1;
		} else if(state == 1) {
			if(c == '(') state = 2;
		} else if(state == 2) {
			if(c == '{') state = 3;
		} else if(state == 3) {
			if(c == '~') state = 4;
		} else if(state == 4) {
			if(c == 'a') state = 5;
		} else if(state == 5) {
			if(c == 'x') state = 6;
		} else if(state == 6) {
			if(c == '}') state = 7;
		} else if(state == 7) {
			if(c == ')') state = 8;
		} else if(state == 8) {
			if(c == ']') state = 9;
		}
	}
}
