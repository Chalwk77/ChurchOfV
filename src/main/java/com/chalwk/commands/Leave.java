// Copyright (c) 2023, Jericho Crosby <jericho.crosby227@gmail.com>

package com.chalwk.commands;

import com.chalwk.listeners.CommandInterface;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.chalwk.Main.members;
import static com.chalwk.Utilities.FileIO.writeJSONArray;

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
    public void execute(SlashCommandInteractionEvent e) throws IOException {

        var userID = e.getUser().getId();
        for (int i = 0; i < members.length(); i++) {
            if (members.getJSONObject(i).getString("id").equals(userID)) {
                members.remove(i);
                e.reply("You have left the **Church of V**").setEphemeral(true).queue();
                writeJSONArray(members, "members.json");
                return;
            }
        }

        e.reply("You are not already a member of the **Church of V**").setEphemeral(true).queue();
    }
}
