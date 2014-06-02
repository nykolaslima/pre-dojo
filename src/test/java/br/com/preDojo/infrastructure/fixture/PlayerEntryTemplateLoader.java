package br.com.preDojo.infrastructure.fixture;

import br.com.preDojo.application.match.entry.Type;
import br.com.preDojo.application.match.entry.player.PlayerEntry;
import br.com.preDojo.domain.model.match.Player;
import br.com.preDojo.domain.model.match.Weapon;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class PlayerEntryTemplateLoader implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(PlayerEntry.class).addTemplate("valid", new Rule() {{
			add("creationDate", instant("now"));
			add("type", Type.PLAYER_KILL);
			add("killer", one(Player.class, "valid"));
			add("killed", one(Player.class, "valid"));
			add("weapon", one(Weapon.class, "valid"));
		}});
	}

}
