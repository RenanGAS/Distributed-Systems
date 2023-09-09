package src.tools;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FSController {
	public static final String ROOT_DIR = "home";
	public static final String USER = "aluno";
	public static final String PASSWORD = "mudar123";
	
	static String handleCommand(String command) throws IOException {
		List<String> parsedCommand = new ArrayList<>();
		parsedCommand = parse(command);
		
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
    	} else {
    		throw new IOException("ERROR: Unknown command");
    	}
    	
    	return response;
    } //handleCommand
	
	static List<String> parse(String buffer) throws IOException {
		Pattern pattern = Pattern.compile("($|\\s+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(buffer);
        
        if (matcher.find()) {
        	String[] command = pattern.split(buffer);
        	
        	if (command.length > 0) {
        		List<String> listTokens = Arrays.asList(command);
            	return listTokens;
        	}
        }
        
        throw new IOException("ERROR: Invalid command");
	} //parse
	
	static boolean userAuthentication(List<String> parsedCommand) throws IOException {
		if (parsedCommand.size() == 3) {
			return (USER.equals(parsedCommand.get(1)) && PASSWORD.equals(parsedCommand.get(2)));
		}
		
		throw new IOException("ERROR: Authentication failed");
	}
	
	static String getPWD() {
		Path path = Paths.get("home");
		Path fullPath = path.toAbsolutePath();
		
		return fullPath.toString();
	} //getPWD
}