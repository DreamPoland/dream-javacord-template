package cc.dreamcode.template.command;

import cc.dreamcode.platform.javacord.component.command.JavacordCommand;
import cc.dreamcode.template.config.MessageConfig;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;

public class ExampleCommand extends JavacordCommand {

    private @Inject MessageConfig messageConfig;

    public ExampleCommand() {
        super("ping", "Ping pong!");
    }

    @Override
    public @NonNull SlashCommandCreateListener respond() {
        return event -> {
            SlashCommandInteraction interaction = event.getSlashCommandInteraction();
            InteractionImmediateResponseBuilder responder = interaction.createImmediateResponder();

            responder.setFlags(MessageFlag.EPHEMERAL);
            this.messageConfig.embedBuilder.applyToResponder(responder, new MapBuilder<String, Object>()
                    .put("user-nick", interaction.getUser().getName())
                    .put("user-id", interaction.getUser().getIdAsString())
                    .put("user-avatar", interaction.getUser().getAvatar().getUrl().toString())
                    .build());

            responder.respond();
        };
    }
}
