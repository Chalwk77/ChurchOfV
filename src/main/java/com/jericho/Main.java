// Copyright (c) 2022, Jericho Crosby <jericho.crosby227@gmail.com>

package com.jericho;

import com.jericho.commands.Confession;
import com.jericho.commands.Join;
import com.jericho.commands.Leave;
import com.jericho.commands.Prayer;
import com.jericho.listeners.CommandManager;
import com.jericho.listeners.EventListeners;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.security.auth.login.LoginException;
import java.io.IOException;

import static com.jericho.Utilities.FileIO.*;

public class Main {

    public static JSONObject auth;
    public static JSONArray members;

    // Retrieves the settings from the settings.json file.
    static {
        try {
            auth = loadJSONObject("auth.token");
            members = loadJSONArray("members.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final ShardManager shardManager;

    /**
     * Loads environment variables and builds the bot shard manager:
     *
     * @throws LoginException if the bot token is invalid.
     */
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

        shardManager = builder.build();
        shardManager.addEventListener(new EventListeners());

        CommandManager manager = new CommandManager();
        manager.add(new Join());
        manager.add(new Leave());
        manager.add(new Prayer());
        manager.add(new Confession());

        shardManager.addEventListener(manager);
    }

    /**
     * Convenience method for printing to console:
     *
     * @param str The message to print to console.
     */
    public static void cprint(String str) {
        System.out.println(str);
    }

    /**
     * Returns the bot token:
     */
    public static String getToken() {
        return String.valueOf(auth.getString("token"));
    }

    /**
     * Main static method:
     *
     * @param args The arguments passed to the program.
     */
    public static void main(String[] args) {
        try {
            new Main();
        } catch (LoginException e) {
            cprint("ERROR: Provided bot token is invalid");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the shard manager:
     *
     * @return The shardManager instance for the bot.
     */
    public ShardManager getShardManager() {
        return shardManager;
    }
}
