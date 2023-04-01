package cc.dreamcode.template.config;

import cc.dreamcode.platform.javacord.component.configuration.Configuration;
import cc.dreamcode.platform.javacord.serdes.embed.WrappedEmbedBuilder;
import cc.dreamcode.platform.javacord.serdes.notice.Notice;
import cc.dreamcode.platform.javacord.serdes.notice.NoticeType;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.Headers;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

import java.awt.*;

@Configuration(child = "messages.yml")
@Headers({
        @Header("## Dream-Template (Message-Config) ##"),
        @Header("Dostepne type: (MESSAGE, EMBED)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public Notice reloadFailed = new Notice(NoticeType.MESSAGE, "``[ ❌ ] Wystapil problem z przeladowanie konfiguracji! ({reason})``");
    public Notice reloadApplied = new Notice(NoticeType.MESSAGE, "``[ ✅ ] Przeladowano konfiguracje! ({time})``");

    @Comment("Example bot embed")
    public Notice embedBuilder = new Notice(NoticeType.EMBED, new WrappedEmbedBuilder()
            .setTitle("Example title")
            .setDescription("Example description")
            .setAuthor("{user-nick}", "http://google.com/", "https://cdn.discordapp.com/avatars/{user-id}/{user-avatar}.png")
            .addField("A field", "Some text inside the field")
            .addInlineField("An inline field", "More text")
            .addInlineField("Another inline field", "Even more text")
            .setColor(Color.BLUE)
            .setFooter("{user-nick}", "https://cdn.discordapp.com/avatars/{user-id}/{user-avatar}.png"));

}
