// Copyright (c) 2023, Jericho Crosby <jericho.crosby227@gmail.com>

package com.chalwk;

import com.chalwk.commands.Confession;
import com.chalwk.commands.Join;
import com.chalwk.commands.Leave;
import com.chalwk.commands.Prayer;
import com.chalwk.listeners.CommandManager;
import com.chalwk.listeners.EventListeners;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.json.JSONArray;

import javax.security.auth.login.LoginException;
import java.io.IOException;

import static com.chalwk.Utilities.Authentication.getToken;
import static com.chalwk.Utilities.FileIO.*;

public class Main {

    public static JSONArray members;

    static {
        try {
            members = getJSONArray("members.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Main() throws LoginException, IOException {

        String token = getToken();
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.listening("Your prayers"));
        builder.enableIntents(
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_PRESENCES,
                GatewayIntent.MESSAGE_CONTENT
        );

        ShardManager shardManager = builder.build();
        shardManager.addEventListener(new EventListeners());

        CommandManager manager = new CommandManager();
        manager.add(new Join());
        manager.add(new Leave());
        manager.add(new Prayer());
        manager.add(new Confession());

        shardManager.addEventListener(manager);
    }

    public static void main(String[] args) {
        try {
            new Main();
        } catch (LoginException e) {
            System.out.println("ERROR: Provided bot token is invalid.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
