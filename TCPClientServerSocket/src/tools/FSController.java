package src.tools;

/**
 * FSController: Classe responsável pelos comandos sobre o sistema de arquivos
 * Descrição: Faz a separação dos campos dos comandos e os executam sobre o sistema
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.charset.Charset;
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
	Logger logger = LoggerFactory.getLogger(FSController.class);
	

	String handleCommand(String command) throws IOException {
		List<String> parsedCommand = new ArrayList<>();
		String fileContent = "";
		
		if (command.indexOf("ADDFILE") != -1) {
			fileContent = getFileContent(command);
		}
		
		parsedCommand = parseCommand(command);

		String commandToken = parsedCommand.get(0);

		String response = "";

		if ("CONNECT".equals(commandToken)) {
			logger.info("Client attemption to login");
			if (userAuthentication(parsedCommand)) {
				logger.info("Successful login");
				response = "SUCCESS";
			} else {
				logger.warn("Invalid credentials");
				throw new IOException("Invalid credentials");
			}
		} else if ("PWD".equals(commandToken)) {
			logger.info("PWD command");
			response = getPWD();
		} else if ("CHDIR".equals(commandToken)) {
			changeDir(parsedCommand);
			logger.info("Directory changed to {}", currentPath.toString());
			response = "SUCCESS";
		} else if ("GETFILES".equals(commandToken)) {
			response = getFiles();
			logger.info("GETFILES command");
		} else if ("GETDIRS".equals(commandToken)) {
			response = getDirs();
			logger.info("GETDIRS command");
		} else if ("ADDFILE".equals(commandToken)) {
			createFile(parsedCommand, fileContent);
			logger.info("Creation of the following file: {}", parsedCommand.get(1));
			response = "SUCCESS";
		} else if ("DELETE".equals(commandToken)) {
			deleteFile(parsedCommand);
			logger.info("File {} deleted", parsedCommand.get(1));
			response = "SUCCESS";
		} else if ("GETFILE".equals(commandToken)) {
			downloadFile(parsedCommand);
			logger.info("File {} downloaded to Downloads directory", parsedCommand.get(1));
			response = "SUCCESS";
		} else if ("HELP".equals(commandToken)) {
			logger.info("HELP command");
			response = showCommands();
		} else {
			logger.warn("Unknown command: {}", command);
			throw new IOException("Unknown command");
		}

		return response;
	} // handleCommand
	
	List<String> parseCommand(String buffer) throws IOException {
		Pattern pattern = Pattern.compile("($|\\s+)", Pattern.CASE_INSENSITIVE);

		return parse(buffer, pattern);
	} //parseCommand
	
	String getFileContent(String buffer) throws IOException {
		Pattern pattern = Pattern.compile("(\\\".+\\\")", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(buffer);
		
		if (!matcher.find()) {
			logger.warn("Invalid syntax: {}", buffer);
			throw new IOException("Invalid syntax: ADDFILE fileName \"content\"");
		}

		return buffer.substring(matcher.start() + 1, matcher.end() - 1);
	}

	List<String> parse(String input, Pattern pattern) throws IOException {
		Matcher matcher = pattern.matcher(input);

		if (matcher.find()) {
			String[] sequence = pattern.split(input);

			if (sequence.length > 0) {
				List<String> listTokens = Arrays.asList(sequence);
				return listTokens;
			}
		}
		
		logger.warn("Invalid command: {}", input);
		throw new IOException("Invalid command");
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
		
		logger.warn("Authentication failed");
		throw new IOException("Authentication failed");
	} //userAuthentication
	
	void currentDirTo(String dir) throws InvalidPathException {
		if (USER.equals(dir) && !userLoggedIn) {
			logger.warn("User not authenticated: attempt to enter /home/aluno directory");
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
			String response = String.format("Number of files: %d\nList of files: %s", listFiles.size(), listFiles.toString());
			return response;
		} else {
			logger.warn("There're no files to display");
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
			String response = String.format("Number of directories: %d\nList of directories: %s", listDirs.size(), listDirs.toString());
			return response;
		} else {
			logger.warn("There're no directories to display");
			throw new IOException("There're no directories here");
		}
	} //getDirs
	
	void changeDir(List<String> relativePath) throws IOException {
		if (relativePath.size() > 2) {
			logger.warn("Cannot handle multiple directory changes at once");
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
				logger.warn(ipe.getMessage());
				throw new IOException(ipe.getMessage());
			}
		} catch (InvalidPathException ipe) {
			logger.warn("{}", ipe.getMessage());
			throw new IOException(ipe.getMessage());
		}
	} //changeDir
	
	void backToParent() throws InvalidPathException {
		Path parentPath = currentPath.getParent();
		
		if (parentPath == null) {
			logger.warn("Root directory achieved: /home");
			throw new InvalidPathException("/home", "Root directory achieved");
		}
		
		currentPath = parentPath;
	} //backToParent
	
	void createFile(List<String> parsedCommand, String fileContent) throws IOException {
		if (parsedCommand.size() == 1) {
			logger.warn("Invalid syntax: {}", parsedCommand.get(0));
			throw new IOException("Invalid syntax: ADDFILE fileName \"content\"");
		}
		
		String fileName = parsedCommand.get(1);
		
		Charset charset = Charset.forName("UTF-8");
		
		Path filePath = currentPath.resolve(fileName);
		
		try (BufferedWriter writer = Files.newBufferedWriter(filePath, charset)) {
		    writer.write(fileContent, 0, fileContent.length());
		} catch (IOException ioe) {
			logger.warn(ioe.getMessage());
			throw new IOException(ioe.getMessage());
		}
	} //createFile
	
	void deleteFile(List<String> parsedCommand) throws IOException {
		if (parsedCommand.size() == 1) {
			logger.warn("Invalid syntax: {}", parsedCommand.get(0));
			throw new IOException("Invalid syntax: DELETE fileName");
		}
		
		String fileName = parsedCommand.get(1);
		
		boolean result;
		
		try {
			Path filePath = currentPath.resolve(fileName);
			if (!Files.deleteIfExists(filePath)) {
				logger.warn("This file does not exist: {}", filePath.toString());
				throw new IOException("This file does not exist");
			}
		} catch (InvalidPathException ipe) {
			logger.warn("This file does not exist: {}/{}", currentPath.toString(), fileName);
			throw new IOException("This file does not exist");
		}
	} //deleteFile
	
	void downloadFile(List<String> parsedCommand) throws IOException {
		if (parsedCommand.size() == 1) {
			logger.warn("Invalid syntax: {}", parsedCommand.get(0));
			throw new IOException("Invalid syntax: GETFILE fileName");
		}
		
		String fileName = parsedCommand.get(1);
		
		Path filePath;
		
		try {
			filePath = currentPath.resolve(fileName);
		} catch (InvalidPathException ipe) {
			logger.warn("This file does not exist: {}/{}", currentPath.toString(), fileName);
			throw new IOException("This file does not exist");
		}
		
		Path targetPath = Paths.get("Downloads");
		targetPath = targetPath.resolve(fileName);
			
		Charset charset = Charset.forName("UTF-8");
		
		try (BufferedReader reader = Files.newBufferedReader(filePath, charset)) {
		    String line = null;
			
			try (BufferedWriter writer = Files.newBufferedWriter(targetPath, charset)) {
				int offset = 0;
				
				while ((line = reader.readLine()) != null) {
					writer.write(line, offset, line.length());
					offset += line.length() + 1;
			    }
			} catch (IOException ioe) {
				logger.warn(ioe.getMessage());
				throw new IOException(ioe.getMessage());
			}
		} catch (IOException ioe) {
			logger.warn(ioe.getMessage());
			throw new IOException(ioe.getMessage());
		}
	} //downloadFile

	String showCommands() {
		String listCommands = String.format("CONNECT aluno mudar123\n" +
							  				"PWD\n" +
											"CHDIR nomeDiretorio\n" +
											"GETFILES\n" +
											"GETDIRS\n" +
											"ADDFILE nomeArquivo.ext \"conteudo\"\n" +
											"DELETE nomeArquivo.ext\n" +
											"GETFILE nomeArquivo.ext\n" +
											"EXIT");
		return listCommands;
	}
}