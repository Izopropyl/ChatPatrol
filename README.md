# Summary
ChatPatrol is a very basic light weight chat filter system to punish players for sending bad words in your chat. This plugin can also prevent chat spam and excessive caps!

# Features
Stop bad words from reaching your chat & execute punishing commands as a result
Stop chat spam & excessive caps
Add words on the fly and easily reload the config /chatpatrol reload

# Commands
- /chatpatrol reload - Reload config


# Permissions:
- chatpatrol.admin - Access to reload command

# Config:

```
# CHAT PATROL CONFIGURATION | BY JAMPLIFIER
# SUPPORT http://discord.gg/simplestudio

# WORD FILTER
ENABLE-WORD-FILTER: true
BLACKLISTED-WORDS:
  - "badword1"
  - "badword2"

# SPAM FILTER
ENABLE-SPAM-FILTER: true
SPAM-THRESHOLD: 5
SPAM-TIME-WINDOW: 4

# CAPS LOCK FILTER
ENABLE-CAPS-FILTER: true
CAPS-THRESHOLD: 3
CAPS-NOTIFICATION: true

# COMMAND TO EXECUTE FOR PUNISHMENTS
PUNISHMENTS:
  BLACKLISTED-WORDS-COMMAND: "ban {player} You were banned for using inappropriate language!"
  SPAM-COMMAND: "kick {player} You were kicked for spamming!"
```
