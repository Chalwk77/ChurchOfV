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

public class Confession implements CommandInterface {

    @Override
    public String getName() {
        return "confess";
    }

    @Override
    public String getDescription() {
        return "Confess your undesirables to the Church of V";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        OptionData custom = new OptionData(OptionType.STRING, "confession", "Confess your undesirables to the Church of V", true);
        data.add(custom);
        return data;
    }

    public void newConfession(SlashCommandInteractionEvent e) {
        String userMention = e.getUser().getAsMention();

        EmbedBuilder msg = new EmbedBuilder();
        msg.setTitle(":pleading_face::pray::person_bowing: Confession :person_bowing::pray::pleading_face:");
        msg.setDescription(userMention + " *shares a confession*");
        msg.setColor(0xffffff);

        String confession = e.getOption("confession").getAsString();
        msg.addField("", confession, false);

        msg.setColor(0xffff00);
        e.replyEmbeds(msg.build()).queue();
    }

    @Override
    public void execute(SlashCommandInteractionEvent e) {
        newConfession(e);
    }
}
