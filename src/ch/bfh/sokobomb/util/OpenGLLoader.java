package ch.bfh.sokobomb.util;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Dynamically initializes lwjgl
 *
 * @author Christoph Bruderer
 *
 */
final public class OpenGLLoader {

	/**
	 * Load native libraries
	 *
	 * Determines the os dependant path
	 *
	 * @throws Exception
	 */
	public static void loadNativeLibraries() throws Exception {
		if (System.getProperty("os.name").equals("Mac OS X")) {
			addLibraryPath("natives/macosx");
		}
		else if (System.getProperty("os.name").equals("Linux")) {
			addLibraryPath("natives/linux");
		}
		else {
			addLibraryPath("native/windows");

			if (System.getProperty("os.arch").equals("amd64") || System.getProperty("os.arch").equals("x86_64")) {
				System.loadLibrary("OpenAL64");	
			}
			else {
				System.loadLibrary("OpenAL32");
			}
		}
	}

	/**
	 * Add a library path
	 *
	 * Only add if it isn't yet in the list
	 *
	 * @param s The path to be added
	 * @throws Exception
	 */
	private static void addLibraryPath(String s) throws Exception {
		final Field usr_paths_field = ClassLoader.class.getDeclaredField("usr_paths");
		usr_paths_field.setAccessible(true);

		final String[] paths = (String[]) usr_paths_field.get(null);

		for (String path: paths){
			if (path.equals(s)){
				return;
			}
		}

		final String[] new_paths = Arrays.copyOf(paths, paths.length + 1);
		new_paths[paths.length - 1] = s;
		usr_paths_field.set(null, new_paths);
	}
}