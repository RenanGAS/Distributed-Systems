package src.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageParser {
	List<String> parsedMessage = null;
	
	// Exs. de Msgs:
		// type:"content"
	    // 1:"Eu vou ser o Rei dos Piratas!"
	    // 2:"happy swag ok"
	    // 3:"https://google.com.br https://moodle.utfpr.edu.br"
	    // 4:"salve servidor"
		// 5:"arquivo.ext"
	
	public void parseMsg(String message) throws IOException {
		Pattern pattern = Pattern.compile(":", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(message);
		
		System.out.println("message: " + message);

		if (matcher.find()) {
			int indexSeparator = matcher.start();
			List<String> parsedMsgStructure = new ArrayList<>();
			
			System.out.format("indexSeparador: %d\n", indexSeparator);
			
			String type = message.substring(0, indexSeparator);
			String content = message.substring(indexSeparator + 1);
			
			System.out.format("Tipo: %s\nConteudo: %s\n", type, content);
			
			parsedMsgStructure.add(type);
			parsedMsgStructure.add(content);
			
			this.parsedMessage = parsedMsgStructure;
			return;
		}
		
		throw new IOException("Wrong message structure: type:\"content\"");
	} //parseMsg
	
	public String getMessageType() throws IOException {
		if (this.parsedMessage == null) {
			throw new IOException("The message needs to be parsed first");
		}
		
		return this.parsedMessage.get(0);
	} //getMessageType
	
	public String getMessageContent() throws IOException {
		if (this.parsedMessage == null) {
			throw new IOException("The message needs to be parsed first");
		}
		
		String messageContent = this.parsedMessage.get(1);

		return parseMessageContent(messageContent);
	} //getMessageContent
	
	String parseMessageContent(String messageContent) throws IOException {
		int indexMsgStart = messageContent.indexOf("\"");
		int indexMsgEnd = messageContent.lastIndexOf("\"");
		
		if(indexMsgStart == -1 || indexMsgEnd == -1 || indexMsgStart == indexMsgEnd) {
			throw new IOException("Wrong message structure: \"content\"");
		}
		
		String parsedContentStructure = messageContent.substring(indexMsgStart + 1, indexMsgEnd);
		
		System.out.format("Conteudo: %s", parsedContentStructure);
		
		return parsedContentStructure;
	} //parseMessageContent
}