package br.com.preDojo.application.mapper.pattern.field;

import br.com.preDojo.application.mapper.pattern.MatchEntryFieldPattern;

public class CreationDatePattern implements MatchEntryFieldPattern {

	@Override
	public String getFieldPattern() {
		return "\\d{2}/\\d{2}/\\d{4}\\s\\d{2}:\\d{2}:\\d{2}";
	}

	@Override
	public String getPrefixPattern() {
		return null;
	}

	@Override
	public String getSuffixPattern() {
		return null;
	}

}
