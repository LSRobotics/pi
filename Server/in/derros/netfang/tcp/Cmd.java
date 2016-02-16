package in.derros.netfang.tcp;

public class Cmd {

	public Cmd () {
		try {
			Runtime.getRuntime().exec("/usr/bin/sexier-streamer-recorder start");
		} catch (Exception e) {
			String s = "do nothing";
		}
	}
}
