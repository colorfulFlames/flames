# Flames
Light up your Discord Experience

## About Flames
Welcome to Flames, a Discord bot that is about what's happening now. It uses artificial intelligence to analyze messages sent in your server, and then uses that information to create a unique experience for the community.

Flames utilizes Google's natural language processing API to analyze messages. You'll receive a "Flames Score" based on the impact you have on the surrounding community. It also can detect what the conversation is about, both in the channel and the community overall.

## Building

Thanks to the Gradle build system, building Flames is easy. Simply run `gradlew build` in the root directory of the project. This will create a JAR file in the `build/libs` directory.

## Running

### Configuring your Environment
Before you can run Flames, you'll need to set up a couple of variables. For the sake of the scope of this document, we'll assume you already have the following:
- A Discord bot token
- A Google Cloud Platform project with the Natural Language API enabled
- A Google Cloud Platform service account and associated JSON key on your machine

Each of these platforms have great guides on how to get started. Keep in mind that the Natural Language API may not be free.

Once you have all of these, you'll need to set a couple of variables.
- `GOOGLE_APPLICATION_CREDENTIALS` should be set to the path of the JSON key you downloaded from Google Cloud Platform.
- `FlamesToken` should be set to your Discord bot token.

We recommend moving the JAR to a self-contained directory, as Flames will create subdirectories for its data.

### Running Flames
Once you have your environment set up, you can run Flames by running `java -jar [FlamesPlus_version_all].jar` in the directory you moved the JAR to. Make sure to change the bit in the square brackets to the name of the JAR file.
## Contributing
All contributions are welcome! If you'd like to contribute, please fork the repository and submit a pull request. We'll review it as soon as possible.
## License
Flames is licensed under the MIT License. See the LICENSE file for more information.
