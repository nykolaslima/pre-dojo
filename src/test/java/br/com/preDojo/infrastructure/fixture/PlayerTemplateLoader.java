package br.com.preDojo.infrastructure.fixture;

import br.com.preDojo.domain.model.match.Player;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class PlayerTemplateLoader implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(Player.class).addTemplate("valid", new Rule() {{
			add("name", firstName());
		}});
	}

}
