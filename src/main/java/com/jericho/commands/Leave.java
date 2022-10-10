// Copyright (c) 2022, Jericho Crosby <jericho.crosby227@gmail.com>

package com.jericho.commands;

import com.jericho.listeners.CommandInterface;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

import static com.jericho.Main.members;

public class Leave implements CommandInterface {

    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getDescription() {
        return "Descend from the Church of V";
    }

    @Override
    public List<OptionData> getOptions() {
        return new ArrayList<>();
    }

    @Override
    public void execute(SlashCommandInteractionEvent e) {
        var userID = e.getUser().getId();

        for (int i = 0; i < members.length(); i++) {
            if (members.getJSONObject(i).getString("id").equals(userID)) {
                members.remove(i);
                e.reply("You have left the **Church of V**").setEphemeral(true).queue();
                return;
            }
        }

        e.reply("You are not already a member of the **Church of V**").setEphemeral(true).queue();
    }
}
