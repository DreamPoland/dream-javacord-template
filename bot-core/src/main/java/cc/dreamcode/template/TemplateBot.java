package cc.dreamcode.template;

import cc.dreamcode.platform.DreamVersion;
import cc.dreamcode.platform.component.ComponentManager;
import cc.dreamcode.platform.javacord.DreamJavacordConfig;
import cc.dreamcode.platform.javacord.DreamJavacordPlatform;
import cc.dreamcode.platform.javacord.component.ConfigurationComponentResolver;
import cc.dreamcode.platform.javacord.exception.JavacordPlatformException;
import cc.dreamcode.platform.javacord.serdes.SerdesJavacord;
import cc.dreamcode.platform.persistence.DreamPersistence;
import cc.dreamcode.platform.persistence.component.DocumentPersistenceComponentResolver;
import cc.dreamcode.platform.persistence.component.DocumentRepositoryComponentResolver;
import cc.dreamcode.template.command.ExampleCommand;
import cc.dreamcode.template.command.ReloadCommand;
import cc.dreamcode.template.config.BotConfig;
import cc.dreamcode.template.config.MessageConfig;
import cc.dreamcode.template.config.TokenConfig;
import cc.dreamcode.template.listener.ExampleListener;
import cc.dreamcode.template.member.MemberRepository;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.persistence.document.DocumentPersistence;
import lombok.NonNull;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.intent.Intent;

import java.util.concurrent.atomic.AtomicReference;

public class TemplateBot extends DreamJavacordPlatform implements DreamPersistence, DreamJavacordConfig {

    /**
     * Basic run main method.
     */
    public static void main(String[] args) {
        DreamJavacordPlatform.run(new TemplateBot(), args);
    }

    @Override
    public @NonNull DiscordApi login(@NonNull ComponentManager componentManager) {
        componentManager.registerResolver(ConfigurationComponentResolver.class);

        final AtomicReference<String> token = new AtomicReference<>();

        componentManager.registerComponent(TokenConfig.class, tokenConfig ->
                token.set(tokenConfig.token));

        return new DiscordApiBuilder()
                .setToken(token.get())
                .addIntents(Intent.MESSAGE_CONTENT)
                .login()
                .whenComplete((discordApi, throwable) -> {
                    if (throwable != null) {
                        throw new JavacordPlatformException("Exception while logging in to Discord");
                    }
                })
                .join();
    }

    @Override
    public void enable(@NonNull ComponentManager componentManager) {
        componentManager.registerComponent(MessageConfig.class);
        componentManager.registerComponent(BotConfig.class, botConfig -> {
            componentManager.setDebug(botConfig.debug);

            // register persistence + repositories
            this.registerInjectable(botConfig.storageConfig);

            componentManager.registerResolver(DocumentPersistenceComponentResolver.class);
            componentManager.registerResolver(DocumentRepositoryComponentResolver.class);

            componentManager.registerComponent(DocumentPersistence.class);
            componentManager.registerComponent(MemberRepository.class);
        });

        componentManager.registerComponent(ExampleListener.class);
        componentManager.registerComponent(ExampleCommand.class);
        componentManager.registerComponent(ReloadCommand.class);
    }

    @Override
    public void disable() {
        // features need to be call when server is stopping
    }

    @Override
    public @NonNull DreamVersion getDreamVersion() {
        return DreamVersion.create("ExampleBot", "1.0-InDEV", "exampleAuthor");
    }

    @Override
    public @NonNull OkaeriSerdesPack getConfigSerdesPack() {
        return registry -> {

        };
    }

    @Override
    public @NonNull OkaeriSerdesPack getPersistenceSerdesPack() {
        return registry -> {
            registry.register(new SerdesJavacord());
        };
    }
}
