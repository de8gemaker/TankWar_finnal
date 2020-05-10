package tank;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class PropertyMgr {
	private static Properties props = new Properties();

	public static Properties getProps() { return props; }

	public static void setProps(Properties props) { PropertyMgr.props = props; }

	static {
		try {
			props.load(Objects.requireNonNull(PropertyMgr.class.getClassLoader().getResourceAsStream("config")));
//			System.out.println(props.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Object get(String key) {
		if(props == null) return null;
		return props.get(key);
	}
	
	//int getInt(key)
	//getString(key)
	public static void main(String[] args) {
		System.out.println(PropertyMgr.get("initTankCount"));
		
	}
}
