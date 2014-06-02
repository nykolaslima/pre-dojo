package br.com.preDojo.infrastructure.fixture;

import br.com.preDojo.domain.model.match.Weapon;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class WeaponTemplateLoader implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(Weapon.class).addTemplate("valid", new Rule() {{
			add("name", random("AK47", "M16", "DROWN"));
		}});
	}

}
