package br.com.preDojo.application.mapper.pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternEvaluator {

	public String evaluatePattern(MatchEntryFieldPattern fieldPattern, String logEntry) {
		Pattern pattern = Pattern.compile(getPattern(fieldPattern));
		Matcher matcher = pattern.matcher(logEntry);
		if(matcher.find()) {
			return processValue(matcher.group(), fieldPattern);
		} else {
			throw new IllegalArgumentException(String.format("%s -> field for this log entry cannot be defined {%s}", fieldPattern.getClass().getSimpleName(), logEntry));
		}
	}
	
	private String getPattern(MatchEntryFieldPattern fieldPattern) {
		StringBuilder pattern = new StringBuilder();
		if(fieldPattern.getPrefixPattern() != null) {
			pattern.append(fieldPattern.getPrefixPattern());
		}
		
		pattern.append(fieldPattern.getFieldPattern());
		
		if(fieldPattern.getSuffixPattern() != null) {
			pattern.append(fieldPattern.getSuffixPattern());
		}
		
		return pattern.toString();
	}
	
	private String processValue(String value, MatchEntryFieldPattern fieldPattern) {
		String processedValue = value;
		if(fieldPattern.getPrefixPattern() != null) {
			processedValue = processedValue.replaceAll(fieldPattern.getPrefixPattern(), "");
		}
		
		if(fieldPattern.getSuffixPattern() != null) {
			processedValue = processedValue.replaceAll(fieldPattern.getSuffixPattern(), "");
		}
		
		return processedValue;
	}
	
}
