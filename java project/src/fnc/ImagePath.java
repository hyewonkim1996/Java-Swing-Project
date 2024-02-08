package fnc;

public interface ImagePath {
	String path = "/image/";
	default String getPath(String name) {
		return path+name+".PNG";
	}
}
