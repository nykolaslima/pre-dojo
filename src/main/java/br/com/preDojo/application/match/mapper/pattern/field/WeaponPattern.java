package br.com.preDojo.application.match.mapper.pattern.field;

import br.com.preDojo.application.match.mapper.pattern.MatchEntryFieldPattern;

public class WeaponPattern implements MatchEntryFieldPattern {

	@Override
	public String getFieldPattern() {
		return "[a-zA-Z0-9]+";
	}

	@Override
	public String getPrefixPattern() {
		return "(using|by)\\s";
	}

	@Override
	public String getSuffixPattern() {
		return null;
	}

}
