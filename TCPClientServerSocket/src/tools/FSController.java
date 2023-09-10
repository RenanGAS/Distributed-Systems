package src.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FSController {
	final String ROOT_DIR = "home";
	final String USER = "aluno";
	final String PASSWORD = "d9e6604cd1df1acfc0c39f200de531d6eedb60f5a7dc0fb1aad5ce8d316a"
			+ "f83f3f974027de37991a2233094d0c8b46aaa13a600bab89"
			+ "678c73c344853883acd0"; // mudar123
	boolean userLoggedIn = false;
	volatile Path currentPath = Paths.get(ROOT_DIR);
	

	String handleCommand(String command) throws IOException {
		List<String> parsedCommand = new ArrayList<>();
		parsedCommand = parseCommand(command);

		String commandToken = parsedCommand.get(0);

		String response = "";

		if ("CONNECT".equals(commandToken)) {
			if (userAuthentication(parsedCommand)) {
				response = "SUCCESS";
			} else {
				throw new IOException("ERROR: Invalid credentials");
			}
		} else if ("PWD".equals(commandToken)) {
			response = getPWD();
		} else if ("CHDIR".equals(commandToken)) {
			changeDir(parsedCommand);
			response = "SUCCESS";
		} else if ("GETFILES".equals(commandToken)) {
			response = getFiles();
		} else if ("GETDIRS".equals(commandToken)) {
			response = getDirs();
		} else {
			throw new IOException("ERROR: Unknown command");
		}

		return response;
	} // handleCommand
	
	List<String> parseCommand(String buffer) throws IOException {
		Pattern pattern = Pattern.compile("($|\\s+)", Pattern.CASE_INSENSITIVE);

		return parse(buffer, pattern);
	} //parseCommand

	List<String> parse(String input, Pattern pattern) throws IOException {
		Matcher matcher = pattern.matcher(input);

		if (matcher.find()) {
			String[] sequence = pattern.split(input);

			if (sequence.length > 0) {
				List<String> listTokens = Arrays.asList(sequence);
				return listTokens;
			}
		}

		throw new IOException("ERROR: Invalid command");
	} // parse

	boolean userAuthentication(List<String> parsedCommand) throws IOException {
		if (parsedCommand.size() == 3) {
			String userName = parsedCommand.get(1);
			String password = parsedCommand.get(2);
			
			if (USER.equals(userName) && PASSWORD.equals(password)) {
				userLoggedIn = true;
				currentDirTo(userName);
				return true;
			}
			
			return false;
		}

		throw new IOException("ERROR: Authentication failed");
	} //userAuthentication
	
	void currentDirTo(String dir) throws InvalidPathException {
		if (USER.equals(dir) && !userLoggedIn) {
			throw new InvalidPathException("/home/aluno", "User not authenticated");
		}
		
		currentPath = currentPath.resolve(dir);
	} //currentDirTo

	String getPWD() {
		Path fullPath = currentPath.toAbsolutePath();

		return fullPath.toString();
	} // getPWD

	String getFiles() throws IOException {
		String currentPath = getPWD();
		Path source = Paths.get(currentPath);
		
		List<String> listFiles = new ArrayList<>();
		
		Files.walkFileTree(source, EnumSet.of(FileVisitOption.FOLLOW_LINKS), 1,
				new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
					throws IOException
			{
				if (!attrs.isDirectory()) {
					listFiles.add(file.getFileName().toString());
				}
				
				return FileVisitResult.CONTINUE;
			}
		});
		
		if (listFiles.size() > 0) {
			String response = """
				    Number of files: %d
				    List of files: %s
				    """.formatted(listFiles.size(), listFiles.toString());
			return response;
		} else {
			throw new IOException("There're no files here");
		}
	} //getFiles
	
	String getDirs() throws IOException {
		List<String> listDirs = new ArrayList<>();
		
		Files.walkFileTree(currentPath, EnumSet.of(FileVisitOption.FOLLOW_LINKS), 2,
				new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
					throws IOException
			{
				String currentDir = currentPath.getFileName().toString();
				String visitedDir = dir.getFileName().toString();
				
				if (!currentDir.equals(visitedDir)) {
					listDirs.add(visitedDir);
				}
				
				return FileVisitResult.CONTINUE;
			}
		});
		
		if (listDirs.size() > 0) {
			String response = """
				    Number of directories: %d
				    List of directories: %s
				    """.formatted(listDirs.size(), listDirs.toString());
			return response;
		} else {
			throw new IOException("There're no directories here");
		}
	} //getDirs
	
	void changeDir(List<String> relativePath) throws IOException {
		if (relativePath.size() > 2) {
			throw new IOException("Cannot handle multiple directory changes at once");
		}
		
		Pattern pattern = Pattern.compile("/", Pattern.CASE_INSENSITIVE);

		try {
			List<String> dirsToFollow = parse(relativePath.get(1), pattern);
			
			for (String dir : dirsToFollow) {
				if ("..".equals(dir)) {
					backToParent();
				} else {
					currentDirTo(dir);
				}
			}
		} catch (IOException ioe) {
			try {
				String uniqueDir = relativePath.get(1);
				
				if ("..".equals(uniqueDir)) {
					backToParent();
				} else {
					currentDirTo(uniqueDir);
				}
			} catch (InvalidPathException ipe) {
				throw new IOException("ERROR: " + ipe.getMessage());
			}
		} catch (InvalidPathException ipe) {
			throw new IOException("ERROR: " + ipe.getMessage());
		}
	} //changeDir
	
	void backToParent() throws InvalidPathException {
		Path parentPath = currentPath.getParent();
		
		if (parentPath == null) {
			throw new InvalidPathException("/home", "Root directory achieved");
		}
		
		currentPath = parentPath;
	} //backToParent
}