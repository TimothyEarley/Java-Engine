package util;

public class RangeTester {

	public static boolean test(float a, float b, float range) {
		return (a - range >= b && a + range <= b);
	}
	
	public static boolean testAngle(float a, float b, float range) {
		return false;
	}
	
}
