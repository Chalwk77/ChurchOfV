// Copyright (c) 2022, Jericho Crosby <jericho.crosby227@gmail.com>

package com.jericho.listeners;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.JSONObject;

import javax.annotation.Nonnull;
import java.io.IOException;

import static com.jericho.Main.cprint;
import static com.jericho.Main.members;
import static com.jericho.Utilities.FileIO.writeJSONFile;

public class EventListeners extends ListenerAdapter {

    @Override
    public void onGuildReady(@Nonnull GuildReadyEvent event) {

        Guild guild = event.getGuild();

        cprint("Guild ready: " + guild.getName());
        cprint("Bot name: " + event.getJDA().getSelfUser().getName());

        if (guild.getRolesByName("Pure of Heart", true).isEmpty()) {
            guild.createRole().setName("Pure of Heart").queue();
        }
    }

    @Override
    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event) {

        String userID = event.getUser().getId();
        String button = event.getButton().getId();

        // agree:
        if (button.equals("agree")) {

            for (int i = 0; i < members.length(); i++) {
                JSONObject member = members.getJSONObject(i);
                if (member.getString("id").equals(userID)) {
                    event.reply("You're already a member!").setEphemeral(true).queue();
                    return;
                }
            }

            String userMention = event.getUser().getAsMention();
            event.reply(userMention + " joined the **Church of V**").queue();
            members.put(new JSONObject().put("id", event.getUser().getId()));

            try {

                writeJSONFile(members, "members.json");
                UserSnowflake userSnowflake = UserSnowflake.fromId(event.getUser().getIdLong());
                var role = event.getGuild().getRolesByName("Pure of Heart", true).get(0);
                event.getGuild().addRoleToMember(userSnowflake, role).queue();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // disagree:
        if (button.equals("disagree")) {
            String mention = event.getUser().getAsMention();
            event.reply(mention + " is not pure").queue();
        }
    }
}

/// rank system