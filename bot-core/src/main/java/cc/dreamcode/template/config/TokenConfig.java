package cc.dreamcode.template.config;

import cc.dreamcode.platform.javacord.component.configuration.Configuration;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

@Configuration(child = "token.yml")
@Header("## Dream-Template (Token) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class TokenConfig extends OkaeriConfig {

    @Comment("Jaki token bot ma obslugiwac?")
    public String token = "";
}
