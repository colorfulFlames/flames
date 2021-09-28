package com.severalcircles.flames.buttonaction.data.deleteuserdata;

import com.severalcircles.flames.buttonaction.ButtonAction;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.system.Flames;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.interactions.components.Button;

import java.awt.*;
import java.io.IOException;

public class DeleteUserDataButtonAction implements ButtonAction {
    @Override
    public void execute(ButtonClickEvent event, FlamesUser user) throws IOException {
        event.reply("Alright, let's move to a DM.").complete();
//        event.getMessage().delete();
        MessageEmbed embed = new EmbedBuilder()
                .setAuthor("Delete User Data", null, event.getUser().getAvatarUrl())
                .setTitle(event.getUser().getName() + ", please read the following information carefully.")
                .setDescription("While this operation will delete most information, due to the nature of Flames not everything will be deleted")
                .addField("What Can be Deleted", "All of your actual User Data, like your Flames Score, Rank, and Stats will be deleted. Your Fun Facts and Emotion data will also be deleted.", true)
                .addField("What Might Not be Deleted", "Although Flames will completely delete all records of you it has, the first time it sees you again in any server it's in, a new record will be created. You can decline the privacy policy, of course, but Flames will have to keep a record of you to remember that you declined it.", true)
                .addField("*THIS ACTION IS IRREVERSABLE!*", "For privacy reasons, and to comply with US law, once you request your data to be deleted, it's gone. There is no way we can recover this data whatsoever. Think carefully before committing to this.", false)
                .setColor(Color.RED)
                .setFooter("Flames", Flames.api.getSelfUser().getAvatarUrl()).build();
        event.getUser().openPrivateChannel().complete().sendMessage(embed).setActionRow(Button.danger("reallyDelete", "I understand, let's do it."), Button.success("noDelete", "Never mind, cancel this.")).complete();
    }
}
