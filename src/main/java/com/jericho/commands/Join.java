// Copyright (c) 2022, Jericho Crosby <jericho.crosby227@gmail.com>

package com.jericho.commands;

import com.jericho.listeners.CommandInterface;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.ArrayList;
import java.util.List;

import static com.jericho.Main.members;

public class Join implements CommandInterface {

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getDescription() {
        return "Ascend to the Church of V";
    }

    @Override
    public List<OptionData> getOptions() {
        return new ArrayList<>();
    }

    public void showAggrement(SlashCommandInteractionEvent e) {
        EmbedBuilder msg = new EmbedBuilder();
        msg.setTitle("Agreement");
        msg.setDescription("Conditions for entering the **Church of V**:");
        msg.addField("You must:", """
                1. Be pure.
                2. Be human.
                3. Be a good person.
                """, false);
        msg.setFooter("- Church of V");
        msg.setColor(0xffffff);
        e.replyEmbeds(msg.build()).addActionRow(
                Button.primary("agree", "I agree"),
                Button.danger("disagree", "I disagree")
        ).setEphemeral(true).queue();
    }

    @Override
    public void execute(SlashCommandInteractionEvent e) {
        if (members.length() == 0) {
            showAggrement(e);
        } else {
            var userID = e.getUser().getId();
            for (int i = 0; i < members.length(); i++) {
                if (members.getJSONObject(i).getString("id").equals(userID)) {
                    e.reply("You are already a member of the **Church of V**").setEphemeral(true).queue();
                    return;
                }
            }
            showAggrement(e);
        }
    }
}
