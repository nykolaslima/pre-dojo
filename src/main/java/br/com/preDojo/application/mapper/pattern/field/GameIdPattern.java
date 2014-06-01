package br.com.preDojo.application.mapper.pattern.field;

import br.com.preDojo.application.mapper.pattern.MatchEntryFieldPattern;

public class GameIdPattern implements MatchEntryFieldPattern {

	@Override
	public String getFieldPattern() {
		return "\\d+";
	}

	@Override
	public String getPrefixPattern() {
		return "[mM]atch\\s";
	}

	@Override
	public String getSuffixPattern() {
		return null;
	}

}
