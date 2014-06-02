package br.com.preDojo.infrastructure.fixture;

import br.com.preDojo.application.match.entry.Type;
import br.com.preDojo.application.match.entry.game.GameEntry;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class GameEntryTemplateLoader implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(GameEntry.class).addTemplate("valid", new Rule() {{
			add("creationDate", instant("now"));
			add("type", random(Type.MATCH_START, Type.MATCH_END));
			add("id", regex("\\d{5}"));
		}})
		.addTemplate("matchStart").inherits("valid", new Rule() {{
			add("type", Type.MATCH_START);
		}})
		.addTemplate("matchEnd").inherits("valid", new Rule() {{
			add("type", Type.MATCH_END);
		}});
	}
	
}
