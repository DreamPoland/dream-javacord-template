package cc.dreamcode.template.listener;

import cc.dreamcode.template.member.MemberRepository;
import cc.dreamcode.utilities.optional.CustomOptional;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ExampleListener implements MessageCreateListener {

    private final MemberRepository memberRepository;

    /**
     * This method is called every time a message is created.
     *
     * @param event The event.
     */
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        final TextChannel textChannel = event.getChannel();

        if (event.getMessageContent().equalsIgnoreCase("!ping")) {
            CustomOptional.of(event.getMessage().getUserAuthor()).ifPresentOrElse(user ->
                    textChannel.sendMessage("Pong - " + user.getName()),
                    () -> textChannel.sendMessage("Pong!"));
        }
    }
}
