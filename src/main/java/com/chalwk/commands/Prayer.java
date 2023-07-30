// Copyright (c) 2023, Jericho Crosby <jericho.crosby227@gmail.com>

package com.chalwk.commands;

import com.chalwk.listeners.CommandInterface;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.ArrayList;
import java.util.List;

import static com.chalwk.Main.members;

public class Prayer implements CommandInterface {

    @Override
    public String getName() {
        return "pray";
    }

    @Override
    public String getDescription() {
        return "Pray to the Church of V";
    }

    @Override
    public List<OptionData> getOptions() {

        List<OptionData> prayers = new ArrayList<>();
        OptionData choices = new OptionData(OptionType.STRING, "prayers", "Select from a list of prayers", false);

        choices.addChoice("Prayer 1", """
                Thanks for making me **funny**.
                Especially since you didn't give me much else to work with...
                """);

        choices.addChoice("Prayer 2", """
                Bless these doughnuts,
                That they will, not be stale...
                """);

        choices.addChoice("Prayer 3", """
                Give me the serenity to accept
                the people who are not pure.
                """);

        prayers.add(choices);

        OptionData custom = new OptionData(OptionType.STRING, "custom", "Say your own prayer", false);
        prayers.add(custom);

        return prayers;
    }

    public void Pray(SlashCommandInteractionEvent e) {

        String optionName = e.getOptions().get(0).getName();
        String option = e.getOption(optionName).getAsString();

        EmbedBuilder msg = new EmbedBuilder();
        msg.setTitle(":pray: **Prayer** :pray:");

        String userMention = e.getUser().getAsMention();
        msg.setDescription(userMention + " *shares a prayer* :pray:");

        msg.addField("Dear V:", option, false);

        msg.setColor(0xffff00);
        e.replyEmbeds(msg.build()).queue();
    }

    @Override
    public void execute(SlashCommandInteractionEvent e) {

        if (members.isEmpty()) {
            e.reply("You cannot pray to the Church of V until you join.").setEphemeral(true).queue();
            System.out.println("length is 0");
            return;
        }

        var userID = e.getUser().getId();
        for (int i = 0; i < members.length(); i++) {
            if (members.getJSONObject(i).getString("id").equals(userID)) {
                Pray(e);
                return;
            }
        }

        e.reply("You cannot pray to the Church of V until you join.").setEphemeral(true).queue();
    }
}
